package ptz;

import processing.core.*;

// import com.cage.colorharmony.*;

class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project.
	DigitalRain dr;
	TextureCube tc;
	TextureSphere ts;
	Background bg;
	// colorAverage ca;

	// ColorHarmony colorHarmony = new ColorHarmony(this);

	// The colors we will need globally.
	// Color rainColor;
	// Color[] colorsComp = new Color[8];

	// Color[] colorsAnal = new Color[8];

	// The variables.
	int i;
	int j;
	int detail; // The detail of the image to be used for its average color.

	PImage bgi; // Background image.
	String bgiurl = "https://picsum.photos/1000/600/?random"; // Pulls a random image 1000x600 to use as background
	String hexValue; // The value that the colour_average is passed into once converted from RGB to
	int objectID;
						// hex.
	// PImage newImg; // a PImage that is used in the colorAverage and
	// extractColorFromImage classes.

	Idle(PApplet p) {
		this.parent = p;
		objectID = 3;//(int)parent.random(1,10);  // (int)parent.random(1,10);
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		// ca = new colorAverage(this.parent);
		if (objectID < 5) { // 50/50 chance of sphere or cube.
			tc = new TextureCube(this.parent);
		} else {
			ts = new TextureSphere(this.parent);
		}

	}

	void draw() {
		bg.draw();
		dr.draw();
		if (objectID < 5) { // 50/50 chance of sphere or cube.
			tc.draw();
		} else {
			ts.draw();
		}
	}
}
