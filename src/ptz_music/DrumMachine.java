package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;
import ddf.minim.ugens.*;
import ddf.minim.analysis.*;



public class DrumMachine {
	PApplet parent;
	Sampler[] voices = new Sampler[11];
	boolean[][] patterns = new boolean[11][16];
	int currentStep = 0;
	Minim minim;
	AudioOutput output;

	FFT fft;
	public int bands = 8;
	public float[] spectrum = new float[bands];


	public DrumMachine(PApplet parent) {
		minim = new Minim(parent);
		output = minim.getLineOut();

		String[] files = {"bd.wav", "sd.wav", "rs.wav", "cp.wav", "ht.wav", "mt.wav", "lt.wav", "ch.wav", "oh.wav", "rd.wav", "cr.wav"};

		this.parent = parent;
		for(int i = 0; i<11; i++) {
			voices[i] = new Sampler(files[i], 12, minim);
			voices[i].patch(output);
		}

		//four to the floor for the hell of it.
		patterns[0][0] = true;
		patterns[0][4] = true;
		patterns[0][8] = true;
		patterns[0][12] = true;

		patterns[8][2] = true;
		patterns[8][6] = true;
		patterns[8][10] = true;
		patterns[8][14] = true;
		
		fft = new FFT(output.bufferSize(), output.sampleRate());
		fft.window(FFT.BARTLETT);
	}

	public void trigger() {
		currentStep++;
		if(currentStep >= 16) {
			currentStep = 0;
		}
		for(int i = 0; i < patterns.length; i++) {
			if(patterns[i][currentStep]) {
				voices[i].trigger();
			}
		}
		fft.forward(output.mix);
		fft.linAverages(bands);
		
		for(int i = 0; i<bands; i++) {
			spectrum[i] = PApplet.map(fft.getBand(i), (float)0.0, (float)150.0, (float)0.0, (float)1.0);

		}
	}
}

