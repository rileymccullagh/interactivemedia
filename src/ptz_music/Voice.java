package ptz_music;

import ddf.minim.*;
import ddf.minim.ugens.*;

class Voice {
	Sampler sample;
	String filename;

	public Voice(String filename, AudioOutput output, Minim minim) {
		this.filename = filename;
		sample = new Sampler(filename, 12, minim);
		sample.patch(output);
	}

	void trigger() {
		sample.trigger();
	}
}