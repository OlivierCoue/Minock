package myGame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class Scores extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int levelNumber=Window.LEVELNUMBER, i=0, level, deathNumber=0;
	private String pathSize, text="";
	private WriteScors ws = new WriteScors(); 
	private int[] savedTime = new int[levelNumber], minute = new int[levelNumber], second = new int[levelNumber], milisec = new int[levelNumber];
	public Button bouton1  = new Button("Back", 63, "bouton");
	private JLabel top=new JLabel();
	private JTextArea ta = new JTextArea();
	private JScrollPane sp = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	public Scores(){
		if(Main.SreenSize==1){pathSize="small";}
		else if(Main.SreenSize==2){pathSize="big";}
		JLabel background=new JLabel(new ImageIcon("image/"+pathSize+"/menu/scores.png"));
		JLabel background2=new JLabel();
		background.setLayout(new FlowLayout());
		background2.setLayout(new FlowLayout());
		background2.setPreferredSize(new Dimension(800, 1000));;
		top.setLayout(new FlowLayout());
    	top.setPreferredSize(new Dimension(0, 450*Main.SreenSize));
		bouton1.setPreferredSize(new Dimension(93*Main.SreenSize, 31*Main.SreenSize));;
		sp.getVerticalScrollBar().setUI(new MyScrollBarUI());
		sp.setPreferredSize(new Dimension(800, 600));
		sp.setBorder(null);
		sp.setWheelScrollingEnabled(true);
		ta.setHighlighter(null);
		ta.setFont(new Font("Impact", Font.PLAIN, 15*Main.SreenSize));
		ta.setForeground(Color.white);
		sp.setBackground(new Color(1, 1, 1, 0f));
		sp.getVerticalScrollBar().setOpaque(false);
		ta.setBackground(new Color(1, 1, 1, 0f));
		ta.setEditable(false);
		
		System.out.println(sp.getVerticalScrollBar().getValue());
		background2.add(top);
		background2.add(sp, BorderLayout.CENTER);
		background2.add(bouton1, BorderLayout.SOUTH);
		background.add(background2);
		this.add(background);
		this.setBackground(Color.BLACK);
		
	}
	
	
	public void update(){
		ws.AddScore(9999999, 1, 0);
		savedTime = ws.getSavedTime();
		deathNumber = savedTime[0];
		for(i=0; i<levelNumber; i++){
			minute[i]=savedTime[i+1]/60000;
			savedTime[i+1] -= savedTime[i+1]/60000;
			second[i]=(savedTime[i+1]-minute[i])/1000;
			savedTime[i+1] -= (savedTime[i+1]-minute[i])/1000;
			milisec[i] = savedTime[i+1]/100;
		}
		ta.setText("Scores :\n\nNombre de morts   "+deathNumber+"\n\n");
		for(i=0; i<levelNumber; i++){
			level=i+1;
			if(minute[i]<59){
				text = ta.getText()+ ("Niveau "+level+"              "+minute[i]+" : "+second[i]+" . "+milisec[i]+"\n\n");
			    ta.setText(text);
			}
			else{
				text = ta.getText()+("Niveau "+level+"              "+"Non défini"+"\n\n");
				ta.setText(text);
			}
		}
		ta.setCaretPosition(0);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);	
	}
	
	public class MyScrollBarUI extends BasicScrollBarUI {
		

	    @Override
	    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	    	g.translate(thumbBounds.x, thumbBounds.y);
	        g.setColor( Color.white );
	        g.fillRect( 0, 0, thumbBounds.width - 2, thumbBounds.height - 1 );
	        g.translate( -thumbBounds.x, -thumbBounds.y );
	    }
	    
	    @Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
	    	g.translate(trackBounds.x, trackBounds.y);
	    	Color white = new Color(0, 0, 1, 0f);
		    g.setColor(white);
	        g.translate( -trackBounds.x, -trackBounds.y );	
	    }
	    
	    @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }
	    
	    @Override    
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }
	    
	    private JButton createZeroButton() {
            JButton jbutton = new JButton();
            jbutton.setPreferredSize(new Dimension(0, 0));
            jbutton.setMinimumSize(new Dimension(0, 0));
            jbutton.setMaximumSize(new Dimension(0, 0));
            return jbutton;
        }
	}
}
