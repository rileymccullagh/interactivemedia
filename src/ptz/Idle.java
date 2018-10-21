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
	PImage img; 
	Prism skybox;
	Idle(PApplet p, List<Feed> feeds) {
		this.parent = p;
		img = parent.loadImage("http://31.51.157.21/cgi-bin/viewer/video.jpg");
		bg = new Background(this.parent);
		dr = new DigitalRain(this.parent);
		tc = new TextureCube(this.parent, img);
		this.feeds = feeds;
		
		skybox = new Prism(0,0,0,1000);
		
	}
	void draw_hollow() {
		parent.pushMatrix();
		parent.textureMode(PApplet.NORMAL);
		parent.noStroke();
		parent.translate(parent.width / 2, parent.height / 2, 0);
		parent.rotateY(parent.frameCount * PApplet.PI * 4 / 360);
		parent.rotateX(parent.frameCount * PApplet.PI * 4 / 720);
		
		parent.scale(2000);
		
		//PApplet.map((float)parent.height+(float)parent.width, (float)0,((float)parent.height+(float)parent.width)*2,(float)40,(float)100));
		
		/*
		parent.beginShape(PApplet.QUADS);
		parent.fill(0, 0, 0);
		parent.texture(img);

		// +Z "front" face
		parent.vertex(-1, -1, 1, 0, 0);
		parent.vertex(1, -1, 1, 1, 0);
		parent.vertex(1, 1, 1, 1, 1);
		parent.vertex(-1, 1, 1, 0, 1);

		parent.endShape();
		*/
		
		parent.beginShape(PApplet.QUADS);

		parent.texture(img);
		// -Z "back" face
		parent.vertex(1, -1, -1, 0, 0);
		parent.vertex(-1, -1, -1, 1, 0);
		parent.vertex(-1, 1, -1, 1, 1);
		parent.vertex(1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);
		parent.texture(img);
		// +Y "bottom" face
		parent.vertex(-1, 1, 1, 0, 0);
		parent.vertex(1, 1, 1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(img);
		// -Y "top" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, -1, 1, 1, 1);
		parent.vertex(-1, -1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(img);
		// +X "right" face
		parent.vertex(1, -1, 1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(1, 1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(img);
		// -X "left" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(-1, -1, 1, 1, 0);
		parent.vertex(-1, 1, 1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);

		parent.endShape();
		parent.popMatrix();
	}
	void draw() {
		List<PImage> images = new ArrayList<PImage>();
		for (int i = 0; i < 4; i++) {
			images.add(img);
		}
		
		skybox.draw(images, img, img, parent);
		bg.draw();
		//dr.draw();
		//tc.draw(feeds);
		//draw_hollow();
	}
}
