package myGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class Level extends JPanel  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean up=false;
	private static final int  BOSSLEVEL = 9;
	private JLabel background;

	private JPanel top = new JPanel();
	private LevelTransition transition;
	private WriteScors WS = new WriteScors();
	private JLayeredPane treeImg;
	private BufferedImage[] img = null, ladderImg=null;
	private BufferedImage sign = null, timerImg=null;
	private Rectangle[] rectList = new Rectangle[510], echelletList, mouvanteList,  mouvanteList2;
	private Ennemy[] ennemiList;
	private Lever[] leverList;
	private Box[] boxList;
	private MovingPlat[] movingPlatList;
	private JLabel[] block = new JLabel[510];
	private JPanel backContainer = new JPanel();
	private DeathScreen DS = new DeathScreen();
	private Character player, grome;
	private Boss boss;
	private String[] treeName, ladderSizeImg;
	private String currentString;
	private String path, pathSize, levelName;
	private int posX=0, posY=0, iniX=0, iniY=0, ennemiNumber=0, leverNumber=0, boxNumber=0, movePlatNumber=0, treeNumber=0, i=0, z=0, ladderNumber=0,  
				waterNumber=0, levelNumber, size=Main.SreenSize, size2,  spikeNumber=0, catTimer=0, catImgId=0;
	private int[] startTimerMove, startTimer, lever, platList, rectBoxtList, rectMovePlatList, target = new int[2], time= new int[3];
	private int[][] treePos,ladderPos, waterList = new int[50][2], spikePos = new int[50][2];
	
	private long savedTime[], savedTimeMove[], savedTimer, savedTimeCat;
	private boolean pause=false, levelComplete=false, transitionDone=true, resetTimer, transitionLevel=false, blur=false, blurBack=false, saveTimeCat=true;
	float alpha=0, alpha2=0;
	
	public boolean isLevelComplete() {
		return levelComplete;
	}

	public void setLevelComplete(boolean levelComplete) {
		this.levelComplete = levelComplete;
	}

	public boolean isPause() {	
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	
	/*CONSTRUCTOR*/
	public Level(String levelPath, int levelNumber){
			this.levelName = levelPath;
			path ="level/"+levelPath+".txt";
			this.levelNumber = levelNumber;
		}

		public void generate(){
			
			int blockNumber=0, concreteBlock = 0, lineNumber = 1;
			boolean water=false, empty=false, decor=false, spike=false; 
			String[] voidBlock = {"XX"}, spikeBlock = {"S0","S1","S2","S3","S4"}, waterBlock = {"W1","W2","W3","W4","X1","X2","X3","X4","Y1","Y2","Y3","Y4"},  decorBlock = {"W0","X0","Y0","Y5","F8","F0","F1","F2","D3","F3","D4","F7","G2","G0","F9","G1","G3","F4","G4"};
			String stringInt;
			char currentCaract = 0, currentCaract2 = 0;
			FileInputStream fis;
			
			if(Main.SreenSize==1){pathSize="small"; size2=2;}
			else if(Main.SreenSize==2){pathSize="big";size2=1;}
			
			backContainer.setLayout(null);
			backContainer.setLayout(new GridLayout(16,30,0,0));
			backContainer.setBackground(Color.BLACK);
			backContainer.setOpaque(false);
			//DC.decrypte("level0.txt");	
			
			 File file = new File(path);
			    if (!file.exists()) {
			      System.out.println(path + " does not exist.");
			      return;
			    }
			    if (!(file.isFile() && file.canRead())) {
			      System.out.println(file.getName() + " cannot be read from.");
			      return;
			    }
			    try {
			      fis = new FileInputStream(file);
			      if((char)fis.read() == '1')Window.WORLD = "up";
			      else Window.WORLD = "down";
			      fis.read();fis.read();

			      //fis.read();
			      /*GENERER LEVEL*/
			      while (lineNumber < 35) {
			    	  if(lineNumber<19){
				    	currentString="";
				        currentCaract = (char) fis.read();
				        currentCaract2 = (char) fis.read();
				        currentString += (char)currentCaract;
				        currentString+=currentCaract2;
			    	  }
			        //System.exit(0);
			        if(currentCaract == '\r' ||currentCaract2 == '\r'){
			        	lineNumber++;	
			        	fis.read();
			        }
			            
			        /*ADD BLOCKS*/
			        if(lineNumber < 17){
			        	for(i=0; i<voidBlock.length; i++){
			        		if(new String(voidBlock[i]).equals(currentString))empty=true;
			        	}
			        	for(i=0; i<waterBlock.length; i++){
			        		if(new String(waterBlock[i].substring(0)).equals(currentString.substring(0))  &&  new String(waterBlock[i].substring(1)).equals(currentString.substring(1)))water=true;
			        	}
			        	for(i=0; i<decorBlock.length; i++){
			        		if(new String(decorBlock[i].substring(0)).equals(currentString.substring(0))  &&  new String(decorBlock[i].substring(1)).equals(currentString.substring(1))){decor=true;if(levelNumber==0);}
			        	}
			        	for(i=0; i<spikeBlock.length; i++){
			        		if(new String(spikeBlock[i].substring(0)).equals(currentString.substring(0))  &&  new String(spikeBlock[i].substring(1)).equals(currentString.substring(1))){spike=true;if(levelNumber==0);}
			        	}
			        	
			        	if(currentCaract == '\r' || currentCaract2 == '\r'){fis.read();posY+=(32*size);posX=-32*size;}
			        	else if(new String("ZZ").equals(currentString)){
			        		block[blockNumber] = new JLabel(new ImageIcon("image/"+pathSize+"/"+Window.WORLD+"/block/Z.png"));
			        		block[blockNumber].setVisible(false);
         			        this.backContainer.add(block[blockNumber]);
			        		this.add(backContainer);
			        	}
			        	else if(spike){
			        		block[blockNumber] = new JLabel(new ImageIcon("image//"+pathSize+"/"+Window.WORLD+"/block/"+currentString+".png"));
         			        this.backContainer.add(block[blockNumber]);
			        		this.add(backContainer);
			        		spikePos[spikeNumber][0]=posX;
			        		spikePos[spikeNumber][1]=posY;
			        		spikeNumber++;
			        	}
			        	else if(water){
			        		block[blockNumber] = new JLabel(new ImageIcon("image//"+pathSize+"/"+Window.WORLD+"/block/"+currentString+".png"));
         			        this.backContainer.add(block[blockNumber]);
			        		this.add(backContainer);
			        		waterList[waterNumber][0]=posX;
			        		waterList[waterNumber][1]=posY;
			        		waterNumber++;
			        	}
			        	
			        	else if(decor){
			        		block[blockNumber] = new JLabel(new ImageIcon("image//"+pathSize+"/"+Window.WORLD+"/block/"+currentString+".png"));
         			        this.backContainer.add(block[blockNumber]);
			        		this.add(backContainer);
			        	}
			        	else if(empty){
			        		block[blockNumber] = new JLabel(new ImageIcon("image//"+pathSize+"/"+Window.WORLD+"/block/"+currentString+".png"));
         			        this.backContainer.add(block[blockNumber]);
			        		this.add(backContainer);
			        	}
			        	else{
			        		block[blockNumber] = new JLabel(new ImageIcon("image//"+pathSize+"/"+Window.WORLD+"/block/"+currentString+".png"));
			        		backContainer.add(block[blockNumber]);
			        		this.add(backContainer);
			        		this.rectList[concreteBlock] = new Rectangle(posX, posY+(10*size)+7*Main.SreenSize, 32*size, 22*size);
			        		concreteBlock++;
			        	}
			        	
			        	
			        	empty=false; water=false; decor=false; spike=false;
			        	posX+=32*size;
			        	blockNumber++;
			        } 
			         
			        //GET INITIAL POSITION
			        if(lineNumber==18){
			        stringInt = "";
			        fis.read();
			        do{
			        	currentCaract = (char)fis.read();
			        	if(currentCaract!='/')stringInt += currentCaract;
			        }while(currentCaract !='/');
			        iniX = Integer.parseInt(stringInt)/size2;
			        stringInt="";
			        do{
			        	currentCaract = (char)fis.read();
			        	if(currentCaract!='\r')stringInt += currentCaract;
			        }while(currentCaract !='\r');
			        iniY = Integer.parseInt(stringInt)/size2+10*Main.SreenSize;
			        fis.read();
			        lineNumber++;
			        	
			        }
			        ///////////////////////
			        
			        //ADD ENNEMIE
			        if(lineNumber==19){
			        	int setting[] = new int[4];
			        	stringInt = "";
			        	do{
				        	currentCaract = (char)fis.read();
				        	if(currentCaract!='\r')stringInt += currentCaract;
				        }while(currentCaract !='\r');
			        	ennemiNumber = Integer.parseInt(stringInt);
			        	ennemiList = new Ennemy[ennemiNumber];
			        	if(ennemiNumber==0){fis.read();fis.read();}
			        	for(i=0; i<ennemiNumber; i++){
			        		for(z=0; z<4; z++){
			        			stringInt = "";
				        		do{
						        	currentCaract = (char)fis.read();
						        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
						        }while(currentCaract !='/');
				        		setting[z]= Integer.parseInt(stringInt);
			        		}
			        		ennemiList[i] = new Ennemy(setting[0]/size2, setting[1]/size2+7*Main.SreenSize, setting[2]/size2, setting[3]/size2);
			        		fis.read();
			        	}
			        	fis.read();
			        	lineNumber+=2;
			        }
			        
			      //ADD LEVER
			        if(lineNumber==21){
			        	int setting[] = new int[7];
			        	stringInt="";
			        	do{
				        	currentCaract = (char)fis.read();
				        	if(currentCaract!='\r')stringInt += currentCaract;
				        }while(currentCaract !='\r');
			        	leverNumber = Integer.parseInt(stringInt);
			        	leverList = new Lever[leverNumber];
			        	platList = new int[leverNumber];
			        	savedTime = new long[leverNumber];
			        	startTimer = new int[leverNumber];
			        	lever = new int[leverNumber];
			        	if(leverNumber==0){fis.read();fis.read();}
			        	for(i=0; i<leverNumber; i++){
			        		for(z=0; z<8; z++){
			        			stringInt = "";
				        		do{
						        	currentCaract = (char)fis.read();
						        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
						        }while(currentCaract !='/');
				        		if(z<7)setting[z]= Integer.parseInt(stringInt);
			        		}
			        		currentCaract = (char)fis.read();
			        		concreteBlock++;
			        		startTimer[i]=1;
			        		lever[i]=0;
			        		platList[i] = concreteBlock;
			        		leverList[i] = new Lever(setting[0]/size2, setting[1]/size2+7*Main.SreenSize, setting[3]/size2, setting[4]/size2, setting[5]/size2, setting[6]/size2, setting[2],stringInt);
			        		
			        		//fis.read();
			        		//fis.read();
			        	}
			        	fis.read();
			        	lineNumber+=2;
			        }
			        
			        
			      //ADD BOX
			        if(lineNumber==23){
			        	int setting[] = new int[2];
			        	stringInt="";
			        	currentCaract=(char)fis.read();
			        	stringInt += currentCaract;
			        	boxNumber = Integer.parseInt(stringInt);
			        	rectBoxtList = new int[boxNumber];
			        	boxList=new Box[boxNumber];
			        	fis.read();
			        	if(boxNumber==0){fis.read();fis.read();}
			        	for(i=0; i<boxNumber; i++){
			        		for(z=0; z<2; z++){
			        			stringInt = "";
				        		do{
						        	currentCaract = (char)fis.read();
						        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
						        }while(currentCaract !='/');
				        		setting[z]= Integer.parseInt(stringInt);
			        		}
			        		concreteBlock++;
			        		rectBoxtList[i] = concreteBlock;
			        		boxList[i] = new Box(setting[0]/size2, setting[1]/size2+7*Main.SreenSize, this.rectList);
			        		fis.read();
			        	}
			        	lineNumber+=2;
			        }
			        
			      //ADD LADDER
			        if(lineNumber==25){
			        	int setting[] = new int[3];
			        	
			        	fis.read();
			        	stringInt="";
			        	currentCaract=(char)fis.read();
			        	stringInt += currentCaract;
			        	ladderNumber = Integer.parseInt(stringInt);
			        	echelletList = new Rectangle[ladderNumber];
			        	ladderPos = new int[ladderNumber][2];
			        	ladderImg = new BufferedImage[ladderNumber];
			        	ladderSizeImg = new String[ladderNumber];
			        	fis.read();
			        	if(ladderNumber!=0){
			        	for(i=0; i<ladderNumber; i++){
			        		for(z=0; z<4; z++){
			        			stringInt = "";
				        		do{
						        	currentCaract = (char)fis.read();
						        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
						        }while(currentCaract !='/');
				        		if(z==3){ladderSizeImg[i] = stringInt;}
				        		else setting[z]= Integer.parseInt(stringInt);
			        		}
			        		echelletList[i] = new Rectangle(setting[0]/size2, setting[1]/size2+7*Main.SreenSize, 25*size, (64*setting[2]+20)/size2);
			        		ladderPos[i][0]=setting[0]/size2;
			        		ladderPos[i][1]=setting[1]/size2;
			        		fis.read();
			        	}
			        	}else {fis.read();fis.read();}
			        	lineNumber+=2;
			        	fis.read();
			        	
			        }
			        
			      //ADD MOVE PLAT
			        if(lineNumber==27){
			        	
			        	int setting[] = new int[5];
			        	stringInt="";
			        	currentCaract=(char)fis.read();
			        	stringInt += currentCaract;
			        	movePlatNumber = Integer.parseInt(stringInt);
			        	movingPlatList = new MovingPlat[movePlatNumber];
			        	rectMovePlatList = new int[movePlatNumber];
			        	startTimerMove = new int[movePlatNumber];
			        	savedTimeMove = new long[movePlatNumber];
			        	mouvanteList = new Rectangle[movePlatNumber];
			        	mouvanteList2 = new Rectangle[movePlatNumber];
			        	fis.read();
			        	if(movePlatNumber!=0){
			        	for(i=0; i<movePlatNumber; i++){
			        		for(z=0; z<6; z++){
			        			stringInt = "";
				        		do{
						        	currentCaract = (char)fis.read();
						        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
						        }while(currentCaract !='/');
				        		if(z<5)setting[z]= Integer.parseInt(stringInt);
			        		}
			        		concreteBlock++;
			        		startTimerMove[i]=1;
			        		rectMovePlatList[i] = concreteBlock;
			        		movingPlatList[i] = new MovingPlat(setting[0]/size2, setting[1]/size2+7*Main.SreenSize, setting[2]/size2, setting[3]/size2, stringInt, setting[4]);
			        	}
			        	}else fis.read();
			        	fis.read();
			        	lineNumber+=2;      	
			        }
			        
			      //ADD TREES
			        if(lineNumber==29){
			        	fis.read();
			        	
			        	int setting[] = new int[2];
			        	stringInt="";
			        	currentCaract=(char)fis.read();
			        	stringInt += currentCaract;
			        	treeNumber = Integer.parseInt(stringInt);
			        	treePos = new int[treeNumber][2];
			        	treeName = new String[treeNumber];
			        	img = new BufferedImage[treeNumber];
			        	fis.read();
			        	if(treeNumber==0){fis.read();fis.read();}
			        	for(i=0; i<treeNumber; i++){
			        		for(z=0; z<3; z++){
			        			stringInt = "";
				        		do{
						        	currentCaract = (char)fis.read();
						        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
						        }while(currentCaract !='/');
				        		if(z==0)treeName[i] = stringInt;
				        		else setting[z-1]= Integer.parseInt(stringInt);
			        		}
			        		treePos[i][0]=setting[0]/size2;
			        		treePos[i][1]=setting[1]/size2+10*Main.SreenSize;
			        		fis.read();
			        	}
			        	lineNumber+=2;
			        	
			        }
			        
			      //GET PASSWORD
			        if(lineNumber==31){
			        	fis.read();
			       	        				        	
	        			stringInt = "";
		        		do{
				        	currentCaract = (char)fis.read();
				        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
				        }while(currentCaract !='/');
		        		Window.passWord[levelNumber] =stringInt;
			        }
			        
			      //GET TARGET
			        if(lineNumber==32){
			        	fis.read();
			        
			        	
		        		for(z=0; z<2; z++){
		        			stringInt = "";
			        		do{
					        	currentCaract = (char)fis.read();
					        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
					        }while(currentCaract !='/');
			        		target[z]= Integer.parseInt(stringInt)/size2;
			        		
		        		}
		        		lineNumber+=2;
		        		fis.read();	        	
			        }
			        
			      //GET TRANSITION TEXT
			        if(lineNumber==35){
	        			stringInt = "";
		        		do{
				        	currentCaract = (char)fis.read();
				        	if(currentCaract!='/' && currentCaract!='\n')stringInt += currentCaract;
				        }while(currentCaract !='/');
		        		transition = new LevelTransition(levelName, stringInt, levelNumber); 
			        }
			        
			      }
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
			    if(Main.SreenSize==1)top.setPreferredSize(new Dimension(960*Main.SreenSize, Main.SreenSize));
			    if(Main.SreenSize==2)top.setPreferredSize(new Dimension(960*Main.SreenSize, 7*Main.SreenSize));
			    
			    top.setBackground(Color.BLACK);
			    this.add(top, BorderLayout.NORTH);
			    background=new JLabel(new ImageIcon("image/"+pathSize+"/"+Window.WORLD+"/background/back.png"));
				background.setLayout(new FlowLayout());
			    background.add(backContainer, BorderLayout.CENTER);
			    background.setLocation(0, 100);
				this.add(background);
				player = new Character(iniX, iniY, rectList, echelletList, mouvanteList, mouvanteList2, levelNumber);
				player.setCaracImage("minock");
				if(levelNumber==BOSSLEVEL){
					grome = new Character(800, 600, rectList, echelletList, mouvanteList, mouvanteList2, levelNumber);
					grome.setWIDTH(104);
					grome.setHEIGHT(150);
					grome.setCaracImage("grome");
					boss = new Boss();
					boss.setGrome(grome);
				};
		}

		public void play(){
			if(transitionLevel && transition.isChangeBack()){
				if(levelNumber!=BOSSLEVEL)background.setIcon(new ImageIcon("image/"+pathSize+"/transition/level0IMG2.jpg"));
				else background.setIcon(new ImageIcon("image/"+pathSize+"/transition/endIMG2.jpg"));
				
			}

			if(!transitionLevel && !levelComplete && player!=null){
			//LEVEL COMPLETE TEST	
			
			if(Window.dead || resetTimer){time[0]=0;time[1]=0;time[2]=0; resetTimer=false;}
			if(System.nanoTime() > savedTimer+10000000){
				savedTimer = System.nanoTime();
				time[2]++;
				if(time[2]==100){time[1]++;time[2]=0;}
				if(time[1]==60){time[1]=0; time[0]++;}
			}
			int i=0;
			player.move();
			
			/*////////////////////   BOSS    ////////////////////////*/
			if(levelNumber==BOSSLEVEL && player!=null && grome!=null){
				try{
					boss.move();
					if((player.getX() > grome.getX()-65 && player.getX()+60 < grome.getX()+150) && (player.getY()+80 > grome.getY())){Window.dead=true;if(!Window.MUTE)Sound.DEAD.play();}
					if(!boss.isLeverActived())this.rectList[rectList.length-1] = new Rectangle(700, 800, 520, 48);
					else this.rectList[rectList.length-1] = new Rectangle(0, 0, 520, 48);
					if(boss.getBall().getBounds().intersects(player.getBoundsA())){Window.dead=true;if(!Window.MUTE)Sound.DEAD.play();}
					if(!boss.isPatternOneFinished())boxList[0].setMove(false);
					if( boss.isBossDead() && !blur && !blurBack){
					WS.AddScore((time[0]*60000+time[1]*1000+time[2]*10),levelNumber,0);
					blur=true;
				}
				else levelComplete=false;
				}catch(NullPointerException e){System.out.println("nullExecption");}
			}
			
			
			
			//////////////////////////////////////////////////////////
			for(i=0; i<ennemiNumber; i++){
				if(ennemiList[i]!=null)ennemiList[i].ennemiMove();
			}
			
			for(i=0; i<leverList.length; i++){
				if(leverList[i].isActive() && startTimer[i]==1 ){
					savedTime[i]=System.currentTimeMillis();
					startTimer[i]=0;
					leverList[i].setLeverTimer(0);
					lever[i]=1;	
				}
				if(System.currentTimeMillis() > savedTime[i]+leverList[i].getTimerLength()){
					leverList[i].setActive(false);
					startTimer[i]=1;
					lever[i]=0;
				}
				
				if(Window.dead){leverList[i].setActive(false);lever[i]=0;}
				if(lever[i]==1){
					this.rectList[platList[i]] = new Rectangle(leverList[i].getPlatX(), leverList[i].getPlatY(),leverList[i].getWidth(), leverList[i].getHeigth());	
				}
				else if(lever[i]==0)this.rectList[platList[i]] = new Rectangle(0, 0, 0, 0);
				
			}
			
			for(i=0; i<spikeNumber; i++){
				if((player!=null && player.getX() > spikePos[i][0]-54 && player.getX()+10 < spikePos[i][0]+56) && (player.getY() > spikePos[i][1]-40 && player.getY()+64 < spikePos[i][1]+60)){Window.dead=true;if(!Window.MUTE)Sound.DEAD.play();}
			}
			for(i=0; i<boxNumber; i++){
				boxList[i].move();
				this.rectList[rectBoxtList[i]] = new Rectangle(boxList[i].getX(), boxList[i].getY() ,96/size2, 76/size2);	
			}
			
			//MOVING PLATEFORM
			for(i=0; i<movePlatNumber; i++){
				
				
						
				if(movingPlatList[i].getAxe()==1){
					if(player!=null && this.player.collisionMU())up=true;
					else up=false;
					if(movingPlatList[i].getPosY()<=movingPlatList[i].getMinY())movingPlatList[i].setGo(true);
					if(movingPlatList[i].getPosY()>=movingPlatList[i].getMaxY())movingPlatList[i].setGo(false);
					if(startTimerMove[i]==1){savedTimeMove[i]=System.nanoTime();startTimerMove[i]=0;}
					
					this.rectList[rectMovePlatList[i]] = new Rectangle(movingPlatList[i].getPosX(), movingPlatList[i].getPosY() ,movingPlatList[i].getWidth(), 25*size);
					this.mouvanteList2[i] = new Rectangle(movingPlatList[i].getPosX(), movingPlatList[i].getPosY() ,movingPlatList[i].getWidth(), 25*size);
					
	
					if((System.nanoTime() > savedTimeMove[i]+8000000) ){
						if(movingPlatList[i].getPosY() < movingPlatList[i].getMaxX() && movingPlatList[i].isGo()) movingPlatList[i].setPosY(movingPlatList[i].getPosY()+6);
						else if(movingPlatList[i].getPosY() > movingPlatList[i].getMinY() && !movingPlatList[i].isGo())movingPlatList[i].setPosY(movingPlatList[i].getPosY()-6);
						startTimerMove[i]=1;
					}
					
				}
				
				else if(movingPlatList[i].getAxe()==0){
					if(movingPlatList[i].getPosX()==movingPlatList[i].getMinX()){movingPlatList[i].setGo(true);}
					if(movingPlatList[i].getPosX()==movingPlatList[i].getMaxX()){movingPlatList[i].setGo(false);}
					if(startTimerMove[i]==1){savedTimeMove[i]=System.nanoTime();startTimerMove[i]=0;}
					if(!movingPlatList[i].isRight() && !movingPlatList[i].isLeft() ){
						if(player!=null && (this.player.collisionM() && movingPlatList[i].isGo()) && !up)player.setXa(1);
						else if((player!=null && this.player.collisionM() && !movingPlatList[i].isGo()) && !up)player.setXa(-1);
					}	
					this.rectList[rectMovePlatList[i]] = new Rectangle(movingPlatList[i].getPosX(), movingPlatList[i].getPosY() ,movingPlatList[i].getWidth(), 28*size);
					this.mouvanteList[i] = new Rectangle(movingPlatList[i].getPosX(), movingPlatList[i].getPosY() ,movingPlatList[i].getWidth(), 25*size);
					
	
					if((System.nanoTime() > savedTimeMove[i]+2000000)){
						if(movingPlatList[i].getPosX() < movingPlatList[i].getMaxX() && movingPlatList[i].isGo()) movingPlatList[i].setPosX(movingPlatList[i].getPosX()+1);
						else if(movingPlatList[i].getPosX() > movingPlatList[i].getMinX() && !movingPlatList[i].isGo())movingPlatList[i].setPosX(movingPlatList[i].getPosX()-1);
						startTimerMove[i]=1;
					}		
				}
				
				
			}
				if((Character.X >= target[0]) && (Character.X <= target[0]+100*size) && ((Character.Y > target[1]) && (Character.Y < target[1]+75*size)) && !blur && !blurBack){
					WS.AddScore((time[0]*60000+time[1]*1000+time[2]*10),levelNumber,0);
					blur=true;
					
				}
				else levelComplete=false;
			}
			
		}
		
		
		public boolean isResetTimer() {
			return resetTimer;
		}

		public void setResetTimer(boolean resetTimer) {
			this.resetTimer = resetTimer;
		}

		public void keyReleased(KeyEvent e) {
			int i =0;
			if(!transitionLevel)player.keyReleased(e);
			for(i=0; i<boxNumber; i++){
				if(boxList[i]!=null)boxList[i].keyReleased(e);
			}
			for(i=0; i<movePlatNumber; i++){
				if(movingPlatList[i]!=null)movingPlatList[i].keyReleased(e);
			}
		}
		public void keyPressed(KeyEvent e) {
			int i =0;
			if(levelNumber==BOSSLEVEL)boss.keyPressed(e);
			for(i=0; i<movePlatNumber; i++){
				if(movingPlatList[i]!=null)movingPlatList[i].keyPressed(e);
			}
			if(!transitionLevel)player.keyPressed(e);
			for(i=0; i<leverList.length; i++){
				if((lever[i]==0 && startTimer[i]==1) && leverList[i]!=null)leverList[i].keyPressed(e);
			}
			for(i=0; i<boxNumber; i++){
				if(boxList[i]!=null)boxList[i].keyPressed(e);
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE ){
				if(!pause){pause=true;transitionDone=false;}
				else {pause=false;transitionDone=false;}
				
			}
			
			if (e.getKeyCode() == KeyEvent.VK_E ){
				/*if((Character.X >= target[0]) && (Character.X <= target[0]+100*size) && ((Character.Y > target[1]) && (Character.Y < target[1]+75*size))){
					WS.AddScore((time[0]*60000+time[1]*1000+time[2]*10),levelNumber);
					blur=true;
				}
				else levelComplete=false;*/
			}
			if (e.getKeyCode() == KeyEvent.VK_F && transitionLevel ){
				transitionLevel=false;
				levelComplete=true;
				
			}
		}
		
		
		
		@Override
		public void paint(Graphics g) {
			treeImg = new JLayeredPane();
			
			int i=0;
			super.paint(g);
			
			if(!transitionLevel ){
			
				if(levelNumber==BOSSLEVEL)boss.paint(g);
				
			for(i=0; i<treeNumber; i++){	
				 try {
						
					    img[i] = ImageIO.read(new File("image/"+pathSize+"/"+Window.WORLD+"/block/"+treeName[i]+".png"));
					 } catch (IOException e) {}
				 img[i].getGraphics();
				 g.drawImage(img[i],treePos[i][0], treePos[i][1], treeImg);
			}
			
			for(i=0; i<ladderNumber; i++){	
				 try {
					 ladderImg[i] = ImageIO.read(new File("image/"+pathSize+"/"+Window.WORLD+"/plateform/ladder/"+ladderSizeImg[i]+".png"));
					 } catch (IOException e) {}
				 if(ladderImg[i]!=null)ladderImg[i].getGraphics();
				 g.drawImage(ladderImg[i],ladderPos[i][0], ladderPos[i][1]+15*Main.SreenSize, treeImg);
			}		
			
			//ANIMATION CAT
			long currentTimeMillis = System.currentTimeMillis();
			if(saveTimeCat){savedTimeCat = currentTimeMillis;saveTimeCat=false;}
			 try {
				 	if(currentTimeMillis > savedTimeCat+catTimer){
				 		sign = ImageIO.read(new File("image/"+pathSize+"/caractere/leon/chat"+catImgId+".png"));
				 		catTimer+=150;
				 		catImgId++;
				 		if(catImgId==16){
				 			catImgId=0;
				 			catTimer=0;
				 			saveTimeCat = true;
				 		}
				 	}	    
			 } catch (IOException e) {}
			 if(sign!=null){
				 sign.getGraphics();
				 g.drawImage(sign,target[0]+30*size, target[1]+60*size, treeImg);
			 }
			 
			
			
			 
			for(i=0; i<leverNumber; i++){
				if(leverList[i]!=null)leverList[i].paint(g);
			}
			 
			
			for(i=0; i<boxNumber; i++){
				if(boxList[i]!=null )boxList[i].paint(g); 
			}
			for(i=0; i<movePlatNumber; i++){
				if(movingPlatList[i]!=null )movingPlatList[i].paint(g); 
			}
			
			
			player.paint(g);
			
			for(i=0; i<ennemiNumber; i++){
				if(ennemiList[i]!=null)ennemiList[i].paint(g);
			}
			
			Color myBlue = new Color(0, 0, 1, 0.4f);
			for(i=0; i<waterNumber; i++){
				g.setColor(myBlue);
				if(Main.SreenSize==1)g.fillRect(waterList[i][0],waterList[i][1]+11+3*Main.SreenSize, 32*size, 32*size);
				if(Main.SreenSize==2)g.fillRect(waterList[i][0],waterList[i][1]+13+8*Main.SreenSize, 32*size, 32*size);
				
			}
			
			
			try {
			    timerImg = ImageIO.read(new File("image/"+pathSize+"/menu/timer.png"));
			 } catch (IOException e) {}
			timerImg.getGraphics();
			g.drawImage(timerImg,20,40, treeImg);
			
			Color white = new Color(1, 1, 1, 1f);
		    g.setColor(white);
			g.setFont(new Font("Impact", Font.PLAIN, 22*size)); 
		    g.drawString(time[0]+" : "+time[1]+" . "+time[2], 31*size, 44*size);

		    
			if(Window.dead){	
				DS.paint(g);
			}		    
			}
			if(transitionLevel){transition.paint(g);}

			if(pause && !transitionDone){
				if(alpha<0.7f){alpha+=0.03f;}
				if(alpha>0.7f) transitionDone=true;
			}
			if(!pause && !transitionDone){
				if(alpha>0.1f){alpha-=0.03f;}
				if(alpha==0f) transitionDone=true;
			}
			Color myBlack = new Color(0, 0, 0, alpha);
			g.setColor(myBlack);
			g.fillRect(0, 0,965*size, 540*size);
			
			if(blur){
				if(alpha2<=0.99f){alpha2+=0.02f;}
				else {
					blurBack=true;
					blur=false;
					transitionLevel=true;
					player=null;
					backContainer=null;
					block = null;
					background.removeAll();
					if(levelNumber != BOSSLEVEL)background.setIcon(new ImageIcon("image/"+pathSize+"/transition/level0IMG1.jpg"));
					else background.setIcon(new ImageIcon("image/"+pathSize+"/transition/endIMG1.jpg"));
					}
			}
			if(blurBack){
				
				if(alpha2>0f){alpha2-=0.05f;}
				else {blurBack=false;}
			}
			if(alpha2>0 && alpha2<=1){Color myBlack2 = new Color(0, 0, 0, alpha2);
			g.setColor(myBlack2);
			g.fillRect(0, 0,965*size, 540*size);}
			
		}



		public float getAlpha() {
			return alpha;
		}

		public void setAlpha(float alpha) {
			this.alpha = alpha;
		}

		public boolean isTransitionDone() {
			return transitionDone;
		}

		public void setTransitionDone(boolean transitionDone) {
			this.transitionDone = transitionDone;
		}
		
		
		protected void finalize() throws Throwable {
			
			  super.finalize();
		}
		
}
