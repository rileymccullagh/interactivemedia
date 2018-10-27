package ptz_histogram;

import processing.core.PVector;
import processing.core.PGraphics;
import java.util.*;
class Ball {
  PVector get_Position() {
    return new PVector(x, y);
  }


  PVector get_Vel() {
    return new PVector(vel_x, vel_y);
  }

  PVector get_bounceback(Ball rhs) {
    return PVector.sub(get_Vel(), rhs.get_Vel());
  }

  PGraphics draw(PGraphics pg, int radius, char text, int ball_color, int text_color) {
    pg.fill(ball_color);
    pg.ellipse(x, y, radius * 2, radius * 2); 
    pg.fill(text_color);
    pg.text(text, x, y - (radius / 4));
    return pg;
  }
  static short absRound(float val) {
    if (val < 0) {
      return (short)(Math.floor(val));
    }
    return (short)(Math.ceil(val));
  }

  //Rounds 0.4 to 1, and -0.4 to -1
  void apply_Vel_ABSround (PVector vel) {
    x += Ball.absRound(vel.x);
    y += Ball.absRound(vel.y);
  }

  int get_left_of_ball(int radius) {
    return x - radius;
  }

  int get_right_of_ball(int radius) {
    return x + radius;
  }

  int get_bottom_of_ball(int radius) {
    return y + radius;
  }
  //Swaps the vels, and returns the new RHS
  Ball swap_vel(Ball rhs) {
    //For simplicity, we swap the vels of both balls
    byte old_vel_x = vel_x;
    byte old_vel_y = vel_y;
    vel_x = rhs.vel_x;
    vel_y = rhs.vel_y;
    rhs.vel_x = old_vel_x;
    rhs.vel_y = old_vel_y;
    return rhs;
  }

  //Assumes the same radius
  boolean colliding_With_Ball (Ball rhs, int radius) { 
    float distance = get_Position().dist(rhs.get_Position()) ;
    return (Math.ceil(distance) < (radius) * 2);
  }
  float overlap_depth (Ball rhs, int radius) {
    return overlap_depth(rhs.get_Position(),radius);
  }
  float overlap_depth (PVector rhs, int radius) {
    return (float)(((radius) * 2.0) - get_Position().sub(rhs).mag());
  }
  byte terminal_velocity = 15;
  byte gravity_x = 0;
  byte gravity_y = 1;

  void update() {
    x_old = x; 
    y_old = y;

    x += vel_x;
    y += vel_y; 

    vel_x += accel_x;
    vel_y += accel_y;
    
    if (vel_x < 0) {
      vel_x = (byte)(Math.max(vel_x, -terminal_velocity));
    } else {
      vel_x = (byte)(Math.min(vel_x, terminal_velocity));
    }
    //Collision work, only checks ahead

    if (vel_y < 0) {
      vel_y = (byte)(Math.max(vel_y, -terminal_velocity));
    } else {
      vel_y = (byte)(Math.min(vel_y, terminal_velocity));
    }

    accel_x = gravity_x;
    accel_y = gravity_y;
  }

  void ball_into_bounds(int max_width, int max_height, int radius) {
    if (y > max_height - radius) {
      y = (short)(max_height - radius);
      vel_y = (byte)(vel_y * -0.9);  
      vel_x = (byte)(5);
    }
    if (x + radius < 0) {
      x = 0;
      y = 0;
      vel_x = (byte)(Math.abs(vel_x));
    }
    if (x - radius >= max_width ) {
      x = (short)(max_width);
      y = 0;
      vel_x = (byte)(-Math.abs(vel_x));
    }
  }


  short x; 
  short y; 

  short x_old; 
  short y_old; 

  byte vel_x; 
  byte vel_y; 
  
  byte accel_x; 
  byte accel_y; 
}



class sortBallByX implements Comparator<Ball> {
  @Override
public int compare(Ball lhs, Ball rhs) {
    return (lhs.x - rhs.x);
  }
}
