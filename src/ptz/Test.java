package ptz;

import processing.core.*;

public class Test {
	PApplet parent;
	PShape globe;
	
	 
	Test(PApplet parent) { 
	  this.parent = parent;
	  String http = "http://";
	  PImage earth = parent.loadImage( http + "previewcf.turbosquid.com/Preview/2014/08/01__15_41_30/Earth.JPG5a55ca7f-1d7c-41d7-b161-80501e00d095Larger.jpg");
	  globe = parent.createShape(PApplet.SPHERE, 200); 
	  globe.setStroke(false);
	  globe.setTexture(earth);
	}
	 
	void draw() {
	  parent.noStroke();

	  parent.translate(parent.width/2, parent.height/2);
	  parent.rotateY(PApplet.map(parent.mouseX,0, parent.width,-PApplet.PI,PApplet.PI));
	  parent.rotateX(PApplet.map(parent.mouseY,0,parent.width,-PApplet.PI,PApplet.PI));
	  parent.shape(globe);
	}
}
