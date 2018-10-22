package ptz_camera;

import java.util.Optional;

import processing.core.PApplet;
import processing.core.PImage;

public interface GetImage {
	Optional<PImage> getNextImage(PApplet parent);
}
