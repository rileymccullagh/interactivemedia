package ptz;

import processing.core.PApplet;
import processing.core.PImage;
import ptz_camera.Camera;
import ptz_music.*;
import ptz_histogram.*;

class Active {
	PApplet parent;

	Engine_Ball_Bar histogram;
	AcidGenerator acidGenerator;
	Camera cam;
	TextureSphere sphere;
	Active(PApplet parent, Camera cam) {
		this.parent = parent;
		this.acidGenerator = new AcidGenerator(parent);
		this.sphere = new TextureSphere(parent);
		String text = "Hello World";
	
		Engine_Ball_Bar_Builder builder = new Engine_Ball_Bar_Builder();
		builder.ball_color = new int[] {255,0,0};
		builder.bar_color = new int[][] {new int[] {0,255,0}};
		builder.text_color = new int[] {0,0,255};
		builder.num_of_balls = text.length();
		builder.num_of_bars = acidGenerator.drumMachine.bands;
		builder.text = text;
	
		this.cam = cam;
		this.histogram = builder.build(parent.width, parent.height, parent);
	}

	void draw() {
		parent.clear();
		parent.background(255);
		parent.fill(255);
		
		//parent.image(cam.get_sequence_of_images_by_index(1).get(0), 0, 0);
		sphere.draw();
		acidGenerator.update();
		
		sphere.setTexture(cam.get_sequence_of_images_by_index(1).get(0));
		//PImage histogram_img = histogram.draw(acidGenerator.drumMachine.spectrum);
		//parent.image(histogram_img, 0, 0);	
		sphere.draw();
	}
}
