package acidhouse;

import processing.sound.*;
import com.corajr.loom.*;

class BassSynth {
  PApplet parent;
  
  TriOsc triOsc;
  Env env; 
  
  float attackTime = 0.001;
  float sustainTime = 0.004;
  float sustainLevel = 0.2;
  float releaseTime = 0.2;


  
  public BassSynth(PApplet p) {
    this.parent = p;
    
    triOsc = new TriOsc(this.parent);
    env = new Env(this.parent);
  }
  
  void trigger() {
    triOsc.play(midiToFreq(40), 1.0);
    this.env.play(this.triOsc, this.attackTime, this.sustainTime, this.sustainLevel, this.releaseTime);

  }
  float midiToFreq(int note) {
    return (pow(2, ((note-69)/12.0)))*440;
  }

}
