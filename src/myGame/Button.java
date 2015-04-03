package myGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException; 

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Button extends JButton implements MouseListener{
	  /**
	 * 
	 */
	  private static final long serialVersionUID = 1L;
	  String name;
	  int left;
	  private Image img;
	  private String path, path2;
	  public Button(String str, int left, String path2){
	    super(str);
	    this.name = str;
	    this.left = left;
	    this.path2 = path2;
	    if(Main.SreenSize==1){path = "small";}
		else if(Main.SreenSize==2){path = "big";};
	    try {
	      img = ImageIO.read(new File("image/"+path+"/menu/normal/"+path2+".png"));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    this.addMouseListener(this);
	  }
	  
	  

	  public void paintComponent(Graphics g){
		this.setBorder(null);
		Color maron = new Color(14, 10, 5);
	    Graphics2D g2d = (Graphics2D)g;
	    g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	    g2d.setColor(maron);
		g2d.setFont(new Font("Impact", Font.PLAIN, 15*Main.SreenSize)); 
	    g2d.drawString(this.name, left, 22*Main.SreenSize);
	  }

	  //Méthode appelée lors du clic de souris
	  public void mouseClicked(MouseEvent event) {
		  try {
		      img = ImageIO.read(new File("image/"+path+"/menu/clic/"+path2+".png"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	  }

	  //Méthode appelée lors du survol de la souris
	  public void mouseEntered(MouseEvent event) { 
		  try {
	      img = ImageIO.read(new File("image/"+path+"/menu/hover/"+path2+".png"));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	  //Méthode appelée lorsque la souris sort de la zone du bouton
	  public void mouseExited(MouseEvent event) { 
		  try {
		      img = ImageIO.read(new File("image/"+path+"/menu/normal/"+path2+".png"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	  }

	  //Méthode appelée lorsque l'on presse le bouton gauche de la souris
	  public void mousePressed(MouseEvent event) { 
		  try {
		      img = ImageIO.read(new File("image/"+path+"/menu/clic/"+path2+".png"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	  }

	  //Méthode appelée lorsque l'on relâche le clic de souris
	  public void mouseReleased(MouseEvent event) { 
		  try {
		      img = ImageIO.read(new File("image/"+path+"/menu/normal/"+path2+".png"));
		    } catch (IOException e) {
		      e.printStackTrace();
		    }  
	  }

	}
