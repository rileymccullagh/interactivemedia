package ptz_histogram;

import processing.core.PApplet;

//Helps control building of the class
public class Engine_Ball_Bar_Builder {
  public Engine_Ball_Bar_Builder() {}
  public int num_of_bars;
  public int num_of_balls; 
  public int ball_color; 
  public int bar_color; 
  public int text_color; 
  public String text;
  public Engine_Ball_Bar build (int screen_width, int screen_height, PApplet parent){
    return new Engine_Ball_Bar(screen_width,screen_height,num_of_bars,num_of_balls,parent,ball_color,bar_color,text_color,text);
  }
};
