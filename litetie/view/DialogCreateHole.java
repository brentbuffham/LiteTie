package litetie.view;
import javax.swing.AbstractButton;
import javax.swing.InputVerifier;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;
import javax.swing.SpinnerModel;
import javax.swing.border.BevelBorder;

import litetie.model.Dummy;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.UnitConvert;
import litetie.model.ZeroArgumentException;
import java.awt.FlowLayout;



@SuppressWarnings("serial")
public class DialogCreateHole extends JDialog{


	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal0 = new DecimalFormat("#0");
	
	

	private JPanel layoutPanel = null;
	
	private JButton holeTypeButton = null;  //  @jve:decl-index=0:visual-constraint="415,29"
	private JButton toggleUnitType = null;
	private JButton ok = null;
	private JButton cancel = null;
	private Color currentColor = Color.BLACK;
	
	private JLabel northingLabel = null;
	private JLabel eastingLabel = null;
	private JLabel diameterLabel = null;
	private JLabel benchLabel = null;
	private JLabel bearingLabel = null;
	private JLabel collarRLLabel = null;
	private JLabel angleLabel = null;
	private JLabel subdrillLabel = null;
	private JLabel badValuesLabel = null;
	private JLabel floorRLLabel = null;
	private JLabel toeRLLabel = null;
	
	private JLabel unitFRLLabel = null;
	private JLabel unitsTRLLabel = null;
	private JLabel unitsNLabel = null;
	private JLabel unitsELabel = null;
	private JLabel unitsDiaLabel = null;
	private JLabel unitsDepLabel = null;
	private JLabel unitsBeaLabel = null;
	private JLabel unitsRLLabel = null;
	private JLabel unitsAngLabel = null;
	private JLabel unitsSubLabel = null;
	private JLabel unitLabel;
	private JLabel displayShape;
	private JLabel shapeLabel;
	
	private JTextField northing = null;
	private JTextField easting = null;
	private JTextField diameter = null;
	private JTextField bench = null;
	private JTextField floorRL = null;
	private JTextField toeRL = null;
	private JTextField subdrill = null;
	private JTextField collarRL = null;
	
	private JSpinner angle = null;
	private JSpinner bearing = null;
	private JSpinner shape;
	
	private boolean okPushed;
	private boolean cancelPushed;
	
	private JCheckBox saveAsPreferredCheckBox = null;
	
	SpinnerNumberModel degreeSpin1 = new SpinnerNumberModel(0, 0, 359.99, 1);
	SpinnerNumberModel degreeSpin2 = new SpinnerNumberModel(0, 0, 359.99, 1);
	SpinnerNumberModel degreeSpin3 = new SpinnerNumberModel(0, 0, 359.99, 1);
	SpinnerNumberModel shapeIntegerModel = new SpinnerNumberModel(1, 1, 10, 1);
	
	double startDegrees1 = degreeSpin1.getNumber().doubleValue(); 
	double startDegrees2 = degreeSpin2.getNumber().doubleValue();
	double startDegrees3 = degreeSpin3.getNumber().doubleValue();

	public static String status;
	
	private Dimension dimension22 = new Dimension(80,22);

	private JTextField holeLength = null;
	private JCheckBox holeAngleOffZero = null;
	private JLabel holeLengthLabel = null;
	private JLabel unitsHLLabel = null;
	private JButton colorButton;
	private JLabel lblDisplayColour = null;


	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension( 410, 575);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());
	private JPanel panel;

	
	public DialogCreateHole(Frame owner) {
		super(owner);
		initialize();
	}

	private void initialize() {
		
		this.setResizable(false);
		this.setBounds(bounds);
		this.setTitle("Create a New Hole");
		this.setContentPane(getLayoutPanel());
		
		this.ghostFields();
	}
	
	private JPanel getLayoutPanel() {
		if (layoutPanel == null) {
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.gridx = 0;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.gridy = 13;
			
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 15;
			unitLabel = new JLabel();
			unitLabel.setText("Unit Type");
			unitLabel.setHorizontalAlignment(SwingConstants.CENTER);
			unitLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			unitLabel.setVisible(true);
			
			
			badValuesLabel = new JLabel();
			badValuesLabel.setText("Values indicated by RED text are inappropriate");
			badValuesLabel.setSize(150, 60);
			badValuesLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			badValuesLabel.setPreferredSize(new Dimension(160, 50));
			badValuesLabel.setForeground(Color.red);
			badValuesLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			badValuesLabel.setHorizontalAlignment(SwingConstants.CENTER);
			badValuesLabel.setVisible(true);
			
			
			//Grid Bags
			GridBagConstraints unitLabelGridBag = new GridBagConstraints();
			unitLabelGridBag.gridx = 0;
			unitLabelGridBag.gridy = 15;
			unitLabelGridBag.anchor = GridBagConstraints.EAST;
			unitLabelGridBag.insets = new Insets(0, 0, 5, 10);
			
			
			
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints.gridwidth = 3;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 16;
			
			GridBagConstraints toggleHoleTypeGridBag = new GridBagConstraints();
			toggleHoleTypeGridBag.gridx = 0;
			toggleHoleTypeGridBag.gridwidth = 3;
			toggleHoleTypeGridBag.fill = GridBagConstraints.BOTH;
			toggleHoleTypeGridBag.insets = new Insets(12, 40, 5, 40);
			toggleHoleTypeGridBag.gridy = 0;
			
			GridBagConstraints badValuesGridBag = new GridBagConstraints();
			badValuesGridBag.fill = GridBagConstraints.BOTH;
			badValuesGridBag.gridwidth = 3;
			badValuesGridBag.gridx = 0;
			badValuesGridBag.anchor = GridBagConstraints.CENTER;
			badValuesGridBag.insets = new Insets(3, 10, 5, 10);
			badValuesGridBag.weighty = 0.0D;
			badValuesGridBag.gridy = 14;
			
			layoutPanel = new JPanel();
			layoutPanel.setMinimumSize(new Dimension(100, 22));
			GridBagLayout gbl_layoutPanel = new GridBagLayout();
			gbl_layoutPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
			gbl_layoutPanel.columnWeights = new double[]{0.0, 1.0, 0.0};
			layoutPanel.setLayout(gbl_layoutPanel);
			
			eastingLabel = new JLabel();
			eastingLabel.setText("X Value");
			eastingLabel.setPreferredSize(new Dimension(94, 16));
			eastingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints eastingLabelGridBag = new GridBagConstraints();
			eastingLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			eastingLabelGridBag.weightx = 1.0;
			eastingLabelGridBag.gridx = 0;
			eastingLabelGridBag.insets = new Insets(2, 25, 5, 10);
			eastingLabelGridBag.anchor = GridBagConstraints.WEST;
			eastingLabelGridBag.gridy = 1;
			layoutPanel.add(eastingLabel, eastingLabelGridBag);
			
			GridBagConstraints eastingValueGridBag = new GridBagConstraints();
			eastingValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			eastingValueGridBag.weightx = 1.0;
			eastingValueGridBag.gridx = 1;
			eastingValueGridBag.insets = new Insets(2, 10, 5, 25);
			eastingValueGridBag.anchor = GridBagConstraints.WEST;
			eastingValueGridBag.gridy = 1;
			layoutPanel.add(getEasting(), eastingValueGridBag);
			
			unitsELabel = new JLabel();
			unitsELabel.setMinimumSize(new Dimension(100, 22));
			unitsELabel.setText("meters");
			
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 2;
			gridBagConstraints3.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridy = 1;
			layoutPanel.add(unitsELabel, gridBagConstraints3);
			
			
			northingLabel = new JLabel();
			northingLabel.setText("Y Value");
			northingLabel.setPreferredSize(new Dimension(94, 16));
			northingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
				
				GridBagConstraints northingLabelGridBag = new GridBagConstraints();
				northingLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
				northingLabelGridBag.weightx = 1.0;
				northingLabelGridBag.gridx = 0;
				northingLabelGridBag.insets = new Insets(2, 25, 5, 10);
				northingLabelGridBag.anchor = GridBagConstraints.EAST;
				northingLabelGridBag.gridy = 2;
				layoutPanel.add(northingLabel, northingLabelGridBag);
			
			GridBagConstraints northingValueGridBag = new GridBagConstraints();
			northingValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			northingValueGridBag.weightx = 1.0;
			northingValueGridBag.gridx = 1;
			northingValueGridBag.insets = new Insets(2, 10, 5, 25);
			northingValueGridBag.anchor = GridBagConstraints.WEST;
			northingValueGridBag.gridy = 2;
			
			layoutPanel.add(getNorthing(), northingValueGridBag);
			
			unitsNLabel = new JLabel();
			unitsNLabel.setMinimumSize(new Dimension(100, 22));
			unitsNLabel.setText("meters");
			
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 2;
			
			layoutPanel.add(unitsNLabel, gridBagConstraints2);
			
			collarRLLabel = new JLabel();
			collarRLLabel.setText("Z Level");
			collarRLLabel.setPreferredSize(new Dimension(94, 16));
			collarRLLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints rLLabelGridBag = new GridBagConstraints();
			rLLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			rLLabelGridBag.weightx = 1.0;
			rLLabelGridBag.gridx = 0;
			rLLabelGridBag.insets = new Insets(2, 25, 5, 10);
			rLLabelGridBag.anchor = GridBagConstraints.WEST;
			rLLabelGridBag.gridy = 3;
			layoutPanel.add(collarRLLabel, rLLabelGridBag);
			
			GridBagConstraints rLValueGridBag = new GridBagConstraints();
			rLValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			rLValueGridBag.weightx = 1.0;
			rLValueGridBag.gridx = 1;
			rLValueGridBag.insets = new Insets(2, 10, 5, 25);
			rLValueGridBag.anchor = GridBagConstraints.WEST;
			rLValueGridBag.gridy = 3;
			layoutPanel.add(getCollarRL(), rLValueGridBag);
			
			unitsRLLabel = new JLabel();
			unitsRLLabel.setMinimumSize(new Dimension(100, 22));
			unitsRLLabel.setText("meters");
			
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridy = 3;
			layoutPanel.add(unitsRLLabel, gridBagConstraints7);
			
			diameterLabel =  new JLabel();
			diameterLabel.setText("Hole Diameter");
			diameterLabel.setPreferredSize(new Dimension(94, 16));
			diameterLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints diameterLabelGridBag = new GridBagConstraints();
			diameterLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			diameterLabelGridBag.weightx = 1.0;
			diameterLabelGridBag.gridx = 0;
			diameterLabelGridBag.insets = new Insets(2, 25, 5, 10);
			diameterLabelGridBag.anchor = GridBagConstraints.WEST;
			diameterLabelGridBag.gridy = 4;
			layoutPanel.add(diameterLabel, diameterLabelGridBag);
			
			GridBagConstraints diameterValueGridBag = new GridBagConstraints();
			diameterValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			diameterValueGridBag.weightx = 1.0;
			diameterValueGridBag.gridx = 1;
			diameterValueGridBag.insets = new Insets(2, 10, 5, 25);
			diameterValueGridBag.anchor = GridBagConstraints.WEST;
			diameterValueGridBag.gridy = 4;
			layoutPanel.add(getDiameter(), diameterValueGridBag);
			
			unitsDiaLabel = new JLabel();
			unitsDiaLabel.setMinimumSize(new Dimension(100, 22));
			unitsDiaLabel.setText("mm");
			
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 4;
			layoutPanel.add(unitsDiaLabel, gridBagConstraints4);
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.gridx = 0;
			gridBagConstraints31.anchor = GridBagConstraints.EAST;
			gridBagConstraints31.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints31.gridy = 5;
			holeLengthLabel = new JLabel();
			holeLengthLabel.setText("Hole Length");
			
//			layoutPanel.add(getAnglesOffZero(), gridBagConstraints21);
			layoutPanel.add(holeLengthLabel, gridBagConstraints31);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.gridy = 5;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.insets = new Insets(2, 10, 5, 25);
			gridBagConstraints1.gridx = 1;
			layoutPanel.add(getHoleLength(), gridBagConstraints1);
			
				GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
				gridBagConstraints41.gridx = 2;
				gridBagConstraints41.gridy = 5;
				gridBagConstraints41.anchor = GridBagConstraints.WEST;
				gridBagConstraints41.insets = new Insets(0, 0, 5, 10);
				unitsHLLabel = new JLabel();
				unitsHLLabel.setMinimumSize(new Dimension(100, 22));
				unitsHLLabel.setText("meters");
				layoutPanel.add(unitsHLLabel, gridBagConstraints41);
			
			
			benchLabel = new JLabel();
			benchLabel.setText("Bench Height");
			benchLabel.setPreferredSize(new Dimension(94, 16));
			benchLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints depthLabelGridBag = new GridBagConstraints();
			depthLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			depthLabelGridBag.weightx = 1.0;
			depthLabelGridBag.gridx = 0;
			depthLabelGridBag.insets = new Insets(2, 25, 5, 10);
			depthLabelGridBag.anchor = GridBagConstraints.WEST;
			depthLabelGridBag.gridy = 6;
			layoutPanel.add(benchLabel, depthLabelGridBag);
			
			GridBagConstraints depthValueGridBag = new GridBagConstraints();
			depthValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			depthValueGridBag.weightx = 1.0;
			depthValueGridBag.gridx = 1;
			depthValueGridBag.insets = new Insets(2, 10, 5, 25);
			depthValueGridBag.anchor = GridBagConstraints.WEST;
			depthValueGridBag.gridy = 6;
			layoutPanel.add(getBench(), depthValueGridBag);
			
			unitsDepLabel = new JLabel();
			unitsDepLabel.setMinimumSize(new Dimension(100, 22));
			unitsDepLabel.setText("meters");
			
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 6;
			layoutPanel.add(unitsDepLabel, gridBagConstraints5);
			
			bearingLabel = new JLabel();
			bearingLabel.setText("Bearing Value");
			bearingLabel.setPreferredSize(new Dimension(94, 16));
			bearingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints bearingLabelGridBag = new GridBagConstraints();
			bearingLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			bearingLabelGridBag.weightx = 1.0;
			bearingLabelGridBag.gridx = 0;
			bearingLabelGridBag.insets = new Insets(2, 25, 5, 10);
			bearingLabelGridBag.anchor = GridBagConstraints.WEST;
			bearingLabelGridBag.gridy = 7;
			layoutPanel.add(bearingLabel, bearingLabelGridBag);
			
			GridBagConstraints bearingValueGridBag = new GridBagConstraints();
			bearingValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			bearingValueGridBag.weightx = 1.0;
			bearingValueGridBag.gridx = 1;
			bearingValueGridBag.insets = new Insets(2, 10, 5, 25);
			bearingValueGridBag.anchor = GridBagConstraints.WEST;
			bearingValueGridBag.gridy = 7;
			layoutPanel.add(getBearing(), bearingValueGridBag);
			
			unitsBeaLabel = new JLabel();
			unitsBeaLabel.setMinimumSize(new Dimension(100, 22));
			unitsBeaLabel.setText("degrees");
			
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 7;
			layoutPanel.add(unitsBeaLabel, gridBagConstraints6);
			
			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.gridx = 0;
			gridBagConstraints81.anchor = GridBagConstraints.EAST;
			gridBagConstraints81.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints81.gridy = 8;
			
			floorRLLabel = new JLabel();
			floorRLLabel.setText("Floor Level");
			layoutPanel.add(floorRLLabel, gridBagConstraints81);
			GridBagConstraints floorRLGridBag = new GridBagConstraints();
			floorRLGridBag.fill = GridBagConstraints.HORIZONTAL;
			floorRLGridBag.gridy = 8;
			floorRLGridBag.weightx = 1.0;
			floorRLGridBag.insets = new Insets(0, 10, 5, 25);
			floorRLGridBag.gridx = 1;
			layoutPanel.add(getFloorRL(), floorRLGridBag);
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 2;
			gridBagConstraints12.anchor = GridBagConstraints.WEST;
			gridBagConstraints12.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints12.gridy = 8;
			unitFRLLabel = new JLabel();
			unitFRLLabel.setMinimumSize(new Dimension(100, 22));
			unitFRLLabel.setText("meters");
			
			layoutPanel.add(unitFRLLabel, gridBagConstraints12);
			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.gridx = 0;
			gridBagConstraints91.anchor = GridBagConstraints.EAST;
			gridBagConstraints91.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints91.gridy = 9;
			
			toeRLLabel = new JLabel();
			toeRLLabel.setText("Toe Level");
			layoutPanel.add(toeRLLabel, gridBagConstraints91);
			GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
			gridBagConstraints111.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints111.gridy = 9;
			gridBagConstraints111.weightx = 1.0;
			gridBagConstraints111.insets = new Insets(0, 10, 5, 25);
			gridBagConstraints111.gridx = 1;
			layoutPanel.add(getToeRL(), gridBagConstraints111);
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints13.gridy = 9;
			unitsTRLLabel = new JLabel();
			unitsTRLLabel.setMinimumSize(new Dimension(100, 22));
			unitsTRLLabel.setText("meters");
			layoutPanel.add(unitsTRLLabel, gridBagConstraints13);
			
			angleLabel = new JLabel();
			angleLabel.setText("Hole Angle");
			angleLabel.setPreferredSize(new Dimension(94, 16));
			angleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints angleLabelGridBag = new GridBagConstraints();
			angleLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			angleLabelGridBag.weightx = 1.0;
			angleLabelGridBag.gridx = 0;
			angleLabelGridBag.insets = new Insets(2, 25, 5, 10);
			angleLabelGridBag.anchor = GridBagConstraints.WEST;
			angleLabelGridBag.gridy = 10;
			layoutPanel.add(angleLabel, angleLabelGridBag);
			
			GridBagConstraints angleValueGridBag = new GridBagConstraints();
			angleValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			angleValueGridBag.weightx = 1.0;
			angleValueGridBag.gridx = 1;
			angleValueGridBag.insets = new Insets(2, 10, 5, 25);
			angleValueGridBag.anchor = GridBagConstraints.WEST;
			angleValueGridBag.gridy = 10;
			layoutPanel.add(getAngle(), angleValueGridBag);
			
			unitsAngLabel = new JLabel();
			unitsAngLabel.setMinimumSize(new Dimension(100, 22));
			unitsAngLabel.setText("degrees");
			
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 10;
			layoutPanel.add(unitsAngLabel, gridBagConstraints8);
			
			subdrillLabel = new JLabel();
			subdrillLabel.setText("Subdrill Value");
			subdrillLabel.setPreferredSize(new Dimension(94, 16));
			subdrillLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints subdrillLabelGridBag = new GridBagConstraints();
			subdrillLabelGridBag.fill = GridBagConstraints.HORIZONTAL;
			subdrillLabelGridBag.weightx = 1.0;
			subdrillLabelGridBag.gridx = 0;
			subdrillLabelGridBag.insets = new Insets(2, 14, 5, 10);
			subdrillLabelGridBag.anchor = GridBagConstraints.WEST;
			subdrillLabelGridBag.gridy = 11;
			layoutPanel.add(subdrillLabel, subdrillLabelGridBag);
			
			GridBagConstraints subdrillValueGridBag = new GridBagConstraints();
			subdrillValueGridBag.fill = GridBagConstraints.HORIZONTAL;
			subdrillValueGridBag.weightx = 1.0;
			subdrillValueGridBag.gridx = 1;
			subdrillValueGridBag.insets = new Insets(2, 10, 5, 25);
			subdrillValueGridBag.anchor = GridBagConstraints.WEST;
			subdrillValueGridBag.gridy = 11;
			layoutPanel.add(getSubdrill(), subdrillValueGridBag);
			
			unitsSubLabel = new JLabel();
			unitsSubLabel.setMinimumSize(new Dimension(100, 22));
			unitsSubLabel.setText("meters");
			
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 2;
			gridBagConstraints9.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridy = 11;
			layoutPanel.add(unitsSubLabel, gridBagConstraints9);
			GridBagConstraints gbc_lblDisplayShape = new GridBagConstraints();
			gbc_lblDisplayShape.anchor = GridBagConstraints.EAST;
			gbc_lblDisplayShape.insets = new Insets(2, 14, 5, 10);
			gbc_lblDisplayShape.gridx = 0;
			gbc_lblDisplayShape.gridy = 12;
			layoutPanel.add(getDisplayShape(), gbc_lblDisplayShape);
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinner.insets = new Insets(2, 10, 5, 25);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 12;
			layoutPanel.add(getHoleShape(), gbc_spinner);
			GridBagConstraints gbc_ShapeLabel = new GridBagConstraints();
			gbc_ShapeLabel.anchor = GridBagConstraints.WEST;
			gbc_ShapeLabel.insets = new Insets(0, 0, 5, 0);
			gbc_ShapeLabel.weightx = 1;
			gbc_ShapeLabel.gridx = 2;
			gbc_ShapeLabel.gridy = 12;
			layoutPanel.add(getShapeLabel(), gbc_ShapeLabel);
			GridBagConstraints gbc_lblDisplayColour = new GridBagConstraints();
			gbc_lblDisplayColour.anchor = GridBagConstraints.EAST;
			gbc_lblDisplayColour.insets = new Insets(2, 14, 5, 10);
			gbc_lblDisplayColour.gridx = 0;
			gbc_lblDisplayColour.gridy = 13;
			layoutPanel.add(getLblDisplayColour(), gbc_lblDisplayColour);
			GridBagConstraints gbc_colorButton = new GridBagConstraints();
			gbc_colorButton.fill = GridBagConstraints.BOTH;
			gbc_colorButton.insets = new Insets(0, 0, 5, 5);
			gbc_colorButton.gridx = 1;
			gbc_colorButton.gridy = 13;
			layoutPanel.add(getColorButton(), gbc_colorButton);
			layoutPanel.add(badValuesLabel, badValuesGridBag);
			layoutPanel.add(unitLabel, unitLabelGridBag);
			layoutPanel.add(getHoleTypeButton(), toggleHoleTypeGridBag);
			layoutPanel.add(getSaveAsPreferredCheckBox(), gridBagConstraints);
			layoutPanel.add(getToggleUnitType(), gridBagConstraints11);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.gridwidth = 3;
			gbc_panel.insets = new Insets(0, 5, 5, 5);
			gbc_panel.fill = GridBagConstraints.BOTH;
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 17;
			layoutPanel.add(getPanel(), gbc_panel);
			
			
		
		}
		return layoutPanel;
	}
	/**
	 * This method initializes toggleHoleTypeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getHoleTypeButton() {
		if (holeTypeButton == null) {
			holeTypeButton = new JButton();
			holeTypeButton.setText("Dummy Hole");
			holeTypeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (holeTypeButton.getText().equals("Dummy Hole")) {
						holeTypeButton.setText("Hole");
						
					}
					else 
						holeTypeButton.setText("Dummy Hole");
						
					ghostFields();
					
					}
				
				});
			
			
		}
		return holeTypeButton;
	}
	
	private JTextField getNorthing() {
		if (northing == null) {
			northing = new JTextField();
			northing.setMinimumSize(dimension22);
			northing.setName("Northing Value");
			
			northing.setHorizontalAlignment(JTextField.TRAILING);
			northing.setText("1000.00");
			northing.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input) {
					
					try {
						double d = Double.parseDouble(getNorthing().getText());
						getNorthing().setText(Double.toString(d));
						getNorthing().setForeground(Color.black);
						if ( d < 0)
							throw new NegativeNumberException ("Northing value is outside the range");
						
						return true;
					
					}
					catch (NegativeNumberException nne){
						getNorthing().setForeground(Color.red);
						System.out.println("Caught at JTextField getNorthing() - NegativeNumberException");
						return false;
					}
					catch (NumberFormatException nfe){
						getNorthing().setForeground(Color.red);
						System.out.println("Caught at JTextField getNorthing() - NumberFormatException");
						return false;
					}
					
				}
			});
			
		}
		return northing;
	}
	
	private JTextField getEasting() {
		if (easting == null) {
			easting = new JTextField();
			easting.setMinimumSize(dimension22);
			easting.setName("Easting Value");
			easting.setText("5000.00");
			easting.setHorizontalAlignment(JTextField.TRAILING);
			easting.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
				try {
						double d = Double.parseDouble(getEasting().getText());
						getEasting().setText(Double.toString(d));
						getEasting().setForeground(Color.black);
						if ( d < 0)
							throw new NegativeNumberException ("Easting value is outside the range");
						return true;
					}
					catch (NegativeNumberException nne) {
					getEasting().setForeground(Color.red);
					System.out.println("Caught at JTextField getEasting() - NegativeNumberException");
					return false;
					}

					catch (NumberFormatException nfe){
						getEasting().setForeground(Color.red);
						System.out.println("Caught at JTextField getEasting() - NumberFormatException");
						return false;
					}
									}
			});
			
		}
		return easting;
	}
	
	private JTextField getDiameter() {
		if (diameter == null) {
			diameter = new JTextField();
			diameter.setMinimumSize(dimension22);
			diameter.setName("Diameter Value");
			diameter.setText("89");
			diameter.setHorizontalAlignment(JTextField.TRAILING);
			diameter.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						double d = Double.parseDouble(getDiameter().getText());
						getDiameter().setText(Double.toString(d));
						getDiameter().setForeground(Color.black);
						if ( d < 0){
							throw new NegativeNumberException ("Diameter value is outside the range");
						}
						else if (d==0){
							throw new ZeroArgumentException ("Diameter can not be a zero value");
						}
						
						return true;
					}
					catch (NegativeNumberException nne) {
						getDiameter().setForeground(Color.red);
						System.out.println("Caught at JTextField getEasting() - NegativeNumberException");
						return false;
					}
					catch (ZeroArgumentException zae){
						getDiameter().setForeground(Color.red);
						System.out.println("Caught at JTextField getEasting() - ZeroArgumentException");
						return false;
					}
					catch (NumberFormatException nfe){
						getDiameter().setForeground(Color.red);
						System.out.println("Caught at JTextField getEasting() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return diameter;
	}
	
	private JTextField getBench() {
		if (bench == null) {
			bench = new JTextField();
			bench.setMinimumSize(dimension22);
			bench.setName("Bench depth Value");
			bench.setText("10");
			bench.setHorizontalAlignment(JTextField.TRAILING);
			bench.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						double d = Double.parseDouble(getBench().getText());
						getBench().setText(Double.toString(d));
						getBench().setForeground(Color.black);
						if ( d < 0)
							throw new NegativeNumberException ("Bench Height must be a positive number");
						else if ( d==0)
							throw new ZeroArgumentException ("Bench Height must be greater than zero");
						
						
						double collarRL = Double.parseDouble(getCollarRL().getText());
						
						double subdrill = Double.parseDouble(getSubdrill().getText());
						double angle = Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
					   				   										
						getFloorRL().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-d))));
						getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-d)-subdrill))));
//					    if (getAnglesOffZero().isSelected()){
//					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((d+subdrill)/Math.cos(angle)))));
//					    	getFloorRL().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-d))));
//					    }
//					    else{
					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((d+subdrill)/Math.sin(angle)))));
					    	getFloorRL().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-d))));

//					    }

						return true;

					} 
					catch (NegativeNumberException nne) {
						getBench().setForeground(Color.red);
						System.out.println("Caught at JTextField getBench() - NegativeNumberException");
						return false;
					}
					catch (ZeroArgumentException zae){
						getBench().setForeground(Color.red);
						System.out.println("Caught at JTextField getBench() - ZeroArgumentException");
						return false;
					}
					catch (NumberFormatException nfe){
						getBench().setForeground(Color.red);
						System.out.println("Caught at JTextField getBench() - NumberFormatException");
						return false;
					}
				}
			});
			
		}
		return bench;
	}
	private JSpinner getBearing() {
		if (bearing == null) {
			bearing = new JSpinner(degreeSpin2);
			bearing.setMinimumSize(dimension22);
			bearing.setName("Bearing Value");
			bearing.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getBearing().getValue().toString());
						
							getBearing().setValue(d);
							getBearing().setForeground(Color.black);	
						
						return true;
						}
					catch (NumberFormatException nfe){
						getBearing().setForeground(Color.red);
						System.out.println("Caught at JSpinner getBench() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return bearing;
	}
	private JTextField getCollarRL() {
		if (collarRL == null) {
			collarRL = new JTextField();
			collarRL.setMinimumSize(dimension22);
			collarRL.setName("RL Value");
			collarRL.setText("0.00");
			collarRL.setHorizontalAlignment(JTextField.TRAILING);
			collarRL.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
						try {
							double collarRL = Double.parseDouble(getCollarRL().getText());
							getCollarRL().setText(Double.toString(collarRL));
							getCollarRL().setForeground(Color.black);
														
							double subdrill = Double.parseDouble(getSubdrill().getText());
							
							double floorRL = Double.parseDouble(getFloorRL().getText());
							double angle = Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
						   				   										
//							if (getAnglesOffZero().isSelected()){
//						    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.cos(angle)))));
//						    }
//						    else{
						    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.sin(angle)))));
//						    }
							getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
							return true;

						} 
						
						catch (NumberFormatException nfe){
							getBench().setForeground(Color.red);
							System.out.println("Caught at JTextField getCollarRL() - NumberFormatException");
							return false;
						}
					}
			});
			
		}
		return collarRL;
	}
	
	private JTextField getFloorRL() {
		if (floorRL == null) {
			floorRL = new JTextField();
			floorRL.setPreferredSize(dimension22);
			floorRL.setText("0.00");
			floorRL.setHorizontalAlignment(JTextField.TRAILING);
			floorRL.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						double floorRL = Double.parseDouble(getFloorRL().getText());
						getFloorRL().setForeground(Color.black);
						getFloorRL().setText(Double.toString(floorRL));							
						double subdrill = Double.parseDouble(getSubdrill().getText());
						
						double collarRL = Double.parseDouble(getCollarRL().getText());
						double angle = Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
					   				   										
						
//						if (getAnglesOffZero().isSelected()){
//					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.cos(angle)))));
//					    }
//					    else{
					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.sin(angle)))));
//					    }
						getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
				    	getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
						return true;

					} 
					
					catch (NumberFormatException nfe){
						getBench().setForeground(Color.red);
						System.out.println("Caught at JTextField getFloorRL() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return floorRL;
	}

	/**
	 * This method initializes toeRLTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getToeRL() {
		if (toeRL == null) {
			toeRL = new JTextField();
			toeRL.setText("0.00");
			toeRL.setPreferredSize(dimension22);
			toeRL.setHorizontalAlignment(JTextField.TRAILING);
			toeRL.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						double toeRL = Double.parseDouble(getToeRL().getText());
						getToeRL().setText(Double.toString(toeRL));
						getToeRL().setForeground(Color.black);
													
						
						double floorRL = Double.parseDouble(getFloorRL().getText());
						double collarRL = Double.parseDouble(getCollarRL().getText());
						double angle = Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
					   				   										
						
//						if (getAnglesOffZero().isSelected()){
//					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+(floorRL-toeRL))/Math.cos(angle)))));
//					    	getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
//					    	
//					    }
//					    else{
					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+(floorRL-toeRL))/Math.sin(angle)))));
					    	getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
//					    }
						return true;

					} 
					catch (NumberFormatException nfe){
						getToeRL().setForeground(Color.red);
						System.out.println("Caught at JTextField getToeRL() - NumberFormatException");
						return false;
					}
					catch (IllegalArgumentException e){
						getToeRL().setForeground(Color.red);
						System.out.println("Caught at JTextField getToeRL() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return toeRL;
		
	}


	
	private JSpinner getAngle() {
		if (angle == null) {
			angle = new JSpinner(degreeSpin1);
			angle.setMinimumSize(dimension22);
			angle.setName("Angle Value");
			angle.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getAngle().getValue().toString());
						getAngle().setValue(d);
						getAngle().setForeground(Color.black);
						
						return true;
						}
					catch (NumberFormatException nfe){
						getAngle().setForeground(Color.red);
						System.out.println("Caught at JSpinner getAngle() - NumberFormatException");
						return false;
						}
				}
			});
				
		}
		return angle;
	}
	private JTextField getSubdrill() {
		if (subdrill == null) {
			subdrill = new JTextField();
			subdrill.setMinimumSize(dimension22);
			subdrill.setName("Subdrill Value");
			subdrill.setText("0.00");
			subdrill.setHorizontalAlignment(JTextField.TRAILING);
			subdrill.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						double subdrill = Double.parseDouble(getSubdrill().getText());
						getSubdrill().setText(Double.toString(subdrill));
						getSubdrill().setForeground(Color.black);
													
						
						double floorRL = Double.parseDouble(getFloorRL().getText());
						double collarRL = Double.parseDouble(getCollarRL().getText());
						double angle = Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
					   				   										
						
//						if (getAnglesOffZero().isSelected()){
//					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.cos(angle)))));
//					    	getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
//					    	getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
//					    }
//					    else{
					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.sin(angle)))));
					    	getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
					    	getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
//					    }
				    	//getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
						//getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
						return true;

					} 
					catch (NumberFormatException nfe){
						getBench().setForeground(Color.red);
						System.out.println("Caught at JTextField getSubdrill() - NumberFormatException");
						return false;
					}
				}
			});
			
		}
		return subdrill;
	}
	
	private JButton getCancel() {	
		if (cancel == null) {
			cancel = new JButton();
			cancel.setPreferredSize(new Dimension(80, 25));
			cancel.setText("Cancel");
			cancel.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					okPushed = false;
					cancelPushed = true;
					//System.out.println("buttonPush = false");
					setVisible(false);
					//System.out.println("setVisible(false)");
					status = ("New pattern creation cancelled...");
					//System.out.println(status);
				}
			});
		}
		return cancel;
	}
	private JButton getOK() {

		if (ok == null) {
			ok = new JButton();
			ok.setPreferredSize(new Dimension(80, 25));
			ok.setText("OK");

			ok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					okPushed = true;
					cancelPushed = false;
					setVisible(false);
					
					if (getHoleTypeButton().getText().equals("Hole")) {
						status = ("New Hole created...");
					}
					else
						status = ("New Dummy created...");
					
					System.out.println("Northing = " + getNorthing().getText() + "  && Easting =" + getEasting().getText());
				}
			});
		}
		return ok;
	}

	private JTextField getHoleLength() {
		if (holeLength == null) {
			holeLength = new JTextField();
			holeLength.setName("Hole Length");
			holeLength.setMinimumSize(dimension22);
			holeLength.setText("10");
			holeLength.setHorizontalAlignment(JTextField.TRAILING);
			holeLength.setInputVerifier(new InputVerifier(){
			  public boolean verify(  JComponent input){
			    try {
			    	if (holeAngleOffZero.isSelected()){
			    		
		    			double angle=Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
		    			double subdrill = Double.parseDouble(getSubdrill().getText());
			    		try{
			    			double holeLength = Double.parseDouble(getHoleLength().getText());
			    			getHoleLength().setText(Double.toString(holeLength));
			    			getHoleLength().setForeground(Color.black);
			    			if ( holeLength < 0){
			    				throw new NegativeNumberException ("Diameter value is outside the range");
			    			}
			    			else if (holeLength==0){
			    				throw new ZeroArgumentException ("Diameter can not be a zero value");
			    			}
			    			
			    			getBench().setText(Double.toString(Double.parseDouble(decimal2.format((holeLength-subdrill)*Math.cos(angle)))));
			    			return true;
			    		}
			    		catch (NegativeNumberException nne){
			    			getHoleLength().setForeground(Color.red);
							System.out.println("Caught at JTextField getHoleLength() - NegativeNumberException");
							return false;
			    		}
			    		catch (ZeroArgumentException zae){
			    			getHoleLength().setForeground(Color.red);
							System.out.println("Caught at JTextField getHoleLength() - ZeroArgumentException");
							return false;
			    		}
			    		
			    	}
			    	else {
			    		
		    			double angle=Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
		    			double subdrill = Double.parseDouble(getSubdrill().getText());
		    			//holeLength.setText(Double.toString((collarRL-floorRL+subdrill)/Math.sin(angle)));
			    		
			    		try{
			    			double holeLength = Double.parseDouble(getHoleLength().getText());
			    			getHoleLength().setText(Double.toString(holeLength));
			    			getHoleLength().setForeground(Color.black);
			    			if ( holeLength < 0){
			    				throw new NegativeNumberException ("Diameter value is outside the range");
			    			}
			    			else if (holeLength==0){
			    				throw new ZeroArgumentException ("Diameter can not be a zero value");
			    			}
			    			
			    			getBench().setText(Double.toString(Double.parseDouble(decimal2.format((holeLength-subdrill)*Math.sin(angle)))));
			    			return true;
			    		}
			    		catch (NegativeNumberException nne){
			    			getHoleLength().setForeground(Color.red);
							System.out.println("Caught at JTextField getHoleLength() - NegativeNumberException");
							return false;
			    		}
			    		catch (ZeroArgumentException zae){
			    			getHoleLength().setForeground(Color.red);
							System.out.println("Caught at JTextField getHoleLength() - ZeroArgumentException");
							return false;
			    		}
			    		
			    		
			    	}
			    	
			    }
			 catch (NumberFormatException nfe) {
			      getBench().setForeground(Color.red);
			      return false;
			    }
			  }
			});
		}
		return holeLength;
	}

//	private JCheckBox getAnglesOffZero() {
//		if (holeAngleOffZero == null) {
//			holeAngleOffZero = new JCheckBox();
//			holeAngleOffZero.setHorizontalAlignment(SwingConstants.TRAILING);
//			holeAngleOffZero.setHorizontalTextPosition(SwingConstants.LEADING);
//			holeAngleOffZero.setText("Vertical holes are zero degrees");
//		}
//		return holeAngleOffZero;
//	}

//SET METHODS START	
	private JTextField setNorthing() {
		return
		northing;
	}
	private JTextField setEasting() {
		return
		easting;
	}
	private JTextField setDiameter() {
		return
		diameter;
	}
	private JTextField setHoleLength() {
		return
		holeLength;
	}
	private JTextField setBench() {
		return
		bench;
	}
	private JSpinner setBearing() {
		return
		bearing;
	}
	private JTextField setCollarRL() {
		return
		collarRL;
	}
	private JTextField setFloorRL() {
		return
		floorRL;
	}
	private JTextField setToeRL() {
		return
		toeRL;
	}
	private JSpinner setAngle() {
		return
		angle;
	}
	private JTextField setSubdrill() {
		return
		subdrill;
	}
	private JSpinner setShape(){
		return
				shape;
	}
	private JButton setColorButton(){
		return
				colorButton;
	}
	private JButton setHoleTypeButton() {
		return
		holeTypeButton;
	}
	private JButton setToggleUnitType() {
		return
		toggleUnitType;
	}
	
//SET METHODS END	
	
	
	private void ghostFields() {
		
		if (holeTypeButton.getText().equals("Dummy Hole")) {
			diameterLabel.setEnabled(false);
			getDiameter().setEnabled(false);
			benchLabel.setEnabled(false);
			getBench().setEnabled(false);
			bearingLabel.setEnabled(false);
			getBearing().setEnabled(false);
			collarRLLabel.setEnabled(true);
			getCollarRL().setEnabled(true);
			holeLengthLabel.setEnabled(false);
			getHoleLength().setEnabled(false);
			floorRLLabel.setEnabled(false);
			getFloorRL().setEnabled(false);
			toeRLLabel.setEnabled(false);
			getToeRL().setEnabled(false);
			angleLabel.setEnabled(false);
			getAngle().setEnabled(false);
			subdrillLabel.setEnabled(false);
			getSubdrill().setEnabled(false);
			shapeLabel.setEnabled(false);
			getHoleShape().setEnabled(false);
//			getAnglesOffZero().setEnabled(false);
			}
		
		else {
			diameterLabel.setEnabled(true);
			getDiameter().setEnabled(true);
			benchLabel.setEnabled(true);
			getBench().setEnabled(true);
			bearingLabel.setEnabled(true);
			getBearing().setEnabled(true);
			collarRLLabel.setEnabled(true);
			getCollarRL().setEnabled(true);
			holeLengthLabel.setEnabled(true);
			getHoleLength().setEnabled(true);
			floorRLLabel.setEnabled(true);
			getFloorRL().setEnabled(true);
			toeRLLabel.setEnabled(true);
			getToeRL().setEnabled(true);
			angleLabel.setEnabled(true);
			getAngle().setEnabled(true);
			subdrillLabel.setEnabled(true);
			getSubdrill().setEnabled(true);
			shapeLabel.setEnabled(true);
			getHoleShape().setEnabled(true);
//			getAnglesOffZero().setEnabled(true);
			}
		
		
	}
	
	public static Dummy showBox(Frame LiteTieWindow) throws NumberFormatException, ZeroArgumentException, NegativeNumberException{
		DialogCreateHole newHole = new DialogCreateHole(LiteTieWindow);
		return
			//showBox(LiteTieWindow, -1,-1,-1, true);
			showBox(LiteTieWindow, Double.parseDouble(newHole.getNorthing().getText()), 
					Double.parseDouble(newHole.getEasting().getText()), 
					Double.parseDouble(newHole.getCollarRL().getText()), true);
	}

	public static Dummy showBox(Frame LiteTieWindow, double clickN, double clickE, double averageCRL, boolean dummyOrHole) throws ZeroArgumentException, NegativeNumberException {
		
		DialogCreateHole newHole = new DialogCreateHole(LiteTieWindow);
		newHole.setModal(true);
		Dummy dummy = new Dummy(clickN,clickE, averageCRL);
			
		try {
			Scanner readPrefs = new Scanner(new File("holePrefs.ltpf"));
			newHole.setNorthing().setText(readPrefs.nextLine());//1
			newHole.setEasting().setText(readPrefs.nextLine());//2
			newHole.setDiameter().setText(readPrefs.nextLine());//3
			newHole.setHoleLength().setText(readPrefs.nextLine());//4
			newHole.setBench().setText(readPrefs.nextLine());//5
			newHole.setBearing().setValue(Double.parseDouble(readPrefs.nextLine()));//6
			newHole.setCollarRL().setText(readPrefs.nextLine());//7
			newHole.setFloorRL().setText(readPrefs.nextLine());//8
			newHole.setToeRL().setText(readPrefs.nextLine());//9
			newHole.setAngle().setValue(Double.parseDouble(readPrefs.nextLine()));//10
			newHole.setSubdrill().setText(readPrefs.nextLine());//11
			newHole.setShape().setValue(Integer.parseInt(readPrefs.nextLine()));//12
			newHole.setColorButton().setBackground(Color.decode(readPrefs.nextLine()));
//			if(readPrefs.nextLine().equals("true")){
//				newHole.getAnglesOffZero().setSelected(true);
//			}
//			else{newHole.getAnglesOffZero().setSelected(false);}
//			
			newHole.setToggleUnitType().setText(readPrefs.nextLine());
		
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(LiteTieWindow,
				    "Unable to Load Defaults - File Not Found - Program Defaults Loaded",
				    "holePrefs.ltpf - File Not Found",
				    JOptionPane.ERROR_MESSAGE);
			System.out.println("Caught FileNotFoundException - showBox()");
		
			newHole.setNorthing().setText(Double.toString(clickN));
			newHole.setEasting().setText(Double.toString(clickE));
			newHole.setDiameter().setText("89");
			newHole.setHoleLength().setText("11");
			newHole.setBench().setText("10");
			newHole.setBearing().setValue(Double.parseDouble("0"));
			newHole.setCollarRL().setText(Double.toString(averageCRL));
			newHole.setFloorRL().setText("90");
			newHole.setToeRL().setText("89");
			newHole.setAngle().setValue(Double.parseDouble("90"));
			newHole.setSubdrill().setText("1");
			newHole.setShape().setValue(Integer.parseInt("1"));
			newHole.setColorButton().setBackground(Color.BLACK);
//			newHole.getAnglesOffZero().setSelected(false);
			newHole.setToggleUnitType().setText("Metric");
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(LiteTieWindow,
				    "Unable to Load Defaults  -  Program Defaults Loaded",
				    "holePrefs.ltpf - No Such Element Exception",
				    JOptionPane.ERROR_MESSAGE);
			System.out.println("Caught NoSuchElementException - showBox()");
		
			newHole.setNorthing().setText(Double.toString(clickN));
			newHole.setEasting().setText(Double.toString(clickE));
			newHole.setDiameter().setText("89");
			newHole.setHoleLength().setText("11");
			newHole.setBench().setText("10");
			newHole.setBearing().setValue(Double.parseDouble("0"));
			newHole.setCollarRL().setText(Double.toString(averageCRL));
			newHole.setFloorRL().setText("90");
			newHole.setToeRL().setText("89");
			newHole.setAngle().setValue(Double.parseDouble("90"));
			newHole.setSubdrill().setText("1");
			newHole.setShape().setValue(Integer.parseInt("1"));
			newHole.setColorButton().setBackground(Color.BLACK);
//			newHole.getAnglesOffZero().setSelected(false);
			newHole.setToggleUnitType().setText("Metric");
		}
		
		if (clickN != -1) {
			newHole.setNorthing().setText(Double.toString(clickN));
		}
		if (clickE != -1) {
			newHole.setEasting().setText(Double.toString(clickE));
		}
		
		
		if (dummyOrHole){
			newHole.setHoleTypeButton().setText("Hole");
		}
		else
			newHole.setHoleTypeButton().setText("Dummy Hole");
		
		newHole.ghostFields();
		
		double northing = Double.parseDouble(newHole.getNorthing().getText());
		double easting = Double.parseDouble(newHole.getEasting().getText());
		double startDiameter = Double.parseDouble(newHole.getDiameter().getText());
		double startHoleLength =Double.parseDouble(newHole.getHoleLength().getText());
		double startBench =Double.parseDouble(newHole.getBench().getText());
		double startBearing = Double.parseDouble(newHole.getBearing().getValue().toString());
		double startCRL = Double.parseDouble(newHole.getCollarRL().getText());
		double startFRL = Double.parseDouble(newHole.getFloorRL().getText());
		double startTRL = Double.parseDouble(newHole.getToeRL().getText());
		double startAngle = Double.parseDouble(newHole.getAngle().getValue().toString());
		double startSubdrill = Double.parseDouble(newHole.getSubdrill().getText());
		int startShape = Integer.parseInt(newHole.getHoleShape().getValue().toString());
		Color color = newHole.getColorButton().getBackground();
		
		Hole hole = new Hole(northing, easting, startCRL," " , " ", " ", startDiameter,  startHoleLength, startBench,
				startBearing,  startFRL, startTRL, startAngle, startSubdrill,startShape, color);
		
		
		
		
		do {
						
			newHole.setVisible(true);
			
			if (newHole.okPushed == true) {
				boolean badValue = false;
				
				
	//Easting
					try {
												
						easting = Double.parseDouble(newHole.getNorthing().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {
							
						}
						else{ 
							easting = UnitConvert.yardsToMeters(easting);}
							
						dummy.setX(easting);
						newHole.getNorthing().setForeground(Color.black);
						
					} 
					catch (NumberFormatException e) {
						newHole.getNorthing().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
//					catch (NegativeNumberException e) {
//						newHole.getNorthing().setForeground(Color.red);
//						status = ("Inappropriate value(s)...");
//						badValue = true;
//						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
//						
//					}
					
					
	//Northing
					try {
						northing = Double.parseDouble(newHole.getEasting().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {
							
						}
						else{ 
							easting = UnitConvert.yardsToMeters(northing);}
							
						dummy.setY(northing);	
						newHole.getEasting().setForeground(Color.black);
						
					} 
					catch (NumberFormatException nfe) {
						newHole.getEasting().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
//					catch (NegativeNumberException e) {
//						newHole.getEasting().setForeground(Color.red);
//						status = ("Inappropriate value(s)...");
//						badValue = true;
//						System.out.println("Bad Value(s) - showBox() method - NegativeNumberException" );
//
//					}


	//COLLAR				
					try {
						startCRL = Double.parseDouble(newHole.getCollarRL().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {

						}
						else 
							startCRL = UnitConvert.feetToCentimeters(startCRL)/100;

						dummy.setZ(startCRL);
						newHole.getCollarRL().setForeground(Color.black);

					} 
					catch (NumberFormatException nfe) {
						newHole.getCollarRL().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");

					}
					catch (IllegalArgumentException iae) {
						newHole.getCollarRL().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
					}	
	//HOLE LENGTH				
					try {
						startHoleLength = Double.parseDouble(newHole.getHoleLength().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {
						
						}
						else 
							startHoleLength = UnitConvert.feetToCentimeters(startHoleLength)/100;
						
						hole.setHoleLength(startHoleLength);
						newHole.getHoleLength().setForeground(Color.black);
					
					} 
					catch (NumberFormatException f) {
						newHole.getHoleLength().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (ZeroArgumentException z) {
						newHole.getHoleLength().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - ZeroArgumentException");
						
					}catch (NegativeNumberException n) {
						newHole.getHoleLength().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NegativeNumberException");
						
					}
					
	//DIAMETER
					try {
						startDiameter = Double.parseDouble(newHole.getDiameter().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {

						}
						else 
							startDiameter = UnitConvert.inchesToMillimeters(startDiameter);

						hole.setDiameter(startDiameter);	
						newHole.getDiameter().setForeground(Color.black);

					} 
					catch (NumberFormatException nfe) {
						newHole.getDiameter().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (NegativeNumberException e) {
						newHole.getDiameter().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NegativeNumberException");
						
					}
					catch (ZeroArgumentException z) {
						newHole.getDiameter().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - ZeroArgumentException");
						
					}
					
	//BENCH or DEPTH				
					try {
						startBench = Double.parseDouble(newHole.getBench().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {
						
						}
						else 
							startBench = UnitConvert.feetToCentimeters(startBench)/100;
						
						hole.setBench(startBench);
						newHole.getBench().setForeground(Color.black);
					
					} 
					catch (NumberFormatException nfe) {
						newHole.getBench().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (NegativeNumberException e) {
						newHole.getBench().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NegativeNumberException");
						
					}
					catch (ZeroArgumentException z) {
						newHole.getBench().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - ZeroArgumentException");
						
					}
	//BEARING				
					try {
						startBearing = Double.parseDouble((newHole.getBearing().getValue()).toString());
						newHole.getBearing().setForeground(Color.black);
						
						hole.setBearing(startBearing);

					} 
					
					
					catch (NumberFormatException nfe) {
						newHole.getBearing().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (IllegalArgumentException iae) {
						newHole.getBearing().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
						
					}
	//FLOOR				
					try {
						startFRL = Double.parseDouble(newHole.getFloorRL().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {
							
						}
						else 
							startFRL = UnitConvert.feetToCentimeters(startFRL)/100;
						
						hole.setFloorRL(startFRL);
						newHole.getCollarRL().setForeground(Color.black);
						
					} 
					
					catch (NumberFormatException nfe) {
						newHole.getFloorRL().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (IllegalArgumentException iae) {
						newHole.getFloorRL().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
					}
	//TOE				
					try {
						startTRL = Double.parseDouble(newHole.getToeRL().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {
							
						}
						else 
							startTRL = UnitConvert.feetToCentimeters(startTRL)/100;
						
						hole.setToeRL(startTRL);
						newHole.getToeRL().setForeground(Color.black);
						
					} 
					catch (NumberFormatException nfe) {
						newHole.getToeRL().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (IllegalArgumentException iae) {
						newHole.getToeRL().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
					}
					
	//ANGLE			
					try {
						startAngle = Double.parseDouble(newHole.getAngle().getValue().toString());
						newHole.getAngle().setForeground(Color.black);
					
						hole.setAngle(startAngle);
				
					} 
					catch (NumberFormatException nfe) {
						newHole.getAngle().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (IllegalArgumentException iae) {
						newHole.getAngle().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
					}
	//SUBDRILL				
					try {
						startSubdrill = Double.parseDouble(newHole.getSubdrill().getText());
						if (newHole.getToggleUnitType().getText().equals("Metric")) {
						}
						else 
							startSubdrill = UnitConvert.feetToCentimeters(startSubdrill)/100;
						
						hole.setSubdrill(startSubdrill);
						newHole.getSubdrill().setForeground(Color.black);
					
					} 
					catch (NumberFormatException nfe) {
						newHole.getSubdrill().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (IllegalArgumentException iae) {
						newHole.getSubdrill().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
					}
//SHAPE					
					try{
						startShape = Integer.parseInt((String)newHole.getHoleShape().getValue().toString());
						hole.setShape(startShape);
						newHole.getHoleShape().setForeground(Color.black);
					}
					catch (NumberFormatException nfe) {
						newHole.getHoleShape().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - NumberFormatException");
									
					}
					catch (IllegalArgumentException iae) {
						newHole.getHoleShape().setForeground(Color.red);
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
					}
//COLOR	
					try {
						color = newHole.getColorButton().getBackground();
//						newPattern.getHoleShape().setForeground(Color.black);
					
						hole.setColor(color);
				
					} 

					catch (IllegalArgumentException iae) {
						newHole.getColorButton().setForeground(Color.red);
						newHole.getColorButton().setText("Error");
						status = ("Inappropriate value(s)...");
						badValue = true;
						System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
					}
								
			if (badValue)
						continue;
			
			//CANCEL BUTTON PRODUCES A NULL POINTER EXCEPTION 
			
			else if (newHole.cancelPushed == true){ System.out.println("Cancel button pushed");	return null;}
				
		}			
			//boolean useID;
			if (newHole.saveAsPreferredCheckBox.isSelected()) {
				try {
					
					PrintStream prefWriter = new PrintStream(new File("holePrefs.ltpf"));
					
					prefWriter.println(newHole.setNorthing().getText());
					prefWriter.println(newHole.setEasting().getText());
					prefWriter.println(newHole.setDiameter().getText());
					prefWriter.println(newHole.setHoleLength().getText());
					prefWriter.println(newHole.setBench().getText());
					prefWriter.println(newHole.setBearing().getValue().toString());
					prefWriter.println(newHole.setCollarRL().getText());
					prefWriter.println(newHole.setFloorRL().getText());
					prefWriter.println(newHole.setToeRL().getText());
					prefWriter.println(newHole.setAngle().getValue().toString());
					prefWriter.println(newHole.setSubdrill().getText());
					prefWriter.println(newHole.setShape().getValue().toString());
					prefWriter.println((newHole.getColorButton().getBackground()).getRGB());
					prefWriter.println(newHole.getToggleUnitType().getText());
											
					prefWriter.close();
					
					
					
				} catch (FileNotFoundException e1) {
					status = "Unable to Save as Default - File Not Found - Check that the file is a valid and accessible LiteTie file.";
					
					JOptionPane.showMessageDialog(LiteTieWindow,
						    "Unable to Save as Default /nFile Not Found",
						    "holePrefs.ltpf - File Not Found - error",
						    JOptionPane.ERROR_MESSAGE);
					System.out.println("Caught FileNotFoundException - showBox()");
					e1.printStackTrace();
					
				}
				
			}
			
			if (newHole.okPushed == true && newHole.getHoleTypeButton().getText().equals("Dummy Hole")) {
				return
					new Dummy ( 1, northing, easting, startCRL);
			}
			else if (newHole.okPushed == true && newHole.getHoleTypeButton().getText().equals("Hole")) {
				return
					new Hole(1, northing, easting, startCRL, " ", " ", " ", startDiameter, startHoleLength, startBench, startBearing, startFRL, startTRL, startAngle , startSubdrill,startShape, color);
			}
			else if(newHole.cancelPushed == true){
				System.out.println("Cancel Button Pushed 2");
				return null;
			}

		}
		while(true);

	}	
	

	
	
	private JButton getToggleUnitType() {
		if (toggleUnitType == null) {
			toggleUnitType = new JButton();
			toggleUnitType.setMaximumSize(new Dimension(100, 29));
			toggleUnitType.setMinimumSize(new Dimension(100, 29));
			toggleUnitType.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			
			
			toggleUnitType.setText("Metric");
			toggleUnitType
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (toggleUnitType.getText().equals("Imperial")) {
								toggleUnitType.setText("Metric");
								unitsNLabel.setText("meters");
								unitsELabel.setText("meters");
								unitsDiaLabel.setText("mm");
								unitsDepLabel.setText("meters");
								unitsBeaLabel.setText("degrees");
								unitsRLLabel.setText("meters");
								unitFRLLabel.setText("meters");
								unitsTRLLabel.setText("meters");
								unitsAngLabel.setText("degrees");
								unitsSubLabel.setText("meters");
								
							} else {
								toggleUnitType.setText("Imperial");
								unitsNLabel.setText("yards ");
								unitsELabel.setText("yards ");
								unitsDiaLabel.setText("inches");
								unitsDepLabel.setText("feet");
								unitsBeaLabel.setText("degrees");
								unitsRLLabel.setText("feet");
								unitFRLLabel.setText("feet");
								unitsTRLLabel.setText("feet");
								unitsAngLabel.setText("degrees");
								unitsSubLabel.setText("feet");
								}

						}
					});
		}		
		return toggleUnitType;
	}

	/**
	 * This method initializes saveAsPreferredCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getSaveAsPreferredCheckBox() {
		if (saveAsPreferredCheckBox == null) {
			saveAsPreferredCheckBox = new JCheckBox();
			saveAsPreferredCheckBox.setSelected(true);
			saveAsPreferredCheckBox.setToolTipText("Use these settings as the defaults for holes");
			saveAsPreferredCheckBox.setSelected(false);
			saveAsPreferredCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
			saveAsPreferredCheckBox.setText("Set as default settings");
			saveAsPreferredCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
		}
		return saveAsPreferredCheckBox;
	}

	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException, ZeroArgumentException {
		showBox(null);
		
		System.exit(0);
	}
	

	private JLabel getDisplayShape() {
		if (displayShape == null) {
			displayShape = new JLabel();
			displayShape.setText("Display Shape");
			displayShape.setPreferredSize(new Dimension(94, 16));
			displayShape.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return displayShape;
	}
	private JSpinner getHoleShape() {
		if (shape == null) {
			shape = new JSpinner();
			shape.setModel(shapeIntegerModel);
			shape.setName("Shape Value");
			shape.setMinimumSize(new Dimension(80, 22));
			
			ChangeListener listener = new ChangeListener() {
			      public void stateChanged(ChangeEvent e) {
			    	  int i = Integer.parseInt(getHoleShape().getValue().toString());
						
						
						if (i == 0){
							getShapeLabel().setText("Image");
						}
						else if (i == 1){
							getShapeLabel().setText("Circle");
						}
						else if (i == 2){
							getShapeLabel().setText("Cross");
						}
						else if (i == 3){
							getShapeLabel().setText("Triangle");
						}
						else if (i == 4){
							getShapeLabel().setText("Square");
						}
						else if (i == 5){
							getShapeLabel().setText("Pentagon");
						}
						else if (i == 6){
							getShapeLabel().setText("Hexagon");
						}
						else if (i == 7){
							shapeLabel.setText("Star");
						}
						else if (i == 8){
							shapeLabel.setText("Filled Circle");
						}
						else if (i == 9){
							shapeLabel.setText("Filled Triangle");
						}
						else if (i == 10){
							shapeLabel.setText("Filled Square");
						}
			      }

			    };

			    shape.addChangeListener(listener);

			
			
		}
		return shape;
	}
	private JLabel getShapeLabel() {
		if (shapeLabel == null) {
			shapeLabel = new JLabel();
			shapeLabel.setText("circle");
			shapeLabel.setMinimumSize(new Dimension(100, 22));
			int i = Integer.parseInt(getHoleShape().getValue().toString());
		
			
			if (i == 0){
				shapeLabel.setText("Image");
			}
			else if (i == 1){
				shapeLabel.setText("Circle");
			}
			else if (i == 2){
				shapeLabel.setText("Cross");
			}
			else if (i == 3){
				shapeLabel.setText("Triangle");
			}
			else if (i == 4){
				shapeLabel.setText("Square");
			}
			else if (i == 5){
				shapeLabel.setText("Pentagon");
			}
			else if (i == 6){
				shapeLabel.setText("Hexagon");
			}
			else if (i == 7){
				shapeLabel.setText("Star");
			}
			else if (i == 8){
				shapeLabel.setText("Filled Circle");
			}
			else if (i == 9){
				shapeLabel.setText("Filled Triangle");
			}
			else if (i == 10){
				shapeLabel.setText("Filled Square");
			}
			
			
		}
		return shapeLabel;
	}
	private JButton getColorButton() {
		if (colorButton == null) {
			colorButton = new JButton("");
			colorButton.setPreferredSize(new Dimension(75, 29));
			colorButton.setMargin(new Insets(0, 0, 0, 0));
			colorButton.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			colorButton.setBackground(Color.BLACK);
			colorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Component color = null;
					currentColor = JColorChooser.showDialog(color , "Change Current Colour", getColorButton().getBackground());
					if (currentColor != null) {
						getColorButton().setBackground(currentColor);

					}
				}
			});
			}
		
		return colorButton;
	}
	private JLabel getLblDisplayColour() {
		if (lblDisplayColour == null) {
			lblDisplayColour = new JLabel();
			lblDisplayColour.setText("Display Colour");
			lblDisplayColour.setPreferredSize(new Dimension(94, 16));
			lblDisplayColour.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return lblDisplayColour;
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
}  //  @jve:decl-index=0:visual-constraint="10,-1"
