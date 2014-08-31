package core;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import modes.DesignPanel;
import conf.Constants;

public class MainWindow extends JFrame {

	private MyActionListener actionListener;
	
	private DesignPanel designPanel;
	
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(Constants.TITLE);
		setSize(new Dimension(900, 900));
		setLocationRelativeTo(null);	
		actionListener = new MyActionListener();
		
		// init panels
		designPanel = new DesignPanel(Constants.CIRCLE_COUNT);
		
		initMenu();
		
		initLayout();
		
		setVisible(true);
	}
	
	public void initLayout()
	{
		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		
		// Base image
		add(designPanel, c);
	}
	
	private void initMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		
		/* FILE MENU */
		JMenu fileMenu = new JMenu("File");
		
		// Load
		JMenuItem loadImgMI = new JMenuItem(ActionCommands.LOAD);
		loadImgMI.setActionCommand(ActionCommands.LOAD);
		loadImgMI.addActionListener(actionListener);

		// Exit
		JMenuItem exitMI = new JMenuItem(ActionCommands.EXIT);
		exitMI.setActionCommand(ActionCommands.EXIT);
		exitMI.addActionListener(actionListener);
		
		fileMenu.add(loadImgMI);
		fileMenu.add(exitMI);
		
		/* MODE MENU */
		JMenu modeMenu = new JMenu("Mode");
		// experiment mode
		JRadioButtonMenuItem experimentModeMI = new JRadioButtonMenuItem(ActionCommands.EXPERIMENT);
		experimentModeMI.setActionCommand(ActionCommands.EXPERIMENT);
		experimentModeMI.addActionListener(actionListener);
		
		JRadioButtonMenuItem editModeMI = new JRadioButtonMenuItem(ActionCommands.EDIT);
		editModeMI.setActionCommand(ActionCommands.EDIT);
		editModeMI.addActionListener(actionListener);
		
		modeMenu.add(experimentModeMI);
		modeMenu.add(editModeMI);
		
		menuBar.add(fileMenu);
		menuBar.add(modeMenu);
		
		setJMenuBar(menuBar);
	}
	
	private class MyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(ae.getActionCommand())
			{
			case ActionCommands.LOAD:	
			default:
					
			}
		}
	}
	
	public static void main(String[] args)
	{
		new MainWindow();
	}
	
	private class ActionCommands
	{
		// General
		public static final String LOAD = "Load";
		public static final String EXIT = "Exit";
		
		// Modes
		public static final String EXPERIMENT = "Experiment";
		public static final String EDIT = "Edit";
	}
}
