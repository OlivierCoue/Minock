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
	private int stopDroite = 0;
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
				if(stopDroite==30){xa=1;stopDroite=0;droite=true;
				gauche=false;}
				if(bougerX)stopDroite++;
				
				stopGauche=0;
			}
			else {
				if(stopGauche==15*Main.SreenSize){xa=-1;stopGauche=0;droite=false;
				gauche=true;}
				if(bougerX)stopGauche++;		
				stopDroite=0;
			}
		}
		//RANDOM MOVE
		else if(Target!=posX){
			if(Target > posX){
				if(stopDroite==15*Main.SreenSize){xa=1;stopDroite=0;droite=true;
				gauche=false;}
				if(bougerX)stopDroite++;
				
				stopGauche=0;
			}
			else {
				if(stopGauche==15*Main.SreenSize){xa=-1;stopGauche=0;droite=false;
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
		
		if(startTimer2){savedTime2 = System.currentTimeMillis();startTimer2=false;}
		
		 try {   	
			if(gauche){
				if(System.currentTimeMillis() > savedTime2+700)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/07.png"));
				else if(System.currentTimeMillis() > savedTime2+600)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/06.png"));
				else if(System.currentTimeMillis() > savedTime2+500)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/05.png"));
				else if(System.currentTimeMillis() > savedTime2+400)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/04.png"));
				else if(System.currentTimeMillis() > savedTime2+300)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/03.png"));
				else if(System.currentTimeMillis() > savedTime2+200)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/02.png"));
				else if(System.currentTimeMillis() > savedTime2+100)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/gauche/01.png"));
				if(System.currentTimeMillis() > savedTime2+700)startTimer2=true;
	    		
			}
			else if(droite){	
    			if(System.currentTimeMillis() > savedTime2+700)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/07.png"));
    			else if(System.currentTimeMillis() > savedTime2+600)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/06.png"));
				else if(System.currentTimeMillis() > savedTime2+500)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/05.png"));
				else if(System.currentTimeMillis() > savedTime2+400)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/04.png"));
				else if(System.currentTimeMillis() > savedTime2+300)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/03.png"));
				else if(System.currentTimeMillis() > savedTime2+200)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/02.png"));
				else if(System.currentTimeMillis() > savedTime2+100)img = ImageIO.read(new File("image/"+chemin+"/"+Window.WORLD+"/ennemi/droite/01.png"));
    			if(System.currentTimeMillis() > savedTime2+700)startTimer2=true;
		    		
			}
		  } catch (IOException e) {
		  }
	 	  if(img!=null)img.getGraphics();
	      g.drawImage(img ,posX, posY-(12*Main.SreenSize), Dog);
	}
}
