import processing.core.PApplet;

class FFT {
  PApplet parent;
  public float[] values;
  
  
  FFT(PApplet p) {
    this.parent = p;
    values = new float[parent.floor(parent.random(5,10))];
  }
  
  void update() {
    for(float value: this.values) {
      value = parent.random(1);
    }
  }
}
