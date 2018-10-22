package ptz;

import processing.core.*;
import ptz_camera.Camera;
import ptz_camera.Feed;

class TextureSphere implements Drawable {

	PApplet parent;

	PShape globe;
	
	float a1;
	float a2;
	Feed feed; 
	PImage default_image;
	TextureSphere(PApplet p, PImage default_image, Feed feed, int size) {
		this.parent = p;
		this.default_image = default_image;
		globe = parent.createShape(PApplet.SPHERE, size);
		this.feed = feed;
		
	}

	
	public void draw() {
		parent.pushMatrix();
		
		parent.rotateY(a1);
		parent.rotateX(a2);
		
		globe.setStroke(false);
		parent.shape(globe);
		globe.setTexture(feed.getNextImage(parent).orElse(default_image));
		parent.popMatrix();
		globe.draw(parent.g);
		a1 += .02;
		a2 += .025;
	}
}
