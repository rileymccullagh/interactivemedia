// This class uses colorAverage to use complementary colors generated from the average color of the background image. 
// In future versions it will have the displayed text ("metaStr") to show meta data gathered from the webcam feed, such as weather, location name, or 
// high frequency words found in the locations matching wikipedia page. 
package ptz;

import java.util.List;

import processing.core.*;
import ptz_camera.Feed;

class RainInformation {
	PApplet parent;
	colorAverage ca;
	PImage default_image;
	List<Feed> feeds;
	Feed feed;

	float x;
	float y;
	float z;
	float yspeed;
	int tCount; // Determines a metaStr to be displayed from 6 options.
	int boxWidth;
	int textS;
	String metaStr;
	PFont mono; // Using a google Inconsolata-Bold font.
	int rainColor;
	float xspeed;

	RainInformation(PApplet parent, List<Feed> feeds, PImage default_image) {
		this.parent = parent;
		this.feeds = feeds;
		ca = new colorAverage(parent, feeds, feed, default_image);
		ca.loadComp(ca.getAverageColor(feeds.get(0).getNextImage(parent).orElse(default_image)));
		mono = parent.createFont("Inconsolata-Bold.ttf", 100);
		x = parent.random(-parent.width, parent.width); // gives each drop a random x value
		y = parent.random(-parent.height * (int)4.5, -parent.height * (int) 2.5); // gives each drop a random y value
		z = parent.random(0, 20); // gives each drop a random z value
		yspeed = PApplet.map(z, 0, 20, 1, 10);
		boxWidth = (int) PApplet.map(z, 0, 20, 0, 23); // Scales the width of the text field, to wforce each character
		textS = (int) PApplet.map(z, 0, 20, 1, 25); // scales text size with Z value
		tCount = (int) parent.random(1, 10); // Determines a metaStr to be displayed from 6 options.
		rainColor = ca.colorsComp[(int) parent.random(8)];
		xspeed = PApplet.map(z, 0, 20, 0, 3);
		feeds.get(0).analyse(5);
		metaStr = feeds.get(0).words_analysed[(int) parent.random(0, 5)];
	}

	void fall() {
		y = y + yspeed; // Makes them fall
		x = x + xspeed;
		if (y > parent.height) { // reset once below the screen
			y = parent.random(-parent.height * (int)4.5, -parent.height * (int) 2.5);
			yspeed = PApplet.map(z, 0, 20, 1, 10);
			xspeed = PApplet.map(z, 0, 20, 0, 3);
			x = parent.random(-parent.width, parent.width);
		}
	}

	void show() {
		parent.pushMatrix();
		parent.textFont(mono); // Needed to have a monospaced font for the boxWidth to work.
		parent.textSize(textS);
		parent.text(metaStr, x, y, boxWidth, 1000);
		parent.fill(rainColor);
		parent.popMatrix();
	}
}
