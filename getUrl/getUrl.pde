import http.requests.*;

public void setup() 
{
  size(400,400);
  smooth();
  
  String url = getWebcamURL(1168708590);
  
  PImage img;
  img = loadImage(url);
  image(img, 0, 0);

}

String getWebcamURL(int index) {  
  GetRequest get = new GetRequest("https://webcamstravel.p.mashape.com/webcams/list/webcam=" + index + "?lang=en&show=webcams%3Aimage%2Clocation");
  get.addHeader("X-Mashape-Key", "RlTIFeDv2lmshNiO66jKm3qrjJKdp16yYtIjsn4hlJRtLCEzOp");
  get.addHeader("X-Mashape-Host", "webcamstravel.p.mashape.com");

  get.send(); // program will wait untill the request is completed
  
  // parse the response as JSON
  JSONObject response = parseJSONObject(get.getContent());
  println("status: " + response.getString("status"));
  JSONObject result = response.getJSONObject("result");
  JSONArray webcams = result.getJSONArray("webcams");
  JSONObject images = webcams.getJSONObject(0);
  JSONObject image = images.getJSONObject("image");
  JSONObject current = image.getJSONObject("current");
  String preview = current.getString("preview");
  
  
  return preview;
}
