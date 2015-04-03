package myGame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Decrypt {
	

	public void decrypte(String levelName){
		 int stop = 0;
		String actualCaractere = "", stringOut="";
		int i=0, keyCompt=0;
		int[] key = {1,4,5,8,2,1,4,5,6,3,5,5,2,1,4,5,7,8,4,5,2,2,1,4,5,2,3,6,5,4,4,7,4,5,8,9,6,5,4,2};

	    try {
	      if(stop==0){
	      File file = new File(levelName+".txt");
	      Scanner input = new Scanner(file);
	      while(input.hasNextLine())
	      {
	        actualCaractere += input.nextLine()+'\n';
	      }
	      
	      char[] charArray = actualCaractere.toCharArray();
	      char[] decrypted = new char[charArray.length+1];
	      
	      for(i=0; i<charArray.length; i++){
	    	  	decrypted[i] = (char) (charArray[i]^key[keyCompt]);
	            keyCompt++;
	            if(keyCompt==40)keyCompt=0;
	      }
	      keyCompt=39;
	      for(i=0; i<charArray.length; i++){
	    	  	decrypted[i] = (char) (decrypted[i]^key[keyCompt]);
	            keyCompt--;
	            if(keyCompt==-1)keyCompt=39;
	      }
	      keyCompt=0;
	      for(i=0; i<charArray.length; i++){
	    	  	decrypted[i] = (char) (decrypted[i]^key[keyCompt]);
	            keyCompt+=2;
	            if(keyCompt==38)keyCompt=0;
	      }
	      
	      for(i=0; i<charArray.length-1; i++){
	    	  stringOut+=decrypted[i];
	      }
	      
	      FileOutputStream tempFile = new FileOutputStream(levelName+".txt");
	      tempFile.write(stringOut.getBytes());
	      stop=1;
	      
	    }
		
	    } catch (IOException e) {
		      e.printStackTrace();
		      System.out.println("Can't open level.txt");
		}
	}
	
}
