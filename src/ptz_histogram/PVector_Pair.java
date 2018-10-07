package ptz_histogram;

import processing.core.PVector;

class PVector_Pair {
  PVector_Pair(PVector v1, PVector v2) {
    this.v1 = v1;
    this.v2 = v2;
  }
  PVector v1;
  PVector v2;
  static PVector_Pair resolve_overlap(Ball lhs, Ball rhs, int radius, int overlap_padding) {
    //We then get the vectors of each ball's current motion
  
    PVector lhs_bounceback_dir =  lhs.get_bounceback(rhs).normalize(); //Normal Vector
    PVector rhs_bounceback_dir =  rhs.get_bounceback(lhs).normalize(); //Normal Vector
  
    //We get the depth of the collision
    float overlap_depth = rhs.overlap_depth(lhs, radius + overlap_padding);
  
    //To distribute the distance we must move each point, we divide by two
    float divisor = (overlap_depth) / 2.0F;
  
  
    float bounceback_padding = 0; //Add a little extra, any number is fine
    lhs_bounceback_dir = lhs_bounceback_dir.mult(divisor + bounceback_padding); 
    rhs_bounceback_dir = rhs_bounceback_dir.mult(divisor + bounceback_padding);
  
    return new PVector_Pair(lhs_bounceback_dir, rhs_bounceback_dir);
  }
}
