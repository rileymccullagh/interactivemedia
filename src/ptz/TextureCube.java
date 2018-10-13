package ptz;

import processing.core.*;

class TextureCube {

	PApplet parent;

	PImage tex1;
	PImage tex2;
	PImage tex3;
	PImage tex4;
	PImage tex5;
	PImage tex6;
	String url1 = "https://picsum.photos/400/400/?random";
	String url2 = "https://picsum.photos/400/400/?random";
	String url3 = "https://picsum.photos/400/400/?random";
	String url4 = "https://picsum.photos/400/400/?random";
	String url5 = "https://picsum.photos/400/400/?random";
	String url6 = "https://picsum.photos/400/400/?random";

	TextureCube(PApplet p) {
		this.parent = p;

		tex1 = parent.loadImage(url1, "jpeg");
		tex2 = parent.loadImage(url2, "jpeg");
		tex3 = parent.loadImage(url3, "jpeg");
		tex4 = parent.loadImage(url4, "jpeg");
		tex5 = parent.loadImage(url5, "jpeg");
		tex6 = parent.loadImage(url6, "jpeg");
	}

	void draw() {
		parent.pushMatrix();
		parent.textureMode(PApplet.NORMAL);
		parent.noStroke();
		parent.translate(parent.width / 2, parent.height / 2, -200);
		parent.rotateY(parent.frameCount * PApplet.PI / 360);
		parent.rotateX(parent.frameCount * PApplet.PI / 720);
		parent.scale(PApplet.map((float)parent.height+(float)parent.width, (float)0,((float)parent.height+(float)parent.width)*2,(float)40,(float)100));

		parent.beginShape(PApplet.QUADS);
		parent.fill(0, 0, 0);
		parent.texture(tex1);

		// +Z "front" face
		parent.vertex(-1, -1, 1, 0, 0);
		parent.vertex(1, -1, 1, 1, 0);
		parent.vertex(1, 1, 1, 1, 1);
		parent.vertex(-1, 1, 1, 0, 1);

		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(tex2);
		// -Z "back" face
		parent.vertex(1, -1, -1, 0, 0);
		parent.vertex(-1, -1, -1, 1, 0);
		parent.vertex(-1, 1, -1, 1, 1);
		parent.vertex(1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);
		parent.texture(tex3);
		// +Y "bottom" face
		parent.vertex(-1, 1, 1, 0, 0);
		parent.vertex(1, 1, 1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(tex4);
		// -Y "top" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, -1, 1, 1, 1);
		parent.vertex(-1, -1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(tex5);
		// +X "right" face
		parent.vertex(1, -1, 1, 0, 0);
		parent.vertex(1, -1, -1, 1, 0);
		parent.vertex(1, 1, -1, 1, 1);
		parent.vertex(1, 1, 1, 0, 1);
		parent.endShape();

		parent.beginShape(PApplet.QUADS);

		parent.texture(tex6);
		// -X "left" face
		parent.vertex(-1, -1, -1, 0, 0);
		parent.vertex(-1, -1, 1, 1, 0);
		parent.vertex(-1, 1, 1, 1, 1);
		parent.vertex(-1, 1, -1, 0, 1);

		parent.endShape();

		parent.popMatrix();
	}
}