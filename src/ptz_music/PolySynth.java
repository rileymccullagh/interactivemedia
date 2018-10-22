package ptz_music;

import processing.core.PApplet;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Instrument;

public class PolySynth implements Instrument {
	
	PApplet parent;
	Minim minim;
	AudioOutput output;
	
	int pattern[];
	int currentStep = 0;
	
	public PolySynth(PApplet parent, int pattern[]) {
		this.parent = parent;
		this.pattern = pattern;
	}

	@Override
	public void noteOn(float arg0) {
		for (int i = 0; i < pattern.length; i++) {
			//trigger ya boi here
		}
	}
	
	@Override
	public void noteOff() {
		currentStep++;
		if (currentStep >= pattern.length) {
			currentStep = 0;
		}
		output.playNote(0, 0.25f, this);
	}
}
