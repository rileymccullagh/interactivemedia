// This class uses colorAverage to use complementary colors generated from the average color of the background image. 
// In future versions it will have the displayed text ("metaStr") to show meta data gathered from the webcam feed, such as weather, location name, or 
// high frequency words found in the locations matching wikipedia page. 
import processing.core.PApplet;

class RainInformation {

  float x;
  float y;
  float z;
  float yspeed;
  int tCount; // Determines a metaStr to be displayed from 6 options.
  int boxWidth;
  int textS;
  String metaStr;
  color rainColor;

  RainInformation() {
    x  = random(width); // gives each drop a random x value
    y  = random(-1700, -800); // gives each drop a random y value
    z  = random(0, 20); // gives each drop a random z value
    yspeed = map(z, 0, 20, 0.5, 1);
    boxWidth = (int)map(z, 0, 20, 4, 29); // Scales the width of the text field, to force each character of a string to appear on a single line
    rainColor = colorsComp[(int)random(8)]; // gives each instance a generated color from an array of 8 complementary colors. 
    textS = (int)map(z, 0, 20, 5, 33); // scales text size with Z value 
    tCount = (int)random(1, 10); // Determines a metaStr to be displayed from 6 options.
  }

  void fall() {
    y = y + yspeed; // Makes them fall
    float grav = map(z, 0.0, 20.0, 0.0, 0.05); // just a little bit of gravity 
    yspeed = yspeed + grav; // add gravity to speed

    if (y > height) { // reset once below the screen
      y = random(-1000, -600);
      yspeed = map(z, 0, 20, 0.5, 1);
    }
  }

  void show() {  
    if (tCount < 2) { // Determines a metaStr to be displayed from 6 options.
      metaStr = "locationdata";
    } else if (tCount < 4) {
      metaStr = "wordfrequency";
    } else if (tCount < 6) {
      metaStr = "text";
    } else if (tCount < 8) {
      metaStr = "loading";
    } else if (tCount < 10) {
      metaStr = "live love laugh";
    } else {
      metaStr = "ERRORLOL";
    }
    textAlign(LEFT);
    textFont(mono); // Needed to have a monospaced font for the boxWidth to work.   
    textSize(textS);
    text(metaStr, x, y, boxWidth, 1000);
    fill(rainColor);
  }
}
