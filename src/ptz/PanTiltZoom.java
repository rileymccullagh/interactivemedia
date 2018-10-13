package ptz;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PGraphics;

import processing.opengl.PShader;
import ptz_camera.Word;



public class PanTiltZoom extends PApplet {
	boolean fullscreen = false;
	PFont titlefont;
	PGraphics green, glow, noise;
	boolean greenHasBeenBlurred = false;

	boolean wait = false;
	
	final int millisActive     = 30000;
	final int millisIdle       = 1000;
	final int millisTransition = 1000;

	Idle idle;
	Active active;

	State state = State.INIT;
	
	boolean activeHasBeenReinitialised = false;
	int timeAtTransition = 0;

	public static void main(String[] args) {
		PApplet.main("ptz.PanTiltZoom");
		Word w = new Word();
		String[] out = w.frequencyAnalysis("https://en.wikipedia.org/wiki/Mount_Laurel,_New_Jersey", 10);
		System.out.println("Data");
	}
	
	@Override
	public void keyPressed() { 
		  wait=false; 
	} 

	@Override
	public void settings(){
		if(fullscreen) {
			fullScreen(P3D);
		} else {
			size(320, 240, P3D);
		}
		smooth();
	}

	@Override
	public void setup(){
		frame.setBackground(new java.awt.Color(0, 0, 0));
		idle = new Idle(this);
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		titlefont = createFont("VT323-Regular.ttf", (int)height/8);
		frameRate(60);
		idle.draw(); //initial fade in doesn't work without this??
		background(0);
		green = createGraphics(width, height, P2D);  
		glow = createGraphics(width, height, P2D);
		noise = createGraphics(width/2, height/2, P2D);
	}

	@Override
	public void draw(){
		if (wait) {
			drawTitle();
			return;
		}
		int fadeTime = millis() - timeAtTransition;
		switch(state) {
			case INIT:
				drawTitle();
				this.transition(false);
				break;
			case ACTIVE:
				// draw the active object
				active.draw();
				
				// check if we have elapsed the active time frame
				if(millis() > timeAtTransition + millisActive) {
					this.transition(false);
				}
				break;
	
			case IDLE:
				// draw the active object
				idle.draw();
	
				// check if we have elapsed the idle time frame
				if(millis() > timeAtTransition + millisIdle) {
					this.transition(true);
				}
				break;
			
			case INIT_TO_IDLE:
				if(fadeTime < millisTransition/2) {
					drawTitle();
				} else {
					idle.draw();
				}
				fade();
				break;
	
			case ACTIVE_TO_IDLE:
				if(fadeTime < millisTransition/2) {
					active.draw();
				} else {
					idle.draw();
				}
				fade();
				break;
	
			case IDLE_TO_ACTIVE:
				if(fadeTime < millisTransition/2) {
					idle.draw();
				} else {
					active.draw();
				}
				fade();
				break;
		}
	}
	
	void fade() {
		// get how far through the fade we are
		int fadeTime = millis() - timeAtTransition;

		// check if we are in a state of transition
		if(fadeTime < millisTransition) {
			//calculate how much the fade should be.
			float fadeAmount = 0;
			//if we are in the first half of the transition, fade to black
			if (fadeTime < millisTransition/2) {
				fadeAmount = map(fadeTime, 0, millisTransition/2, 0, 255);
			} else { //otherwise, fade from black
				fadeTime = fadeTime - millisTransition/2;
				fadeAmount = map(fadeTime, 0, millisTransition/2, 255, 0);
			}
			// make the correct fades
			fill(0, fadeAmount);
			rect(0, 0, width, height);
			
		//check if we need to move from a transition state to a steady state
		} else if (state != State.ACTIVE && state != State.IDLE) {
			//change to the correct steady state
			switch(state) {
				case IDLE_TO_ACTIVE:
					state = State.ACTIVE;
					break;
				default:
					state = State.IDLE;
					break;
			}
		}
	}
	
	void transition(boolean toActive) {
		if (toActive) {
			state = State.IDLE_TO_ACTIVE;
			timeAtTransition = millis();
			active = new Active(this);
		} else {
			if(state == State.INIT) {
				state = State.INIT_TO_IDLE;
			} else {
				state = State.ACTIVE_TO_IDLE;
				active.willMoveFromActive(millisTransition/2);
			}
			activeHasBeenReinitialised = false;
			timeAtTransition = millis();
		}
	}
	
	void drawTitle() {
		
		// print title
		background(0);
		textFont(titlefont);
		textAlign(RIGHT);
		textSize((int)height/8);
		fill(255);
		text("Pan\nTilt\nZoom", width-10, height/2); 
		
		// color aberration
		PImage clean = get();  
	    background(0);
		
	    // needs to be done like this because filter(BLUR) is hideously slow	
	    if(!greenHasBeenBlurred) {
			green.beginDraw();
			green.tint(0, 255, 0);
			green.image(clean, 0, 0);
			green.filter(BLUR, 10);
			green.endDraw();
			greenHasBeenBlurred = true;
	    }

		noise.beginDraw();
		noise.background(255);
		noise.loadPixels();
		for (int x=0; x<noise.pixels.length; x++)
		{
			noise.pixels[x] = color( random(15) );
		}
		noise.updatePixels();
		noise.endDraw();

		int offset = (int)random(0, 10);
		if(offset == 9) {
			offset = -1;
		} else if (offset == 1){
			offset = 1;
		} else {
			offset = 0;
		}
		
		noSmooth();
		image(clean.get(), offset, 0);
		blend(noise.get(), 0, 0, noise.width, noise.height, 0, 0, width, height, ADD);
		blend(green.get(), 0, 0, width, height, offset, 0, width+offset, height, ADD);
		smooth();
	}
}

