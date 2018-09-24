import processing.core.PApplet;

class Idle {
  PApplet parent;

  DigitalRain dr;
  TextureCube tc;
  Background bg;

  Idle(PApplet p) {
    this.parent = p;

    bg = new Background(this.parent);
    tc = new TextureCube(this.parent);
    dr = new DigitalRain(this.parent);
  }



  void draw() {
    bg.draw();
    dr.randomise();
    dr.draw();
  // tc.loadTextures();
    tc.draw();
  }
}
