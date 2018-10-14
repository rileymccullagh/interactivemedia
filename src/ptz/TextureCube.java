package ptz;

import java.util.List;

import processing.core.*;

class TextureCube {

	PApplet parent;

	TextureCube(PApplet p) {
		this.parent = p;
	}

	void draw(List<PImage> images) {
		parent.pushMatrix();
		parent.textureMode(PApplet.NORMAL);
		parent.noStroke();
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(parent.frameCount * PApplet.PI / 360);
		parent.rotateX(parent.frameCount * PApplet.PI / 720);
		parent.scale(PApplet.map((float)parent.height+(float)parent.width, (float)0,((float)parent.height+(float)parent.width)*2,(float)40,(float)100));

		parent.beginShape(PApplet.QUADS);
		parent.fill(0, 0, 0);
		parent.texture(images.get(0));

		// +Z "front" face
		parent.vertex(-1, -1, 1, 0, 0);
		parent.vertex(1, -1, 1, 1, 0);
		parent.vertex(1, 1, 1, 1, 1);
		parent.vertex(-1, 1, 1, 0, 1);

		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(images.get(1));
		// -Z "back" face
		parent.vertex(1, -1, -1, 0, 0);
		parent.vertex(-1, -1, -1, 1, 0);
		parent.vertex(-1, 1, -1, 1, 1);
		parent.vertex(1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);
		parent.texture(images.get(2));
		// +Y "bottom" face
		parent.vertex(-1, 1, 1, 0, 0);
		parent.vertex(1, 1, 1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(images.get(3));
		// -Y "top" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, -1, 1, 1, 1);
		parent.vertex(-1, -1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(images.get(4));
		// +X "right" face
		parent.vertex(1, -1, 1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(1, 1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(images.get(5));
		// -X "left" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(-1, -1, 1, 1, 0);
		parent.vertex(-1, 1, 1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);

		parent.endShape();

		parent.popMatrix();
	}
}