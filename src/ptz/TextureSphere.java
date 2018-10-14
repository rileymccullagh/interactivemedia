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
	TextureSphere(PApplet p) {
		this.parent = p;
		globe = parent.createShape(PApplet.SPHERE, 175);
		globe.textureMode(PApplet.NORMAL);
	}

	void setFeed(Feed feed) {
		this.feed = feed;
		globe.setTexture(feed.getNextImage());
	}
	
	void draw() {
		parent.image(feed.getNextImage(), 0, 0);
		parent.pushMatrix();
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(a1);
		parent.rotateX(a2);
		globe.setStroke(false);
		parent.shape(globe);
		parent.popMatrix();
		a1 += .02;
		a2 += .025;
		globe.setTexture(feed.getNextImage());
		globe.draw(parent.g);
	}
}
