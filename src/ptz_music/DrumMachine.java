package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;
import ddf.minim.ugens.*;


public class DrumMachine {
	PApplet parent;
	Sampler[] voices = new Sampler[11];
	boolean[][] patterns = new boolean[11][16];
	int currentStep = 0;
	Minim minim;
	AudioOutput output;
  
  public DrumMachine(PApplet parent) {
	minim = new Minim(parent);
	output = minim.getLineOut();
	  
    String[] files = {"bd.wav", "sd.wav", "rs.wav", "cp.wav", "ht.wav", "mt.wav", "lt.wav", "ch.wav", "oh.wav", "rd.wav", "cr.wav"};

    this.parent = parent;
    for(int i = 0; i<11; i++) {
    	voices[i] = new Sampler(files[i], 12, minim);
    	voices[i].patch(output);
    }
    
    //four to the floor for the hell of it.
    patterns[0][0] = true;
    patterns[0][4] = true;
    patterns[0][8] = true;
    patterns[0][12] = true;
    
    patterns[8][2] = true;
    patterns[8][6] = true;
    patterns[8][10] = true;
    patterns[8][14] = true;
  }
  
  public void trigger() {
	currentStep++;
	if(currentStep >= 16) {
		currentStep = 0;
	}
	for(int i = 0; i < patterns.length; i++) {
		if(patterns[i][currentStep]) {
			System.out.println("Triggering "+i);
			voices[i].trigger();
		}
	}
  }
}

