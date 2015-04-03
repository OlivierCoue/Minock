package myGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLayeredPane;

public class Boss {
	
	private Character grome;
	private Rectangle ball;
	private Rectangle fallingBall;
	private long savedTime, savedTimeBall,  savedTimeFallingBall, patternTransitionTimer;
	private double ballFunctionX;
	private Lever[] lever = new Lever[2];
	private boolean onRight,onLeft=true, moveBall, moveFallingBall, leverActived=false, move=false, startTimer=true, switchRightBall=true, patternOneFinished=false, bossDead = false;
	public boolean isBossDead() {
		return bossDead;
	}
	public void setBossDead(boolean bossDead) {
		this.bossDead = bossDead;
	}

	private Point ballPos = new Point(0, 0);
	private int gromeHealth=40;
	private String gromeHealthStr="100";
	public boolean isLeverActived() {
		return leverActived;
	}
	public void setLeverActived(boolean leverActived) {
		this.leverActived = leverActived;
	}
	public Character getGrome() {
		return grome;
	}
	public void setGrome(Character grome) {
		this.grome = grome;
	}
	public Rectangle getBall() {
		return ball;
	}
	public void setBall(Rectangle ball) {
		this.ball = ball;
	}
	public boolean isPatternOneFinished() {
		return patternOneFinished;
	}
	public void setPatternOneFinished(boolean patternOneFinished) {
		this.patternOneFinished = patternOneFinished;
	}
	
	public Boss(){
		lever[0] = new Lever(680, 170, 600, 0, 0,0, 0, "P1");
		lever[1] = new Lever(70,550, 600, 0, 0,0, 0, "P9");
		lever[1].setPath2("image/big/"+Window.WORLD+"/block/rock.png");
		lever[0].setDrawPlat(false);
		lever[1].setPlatX(240);
		lever[1].setPlatY(150);
		ball = new Rectangle(0, 0, 50, 50);
		fallingBall = new Rectangle(240, 150, 50, 50);
	}
	
	public void move(){
		long time = System.nanoTime();
		grome.move();
		
		if(grome.getY() > 1100)bossDead=true;
		
		if(time > savedTimeBall+6000000)
		{
				savedTimeBall = System.nanoTime();
				moveBall=true;
		}
		else{moveBall=false;}
		if(time >  savedTimeFallingBall+2000000)
		{
			 	savedTimeFallingBall = System.nanoTime();
			 	moveFallingBall=true;
		}
		else{moveFallingBall=false;}
		
		if(Window.dead){patternOneFinished=false; gromeHealth=200;lever[0].setActive(false);}
		if(lever[0].isActive())leverActived=true;
		else leverActived=false;
		grome.setStep(3300000);
		if(gromeHealth>0)firtPattern();
		else{
			ballPos.setLocation(-100, -100);
			ball.setLocation(-100, -100);
			secondPattern();
			
		}
	}
	

	public void keyPressed(KeyEvent e) {
		lever[0].keyPressed(e);
		lever[1].keyPressed(e);
	}
	/////////////////	PATTERN 2 //////////////////
	public void secondPattern(){
		if(!patternOneFinished){
			if(grome.getX() < 400 ){
				grome.setXa(1);
				grome.setDroite(true);
				grome.setGauche(false);
				grome.setAnimer(true);
			}
			else{ 
				grome.setXa(0);
				grome.setAnimer(false);
				if(System.currentTimeMillis() > patternTransitionTimer+5000){patternOneFinished=true;move=true;}
			}
		}
		else {
			 if(grome.getX()==1360 && !startTimer && onLeft ){	
					onLeft=false;
					onRight=true;
					startTimer=true;
					move=false;
					savedTime = System.currentTimeMillis();
					grome.setAnimer(false);
					
				 }
				 else if(grome.getX()==200 && !startTimer && onRight ){
					onLeft=true;
					onRight=false;
					startTimer=true;
					move=false;
					savedTime = System.currentTimeMillis();
					grome.setAnimer(false);
		
				}
				
			 	if(System.currentTimeMillis()> savedTime+2000 && startTimer)
				{		
							grome.setAnimer(true);
							startTimer=false;
							move=true;
				}
			 
				if(move){
					if(onRight){
						grome.setDroite(false);
						grome.setGauche(true);
						grome.setXa(-1);
					}
					else if(onLeft){
						grome.setDroite(true);
						grome.setGauche(false);
						grome.setXa(1);
					}
				}
		}
		
	}
	/////////////////	PATTERN 1	/////////////////
	public void firtPattern(){
		long time=0;	
		//falling ball
		if(lever[1].isActive() && moveFallingBall){
			lever[1].setPlatY(lever[1].getPlatY()+1);
			fallingBall.setLocation(240, lever[1].getPlatY());
		}
		else if(!lever[1].isActive()){
			lever[1].setPlatY(150);
		}
		
		
		if(lever[1].getPlatY() > 800 ){
			lever[1].setActive(false);
			lever[1].setLeverTimer(0);
			fallingBall.setLocation(240,150);
		}
		if(fallingBall.getBounds().intersects(grome.getBoundsA())){
			lever[1].setActive(false);
			lever[1].setLeverTimer(0);
			fallingBall.setLocation(240, 150);
			gromeHealth-=40;
		}
		if(gromeHealth==0)patternTransitionTimer = System.currentTimeMillis();
		if(Window.dead)gromeHealth=200;
		gromeHealthStr = Integer.toString(gromeHealth);
		
		 if(grome.getX()==1360 && !startTimer && onLeft ){	
			onLeft=false;
			onRight=true;
			startTimer=true;
			move=false;
			savedTime = System.currentTimeMillis();
			grome.setAnimer(false);
			ballPos.x = grome.getX();
			ballPos.y = grome.getY();
			if(switchRightBall)switchRightBall=false;
			else if(!switchRightBall)switchRightBall=true;
		 }
		 else if(grome.getX()==200 && !startTimer && onRight ){
			onLeft=true;
			onRight=false;
			startTimer=true;
			move=false;
			savedTime = System.currentTimeMillis();
			grome.setAnimer(false);
			ballPos.x = grome.getX();
			ballPos.y = grome.getY();
		}
		 
		time = System.currentTimeMillis();
		if(System.currentTimeMillis()> savedTime+2000 && startTimer)
		{		
					grome.setAnimer(true);
					startTimer=false;
					move=true;
					ballFunctionX=0;
		}
		else if(startTimer && onRight)ballMove(true);
		else if(startTimer && onLeft)ballMove(false);
			
			
		if(move){
			if(onRight){
				grome.setDroite(false);
				grome.setGauche(true);
				grome.setXa(-1);
			}
			else if(onLeft){
				grome.setDroite(true);
				grome.setGauche(false);
				grome.setXa(1);
			}
		}
		
		
		
	}
	
	
	public void ballMove(boolean right){
		if(moveBall){
			if(right){
				if(switchRightBall){
					ballPos.x+=2;
					ballFunctionX+=0.08;
					ballPos.y= (int) ((Math.pow((ballFunctionX-6), 2)*2))+570;
				}
				else if(!switchRightBall){
					ballPos.x+=3;
					ballFunctionX+=0.05;
					ballPos.y= (int) ((Math.pow((ballFunctionX-6), 2)*5))+400;
				}
			}
			else {
				ballPos.x-=2;
				ballFunctionX+=0.08;
				ballPos.y= (int) ((Math.pow((ballFunctionX-6), 2)*2))+500;
			}
		}
		ball.setLocation(ballPos.x, ballPos.y);
	}
	
	public void paint(Graphics g) {
		JLayeredPane treeImg = new JLayeredPane();
		BufferedImage bossPlat = null, launchBall = null, life = null;
		
		lever[0].paint(g);
		lever[1].paint(g);
		
		if(!leverActived){
			try {
			    bossPlat = ImageIO.read(new File("image/big/"+Window.WORLD+"/plateform/activable/P7.png"));
			 } catch (IOException e) {}
			bossPlat.getGraphics();
			g.drawImage(bossPlat,710, 800, treeImg);
		}
		
		AffineTransform at = new AffineTransform();

     
        at.translate(ball.x, ball.y);
        at.rotate(ballFunctionX, 25, 25);

		Graphics2D g2d = (Graphics2D)g;

		// LAUNCH BALL //
		try {
			launchBall = ImageIO.read(new File("image/big/"+Window.WORLD+"/block/rock.png"));
		 } catch (IOException e) {}
		if(startTimer && !move && !grome.isAnimer()){
			launchBall.getGraphics();
			g2d.drawImage(launchBall, at, treeImg);
			/*g.setColor(Color.red);
			g.fillRect(ball.x, ball.y, 50, 50);*/
		}
		
		
		try {
			life = ImageIO.read(new File("image/big/up/block/vie.png"));
		 } catch (IOException e) {}
		life.getGraphics();
		g.drawImage(life,835, 50, treeImg);
		g.setColor(Color.RED);
		g.fillRect(858, 56, 200-gromeHealth, 30);
		//g.drawString(gromeHealthStr, 1700, 100);
		
		grome.paint(g);
	}
}
