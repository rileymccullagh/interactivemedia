package webcam;

// cameras from here work
// https://www.insecam.org/en/bytype/Vivotek/

import processing.core.PApplet;
import processing.core.PImage;

public class Camera {
  PApplet parent;
  PImage current, future;
  boolean newImage;
  boolean retrieving = false;
  public Camera(PApplet p) {
    this.parent = p;
    getAsync();
  }
  int num = 0;
  public PImage getNext() {
	  //System.out.println("Getting");
	  if (retrieving == false) {
		  System.out.println("Enter");
		  retrieving = true;
		  new Thread (new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				num++;
				current = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=153779841" + num);
				retrieving = false;
				System.out.println("EXECUTED ASYNC");
			}
		  }).start();
	  }
	  //this.current = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=1537798410");
	  //parent.thread("getAsync");
	  return current;
  }
  
  public void getAsync() {
	  this.current = parent.loadImage("http://85.187.13.43:8080/cgi-bin/viewer/video.jpg?r=1537798410");
	  this.newImage = true;
	}
}

