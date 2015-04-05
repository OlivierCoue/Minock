package myGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int SIZE = Main.SreenSize;
	public static int LEVELNUMBER = 15, BOSSLEVEL = 9;
	public static String WORLD;
	public static boolean MUTE = false, dead = false;
	private NewGameImages newGame;
	private JPanel containerTotal = new JPanel(), containerMenu = new JPanel(), containerLevel = new JPanel();
	private JLabel loadingScreen = new JLabel(new ImageIcon("image/big/menu/loadingScreen.gif"));
	private Break Pause = new Break();
	private Level[] level = new Level[LEVELNUMBER];
	private Menu menu = new Menu();
	private Scores scores = new Scores();
	private Option option = new Option();
	private int levelActuel = 0, i=0;
	private static int levelNumber=LEVELNUMBER;		
	private boolean pause=false, inMenu=true;
	public static String[] passWord = new String[levelNumber];
	
	public Window() throws Throwable {	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("image/small/block/B1.png"));
		this.setTitle("Minoc'k");
		this.setSize(960*SIZE, 540*SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setUndecorated(true);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		containerTotal.setLayout(new BorderLayout());
		containerLevel.setLayout(new BorderLayout());
		/*Background color*/
		menu.setBackground(Color.BLACK);
		option.setBackground(Color.BLACK);
		containerMenu.setBackground(Color.BLACK);
		containerTotal.setBackground(Color.BLACK);
		containerMenu.add(menu);
		containerTotal.add(loadingScreen);
		Pause.setOpaque(false);		
		this.setContentPane(containerTotal);
		this.setVisible(true);
		
		passWord=new String[levelNumber];
		
		for(i=0; i<levelNumber; i++){
			level[i] = new Level("level"+i, i);
			level[i].generate();
			level[i]=null;
		}
		if(!MUTE)Sound.AMBIENCE.loop();
		containerTotal.removeAll();
		containerTotal.add(containerMenu);
		revalidate();
		repaint();
		/*LISTENER*/
		/*touche*/
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(newGame!=null){
					newGame.keyPressed(e);
					if(newGame.isSwitchLevel()){
				    	switchLevel();
				    	pause=false;
				    	if(level[levelActuel]!=null){
				    		level[levelActuel].setPause(false);
				    		level[levelActuel].setTransitionDone(false);
					    	level[levelActuel].setAlpha(0);
				    	}
				    	
				    	menu = null;
				    	newGame = null;
					}
				}

				if(!inMenu && !pause)level[levelActuel].keyPressed(e);
				if(level[levelActuel]!=null && level[levelActuel].isLevelComplete() && levelActuel!=BOSSLEVEL){
					level[levelActuel].setLevelComplete(false);
					levelActuel++;
					switchLevel();
					pause=false;
					level[levelActuel].setPause(false);
				}
				else if(level[levelActuel]!=null && level[levelActuel].isLevelComplete()){
					pause=false;
			    	inMenu=true;
			    	level[levelActuel]=null;
			    	containerTotal.removeAll();
			    	containerMenu.removeAll();		    	
			    	containerMenu.add(menu, BorderLayout.CENTER);
			    	containerTotal.add(containerMenu, BorderLayout.CENTER);
			    	levelActuel=0;
			    	menu.bouton1.setVisible(true);
			    	menu.boutonNew.setVisible(false);
					menu.textField.setVisible(false);
					
				}
				
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE )
				{
					if(!pause){
						resetMenu();
						addListener();
						pause=true;
						containerLevel.add(Pause, 0);						
					}
					else {
						menu = null;
						pause=false;
						level[levelActuel].setPause(false);
						level[levelActuel].setTransitionDone(false);
						containerLevel.remove(Pause);
						if(level[levelActuel]!=null)containerLevel.add(level[levelActuel],BorderLayout.CENTER);						
					}
					containerTotal.revalidate();
			    	containerTotal.repaint();
				}														
			}
			@Override
			public void keyReleased(KeyEvent e) {						
					if(!inMenu)level[levelActuel].keyReleased(e);						
			}
			
		});
		setFocusable(true);
		
		/*boutons*/
		addListener();
		
		
		/*REFRESH*/
		new Timer().schedule(new TimerTask() {
			public void run()  {
				if(!pause){
					if(!inMenu && level[levelActuel]!=null){level[levelActuel].play();}
				}
				containerTotal.repaint();
				containerTotal.revalidate();
				if(level[levelActuel]!=null && !inMenu && !level[levelActuel].isTransitionDone())repaint();			
			}
		}, 0, 38);
		
	}
	
	
	/*GO TO LEVEL*/
	public void switchLevel(){
	
		inMenu=true;
		
		if(!pause){
			if(levelActuel>1 && level[levelActuel-1]!=null)level[levelActuel-1]=null;
			level[levelActuel] = new Level("level"+levelActuel, levelActuel);
			containerLevel.removeAll();
			level[levelActuel].removeAll();
			level[levelActuel].generate();
			level[levelActuel].setResetTimer(true);
			level[levelActuel].setBackground(Color.BLACK);
		
		}
		else{
			containerLevel.remove(Pause);
			if(level[levelActuel]!=null)containerLevel.add(level[levelActuel],BorderLayout.CENTER);
			System.out.println("end break");
		}
		pause=false;
		level[levelActuel].setAlpha(0);
		containerLevel.add(level[levelActuel],BorderLayout.CENTER);

    	containerTotal.removeAll();			    	
    	containerTotal.add(containerLevel, BorderLayout.CENTER);
    	containerTotal.revalidate();
    	containerTotal.repaint();
    	inMenu=false;
	
	}
		
	
	public void resetMenu(){
		menu=null;
		menu=new Menu();
		option=null;
		option=new Option();
		scores=null;
		scores=new Scores();
		Pause=null;
		Pause = new Break();
		this.setLocationRelativeTo(null);
		menu.setBackground(Color.BLACK);
		option.setBackground(Color.BLACK);
		containerMenu.setBackground(Color.BLACK);
		containerTotal.setBackground(Color.BLACK);
		containerMenu.removeAll();		    	
    	containerMenu.add(option, BorderLayout.CENTER);
    	containerTotal.revalidate();
    	containerTotal.repaint();	
	}
	
	public void addListener(){
		
		class Play implements ActionListener{
		    public void actionPerformed(ActionEvent a) {
		    	if(level[levelActuel]==null){
		    		newGame = new NewGameImages();
		    		containerMenu.removeAll();
			    	containerMenu.add(newGame);			    	
		    	}
		    	else{
		    		menu = null;
		    		pause=false;
					level[levelActuel].setPause(false);
					level[levelActuel].setTransitionDone(false);
					containerLevel.remove(Pause);
					if(level[levelActuel]!=null)containerLevel.add(level[levelActuel],BorderLayout.CENTER);
			    	pause=false;
		    	}
				
		    	
		    }
		  }
		class Option implements ActionListener{
		    public void actionPerformed(ActionEvent a) {
		    	containerMenu.removeAll();		    	
		    	containerMenu.add(option, BorderLayout.CENTER);
		    	containerTotal.revalidate();
		    	containerTotal.repaint();	
		    }
		  }
		class Menu implements ActionListener{
		    public void actionPerformed(ActionEvent a) {
		    	pause=false;
		    	inMenu=true;
		    	level[levelActuel]=null;
		    	containerTotal.removeAll();
		    	containerMenu.removeAll();		    	
		    	containerMenu.add(menu, BorderLayout.CENTER);
		    	containerTotal.add(containerMenu, BorderLayout.CENTER);
		    	levelActuel=0;
		    	menu.bouton1.setVisible(true);
		    	menu.boutonNew.setVisible(false);
				menu.textField.setVisible(false);
		    }
		  }
		
		class Scores implements ActionListener{
		    public void actionPerformed(ActionEvent a) {
		    	pause=false;
		    	containerTotal.removeAll();
		    	containerMenu.removeAll();		    	
		    	containerMenu.add(scores, BorderLayout.CENTER);
		    	containerTotal.add(containerMenu, BorderLayout.CENTER);
		    	scores.update();
		    	containerTotal.revalidate();
		    	containerTotal.repaint();	
		    }
		  }
		class Exit implements ActionListener{
		    public void actionPerformed(ActionEvent a) {
		    	System.exit(0);
		    }
		  }
		////////////////////////////////////////////////
		////////////////////////////////////////////////
		//////////////        CODE LEVEL    ////////////
		class Code implements ActionListener{ 
			 public void actionPerformed(ActionEvent evt) {
				 int i=0;
				 @SuppressWarnings("deprecation")
				String code = menu.textField.getText();
				 menu.textField.setText("");					 
			        for(i=0; i<levelNumber; i++){
				        if(new String(code).equals(passWord[i])){
				        	
				        	levelActuel=i;
				        	switchLevel();
				        	
					    }
			        }
			        code=" ";
			        pause=false;
			        if(level[levelActuel]!=null)level[levelActuel].setPause(false);	
			        if(level[levelActuel]!=null)level[levelActuel].setAlpha(0);
			        
			    }
		}
		class FirstPlay implements ActionListener{
		    public void actionPerformed(ActionEvent a) {
		    	menu.setPlayClicked(true);
		    	menu.bouton1.setVisible(false);
		    	menu.boutonNew.setVisible(true);
				menu.textField.setVisible(true);
		    }
		  }
		menu.textField.addActionListener(new Code());
		menu.bouton1.addActionListener(new FirstPlay());
		menu.boutonNew.addActionListener(new Play());
		menu.bouton2.addActionListener(new Option());
		menu.bouton4.addActionListener(new Scores());
		menu.bouton3.addActionListener(new Exit());
		Pause.bouton1.addActionListener(new Play());
		Pause.bouton2.addActionListener(new Exit());
		Pause.bouton3.addActionListener(new Menu());
		class Big implements ActionListener{

			public void actionPerformed(ActionEvent a) {
		    		setVisible(false);
		    		Main.SreenSize=2;
		    		setSize(1920, 1080);
		    		resetMenu();
		    		addListener();
		    		revalidate();
		    		setVisible(true);
		    }
		  }
		class Small implements ActionListener{
			public void actionPerformed(ActionEvent a) {
					Main.SreenSize=1;
		    		setSize(960, 540);
		    		resetMenu();
		    		addListener();
		    		revalidate();
		    		
		    }
		  }
		class ChangeSound implements ActionListener{
			public void actionPerformed(ActionEvent a) {
					if(MUTE){option.bouton4.name="Mute";option.bouton4.left=50; MUTE=false;Sound.AMBIENCE.loop();}
					else {option.bouton4.name="Add Sound";option.bouton4.left=5; MUTE=true;Sound.AMBIENCE.stop();}
		    }
		  }
		class Back implements ActionListener{
		    public void actionPerformed(ActionEvent a) {
		    	containerMenu.removeAll();		    	
		    	containerMenu.add(menu, BorderLayout.CENTER);
		    	containerTotal.revalidate();
		    	containerTotal.repaint();		
		    }
		  }
		
		option.bouton1.addActionListener(new Big());
		option.bouton2.addActionListener(new Small());
		option.bouton3.addActionListener(new Back());
		option.bouton4.addActionListener(new ChangeSound());
		scores.bouton1.addActionListener(new Back());
	}
}
