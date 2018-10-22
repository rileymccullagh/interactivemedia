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
	TextureSphere(PApplet p, PImage default_image, Feed feed) {
		this.parent = p;
		this.default_image = default_image;
		globe = parent.createShape(PApplet.SPHERE, 175);
		this.feed = feed;
		
		/*
		this.default_image = p.createImage(640, 640, p.ARGB);
		for (int i = 0; i < p.pixels.length; i++) { p.pixels[i] = 150;} 
		*/
	}

	
	public void draw() {
		parent.pushMatrix();
		parent.translate(parent.width / 2, parent.height / 2, 0);
		parent.rotateY(a1);
		parent.rotateX(a2);
		
		globe.setStroke(false);
		parent.shape(globe);
		globe.setTexture(feed.getNextImage(parent).orElse(default_image));
		parent.popMatrix();
		a1 += .02;
		a2 += .025;
		//globe.draw(parent.g);
	}
}
