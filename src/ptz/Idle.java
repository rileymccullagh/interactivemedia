package ptz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.*;
import ptz_camera.Camera;
import ptz_camera.Feed;
import ptz_histogram.Engine_Ball_Bar;

public class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project.
	Background bg;
	DigitalRain dr;
	TextureCube tc;
    List<Feed> feeds;
	PImage img; 
	Prism skybox;
	
	Idle(PApplet p, List<Feed> feeds) {
		this.parent = p;
		img = parent.loadImage("http://96.78.107.22/cgi-bin/viewer/video.jpg");
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		tc = new TextureCube(this.parent, img);
		this.feeds = feeds;
		
		skybox = new Prism(0,0,0,1000);
		
	}
	
	void draw_outer_prism(List<PImage> images) {
		
		skybox.rotate();
		parent.pushMatrix();
		
		parent.translate(
				(float)(parent.width / 2.0f), 
				(float)(parent.height / 2.0f), 
				(float)((parent.height/2.0) / Math.tan(parent.PI * 30.0 / 180.0) )
		);
		
		parent.rotateX(parent.PI /2.0f);
		
		skybox.draw(images, images.get(0), images.get(0), parent);
		parent.popMatrix();
	}
	
	void draw() {
		parent.clear();
		parent.background(255);
		parent.fill(255);
		parent.noStroke();
		
		
		List<PImage> images = new ArrayList<PImage>();
		for (int i = 0; i < 4; i++) {
			images.add(img);
		}
		
		draw_outer_prism(images);
		
		tc.draw(feeds);
		
		//bg.draw();
		//dr.draw();
		
		
		//draw_hollow();
	}
}
