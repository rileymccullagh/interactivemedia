package ptz_music;

import processing.core.PApplet;

public class AcidGenerator {
	PApplet parent;
	
	BassSynth bassSynth;
	public DrumMachine drumMachine;
	
	boolean active = false;
	
	public AcidGenerator(PApplet parent, String ...words) {
		this.parent = parent;
		int[] midiSequence = {40, 40, 48, 48, 51, 36, 36, 38, 40, 40, 48, 48, 51, 36, 36, 38}; 

		bassSynth = new BassSynth(parent, midiSequence);
		
		boolean[] kick = new boolean[16];
		kick[0] = true;
		kick[4] = true;
		kick[8] = true;
		kick[12] = true;
		drumMachine = new DrumMachine(parent, kick);
	}
	
	public void update() {
		if (active) {
			bassSynth.update();
		}
	}
	
	public void active(String word) {
		active = true;
		int[] sequence = wordToBassSequence(word);
		bassSynth = new BassSynth(parent, sequence);
	}
	
	public void idle() {
		active = false;
	}
	
	public int[] wordToBassSequence(String word) {
		System.out.println("Making sequence from " + word);
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
