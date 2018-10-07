color extractColorFromImage(final PImage newImg) {  // I'm not going to pretend that I fully understand this.
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
  hexValue = colorHarmony.P52Hex(color(r, g, b));
  return color(r, g, b);
}
