
/**
 * 
 * @author Brent Buffham
 * @category Properties
 * @version 1.0
 * @description Dialog for modifying the Hole Properties.
 * @date 30th December 2013 
 */

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
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import litetie.model.Coordinate;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.ZeroArgumentException;

import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.UIManager;



@SuppressWarnings("serial")
public class DialogPropertiesHole extends JDialog{

	static DecimalFormat decimal0 = new DecimalFormat("#0");
	static DecimalFormat decimal1 = new DecimalFormat("#0.0");
	static DecimalFormat decimal2 = new DecimalFormat("#0.00");
	static DecimalFormat decimal3 = new DecimalFormat("#0.000");
	
	SpinnerNumberModel bearingModel = new SpinnerNumberModel(0.00, 0, 359.99999, 1.1);
	SpinnerNumberModel shapeModel = new SpinnerNumberModel(1, 1, 10, 1);
	SpinnerNumberModel dipModel = new SpinnerNumberModel(90.0, 1.0, 90.0, 1.1);
	SpinnerNumberModel diameterModel = new SpinnerNumberModel(89, 0.1, 1000, 1);
	private JPanel layoutPanel = null;
	private JButton cancel = null;

	//Input Titles for the textfields
	private JLabel labelXValue;
	private JLabel labelYValue;
	private JLabel labelZValue;
	
	
	
	private boolean okPushed;
	private boolean cancelPushed;
	
	private JCheckBox checkboxRelativeTo = null;

	public static String status;
	boolean badValue = false;
	
	protected Component dialogOwner;
	private static double xVal = 1;
	private static double yVal = 1;
	private static double zVal = 1;
	private static String labelOne;
	private static String labelTwo;
	private static String labelThree;
	private static double diameter; //mm
	private static double holeLength;//meters
	private static double benchHeight;//meters
	private static double bearing;//degrees
	private static double floorRL;//meters
	private static double toeRL;//meters
	private static double angle;//degrees
	private static double subdrill;//meters
	private static int shape;
	private static String shapeLabel;
	private static Color color;
	
	private static Hole currentHole;
	private static Hole oldHole;
	private JPanel panel;
	private JButton ok;
	
	private static JTextField textFieldYValue = null;
	private static JTextField textFieldXValue = null;
	private static JTextField textFieldZValue = null;
	private static JTextField textFieldHoleLength;
	private static JTextField textFieldBenchHeight;
	private static JTextField textFieldFloorLevel;
	private static JTextField textFieldToeLevel;
	private static JTextField textFieldSubdrill;
	private static JTextField textFieldLabelOne;
	private static JTextField textFieldLabelTwo;
	private static JTextField textFieldLabelThree;
	private static JSpinner spinnerBearing;
	private static JSpinner spinnerDip;
	private static JSpinner spinnerShape;
	private static JSpinner spinnerDiameter;
	private static JPanel colorWell;

	private JLabel labelHoleLength;
	private JLabel labelBearing;
	private JLabel labelDip;
	private JLabel labelShape;
	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel labelLabelOne;
	private JLabel labelLabelTwo;
	private JLabel labelLabelThree;
	private JLabel labelColor;
	private JLabel labelDiameter;
	private JLabel labelBenchHeight;
	private JLabel labelFloorLevel;
	private JLabel labelToeLevel;
	private JLabel labelSubdrill;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension( 230, 570);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());
	private JLabel lableShapeType;
	private Color currentColor;
	
	
	public DialogPropertiesHole(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(bounds);
		this.setTitle("Hole Properties");
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
			gbl_layoutPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
			gbl_layoutPanel.columnWeights = new double[]{1.0, 1.0};
			gbl_layoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
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
			GridBagConstraints gbc_labelHoleLength = new GridBagConstraints();
			gbc_labelHoleLength.insets = new Insets(0, 0, 5, 5);
			gbc_labelHoleLength.anchor = GridBagConstraints.EAST;
			gbc_labelHoleLength.gridx = 0;
			gbc_labelHoleLength.gridy = 4;
			layoutPanel.add(getLabelHoleLength(), gbc_labelHoleLength);
			GridBagConstraints gbc_textFieldHoleLength = new GridBagConstraints();
			gbc_textFieldHoleLength.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldHoleLength.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldHoleLength.gridx = 1;
			gbc_textFieldHoleLength.gridy = 4;
			layoutPanel.add(getTextFieldHoleLength(), gbc_textFieldHoleLength);
			GridBagConstraints gbc_lblBenchHeight = new GridBagConstraints();
			gbc_lblBenchHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblBenchHeight.anchor = GridBagConstraints.EAST;
			gbc_lblBenchHeight.gridx = 0;
			gbc_lblBenchHeight.gridy = 5;
			layoutPanel.add(getLabelBenchHeight(), gbc_lblBenchHeight);
			GridBagConstraints gbc_textFieldBenchHeight = new GridBagConstraints();
			gbc_textFieldBenchHeight.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldBenchHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldBenchHeight.gridx = 1;
			gbc_textFieldBenchHeight.gridy = 5;
			layoutPanel.add(getTextFieldBenchHeight(), gbc_textFieldBenchHeight);
			GridBagConstraints gbc_lblFloorLevel = new GridBagConstraints();
			gbc_lblFloorLevel.insets = new Insets(0, 0, 5, 5);
			gbc_lblFloorLevel.anchor = GridBagConstraints.EAST;
			gbc_lblFloorLevel.gridx = 0;
			gbc_lblFloorLevel.gridy = 6;
			layoutPanel.add(getLabelFloorLevel(), gbc_lblFloorLevel);
			GridBagConstraints gbc_textFieldFloorLevel = new GridBagConstraints();
			gbc_textFieldFloorLevel.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldFloorLevel.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldFloorLevel.gridx = 1;
			gbc_textFieldFloorLevel.gridy = 6;
			layoutPanel.add(getTextFieldFloorLevel(), gbc_textFieldFloorLevel);
			GridBagConstraints gbc_lblToeLevel = new GridBagConstraints();
			gbc_lblToeLevel.insets = new Insets(0, 0, 5, 5);
			gbc_lblToeLevel.anchor = GridBagConstraints.EAST;
			gbc_lblToeLevel.gridx = 0;
			gbc_lblToeLevel.gridy = 7;
			layoutPanel.add(getLabelToeLevel(), gbc_lblToeLevel);
			GridBagConstraints gbc_textFieldToeLevel = new GridBagConstraints();
			gbc_textFieldToeLevel.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldToeLevel.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldToeLevel.gridx = 1;
			gbc_textFieldToeLevel.gridy = 7;
			layoutPanel.add(getTextFieldToeLevel(), gbc_textFieldToeLevel);
			GridBagConstraints gbc_lblSubdrill = new GridBagConstraints();
			gbc_lblSubdrill.insets = new Insets(0, 0, 5, 5);
			gbc_lblSubdrill.anchor = GridBagConstraints.EAST;
			gbc_lblSubdrill.gridx = 0;
			gbc_lblSubdrill.gridy = 8;
			layoutPanel.add(getLabelSubdrill(), gbc_lblSubdrill);
			GridBagConstraints gbc_textFieldSubdrill = new GridBagConstraints();
			gbc_textFieldSubdrill.insets = new Insets(0, 0, 5, 5);
			gbc_textFieldSubdrill.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldSubdrill.gridx = 1;
			gbc_textFieldSubdrill.gridy = 8;
			layoutPanel.add(getTextFieldSubdrill(), gbc_textFieldSubdrill);
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.gridwidth = 2;
			gbc_separator.insets = new Insets(0, 0, 5, 0);
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 9;
			layoutPanel.add(getSeparator(), gbc_separator);
			GridBagConstraints gbc_lblLabelOne = new GridBagConstraints();
			gbc_lblLabelOne.insets = new Insets(0, 0, 5, 5);
			gbc_lblLabelOne.anchor = GridBagConstraints.EAST;
			gbc_lblLabelOne.gridx = 0;
			gbc_lblLabelOne.gridy = 10;
			layoutPanel.add(getLabelLabelOne(), gbc_lblLabelOne);
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 10;
			layoutPanel.add(getTextFieldLabelOne(), gbc_textField);
			GridBagConstraints gbc_lblLabelTwo = new GridBagConstraints();
			gbc_lblLabelTwo.insets = new Insets(0, 0, 5, 5);
			gbc_lblLabelTwo.anchor = GridBagConstraints.EAST;
			gbc_lblLabelTwo.gridx = 0;
			gbc_lblLabelTwo.gridy = 11;
			layoutPanel.add(getLabelLabelTwo(), gbc_lblLabelTwo);
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 5, 5);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 1;
			gbc_textField_1.gridy = 11;
			layoutPanel.add(getTextFieldLabelTwo(), gbc_textField_1);
			GridBagConstraints gbc_lblLabelThree = new GridBagConstraints();
			gbc_lblLabelThree.insets = new Insets(0, 0, 5, 5);
			gbc_lblLabelThree.anchor = GridBagConstraints.EAST;
			gbc_lblLabelThree.gridx = 0;
			gbc_lblLabelThree.gridy = 12;
			layoutPanel.add(getLabelLabelThree(), gbc_lblLabelThree);
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.insets = new Insets(0, 0, 5, 5);
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.gridx = 1;
			gbc_textField_2.gridy = 12;
			layoutPanel.add(getTextFieldLabelThree(), gbc_textField_2);
			GridBagConstraints gbc_separator_1 = new GridBagConstraints();
			gbc_separator_1.gridwidth = 2;
			gbc_separator_1.insets = new Insets(0, 0, 5, 0);
			gbc_separator_1.gridx = 0;
			gbc_separator_1.gridy = 13;
			layoutPanel.add(getSeparator_1(), gbc_separator_1);
			GridBagConstraints gbc_lblDiameter = new GridBagConstraints();
			gbc_lblDiameter.anchor = GridBagConstraints.EAST;
			gbc_lblDiameter.insets = new Insets(0, 0, 5, 5);
			gbc_lblDiameter.gridx = 0;
			gbc_lblDiameter.gridy = 14;
			layoutPanel.add(getLabelDiameter(), gbc_lblDiameter);
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinner.insets = new Insets(0, 0, 5, 7);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 14;
			layoutPanel.add(getSpinnerDiameter(), gbc_spinner);
			GridBagConstraints gbc_lblBearing = new GridBagConstraints();
			gbc_lblBearing.anchor = GridBagConstraints.EAST;
			gbc_lblBearing.insets = new Insets(0, 0, 5, 5);
			gbc_lblBearing.gridx = 0;
			gbc_lblBearing.gridy = 15;
			layoutPanel.add(getLabelBearing(), gbc_lblBearing);
			GridBagConstraints gbc_spinnerBearing = new GridBagConstraints();
			gbc_spinnerBearing.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerBearing.insets = new Insets(0, 0, 5, 7);
			gbc_spinnerBearing.gridx = 1;
			gbc_spinnerBearing.gridy = 15;
			layoutPanel.add(getSpinnerBearing(), gbc_spinnerBearing);
			GridBagConstraints gbc_lblDip = new GridBagConstraints();
			gbc_lblDip.anchor = GridBagConstraints.EAST;
			gbc_lblDip.insets = new Insets(0, 0, 5, 5);
			gbc_lblDip.gridx = 0;
			gbc_lblDip.gridy = 16;
			layoutPanel.add(getLabelDip(), gbc_lblDip);
			GridBagConstraints gbc_spinnerDip = new GridBagConstraints();
			gbc_spinnerDip.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerDip.insets = new Insets(0, 0, 5, 7);
			gbc_spinnerDip.gridx = 1;
			gbc_spinnerDip.gridy = 16;
			layoutPanel.add(getSpinnerDip(), gbc_spinnerDip);
			GridBagConstraints gbc_lblShape = new GridBagConstraints();
			gbc_lblShape.anchor = GridBagConstraints.EAST;
			gbc_lblShape.insets = new Insets(0, 0, 5, 5);
			gbc_lblShape.gridx = 0;
			gbc_lblShape.gridy = 17;
			layoutPanel.add(getLabelShape(), gbc_lblShape);
			GridBagConstraints gbc_spinnerShape = new GridBagConstraints();
			gbc_spinnerShape.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerShape.insets = new Insets(0, 0, 5, 7);
			gbc_spinnerShape.gridx = 1;
			gbc_spinnerShape.gridy = 17;
			layoutPanel.add(getSpinnerShape(), gbc_spinnerShape);
			GridBagConstraints gbc_lblCircle = new GridBagConstraints();
			gbc_lblCircle.anchor = GridBagConstraints.EAST;
			gbc_lblCircle.gridwidth = 2;
			gbc_lblCircle.insets = new Insets(0, 0, 5, 10);
			gbc_lblCircle.gridx = 0;
			gbc_lblCircle.gridy = 18;
			layoutPanel.add(getLabelShapeType(), gbc_lblCircle);
			GridBagConstraints gbc_labelColor = new GridBagConstraints();
			gbc_labelColor.anchor = GridBagConstraints.EAST;
			gbc_labelColor.insets = new Insets(0, 0, 5, 5);
			gbc_labelColor.gridx = 0;
			gbc_labelColor.gridy = 19;
			layoutPanel.add(getLabelColor(), gbc_labelColor);
			GridBagConstraints gbc_colorWell = new GridBagConstraints();
			gbc_colorWell.fill = GridBagConstraints.BOTH;
			gbc_colorWell.insets = new Insets(0, 0, 5, 5);
			gbc_colorWell.gridx = 1;
			gbc_colorWell.gridy = 19;
			layoutPanel.add(getColorWell(), gbc_colorWell);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.SOUTH;
			gbc_panel.gridwidth = 2;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 20;
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
	private JTextField getTextFieldLabelOne() {
		if (textFieldLabelOne == null) {
			textFieldLabelOne = new JTextField();
			textFieldLabelOne.setPreferredSize(new Dimension(100, 22));
		}
		return textFieldLabelOne;
	}
	private JTextField getTextFieldLabelTwo() {
		if (textFieldLabelTwo == null) {
			textFieldLabelTwo = new JTextField();
			textFieldLabelTwo.setPreferredSize(new Dimension(100, 22));
		}
		return textFieldLabelTwo;
	}
	private JTextField getTextFieldLabelThree() {
		if (textFieldLabelThree == null) {
			textFieldLabelThree = new JTextField();
			textFieldLabelThree.setPreferredSize(new Dimension(100, 22));
		}
		return textFieldLabelThree;
	}
	private JTextField getTextFieldHoleLength() {
		if (textFieldHoleLength == null) {
			textFieldHoleLength = new JTextField();
			textFieldHoleLength.setPreferredSize(new Dimension(100, 22));
			textFieldHoleLength.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldHoleLength().getText());
						getTextFieldHoleLength().setText(Double.toString(d));
						getTextFieldHoleLength().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldHoleLength().setForeground(Color.red);
						System.out.println("Caught at getTextFieldHoleLength() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldHoleLength;
	}
	private JTextField getTextFieldBenchHeight() {
		if (textFieldBenchHeight == null) {
			textFieldBenchHeight = new JTextField();
			textFieldBenchHeight.setPreferredSize(new Dimension(100, 22));
			textFieldBenchHeight.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldBenchHeight().getText());
						getTextFieldBenchHeight().setText(Double.toString(d));
						getTextFieldBenchHeight().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldBenchHeight().setForeground(Color.red);
						System.out.println("Caught at getTextFieldBenchHeight() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldBenchHeight;
	}
	private JTextField getTextFieldFloorLevel() {
		if (textFieldFloorLevel == null) {
			textFieldFloorLevel = new JTextField();
			textFieldFloorLevel.setPreferredSize(new Dimension(100, 22));
			textFieldFloorLevel.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldFloorLevel().getText());
						getTextFieldFloorLevel().setText(Double.toString(d));
						getTextFieldFloorLevel().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldFloorLevel().setForeground(Color.red);
						System.out.println("Caught at getTextFieldFloorLevel() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldFloorLevel;
	}
	private JTextField getTextFieldToeLevel() {
		if (textFieldToeLevel == null) {
			textFieldToeLevel = new JTextField();
			textFieldToeLevel.setPreferredSize(new Dimension(100, 22));
			textFieldToeLevel.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldToeLevel().getText());
						getTextFieldToeLevel().setText(Double.toString(d));
						getTextFieldToeLevel().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldToeLevel().setForeground(Color.red);
						System.out.println("Caught at getTextFieldToeLevel() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldToeLevel;
	}
	private JTextField getTextFieldSubdrill() {
		if (textFieldSubdrill == null) {
			textFieldSubdrill = new JTextField();
			textFieldSubdrill.setPreferredSize(new Dimension(100, 22));
			textFieldSubdrill.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getTextFieldSubdrill().getText());
						getTextFieldSubdrill().setText(Double.toString(d));
						getTextFieldSubdrill().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getTextFieldSubdrill().setForeground(Color.red);
						System.out.println("Caught at getTextFieldSubdrill() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return textFieldSubdrill;
	}
	private JSpinner getSpinnerDiameter() {
		if (spinnerDiameter == null) {
			spinnerDiameter = new JSpinner(diameterModel);
			spinnerDiameter.setPreferredSize(new Dimension(100, 22));
			spinnerDiameter.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getSpinnerDiameter().getValue().toString());
						getSpinnerDiameter().setValue(d);
						getSpinnerDiameter().setForeground(Color.black);
						return true;
						}
					catch (NumberFormatException nfe){
						getSpinnerDiameter().setForeground(Color.red);
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
		}
		return spinnerDiameter;
	}
	private JSpinner getSpinnerShape() {
		if (spinnerShape == null) {
			spinnerShape = new JSpinner(shapeModel);
			spinnerShape.setPreferredSize(new Dimension(100, 22));
			spinnerShape.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getSpinnerShape().getValue().toString());
						getSpinnerShape().setValue(d);
						getSpinnerShape().setForeground(Color.black);
						return true;
						}
					catch (NumberFormatException nfe){
						getSpinnerShape().setForeground(Color.red);
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
		}
		return spinnerShape;
	}
	
	private JSpinner getSpinnerDip() {
		if (spinnerDip == null) {
			spinnerDip = new JSpinner(dipModel);
			spinnerDip.setPreferredSize(new Dimension(100, 22));
			spinnerDip.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getSpinnerDip().getValue().toString());
						getSpinnerDip().setValue(d);
						getSpinnerDip().setForeground(Color.black);
						return true;
						}
					catch (NumberFormatException nfe){
						getSpinnerDip().setForeground(Color.red);
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
		}
		return spinnerDip;
	}
	private JSpinner getSpinnerBearing() {
		if (spinnerBearing == null) {
			spinnerBearing = new JSpinner(bearingModel);
			spinnerBearing.setPreferredSize(new Dimension(100, 22));
			spinnerBearing.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getSpinnerBearing().getValue().toString());
							getSpinnerBearing().setValue(d);
							getSpinnerBearing().setForeground(Color.black);
						return true;
						}
					catch (NumberFormatException nfe){
						getSpinnerBearing().setForeground(Color.red);
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
		}
		return spinnerBearing;
	}


//SET METHODS START	
	private JTextField setTextFieldXValue() 		{return textFieldXValue;}
	private JTextField setTextFieldYValue() 		{return textFieldYValue;}
	private JTextField setTextFieldZValue() 		{return textFieldZValue;}
	private JTextField setTextFieldHoleLength() 	{return textFieldHoleLength;}
	private JTextField setTextFieldBenchHeight() 	{return textFieldBenchHeight;}
	private JTextField setTextFieldFloorLevel() 	{return textFieldFloorLevel;}
	private JTextField setTextFieldToeLevel() 		{return textFieldToeLevel;}
	private JTextField setTextFieldSubdrill() 		{return textFieldSubdrill;}
	private JTextField setTextFieldLabelOne() 		{return textFieldLabelOne;}
	private JTextField setTextFieldLabelTwo() 		{return textFieldLabelTwo;}
	private JTextField setTextFieldLabelThree() 	{return textFieldLabelThree;}
	private JSpinner setSpinnerShape() 				{return spinnerShape;}
	private JSpinner setSpinnerDip() 				{return spinnerDip;}
	private JSpinner setSpinnerBearing() 			{return spinnerBearing;}
	private JSpinner setSpinnerDiameter() 			{return spinnerDiameter;}
	private JPanel setColorWell()					{return colorWell;}
//SET METHODS END	
	
	public static Hole showBox(JPanel frame) throws NumberFormatException, NegativeNumberException, ZeroArgumentException{
		DialogPropertiesHole dialog = new DialogPropertiesHole(frame);
		
		xVal = 			Double.parseDouble(dialog.getTextFieldXValue().getText());
		yVal = 			Double.parseDouble(dialog.getTextFieldYValue().getText());
		zVal = 			Double.parseDouble(dialog.getTextFieldZValue().getText());
		labelOne = 		dialog.getTextFieldLabelOne().getText();
		labelTwo = 		dialog.getTextFieldLabelTwo().getText();
		labelThree = 	dialog.getTextFieldLabelThree().getText();
		diameter = 		(Double) dialog.getSpinnerDiameter().getValue();
		holeLength = 	Double.parseDouble(dialog.getTextFieldHoleLength().getText());
		benchHeight = 	Double.parseDouble(dialog.getTextFieldBenchHeight().getText());
		bearing = 		(Double) dialog.getSpinnerBearing().getValue();
		floorRL = 		Double.parseDouble(dialog.getTextFieldFloorLevel().getText());
		toeRL = 		Double.parseDouble(dialog.getTextFieldToeLevel().getText());
		angle = 		(Double) dialog.getSpinnerDiameter().getValue();
		subdrill = 		Double.parseDouble(dialog.getTextFieldSubdrill().getText());
		shape = 		(Integer) dialog.getSpinnerShape().getValue();
		color = 		dialog.getColorWell().getBackground();
		Hole hole = new Hole(
				yVal, xVal, zVal, 
				labelOne, labelTwo, labelThree, 
				diameter, holeLength, benchHeight, 
				bearing, floorRL, toeRL, 
				angle, subdrill, shape, 
				color);
		return showBox(frame, hole);
	}

	public static Hole showBox(JPanel canvasPanel, Hole hole) throws ZeroArgumentException, NegativeNumberException {
		DialogPropertiesHole dialog = new DialogPropertiesHole(canvasPanel);
		dialog.setModal(true);
		
			try {
				xVal = hole.getX();
				yVal = hole.getY();
				zVal = hole.getZ();
				labelOne = hole.getLabelOne();
				labelTwo = hole.getLabelTwo();
				labelThree = hole.getLabelThree();
				diameter = hole.getDiameter();
				holeLength = hole.getHoleLength();
				benchHeight = hole.getBench();
				bearing = hole.getBearing();
				floorRL = hole.getFloorRL();
				toeRL = hole.getToeRL();
				angle = hole.getAngle();
				subdrill = hole.getSubdrill();
				shape = hole.getShape();
				color = hole.getColor();

				DialogPropertiesHole.oldHole = new Hole(yVal, xVal, zVal, labelOne, labelTwo, labelThree, diameter, holeLength, benchHeight, bearing, floorRL, toeRL, angle, subdrill, shape, color);
				
				dialog.setTextFieldXValue().setText(		DialogPropertiesHole.decimal3.format(xVal));
				dialog.setTextFieldYValue().setText(		DialogPropertiesHole.decimal3.format(yVal));
				dialog.setTextFieldZValue().setText(		DialogPropertiesHole.decimal3.format(zVal));
				dialog.setTextFieldHoleLength().setText(	DialogPropertiesHole.decimal3.format(holeLength));
				dialog.setTextFieldBenchHeight().setText(	DialogPropertiesHole.decimal3.format(benchHeight));
				dialog.setTextFieldFloorLevel().setText(	DialogPropertiesHole.decimal3.format(floorRL));
				dialog.setTextFieldToeLevel().setText(		DialogPropertiesHole.decimal3.format(toeRL));
				dialog.setTextFieldSubdrill().setText(		DialogPropertiesHole.decimal3.format(subdrill));
				dialog.setTextFieldLabelOne().setText(		labelOne);
				dialog.setTextFieldLabelTwo().setText(		labelTwo);
				dialog.setTextFieldLabelThree().setText(	labelThree);
				dialog.setSpinnerBearing().setValue(		bearing);
				dialog.setSpinnerDip().setValue(			angle);
				dialog.setSpinnerDiameter().setValue(		diameter);
				dialog.setSpinnerShape().setValue(			shape);
				dialog.setColorWell().setBackground(		color);
				
				currentHole = oldHole;
				
			} 
			catch (IllegalArgumentException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(canvasPanel,
						"One of the values inputed is not valid",
						"HolePropertiesDialog", JOptionPane.ERROR_MESSAGE);

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

					try {
						holeLength = Double.parseDouble(dialog.getTextFieldHoleLength().getText());			
						dialog.getTextFieldHoleLength().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getTextFieldHoleLength().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Hole Length = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						benchHeight = Double.parseDouble(dialog.getTextFieldBenchHeight().getText());			
						dialog.getTextFieldBenchHeight().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getTextFieldBenchHeight().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Hole Length = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						floorRL = Double.parseDouble(dialog.getTextFieldFloorLevel().getText());			
						dialog.getTextFieldHoleLength().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getTextFieldHoleLength().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Hole Length = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						toeRL = Double.parseDouble(dialog.getTextFieldToeLevel().getText());			
						dialog.getTextFieldToeLevel().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getTextFieldToeLevel().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Toe Level = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						subdrill = Double.parseDouble(dialog.getTextFieldSubdrill().getText());			
						dialog.getTextFieldSubdrill().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getTextFieldSubdrill().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Subdrill = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						angle = (Double) (dialog.getSpinnerDip().getValue());			
						dialog.getSpinnerDip().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getSpinnerDip().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Hole Dip/Angle = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						bearing = (Double) (dialog.getSpinnerBearing().getValue());			
						dialog.getSpinnerBearing().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getSpinnerBearing().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Bearing = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						diameter = (Double) (dialog.getSpinnerDiameter().getValue());			
						dialog.getSpinnerDiameter().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getSpinnerDiameter().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Diameter = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						shape = (Integer) (dialog.getSpinnerShape().getValue());			
						dialog.getSpinnerShape().setForeground(Color.black);			
					} 
					catch (NumberFormatException e) {
						dialog.getSpinnerShape().setForeground(Color.red);
						dialog.badValue = true;
						System.out.println("Shape Integer = Bad Value(s) - showBox() method - NumberFormatException");					
					}
					try {
						labelOne = dialog.getTextFieldLabelOne().getText();			
						dialog.getTextFieldLabelOne().setForeground(Color.black);
					}
					catch (NullPointerException e) {
						dialog.setTextFieldLabelOne().setText("(null)");
					}
					try {
						labelTwo = dialog.getTextFieldLabelTwo().getText();			
						dialog.getTextFieldLabelTwo().setForeground(Color.black);
					}
					catch (NullPointerException e) {
						dialog.getTextFieldLabelTwo().setText("(null)");
					}
					try {
						labelThree = dialog.getTextFieldLabelThree().getText();			
						dialog.getTextFieldLabelThree().setForeground(Color.black);
					}
					catch (NullPointerException e) {
						dialog.getTextFieldLabelThree().setText("(null)");
					}
					try {
						color = dialog.getColorWell().getBackground();
					}
					catch (IllegalArgumentException e) {
						dialog.getColorWell().setBackground(Color.BLACK);
					}

				if (dialog.badValue) continue;
				if (dialog.okPushed == true  && !(dialog.badValue)) {
					return
							dialog.getCurrentHole();
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
		
		return currentHole;

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
						System.out.println(oldHole);
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
	private Hole getCurrentHole() throws NumberFormatException, ZeroArgumentException, NegativeNumberException{
		if( ( Double.parseDouble(getTextFieldXValue().getText()) < 0 || Double.parseDouble(getTextFieldYValue().getText()) < 0 ||	Double.parseDouble(getTextFieldZValue().getText()) < 0) && !(checkboxRelativeTo.isSelected())){
			currentHole = new Hole(
					yVal, xVal, zVal, 
					labelOne, labelTwo, labelThree, 
					diameter, holeLength, benchHeight, 
					bearing, floorRL, toeRL, 
					angle, subdrill, shape, 
					color);
			
		}
		else if (checkboxRelativeTo.isSelected()){
			xVal = oldHole.getX() + Double.parseDouble(getTextFieldXValue().getText());
			yVal = oldHole.getY() + Double.parseDouble(getTextFieldYValue().getText());
			zVal = oldHole.getZ() + Double.parseDouble(getTextFieldZValue().getText()); 
			currentHole = new Hole(
					yVal, xVal, zVal, 
					labelOne, labelTwo, labelThree, 
					diameter, holeLength, benchHeight, 
					bearing, floorRL, toeRL, 
					angle, subdrill, shape, 
					color);
		}
		
		else if (!(checkboxRelativeTo.isSelected())&&( Double.parseDouble(getTextFieldXValue().getText()) >= 0 || Double.parseDouble(getTextFieldYValue().getText()) >= 0 || Double.parseDouble(getTextFieldZValue().getText()) >= 0) ){
			xVal = Double.parseDouble(getTextFieldXValue().getText());
			yVal = Double.parseDouble(getTextFieldYValue().getText());
			zVal = Double.parseDouble(getTextFieldZValue().getText()); 
			currentHole = new Hole(
					yVal, xVal, zVal, 
					labelOne, labelTwo, labelThree, 
					diameter, holeLength, benchHeight, 
					bearing, floorRL, toeRL, 
					angle, subdrill, shape, 
					color);
		}
		return currentHole;
		
	}


	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException, ZeroArgumentException {
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
	
	private JLabel getLabelHoleLength() {
		if (labelHoleLength == null) {
			labelHoleLength = new JLabel("Hole Length");
		}
		return labelHoleLength;
	}

	private JLabel getLabelBenchHeight() {
		if (labelBenchHeight == null) {
			labelBenchHeight = new JLabel("Bench Height");
		}
		return labelBenchHeight;
	}
	private JLabel getLabelFloorLevel() {
		if (labelFloorLevel == null) {
			labelFloorLevel = new JLabel("Floor Level");
		}
		return labelFloorLevel;
	}
	private JLabel getLabelToeLevel() {
		if (labelToeLevel == null) {
			labelToeLevel = new JLabel("Toe Level");
		}
		return labelToeLevel;
	}
	private JLabel getLabelSubdrill() {
		if (labelSubdrill == null) {
			labelSubdrill = new JLabel("Subdrill");
		}
		return labelSubdrill;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
	
	private JLabel getLabelBearing() {
		if (labelBearing == null) {
			labelBearing = new JLabel("Bearing");
		}
		return labelBearing;
	}
	private JLabel getLabelDip() {
		if (labelDip == null) {
			labelDip = new JLabel("Dip");
		}
		return labelDip;
	}

	
	private JLabel getLabelShape() {
		if (labelShape == null) {
			labelShape = new JLabel("Shape");
		}
		return labelShape;
	}

	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setSize(200, 3);
			separator_1.setBackground(UIManager.getColor("Button.disabledText"));
			separator_1.setForeground(UIManager.getColor("Button.disabledText"));
		}
		return separator_1;
	}
	private JLabel getLabelLabelOne() {
		if (labelLabelOne == null) {
			labelLabelOne = new JLabel("Label One");
		}
		return labelLabelOne;
	}
	private JLabel getLabelLabelTwo() {
		if (labelLabelTwo == null) {
			labelLabelTwo = new JLabel("Label Two");
		}
		return labelLabelTwo;
	}
	private JLabel getLabelLabelThree() {
		if (labelLabelThree == null) {
			labelLabelThree = new JLabel("Label Three");
		}
		return labelLabelThree;
	}
	private JPanel getColorWell() {
		if (colorWell == null) {
			colorWell = new JPanel();
			colorWell.setBackground(Color.BLACK);
			colorWell.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			colorWell.setPreferredSize(new Dimension(100, 22));
			colorWell.addMouseListener(new java.awt.event.MouseListener() {
				
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPropertiesHole.this , "Change Current Colour", getColorWell().getBackground());
					if (currentColor != null) {
						getColorWell().setBackground(currentColor);
					}
				}
				public void mousePressed(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPropertiesHole.this , "Change Current Colour", getColorWell().getBackground());
					if (currentColor != null) {
						getColorWell().setBackground(currentColor);
					}
				}
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub	
				}
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub	
				}
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub	
				}
			});
		}
		return colorWell;
	}
	private JLabel getLabelColor() {
		if (labelColor == null) {
			labelColor = new JLabel("Colour");
		}
		return labelColor;
	}

	private JLabel getLabelDiameter() {
		if (labelDiameter == null) {
			labelDiameter = new JLabel("Diameter");
		}
		return labelDiameter;
	}
	private JLabel getLabelShapeType() {
		if (lableShapeType == null) {
			lableShapeType = new JLabel("Circle");
			lableShapeType.setFont(new Font("Dialog", Font.PLAIN, 10));
			ChangeListener listener = new ChangeListener() {
			      public void stateChanged(ChangeEvent e) {
			    	  int i = Integer.parseInt(getSpinnerShape().getValue().toString());
						
						
						if (i == 0){
							getLabelShapeType().setText("Image");
						}
						else if (i == 1){
							getLabelShapeType().setText("Circle");
						}
						else if (i == 2){
							getLabelShapeType().setText("Cross");
						}
						else if (i == 3){
							getLabelShapeType().setText("Triangle");
						}
						else if (i == 4){
							getLabelShapeType().setText("Square");
						}
						else if (i == 5){
							getLabelShapeType().setText("Pentagon");
						}
						else if (i == 6){
							getLabelShapeType().setText("Hexagon");
						}
						else if (i == 7){
							getLabelShapeType().setText("Star");
						}
						else if (i == 8){
							getLabelShapeType().setText("Filled Circle");
						}
						else if (i == 9){
							getLabelShapeType().setText("Filled Triangle");
						}
						else if (i == 10){
							getLabelShapeType().setText("Filled Square");
						}
			      }

			    };

			    spinnerShape.addChangeListener(listener);
		}
		
		return lableShapeType;
	}
}  //  @jve:decl-index=0:visual-constraint="10,-1"
