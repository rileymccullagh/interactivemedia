package ptz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.*;
import ptz_camera.Camera;

public class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project.
	Background bg;
	DigitalRain dr;
	TextureCube tc;
	Camera cam;
	List<String> camera_urls = new ArrayList<String>();
	Idle(PApplet p) {
		this.parent = p;
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		tc = new TextureCube(this.parent);
		cam = new Camera(this.parent);
		
		for (int i = 0; i < cam.cameras.size(); i++) { 
			camera_urls.add(cam.cameras.get(i)); 
		};
		Collections.shuffle(camera_urls);
		cam.download_multiple_images_in_sequence(camera_urls.subList(0, 6),1,1,3);
	}

	void draw() {
		bg.draw();
		dr.draw();
		tc.draw(cam.get_multiple_images(camera_urls).subList(0, 6));
	}
}
