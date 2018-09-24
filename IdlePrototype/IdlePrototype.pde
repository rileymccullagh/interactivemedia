Idle idle;
PApplet parent;

void setup() {
  idle = new Idle(this.parent);
  size(1000, 600, P3D);
}

void draw() {
  idle.draw();
}
