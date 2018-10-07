package ptz_music;

import processing.core.PApplet;
import ptz.FFT;

public class AcidGenerator {
	PApplet parent;
	FFT fft;
	DrumMachine drumMachine;

	int tempo = 128;
	int lastTrigger = 0;
	
	public AcidGenerator(PApplet parent, FFT fft) {
		this.parent = parent;
		this.fft = fft;
		
		drumMachine = new DrumMachine(parent);
	}
	
	public void update() {
		if(parent.millis() - lastTrigger >= (60000/(tempo*4))) {
			drumMachine.trigger();
			System.out.println("triggering");
			lastTrigger = parent.millis();
		}
	}
}
