package ptz;

import processing.core.*;

class DigitalRain {
  PApplet parent;
  DigitalRain[] DigitalRain = new DigitalRain[400];


  float x;
  float y;
  float z;
  float len;
  float yspeed;
  String metaStr;
  float boxWidth;

  DigitalRain(PApplet p) {
    this.parent = p;
    x = parent.random(800);
    y = parent.random(-500, -5);
    z = parent.random(0, 20);
    yspeed = PApplet.map(z, 0, 20, 1, 3);
    len = PApplet.map(z, 0, 20, 10, 20);
    boxWidth = PApplet.map(z, 0, 20, 0, 20);
    
  }
  
  void randomise() {
    for (int i = 0; i < DigitalRain.length; i++) {
      DigitalRain[i] = new DigitalRain(parent);
    
    }
  }
  void draw() {  

    for (int i=0; i<DigitalRain.length; i++) {
      y = y + yspeed;
      float grav = PApplet.map(z, 0, 20, 0, (float) 0.2);
      yspeed = yspeed + grav;

      if (y > parent.height) {
        y = parent.random(-200, -100);
        yspeed = PApplet.map(z, 0, 20, 4, 10);
      }

      metaStr = "test";
      float thick = PApplet.map(z, 0, 20, 1, 3);
      parent.strokeWeight(thick);
      parent.fill(0, 255, 65);
      parent.textSize(PApplet.map(z, 0, 20, 5, 20));
      parent.text(metaStr, x, y, boxWidth, 1000);
    }
  }
}