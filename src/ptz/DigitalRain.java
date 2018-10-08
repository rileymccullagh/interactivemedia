package ptz;

import processing.core.*;

class DigitalRain {

	PApplet parent;
	RainInformation[] rain = new RainInformation[100];

	DigitalRain(PApplet p) {
		this.parent = p;
		
		for (int i = 0; i < rain.length; i++) {
			rain[i] = new RainInformation(parent);
		}
	}



	void draw() {
		for (int i = 0; i < rain.length; i++) {
			rain[i].fall();
			rain[i].show();
		}
	}
}