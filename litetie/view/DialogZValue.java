package litetie.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.DecimalFormat;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import litetie.model.Coordinate;
import litetie.model.NegativeNumberException;



@SuppressWarnings("serial")
public class DialogZValue extends JDialog{

	DecimalFormat decimal0 = new DecimalFormat("#0");
	DecimalFormat decimal1 = new DecimalFormat("#0.0");
	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal3 = new DecimalFormat("#0.000");

	private JPanel layoutPanel = null;
	private JButton cancel = null;

	//Input Titles for the textfields
	private JLabel labelZValue;
	//Input Units for the textfields
	private JLabel labelUnitsZValue;
	
	private static JTextField textFieldZValue = null;
	
	private boolean okPushed;
	private boolean cancelPushed;
	
	private JCheckBox checkboxRelativeTo = null;

	public static String status;
	boolean badValue = false;
	
	protected Component dialogOwner;

	private static double zVal = 1;
	private static double currentValue;
	private static double oldValue;
	private JPanel panel;
	private JButton ok;

	public DialogZValue(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(new Rectangle(100, 100, 300, 190));
		this.setTitle("Adjust Z Values");
		this.setContentPane(getLayoutPanel());
	}
	
	private JPanel getLayoutPanel() {
		if (layoutPanel == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.gridy = 13;
			
			layoutPanel = new JPanel();
			layoutPanel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			layoutPanel.setMinimumSize(new Dimension(100, 22));
			GridBagLayout gbl_layoutPanel = new GridBagLayout();
			gbl_layoutPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
			gbl_layoutPanel.columnWeights = new double[]{1.0, 0.0, 1.0};
			gbl_layoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
			layoutPanel.setLayout(gbl_layoutPanel);
			
			GridBagConstraints gbc_labelZValue = new GridBagConstraints();
			gbc_labelZValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelZValue.insets = new Insets(2, 5, 5, 5);
			gbc_labelZValue.gridx = 0;
			gbc_labelZValue.gridy = 2;
			layoutPanel.add(getLabelZValue(), gbc_labelZValue);

			GridBagConstraints gbc_spinnerZValue = new GridBagConstraints();
			gbc_spinnerZValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerZValue.insets = new Insets(2, 5, 5, 5);
			gbc_spinnerZValue.gridx = 1;
			gbc_spinnerZValue.gridy = 2;
			layoutPanel.add(getTextFieldZValue(), gbc_spinnerZValue);
			GridBagConstraints gbc_labelUnitsZValue = new GridBagConstraints();
			gbc_labelUnitsZValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelUnitsZValue.insets = new Insets(0, 0, 5, 0);
			gbc_labelUnitsZValue.gridx = 2;
			gbc_labelUnitsZValue.gridy = 2;
			layoutPanel.add(getLabelUnitsZValue(), gbc_labelUnitsZValue);
			GridBagConstraints gbc_checkboxRelativeTo = new GridBagConstraints();
			gbc_checkboxRelativeTo.gridwidth = 3;
			gbc_checkboxRelativeTo.insets = new Insets(0, 0, 5, 5);
			gbc_checkboxRelativeTo.gridx = 0;
			gbc_checkboxRelativeTo.gridy = 3;
			layoutPanel.add(getCheckboxRelativeTo(), gbc_checkboxRelativeTo);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.SOUTH;
			gbc_panel.gridwidth = 3;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 4;
			layoutPanel.add(getPanel(), gbc_panel);	
		}
		return layoutPanel;
	}
	

	private JTextField getTextFieldZValue() {
		if (textFieldZValue == null) {
			textFieldZValue = new JTextField();
			textFieldZValue.setPreferredSize(new Dimension(100, 22));
			textFieldZValue.setMinimumSize(new Dimension(100, 22));
			textFieldZValue.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldZValue().getText());
						getTextFieldZValue().setText(Double.toString(d));
						getTextFieldZValue().setForeground(Color.black);
							
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldZValue().setForeground(Color.red);
						JOptionPane.showMessageDialog(dialogOwner, "The value cannot be parsed.\nThe default value has been returned.");
						textFieldZValue.setText(decimal3.format(zVal));
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
			textFieldZValue.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldZValue().getText());
						getTextFieldZValue().setText(Double.toString(d));
						getTextFieldZValue().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldZValue().setForeground(Color.red);
						System.out.println("Caught at getTextFieldZValue() - NumberFormatException");
						return false;
						}
				}
			});
				
		}
		return textFieldZValue;
	}
	


//SET METHODS START	
	private JTextField setTextFieldZValue() {return textFieldZValue;}
//SET METHODS END	
	
	public static Double showBox(JPanel frame) throws NumberFormatException, NegativeNumberException{
		DialogZValue dialog = new DialogZValue(frame);
		
		zVal = Double.parseDouble(dialog.getTextFieldZValue().getText());
		double value = (zVal);
		return showBox(frame, value);
	}

	public static Double showBox(JPanel canvasPanel, double value) {
		DialogZValue dialog = new DialogZValue(canvasPanel);
		dialog.setModal(true);
		
			try {

				zVal = value;
				dialog.oldValue = zVal;
				dialog.setTextFieldZValue().setText(dialog.decimal3.format(zVal));
				
				currentValue = oldValue;
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(canvasPanel,
						"One of the values inputed is not an integer",
						"DialogXYZValues", JOptionPane.ERROR_MESSAGE);
				dialog.setTextFieldZValue().setText(Double.toString(currentValue));
			}
		
		do {
			dialog.setVisible(true);
			if (dialog.okPushed == true) {
				try {	
					zVal = Double.parseDouble(dialog.getTextFieldZValue().getText());			
					dialog.getTextFieldZValue().setForeground(Color.black);			
				} 
				catch (NumberFormatException e) {
					dialog.getTextFieldZValue().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Z = Bad Value(s) - showBox() method - NumberFormatException");					
				}

				if (dialog.badValue) continue;
				if (dialog.okPushed == true  && !(dialog.badValue)) {
					return
							dialog.getCurrentCoordinate();
				}
				else {
					dialog.setVisible(true);
				}
				if (dialog.cancelPushed == true){
					System.out.println("Cancel button pushed");
					dialog.setVisible(false);
					
					return
							(Double) null;
				}
			}
		}	
		while(dialog.badValue);
		
		return currentValue;

	}	

	/**
	 * This method initializes saveAsPreferredCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCheckboxRelativeTo() {
		if (checkboxRelativeTo == null) {
			checkboxRelativeTo = new JCheckBox();
			checkboxRelativeTo.setToolTipText("");
			checkboxRelativeTo.setSelected(false);
			checkboxRelativeTo.setHorizontalAlignment(SwingConstants.CENTER);
			checkboxRelativeTo.setText("Adjust relative to current values");
			checkboxRelativeTo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(checkboxRelativeTo.isSelected()){
						setTextFieldZValue().setText("0");
						checkboxRelativeTo.setForeground(Color.RED);
						System.out.println(oldValue);
					}
					else if(!(checkboxRelativeTo.isSelected())){
						getTextFieldZValue().setText(decimal3.format(zVal));
						checkboxRelativeTo.setForeground(Color.BLACK);	
					}
				}
			});
		}
		return checkboxRelativeTo;
	}
	private double getCurrentCoordinate(){
		if( ( Double.parseDouble(getTextFieldZValue().getText()) < 0) && !(checkboxRelativeTo.isSelected())){
			currentValue = zVal;
			
		}
		else if (checkboxRelativeTo.isSelected()){
			zVal = oldValue + Double.parseDouble(getTextFieldZValue().getText()); 
			currentValue = zVal;
		}
		
		else if (!(checkboxRelativeTo.isSelected())&&(Double.parseDouble(getTextFieldZValue().getText()) >= 0) ){
			zVal = Double.parseDouble(getTextFieldZValue().getText()); 
			currentValue = zVal;
		}
		return currentValue;
		
	}


	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException {
		showBox(null);
		System.out.println(showBox(null));
		System.exit(0);
	}
	
	private JLabel getLabelZValue() {
		if (labelZValue == null) {
			labelZValue = new JLabel();
			labelZValue.setText("Z Value");
			labelZValue.setPreferredSize(new Dimension(35, 16));
			labelZValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelZValue;
	}
	private JLabel getLabelUnitsZValue() {
		if (labelUnitsZValue == null) {
			labelUnitsZValue = new JLabel();
			labelUnitsZValue.setText("Level");
			labelUnitsZValue.setPreferredSize(new Dimension(20, 25));
			labelUnitsZValue.setMinimumSize(new Dimension(20, 22));
		}
		return labelUnitsZValue;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panel.add(getCancel());
			panel.add(getOK());
		}
		return panel;
	}
	private JButton getOK() {

		if (ok == null) {
			ok = new JButton();
			ok.setText("OK");
			ok.setHorizontalAlignment(SwingConstants.RIGHT);
			ok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
						okPushed = true;
						cancelPushed = false;
						if(badValue) {
							setVisible(true);
						}
						else
							setVisible(false);
				}
			});
		}
		return ok;
	}

	private JButton getCancel() {	
		if (cancel == null) {
			cancel = new JButton();
			cancel.setHorizontalAlignment(SwingConstants.RIGHT);
			cancel.setText("Cancel");
			cancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okPushed = false;
					cancelPushed = true;
					setVisible(false);
				}
			});
		}
		return cancel;
	}
}  //  @jve:decl-index=0:visual-constraint="10,-1"
