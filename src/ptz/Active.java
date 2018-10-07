package ptz;

import processing.core.PApplet;

import ptz_music.*;
import ptz_histogram.*;

class Active {
	PApplet parent;
	FFT fft;
	Engine_Ball_Bar histogram;
	AcidGenerator acidGenerator;
	
	Active(PApplet p) {
		this.parent = p;
		this.fft = new FFT(this.parent);
		this.histogram = new Engine_Ball_Bar(p.width, p.height, fft.values.length, p);
		this.acidGenerator = new AcidGenerator(parent, fft);
	}

	void draw() {
		parent.clear();
		acidGenerator.update();
		fft.update();
		parent.image(histogram.draw(fft.values), 0, 0);	
	}
}
