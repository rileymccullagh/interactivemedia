package ptz;

import barballview.Engine_Ball_Bar;
//import drummachine.DrumMachine;
import processing.core.PApplet;
import webcam.Camera;

class Active {
	PApplet parent;
	FFT fft;
	Engine_Ball_Bar engine;
	Camera cam;
	boolean retrieving = false;
	//DrumMachine dm;
	int lastTrigger = 0;
	String current_url;
	Active(PApplet p) {
		this.parent = p;
		this.fft = new FFT(this.parent);
		this.engine = new Engine_Ball_Bar(p.width / 3, p.height / 3, fft.values.length, p);
		//this.dm = new DrumMachine(this.parent);
		this.cam = new Camera(this.parent);
		
		//cam.get_amount_of_images(30);
	}

	void draw() {
		if (retrieving == false) {
			current_url = cam.cameras.get(0);
			cam.download_multiple_images(current_url, 30); 
			retrieving = true;
		}
		parent.clear();
 		//parent.background(51, 51, 126);
		fft.update();

		if(parent.millis() > lastTrigger +5000) {
			//dm.trigger(1);
			lastTrigger = parent.millis();
		}

		parent.image(cam.getNextImage(current_url),0,0);
		//parent.image(engine.draw(fft.values), 0, 0);
		
	}
	
	//We need to cancel the threads in cam before we destroy this object
	void destructor() {
		cam.cancel_threads();
	}
}
