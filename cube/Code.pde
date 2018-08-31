class Code {
 float x = random(width);
 float y = random(-2000,-1000);
 float z = random(0,20);
 float yspeed = map(z, 0, 20, 1, 3);
 float len = map(z, 0, 20, 10, 20);
 float boxWidth = map(z,0,20,0,20);
 String metaStr;

 
 void fall() {
   y = y + yspeed;
    float grav = map(z, 0, 20, 0, 0.05);
    yspeed = yspeed + grav;
   
   if (y > height) {
    y = random(-1000,-100);
   yspeed = map(z, 0, 20, 1, 3);
   }
 }
 
 void show() {
   metaStr = "code";
   float thick = map(z, 0 , 20, 1, 3);
   strokeWeight(thick);
   fill(0,255,65);
   textSize(map(z,0,20,5,20));
   text(metaStr,x,y,boxWidth,1000);
   if (z > 10) {
   fill(0,143,17);
   }
}
}
