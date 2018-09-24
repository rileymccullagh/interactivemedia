package ptz;

import processing.core.*;

class Background {
  PApplet parent;
  PImage bgi;
  Background(PApplet p) {
    this.parent = p;
  }
  
  void draw() {

    bgi = parent.loadImage("space.png");
    bgi.resize(1000,600);
    parent.background(bgi);
  }
}
