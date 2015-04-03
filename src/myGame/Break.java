package myGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;


public class Break extends JLabel {
	private static final long serialVersionUID = 1L;
	public Button bouton1 = new Button(" ", 1, "p1");
	public Button bouton2 = new Button(" ", 25, "p3");
	public Button bouton3  = new Button(" ", 52, "p2");
 
	public Break(){

		JLabel background=new JLabel();

		JLabel background2=new JLabel();
		JLabel top=new JLabel();
		top.setLayout(new FlowLayout());
    	background.setLayout(new FlowLayout());

    	background2.setLayout(new FlowLayout());
    	background2.setLayout(new GridLayout(5,100*Main.SreenSize,100*Main.SreenSize,10*Main.SreenSize)); 
    	background2.setPreferredSize(new Dimension(100*Main.SreenSize, 200*Main.SreenSize));

    	top.setPreferredSize(new Dimension(475*Main.SreenSize, 175*Main.SreenSize));
    	background.setPreferredSize(new Dimension(475*Main.SreenSize, 750*Main.SreenSize));
    	
    	
		
		bouton1.setPreferredSize(new Dimension(50*Main.SreenSize, 20*Main.SreenSize));		
		
		bouton3.setPreferredSize(new Dimension(5*Main.SreenSize, 20*Main.SreenSize));
		background.setOpaque(false);
		this.setOpaque(false);
		background2.setOpaque(false);
		
		background2.add(bouton1);;
		background2.add(bouton3);
		background2.add(bouton2);
		
		background.add(top, BorderLayout.NORTH);
		background.add(background2);
		this.setPreferredSize(new Dimension(965*Main.SreenSize, 540*Main.SreenSize));
    	this.setLayout(new FlowLayout());
		this.add(background);
		
	}
}
