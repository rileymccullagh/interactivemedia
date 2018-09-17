import processing.core.PApplet;

class Active {
  PApplet parent;
  FFT fft;
  
  Active(PApplet p) {
    this.parent = p;
    this.fft = new FFT(this.parent);
  }
  
  void draw() {
    parent.background(0, 204, 255);
    fft.update();
  }
}
