package ptz;

import com.cage.colorharmony.ColorHarmony;

import processing.core.*;
import ptz_camera.Feed;

public class colorAverage {

	PApplet parent;
	Feed feed;
	PImage default_image;
	ColorHarmony colorHarmony;

	int[] colorsComp = new int[8];
	int[] colorsAnal = new int[8];
	String hexValue;

	public colorAverage(PApplet parent, Feed feed, PImage default_image) {
		this.parent = parent;
		this.default_image = default_image;
		this.feed = feed;
		colorHarmony = new ColorHarmony(parent);

	}

	public String getAverageColor(PImage img) {
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
		return hexValue;
	}

	public int[] loadAnal(String hexValue) {
		colorsAnal = colorHarmony.Analogous(hexValue);
		return colorsAnal;
	}

	public int[] loadComp(String hexValue) {
		colorsComp = colorHarmony.Complementary(hexValue);
		return colorsComp;
	}
}
