package ptz;

import java.util.ArrayList;
import java.util.List;

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
	List<String> current_urls = new ArrayList<String>();
	Active(PApplet p) {
		this.parent = p;
		this.fft = new FFT(this.parent);
		this.engine = new Engine_Ball_Bar(p.width / 3, p.height / 3, fft.values.length, p);
		//this.dm = new DrumMachine(this.parent);
		this.cam = new Camera(this.parent);
		
		//cam.get_amount_of_images(30);
		current_urls.add(cam.cameras.get(0));
		current_urls.add(cam.cameras.get(1));
		
		//current_urls.add(cam.cameras.get(2));
		//current_urls.add(cam.cameras.get(5));
		/*current_urls.add(cam.cameras.get(5));
		current_urls.add(cam.cameras.get(7)); */
		cam.download_multiple_images_in_sequence(current_urls, 6, 12, 2); 
	}

	void draw() {
		//cam.cancel_threads(); //Later we can implement this, so we can prevent it retrieving during render
		parent.clear();
		fft.update();

		if(parent.millis() > lastTrigger +5000) {
			//dm.trigger(1);
			lastTrigger = parent.millis();
		}
		
		int img_width = parent.width / current_urls.size();
		int img_height = parent.height;
		int i = 0;
		for (String entry : current_urls) {
			parent.image(cam.getNextImage(entry),i * img_width, 0, img_width, img_height);
			++i;
		}
		
		
		//parent.image(engine.draw(fft.values), 0, 0);
		
	}
	
}
