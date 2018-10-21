package ptz;

import processing.core.*;
import ptz_camera.Camera;
import ptz_camera.Feed;

class TextureSphere {

	PApplet parent;

	PShape globe;
	float a1;
	float a2;
	Feed feed; 
	PImage default_image;
	TextureSphere(PApplet p, PImage default_image) {
		this.parent = p;
		this.default_image = default_image;
		globe = parent.createShape(PApplet.SPHERE, 175);
		globe.textureMode(PApplet.NORMAL);
		/*
		this.default_image = p.createImage(640, 640, p.ARGB);
		for (int i = 0; i < p.pixels.length; i++) { p.pixels[i] = 150;} 
		*/
	}

	void setFeed(Feed feed) {
		this.feed = feed;
		globe.setTexture(feed.getNextImage(parent, 3).orElse(default_image));
	}
	
	void draw() {
		parent.image(feed.getNextImage(parent, 3).orElse(default_image), 0, 0);
		parent.pushMatrix();
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(a1);
		parent.rotateX(a2);
		globe.setStroke(false);
		parent.shape(globe);
		parent.popMatrix();
		a1 += .02;
		a2 += .025;
		globe.setTexture(feed.getNextImage(parent, 3).orElse(default_image));
		globe.draw(parent.g);
	}
}
