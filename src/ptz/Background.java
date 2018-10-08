package ptz;

import processing.core.*;

class Background {
	PApplet parent;
	
	PImage bgi;
	String bgiImg = "space.png";

	// PImage bgi;
	Background(PApplet p) {
		this.parent = p;
		bgi = parent.loadImage(bgiImg);
		bgi.resize(parent.width,parent.height);
		
	}

	void draw() {
		parent.translate(0, 0, -1000);
		parent.image(bgi,0,0);
	}
}