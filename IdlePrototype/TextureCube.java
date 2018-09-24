import processing.core.PApplet;

class TextureCube {

  PApplet parent;

  TextureCube(PApplet p) {
    this.parent = p;
  }

  float a1; 
  float a2;
  PImage tex1;
  PImage tex2;
  PImage tex3;
  PImage tex4;
  PImage tex5;
  PImage tex6;
  String url1 = "http://www.visitsydneyaustralia.com.au/images/Balmain_aerial.jpg";
  String url2 = "http://www.visitsydneyaustralia.com.au/images/Balmain_aerial.jpg";
  String url3 = "http://www.visitsydneyaustralia.com.au/images/Balmain_aerial.jpg";
  String url4 = "http://www.visitsydneyaustralia.com.au/images/Balmain_aerial.jpg";
  String url5 = "http://www.visitsydneyaustralia.com.au/images/Balmain_aerial.jpg";
  String url6 = "http://www.visitsydneyaustralia.com.au/images/Balmain_aerial.jpg";


  void loadTextures()
  {
    tex1 = loadImage(url1);
    tex2 = loadImage(url2);
    tex3 = loadImage(url3);
    tex4 = loadImage(url4);
    tex5 = loadImage(url5);
    tex6 = loadImage(url6);
  }

  void draw()
  {
    smooth();
    textureMode(NORMAL);
    noStroke();
    lights();
    translate(width/2, height/2, 0);
    rotateY(a1); 
    rotateX(a2);
    scale(100);
    beginShape(QUADS);
    fill(255, 255, 255);
    texture(tex1);

    // +Z "front" face
    vertex(-1, -1, 1, 0, 0);
    vertex( 1, -1, 1, 1, 0);
    vertex( 1, 1, 1, 1, 1);
    vertex(-1, 1, 1, 0, 1);

    endShape();

    texture(tex2);
    // -Z "back" face
    vertex( 1, -1, -1, 0, 0);
    vertex(-1, -1, -1, 1, 0);
    vertex(-1, 1, -1, 1, 1);
    vertex( 1, 1, -1, 0, 1);
    endShape();

    beginShape(QUADS);
    texture(tex3);
    // +Y "bottom" face
    vertex(-1, 1, 1, 0, 0);
    vertex( 1, 1, 1, 1, 0);
    vertex( 1, 1, -1, 1, 1);
    vertex(-1, 1, -1, 0, 1);
    endShape();

    beginShape(QUADS);

    texture(tex4);
    // -Y "top" face
    vertex(-1, -1, -1, 0, 0);
    vertex( 1, -1, -1, 1, 0);
    vertex( 1, -1, 1, 1, 1);
    vertex(-1, -1, 1, 0, 1);
    endShape();
    beginShape(QUADS);

    texture(tex5);
    // +X "right" face
    vertex( 1, -1, 1, 0, 0);
    vertex( 1, -1, -1, 1, 0);
    vertex( 1, 1, -1, 1, 1);
    vertex( 1, 1, 1, 0, 1);
    endShape();

    beginShape(QUADS);

    texture(tex6);
    // -X "left" face
    vertex(-1, -1, -1, 0, 0);
    vertex(-1, -1, 1, 1, 0);
    vertex(-1, 1, 1, 1, 1);
    vertex(-1, 1, -1, 0, 1);

    endShape();

    a1+=.03;
    a2+=.04;
  }
}
