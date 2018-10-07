class colorAverage { // This class is possible due to the colorharmony library and posts on this thread https://forum.processing.org/two/discussion/15573/get-the-average-rgb-from-pixels

  colorAverage() {
  }

  void setup() {
    int detail = 50; // Specifies the detail used in the colour_average. Around ~100 usually works but will depend on final size of installation
    for (int i=0; i<width; i+=detail) {
      for (int j=0; j<height; j+=detail) {
        newImg = bgi.get(i, j, detail, detail);
        fill(extractColorFromImage(newImg));
        rect(i, j, detail, detail);
      }
    }
    colorsComp = loadCompPalette(); // Generates and loads the complementary palette into colorsComp
    colorsAnal = loadAnalPalette(); // Generates and loads the analogous palette into colorsAnal
  }

  color[] loadAnalPalette() {
    colorsAnal = colorHarmony.Analogous(hexValue);
    return colorsAnal;
  }
  color[] loadCompPalette() {
    colorsComp = colorHarmony.Complementary(hexValue);
    return colorsComp;
  }
}
