package ptz;

import processing.core.*;

class Background {
	PApplet parent;

	PImage bgi;
	String bgiImg = "Unknown.png";

	Background(PApplet p) {
		this.parent = p;
		bgi = parent.loadImage("Unknown.png");
		bgi.resize(parent.width, parent.height);
	}

	void draw() {
		parent.image(bgi, 0, 0);
		parent.translate(0, 0, -500 );
	}

	PImage get() {
		return bgi;
	}
}