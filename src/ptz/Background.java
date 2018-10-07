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
    bgi.resize(parent.width,parent.height);
    parent.image(bgi,0,0);
  }
}