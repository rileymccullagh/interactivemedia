package ptz_camera;

import java.util.Optional;
import processing.core.PApplet;
import processing.core.PImage;

public class PC_Webcam implements GetImage {
	String webcam_name;
	
	PC_Webcam(String webcam_name){
		this.webcam_name = webcam_name;
	}
	@Override
	public Optional<PImage> getNextImage(PApplet parent) {
		// TODO Auto-generated method stub
		
		return null;
	}
	

}
