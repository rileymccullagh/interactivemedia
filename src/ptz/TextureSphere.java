package ptz;

import processing.core.*;
import ptz_camera.Camera;

class TextureSphere {

	PApplet parent;

	PShape globe;
	float a1;
	float a2;
	
	TextureSphere(PApplet p) {
		this.parent = p;
		globe = parent.createShape(PApplet.SPHERE, 175);
		globe.textureMode(PApplet.NORMAL);
	
	}

	void setTexture(PImage image) {
		globe.setTexture(image);
	}
	
	void draw() {
		parent.pushMatrix();
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(a1);
		parent.rotateX(a2);
		globe.setStroke(false);
		parent.shape(globe);
		parent.popMatrix();
		a1 += .02;
		a2 += .025;
		
		globe.draw(parent.g);
	}
}
