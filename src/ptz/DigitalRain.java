package ptz;

import java.util.List;

import processing.core.*;
import ptz_camera.Feed;

class DigitalRain {

	PApplet parent;
	List<Feed> feeds;
	PImage img;
	
	
	RainInformation[] rain = new RainInformation[100];

	DigitalRain(PApplet parent, List<Feed> feeds, PImage default_image) {
		this.parent = parent;
		this.feeds = feeds;
		

		for (int i = 0; i < rain.length; i++) {
			rain[i] = new RainInformation(parent,feeds,img);
		}
	}

	void draw() {
		parent.pushMatrix();
		parent.translate(0, 0, -300);
		for (int i = 0; i < rain.length; i++) {
			rain[i].fall();
			rain[i].show();
		}
		parent.translate(0, 0, 300);
		parent.popMatrix();
	}
}