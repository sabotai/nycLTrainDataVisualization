void assignStations() {

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

    trainLengthMultiplier[i] = float(data[i][1]) / trainIndex;


    if ((incomingTrain[0] <= station[0].x - trainX[0]) && fixed[0] == false) { //animate the incoming train

      incomingTrain[0] += 1;
      trainX[0] = incomingTrain[0] + int(station[0].x);
    } 
    else {
      fixed[0] = true;
      trainX[0] = int(station[0].x); //ensures the train is fixed once its rolled into it's station
    }

    //if (i > 0 && (incomingTrain[i] < station[i].x - trainX[i]) && fixed[i-1] == true) { //animate the incoming train
    if (i > 0 && (incomingTrain[i] <= station[i].x - trainX[i]) && fixed[i-1] == true) { //animate the incoming train

      incomingTrain[i] += 100; //increases the speed of each one
      trainX[i] = incomingTrain[i] + int(station[i].x);
    } 
    else {

      //if (trainX[i] >= int(station[i].x) || fixed[i] == true) {
      fixed[i] = true; 
      trainX[i] = int(station[i].x);
      //trainDrawnY[i] = station[i].y;
    }



    if ((i == 0 && fixed[0] == true) || (i<5 && fixed[i-1] == true)) {
      copy(train, 0, 0, int(trainLengthMultiplier[i] * trainGifWidth), 52, int(trainX[i]) + int(padding/2), int(station[i].y) - trainGifHeight/2, int(trainLengthMultiplier[i] * trainGifWidth), 52);
    }
    if (i>4 && fixed[i-1] == true && i<7) { //adjusting the positions of Bedford and Lorimer trains 
      copy(train, 0, 0, int(trainLengthMultiplier[5] * trainGifWidth), 52, int(trainX[5]), int(station[5].y) - trainGifHeight, int(trainLengthMultiplier[5] * trainGifWidth), 52);
      copy(train, 0, 0, int(trainLengthMultiplier[6] * trainGifWidth), 52, int(trainX[6]), int(station[6].y) - trainGifHeight, int(trainLengthMultiplier[6] * trainGifWidth), 52);
    }
    if (i>6 && fixed[i-1] == true) {
      copy(train, 0, 0, int(trainLengthMultiplier[i] * trainGifWidth), 52, int(trainX[i]) + int(padding/2), int(station[i].y) - trainGifHeight/2, int(trainLengthMultiplier[i] * trainGifWidth), 52);
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

