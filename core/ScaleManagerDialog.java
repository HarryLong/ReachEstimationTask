package core;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScaleManagerDialog extends JDialog{
	
	public static final String TITLE = "Scale Manager";		
	
	public MyActionListener actionListener;
	
	private ScalePanel scalePnl;
	private JButton closeBtn;
	
	public ScaleManagerDialog(JFrame parent)
	{
		super(parent, TITLE, true);
		actionListener = new MyActionListener();
		initComponents();
		initLayout();
	}
	
	private void initComponents()
	{
		scalePnl = new ScalePanel();
		
		closeBtn = new JButton(ActionCommands.CLOSE);
		closeBtn.setActionCommand(ActionCommands.CLOSE);
		closeBtn.addActionListener(actionListener);		
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
		c.weightx = 1;
		c.weighty = 0.9;
		contentPane.add(scalePnl,c);
		
		// Add the close btn
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 0.1;
		contentPane.add(closeBtn,c);
	}
	
	private void close()
	{
		dispose();
	}
	
//    @Override
//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//    }
	
	private class MyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(ae.getActionCommand())
			{
			case ActionCommands.CLOSE:
				ScaleManagerDialog.this.close();
				break;
			}
		}
	}
	
	private class ActionCommands
	{
		public static final String CLOSE = "Close";
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
	        
	        // Draw center line
	        g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
	    }
	}
}
