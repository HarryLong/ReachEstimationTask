package modes;

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
import dataholders.RunData;

public class DesignPanel extends JPanel implements ListSelectionListener, MouseWheelListener, KeyEventDispatcher{
	CircleDrawerPanel circleDrawer;
	
	JList<Circle> circleProvider;
	
	boolean accelerateScroller;
	private RunData runData;
	
	public DesignPanel(RunData runData) {
		circleDrawer = new CircleDrawerPanel(true);
		
		circleProvider = new JList<Circle>();
		circleProvider.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		circleProvider.addListSelectionListener(this);

		setRunData(runData);
		
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

	public RunData getRunData()
	{
		return runData;
	}
	
	public void setRunData(RunData runData)
	{
		this.runData = runData;
		circleProvider.setListData(runData.getCircles());
		repaint();
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
				circlesToDraw[i] = runData.getCircles()[selectedIndices[i]];
			}
			circleDrawer.setCircles(circlesToDraw);
		}
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
		return false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		int[] selectedCircles = DesignPanel.this.circleProvider.getSelectedIndices();
		if(selectedCircles.length == 1)
		{
	        int count = mwe.getWheelRotation();
	        if(accelerateScroller)
	        	count *= 10;
	        
	        DesignPanel.this.runData.incrementY(selectedCircles[0], count);
	        DesignPanel.this.repaint();
		}		
	}
}
