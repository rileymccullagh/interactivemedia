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
	TextureCube tc;
	colorAverage ca; 
	Prism skybox;
	PImage img;
	
	Active(PApplet parent, Feed feed) {
		img = parent.loadImage("http://96.78.107.22/cgi-bin/viewer/video.jpg");
		this.parent = parent;
		this.acidGenerator = new AcidGenerator(parent);
		this.sphere = new TextureSphere(parent, img);
		ca = new colorAverage(parent);
		
		//PImage img = feed.getNextImage(parent, 1).orElse(parent.createImage(50, 50, parent.ARGB));
		
		ca.loadAnal(ca.getAverageColor(img));
		ca.loadComp(ca.getAverageColor(img));
		
		feed.analyse(1);
		
		Engine_Ball_Bar_Builder builder = new Engine_Ball_Bar_Builder();
		builder.ball_color = ca.colorsComp;
		builder.bar_color = new int[][] {new int[] {0,255,0}/*ca.colorsAnal*/};
		builder.text_color = ca.colorsAnal;
		builder.text = feed.words_analysed[0];
		builder.num_of_balls = feed.words_analysed.length;
		builder.num_of_bars = acidGenerator.drumMachine.bands;
		
		set_Sphere_Feed(feed);
		
		for (int i = 0; i < 12; i++) {
			histogram.add(builder.build(parent.width, parent.height, parent));
		}
		
		skybox = new Prism(0,0,0,850);
		tc = new TextureCube(this.parent, img);
		System.out.println("Made output");
	}
	
	void set_Sphere_Feed (Feed feed) {
		sphere.setFeed(feed);
	}
	
	void draw_outer_prism() {
		
		skybox.rotate();
		parent.pushMatrix();
		
		parent.translate(
				(float)(parent.width / 2.0f), 
				(float)(parent.height / 2.0f), 
				(float)((parent.height/2.0) / Math.tan(parent.PI * 30.0 / 180.0) )
		);
		
		List<PImage> images = new ArrayList<PImage>();
		parent.rotateX(parent.PI /2.0f);
		for (Engine_Ball_Bar item : histogram) {
			images.add(item.draw(acidGenerator.drumMachine.spectrum));
		}
		
		skybox.draw(images, images.get(0), images.get(0), parent);
		parent.popMatrix();
	}
	
	void draw(Feed feed) {
		parent.clear();
		parent.background(255);
		parent.fill(255);
		parent.noStroke();
		acidGenerator.update();
		
		List<Feed> feeds = new ArrayList<Feed>();
		for (int i = 0; i < 6; i++) {
			feeds.add(feed);
		}
		sphere.setFeed(feeds.get(0));
		
				
		
		draw_outer_prism();
		tc.draw(feeds);
		sphere.draw();
	}
}
