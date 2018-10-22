package ptz;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cage.colorharmony.ColorConvertor;
import com.cage.colorharmony.ColorHarmony;

import processing.core.PApplet;
import processing.core.PImage;
import ptz_camera.Camera;
import ptz_camera.Feed;
import ptz_music.*;
import ptz_histogram.*;




class Active {
	PApplet parent;

	List<Engine_Ball_Bar> histogram = new ArrayList<Engine_Ball_Bar>();
	AcidGenerator acidGenerator;
	TextureSphere sphere;
	colorAverage ca; 
	Prism skybox;
	PImage img;
	Feed feed;
	
	Active(PApplet parent, Feed feed) {
		this.feed = feed;
		img = feed.getNextImage(parent).get();
		this.parent = parent;

		this.sphere = new TextureSphere(parent, img, feed, 100);
		ca = new colorAverage(parent);
		
		ca.loadAnal(ca.getAverageColor(img));
		ca.loadComp(ca.getAverageColor(img));
				
		this.acidGenerator = new AcidGenerator(parent, feed.words_analysed);

		Engine_Ball_Bar_Builder builder = new Engine_Ball_Bar_Builder();
		builder.ball_color = new int[]{0,0,255};
		builder.bar_color = new int[][] {new int[] {255,0,0}, new int[] {0,255,0}};
		builder.text_color = new int[]{0,0,0};
		builder.text = feed.words_analysed[0];
		builder.num_of_balls = builder.text.length();
		builder.num_of_bars = acidGenerator.drumMachine.bands;
		
		//Set histogram up
		for (int i = 0; i < 4; i++) {
			histogram.add(builder.build(parent.width, parent.height, parent));
		}
		skybox = new Prism(0,0,0,800);	
		

	}
	
	
	
	void draw_outer_prism(List<PImage> images) {
		skybox.rotate();
		parent.pushMatrix();
		skybox.draw(images, images.get(0), images.get(0), parent);
		parent.popMatrix();
	}
	
	void draw() {
		parent.clear();
		parent.background(255);
		parent.fill(255);
		parent.noStroke();
		acidGenerator.update();
		
		feed.analyse(6);
		
		List<PImage> images = new ArrayList<PImage>();	
		for (Engine_Ball_Bar item : histogram) {
			images.add(item.draw(acidGenerator.drumMachine.spectrum[0]));
		}
		
		parent.pushMatrix();
		parent.translate(
				(float)(parent.width / 2.0f), 
				(float)(parent.height / 2.0f), 
				(float)((parent.height/2.0) / Math.tan(parent.PI * 30.0 / 180.0) )
		);
		int val = skybox.camera_max();
		parent.translate(0, 0, -val);
		parent.rotateX(parent.PI /2.0f);
		
		draw_outer_prism(images);
		sphere.draw();
		parent.translate(0, 0, -val);
		parent.popMatrix();
	}
	
	void willMoveFromActive(int transitionTime) {
		
	}
}
