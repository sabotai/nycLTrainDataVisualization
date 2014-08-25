import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class nycLTrainDataVisualization extends PApplet {


int titleX, titleY, transparency;
int titleSpeed;
int timer, oldTime;
int currentState;
float padding;
PImage mapImage, lTrainLogo, train, oneTrain, lLogoSmall;
PFont helveticaBlack, helveticaLight;
int controlX, controlY, controlSpeed;

//int numberOfStations;
//PVector[] station = new PVector[2]; //23 stations
//int trainX[] = new int[2];
int stationEllipseSize;
//int numberOfStations;
int numberOfStations = 23;
boolean[] fixed = new boolean[numberOfStations];
PVector[] station = new PVector[numberOfStations]; //23 stations
int trainX[] = new int[numberOfStations];
int[] incomingTrain = new int[numberOfStations];
float trainIndex; //largest value from the spreadsheet becomes the index for measuring the other trains
float[] trainLengthMultiplier = new float[numberOfStations];
//float[] trainDrawnY = new float[numberOfStations];

int trainGifWidth, trainGifHeight;

String[] rawData;
String[][] data;


      


Minim minim;
AudioPlayer trainSound;
AudioPlayer peopleTalking;
boolean hearTalking;

public void setup() {
  size(800, 600);
  smooth();
  frameRate(200);
  //frame.setResizable(true);


  titleX = -2000;
  titleY = height/2;
  titleSpeed = 20; //must come in a multiple of the width
  padding = height * 1/20;


  fill(255);
  background(0);
  currentState = 0;
  transparency = 255;
  timer = millis();

  controlX = -450;
  controlY = -1650;
  controlSpeed = 5;

  stationEllipseSize = 20;
  //incomingTrain = controlX - 1000; //starting position for trains
  //fixed = false;
  trainIndex = 6968;
  
  for (int i = 0; i < numberOfStations; i++) {
     incomingTrain[i] = controlX - 1000; 
  }
  

  minim = new Minim(this);
  trainSound = minim.loadFile("train-pass-by-01.mp3"); //train-pass-by-01.mp3 is from soundjay.com
  peopleTalking = minim.loadFile("crowd-talking-1.mp3");

  //mapImage = loadImage ("NYCS_map_grey b.png"); //modified version of original image from wikimedia commons
  mapImage = loadImage ("basic high res map c.jpg"); //modified version of original image from wikimedia commons

  lTrainLogo = loadImage ("l logo.gif");
  train = loadImage ("trainGif12.png");
  oneTrain = loadImage ("oneTrain.png");
  trainGifWidth = 740;
  trainGifHeight = 52;

  helveticaBlack = loadFont("HelveticaLTStd-Blk-48.vlw");
  helveticaLight = loadFont("Helvetica-20.vlw");
  textFont(helveticaBlack, 32);
  
  rawData = loadStrings("mtaC.csv");
  splitData();
  
  lLogoSmall = loadImage("l logo.gif");
  lLogoSmall.resize(20, 0);
}


public void draw() {
  if (currentState == 0) {
    title_animation();
  }

  if (currentState > 0) {
    cityMap();
  }
  if (currentState == 2) {
    assignStations();
  }
  if (currentState == 3) {
    keyBox(); 
    hoverActions();
    println("current state is 3");
  }
}

public void assignStations() {

  station[0] = new PVector(746 + controlX, 1769 + controlY); //creates station circles that updates with map movement
  station[1] = new PVector(782 + controlX, 1791 + controlY);
  station[2] = new PVector(826 + controlX, 1816 + controlY);
  station[3] = new PVector(864 + controlX, 1840 + controlY);
  station[4] = new PVector(901 + controlX, 1859 + controlY);
  station[5] = new PVector(1057 + controlX, 1978 + controlY);
  station[6] = new PVector(1118 + controlX, 2021 + controlY);
  station[7] = new PVector(1163 + controlX, 2017 + controlY);
  station[8] = new PVector(1192 + controlX, 2045 + controlY);
  station[9] = new PVector(1199 + controlX, 2078 + controlY);
  station[10] = new PVector(1252 + controlX, 2095 + controlY);
  station[11] = new PVector(1332 + controlX, 2104 + controlY);
  station[12] = new PVector(1371 + controlX, 2136 + controlY);
  station[13] = new PVector(1409 + controlX, 2163 + controlY);
  station[14] = new PVector(1456 + controlX, 2198 + controlY);
  station[15] = new PVector(1460 + controlX, 2264 + controlY);
  station[16] = new PVector(1464 + controlX, 2364 + controlY);
  station[17] = new PVector(1466 + controlX, 2397 + controlY);
  station[18] = new PVector(1477 + controlX, 2459 + controlY);
  station[19] = new PVector(1484 + controlX, 2507 + controlY);
  station[20] = new PVector(1495 + controlX, 2560 + controlY);
  station[21] = new PVector(1491 + controlX, 2629 + controlY);
  station[22] = new PVector(1472 + controlX, 2672 + controlY);


  fill(255);
  noStroke();


  for (int i = 0; i < numberOfStations; i++) { //makes all the trains, stations and labels

    trainLengthMultiplier[i] = PApplet.parseFloat(data[i][1]) / trainIndex;


    if ((incomingTrain[0] <= station[0].x - trainX[0]) && fixed[0] == false) { //animate the incoming train

      incomingTrain[0] += 1;
      trainX[0] = incomingTrain[0] + PApplet.parseInt(station[0].x);
    } 
    else {
      fixed[0] = true;
      trainX[0] = PApplet.parseInt(station[0].x); //ensures the train is fixed once its rolled into it's station
    }

    //if (i > 0 && (incomingTrain[i] < station[i].x - trainX[i]) && fixed[i-1] == true) { //animate the incoming train
    if (i > 0 && (incomingTrain[i] <= station[i].x - trainX[i]) && fixed[i-1] == true) { //animate the incoming train

      incomingTrain[i] += 100; //increases the speed of each one
      trainX[i] = incomingTrain[i] + PApplet.parseInt(station[i].x);
    } 
    else {

      //if (trainX[i] >= int(station[i].x) || fixed[i] == true) {
      fixed[i] = true; 
      trainX[i] = PApplet.parseInt(station[i].x);
      //trainDrawnY[i] = station[i].y;
    }



    if ((i == 0 && fixed[0] == true) || (i<5 && fixed[i-1] == true)) {
      copy(train, 0, 0, PApplet.parseInt(trainLengthMultiplier[i] * trainGifWidth), 52, PApplet.parseInt(trainX[i]) + PApplet.parseInt(padding/2), PApplet.parseInt(station[i].y) - trainGifHeight/2, PApplet.parseInt(trainLengthMultiplier[i] * trainGifWidth), 52);
    }
    if (i>4 && fixed[i-1] == true && i<7) { //adjusting the positions of Bedford and Lorimer trains 
      copy(train, 0, 0, PApplet.parseInt(trainLengthMultiplier[5] * trainGifWidth), 52, PApplet.parseInt(trainX[5]), PApplet.parseInt(station[5].y) - trainGifHeight, PApplet.parseInt(trainLengthMultiplier[5] * trainGifWidth), 52);
      copy(train, 0, 0, PApplet.parseInt(trainLengthMultiplier[6] * trainGifWidth), 52, PApplet.parseInt(trainX[6]), PApplet.parseInt(station[6].y) - trainGifHeight, PApplet.parseInt(trainLengthMultiplier[6] * trainGifWidth), 52);
    }
    if (i>6 && fixed[i-1] == true) {
      copy(train, 0, 0, PApplet.parseInt(trainLengthMultiplier[i] * trainGifWidth), 52, PApplet.parseInt(trainX[i]) + PApplet.parseInt(padding/2), PApplet.parseInt(station[i].y) - trainGifHeight/2, PApplet.parseInt(trainLengthMultiplier[i] * trainGifWidth), 52);
    }


    ellipse(station[i].x, station[i].y, stationEllipseSize, stationEllipseSize); //creates station circles

    textSize(14);
    textAlign(RIGHT, CENTER);
    textFont(helveticaLight, 14);

    if (i < 7) {
      text(data[i][0], station[i].x - padding, station[i].y); //write the station title
    }

    if (i == 7 ) {
      text(data[i][0], station[i].x - padding + 30, station[i].y+25); //write the station title
    }

    if (i > 7 && i < 15) {
      text(data[i][0], station[i].x - padding + 18, station[i].y+13); //write the station title
    }   

    if (i > 14) {
      text(data[i][0], station[i].x - padding, station[i].y); //write the station title
    }
  }

  if (fixed[numberOfStations - 1]) {
    currentState = 3;
  }
}

public void cityMap() {

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

public void hoverActions() {
  
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
      text("A 5 year increase of " + PApplet.parseInt(data[i][4]) + "% (" + data[i][1] + " additional riders)", mouseX-340, mouseY+70);
      
      if (PApplet.parseFloat(data[i][3]) < 60000) {
      rect(mouseX-340, mouseY+85, (PApplet.parseFloat(data[i][3])/60000)*400, 20);
      }
      text("2012 Annual Ridership: " + data[i][3], mouseX-340, mouseY+120);

      if (PApplet.parseFloat(data[i][2]) < 60000) {
      rect(mouseX-340, mouseY+130, (PApplet.parseFloat(data[i][2])/60000)*400, 20);
      }
      text("2007 Annual Ridership: " + data[i][2], mouseX-340, mouseY+165);
      
      if (PApplet.parseFloat(data[i][3]) > 60000) { //showing that all of union squares bars cant be displayed with a plus sign
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

public void keyBox() {
 
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

public void splitData() {
 
 data = new String[rawData.length][5]; //specify how many columns in csv

  for (int i = 0; i < rawData.length; i++) {
   String[] pieces = split(rawData[i], ",");
   
   for (int j=0; j<5; j++) {
     data[i][j] = pieces[j];
   }
  } 
  
}


public void title_animation() {
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

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "nycLTrainDataVisualization" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
