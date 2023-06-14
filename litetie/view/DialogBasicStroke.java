package litetie.view;
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
	

	private JPanel layoutPanel = null;
	private JButton cancel = null;

	private JLabel lineWidthLabel = null;
	private JLabel dashLengthLabel = null;
	private JLabel unitslabel1 = null;
	private JLabel unitsLabel2 = null;
	
	private static JSpinner spinnerDashLength = null;
	private static JSpinner spinnerLineWidth = null;
	private static JSpinner spinnerSpaceLength = null;
	
	private boolean okPushed;
	private boolean cancelPushed;
	
	private JCheckBox checkBoxLock = null;
	
	SpinnerNumberModel dashLengthModel = new SpinnerNumberModel(0, 0, 50, 1);
	SpinnerNumberModel lineWidthModel = new SpinnerNumberModel(0, 0, 50, 1);
	SpinnerNumberModel spaceLengthModel = new SpinnerNumberModel(0, 0, 50, 1);
	
	
	double startDash = dashLengthModel.getNumber().doubleValue(); 
	double startLine = lineWidthModel.getNumber().doubleValue();
	double startSpace = spaceLengthModel.getNumber().doubleValue();

	public static String status;
	private JLabel lblSpaceLength;
	private JLabel unitsLabel3;
	protected Component dialogOwner;
	private static int lineWidth = 1;
	private int dashLength = 1;
	private int spaceLength =1;
	private static float[] dashSpacing = new float[2];
	private static BasicStroke currentStroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, null, 1);
	private JPanel panel;
	private JButton ok;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension(300,190);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());
	

	
	public DialogBasicStroke(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		
		this.setResizable(false);
		this.setBounds(bounds);
		this.setTitle("Adjust Stroke Values");
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
			gbl_layoutPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
			gbl_layoutPanel.columnWeights = new double[]{1.0, 0.0, 1.0};
			gbl_layoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
			layoutPanel.setLayout(gbl_layoutPanel);
			
			lineWidthLabel = new JLabel();
			lineWidthLabel.setText("Line Width");
			lineWidthLabel.setPreferredSize(new Dimension(94, 16));
			lineWidthLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints bearingLabelGridBag = new GridBagConstraints();
			bearingLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			bearingLabelGridBag.weightx = 1.0;
			bearingLabelGridBag.gridx = 0;
			bearingLabelGridBag.insets = new Insets(10, 5, 5, 5);
			bearingLabelGridBag.gridy = 0;
			layoutPanel.add(lineWidthLabel, bearingLabelGridBag);
			
			GridBagConstraints bearingValueGridBag = new GridBagConstraints();
			bearingValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			bearingValueGridBag.weightx = 1.0;
			bearingValueGridBag.gridx = 1;
			bearingValueGridBag.insets = new Insets(10, 5, 5, 5);
			bearingValueGridBag.gridy = 0;
			layoutPanel.add(getLineWidth(), bearingValueGridBag);
			
			unitslabel1 = new JLabel();
			unitslabel1.setPreferredSize(new Dimension(20, 25));
			unitslabel1.setMinimumSize(new Dimension(20, 22));
			unitslabel1.setText("px");
			
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.insets = new Insets(10, 0, 5, 0);
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 0;
			layoutPanel.add(unitslabel1, gridBagConstraints6);
			
			dashLengthLabel = new JLabel();
			dashLengthLabel.setText("Dash Length\n");
			dashLengthLabel.setPreferredSize(new Dimension(94, 16));
			dashLengthLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints angleLabelGridBag = new GridBagConstraints();
			angleLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			angleLabelGridBag.weightx = 1.0;
			angleLabelGridBag.gridx = 0;
			angleLabelGridBag.insets = new Insets(2, 5, 5, 5);
			angleLabelGridBag.anchor = GridBagConstraints.WEST;
			angleLabelGridBag.gridy = 1;
			layoutPanel.add(dashLengthLabel, angleLabelGridBag);
			
			GridBagConstraints angleValueGridBag = new GridBagConstraints();
			angleValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			angleValueGridBag.weightx = 1.0;
			angleValueGridBag.gridx = 1;
			angleValueGridBag.insets = new Insets(2, 5, 5, 5);
			angleValueGridBag.gridy = 1;
			layoutPanel.add(getDashLength(), angleValueGridBag);
			
			unitsLabel2 = new JLabel();
			unitsLabel2.setPreferredSize(new Dimension(20, 25));
			unitsLabel2.setMinimumSize(new Dimension(20, 22));
			unitsLabel2.setText("px");
			
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 1;
			layoutPanel.add(unitsLabel2, gridBagConstraints8);
			GridBagConstraints gbc_lblSpaceLength = new GridBagConstraints();
			gbc_lblSpaceLength.anchor = GridBagConstraints.EAST;
			gbc_lblSpaceLength.insets = new Insets(2, 5, 5, 5);
			gbc_lblSpaceLength.gridx = 0;
			gbc_lblSpaceLength.gridy = 2;
			layoutPanel.add(getLblSpaceLength(), gbc_lblSpaceLength);

			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinner.insets = new Insets(2, 5, 5, 5);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 2;
			layoutPanel.add(getSpaceLength(), gbc_spinner);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 5, 0);
			gbc_label.gridx = 2;
			gbc_label.gridy = 2;
			layoutPanel.add(getUnitsLabel3(), gbc_label);
			GridBagConstraints gbc_checkBoxLock = new GridBagConstraints();
			gbc_checkBoxLock.gridwidth = 3;
			gbc_checkBoxLock.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxLock.gridx = 0;
			gbc_checkBoxLock.gridy = 3;
			layoutPanel.add(getCheckBoxLock(), gbc_checkBoxLock);
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
	private JSpinner getLineWidth() {
		if (spinnerLineWidth == null) {
			spinnerLineWidth = new JSpinner(lineWidthModel);
			spinnerLineWidth.setPreferredSize(new Dimension(100, 22));
			spinnerLineWidth.setMinimumSize(new Dimension(100, 22));
			spinnerLineWidth.setName("Line Width");
			
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
		}
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
			lblSpaceLength.setEnabled(false);
			spinnerSpaceLength.setValue(getDashLength().getValue());
			}
		
		else {
			spinnerSpaceLength.setEnabled(true);
			lblSpaceLength.setEnabled(true);
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

//private StrokeComponent canvas;	
	private JLabel getLblSpaceLength() {
		if (lblSpaceLength == null) {
			lblSpaceLength = new JLabel();
			lblSpaceLength.setText("Space Length\n");
			lblSpaceLength.setPreferredSize(new Dimension(94, 16));
			lblSpaceLength.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return lblSpaceLength;
	}
	private JLabel getUnitsLabel3() {
		if (unitsLabel3 == null) {
			unitsLabel3 = new JLabel();
			unitsLabel3.setText("px");
			unitsLabel3.setPreferredSize(new Dimension(20, 25));
			unitsLabel3.setMinimumSize(new Dimension(20, 22));
		}
		return unitsLabel3;
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
					setVisible(false);
					
				}
			});
		}
		return ok;
	}

}  //  @jve:decl-index=0:visual-constraint="10,-1"
