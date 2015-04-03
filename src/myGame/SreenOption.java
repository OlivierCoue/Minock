package myGame;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SreenOption extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JButton bouton1 = new JButton("1920x1080");
	public JButton bouton2 = new JButton("960x540");
	public JButton bouton3 = new JButton("Play");
	private JPanel container = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	public SreenOption() throws InterruptedException{
		this.setTitle("Mon Jeux");
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		bouton1.setPreferredSize(new Dimension(100, 40));	
		bouton2.setPreferredSize(new Dimension(100, 40));
		bouton3.setPreferredSize(new Dimension(100, 40));
		container.add(bouton1);
		container.add(bouton2);
		container.add(bouton3);
		
		bouton1.addActionListener(new Big());
		bouton2.addActionListener(new Small());
	
		
		this.setContentPane(container);
		this.setVisible(true);
		
	}
	
	class Small implements ActionListener{
		public void actionPerformed(ActionEvent a) {
				Main.SreenSize=1;
	    }
	  }
	class Big implements ActionListener{
		public void actionPerformed(ActionEvent a) {
				Main.SreenSize=2;	
	    }
	  }
	
	
}
