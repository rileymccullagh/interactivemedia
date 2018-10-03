package webcam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
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
	
	/*
	*	Im too tired. 
	*	This basically makes sets of sets. Then runs those sets, one set at a time. 
	*/
	public void download_multiple_images_in_sequence (List<String> images, final int how_many_before_next_image, int how_many_total, int feeds_at_a_time) {
		new Thread (new Runnable() {
			int amount_remaining = how_many_total;
			@Override
			public void run() {
				for (int i = 0; i < how_many_total + 1; i++) {
					for (String item : images) {
						images_retrieved.get(item).add(empty_image);
					}
				}
				
				ArrayList<ArrayList<String>> wrapped_list = new ArrayList<ArrayList<String>>();
				ArrayList<String> temp = new ArrayList<String>();
				int counter = feeds_at_a_time;
				for (String item : images) {
					temp.add(item);
					
					counter--;
					if (counter == 0) {
						counter = feeds_at_a_time;
						ArrayList<String> copy = new ArrayList<String>(temp);
						wrapped_list.add(copy);
						temp = new ArrayList<String>();
					}
				}
				if (wrapped_list.isEmpty() == false ) {
					wrapped_list.add(temp);
				}
				List<ArrayList<Thread>> queue = new ArrayList<ArrayList<Thread>>();
				
				int insertion_index = 0;
				while (amount_remaining > 0 ) {
					
					
					for (ArrayList<String> inner_list : wrapped_list) {
						ArrayList<Thread> inner_queue = new ArrayList<Thread>();
						for (String item : inner_list) {
							for (int j = 0; j < how_many_before_next_image; j++) {
								
								final int insert_at = insertion_index + j;
								System.out.println("Created at: " + item + " at: "  + insert_at);
								inner_queue.add(new Thread (new Runnable() 
									{
										String url = item;
										@Override
										public void run() {
											System.out.println("running at: " + url + " at: "  + insert_at);
											images_retrieved.get(url).set(insert_at, parent.loadImage(url));
											System.out.println("Inserted at: " + url + " at: "  + insert_at);
										}}));
								
							}	
						} 
						
						if (inner_queue.size() > 0) {
							queue.add(inner_queue);
						}
					}
					insertion_index  += how_many_before_next_image;
					amount_remaining -= how_many_before_next_image;
				}
				for (int i = 0; i < queue.get(0).size() -1; i++) {
					queue.get(0).get(i).start();
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				while (true) {
					boolean any_alive = false;
					for (Thread running : queue.get(0)) {
						if (running.isAlive()) {
							any_alive = true;
						}
					}
					if (any_alive == false) {
						queue.remove(0);
						if (queue.size() == 0) {
							break;
						}
						
						for (Thread t : queue.get(0)) {
							t.start();
							try {
								TimeUnit.MILLISECONDS.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
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
			latest_image.put(camera_url, latest_image.get(camera_url) + 1);
			
			boolean looped_already = false;
			//while (true) {
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

