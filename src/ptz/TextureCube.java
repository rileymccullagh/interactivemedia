package ptz;

import java.util.List;

import processing.core.*;
import ptz_camera.Feed;

class TextureCube {

	PApplet parent;

	TextureCube(PApplet p) {
		this.parent = p;
	}

	void draw(List<Feed> feeds) {
		parent.pushMatrix();
		parent.textureMode(PApplet.NORMAL);
		parent.noStroke();
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(parent.frameCount * PApplet.PI / 360);
		parent.rotateX(parent.frameCount * PApplet.PI / 720);
		parent.scale(PApplet.map((float)parent.height+(float)parent.width, (float)0,((float)parent.height+(float)parent.width)*2,(float)40,(float)100));

		parent.beginShape(PApplet.QUADS);
		parent.fill(0, 0, 0);
		parent.texture(feeds.get(0).getNextImage(parent, 3));

		// +Z "front" face
		parent.vertex(-1, -1, 1, 0, 0);
		parent.vertex(1, -1, 1, 1, 0);
		parent.vertex(1, 1, 1, 1, 1);
		parent.vertex(-1, 1, 1, 0, 1);

		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(feeds.get(1).getNextImage(parent, 3));
		// -Z "back" face
		parent.vertex(1, -1, -1, 0, 0);
		parent.vertex(-1, -1, -1, 1, 0);
		parent.vertex(-1, 1, -1, 1, 1);
		parent.vertex(1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);
		parent.texture(feeds.get(2).getNextImage(parent, 3));
		// +Y "bottom" face
		parent.vertex(-1, 1, 1, 0, 0);
		parent.vertex(1, 1, 1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(feeds.get(3).getNextImage(parent, 3));
		// -Y "top" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, -1, 1, 1, 1);
		parent.vertex(-1, -1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(feeds.get(4).getNextImage(parent, 3));
		// +X "right" face
		parent.vertex(1, -1, 1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(1, 1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(feeds.get(5).getNextImage(parent, 3));
		// -X "left" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(-1, -1, 1, 1, 0);
		parent.vertex(-1, 1, 1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);

		parent.endShape();

		parent.popMatrix();
	}
}