package ptz;

import java.awt.Color;

import com.cage.colorharmony.ColorHarmony;

import processing.core.*;

class colorAverage { // This class is possible due to the colorharmony library and posts on this
						// thread
						// https://forum.processing.org/two/discussion/15573/get-the-average-rgb-from-pixels

	PApplet parent;

	ColorHarmony colorHarmony = new ColorHarmony(parent);

	int[] colorsComp = new int[8];
	int[] colorsAnal = new int[8];
	String hexValue;

	colorAverage(PApplet parent) {
		this.parent = parent;
		int detail = 50;

	}

	int getAverageColor(PImage img) {
		img.loadPixels();
		int r = 0, g = 0, b = 0;
		for (int i = 0; i < img.pixels.length; i++) {
			int c = img.pixels[i];
			r += c >> 16 & 0xFF;
			g += c >> 8 & 0xFF;
			b += c & 0xFF;
		}
		r /= img.pixels.length;
		g /= img.pixels.length;
		b /= img.pixels.length;

		System.out.println(r + "," + g + "," + b);
		hexValue = colorHarmony.P52Hex(parent.color(r, g, b));
		colorsAnal = colorHarmony.Analogous(hexValue);
		colorsComp = colorHarmony.Complementary(hexValue);
		return parent.color(r, g, b);
		

	}
}