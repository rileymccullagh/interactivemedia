package webcam;

// cameras from here work
// https://www.insecam.org/en/bytype/Vivotek/

import processing.core.PApplet;
import processing.core.PImage;

public class Camera {
  PApplet parent;
  PImage current, future;
  boolean newImage;
  
  public Camera(PApplet p) {
    this.parent = p;
  }
  
  
  public PImage getNext() {
	  this.current = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=1537798410");
	  parent.thread("getAsync");
	  return current;
  }
  
  void getAsync() {
	  this.future = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=1537798410");
	  this.newImage = true;
	}
}

