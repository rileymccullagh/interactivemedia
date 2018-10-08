package ptz_music;

import processing.core.PApplet;

public class AcidGenerator {
	PApplet parent;
	public DrumMachine drumMachine;
	Vortex vortex;

	int tempo = 128;
	int lastTrigger = 0;
	
	public AcidGenerator(PApplet parent) {
		this.parent = parent;
		
		drumMachine = new DrumMachine(parent);
		vortex = new Vortex(parent, drumMachine.output);
	}
	
	public void update() {
		if(parent.millis() - lastTrigger >= (60000/(tempo*4))) {
			drumMachine.trigger();
			lastTrigger = parent.millis();
		}
//		vortex.draw();
	}
}
