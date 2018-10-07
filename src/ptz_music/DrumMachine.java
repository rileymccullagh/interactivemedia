package ptz_music;

import processing.core.PApplet;


public class DrumMachine {
  PApplet parent;
  Voice[] voices = new Voice[11];
  boolean[][] patterns = new boolean[11][16];
  int currentStep = 0;
  
  public DrumMachine(PApplet parent) {
    String[] files = {"bd.wav", "sd.wav", "rs.wav", "cp.wav", "ht.wav", "mt.wav", "lt.wav", "ch.wav", "oh.wav", "rd.wav", "cr.wav"};

    this.parent = parent;
    for(int i = 0; i<11; i++) {
      voices[i] = new Voice(this.parent, files[i]);
    }
    
    //four to the floor for the hell of it.
    patterns[0][0] = true;
    patterns[0][4] = true;
    patterns[0][8] = true;
    patterns[0][12] = true;
  }
  
  public void trigger() {
	currentStep++;
	if(currentStep >= 16) {
		currentStep = 0;
	}
	for(int i = 0; i < patterns.length; i++) {
		if(patterns[i][currentStep]) {
			voices[i].trigger();
		}
	}
  }
}

