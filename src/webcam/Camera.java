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
  String pre_string = "http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=";
  int current_number = 1538256987;
  int position = -1;
  int latest_retrieved = 0;
  
  PImage current, future;
  boolean newImage;
  boolean retrieving = false;
  public Camera(PApplet p) {
	this.parent = p;
	getAsync();
	empty_image = p.createImage(0, 0, 0);
  }
  
  public void update_image() {
	  new Thread (new Runnable() {
			@Override
			public void run() {
				position++;
				current_number--;
				images.add(position, parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=" + Integer.toString(current_number)));
				System.out.println("EXECUTED ASYNC");
			}
			}).start();
  }
  
  public void get_amount_of_images(int amount) {
	  for (int i = 0; i < amount; ++i) {
		  ++position;
		  current_number--;
		  
		  new Thread (new Runnable() {
			  int position_to_insert = position; //Don't move this into run
			  @Override
				public void run() {
				  try{
					  images.add(empty_image);
					  
					  PImage new_image = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=" + Integer.toString(current_number));
					  if (new_image == null) {
						  images.remove(position_to_insert);
					  }
					  else {
						  images.set(position_to_insert, new_image);
					  }
					  System.out.println(position_to_insert);
				  } catch (Exception e) {
					  System.out.println(position_to_insert);
					  System.out.println(Integer.toString(current_number));
				  }
			  }
		  }).start();
	  }
  }
  
  public PImage getNext() {
	  if (images.isEmpty()){
		  return empty_image;
	  } else {
		  latest_retrieved %= images.size();
		  if (images.get(latest_retrieved).height > 0) {
			  if (images.size() != 1) {
				  latest_retrieved++;
			  }
			  return images.get(latest_retrieved % images.size());
		  } else {
			  latest_retrieved %= images.size();
			  return images.get(0);
		  }
	  }
  }
  
  public void getAsync() {
	  this.current = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=1537798410");
	  this.newImage = true;
	}
}

