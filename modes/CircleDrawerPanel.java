package modes;

import helper.Utils;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import conf.Constants;
import core.MainWindow;
import dataholders.Circle;

public class CircleDrawerPanel extends JPanel {
	Circle[] circles;
	boolean designMode;

	public CircleDrawerPanel()
	{
		this(false);
	}
	
	public CircleDrawerPanel(boolean designMode)
	{
		this.designMode = designMode;
		initLayout();
	}
	
	public void initLayout()
	{
		setBackground(Color.BLACK);
	}
	
	public void drawCircle(Graphics g, Circle c)
	{
		int r = (int) Utils.convert(Constants.CIRCLE_RADIUS, Utils.UNIT.MM, Utils.UNIT.CM) * MainWindow._scaleFactor;
		int d = r*2; // diameter
		int height = getHeight() - d;
		
		System.out.println("Height: " + height);
		System.out.println("R: " + r);

		int x = ((getWidth()/2) - r); // Always at center		
		int y = height - c.getY();

		System.out.println("Drawing circle @ [" + x + "," + y + "]");
		
		g.setColor(c.getColor());
    	g.fillOval(x, y, d, d);
    	
    	if(designMode)
    	{
    		DecimalFormat df = new DecimalFormat("#.00"); 
    		g.setColor(Color.WHITE);
    		String distance = df.format(c.getY()/(double)MainWindow._scaleFactor) + " cm";
    		g.drawString(String.valueOf(c.getId()) + " - " + distance, x+r, y+r);
    	}
	}
	
	public void setCircles(Circle[] circles)
	{
		this.circles = circles;
		repaint();
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw center line
        g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
        
        if(circles != null)
        {
            for(Circle c : circles)
            {
            	drawCircle(g, c);
            }	
        }
    }
}
