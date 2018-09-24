package ptz;

import processing.core.PApplet;
import processing.core.PGraphics;

import drummachine.DrumMachine;
import barballview.Engine_Ball_Bar;

class Active {
	PApplet parent;
	FFT fft;
	Engine_Ball_Bar engine;

	DrumMachine dm;
	int lastTrigger = 0;

	Active(PApplet p) {
		this.parent = p;
		this.fft = new FFT(this.parent);
		this.engine = new Engine_Ball_Bar(p.width, p.height, fft.values.length, p);
		this.dm = new DrumMachine(this.parent);
	}

	PGraphics draw() {
		parent.background(0, 0, 0);
		fft.update();

		if(parent.millis() > lastTrigger +5000) {
			dm.trigger(1);
			lastTrigger = parent.millis();
		}

		return engine.draw(fft.values);
	}
}
