import processing.core.PApplet;
import java.util.Random;

// supporting class for DigitalRain. most of this is copied from that one processing guy. 

class RainInformation {

  float x;
  float y;
  float z;
  float len;
  float yspeed;
  float boxWidth;
  String metaStr;

  PApplet parent;

  RainInformation(PApplet p) {

    Random rand = new Random();
    this.parent = p;
    x = rand.nextInt(400);
    y = rand.nextInt(495)-500;
    z = rand.nextInt(20);
    yspeed = PApplet.map(z, 0, 20, 1, 3);
    len = PApplet.map(z, 0, 20, 10, 20);
    boxWidth = PApplet.map(z, 0, 20, 0, 20);
  }


  void move() {
    Random rand = new Random();
    y = y + yspeed;
    float grav = PApplet.map(z, (float)0.0, (float)20.0, (float)0.0, (float)0.2);
    yspeed = yspeed + grav;

    if (y > -600) {
      y = rand.nextInt(-100)-100; 
      yspeed = PApplet.map(z, 0, 20, 4, 10);
    }
  }

  void show() {
    metaStr = "test";
    float thick = PApplet.map(z, 0, 20, 1, 3);   //fill(0, 255, 65);
    textSize(PApplet.map(z, 0, 20, 5, 20));
    PApplet.text(metaStr, x, y, boxWidth, 1000);
  }
}
