package ptz_histogram;

import processing.core.PGraphics;

class Bar_Manager {
  private int screen_width;
  private int screen_height;
  private short[] bars_height;
  private short[] bars_previous_height;
  private int[][] bar_color;
  Bar_Manager(int screen_width, int screen_height, int num_of_bars, int[][] bar_color) {
    this.screen_width = screen_width;
    this.screen_height = screen_height;
    this.bar_color = bar_color;
    bars_height = new short[num_of_bars];
    bars_previous_height = new short[num_of_bars];
    for (int i = 0; i < bars_height.length; i++) {
      bars_height[i] = 0;
    }
  }

  void update_bars (float[] values) {
    for (int i = 0; i < bars_height.length; i++) {
      bars_previous_height[i] = bars_height[i];
      bars_height[i] = (short)(screen_height * values[i]); //We can make this a lerp 
      if (bars_height[i] < 20) {
        bars_height[i] = 20;
      }
    }
  }

  PGraphics draw(PGraphics pg) {

    for (int i = 0; i < bars_height.length; i++) {
      pg.fill(bar_color[i % bar_color.length][0],bar_color[i % bar_color.length][1],bar_color[i % bar_color.length][2]);
      pg.rect(get_bar_width() * (i), get_height(i), get_bar_width(), bars_height[i]);
    }
    return pg;
  }

  int bar_count () 
  {
    return bars_height.length;
  }
  int get_height(int i) {
    return screen_height - bars_height[i];
  }
  int get_height_previous(int i) {
    return screen_height - bars_previous_height[i];
  }

  int get_bar_width() {
    return (int)(Math.ceil(screen_width / bars_height.length));
  }

  int get_bar_left(int i) {
    return get_bar_width() * i;
  }

  boolean point_in_bar (int x, int y, int bar) {
    if (x > get_bar_left(bar) && x < get_bar_left(bar + 1)) {
      if (y > get_height(bar)) {
        return true;
      }
    }
    return false;
  }

  //Derived from jeffreythompson who derived it from Matt Worden 
  //http://www.jeffreythompson.org/collision-detection/circle-rect.php 
  boolean circleRect(Ball ball, float radius, int iter) {

    // temporary variables to set edges for testing
    float corner_X = ball.x;
    float corner_Y = ball.y;

    // which edge is closest?
    if (ball.x < get_bar_left(iter)) {         
      corner_X = get_bar_left(iter);
    }// test left edge
    else if (ball.x > get_bar_left(iter)+get_bar_width()) {
      corner_X = get_bar_left(iter) + get_bar_width();
    } // right edge
    if (ball.y < get_height(iter)) {         
      corner_Y = get_height(iter);      // top edge
    } else if (ball.y > get_height(iter)+bars_height[iter]) {
      corner_Y = get_height(iter) + bars_height[iter];   // bottom edge
    }

    // get distance from closest edges
    float distX = ball.x-corner_X;
    float distY = ball.y-corner_Y;
    float distance = (float)(Math.sqrt( (distX*distX) + (distY*distY) ));

    // if the distance is less than the radius, collision!
    if (distance <= radius) {
      return true;
    }
    return false;
  }

  Collision_Side ball_in_bar (Ball ball, int bar, int radius) {
    if (circleRect(ball, radius, bar)) {
      return  bar_to_ball_side(ball, get_height(bar), get_height_previous(bar), get_bar_left(bar), get_bar_left(bar + 1), radius);
    } else {
      return Collision_Side.NONE;
    }
  }

  Collision_Side ball_in_any_bar (Ball ball, int radius) {
    for (int i = 0; i < bars_height.length; i++) {
      Collision_Side col = ball_in_bar(ball, i, radius);
      if (col != Collision_Side.NONE) {
        return col;
      }
    }
    return  Collision_Side.NONE;
  }
  static Ball handle_bar_to_ball_collision(Ball ball, int bar_iter, Bar_Manager bars, Collision_Side col, int radius) {

    int left_of_bar = bars.get_bar_left(bar_iter);
    int right_of_bar = left_of_bar + bars.get_bar_width();

    switch (col) {
    case LEFT: 
      {
        ball.vel_x = (byte)-(Math.abs(ball.vel_x));
        ball.x = (short)(left_of_bar - (radius + 1));

        if (bar_iter > 0) {
          int new_bar_top = bars.get_height(bar_iter - 1); 

          //If snapping left caused us to go into the bar, we snap above that bar
          if (new_bar_top < ball.y) {
            ball.y = (short)(new_bar_top - (radius + 1));
            ball.vel_y = (byte)-(Math.abs(ball.vel_y));
          }
        }
        break;
      }
    case RIGHT: 
      {
        ball.vel_x = (byte)(Math.abs(ball.vel_x));
        ball.x = (short)(left_of_bar + bars.get_bar_width() + (radius) + 1);

        if (bar_iter < bars.bar_count() - 1) {
          int new_bar_top = bars.get_height(bar_iter + 1); 

          //If snapping right caused us to go into the bar, we snap above that bar
          if (new_bar_top < ball.y) {
            ball.y = (short)(new_bar_top - (radius + 1));
            ball.vel_y = (byte)-(Math.abs(ball.vel_y));
          }
          break;
        }
      }
    case TOP: 
      {
        ball.vel_y = (byte)(-15);
        ball.y = (short)((bars.get_height(bar_iter) - radius) - 2);
        break;
      }
    case NONE: 
      {
        break;
      }
    }
    return ball;
  }
  static Collision_Side bar_to_ball_side(Ball ball, int bar_height_curr, int bar_height_old, int bar_left, int bar_right, int radius) { 
    //Bodge, needs to be done seperately

    int ball_left_prev = ball.x_old - radius;
    int ball_right_prev = ball.x_old + radius;

    boolean was_previously_in_lines = 
      ((ball_right_prev > bar_left) && (ball_left_prev < bar_right));

    int ball_left = ball.x - radius;
    int ball_right = ball.x + radius;
    boolean in_lines = 
      ((ball_right > bar_left) && (ball_left < bar_right));

    //BAD ASSUMPTION, IF YOU ARE COLLIDING FOR TWO FRAMES THIS WILL STILL RETURN TRUE, BUT YOU MAY HAVE COLLIDED AT THE SIDES FOR TWO FRAMES
    if (in_lines && was_previously_in_lines && ball.y_old + radius < bar_height_old) {
      return (Collision_Side.TOP);
    }

    //Assumes there's no way it can go from below to above (Bullshit, bar moves up a lot)
    if (ball.y_old > bar_height_curr) {
      if (ball.x_old < bar_left) {
        return Collision_Side.LEFT;
      }
      return Collision_Side.RIGHT;
    }
    if (was_previously_in_lines && (ball.y_old <= bar_height_curr)) {
      return (Collision_Side.TOP);
    }

    //SET A BREAKPOINT BELOW AND NOTICE HOW THE CODE NEVER ENTERS BELOW. STRANGE AINT IT.
    //BUT DON'T REMOVE BELOW, IT'S AN EXTREME EDGE CASE



    float gradiant = (float)(ball.y - ball.y_old) / (float)(ball.x - ball.x_old);
    float y_intercept = ball.y - gradiant * ball.x_old;

    float x = (bar_height_curr - y_intercept) / gradiant;
    float bar_intercept = 0;
    Collision_Side ret;


    if (ball.x_old - radius <= bar_left) {
      bar_intercept = (gradiant * bar_left) + y_intercept; //Came from left
      ret = Collision_Side.LEFT;
    } else {
      bar_intercept = (gradiant * bar_right) + y_intercept; //Came from right
      ret = Collision_Side.RIGHT;
    }
    if (bar_intercept + radius > bar_height_curr) {
      return ret;
    } else {
      return Collision_Side.TOP;
    }
  }
}


