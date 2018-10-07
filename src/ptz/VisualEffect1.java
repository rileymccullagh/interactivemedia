package ptz;

import ddf.minim.*;
import drummachine.DrumMachine;
import drummachine.Voice;
import processing.core.*;


public class VisualEffect1 extends PApplet {
    String melody;

    int n = 0;
    public void visualEffect1 (String melody) {
    	Voice[] voices = new Voice[11];
    	DrumMachine a = new DrumMachine(null);
        Minim minim = new Minim(this);
//        if(trigger() == 0) {
//        	melody = 
//        }
        AudioPlayer mySound = minim.loadFile(melody);
        mySound.play();

        translate(width/2,height/2);
        for(int i = 0; i < mySound.bufferSize() - 1; i++)  {
            rotateX((float) (n*-PI/6*0.05));
            rotateY((float) (n*-PI/6*0.05));
            rotateZ((float) (n*-PI/6*0.05));
            fill(random(255),random(255),random(255));
            rect(i,i,mySound.left.get(i)*50,mySound.left.get(i)*5000);
        }
        n++;
    }


}

