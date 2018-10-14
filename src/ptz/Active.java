package ptz;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import ptz_camera.Camera;
import ptz_camera.Feed;
import ptz_music.*;
import ptz_histogram.*;

class Active {
	PApplet parent;

	Engine_Ball_Bar histogram;
	AcidGenerator acidGenerator;
	TextureSphere sphere;
	TextureCube tc;
	
	Active(PApplet parent, Feed feed) {
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
	
		set_Sphere_Feed(feed);
		this.histogram = builder.build(parent.width, parent.height, parent);
		
		tc = new TextureCube(this.parent);
	}
	
	void set_Sphere_Feed (Feed feed) {
		sphere.setFeed(feed);
	}

	void draw(Feed feed) {
		parent.clear();
		parent.background(255);
		parent.fill(255);
		parent.noStroke();
		acidGenerator.update();
		

		PImage histogram_img = histogram.draw(acidGenerator.drumMachine.spectrum);
		
		
		List<Feed> feeds = new ArrayList<Feed>();
		for (int i = 0; i < 6; i++) {
			feeds.add(feed);
		}
		tc.draw(feeds);
		//parent.image(histogram_img, 0, 0);	
		//sphere.draw();
	}
}
