package myGame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

public class MovingPlat {
	
	private JLayeredPane platImg;
	private  BufferedImage img = null;
	private String pathSize;
	private int posX=0, posY=0, width=0, maxX=0, minX=0, axe=0, maxY=0, minY=0;
	public int getAxe() {
		return axe;
	}

	public void setAxe(int axe) {
		this.axe = axe;
	}

	private String path = " ";
	private boolean go, right=false, left=false;

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public boolean isGo() {
		return go;
	}

	public void setGo(boolean go) {
		this.go = go;
	}

	public MovingPlat(int posX, int posY, int width, int maxX, String path, int axe){
		this.posX= posX;
		this.posY = posY;
		this.width= width;
		this.maxX = maxX;
		this.minX = posX;
		this.minY = posY;
		this.maxY = maxX;
		this.axe=axe;
		if(Main.SreenSize==1)pathSize="small";
		else if (Main.SreenSize==2)pathSize="big";
		this.path = "image/"+pathSize+"/"+Window.WORLD+"/plateform/activable/"+path+".png";
		
	}
	
	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public void paint(Graphics g) {

			 try {
				    img = ImageIO.read(new File(path));
				 } catch (IOException e) {}
			 img.getGraphics();
			 g.drawImage(img,posX, posY-7*Main.SreenSize, platImg);

		}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Q)left=false;	
		if (e.getKeyCode() == KeyEvent.VK_D)right=false;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			left=true;
			right=false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D)
		{
			right=true;
			left=false;
		}
	}
	
}