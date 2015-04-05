package myGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
	
	public class Ennemy extends JPanel{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxDroite=0;
	private int maxGauche=0;
	private int posX=0;
	private int posY=0;
	private int posCarX=0;
	private int posCarY=0;
	private int xa=0;
	private int Target = 0;
	private JLayeredPane Dog;
	private boolean droite=false;
	private boolean gauche=false;
	private boolean bougerX=true;;
	private int stopGauche = 0;
	private int stopDroite = 0, animationTime;
	private String chemin;
	private BufferedImage img = null;
	private long savedTime=System.nanoTime(), savedTime2=System.nanoTime();
	private boolean startTimer=true, startTimer2=true;
	
	
	public Ennemy(int posX, int posY, int limiteGauche, int limiteDroite) {
			this.posX = posX;
			this.posY = posY;
			this.maxGauche = limiteGauche;
			this.maxDroite = limiteDroite;
			this.Target = limiteGauche;
	}
	
	public void ennemiMove(){
		if(Main.SreenSize==1)chemin="small";
		else if(Main.SreenSize==2)chemin="big";
		posCarX = Character.X;
		posCarY = Character.Y;
		
		if(startTimer){savedTime=System.nanoTime();startTimer=false;}
		if((System.nanoTime() > savedTime+7000000) ){
			bougerX=true;
			startTimer=true;
		}
		else bougerX=false;
		
		
		if( (posCarY > posY-50*Main.SreenSize) && ((posCarX >= posX) && ((posCarX <= posX+35*Main.SreenSize)) || ((posCarX <= posX) && (posCarX > posX-20*Main.SreenSize))) && (posCarY < posY+35*Main.SreenSize)){Window.dead=true;if(!Window.MUTE)Sound.DEAD.play();}
		else if((posCarX >= maxGauche) && (posCarX <= maxDroite) && (posCarY > posY-50*Main.SreenSize) && (posCarY < posY+35*Main.SreenSize) ){
			if(posCarX > posX){
				if(stopDroite==16){xa=6;stopDroite=0;droite=true;
				gauche=false;}
				if(bougerX)stopDroite++;
				
				stopGauche=0;
			}
			else {
				if(stopGauche==8*Main.SreenSize){xa=-6;stopGauche=0;droite=false;
				gauche=true;}
				if(bougerX)stopGauche++;		
				stopDroite=0;
			}
		}
		//RANDOM MOVE
		else if(Target!=posX){
			if(Target > posX){
				if(stopDroite==8*Main.SreenSize){xa=6;stopDroite=0;droite=true;
				gauche=false;}
				if(bougerX)stopDroite++;
				
				stopGauche=0;
			}
			else {
				if(stopGauche==8*Main.SreenSize){xa=-6;stopGauche=0;droite=false;
				gauche=true;}
				if(bougerX)stopGauche++;
				
				stopDroite=0;
			}
			
		}
		else {
			Target = (int) (Math.random() * (maxDroite - maxGauche)) + maxGauche;
		}
			
		if((posX+xa >= maxGauche && posX+xa <= maxDroite)&& bougerX){posX = posX+xa; }
		
	}

	@Override
	public void paint(Graphics g) {
		
		Dog = new JLayeredPane();
		long currentTimeMillis = System.currentTimeMillis();
		if(startTimer2){savedTime2 = System.currentTimeMillis();startTimer2=false;animationTime=1;}
		
		
		 try {   	
			if(gauche){
				
				if(currentTimeMillis > savedTime2+(100*animationTime)){
    				img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/0"+animationTime+".png"));
    				animationTime++;	    				
    			}
    			if(animationTime>=8){startTimer2=true;}
	    		
			}
			else if(droite){	
				if(currentTimeMillis > savedTime2+(100*animationTime)){
    				img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/0"+animationTime+".png"));
    				animationTime++;	    				
    			}
    			if(animationTime>=8){startTimer2=true;}
		    		
			}
		  } catch (IOException e) {
		  }
	 	  if(img!=null)img.getGraphics();
	      g.drawImage(img ,posX, posY-(12*Main.SreenSize), Dog);
	}
}
