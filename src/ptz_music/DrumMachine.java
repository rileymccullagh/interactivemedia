package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;
import ddf.minim.ugens.*;
import ddf.minim.analysis.*;

public class DrumMachine {
	PApplet parent;
	Sampler[] voices = new Sampler[11];
	boolean[][] patterns = new boolean[11][16];
	Minim minim;
	public AudioOutput output;
	Summer summer;
	FFT fft[] = new FFT[11];
	public int bands = 8;
	public float[][] spectrum = new float[11][bands];

	public DrumMachine(PApplet parent) {
		this.parent = parent;

		minim = new Minim(parent);
		output = minim.getLineOut();
		summer = new Summer();
		

		String[] files = { "bd.wav", "sd.wav", "rs.wav", "cp.wav", "ht.wav", "mt.wav", "lt.wav", "ch.wav", "oh.wav", "rd.wav", "cr.wav" };

		for (int i = 0; i < 11; i++) {
			fft[i] = new FFT(output.bufferSize(), output.sampleRate());
			voices[i] = new Sampler(files[i], 4, minim);
			voices[i].patch(summer);
		}
		
		summer.patch(output);
		

		// four to the floor for the hell of it.
		patterns[0][0] = true;
//		patterns[0][4] = true;
		patterns[0][8] = true;
//		patterns[0][12] = true;

//		patterns[1][0] = true;
		patterns[1][4] = true;
//		patterns[1][8] = true;
		patterns[1][12] = true;

		patterns[7][2] = true;
		patterns[7][6] = true;
		patterns[7][10] = true;
		patterns[7][14] = true;
	}

	public void noteOn(int step) {
		for (int i = 0; i < patterns.length; i++) {
			if (patterns[i][step]) {
				voices[i].trigger();
			}
		}
	}

	public void updateFFT() {
		for (int i = 0; i < 11; i++) {
			fft[i].forward(output.mix);
			fft[i].linAverages(bands);
	
			for (int j = 0; j < bands; j++) {
				spectrum[i][j] = PApplet.map(fft[i].getBand(j), 0.0f, 500.0f, 0.0f, 1.0f);
			}
		}
	}
	
	public void destroy() {
		minim.stop();
	}
}
