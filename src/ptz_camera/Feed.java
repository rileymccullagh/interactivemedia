package ptz_camera;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import processing.core.PApplet;
import processing.core.PImage;

public class Feed {
	final String camera_url, wiki; 
	int latest_retrieved;
	PImage default_image = null;
	int thread_count = 0;
	
	ArrayList<PImage> images = new ArrayList<PImage>();
	Feed (String wiki, String cameraurl){
		
		this.wiki = wiki;
		this.camera_url = cameraurl;
		this.default_image = default_image;
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
	
	void retrieve_images (int count, PApplet parent) {
		if (count < 1) {
			return;
		} else {
			new Thread (new Runnable() 
			{
				@Override
				public void run() {
					try {
						TimeUnit.MILLISECONDS.sleep(250);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					++thread_count;
					
					final int index = images.size();
					images.add(null);
					retrieve_images(index - 1, parent);
					
					try {
						images.set(index, parent.loadImage(camera_url));
					} catch (Exception e) {
						
					}
					--thread_count;
				}}).start();
		}
		
	}
	
	
	
}