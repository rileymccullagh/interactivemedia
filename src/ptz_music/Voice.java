package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;

class Voice {
	AudioPlayer player;
	PApplet parent;
	Minim minim;
	String filename;

	public Voice(PApplet parent, String filename) {
		this.parent = parent;
		minim = new Minim(this.parent);
		this.filename = filename;
		
	}

	void trigger() {
		player = minim.loadFile(filename);
		player.play();
	}
}
