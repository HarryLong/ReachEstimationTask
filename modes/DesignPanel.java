package modes;

import helper.Coordinate2D;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataholders.Circle;

public class DesignPanel extends JPanel implements ListSelectionListener, MouseWheelListener, KeyEventDispatcher{
	CircleDrawerPanel circleDrawer;
	
	Circle[] circles;
	JList<Circle> circleProvider;
	
	boolean accelerateScroller;
	
	public DesignPanel(int circleCount) {
		circleDrawer = new CircleDrawerPanel();
		
		circles = new Circle[circleCount];
		for(int i = 0; i < circleCount; i++)
		{
			circles[i] = new Circle(new Coordinate2D(0, 0), i+1);
		}
		
		circleProvider = new JList<Circle>(circles);
		circleProvider.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		circleProvider.addListSelectionListener(this);
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this); 
		addMouseWheelListener(this);
		
		initLayout();
	}
	
	private void initLayout()
	{
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.weighty = 1;
		
		// Circle Panel
		c.weightx = 0.9;
		add(circleDrawer, c);
		
		// List panel
		c.weightx = 0.1;
		add(circleProvider, c);
	}
	
	public int[] getSelectedCircles()
	{
		return circleProvider.getSelectedIndices();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		circleDrawer.repaint();
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting())
		{
			int[] selectedIndices = circleProvider.getSelectedIndices();
			int circleCount = selectedIndices.length;
			
			Circle[] circlesToDraw = new Circle[circleCount];
			
			for(int i = 0; i < circleCount; i++)
			{
				circlesToDraw[i] = circles[selectedIndices[i]];
			}
			circleDrawer.setCircles(circlesToDraw);
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent ke) {
		if(ke.isControlDown())
		{
			accelerateScroller = true;
			return true;
		}
		else
		{
			accelerateScroller = false;
			return false;
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		int[] selectedCircles = DesignPanel.this.getSelectedCircles();
		if(selectedCircles.length == 1)
		{
	        int count = mwe.getWheelRotation();
	        if(accelerateScroller)
	        	count *= 10;
	        
	        circles[selectedCircles[0]].incrementY(count);
	        DesignPanel.this.repaint();
		}		
	}
}
