package ptz_music;

import processing.core.PApplet;

import ddf.minim.*;
import ddf.minim.ugens.*;
import ddf.minim.analysis.*;
import javax.sound.midi.*;


public class BassSynth  {
	PApplet parent;
	
	Synthesizer synth;
	MidiChannel[] channels;	
	
	int currentStep = 0;
	Minim minim;
	AudioOutput output;
	
	int[] pattern = new int[16];
	
	FFT fft;
	public int bands = 8;
	public float[] spectrum = new float[bands];

	
	public BassSynth(PApplet parent) {
		this.parent = parent;
		
		minim = new Minim(parent);
		output = minim.getLineOut();
				
		fft = new FFT(output.bufferSize(), output.sampleRate());
		try {
		    synth = MidiSystem.getSynthesizer();
		    synth.open();
		    Soundbank sounds = MidiSystem.getSoundbank( parent.createInput("303.sf2") );
		    synth.loadAllInstruments( sounds );
		    channels = synth.getChannels();
		} catch( MidiUnavailableException ex )
		{
	        System.out.println("No default synthesizer found.");
	    }
	    catch( Exception ex )
	    {
		    System.out.println(ex.toString());
	    }
	}

	public void noteOn(float arg0) {
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i]>0) {
				//trigger ya synth here
			}
		}
	}
}