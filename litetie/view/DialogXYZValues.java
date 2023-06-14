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
import java.awt.Toolkit;
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
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;



@SuppressWarnings("serial")
public class DialogXYZValues extends JDialog{

	DecimalFormat decimal0 = new DecimalFormat("#0");
	DecimalFormat decimal1 = new DecimalFormat("#0.0");
	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal3 = new DecimalFormat("#0.000");

	private JPanel layoutPanel = null;
	private JButton cancel = null;

	//Input Titles for the textfields
	private JLabel labelXValue;
	private JLabel labelYValue;
	private JLabel labelZValue;
	
	private static JTextField textFieldYValue = null;
	private static JTextField textFieldXValue = null;
	private static JTextField textFieldZValue = null;
	
	private boolean okPushed;
	private boolean cancelPushed;
	
	private JCheckBox checkboxRelativeTo = null;

	public static String status;
	boolean badValue = false;
	
	protected Component dialogOwner;
	private static double xVal = 1;
	private static double yVal = 1;
	private static double zVal = 1;
	private static Coordinate currentCoordinate;
	private Coordinate oldCoordinate;
	private JPanel panel;
	private JButton ok;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension( 230, 180);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());
	
	
	public DialogXYZValues(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(bounds);
		this.setTitle("XYZ Values");
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
			gbl_layoutPanel.columnWeights = new double[]{1.0, 1.0};
			gbl_layoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
			layoutPanel.setLayout(gbl_layoutPanel);
			
			labelXValue = new JLabel();
			labelXValue.setText("X Value");
			labelXValue.setPreferredSize(new Dimension(35, 16));
			labelXValue.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gbc_labelXValue = new GridBagConstraints();
			gbc_labelXValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelXValue.weightx = 1.0;
			gbc_labelXValue.gridx = 0;
			gbc_labelXValue.insets = new Insets(10, 5, 5, 5);
			gbc_labelXValue.gridy = 0;
			layoutPanel.add(labelXValue, gbc_labelXValue);
			
			GridBagConstraints gbc_spinnerXValue = new GridBagConstraints();
			gbc_spinnerXValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerXValue.weightx = 1.0;
			gbc_spinnerXValue.gridx = 1;
			gbc_spinnerXValue.insets = new Insets(10, 0, 5, 5);
			gbc_spinnerXValue.gridy = 0;
			layoutPanel.add(getTextFieldXValue(), gbc_spinnerXValue);
			
			labelYValue = new JLabel();
			labelYValue.setText("Y Value");
			labelYValue.setPreferredSize(new Dimension(35, 16));
			labelYValue.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gbc_labelYValue = new GridBagConstraints();
			gbc_labelYValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelYValue.weightx = 1.0;
			gbc_labelYValue.gridx = 0;
			gbc_labelYValue.insets = new Insets(2, 5, 5, 5);
			gbc_labelYValue.anchor = GridBagConstraints.WEST;
			gbc_labelYValue.gridy = 1;
			layoutPanel.add(labelYValue, gbc_labelYValue);
			
			GridBagConstraints gbc_spinnerYValue = new GridBagConstraints();
			gbc_spinnerYValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerYValue.weightx = 1.0;
			gbc_spinnerYValue.gridx = 1;
			gbc_spinnerYValue.insets = new Insets(0, 0, 5, 5);
			gbc_spinnerYValue.gridy = 1;
			layoutPanel.add(getTextFieldYValue(), gbc_spinnerYValue);
			GridBagConstraints gbc_labelZValue = new GridBagConstraints();
			gbc_labelZValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelZValue.insets = new Insets(2, 5, 5, 5);
			gbc_labelZValue.gridx = 0;
			gbc_labelZValue.gridy = 2;
			layoutPanel.add(getLabelZValue(), gbc_labelZValue);

			GridBagConstraints gbc_spinnerZValue = new GridBagConstraints();
			gbc_spinnerZValue.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerZValue.insets = new Insets(0, 0, 5, 5);
			gbc_spinnerZValue.gridx = 1;
			gbc_spinnerZValue.gridy = 2;
			layoutPanel.add(getTextFieldZValue(), gbc_spinnerZValue);
			GridBagConstraints gbc_checkboxRelativeTo = new GridBagConstraints();
			gbc_checkboxRelativeTo.gridwidth = 2;
			gbc_checkboxRelativeTo.insets = new Insets(0, 0, 5, 0);
			gbc_checkboxRelativeTo.gridx = 0;
			gbc_checkboxRelativeTo.gridy = 3;
			layoutPanel.add(getCheckboxRelativeTo(), gbc_checkboxRelativeTo);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.SOUTH;
			gbc_panel.gridwidth = 2;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 4;
			layoutPanel.add(getPanel(), gbc_panel);	
		}
		return layoutPanel;
	}
	
	JTextField getTextFieldXValue() {
		if (textFieldXValue == null) {
			textFieldXValue = new JTextField();
			textFieldXValue.setPreferredSize(new Dimension(100, 22));
			textFieldXValue.setMinimumSize(new Dimension(100, 22));
			textFieldXValue.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldXValue().getText());
						getTextFieldXValue().setText(Double.toString(d));
						getTextFieldXValue().setForeground(Color.black);
							
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldXValue().setForeground(Color.red);
						JOptionPane.showMessageDialog(dialogOwner, "The value cannot be parsed.\nThe default value has been returned.");
						textFieldXValue.setText(decimal3.format(xVal));
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
			textFieldXValue.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldXValue().getText());
						getTextFieldXValue().setText(Double.toString(d));
						getTextFieldXValue().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldXValue().setForeground(Color.red);
						System.out.println("Caught at getTextFieldXValue() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldXValue;
	}
	JTextField getTextFieldYValue() {
		if (textFieldYValue == null) {
			textFieldYValue = new JTextField();
			textFieldYValue.setPreferredSize(new Dimension(100, 22));
			textFieldYValue.setMinimumSize(new Dimension(100, 22));
			textFieldYValue.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldYValue().getText());
						getTextFieldYValue().setText(Double.toString(d));
						getTextFieldYValue().setForeground(Color.black);
							
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldYValue().setForeground(Color.red);
						JOptionPane.showMessageDialog(dialogOwner, "The value cannot be parsed.\nThe default value has been returned.");
						textFieldYValue.setText(decimal3.format(xVal));
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
			textFieldYValue.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldYValue().getText());
						getTextFieldYValue().setText(Double.toString(d));
						getTextFieldYValue().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldYValue().setForeground(Color.red);
						System.out.println("Caught at getTextFieldYValue() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldYValue;
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
	private JTextField setTextFieldXValue() {return textFieldXValue;}
	private JTextField setTextFieldYValue() {return textFieldYValue;}
	private JTextField setTextFieldZValue() {return textFieldZValue;}
//SET METHODS END	
	
	public static Coordinate showBox(JPanel frame) throws NumberFormatException, NegativeNumberException{
		DialogXYZValues dialog = new DialogXYZValues(frame);
		
		xVal = Double.parseDouble(dialog.getTextFieldXValue().getText());
		yVal = Double.parseDouble(dialog.getTextFieldYValue().getText());
		zVal = Double.parseDouble(dialog.getTextFieldZValue().getText());
		Coordinate coordinate = new Coordinate(xVal,yVal,zVal);
		return showBox(frame, coordinate);
	}

	public static Coordinate showBox(JPanel canvasPanel, Coordinate coordinate) {
		DialogXYZValues dialog = new DialogXYZValues(canvasPanel);
		dialog.setModal(true);
		
			try {

				xVal = coordinate.getX();
				yVal = coordinate.getY();
				zVal = coordinate.getZ();
				
				dialog.oldCoordinate = new Coordinate(xVal,yVal,zVal);
				
				dialog.setTextFieldXValue().setText(dialog.decimal3.format(xVal));
				dialog.setTextFieldYValue().setText(dialog.decimal3.format(yVal));
				dialog.setTextFieldZValue().setText(dialog.decimal3.format(zVal));
				
				currentCoordinate = dialog.oldCoordinate;
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(canvasPanel,
						"One of the values inputed is not an integer",
						"DialogXYZValues", JOptionPane.ERROR_MESSAGE);
				dialog.setTextFieldXValue().setText(Double.toString(currentCoordinate.getX()));
				dialog.setTextFieldYValue().setText(Double.toString(currentCoordinate.getY()));
				dialog.setTextFieldZValue().setText(Double.toString(currentCoordinate.getZ()));
			}
		
		do {
			
			dialog.setVisible(true);
			
			if (dialog.okPushed == true) {
				try {
					xVal = Double.parseDouble(dialog.getTextFieldXValue().getText()); 
					dialog.getTextFieldXValue().setForeground(Color.black);			
				} 
				catch (NumberFormatException e) {
					dialog.getTextFieldXValue().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("X = Bad Value(s) - showBox() method - NumberFormatException");					
				}
				
				try {
						yVal =Double.parseDouble(dialog.getTextFieldYValue().getText());
						dialog.getTextFieldYValue().setForeground(Color.black);
					
				} 
				catch (NumberFormatException e) {
					dialog.getTextFieldYValue().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Y = Bad Value(s) - showBox() method - NumberFormatException");					
				}
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
							null;
				}
			}
		}	
		while(dialog.badValue);
		
		return currentCoordinate;

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
						setTextFieldXValue().setText("0");
						setTextFieldYValue().setText("0");
						setTextFieldZValue().setText("0");
						checkboxRelativeTo.setForeground(Color.RED);
						System.out.println(oldCoordinate);
					}
					else if(!(checkboxRelativeTo.isSelected())){
						getTextFieldXValue().setText(decimal3.format(xVal));
						getTextFieldYValue().setText(decimal3.format(yVal));
						getTextFieldZValue().setText(decimal3.format(zVal));
						checkboxRelativeTo.setForeground(Color.BLACK);
						
					}
				}
			});
		}
		return checkboxRelativeTo;
	}
	private Coordinate getCurrentCoordinate(){
		if( ( Double.parseDouble(getTextFieldXValue().getText()) < 0 || Double.parseDouble(getTextFieldYValue().getText()) < 0 ||	Double.parseDouble(getTextFieldZValue().getText()) < 0) && !(checkboxRelativeTo.isSelected())){
			currentCoordinate = new Coordinate(xVal,yVal,zVal);
			
		}
		else if (checkboxRelativeTo.isSelected()){
			xVal = oldCoordinate.getX() + Double.parseDouble(getTextFieldXValue().getText());
			yVal = oldCoordinate.getY() + Double.parseDouble(getTextFieldYValue().getText());
			zVal = oldCoordinate.getZ() + Double.parseDouble(getTextFieldZValue().getText()); 
			currentCoordinate = new Coordinate(xVal,yVal,zVal);
		}
		
		else if (!(checkboxRelativeTo.isSelected())&&( Double.parseDouble(getTextFieldXValue().getText()) >= 0 || Double.parseDouble(getTextFieldYValue().getText()) >= 0 ||	Double.parseDouble(getTextFieldZValue().getText()) >= 0) ){
			xVal = Double.parseDouble(getTextFieldXValue().getText());
			yVal = Double.parseDouble(getTextFieldYValue().getText());
			zVal = Double.parseDouble(getTextFieldZValue().getText()); 
			currentCoordinate = new Coordinate(xVal,yVal,zVal);
		}
		return currentCoordinate;
		
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
