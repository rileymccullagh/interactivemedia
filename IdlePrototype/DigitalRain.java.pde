import processing.core.PApplet;

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
    x = random(width);
    y = random(-500, -5);
    z = random(0, 20);
    yspeed = map(z, 0, 20, 1, 3);
    len = map(z, 0, 20, 10, 20);
    boxWidth = map(z, 0, 20, 0, 20);
  }
  
  void randomise() {
    for (int i = 0; i < DigitalRain.length; i++) {
      DigitalRain[i] = new DigitalRain(parent);
    
    }
  }
  void draw() {  

    for (int i=0; i<DigitalRain.length; i++) {
      y = y + yspeed;
      float grav = map(z, 0, 20, 0, 0.2);
      yspeed = yspeed + grav;

      if (y > height) {
        y = random(-200, -100);
        yspeed = map(z, 0, 20, 4, 10);
      }

      metaStr = "test";
      float thick = map(z, 0, 20, 1, 3);
      strokeWeight(thick);
      fill(0, 255, 65);
      textSize(map(z, 0, 20, 5, 20));
      text(metaStr, x, y, boxWidth, 1000);
    }
  }
}
