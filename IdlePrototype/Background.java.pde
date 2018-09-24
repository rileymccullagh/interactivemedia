import processing.core.PApplet;

class Background {
  PApplet parent;
  PImage bgi;
  Background(PApplet p) {
    this.parent = p;
  }

  void draw() {

    bgi = loadImage("space.png");
    bgi.resize(1000, 600);
    background(bgi);
  }
}
