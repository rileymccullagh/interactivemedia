package ptz_camera;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
// cameras from here work
// https://www.insecam.org/en/bytype/Vivotek/

import processing.core.PApplet;
import processing.core.PImage;

public class Camera {
	private static final List<Feed> feeds;
	
	static {
		feeds = new ArrayList<Feed>();
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Santa_Fe,_Argentina", "http://200.107.99.38:8080/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Falmouth,_Cornwall", "http://31.51.157.21/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Boston", "http://24.60.80.6:8090/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Bucharest", "http://109.102.176.21/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Mount_Laurel,_New_Jersey", "http://96.78.107.22/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Santa_Fe,_Argentina", "http://200.107.99.38:8080/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Little_Rock,_Arkansas", "http://162.40.158.193/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Santiago", "http://181.75.96.249:5000/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/San_Juan,_Trinidad_and_Tobago", "http://190.213.182.16:8084/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Nuremberg", "http://93.214.124.11/cgi-bin/viewer/video.jpg"));
		feeds.add(new Feed("https://en.wikipedia.org/wiki/Nov%C3%A1ky", "http://85.163.238.8/cgi-bin/viewer/video.jpg"));
	}
	
	PApplet parent;
	
	PImage empty_image;
	

	public Camera(PApplet p) {
		this.parent = p;
		empty_image = p.createImage(0, 0, 0);
		for (int i = 0; i < feeds.size(); i++) {
			feeds.get(i).set_default(empty_image);
		}
	}

	public void shuffle_feeds () {
		Collections.shuffle(feeds);
	}
	
	public void download_multiple_images_in_sequence (int last_index, final int total_images_per_camera, final int feeds_at_a_time) {
		new Thread (new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < last_index; i++) {
					while (true) {
						int feeds_retrieving = 0;
						for (int j = 0; j < i; j++) {
							if (feeds.get(j).thread_count > 0) {
								feeds_retrieving++;
							}
						}
						if (feeds_retrieving < feeds_at_a_time) {
							break;
						}
						
						try {
							TimeUnit.MILLISECONDS.sleep(100);
						} catch (Exception e) {	
							
						}
					}
					
					feeds.get(i).retrieve_images(total_images_per_camera, parent);
				}
			}
		}).start();
		
	}
	
	public List<PImage> get_sequence_of_images_by_index(int range){
		List<PImage> images_ret = new ArrayList<PImage>();
		for (int i = 0; i < range; i++) {
			images_ret.add(feeds.get(i).getNextImage());
		}
		return images_ret;
	}
	

}

