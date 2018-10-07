package ptz;

import processing.core.PApplet;

public class FFT {
  PApplet parent;
  public float[] values;
  
  
  FFT(PApplet p) {
    this.parent = p;
    values = new float[PApplet.floor(parent.random(5,10))];
  }
  
  void update() {
	for(int i = 0; i<this.values.length; i++) {
		values[i] = parent.random(1);
	}
  }
}