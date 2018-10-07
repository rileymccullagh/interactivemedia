package ptz;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import processing.core.PApplet;

public class PanTiltZoom extends PApplet {
	final int millisActive     = 30000;
	final int millisIdle       = 30000;
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
	public void settings(){
		//fullScreen(P3D);
		size(640, 480);
	}

	@Override
	public void setup(){
		active = new Active(this);
		idle = new Idle(this);
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		frameRate(60);
		background(0);
	}

	@Override
	public void draw(){
		switch(state) {
			case INIT:
				this.transition(false);
				break;
			case ACTIVE:
				// draw the active object
				active.draw();
				
				// check if we have elapsed the active timeframe
				if(millis() > timeAtTransition + millisActive) {
					this.transition(false);
				}
				break;
	
			case IDLE:
				// draw the active object
				idle.draw();
	
				// check if we have elapsed the idle timeframe
				if(millis() > timeAtTransition + millisIdle) {
					this.transition(true);
				}
				break;
			
			case INIT_TO_IDLE:
				fade();
				break;
	
			case ACTIVE_TO_IDLE:
				fade();
				break;
	
			case IDLE_TO_ACTIVE:
				fade();
				break;
		}
	}
	
	void fade() {
		// check if we are in a state of transition
		if((millis() - timeAtTransition) < millisTransition) {
			// get how far through the fade we are
			int fadeTime = millis() - timeAtTransition;
			//calculate how much the fade should be.
			float fadeAmount = 0;
			//if we are in the first half of the transition, fade to black
			if (fadeTime/millisTransition<0.5) {
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
				case INIT_TO_IDLE:
					state = State.IDLE;
					break;
		
				case ACTIVE_TO_IDLE:
					state = State.IDLE;
					break;
		
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
}

