

void title_animation() {
    trainSound.play(); 
  if (titleX < width/2) { //draw the title coming in from the left
    background(0);
    textAlign(CENTER, CENTER);
    textSize(50);
    text("NYC SUBWAY", titleX, titleY - padding);
    text("THE     TRAIN", titleX, titleY + padding);
    imageMode(CENTER);
    image(lTrainLogo, titleX - padding, titleY + padding, 75, 75);

    //textSize(25);
    //text("2007 - 2012 GROWTH", titleX- 0.25*padding, titleY + 2.5*padding);
    titleX+= titleSpeed;
  }

  if (titleX == width/2) { //one the title hits the middle, the sound starts to play
//    player.play(); 
    titleX = width/2 + 1;
    oldTime = millis();
    //currentState++;
  }

  if (millis() - oldTime > 5500) { //delay causes the title to stop for a bit and then move off screen
    background(0);
    textSize(50);
    text("NYC SUBWAY", titleX, titleY - padding);
    text("THE L TRAIN", titleX, titleY + padding);
    image(lTrainLogo, titleX - padding, titleY + padding, 75, 75);

    //textSize(25);
    //text("2007 - 2012 GROWTH", titleX- 0.25*padding, titleY + 2.5*padding);
    titleX+= titleSpeed;
  }
  
  if (titleX > width + 10*titleSpeed) { //once the title moves off the screen, advance to the next state
    currentState = 1;
    
  }
  
}

