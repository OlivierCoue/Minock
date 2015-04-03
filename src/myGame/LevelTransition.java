package myGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class LevelTransition extends JPanel{
	private static final int BOSSLEVEL = 9;
	private int levelNumber=0;
	private JLayeredPane back;
	private  BufferedImage img = null;
	private long savedTime = System.currentTimeMillis();
	private String path, text, pathSize;
	private boolean setTimer = true, blur=false, blurBack=false, stop=false, changeBack = false;
	public boolean isChangeBack() {
		return changeBack;
	}

	public void setChangeBack(boolean changeBack) {
		this.changeBack = changeBack;
	}

	private float alpha=0;
	 private JLabel background;
	
	public LevelTransition(String path, String text, int levelNumber){
		this.path = path;
		this.text = text;
		this.levelNumber = levelNumber;
		background=new JLabel(new ImageIcon("image/"+pathSize+"/"+Window.WORLD+"/background/level0.png"));
		background.setLayout(new FlowLayout());
	    background.setLocation(0, 100);
		this.add(background);
	}
	
	public void paint(Graphics g) {
		if(Main.SreenSize==1){pathSize="small"; }
		else if(Main.SreenSize==2){pathSize="big";}
		if(setTimer){savedTime = System.currentTimeMillis();setTimer=false;}
		
		
		if(System.currentTimeMillis() > savedTime+5000){
				blur=true;
		}
		
		
		
		if(blur && !stop){
			if(alpha<=0.99f){alpha+=0.005f;}
			else {
				blurBack=true;
				blur=false;
				changeBack=true;
				}
		}
		if(blurBack){
			stop=true;
			if(alpha>0f){alpha-=0.01f;}
			else {blurBack=false;}
		}
		if(alpha>0 && alpha<=1){Color myBlack2 = new Color(0, 0, 0, alpha);
		g.setColor(myBlack2);
		g.fillRect(0, 0,965*Main.SreenSize, 540*Main.SreenSize);}
		if(levelNumber != BOSSLEVEL){
			Color white = new Color(173, 147, 105);
			g.setColor(white);
			g.setFont(new Font("Impact", Font.PLAIN, 15*Main.SreenSize));
			g.drawString(text, 50*Main.SreenSize, 100*Main.SreenSize);
	
			g.setFont(new Font("Impact", Font.PLAIN, 30*Main.SreenSize));
			g.drawString("CODE NIVEAU : "+Window.passWord[levelNumber+1], 700*Main.SreenSize, 460*Main.SreenSize);
			g.setFont(new Font("Impact", Font.PLAIN, 8*Main.SreenSize));
			g.drawString("Conservé précieusement ce code pour aller directement à ce niveau", 700*Main.SreenSize, 480*Main.SreenSize);
			
			g.setColor(white);
			g.setFont(new Font("Impact", Font.PLAIN, 10*Main.SreenSize));
			g.drawString("Appuyez sur F pour continuer", 800*Main.SreenSize, 495*Main.SreenSize);
		}
		
	}
}
