package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;
import ddf.minim.ugens.*;
import ddf.minim.analysis.*;


public class BassSynth  {
	PApplet parent;
	
	Minim minim;
	AudioOutput output;	
	
	int[] sequence = new int[16];
	
	FFT fft;
	public int bands = 8;
	public float[] spectrum = new float[bands];

	
	public BassSynth(PApplet parent, int[] sequence) {
		this.parent = parent;
		
		minim = new Minim(parent);
		output = minim.getLineOut();
				
		fft = new FFT(output.bufferSize(), output.sampleRate());
		
		this.sequence = sequence;
	}

	public void noteOn(int step) {
		if (sequence[step]>0) {
		}
	}
}