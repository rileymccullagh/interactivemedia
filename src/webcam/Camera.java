package webcam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

// cameras from here work
// https://www.insecam.org/en/bytype/Vivotek/

import processing.core.PApplet;
import processing.core.PImage;

public class Camera {
  PApplet parent;
  List<PImage> images = new ArrayList<PImage>();
  PImage empty_image;
  
  int num_of_images = 0;
  int num_of_threads = 0;
  
  
  String pre_string = "http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r="; //Changes per webcam
  int latest_image = 1538256987; //Changes per webcam
  
  int longest_sequence_of_valid_images_from_0 = 0; //Number of images in List<Pimage> images  that are valid, starting from 0

  public Camera(PApplet p) {
	this.parent = p;
	empty_image = p.createImage(0, 0, 0);
  }
  
  public int get_largest_sequence_of_valid_images_from_0() {
	  for (int i = 0; i < images.size(); ++i){
		  if (images.get(i) == null | images.get(i).height <= 0){
			  return i;
		  }
	  }
	  return images.size() - 1;
  }

  /* 
   * This function will start download the next x images, each in their own thread
   * However if an error occured when downloading an image previously, it will attempt
   * to redownload that image
  */
  public void download_multiple_images(int amount_of_images) {
	  download_multiple_images(amount_of_images, 0);
  }

  /*
   * This is a recursive function, the parent caller must set earliest start to 0.
  */
  private void download_multiple_images(int amount_of_images, int first_index) {
		if (amount_of_images < 0) { return; }
		int image_to_download  = latest_image; //We start at the latest image, and work backwards
		
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
			image_to_download--;
			first_index++;
		}
		
		final int pos_to_start = first_index;
		
		new Thread (new Runnable() {
			int position_to_insert = pos_to_start; //Don't move this into run
			//int num_of_img = amount
			@Override
			public void run() {
				num_of_threads++;
				try{
					/* 
					 * We call this from here, that way if we want to add a delay between 
					 * each retrieval it takes place outside of the main thread. 
					*/
	
					download_multiple_images(amount_of_images - 1, pos_to_start + 1);
	
					/* This function may not be necessarily appending to the end of the list, 
					 * or it may be appending more further back than the end of the list
					 * so we do the following to fill the array up
					*/
					while(position_to_insert > images.size()){
						images.add(empty_image);
					}
	
	
					PImage new_image = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=" + Integer.toString(pos_to_start));
					
					//An error may have occured, so we only insert on success
					if (new_image != null) {
						images.set(position_to_insert, new_image);
						System.out.println("Inserted at: " + position_to_insert);
						//Must be done constantly to ensure the retrieval function will get a valid -sequence- 
						longest_sequence_of_valid_images_from_0 = get_largest_sequence_of_valid_images_from_0();
					}
				} catch (Exception e) {
					//System.out.println("Error at: " + Integer.toString(current_number) + " i = " position_to_insert);
				}
				num_of_threads--;
			}
		}).start();
  }
  
  private int latest_retrieved = 0; 
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

		  if (++latest_retrieved >= longest_sequence_of_valid_images_from_0){
			  latest_retrieved = 0;
		  }
		  if (num_of_threads == 0) {
			  longest_sequence_of_valid_images_from_0 = get_largest_sequence_of_valid_images_from_0();
			  System.out.println("We have: " + longest_sequence_of_valid_images_from_0 + " valid images");
			  System.out.println("Starting new retrieval");
			  download_multiple_images(2);
		  }
		  
		  System.out.println("Rendering:  " + latest_retrieved % images.size());
		  return images.get(latest_retrieved % images.size());
	  }
  }
  
  public void getAsync() {
	  //this.current = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=1537798410");
	  //this.newImage = true;
	}
}

