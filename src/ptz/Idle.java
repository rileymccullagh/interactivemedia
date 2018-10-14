package ptz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.*;
import ptz_camera.Camera;
import ptz_camera.Feed;

public class Idle {
	PApplet parent;

	// Let's start with all the classes that we will need in the main project.
	Background bg;
	DigitalRain dr;
	TextureCube tc;
    List<Feed> feeds;
	
	Idle(PApplet p, List<Feed> feeds) {
		this.parent = p;
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		tc = new TextureCube(this.parent);
		this.feeds = feeds;
	}

	void draw() {
		bg.draw();
		dr.draw();
		tc.draw(feeds);
	}
}
