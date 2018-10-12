package ptz;

import processing.core.*;

public class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project.
	Background bg;
	DigitalRain dr;
	TextureCube tc;

	Idle(PApplet p) {
		this.parent = p;
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		tc = new TextureCube(this.parent);
	}

	void draw() {
		bg.draw();
		dr.draw();
		tc.draw();
	}
}
