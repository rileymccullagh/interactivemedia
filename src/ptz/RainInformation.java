// This class uses colorAverage to use complementary colors generated from the average color of the background image. 
// In future versions it will have the displayed text ("metaStr") to show meta data gathered from the webcam feed, such as weather, location name, or 
// high frequency words found in the locations matching wikipedia page. 
package ptz;

import processing.core.*;

class RainInformation {
	PApplet parent;

	float x;
	float y;
	float z;
	float yspeed;
	int tCount; // Determines a metaStr to be displayed from 6 options.
	int boxWidth;
	int textS;
	String metaStr;
	// Color rainColor;
	PFont mono; // Using a google Inconsolata-Bold font.

	RainInformation(PApplet p) {
		this.parent = p;
		mono = parent.createFont("Inconsolata-Bold.ttf", 100);
		x = parent.random(parent.width); // gives each drop a random x value
		y = parent.random(-1700, -800); // gives each drop a random y value
		z = parent.random(0, 20); // gives each drop a random z value
		yspeed = PApplet.map(z, 0, 20, 0, 1);
		boxWidth = (int) PApplet.map(z, 0, 20, 4, 29); // Scales the width of the text field, to force each character of
														// a string to appear on a single line
		// rainColor = parent.colorsComp[(int)parent.random(8)]; // gives each instance
		// a generated color from an array of 8 complementary colors.
		textS = (int) PApplet.map(z, 0, 20, 5, 33); // scales text size with Z value
		tCount = (int) parent.random(1, 10); // Determines a metaStr to be displayed from 6 options.
	}

	void fall() {
		y = y + 1; // Makes them fall
		// just a little bit of gravity
		yspeed = yspeed + 1; // add gravity to speed

		if (y > parent.height) { // reset once below the screen
			y = parent.random(-1000, -600);
			yspeed = PApplet.map(z, 0, 20, 0, 1);
		}
	}

	void show() {
		if (tCount < 2) { // Determines a metaStr to be displayed from 6 options.
			metaStr = "locationdata";
		} else if (tCount < 4) {
			metaStr = "wordfrequency";
		} else if (tCount < 6) {
			metaStr = "text";
		} else if (tCount < 8) {
			metaStr = "loading";
		} else if (tCount < 10) {
			metaStr = "live love laugh";
		} else {
			metaStr = "ERRORLOL";
		}
		// textAlign(LEFT);
		parent.textFont(mono); // Needed to have a monospaced font for the boxWidth to work.
		parent.textSize(textS);
		parent.text(metaStr, x, y, boxWidth, 1000);
		parent.fill(0);
	}
}
