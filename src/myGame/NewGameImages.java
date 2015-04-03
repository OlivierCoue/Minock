package myGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NewGameImages extends JPanel {
	
	private float alpha=0;
	 private JLabel background;
	 private boolean blurBack, blur=false, stop = false,changeBack, setTimer=true, img3=false,  img2=false, switchLevel=false;
	 public boolean isSwitchLevel() {
		return switchLevel;
	}


	public void setSwitchLevel(boolean switchLevel) {
		this.switchLevel = switchLevel;
	}
	private long savedTime = 0;
	 private String pathSize;
	
	public NewGameImages(){
		background=new JLabel(new ImageIcon("image/big/transition/start1.jpg"));
		this.setBackground(Color.black);
		this.add(background);
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		if(Main.SreenSize==1){pathSize="small"; }
		else if(Main.SreenSize==2){pathSize="big";}
		if(setTimer){savedTime = System.currentTimeMillis();setTimer=false;}
		
		
		if(System.currentTimeMillis() > savedTime+4000){
				blur=true;
				if(img3)img2=true;
		}
		
		if(!img3)transition("start2");
		if(img2)transition("start3");
		
		if(alpha>0 && alpha<=1){Color myBlack2 = new Color(0, 0, 0, alpha);
		g.setColor(myBlack2);
		g.fillRect(0, 0,965*Main.SreenSize, 540*Main.SreenSize);
		}
		

		g.setColor(Color.WHITE);
		g.setFont(new Font("Impact", Font.PLAIN, 10*Main.SreenSize));
		g.drawString("Appuyez sur F pour passer", 800*Main.SreenSize, 495*Main.SreenSize);
		
		
	}
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F ){
			switchLevel = true;
		}
		
	}
	public void transition(String img){
		if(blur && !stop){
			if(alpha<=0.99f){alpha+=0.005f;}
			else {
				blurBack=true;
				blur=false;
				changeBack=true;
				background=null;
				background=new JLabel(new ImageIcon("image/big/transition/"+img+".jpg"));
				this.removeAll();
				this.add(background);
				this.revalidate();
				}
		}
		if(blurBack){
			stop=true;
			if(alpha>0f){alpha-=0.01f;}
			else {
				blurBack=false; 
				img3=true;
				setTimer = true;
				if(img != "start3") stop=false;

			}
		}
	
	}
}
