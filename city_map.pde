void cityMap() {

  //  for (int i = 0; i <= 255; i++) {
  //   background(transparency); 
  //   println("running");
  //   transparency++;
  //  }
  //  
  imageMode(CORNER);
  image(mapImage, controlX, controlY);


  if (transparency > 0) { //creates initial map fade in
  
    fill(0, transparency); 
    rect(0, 0, width, height);
    transparency-= 5;
  }
  //println(transparency);


    
 if (keyPressed) { //controls - replace with switch
    if ((key == 'w' || key == 'W') && controlY <= 0) {
      controlY+= controlSpeed;
    }

    if ((key == 'a' || key == 'A') && controlX <= 0) {
      controlX+= controlSpeed;
    }

    if ((key == 's' || key == 'S') && controlY >= -3685+height) {
      controlY-= controlSpeed;
    }

    if ((key == 'd' || key == 'D') && controlX > -3000+width) {
      controlX-= controlSpeed;
    }

  }  


  if (transparency == 0) {
     currentState = 2; 
  }
}

void mouseDragged(){
 controlX += mouseX-pmouseX;
 controlY += mouseY-pmouseY;

}


