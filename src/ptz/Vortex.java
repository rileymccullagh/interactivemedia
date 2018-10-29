package ptz;

import processing.core.PImage;
import ptz_camera.Feed;
import processing.core.PApplet;

import java.util.List;

import ddf.minim.*;

public class Vortex {
	PApplet parent;
	Minim minim;
	AudioOutput output;
	PImage default_image;
	colorAverage ca;
	Feed feed;
	List<Feed> feeds;
	int vColor;
	int n=0;
	
	public Vortex(PApplet parent, List<Feed> feeds, PImage default_image) {
		this.parent = parent;
		this.feeds = feeds;
		ca = new colorAverage(parent, feed, default_image);
		ca.loadAnal(ca.getAverageColor(feeds.get(0).getNextImage(parent).orElse(default_image)));
		vColor = ca.colorsAnal[(int) parent.random(8)];
		minim = new Minim(this.parent);
		
		this.output = minim.getLineOut();
	}
	
	public Vortex(PApplet parent, AudioOutput output, List<Feed> feeds, PImage img) {
		this.parent = parent;
		minim = new Minim(this.parent);
		this.feeds = feeds;
		ca = new colorAverage(parent, feed, default_image);
		ca.loadAnal(ca.getAverageColor(feeds.get(0).getNextImage(parent).orElse(default_image)));
		vColor = ca.colorsAnal[(int) parent.random(8)];
		this.output = output;
	}
	
	public void draw() {
		parent.pushMatrix();
		parent.translate(parent.width/2,parent.height/2);
		for(int i = 0; i <= output.bufferSize() - 1; i++)  {
			parent.rotateX((float) (n*-PApplet.PI/6*0.05));
			parent.rotateY((float) (n*-PApplet.PI/6*0.05));
			parent.rotateZ((float) (n*-PApplet.PI/6*0.05));
			parent.fill(ca.colorsAnal[(int) parent.random(8)]);
			parent.rect(i,i,output.left.get(i)*50,output.left.get(i)*5000);
		}
		parent.popMatrix();
		n++;
	}

}