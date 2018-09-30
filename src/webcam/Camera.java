package webcam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

// cameras from here work
// https://www.insecam.org/en/bytype/Vivotek/

import processing.core.PApplet;
import processing.core.PImage;

public class Camera {

	private static final List<String> wikis;
	private static final List<String> cameras;
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
	List<PImage> images = new ArrayList<PImage>();
	PImage empty_image;

	int num_of_images = 0;
	int num_of_threads = 0;
	final int max_number_of_retrieval_threads = 9;


	String url = ""; //Changes per webcam
	int latest_image = 0; //Changes per webcam

	public Camera(PApplet p) {
		this.parent = p;
		empty_image = p.createImage(0, 0, 0);
		
		// get a random camera from the list
		Random rand = new Random();
		int  camNum = rand.nextInt(cameras.size());
		if(camNum == Camera.lastCamera) {
			camNum++;
			camNum = camNum % cameras.size();
		}
		
		url = cameras.get(camNum);
	}

	public void prune_images () {
		for (int i = -1; i < images.size() - 1; i++) {
			if (validate_image(i + 1) == false) {
				images.remove(i + 1);
			}
		}
	}
	/* 
	 * This function will start download the next x images, each in their own thread
	 * However if an error occured when downloading an image previously, it will attempt
	 * to redownload that image
	 */
	public void download_multiple_images(int amount_of_images) {
		download_multiple_images(amount_of_images, 0, amount_of_images);
	}

	/*
	 * This is a recursive function, the parent caller must set earliest start to 0.
	 */
	private void download_multiple_images(int amount_of_images, int first_index, int max_num) {
		if (amount_of_images < 0) { 
			prune_images();
			if (max_num > images.size()) {
				amount_of_images = first_index - images.size();
				first_index = images.size();
			} else {
				return; 
			}
		}
		

		/*
		 *  Lets say that due to an error occuring, you have the following in the array: 
		 *  YYYNYYY
		 *  Instead of just starting to insert at the end, we check one by one to see if each 
		 *  image is valid. So in this instance, when i = 3 it will realise it is an invalid 
		 *  image, then it will begin to retrieve that image instead. 
		 *  
		 *  The first_index val ensures that the next recursive calll does not simultaneously
		 *  begin retrieving i = 3, by starting instead at 3
		 *	
		 */

		for (int i = first_index; i < images.size(); ++i){
			if (images.get(i).height <= 0) {
				break;
			} 
			first_index++;
		}

		final int pos_to_start = first_index;
		final int image_amount = amount_of_images;
		new Thread (new Runnable() {
			final int position_to_insert = pos_to_start; //Don't move this into run
			final int thread_num = num_of_threads; 
			@Override
			public void run() {
				++num_of_threads;
				try{
					
					/* 
					 * We call this from here, that way if we want to add a delay between 
					 * each retrieval it takes place outside of the main thread. 
					 */

					

					/* This function may not be necessarily appending to the end of the list, 
					 * or it may be appending more further back than the end of the list
					 * so we do the following to fill the array up
					 */
					while(position_to_insert > images.size()){
						images.add(empty_image);
					}

					boolean slept = false;
					while (num_of_threads >= max_number_of_retrieval_threads) {
						TimeUnit.SECONDS.sleep(1);
						slept = true;
					}
					
					if (slept == false) { TimeUnit.MILLISECONDS.sleep(500); }
					
					download_multiple_images(image_amount - 1, position_to_insert + 1, max_num);
					PImage new_image = parent.loadImage(url);
					
					//An error may have occured, so we only insert on success
					if (new_image != null) {
						images.set(position_to_insert, new_image);
						System.out.println("Inserted at: " + position_to_insert + " thread num: " + thread_num);
						//Must be done constantly to ensure the retrieval function will get a valid -sequence- 
					}
				} catch (Exception e) {
					//System.out.println("Error at: " + Integer.toString(current_number) + " i = " position_to_insert);
				}
				--num_of_threads;
			}
		}).start();
	}
	
	final int see_through_pixel = -8355712; //This means the download had an error and skipped a portion
	
	public boolean validate_image (int image_index) {
		if (images.size() < image_index + 1) {
			return false;
		} 
		if (images.get(image_index) == null) {
			return false;
		}
		if (images.get(image_index).height <= 0 | images.get(image_index).width <= 0 ) {
			return false;
		}
		if (images.get(image_index).pixels[((images.get(image_index).width * images.get(image_index).height)) - 1] == see_through_pixel) {
			return false;
		}
		return true;
	}

	//Will also loopback
	public PImage getNextImage() {
		if (images.isEmpty()){
			return empty_image;
		} else {
			/*
			 * if you are having issues with this function, you may need to do the following, 
			 * however this should be handled in get_multiple_images()
			 * last_valid_image = get_largest_sequence_of_valid_images_from_0();
			 */

			if (num_of_threads == 0) {
				prune_images();
			}
			
			++latest_image;
			while (validate_image(latest_image) == false) {
				++latest_image;
				latest_image %= images.size();
			}
			
			return images.get(latest_image);
		}
	}

}

