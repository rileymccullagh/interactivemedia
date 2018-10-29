package ptz_music;

import ddf.minim.AudioOutput;
import ddf.minim.Minim;
import ddf.minim.analysis.FFT;
import processing.core.PApplet;

public class AcidGenerator {
	PApplet parent;
	Minim minim;
	public AudioOutput output;
	
	BassSynth bassSynth;
	DrumMachine drumMachine;
	
	FFT fft;
	
	boolean[] kick;
	boolean[] ch;
	boolean[] oh;
	
	boolean active = false;
	
	public int bands = 8;
	public float[] spectrum = new float[bands];
	
	public AcidGenerator(PApplet parent, String ...words) {
		this.parent = parent;
		
		minim = new Minim(parent);
		output = minim.getLineOut();
		fft = new FFT(output.bufferSize(), output.sampleRate());
		drumMachine = new DrumMachine(parent, output);

		
		kick = new boolean[16];
		kick[0] = true;
		kick[4] = true;
		kick[8] = true;
		kick[12] = true;
		
		ch = new boolean[16];
		ch[0] = true;
		ch[4] = true;
		ch[8] = true;
		ch[12] = true;
		
		oh = new boolean[16];
		oh[2] = true;
		oh[6] = true;
		oh[10] = true;
		oh[14] = true;
	}
	
	public void update() {
		if (active) {
			bassSynth.update();
		}
		drumMachine.update();
			
		fft.forward(output.mix);
		fft.linAverages(bands);
		for (int i = 0; i < bands; i++) {
			spectrum[i] = PApplet.map(fft.getBand(i), 0.0f, 500.0f, 0.0f, 1.0f);
		}
	}
	
	public void active(String word) {
		active = true;
		int[] sequence = wordToBassSequence(word);
		bassSynth = new BassSynth(parent, sequence);
		drumMachine = new DrumMachine(parent, output, kick, new boolean[16], new boolean[16], new boolean[16], new boolean[16], new boolean[16], new boolean[16],ch, oh);
	}
	
	public void idle() {
		active = false;
		drumMachine = new DrumMachine(parent, output, new boolean[16], new boolean[16], new boolean[16], new boolean[16], new boolean[16], new boolean[16],new boolean[16], ch, oh);
	}
	
	public int[] wordToBassSequence(String word) {
		System.out.println("Making sequence from " + word);
		int[] sequence = new int[16];
		
		for(int i = 0; i < 16 && i < word.length(); i++) {
			char temp = word.charAt(i);
			if(temp >= 'a' && temp <= 'z') { //97~122
				int num = (int)temp;
				num -= 97; // get rid of unicode offset
				num += 38; // add midi offset to c3
				sequence[i] = num;
			}
			if(temp >= 'A' && temp <= 'Z') { //65~90
				int num = (int)temp;
				num -= 65; // get rid of unicode offset
				num += 38; // add midi offset to c3
				sequence[i] = num;
				i += 3;
			}
		}
		
		return sequence;
	}
}
