package ptz;

import processing.core.PApplet;

import ptz_music.*;
import ptz_histogram.*;

class Active {
	PApplet parent;
	FFT fft;
	Engine_Ball_Bar engine;
	AcidGenerator acidGenerator;
	int lastTrigger = 0;
	Active(PApplet p) {
		this.parent = p;
		this.fft = new FFT(this.parent);
		this.engine = new Engine_Ball_Bar(p.width, p.height, fft.values.length, p);
		this.acidGenerator = new AcidGenerator();
	}

	void draw() {
		parent.clear();
		acidGenerator.update();
		fft.update();

		
		
		parent.image(engine.draw(fft.values), 0, 0);
		
	}
	
}
