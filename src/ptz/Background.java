package ptz;

import processing.core.*;

class Background {
	PApplet parent;
	
	PImage bgi;
	String bgiImg = "space.png";

    bgi = parent.loadImage("space.png");
    bgi.resize(parent.width,parent.height);
    parent.image(bgi,0,0);
  }
}