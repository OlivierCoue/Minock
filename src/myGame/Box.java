package myGame;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;








import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

public class Box {
	
	private JLayeredPane EverythingButPlayer;
	private int WIDTH = 48*Main.SreenSize;
	private int HEIGHT = 38*Main.SreenSize;

	private static String path;
	private int iniX = 0;
	private int iniY = 0;
	private int x = 150;
	private int y = 600;
	
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


	private int xa = 0;
	private int ya = 0;
	private int i = 0;
	private boolean move = false;
	private boolean moveR=true;
	private boolean moveL=true;
	private long savedTime=0;
	
	private Rectangle[] rectList = new Rectangle[550];

	public Box(int x, int y, Rectangle[] rectList ) {
		this.x= x;
		this.y= y;
		this.iniX= x;
		this.iniY= y;
		this.rectList= rectList;	
	}
	
	
	/*//////////////////////////////////////////////*/
	/*//////////        DEPLACEMENT        /////////*/
	public void move() {
		WIDTH=31*Main.SreenSize;
		HEIGHT = 54*Main.SreenSize;
		if(Main.SreenSize==1){path="small";}
		else if(Main.SreenSize==2){path="big";}
		if(Window.dead){x=iniX; y=iniY;}
		if(move){y = y + ya;}
		
		if(savedTime+4000000 < System.nanoTime())
		{
				savedTime = System.nanoTime();
				move=true;
		}
		else{
			move=false;
		}	
	
		if (collisionD()){
			ya = 0;
		}
		
		if (collisionR()){
			if(xa==2*Main.SreenSize)xa=0;
			moveR=false;
		}
		else moveR=true;
		
		if (collisionL()){
			if(xa==-(2*Main.SreenSize))xa=0;
			moveL=false;
		}
		else moveL=true;
		
		if(!collisionD()){
			ya = 2;
		}
		
		x = x + xa;
		xa=0;
	}
	

	/*//////////////////////////////////////////////*/
	/*//////////     DETECTER TOUCHE       /////////*/
	
	public boolean isMove() {
		return move;
	}


	public void setMove(boolean move) {
		this.move = move;
	}


	public void keyReleased(KeyEvent e) {
		xa=0;
		
	}
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_D && moveR)
		{
			if((Character.X+32*Main.SreenSize+5 > x) && (Character.X < x+WIDTH) && (Character.Y+54*Main.SreenSize > y) && (Character.Y+54*Main.SreenSize < y+HEIGHT+5)){xa = 4;x = x + xa;}
			
			
		}
		if (e.getKeyCode() == KeyEvent.VK_Q  && moveL)
		{
			
			if((Character.X > x+WIDTH-5) && (Character.X < x+WIDTH+1) && (Character.Y+54*Main.SreenSize > y) && (Character.Y+54*Main.SreenSize < y+HEIGHT+10) ){xa = -4;x = x + xa;}
		}
	}


	/*//////////////////////////////////////////////*/
	/*//////////     COLLISION BAS       ///////////*/
	private boolean collisionD() {
		int result=0;
		for(i=0; i< rectList.length; i++){
			if(rectList[i]!=null && rectList[i].getBounds().intersects(getBoundsD())){
				result++;
			}
		}
		if(result==0)return(false);
		else return(true);
	}
	/*//////////     COLLISION DROITE    ///////////*/
	private boolean collisionR() {
		int result=0;
		for(i=0; i< rectList.length; i++){
			if(rectList[i]!=null && rectList[i].getBounds().intersects(getBoundsR())){
				result++;
			}
		}
		if(result==0)return(false);
		else return(true);
	}
	/*//////////    COLLISION GAUCHE   ///////////*/
	private boolean collisionL() {
		int result=0;
		for(i=0; i< rectList.length; i++){
			if(rectList[i]!=null && rectList[i].getBounds().intersects(getBoundsL())){
				result++;
			}
		}
		if(result==0)return(false);
		else return(true);
	}
	/*//////////    COLLISION TOP   ///////////*/
	
	/*//////////////////////////////////////////////*/
	/*//////     RECTANGLE DE COLLISION       //////*/
	private Rectangle getBoundsD() {
		HEIGHT = 38*Main.SreenSize;
		WIDTH = 48*Main.SreenSize;
		return new Rectangle(x+1, y+HEIGHT, WIDTH-5 , 3);
	}
	
	private Rectangle getBoundsR() {
		return new Rectangle(x+WIDTH, y, 3 , HEIGHT-5);
	}
	
	private Rectangle getBoundsL() {
		return new Rectangle(x-3, y, 1 , HEIGHT);
	}
	
	
	
	/*//////////////////////////////////////////////*/
	/*//////           DESSINER PERSO         //////*/
	
	public void paint(Graphics g) {
		if(Main.SreenSize==1){path = "small";}
		else if(Main.SreenSize==2){path = "big";}
	    EverythingButPlayer = new JLayeredPane();
	    BufferedImage img = null;

	    try {
	    	img = ImageIO.read(new File("image/"+path+"/"+Window.WORLD+"/box.png"));
	    	
	    } catch (IOException e) {
	    }
	    img.getGraphics();
	    g.drawImage(img,x, y-(7*Main.SreenSize), EverythingButPlayer);
	}
}
