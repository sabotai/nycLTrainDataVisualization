
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


      

import ddf.minim.*;
Minim minim;
AudioPlayer trainSound;
AudioPlayer peopleTalking;
boolean hearTalking;

void setup() {
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


void draw() {
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

