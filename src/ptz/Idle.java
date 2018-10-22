package ptz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import processing.core.*;
import ptz_camera.Camera;
import ptz_camera.Feed;
import ptz_histogram.Engine_Ball_Bar;

public class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project.
	Background bg;
	DigitalRain dr;
	
    List<Feed> feeds;
	PImage img; 
	Prism skybox;
	
	Drawable center_shape; 
	
	Idle(PApplet p, List<Feed> feeds, PImage default_img) {
		this.parent = p;
		img = default_img;
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);

		this.feeds = feeds;
		
		Optional<List<Feed>> working_feeds = Feed.get_shuffled_list(6);
		if (working_feeds.isPresent() == false) {
			center_shape = new TextureSphere(parent,img,feeds.get(0), 100);
		} else {
			center_shape = new TextureCube(this.parent, img, feeds);
			
		}
		
		skybox = new Prism(0,0,0,800);
		
	}
	
	void draw_outer_prism(List<PImage> images) {
		skybox.rotate();
		parent.pushMatrix();
		skybox.draw(images, images.get(0), images.get(0), parent);
		parent.popMatrix();
	}
	
	void draw() {
		parent.clear();
		parent.background(255);
		parent.fill(255);
		parent.noStroke();
		
		
		List<PImage> images = new ArrayList<PImage>();
		for (int i = 0; i < 8; i++) {
			images.add(img);
		}
		
		parent.pushMatrix();
			parent.translate(
					(float)(parent.width / 2.0f), 
					(float)(parent.height / 2.0f), 
					(float)((parent.height/2.0) / Math.tan(parent.PI * 30.0 / 180.0) )
			);
			int val = skybox.camera_max();
			System.out.println(val);
			parent.translate(0, 0, -val);
			parent.rotateX(parent.PI /2.0f);
			
			draw_outer_prism(images);
			center_shape.draw();
			parent.translate(0, 0, -val);
		parent.popMatrix();
		
		//bg.draw();
		//dr.draw();
	}
}

