package modes;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import dataholders.Circle;

public class CircleDrawerPanel extends JPanel {
	Circle[] circles;
	
	public CircleDrawerPanel()
	{
		initLayout();
	}
	
	public void initLayout()
	{
		setBackground(Color.BLACK);
	}
	
	public void drawCircle(Graphics g, Circle c)
	{
		int r = c.getRadius();
		int d = r*2; // diameter
		int height = getHeight();
		
		System.out.println("Height: " + height);
		System.out.println("R: " + r);

		int x = ((getWidth()/2) - r); // Always at center
//		int y = (height) - (int) (c.getOffset()*height);
		
//		y = Math.max(0, Math.min(height-d, y));
//
//		System.out.println("Drawing circle @ [" + x + "," + y + "]");
//		
//		g.setColor(c.getColor());
//    	g.fillOval(x, y, d, d);
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
