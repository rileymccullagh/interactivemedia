package ptz_music;

import ddf.minim.AudioOutput;
import processing.core.PApplet;

public class IdleMusic {
	
	PApplet parent;
	int[] numbers;
	public AudioOutput output;
	
	public IdleMusic(PApplet parent, int[] numbers) {
		this.parent = parent;
		this.numbers = numbers;
	}
}
