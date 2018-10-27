package ptz_histogram;

import processing.core.PGraphics;
import processing.core.PConstants;
import processing.core.PApplet;

//A class to handle balls and bars
public class Engine_Ball_Bar {
	
  private int screen_width = 0;
  private int screen_height = 0;
  private PGraphics pg; 
  private Bar_Manager bar_manager;
  private Ball_Manager ball_manager;

  //If bar_color is smaller than the number of balls, it will cycle through the colors
  public Engine_Ball_Bar (int screen_width, int screen_height, int num_of_bars, int num_of_balls, PApplet parent, int ball_color, int bar_color, int text_color, String text) {
    this.bar_manager = new Bar_Manager(screen_width, screen_height, num_of_bars, bar_color);
    this.ball_manager = new Ball_Manager(num_of_balls, (int)(Math.ceil(screen_width * 0.03)), ball_color, text_color, text); 
    this.screen_width = screen_width; 
    this.screen_height = screen_height;
    ball_manager.reset_balls(screen_width);
    pg = parent.createGraphics(screen_width,screen_height);
    pg.beginDraw();
    pg.endDraw();
  }
  
  public PGraphics draw(float[] values) {
    this.update(values);
    pg.beginDraw();
    pg.clear();
    pg.textSize(ball_manager.getRadius() * 2);
    pg.textAlign(PConstants.CENTER, PConstants.CENTER);

    pg = ball_manager.draw(pg);
    pg = bar_manager.draw(pg);
    pg.endDraw();
    return pg;
  }

  private void update(float[] values) {
    ball_manager.update_balls();
    bar_manager.update_bars(values);
    ball_manager.move_balls_into_bounds(screen_width, screen_height);
    handle_collisions();
  }

  void handle_collisions() {
    ball_manager.sortByX();
    ball_manager.bar_to_ball_collisions(bar_manager);
    ball_manager.sortByX();
    ball_manager.Handle_Ball_To_Ball_Collisions(bar_manager);
    ball_manager.sortByX();
    ball_manager.bar_to_ball_collisions(bar_manager);
  }
}
