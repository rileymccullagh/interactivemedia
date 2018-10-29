package ptz;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Prism {

  private List<Float> x = new ArrayList<Float>();
  private List<Float> y = new ArrayList<Float>();
  float prism_height = 1;
  
  int scale = 100;
  float rotation = 0.1f;
  float accel = 0.001f;
  float x_pos = 0;
  float y_pos = 0;
  
  public Prism(int starting_x, int starting_y, int starting_rot, int scale) {
	  x_pos = starting_x;
	  y_pos = starting_y;
	  rotation = starting_rot;
	  this.scale = scale;
  }

  public int camera_max() {
	  return (int)(scale * 0.5f);
  }

  public void update_sides(int sides, PApplet p) {
    float angle_per_edge = PApplet.radians(360.0F / sides);  

    List<Float> x = new ArrayList<Float>();
    List<Float> y = new ArrayList<Float>();
    
    for (int i = 0; i < (sides); i++) {
      x.add((float)(Math.sin(angle_per_edge * i)));
      y.add((float)(Math.cos(angle_per_edge * i)));
    }
    
    x.add((float)(Math.sin(0)));
    y.add((float)(Math.cos(0)));
    
    this.x = x;
    this.y = y;
  }
  
  void setScale(int scale){
      this.scale = scale;  
  }
  
  
  void setpos(int x, int y){
     x_pos = x;
     y_pos = y;
  }
  
  void rotate() {
	  rotation += accel;
  }
  
  //Height is 0-1
  void draw(List<PImage> wall_images, PImage top_image, PImage bottom_image, PApplet p) {
    p.scale(scale);
    p.translate(x_pos, y_pos);
    p.rotateZ(rotation);
    p.fill(0.0f,0.0f,255.0f);
    
    if (wall_images.size() + 1 != x.size()) {
      update_sides(wall_images.size(), p);
    }
    
    for (int i = 0; i < x.size() - 1; i++)
    {
      p.beginShape(PConstants.QUADS);
      p.texture(wall_images.get(i));
      p.vertex(x.get(i), y.get(i), -prism_height / 2.0f,1,1);
      p.vertex(x.get(i + 1), y.get(i+1), -prism_height / 2.0f, 0, 1);
      p.vertex(x.get(i + 1), y.get(i+1), prism_height / 2.0f,0,0);
      p.vertex(x.get(i), y.get(i), prism_height / 2.0f,1,0);
      p.endShape();
    }

    draw_square(prism_height / 2.0f, top_image, p);
    draw_square(-prism_height / 2.0f, bottom_image, p);
    
    p.rotateZ(-rotation);    
    p.translate(-x_pos,-y_pos);
    p.scale(1);   
  }
  
  void draw_square(float z, PImage img, PApplet p) {
    p.beginShape(PConstants.QUADS);
    p.texture(img);
    p.vertex(-1,-1,z,1,1);
    p.vertex(1,-1,z,0,1);
    p.vertex(1,1,z,0,0);
    p.vertex(-1,1,z,1,0);
    p.endShape();
  }
  
  void draw_shape (float z, PApplet p) {
    for (int i = 0; i < x.size() -1; i++)
    {
      p.beginShape();
      p.vertex(x.get(i), y.get(i), z);
      p.vertex(x.get(i + 1), y.get(i+1), z);
      p.vertex(0, 0, z);
      p.endShape();
    }
  }
};