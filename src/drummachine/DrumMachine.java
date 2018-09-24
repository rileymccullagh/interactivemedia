package drummachine;

import processing.core.PApplet;


public class DrumMachine {
  PApplet parent;
  Voice[] voices = new Voice[11];
  
  public DrumMachine(PApplet p) {
    String[] files = {"bd.wav", "sd.wav", "rs.wav", "cp.wav", "ht.wav", "mt.wav", "lt.wav", "ch.wav", "oh.wav", "rd.wav", "cr.wav"};

    this.parent = p;
    for(int i = 0; i<11; i++) {
      voices[i] = new Voice(this.parent, files[i]);
    }
  }
  
  public void trigger(int voice) {
    if(voice>=0 && voice<voices.length) {
      voices[voice].trigger();
    }
  }
}

