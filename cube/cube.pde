float a1; 
float a2;
PImage bg;
PImage tex1;
PImage tex2;
PImage tex3;
PImage tex4;
PImage tex5;
PImage tex6;
String s1 = "1.png";
String s2 = "2.jpeg";
String s3 = "3.jpg";
String s4 = "4.png";
String s5 = "5.jpeg";
String s6 = "6.jpg";
Code[] codes = new Code[200];
  
void settings() {
     bg = loadImage("space.png"); 
     size(bg.width, bg.height, P3D);
 }
  
void setup()
{
  tex1 = loadImage(s1);
  tex2 = loadImage(s2);
  tex3 = loadImage(s3);
  tex4 = loadImage(s4);
  tex5 = loadImage(s5);
  tex6 = loadImage(s6);
smooth();
textureMode(NORMAL);
for (int i = 0; i < codes.length; i++) {
codes[i] = new Code();
}
}

void draw()
{
  background(bg);
for (int i = 0; i < codes.length; i++) {
codes[i].fall();
  codes[i].show();

}

noStroke();
  lights();
translate(width/2, height/2, 0);
// rotateX( map(mouseY,0,height,0,TWO_PI));
// rotateY( map(mouseX,0,width,0,TWO_PI));
rotateY(a1); 
rotateX(a2);
scale(100);
beginShape(QUADS);
fill(255,255,255);
 texture(tex1);

// +Z "front" face
  vertex(-1, -1,  1, 0, 0);
  vertex( 1, -1,  1, 1, 0);
  vertex( 1,  1,  1, 1, 1);
  vertex(-1,  1,  1, 0, 1);

endShape();

;
 texture(tex2);
  // -Z "back" face
  vertex( 1, -1, -1, 0, 0);
  vertex(-1, -1, -1, 1, 0);
  vertex(-1,  1, -1, 1, 1);
  vertex( 1,  1, -1, 0, 1);
endShape();

beginShape(QUADS);
 texture(tex3);
  // +Y "bottom" face
  vertex(-1,  1,  1, 0, 0);
  vertex( 1,  1,  1, 1, 0);
  vertex( 1,  1, -1, 1, 1);
  vertex(-1,  1, -1, 0, 1);
endShape();

beginShape(QUADS);

 texture(tex4);
  // -Y "top" face
  vertex(-1, -1, -1, 0, 0);
  vertex( 1, -1, -1, 1, 0);
  vertex( 1, -1,  1, 1, 1);
  vertex(-1, -1,  1, 0, 1);
endShape();
beginShape(QUADS);

 texture(tex5);
  // +X "right" face
  vertex( 1, -1,  1, 0, 0);
  vertex( 1, -1, -1, 1, 0);
  vertex( 1,  1, -1, 1, 1);
  vertex( 1,  1,  1, 0, 1);
endShape();

beginShape(QUADS);

 texture(tex6);
  // -X "left" face
  vertex(-1, -1, -1, 0, 0);
  vertex(-1, -1,  1, 1, 0);
  vertex(-1,  1,  1, 1, 1);
  vertex(-1,  1, -1, 0, 1);

endShape();

a1+=.03;
a2+=.04;
}
