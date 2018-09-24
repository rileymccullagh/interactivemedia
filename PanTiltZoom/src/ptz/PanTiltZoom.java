package ptz;

import processing.core.PApplet;

public class PanTiltZoom extends PApplet {
	final int millisActive     = 5000;
	final int millisIdle       = 5000;
	final int millisTransition = 5000;

	Idle idle;
	Active active;

	State state = State.IDLE;
	boolean activeHasBeenReinstantiated = false;
	int timeAtTransition = 0;

	public static void main(String[] args) {
		PApplet.main("ptz.PanTiltZoom");
	}
	
    public void settings(){
    	size(1000,600,P3D);
    }

    public void setup(){
    	active = new Active(this);
        idle = new Idle(this);
        
    }

    public void draw(){
    	switch(state) {
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
	          
	          // check if we have elapsed the active timeframe
	          if(millis() > timeAtTransition + millisIdle) {
	            this.transition(true);
	          }
	          break;
	          
	        case ACTIVE_TO_IDLE:
	          // transition from active to idle
	          //print("active to idle");
	          break;
	          
	        case IDLE_TO_ACTIVE:
	          // transition from idle to active
	          //print("idle to active");
	          break;
    	}
    }
    
    void transition(boolean toActive) {
        if (toActive) {
          //print("idle to active");
          state = State.ACTIVE;
          timeAtTransition = millis();
        } else {
          //print("active to idle");
          state = State.IDLE;
          activeHasBeenReinstantiated = false;
          timeAtTransition = millis();
        }
      }
}
