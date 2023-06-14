package litetie.view;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.Collection;

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
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import litetie.model.BPoint;
import litetie.model.Boundary;
import litetie.model.Charge;
import litetie.model.Coordinate;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.ZeroArgumentException;

import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;



@SuppressWarnings("serial")
public class DialogCreateCharge extends JDialog{
	
	


	DecimalFormat decimal0 = new DecimalFormat("#0");
	DecimalFormat decimal1 = new DecimalFormat("#0.0");
	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal3 = new DecimalFormat("#0.000");

	String [] types = {	"AIR","WATER","AGGREGATE","CUTTINGS","SAND","GAS_BAG",
			"ANFO",
			"10%_HANFO","20%_HANFO","30%_HANFO", "40%_HANFO", "50%_HANFO",
			"50%_GASSED", "60%_GASSED", "70%_GASSED", "80%_GASSED", "90%_GASSED", "100%_GASSED",
			"50%_INHIBITED", "60%_INHIBITED", "70%_INHIBITED", "80%_INHIBITED", "90%_INHIBITED", "100%_INHIBITED",
			"50%_WATERGEL", "60%_WATERGEL", "70%_WATERGEL", "80%_WATERGEL", "90%_WATERGEL", "100%_WATERGEL",
			"PACKAGE"};
	
	SpinnerListModel spinnerModelTypes = new SpinnerListModel(types);
	SpinnerNumberModel spinnerModelChargeDiameter = new SpinnerNumberModel(0.0, 0, 2000, 1);
	SpinnerNumberModel spinnerModelColumnCapDepth = new SpinnerNumberModel(0.0, 0.0, 5000.9, 0.5);
	SpinnerNumberModel spinnerModelColumnBaseDepth = new SpinnerNumberModel(0.0, 0.0, 5000.9, 0.5);
	SpinnerNumberModel spinnerModelChargeDensity = new SpinnerNumberModel(0.01, 0.00, 10.9, 0.15);
	
	private JPanel layoutPanel = null;
	private JButton cancel = null;

	//Input Titles for the textfields
	private JLabel labelChargeType;
	private JLabel labelColumnCapDepth;
	private JLabel labelColumnBaseDepth;
	private JLabel labelColour;
	private JPanel colorWellFill;
	private JLabel labelChargeDiameter;
	private JLabel labelDensity;
	
	private JSpinner spinnerChargeType;
	private JSpinner spinnerChargeDiameter;
	private JSpinner spinnerCapDensity;
	private JSpinner spinnerBaseDepth;
	private JSpinner spinnerCapDepth;
	
	private JCheckBox checkBoxLastChargeHoleBase;
	
	private boolean okPushed;
	private boolean cancelPushed;

	public static String status;
	boolean badValue = false;
	
	protected Component dialogOwner;

	static String chargeType;
	double capDensity;
	double capDepth;
	double baseDepth;
	double chargeDiameter;
	Hole holeTemp;
	
	static Charge currentCharge;
	private Charge oldCharge;
	private JPanel panel;
	private JButton ok;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension( 365, 265);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());

	
	public DialogCreateCharge() {
		super();
		initialize();
	}
	
	public DialogCreateCharge(JPanel canvasPanel) {
		super();
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(bounds);
		this.setTitle("Create Charge");
		this.setContentPane(getLayoutPanel());
	}
	
	private JPanel getLayoutPanel() {
		if (layoutPanel == null) {
			
			layoutPanel = new JPanel();
			layoutPanel.setMinimumSize(new Dimension(100, 22));
			
			GridBagLayout gbl_layoutPanel = new GridBagLayout();
			
			gbl_layoutPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0};
			gbl_layoutPanel.columnWeights = new double[]{1.0, 1.0};
			gbl_layoutPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
			layoutPanel.setLayout(gbl_layoutPanel);
			

			
			GridBagConstraints gbc_labelChargeType = new GridBagConstraints();
			GridBagConstraints gbc_spinnerChargeType = new GridBagConstraints();
			GridBagConstraints gbc_labelColumnCapDepth = new GridBagConstraints();
			GridBagConstraints gbc_spinnerCapDepth = new GridBagConstraints();
			GridBagConstraints gbc_checkBoxNewCheckBox = new GridBagConstraints();
			GridBagConstraints gbc_labelColumnBaseDepth = new GridBagConstraints();
			GridBagConstraints gbc_spinnerCapDensity = new GridBagConstraints();
			GridBagConstraints gbc_spinnerBaseDepth = new GridBagConstraints();
			GridBagConstraints gbc_labelDensity = new GridBagConstraints();
			GridBagConstraints gbc_labelChargeDiameter = new GridBagConstraints();
			GridBagConstraints gbc_spinnerChargeDiameter = new GridBagConstraints();
			GridBagConstraints gbc_labelColour = new GridBagConstraints();
			GridBagConstraints gbc_colorWellFill = new GridBagConstraints();
			GridBagConstraints gbc_panel = new GridBagConstraints();
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.gridy = 13;
			
			
			labelChargeType = new JLabel();
			labelChargeType.setText("Charge Type");
			labelChargeType.setPreferredSize(new Dimension(60, 16));
			labelChargeType.setHorizontalAlignment(SwingConstants.RIGHT);
			labelColumnCapDepth = new JLabel();
			labelColumnCapDepth.setText("Column Cap Depth");
			labelColumnCapDepth.setPreferredSize(new Dimension(35, 16));
			labelColumnCapDepth.setHorizontalAlignment(SwingConstants.RIGHT);
			gbc_labelChargeType.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelChargeType.weightx = 1.0;
			gbc_labelChargeType.gridx = 0;
			gbc_labelChargeType.insets = new Insets(10, 5, 5, 5);
			gbc_labelChargeType.gridy = 0;
			gbc_spinnerChargeType.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerChargeType.insets = new Insets(10, 0, 5, 0);
			gbc_spinnerChargeType.gridx = 1;
			gbc_spinnerChargeType.gridy = 0;
			gbc_labelColumnCapDepth.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelColumnCapDepth.weightx = 1.0;
			gbc_labelColumnCapDepth.gridx = 0;
			gbc_labelColumnCapDepth.insets = new Insets(2, 5, 5, 5);
			gbc_labelColumnCapDepth.gridy = 1;
			gbc_spinnerCapDepth.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerCapDepth.insets = new Insets(0, 0, 5, 0);
			gbc_spinnerCapDepth.gridx = 1;
			gbc_spinnerCapDepth.gridy = 1;
			gbc_checkBoxNewCheckBox.gridwidth = 2;
			gbc_checkBoxNewCheckBox.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxNewCheckBox.gridx = 0;
			gbc_checkBoxNewCheckBox.gridy = 2;
			gbc_labelColumnBaseDepth.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelColumnBaseDepth.insets = new Insets(2, 5, 5, 5);
			gbc_labelColumnBaseDepth.gridx = 0;
			gbc_labelColumnBaseDepth.gridy = 3;
			gbc_spinnerBaseDepth.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerBaseDepth.insets = new Insets(0, 0, 5, 0);
			gbc_spinnerBaseDepth.gridx = 1;
			gbc_spinnerBaseDepth.gridy = 3;
			gbc_labelDensity.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelDensity.insets = new Insets(0, 0, 5, 5);
			gbc_labelDensity.gridx = 0;
			gbc_labelDensity.gridy = 4;
			gbc_spinnerCapDensity.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerCapDensity.insets = new Insets(0, 0, 5, 0);
			gbc_spinnerCapDensity.gridx = 1;
			gbc_spinnerCapDensity.gridy = 4;
			gbc_labelChargeDiameter.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelChargeDiameter.insets = new Insets(0, 0, 5, 5);
			gbc_labelChargeDiameter.gridx = 0;
			gbc_labelChargeDiameter.gridy = 6;
			gbc_spinnerChargeDiameter.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerChargeDiameter.insets = new Insets(0, 0, 5, 0);
			gbc_spinnerChargeDiameter.gridx = 1;
			gbc_spinnerChargeDiameter.gridy = 6;
			gbc_labelColour.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelColour.insets = new Insets(0, 0, 5, 5);
			gbc_labelColour.gridx = 0;
			gbc_labelColour.gridy = 7;
			gbc_colorWellFill.insets = new Insets(0, 0, 5, 0);
			gbc_colorWellFill.gridx = 1;
			gbc_colorWellFill.gridy = 7;
			gbc_panel.anchor = GridBagConstraints.SOUTH;
			gbc_panel.gridwidth = 2;
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 8;
			
			layoutPanel.add(labelChargeType, gbc_labelChargeType);
			layoutPanel.add(getSpinnerChargeType(), gbc_spinnerChargeType);
			layoutPanel.add(labelColumnCapDepth, gbc_labelColumnCapDepth);
			layoutPanel.add(getSpinnerCapDepth(), gbc_spinnerCapDepth);
			layoutPanel.add(getCheckBoxLastChargeHoleBase(), gbc_checkBoxNewCheckBox);
			layoutPanel.add(getLabelColumnBaseDepth(), gbc_labelColumnBaseDepth);
			layoutPanel.add(getSpinnerBaseDepth(), gbc_spinnerBaseDepth);
			layoutPanel.add(getLabelDensity(), gbc_labelDensity);
			GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
			gbc_chckbxNewCheckBox.gridwidth = 2;
			gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox.gridx = 0;
			gbc_chckbxNewCheckBox.gridy = 5;
			layoutPanel.add(getChckbxNewCheckBox(), gbc_chckbxNewCheckBox);
			layoutPanel.add(getLabelColour(), gbc_labelColour);
			layoutPanel.add(getSpinnerCapDensity(), gbc_spinnerCapDensity);
			layoutPanel.add(getLabelChargeDiameter(), gbc_labelChargeDiameter);
			layoutPanel.add(getSpinnerChargeDiameter(), gbc_spinnerChargeDiameter);
			layoutPanel.add(getColorWellFill(), gbc_colorWellFill);
			layoutPanel.add(getPanel(), gbc_panel);	
		}
		return layoutPanel;
	}


//SET METHODS START	
	private JSpinner setSpinnerChargeDiameter() {return spinnerChargeDiameter;}
	private JSpinner setSpinnerChargeType() {return spinnerChargeType;}
	private JSpinner setSpinnerCapDensity() {return spinnerCapDensity;}
	private JSpinner setSpinnerCapDepth() {return spinnerCapDepth;}
	private JSpinner setSpinnerBaseDepth() {return spinnerBaseDepth;}
//SET METHODS END	
	
	//FIXME Need to decide on creating products or allowing a thin built in array of products.
	public static Charge showBox(JPanel frame) throws NumberFormatException, NegativeNumberException, ZeroArgumentException{
		DialogCreateCharge dialog = new DialogCreateCharge(frame);
		Hole hole = dialog.holeTemp;
		String type = (String) dialog.getSpinnerChargeType().getValue();
		double capDensity = (Double) dialog.getSpinnerCapDensity().getValue();
		double capDepth = (Double) dialog.getSpinnerCapDepth().getValue();
		double baseDepth = (Double) dialog.getSpinnerBaseDepth().getValue();
		double chargeDiameter = (Double) dialog.getSpinnerChargeDiameter().getValue();
		Color color = dialog.getColorWellFill().getBackground();
		Charge charge = new Charge(type, hole, capDepth, baseDepth, capDensity, chargeDiameter, false, color);
		return showBox(frame, charge, hole);
	}

	public static Charge showBox(JPanel canvasPanel, Charge charge, Hole hole) throws ZeroArgumentException, NegativeNumberException {
		DialogCreateCharge dialog = new DialogCreateCharge(canvasPanel);
		dialog.setModal(true);
		
			try {

				dialog.chargeType = (String) dialog.getSpinnerChargeType().getValue();
				dialog.capDensity = (Double) dialog.getSpinnerCapDensity().getValue();
				dialog.capDepth = (Double) dialog.getSpinnerCapDepth().getValue();
				dialog.baseDepth = (Double) dialog.getSpinnerBaseDepth().getValue();
				dialog.chargeDiameter = (Double) dialog.getSpinnerChargeDiameter().getValue();
				dialog.holeTemp = hole;
				dialog.oldCharge = new Charge(chargeType, hole, dialog.capDepth, dialog.baseDepth, dialog.capDensity, dialog.chargeDiameter, charge.getCompressible(), charge.getColor());
				
				dialog.setSpinnerChargeType().setValue(chargeType);
				dialog.setSpinnerCapDensity().setValue(dialog.decimal2.format(dialog.capDensity));
				dialog.setSpinnerCapDepth().setValue(dialog.decimal1.format(dialog.capDepth));
				dialog.setSpinnerBaseDepth().setValue(dialog.decimal1.format(dialog.baseDepth));
				dialog.setSpinnerChargeDiameter().setValue(dialog.decimal0.format(dialog.chargeDiameter));
				
				currentCharge = dialog.oldCharge;
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(canvasPanel,
						"One of the values inputed is not an integer",
						"DialogXYZValues", JOptionPane.ERROR_MESSAGE);
				dialog.setSpinnerChargeType().setValue(currentCharge.getTypeName());
				dialog.setSpinnerCapDensity().setValue(currentCharge.getDensity());
				dialog.setSpinnerCapDepth().setValue(currentCharge.getStartDepth());
				dialog.setSpinnerBaseDepth().setValue(currentCharge.getFinishDepth());
				dialog.setSpinnerChargeDiameter().setValue(currentCharge.getChargeDiameter());
				
			}
		
		do {
			
			dialog.setVisible(true);
			
			if (dialog.okPushed == true) {
				try {
					chargeType = (String) dialog.getSpinnerChargeType().getValue(); 
					dialog.getSpinnerChargeType().setForeground(Color.black);			
				} 
				catch (IllegalArgumentException e) {
					dialog.getSpinnerChargeType().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Bad Value(s) - showBox() method");					
				}
				try {
					dialog.capDensity = (Double) dialog.getSpinnerCapDensity().getValue();
					dialog.getSpinnerCapDensity().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerCapDensity().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Bad Value(s) - showBox() method");					
				}
				try {
					dialog.capDepth = (Double) dialog.getSpinnerCapDepth().getValue();
					dialog.getSpinnerCapDepth().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerCapDepth().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Bad Value(s) - showBox() method");					
				}
				try {
					dialog.baseDepth = (Double) dialog.getSpinnerBaseDepth().getValue();
					dialog.getSpinnerBaseDepth().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerBaseDepth().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Bad Value(s) - showBox() method");					
				}
				try {
					dialog.chargeDiameter = (Double) dialog.getSpinnerChargeDiameter().getValue();
					dialog.getSpinnerChargeDiameter().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerChargeDiameter().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("Bad Value(s) - showBox() method");					
				}

				if (dialog.badValue) continue;
				if (dialog.okPushed == true  && !(dialog.badValue)) {
					return
							dialog.getCurrentCharge();
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
		
		return currentCharge;

	}	
	private Charge getCurrentCharge(){
		if (getCheckBoxLastChargeHoleBase().isSelected()){
			chargeType = (String) this.getSpinnerChargeType().getValue();
			capDensity = (Double) this.getSpinnerCapDensity().getValue();
			capDepth = (Double) this.getSpinnerCapDepth().getValue();
			baseDepth = holeTemp.getHoleLength();
			chargeDiameter = (Double) this.getSpinnerChargeDiameter().getValue(); 
			try {
				currentCharge = new Charge(chargeType, this.holeTemp, capDepth, baseDepth, capDensity, chargeDiameter, currentCharge.getCompressible(), this.currentFillColor);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ZeroArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NegativeNumberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (!(getCheckBoxLastChargeHoleBase().isSelected())){
			
		}
		return currentCharge;
		
	}
	

//	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException {
//		showBox(null);
//		System.out.println(showBox(null));
//		System.exit(0);
//	}
	
	private JLabel getLabelColumnBaseDepth() {
		if (labelColumnBaseDepth == null) {
			labelColumnBaseDepth = new JLabel();
			labelColumnBaseDepth.setText("Column Base Depth");
			labelColumnBaseDepth.setPreferredSize(new Dimension(35, 16));
			labelColumnBaseDepth.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return labelColumnBaseDepth;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panel.add(getChckbxNewCheckBox_1());
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
	private JLabel getLabelDensity() {
		if (labelDensity == null) {
			labelDensity = new JLabel();
			labelDensity.setText("Column Cap Density");
			labelDensity.setPreferredSize(new Dimension(35, 16));
			labelDensity.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return labelDensity;
	}
	private JSpinner getSpinnerChargeType() {
		if (spinnerChargeType == null) {
			spinnerChargeType = new JSpinner();
			spinnerChargeType.setPreferredSize(new Dimension(150, 20));
			spinnerChargeType.setMinimumSize(new Dimension(150, 20));
		}
		return spinnerChargeType;
	}
	private JSpinner getSpinnerCapDensity() {
		if (spinnerCapDensity == null) {
			spinnerCapDensity = new JSpinner();
			spinnerCapDensity.setPreferredSize(new Dimension(150, 20));
			spinnerCapDensity.setMinimumSize(new Dimension(150, 20));
		}
		return spinnerCapDensity;
	}
	private JSpinner getSpinnerBaseDepth() {
		if (spinnerBaseDepth == null) {
			spinnerBaseDepth = new JSpinner();
			spinnerBaseDepth.setPreferredSize(new Dimension(150, 20));
			spinnerBaseDepth.setMinimumSize(new Dimension(150, 20));
			if(getCheckBoxLastChargeHoleBase().isSelected()) {
				baseDepth = holeTemp.getHoleLength();
			}
		
		}
		return spinnerBaseDepth;
	}
	private JSpinner getSpinnerCapDepth() {
		if (spinnerCapDepth == null) {
			spinnerCapDepth = new JSpinner();
			spinnerCapDepth.setPreferredSize(new Dimension(150, 20));
			spinnerCapDepth.setMinimumSize(new Dimension(150, 20));
		}
		return spinnerCapDepth;
	}
	private JLabel getLabelChargeDiameter() {
		if (labelChargeDiameter == null) {
			labelChargeDiameter = new JLabel("Charge Diameter");
			labelChargeDiameter.setHorizontalAlignment(SwingConstants.RIGHT);
			labelChargeDiameter.setHorizontalTextPosition(SwingConstants.CENTER);
		}
		return labelChargeDiameter;
	}
	private JSpinner getSpinnerChargeDiameter() {
		if (spinnerChargeDiameter == null) {
			spinnerChargeDiameter = new JSpinner();
			spinnerChargeDiameter.setPreferredSize(new Dimension(150, 20));
			spinnerChargeDiameter.setMinimumSize(new Dimension(150, 20));
			if(getCheckBoxLastChargeHoleBase().isSelected()) {
				chargeDiameter = holeTemp.getDiameter();
			}
		}
		return spinnerChargeDiameter;
	}
	private JCheckBox getCheckBoxLastChargeHoleBase() {
		if (checkBoxLastChargeHoleBase == null) {
			checkBoxLastChargeHoleBase = new JCheckBox("Last charge/hole base");
			checkBoxLastChargeHoleBase.setSelected(true);
			
		
		}
		return checkBoxLastChargeHoleBase;
	}
	private JLabel getLabelColour() {
		if (labelColour == null) {
			labelColour = new JLabel("Colour");
			labelColour.setHorizontalTextPosition(SwingConstants.CENTER);
			labelColour.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return labelColour;
	}
	
	Color currentFillColor;
	private JCheckBox chckbxNewCheckBox;
	private JCheckBox chckbxNewCheckBox_1;
	
	private JPanel getColorWellFill() {
		if (colorWellFill == null) {
			colorWellFill = new JPanel() {
				public void paintComponent(Graphics g) {
					if(colorWellFill.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(colorWellFill.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 21, 21);
					}
					else {
						g.setColor(colorWellFill.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 21, 21);
					}
					
				}
			};
			colorWellFill.setPreferredSize(new Dimension(22, 22));
			colorWellFill.setBackground(Color.WHITE);
			colorWellFill.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentFillColor = JColorChooser.showDialog(dialogOwner, "Change Current Colour", getColorWellFill().getBackground());
					if (currentFillColor != null) {
						getColorWellFill().setBackground(currentFillColor);
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
			colorWellFill.setToolTipText("Change the Charge Colour");
			colorWellFill.setBackground(currentFillColor);

		}
		return colorWellFill;
	}
	private JCheckBox getChckbxNewCheckBox() {
		if (chckbxNewCheckBox == null) {
			chckbxNewCheckBox = new JCheckBox("Use blast hole diameter");
			chckbxNewCheckBox.setSelected(true);
		}
		return chckbxNewCheckBox;
	}
	private JCheckBox getChckbxNewCheckBox_1() {
		if (chckbxNewCheckBox_1 == null) {
			chckbxNewCheckBox_1 = new JCheckBox("Save as default");
		}
		return chckbxNewCheckBox_1;
	}
} 
