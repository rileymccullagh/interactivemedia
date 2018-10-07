package ptz_music;

import processing.core.PApplet;
import ptz.FFT;

public class AcidGenerator {
	PApplet parent;
	FFT fft;
	DrumMachine drumMachine;
	Vortex vortex;

	int tempo = 128;
	int lastTrigger = 0;
	
	public AcidGenerator(PApplet parent, FFT fft) {
		this.parent = parent;
		this.fft = fft;
		
		drumMachine = new DrumMachine(parent);
		vortex = new Vortex(parent, drumMachine.output);
	}
	
	public void update() {
		if(parent.millis() - lastTrigger >= (60000/(tempo*4))) {
			drumMachine.trigger();
			lastTrigger = parent.millis();
		}
		vortex.draw();
	}
}
