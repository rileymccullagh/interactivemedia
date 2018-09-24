import processing.core.PApplet;

// this still has an issue with all instances of the text generating on the same  X value, IDK why but its a minor detail at this point. 

class DigitalRain {

  PApplet parent;

  DigitalRain(PApplet p) {
    this.parent = p;
  }
  RainInformation[] newRain = new RainInformation[400]; // make a bunch of raindrops



  void setup() {
    for (int i = 0; i < newRain.length; i++) {
      newRain[i] = new RainInformation(parent); //
    }
  }

  void draw() {  

    for (int i = 0; i < newRain.length; i++) {
      newRain[i].move(); // Couldn't get the draw function to work without having the .move and .show in a seperate class im a dummy
      newRain[i].show();
    }
  }
}
