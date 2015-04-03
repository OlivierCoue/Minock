package myGame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;


public class Lever {
	private JLayeredPane leverImg;
	private  BufferedImage img = null;
	private  BufferedImage lever = null;
	private int posX=0, posY=0;
	private int platX=0;
	private int platY=0;
	private int width=0;
	private int heigth=0;
	private int leverTimer=0;
	private int leverTimerBack=45;
	private boolean active = false, drawPlat=true;
	public void setDrawPlat(boolean drawPlat) {
		this.drawPlat = drawPlat;
	}
	private int timerLength = 0;
	private String path2;
	public int getLeverTimer() {
		return leverTimer;
	}


	public void setLeverTimer(int leverTimer) {
		this.leverTimer = leverTimer;
	}

	public int getTimerLength() {
		return timerLength;
	}


	public void setTimerLength(int timerLength) {
		this.timerLength = timerLength;
	}

	private String path = "";
	public int getPlatX() {
		return platX;
	}


	public void setPlatX(int platX) {
		this.platX = platX;
	}


	public int getPlatY() {
		return platY;
	}


	public void setPlatY(int platY) {
		this.platY = platY;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeigth() {
		return heigth;
	}


	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}

	
	
	
	public Lever(int posX, int posY, int platX, int platY, int width, int heigth, int timerLength, String path2){
		if(Main.SreenSize==1){path = "small";}
		else if(Main.SreenSize==2){path = "big";}
		this.posX= posX;
		this.posY = posY;
		this.platX= platX;
		this.platY = platY;
		this.width= width;
		this.heigth = heigth;
		this.timerLength = timerLength;
		this.path2 = "image/"+path+"/"+Window.WORLD+"/plateform/activable/"+path2+".png";
	}
	
	
	public void setPath2(String path2) {
		this.path2 = path2;
	}


	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_E)
		{

			if((Character.X > posX-70 && Character.X < posX+70)  && (Character.Y > posY-100 && Character.Y < posY)){
				active=true;
				
			}
		}
	}
	
	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
	}


	public void paint(Graphics g) {
		
		if(Main.SreenSize==1){path = "small";}
		else if(Main.SreenSize==2){path = "big";}
		try {   	
			 if(active && leverTimer < 40){
			 if(leverTimer==0)lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/2.png"));
			 else if(leverTimer<10)lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/3.png"));
			 else if(leverTimer<20)lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/4.png"));
			 else if(leverTimer<30)lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/5.png"));
			 else lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/5.png"));
			 leverTimer++;
			 leverTimerBack=0;
			 }
			 else if(!active && leverTimerBack < 40){
				 if(leverTimerBack < 10) lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/4.png"));
				 else if(leverTimerBack <20) lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/3.png"));
				 else if(leverTimerBack <30) lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/2.png"));
				 else lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/1.png"));
				 leverTimerBack ++;
			 }
			 else if(!active)lever = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/levier/1.png"));
	    } catch (IOException e) {
	    }
		if(lever!=null)lever.getGraphics();
		if(lever!=null) g.drawImage(lever,posX, posY, leverImg);
	 try {
			
		    img = ImageIO.read(new File(path2));
		 } catch (IOException e) {}
	 if(active && drawPlat )img.getGraphics();
	 if(active && drawPlat)g.drawImage(img,platX, platY-10, leverImg);
	 
	}	
}
