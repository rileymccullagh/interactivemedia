package ptz_music;

import processing.core.PApplet;
import ptz.Vortex;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Instrument;

public class AcidGenerator implements Instrument{
	PApplet parent;
	public DrumMachine drumMachine;
//	public BassSynth bassSynth;
	
	public AudioOutput output;
	Minim minim;

	int tempo = 128;
	int currentStep = 0;
	
	public AcidGenerator(PApplet parent) {
		this.parent = parent;
		
		minim = new Minim(parent);
		output = minim.getLineOut();
		output.setTempo(tempo);

		
		drumMachine = new DrumMachine(parent);
		
//		bassSynth = new BassSynth(parent);
		
		
		//drumMachine.output.patch(output);
	}
	
	@Override
	public void noteOn(float arg0) {
		drumMachine.noteOn(currentStep);
	}

	@Override
	public void noteOff() {
		currentStep++;
		if (currentStep >= 16) {
			currentStep = 0;
		}
		output.playNote(0, 0.25f, this);
	}
	
	public void update() {
		drumMachine.updateFFT();
	}
	
	public void willMoveFromActive(int transitionTime) {
		drumMachine.output.shiftGain(0, -80, transitionTime);
//		bassSynth.output.shiftGain(0, -80, transitionTime);
	}
	


	
}
