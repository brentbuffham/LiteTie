package litetie;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

import litetie.model.Hole;
import litetie.model.NegativeNumberException;



@SuppressWarnings("serial")
public class RelabellerChooserPanel extends JDialog{


	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal0 = new DecimalFormat("#0");
	
	static float line = 0;
	static float dash = 0;
	static float space = 0;
	

	private JPanel layoutPanel = null;
	private JButton ok = null;
	private JButton cancel = null;

	private JLabel lineWidthLabel = null;
	private JLabel dashLengthLabel = null;
	private JLabel unitslabel1 = null;
	private JLabel unitsLabel2 = null;
	private static JSpinner numberOFZeros = null;
	
	private boolean okPushed;
	private boolean cancelPushed;
	
	private JCheckBox checkBoxPrefixWithZero = null;
	
	SpinnerNumberModel dashLengthModel = new SpinnerNumberModel(0, 0, 50, 1);
	SpinnerNumberModel numberOfZerosModel = new SpinnerNumberModel(0, 0, 50, 1);
	SpinnerNumberModel spaceLengthModel = new SpinnerNumberModel(0, 0, 50, 1);
	
	
	double startDash = dashLengthModel.getNumber().doubleValue(); 
	double startLine = numberOfZerosModel.getNumber().doubleValue();
	double startSpace = spaceLengthModel.getNumber().doubleValue();

	public static String status;
	private JLabel secondValueLabel1;
	private JLabel secondValueLabel2;
	protected Component dialogOwner;
	private static int lineWidth = 1;
	private int dashLength = 1;
	private int spaceLength =1;
	private static float[] dashSpacing = new float[2];
	private static BasicStroke currentStroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, null, 1);
	private JTextField firstValueTxt;
	private JTextField secondValueTxt;
	private JCheckBox chckbxLabel1;
	private JCheckBox chckbxLabel2;
	private JCheckBox chckbxLabel3;
	private JCheckBox chckbxLabelAllHoles;
	private JTextField txtLabel;
	private JSeparator separator;
	private JSeparator separator_1;

	
	

	
	public RelabellerChooserPanel(JPanel canvasPanel) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		super();
		initialize();
	}

	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		this.setResizable(false);
		this.setBounds(new Rectangle(100, 100, 390, 250));
		UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); // Basic Metal feel
		this.setTitle("Re-labeller Chooser");
		this.setContentPane(getLayoutPanel());
		this.ghostFields();
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
			gbl_layoutPanel.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_layoutPanel.columnWeights = new double[]{1.0, 1.0, 0.0};
			gbl_layoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 37};
			layoutPanel.setLayout(gbl_layoutPanel);
			GridBagConstraints gbc_chckbxLabelAllHoles = new GridBagConstraints();
			gbc_chckbxLabelAllHoles.gridwidth = 3;
			gbc_chckbxLabelAllHoles.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxLabelAllHoles.gridx = 0;
			gbc_chckbxLabelAllHoles.gridy = 0;
			layoutPanel.add(getChckbxLabelAllHoles(), gbc_chckbxLabelAllHoles);
			GridBagConstraints gbc_txtLabel = new GridBagConstraints();
			gbc_txtLabel.gridwidth = 3;
			gbc_txtLabel.insets = new Insets(0, 12, 5, 12);
			gbc_txtLabel.fill = GridBagConstraints.BOTH;
			gbc_txtLabel.gridx = 0;
			gbc_txtLabel.gridy = 1;
			layoutPanel.add(getTxtLabel(), gbc_txtLabel);
			GridBagConstraints gbc_separator_1 = new GridBagConstraints();
			gbc_separator_1.fill = GridBagConstraints.BOTH;
			gbc_separator_1.gridwidth = 3;
			gbc_separator_1.gridx = 0;
			gbc_separator_1.gridy = 2;
			layoutPanel.add(getSeparator_1(), gbc_separator_1);
			
			dashLengthLabel = new JLabel();
			dashLengthLabel.setMinimumSize(new Dimension(170, 22));
			dashLengthLabel.setText("First Value");
			dashLengthLabel.setPreferredSize(new Dimension(94, 16));
			dashLengthLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints angleLabelGridBag = new GridBagConstraints();
			angleLabelGridBag.fill = GridBagConstraints.BOTH;
			angleLabelGridBag.weightx = 1.0;
			angleLabelGridBag.gridx = 0;
			angleLabelGridBag.insets = new Insets(2, 10, 5, 5);
			angleLabelGridBag.gridy = 3;
			layoutPanel.add(dashLengthLabel, angleLabelGridBag);
			GridBagConstraints gbc_txtA = new GridBagConstraints();
			gbc_txtA.insets = new Insets(0, 0, 5, 5);
			gbc_txtA.gridx = 1;
			gbc_txtA.gridy = 3;
			layoutPanel.add(getTxtA(), gbc_txtA);
			
			unitsLabel2 = new JLabel();
			unitsLabel2.setPreferredSize(new Dimension(20, 25));
			unitsLabel2.setMinimumSize(new Dimension(20, 22));
			unitsLabel2.setText("(optional)");
			
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.BOTH;
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints8.gridy = 3;
			layoutPanel.add(unitsLabel2, gridBagConstraints8);
			GridBagConstraints gbc_lblSpaceLength = new GridBagConstraints();
			gbc_lblSpaceLength.fill = GridBagConstraints.BOTH;
			gbc_lblSpaceLength.insets = new Insets(2, 10, 5, 5);
			gbc_lblSpaceLength.gridx = 0;
			gbc_lblSpaceLength.gridy = 4;
			layoutPanel.add(getSecondValueLabel1(), gbc_lblSpaceLength);
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 4;
			layoutPanel.add(getTextField(), gbc_textField);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.fill = GridBagConstraints.BOTH;
			gbc_label.insets = new Insets(0, 0, 5, 10);
			gbc_label.gridx = 2;
			gbc_label.gridy = 4;
			layoutPanel.add(getSecondValueLabel2(), gbc_label);
			GridBagConstraints gbc_chckbxLabel = new GridBagConstraints();
			gbc_chckbxLabel.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxLabel.gridx = 0;
			gbc_chckbxLabel.gridy = 5;
			layoutPanel.add(getChckbxLabel1(), gbc_chckbxLabel);
			GridBagConstraints gbc_chckbxLabel_1 = new GridBagConstraints();
			gbc_chckbxLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_chckbxLabel_1.gridx = 1;
			gbc_chckbxLabel_1.gridy = 5;
			layoutPanel.add(getChckbxLabel2(), gbc_chckbxLabel_1);
			GridBagConstraints gbc_chckbxLabel_2 = new GridBagConstraints();
			gbc_chckbxLabel_2.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxLabel_2.gridx = 2;
			gbc_chckbxLabel_2.gridy = 5;
			layoutPanel.add(getChckbxLabel3(), gbc_chckbxLabel_2);
			GridBagConstraints gbc_separator = new GridBagConstraints();
			gbc_separator.fill = GridBagConstraints.BOTH;
			gbc_separator.gridwidth = 3;
			gbc_separator.gridx = 0;
			gbc_separator.gridy = 6;
			layoutPanel.add(getSeparator(), gbc_separator);
			
			
			
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridwidth = 2;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.insets = new Insets(0, 10, 5, 10);
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 7;
			layoutPanel.add(getCheckBoxPrefixWithZero(), gridBagConstraints);
			
			GridBagConstraints bearingValueGridBag = new GridBagConstraints();
			bearingValueGridBag.anchor = GridBagConstraints.WEST;
			bearingValueGridBag.weightx = 1.0;
			bearingValueGridBag.gridx = 2;
			bearingValueGridBag.insets = new Insets(10, 5, 5, 100);
			bearingValueGridBag.gridy = 7;
			layoutPanel.add(getNumberOfZerosSpinner(), bearingValueGridBag);
			
			GridBagConstraints cancelGridBag = new GridBagConstraints();
			cancelGridBag.gridx = 1;
			cancelGridBag.anchor = GridBagConstraints.WEST;
			cancelGridBag.insets = new Insets(5, 0, 12, 5);
			cancelGridBag.gridy = 8;
			layoutPanel.add(getCancel(), cancelGridBag);
			
			GridBagConstraints okGridBag = new GridBagConstraints();
			okGridBag.fill = GridBagConstraints.HORIZONTAL;
			okGridBag.gridx = 2;
			okGridBag.insets = new Insets(5, 0, 12, 12);
			okGridBag.gridy = 8;
			layoutPanel.add(getOK(), okGridBag);
			
			
		
		}
		return layoutPanel;
	}
	private JSpinner getNumberOfZerosSpinner() {
		if (numberOFZeros == null) {
			numberOFZeros = new JSpinner(numberOfZerosModel);
			numberOFZeros.setPreferredSize(new Dimension(100, 22));
			numberOFZeros.setMinimumSize(new Dimension(100, 22));
			numberOFZeros.setName("Number of Zeros");
			
			numberOFZeros.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						int i = Integer.parseInt(getNumberOfZerosSpinner().getValue().toString());
						getNumberOfZerosSpinner().setValue(i);
						line = i;
						
						getNumberOfZerosSpinner().setForeground(Color.black);	
						return true;
						}
					catch (NumberFormatException nfe){
						getNumberOfZerosSpinner().setForeground(Color.red);
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
			
			
			numberOFZeros.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getNumberOfZerosSpinner().getValue().toString());
						
							getNumberOfZerosSpinner().setValue(d);
							getNumberOfZerosSpinner().setForeground(Color.black);	
						
						return true;
						}
					catch (NumberFormatException nfe){
						getNumberOfZerosSpinner().setForeground(Color.red);
						System.out.println("Caught at JSpinner getBench() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return numberOFZeros;
	}
	
	private JButton getCancel() {	
		if (cancel == null) {
			cancel = new JButton();
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
	private JButton getOK() {

		if (ok == null) {
			ok = new JButton();
			ok.setText("OK");

			ok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					okPushed = true;
					cancelPushed = false;
					setVisible(false);
					
				}
			});
		}
		return ok;
	}
	private JLabel getSecondValueLabel1() {
		if (secondValueLabel1 == null) {
			secondValueLabel1 = new JLabel();
			secondValueLabel1.setMinimumSize(new Dimension(170, 22));
			secondValueLabel1.setText("Second Value");
			secondValueLabel1.setPreferredSize(new Dimension(94, 16));
			secondValueLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return secondValueLabel1;
	}
	private JLabel getSecondValueLabel2() {
		if (secondValueLabel2 == null) {
			secondValueLabel2 = new JLabel();
			secondValueLabel2.setText("If blank \"1,2,3...n\"");
			secondValueLabel2.setPreferredSize(new Dimension(20, 25));
			secondValueLabel2.setMinimumSize(new Dimension(20, 22));
		}
		return secondValueLabel2;
	}
	
	private JTextField getTxtA() {
		if (firstValueTxt == null) {
			firstValueTxt = new JTextField();
			firstValueTxt.setMinimumSize(new Dimension(120, 22));
			firstValueTxt.setPreferredSize(new Dimension(40, 22));
			firstValueTxt.setHorizontalAlignment(SwingConstants.CENTER);
			firstValueTxt.setText("A");
			firstValueTxt.setColumns(10);
		}
		return firstValueTxt;
	}
	private JTextField getTextField() {
		if (secondValueTxt == null) {
			secondValueTxt = new JTextField();
			secondValueTxt.setMinimumSize(new Dimension(120, 22));
			secondValueTxt.setPreferredSize(new Dimension(40, 22));
			secondValueTxt.setHorizontalAlignment(SwingConstants.CENTER);
			secondValueTxt.setText("1");
			secondValueTxt.setColumns(10);
		}
		return secondValueTxt;
	}
	private JCheckBox getChckbxLabel1() {
		if (chckbxLabel1 == null) {
			chckbxLabel1 = new JCheckBox("Label 1");
			chckbxLabel1.setHorizontalTextPosition(SwingConstants.LEADING);
			chckbxLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return chckbxLabel1;
	}
	private JCheckBox getChckbxLabel2() {
		if (chckbxLabel2 == null) {
			chckbxLabel2 = new JCheckBox("Label 2");
			chckbxLabel2.setMinimumSize(new Dimension(120, 22));
			chckbxLabel2.setHorizontalTextPosition(SwingConstants.LEADING);
			chckbxLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return chckbxLabel2;
	}
	private JCheckBox getChckbxLabel3() {
		if (chckbxLabel3 == null) {
			chckbxLabel3 = new JCheckBox("Label 3");
			chckbxLabel3.setHorizontalTextPosition(SwingConstants.LEADING);
			chckbxLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return chckbxLabel3;
	}
	private JCheckBox getChckbxLabelAllHoles() {
		if (chckbxLabelAllHoles == null) {
			chckbxLabelAllHoles = new JCheckBox("Label all holes with the same value");
			chckbxLabelAllHoles.setHorizontalTextPosition(SwingConstants.LEADING);
			chckbxLabelAllHoles.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return chckbxLabelAllHoles;
	}
	private JTextField getTxtLabel() {
		if (txtLabel == null) {
			txtLabel = new JTextField();
			txtLabel.setHorizontalAlignment(SwingConstants.CENTER);
			txtLabel.setText("Label");
			txtLabel.setColumns(10);
		}
		return txtLabel;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
			separator_1.setPreferredSize(new Dimension(0, 4));
		}
		return separator_1;
	}


//SET METHODS START	

	
//SET METHODS END	
	private void ghostFields() {
		
		if (getChckbxLabelAllHoles().isSelected() == true) {
			firstValueTxt.setEnabled(false);
			secondValueTxt.setEnabled(false);
			numberOFZeros.setEnabled(false);
			checkBoxPrefixWithZero.setSelected(false);
			checkBoxPrefixWithZero.setEnabled(false);
			firstValueTxt.setForeground(Color.LIGHT_GRAY);
			secondValueTxt.setForeground(Color.LIGHT_GRAY);
			numberOFZeros.setForeground(Color.LIGHT_GRAY);
		}
		
		else {
			firstValueTxt.setEnabled(true);
			secondValueTxt.setEnabled(true);
			numberOFZeros.setEnabled(true);
			checkBoxPrefixWithZero.setSelected(true);
			checkBoxPrefixWithZero.setEnabled(true);
			firstValueTxt.setForeground(Color.DARK_GRAY);
			secondValueTxt.setForeground(Color.DARK_GRAY);
			numberOFZeros.setForeground(Color.DARK_GRAY);
		
			}
		
		
	}

	public static LinkedHashSet showBox(JPanel frame) throws NumberFormatException, NegativeNumberException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		RelabellerChooserPanel newLabel = new RelabellerChooserPanel(frame);
		
		LinkedHashSet<Object> tempList = null;
		return showBox(frame,  tempList);
		
	}

	
	public static LinkedHashSet showBox(JPanel canvasPanel, LinkedHashSet<Object> fifo) throws  NegativeNumberException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		RelabellerChooserPanel relabeller = new RelabellerChooserPanel(canvasPanel);
		relabeller.setModal(true);
		if(fifo == null || fifo.isEmpty() ) {
			System.out.println("fifo is EMPTY");
		}
		else {
		Object [] h = fifo.toArray();
		
		do {
			
			relabeller.setVisible(true);
			
			if (relabeller.okPushed == true) {
				boolean badValue = false;

				if(relabeller.getChckbxLabelAllHoles().isSelected() == true) {


					for(int i = 0; i < fifo.size(); i++ ) {

						if(h[i] instanceof Hole) {

							if(relabeller.getChckbxLabel1().isSelected() == true && relabeller.getChckbxLabel2().isSelected() == true && relabeller.getChckbxLabel3().isSelected() == true) {
								((Hole) h[i]).setLabelOne(relabeller.getTxtLabel().getText());
								((Hole) h[i]).setLabelTwo(relabeller.getTxtLabel().getText());
								((Hole) h[i]).setLabelThree(relabeller.getTxtLabel().getText());
							}
							else if(relabeller.getChckbxLabel1().isSelected() == true && relabeller.getChckbxLabel2().isSelected() == true && relabeller.getChckbxLabel3().isSelected() == false) {
								((Hole) h[i]).setLabelOne(relabeller.getTxtLabel().getText());
								((Hole) h[i]).setLabelTwo(relabeller.getTxtLabel().getText());
							}
							else if(relabeller.getChckbxLabel1().isSelected() == true && relabeller.getChckbxLabel2().isSelected() == false && relabeller.getChckbxLabel3().isSelected() == false) {
								((Hole) h[i]).setLabelOne(relabeller.getTxtLabel().getText());
							}
							else if(relabeller.getChckbxLabel1().isSelected() == false && relabeller.getChckbxLabel2().isSelected() == true && relabeller.getChckbxLabel3().isSelected() == true) {
								((Hole) h[i]).setLabelTwo(relabeller.getTxtLabel().getText());
								((Hole) h[i]).setLabelThree(relabeller.getTxtLabel().getText());
							}
							else if(relabeller.getChckbxLabel1().isSelected() == false && relabeller.getChckbxLabel2().isSelected() == false && relabeller.getChckbxLabel3().isSelected() == true) {
								((Hole) h[i]).setLabelThree(relabeller.getTxtLabel().getText());
							}
							else if(relabeller.getChckbxLabel1().isSelected() == false && relabeller.getChckbxLabel2().isSelected() == true && relabeller.getChckbxLabel3().isSelected() == false) {
								((Hole) h[i]).setLabelTwo(relabeller.getTxtLabel().getText());

							}
							else if(relabeller.getChckbxLabel1().isSelected() == true && relabeller.getChckbxLabel2().isSelected() == false && relabeller.getChckbxLabel3().isSelected() == true) {
								((Hole) h[i]).setLabelOne(relabeller.getTxtLabel().getText());
								((Hole) h[i]).setLabelThree(relabeller.getTxtLabel().getText());
							}

						}
					}

				}
					
//				try {
//					line = Integer.parseInt(relabeller.getNumberOfZerosSpinner().getValue().toString());
//					lineWidth = (int) line;
//					 
//					relabeller.getNumberOfZerosSpinner().setForeground(Color.black);			
//				} 
//				catch (NumberFormatException e) {
//					relabeller.getNumberOfZerosSpinner().setForeground(Color.red);
//					badValue = true;
//					System.out.println("line Bad Value(s) - showBox() method - NumberFormatException");					
//				}
//				try {
//					dash = Integer.parseInt(relabeller.getDashLength().getValue().toString());	
//					dashLength = (int) dash;
//					 
//					relabeller.getNumberOfZerosSpinner().setForeground(Color.black);			
//				} 
//				catch (NumberFormatException e) {
//					relabeller.getNumberOfZerosSpinner().setForeground(Color.red);
//					badValue = true;
//					System.out.println("dash length Bad Value(s) - showBox() method - NumberFormatException");					
//				}
//				try {
//					space = Integer.parseInt(relabeller.getDashLength().getValue().toString());	
//					spaceLength = (int) space;			
//					 
//					relabeller.getNumberOfZerosSpinner().setForeground(Color.black);			
//				} 
//				catch (NumberFormatException e) {
//					relabeller.getNumberOfZerosSpinner().setForeground(Color.red);
//					badValue = true;
//					System.out.println("space length Bad Value(s) - showBox() method - NumberFormatException");					
//				}
//				
////				stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashArray, 0.0f);
////				getCurrentStroke();
				if (badValue) continue;
				if (relabeller.okPushed == true  && !badValue) {
					return
							relabeller.getLinkedHashMap();
				}
				else if (relabeller.cancelPushed == true){
					System.out.println("Cancel button pushed");
					
					return
							null;
				}
			}
			
			
		}
		while(true);
		}
		return fifo;
	}	

	private LinkedHashSet getLinkedHashMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method initializes saveAsPreferredCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCheckBoxPrefixWithZero() {
		if (checkBoxPrefixWithZero == null) {
			checkBoxPrefixWithZero = new JCheckBox();
			checkBoxPrefixWithZero.setMinimumSize(new Dimension(200, 22));
			checkBoxPrefixWithZero.setSelected(true);
			checkBoxPrefixWithZero.setToolTipText("Prefixes the numerical label with a set number of zeros.");
			checkBoxPrefixWithZero.setSelected(false);
			checkBoxPrefixWithZero.setHorizontalAlignment(SwingConstants.CENTER);
			checkBoxPrefixWithZero.setText("Prefix numbers with zeros");
			checkBoxPrefixWithZero.setHorizontalTextPosition(SwingConstants.LEADING);
			checkBoxPrefixWithZero.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(checkBoxPrefixWithZero.isSelected()){


					}
					else if(!(checkBoxPrefixWithZero.isSelected())){

						
					}
				}
			});
		}
		return checkBoxPrefixWithZero;
	}
	

	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		showBox(null);
		System.out.println(showBox(null));
		System.exit(0);
	}
	
	}  //  @jve:decl-index=0:visual-constraint="10,-1"
