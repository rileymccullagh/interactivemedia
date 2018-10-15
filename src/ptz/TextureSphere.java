package ptz;

import processing.core.*;

class TextureSphere {

	PApplet parent;

	PShape globe;
	PImage tex1;
	String url1 = "https://picsum.photos/400/400/?random";

	TextureSphere(PApplet p) {
		this.parent = p;
		tex1 = parent.loadImage(url1, "jpeg");
		globe = parent.createShape(PApplet.SPHERE, 175);
		globe.setTexture(tex1);

	}

	void draw() {
		parent.pushMatrix();
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(parent.frameCount * PApplet.PI / 360);
		parent.rotateX(parent.frameCount * PApplet.PI / 720);
		globe.setStroke(false);
		parent.shape(globe);
		parent.popMatrix();
	}
}
