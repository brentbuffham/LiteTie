package litetie.view;
import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.InputVerifier;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.ComponentOrientation;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.JTextComponent;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;

import litetie.controller.PatternTemplate;
import litetie.model.Dummy;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.UnitConvert;
import litetie.model.ZeroArgumentException;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;


@SuppressWarnings("serial")
public class DialogCreatePattern extends JDialog {
	
	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal1 = new DecimalFormat("#0.0");
	DecimalFormat decimal0 = new DecimalFormat("#0");
	
	public static String status;
	private boolean okPushed;	
	private boolean cancelPushed;
	private boolean resizable = false;
	
	

	private JPanel newPatternAttributes = null;
	
	private JTextField northing = null;
	private JTextField easting = null;
	private JTextField diameter = null;
	private JTextField bench = null;
	private JSpinner bearing = null;
	private JTextField collarRL = null;
	private JTextField floorRL = null;
	private JTextField toeRL = null;
	private JSpinner angle = null;
	private JTextField subdrill = null;
	private JTextField burden = null;
	private JTextField spacing = null;
	private JTextField numberOfRows = null;
	private JTextField holesInRow = null;
	
	private JButton ok = null;
	private JButton cancel = null;
	private JButton togglePatternType = null;
	private JButton toggleUnitType = null;
	private Color currentColor = null;
	
	private JLabel northingValue = new JLabel("Northing Value"); 
	private JLabel eastingValue = new JLabel("Easting Value");
	private JLabel burdenLabel = new JLabel("Burden Value");
	private JLabel spacingLabel = new JLabel("Spacing Value");
	private JLabel holeLengthLabel = new JLabel("Hole Length");;
	private JLabel rowsLabel = new JLabel("Rows per Pattern");
	private JLabel holesRowsLabel = new JLabel("Holes per Row");
	private JLabel diameterLabel = new JLabel("Hole Diameter");;
	private JLabel benchLabel = new JLabel("Bench Height");
	private JLabel bearingLabel = new JLabel("Bearing Value");
	private JLabel rLLabel = new JLabel("Collar Level");
	private JLabel toeRLLabel = new JLabel("Toe Level");
	private JLabel designFloorLabel = new JLabel("Floor Level");
	private JLabel angleLabel = new JLabel("Hole Angle");
	private JLabel subdrillLabel = new JLabel("Subdrill Value");
	private JLabel badValuesLabel =  new JLabel("Values indicated by RED text are inappropriate");
	
	private JLabel unitLabel = new JLabel("Unit Type");
		
	private JLabel measurementLabel1 = null;
	private JLabel measurementLabel2 = null;
	private JLabel measurementLabel3 = null;
	private JLabel measurementLabel4 = null;
	private JLabel measurementLabel5 = null;
	private JLabel measurementLabel6 = null;
	private JLabel measurementLabel7 = null;
	private JLabel measurementLabel9 = null;
	private JLabel measurementLabel10 = null;
	private JLabel measurementLabel11 = null;
	private JLabel measurement13 = null;
	private JLabel measurement14 = null;
	private JLabel measurement15 = null;
	private JLabel measurement16 = null;
	
	private JCheckBox alignBToOJCheckBox = null;
	private JCheckBox saveAsPreferredCheckBox = null;
	private JLabel orientationLabel =  new JLabel("Orientation");
	private JSpinner orientation = null;
	
//	private Dimension dimension22 = new Dimension(100,24);
	
	SpinnerNumberModel degreeSpin1 = new SpinnerNumberModel(90, 20, 90, 1);
	SpinnerNumberModel degreeSpin2 = new SpinnerNumberModel(0.0, -360.0, 360.0, 0.1);
	SpinnerNumberModel degreeSpin3 = new SpinnerNumberModel(0.0, -360.0, 360.0, 0.1);
	SpinnerNumberModel shapeIntegerModel = new SpinnerNumberModel(1, 1, 10, 1);
	double startDegrees1 = degreeSpin1.getNumber().doubleValue(); 
	double startDegrees2 = degreeSpin2.getNumber().doubleValue();
	double startDegrees3 = degreeSpin3.getNumber().doubleValue();
	private JTextField holeLength = null;
	private JLabel displayShape;
	private JSpinner shape;
	private JLabel shapeLabel;
	private JButton colorButton;
	private JLabel lblDisplayColour;
	private JButton buttonEquilateralSpacing;
	private JButton buttonEquilateralBurden;
	private JLabel lblPatternType;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension( 700, 435);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());
	private JPanel panel_1;
	/**
	 * @param canvasPanel
	 */
	public DialogCreatePattern(JPanel canvasPanel) {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void.
	 */
	private void initialize() {
	
		this.setBounds(bounds);
		this.setTitle("Create a New Pattern");
		this.setContentPane(getNewPatternAttributes());
		this.setResizable(resizable);	
	}

	private JPanel getNewPatternAttributes() {
		if (newPatternAttributes == null) {
			
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridwidth = 3;
			gridBagConstraints15.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints15.insets = new Insets(0, 30, 0, 30);
			gridBagConstraints15.gridy = 0;
			
		
			GridBagConstraints gridBagConstraints34 = new GridBagConstraints();
			gridBagConstraints34.gridx = 5;
			gridBagConstraints34.gridwidth = 2;
			gridBagConstraints34.anchor = GridBagConstraints.EAST;
			gridBagConstraints34.gridy = 10;
			GridBagConstraints badValuesGridBag = new GridBagConstraints();
			badValuesGridBag.insets = new Insets(0, 0, 5, 0);
			badValuesGridBag.gridx = 0;
			badValuesGridBag.gridy = 11;
			badValuesGridBag.fill = GridBagConstraints.BOTH;
			badValuesGridBag.gridwidth = 8;
			badValuesGridBag.anchor = GridBagConstraints.CENTER;
			badValuesLabel.setSize(150, 60);
			badValuesLabel.setPreferredSize(new Dimension(160, 50));
			badValuesLabel.setForeground(Color.red);
			badValuesLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			badValuesLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			badValuesLabel.setHorizontalAlignment(SwingConstants.CENTER);
			
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 4;
			gridBagConstraints2.weightx = 1.0;  
			
			newPatternAttributes = new JPanel();
			GridBagLayout gbl_newPatternAttributes = new GridBagLayout();
			gbl_newPatternAttributes.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
			gbl_newPatternAttributes.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
			newPatternAttributes.setLayout(gbl_newPatternAttributes);
			newPatternAttributes.setName("New Pattern");
			eastingValue.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagEastingLabel = new GridBagConstraints();
			gridBagEastingLabel.insets = new Insets(5, 0, 5, 5);
			gridBagEastingLabel.gridx = 0;
			gridBagEastingLabel.anchor = GridBagConstraints.EAST;
			gridBagEastingLabel.gridy = 0;
			newPatternAttributes.add(eastingValue, gridBagEastingLabel);
			
			GridBagConstraints gridBagEastingValue = new GridBagConstraints();
			gridBagEastingValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagEastingValue.weightx = 0.0;
			gridBagEastingValue.gridx = 1;
			gridBagEastingValue.insets = new Insets(5, 10, 5, 25);
			gridBagEastingValue.gridy = 0;
			newPatternAttributes.add(getEasting(), gridBagEastingValue);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 2;
			gridBagConstraints.insets = new Insets(5, 0, 5, 10);
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			measurementLabel2 = new JLabel();
			measurementLabel2.setText("meters");
			newPatternAttributes.add(measurementLabel2, gridBagConstraints);
			burdenLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.insets = new Insets(5, 0, 5, 5);
			gridBagConstraints23.gridx = 4;
			gridBagConstraints23.anchor = GridBagConstraints.EAST;
			gridBagConstraints23.gridy = 0;
			newPatternAttributes.add(burdenLabel, gridBagConstraints23);
			
			GridBagConstraints gridBagBurdenValue = new GridBagConstraints();
			gridBagBurdenValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagBurdenValue.gridx = 5;
			gridBagBurdenValue.gridy = 0;
			gridBagBurdenValue.gridheight = 1;
			gridBagBurdenValue.anchor = GridBagConstraints.WEST;
			gridBagBurdenValue.ipadx = 0;
			gridBagBurdenValue.ipady = 0;
			gridBagBurdenValue.insets = new Insets(5, 10, 5, 20);
			gridBagBurdenValue.weightx = 0.0;
			newPatternAttributes.add(getBurden(), gridBagBurdenValue);
			GridBagConstraints gbc_buttonEquilateralBurden = new GridBagConstraints();
			gbc_buttonEquilateralBurden.insets = new Insets(5, 0, 5, 5);
			gbc_buttonEquilateralBurden.gridx = 6;
			gbc_buttonEquilateralBurden.gridy = 0;
			newPatternAttributes.add(getButtonEquilateralBurden(), gbc_buttonEquilateralBurden);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints3.gridx = 7;
			gridBagConstraints3.insets = new Insets(5, 0, 5, 15);
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.gridy = 0;
			measurementLabel11 = new JLabel();
			measurementLabel11.setMinimumSize(new Dimension(100, 22));
			measurementLabel11.setText("meters");
			newPatternAttributes.add(measurementLabel11, gridBagConstraints3);
			
			northingValue.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagNorthingLabel = new GridBagConstraints();
			gridBagNorthingLabel.insets = new Insets(0, 0, 5, 5);
			gridBagNorthingLabel.gridx = 0;
			gridBagNorthingLabel.anchor = GridBagConstraints.EAST;
			gridBagNorthingLabel.gridy = 1;
			newPatternAttributes.add(northingValue, gridBagNorthingLabel);
			
			GridBagConstraints gridBagConstraintsNorthingValue = new GridBagConstraints();
			gridBagConstraintsNorthingValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraintsNorthingValue.gridx = 1;
			gridBagConstraintsNorthingValue.gridy = 1;
			gridBagConstraintsNorthingValue.weighty = 0.0D;
			gridBagConstraintsNorthingValue.insets = new Insets(2, 10, 5, 25);
			gridBagConstraintsNorthingValue.ipadx = 0;
			gridBagConstraintsNorthingValue.gridwidth = 1;
			gridBagConstraintsNorthingValue.weightx = 0.0D;
			newPatternAttributes.add(getNorthing(), gridBagConstraintsNorthingValue);
			GridBagConstraints mesurementGridBag1 = new GridBagConstraints();
			mesurementGridBag1.gridx = 2;
			mesurementGridBag1.insets = new Insets(0, 0, 5, 10);
			mesurementGridBag1.anchor = GridBagConstraints.WEST;
			mesurementGridBag1.gridy = 1;
			measurementLabel1 = new JLabel();
			measurementLabel1.setText("meters");
			newPatternAttributes.add(measurementLabel1, mesurementGridBag1);
			spacingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagConstraints32 = new GridBagConstraints();
			gridBagConstraints32.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints32.gridx = 4;
			gridBagConstraints32.anchor = GridBagConstraints.EAST;
			gridBagConstraints32.gridy = 1;
			newPatternAttributes.add(spacingLabel, gridBagConstraints32);
			
			GridBagConstraints gridBagSpacingValue = new GridBagConstraints();
			gridBagSpacingValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagSpacingValue.gridx = 5;
			gridBagSpacingValue.gridy = 1;
			gridBagSpacingValue.insets = new Insets(2, 10, 5, 20);
			gridBagSpacingValue.anchor = GridBagConstraints.WEST;
			gridBagSpacingValue.weightx = 0.0;
			newPatternAttributes.add(getSpacing(), gridBagSpacingValue);
			GridBagConstraints gbc_buttonEquilateralSpacing = new GridBagConstraints();
			gbc_buttonEquilateralSpacing.insets = new Insets(0, 0, 5, 5);
			gbc_buttonEquilateralSpacing.gridx = 6;
			gbc_buttonEquilateralSpacing.gridy = 1;
			newPatternAttributes.add(getButtonEquilateralSpacing(), gbc_buttonEquilateralSpacing);
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints9.gridx = 7;
			gridBagConstraints9.insets = new Insets(0, 0, 5, 15);
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridy = 1;
			measurementLabel10 = new JLabel();
			measurementLabel10.setMinimumSize(new Dimension(100, 22));
			measurementLabel10.setText("meters");
			newPatternAttributes.add(measurementLabel10, gridBagConstraints9);
			diameterLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagDiameterLabel = new GridBagConstraints();
			gridBagDiameterLabel.insets = new Insets(0, 0, 5, 5);
			gridBagDiameterLabel.gridx = 0;
			gridBagDiameterLabel.anchor = GridBagConstraints.EAST;
			gridBagDiameterLabel.gridy = 2;
			newPatternAttributes.add(diameterLabel, gridBagDiameterLabel);
			
			GridBagConstraints gridBagDiameterValue = new GridBagConstraints();
			gridBagDiameterValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagDiameterValue.weightx = 0.0;
			gridBagDiameterValue.gridx = 1;
			gridBagDiameterValue.insets = new Insets(2, 10, 5, 25);
			gridBagDiameterValue.gridy = 2;
			newPatternAttributes.add(getDiameter(), gridBagDiameterValue);
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 2;
			gridBagConstraints1.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.gridy = 2;
			measurementLabel3 = new JLabel();
			measurementLabel3.setText("mm");
			newPatternAttributes.add(measurementLabel3, gridBagConstraints1);
			rowsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints42.gridx = 4;
			gridBagConstraints42.anchor = GridBagConstraints.EAST;
			gridBagConstraints42.gridy = 2;
			newPatternAttributes.add(rowsLabel, gridBagConstraints42);
			
			GridBagConstraints gridBagNumberOfRowsValue = new GridBagConstraints();
			gridBagNumberOfRowsValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagNumberOfRowsValue.gridx = 5;
			gridBagNumberOfRowsValue.gridy = 2;
			gridBagNumberOfRowsValue.anchor = GridBagConstraints.WEST;
			gridBagNumberOfRowsValue.insets = new Insets(2, 10, 5, 20);
			gridBagNumberOfRowsValue.weightx = 0.0;
			newPatternAttributes.add(getNumberOfRows(), gridBagNumberOfRowsValue);
			GridBagConstraints gridBagConstraints44 = new GridBagConstraints();
			gridBagConstraints44.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints44.gridx = 0;
			gridBagConstraints44.anchor = GridBagConstraints.EAST;
			gridBagConstraints44.gridy = 3;
			//			newPatternAttributes.add(getAnglesOffZero(), gridBagConstraints34);
						newPatternAttributes.add(holeLengthLabel, gridBagConstraints44);
			GridBagConstraints gridBagConstraints26 = new GridBagConstraints();
			gridBagConstraints26.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints26.gridy = 3;
			gridBagConstraints26.weightx = 1.0;
			gridBagConstraints26.insets = new Insets(2, 10, 5, 25);
			gridBagConstraints26.gridx = 1;
			newPatternAttributes.add(getHoleLength(), gridBagConstraints26);
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints53.gridx = 2;
			gridBagConstraints53.anchor = GridBagConstraints.WEST;
			gridBagConstraints53.gridy = 3;
			measurement16 = new JLabel();
			measurement16.setText("meters");
			newPatternAttributes.add(measurement16, gridBagConstraints53);
			holesRowsLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints51.gridx = 4;
			gridBagConstraints51.anchor = GridBagConstraints.EAST;
			gridBagConstraints51.gridy = 3;
			newPatternAttributes.add(holesRowsLabel, gridBagConstraints51);
			
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints41.gridx = 5;
			gridBagConstraints41.gridy = 3;
			gridBagConstraints41.insets = new Insets(2, 10, 5, 20);
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.weightx = 1.0;
			newPatternAttributes.add(getHolesInRow(), gridBagConstraints41);
			benchLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagDepthLabel = new GridBagConstraints();
			gridBagDepthLabel.insets = new Insets(0, 0, 5, 5);
			gridBagDepthLabel.gridx = 0;
			gridBagDepthLabel.anchor = GridBagConstraints.EAST;
			gridBagDepthLabel.gridy = 4;
			newPatternAttributes.add(benchLabel, gridBagDepthLabel);
			
			GridBagConstraints gridBagDepthValue = new GridBagConstraints();
			gridBagDepthValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagDepthValue.weightx = 0.0;
			gridBagDepthValue.gridx = 1;
			gridBagDepthValue.insets = new Insets(2, 10, 5, 25);
			gridBagDepthValue.gridy = 4;
			newPatternAttributes.add(getBench(), gridBagDepthValue);
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 4;
			measurementLabel4 = new JLabel();
			measurementLabel4.setText("meters");
			newPatternAttributes.add(measurementLabel4, gridBagConstraints4);
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints24.gridx = 4;
			gridBagConstraints24.anchor = GridBagConstraints.EAST;
			gridBagConstraints24.gridy = 4;
			newPatternAttributes.add(orientationLabel, gridBagConstraints24);
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints31.gridy = 4;
			gridBagConstraints31.weightx = 1.0;
			gridBagConstraints31.insets = new Insets(2, 10, 5, 25);
			gridBagConstraints31.gridx = 5;
			newPatternAttributes.add(getOrientation(), gridBagConstraints31);
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridwidth = 2;
			gridBagConstraints71.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints71.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints71.gridx = 6;
			gridBagConstraints71.anchor = GridBagConstraints.WEST;
			gridBagConstraints71.gridy = 4;
			measurement15 = new JLabel();
			measurement15.setMinimumSize(new Dimension(100, 22));
			measurement15.setText("degrees");
			newPatternAttributes.add(measurement15, gridBagConstraints71);
			bearingLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagBearingLabel = new GridBagConstraints();
			gridBagBearingLabel.insets = new Insets(0, 0, 5, 5);
			gridBagBearingLabel.gridx = 0;
			gridBagBearingLabel.anchor = GridBagConstraints.EAST;
			gridBagBearingLabel.gridy = 5;
			newPatternAttributes.add(bearingLabel, gridBagBearingLabel);
			
			GridBagConstraints gridBagBearingValue = new GridBagConstraints();
			gridBagBearingValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagBearingValue.weightx = 0.0;
			gridBagBearingValue.gridx = 1;
			gridBagBearingValue.insets = new Insets(2, 10, 5, 25);
			gridBagBearingValue.gridy = 5;
			newPatternAttributes.add(getBearing(), gridBagBearingValue);
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints5.anchor = GridBagConstraints.WEST;
			gridBagConstraints5.gridy = 5;
			measurementLabel5 = new JLabel();
			measurementLabel5.setText("degrees");
			newPatternAttributes.add(measurementLabel5, gridBagConstraints5);
			
			GridBagConstraints gbc_lblHoleSymbol = new GridBagConstraints();
			gbc_lblHoleSymbol.anchor = GridBagConstraints.EAST;
			gbc_lblHoleSymbol.insets = new Insets(0, 0, 5, 5);
			gbc_lblHoleSymbol.gridx = 4;
			gbc_lblHoleSymbol.gridy = 5;
			newPatternAttributes.add(getDisplayShape(), gbc_lblHoleSymbol);
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinner.insets = new Insets(2, 10, 5, 25);
			gbc_spinner.gridx = 5;
			gbc_spinner.gridy = 5;
			newPatternAttributes.add(getHoleShape(), gbc_spinner);
			GridBagConstraints gbc_ShapeLabel = new GridBagConstraints();
			gbc_ShapeLabel.gridwidth = 2;
			gbc_ShapeLabel.fill = GridBagConstraints.HORIZONTAL;
			gbc_ShapeLabel.anchor = GridBagConstraints.WEST;
			gbc_ShapeLabel.insets = new Insets(0, 0, 5, 0);
			gbc_ShapeLabel.gridx = 6;
			gbc_ShapeLabel.gridy = 5;
			newPatternAttributes.add(getShapeLabel(), gbc_ShapeLabel);
			rLLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagRLLabel = new GridBagConstraints();
			gridBagRLLabel.insets = new Insets(0, 0, 5, 5);
			gridBagRLLabel.gridx = 0;
			gridBagRLLabel.anchor = GridBagConstraints.EAST;
			gridBagRLLabel.gridy = 6;
			newPatternAttributes.add(rLLabel, gridBagRLLabel);
			
			GridBagConstraints gridBagRLValue = new GridBagConstraints();
			gridBagRLValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagRLValue.weightx = 0.0;
			gridBagRLValue.gridx = 1;
			gridBagRLValue.insets = new Insets(2, 10, 5, 25);
			gridBagRLValue.gridy = 6;
			newPatternAttributes.add(getCollarRL(), gridBagRLValue);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 6;
			measurementLabel6 = new JLabel();
			measurementLabel6.setText("meters");
			newPatternAttributes.add(measurementLabel6, gridBagConstraints6);
			GridBagConstraints gbc_lblDisplayColour = new GridBagConstraints();
			gbc_lblDisplayColour.anchor = GridBagConstraints.EAST;
			gbc_lblDisplayColour.insets = new Insets(0, 0, 5, 5);
			gbc_lblDisplayColour.gridx = 4;
			gbc_lblDisplayColour.gridy = 6;
			newPatternAttributes.add(getLblDisplayColour(), gbc_lblDisplayColour);
			GridBagConstraints gbc_colorButton = new GridBagConstraints();
			gbc_colorButton.fill = GridBagConstraints.BOTH;
			gbc_colorButton.insets = new Insets(0, 10, 5, 20);
			gbc_colorButton.gridx = 5;
			gbc_colorButton.gridy = 6;
			newPatternAttributes.add(getColorButton(), gbc_colorButton);
			GridBagConstraints gridBagConstraints43 = new GridBagConstraints();
			gridBagConstraints43.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints43.gridx = 0;
			gridBagConstraints43.anchor = GridBagConstraints.EAST;
			gridBagConstraints43.fill = GridBagConstraints.NONE;
			gridBagConstraints43.gridy = 7;
			newPatternAttributes.add(designFloorLabel, gridBagConstraints43);
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints52.gridy = 7;
			gridBagConstraints52.weightx = 1.0;
			gridBagConstraints52.insets = new Insets(0, 10, 5, 25);
			gridBagConstraints52.gridx = 1;
			newPatternAttributes.add(getFloorRL(), gridBagConstraints52);
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints61.gridx = 2;
			gridBagConstraints61.anchor = GridBagConstraints.WEST;
			gridBagConstraints61.gridy = 7;
			measurement14 = new JLabel();
			measurement14.setText("meters");
			newPatternAttributes.add(measurement14, gridBagConstraints61);
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints14.gridx = 4;
			gridBagConstraints14.gridwidth = 2;
			gridBagConstraints14.anchor = GridBagConstraints.EAST;
			gridBagConstraints14.gridy = 7;
			newPatternAttributes.add(getAlignBToOJCheckBox(), gridBagConstraints14);
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints12.gridx = 0;
			gridBagConstraints12.anchor = GridBagConstraints.EAST;
			gridBagConstraints12.gridy = 8;
			newPatternAttributes.add(toeRLLabel, gridBagConstraints12);
			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints25.gridy = 8;
			gridBagConstraints25.weightx = 1.0;
			gridBagConstraints25.insets = new Insets(0, 10, 5, 25);
			gridBagConstraints25.gridx = 1;
			newPatternAttributes.add(getToeRL(), gridBagConstraints25);
			
			GridBagConstraints gridBagConstraints33 = new GridBagConstraints();
			gridBagConstraints33.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints33.gridx = 2;
			gridBagConstraints33.anchor = GridBagConstraints.WEST;
			gridBagConstraints33.gridy = 8;
			measurement13 = new JLabel();
			measurement13.setText("meters");
			newPatternAttributes.add(measurement13, gridBagConstraints33);
			GridBagConstraints unitLabelGridBag = new GridBagConstraints();
			unitLabelGridBag.gridx = 4;
			unitLabelGridBag.gridy = 8;
			unitLabelGridBag.anchor = GridBagConstraints.EAST;
			unitLabelGridBag.insets = new Insets(0, 0, 5, 10);
			unitLabel.setEnabled(false);
			
			
			//badValuesLabel.setVisible(true);
			
			unitLabel.setHorizontalAlignment(SwingConstants.CENTER);
			unitLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			unitLabel.setVisible(true);
			newPatternAttributes.add(unitLabel, unitLabelGridBag);
			GridBagConstraints toggleUnitTypeGridBag = new GridBagConstraints();
			toggleUnitTypeGridBag.gridx = 5;
			toggleUnitTypeGridBag.anchor = GridBagConstraints.CENTER;
			toggleUnitTypeGridBag.insets = new Insets(0, 0, 5, 5);
			toggleUnitTypeGridBag.fill = GridBagConstraints.HORIZONTAL;
			toggleUnitTypeGridBag.gridy = 8;
			newPatternAttributes.add(getToggleUnitType(), toggleUnitTypeGridBag);
			angleLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagAngleLabel = new GridBagConstraints();
			gridBagAngleLabel.insets = new Insets(0, 0, 5, 5);
			gridBagAngleLabel.gridx = 0;
			gridBagAngleLabel.anchor = GridBagConstraints.EAST;
			gridBagAngleLabel.gridy = 9;
			newPatternAttributes.add(angleLabel, gridBagAngleLabel);
			
			GridBagConstraints gridBagAngleValue = new GridBagConstraints();
			gridBagAngleValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagAngleValue.weightx = 0.0;
			gridBagAngleValue.gridx = 1;
			gridBagAngleValue.insets = new Insets(2, 10, 5, 25);
			gridBagAngleValue.anchor = GridBagConstraints.WEST;
			gridBagAngleValue.gridy = 9;
			newPatternAttributes.add(getAngle(), gridBagAngleValue);
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 2;
			gridBagConstraints7.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints7.anchor = GridBagConstraints.WEST;
			gridBagConstraints7.gridy = 9;
			measurementLabel7 = new JLabel();
			measurementLabel7.setText("degrees");
			newPatternAttributes.add(measurementLabel7, gridBagConstraints7);
			GridBagConstraints gbc_lblPatternType = new GridBagConstraints();
			gbc_lblPatternType.anchor = GridBagConstraints.EAST;
			gbc_lblPatternType.insets = new Insets(0, 0, 5, 10);
			gbc_lblPatternType.gridx = 4;
			gbc_lblPatternType.gridy = 9;
			newPatternAttributes.add(getLblPatternType(), gbc_lblPatternType);
			
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 5;
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.weighty = 0.0D;
			gridBagConstraints11.weightx = 0.0D;
			gridBagConstraints11.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints11.gridy = 9;
			newPatternAttributes.add(getTogglePatternType(), gridBagConstraints11);
			subdrillLabel.setHorizontalAlignment(SwingConstants.TRAILING);
			
			GridBagConstraints gridBagSubdrillLabel = new GridBagConstraints();
			gridBagSubdrillLabel.gridx = 0;
			gridBagSubdrillLabel.anchor = GridBagConstraints.EAST;
			gridBagSubdrillLabel.insets = new Insets(0, 10, 5, 5);
			gridBagSubdrillLabel.ipadx = 5;
			gridBagSubdrillLabel.gridy = 10;
			newPatternAttributes.add(subdrillLabel, gridBagSubdrillLabel);
			
			
			GridBagConstraints gridBagSubdrillValue = new GridBagConstraints();
			gridBagSubdrillValue.fill = GridBagConstraints.HORIZONTAL;
			gridBagSubdrillValue.weightx = 0.0;
			gridBagSubdrillValue.gridx = 1;
			gridBagSubdrillValue.insets = new Insets(2, 10, 5, 25);
			gridBagSubdrillValue.anchor = GridBagConstraints.WEST;
			gridBagSubdrillValue.gridy = 10;
			newPatternAttributes.add(getSubdrill(), gridBagSubdrillValue);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 2;
			gridBagConstraints8.insets = new Insets(0, 0, 5, 10);
			gridBagConstraints8.anchor = GridBagConstraints.WEST;
			gridBagConstraints8.gridy = 10;
			measurementLabel9 = new JLabel();
			measurementLabel9.setText("meters");
			newPatternAttributes.add(measurementLabel9, gridBagConstraints8);
			
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.insets = new Insets(0, 0, 5, 15);
			gridBagConstraints21.gridx = 4;
			gridBagConstraints21.fill = GridBagConstraints.BOTH;
			gridBagConstraints21.gridwidth = 2;
			gridBagConstraints21.anchor = GridBagConstraints.EAST;
			gridBagConstraints21.gridy = 10;
			newPatternAttributes.add(getSaveAsPreferredCheckBox(), gridBagConstraints21);
			newPatternAttributes.add(badValuesLabel, badValuesGridBag);
			GridBagConstraints gbc_panel_1 = new GridBagConstraints();
			gbc_panel_1.gridwidth = 8;
			gbc_panel_1.insets = new Insets(0, 5, 0, 0);
			gbc_panel_1.fill = GridBagConstraints.BOTH;
			gbc_panel_1.gridx = 0;
			gbc_panel_1.gridy = 12;
			newPatternAttributes.add(getPanel_1(), gbc_panel_1);
			
		}
		return newPatternAttributes;
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
					status = ("New pattern created...");
					//System.out.println("Northing = " + getNorthing().getText() + "  && Easting =" + getEasting().getText());
				}
			});
		}
		return ok;
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
	private JButton getTogglePatternType() {
		if (togglePatternType == null) {
			togglePatternType = new JButton();
			togglePatternType.setText("Staggered");
			togglePatternType
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (togglePatternType.getText().equals("Square")) {
								togglePatternType.setText("Staggered");
							} else
								togglePatternType.setText("Square");

						}
					});
		}		
		return togglePatternType;
	}
	private JButton getToggleUnitType() {
		if (toggleUnitType == null) {
			toggleUnitType = new JButton();
			toggleUnitType.setEnabled(false);
			toggleUnitType.setMaximumSize(new Dimension(100, 29));
			toggleUnitType.setMinimumSize(new Dimension(100, 29));
			toggleUnitType.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			toggleUnitType.setPreferredSize(new Dimension(100, 25));
			toggleUnitType.setText("Metric");
			toggleUnitType
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (toggleUnitType.getText().equals("Imperial")) {
								toggleUnitType.setText("Metric");
								measurementLabel1.setText("meters");
								measurementLabel2.setText("meters");
								measurementLabel3.setText("mm");
								measurementLabel4.setText("meters");
								
								measurementLabel6.setText("meters");
								
								measurementLabel9.setText("meters");
								measurementLabel11.setText("meters");
								measurementLabel10.setText("meters");
								measurement13.setText("meters");
								measurement14.setText("meters");
							
							} 
							else {
								toggleUnitType.setText("Imperial");
								measurementLabel1.setText("yards ");
								measurementLabel2.setText("yards ");
								measurementLabel3.setText("inches");
								measurementLabel4.setText("feet");
								
								measurementLabel6.setText("feet");
								
								measurementLabel9.setText("feet");
								measurementLabel11.setText("yards ");
								measurementLabel10.setText("yards ");
								measurement13.setText("feet");
								measurement14.setText("feet");
								}
						}
					});
		}		
		return toggleUnitType;
	}
	private JCheckBox getAlignBToOJCheckBox() {
		if (alignBToOJCheckBox == null) {
			alignBToOJCheckBox = new JCheckBox();
			alignBToOJCheckBox.setText("Align bearings to Orientation");
			alignBToOJCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
			alignBToOJCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
			alignBToOJCheckBox.setSelected(true);
		}
		return alignBToOJCheckBox;
	}
	private JCheckBox getSaveAsPreferredCheckBox() {
		if (saveAsPreferredCheckBox == null) {
			saveAsPreferredCheckBox = new JCheckBox();
			saveAsPreferredCheckBox.setText("Set as the default settings");
			saveAsPreferredCheckBox.setToolTipText("Use these settings as the defaults for patterns");
			saveAsPreferredCheckBox.setSelected(false);
			saveAsPreferredCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
			saveAsPreferredCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
		}
		
		return saveAsPreferredCheckBox;
	}
	private JTextField getNorthing() {
		if (northing == null) {
			northing = new JTextField();
			northing.setMinimumSize(new Dimension(100, 22));
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
	private JTextField setNorthing() {
		return
			this.northing = getNorthing();
	}
	private JTextField getEasting() {
		if (easting == null) {
			easting = new JTextField();
			easting.setMinimumSize(new Dimension(100, 22));
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
	private JTextField setEasting() {
		return
			this.easting = getEasting();
	}
	private JTextField getDiameter() {
		if (diameter == null) {
			diameter = new JTextField();
			diameter.setMinimumSize(new Dimension(100, 22));
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
	private JTextField setDiameter() {
		return
			diameter;
	}
	private JTextField getHoleLength() {
		if (holeLength == null) {
			holeLength = new JTextField();
			holeLength.setName("Hole Length");
			holeLength.setMinimumSize(new Dimension(100, 22));
			holeLength.setText("10");
			holeLength.setHorizontalAlignment(JTextField.TRAILING);
			holeLength.setInputVerifier(new InputVerifier(){
			  public boolean verify(  JComponent input){
			    try {
//			    	
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
			    	
//			    }
			 catch (NumberFormatException nfe) {
			      getBench().setForeground(Color.red);
			      return false;
			    }
			  }
			}
			);
		}
		return holeLength;
	}
	private JTextField getBench() {
		if (bench == null) {
			bench = new JTextField();
			bench.setMinimumSize(new Dimension(100, 22));
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
	private JTextField setBench() {
		return
			bench;
	}
	private JSpinner getBearing() {
		if (bearing == null) {
			
			bearing = new JSpinner(degreeSpin2);
			bearing.setMinimumSize(new Dimension(100, 22));
			bearing.setName("Bearing Value");
			bearing.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getBearing().getValue().toString());
						double o = Double.parseDouble(getOrientation().getValue().toString());
						if (getAlignBToOJCheckBox().isSelected()) {
							getBearing().setValue(Double.toString(Math.abs(-o)));
							getBearing().setForeground(Color.black);
							System.out.println(getBearing());
						}
						else{
							getBearing().setValue(d);
							getBearing().setForeground(Color.black);	
						}
						return true;
						}
					catch (NumberFormatException nfe){
						getBearing().setForeground(Color.red);
						System.out.println("Caught - NumberFormatException");
						return false;
						}
				}
			});
		}
		return bearing;
	}
	private JSpinner setBearing() {
		return
			bearing;
	}
	private JTextField getCollarRL() {
		if (collarRL == null) {
			collarRL = new JTextField();
			collarRL.setMinimumSize(new Dimension(100, 22));
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
						    getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.sin(angle)))));
//						   
							getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
							return true;

						} 
						
						catch (NumberFormatException nfe){
							getCollarRL().setForeground(Color.red);
							System.out.println("Caught at JTextField getCollarRL() - NumberFormatException");
							return false;
						}
					}
			});
			
		}
		return collarRL;
	}
	private JTextField setCollarRL() {
		return
			collarRL;
	}
	private JTextField getFloorRL() {
		if (floorRL == null) {
			floorRL = new JTextField();
			floorRL.setPreferredSize(new Dimension(100, 22));
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

					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.sin(angle)))));
//					    }
						getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
				    	getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
						return true;

					} 
					
					catch (NumberFormatException nfe){
						getFloorRL().setForeground(Color.red);
						System.out.println("Caught at JTextField getFloorRL() - NumberFormatException");
						return false;
					}
					catch (IllegalArgumentException e){
						getFloorRL().setForeground(Color.red);
						System.out.println("Caught at JTextField getFloorRL() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return floorRL;
	}
	private JTextField getToeRL() {
		if (toeRL == null) {
			toeRL = new JTextField();
			toeRL.setText("0.00");
			toeRL.setPreferredSize(new Dimension(100, 22));
			toeRL.setHorizontalAlignment(JTextField.TRAILING);
			//System.out.println("* "+toeRL.getText());//DEBUG
			toeRL.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					//System.out.println("** "+toeRL.getText());//DEBUG
					try {
						//System.out.println("*** "+toeRL.getText());//DEBUG
						double toeRL = Double.parseDouble(getToeRL().getText());
						getToeRL().setText(Double.toString(toeRL));
						getToeRL().setForeground(Color.black);
													
						//System.out.println("**** "+getToeRL().getText());//DEBUG
						double floorRL = Double.parseDouble(getFloorRL().getText());
						double collarRL = Double.parseDouble(getCollarRL().getText());
						//double subdrill = Double.parseDouble(getSubdrill().getText());
						double angle = Math.toRadians(Double.parseDouble(getAngle().getValue().toString()));
					   				   										
						
//						if (getAnglesOffZero().isSelected()){
//					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+(floorRL-toeRL))/Math.cos(angle)))));
//					    	getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
//					    	//getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
//					    }
//					    else{
					    	getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+(floorRL-toeRL))/Math.sin(angle)))));
					    	getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
					    	//getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
//					    }
						//System.out.println("***** "+getToeRL().getText());
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
	
//	private JCheckBox getAnglesOffZero() {
//		if (holeAngleOffZero == null) {
//			holeAngleOffZero = new JCheckBox();
//			holeAngleOffZero.setHorizontalAlignment(SwingConstants.TRAILING);
//			holeAngleOffZero.setHorizontalTextPosition(SwingConstants.LEADING);
//			holeAngleOffZero.setText("Vertical holes are zero degrees");
//		}
//		return holeAngleOffZero;
//	}
	
	private JSpinner getAngle() {
		if (angle == null) {
			angle = new JSpinner(degreeSpin1);
			angle.setMinimumSize(new Dimension(100, 22));
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
			subdrill.setMinimumSize(new Dimension(100, 22));
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
//					    }
//					    else{
					    getHoleLength().setText(Double.toString(Double.parseDouble(decimal2.format((collarRL-floorRL+subdrill)/Math.sin(angle)))));
//					    }
				    	getToeRL().setText(Double.toString(Double.parseDouble(decimal2.format(floorRL-subdrill))));
						getBench().setText(Double.toString(Double.parseDouble(decimal2.format(collarRL-floorRL))));
						return true;

					} 
					catch (NumberFormatException nfe){
						getSubdrill().setForeground(Color.red);
						System.out.println("Caught at JTextField getSubdrill() - NumberFormatException");
						return false;
					}
				}
			});
			
		}
		return subdrill;
	}
	private JTextField getBurden() {
		if (burden == null) {
			burden = new JTextField();
			burden.setSize(new Dimension(100, 22));
			burden.setText("3");
			burden.setHorizontalAlignment(JTextField.TRAILING);
			burden.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						double burden= Double.parseDouble(getBurden().getText());
						getBurden().setText(Double.toString(burden));
						getBurden().setForeground(Color.black);
						if ( burden < 0)
							throw new NegativeNumberException ("Burden must be a positive number");
						else if ( burden==0)
							throw new ZeroArgumentException ("Burden must be greater than zero");
						
						return true;

					} 
					catch (NegativeNumberException nne) {
						getBurden().setForeground(Color.red);
						System.out.println("Caught at JTextField getBurden() - NegativeNumberException");
						return false;
					}
					catch (ZeroArgumentException zae){
						getBurden().setForeground(Color.red);
						System.out.println("Caught at JTextField getBurden() - ZeroArgumentException");
						return false;
					}
					catch (NumberFormatException nfe){
						getBurden().setForeground(Color.red);
						System.out.println("Caught at JTextField getBurden() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return burden;
	}
	private JTextField getSpacing() {
		if (spacing == null) {
			spacing = new JTextField();
			spacing.setMinimumSize(new Dimension(100, 22));
			spacing.setText("3");
			spacing.setHorizontalAlignment(JTextField.TRAILING);
			spacing.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						double spacing= Double.parseDouble(getSpacing().getText());
						getSpacing().setText(Double.toString(spacing));
						getSpacing().setForeground(Color.black);
						if ( spacing < 0)
							throw new NegativeNumberException ("spacing must be a positive number");
						else if ( spacing==0)
							throw new ZeroArgumentException ("spacing must be greater than zero");
						
						return true;

					} 
					catch (NegativeNumberException nne) {
						getSpacing().setForeground(Color.red);
						System.out.println("Caught at JTextField getSpacing() - NegativeNumberException");
						return false;
					}
					catch (ZeroArgumentException zae){
						getSpacing().setForeground(Color.red);
						System.out.println("Caught at JTextField getSpacing() - ZeroArgumentException");
						return false;
					}
					catch (NumberFormatException nfe){
						getSpacing().setForeground(Color.red);
						System.out.println("Caught at JTextField getSpacing() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return spacing;
	}
	private JTextField getNumberOfRows() {
		if (numberOfRows == null) {
			numberOfRows = new JTextField();
			numberOfRows.setMinimumSize(new Dimension(100, 22));
			numberOfRows.setText("5");
			numberOfRows.setHorizontalAlignment(JTextField.TRAILING);
			numberOfRows.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					
					try {
						int numberOfRows= Integer.parseInt(getNumberOfRows().getText());
						getNumberOfRows().setText(Integer.toString(numberOfRows));
						getNumberOfRows().setForeground(Color.black);
						if ( numberOfRows < 0)
							throw new NegativeNumberException ("numberOfRows must be a positive number");
						else if ( numberOfRows==0)
							throw new ZeroArgumentException ("numberOfRows must be greater than zero");
						
						return true;

					} 
					catch (NegativeNumberException nne) {
						getNumberOfRows().setForeground(Color.red);
						System.out.println("Caught at JTextField getNumberOfRows() - NegativeNumberException");
						return false;
					}
					catch (ZeroArgumentException zae){
						getNumberOfRows().setForeground(Color.red);
						System.out.println("Caught at JTextField getNumberOfRows() - ZeroArgumentException");
						return false;
					}
					catch (NumberFormatException nfe){
						getNumberOfRows().setForeground(Color.red);
						System.out.println("Caught at JTextField getNumberOfRows() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return numberOfRows;
	}
	private JTextField getHolesInRow() {
		if (holesInRow == null) {
			holesInRow = new JTextField();
			holesInRow.setMinimumSize(new Dimension(100, 22));
			holesInRow.setText("10");
			holesInRow.setHorizontalAlignment(JTextField.TRAILING);
			holesInRow.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						int holesInRow= Integer.parseInt(getHolesInRow().getText());
						getHolesInRow().setText(Integer.toString(holesInRow));
						getHolesInRow().setForeground(Color.black);
						if ( holesInRow < 0)
							throw new NegativeNumberException ("holesInRow must be a positive number");
						else if ( holesInRow==0)
							throw new ZeroArgumentException ("holesInRow must be greater than zero");
						
						return true;

					} 
					catch (NegativeNumberException nne) {
						getHolesInRow().setForeground(Color.red);
						System.out.println("Caught at JTextField getHolesInRow() - NegativeNumberException");
						return false;
					}
					catch (ZeroArgumentException zae){
						getHolesInRow().setForeground(Color.red);
						System.out.println("Caught at JTextField getHolesInRow() - ZeroArgumentException");
						return false;
					}
					catch (NumberFormatException nfe){
						getHolesInRow().setForeground(Color.red);
						System.out.println("Caught at JTextField getHolesInRow() - NumberFormatException");
						return false;
					}
				}
			});
		}
		return holesInRow;
	}
	private JSpinner getOrientation() {
		if (orientation == null) {
			orientation= new JSpinner(degreeSpin3);
			orientation.setMinimumSize(new Dimension(100, 22));
			orientation.setInputVerifier(new InputVerifier(){
				public boolean verify (JComponent input){
					try {
						double d = Double.parseDouble(getOrientation().getValue().toString());
						double b = Double.parseDouble(getBearing().getValue().toString());
						getOrientation().setValue(d);
						getOrientation().setForeground(Color.black);
						if (getAlignBToOJCheckBox().isSelected()){
							getBearing().setValue(Double.toString(Math.abs(-d)));
						}
						return true;
						}
					catch (NumberFormatException nfe){
						getOrientation().setForeground(Color.red);
						System.out.println("Caught at JSpinner getOrientation() - NumberFormatException");
						return false;
						}
				}
			});
		}
		return orientation;
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
	private JSpinner setShape() {
		return
			shape;
	}
	//colour
	private JButton setColorButton(){	
		return
				colorButton;
	}
	private JTextField setBurden() {
		return
			burden;
	}
	private JTextField setSpacing() {
		return
			spacing;
	}
	private JTextField setNumberOfRows() {
		return
			numberOfRows;
	}
	private JTextField setHolesInRow() {
		return
			holesInRow;
	}
	private JSpinner setOrientation() {
		return
		orientation;
	}
	private JButton setToggleUnitType() {
		return
			toggleUnitType;
	}
	private JButton setTogglePatternType() {
		return togglePatternType;
	}
	private JTextComponent setHoleLength() {
		return holeLength;
	}

	
	public static Pattern showBox(JPanel panel) throws NegativeNumberException,IllegalArgumentException, ZeroArgumentException{
		DialogCreatePattern newPattern = new DialogCreatePattern(panel);
		return
			//showBox(LiteTieWindow, -1,-1,-1);
			showBox(panel, Double.parseDouble(newPattern.getNorthing().getText()), Double.parseDouble(newPattern.getEasting().getText()), Double.parseDouble(newPattern.getCollarRL().getText()));
	}
	
	
	public static Pattern showBox(JPanel canvasPanel, double clickN, double clickE, double averageCRL) throws NegativeNumberException, IllegalArgumentException, ZeroArgumentException{

		DialogCreatePattern newPattern = new DialogCreatePattern(canvasPanel);
		newPattern.setModal(true);
		Dummy dummy = new Dummy(clickN,clickE, averageCRL);
		
	
		try {
			Scanner readPrefs = new Scanner(new File("patPrefs.ltpf"));
			
			
			newPattern.setNorthing().setText(readPrefs.nextLine()); //1
			newPattern.setEasting().setText(readPrefs.nextLine()); //2
			newPattern.setDiameter().setText(readPrefs.nextLine()); //3
			newPattern.setHoleLength().setText(readPrefs.nextLine()); //4
			newPattern.setBench().setText(readPrefs.nextLine());  //5
			newPattern.setBearing().setValue(Double.parseDouble(readPrefs.nextLine())); //6
			newPattern.setCollarRL().setText(readPrefs.nextLine()); //7
			newPattern.setFloorRL().setText(readPrefs.nextLine()); //8
			newPattern.setToeRL().setText(readPrefs.nextLine()); //9
			newPattern.setAngle().setValue(Double.parseDouble(readPrefs.nextLine()));  //10
			newPattern.setSubdrill().setText(readPrefs.nextLine()); //11
			newPattern.setShape().setValue(Integer.parseInt(readPrefs.nextLine()));  //12
			newPattern.setColorButton().setBackground(Color.decode(readPrefs.nextLine()));  //13	
			newPattern.setTogglePatternType().setText(readPrefs.nextLine()); //14
			newPattern.setBurden().setText(readPrefs.nextLine());  //15
			newPattern.setSpacing().setText(readPrefs.nextLine());  //16
			newPattern.setNumberOfRows().setText(readPrefs.nextLine());  //17
			newPattern.setHolesInRow().setText(readPrefs.nextLine());  //18
			newPattern.setOrientation().setValue(Double.parseDouble(readPrefs.nextLine())); //19
			
//			if(readPrefs.nextLine().equals("true")){
//				newPattern.getAnglesOffZero().setSelected(true);
//			}
//			else{newPattern.getAnglesOffZero().setSelected(false);}
//			
			if(readPrefs.nextLine().equals("true")){ //20
				newPattern.getAlignBToOJCheckBox().setSelected(true);
			}
			else{newPattern.getAlignBToOJCheckBox().setSelected(false);}
		
			newPattern.setToggleUnitType().setText(readPrefs.nextLine()); //21
			
			
			
			
		} 

		 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(canvasPanel,
				    "Unable to Load Defaults - File Not Found - Program Defaults Loaded",
				    "patPrefs.ltpf - File Not Found",
				    JOptionPane.ERROR_MESSAGE);
			System.out.println("Caught FileNotFoundException - showBox()");
		
			newPattern.setNorthing().setText(Double.toString(clickN));
			newPattern.setEasting().setText(Double.toString(clickE));
			newPattern.setDiameter().setText("89");
			newPattern.setHoleLength().setText("11");
			newPattern.setBench().setText("10");
			newPattern.setBearing().setValue(Double.parseDouble("0"));
			newPattern.setCollarRL().setText("100");
			newPattern.setFloorRL().setText("90");
			newPattern.setToeRL().setText("89");
			newPattern.setAngle().setValue(Double.parseDouble("90"));
			newPattern.setSubdrill().setText("1");
			newPattern.setShape().setValue(Integer.parseInt("1"));
			newPattern.setColorButton().setBackground(Color.BLACK);
			newPattern.setTogglePatternType().setText("Staggered");
			newPattern.setBurden().setText("2.9");
			newPattern.setSpacing().setText("3.3");
			newPattern.setNumberOfRows().setText("5");
			newPattern.setHolesInRow().setText("10");
			newPattern.setOrientation().setValue(Double.parseDouble("0"));
//			newPattern.getAnglesOffZero().setSelected(false);
			newPattern.getAlignBToOJCheckBox().setSelected(true);
			newPattern.setToggleUnitType().setText("metric");
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(canvasPanel,
				    "Unable to Load Defaults  -  Program Defaults Loaded",
				    "patPrefs.ltpf - No Such Element Exception",
				    JOptionPane.ERROR_MESSAGE);
			System.out.println("Caught NoSuchElementException - showBox()");
		
			newPattern.setNorthing().setText(Double.toString(clickN));
			newPattern.setEasting().setText(Double.toString(clickE));
			newPattern.setDiameter().setText("89");
			newPattern.setHoleLength().setText("11");
			newPattern.setBench().setText("10");
			newPattern.setBearing().setValue(Double.parseDouble("0"));
			newPattern.setCollarRL().setText("100");
			newPattern.setFloorRL().setText("90");
			newPattern.setToeRL().setText("89");
			newPattern.setAngle().setValue(Double.parseDouble("90"));
			newPattern.setSubdrill().setText("1");
			newPattern.setShape().setValue(Integer.parseInt("1"));
			newPattern.setColorButton().setBackground(Color.BLACK);
			newPattern.setTogglePatternType().setText("Staggered");
			newPattern.setBurden().setText("2.9");
			newPattern.setSpacing().setText("3.3");
			newPattern.setNumberOfRows().setText("5");
			newPattern.setHolesInRow().setText("10");
			newPattern.setOrientation().setValue(Double.parseDouble("0"));
//			newPattern.getAnglesOffZero().setSelected(false);
			newPattern.getAlignBToOJCheckBox().setSelected(true);
			newPattern.setToggleUnitType().setText("metric");
		}
		
		
		if (clickN != -1){
			newPattern.setNorthing().setText(Double.toString(clickN));
		}
		if (clickE != -1) {
			newPattern.setEasting().setText(Double.toString(clickE));
		}
		
		
		
		
		double northing = Double.parseDouble(newPattern.getNorthing().getText());
		double easting = Double.parseDouble(newPattern.getEasting().getText());
		double startDiameter = Double.parseDouble(newPattern.getDiameter().getText());
		double startHoleLength =Double.parseDouble(newPattern.getHoleLength().getText());
		double startBench =Double.parseDouble(newPattern.getBench().getText());
		double startBearing = Double.parseDouble(newPattern.getBearing().getValue().toString());
		double startCRL = Double.parseDouble(newPattern.getCollarRL().getText());
		double startFRL = Double.parseDouble(newPattern.getFloorRL().getText());
		double startTRL = Double.parseDouble(newPattern.getToeRL().getText());
		double startAngle = Double.parseDouble(newPattern.getAngle().getValue().toString());
		double startSubdrill = Double.parseDouble(newPattern.getSubdrill().getText());
		
		int startShape = Integer.parseInt(newPattern.getHoleShape().getValue().toString());
		Color color = newPattern.getColorButton().getBackground();
		
		double burden = Double.parseDouble(newPattern.getBurden().getText());
		double spacing = Double.parseDouble(newPattern.getSpacing().getText());
		int numberOfRows = Integer.parseInt(newPattern.getNumberOfRows().getText());
		int holesInRow = Integer.parseInt(newPattern.getHolesInRow().getText());
		double orientation = Double.parseDouble(newPattern.getOrientation().getValue().toString());
		
		Hole hole = new Hole(northing, easting, startCRL," " , " ", " ", startDiameter,  startHoleLength, startBench,
				startBearing,  startFRL, startTRL, startAngle, startSubdrill,startShape,color);
		
		Pattern pattern = new Pattern(burden, spacing, numberOfRows, holesInRow, orientation);
		

		do {
			
			newPattern.setVisible(true);
			
			if (newPattern.okPushed == true) {
				boolean badValue = false;
				
//NORTHING
				try {
					northing = Double.parseDouble(newPattern.getNorthing().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
						
					}
					else 
						northing = UnitConvert.yardsToMeters(northing);
						
					dummy.setX(northing);
					newPattern.getNorthing().setForeground(Color.black);
					
				} 
				catch (NumberFormatException e) {
					newPattern.getNorthing().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("northing Bad Value(s) - showBox() method - NumberFormatException");
								
				}
//				catch (NegativeNumberException e) {
//					newPattern.getNorthing().setForeground(Color.red);
//					status = ("Inappropriate value(s)...");
//					badValue = true;
//					System.out.println("Bad Value(s) - showBox() method - IllegalArgumentException");
//					
//				}
				
				
//EASTING
				try {
					easting = Double.parseDouble(newPattern.getEasting().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
						
					}
					else 
						easting = UnitConvert.yardsToMeters(easting);
						
					dummy.setY(easting);	
					newPattern.getEasting().setForeground(Color.black);
					
				} 
				catch (NumberFormatException nfe) {
					newPattern.getEasting().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("easting Bad Value(s) - showBox() method - NumberFormatException");
								
				}


//COLLAR				
				try {
					System.out.println(newPattern.getCollarRL().getText());
					System.out.println(newPattern.getFloorRL().getText());
					startCRL = Double.parseDouble(newPattern.getCollarRL().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {

					}
					else 
						startCRL = UnitConvert.feetToCentimeters(startCRL)/100;

					hole.setZ(startCRL);
					newPattern.getCollarRL().setForeground(Color.black);

				} 
				catch (NumberFormatException nfe) {
					newPattern.getCollarRL().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startCRL Bad Value(s) - showBox() method - NumberFormatException");

				}
//				catch (IllegalArgumentException iae) {
//					newPattern.getCollarRL().setForeground(Color.red);
//					status = ("Inappropriate value(s)...");
//					badValue = true;
//					System.out.println("startCRL Bad Value(s) - showBox() method - IllegalArgumentException");
//				}	
//HOLE LENGTH				
				try {
					startHoleLength = Double.parseDouble(newPattern.getHoleLength().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
					
					}
					else 
						startHoleLength = UnitConvert.feetToCentimeters(startHoleLength)/100;
					
					hole.setHoleLength(startHoleLength);
					newPattern.getHoleLength().setForeground(Color.black);
				
				} 
				catch (NumberFormatException f) {
					newPattern.getHoleLength().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startHoleLength Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (ZeroArgumentException z) {
					newPattern.getHoleLength().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startHoleLength Bad Value(s) - showBox() method - ZeroArgumentException");
					
				}catch (NegativeNumberException n) {
					newPattern.getHoleLength().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startHoleLength Bad Value(s) - showBox() method - NegativeNumberException");	
				}
				
//DIAMETER
				try {
					startDiameter = Double.parseDouble(newPattern.getDiameter().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {

					}
					else 
						startDiameter = UnitConvert.inchesToMillimeters(startDiameter);
					hole.setDiameter(startDiameter);	
					newPattern.getDiameter().setForeground(Color.black);

				} 
				catch (NumberFormatException nfe) {
					newPattern.getDiameter().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startDiameter Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (NegativeNumberException e) {
					newPattern.getDiameter().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startDiameter Bad Value(s) - showBox() method - NegativeNumberException");
				}
				catch (ZeroArgumentException z) {
					newPattern.getDiameter().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startDiameter Bad Value(s) - showBox() method - ZeroArgumentException");	
				}
				
//BENCH or DEPTH				
				try {
					startBench = Double.parseDouble(newPattern.getBench().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
					
					}
					else 
						startBench = UnitConvert.feetToCentimeters(startBench)/100;
					
					hole.setBench(startBench);
					newPattern.getBench().setForeground(Color.black);
				
				} 
				catch (NumberFormatException nfe) {
					newPattern.getBench().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startBench Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (NegativeNumberException e) {
					newPattern.getBench().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startBench Bad Value(s) - showBox() method - NegativeNumberException");
					
				}
				catch (ZeroArgumentException z) {
					newPattern.getBench().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startBench Bad Value(s) - showBox() method - ZeroArgumentException");
					
				}
//BEARING				
				try {
					startBearing = Double.parseDouble((newPattern.getBearing().getValue()).toString());
					newPattern.getBearing().setForeground(Color.black);	
					hole.setBearing(startBearing);
				} 

				catch (NumberFormatException nfe) {
					newPattern.getBearing().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startBearing Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getBearing().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startBearing Bad Value(s) - showBox() method - IllegalArgumentException");
					
				}
//FLOOR				
				try {
					startFRL = Double.parseDouble(newPattern.getFloorRL().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
						
					}
					else 
						startFRL = UnitConvert.feetToCentimeters(startFRL)/100;
					
					hole.setFloorRL(startFRL);
					newPattern.getCollarRL().setForeground(Color.black);
					
				} 
				
				catch (NumberFormatException nfe) {
					newPattern.getFloorRL().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startFRL Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getFloorRL().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startFRL Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//TOE				
				try {
					startTRL = Double.parseDouble(newPattern.getToeRL().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
						
					}
					else 
						startTRL = UnitConvert.feetToCentimeters(startTRL)/100;
					
					hole.setToeRL(startTRL);
					newPattern.getToeRL().setForeground(Color.black);
					
				} 
				catch (NumberFormatException nfe) {
					newPattern.getToeRL().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startTRL Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getToeRL().setForeground(Color.red);
					System.out.println(hole.getToeRL());
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startTRL Bad Value(s) - showBox() method - IllegalArgumentException");
				}
				
//ANGLE			
				try {
					startAngle = Double.parseDouble(newPattern.getAngle().getValue().toString());
					newPattern.getAngle().setForeground(Color.black);
				
					hole.setAngle(startAngle);
			
				} 
				catch (NumberFormatException nfe) {
					newPattern.getAngle().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startAngle Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getAngle().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startAngle Bad Value(s) - showBox() method - IllegalArgumentException");
				}

//SUBDRILL				
				try {
					startSubdrill = Double.parseDouble(newPattern.getSubdrill().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
					}
					else 
						startSubdrill = UnitConvert.feetToCentimeters(startSubdrill)/100;
					
					hole.setSubdrill(startSubdrill);
					newPattern.getSubdrill().setForeground(Color.black);
				
				} 
				catch (NumberFormatException nfe) {
					newPattern.getSubdrill().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startSubdrill Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getSubdrill().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startSubdrill Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//SHAPE		
				try {
					startShape = Integer.parseInt(newPattern.getHoleShape().getValue().toString());
					newPattern.getHoleShape().setForeground(Color.black);
				
					hole.setShape(startShape);
			
				} 
				catch (NumberFormatException nfe) {
					newPattern.getHoleShape().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startShape Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getHoleShape().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("startShape Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//COLOR	
				try {
					color = newPattern.getColorButton().getBackground();
					hole.setColor(color);
			
				} 

				catch (IllegalArgumentException iae) {
					newPattern.getColorButton().setForeground(Color.red);
					newPattern.getColorButton().setText("Error");
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("color Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//BURDEN				
				try {
					burden = Double.parseDouble(newPattern.getBurden().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
					
					}
					else 
						burden = UnitConvert.yardsToMeters(burden);
					
					pattern.setBurden(burden);
					newPattern.getBurden().setForeground(Color.black);
				
				} 
				catch (NumberFormatException nfe) {
					newPattern.getBurden().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("burden Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getBurden().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("burden Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//SPACING
				try {
					spacing = Double.parseDouble(newPattern.getSpacing().getText());
					if (newPattern.getToggleUnitType().getText().equals("Metric")) {
					}
					else 
						spacing = UnitConvert.yardsToMeters(spacing);
					
					pattern.setSpacing(spacing);
					newPattern.getSpacing().setForeground(Color.black);
				
				} 
				catch (NumberFormatException nfe) {
					newPattern.getSpacing().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("spacing Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getSpacing().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("spacing Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//NUMBER OF ROWS
				try {
					numberOfRows = Integer.parseInt(newPattern.getNumberOfRows().getText());
					pattern.setRows(numberOfRows);
					newPattern.getNumberOfRows().setForeground(Color.black);
					
				
				} 
				catch (NumberFormatException nfe) {
					newPattern.getNumberOfRows().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("numberOfRows Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getNumberOfRows().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("numberOfRows Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//HOLES IN ROW				
				try {
					holesInRow = Integer.parseInt(newPattern.getHolesInRow().getText());
					pattern.setHolesinRow(holesInRow);
					newPattern.getHolesInRow().setForeground(Color.black);
					
				} 
				catch (NumberFormatException nfe) {
					newPattern.getHolesInRow().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("holesInRow Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getHolesInRow().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("holesInRow Bad Value(s) - showBox() method - IllegalArgumentException");
				}
//ORIENTATION				
				try {
					orientation = Double.parseDouble(newPattern.getOrientation().getValue().toString());
					pattern.setOrientation(orientation);
					newPattern.getOrientation().setForeground(Color.black);
					
					
				} 
				catch (NumberFormatException nfe) {
					newPattern.getOrientation().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("Orientation Bad Value(s) - showBox() method - NumberFormatException");
								
				}
				catch (IllegalArgumentException iae) {
					newPattern.getOrientation().setForeground(Color.red);
					status = ("Inappropriate value(s)...");
					badValue = true;
					System.out.println("Orientation Bad Value(s) - showBox() method - IllegalArgumentException");
				}

				
				
				if (badValue) 
						continue;
				
			}
			else if (newPattern.cancelPushed == true){
				System.out.println("Cancel button pushed");
				return null;}
			
			if (newPattern.saveAsPreferredCheckBox.isSelected()) {
				try {
					
					PrintStream prefWriter = new PrintStream(new File("patPrefs.ltpf"));
					
					
					prefWriter.println(newPattern.setNorthing().getText());
					prefWriter.println(newPattern.setEasting().getText());
					prefWriter.println(newPattern.setDiameter().getText());
					prefWriter.println(newPattern.setHoleLength().getText());
					prefWriter.println(newPattern.setBench().getText());
					prefWriter.println(newPattern.setBearing().getValue().toString());
					prefWriter.println(newPattern.setCollarRL().getText());
					prefWriter.println(newPattern.setFloorRL().getText());
					prefWriter.println(newPattern.setToeRL().getText());
					prefWriter.println(newPattern.setAngle().getValue().toString());
					prefWriter.println(newPattern.setSubdrill().getText());
					prefWriter.println(newPattern.setShape().getValue().toString());
					prefWriter.println((newPattern.getColorButton().getBackground()).getRGB());
					
					prefWriter.println(newPattern.getTogglePatternType().getText());
					prefWriter.println(newPattern.getBurden().getText());//9
					prefWriter.println(newPattern.getSpacing().getText());//10
					prefWriter.println(newPattern.getNumberOfRows().getText());//11
					prefWriter.println(newPattern.getHolesInRow().getText());//12
					prefWriter.println(newPattern.getOrientation().getValue());//13
					
//					patPrefWriter.println(newPattern.getAnglesOffZero().isSelected());
					prefWriter.println(newPattern.getAlignBToOJCheckBox().isSelected());
					prefWriter.println(newPattern.getToggleUnitType().getText());//14
					
					
					prefWriter.close();
					
					
					
				} catch (FileNotFoundException e1) {
					status = "Unable to Save as Default - File Not Found - Check that the file is a valid and accessible LiteTie file.";
					
					JOptionPane.showMessageDialog(canvasPanel,
						    "Unable to Save as Default /nFile Not Found",
						    "patPrefs.ltpf - File Not Found - error",
						    JOptionPane.ERROR_MESSAGE);
					System.out.println("Caught FileNotFoundException - showBox()");
					e1.printStackTrace();
				}
				
			}
						

				if (newPattern.getTogglePatternType().getText().equals("Staggered") && newPattern.getAlignBToOJCheckBox().isSelected()){

					return
					PatternTemplate.createStaggered(northing, //1
													easting, //2
													startCRL, //3
													" ", //4
													" ", //5
													" ", //6
													startDiameter,//7 
													startHoleLength, //8
													startBench, //9
													orientation+90,//10 
													startFRL, //11
													startTRL, //12
													startAngle, //13
													startSubdrill,
													startShape,//14
													color,
													burden, //15
													spacing, //16
													numberOfRows,//17 
													holesInRow, //18
													orientation);//19
				}

				else if (newPattern.getTogglePatternType().getText().equals("Square") && newPattern.getAlignBToOJCheckBox().isSelected()) {

					return
					PatternTemplate.createSquare(	northing, 
													easting, 
													startCRL, 
													" ", 
													" ", 
													" ", 
													startDiameter, 
													startHoleLength, 
													startBench, 
													orientation+90, 
													startFRL, 
													startTRL, 
													startAngle, 
													startSubdrill,
													startShape,
													color,
													burden, spacing, 
													numberOfRows, 
													holesInRow, 
													orientation);
				}
				else if (newPattern.getTogglePatternType().getText().equals("Staggered")){

					return
					PatternTemplate.createStaggered(northing, 
													easting, 
													startCRL, 
													" ", 
													" ", 
													" ", 
													startDiameter, 
													startHoleLength, 
													startBench, 
													startBearing, 
													startFRL, 
													startTRL, 
													startAngle, 
													startSubdrill,
													startShape,
													color,
													burden, 
													spacing, 
													numberOfRows, 
													holesInRow, 
													orientation);
				}

				else if (newPattern.getTogglePatternType().getText().equals("Square")) {

					return
					PatternTemplate.createSquare(	northing, 
													easting,
													startCRL, 
													" ", 
													" ", 
													" ", 
													startDiameter, 
													startHoleLength, 
													startBench, 
													startBearing, 
													startFRL, 
													startTRL, 
													startAngle, 
													startSubdrill, 
													startShape,
													color,
													burden, 
													spacing, 
													numberOfRows, 
													holesInRow,  
													orientation);
				}
			
			
			else if (newPattern.cancelPushed == true){
				System.out.println("Cancel button pushed");
				return
					null;
			}
				
		}
		while(true);
	
	}
		

	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException, ZeroArgumentException {

		showBox(null);
		
		System.exit(0);
	}
	private JLabel getDisplayShape() {
		if (displayShape == null) {
			displayShape = new JLabel();
			displayShape.setText("Display Shape");
			displayShape.setPreferredSize(new Dimension(94, 22));
			displayShape.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		return displayShape;
	}
	private JSpinner getHoleShape() {
		if (shape == null) {
			shape = new JSpinner();
			shape.setName("Shape Value");
			shape.setMinimumSize(new Dimension(80, 22));
			shape.setModel(shapeIntegerModel);
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
			shapeLabel.setSize(new Dimension(150,22));
			shapeLabel.setMinimumSize(new Dimension(150, 22));
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
//			colorButton.setForeground(Color.BLACK);
			colorButton.setPreferredSize(new Dimension(75, 29));
			colorButton.setMargin(new Insets(0, 0, 0, 0));
			colorButton.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			colorButton.setBackground(Color.BLACK);
			colorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentColor = JColorChooser.showDialog(DialogCreatePattern.this , "Change Current Colour", getColorButton().getBackground());
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
	private JButton getButtonEquilateralSpacing() {
		if (buttonEquilateralSpacing == null) {
			buttonEquilateralSpacing = new JButton("");
			buttonEquilateralSpacing.setPreferredSize(new Dimension(25, 25));
			buttonEquilateralSpacing.setIcon(new ImageIcon(DialogCreatePattern.class.getResource("/icons_LiteTie_v2/EquilateralSpacing.png")));
			buttonEquilateralSpacing.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setSpacing().setText(decimal1.format(Double.parseDouble(getBurden().getText())/Math.cos(Math.toRadians(30))));
				}
			});
		}
		return buttonEquilateralSpacing;
	}
	private JButton getButtonEquilateralBurden() {
		if (buttonEquilateralBurden == null) {
			buttonEquilateralBurden = new JButton("");
			buttonEquilateralBurden.setIcon(new ImageIcon(DialogCreatePattern.class.getResource("/icons_LiteTie_v2/EquilateralBurden.png")));
			buttonEquilateralBurden.setPreferredSize(new Dimension(25, 25));
			buttonEquilateralBurden.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setBurden().setText(decimal1.format(Double.parseDouble(getSpacing().getText())*Math.cos(Math.toRadians(30))));
				}
			});
		}
		return buttonEquilateralBurden;
	}
	private JLabel getLblPatternType() {
		if (lblPatternType == null) {
			lblPatternType = new JLabel("Pattern Type");
		}
		return lblPatternType;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panel_1.add(getCancel());
			panel_1.add(getOK());
		}
		return panel_1;
	}
}  //  @jve:decl-index=0:visual-constraint="122,10"
//Read up on NumberFormat.class about getIntegerInstance
//also JFormattedTextField 

//Fix error handling in the textfields i.e northing can't be a negative
