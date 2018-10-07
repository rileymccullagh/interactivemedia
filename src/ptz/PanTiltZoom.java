package ptz;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import processing.core.PApplet;
import processing.core.PFont;

public class PanTiltZoom extends PApplet {
	PFont titlefont;
	boolean wait = true;
	
	final int millisActive     = 30000;
	final int millisIdle       = 15000;
	final int millisTransition = 10000;

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
//		fullScreen(P3D);
		size(640, 480);
		smooth();
	}

	@Override
	public void setup(){
		frame.setBackground(new java.awt.Color(0, 0, 0));
		active = new Active(this);
		idle = new Idle(this);
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		titlefont = createFont("newwatch.ttf", (int)height/4);
		frameRate(60);
		idle.draw(); //initial fade in doesn't work without this??
		background(0);
		drawTitle();
	}

	@Override
	public void draw(){
		if (wait) return;
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
		background(0);
		textFont(titlefont);
		textAlign(RIGHT);
		textSize((int)height/4);
		fill(131, 245, 45);
		text("Pan\nTilt\nZoom", width-10, height/4); 
	}
	
}

