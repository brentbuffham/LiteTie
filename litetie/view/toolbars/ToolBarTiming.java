package litetie.view.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ToolBarTiming extends JToolBar implements ActionListener  {

	
	//Toggle Selection Buttons
	JToggleButton toggleTie1 = new JToggleButton("Tie #1");
	JToggleButton toggleTie2 = new JToggleButton("Tie #2");
	JToggleButton toggleTie3 = new JToggleButton("Tie #3");
	JToggleButton toggleTie4 = new JToggleButton("Tie #4");
	JToggleButton toggleTie5 = new JToggleButton("Tie #5");
	JToggleButton toggleTie6 = new JToggleButton("Tie #6");
	
	JButton showHideButton = new JButton(new ImageIcon(ToolBarTiming.class.getResource("/icons_LiteTie/showeye.png")));
	
	//Text Field inputs
	JTextField tieText1 = new JTextField("000");
	JTextField tieText2 = new JTextField("000");
	JTextField tieText3 = new JTextField("000");
	JTextField tieText4 = new JTextField("000");
	JTextField tieText5 = new JTextField("000");
	JTextField tieText6 = new JTextField("000");
	
	Dimension textFieldSize = new Dimension(60,20);
	Dimension buttonSize = new Dimension(60,20);
	Dimension panelSize = new Dimension(615,27);
	
	ButtonGroup toggleButtons = new ButtonGroup();
	
	//Constructor
	/**
	 * <code>ToolBarTiming toolbar = new ToolBarTiming();</code> to call the ToolBar.<br><br>
	 * This creates a <i>"Nimbus Styled toolbar"</i> containing six <code>JToggleButtons</code>
	 * and six <code>JTextFields</code>.
	 * 
	 * The <code>JToggleButton</code>'s <code>actionCommand</code>s are:<br>
	 * <code>toggleTie1</code>, 
	 * <code>toggleTie2</code>, 
	 * <code>toggleTie3</code>,<br>
	 * <code>toggleTie4</code>, 
	 * <code>toggleTie5</code>, 
	 * <code>toggleTie6</code>.<br> 
	 */
	public ToolBarTiming() {
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
		tieText1.setSize(textFieldSize);
		tieText2.setSize(textFieldSize);
		tieText3.setSize(textFieldSize);;
		tieText4.setSize(textFieldSize);
		tieText5.setSize(textFieldSize);
		tieText6.setSize(textFieldSize);
		
		//Set the Toggle Buton Sizes
		toggleTie1.setSize(buttonSize);
		toggleTie2.setSize(buttonSize);
		toggleTie3.setSize(buttonSize);
		toggleTie4.setSize(buttonSize);
		toggleTie5.setSize(buttonSize);
		toggleTie6.setSize(buttonSize);

		tieText1.setHorizontalAlignment(JTextField.CENTER);
		tieText2.setHorizontalAlignment(JTextField.CENTER);
		tieText3.setHorizontalAlignment(JTextField.CENTER);
		tieText4.setHorizontalAlignment(JTextField.CENTER);
		tieText5.setHorizontalAlignment(JTextField.CENTER);
		tieText6.setHorizontalAlignment(JTextField.CENTER);
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		showHideButton.setSize(new Dimension(20, 30));
		
//		add(showHideButton); //Implement Later if you want to.
		
		add(toggleTie1);
		add(tieText1);
		add(toggleTie2);
		add(tieText2);
		add(toggleTie3);
		add(tieText3);
		add(toggleTie4);
		add(tieText4);
		add(toggleTie5);
		add(tieText5);
		add(toggleTie6);
		add(tieText6);

		toggleButtons.add(toggleTie1);
		toggleButtons.add(toggleTie2);
		toggleButtons.add(toggleTie3);
		toggleButtons.add(toggleTie4);
		toggleButtons.add(toggleTie5);
		toggleButtons.add(toggleTie6);
		
		toggleTie1.setActionCommand("toggleTie1");
		toggleTie2.setActionCommand("toggleTie2");
		toggleTie3.setActionCommand("toggleTie3");
		toggleTie4.setActionCommand("toggleTie4");
		toggleTie5.setActionCommand("toggleTie5");
		toggleTie6.setActionCommand("toggleTie6");
	}
	//Returns the toggle button tie 1
	public JToggleButton getToggleTie1(){
		return
				toggleTie1;
	}
	//Returns the toggle button tie 2
	public JToggleButton getToggleTie2(){
		return
				toggleTie2;
	}
	//Returns the toggle button tie 3
	public JToggleButton getToggleTie3(){
		return
				toggleTie3;
	}
	//Returns the toggle button tie 4
	public JToggleButton getToggleTie4(){
		return
				toggleTie4;
	}
	//Returns the toggle button tie 5
	public JToggleButton getToggleTie5(){
		return
				toggleTie5;
	}
	//Returns the toggle button tie 6
	public JToggleButton getToggleTie6(){
		return
				toggleTie6;
	}
	//Returns the tie texts 1
	public JTextField getTieText1(){
		return
				tieText1;
	}
	//Returns the tie texts 2
	public JTextField getTieText2(){
		return
				tieText2;
	}
	//Returns the tie texts 3
	public JTextField getTieText3(){
		return
				tieText3;
	}
	//Returns the tie texts 4
	public JTextField getTieText4(){
		return
				tieText4;
	}
	//Returns the tie texts 5
	public JTextField getTieText5(){
		return
				tieText5;
	}
	//Returns the tie texts 6
	public JTextField getTieText6(){
		return
				tieText6;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
