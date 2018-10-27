package ptz_music;

import processing.core.PApplet;

import processing.sound.*;

public class BassSynth  {
	PApplet parent;
		
	int[] sequence = new int[16];
	
	TriOsc triOsc;
	Env env;
	LowPass lowPass;
	HighPass highPass;
	
	float attackTime = 0.01f;
	float sustainTime = 0.5f;
	float sustainLevel = 0.2f;
	float releaseTime = 0.2f;
	
	float cutoff = 800f;
	
	// Set the duration between the notes
	int duration = 200;
	// Set the note trigger
	int trigger = 0; 
	
	// An index to count up the notes
	int note = 0; 

	FFT fft;
	public int bands = 8;
	public float[] spectrum = new float[bands];
	
	public BassSynth(PApplet parent, int[] sequence) {
		this.parent = parent;
		this.sequence = sequence;	
		triOsc = new TriOsc(parent);
		env = new Env(parent);
		highPass = new HighPass(parent);
		highPass.freq(80.0f);
		highPass.process(triOsc);
		lowPass = new LowPass(parent);
		lowPass.process(triOsc);
	}
	
	public void update() {
	  if ((parent.millis() > trigger) && (note<sequence.length)) {
		
		// DSP section
	    triOsc.play((float) midiToFreq(sequence[note]), 0.8f);
	    env.play(triOsc, attackTime, sustainTime, sustainLevel, releaseTime);
	    
	    // set next trigger time
	    trigger = parent.millis() + duration;
	    //loop
	    note = (note + 1) % 16; 
	  }
	  cutoff = (float)(trigger - parent.millis()) / duration;
	  cutoff *= 4000;
	  lowPass.freq(cutoff);
	}

	// This function calculates the respective frequency of a MIDI note
	float midiToFreq(int note) {
	  float f = PApplet.pow(2, (float) ((note-69)/12.0));
	  return f*440;
	}
}