package ptz;

import processing.core.PImage;
import processing.core.PApplet;


import ddf.minim.*;

public class Vortex {
	PApplet parent;
	Minim minim;
	AudioOutput output;
	PImage img;
	int n=0;
	
	public Vortex(PApplet parent) {
		this.parent = parent;
		minim = new Minim(this.parent);
		this.output = minim.getLineOut();
	}
	
	public Vortex(PApplet parent, AudioOutput output) {
		this.parent = parent;
		minim = new Minim(this.parent);
		this.output = output;
	}
	
	public void draw() {
		System.out.println("vortex drawing");
		parent.pushMatrix();
		parent.translate(parent.width/2,parent.height/2);
		for(int i = 0; i <= output.bufferSize() - 1; i++)  {
			parent.rotateX((float) (n*-PApplet.PI/6*0.05));
			parent.rotateY((float) (n*-PApplet.PI/6*0.05));
			parent.rotateZ((float) (n*-PApplet.PI/6*0.05));
			parent.fill(parent.random(255),parent.random(255),parent.random(255));
			parent.rect(i,i,output.left.get(i)*50,output.left.get(i)*5000);
		}
		parent.popMatrix();
		n++;
	}

}