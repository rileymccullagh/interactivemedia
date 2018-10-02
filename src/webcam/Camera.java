package webcam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

// cameras from here work
// https://www.insecam.org/en/bytype/Vivotek/

import processing.core.PApplet;
import processing.core.PImage;

public class Camera {

	private static final List<String> wikis;
	public static final List<String> cameras; //Its final, may as well expose it
	private static int lastCamera;

	static {
		lastCamera = -1;
		wikis = new ArrayList<String>();
		cameras = new ArrayList<String>();

		//https://www.insecam.org/en/view/742551/
		wikis.add("https://en.wikipedia.org/wiki/Santa_Fe,_Argentina");
		cameras.add("http://200.107.99.38:8080/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/739612/
		wikis.add("https://en.wikipedia.org/wiki/Falmouth,_Cornwall");
		cameras.add("http://31.51.157.21/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/722317/
		wikis.add("https://en.wikipedia.org/wiki/Boston");
		cameras.add("http://24.60.80.6:8090/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/739611/
		wikis.add("https://en.wikipedia.org/wiki/Bucharest");
		cameras.add("http://109.102.176.21/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/738801/
		wikis.add("https://en.wikipedia.org/wiki/Mount_Laurel,_New_Jersey");
		cameras.add("http://96.78.107.22/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/737944/
		wikis.add("https://en.wikipedia.org/wiki/Little_Rock,_Arkansas");
		cameras.add("http://162.40.158.193/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/737755/
		wikis.add("https://en.wikipedia.org/wiki/Santiago");
		cameras.add("http://181.75.96.249:5000/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/724772/
		wikis.add("https://en.wikipedia.org/wiki/San_Juan,_Trinidad_and_Tobago");
		cameras.add("http://190.213.182.16:8084/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/723422/
		wikis.add("https://en.wikipedia.org/wiki/Nuremberg");
		cameras.add("http://93.214.124.11/cgi-bin/viewer/video.jpg");

		//https://www.insecam.org/en/view/722965/
		wikis.add("https://en.wikipedia.org/wiki/Nov%C3%A1ky");
		cameras.add("http://85.163.238.8/cgi-bin/viewer/video.jpg");
	}
	
	PApplet parent;
	 
	Map<String,List<PImage>> images_retrieved = new HashMap<String,List<PImage>>();
	Map<String, Integer> latest_image = new HashMap<String,Integer>(); //Changes per webcam
	
	PImage empty_image;
	List<Thread> running_threads = new ArrayList<Thread>();

	
	final int max_number_of_retrieval_threads = 6;
	int num_of_threads = 0; //The current number of running threads
	int threads_being_initialised = 0; //Threads that are NOT running, but WILL run 
	
	String current_url = ""; //Changes per webcam
	boolean cancel_threads = false;

	public Camera(PApplet p) {
		this.parent = p;
		empty_image = p.createImage(0, 0, 0);
		
		for (String entry : cameras) {
			images_retrieved.put(entry, new ArrayList<PImage>());
			latest_image.put(entry, 0);
		}
		// get a random camera from the list
		Random rand = new Random();
		int  camNum = rand.nextInt(cameras.size());
		if(camNum == Camera.lastCamera) {
			camNum++;
			camNum = camNum % cameras.size();
		}
		
		current_url = cameras.get(camNum);
	}

	public void prune_images (String camera_url) {
		for (int i = -1; i < images_retrieved.get(current_url).size() - 1; i++) {
			if (validate_image(current_url, i + 1) == false) {
				images_retrieved.get(current_url).remove(i + 1);
			}
		}
	}
	public void cancel_threads_then_download_images (String url, int amount_of_images ) {
		new Thread (new Runnable() {
			@Override
			public void run() {
				cancel_threads();
				
				while (cancel_threads){ 
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e) {
						
					}
				}
				download_multiple_images(url, amount_of_images);
			}});
	}

	public void cancel_threads () {
		
		if (num_of_threads > 0) {
			cancel_threads = true;
			System.out.println("Cancelling Threads");
		}
	}
	

	/* 
	 * This function will start download the next x images, each in their own thread.
	*/
	public void download_multiple_images(String url, int amount_of_images) {
				threads_being_initialised += amount_of_images;
				download_multiple_images(url, amount_of_images, 0, amount_of_images);
	}
	
	/*
	*	how_many_before_next_image means: download 3 in a row, then get 3 in a row for the next image...
	*	How many total is how many do you want to retrieve in total
	*/
	public void download_multiple_images_in_sequence (List<String> images, final int how_many_before_next_image, final int how_many_total) {
		new Thread (new Runnable() {
			int how_many_total_copy = how_many_total;
			int how_many_before_next_image_copy = how_many_before_next_image;
			@Override
			public void run() {
				while (how_many_total_copy > 0) {
					for (String item : images) {
						
						/*
						*  Lets say images.size() = 6
						*  The max number of threads is 12
						*  We want 6 images in total
						*  We want 3 images in sequence
						*
						*  If we are already running 10 threads, running another 3 will put us over the limit
						*  so instead we will wait for another to finish execution.
						*
						*  Remember, we want to run as many in SEQUENCE as possible, or else the feed has gaps
						*/

						while (num_of_threads + threads_being_initialised + how_many_before_next_image > max_number_of_retrieval_threads)
						{
							System.out.println("Num of threads holding up: " + num_of_threads);
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						download_multiple_images(item, how_many_before_next_image_copy);
					}
					how_many_total_copy -= how_many_before_next_image_copy;
				}
			}
		}).start();
	}
	/*
	 * This is a recursive function, the parent caller must set max_num_of_recursive_calls start to 0.
	 * Don't ever call this function. Call the other one which calls this one. 
	*/
	private void download_multiple_images(String camera_url, int amount_of_images, int first_index, int max_num) {
		
		if (amount_of_images < 0) { 
			//prune_images(camera_url);
			if (max_num > images_retrieved.get(camera_url).size()) {
				amount_of_images = first_index - images_retrieved.get(camera_url).size();
				first_index = images_retrieved.get(camera_url).size();
			} else {
				return; 
			}
		}
		

		/*
		 *  The first_index val ensures that the next recursive call does not simultaneously
		 *  begin retrieving i = 3, by starting instead at 3.
		*/

		for (int i = first_index; i < images_retrieved.get(camera_url).size(); ++i){
			if (validate_image(camera_url,i) == false) {
				break;
			} 
			first_index++;
		}

		final int pos_to_start = first_index;
		final int image_amount = amount_of_images;
		++num_of_threads;
		--threads_being_initialised;
		new Thread (new Runnable() {
			final int position_to_insert = pos_to_start; //Don't move this into run
			final int thread_num = num_of_threads - 1; 
			final String url_of_image = camera_url;
			@Override
			public void run() {
				System.out.println("Initialised thread: " + thread_num + " for " + url_of_image);
				
				try{
					/* 
					 * We can't push to the end of the list, as for example: Thread #5 may 
					 * finish earlier than Thread #4, what we do is put in an empty entry
					 * which is later overriden 
					 */
					while(position_to_insert > images_retrieved.get(url_of_image).size()){
						images_retrieved.get(url_of_image).add(empty_image);
					}

					boolean slept = false;
					while (num_of_threads >= max_number_of_retrieval_threads) {
						TimeUnit.MILLISECONDS.sleep(250);
						slept = true;
					}
					/*
					 * When we fire 5 threads, even if we initialise them in order the parent.loadimage(url)
					 * function will not necessarily be called in order, meaning thread 5 may retrieve before
					 * thread 4. This will cause the images to become out of order. Adding a small delay will 
					 * ensure that the threads run in sequence. 
					 * 
					 * An alternative explanation is that this disorganisation is caused by println. 
					*/
					if (slept == false) { TimeUnit.MILLISECONDS.sleep(500); } 
					
					if (cancel_threads == false) {
						download_multiple_images(url_of_image, image_amount - 1, position_to_insert + 1, max_num);
						PImage new_image = parent.loadImage(camera_url);
						
						//An error may occur, so we only insert on success
						if (validate_image(new_image) && cancel_threads == false) {
							images_retrieved.get(url_of_image).set(position_to_insert, new_image);
							System.out.println("Inserted at: " + position_to_insert + " thread num: " + thread_num);
							//Must be done constantly to ensure the retrieval function will get a valid -sequence- 
						} else {
							System.out.println("Image invalid or thread cancelled");
						}
					} else {
						System.out.println("Thread Cancelled");
					}
				} catch (Exception e) {
					//Swallow the exception
				}
				--num_of_threads;
				if (cancel_threads && num_of_threads == 0) {
					cancel_threads = false;
				}
			}
		}).start();
	}
	
	public boolean validate_image (String camera_url, int image_index) {
		
		if (images_retrieved.get(camera_url).size() - 1 < image_index) {
			return false;
		}
		PImage frame = images_retrieved.get(camera_url).get(image_index);
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

	public PImage getNextImage(String camera_url) {
		if (images_retrieved.get(camera_url).isEmpty()){
			return empty_image;
		} else {
			if (num_of_threads == 0 && threads_being_initialised == 0) {
				prune_images(camera_url);
			}
			
			latest_image.put(camera_url, latest_image.get(camera_url) + 1);
			
			boolean looped_already = false;
			while (validate_image(camera_url, latest_image.get(camera_url)) == false) {
				latest_image.put(camera_url, latest_image.get(camera_url) + 1);
				
				if (latest_image.get(camera_url) > images_retrieved.get(camera_url).size() - 1) {
					if (looped_already) {
						return empty_image;
					} else {
						latest_image.put(camera_url, 0);
						looped_already = true;
					}
				}
			}
			return images_retrieved.get(camera_url).get(latest_image.get(camera_url));
		}
	}

}

