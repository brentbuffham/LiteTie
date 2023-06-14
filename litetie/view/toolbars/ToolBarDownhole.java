package litetie.view.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class ToolBarDownhole extends JToolBar implements ActionListener {

	
	//Toggle Selection Buttons
	JToggleButton toggleDet1 = new JToggleButton("Det #1");
	JToggleButton toggleDet2 = new JToggleButton("Det #2");
	JToggleButton toggleDet3 = new JToggleButton("Det #3");
	JToggleButton toggleDet4 = new JToggleButton("Det #4");
	
	JButton showHideButton = new JButton(new ImageIcon(ToolBarDownhole.class.getResource("/icons_LiteTie/showeye.png")));
	
	//Text Field inputs
	JTextField detText1 = new JTextField("000");
	JTextField detText2 = new JTextField("000");
	JTextField detText3 = new JTextField("000");
	JTextField detText4 = new JTextField("000");

	
	Dimension textFieldSize = new Dimension(60,20);
	Dimension buttonSize = new Dimension(60,20);
	Dimension panelSize = new Dimension(420,27);
	
	JPanel tools = new JPanel();
	
	ButtonGroup toggleButtons = new ButtonGroup();
	
	//Constructor
	/**
	 * <code>ToolBarDownhole toolbar = new ToolBarDownhole();</code> to call the ToolBar.<br><br>
	 * This creates a <i>"Nimbus Styled toolbar"</i> containing four <code>JToggleButtons</code>
	 * and six <code>JTextFields</code>.
	 * 
	 * The <code>JToggleButton</code>'s <code>actionCommand</code>s are:<br>
	 * <code>toggleDet1</code>, 
	 * <code>toggleDet2</code>,<br>  
	 * <code>toggleDet3</code>,
	 * <code>toggleDet6</code>.<br> 
	 */
	public ToolBarDownhole() {
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
		//Set Text Field Sizes
		detText1.setSize(textFieldSize);
		detText2.setSize(textFieldSize);
		detText3.setSize(textFieldSize);
		detText4.setSize(textFieldSize);
		
		//Set the Toggle Button Sizes
		toggleDet1.setSize(buttonSize);
		toggleDet2.setSize(buttonSize);
		toggleDet3.setSize(buttonSize);
		toggleDet4.setSize(buttonSize);
		
		detText1.setHorizontalAlignment(JTextField.CENTER);
		detText2.setHorizontalAlignment(JTextField.CENTER);
		detText3.setHorizontalAlignment(JTextField.CENTER);
		detText4.setHorizontalAlignment(JTextField.CENTER);
		
		add(tools);
		
		tools.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		showHideButton.setSize(new Dimension(20, 30));
		
//		add(showHideButton); //Implement Later if you want to.
		
		tools.add(toggleDet1);
		tools.add(detText1);
		tools.add(toggleDet2);
		tools.add(detText2);
		tools.add(toggleDet3);
		tools.add(detText3);
		tools.add(toggleDet4);
		tools.add(detText4);
		
		toggleButtons.add(toggleDet1);
		toggleButtons.add(toggleDet2);
		toggleButtons.add(toggleDet3);
		toggleButtons.add(toggleDet4);
		
		toggleDet1.setActionCommand("toggleDet1");
		toggleDet2.setActionCommand("toggleDet2");
		toggleDet3.setActionCommand("toggleDet3");
		toggleDet4.setActionCommand("toggleDet4");
	}
	//Returns the toggle button det 1
	public JToggleButton getToggleDet1(){
		return
				toggleDet1;
	}
	//Returns the toggle button det 2
	public JToggleButton getToggleDet2(){
		return
				toggleDet2;
	}
	//Returns the toggle button det 3
	public JToggleButton getToggleDet3(){
		return
				toggleDet3;
	}
	//Returns the toggle button det 4
	public JToggleButton getToggleDet4(){
		return
				toggleDet4;
	}

	//Returns the det texts 1
	public JTextField getDetText1(){
		return
				detText1;
	}
	//Returns the det texts 2
	public JTextField getDetText2(){
		return
				detText2;
	}
	//Returns the det texts 3
	public JTextField getDetText3(){
		return
				detText3;
	}
	//Returns the det texts 4
	public JTextField getDetText4(){
		return
				detText4;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
