package ptz;

import processing.core.*;
import ptz_camera.Camera;
import ptz_camera.Feed;

class TextureSphere implements Drawable {

	PApplet parent;

	PShape globe;
<<<<<<< HEAD
	float a1;
	float a2;
	Feed feed; 
	PImage default_image;
	TextureSphere(PApplet p, PImage default_image, Feed feed, int size) {
=======
	PImage tex1;
	String url1 = "https://picsum.photos/400/400/?random";

	TextureSphere(PApplet p) {
>>>>>>> 468864cd602e35568364b70d055ca7d1c4fe5713
		this.parent = p;
		this.default_image = default_image;
		globe = parent.createShape(PApplet.SPHERE, size);
		this.feed = feed;
		
	}

	
	public void draw() {
		parent.pushMatrix();
<<<<<<< HEAD
		parent.rotateY(a1);
		parent.rotateX(a2);
		
=======
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(parent.frameCount * PApplet.PI / 360);
		parent.rotateX(parent.frameCount * PApplet.PI / 720);
>>>>>>> 468864cd602e35568364b70d055ca7d1c4fe5713
		globe.setStroke(false);
		parent.shape(globe);
		globe.setTexture(feed.getNextImage(parent).orElse(default_image));
		parent.popMatrix();
<<<<<<< HEAD
		a1 += .02;
		a2 += .025;
		//globe.draw(parent.g);
=======
>>>>>>> 468864cd602e35568364b70d055ca7d1c4fe5713
	}
}
