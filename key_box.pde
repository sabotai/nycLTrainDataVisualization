void keyBox() {
 
  noStroke();
  fill(0, 200);
 rect(10, height - 130, 300, 120); 
  
  fill(255);
  textAlign(LEFT, LEFT);
  text("Ridership Growth from 2007 - 2012", 20, height - 60);
  
  //copy(train, 0, 0, 250, 52, 20, height-50 , 250, 52);
  //train.resize(100,0);
  image(oneTrain, 15, height-50);
  
  text("= ~ 2300 additional",170,height-35);
  text("riders since 2007",190 ,height - 20);
  
  lTrainLogo.resize(60, 0);
  image(lTrainLogo, 240, height-130);
  textFont(helveticaBlack, 28);
  text("NYC SUBWAY", 25, height-90);
}
