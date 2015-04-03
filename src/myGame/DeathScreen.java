package myGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class DeathScreen  extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WriteScors WS = new WriteScors();
	private JLayeredPane screenDead;
	private String path;
	private boolean deathSound=true;
	private  BufferedImage deathS = null;
	private long savedTime = System.currentTimeMillis();
	private boolean startTimer=true;
	public void paint(Graphics g) {
		
		if(Main.SreenSize==1){path = "small";}
		else if(Main.SreenSize==2){path = "big";}
	    screenDead = new JLayeredPane();
	    if(deathSound && !Window.MUTE){Sound.DEAD.play();deathSound=false;}
	    
	    if(startTimer){savedTime = System.currentTimeMillis();startTimer=false;}
	    
	    try {
	    	if(Window.dead){
		
	    		if(System.currentTimeMillis() > savedTime+850)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/1.png"));
	    		
	    		else if(System.currentTimeMillis() > savedTime+800)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/2.png"));
	    		
	    		else if(System.currentTimeMillis() > savedTime+750)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/3.png"));

	    		else if(System.currentTimeMillis() > savedTime+700)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/4.png"));
	    		
	    		else if(System.currentTimeMillis() > savedTime+250)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/5.png"));

	    		else if(System.currentTimeMillis() > savedTime+200)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/4.png"));

	    		else if(System.currentTimeMillis() > savedTime+150)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/3.png"));

	    		else if(System.currentTimeMillis() > savedTime+100)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/2.png"));

	    		else if(System.currentTimeMillis() > savedTime+50)	deathS = ImageIO.read(new File("image/"+path+"/deathSreen/1.png"));
    		
	    		if(System.currentTimeMillis() > savedTime+900){startTimer=true;Window.dead=false;deathSound=true;deathS=null;WS.AddScore(0,0,1);}
	    	}	
	    	
	    } catch (IOException e) {
	    }
	    if(Window.dead){
	    	if(deathS!=null)deathS.getGraphics();
	    	if(deathS!=null)g.drawImage(deathS,0, 0, screenDead);
	    }
	}

}
