package ptz;

import processing.core.PApplet;
import drummachine.DrumMachine;

class Active {
  PApplet parent;
  FFT fft;
  DrumMachine dm;
  int lastTrigger = 0;
  
  Active(PApplet p) {
    this.parent = p;
    this.fft = new FFT(this.parent);
    this.dm = new DrumMachine(this.parent);
  }
  
  void draw() {
    parent.background(0, 204, 255);
    fft.update();
    if(parent.millis() > lastTrigger +5000) {
    	dm.trigger(1);
    	lastTrigger = parent.millis();
    }
  }
}
