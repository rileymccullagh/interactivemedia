package acidhouse;

import processing.sound.*;
import com.corajr.loom.*;

class DrumMachineVoice {
  SoundFile file;
  PApplet parent;
  
  public DrumMachineVoice(PApplet p, String filename) {
    this.parent = p;
    file = new SoundFile(this.parent, filename);
  }
  
  void trigger() {
    file.play();
  }
  
}

class DrumMachine {
  PApplet parent;
  Loom loom;
  DrumMachineVoice[] voices = new DrumMachineVoice[11];
  Pattern[] pattern = new Pattern[11];
  
  public DrumMachine(PApplet p, String[] patterns) {
    String[] files = {"bd.wav", "sd.wav", "rs.wav", "cp.wav", "ht.wav", "mt.wav", "lt.wav", "ch.wav", "oh.wav", "rd.wav", "cr.wav"};
    loom = new Loom(this.parent);

    this.parent = p;
    for(int i = 0; i<11; i++) {
      voices[i] = new DrumMachineVoice(this.parent, files[i]);
      pattern[i] = new Pattern(this.loom);
      if(i < patterns.length) {
        pattern[i].extend(patterns[i]);
      } else {
        pattern[i].extend("0");
      }
      pattern[i].asSoundFile(voices[i].file);
      pattern[i].loop();
      
    }
    loom.play();
  }
  
  void trigger(int voice) {
    if(voice>=0 && voice<voices.length) {
      voices[voice].trigger();
    }
  }
}
