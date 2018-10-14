package ptz_camera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
// cameras from here work
// https://www.insecam.org/en/bytype/Vivotek/

import processing.core.PApplet;
import processing.core.PImage;

public class Camera {
	
	
	
	PApplet parent;
	
	PImage empty_image;
	

	public Camera(PApplet p) {
		this.parent = p;
		empty_image = p.createImage(0, 0, 0);
	}


}

