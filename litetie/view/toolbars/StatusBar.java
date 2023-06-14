package litetie.view.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class StatusBar extends JPanel {

	
	//Toggle Selection Buttons
	public static JLabel statusLabel = new JLabel("Status...");
	public static JProgressBar progressBar = new JProgressBar();
	static final int PROGRESS_MINIMUM = 0;
	static final int PROGRESS_MAXIMUM = 100;
	//Constructor
	
	public StatusBar() {
		//set width of the toolbar
		setSize(new Dimension(500,27));
		
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
		setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
		add(statusLabel);
		add(progressBar);
		
		progressBar.setMinimum(PROGRESS_MINIMUM);
		progressBar.setMaximum(PROGRESS_MAXIMUM);
		
		statusLabel.setPreferredSize(new Dimension((int) (getSize().getWidth()-120), 27));
		progressBar.setPreferredSize(new Dimension(100,27));
	}

	public static JLabel getStatusLabel(){
		return
				statusLabel;
	}

	public static JProgressBar getProgressBar(){
		return
				progressBar;
	}
	public static void updateProgressBar(int newValue) {
		  progressBar.setValue(newValue);
		}
}

