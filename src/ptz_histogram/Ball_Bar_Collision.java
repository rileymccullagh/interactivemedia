package ptz_histogram;

class Ball_Bar_Collision {
  Ball_Bar_Collision(Collision_Side collision, int bar) {
    this.collision = collision;
    if (collision != Collision_Side.NONE) {
      assert(bar > -1);
      this.bar = bar;
    }
  }

  boolean isColliding() {
    return collision != Collision_Side.NONE;
  }

  int bar = -1;
  Collision_Side collision = Collision_Side.NONE;
}