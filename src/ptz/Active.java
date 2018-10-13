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
		acidGenerator.output.playNote(0, 0.25f, acidGenerator);		

		String text = "Hello World";
	
		Engine_Ball_Bar_Builder builder = new Engine_Ball_Bar_Builder();
		builder.ball_color = new int[] {255,0,0};
		builder.bar_color = new int[][] {new int[] {0,255,0}};
		builder.text_color = new int[] {0,0,255};
		builder.num_of_balls = text.length();
		builder.num_of_bars = acidGenerator.drumMachine.bands;
		builder.text = text;
	
		this.histogram = builder.build(parent.width, parent.height, parent); //new Engine_Ball_Bar(parent.width, parent.height, acidGenerator.drumMachine.bands, parent);
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
