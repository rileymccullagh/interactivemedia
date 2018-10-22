package ptz_camera;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import processing.core.PApplet;
import processing.core.PImage;

public class Feed implements GetImage{
	final String camera_url, wiki; 
	int latest_retrieved;
	int latest_retrieved_time; 
	PImage default_image = null;
	int thread_count = 0;
	final int words_per_feed = 6;
	public String[] words_analysed = new String[]{""};
	int desired_framerate = 1;
	
	public void set_framerate(int val) {this.desired_framerate = val; }
	
	public static int valid_feeds_count() {
		int count = 0;
		for (Feed entry : feeds) {
			if (entry.getNextImage().isPresent()){
				count++;
			}
		}
		return count;
	}
	public void analyse(int num_to_retrieve) {
		if (words_analysed.length < 2) {
			words_analysed = new Word().frequencyAnalysis(wiki, num_to_retrieve);
		}
	}
	
	ArrayList<PImage> images = new ArrayList<PImage>();
	private Feed (String wiki, String cameraurl){
		this.wiki = wiki;
		this.camera_url = cameraurl;
		words_analysed[0] = default_image_filename();
	}
	
	void set_default(PImage image) {
		this.default_image = image;
	}
	
	//WIP
	public Optional<PImage> getNextImage(PApplet parent) {
		int time_difference = parent.millis() - latest_retrieved_time;
		float millis_per_image = 1000.0f / desired_framerate;
		
		if (time_difference > millis_per_image) {
			latest_retrieved_time = parent.millis();
			return getNextImage();
		} else {
			latest_retrieved--;
			return getNextImage();
		}
	}
	
	private Optional<PImage> getNextImage() {
		latest_retrieved++;
		boolean looped_already = false;
		
		
		while (validate_image(latest_retrieved) == false) {
			latest_retrieved++;
			
			if (latest_retrieved++ > images.size() - 1) {
				if (looped_already) {
					return Optional.empty();
				} else {
					latest_retrieved = 0;
					looped_already = true;
				}
			}
		}
		if (validate_image(latest_retrieved)) {
			return Optional.of(images.get(latest_retrieved));
		} else {
			return Optional.empty();
		}
		
	}
	
	public void prune_images () {
		for (int i = -1; i < images.size() - 1; i++) {
			if (validate_image(i + 1) == false) {
				images.remove(i + 1);
			}
		}
	}
	
	public boolean validate_image (int index) {
		
		if (images.size() - 1 < index) {
			return false;
		}
		PImage frame = images.get(index);
		return validate_image(frame);
	}
	
	final int see_through_pixel = -8355712; //This means the download had an error and skipped a portion
	public boolean validate_image (PImage image) {
		if (image == null) {
			return false;
		}
		if (image.height <= 0 | image.width <= 0 ) {
			return false;
		}
		if (image.pixels[((image.width * image.height)) - 1] == see_through_pixel) {
			return false;
		}
		return true;
	}
	
	/*
	 * Doesn't exit until a minimum number of images are retrieved
	 * Will throw if the timeout runs out
	 */
	public static void forcefully_retrieve_feeds (List<Feed> feeds, PApplet parent, final int total_images_per_camera, final int feeds_at_a_time, final int minimum_number_of_images, int timeout) throws InterruptedException {
		download_feeds(feeds,parent,total_images_per_camera, feeds_at_a_time);
		int start_time = parent.second();
		
		while (true) {
			if (parent.second() > start_time + timeout ) {
				throw new InterruptedException();
			}
			
			boolean all_satisfied = true;
			for (Feed feed : feeds) {
				
				if (feed.images.size() < minimum_number_of_images) {
					all_satisfied = false;
				}
			}
			
			if (all_satisfied) {
				return;
			}
		}
		
	}
	
	public static void get_minimum_feeds(int minimum_number, PApplet parent, final int total_images_per_camera, final int feeds_at_a_time) {
			//We assume the list is already shuffled
			int feeds_needed = minimum_number;
			
			while (Feed.valid_feeds_count() < minimum_number && feeds.size() > 0) {
				System.out.println("Beginning Retrieval");
				List<Feed> retrieve = feeds.subList(Feed.valid_feeds_count(), Math.min(feeds.size(), minimum_number));
				
				download_feeds_sync(retrieve, parent, total_images_per_camera, feeds_at_a_time);
				System.out.println("Exited Retrieval");
				
				 
				List<Feed> invalids = new ArrayList();
				
				for (Feed item : retrieve) {
					if (item.getNextImage().isPresent() == false) {
						System.out.println("Removing invalid feed");
						invalids.add(item);
					}
				}
				
				feeds.removeAll(invalids);
				feeds_needed = minimum_number - Feed.valid_feeds_count();
				System.out.println("We need: " + feeds_needed);
			}
			
	}
	
	public static void download_feeds_sync (List<Feed> feeds_to_retrieve, PApplet parent, final int total_images_per_camera, final int feeds_at_a_time) {
		Word analyser = new Word();
		for (int i = 0; i < feeds_to_retrieve.size(); i++) {
			
			if (feeds_to_retrieve.get(i).words_analysed.length == 0) {
				feeds_to_retrieve.get(i).words_analysed = analyser.frequencyAnalysis(feeds_to_retrieve.get(i).wiki, 5);
			}
			
			while (true) {
				int feeds_retrieving = 0;
				for (int j = 0; j < i; j++) {
					if (feeds_to_retrieve.get(j).thread_count > 0) {
						feeds_retrieving++;
					}
				}
				if (feeds_retrieving < feeds_at_a_time) {
					break;
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (Exception e) {	
					
				}
			}
			
			for (int j = 0; j < total_images_per_camera; j++) {
				feeds_to_retrieve.get(i).retrieve_images(parent);
				
				try {
					TimeUnit.MILLISECONDS.sleep(250); 
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		
		while (true)
		{
			boolean exit = true;
			
			for (Feed entry : feeds_to_retrieve) { 
				if (entry.thread_count > 0) { 
					exit = false; 
					System.out.println("Waiting on: " + entry.camera_url);
				} 
			}
			
			if (exit) { break; }
			
			try {
				TimeUnit.MILLISECONDS.sleep(1000); 
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Waiting");
		}
	}	
	
	
	public static void download_feeds (List<Feed> feeds, PApplet parent, final int total_images_per_camera, final int feeds_at_a_time) {
		new Thread (new Runnable() {
			@Override
			public void run() {
				download_feeds_sync(feeds, parent, total_images_per_camera, feeds_at_a_time);
			}
		}).start();
	}
	
	void retrieve_images (PApplet parent) {
		new Thread (new Runnable() 
		{
			@Override
			public void run() {
				++thread_count;
				
				final int index = images.size();
				images.add(null);
				
				try {
					images.set(index, parent.loadImage(camera_url));
				} catch (Exception e) {
					System.out.println("Image retrieval failed");
				}
				--thread_count;
			}}).start();	
	}
	
	private static final List<Feed> feeds;
	
	public static List<Feed> get_all_feeds () {
		return feeds;
	}
	
	public static Feed get_feed (int i) {
		return feeds.get(i);
	}
	private void reset_feed () {
		images = new ArrayList<PImage>();
		latest_retrieved = 0;
	}
	public static void reset_feeds (List<Feed> feeds_to_reset) {
		for (Feed item : feeds_to_reset) {
			item.reset_feed();
		}
	}
	
	/*
	 * Will forcefully download and set the default image for each feed
	*/
	
	public static void forcefully_retrieve_defaults (List<Feed> feeds, PApplet parent, int threads_at_a_time, final int timeout) {
		Stack<Thread> prepared_threads = new Stack<Thread>();
		List<Thread> running_threads = new ArrayList<Thread>();
		
		for (Feed item : feeds ) {
			prepared_threads.add(new Thread (new Runnable() {
			@Override
			public void run() {
				item.set_default_image(parent);
			}	
			}));
		}
		
		for (int i = 0; i < threads_at_a_time; i++) {
			if (prepared_threads.isEmpty()) {
				break;
			}
			running_threads.add(prepared_threads.pop());
		}
		
		while (running_threads.isEmpty() == false) {
			for (int i = 0; i < running_threads.size(); i++) {
				if (running_threads.get(i).isAlive()) {
					continue;
				}
				if (prepared_threads.isEmpty()) {
					running_threads.remove(i);
					i = 0;
					continue;
				}
				
				running_threads.set(i, prepared_threads.pop());
				running_threads.get(i).run();
			}
		}	
	}
	
	String default_image_filename () {
		String name = ("defaults/" + wiki.split("/")[wiki.split("/").length -1] + ".jpg");
		name = name.replace(',', '_');
		return name;
	}
	public static Optional<List<Feed>> get_shuffled_list(int size){
		List<Feed> valid_feeds = get_valid_feeds(feeds);
		
		if (valid_feeds.size() == 0) {
			return Optional.empty();
		}
		
		if (valid_feeds.size() == size - 1) {
			//Add webcam as a feed
		}
		
		if (valid_feeds.size() < size) {
			//What to do when we don't have enough
			
		} 
		
		Collections.shuffle(valid_feeds);
		return Optional.of(valid_feeds.subList(0, size));
	}
	
	public static List<Feed> get_valid_feeds(List<Feed> masterList){
		List<Feed> valids = new ArrayList<Feed>();
		for (Feed entry : masterList) {
			if (entry.getNextImage().isPresent()) {
				valids.add(entry);
			}
		}
		return valids;
	}
	
	public void set_default_image(PApplet parent) {
		boolean retrieve_default = false;
		
		if (parent.dataFile(default_image_filename()).exists() && retrieve_default) {
			this.default_image = parent.loadImage(default_image_filename());
			
		} else {
			//We could add a local image here that we can default back to
			this.default_image = parent.loadImage(camera_url);
			//We also save it for when we run again
			if (validate_image(default_image)) {
				this.default_image.save(default_image_filename()); 
			}
		}
	}
	
	boolean ping () {
		try{
            InetAddress address = InetAddress.getByName(camera_url);
            return address.isReachable(10000);
        } catch (Exception e){
        	return false;
        }
	}
	
	static {
		feeds = new ArrayList<Feed>();
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Falmouth,_Cornwall", "http://31.51.157.21/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Boston", "http://24.60.80.6:8090/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Bucharest", "http://109.102.176.21/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Mount_Laurel,_New_Jersey", "http://96.78.107.22/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/San,_Argentina", "http://200.107.99.38:8080/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Little_Rock,_Arkansas", "http://162.40.158.193/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Santiago", "http://181.75.96.249:5000/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/San_Juan,_Trinidad_and_Tobago", "http://190.213.182.16:8084/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Nuremberg", "http://93.214.124.11/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Nov%C3%A1ky", "http://85.163.238.8/cgi-bin/viewer/video.jpg"));
	}
}