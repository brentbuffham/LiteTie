package litetie.view.contextDialog;
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
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.text.DecimalFormat;
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
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

import litetie.model.NegativeNumberException;

import java.awt.FlowLayout;



@SuppressWarnings("serial")
public class DialogBasicStroke extends JDialog{


	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal0 = new DecimalFormat("#0");
	
	static float line = 0;
	static float dash = 0;
	static float space = 0;
	


	private JButton cancel = new JButton("Cancel");
	private JButton ok = new JButton("Ok");

	private JLabel lineWidthLabel = new JLabel("Stroke Width");
	private JLabel dashLengthLabel = new JLabel("Dash Length");
	private JLabel spaceLengthLabel = new JLabel("Space Length");
	private JLabel unitslabel1 = new JLabel("px");
	private JLabel unitslabel2 = new JLabel("px");
	private JLabel unitslabel3 = new JLabel("px");
	
	private static SpinnerNumberModel dashLengthModel = new SpinnerNumberModel(0, 0, 50, 1);
	private static SpinnerNumberModel lineWidthModel = new SpinnerNumberModel(0, 0, 50, 1);
	private static SpinnerNumberModel spaceLengthModel = new SpinnerNumberModel(0, 0, 50, 1);
	
	private static JSpinner spinnerDashLength = new JSpinner(dashLengthModel);
	private static JSpinner spinnerLineWidth = new JSpinner(lineWidthModel);;
	private static JSpinner spinnerSpaceLength = new JSpinner(spaceLengthModel);;
	
	private boolean okPushed;
	private boolean cancelPushed;
	
	private JCheckBox checkBoxLock = new JCheckBox("Lock dash and space", false);
	
	double startDash = dashLengthModel.getNumber().doubleValue(); 
	double startLine = lineWidthModel.getNumber().doubleValue();
	double startSpace = spaceLengthModel.getNumber().doubleValue();

	public static String status;

	protected Component dialogOwner;
	private static int lineWidth = 1;
	private int dashLength = 1;
	private int spaceLength =1;
	private static float[] dashSpacing = new float[2];
	private static BasicStroke currentStroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, null, 1);

	

	Dimension dialogSize = new Dimension(300,190);
	
	public DialogBasicStroke(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		
		this.setResizable(false);
		this.setBounds(null);
		this.setTitle("Adjust Stroke Values");
		setLayout(new FlowLayout());
		add(lineWidthLabel);
		add(spinnerLineWidth);
		add(unitslabel1);
		add(dashLengthLabel);
		add(spinnerDashLength);
		add(unitslabel2);
		add(spaceLengthLabel);
		add(spinnerSpaceLength);
		add(unitslabel3);
		add(checkBoxLock);
		add(ok);
		add(cancel);
		
		this.ghostFields();
		
	}
	
	
	private JSpinner getLineWidth() {
			
			spinnerLineWidth.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						int i = Integer.parseInt(getLineWidth().getValue().toString());
						getLineWidth().setValue(i);
						line = i;
						
						getLineWidth().setForeground(Color.black);	
						return true;
						}
					catch (NumberFormatException nfe){
						getLineWidth().setForeground(Color.red);
						JOptionPane.showMessageDialog(dialogOwner, "The value in for the line/border width\n in the Spinner cannot be parsed.\nThe default value of 1 has been returned.");
						spinnerLineWidth.setValue(1);
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
			
			
			spinnerLineWidth.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getLineWidth().getValue().toString());
						
							getLineWidth().setValue(d);
							getLineWidth().setForeground(Color.black);	
						
						return true;
						}
					catch (NumberFormatException nfe){
						getLineWidth().setForeground(Color.red);
						System.out.println("Caught at JSpinner getBench() - NumberFormatException");
						return false;
						}
				}
			});
		
		return spinnerLineWidth;
	}
	private JSpinner getDashLength() {
		if (spinnerDashLength == null) {
			spinnerDashLength = new JSpinner(dashLengthModel);
			spinnerDashLength.setPreferredSize(new Dimension(100, 22));
			spinnerDashLength.setMinimumSize(new Dimension(100, 22));
			spinnerDashLength.setName("Dash Length");
			spinnerDashLength.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getDashLength().getValue().toString());
						getDashLength().setValue(d);
						getDashLength().setForeground(Color.black);
						if(getCheckBoxLock().isSelected()){
							getSpaceLength().setValue(getDashLength().getValue());
						}
						return true;
						}
					catch (NumberFormatException nfe){
						getDashLength().setForeground(Color.red);
						System.out.println("Caught at JSpinner getAngle() - NumberFormatException");
						return false;
						}
				}
			});
				
		}
		return spinnerDashLength;
	}
	private JSpinner getSpaceLength() {
		if (spinnerSpaceLength == null) {
			spinnerSpaceLength = new JSpinner();
			spinnerSpaceLength.setPreferredSize(new Dimension(100, 22));
			spinnerSpaceLength.setModel(spaceLengthModel);
			spinnerSpaceLength.setName("Space Length");
			spinnerSpaceLength.setMinimumSize(new Dimension(100, 22));
			spinnerSpaceLength.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getDashLength().getValue().toString());
						getSpaceLength().setValue(d);
						getSpaceLength().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getSpaceLength().setForeground(Color.red);
						System.out.println("Caught at JSpinner getAngle() - NumberFormatException");
						return false;
						}
				}
			});
				
		}
		return spinnerSpaceLength;
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

//SET METHODS START	
	private JSpinner setLineWidth() {
		return
		spinnerLineWidth;
	}
	private JSpinner setDashLength() {
		return
				spinnerDashLength;
	}
	private JSpinner setSpaceLength(){
		return
				spinnerSpaceLength;
	}
	
//SET METHODS END	
	private void ghostFields() {
		
		if (checkBoxLock.isSelected() == true) {
			spinnerSpaceLength.setEnabled(false);
			spaceLengthLabel.setEnabled(false);
			spinnerSpaceLength.setValue(getDashLength().getValue());
			}
		
		else {
			spinnerSpaceLength.setEnabled(true);
			spaceLengthLabel.setEnabled(true);
			}
		
		
	}
//	public void paintComponent(Graphics g)
//	   {
//	      Graphics2D g2 = (Graphics2D) g;
//	      
//	      Rectangle2D path = new Rectangle2D.Double(5, 5, 50, 30);
//	      float[] dashPattern = { Float.parseFloat(getDashLength().getValue().toString()), Float.parseFloat(getSpaceLength().getValue().toString()) };
//	      BasicStroke stroke = new BasicStroke(line, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashPattern, 0.0f);
//
//	      g2.setStroke(stroke);
//	      g2.draw(path);
//	   }
//	
	public static BasicStroke showBox(JPanel frame) throws NumberFormatException, NegativeNumberException{
		DialogBasicStroke newStroke = new DialogBasicStroke(frame);
		
		return showBox(frame,  
				Integer.parseInt((String)(newStroke.getLineWidth().getValue().toString())),
				Integer.parseInt((String)(newStroke.getDashLength().getValue().toString())),
				Integer.parseInt((String)(newStroke.getSpaceLength().getValue().toString())));
		
	}

	
	public static BasicStroke showBox(JPanel canvasPanel, int lineWidth, int dashLength, int spaceLength) throws  NegativeNumberException {
		
		DialogBasicStroke newStrokeChooser = new DialogBasicStroke(canvasPanel);
		newStrokeChooser.setModal(true);
		boolean badValue = false;
		float[] dashArray = {dashLength,spaceLength};


			try {
				newStrokeChooser.setLineWidth().setValue(lineWidth);
				newStrokeChooser.setDashLength().setValue(dashLength);
				newStrokeChooser.setSpaceLength().setValue(spaceLength);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(canvasPanel,
						"One of the values inputed is not an integer",
						"StrokeChooserPanel", JOptionPane.ERROR_MESSAGE);
				newStrokeChooser.setLineWidth().setValue(1);
				newStrokeChooser.setDashLength().setValue(1);
				newStrokeChooser.setSpaceLength().setValue(1);
			}
		
		do {
			
			newStrokeChooser.setVisible(true);
			
			if (newStrokeChooser.okPushed == true) {
				
				
				try {
					line = Integer.parseInt(newStrokeChooser.getLineWidth().getValue().toString());
					lineWidth = (int) line;
					 
					newStrokeChooser.getLineWidth().setForeground(Color.black);			
				} 
				catch (NumberFormatException e) {
					newStrokeChooser.getLineWidth().setForeground(Color.red);
					badValue = true;
					System.out.println("line Bad Value(s) - showBox() method - NumberFormatException");					
				}
				try {
					dash = Integer.parseInt(newStrokeChooser.getDashLength().getValue().toString());	
					dashLength = (int) dash;
					 
					newStrokeChooser.getLineWidth().setForeground(Color.black);			
				} 
				catch (NumberFormatException e) {
					newStrokeChooser.getLineWidth().setForeground(Color.red);
					badValue = true;
					System.out.println("dash length Bad Value(s) - showBox() method - NumberFormatException");					
				}
				try {
					space = Integer.parseInt(newStrokeChooser.getDashLength().getValue().toString());	
					spaceLength = (int) space;			
					 
					newStrokeChooser.getLineWidth().setForeground(Color.black);			
				} 
				catch (NumberFormatException e) {
					newStrokeChooser.getLineWidth().setForeground(Color.red);
					badValue = true;
					System.out.println("space length Bad Value(s) - showBox() method - NumberFormatException");					
				}
				
//				stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashArray, 0.0f);
//				getCurrentStroke();
				if (badValue) continue;
				if (newStrokeChooser.okPushed == true  && !badValue) {
					return
							newStrokeChooser.getCurrentStroke();
				}
				else if (newStrokeChooser.cancelPushed == true){
					System.out.println("Cancel button pushed");
					newStrokeChooser.setVisible(false);
					return
							null;
				}
			}
			
		}
		while(badValue);
		return currentStroke;

	}	

	/**
	 * This method initializes saveAsPreferredCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getCheckBoxLock() {
		if (checkBoxLock == null) {
			checkBoxLock = new JCheckBox();
			checkBoxLock.setToolTipText("Lock the dash and Space to the same Value");
			checkBoxLock.setSelected(false);
			checkBoxLock.setHorizontalAlignment(SwingConstants.CENTER);
			checkBoxLock.setText("Set dash and space to the same");
			checkBoxLock.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(checkBoxLock.isSelected()){
						checkBoxLock.setForeground(Color.RED);
						checkBoxLock.setText("Locked");
						getSpaceLength().setModel(dashLengthModel);
//						getSpinnerSpaceDash().setForeground(Color.RED);
						getSpaceLength().setEnabled(false);
					}
					else if(!(checkBoxLock.isSelected())){
						checkBoxLock.setForeground(Color.GRAY);
						checkBoxLock.setText("Locked");
						getSpaceLength().setModel(spaceLengthModel);
//						getSpinnerSpaceDash().setForeground(Color.BLACK);
						getSpaceLength().setEnabled(true);
					}
				}
			});
		}
		return checkBoxLock;
	}
	private BasicStroke getCurrentStroke(){
		if(Integer.parseInt(getDashLength().getValue().toString()) < 1 ){
			currentStroke = new BasicStroke(Integer.parseInt(getLineWidth().getValue().toString()));
		}
		else if (Integer.parseInt(getDashLength().getValue().toString()) >= 1 && Integer.parseInt(getSpaceLength().getValue().toString()) >= 1){
			
			dashLength = Integer.parseInt(getDashLength().getValue().toString());
			spaceLength = Integer.parseInt(getSpaceLength().getValue().toString());
			dashSpacing[0] = dashLength;
			dashSpacing[1] = spaceLength;
			currentStroke = new BasicStroke(Integer.parseInt(getLineWidth().getValue().toString()), 
					BasicStroke.CAP_BUTT, 
					BasicStroke.JOIN_MITER, 
					1, 
					dashSpacing, 
					1);
		}
		return currentStroke;
		
	}


	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException {
		showBox(null);
		System.out.println(showBox(null));
		System.exit(0);
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
					setVisible(false);
					
				}
			});
		}
		return ok;
	}

}  //  @jve:decl-index=0:visual-constraint="10,-1"
