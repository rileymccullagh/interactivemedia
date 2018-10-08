//package ptz;
//
//import java.awt.Color;
//
//import processing.core.*;
//
//class colorAverage { // This class is possible due to the colorharmony library and posts on this thread https://forum.processing.org/two/discussion/15573/get-the-average-rgb-from-pixels
//
//	PApplet parent; 
//	
//	
//  colorAverage(PApplet p) {
//	  this.parent = p;
//  }
//
//  void setup() {
//    int detail = 50; // Specifies the detail used in the colour_average. Around ~100 usually works but will depend on final size of installation
//    for (int i=0; i<parent.width; i+=detail) {
//      for (int j=0; j<parent.height; j+=detail) {
//    	PApplet bgi;
//		Object newImg = bgi.get(i, j, detail, detail);
//        fill(extractColorFromImage(parent.newImg));
//        parent.rect(i, j, detail, detail);
//      }
//    }
//    // parent.colorsComp = parent.loadCompPalette(); // Generates and loads the complementary palette into colorsComp
//    // parent.colorsAnal = parent.loadAnalPalette(); // Generates and loads the analogous palette into colorsAnal
//  }
//
//  	// Color[] loadAnalPalette() {
//    // colorsAnal = colorHarmony.Analogous(hexValue);
//    // return colorsAnal;
// //  }
// // Color[] loadCompPalette() {
//   // colorsComp = colorHarmony.Complementary(hexValue);
//   // return colorsComp;
//  // }
//}
