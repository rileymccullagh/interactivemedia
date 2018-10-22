package ptz_music;

import processing.core.PApplet;
import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.ugens.Instrument;

public class AcidGenerator implements Instrument {
	PApplet parent;
	public DrumMachine drumMachine;
	public BassSynth bassSynth;
	
	public AudioOutput output;
	Minim minim;

	int tempo = 120;
	int currentStep = 0;
	
	public AcidGenerator(PApplet parent, String ...words) {
		this.parent = parent;
		
		minim = new Minim(parent);
		output = minim.getLineOut();
		output.setTempo(tempo);
		
		for(int i = 0; i < words.length; i++) {
			System.out.println("Word is: " + words[i]);
		}
		
		if(words.length > 0) {
			bassSynth = new BassSynth(parent, wordToBassSequence(words[0]));
		} else {
			bassSynth = new BassSynth(parent, new int[16]);
		}
		
		int numberOfSequences = words.length-1;
		if(numberOfSequences < 0) numberOfSequences = 0;
		
		boolean[][] drumSequence = new boolean[numberOfSequences][16];
		if (numberOfSequences != 0) {
			for(int i = 0; i < words.length; i++) {
				drumSequence[i] = wordToDrumSequence(words[i+1]);
			}
		}
		drumMachine = new DrumMachine(parent, drumSequence);
		
		
		
//		drumMachine.output.patch(output);
	}
	
	@Override
	public void noteOn(float arg0) {
			drumMachine.noteOn(currentStep);
			bassSynth.noteOn(currentStep);


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
		bassSynth.output.shiftGain(0, -80, transitionTime);
	}
	
	public boolean[] wordToDrumSequence(String word) {
		boolean sequence[] = new boolean[16];
		
		for(int i = 0; i < 16; i++) {
			char temp = word.charAt(i);
			if(temp == 'a' || temp == 'e' || temp == 'i' || temp == 'o' || temp == 'u') {
				sequence[i] = true;
			}
			if(temp == 'A' || temp == 'E' || temp == 'I' || temp == 'O' || temp == 'U') {
				sequence[i] = true;
				i += 3;
			}
		}
		
		return sequence;
	}
	
	public int[] wordToBassSequence(String word) {
		int[] sequence = new int[16];
		
		for(int i = 0; i < 16 && i < word.length(); i++) {
			char temp = word.charAt(i);
			if(temp >= 'a' && temp <= 'z') { //97~122
				int num = (int)temp;
				num -= 97; // get rid of unicode offset
				num += 48; // add midi offset to c3
				sequence[i] = num;
			}
			if(temp >= 'A' && temp <= 'Z') { //65~90
				int num = (int)temp;
				num -= 65; // get rid of unicode offset
				num += 48; // add midi offset to c3
				sequence[i] = num;
				i += 3;
			}
		}
		
		return sequence;
	}
}
