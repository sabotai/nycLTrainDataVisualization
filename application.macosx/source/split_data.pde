void splitData() {
 
 data = new String[rawData.length][5]; //specify how many columns in csv

  for (int i = 0; i < rawData.length; i++) {
   String[] pieces = split(rawData[i], ",");
   
   for (int j=0; j<5; j++) {
     data[i][j] = pieces[j];
   }
  } 
  
}
