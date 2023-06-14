package litetie.view.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ToolBarViewFunctions extends JToolBar implements ActionListener  {

	
	//Toggle Selection Buttons
	JToggleButton buttonZoomToFit = new JToggleButton(new ImageIcon(ToolBarViewFunctions.class.getResource("/icons_LiteTie/zoomtofit.png")));
	JButton buttonZoomUser = new JButton(new ImageIcon(ToolBarViewFunctions.class.getResource("/icons_LiteTie/zoomuser.png")));
	JButton buttonZoomIn = new JButton(new ImageIcon(ToolBarViewFunctions.class.getResource("/icons_LiteTie/zoomin.png")));
	JButton buttonZoomOut = new JButton(new ImageIcon(ToolBarViewFunctions.class.getResource("/icons_LiteTie/zoomout.png")));
	JButton buttonStopAll = new JButton(new ImageIcon(ToolBarViewFunctions.class.getResource("/icons_LiteTie/stop2.png")));
	
	JButton showHideButton = new JButton(new ImageIcon(ToolBarViewFunctions.class.getResource("/icons_LiteTie/showeye.png")));
	
	Dimension textFieldSize = new Dimension(60,20);
	Dimension buttonSize = new Dimension(30,20);
	Dimension panelSize = new Dimension(180,27);
	
	ButtonGroup toggleButtons = new ButtonGroup();
	
	//Constructor
	/**
	 * <code>ToolBarViewFunctions toolbar = new ToolBarViewFunctions();</code> to call the ToolBar.<br><br>
	 * This creates a <i>"Nimbus Styled toolbar"</i> containing view related JButtons, JToggleButtons</code>.
	 * 
	 * The <code>JToggleButton</code>'s <code>actionCommand</code>s are:<br>
	 * <code>zoomIn</code>, 
	 * <code>zoomOut</code>,<br>  
	 * <code>zoomFit</code>,
	 * <code>zoomUser</code>.<br> 
	 */
	public ToolBarViewFunctions() {
		setSize(panelSize);
		
		//Set the Look and Feel
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		showHideButton.setSize(new Dimension(20, 30));
		setLayout(new GridLayout(0, 5, 0, 0));
		
//		add(showHideButton); //Implement Later if you want to.
		
		add(buttonZoomIn);
		add(buttonZoomOut);
		add(buttonZoomUser);
		add(buttonZoomToFit);
		add(buttonStopAll);

		toggleButtons.add(buttonZoomUser);
		toggleButtons.add(buttonZoomToFit);
		toggleButtons.add(buttonZoomIn);
		toggleButtons.add(buttonZoomOut);
		
		buttonZoomIn.setActionCommand("zoomIn");
		buttonZoomOut.setActionCommand("zoomOut");
		buttonZoomUser.setActionCommand("zoomUser");
		buttonZoomToFit.setActionCommand("zoomFit");
		buttonStopAll.setActionCommand("STOP");
	}
	//Returns the button Zoom In
	public JButton getButtonZoomIn(){
		return
				buttonZoomIn;
	}
	//Returns the button Zoom Out
	public JButton getButtonZoomOut(){
		return
				buttonZoomOut;
	}
	//Returns the button Zoom User
	public JButton getButtonZoomUser(){
		return
				buttonZoomUser;
	}
	//Returns the toggle button Zoom to Fit
	public JToggleButton getButtonZoomToFit(){
		return
				buttonZoomToFit;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
