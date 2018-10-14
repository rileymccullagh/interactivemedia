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
	
	Idle(PApplet p, Camera cam) {
		this.parent = p;
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		tc = new TextureCube(this.parent);
		this.cam = cam;
	}

	void draw() {
		bg.draw();
		dr.draw();
		tc.draw(cam.get_sequence_of_images_by_index(6));
	}
}
