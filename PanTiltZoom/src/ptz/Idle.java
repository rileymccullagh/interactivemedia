package ptz;

import processing.core.PApplet;

class Idle {
  PApplet parent;
  
  Idle(PApplet p) {
    this.parent = p;
  }
  
  void draw() {
    parent.background(255, 204, 0);
  }
}
