package ptz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import processing.core.*;
import ptz_camera.Camera;
import ptz_camera.Feed;
import ptz_histogram.Engine_Ball_Bar;

public class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project
	DigitalRain dr;

	List<Feed> feeds;
	Feed feed;
	PImage img;
	Prism skybox;
	Vortex vortex;


	Drawable center_shape;

	Idle(PApplet p, List<Feed> feeds, PImage default_img) {
		this.parent = p;
		img = default_img;
		dr = new DigitalRain(parent, feeds, img);
		vortex = new Vortex(parent);

		this.feeds = feeds;

		Optional<List<Feed>> working_feeds = Feed.get_shuffled_list(6);
		if (working_feeds.isPresent() == false) {
			center_shape = new TextureSphere(parent, img, feeds.get(0), 100);
		} else {
			center_shape = new TextureCube(this.parent, img, feeds);

		}

		skybox = new Prism(0, 0, 0, 800);

	}

	void draw_outer_prism(List<PImage> images) {
		skybox.rotate();
		parent.pushMatrix();
		skybox.draw(images, images.get(0), images.get(0), parent);
		parent.popMatrix();
	}

	void draw() {
		parent.clear();
		parent.background(0);
		parent.fill(0);

		parent.noStroke();

		List<PImage> images = new ArrayList<PImage>();
		for (int i = 0; i < 8; i++) {
			images.add(img);
		}

		parent.pushMatrix();
		parent.translate((float) (parent.width / 2.0f), (float) (parent.height / 2.0f),
				(float) ((parent.height / 2.0) / Math.tan(PApplet.PI * 30.0 / 180.0)));

		int val = skybox.camera_max();
		parent.translate(0, 0, -val);
		parent.rotateX(PApplet.PI / 2.0f);

		draw_outer_prism(images);
		
		vortex.draw();

		center_shape.draw();
		parent.translate(0, 0, -val);

		parent.popMatrix();
		dr.draw();
		// bg.draw();
		// dr.draw();
	}
}
