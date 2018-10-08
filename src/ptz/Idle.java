package ptz;

import processing.core.PApplet;
import ptz_music.IdleMusic;

class Idle {
  PApplet parent;

  DigitalRain dr;
  //TextureCube tc;
  Background bg;
  Vortex vortex;
  IdleMusic idleMusic;


  Idle(PApplet p) {
    this.parent = p;
    
    idleMusic = new IdleMusic(parent, null);

    bg = new Background(this.parent);
    //tc = new TextureCube(this.parent);
    dr = new DigitalRain(this.parent);
	vortex = new Vortex(parent, idleMusic.output);

  }



  void draw() {
    bg.draw();
	vortex.draw();

    dr.randomise();
    dr.draw();
  // tc.loadTextures();
   // tc.draw();

  }
}