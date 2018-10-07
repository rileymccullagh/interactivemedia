package ptz_music;

import processing.core.PImage;
import processing.core.PApplet;


import ddf.minim.*;

public class Vortex {
	PApplet parent;
	Minim minim;
	AudioPlayer sound;
	PImage img;
	int n=0;
	
	public Vortex(PApplet parent, AudioPlayer sound) {
		this.parent = parent;
		minim = new Minim(this.parent);
		this.sound = sound;
	}
	
	public void draw() {
		parent.translate(parent.width/2,parent.height/2);
		for(int i = 0; i <= sound.bufferSize() - 1; i++)  {
			parent.rotateX((float) (n*-PApplet.PI/6*0.05));
			parent.rotateY((float) (n*-PApplet.PI/6*0.05));
			parent.rotateZ((float) (n*-PApplet.PI/6*0.05));
			parent.fill(parent.random(255),parent.random(255),parent.random(255));
			parent.rect(i,i,sound.left.get(i)*50,sound.left.get(i)*5000);
		}
		n++;
	}

}