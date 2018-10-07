package ptz;

import processing.core.*;

class FFT {
  PApplet parent;
  public float[] values;
  public float value;
  
  FFT(PApplet p) {
    this.parent = p;
    values = new float[PApplet.floor(parent.random(5,10))];
  }
  
  void update() {
	for(int i = 0; i<this.values.length; i++) {
		values[i] = parent.random(1);
	}
  }
  
  public float[] getValue() {
	  return values;
  }
  
  public void setValue(float value) {
	  this.value = value;
  }
}