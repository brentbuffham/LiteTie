package litetie.view.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ToolBarFileFunctions extends JToolBar implements ActionListener  {

	
	//Toggle Selection Buttons
	JButton buttonSave = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/save.png")));
	JButton buttonOpen = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/open.png")));
	JButton buttonUndo = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/undo.png")));
	JButton buttonRedo = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/redo.png")));
	JButton buttonSaveAs = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/saveas.png")));
	JButton buttonClose = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/close.png")));
	JButton buttonPrint = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/print.png")));
	
	JButton showHideButton = new JButton(new ImageIcon(ToolBarFileFunctions.class.getResource("/icons_LiteTie/showeye.png")));
	
	Dimension textFieldSize = new Dimension(60,20);
	Dimension buttonSize = new Dimension(50,50);
	Dimension panelSize = new Dimension(220,27);
	
	ButtonGroup toggleButtons = new ButtonGroup();
	
	//Constructor
	/**
	 * <code>ToolBarFileFunctions toolbar = new ToolBarFileFunctions();</code> to call the ToolBar.<br><br>
	 * This creates a <i>"Nimbus Styled toolbar"</i> containing view related JButtons, JToggleButtons</code>.
	 * 
	 * The <code>JToggleButton</code>'s <code>actionCommand</code>s are:<br>
	 * <code>undo</code>, 
	 * <code>redo</code>,<br>  
	 * <code>open</code>,
	 * <code>save</code>,
	 * <code>saveas</code>,
	 * <code>close</code>.<br> 
	 */
	public ToolBarFileFunctions() {
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
		
		//Set the Toggle Button Sizes
		buttonUndo.setSize(buttonSize);
		buttonRedo.setSize(buttonSize);
		buttonOpen.setSize(buttonSize);
		buttonSave.setSize(buttonSize);
		buttonSaveAs.setSize(buttonSize);
		buttonClose.setSize(buttonSize);
		buttonPrint.setSize(buttonSize);
		showHideButton.setSize(new Dimension(20, 30));
		setLayout(new GridLayout(1, 0, 0, 0));
		
//		add(showHideButton); //Implement Later if you want to.
		
		add(buttonUndo);
		add(buttonRedo);
		add(buttonOpen);
		add(buttonSave);
		add(buttonSaveAs);
		add(buttonClose);
		add(buttonPrint);

//		toggleButtons.add(buttonOpen);
//		toggleButtons.add(buttonSave);
//		toggleButtons.add(buttonUndo);
//		toggleButtons.add(buttonRedo);
		
		buttonUndo.setActionCommand("undo");
		buttonRedo.setActionCommand("redo");
		buttonOpen.setActionCommand("open");
		buttonSave.setActionCommand("save");
		buttonSaveAs.setActionCommand("saveas");
		buttonClose.setActionCommand("close");
		buttonPrint.setActionCommand("print");

	}
	//Returns the button undo
	public JButton getButtonUndo(){
		return
				buttonUndo;
	}
	//Returns the button redo
	public JButton getButtonRedo(){
		return
				buttonRedo;
	}
	//Returns the  button Open
	public JButton getButtonOpen(){
		return
				buttonOpen;
	}
	//Returns the  button Save
	public JButton getButtonSave(){
		return
				buttonSave;
	}
	//Returns the  button save As
	public JButton getButtonSaveAs(){
		return
				buttonSaveAs;
	}
	//Returns the  button close
	public JButton getButtonClose(){
		return
				buttonClose;
	}
	//Returns the  button close
	public JButton getButtonprint(){
		return
				buttonPrint;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
