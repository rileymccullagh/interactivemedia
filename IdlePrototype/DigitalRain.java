import processing.core.PApplet;

class DigitalRain {

  PApplet parent;

  DigitalRain(PApplet p) {
    this.parent = p;
  }
  RainInformation[] newRain = new RainInformation[400];



  void setup() {
    for (int i = 0; i < newRain.length; i++) {
      newRain[i] = new RainInformation(parent);
    }
  }

  void draw() {  

    for (int i = 0; i < newRain.length; i++) {
      newRain[i].move();
      newRain[i].show();
    }
  }
}
