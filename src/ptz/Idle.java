package ptz;

import java.awt.Color;

import com.cage.colorharmony.ColorHarmony;

import processing.core.*;

public class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project.
	DigitalRain dr;
	TextureCube tc;
	TextureSphere ts;
	Background bg;
	colorAverage ca;
	

	ColorHarmony colorHarmony = new ColorHarmony(parent);

	Color rainColor;
	Color[] colorsComp = new Color[8];
	Color[] colorsAnal = new Color[8];

	// The variables.
	int i;
	int j;
	int detail; // The detail of the image to be used for its average color.

	PImage bgi; // Background image.
	String bgiurl = "https://picsum.photos/1000/600/?random"; // Pulls a random image 1000x600 to use as background
	String hexValue; // The value that the colour_average is passed into once converted from RGB to
	// hex.
	// PImage newImg; // a PImage that is used in the colorAverage and
	// extractColorFromImage classes.

	Idle(PApplet p) {
		this.parent = p;
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		ca = new colorAverage(this.parent);
		tc = new TextureCube(this.parent);
	}

	void draw() {
		bg.draw();
		dr.draw();
		tc.draw();
	}
}
