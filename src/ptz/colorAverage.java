package ptz;

import com.cage.colorharmony.ColorHarmony;

import processing.core.*;

public class colorAverage {

	PApplet parent;

	ColorHarmony colorHarmony;

	int[] colorsComp = new int[8];
	int[] colorsAnal = new int[8];
	String hexValue;

	colorAverage(PApplet parent) {
		this.parent = parent;
		colorHarmony = new ColorHarmony(parent);
		

	}

	String getAverageColor(PImage img) {
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

		hexValue = colorHarmony.P52Hex(parent.color(r, g, b));
		// colorsAnal = colorHarmony.Analogous(hexValue);
	    // 	colorsComp = colorHarmony.Complementary(hexValue);
		System.out.println("hexValue is " +hexValue);
		return hexValue;
	}

	int [] loadAnal(String hexValue) {
		colorsAnal = colorHarmony.Analogous(hexValue);
		System.out.println("colorAnal is " + colorsAnal[(int)parent.random(8)]);
		return colorsAnal;
	}
	int [] loadComp(String hexValue) {
		colorsComp = colorHarmony.Complementary(hexValue);
		System.out.println("colorsComp is " + colorsComp[(int)parent.random(8)]);
		return colorsComp;
	}
}
