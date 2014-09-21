package core;

import helper.ConfigurationUtils;
import helper.Utils;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.ParserConfigurationException;

import modes.DesignPanel;
import modes.ExperimentPanel;

import org.xml.sax.SAXException;

import dataholders.CustomExceptions.XMLParsingExeption;
import dataholders.RunData;

public class MainWindow extends JFrame {
	public static final String TITLE = "Reach Estimation Experiment";
	
	public static int _scaleFactor = 1;
	
	private MyActionListener actionListener;
	
	private DesignPanel designPanel;
	private ExperimentPanel experimentPanel;
	private JMenu experimentMenu;
	private JFileChooser fileChooser;
	
	enum Mode {
		Experiment,
		Design
	}
	private Mode currentMode;
	
	public MainWindow() {
		super(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(900, 900));
		setLocationRelativeTo(null);	
		actionListener = new MyActionListener();
		reloadScalingFactor();
		
		designPanel = new DesignPanel(new RunData());
		experimentPanel = new ExperimentPanel();
		currentMode = Mode.Experiment; // Experiment by default		
		
		fileChooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "XML Files", "xml");
	    fileChooser.setFileFilter(filter);
	    
		initMenu();
		initUI();
		setVisible(true);
	}
	
	private void initUI()
	{
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		
		c.weightx = 1;
		c.weighty = 1;
		add(designPanel,c);
		add(experimentPanel,c);
		
		experimentMenu.setEnabled(false);
		designPanel.setVisible(false);
	}
	
	private void updateMainPanel()
	{
		System.out.println("Updating main panel to " + currentMode + " mode");
		
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	boolean displayExperiment = (currentMode == Mode.Experiment);
		    	
		    	experimentPanel.setVisible(displayExperiment);
		    	designPanel.setVisible(!displayExperiment);
		    	experimentMenu.setEnabled(!displayExperiment);
		    	
				repaint();
		    }
		});
		System.out.println("Done");
	}
	
	private void initMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		
		//********FILE MENU*****************************************************
		JMenu fileMenu = new JMenu("File");
		
		// Load
		JMenuItem loadMI = new JMenuItem(ActionCommands.LOAD);
		loadMI.setActionCommand(ActionCommands.LOAD);
		loadMI.addActionListener(actionListener);
		
		// Exit
		JMenuItem exitMI = new JMenuItem(ActionCommands.EXIT);
		exitMI.setActionCommand(ActionCommands.EXIT);
		exitMI.addActionListener(actionListener);
		
		fileMenu.add(loadMI);
		fileMenu.add(exitMI);
		//**********************************************************************
		
		
		//********MODE MENU*****************************************************
		JMenu modeMenu = new JMenu("Mode");
		
		// experiment mode
		ButtonGroup modeBG = new ButtonGroup();
		JRadioButtonMenuItem experimentModeMI = new JRadioButtonMenuItem(ActionCommands.EXPERIMENT_MODE);
		experimentModeMI.setActionCommand(ActionCommands.EXPERIMENT_MODE);
		experimentModeMI.addActionListener(actionListener);
		modeBG.add(experimentModeMI);
		
		JRadioButtonMenuItem editModeMI = new JRadioButtonMenuItem(ActionCommands.EDIT_MODE);
		editModeMI.setActionCommand(ActionCommands.EDIT_MODE);
		editModeMI.addActionListener(actionListener);
		modeBG.add(editModeMI);
		
		experimentModeMI.setSelected(true);
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

		//********EXPERIMENT MENU*************************************************
		experimentMenu = new JMenu("Design");

		// Save
		JMenuItem newMI = new JMenuItem(ActionCommands.NEW);
		newMI.setActionCommand(ActionCommands.NEW);
		newMI.addActionListener(actionListener);
		
		// Save
		JMenuItem saveMI = new JMenuItem(ActionCommands.SAVE);
		saveMI.setActionCommand(ActionCommands.SAVE);
		saveMI.addActionListener(actionListener);
		
		experimentMenu.add(newMI);
		experimentMenu.add(saveMI);

		//****************************************
		menuBar.add(fileMenu);
		menuBar.add(modeMenu);
		menuBar.add(settingsMenu);
		menuBar.add(experimentMenu);

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
		ScaleManagerDialog smDialog = new ScaleManagerDialog(this, _scaleFactor);
		Dimension screenSize = Utils.getScreenSize();
		Dimension dSize = new Dimension((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
		smDialog.setSize(dSize);
		setLocationRelativeTo(null);
		
		smDialog.setVisible(true);
	}
	
	public void reloadScalingFactor()
	{
		// Load the scaling factor
		_scaleFactor = ConfigurationUtils.loadScalingFactorFromFile();
		System.out.println("Scaling factor = " + _scaleFactor);
	}
	
	private class MyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae) {
			switch(ae.getActionCommand())
			{
			case ActionCommands.LOAD:
				{
				    int returnVal = fileChooser.showOpenDialog(MainWindow.this);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	RunData loadedData = new RunData();
				    	File file = fileChooser.getSelectedFile();
						try {
							loadedData = RunData.loadFromFile(fileChooser.getSelectedFile());
						} catch (XMLParsingExeption
								| ParserConfigurationException | SAXException
								| IOException e) {
							System.err.println("Failed to load run configuration from file: " + file.getAbsolutePath());
						}
				    	if(currentMode == Mode.Design)
				    		designPanel.setRunData(loadedData);
				    }		
				}
			    break;
			case ActionCommands.SAVE:
				{
					int returnVal = fileChooser.showOpenDialog(MainWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	RunData.writeToFile(fileChooser.getSelectedFile(), designPanel.getRunData());
					}		
				}
				break;
			case ActionCommands.NEW:
			designPanel.setRunData(new RunData());
			break;
			case ActionCommands.EXIT:
				exit();
				break;
			case ActionCommands.EXPERIMENT_MODE:
				if(currentMode != Mode.Experiment)
				{
					currentMode = Mode.Experiment;
					updateMainPanel();
				}
				break;
			case ActionCommands.EDIT_MODE:
				if(currentMode != Mode.Design)
				{
					currentMode = Mode.Design;
					updateMainPanel();
				}
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
		
		// Experiment
		public static final String SAVE = "Save";
		public static final String NEW = "New...";
	}
}
