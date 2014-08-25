void hoverActions() {
  
  textFont(helveticaBlack, 14);
  text("Use the WASD keys for cardinal navigation",450, 15);
  for (int i = 0; i < numberOfStations; i++) {
    if (dist(mouseX, mouseY, station[i].x, station[i].y) < stationEllipseSize) {
      fill(0, 40);
      rect(mouseX-350, mouseY, 350, 200, 8);
      
//      fill(150, 0, 0);
//      ellipse(station[i].x, station[i].y, stationEllipseSize+2, stationEllipseSize+2);
        
      
        imageMode(CENTER);
        image(lLogoSmall, station[i].x, station[i].y);
        imageMode(CORNER);
      
      
       fill(255); 
       textAlign(TOP, LEFT);
      text(data[i][0], mouseX-340, mouseY+50); //title in hover over box
      
      textFont(helveticaLight, 14);
      text("A 5 year increase of " + int(data[i][4]) + "% (" + data[i][1] + " additional riders)", mouseX-340, mouseY+70);
      
      if (float(data[i][3]) < 60000) {
      rect(mouseX-340, mouseY+85, (float(data[i][3])/60000)*400, 20);
      }
      text("2012 Annual Ridership: " + data[i][3], mouseX-340, mouseY+120);

      if (float(data[i][2]) < 60000) {
      rect(mouseX-340, mouseY+130, (float(data[i][2])/60000)*400, 20);
      }
      text("2007 Annual Ridership: " + data[i][2], mouseX-340, mouseY+165);
      
      if (float(data[i][3]) > 60000) { //showing that all of union squares bars cant be displayed with a plus sign
      rect(mouseX-340, mouseY+85, 330, 20);
      rect(mouseX-340, mouseY+130, 325, 20);   
      fill(30); 
      text("+", mouseX-340+319, mouseY+98);
      text("+", mouseX-340+315, mouseY+143);
      }
            
      //station[i].y = station[i].y - trainGifHeight/2 + random(-5, 5); //shaking train!
      
      peopleTalking.play(); //play the sound of people talking
      hearTalking = true;


      println("talking");
      //  } 
      //   if (hearTalking == true && dist(mouseX, mouseY, station[i].x, station[i].y) > 100) {
      //    peopleTalking.pause();
      //    hearTalking = false;
      //    print("no more talking");
      //  }
    }
  }
}

