package ptz;

import processing.core.PApplet;
import processing.core.PGraphics;
import webcam.Camera;
import drummachine.DrumMachine;
import barballview.Engine_Ball_Bar;

class Active {
	PApplet parent;
	FFT fft;
	Engine_Ball_Bar engine;
	Camera cam;

	DrumMachine dm;
	int lastTrigger = 0;

	Active(PApplet p) {
		this.parent = p;
		this.fft = new FFT(this.parent);
		this.engine = new Engine_Ball_Bar(p.width, p.height, fft.values.length, p);
		this.dm = new DrumMachine(this.parent);
		this.cam = new Camera(this.parent);
	}

	void draw() {
//		parent.background(51, 51, 126);
		fft.update();

		if(parent.millis() > lastTrigger +5000) {
			dm.trigger(1);
			lastTrigger = parent.millis();
		}

		parent.image(cam.getNext(),0,0);
		parent.print("asdf");
		
	}
}
