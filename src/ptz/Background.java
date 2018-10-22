package ptz;

import processing.core.*;

class Background {
	PApplet parent;

	PImage bgi;

	Background(PApplet p) {
		this.parent = p;
		bgi = parent.loadImage("Unknown.png");
		bgi.resize(parent.width, parent.height);

	}

	void draw() {
		parent.pushMatrix();
		parent.translate(0, 0, -500);
		parent.background(bgi);
		parent.popMatrix();

	}

	PImage get() {
		return bgi;
	}
}