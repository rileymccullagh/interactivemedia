package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;
import ddf.minim.ugens.*;

public class DrumMachine {
	PApplet parent;
	AudioOutput output;
	Sampler[] voices = new Sampler[11];
	boolean[][] patterns = new boolean[11][16];
	Minim minim;

	// Set the duration between the notes
	int duration = 200;
	// time last triggered
	int trigger = 0; 
	int step = 0;


	public DrumMachine(PApplet parent, boolean[] ...newPatterns) {
		this.parent = parent;

		minim = new Minim(parent);		
		this.output = minim.getLineOut();

		String[] files = { "bd.wav", "sd.wav", "rs.wav", "cp.wav", "ht.wav", "mt.wav", "lt.wav", "ch.wav", "oh.wav", "rd.wav", "cr.wav" };

		for (int i = 0; i < 11; i++) {
			voices[i] = new Sampler(files[i], 4, minim);
			voices[i].patch(output);
		}
		
		for (int i = 0; i < newPatterns.length; i++) {
			patterns[i] = newPatterns[i];
		}
		
	}

	public void update() {
		int timeStarted = parent.millis();
		if (timeStarted > trigger) {
			for (int i = 0; i < patterns.length; i++) {
				if (patterns[i][step]) {
					voices[i].trigger();
				}
			}
			step = (step + 1) % 16;
			trigger = timeStarted + duration;
		}

	}
}
