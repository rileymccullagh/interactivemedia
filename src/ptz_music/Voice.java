package ptz_music;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class Voice {
	SoundFile file;
	PApplet parent;

	public Voice(PApplet p, String filename) {
		this.parent = p;
		file = new SoundFile(this.parent, filename);
	}

	void trigger() {
		file.play();
	}

}
