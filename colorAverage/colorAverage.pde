
// Keep in mind fellas that there is a distinction between the way we write Colour down here and the way the bloody yanks spell it. 'Color' is always used here. Die for your country.

import com.cage.colorharmony.*;

ColorHarmony colorHarmony = new ColorHarmony(this); // New colorharmony 
color[] colorsComp = new color[8];
color[] colorsAnal = new color[8];
String hexValue; // The value that the colour_average is passed into once converted from RGM to hex.
PImage webImg;

void setup() {
  size(400,400);
 String url = "http://www.visitsydneyaustralia.com.au/images/Balmain_aerial.jpg"; // Currently using placeholder image, will use Riley's web get method
 PImage webImg = loadImage(url, "png"); 
 int detail = 100; // Specifies the detail used in the colour_average. Around ~100 usually works but will depend on final size of installation
  for (int i=0; i<width; i+=detail) {
    for (int j=0; j<height; j+=detail) {
      PImage newImg = webImg.get(i, j, detail, detail);
      fill(extractColorFromImage(newImg));
      rect(i, j, detail, detail);
    }
  }
  colorsComp = loadCompPalette(); // Generates and loads the complementary palette into colorsComp
  colorsAnal = loadAnalPalette(); // Generates and loads the analogous palette into colorsAnal
}
 
// Draw function not necessary, just going to keep it as a little implementation note. 
void draw(){
background(colorsComp[(int)random(8)]); 
rectMode(CENTER);
noStroke();
fill(colorsAnal[(int)random(8)]);
rect(width/2,height/2,width/2,height/2);
frameRate(5);
}
