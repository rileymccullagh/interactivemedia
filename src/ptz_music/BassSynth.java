package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;
import ddf.minim.ugens.*;
import ddf.minim.analysis.*;

public class BassSynth  {
	PApplet parent;
		
	int currentStep = 0;
	Minim minim;
	AudioOutput output;
	
	int[] pattern = new int[16];
	
	FFT fft;
	public int bands = 8;
	public float[] spectrum = new float[bands];

	
	public BassSynth(PApplet parent) {
		this.parent = parent;
		
		minim = new Minim(parent);
		output = minim.getLineOut();
				
		fft = new FFT(output.bufferSize(), output.sampleRate());
		
	}

	public void noteOn(float arg0) {
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i]>0) {
				//trigger ya synth here
			}
		}
	}
}