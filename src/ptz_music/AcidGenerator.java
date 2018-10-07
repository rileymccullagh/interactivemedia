package ptz_music;

import processing.core.PApplet;

public class AcidGenerator {
	PApplet parent;
	public DrumMachine drumMachine;
	public BassSynth bassSynth;
	Vortex vortex;

	int tempo = 128;
	int lastTrigger = 0;
	
	public AcidGenerator(PApplet parent) {
		this.parent = parent;
		
		drumMachine = new DrumMachine(parent);
		drumMachine.output.setTempo(tempo);
		drumMachine.output.playNote(0, 0.25f, drumMachine);
		
		bassSynth = new BassSynth(parent);
		bassSynth.output.setTempo(tempo);
		bassSynth.output.playNote(0, 0.25f, bassSynth);
		
		vortex = new Vortex(parent, drumMachine.output);
		
	}
	
	public void update() {
		drumMachine.updateFFT();
		vortex.draw();
	}
	
	public void willMoveFromActive(int transitionTime) {
		drumMachine.output.shiftGain(0, -80, transitionTime);
		bassSynth.output.shiftGain(0, -80, transitionTime);
	}
}
