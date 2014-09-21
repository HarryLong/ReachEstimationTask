package core;

import helper.ConfigurationUtils;
import helper.Coordinate2D;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ScaleManagerDialog extends JDialog implements MouseWheelListener, KeyEventDispatcher{
	
	public static final String TITLE = "Scale Manager";		
	
	public MyActionListener actionListener;
	
	private ScalePanel scalePnl;
	private JButton okBtn, closeBtn;
	private int scaleFactor; // 1 cm in pixels
	private boolean accelerateScroller;
	private MainWindow parent;
	
	public ScaleManagerDialog(MainWindow parent, int currentScaleFactor)
	{
		super(parent, TITLE, true);
		this.parent = parent;
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		setUndecorated(false); 
		actionListener = new MyActionListener();
		scaleFactor = currentScaleFactor;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this); 
		addMouseWheelListener(this);
		initComponents();
		initLayout();
	}
	
	private void initComponents()
	{
		scalePnl = new ScalePanel();
		
		closeBtn = new JButton(ActionCommands.CANCEL);
		closeBtn.setActionCommand(ActionCommands.CANCEL);
		closeBtn.addActionListener(actionListener);		
		
		okBtn = new JButton(ActionCommands.OK);
		okBtn.setActionCommand(ActionCommands.OK);
		okBtn.addActionListener(actionListener);		
	}
	
	private void initLayout()
	{	
		Container contentPane = getContentPane();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		// Add the scale panel
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 0.95;
		contentPane.add(scalePnl,c);
		
		// Add the close btn
		c.fill = GridBagConstraints.NONE;
		c.gridy = 1;
		c.weightx = 0.2;
		c.weighty = 0.05;
		c.gridwidth = 1;
		contentPane.add(okBtn,c);
		c.gridx = 1;
		contentPane.add(closeBtn,c);
	}
	
	private void close()
	{
		dispose();
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent ke) {
		if(ke.isControlDown())
		{
			accelerateScroller = true;
		}
		else
		{
			accelerateScroller = false;
		}
		
		return false; // Always dispatch to other components
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		System.out.println("Wheel scrolled");
		int count = mwe.getWheelRotation();
	    if(accelerateScroller)
	    	count *= 10;
	        
	    scaleFactor = Math.max(5, scaleFactor+count);
	    repaint();	
	}
	
	private class MyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(ae.getActionCommand())
			{
			case ActionCommands.CANCEL:
				ScaleManagerDialog.this.close();
				break;
			case ActionCommands.OK:
				ConfigurationUtils.writeScalingFactorToFile(scaleFactor);
				parent.reloadScalingFactor();
				ScaleManagerDialog.this.close();
				break;
			}
		}
	}
	
	private class ActionCommands
	{
		public static final String OK = "OK";
		public static final String CANCEL = "Cancel";
	}
	
	private class ScalePanel extends JPanel{
		
		public ScalePanel()
		{
			initLayout();
		}
		
		public void initLayout()
		{
			setBackground(Color.BLACK);
		}
		
	    @Override
	    public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        int h = getHeight();
	        int w = getWidth();	        
	        
	        // Draw horizontal line 1
	        int y = (int) (h/4d);
	        int x1 = (int) (w/4d);
	        int x2 = 3*x1;
	        System.out.println("Drawing line 1 from " + Coordinate2D.formatCoordinate(new Coordinate2D(x1, y)) + " to " + 
	        		Coordinate2D.formatCoordinate(new Coordinate2D(x2, y)));
	        g.drawLine(x1, y, x2, y);
	        
	        // Draw horizontal line 2
	        y += ScaleManagerDialog.this.scaleFactor; 
	        System.out.println("Drawing line 2 from " + Coordinate2D.formatCoordinate(new Coordinate2D(x1, y)) + " to " + 
	        		Coordinate2D.formatCoordinate(new Coordinate2D(x2, y)));
	        g.drawLine(x1, y, x2, y);
	        
	    }
	}
}
