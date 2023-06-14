package litetie.view.mainView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import litetie.view.toolbars.StatusBar;
import litetie.view.toolbars.ToolBarDownhole;
import litetie.view.toolbars.ToolBarFileFunctions;
import litetie.view.toolbars.ToolBarTiming;
import litetie.view.toolbars.ToolBarToolFunctions;
import litetie.view.toolbars.ToolBarViewFunctions;
import litetie.view.toolbars.ToolBarVisibilityOptions;

import javax.swing.SwingConstants;
import javax.swing.JToggleButton;


@SuppressWarnings("serial")
public class MainViewLiteTie extends JFrame{

	//Preconstructed Panels 
//	JPanel statusBar = new StatusBar();
	ToolBarDownhole toolBarDownhole = new ToolBarDownhole();
	ToolBarFileFunctions toolBarFileFunctions = new ToolBarFileFunctions();
	ToolBarTiming toolBarTiming = new ToolBarTiming();
	ToolBarToolFunctions toolBarToolFunctions = new ToolBarToolFunctions();
	ToolBarViewFunctions toolBarViewFunctions = new ToolBarViewFunctions();
	ToolBarVisibilityOptions toolBarVisibilityOptions = new ToolBarVisibilityOptions();
	
	//Panel Holders
	//North
	JPanel northPanel = new JPanel();
	//East
	JPanel eastPanel = new JPanel();
	//South
	JPanel southPanel = new JPanel();
	//West
	JPanel westPanel = new JPanel();
	//Center
	JPanel centerPanel = new JPanel();
	//Create Layouts
	BorderLayout borderLayout = new BorderLayout();

	
	//Constructor
	public MainViewLiteTie(){
		//Set the Main Views Layout type
		getContentPane().setLayout(borderLayout);
		//Add the panels to the MainView
		getContentPane().add(northPanel, BorderLayout.NORTH);
		getContentPane().add(eastPanel, BorderLayout.EAST);
		
	
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(westPanel, BorderLayout.WEST);
		westPanel.add(toolBarToolFunctions);
		getContentPane().add(southPanel, BorderLayout.SOUTH);
		
		// Set the North Panel Layout to Flow
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		//Add the panels to the the top Panel
		northPanel.add(toolBarFileFunctions);
		northPanel.add(toolBarViewFunctions);
		northPanel.add(toolBarTiming);
		northPanel.add(toolBarDownhole);
		southPanel.add(toolBarVisibilityOptions);	
	}
	
}
