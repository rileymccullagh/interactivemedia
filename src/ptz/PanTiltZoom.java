package ptz;

import java.io.FileDescriptor;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PGraphics;

import processing.opengl.PShader;
import ptz_camera.Camera;
import ptz_camera.Feed;
import ptz_camera.Word;
import processing.*;



public class PanTiltZoom extends PApplet {
	boolean fullscreen = false;
	PFont titlefont;
	PGraphics green, glow, noise;
	boolean greenHasBeenBlurred = false;
	
	Camera cam;
	List<Feed> feeds = Feed.get_all_feeds();
	boolean wait = true;
	boolean loading = true;
	String title_subtext = "loading";
	

	final int millisActive     = 90000;
	final int millisIdle       = 10000000; // 10000;
	final int millisTransition = 5000;
	
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
			size(480, 360, P3D);
			
		}
		smooth();
	}

	@Override
	public void setup(){
		System.out.println("starting setup");
		frame.setBackground(new java.awt.Color(0, 0, 0));
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		titlefont = createFont("VT323-Regular.ttf", (int)height/8);
		frameRate(60);
		
		green = createGraphics(width, height, P2D);  
		glow = createGraphics(width, height, P2D);
		noise = createGraphics(width/2, height/2, P2D);
		
		System.out.println("Finished Setup");
	}
	int loading_counter = 2;
	
	
	//If setup() takes 5 seconds, it crashes, so we will run it in draw.
	void setup_longer () {
		
		System.out.println("Beginning initialisation");
		PApplet p = this;
		new Thread (new Runnable() {
			@Override
			public void run() {
				final int minimum_number_of_feeds = 6;
				
				Feed.get_minimum_feeds(6, p, 6, 3);
				if (Feed.valid_feeds_count() < 6) {
					System.out.println("Couldn't retrieve 6 feeds! Let's copy");
					if (Feed.valid_feeds_count() == 0) {
						System.out.println("We did no retrievals, we're fucked");
					} else {
						for (int i = 0; i < minimum_number_of_feeds - Feed.valid_feeds_count(); i++) {
							feeds.add(Feed.get_feed(i));
						}
					}
				}
			}
		}).start();
	}
	
	
	@Override
	public void draw(){
		if (loading_counter == 2) {
			loading_counter--;
			return;
		}
		if (loading_counter == 1) 
		{
			loading_counter--;
			setup_longer(); //Can be soon replaced
			return;
		}
		
		if (loading) {
			
			title_subtext = "loaded: " + (int)(((float)Feed.valid_feeds_count()/6)*100) + "%";
			if (Feed.valid_feeds_count() == 6) {
				loading = false;
				drawTitle(); // this will show 6 briefly while idle is instantiated
				//Always retrieve the first 6, because we know they are reliable
				idle = new Idle(
						this, 
						feeds.subList(0, 6),  
						feeds.get(0).getNextImage(this).get());
				title_subtext = "ready";
				greenHasBeenBlurred = false;
			}
			drawTitle();
			return;
		}
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
				active.draw();
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
			active = new Active(this,Feed.get_feed((int)random(6)));
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
		textSize((int)height/12);
		textAlign(CENTER,BOTTOM);
		text(title_subtext, width / 2, height);
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

		int offset = (int)random(0, 50);
		if(offset == 1) {
			offset = -1;
		} else if (offset == 2){
			offset = 1;
		} else {
			offset = 0;
		}
		
		image(clean.get(), offset, 0);
		blend(noise.get(), 0, 0, noise.width, noise.height, 0, 0, width, height, ADD);

		blend(green.get(), 0, 0, width, height, offset, offset, width+offset, height, ADD);

	}

	
}
