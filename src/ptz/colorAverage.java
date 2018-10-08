package ptz;

import java.awt.Color;

import com.cage.colorharmony.ColorHarmony;

import processing.core.*;

class colorAverage { // This class is possible due to the colorharmony library and posts on this
						// thread
						// https://forum.processing.org/two/discussion/15573/get-the-average-rgb-from-pixels

	PApplet parent;
	
	ColorHarmony colorHarmony = new ColorHarmony(parent);
	
	Color[] colorsComp = new Color[8];
	Color[] colorsAnal = new Color[8];
	
	String hexValue;

	colorAverage(PApplet p) {
		this.parent = p;
	}

	void setup() {
		int detail = 50; // Specifies the detail used in the colour_average. Around ~100 usually works
							// but will depend on final size of installation
		for (int i = 0; i < parent.width; i += detail) {
			for (int j = 0; j < parent.height; j += detail) {
				PImage bgi;
				PImage newImg = bgi.get(i, j, detail, detail);
				parent.fill(extractColorFromImage(newImg));
				parent.rect(i, j, detail, detail);
			}
		}
		colorsComp = loadCompPalette(); // Generates and loads the complementary palette into colorsComp
		colorsAnal = loadAnalPalette(); // Generates and loads the analogous palette into colorsAnal
	}

	Color[] loadAnalPalette() {
		colorsAnal = colorHarmony.Analogous(hexValue);
		return colorsAnal;
	}

	Color[] loadCompPalette() {
		colorsComp = colorHarmony.Complementary(hexValue);
		return colorsComp;
	}
	
	
	public int extractColorFromImage(PImage newImg) { // I'm not going to pretend that I fully understand this.
		
		  newImg.loadPixels();
		  byte r = 0, g = 0, b = 0;

		  for (int c : newImg.pixels) {
		    r += c >> 020 & 0xFF;
		    g += c >> 010 & 0xFF;
		    b += c        & 0xFF;
		  }

		  r /= newImg.pixels.length;
		  g /= newImg.pixels.length;
		  b /= newImg.pixels.length;
		  System.out.println( r + "," + g + "," + b + " ");
		  hexValue = colorHarmony.P52Hex(parent.color(r, g, b));
		  return parent.color(r, g, b);
		}
}