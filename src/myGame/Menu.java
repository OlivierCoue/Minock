package myGame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class Menu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Button bouton1 = new Button(" ", 23*Main.SreenSize, "jouer");
	public Button bouton2 = new Button(" ", 9*Main.SreenSize, "option");
	public Button boutonNew = new Button("", 46, "newGame");
	public Button bouton3  = new Button(" ", 10*Main.SreenSize, "quitter");
	public Button bouton4  = new Button(" ", 10*Main.SreenSize, "scores");
	protected JPasswordField textField;
    protected JTextArea textArea;
    private String chemin; 
    private boolean playClicked=false; 
    private JLabel background;
    
	public boolean isPlayClicked() {	
		return playClicked;
	}
	public void setPlayClicked(boolean playClicked) {
		this.playClicked = playClicked;
	}
	public Menu(){		
		
		if(Main.SreenSize==1){chemin = "small";}
		else if(Main.SreenSize==2){chemin = "big";};

		background=new JLabel(new ImageIcon("image/"+chemin+"/menu/menu2.gif"));
		
		JPanel background2=new JPanel();
		JLabel top=new JLabel();
		JLabel separa=new JLabel();
		top.setLayout(new FlowLayout());
    	background.setLayout(new FlowLayout());
    	
    
    	background2.setPreferredSize(new Dimension(250*Main.SreenSize, 300*Main.SreenSize));
    	//DECALLER DROITE HAUT BOUTON
    	top.setPreferredSize(new Dimension(475*Main.SreenSize, 859*Main.SreenSize-(20/Main.SreenSize)));
    	separa.setPreferredSize(new Dimension(250*Main.SreenSize, 9*Main.SreenSize));
    	
		textField = new JPasswordField(5);
		textField.setFont(new Font("Impact", Font.PLAIN, 22*Main.SreenSize));
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBackground(new Color(1, 1, 1, 0f));
		textField.setBorder(null);
		bouton1.setPreferredSize(new Dimension(200*Main.SreenSize, 67*Main.SreenSize));
		boutonNew.setPreferredSize(new Dimension(90*Main.SreenSize, 67*Main.SreenSize));
		textField.setPreferredSize(new Dimension(75*Main.SreenSize, 67*Main.SreenSize));
		bouton2.setPreferredSize(new Dimension(65*Main.SreenSize, 21*Main.SreenSize));
		bouton3.setPreferredSize(new Dimension(65*Main.SreenSize, 21*Main.SreenSize));
		bouton4.setPreferredSize(new Dimension(65*Main.SreenSize, 21*Main.SreenSize));
		background.setOpaque(false);
		background2.setOpaque(false);
		
		boutonNew.setVisible(false);
		textField.setVisible(false);
		
		background2.add(bouton1);
		
		background2.add(boutonNew);
		background2.add(textField);
		background2.add(separa);
		background2.add(bouton4);
		background2.add(bouton2);
		background2.add(bouton3);
		
		background.add(top, BorderLayout.NORTH);
		background.add(background2);
		this.add(background);
		
		
	}
	
}	
