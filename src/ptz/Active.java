package ptz;

import processing.core.PApplet;

import ptz_music.*;
import ptz_histogram.*;

class Active {
	PApplet parent;
	Engine_Ball_Bar histogram;
	AcidGenerator acidGenerator;
	
	Active(PApplet parent) {
		this.parent = parent;
		acidGenerator = new AcidGenerator(parent);		
		histogram = new Engine_Ball_Bar(parent.width, parent.height, acidGenerator.drumMachine.bands, parent);
		
		acidGenerator.output.playNote(0, 0.25f, acidGenerator);		
	}

	void draw() {
		parent.clear();
		acidGenerator.update();
		parent.image(histogram.draw(acidGenerator.drumMachine.spectrum[0]), 0, 0);	
	}
	
	void willMoveFromActive(int transitionTime) {
		acidGenerator.willMoveFromActive(transitionTime);
	}
}
