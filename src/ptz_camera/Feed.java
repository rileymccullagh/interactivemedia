package ptz_camera;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import processing.core.PApplet;
import processing.core.PImage;

public class Feed {
	final String camera_url, wiki; 
	int latest_retrieved;
	PImage default_image = null;
	int thread_count = 0;
	
	ArrayList<PImage> images = new ArrayList<PImage>();
	private Feed (String wiki, String cameraurl){
		this.wiki = wiki;
		this.camera_url = cameraurl;
	}
	
	void set_default(PImage image) {
		this.default_image = image;
	}
	public PImage getNextImage() {
		latest_retrieved++;
			
		boolean looped_already = false;
			//while (true) {
		while (validate_image(latest_retrieved) == false) {
			latest_retrieved++;
			
			if (latest_retrieved++ > images.size() - 1) {
				if (looped_already) {
					return default_image;
				} else {
					latest_retrieved = 0;
					looped_already = true;
				}
			}
		}
		return images.get(latest_retrieved);
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
	
	
	public static void download_feeds (List<Feed> feeds, PApplet parent, final int total_images_per_camera, final int feeds_at_a_time) {
		new Thread (new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < feeds.size(); i++) {
					while (true) {
						int feeds_retrieving = 0;
						for (int j = 0; j < i; j++) {
							if (feeds.get(j).thread_count > 0) {
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
						feeds.get(i).retrieve_images(parent);
						try {
							TimeUnit.MILLISECONDS.sleep(250); 
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
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
	static {
		feeds = new ArrayList<Feed>();
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Santa_Fe,_Argentina", "http://200.107.99.38:8080/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Falmouth,_Cornwall", "http://31.51.157.21/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Boston", "http://24.60.80.6:8090/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Bucharest", "http://109.102.176.21/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Mount_Laurel,_New_Jersey", "http://96.78.107.22/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Santa_Fe,_Argentina", "http://200.107.99.38:8080/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Little_Rock,_Arkansas", "http://162.40.158.193/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Santiago", "http://181.75.96.249:5000/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/San_Juan,_Trinidad_and_Tobago", "http://190.213.182.16:8084/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Nuremberg", "http://93.214.124.11/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Nov%C3%A1ky", "http://85.163.238.8/cgi-bin/viewer/video.jpg"));
	}
}