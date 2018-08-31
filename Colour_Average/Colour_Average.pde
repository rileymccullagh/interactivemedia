PImage webImg;

void setup() {
 String url = "https://static1.squarespace.com/static/59d2bea58a02c78793a95114/t/5b7ae9852b6a28a662146bca/1534781857426/quip.png";
 PImage webImg = loadImage(url, "png"); 
 int detail = 100;
  for (int i=0; i<width; i+=detail) {
    for (int j=0; j<height; j+=detail) {
      PImage newImg = webImg.get(i, j, detail, detail);
      fill(extractColorFromImage(newImg));
      rect(i, j, detail, detail);
    }
  }
}
 
color extractColorFromImage(final PImage newImg) {
  newImg.loadPixels();
  color r = 0, g = 0, b = 0;
 
  for (final color c : newImg.pixels) {
    r += c >> 020 & 0xFF;
    g += c >> 010 & 0xFF;
    b += c        & 0xFF;
  }
 
  r /= newImg.pixels.length;
  g /= newImg.pixels.length;
  b /= newImg.pixels.length;
  print( r + "," + g + "," + b + " ");
  return color(r, g, b);
}
