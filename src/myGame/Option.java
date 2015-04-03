package myGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Option extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String chemin; 
	public Button bouton1 = new Button("1920x1080", 18*Main.SreenSize, "bouton");
	public Button bouton2 = new Button("960x540", 23*Main.SreenSize, "bouton");
	public Button bouton3  = new Button("Back", 35*Main.SreenSize, "bouton");
	public Button bouton4  = new Button("Mute", 32*Main.SreenSize, "bouton");
	private JPanel container = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
	public Option(){
		if(Main.SreenSize==1){chemin = "small";}
		else if(Main.SreenSize==2){chemin = "big";}; 
		GridLayout layout = new GridLayout(3,1);
		container.setLayout(layout);
		JLabel background=new JLabel(new ImageIcon("image/"+chemin+"/menu/menu.gif"));

		JLabel background2=new JLabel();
		JLabel top=new JLabel();
		top.setLayout(new FlowLayout());
    	background.setLayout(new FlowLayout());

    	background2.setLayout(new FlowLayout());
    	background2.setLayout(new GridLayout(5,100*Main.SreenSize,100*Main.SreenSize,10*Main.SreenSize)); 
    	background2.setPreferredSize(new Dimension(100*Main.SreenSize, 200*Main.SreenSize));
    	//DECALLER DROITE HAUT BOUTON
    	top.setPreferredSize(new Dimension(475*Main.SreenSize, 750*Main.SreenSize));
    	
		bouton1.setPreferredSize(new Dimension(50*Main.SreenSize, 20*Main.SreenSize));
		bouton2.setPreferredSize(new Dimension(50*Main.SreenSize, 20*Main.SreenSize));
		bouton3.setPreferredSize(new Dimension(50*Main.SreenSize, 20*Main.SreenSize));
		bouton4.setPreferredSize(new Dimension(50*Main.SreenSize, 20*Main.SreenSize));
		container.setOpaque(false);
		background2.setOpaque(false);
		
		background2.add(bouton1);
		background2.add(bouton2);
		background2.add(bouton4);
		background2.add(bouton3);
		
		background.add(top, BorderLayout.NORTH);
		background.add(background2);
		this.add(background);
	}
}
