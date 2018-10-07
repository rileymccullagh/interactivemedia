package ptz;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PGraphics;


public class PanTiltZoom extends PApplet {
	boolean fullscreen = false;
	PFont titlefont;
	PGraphics red, green, blue;

	boolean wait = true;
	
	final int millisActive     = 30000;
	final int millisIdle       = 100;
	final int millisTransition = 100;

	Idle idle;
	Active active;

	State state = State.INIT;
	
	boolean activeHasBeenReinitialised = false;
	int timeAtTransition = 0;

	public static void main(String[] args) {
		PApplet.main("ptz.PanTiltZoom");
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
			size(640, 480, P3D);
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
		red = createGraphics( width, height, P2D);  
		green = createGraphics( width, height, P2D);  
		blue = createGraphics( width, height, P2D);
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
		fill(131, 245, 45);
		text("Pan\nTilt\nZoom", width-10, height/2); 
		
		// color aberration
		PImage clean = get();  
	    background(0);

	    red.beginDraw();
		red.tint(255, 0, 0);
		red.image(clean, 0, 0);
		red.endDraw();
		
		green.beginDraw();
		green.tint(0, 255, 0);
		green.image(clean, 0, 0);
		green.endDraw();
		
		blue.beginDraw();
		blue.tint(0, 0, 255);
		blue.image(clean, 0, 0);
		blue.endDraw();
		
		int redRandom = -(int)random(1, 4);
		int blueRandom = (int)random(1, 4);

		image( red.get(), redRandom, redRandom);
		blend( green.get(), 0, 0, width, height, 0, 0, width, height, ADD);
		blend( blue.get(), blueRandom, blueRandom, width, height, 0, 0, width, height, ADD);
		if ( random(1) < .1 ) { 
		    filter(BLUR, (int)random(2));
		}
	}
	
}

