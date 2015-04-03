package myGame;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class WriteScors {

	
	private int i=0;
	private String stringInt;
	private char currentCaract;
	private FileInputStream fis;
	private int[] savedTime = new int[Window.LEVELNUMBER+1];
	private FileOutputStream file;
	private int levelNumber=Window.LEVELNUMBER;
	public WriteScors(){
   
	}
	
	public void AddScore(int milisec, int level, int action){
		 Decrypt DC = new Decrypt();
		 DC.decrypte("scores");
		 try {  
			 	
		       String input = "";
		       
		        fis = new FileInputStream("scores.txt");
		        for(i=0; i<levelNumber; i++){
		        	
		    	   stringInt = "";
		    	   do{
			        	currentCaract = (char)fis.read();
			        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
			        }while(currentCaract !='/');
		    	   savedTime[i] = Integer.parseInt(stringInt);	     
		        }
		       
		        if(action==0 && savedTime[level+1] > milisec)savedTime[level+1] =  milisec;
		        else if(action == 1)savedTime[0]++;
		        
		        for(i=0; i<levelNumber; i++){
		        	input += Integer.toString(savedTime[i])+"/";
		        }
		        file = new FileOutputStream("scores.txt");
		        file.write(input.getBytes());

		    } catch (Exception e) {
		        System.out.println("Problem reading file.");
		    }
		 
		 DC.decrypte("scores");
		
	}

	public int[] getSavedTime() {
		return savedTime;
	}
	
}
