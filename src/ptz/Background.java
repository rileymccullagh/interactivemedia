package ptz;

import processing.core.*;

class Background {
	PApplet parent;

	// PImage bgi;
	Background(PApplet p) {
		this.parent = p;
	}

	void draw() {

		// bgi = parent.loadImage("space.png");
		// bgi.resize(parent.height,parent.width);
		// parent.image(bgi,0,0);
		parent.background(255);
	}
}