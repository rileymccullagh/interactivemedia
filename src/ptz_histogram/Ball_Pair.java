package ptz_histogram;

class Ball_Pair {
  Ball_Pair(Ball b1, Ball b2) {
    this.b1 = b1;
    this.b2 = b2;
  }
  Ball b1;
  Ball b2;

  void swap() {
    Ball swap = b1;
    b1 = b2;
    b2 = swap;
  }
  static Ball_Pair handle_collision_with_balls(Ball_Pair balls, int radius, int padding) {
    PVector_Pair dirs = PVector_Pair.resolve_overlap(balls.b1, balls.b2, radius, padding);

    //We then apply the new direction, to move out of the depth
    //1.1 is a randomly chosen number, must be negative.
    balls.b1.apply_Vel_ABSround(dirs.v1.mult(-1.1F));
    balls.b2.apply_Vel_ABSround(dirs.v2.mult(-1.1F));

    //To simply transfer vel, we swap the vels of both balls
    balls.b2 = balls.b1.swap_vel(balls.b2);
    if (balls.b1.colliding_With_Ball(balls.b2, radius)) {
    }
    return balls;
  }

  //The top gets pushed up, bottom gets pushed down
  static Ball_Pair resolve_ball_collision_vertically(Ball_Pair balls, int radius) {
    if (balls.b1.y > balls.b2.y) {
      balls.swap();
    }
    while (balls.b1.colliding_With_Ball(balls.b2, radius)) {
      balls.b1.y--;
      balls.b2.y++;
    }
    return balls;
  }
  static Ball_Pair move_balls_outside_of_bars(Ball_Pair balls, Bar_Manager bars, int radius) {

    if (balls.b1.x > balls.b2.x) {
      balls.swap();
    }


    balls = Ball_Manager.move_out(balls, bars, radius);

    balls.swap();

    balls = Ball_Manager.move_out(balls, bars, radius);

    //Top ball goes up, bottom goes down
    balls = Ball_Pair.resolve_ball_collision_vertically(balls, radius);

    while (bars.ball_in_any_bar(balls.b1, radius) == Collision_Side.TOP | bars.ball_in_any_bar(balls.b2, radius) == Collision_Side.TOP) {
      balls.b1.y--;
      balls.b2.y--;
    }
    return balls;
  }
}
