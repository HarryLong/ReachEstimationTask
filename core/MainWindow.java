package core;

import helper.Utils;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import modes.DesignPanel;
import conf.Constants;

public class MainWindow extends JFrame {
	public static final String TITLE = "Reach Estimation Experiment";

	
	private MyActionListener actionListener;
	
	private DesignPanel designPanel;
	
	public MainWindow() {
		super(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		//********FILE MENU*****************************************************
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
		//**********************************************************************
		
		
		//********MODE MENU*****************************************************
		JMenu modeMenu = new JMenu("Mode");
		
		// experiment mode
		JRadioButtonMenuItem experimentModeMI = new JRadioButtonMenuItem(ActionCommands.EXPERIMENT_MODE);
		experimentModeMI.setActionCommand(ActionCommands.EXPERIMENT_MODE);
		experimentModeMI.addActionListener(actionListener);
		
		JRadioButtonMenuItem editModeMI = new JRadioButtonMenuItem(ActionCommands.EDIT_MODE);
		editModeMI.setActionCommand(ActionCommands.EDIT_MODE);
		editModeMI.addActionListener(actionListener);
		
		modeMenu.add(experimentModeMI);
		modeMenu.add(editModeMI);
		//**********************************************************************
		
		//********SETTINGS MENU*************************************************
		JMenu settingsMenu = new JMenu("Settings");
		
		// Scale Manager
		JMenuItem scaleMgrMI = new JMenuItem(ActionCommands.SCALE_MANAGER);
		scaleMgrMI.setActionCommand(ActionCommands.SCALE_MANAGER);
		scaleMgrMI.addActionListener(actionListener);
		
		settingsMenu.add(scaleMgrMI);
		//**********************************************************************

		menuBar.add(fileMenu);
		menuBar.add(modeMenu);
		menuBar.add(settingsMenu);
		
		setJMenuBar(menuBar);
	}
	
	public void exit()
	{
		int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", null, JOptionPane.YES_NO_OPTION);
		
		if(ret == JOptionPane.YES_OPTION)
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));			
	}
	
	public void showScalingManager()
	{
		ScaleManagerDialog smDialog = new ScaleManagerDialog(this);
		Dimension screenSize = Utils.getScreenSize();
		Dimension dSize = new Dimension((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
		smDialog.setSize(dSize);
		setLocationRelativeTo(null);
		
		smDialog.setVisible(true);
	}
	
	private class MyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(ae.getActionCommand())
			{
			case ActionCommands.LOAD:
				System.out.println("Not yet Implemented...");
				break;
			case ActionCommands.EXIT:
				exit();
				break;
			case ActionCommands.EXPERIMENT_MODE:
				System.out.println("Not yet Implemented...");
				break;
			case ActionCommands.EDIT_MODE:
				System.out.println("Not yet Implemented...");
				break;
			case ActionCommands.SCALE_MANAGER:
			default:
				MainWindow.this.showScalingManager();
				
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
		public static final String EXPERIMENT_MODE = "Experiment";
		public static final String EDIT_MODE = "Edit";
		
		// Settings
		public static final String SCALE_MANAGER = "Scale Manager";
	}
}
