package ptz_histogram;

import processing.core.PGraphics;
import java.util.Arrays;

class Ball_Manager {
  private Ball[] balls;
  private int radius;
  private String displaytext; 
  private int ball_color; 
  private int text_color;

  Ball_Manager(int ball_count, int radius, int ball_color, int text_color, String displaytext) {
    balls = new Ball[ball_count];
    this.radius = radius;
    this.ball_color = ball_color; 
    this.text_color = text_color;
    this.displaytext = displaytext;
  }
  void update_balls() {
    for (Ball ball : balls) {
      ball.update();
    }
  }

  public void move_balls_into_bounds(int screen_width, int screen_height) {
    for (Ball ball : balls) {
      ball.ball_into_bounds(screen_width, screen_height, radius);
    }
  }

  void reset_balls(int screen_width) {
    for (int i = 0; i < balls.length; i++) {
      balls[i] = new Ball();
      balls[i].x = (short)((i * 60) %  screen_width);
      balls[i].y = (short)((15 * i) % screen_width);
      balls[i].vel_x = (byte)(1);
    }
  }

  public void Handle_Ball_To_Ball_Collisions(Bar_Manager bars) {
    boolean collision_found = false;
    byte iteration_limit = 100; //We define a limit for collisions
    byte iteration_limit_count = 0;

    for (int i = 0; i < balls.length; i++) {
      collision_found = false;
      for (int j = i + 1; j < balls.length && collision_found == false; j++) {
        if (balls[i].colliding_With_Ball(balls[j], radius)) {
          int overlap_padding = 4; //Can be anything
          Ball_Pair resolved_balls = Ball_Pair.handle_collision_with_balls(new Ball_Pair(balls[i], balls[j]), radius, overlap_padding);
          resolved_balls.swap();
          resolved_balls = Ball_Pair.move_balls_outside_of_bars(resolved_balls, bars, radius);
          balls[i] = resolved_balls.b1;
          balls[j] = resolved_balls.b2;

          iteration_limit++;
          if (iteration_limit_count > iteration_limit) {
            break;
          }
          collision_found = true;
          i = 0;
          iteration_limit_count++;
        }
      }
    }
  }
  
  void sortByX(){
   Arrays.sort(balls, new sortBallByX()); 
  }
  
  PGraphics draw(PGraphics pg) {
    for (int i = 0; i < balls.length; i++) {
      pg = balls[i].draw(pg, radius, displaytext.charAt(i % displaytext.length()), ball_color, text_color);
    }
    return pg;
  }

  void bar_to_ball_collisions (Bar_Manager bars) {
    Arrays.sort(balls, new sortBallByX());
    int min = 0;
    for (int i = 0; i < bars.bar_count(); i++) {
      for (int j = min; j < balls.length; j++) {
        Collision_Side col = bars.ball_in_bar(balls[j], i, radius);
        if (balls[j].x + radius < bars.get_bar_left(i + 1)) { //Optimisation
          min = j;
        }
        if (col != Collision_Side.NONE) {
          balls[j] = Bar_Manager.handle_bar_to_ball_collision(balls[j], i, bars, col, radius);
        }

        if (balls[j].x - radius > bars.get_bar_left(i+1)) { //Gonne be honest, no fucking clue why i + 1 doesn't work, 2 seems to work
          break;
        }
      }
    }
  }
  
  int getRadius() {
   return radius; 
  }
  
  static Ball_Pair move_out (Ball_Pair balls, Bar_Manager bars, int radius) {
    //Assumption being made: B1 is colliding on the left
    Collision_Side col = bars.ball_in_any_bar(balls.b1, radius); 
    Collision_Side col_new;
    while (col != Collision_Side.NONE) {
      if (col == Collision_Side.TOP) {
        balls.b1.y--;
        balls.b2.y--;
      } else if (col == Collision_Side.RIGHT) {
        balls.b1.x++;
      } else if (col == Collision_Side.LEFT) {
        balls.b1.x--;
      }
      col_new = bars.ball_in_any_bar(balls.b1, radius);
      if (col_new != col) {
        break; //This is necessary to block infinite loops
      }
      col = col_new;
    }
    return balls;
  }
}
