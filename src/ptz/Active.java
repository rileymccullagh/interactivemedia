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
		this.acidGenerator = new AcidGenerator(parent);
		this.histogram = new Engine_Ball_Bar(parent.width, parent.height, acidGenerator.drumMachine.bands, parent);

	}

	void draw() {
		parent.clear();
		acidGenerator.update();
		parent.image(histogram.draw(acidGenerator.drumMachine.spectrum), 0, 0);	
	}
	
	void willMoveFromActive(int transitionTime) {
		acidGenerator.willMoveFromActive(transitionTime);
	}
}
