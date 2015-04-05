package myGame;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

public class Character {

	private int WIDTH = 30*Window.SIZE;
	private int HEIGHT = 54*Window.SIZE;
	static int X = 0;
	static int Y = 0;
	private static String path;
	private String caracImage;
	private int iniX = 0, iniY = 0, x = 150, y = 600, xa = 0, ya = 0, i = 0, hauteurSaut = 0, levelNumber = 0;
	private int[] animationTmer = new int[4];
	double ySpeed=0, ySpeedX=-2490;
	private double step=0;
	private long savedTime=0, savedTime2=0, savedTimeStart=0, savedTimeJump=0;
	private boolean compterHauteur = true, sauter = false, droite = true, gauche = false, bougerX, bougerY, animer = false, top=false, inertDroite=false, 
					inertGauche=false, ladder=false, saveTime=true, startStep=true, allowJump=false;
	private  BufferedImage img = null;
	private Rectangle[] rectList = new Rectangle[510], ladderList = new Rectangle[10], movingList = new Rectangle[20], movingList2 = new Rectangle[20];
	
	public boolean isAnimer() {
		return animer;
	}
	public void setAnimer(boolean animer) {
		this.animer = animer;
	}
	public double getStep() {
		return step;
	}
	public void setStep(double step) {
		this.step = step;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getXa() {
		return xa;
	}
	public int getYa() {
		return ya;
	}
	public void setYa(int ya) {
		this.ya = ya;
	}
	public void setXa(int xa) {
		this.xa = xa;
	}
	public int getWIDTH() {
		return WIDTH;
	}
	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}
	public int getHEIGHT() {
		return HEIGHT;
	}
	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}
	public String getCaracImage() {
		return caracImage;
	}
	public void setCaracImage(String caracImage) {
		this.caracImage = caracImage;
	}
	
	public Character(int x, int y, Rectangle[] rectList, Rectangle[] echelletList, Rectangle[] mouvantetList, Rectangle[] mouvantetList2, int levelNumber ) {
		this.x= x;
		this.y= y;
		this.iniX= x;
		this.iniY= y;
		this.rectList= rectList;
		this.ladderList= echelletList;
		this.movingList= mouvantetList;
		this.movingList2= mouvantetList2;
		this.levelNumber = levelNumber;
		
	}
	
	
	/*//////////////////////////////////////////////*/
	/*//////////        DEPLACEMENT        /////////*/
	public void move() {
		if(Main.SreenSize==1){path="small";}
		else if(Main.SreenSize==2){path="big";}
		if(caracImage!="grome"){
			Character.X = x;
			Character.Y = y;
		}
		if(animer && startStep){step=100;startStep=false;}
		if(!animer)startStep=true;

		if( System.nanoTime() > savedTimeStart+1000000/Main.SreenSize){
			savedTimeStart = System.nanoTime();
			if(step>10)step-=5;	
		}
		
		if(System.currentTimeMillis()> savedTime+10)
		{
				savedTime = System.currentTimeMillis();
				bougerX=true;
		}
		else{bougerX=false;}
		
		if(System.nanoTime()> savedTimeJump+ySpeed)
		{
				savedTimeJump = System.nanoTime();
				bougerY=true;
		}
		else{bougerY=false;}
		
		if(bougerY){y = y + ya;}
		
		/*JUMP FUNCTION*/
		if(sauter && ySpeedX > -2800 && levelNumber!=9)ySpeedX-=10;
		else if(sauter && ySpeedX > -2800 && levelNumber==9)ySpeedX-=18;
		else if (ySpeedX < -2300) ySpeedX+=0.02;	
		ySpeed = -(3-(Math.pow(ySpeedX,  2))/2);
		
		
		
		if (collisionD()&& !sauter && !ladder){
			ya = 0;
			sauter=false;
			ySpeedX=-2600;
			if(top){
				top=false;
				if(!animer)xa=0;
				else if(droite) xa=6;
				else if(gauche) xa=-6;
				inertDroite=false;
				inertGauche=false;
			}			
		}
		
		if(top)
		{
			if(inertDroite)xa=6;
			else if(inertGauche)xa=-6;;			
		}
		
		if(x+xa < 0 || x+xa > 935*Main.SreenSize)xa=0;
		if (collisionR()){
			if(xa>0)xa=0;
		}
		
		if (collisionL()){
			if(xa<0)xa=0;
		}
		
		
		if((!sauter && !collisionD()) && !ladder && !collisionE()){
			ya = 8;
		}
		
		if(collisionT()){
			sauter=false;
		}
		if(collisionMU() && Level.up && !sauter && ya<1	){ya=-6;allowJump=true;}
		if(collisionMU() && ya != -1 && !sauter)ya=-6;

		if(bougerX){x = x + xa;}
		if(y <=hauteurSaut){sauter=false;}
		if(!collisionE())ladder=false;
		////////////// DEAD ///////////
		if(Window.dead){
			xa=0;
			x=iniX;
			y=iniY;
		}
		if(y>540*Main.SreenSize && caracImage!="grome"){y=-(500*Main.SreenSize);if(!Window.MUTE)Sound.DEAD.play();Window.dead=true;}
		
		
	}
	
	
	
	/*//////////////////////////////////////////////*/
	/*//////////     DETECTER TOUCHE       /////////*/
	
	public boolean isBougerX() {
		return bougerX;
	}
	public void setBougerX(boolean bougerX) {
		this.bougerX = bougerX;
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_D){xa=0;animer=false;}
		if (e.getKeyCode() == KeyEvent.VK_SPACE )
		{	
			compterHauteur = true;
		}
		if(!top){inertDroite=false;inertGauche=false;}
		
	}
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_Z)
		{
			if(collisionE()){
				ya= -6;
				y = y + ya;
				ladder=true;
			}
			else ladder=false;
		}
		/////////////////  JUMP  /////////////////
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			
			if(compterHauteur && (collisionD() || collisionE() || allowJump)){
				sauter=true;
				if(!Window.MUTE)Sound.JUMP.play();
				hauteurSaut = y-70*Main.SreenSize;		
				compterHauteur=false;
				ySpeedX = -230;
				sauter=true;
				if(allowJump){ ySpeed=ySpeed/2;}
				allowJump=false;
			}
			if(y > hauteurSaut && sauter)
			{	
					
					if (droite && animer)inertDroite=true;
					if (gauche && animer)inertGauche=true;
					top=true;
					ya=-10;
					
			}
			else if(y <=hauteurSaut) {sauter=false; }
	}
		if (e.getKeyCode() == KeyEvent.VK_Q)
			{
			if(!collisionM() || Level.up )xa = -6;
			else  xa=-12;

			animer=true;
			gauche=true;
			droite=false;
			if(top){inertGauche=true; inertDroite=false;}
			if(y <=hauteurSaut) {sauter=false;}
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			if(!collisionM() || Level.up )xa = 6;
			else xa=12;

			animer=true;
			droite=true;
			gauche=false;
			if(top){inertGauche=false; inertDroite=true;}
			if(y <=hauteurSaut) {sauter=false;}
		}
		
	}

	
	
	public boolean isDroite() {
		return droite;
	}
	public void setDroite(boolean droite) {
		this.droite = droite;
	}
	public boolean isGauche() {
		return gauche;
	}
	public void setGauche(boolean gauche) {
		this.gauche = gauche;
	}
	/*//////////////////////////////////////////////*/
	/*//////////     COLLISION BAS       ///////////*/
	
	private boolean collisionD() {
		int result=0;
		try{
		for(i=0; i< rectList.length; i++){	if(i<rectList.length && rectList[i]!=null && rectList[i].getBounds().intersects(getBoundsD())){
				result++;
			}
		}
		}catch(ArrayIndexOutOfBoundsException|NullPointerException e){System.out.println("Out of array");};
		if(result==0)return(false);
		else return(true);
	}
	/*//////////     COLLISION DROITE    ///////////*/
	private boolean collisionR() {
		int result=0;
		try{
		for(i=0; i< rectList.length; i++){
			if(i<rectList.length && rectList[i]!=null && rectList[i].getBounds().intersects(getBoundsR())){
				result++;
			}
		}
		}catch(ArrayIndexOutOfBoundsException|NullPointerException e){System.out.println("Out of array");}
		
		if(result==0)return(false);
		else return(true);
	}
	/*//////////    COLLISION GAUCHE   ///////////*/
	private boolean collisionL() {
		int result=0;
		try{
		for(i=0; i< rectList.length; i++){
			if(i<rectList.length &&  rectList[i]!=null && rectList[i]!=null && rectList[i].getBounds().intersects(getBoundsL())){
				result++;
			}
		};
		}catch(ArrayIndexOutOfBoundsException|NullPointerException e){System.out.println("Out of array");};
		if(result==0)return(false);
		else return(true);
	}
	/*//////////    COLLISION TOP   ///////////*/
	private boolean collisionT() {
		int result=0;
		try{
		for(i=0; i< rectList.length; i++){
			if(i+1<rectList.length && rectList[i]!=null && rectList[i].getBounds().intersects(getBoundsT())){
				result++;
			}
		}
		}catch(ArrayIndexOutOfBoundsException|NullPointerException e){System.out.println("Out of array");};
		if(result==0)return(false);
		else return(true);
	}
	/*//////////    COLLISION ECHELLE   ///////////*/
	private boolean collisionE() {
		int result=0;
		try{
		for(i=0; i< ladderList.length; i++){
			if(i<ladderList.length && ladderList[i]!=null && ladderList[i].getBounds().intersects(getBoundsE())){		
				result++;
			}
		}
		}catch(ArrayIndexOutOfBoundsException|NullPointerException e){System.out.println("Out of array");};
		if(result==0)return(false);
		else return(true);
	}
	/*//////////////////////////////////////////////*/
	/*//////     COLLISION PLAT MOUVANTE    ////////*/
	boolean collisionM() {
		int result=0;
		try{
		for(i=0; i< movingList.length; i++){
			if(movingList[i]!=null && movingList[i].getBounds().intersects(getBoundsD())){
				result++;
			}
		}
		}catch(ArrayIndexOutOfBoundsException|NullPointerException e){System.out.println("Out of array");};
		if(result==0)return(false);
		else return(true);
	}
	/*//////////////////////////////////////////////*/
	/*//////     COLLISION PLAT MOUVANTE UP    ////////*/
	boolean collisionMU() {
		int result=0;
		try{
		for(i=0; i< movingList2.length; i++){
			if(movingList2[i]!=null && movingList2[i].getBounds().intersects(getBoundsD())){
				result++;
			}
		}
		}catch(ArrayIndexOutOfBoundsException|NullPointerException e){System.out.println("Out of array");};
		if(result==0)return(false);
		else return(true);
	}
	

	
	/*//////////////////////////////////////////////*/
	/*//////     RECTANGLE DE COLLISION       //////*/
	private Rectangle getBoundsD() {
		return new Rectangle(x+5, y+HEIGHT+6, WIDTH-10 , 3);
	}
	
	private Rectangle getBoundsR() {
		return new Rectangle(x+WIDTH, y, 3 , HEIGHT);
	}
	
	private Rectangle getBoundsL() {
		return new Rectangle(x-1, y, 3 , HEIGHT-1);
	}
	
	private Rectangle getBoundsT() {
		return new Rectangle(x+5, y, WIDTH-10 , 1);
	}
	
	private Rectangle getBoundsE() {
		return new Rectangle(x+(WIDTH/3), y+HEIGHT, WIDTH/3 , 1);
	}
	
	public Rectangle getBoundsA() {
		return new Rectangle(x, y, WIDTH , HEIGHT);
	}
	
	/*//////////////////////////////////////////////*/
	/*//////           DESSINER PERSO         //////*/
	
	public void paint(Graphics g) {
		
		JLayeredPane EverythingButPlayer;
	    EverythingButPlayer = new JLayeredPane();
	    long currentTimeMillis = System.currentTimeMillis();
		if(saveTime){savedTime2 = currentTimeMillis;saveTime=false;animationTmer[0]=1;animationTmer[1]=1;}
	    
	    try {
	    	
    		
	    	if(animer && (droite || gauche) && collisionD()){
		    	if(droite && animer ){
		    			
		    		if(animationTmer[0]==9){saveTime=true;}
	    			if(currentTimeMillis > savedTime2+(90*animationTmer[0])){
	    				img = ImageIO.read(new File("image/"+path+"/caractere/"+caracImage+"/droite/0"+(animationTmer[0])+".png"));
	    				animationTmer[0]++;	    				
	    			}		    			
		    	}

		    	if(gauche && animer){
		    		
		    		if(animationTmer[0]==9){saveTime=true;}
	    			if(currentTimeMillis > savedTime2+(90*animationTmer[0])){
	    				img = ImageIO.read(new File("image/"+path+"/caractere/"+caracImage+"/gauche/0"+(animationTmer[0])+".png"));
	    				animationTmer[0]++;	    			
	    			}		    		
		    	}
	    	}
	    	else if(sauter && droite)img = ImageIO.read(new File("image/"+path+"/caractere/"+caracImage+"/droite/jump.png"));
	    	else if(sauter && gauche)img = ImageIO.read(new File("image/"+path+"/caractere/"+caracImage+"/gauche/jump.png"));
	    	else if(droite){
	    		
    			if(currentTimeMillis > savedTime2+(150*animationTmer[1])){
    				img = ImageIO.read(new File("image/"+path+"/caractere/"+caracImage+"/droite/st0"+(animationTmer[1])+".png"));
    				animationTmer[1]++;	    				
    			}
    			if(animationTmer[1]>=6){saveTime=true;}
	    	}
	    	else if(gauche){
	    	
	    		if(currentTimeMillis > savedTime2+(150*animationTmer[1])){
    				img = ImageIO.read(new File("image/"+path+"/caractere/"+caracImage+"/gauche/st0"+(animationTmer[1])+".png"));
    				animationTmer[1]++;	    				
    			}
    			if(animationTmer[1]>=6){saveTime=true;}
	    	}
	    	
	    } catch (IOException e) {
	    }
	    
	    if(img!=null)img.getGraphics();    
	    if(img!=null)g.drawImage(img,x, y+5, EverythingButPlayer);
	}
	
}
