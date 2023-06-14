package litetie.view;
import javax.swing.JPanel;
import javax.swing.JDialog;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.ComboBoxModel;
import javax.swing.InputVerifier;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;

import litetie.model.NegativeNumberException;
import litetie.model.ZeroArgumentException;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import javax.swing.JRadioButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.SpinnerModel;



@SuppressWarnings("serial")
public class DialogPreferences extends JDialog {
	
	DecimalFormat decimal3 = new DecimalFormat("#0.000");
	DecimalFormat decimal2 = new DecimalFormat("#0.00");
	DecimalFormat decimal1 = new DecimalFormat("#0.0");
	DecimalFormat decimal0 = new DecimalFormat("#0");

	public static String status;
	private boolean okPushed;	
	private boolean cancelPushed;
	private boolean resizable = false;
	

	public ImageIcon transIcon = new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/transparentIcon.png"));
	public ImageIcon australia = new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/Australia.png"));
	public ImageIcon unitedStates = new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/UnitedStates.png"));
	public ImageIcon unitedKingdom = new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/UnitedKingdom.png"));
	
	//AUSTIN SNAP CONNECTORS COLOUR RANGE 2013
	public static Color austinGreen = new Color(85,138,21);//9ms
	public static Color austinYellow = new Color(247,241,36);//17ms
	public static Color austinRed = new Color(248,0,22);//25ms
	public static Color austinOrange = new Color(248,77,26);//33ms
	public static Color austinWhite = new Color(255,255,255);//42ms
	public static Color austinBlue = new Color(90,194,252);//67ms
	
	//DYNO EZTL CONNECTORS COLOUR RANGE 2013
	public static Color dynoPurple = new Color(128,0,255);//9
	public static Color dynoYellow = new Color(255,255,0);//17
	public static Color dynoRed = new Color(255,0,0);//25
	public static Color dynoWhite = new Color(245,245,245);//42
	public static Color dynoBlue = new Color(0,0,255);//67
	public static Color dynoBlack = new Color(10,10,10);//109
	
	//MAXAM RIONEL CONNECTORS COLOUR RANGE 2013
	public static Color maxamGreen = new Color(0,170,0);//9
	public static Color maxamYellow = new Color(255,230,0);//17
	public static Color maxamRed = new Color(255,30,30);//25
	public static Color maxamWhite = new Color(245,245,245);//42
	public static Color maxamBlack = new Color(10,10,10);//67
	public static Color maxamBlue = new Color(0,100,255);//109

	//ORICA CDs CONNECTORS COLOUR RANGE 2013
	public static Color oricaGreen = new Color(21,159,66);//9
	public static Color oricaYellow = new Color(242,191,53);//17
	public static Color oricaRed = new Color(200,39,45);//25
	public static Color oricaWhite = new Color(245,245,245);//42
	public static Color oricaBlue = new Color(14,91,155);//67
	public static Color oricaOrange = new Color(225,56,32);//109
	
	private JPanel preferenceTabHolder = null;
	
	private JButton ok = null;
	private JButton cancel = null;
	private Color currentColor = null;
		
	//STRING STORES FOR PREFERENCE FILE VALUES
	public static String 	directoryPath, holeFactor, markerFactor, surfaceFactor, defaultSupplier, 
							tie1Amount, tie2Amount, tie3Amount, tie4Amount, tie5Amount, tie6Amount,
							downHole1Amount, downHole2Amount, downHole3Amount,
							registration, userName, defaultFont, defaultFontSize,tieInLineTolerance;
	
	public static Color p_holeTextColour,p_holeLengthTextColour,p_labelOneTextColour,p_labelTwoTextColour,p_labelThreeTextColour,
						p_benchHeightTextColour,p_subdrillTextColour,p_holeDiameterTextColour, p_holeBearingTextColour,
						p_collarLocationTextColour,p_floorLocationTextColour,p_toeLocationTextColour,p_holeAngleTextColour,
						p_backgroundCanvasColour,p_selectedObjectsColour,p_gridLinesAndTextColour,p_floorLocationFillColour,
						p_floorLocationLineColour,p_toeLocationFillColour,p_toeLocationLineColour,p_detonatorDelayTextColour,
						p_downHoleFiringTimesColour,p_surfaceConnectorFiringTimesColour,
						tie1Colour, tie2Colour, tie3Colour, tie4Colour, tie5Colour, tie6Colour,
						downHole1Colour, downHole2Colour, downHole3Colour;
	public static String  p_showGrid, p_showBoundary, p_showText, p_showHoleAngle, p_showHoleSymbols, p_showDummySymbols, p_showDrillTracks,
					p_showFloorCircle, p_showFloorLine, p_showToeCircle, p_showToeLine, p_showHoleID, p_showLabelOne, p_showLabelTwo, p_showLabelThree,
					p_showBench, p_showSubdrill, p_showCollarLoc, p_showFloorLoc, p_showToeLoc, p_showLength, p_showDiameter,
					p_showSurfaceConnections, p_showSurfaceConnectionLabel, p_showDetonators, p_showDetonatorDelay,
					p_showDetonatorTimes, p_showSurfaceTimes, p_showContours, p_showmovements, p_showRelief;
	
	public boolean  showGrid, showBoundary, showText, showHoleAngle, showHoleSymbols, showDummySymbols, showDrillTracks,
					showFloorCircle, showFloorLine, showToeCircle, showToeLine, showHoleID, showLabelOne, showLabelTwo, showLabelThree,
					showBench, showSubdrill, showCollarLoc, showFloorLoc, showToeLoc, showLength, showDiameter,
					showSurfaceConnections, showSurfaceConnectionLabel, showDetonators, showDetonatorDelay,
					showDetonatorTimes, showSurfaceTimes, showContours, showmovements, showRelief, applyFontScaling;
	
	
	
	static ArrayList <String> fontNames = new ArrayList<String>();
	public ArrayList<String> getFontNames(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font[] fonts = ge.getAllFonts();
		for (Font f: fonts){
			fontNames.add(f.getName());
		}
		return fontNames;
	}
	SpinnerListModel spinnerModelFont = new SpinnerListModel(getFontNames());
	SpinnerNumberModel spinnerModelFontSize = new SpinnerNumberModel(12, 1, 200, 1);
	
//	ComboBoxModel <String> comboBoxModelFont = (ComboBoxModel<String>) (getFontNames());
	
	SpinnerNumberModel spinnerModelSurfaceFactor = new SpinnerNumberModel(1.0, 0.1, 20.0, 1.0);
	SpinnerNumberModel spinnerModelMarkerFactor = new SpinnerNumberModel(1.1, 0.1, 100.0, 1.0);
	SpinnerNumberModel spinnerModelHoleFactor = new SpinnerNumberModel(1.1, 0.1, 100.0, 1.0);
	SpinnerNumberModel spinnerModelTieInLineTolerance = new SpinnerNumberModel(0.5,0.1,100,0.5);
	
	private JCheckBox saveAsPreferredCheckBox = null;
	
//	private Dimension dimension22 = new Dimension(100,24);

	private JPanel wellSelectedObjectsColor;
	private JLabel labelDisplayColour;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension dialogSize = new Dimension( 700, 550);
	Rectangle bounds = new Rectangle(
			(int)(screenSize.getWidth()/2-(dialogSize.getWidth()/2)),
			(int) (screenSize.getHeight()/2-(dialogSize.getHeight()/2)), 
			(int)dialogSize.getWidth(), 
			(int)dialogSize.getHeight());
	private JPanel preferenceTabHolder2;
	private JTabbedPane tabbedPane;
	private JPanel buttonPanel;
	private JPanel colorPanel;
	private JPanel generalPanel;
	private JLabel labelGridLinesAnd;
	private JLabel labelBackgroundCanvas;
	private JLabel labelHoleIdText;
	private JLabel labelHoleLengthText;
	private JLabel labelLabelOneText;
	private JLabel labelLabelTwoText;
	private JLabel labelLabelThreeText;
	private JLabel labelBenchHeightText;
	private JLabel labelSubdrillAmountText;
	private JLabel labelHoleDiameterText;
	private JLabel labelHoleBearingText;
	private JLabel labelHoleTrackLine;
	private JLabel labelFloorCircleLocation;
	private JLabel labelToeCircleLocation;
	private JLabel labelToeLocationLine;
	private JLabel labelCollarLocationText;
	private JLabel labelFloorLocationText;
	private JLabel labelToeLocationText;
	private JPanel wellCanvasColor;
	private JPanel wellGridLineColor;
	private JPanel wellHoleIDColor;
	private JPanel wellHoleLengthColor;
	private JPanel wellLabelOneColor;
	private JPanel wellLabelTwoColor;
	private JPanel wellLabelThreeColor;
	private JPanel wellBenchHeightColor;
	private JPanel wellSubdrillColor;
	private JPanel wellHoleDiameterColor;
	private JPanel wellHoleBearingColor;
	private JPanel wellFloorLocFillColor;
	private JPanel wellFloorLineColor;
	private JPanel wellToeLocFillColor;
	private JPanel wellToeLocLineColor;
	private JPanel wellCollarLocColor;
	private JPanel wellFloorLocTextColor;
	private JPanel wellToeLocTextColor;
	private JPanel wellDetonatorDelayColor;
	private JPanel wellSurfaceFiringTime;	
	private JPanel wellDownholeFiringTime;
	
	private JButton buttonDirectoryPath;
	private JTextField textFieldDirectoryPath;
	private JLabel labelDefaultFileSaving;
	private JPanel registrationPanel;
	private JLabel labelRegistrationCode;
	private JTextField textFieldReg1;
	private JLabel labelUserName;
	private JTextField textFieldUserName;

	private JLabel labelHoleSymbolStartup;
	private JLabel labelFloorAndToe;
	private JLabel labelSurfaceConnectorStartup;
	
	private JSpinner spinnerHoleEnhancement;
	private JSpinner spinnerMarkerEnhancement;
	private JSpinner spinnerSurfaceConnectorEnhancement;
	private JSpinner spinnerTieInLineTolerance;
	
	private JRadioButton rdbtnAustinPowder;
	private JRadioButton rdbtnDynoNobelAp;
	private JRadioButton rdbtnOricaAustralia;
	private JRadioButton rdbtnMaxamAus;
	private JLabel labelDefaultSurfaceRange;
	private JTextField textFieldTieOne;
	private JTextField textFieldTieTwo;
	private JTextField textFieldTieThree;
	private JTextField textFieldTieFour;
	private JTextField textFieldTieFive;
	private JTextField textFieldTieSix;
	private JLabel labelTieOne;
	private JLabel labelTieTwo;
	private JLabel labelTieThree;
	private JLabel labelTieFour;
	private JLabel labelTieFive;
	private JLabel labelTieSix;
	private JTextField textFieldDownHoleOne;
	private JLabel labelDownHoleOne;
	private JRadioButton rdbtnCustom;
	private JTextField textFieldDownHoleTwo;
	private JTextField textFieldDownHoleThree;
	private JLabel labelDownHoleTwo;
	private JLabel labelDownHoleThree;
	private JPanel SurfaceConnectionPalette;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel colorPanel2;
	private JLabel labelTextAndObject;
	private JPanel panel_2;
	private JPanel displayPanel;
	private JLabel labelDisplayOnStartup;
	private JCheckBox checkBoxToeCircleMarker;
	private JPanel checkBoxDisplayPanel;
	private JCheckBox checkBoxGridLines;
	private JCheckBox checkBoxBoundaryLinesOn;
	private JCheckBox checkBoxTextOn;
	private JCheckBox checkBoxHoleAngleText;
	private JCheckBox checkBoxHoleSymbolsOn;
	private JCheckBox checkBoxDummyHoleSymbols;
	private JCheckBox checkBoxHoleDrillTracks;
	private JCheckBox checkBoxFloorCirclMarker;
	private JCheckBox checkBoxFloorLineMarker;
	private JCheckBox checkBoxToeLineMarker;
	private JCheckBox checkBoxHoleLabelTwo;
	private JCheckBox checkBoxHoleIdNumber;
	private JCheckBox checkBoxHoleLabelOne;
	private JCheckBox checkBoxHoleLabelThree;
	private JCheckBox checkBoxBenchHeightText;
	private JCheckBox checkBoxSubdrillAmountText;
	private JCheckBox checkBoxCollarLocationText;
	private JCheckBox checkBoxFloorLocationText;
	private JCheckBox checkBoxToeLocationText;
	private JCheckBox checkBoxHoleLengthOn;
	private JCheckBox checkBoxHoleDiameterOn;
	private JCheckBox checkBoxSurfaceConnections;
	private JCheckBox checkBoxSurfaceConnectorLabels;
	private JCheckBox checkBoxDetonatorIndicationOn;
	private JCheckBox checkBoxDetonatorDelaysOn;
	private JCheckBox checkBoxDownHoleFiring;
	private JCheckBox checkBoxSurfaceConnectorFiring;
	private JCheckBox checkBoxSurfaceTimeContours;
	private JCheckBox checkBoxFirstMovements;
	private JCheckBox checkBoxReliefShading;
	
	private JLabel labelDetonatorDelayText;
	private JLabel labelDownHoleFiring;
	private JLabel labelSurfaceConnectorFiring;

	private SpringLayout sl_registrationPanel;
	private JButton buttonDownHoleTwoColorChange;
	private JButton buttonDownHoleOneColorChange;
	private JButton buttonDownHoleThreeColorChange;
	private JButton buttonTieOneColorChange;
	private JButton buttonTieTwoColorChange;
	private JButton buttonTieThreeColorChange;
	private JButton buttonTieFourColorChange;
	private JButton buttonTieFiveColorChange;
	private JButton buttonTieSixColorChange;
	
	static BufferedImage image; 
	private JPanel wellHoleAngleTextColour;
	private JLabel lblHoleAngledipText;
	private boolean badValue;
	private Scanner readPrefs;
	private JSpinner spinnerFontName;
	private JLabel lblDefaultFont;
	private JSpinner spinnerFontPointSize;
	private JCheckBox chckbxApplyScaling;
	private JLabel lblPointSize;
	private JLabel labelMultiTieIn;
	
	
	/**
	 * @param canvasPanel
	 */
	public DialogPreferences(JPanel canvasPanel) {
		super();
		initialize();
	}
	public DialogPreferences() {
		super();
		initialize();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void.
	 */
	private void initialize() {
		try {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (ClassNotFoundException e) {
			UIManager.getLookAndFeelDefaults();
			e.printStackTrace();
		} catch (InstantiationException e) {
			UIManager.getLookAndFeelDefaults();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			UIManager.getLookAndFeelDefaults();
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			UIManager.getLookAndFeelDefaults();
			e.printStackTrace();
		}
		this.setBounds(bounds);
		this.setTitle("Preferences");
		this.setContentPane(getPreferenceTabHolder());
		this.setResizable(resizable);
		
	}

	private JPanel getPreferenceTabHolder() {
		if (preferenceTabHolder == null) {
			
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
			
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 4;
			gridBagConstraints2.weightx = 1.0;  
			
			preferenceTabHolder = new JPanel();
			GridBagLayout gbl_preferenceTabHolder = new GridBagLayout();
			gbl_preferenceTabHolder.rowWeights = new double[]{1.0, 0.0};
			gbl_preferenceTabHolder.columnWeights = new double[]{1.0};
			preferenceTabHolder.setLayout(gbl_preferenceTabHolder);
			preferenceTabHolder.setName("New Pattern");
			GridBagConstraints gbc_preferenceTabHolder2 = new GridBagConstraints();
			gbc_preferenceTabHolder2.insets = new Insets(0, 0, 5, 5);
			gbc_preferenceTabHolder2.fill = GridBagConstraints.BOTH;
			gbc_preferenceTabHolder2.gridx = 0;
			gbc_preferenceTabHolder2.gridy = 0;
			preferenceTabHolder.add(getPreferenceTabHolder2(), gbc_preferenceTabHolder2);
			GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
			gbc_buttonPanel.fill = GridBagConstraints.HORIZONTAL;
			gbc_buttonPanel.gridx = 0;
			gbc_buttonPanel.gridy = 1;
			preferenceTabHolder.add(getButtonPanel(), gbc_buttonPanel);
			
		}
		return preferenceTabHolder;
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
					status = ("Preferences Changed...");
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
	private JCheckBox getSaveAsPreferredCheckBox() {
		if (saveAsPreferredCheckBox == null) {
			saveAsPreferredCheckBox = new JCheckBox();
			saveAsPreferredCheckBox.setText("Set as the default settings");
			saveAsPreferredCheckBox.setToolTipText("Use these settings as the defaults for patterns");
			saveAsPreferredCheckBox.setSelected(true);
			saveAsPreferredCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
			saveAsPreferredCheckBox.setHorizontalTextPosition(SwingConstants.LEADING);
		}
		
		return saveAsPreferredCheckBox;
	}


	
//	public static Pattern showBox(JPanel panel) throws NegativeNumberException,IllegalArgumentException, ZeroArgumentException{
//		DialogPreferences newPattern = new DialogPreferences(panel);
//		return
//			//showBox(LiteTieWindow, -1,-1,-1);
//			showBox(panel);
//	}
	
	public Object [] readPreferences(){
		Object [] preferences = new Object[83];
		try {
			readPrefs = new Scanner(new File("LiteTiePreferences.ltpf"));
			directoryPath = readPrefs.nextLine();//1
			holeFactor = readPrefs.nextLine();//2
			markerFactor =readPrefs.nextLine();//3
			surfaceFactor = (readPrefs.nextLine());//4
			defaultSupplier = readPrefs.nextLine();//5
			tie1Colour = (Color.decode(readPrefs.nextLine()));//6
			tie1Amount = (readPrefs.nextLine());//7
			tie2Colour = (Color.decode(readPrefs.nextLine()));//8
			tie2Amount = (readPrefs.nextLine());//9
			tie3Colour = (Color.decode(readPrefs.nextLine()));//10
			tie3Amount = (readPrefs.nextLine());//11
			tie4Colour = (Color.decode(readPrefs.nextLine()));//12
			tie4Amount = (readPrefs.nextLine());//13
			tie5Colour = (Color.decode(readPrefs.nextLine()));//14
			tie5Amount = (readPrefs.nextLine());//15
			tie6Colour = (Color.decode(readPrefs.nextLine()));//16
			tie6Amount = (readPrefs.nextLine());//17
			downHole1Colour = (Color.decode(readPrefs.nextLine()));//18
			downHole1Amount = (readPrefs.nextLine());//19		
			downHole2Colour = (Color.decode(readPrefs.nextLine()));//20
			downHole2Amount = (readPrefs.nextLine());//21		
			downHole3Colour = (Color.decode(readPrefs.nextLine()));//22
			downHole3Amount = (readPrefs.nextLine());	//23	

			//REGISTRATION
			registration = (readPrefs.nextLine()); 		//24
			userName = (readPrefs.nextLine()); 			//25

			p_showGrid = readPrefs.nextLine(); //26
			p_showBoundary = readPrefs.nextLine(); //27
			p_showText = readPrefs.nextLine();//28
			p_showHoleSymbols = readPrefs.nextLine();//29
			p_showDummySymbols = readPrefs.nextLine();//30
			p_showSurfaceConnections = readPrefs.nextLine();//31
			p_showSurfaceConnectionLabel = readPrefs.nextLine();//32
			p_showSurfaceTimes = readPrefs.nextLine();//33
			p_showDetonatorTimes = readPrefs.nextLine();//34
			p_showContours = readPrefs.nextLine();//35
			p_showmovements = readPrefs.nextLine();//36
			p_showRelief = readPrefs.nextLine();//37
			p_showFloorCircle = readPrefs.nextLine();//38
			p_showFloorLine = readPrefs.nextLine();//39
			p_showToeCircle = readPrefs.nextLine();//40
			p_showToeLine = readPrefs.nextLine();//41
			p_showDrillTracks = readPrefs.nextLine();//42
			p_showDetonators = readPrefs.nextLine();//43
			p_showDetonatorDelay = readPrefs.nextLine();//44
			p_showHoleID = readPrefs.nextLine();//45
			p_showCollarLoc = readPrefs.nextLine();//46
			p_showFloorLoc = readPrefs.nextLine();//47
			p_showToeLoc = readPrefs.nextLine();//48
			p_showDiameter = readPrefs.nextLine();//49
			p_showLength = readPrefs.nextLine();//50
			p_showHoleAngle = readPrefs.nextLine();//51
			p_showBench = readPrefs.nextLine();//52
			p_showSubdrill = readPrefs.nextLine();//53
			p_showLabelOne = readPrefs.nextLine();//54
			p_showLabelTwo = readPrefs.nextLine();//55
			p_showLabelThree = readPrefs.nextLine();//56

			Color color; 
			color = Color.decode(readPrefs.nextLine());	/*57*/	p_holeTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));/*58*/	
			color = Color.decode(readPrefs.nextLine());	/*59*/	p_holeLengthTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*61*/	p_labelOneTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*63*/	p_labelTwoTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*65*/	p_labelThreeTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*67*/	p_benchHeightTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*69*/	p_subdrillTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*71*/	p_holeDiameterTextColour=(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*73*/	p_holeBearingTextColour=(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*75*/	p_collarLocationTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*77*/	p_floorLocationTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*79*/	p_toeLocationTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*81*/	p_holeAngleTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*83*/	p_backgroundCanvasColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*85*/	p_selectedObjectsColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*87*/	p_gridLinesAndTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*89*/	p_floorLocationFillColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*91*/	p_floorLocationLineColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*93*/	p_toeLocationFillColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*95*/	p_toeLocationLineColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*97*/	p_detonatorDelayTextColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*99*/	p_downHoleFiringTimesColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			color = Color.decode(readPrefs.nextLine());	/*101*/	p_surfaceConnectorFiringTimesColour = (new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
	
			//Font Defaults
			defaultFont = readPrefs.nextLine();//103
			defaultFontSize = readPrefs.nextLine();//104
			applyFontScaling = Boolean.parseBoolean(readPrefs.nextLine());
			tieInLineTolerance = readPrefs.nextLine();
			
			int i = 0;
			preferences[i++] = directoryPath;//0
			preferences[i++] = holeFactor;//1
			preferences[i++] = markerFactor;//2
			preferences[i++] = surfaceFactor;//3
			preferences[i++] = defaultSupplier;//4
			preferences[i++] = tie1Colour;//5
			preferences[i++] = tie1Amount;//6
			preferences[i++] = tie2Colour;//7
			preferences[i++] = tie2Amount;//8
			preferences[i++] = tie3Colour;//9
			preferences[i++] = tie3Amount;//10
			preferences[i++] = tie4Colour;//1
			preferences[i++] = tie4Amount;//2
			preferences[i++] = tie5Colour;//3
			preferences[i++] = tie5Amount;//4
			preferences[i++] = tie6Colour;//5
			preferences[i++] = tie6Amount;//6
			preferences[i++] = downHole1Colour;//7
			preferences[i++] = downHole1Amount;//8
			preferences[i++] = downHole2Colour;//9
			preferences[i++] = downHole2Amount;//20
			preferences[i++] = downHole3Colour;//1
			preferences[i++] = downHole3Amount;//2
			preferences[i++] = registration;//3
			preferences[i++] = userName;//4
			preferences[i++] = p_showGrid;//5
			preferences[i++] = p_showBoundary;//6
			preferences[i++] = p_showText;//7
			preferences[i++] = p_showHoleSymbols;//8
			preferences[i++] = p_showDummySymbols;//9
			preferences[i++] = p_showSurfaceConnections;//30
			preferences[i++] = p_showSurfaceConnectionLabel;//1
			preferences[i++] = p_showSurfaceTimes;//2
			preferences[i++] = p_showDetonatorTimes;//3
			preferences[i++] = p_showContours;//4
			preferences[i++] = p_showmovements;//5
			preferences[i++] = p_showRelief;//6
			preferences[i++] = p_showFloorCircle;//7
			preferences[i++] = p_showFloorLine;//8
			preferences[i++] = p_showToeCircle;//9
			preferences[i++] = p_showToeLine;//40
			preferences[i++] = p_showDrillTracks;//1
			preferences[i++] = p_showDetonators;//2
			preferences[i++] = p_showDetonatorDelay;//3
			preferences[i++] = p_showHoleID;//4
			preferences[i++] = p_showCollarLoc;//5
			preferences[i++] = p_showFloorLoc;//6
			preferences[i++] = p_showToeLoc;//7
			preferences[i++] = p_showDiameter;//8
			preferences[i++] = p_showLength;//9
			preferences[i++] = p_showHoleAngle;//50
			preferences[i++] = p_showBench;//1
			preferences[i++] = p_showSubdrill;//2
			preferences[i++] = p_showLabelOne;//3
			preferences[i++] = p_showLabelTwo;//4
			preferences[i++] = p_showLabelThree;//5
			preferences[i++] = p_holeTextColour;//6
			preferences[i++] = p_holeLengthTextColour;//7
			preferences[i++] = p_labelOneTextColour;//8
			preferences[i++] = p_labelTwoTextColour;//9
			preferences[i++] = p_labelThreeTextColour;//60
			preferences[i++] = p_benchHeightTextColour;//1
			preferences[i++] = p_subdrillTextColour;//2
			preferences[i++] = p_holeDiameterTextColour;//3
			preferences[i++] = p_holeBearingTextColour;//4
			preferences[i++] = p_collarLocationTextColour;//5
			preferences[i++] = p_floorLocationTextColour;//6
			preferences[i++] = p_toeLocationTextColour;//7
			preferences[i++] = p_holeAngleTextColour;//8
			preferences[i++] = p_backgroundCanvasColour;//9
			preferences[i++] = p_selectedObjectsColour;//70
			preferences[i++] = p_gridLinesAndTextColour;//1
			preferences[i++] = p_floorLocationFillColour;//2
			preferences[i++] = p_floorLocationLineColour;//3
			preferences[i++] = p_toeLocationFillColour;//4
			preferences[i++] = p_toeLocationLineColour;//5
			preferences[i++] = p_detonatorDelayTextColour;//6
			preferences[i++] = p_downHoleFiringTimesColour;//7
			preferences[i++] = p_surfaceConnectorFiringTimesColour;//8
			preferences[i++] = defaultFont;
			preferences[i++] = defaultFontSize;
			preferences[i++] = applyFontScaling;
			preferences[i++] = tieInLineTolerance;
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Caught FileNotFoundException - showBox()");
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
			System.out.println("Caught NoSuchElementException - showBox()");
		}
		return preferences ;
		

	}
	
	
	public static File showBox(JPanel canvasPanel){

		DialogPreferences dialog = new DialogPreferences(canvasPanel);
		dialog.setModal(true);
		
		try {
			Scanner readPrefs = new Scanner(new File("LiteTiePreferences.ltpf"));
			int i = 1;

//READ TRY

			dialog.getTextFieldDirectoryPath().setText(readPrefs.nextLine());//1
			dialog.getSpinnerHoleEnhancement().setValue(Float.parseFloat(readPrefs.nextLine()));//2
			dialog.getSpinnerMarkerEnhancement().setValue(Float.parseFloat(readPrefs.nextLine()));//3
			dialog.getSpinnerSurfaceConnectorEnhancement().setValue(Float.parseFloat(readPrefs.nextLine()));//4
			String supplier = readPrefs.nextLine();
			if(supplier.equals("Austin")){																//5	
				System.out.println(i++);
				dialog.getRdbtnAustinPowder().setSelected(true);
				dialog.getRdbtnDynoNobelAp().setSelected(false);
				dialog.getRdbtnMaxamAus().setSelected(false);
				dialog.getRdbtnOricaAustralia().setSelected(false);
				dialog.getRdbtnCustom().setSelected(false);
				dialog.getTextFieldTieOne().setBackground(austinGreen);
				dialog.getTextFieldTieOne().setText("9");				
				dialog.getTextFieldTieTwo().setBackground(austinYellow);
				dialog.getTextFieldTieTwo().setText("17");				
				dialog.getTextFieldTieThree().setBackground(austinRed);	
				dialog.getTextFieldTieThree().setText("25");			
				dialog.getTextFieldTieFour().setBackground(austinOrange);
				dialog.getTextFieldTieFour().setText("33");				
				dialog.getTextFieldTieFive().setBackground(austinWhite);
				dialog.getTextFieldTieFive().setText("42");				
				dialog.getTextFieldTieSix().setBackground(austinBlue);	
				dialog.getTextFieldTieSix().setText("67");				
				readPrefs.nextLine();//1																			//6
				System.out.println(i++);
				readPrefs.nextLine();//2																			//7
				System.out.println(i++);
				readPrefs.nextLine();//3																			//8
				System.out.println(i++);
				readPrefs.nextLine();//4																			//9
				System.out.println(i++);
				readPrefs.nextLine();//5																			//10
				System.out.println(i++);
				readPrefs.nextLine();//6																			//11
				System.out.println(i++);
				readPrefs.nextLine();//7																			//12
				System.out.println(i++);
				readPrefs.nextLine();//8																			//13
				System.out.println(i++);
				readPrefs.nextLine();//9																			//14
				System.out.println(i++);
				readPrefs.nextLine();//10																			//15
				System.out.println(i++);
				readPrefs.nextLine();//11																			//16
				System.out.println(i++);
				readPrefs.nextLine();//12																			//17
				System.out.println(i++);
				dialog.getTextFieldDownHoleOne().setBackground(Color.decode(readPrefs.nextLine()));					//18
				System.out.println(i++);
				dialog.getTextFieldDownHoleOne().setText(readPrefs.nextLine());										//19
				System.out.println(i++);
				dialog.getTextFieldDownHoleTwo().setBackground(Color.decode(readPrefs.nextLine()));					//20
				System.out.println(i++);
				dialog.getTextFieldDownHoleTwo().setText(readPrefs.nextLine());										//21
				System.out.println(i++);
				dialog.getTextFieldDownHoleThree().setBackground(Color.decode(readPrefs.nextLine()));				//22
				System.out.println(i++);
				dialog.getTextFieldDownHoleThree().setText(readPrefs.nextLine());									//23
				System.out.println(i++);
			}
			else if(supplier.equals("Dyno")){															//5
				dialog.getRdbtnAustinPowder().setSelected(false);
				dialog.getRdbtnDynoNobelAp().setSelected(true);
				dialog.getRdbtnMaxamAus().setSelected(false);
				dialog.getRdbtnOricaAustralia().setSelected(false);
				dialog.getRdbtnCustom().setSelected(false);
				dialog.getTextFieldTieOne().setBackground(dynoPurple);
				dialog.getTextFieldTieOne().setText("9");
				dialog.getTextFieldTieTwo().setBackground(dynoYellow);
				dialog.getTextFieldTieTwo().setText("17");
				dialog.getTextFieldTieThree().setBackground(dynoRed);
				dialog.getTextFieldTieThree().setText("25");
				dialog.getTextFieldTieFour().setBackground(dynoWhite);
				dialog.getTextFieldTieFour().setText("42");
				dialog.getTextFieldTieFive().setBackground(dynoBlue);
				dialog.getTextFieldTieFive().setText("67");
				dialog.getTextFieldTieSix().setBackground(dynoBlack);
				dialog.getTextFieldTieSix().setText("109");
				readPrefs.nextLine();//6
				readPrefs.nextLine();//7
				readPrefs.nextLine();//8
				readPrefs.nextLine();//9
				readPrefs.nextLine();//10
				readPrefs.nextLine();//11
				readPrefs.nextLine();//12
				readPrefs.nextLine();//13
				readPrefs.nextLine();//14
				readPrefs.nextLine();//15
				readPrefs.nextLine();//16
				readPrefs.nextLine();//17
				dialog.getTextFieldDownHoleOne().setBackground(Color.decode(readPrefs.nextLine()));//18
				dialog.getTextFieldDownHoleOne().setText(readPrefs.nextLine());//19
				dialog.getTextFieldDownHoleTwo().setBackground(Color.decode(readPrefs.nextLine()));//20
				dialog.getTextFieldDownHoleTwo().setText(readPrefs.nextLine());//21
				dialog.getTextFieldDownHoleThree().setBackground(Color.decode(readPrefs.nextLine()));//22
				dialog.getTextFieldDownHoleThree().setText(readPrefs.nextLine());//23
			}
			else if(supplier.equals("Maxam")){	//5
				dialog.getRdbtnAustinPowder().setSelected(false);
				dialog.getRdbtnDynoNobelAp().setSelected(false);
				dialog.getRdbtnMaxamAus().setSelected(true);
				dialog.getRdbtnOricaAustralia().setSelected(false);
				dialog.getRdbtnCustom().setSelected(false);
				dialog.getTextFieldTieOne().setBackground(maxamGreen);
				dialog.getTextFieldTieOne().setText("9");
				dialog.getTextFieldTieTwo().setBackground(maxamYellow);
				dialog.getTextFieldTieTwo().setText("17");
				dialog.getTextFieldTieThree().setBackground(maxamRed);
				dialog.getTextFieldTieThree().setText("25");
				dialog.getTextFieldTieFour().setBackground(maxamWhite);
				dialog.getTextFieldTieFour().setText("42");
				dialog.getTextFieldTieFive().setBackground(maxamBlack);
				dialog.getTextFieldTieFive().setText("67");
				dialog.getTextFieldTieSix().setBackground(maxamBlue);
				dialog.getTextFieldTieSix().setText("109");
				readPrefs.nextLine();//6
				readPrefs.nextLine();//7
				readPrefs.nextLine();//8
				readPrefs.nextLine();//9
				readPrefs.nextLine();//10
				readPrefs.nextLine();//11
				readPrefs.nextLine();//12
				readPrefs.nextLine();//13
				readPrefs.nextLine();//14
				readPrefs.nextLine();//15
				readPrefs.nextLine();//16
				readPrefs.nextLine();//17
				dialog.getTextFieldDownHoleOne().setBackground(Color.decode(readPrefs.nextLine()));//18
				dialog.getTextFieldDownHoleOne().setText(readPrefs.nextLine());//19
				dialog.getTextFieldDownHoleTwo().setBackground(Color.decode(readPrefs.nextLine()));//20
				dialog.getTextFieldDownHoleTwo().setText(readPrefs.nextLine());//21
				dialog.getTextFieldDownHoleThree().setBackground(Color.decode(readPrefs.nextLine()));//22
				dialog.getTextFieldDownHoleThree().setText(readPrefs.nextLine());//23
			}
			else if(supplier.equals("Orica")){	//5
				dialog.getRdbtnAustinPowder().setSelected(false);
				dialog.getRdbtnDynoNobelAp().setSelected(false);
				dialog.getRdbtnMaxamAus().setSelected(false);
				dialog.getRdbtnOricaAustralia().setSelected(true);
				dialog.getRdbtnCustom().setSelected(false);
				dialog.getTextFieldTieOne().setBackground(oricaGreen);
				dialog.getTextFieldTieOne().setText("9");
				dialog.getTextFieldTieTwo().setBackground(oricaYellow);
				dialog.getTextFieldTieTwo().setText("17");
				dialog.getTextFieldTieThree().setBackground(oricaRed);
				dialog.getTextFieldTieThree().setText("25");
				dialog.getTextFieldTieFour().setBackground(oricaWhite);
				dialog.getTextFieldTieFour().setText("42");
				dialog.getTextFieldTieFive().setBackground(oricaBlue);
				dialog.getTextFieldTieFive().setText("65");
				dialog.getTextFieldTieSix().setBackground(oricaOrange);
				dialog.getTextFieldTieSix().setText("100");
				readPrefs.nextLine();//6
				readPrefs.nextLine();//7
				readPrefs.nextLine();//8
				readPrefs.nextLine();//9
				readPrefs.nextLine();//10
				readPrefs.nextLine();//11
				readPrefs.nextLine();//12
				readPrefs.nextLine();//13
				readPrefs.nextLine();//14
				readPrefs.nextLine();//15
				readPrefs.nextLine();//16
				readPrefs.nextLine();//17
				dialog.getTextFieldDownHoleOne().setBackground(Color.decode(readPrefs.nextLine()));//18
				dialog.getTextFieldDownHoleOne().setText(readPrefs.nextLine());//19
				dialog.getTextFieldDownHoleTwo().setBackground(Color.decode(readPrefs.nextLine()));//20
				dialog.getTextFieldDownHoleTwo().setText(readPrefs.nextLine());//21
				dialog.getTextFieldDownHoleThree().setBackground(Color.decode(readPrefs.nextLine()));//22
				dialog.getTextFieldDownHoleThree().setText(readPrefs.nextLine());//23
				
			}
			else if(supplier.equals("Custom")){//5
				dialog.getRdbtnAustinPowder().setSelected(false);
				dialog.getRdbtnDynoNobelAp().setSelected(false);
				dialog.getRdbtnMaxamAus().setSelected(false);
				dialog.getRdbtnOricaAustralia().setSelected(false);
				dialog.getRdbtnCustom().setSelected(true);
				dialog.getTextFieldTieOne().setBackground(Color.decode(readPrefs.nextLine()));//6
				dialog.getTextFieldTieOne().setText(readPrefs.nextLine());//7
				dialog.getTextFieldTieTwo().setBackground(Color.decode(readPrefs.nextLine()));//8
				dialog.getTextFieldTieTwo().setText(readPrefs.nextLine());//9
				dialog.getTextFieldTieThree().setBackground(Color.decode(readPrefs.nextLine()));//10
				dialog.getTextFieldTieThree().setText(readPrefs.nextLine());//11
				dialog.getTextFieldTieFour().setBackground(Color.decode(readPrefs.nextLine()));//12
				dialog.getTextFieldTieFour().setText(readPrefs.nextLine());//13
				dialog.getTextFieldTieFive().setBackground(Color.decode(readPrefs.nextLine()));//14
				dialog.getTextFieldTieFive().setText(readPrefs.nextLine());//15
				dialog.getTextFieldTieSix().setBackground(Color.decode(readPrefs.nextLine()));//16
				dialog.getTextFieldTieSix().setText(readPrefs.nextLine());//17
				dialog.getTextFieldDownHoleOne().setBackground(Color.decode(readPrefs.nextLine()));//18
				dialog.getTextFieldDownHoleOne().setText(readPrefs.nextLine());//19
				dialog.getTextFieldDownHoleTwo().setBackground(Color.decode(readPrefs.nextLine()));//20
				dialog.getTextFieldDownHoleTwo().setText(readPrefs.nextLine());//21
				dialog.getTextFieldDownHoleThree().setBackground(Color.decode(readPrefs.nextLine()));//22
				dialog.getTextFieldDownHoleThree().setText(readPrefs.nextLine());//23
			}						
			
			//REGISTRATION
			//REGISTRATION INFORMATION
			dialog.getTextFieldReg1().setText(readPrefs.nextLine()); 												//24
			dialog.getTextFieldUserName().setText(readPrefs.nextLine()); 											//25
			
			//ON AT STARTUP
			dialog.getCheckBoxGridLines().setSelected(Boolean.valueOf(readPrefs.nextLine()));//26
			dialog.getCheckBoxBoundaryLinesOn().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxTextOn().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleSymbolsOn().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxDummyHoleSymbols().setSelected(Boolean.valueOf(readPrefs.nextLine()));//30
			dialog.getCheckBoxSurfaceConnections().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxSurfaceConnectorLabels().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxSurfaceConnectorFiring().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxDownHoleFiring().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxSurfaceTimeContours().setSelected(Boolean.valueOf(readPrefs.nextLine()));//35
			dialog.getCheckBoxFirstMovements().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxReliefShading().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxFloorCirclMarker().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxFloorLineMarker().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxToeCircleMarker().setSelected(Boolean.valueOf(readPrefs.nextLine()));//40
			dialog.getCheckBoxToeLineMarker().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleDrillTracks().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxDetonatorIndicationOn().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxDetonatorDelaysOn().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleIdNumber().setSelected(Boolean.valueOf(readPrefs.nextLine()));//45
			dialog.getCheckBoxCollarLocationText().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxFloorLocationText().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxToeLocationText().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleDiameterOn().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleLengthOn().setSelected(Boolean.valueOf(readPrefs.nextLine()));//50
			dialog.getCheckBoxHoleAngleText().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxBenchHeightText().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxSubdrillAmountText().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleLabelOne().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleLabelTwo().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			dialog.getCheckBoxHoleLabelThree().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			
	//COLOR PANEL
			//56 //HOLE ID TEXT
			Color color; 
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelHoleIDColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//HOLE LENGTH
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelHoleLengthColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//LABEL ONE
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelLabelOneColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//LABEL TWO
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelLabelTwoColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//60 //LABEL THREE
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelLabelThreeColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//61 //BENCH HEIGHT TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelBenchHeightColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//62 //SUBDRILL TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelSubdrillColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//63 //HOLE DIAMETER
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelHoleDiameterColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//64 //HOLE BEARING
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelHoleBearingColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//65 //COLLAR LOCATION TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelCollarLocColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			// 66 //FLOOR LOCATION TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelFloorLocTextColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//67 //TOE LOCATION TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelToeLocTextColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//68 //ANGLE TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelHoleAngleTextColour().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			
			//69 //CANVAS BACK GROUND
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelCanvasColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//SELECTED OBJECTS
			color = Color.decode(readPrefs.nextLine());
			dialog.getSelectedObjectsColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			p_selectedObjectsColour = dialog.getSelectedObjectsColor().getBackground();
			//GRID LINES AND TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelGridLineColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//FLOOR LOCATION FILL
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelFloorLocFillColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//FLOOR LOCATION LINE
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelFloorLineColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//TOE LOCATION FILL
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelToeLocFillColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//TOE LOCATION LINE
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelToeLocLineColor().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//DETONATOR DELAY TEXT
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelDetonatorDelayText().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//DOWNHOLE FIRING TIMES
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelDownholeFiringTime().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			//SURFACE FIRING TIMES
			color = Color.decode(readPrefs.nextLine());
			dialog.getPanelSurfaceFiringTimes().setBackground(new Color((int)color.getRed(), (int)color.getGreen(), (int)color.getBlue(), Integer.parseInt(readPrefs.nextLine())));	
			
			p_holeTextColour = dialog.getPanelHoleIDColor().getBackground();
			p_holeLengthTextColour = dialog.getPanelHoleLengthColor().getBackground();
			p_labelOneTextColour = dialog.getPanelLabelOneColor().getBackground();;
			p_labelTwoTextColour = dialog.getPanelLabelTwoColor().getBackground();;
			p_labelThreeTextColour = dialog.getPanelLabelThreeColor().getBackground();;
			p_benchHeightTextColour = dialog.getPanelBenchHeightColor().getBackground();;
			p_subdrillTextColour = dialog.getPanelSubdrillColor().getBackground();;
			p_holeDiameterTextColour = dialog.getPanelHoleDiameterColor().getBackground();;
			p_holeBearingTextColour = dialog.getPanelHoleBearingColor().getBackground();;
			p_collarLocationTextColour = dialog.getPanelCollarLocColor().getBackground();;
			p_floorLocationTextColour = dialog.getPanelFloorLocTextColor().getBackground();
			p_toeLocationTextColour = dialog.getPanelToeLocTextColor().getBackground();
			p_holeAngleTextColour = dialog.getPanelHoleAngleTextColour().getBackground();
			p_backgroundCanvasColour = dialog.getPanelCanvasColor().getBackground();
			p_selectedObjectsColour = dialog.getSelectedObjectsColor().getBackground();
			p_gridLinesAndTextColour = dialog.getPanelGridLineColor().getBackground();
			p_floorLocationFillColour = dialog.getPanelFloorLocFillColor().getBackground();
			p_floorLocationLineColour = dialog.getPanelFloorLineColor().getBackground();
			p_toeLocationFillColour = dialog.getPanelToeLocFillColor().getBackground();
			p_toeLocationLineColour = dialog.getPanelToeLocLineColor().getBackground();
			p_detonatorDelayTextColour = dialog.getPanelDetonatorDelayText().getBackground();
			p_downHoleFiringTimesColour = dialog.getPanelDownholeFiringTime().getBackground();
			p_surfaceConnectorFiringTimesColour = dialog.getPanelSurfaceFiringTimes().getBackground();
						
			//Read in the preference and set the UI to it
			String fontNameReadIn = readPrefs.nextLine();
			
			if(fontNames.contains(fontNameReadIn)){
				dialog.getSpinnerFontName().setValue(fontNameReadIn);
			}
			else{
				dialog.getSpinnerFontName().setValue("Dialog");
			}
			dialog.getSpinnerFontPointSize().setValue(Integer.parseInt(readPrefs.nextLine()));
			//Font related amounts
			defaultFont = dialog.getSpinnerFontName().getValue().toString();
			defaultFontSize = dialog.getSpinnerFontPointSize().getValue().toString();
			dialog.getCheckBoxApplyFontScaling().setSelected(Boolean.valueOf(readPrefs.nextLine()));
			//Tie in Line Tolerance 
			dialog.getSpinnerTieInLineTolerance().setValue(Float.parseFloat(readPrefs.nextLine()));
			tieInLineTolerance = dialog.getSpinnerTieInLineTolerance().getValue().toString();
		} 

		catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(canvasPanel,
				    "Unable to Load Defaults - File Not Found - Contact Chin-Apse Pty Ltd","Preferences.ltpf - File Not Found",
				    JOptionPane.ERROR_MESSAGE);
			System.out.println("Caught FileNotFoundException - showBox()");
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(canvasPanel,
					"Unable to Load Defaults  -  Contact Chin-Apse Pty Ltd", "Preferences.ltpf - No Such Element Exception",
				    JOptionPane.ERROR_MESSAGE);
			System.out.println("Caught NoSuchElementException - showBox()");
		}
		
		
		String directoryPath = dialog.getTextFieldDirectoryPath().getText();
		//ENHANCEMENT FACTOR PREFS 20130111
		String holeEFactor = dialog.getSpinnerHoleEnhancement().getValue().toString();
		String markerEFactor = dialog.getSpinnerHoleEnhancement().getValue().toString();
		String surfaceEFactor = dialog.getSpinnerHoleEnhancement().getValue().toString();
		String defaultFontSize = dialog.getSpinnerFontPointSize().getValue().toString();
		String defaultFontNameString = dialog.getSpinnerFontName().getValue().toString();
		String tieInLineToleranceAmount = dialog.getSpinnerTieInLineTolerance().getValue().toString();
		
		//PREFERRED SUPPLIER 20130111
		String supplier = "Custom";
		if(dialog.getRdbtnAustinPowder().isSelected()){supplier = dialog.getRdbtnAustinPowder().getText();}
		else if(dialog.getRdbtnDynoNobelAp().isSelected()) {supplier = dialog.getRdbtnDynoNobelAp().getText();}
		else if(dialog.getRdbtnMaxamAus().isSelected()) {supplier = dialog.getRdbtnMaxamAus().getText();}
		else if(dialog.getRdbtnOricaAustralia().isSelected()) {supplier = dialog.getRdbtnOricaAustralia().getText();}
		else supplier = "Custom";
		
		Color customTie1 = dialog.getTextFieldTieOne().getBackground();
		String customTieTime1 = dialog.getTextFieldTieOne().getText();
		Color customTie2 = dialog.getTextFieldTieTwo().getBackground();
		String customTieTime2 = dialog.getTextFieldTieTwo().getText();
		Color customTie3 = dialog.getTextFieldTieThree().getBackground();
		String customTieTime3 = dialog.getTextFieldTieThree().getText();
		Color customTie4 = dialog.getTextFieldTieFour().getBackground();
		String customTieTime4 = dialog.getTextFieldTieFour().getText();
		Color customTie5 = dialog.getTextFieldTieFive().getBackground();
		String customTieTime5 = dialog.getTextFieldTieFive().getText();
		Color customTie6 = dialog.getTextFieldTieSix().getBackground();
		String customTieTime6 = dialog.getTextFieldTieSix().getText();
		Color customDownhole1 = dialog.getTextFieldDownHoleOne().getBackground();
		String customDownholeTime1 = dialog.getTextFieldDownHoleOne().getText();
		Color customDownhole2 = dialog.getTextFieldDownHoleTwo().getBackground();
		String customDownholeTime2 = dialog.getTextFieldDownHoleTwo().getText();
		Color customDownhole3 = dialog.getTextFieldDownHoleThree().getBackground();
		String customDownholeTime3 = dialog.getTextFieldDownHoleThree().getText();
		
		String registration = dialog.getTextFieldReg1().getText();
		String userName = dialog.getTextFieldUserName().getText();
		
		String isOnGridLines = Boolean.toString(dialog.getCheckBoxGridLines().isSelected());
		String isOnCheckBoxBoundaryLinesOn = Boolean.toString(dialog.getCheckBoxBoundaryLinesOn().isSelected());
		String isOnCheckBoxTextOn  = Boolean.toString(dialog.getCheckBoxTextOn().isSelected());
		String isOnCheckBoxHoleAngleText = Boolean.toString(dialog.getCheckBoxHoleAngleText().isSelected());
		String isOnCheckBoxHoleSymbolsOn = Boolean.toString(dialog.getCheckBoxHoleSymbolsOn().isSelected());
		String isOnCheckBoxDummyHoleSymbols = Boolean.toString(dialog.getCheckBoxDummyHoleSymbols().isSelected());
		String isOnCheckBoxHoleDrillTracks  = Boolean.toString(dialog.getCheckBoxHoleDrillTracks().isSelected());
		String isOnCheckBoxFloorCirclMarker = Boolean.toString(dialog.getCheckBoxFloorCirclMarker().isSelected());
		String isOnCheckBoxFloorLineMarker = Boolean.toString(dialog.getCheckBoxFloorLineMarker().isSelected());
		String isOnCheckBoxToeLineMarker = Boolean.toString(dialog.getCheckBoxToeLineMarker().isSelected());
		String isOnCheckBoxHoleLabelTwo  = Boolean.toString(dialog.getCheckBoxHoleLabelTwo().isSelected());
		String isOnCheckBoxHoleIdNumber = Boolean.toString(dialog.getCheckBoxHoleIdNumber().isSelected());
		String isOnCheckBoxHoleLabelOne  = Boolean.toString(dialog.getCheckBoxHoleLabelOne().isSelected());
		String isOnCheckBoxHoleLabelThree = Boolean.toString(dialog.getCheckBoxHoleLabelThree().isSelected());
		String isOnCheckBoxBenchHeightText = Boolean.toString(dialog.getCheckBoxBenchHeightText().isSelected());
		String isOnCheckBoxSubdrillAmountText = Boolean.toString(dialog.getCheckBoxSubdrillAmountText().isSelected());
		String isOnCheckBoxCollarLocationText = Boolean.toString(dialog.getCheckBoxCollarLocationText().isSelected());
		String isOnCheckBoxFloorLocationText = Boolean.toString(dialog.getCheckBoxFloorLocationText().isSelected());
		String isOnCheckBoxToeLocationText = Boolean.toString(dialog.getCheckBoxToeLocationText().isSelected());
		String isOnCheckBoxHoleLengthOn  = Boolean.toString(dialog.getCheckBoxHoleLengthOn().isSelected());
		String isOnCheckBoxHoleDiameterOn = Boolean.toString(dialog.getCheckBoxHoleDiameterOn().isSelected());
		String isOnCheckBoxHoleBearingOn = Boolean.toString(dialog.getCheckBoxSurfaceConnections().isSelected());
		String isOnCheckBoxSurfaceConnectorLabels = Boolean.toString(dialog.getCheckBoxSurfaceConnectorLabels().isSelected());
		String isOnCheckBoxDetonatorIndicationOn = Boolean.toString(dialog.getCheckBoxDetonatorIndicationOn().isSelected());
		String isOnCheckBoxDetonatorDelaysOn = Boolean.toString(dialog.getCheckBoxDetonatorDelaysOn().isSelected());
		String isOnCheckBoxDownHoleFiring = Boolean.toString(dialog.getCheckBoxDownHoleFiring().isSelected());
		String isOnCheckBoxSurfaceConnectorFiring  = Boolean.toString(dialog.getCheckBoxSurfaceConnectorFiring().isSelected());
		String isOnCheckBoxSurfaceTimeContours = Boolean.toString(dialog.getCheckBoxSurfaceTimeContours().isSelected());
		String isOnCheckBoxFirstMovements = Boolean.toString(dialog.getCheckBoxFirstMovements().isSelected());
		String isOnCheckBoxReliefShading = Boolean.toString(dialog.getCheckBoxReliefShading().isSelected());
		
		String panelCanvasColor = dialog.getPanelCanvasColor().getBackground().toString();
		String panelGridLineColor =  dialog.getPanelGridLineColor().getBackground().toString();
		String panelHoleIDColor =  dialog.getPanelHoleIDColor().getBackground().toString();
		String panelHoleLengthColor =  dialog.getPanelHoleLengthColor().getBackground().toString();
		String panelLabelOneColor =  dialog.getPanelLabelOneColor().getBackground().toString();
		String panelLabelTwoColor = dialog.getPanelLabelTwoColor().getBackground().toString();
		String panelLabelThreeColor = dialog.getPanelLabelThreeColor().getBackground().toString();
		String panelBenchHeightColor = dialog.getPanelBenchHeightColor().getBackground().toString();
		String panelSubdrillColor = dialog.getPanelSubdrillColor().getBackground().toString();
		String panelHoleDiameterColor = dialog.getPanelHoleDiameterColor().getBackground().toString();
		String panelHoleBearingColor = dialog.getPanelHoleBearingColor().getBackground().toString();
		String panelHoleAngleTextColour = dialog.getPanelHoleAngleTextColour().getBackground().toString();
		String panelFloorLocFillColor = dialog.getPanelFloorLocFillColor().getBackground().toString();
		String panelFloorLineColor = dialog.getPanelFloorLineColor().getBackground().toString();
		String panelToeLocFillColor = dialog.getPanelToeLocFillColor().getBackground().toString();
		String panelToeLocLineColor = dialog.getPanelToeLocLineColor().getBackground().toString();
		String panelCollarLocColor = dialog.getPanelCollarLocColor().getBackground().toString();
		String panelFloorLocTextColor = dialog.getPanelFloorLocTextColor().getBackground().toString();
		String panelToeLocTextColor = dialog.getPanelToeLocTextColor().getBackground().toString();
		 		

		do {
			
			dialog.setVisible(true);
			
			if (dialog.okPushed == true) {
				try {
					directoryPath = dialog.getTextFieldDirectoryPath().getText();
					 dialog.getTextFieldDirectoryPath().setForeground(Color.black);
				} 
				catch (IllegalArgumentException e) {
					dialog.getTextFieldDirectoryPath().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");								
				}
				try {
					holeEFactor = (dialog.getSpinnerHoleEnhancement().getValue().toString());
					dialog.getSpinnerHoleEnhancement().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerHoleEnhancement().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("NumberFormatException");								
				}
				try {
					surfaceEFactor = (dialog.getSpinnerSurfaceConnectorEnhancement().getValue().toString());
					dialog.getSpinnerSurfaceConnectorEnhancement().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerSurfaceConnectorEnhancement().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("NumberFormatException");								
				}
				try {
					markerEFactor = (dialog.getSpinnerMarkerEnhancement().getValue().toString());
					dialog.getSpinnerMarkerEnhancement().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerMarkerEnhancement().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("NumberFormatException");								
				}
				try {
					tieInLineToleranceAmount = (dialog.getSpinnerTieInLineTolerance().getValue().toString());
					dialog.getSpinnerTieInLineTolerance().setForeground(Color.black);
				} 
				catch (NumberFormatException e) {
					dialog.getSpinnerTieInLineTolerance().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("NumberFormatException");								
				}
				try {
					if(dialog.getRdbtnAustinPowder().isSelected()) {
						supplier = dialog.getRdbtnAustinPowder().getText();
					}
					if(dialog.getRdbtnDynoNobelAp().isSelected()) {
						supplier = dialog.getRdbtnDynoNobelAp().getText();
					}
					if(dialog.getRdbtnMaxamAus().isSelected()) {
						supplier = dialog.getRdbtnMaxamAus().getText();
					}
					if(dialog.getRdbtnOricaAustralia().isSelected()) {
						supplier = dialog.getRdbtnOricaAustralia().getText();
					}
					if(dialog.getRdbtnCustom().isSelected()) {
						supplier = dialog.getRdbtnCustom().getText();
					}
					
				} 
				catch (IllegalArgumentException e) {
					supplier = dialog.getRdbtnCustom().getText();
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");								
				}
				//SURFACE CONNECTION COLOURS
				try {
					customTie1 = dialog.getTextFieldTieOne().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTie2 = dialog.getTextFieldTieTwo().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTie3 = dialog.getTextFieldTieThree().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTie4 = dialog.getTextFieldTieFour().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTie5 = dialog.getTextFieldTieFive().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTie6 = dialog.getTextFieldTieSix().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				//DOWNHOLE COLOURS
				try {
					customDownhole1 = dialog.getTextFieldDownHoleOne().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customDownhole2 = dialog.getTextFieldDownHoleTwo().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customDownhole3 = dialog.getTextFieldDownHoleThree().getBackground();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				//SURFACE CONNECTION TEXT
				try {
					customTieTime1 = dialog.getTextFieldTieOne().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTieTime2 = dialog.getTextFieldTieTwo().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTieTime3 = dialog.getTextFieldTieThree().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTieTime4 = dialog.getTextFieldTieFour().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTieTime5 = dialog.getTextFieldTieFive().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customTieTime6 = dialog.getTextFieldTieSix().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				//DOWNHOLE DELAY TEXT
				try {
					customDownholeTime1 = dialog.getTextFieldDownHoleOne().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customDownholeTime2 = dialog.getTextFieldDownHoleTwo().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				try {
					customDownholeTime3 = dialog.getTextFieldDownHoleThree().getText();
				} 
				catch (IllegalArgumentException nfe) {
					dialog.badValue = true;
					System.out.println("IllegalArgumentException");				
				}
				//REGISTRATION			
				try {
					registration = dialog.getTextFieldReg1().getText();
					userName = dialog.getTextFieldUserName().getText();
					dialog.getTextFieldReg1().setForeground(Color.black);
					dialog.getTextFieldUserName().setForeground(Color.black);
				} 
			
				catch (IllegalArgumentException iae) {
					dialog.getTextFieldReg1().setForeground(Color.red);
					dialog.getTextFieldUserName().setForeground(Color.red);
					dialog.badValue = true;
					System.out.println("startFRL Bad Value(s) - showBox() method - IllegalArgumentException");
				}
				//CHECKBOX PANEL
				try {
					isOnGridLines = Boolean.toString(dialog.getCheckBoxGridLines().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnGridLines = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxBoundaryLinesOn = Boolean.toString(dialog.getCheckBoxBoundaryLinesOn().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxBoundaryLinesOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxTextOn  = Boolean.toString(dialog.getCheckBoxTextOn().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxTextOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleAngleText = Boolean.toString(dialog.getCheckBoxHoleAngleText().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleAngleText = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleSymbolsOn = Boolean.toString(dialog.getCheckBoxHoleSymbolsOn().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleSymbolsOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxDummyHoleSymbols = Boolean.toString(dialog.getCheckBoxDummyHoleSymbols().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxDummyHoleSymbols = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleDrillTracks  = Boolean.toString(dialog.getCheckBoxHoleDrillTracks().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleDrillTracks = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxFloorCirclMarker = Boolean.toString(dialog.getCheckBoxFloorCirclMarker().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxFloorCirclMarker = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxFloorLineMarker = Boolean.toString(dialog.getCheckBoxFloorLineMarker().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxFloorLineMarker = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxToeLineMarker = Boolean.toString(dialog.getCheckBoxToeLineMarker().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxToeLineMarker = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleLabelTwo  = Boolean.toString(dialog.getCheckBoxHoleLabelTwo().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleLabelTwo = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleIdNumber = Boolean.toString(dialog.getCheckBoxHoleIdNumber().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleIdNumber = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleLabelOne  = Boolean.toString(dialog.getCheckBoxHoleLabelOne().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleLabelOne = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleLabelThree = Boolean.toString(dialog.getCheckBoxHoleLabelThree().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleLabelThree = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxBenchHeightText = Boolean.toString(dialog.getCheckBoxBenchHeightText().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxBenchHeightText = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxSubdrillAmountText = Boolean.toString(dialog.getCheckBoxSubdrillAmountText().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxSubdrillAmountText = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxCollarLocationText = Boolean.toString(dialog.getCheckBoxCollarLocationText().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxCollarLocationText = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxFloorLocationText = Boolean.toString(dialog.getCheckBoxFloorLocationText().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxFloorLocationText = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxToeLocationText = Boolean.toString(dialog.getCheckBoxToeLocationText().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxToeLocationText = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleLengthOn  = Boolean.toString(dialog.getCheckBoxHoleLengthOn().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleLengthOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleDiameterOn = Boolean.toString(dialog.getCheckBoxHoleDiameterOn().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleDiameterOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxHoleBearingOn = Boolean.toString(dialog.getCheckBoxSurfaceConnections().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxHoleBearingOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxSurfaceConnectorLabels = Boolean.toString(dialog.getCheckBoxSurfaceConnectorLabels().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxSurfaceConnectorLabels = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxDetonatorIndicationOn = Boolean.toString(dialog.getCheckBoxDetonatorIndicationOn().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxDetonatorIndicationOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxDetonatorDelaysOn = Boolean.toString(dialog.getCheckBoxDetonatorDelaysOn().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxDetonatorDelaysOn = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxDownHoleFiring = Boolean.toString(dialog.getCheckBoxDownHoleFiring().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxDownHoleFiring = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxSurfaceConnectorFiring  = Boolean.toString(dialog.getCheckBoxSurfaceConnectorFiring().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxSurfaceConnectorFiring = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxSurfaceTimeContours = Boolean.toString(dialog.getCheckBoxSurfaceTimeContours().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxSurfaceTimeContours = "true";
					dialog.badValue = true;
				}
				try {
					isOnCheckBoxFirstMovements = Boolean.toString(dialog.getCheckBoxFirstMovements().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxFirstMovements = "true";
				}
				try {
					isOnCheckBoxReliefShading = Boolean.toString(dialog.getCheckBoxReliefShading().isSelected());
				}
				catch (IllegalArgumentException b) {
					isOnCheckBoxReliefShading = "true";
					dialog.badValue = true;
				}
				
				//COLOR PANEL
				try {
					panelCanvasColor = dialog.getPanelCanvasColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelCanvasColor = Color.WHITE.toString();
					dialog.badValue = true;
				}
				try {
					panelGridLineColor =  dialog.getPanelGridLineColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelGridLineColor = Color.CYAN.toString();
					dialog.badValue = true;
				}
				try {
					panelHoleIDColor =  dialog.getPanelHoleIDColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelHoleIDColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelHoleLengthColor =  dialog.getPanelHoleLengthColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelHoleLengthColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelLabelOneColor =  dialog.getPanelLabelOneColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelHoleLengthColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelLabelTwoColor = dialog.getPanelLabelTwoColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelLabelTwoColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelLabelThreeColor = dialog.getPanelLabelThreeColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelLabelThreeColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				
				try {
					panelBenchHeightColor = dialog.getPanelBenchHeightColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelBenchHeightColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelSubdrillColor = dialog.getPanelSubdrillColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelSubdrillColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelHoleDiameterColor = dialog.getPanelHoleDiameterColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelHoleDiameterColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelHoleBearingColor = dialog.getPanelHoleBearingColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelHoleBearingColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelFloorLocFillColor = dialog.getPanelFloorLocFillColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelFloorLocFillColor = new Color(255,0,0,10).toString();
					dialog.badValue = true;
				}
				try {
					panelFloorLineColor = dialog.getPanelFloorLineColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelFloorLineColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelHoleAngleTextColour = dialog.getPanelHoleAngleTextColour().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelHoleAngleTextColour = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelToeLocFillColor = dialog.getPanelToeLocFillColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelToeLocFillColor = new Color(255,0,0,10).toString();
					dialog.badValue = true;
				}
				try {
					panelToeLocLineColor = dialog.getPanelToeLocLineColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelToeLocLineColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelCollarLocColor = dialog.getPanelCollarLocColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelCollarLocColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelFloorLocTextColor = dialog.getPanelFloorLocTextColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelFloorLocTextColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
				try {
					panelToeLocTextColor = dialog.getPanelToeLocTextColor().getBackground().toString();
				}
				catch (IllegalArgumentException b) {
					panelToeLocTextColor = Color.BLACK.toString();
					dialog.badValue = true;
				}
	
				if (dialog.badValue) 
						continue;
				
			}
			
			if (dialog.saveAsPreferredCheckBox.isSelected()) {
				try {
					
					PrintStream preferenceWriter = new PrintStream(new File("LiteTiePreferences.ltpf"));

					preferenceWriter.println(dialog.getTextFieldDirectoryPath().getText());//1
					preferenceWriter.println(dialog.getSpinnerHoleEnhancement().getValue());//2
					preferenceWriter.println(dialog.getSpinnerMarkerEnhancement().getValue());//3
					preferenceWriter.println(dialog.getSpinnerSurfaceConnectorEnhancement().getValue());//4
					preferenceWriter.println(supplier);//5
					preferenceWriter.println(dialog.getTextFieldTieOne().getBackground().getRGB());				//6
					preferenceWriter.println(dialog.getTextFieldTieOne().getText());							//7
					preferenceWriter.println(dialog.getTextFieldTieTwo().getBackground().getRGB());				//8
					preferenceWriter.println(dialog.getTextFieldTieTwo().getText());							//9
					preferenceWriter.println(dialog.getTextFieldTieThree().getBackground().getRGB());			//10
					preferenceWriter.println(dialog.getTextFieldTieThree().getText());							//11
					preferenceWriter.println(dialog.getTextFieldTieFour().getBackground().getRGB());			//12
					preferenceWriter.println(dialog.getTextFieldTieFour().getText());							//13
					preferenceWriter.println(dialog.getTextFieldTieFive().getBackground().getRGB());			//14
					preferenceWriter.println(dialog.getTextFieldTieFive().getText());							//15
					preferenceWriter.println(dialog.getTextFieldTieSix().getBackground().getRGB());				//16
					preferenceWriter.println(dialog.getTextFieldTieSix().getText());							//17
					preferenceWriter.println(dialog.getTextFieldDownHoleOne().getBackground().getRGB());		//18
					preferenceWriter.println(dialog.getTextFieldDownHoleOne().getText());						//19
					preferenceWriter.println(dialog.getTextFieldDownHoleTwo().getBackground().getRGB());		//20
					preferenceWriter.println(dialog.getTextFieldDownHoleTwo().getText());						//21
					preferenceWriter.println(dialog.getTextFieldDownHoleThree().getBackground().getRGB());		//22
					preferenceWriter.println(dialog.getTextFieldDownHoleThree().getText());						//23
					
					//REGISTRATION
					preferenceWriter.println(registration);//24
					preferenceWriter.println(userName);//25
					
					//ON AT STARTUP
					preferenceWriter.println(dialog.getCheckBoxGridLines().isSelected());//26
					preferenceWriter.println(dialog.getCheckBoxBoundaryLinesOn().isSelected());
					preferenceWriter.println(dialog.getCheckBoxTextOn().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleSymbolsOn().isSelected());
					preferenceWriter.println(dialog.getCheckBoxDummyHoleSymbols().isSelected());//30
					preferenceWriter.println(dialog.getCheckBoxSurfaceConnections().isSelected());
					preferenceWriter.println(dialog.getCheckBoxSurfaceConnectorLabels().isSelected());
					preferenceWriter.println(dialog.getCheckBoxSurfaceConnectorFiring().isSelected());
					preferenceWriter.println(dialog.getCheckBoxDownHoleFiring().isSelected());
					preferenceWriter.println(dialog.getCheckBoxSurfaceTimeContours().isSelected());//35
					preferenceWriter.println(dialog.getCheckBoxFirstMovements().isSelected());
					preferenceWriter.println(dialog.getCheckBoxReliefShading().isSelected());
					preferenceWriter.println(dialog.getCheckBoxFloorCirclMarker().isSelected());
					preferenceWriter.println(dialog.getCheckBoxFloorLineMarker().isSelected());
					preferenceWriter.println(dialog.getCheckBoxToeCircleMarker().isSelected());//40
					preferenceWriter.println(dialog.getCheckBoxToeLineMarker().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleDrillTracks().isSelected());
					preferenceWriter.println(dialog.getCheckBoxDetonatorIndicationOn().isSelected());
					preferenceWriter.println(dialog.getCheckBoxDetonatorDelaysOn().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleIdNumber().isSelected());//45
					preferenceWriter.println(dialog.getCheckBoxCollarLocationText().isSelected());
					preferenceWriter.println(dialog.getCheckBoxFloorLocationText().isSelected());
					preferenceWriter.println(dialog.getCheckBoxToeLocationText().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleDiameterOn().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleLengthOn().isSelected());//50
					preferenceWriter.println(dialog.getCheckBoxHoleAngleText().isSelected());
					preferenceWriter.println(dialog.getCheckBoxBenchHeightText().isSelected());
					preferenceWriter.println(dialog.getCheckBoxSubdrillAmountText().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleLabelOne().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleLabelTwo().isSelected());
					preferenceWriter.println(dialog.getCheckBoxHoleLabelThree().isSelected());
					
			//COLOR PANEL
					//HOLE ID TEXT
					preferenceWriter.println(dialog.getPanelHoleIDColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelHoleIDColor().getBackground().getAlpha());
					//HOLE LENGTH
					preferenceWriter.println(dialog.getPanelHoleLengthColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelHoleLengthColor().getBackground().getAlpha());
					//LABEL ONE
					preferenceWriter.println(dialog.getPanelLabelOneColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelLabelOneColor().getBackground().getAlpha());
					//LABEL TWO
					preferenceWriter.println(dialog.getPanelLabelTwoColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelLabelTwoColor().getBackground().getAlpha());
					//LABEL THREE
					preferenceWriter.println(dialog.getPanelLabelThreeColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelLabelThreeColor().getBackground().getAlpha());
					//BENCH HEIGHT TEXT
					preferenceWriter.println(dialog.getPanelBenchHeightColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelBenchHeightColor().getBackground().getAlpha());
					//SUBDRILL TEXT
					preferenceWriter.println(dialog.getPanelSubdrillColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelSubdrillColor().getBackground().getAlpha());
					//HOLE DIAMETER
					preferenceWriter.println(dialog.getPanelHoleDiameterColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelHoleDiameterColor().getBackground().getAlpha());
					//HOLE BEARING
					preferenceWriter.println(dialog.getPanelHoleBearingColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelHoleBearingColor().getBackground().getAlpha());
					//COLLAR LOCATION TEXT
					preferenceWriter.println(dialog.getPanelCollarLocColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelCollarLocColor().getBackground().getAlpha());
					//FLOOR LOCATION TEXT
					preferenceWriter.println(dialog.getPanelFloorLocTextColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelFloorLocTextColor().getBackground().getAlpha());
					//TOE LOCATION TEXT
					preferenceWriter.println(dialog.getPanelToeLocTextColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelToeLocTextColor().getBackground().getAlpha());
					//ANGLE TEXT
					preferenceWriter.println(dialog.getPanelHoleAngleTextColour().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelHoleAngleTextColour().getBackground().getAlpha());
					
					//CANVAS BACK GROUND
					preferenceWriter.println(dialog.getPanelCanvasColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelCanvasColor().getBackground().getAlpha());
					//SELECTED OBJECTS
					preferenceWriter.println(dialog.getSelectedObjectsColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getSelectedObjectsColor().getBackground().getAlpha());
					//GRID LINES AND TEXT
					preferenceWriter.println(dialog.getPanelGridLineColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelGridLineColor().getBackground().getAlpha());
					//FLOOR LOCATION FILL
					preferenceWriter.println(dialog.getPanelFloorLocFillColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelFloorLocFillColor().getBackground().getAlpha());
					//FLOOR LOCATION LINE
					preferenceWriter.println(dialog.getPanelFloorLineColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelFloorLineColor().getBackground().getAlpha());
					//TOE LOCATION FILL
					preferenceWriter.println(dialog.getPanelToeLocFillColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelToeLocFillColor().getBackground().getAlpha());
					//TOE LOCATION LINE
					preferenceWriter.println(dialog.getPanelToeLocLineColor().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelToeLocLineColor().getBackground().getAlpha());
					//DETONATOR DELAY TEXT
					preferenceWriter.println(dialog.getPanelDetonatorDelayText().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelDetonatorDelayText().getBackground().getAlpha());
					//DOWNHOLE FIRING TIMES
					preferenceWriter.println(dialog.getPanelDownholeFiringTime().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelDownholeFiringTime().getBackground().getAlpha());
					//SURFACE FIRING TIMES
					preferenceWriter.println(dialog.getPanelSurfaceFiringTimes().getBackground().getRGB());
					preferenceWriter.println(dialog.getPanelSurfaceFiringTimes().getBackground().getAlpha());
					
					//FONT SETTINGS
					preferenceWriter.println(dialog.getSpinnerFontName().getValue().toString());
					preferenceWriter.println(dialog.getSpinnerFontPointSize().getValue().toString());
					preferenceWriter.println(dialog.getCheckBoxApplyFontScaling().isSelected());
					
					//TIE IN LINE
					preferenceWriter.println(dialog.getSpinnerTieInLineTolerance().getValue().toString());
					
					preferenceWriter.close();
				} 
				catch (FileNotFoundException e1) {
					status = "Unable to Save as Default - File Not Found - Check that the file is a valid and accessible LiteTie file.";
					
					JOptionPane.showMessageDialog(canvasPanel,
						    "Unable to Save as Default /nFile Not Found",
						    "LiteTiePreferences.ltpf - File Not Found - error",
						    JOptionPane.ERROR_MESSAGE);
					System.out.println("Caught FileNotFoundException - showBox()");
					e1.printStackTrace();
				}
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
			
		while(dialog.badValue);
		
		return null;

	}
		


	public static void main(String[] args) throws IllegalArgumentException, NegativeNumberException, ZeroArgumentException {

		showBox(null);
		
		System.exit(0);
	}
	private JPanel getSelectedObjectsColor() {
		if (wellSelectedObjectsColor == null) {
			wellSelectedObjectsColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellSelectedObjectsColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);
						g.fillRect(14, 2, 6, 6);
						g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);
						g.fillRect(14, 14, 6, 6);
						g.setColor(wellSelectedObjectsColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellSelectedObjectsColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellSelectedObjectsColor.setPreferredSize(new Dimension(22, 22));
			wellSelectedObjectsColor.setBackground(Color.MAGENTA);
			wellSelectedObjectsColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getSelectedObjectsColor().getBackground());
					if (currentColor != null) {
						getSelectedObjectsColor().setBackground(currentColor);
						p_selectedObjectsColour = (wellSelectedObjectsColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});


		}
		return wellSelectedObjectsColor;
	}
	private JLabel getLabelDisplayColour() {
		if (labelDisplayColour == null) {
			labelDisplayColour = new JLabel();
			labelDisplayColour.setText("Selected Objects");
			labelDisplayColour.setPreferredSize(new Dimension(120, 22));
			labelDisplayColour.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return labelDisplayColour;
	}
	private JPanel getPreferenceTabHolder2() {
		if (preferenceTabHolder2 == null) {
			preferenceTabHolder2 = new JPanel();
			preferenceTabHolder2.setLayout(new GridLayout(0, 1, 0, 0));
			preferenceTabHolder2.add(getTabbedPane());
			preferenceTabHolder2.setFocusTraversalPolicy(new FocusTraversalOnArray(new JComponent[]{getGeneralPanel()}));
		}
		return preferenceTabHolder2;
	}
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane.addTab("General", null, getGeneralPanel(), null);
			tabbedPane.addTab("Colour", null, getColorPanel(), null);
			tabbedPane.addTab("Display", null, getDisplayPanel(), null);
			tabbedPane.addTab("Registration", null, getRegistrationPanel(), null);
		}
		return tabbedPane;
	}
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) buttonPanel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			buttonPanel.add(getSaveAsPreferredCheckBox());
			buttonPanel.add(getCancel());
			buttonPanel.add(getOK());
		}
		return buttonPanel;
	}
	private JPanel getColorPanel() {
		if (colorPanel == null) {
			colorPanel = new JPanel();
			GridBagLayout gbl_colorPanel = new GridBagLayout();
			gbl_colorPanel.columnWidths = new int[]{0, 0};
			gbl_colorPanel.rowHeights = new int[]{0, 0, 0};
			gbl_colorPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_colorPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			colorPanel.setLayout(gbl_colorPanel);
			GridBagConstraints gbc_labelTextAndObject = new GridBagConstraints();
			gbc_labelTextAndObject.insets = new Insets(0, 0, 5, 0);
			gbc_labelTextAndObject.gridx = 0;
			gbc_labelTextAndObject.gridy = 0;
			colorPanel.add(getLabelTextAndObject(), gbc_labelTextAndObject);
			GridBagConstraints gbc_colorPanel2 = new GridBagConstraints();
			gbc_colorPanel2.fill = GridBagConstraints.BOTH;
			gbc_colorPanel2.insets = new Insets(0, 12, 10, 12);
			gbc_colorPanel2.gridx = 0;
			gbc_colorPanel2.gridy = 1;
			colorPanel.add(getColorPanel2(), gbc_colorPanel2);
		}
		return colorPanel;
	}
	private JPanel getGeneralPanel() {
		if (generalPanel == null) {
			generalPanel = new JPanel();
			GridBagLayout gbl_generalPanel = new GridBagLayout();
			gbl_generalPanel.columnWidths = new int[]{100, 100, 100, 100, 0, 0};
			gbl_generalPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_generalPanel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_generalPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			generalPanel.setLayout(gbl_generalPanel);
			GridBagConstraints gbc_labelDefaultFileSaving = new GridBagConstraints();
			gbc_labelDefaultFileSaving.insets = new Insets(0, 0, 5, 0);
			gbc_labelDefaultFileSaving.gridwidth = 5;
			gbc_labelDefaultFileSaving.gridx = 0;
			gbc_labelDefaultFileSaving.gridy = 0;
			generalPanel.add(getLabelDefaultFileSaving(), gbc_labelDefaultFileSaving);
			GridBagConstraints gbc_textFieldDirectoryPath = new GridBagConstraints();
			gbc_textFieldDirectoryPath.gridwidth = 4;
			gbc_textFieldDirectoryPath.insets = new Insets(0, 10, 5, 5);
			gbc_textFieldDirectoryPath.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFieldDirectoryPath.gridx = 0;
			gbc_textFieldDirectoryPath.gridy = 1;
			generalPanel.add(getTextFieldDirectoryPath(), gbc_textFieldDirectoryPath);
			GridBagConstraints gbc_buttonDirectoryPath = new GridBagConstraints();
			gbc_buttonDirectoryPath.insets = new Insets(0, 0, 5, 10);
			gbc_buttonDirectoryPath.gridx = 4;
			gbc_buttonDirectoryPath.gridy = 1;
			generalPanel.add(getButtonDierectoryPath(), gbc_buttonDirectoryPath);
			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.fill = GridBagConstraints.BOTH;
			gbc_panel_2.gridwidth = 5;
			gbc_panel_2.insets = new Insets(0, 10, 5, 10);
			gbc_panel_2.gridx = 0;
			gbc_panel_2.gridy = 2;
			generalPanel.add(getPanel_2(), gbc_panel_2);
			GridBagConstraints gbc_labelDefaultSurfaceRange = new GridBagConstraints();
			gbc_labelDefaultSurfaceRange.gridwidth = 5;
			gbc_labelDefaultSurfaceRange.insets = new Insets(0, 0, 5, 0);
			gbc_labelDefaultSurfaceRange.gridx = 0;
			gbc_labelDefaultSurfaceRange.gridy = 3;
			generalPanel.add(getLabelDefaultSurfaceRange(), gbc_labelDefaultSurfaceRange);
			GridBagConstraints gbc_rdbtnAustinPowder = new GridBagConstraints();
			gbc_rdbtnAustinPowder.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnAustinPowder.gridx = 0;
			gbc_rdbtnAustinPowder.gridy = 4;
			generalPanel.add(getRdbtnAustinPowder(), gbc_rdbtnAustinPowder);
			GridBagConstraints gbc_rdbtnDynoNobelAp = new GridBagConstraints();
			gbc_rdbtnDynoNobelAp.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnDynoNobelAp.gridx = 1;
			gbc_rdbtnDynoNobelAp.gridy = 4;
			generalPanel.add(getRdbtnDynoNobelAp(), gbc_rdbtnDynoNobelAp);
			GridBagConstraints gbc_rdbtnMaxamAus = new GridBagConstraints();
			gbc_rdbtnMaxamAus.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnMaxamAus.gridx = 2;
			gbc_rdbtnMaxamAus.gridy = 4;
			generalPanel.add(getRdbtnMaxamAus(), gbc_rdbtnMaxamAus);
			GridBagConstraints gbc_rdbtnOricaAustralia = new GridBagConstraints();
			gbc_rdbtnOricaAustralia.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnOricaAustralia.gridx = 3;
			gbc_rdbtnOricaAustralia.gridy = 4;
			generalPanel.add(getRdbtnOricaAustralia(), gbc_rdbtnOricaAustralia);
			GridBagConstraints gbc_rdbtnCustom = new GridBagConstraints();
			gbc_rdbtnCustom.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnCustom.gridx = 4;
			gbc_rdbtnCustom.gridy = 4;
			generalPanel.add(getRdbtnCustom(), gbc_rdbtnCustom);
			GridBagConstraints gbc_SurfaceConnectionPalette = new GridBagConstraints();
			gbc_SurfaceConnectionPalette.fill = GridBagConstraints.BOTH;
			gbc_SurfaceConnectionPalette.insets = new Insets(0, 10, 10, 10);
			gbc_SurfaceConnectionPalette.gridwidth = 5;
			gbc_SurfaceConnectionPalette.gridx = 0;
			gbc_SurfaceConnectionPalette.gridy = 5;
			generalPanel.add(getSurfaceConnectionPalette(), gbc_SurfaceConnectionPalette);
		}
		return generalPanel;
	}
	private JLabel getLabelBackgroundCanvas() {
		if (labelBackgroundCanvas == null) {
			labelBackgroundCanvas = new JLabel("Background Canvas");
		}
		return labelBackgroundCanvas;
	}
	private JPanel getPanelCanvasColor() {
		if (wellCanvasColor == null) {
			wellCanvasColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellCanvasColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellCanvasColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellCanvasColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellCanvasColor.setPreferredSize(new Dimension(22, 22));
			wellCanvasColor.setBackground(Color.WHITE);
			wellCanvasColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelCanvasColor().getBackground());
					if (currentColor != null) {
						getPanelCanvasColor().setBackground(currentColor);
						p_backgroundCanvasColour = (wellCanvasColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellCanvasColor;
	}
	private JLabel getLabelGridLinesAnd() {
		if (labelGridLinesAnd == null) {
			labelGridLinesAnd = new JLabel("Grid Lines and Text");
		}
		return labelGridLinesAnd;
	}
	private JPanel getPanelGridLineColor() {
		if (wellGridLineColor == null) {
			wellGridLineColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellGridLineColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellGridLineColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellGridLineColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellGridLineColor.setPreferredSize(new Dimension(22, 22));
			wellGridLineColor.setBackground(Color.WHITE);
			wellGridLineColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelGridLineColor().getBackground());
					if (currentColor != null) {
						getPanelGridLineColor().setBackground(currentColor);
						p_backgroundCanvasColour = (wellGridLineColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellGridLineColor;
	}
	private JPanel getPanelHoleIDColor() {
		if (wellHoleIDColor == null) {
			wellHoleIDColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellHoleIDColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellHoleIDColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellHoleIDColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellHoleIDColor.setPreferredSize(new Dimension(22, 22));
			wellHoleIDColor.setBackground(Color.WHITE);
			wellHoleIDColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelHoleIDColor().getBackground());
					if (currentColor != null) {
						getPanelHoleIDColor().setBackground(currentColor);
						p_holeTextColour = (wellHoleIDColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellHoleIDColor;
	}
	private JPanel getPanelHoleLengthColor() {
		if (wellHoleLengthColor == null) {
			wellHoleLengthColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellHoleLengthColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellHoleLengthColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellHoleLengthColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellHoleLengthColor.setPreferredSize(new Dimension(22, 22));
			wellHoleLengthColor.setBackground(Color.WHITE);
			wellHoleLengthColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelHoleLengthColor().getBackground());
					if (currentColor != null) {
						getPanelHoleLengthColor().setBackground(currentColor);
						p_holeLengthTextColour = (wellHoleLengthColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});

		}
		return wellHoleLengthColor;
	}
	private JPanel getPanelLabelOneColor() {
		if (wellLabelOneColor == null) {
			wellLabelOneColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellLabelOneColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellLabelOneColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellLabelOneColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellLabelOneColor.setPreferredSize(new Dimension(22, 22));
			wellLabelOneColor.setBackground(Color.WHITE);
			wellLabelOneColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelLabelOneColor().getBackground());
					if (currentColor != null) {
						getPanelLabelOneColor().setBackground(currentColor);
						p_labelOneTextColour = (wellLabelOneColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellLabelOneColor;
	}
	private JPanel getPanelLabelTwoColor() {
		if (wellLabelTwoColor == null) {
			wellLabelTwoColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellLabelTwoColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellLabelTwoColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellLabelTwoColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellLabelTwoColor.setPreferredSize(new Dimension(22, 22));
			wellLabelTwoColor.setBackground(Color.WHITE);
			wellLabelTwoColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelLabelTwoColor().getBackground());
					if (currentColor != null) {
						getPanelLabelTwoColor().setBackground(currentColor);
						p_labelTwoTextColour = (wellLabelTwoColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellLabelTwoColor;
	}
	private JPanel getPanelLabelThreeColor() {
		if (wellLabelThreeColor == null) {
			wellLabelThreeColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellLabelThreeColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellLabelThreeColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellLabelThreeColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellLabelThreeColor.setPreferredSize(new Dimension(22, 22));
			wellLabelThreeColor.setBackground(Color.WHITE);
			wellLabelThreeColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelLabelThreeColor().getBackground());
					if (currentColor != null) {
						getPanelLabelThreeColor().setBackground(currentColor);
						p_labelThreeTextColour = (wellLabelThreeColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellLabelThreeColor;
	}
	private JLabel getLabelHoleIdText() {
		if (labelHoleIdText == null) {
			labelHoleIdText = new JLabel("Hole ID Text");
		}
		return labelHoleIdText;
	}
	private JLabel getLabelHoleLengthText() {
		if (labelHoleLengthText == null) {
			labelHoleLengthText = new JLabel("Hole Length Text");
		}
		return labelHoleLengthText;
	}
	private JLabel getLabelLabelOneText() {
		if (labelLabelOneText == null) {
			labelLabelOneText = new JLabel("Label One Text");
		}
		return labelLabelOneText;
	}
	private JLabel getLabelLabelTwoText() {
		if (labelLabelTwoText == null) {
			labelLabelTwoText = new JLabel("Label Two Text");
		}
		return labelLabelTwoText;
	}
	private JLabel getLabelLabelThreeText() {
		if (labelLabelThreeText == null) {
			labelLabelThreeText = new JLabel("Label Three Text");
		}
		return labelLabelThreeText;
	}
	private JLabel getLabelBenchHeightText() {
		if (labelBenchHeightText == null) {
			labelBenchHeightText = new JLabel("Bench Height Text");
		}
		return labelBenchHeightText;
	}
	private JLabel getLabelSubdrillAmountText() {
		if (labelSubdrillAmountText == null) {
			labelSubdrillAmountText = new JLabel("Subdrill Amount Text");
		}
		return labelSubdrillAmountText;
	}
	private JLabel getLabelHoleDiameterText() {
		if (labelHoleDiameterText == null) {
			labelHoleDiameterText = new JLabel("Hole Diameter Text");
		}
		return labelHoleDiameterText;
	}
	
	private JPanel getPanelBenchHeightColor() {
		if (wellBenchHeightColor == null) {
			wellBenchHeightColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellBenchHeightColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellBenchHeightColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellBenchHeightColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellBenchHeightColor.setPreferredSize(new Dimension(22, 22));
			wellBenchHeightColor.setBackground(Color.WHITE);
			wellBenchHeightColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelBenchHeightColor().getBackground());
					if (currentColor != null) {
						getPanelBenchHeightColor().setBackground(currentColor);
						p_benchHeightTextColour = (wellBenchHeightColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellBenchHeightColor;
	}
	private JPanel getPanelSubdrillColor() {
		if (wellSubdrillColor == null) {
			wellSubdrillColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellSubdrillColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellSubdrillColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellSubdrillColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellSubdrillColor.setPreferredSize(new Dimension(22, 22));
			wellSubdrillColor.setBackground(Color.WHITE);
			wellSubdrillColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelSubdrillColor().getBackground());
					if (currentColor != null) {
						getPanelSubdrillColor().setBackground(currentColor);
						p_subdrillTextColour = (wellSubdrillColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellSubdrillColor;
	}
	private JPanel getPanelHoleDiameterColor() {
		if (wellHoleDiameterColor == null) {
			wellHoleDiameterColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellHoleDiameterColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellHoleDiameterColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellHoleDiameterColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellHoleDiameterColor.setPreferredSize(new Dimension(22, 22));
			wellHoleDiameterColor.setBackground(Color.WHITE);
			wellHoleDiameterColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelHoleDiameterColor().getBackground());
					if (currentColor != null) {
						getPanelHoleDiameterColor().setBackground(currentColor);
						p_holeDiameterTextColour = (wellHoleDiameterColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellHoleDiameterColor;
	}
	private JPanel getPanelHoleBearingColor() {
		if (wellHoleBearingColor == null) {
			wellHoleBearingColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellHoleBearingColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellHoleBearingColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellHoleBearingColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellHoleBearingColor.setPreferredSize(new Dimension(22, 22));
			wellHoleBearingColor.setBackground(Color.WHITE);
			wellHoleBearingColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelHoleBearingColor().getBackground());
					if (currentColor != null) {
						getPanelHoleBearingColor().setBackground(currentColor);
						p_holeBearingTextColour = (wellHoleBearingColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellHoleBearingColor;
	}
	private JPanel getPanelFloorLocFillColor() {
		if (wellFloorLocFillColor == null) {
			wellFloorLocFillColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellFloorLocFillColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellFloorLocFillColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellFloorLocFillColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellFloorLocFillColor.setPreferredSize(new Dimension(22, 22));
			wellFloorLocFillColor.setBackground(Color.WHITE);
			wellFloorLocFillColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelFloorLocFillColor().getBackground());
					if (currentColor != null) {
						getPanelFloorLocFillColor().setBackground(currentColor);
						p_floorLocationFillColour = (wellFloorLocFillColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellFloorLocFillColor;
	}
	private JLabel getLabelHoleBearingText() {
		if (labelHoleBearingText == null) {
			labelHoleBearingText = new JLabel("Hole Bearing Text");
		}
		return labelHoleBearingText;
	}
	private JLabel getLabelHoleTrackLine() {
		if (labelHoleTrackLine == null) {
			labelHoleTrackLine = new JLabel("Floor Location Fill");
		}
		return labelHoleTrackLine;
	}
	private JPanel getPanelFloorLineColor() {
		if (wellFloorLineColor == null) {
			wellFloorLineColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellFloorLineColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellFloorLineColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellFloorLineColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellFloorLineColor.setPreferredSize(new Dimension(22, 22));
			wellFloorLineColor.setBackground(Color.WHITE);
			wellFloorLineColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelFloorLineColor().getBackground());
					if (currentColor != null) {
						getPanelFloorLineColor().setBackground(currentColor);
						p_floorLocationLineColour = (wellFloorLineColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellFloorLineColor;
	}
	private JLabel getLabelFloorCircleLocation() {
		if (labelFloorCircleLocation == null) {
			labelFloorCircleLocation = new JLabel("Floor Location Line");
		}
		return labelFloorCircleLocation;
	}
	private JLabel getLabelToeCircleLocation() {
		if (labelToeCircleLocation == null) {
			labelToeCircleLocation = new JLabel("Toe Location Fill");
		}
		return labelToeCircleLocation;
	}
	private JLabel getLabelToeLocationLine() {
		if (labelToeLocationLine == null) {
			labelToeLocationLine = new JLabel("Toe Location Line");
		}
		return labelToeLocationLine;
	}
	private JPanel getPanelToeLocFillColor() {
		if (wellToeLocFillColor == null) {
			wellToeLocFillColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellToeLocFillColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellToeLocFillColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellToeLocFillColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellToeLocFillColor.setPreferredSize(new Dimension(22, 22));
			wellToeLocFillColor.setBackground(Color.WHITE);
			wellToeLocFillColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelToeLocFillColor().getBackground());
					if (currentColor != null) {
						getPanelToeLocFillColor().setBackground(currentColor);
						p_toeLocationFillColour = (wellToeLocFillColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellToeLocFillColor;
	}
	private JPanel getPanelToeLocLineColor() {
		if (wellToeLocLineColor == null) {
			wellToeLocLineColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellToeLocLineColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellToeLocLineColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellToeLocLineColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellToeLocLineColor.setPreferredSize(new Dimension(22, 22));
			wellToeLocLineColor.setBackground(Color.WHITE);
			wellToeLocLineColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelToeLocLineColor().getBackground());
					if (currentColor != null) {
						getPanelToeLocLineColor().setBackground(currentColor);
						p_toeLocationLineColour = (wellToeLocLineColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellToeLocLineColor;
	}
	private JPanel getPanelCollarLocColor() {
		if (wellCollarLocColor == null) {
			wellCollarLocColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellCollarLocColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellCollarLocColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellCollarLocColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellCollarLocColor.setPreferredSize(new Dimension(22, 22));
			wellCollarLocColor.setBackground(Color.WHITE);
			wellCollarLocColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelCollarLocColor().getBackground());
					if (currentColor != null) {
						getPanelCollarLocColor().setBackground(currentColor);
						p_collarLocationTextColour = (wellCollarLocColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellCollarLocColor;
	}
	private JPanel getPanelFloorLocTextColor() {
		if (wellFloorLocTextColor == null) {
			wellFloorLocTextColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellFloorLocTextColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellFloorLocTextColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellFloorLocTextColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellFloorLocTextColor.setPreferredSize(new Dimension(22, 22));
			wellFloorLocTextColor.setBackground(Color.WHITE);
			wellFloorLocTextColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelFloorLocTextColor().getBackground());
					if (currentColor != null) {
						getPanelFloorLocTextColor().setBackground(currentColor);
						p_floorLocationTextColour = (wellFloorLocTextColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellFloorLocTextColor;
	}
	private JPanel getPanelToeLocTextColor() {
		if (wellToeLocTextColor == null) {
			wellToeLocTextColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellToeLocTextColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellToeLocTextColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellToeLocTextColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellToeLocTextColor.setPreferredSize(new Dimension(22, 22));
			wellToeLocTextColor.setBackground(Color.WHITE);
			wellToeLocTextColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelToeLocTextColor().getBackground());
					if (currentColor != null) {
						getPanelToeLocTextColor().setBackground(currentColor);
						p_toeLocationTextColour = (wellToeLocTextColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellToeLocTextColor;
	}
	private JLabel getLabelCollarLocationText() {
		if (labelCollarLocationText == null) {
			labelCollarLocationText = new JLabel("Collar Location Text");
		}
		return labelCollarLocationText;
	}
	private JLabel getLabelFloorLocationText() {
		if (labelFloorLocationText == null) {
			labelFloorLocationText = new JLabel("Floor Location Text");
		}
		return labelFloorLocationText;
	}
	private JLabel getLabelToeLocationText() {
		if (labelToeLocationText == null) {
			labelToeLocationText = new JLabel("Toe Location Text");
		}
		return labelToeLocationText;
	}
	private JButton getButtonDierectoryPath() {
		if (buttonDirectoryPath == null) {
			buttonDirectoryPath = new JButton("Browse");
			buttonDirectoryPath.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser chooser = new JFileChooser(new File("/Users"));
					chooser.setFileSelectionMode(1);
					chooser.setMultiSelectionEnabled(false);
					chooser.setCurrentDirectory(new File("/Users"));
					chooser.showDialog(DialogPreferences.this, "Browse");
					getTextFieldDirectoryPath().setText(chooser.getSelectedFile().getAbsolutePath().toString());
				}
			});
		}
		return buttonDirectoryPath;
	}
	private JTextField getTextFieldDirectoryPath() {
		if (textFieldDirectoryPath == null) {
			textFieldDirectoryPath = new JTextField();
			textFieldDirectoryPath.setPreferredSize(new Dimension(100, 22));
			textFieldDirectoryPath.setText("c://users/");
			textFieldDirectoryPath.setColumns(10);
		}
		return textFieldDirectoryPath;
	}
	private JLabel getLabelDefaultFileSaving() {
		if (labelDefaultFileSaving == null) {
			labelDefaultFileSaving = new JLabel("Default File Saving Location");
		}
		return labelDefaultFileSaving;
	}
	private JPanel getRegistrationPanel() {
		if (registrationPanel == null) {
			registrationPanel = new JPanel();
			sl_registrationPanel = new SpringLayout();
			registrationPanel.setLayout(sl_registrationPanel);
			registrationPanel.add(getLabelRegistrationCode());
			registrationPanel.add(getTextFieldReg1());
			registrationPanel.add(getLabelUserName());
			registrationPanel.add(getTextFieldUserName());
		}
		return registrationPanel;
	}
	private JLabel getLabelRegistrationCode() {
		if (labelRegistrationCode == null) {
			labelRegistrationCode = new JLabel("Registration Code");
			sl_registrationPanel.putConstraint(SpringLayout.WEST, labelRegistrationCode, 289, SpringLayout.WEST, getRegistrationPanel());
			sl_registrationPanel.putConstraint(SpringLayout.SOUTH, labelRegistrationCode, -256, SpringLayout.SOUTH, getRegistrationPanel());
		}
		return labelRegistrationCode;
	}
	private JTextField getTextFieldReg1() {
		if (textFieldReg1 == null) {
			textFieldReg1 = new JTextField();
			sl_registrationPanel.putConstraint(SpringLayout.NORTH, textFieldReg1, 6, SpringLayout.SOUTH, getLabelRegistrationCode());
			sl_registrationPanel.putConstraint(SpringLayout.WEST, textFieldReg1, 63, SpringLayout.WEST, getRegistrationPanel());
			sl_registrationPanel.putConstraint(SpringLayout.EAST, textFieldReg1, -64, SpringLayout.EAST, getRegistrationPanel());
			textFieldReg1.setColumns(10);
		}
		return textFieldReg1;
	}
	private JLabel getLabelUserName() {
		if (labelUserName == null) {
			labelUserName = new JLabel("User Name");
			sl_registrationPanel.putConstraint(SpringLayout.SOUTH, labelUserName, -172, SpringLayout.SOUTH, getRegistrationPanel());
			sl_registrationPanel.putConstraint(SpringLayout.SOUTH, getTextFieldReg1(), -28, SpringLayout.NORTH, labelUserName);
			sl_registrationPanel.putConstraint(SpringLayout.WEST, labelUserName, 311, SpringLayout.WEST, getRegistrationPanel());
		}
		return labelUserName;
	}
	private JTextField getTextFieldUserName() {
		if (textFieldUserName == null) {
			textFieldUserName = new JTextField();
			sl_registrationPanel.putConstraint(SpringLayout.NORTH, textFieldUserName, 6, SpringLayout.SOUTH, getLabelUserName());
			sl_registrationPanel.putConstraint(SpringLayout.WEST, textFieldUserName, 63, SpringLayout.WEST, getRegistrationPanel());
			sl_registrationPanel.putConstraint(SpringLayout.SOUTH, textFieldUserName, -131, SpringLayout.SOUTH, getRegistrationPanel());
			sl_registrationPanel.putConstraint(SpringLayout.EAST, textFieldUserName, -64, SpringLayout.EAST, getRegistrationPanel());
			textFieldUserName.setColumns(10);
		}
		return textFieldUserName;
	}
	private JSpinner getSpinnerHoleEnhancement() {
		if (spinnerHoleEnhancement == null) {
			spinnerHoleEnhancement = new JSpinner(spinnerModelHoleFactor);
			spinnerHoleEnhancement.setPreferredSize(new Dimension(80, 22));
		}
		return spinnerHoleEnhancement;
	}
	private JLabel getLabelHoleSymbolStartup() {
		if (labelHoleSymbolStartup == null) {
			labelHoleSymbolStartup = new JLabel("Hole Symbol Startup Enhancement Factor");
		}
		return labelHoleSymbolStartup;
	}
	private JSpinner getSpinnerMarkerEnhancement() {
		if (spinnerMarkerEnhancement == null) {
			spinnerMarkerEnhancement = new JSpinner(spinnerModelMarkerFactor);
			spinnerMarkerEnhancement.setPreferredSize(new Dimension(80, 22));
		}
		return spinnerMarkerEnhancement;
	}
	private JSpinner getSpinnerSurfaceConnectorEnhancement() {
		if (spinnerSurfaceConnectorEnhancement == null) {
			spinnerSurfaceConnectorEnhancement = new JSpinner(spinnerModelSurfaceFactor);
			spinnerSurfaceConnectorEnhancement.setPreferredSize(new Dimension(80, 22));
		}
		return spinnerSurfaceConnectorEnhancement;
	}
	private JLabel getLabelFloorAndToe() {
		if (labelFloorAndToe == null) {
			labelFloorAndToe = new JLabel("Floor and Toe Marker Startup Enhancement Factor");
		}
		return labelFloorAndToe;
	}
	private JLabel getLabelSurfaceConnectorStartup() {
		if (labelSurfaceConnectorStartup == null) {
			labelSurfaceConnectorStartup = new JLabel("Surface Connector Startup Enhancement Factor");
		}
		return labelSurfaceConnectorStartup;
	}
/**
 * @since 28-1-2014  
 * @return
 */
	private JRadioButton getRdbtnAustinPowder() {
		if (rdbtnAustinPowder == null) {
			rdbtnAustinPowder = new JRadioButton("Austin");
			buttonGroup.add(rdbtnAustinPowder);
			rdbtnAustinPowder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getRdbtnAustinPowder().setSelected(true);
					getRdbtnDynoNobelAp().setSelected(false);
					getRdbtnMaxamAus().setSelected(false);
					getRdbtnOricaAustralia().setSelected(false);
					getRdbtnCustom().setSelected(false);
					getTextFieldTieOne().setBackground(austinGreen);
					getTextFieldTieOne().setText("9");				
					getTextFieldTieTwo().setBackground(austinYellow);
					getTextFieldTieTwo().setText("17");				
					getTextFieldTieThree().setBackground(austinRed);	
					getTextFieldTieThree().setText("25");			
					getTextFieldTieFour().setBackground(austinOrange);
					getTextFieldTieFour().setText("33");				
					getTextFieldTieFive().setBackground(austinWhite);
					getTextFieldTieFive().setText("42");				
					getTextFieldTieSix().setBackground(austinBlue);	
					getTextFieldTieSix().setText("67");
				
				
				
				}
			});
		}
		return rdbtnAustinPowder;
	}
	
	
	private JRadioButton getRdbtnDynoNobelAp() {
		if (rdbtnDynoNobelAp == null) {
			rdbtnDynoNobelAp = new JRadioButton("Dyno");
			buttonGroup.add(rdbtnDynoNobelAp);
//			rdbtnDynoNobelAp.setIcon(australia);
			rdbtnDynoNobelAp.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getRdbtnAustinPowder().setSelected(false);
					getRdbtnDynoNobelAp().setSelected(true);
					getRdbtnMaxamAus().setSelected(false);
					getRdbtnOricaAustralia().setSelected(false);
					getRdbtnCustom().setSelected(false);
					getTextFieldTieOne().setBackground(dynoPurple);
					getTextFieldTieOne().setText("9");
					getTextFieldTieTwo().setBackground(dynoYellow);
					getTextFieldTieTwo().setText("17");
					getTextFieldTieThree().setBackground(dynoRed);
					getTextFieldTieThree().setText("25");
					getTextFieldTieFour().setBackground(dynoWhite);
					getTextFieldTieFour().setText("42");
					getTextFieldTieFive().setBackground(dynoBlue);
					getTextFieldTieFive().setText("67");
					getTextFieldTieSix().setBackground(dynoBlack);
					getTextFieldTieSix().setText("109");
				}
			});
		}
		return rdbtnDynoNobelAp;
	}
	private JRadioButton getRdbtnOricaAustralia() {
		if (rdbtnOricaAustralia == null) {
			rdbtnOricaAustralia = new JRadioButton("Orica");
			buttonGroup.add(rdbtnOricaAustralia);
//			rdbtnOricaAustralia.setIcon(australia);
			rdbtnOricaAustralia.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getRdbtnAustinPowder().setSelected(false);
					getRdbtnDynoNobelAp().setSelected(false);
					getRdbtnMaxamAus().setSelected(false);
					getRdbtnOricaAustralia().setSelected(true);
					getRdbtnCustom().setSelected(false);
					getTextFieldTieOne().setBackground(oricaGreen);
					getTextFieldTieOne().setText("9");
					getTextFieldTieTwo().setBackground(oricaYellow);
					getTextFieldTieTwo().setText("17");
					getTextFieldTieThree().setBackground(oricaRed);
					getTextFieldTieThree().setText("25");
					getTextFieldTieFour().setBackground(oricaWhite);
					getTextFieldTieFour().setText("42");
					getTextFieldTieFive().setBackground(oricaBlue);
					getTextFieldTieFive().setText("65");
					getTextFieldTieSix().setBackground(oricaOrange);
					getTextFieldTieSix().setText("100");
				}
			});
		}
		return rdbtnOricaAustralia;
	}
	private JRadioButton getRdbtnMaxamAus() {
		if (rdbtnMaxamAus == null) {
			rdbtnMaxamAus = new JRadioButton("Maxam");
			buttonGroup.add(rdbtnMaxamAus);
//			rdbtnMaxamAus.setIcon(australia);
			rdbtnMaxamAus.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getRdbtnAustinPowder().setSelected(false);
					getRdbtnDynoNobelAp().setSelected(false);
					getRdbtnMaxamAus().setSelected(true);
					getRdbtnOricaAustralia().setSelected(false);
					getRdbtnCustom().setSelected(false);
					getTextFieldTieOne().setBackground(maxamGreen);
					getTextFieldTieOne().setText("9");
					getTextFieldTieTwo().setBackground(maxamYellow);
					getTextFieldTieTwo().setText("17");
					getTextFieldTieThree().setBackground(maxamRed);
					getTextFieldTieThree().setText("25");
					getTextFieldTieFour().setBackground(maxamWhite);
					getTextFieldTieFour().setText("42");
					getTextFieldTieFive().setBackground(maxamBlack);
					getTextFieldTieFive().setText("67");
					getTextFieldTieSix().setBackground(maxamBlue);
					getTextFieldTieSix().setText("109");
				}
			});
		}
		return rdbtnMaxamAus;
	}
	private JRadioButton getRdbtnCustom() {
		if (rdbtnCustom == null) {
			rdbtnCustom = new JRadioButton("Custom");
			rdbtnCustom.setSelected(true);
			buttonGroup.add(rdbtnCustom);
			rdbtnAustinPowder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getRdbtnAustinPowder().setSelected(true);
					getRdbtnDynoNobelAp().setSelected(false);
					getRdbtnMaxamAus().setSelected(false);
					getRdbtnOricaAustralia().setSelected(false);
					getRdbtnCustom().setSelected(false);
					try {
						Scanner readPrefs = new Scanner(new File("LiteTiePreferences.ltpf"));
						int i = 1;

						getTextFieldDirectoryPath().setText(readPrefs.nextLine());//1
						getSpinnerHoleEnhancement().setValue(Float.parseFloat(readPrefs.nextLine()));//2
						getSpinnerMarkerEnhancement().setValue(Float.parseFloat(readPrefs.nextLine()));//3
						getSpinnerSurfaceConnectorEnhancement().setValue(Float.parseFloat(readPrefs.nextLine()));//4
						String supplier = readPrefs.nextLine();
						getTextFieldTieOne().setBackground(Color.decode(readPrefs.nextLine()));//6
						getTextFieldTieOne().setText(readPrefs.nextLine());//7
						getTextFieldTieTwo().setBackground(Color.decode(readPrefs.nextLine()));//8
						getTextFieldTieTwo().setText(readPrefs.nextLine());//9
						getTextFieldTieThree().setBackground(Color.decode(readPrefs.nextLine()));//10
						getTextFieldTieThree().setText(readPrefs.nextLine());//11
						getTextFieldTieFour().setBackground(Color.decode(readPrefs.nextLine()));//12
						getTextFieldTieFour().setText(readPrefs.nextLine());//13
						getTextFieldTieFive().setBackground(Color.decode(readPrefs.nextLine()));//14
						getTextFieldTieFive().setText(readPrefs.nextLine());//15
						getTextFieldTieSix().setBackground(Color.decode(readPrefs.nextLine()));//16
						getTextFieldTieSix().setText(readPrefs.nextLine());//17
						getTextFieldDownHoleOne().setBackground(Color.decode(readPrefs.nextLine()));//18
						getTextFieldDownHoleOne().setText(readPrefs.nextLine());//19
						getTextFieldDownHoleTwo().setBackground(Color.decode(readPrefs.nextLine()));//20
						getTextFieldDownHoleTwo().setText(readPrefs.nextLine());//21
						getTextFieldDownHoleThree().setBackground(Color.decode(readPrefs.nextLine()));//22
						getTextFieldDownHoleThree().setText(readPrefs.nextLine());//23
					}
					catch (FileNotFoundException e1) {
						e1.printStackTrace();
						
						System.out.println("Caught FileNotFoundException - showBox()");
					}
					catch (NoSuchElementException e1) {
						e1.printStackTrace();
						
						System.out.println("Caught NoSuchElementException - showBox()");
					}
					
					}
			});
		}
		return rdbtnCustom;
	}
	private JLabel getLabelDefaultSurfaceRange() {
		if (labelDefaultSurfaceRange == null) {
			labelDefaultSurfaceRange = new JLabel("Default Surface Range");
		}
		return labelDefaultSurfaceRange;
	}
	private JTextField getTextFieldTieOne() {
		if (textFieldTieOne == null) {
			textFieldTieOne = new JTextField();
			textFieldTieOne.setBackground(new Color(0, 128, 0));
			textFieldTieOne.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTieOne.setPreferredSize(new Dimension(80, 22));
			textFieldTieOne.setText("9");
			textFieldTieOne.setColumns(10);
			textFieldTieOne.setInputVerifier(new InputVerifier(){
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTextFieldTieOne().getText());
						if (i < 0){
							return false;
						}
						return true;
					}
					catch (IllegalArgumentException e){
						return false;
					}// TODO Auto-generated method stub
					//return true;

				}
			});
			textFieldTieOne.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldTieOne.getBackground().getRed()+textFieldTieOne.getBackground().getGreen()+textFieldTieOne.getBackground().getBlue())/3 < 130) {
						getTextFieldTieOne().setForeground(Color.white);
					}
					else
						getTextFieldTieOne().setForeground(Color.black);
				}
			});
		}
		return textFieldTieOne;
	}
	private JTextField getTextFieldTieTwo() {
		if (textFieldTieTwo == null) {
			textFieldTieTwo = new JTextField();
			textFieldTieTwo.setBackground(new Color(255, 255, 0));
			textFieldTieTwo.setText("17");
			textFieldTieTwo.setPreferredSize(new Dimension(80, 22));
			textFieldTieTwo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTieTwo.setColumns(10);
			textFieldTieTwo.setInputVerifier(new InputVerifier(){
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTextFieldTieTwo().getText());
						if (i < 0){
							return false;
						}
						return true;
					}
					catch (IllegalArgumentException e){
						return false;
					}// TODO Auto-generated method stub
					//return true;

				}
			});
			textFieldTieTwo.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldTieTwo.getBackground().getRed()+textFieldTieTwo.getBackground().getGreen()+textFieldTieTwo.getBackground().getBlue())/3 < 130) {
						getTextFieldTieTwo().setForeground(Color.white);
					}
					else
						getTextFieldTieTwo().setForeground(Color.black);
				}
			});
		}
		
		return textFieldTieTwo;
	}
	private JTextField getTextFieldTieThree() {
		if (textFieldTieThree == null) {
			textFieldTieThree = new JTextField();
			textFieldTieThree.setBackground(new Color(255, 0, 0));
			textFieldTieThree.setText("25");
			textFieldTieThree.setPreferredSize(new Dimension(80, 22));
			textFieldTieThree.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTieThree.setColumns(10);
			textFieldTieThree.setInputVerifier(new InputVerifier(){
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTextFieldTieThree().getText());
						if (i < 0){
							return false;
						}
						return true;
					}
					catch (IllegalArgumentException e){
						return false;
					}// TODO Auto-generated method stub
					//return true;

				}
			});
			textFieldTieThree.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldTieThree.getBackground().getRed()+textFieldTieThree.getBackground().getGreen()+textFieldTieThree.getBackground().getBlue())/3 < 130) {
						getTextFieldTieThree().setForeground(Color.white);
					}
					else
						getTextFieldTieThree().setForeground(Color.black);
				}
			});
		}
		return textFieldTieThree;
	}
	private JTextField getTextFieldTieFour() {
		if (textFieldTieFour == null) {
			textFieldTieFour = new JTextField();
			textFieldTieFour.setBackground(new Color(128, 128, 128));
			textFieldTieFour.setText("42");
			textFieldTieFour.setPreferredSize(new Dimension(80, 22));
			textFieldTieFour.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTieFour.setColumns(10);
			textFieldTieFour.setInputVerifier(new InputVerifier(){
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTextFieldTieFour().getText());
						if (i < 0){
							return false;
						}
						return true;
					}
					catch (IllegalArgumentException e){
						return false;
					}// TODO Auto-generated method stub
					//return true;

				}
			});
			textFieldTieFour.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldTieFour.getBackground().getRed()+textFieldTieFour.getBackground().getGreen()+textFieldTieFour.getBackground().getBlue())/3 < 130) {
						getTextFieldTieFour().setForeground(Color.white);
					}
					else
						getTextFieldTieFour().setForeground(Color.black);
				}
			});
		}
		return textFieldTieFour;
	}
	private JTextField getTextFieldTieFive() {
		if (textFieldTieFive == null) {
			textFieldTieFive = new JTextField();
			textFieldTieFive.setBackground(new Color(30, 144, 255));
			textFieldTieFive.setText("67");
			textFieldTieFive.setPreferredSize(new Dimension(80, 22));
			textFieldTieFive.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTieFive.setColumns(10);
			textFieldTieFive.setInputVerifier(new InputVerifier(){
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTextFieldTieFive().getText());
						if (i < 0){
							return false;
						}
						return true;
					}
					catch (IllegalArgumentException e){
						return false;
					}// TODO Auto-generated method stub
					//return true;

				}
			});
			textFieldTieFive.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldTieFive.getBackground().getRed()+textFieldTieFive.getBackground().getGreen()+textFieldTieFive.getBackground().getBlue())/3 < 130) {
						getTextFieldTieFive().setForeground(Color.white);
					}
					else
						getTextFieldTieFive().setForeground(Color.black);
				}
			});
		}
		return textFieldTieFive;
	}
	private JTextField getTextFieldTieSix() {
		if (textFieldTieSix == null) {
			textFieldTieSix = new JTextField();
			textFieldTieSix.setBackground(new Color(138, 43, 226));
			textFieldTieSix.setText("109");
			textFieldTieSix.setPreferredSize(new Dimension(80, 22));
			textFieldTieSix.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTieSix.setColumns(10);
			textFieldTieSix.setInputVerifier(new InputVerifier(){
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTextFieldTieSix().getText());
						if (i < 0){
							return false;
						}
						return true;
					}
					catch (IllegalArgumentException e){
						return false;
					}// TODO Auto-generated method stub
					//return true;

				}
			});
			textFieldTieSix.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldTieSix.getBackground().getRed()+textFieldTieSix.getBackground().getGreen()+textFieldTieSix.getBackground().getBlue())/3 < 130) {
						getTextFieldTieSix().setForeground(Color.white);
					}
					else
						getTextFieldTieSix().setForeground(Color.black);
				}
			});
		}
		return textFieldTieSix;
	}
	private JLabel getLabelTieOne() {
		if (labelTieOne == null) {
			labelTieOne = new JLabel("Tie 1");
		}
		return labelTieOne;
	}
	private JLabel getLabelTieTwo() {
		if (labelTieTwo == null) {
			labelTieTwo = new JLabel("Tie 2");
		}
		return labelTieTwo;
	}
	private JLabel getLabelTieThree() {
		if (labelTieThree == null) {
			labelTieThree = new JLabel("Tie 3");
		}
		return labelTieThree;
	}
	private JLabel getLabelTieFour() {
		if (labelTieFour == null) {
			labelTieFour = new JLabel("Tie 4");
		}
		return labelTieFour;
	}
	private JLabel getLabelTieFive() {
		if (labelTieFive == null) {
			labelTieFive = new JLabel("Tie 5");
		}
		return labelTieFive;
	}
	private JLabel getLabelTieSix() {
		if (labelTieSix == null) {
			labelTieSix = new JLabel("Tie 6");
		}
		return labelTieSix;
	}
	private JTextField getTextFieldDownHoleOne() {
		if (textFieldDownHoleOne == null) {
			textFieldDownHoleOne = new JTextField();
			textFieldDownHoleOne.setBackground(new Color(233, 150, 122));
			textFieldDownHoleOne.setText("500");
			textFieldDownHoleOne.setPreferredSize(new Dimension(80, 22));
			textFieldDownHoleOne.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDownHoleOne.setColumns(10);
			textFieldDownHoleOne.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldDownHoleOne.getBackground().getRed()+textFieldDownHoleOne.getBackground().getGreen()+textFieldDownHoleOne.getBackground().getBlue())/3 < 130) {
						getTextFieldDownHoleOne().setForeground(Color.white);
					}
					else
						getTextFieldDownHoleOne().setForeground(Color.black);
				}
			});
		}
		return textFieldDownHoleOne;
	}
	private JLabel getLabelDownHoleOne() {
		if (labelDownHoleOne == null) {
			labelDownHoleOne = new JLabel("Down hole 1");
		}
		return labelDownHoleOne;
	}

	private JTextField getTextFieldDownHoleTwo() {
		if (textFieldDownHoleTwo == null) {
			textFieldDownHoleTwo = new JTextField();
			textFieldDownHoleTwo.setBackground(new Color(238, 130, 238));
			textFieldDownHoleTwo.setText("450");
			textFieldDownHoleTwo.setPreferredSize(new Dimension(80, 22));
			textFieldDownHoleTwo.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDownHoleTwo.setColumns(10);
			textFieldDownHoleTwo.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldDownHoleTwo.getBackground().getRed()+textFieldDownHoleTwo.getBackground().getGreen()+textFieldDownHoleTwo.getBackground().getBlue())/3 < 130) {
						getTextFieldDownHoleTwo().setForeground(Color.white);
					}
					else
						getTextFieldDownHoleTwo().setForeground(Color.black);
				}
			});
		}
		return textFieldDownHoleTwo;
	}
	private JTextField getTextFieldDownHoleThree() {
		if (textFieldDownHoleThree == null) {
			textFieldDownHoleThree = new JTextField();
			textFieldDownHoleThree.setBackground(new Color(238, 232, 170));
			textFieldDownHoleThree.setText("400");
			textFieldDownHoleThree.setPreferredSize(new Dimension(80, 22));
			textFieldDownHoleThree.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldDownHoleThree.setColumns(10);
			textFieldDownHoleThree.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if((textFieldDownHoleThree.getBackground().getRed()+textFieldDownHoleThree.getBackground().getGreen()+textFieldDownHoleThree.getBackground().getBlue())/3 < 130) {
						getTextFieldDownHoleThree().setForeground(Color.white);
					}
					else
						getTextFieldDownHoleThree().setForeground(Color.black);
				}
			});
		}
		return textFieldDownHoleThree;
	}
	private JLabel getLabelDownHoleTwo() {
		if (labelDownHoleTwo == null) {
			labelDownHoleTwo = new JLabel("Down hole 2");
		}
		return labelDownHoleTwo;
	}
	private JLabel getLabelDownHoleThree() {
		if (labelDownHoleThree == null) {
			labelDownHoleThree = new JLabel("Down hole 3");
		}
		return labelDownHoleThree;
	}
	private JPanel getSurfaceConnectionPalette() {
		if (SurfaceConnectionPalette == null) {
			SurfaceConnectionPalette = new JPanel();
			SurfaceConnectionPalette.setBackground(new Color(225, 225, 225));
			SurfaceConnectionPalette.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			GridBagLayout gbl_SurfaceConnectionPalette = new GridBagLayout();
			gbl_SurfaceConnectionPalette.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
			gbl_SurfaceConnectionPalette.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_SurfaceConnectionPalette.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_SurfaceConnectionPalette.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			SurfaceConnectionPalette.setLayout(gbl_SurfaceConnectionPalette);
			GridBagConstraints gbc_buttonTieOneColorChange = new GridBagConstraints();
			gbc_buttonTieOneColorChange.insets = new Insets(2, 12, 2, 5);
			gbc_buttonTieOneColorChange.gridx = 1;
			gbc_buttonTieOneColorChange.gridy = 0;
			SurfaceConnectionPalette.add(getButtonTieOneColorChange(), gbc_buttonTieOneColorChange);
			GridBagConstraints gbc_textFieldTieOne = new GridBagConstraints();
			gbc_textFieldTieOne.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldTieOne.gridx = 2;
			gbc_textFieldTieOne.gridy = 0;
			SurfaceConnectionPalette.add(getTextFieldTieOne(), gbc_textFieldTieOne);
			GridBagConstraints gbc_labelTieOne = new GridBagConstraints();
			gbc_labelTieOne.anchor = GridBagConstraints.WEST;
			gbc_labelTieOne.insets = new Insets(2, 0, 2, 5);
			gbc_labelTieOne.gridx = 3;
			gbc_labelTieOne.gridy = 0;
			SurfaceConnectionPalette.add(getLabelTieOne(), gbc_labelTieOne);
			GridBagConstraints gbc_buttonDownHoleOneColorChange = new GridBagConstraints();
			gbc_buttonDownHoleOneColorChange.insets = new Insets(2, 40, 2, 5);
			gbc_buttonDownHoleOneColorChange.gridx = 4;
			gbc_buttonDownHoleOneColorChange.gridy = 0;
			SurfaceConnectionPalette.add(getButtonDownHoleOneColorChange(), gbc_buttonDownHoleOneColorChange);
			GridBagConstraints gbc_textFieldDownHoleOne = new GridBagConstraints();
			gbc_textFieldDownHoleOne.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldDownHoleOne.gridx = 5;
			gbc_textFieldDownHoleOne.gridy = 0;
			SurfaceConnectionPalette.add(getTextFieldDownHoleOne(), gbc_textFieldDownHoleOne);
			GridBagConstraints gbc_labelDownHoleOne = new GridBagConstraints();
			gbc_labelDownHoleOne.insets = new Insets(2, 0, 2, 40);
			gbc_labelDownHoleOne.anchor = GridBagConstraints.WEST;
			gbc_labelDownHoleOne.gridx = 6;
			gbc_labelDownHoleOne.gridy = 0;
			SurfaceConnectionPalette.add(getLabelDownHoleOne(), gbc_labelDownHoleOne);
			GridBagConstraints gbc_buttonTieTwoColorChange = new GridBagConstraints();
			gbc_buttonTieTwoColorChange.anchor = GridBagConstraints.EAST;
			gbc_buttonTieTwoColorChange.insets = new Insets(2, 0, 2, 5);
			gbc_buttonTieTwoColorChange.gridx = 1;
			gbc_buttonTieTwoColorChange.gridy = 1;
			SurfaceConnectionPalette.add(getButtonTieTwoColorChange(), gbc_buttonTieTwoColorChange);
			GridBagConstraints gbc_textFieldTieTwo = new GridBagConstraints();
			gbc_textFieldTieTwo.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldTieTwo.gridx = 2;
			gbc_textFieldTieTwo.gridy = 1;
			SurfaceConnectionPalette.add(getTextFieldTieTwo(), gbc_textFieldTieTwo);
			GridBagConstraints gbc_labelTieTwo = new GridBagConstraints();
			gbc_labelTieTwo.anchor = GridBagConstraints.WEST;
			gbc_labelTieTwo.insets = new Insets(2, 0, 2, 5);
			gbc_labelTieTwo.gridx = 3;
			gbc_labelTieTwo.gridy = 1;
			SurfaceConnectionPalette.add(getLabelTieTwo(), gbc_labelTieTwo);
			GridBagConstraints gbc_buttonDownHoleTwoColorChange = new GridBagConstraints();
			gbc_buttonDownHoleTwoColorChange.insets = new Insets(2, 40, 2, 5);
			gbc_buttonDownHoleTwoColorChange.gridx = 4;
			gbc_buttonDownHoleTwoColorChange.gridy = 1;
			SurfaceConnectionPalette.add(getButtonDownHoleTwoColorChange(), gbc_buttonDownHoleTwoColorChange);
			GridBagConstraints gbc_textFieldDownHoleTwo = new GridBagConstraints();
			gbc_textFieldDownHoleTwo.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldDownHoleTwo.gridx = 5;
			gbc_textFieldDownHoleTwo.gridy = 1;
			SurfaceConnectionPalette.add(getTextFieldDownHoleTwo(), gbc_textFieldDownHoleTwo);
			GridBagConstraints gbc_labelDownHoleTwo = new GridBagConstraints();
			gbc_labelDownHoleTwo.insets = new Insets(2, 0, 2, 40);
			gbc_labelDownHoleTwo.anchor = GridBagConstraints.WEST;
			gbc_labelDownHoleTwo.gridx = 6;
			gbc_labelDownHoleTwo.gridy = 1;
			SurfaceConnectionPalette.add(getLabelDownHoleTwo(), gbc_labelDownHoleTwo);
			GridBagConstraints gbc_buttonTieThreeColorChange = new GridBagConstraints();
			gbc_buttonTieThreeColorChange.anchor = GridBagConstraints.EAST;
			gbc_buttonTieThreeColorChange.insets = new Insets(2, 0, 2, 5);
			gbc_buttonTieThreeColorChange.gridx = 1;
			gbc_buttonTieThreeColorChange.gridy = 2;
			SurfaceConnectionPalette.add(getButtonTieThreeColorChange(), gbc_buttonTieThreeColorChange);
			GridBagConstraints gbc_textFieldTieThree = new GridBagConstraints();
			gbc_textFieldTieThree.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldTieThree.gridx = 2;
			gbc_textFieldTieThree.gridy = 2;
			SurfaceConnectionPalette.add(getTextFieldTieThree(), gbc_textFieldTieThree);
			GridBagConstraints gbc_labelTieThree = new GridBagConstraints();
			gbc_labelTieThree.anchor = GridBagConstraints.WEST;
			gbc_labelTieThree.insets = new Insets(2, 0, 2, 5);
			gbc_labelTieThree.gridx = 3;
			gbc_labelTieThree.gridy = 2;
			SurfaceConnectionPalette.add(getLabelTieThree(), gbc_labelTieThree);
			GridBagConstraints gbc_buttonDownHoleThreeColorChange = new GridBagConstraints();
			gbc_buttonDownHoleThreeColorChange.insets = new Insets(2, 40, 2, 5);
			gbc_buttonDownHoleThreeColorChange.gridx = 4;
			gbc_buttonDownHoleThreeColorChange.gridy = 2;
			SurfaceConnectionPalette.add(getButtonDownHoleThreeColorChange(), gbc_buttonDownHoleThreeColorChange);
			GridBagConstraints gbc_textFieldDownHoleThree = new GridBagConstraints();
			gbc_textFieldDownHoleThree.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldDownHoleThree.gridx = 5;
			gbc_textFieldDownHoleThree.gridy = 2;
			SurfaceConnectionPalette.add(getTextFieldDownHoleThree(), gbc_textFieldDownHoleThree);
			GridBagConstraints gbc_labelDownHoleThree = new GridBagConstraints();
			gbc_labelDownHoleThree.insets = new Insets(2, 0, 2, 40);
			gbc_labelDownHoleThree.anchor = GridBagConstraints.WEST;
			gbc_labelDownHoleThree.gridx = 6;
			gbc_labelDownHoleThree.gridy = 2;
			SurfaceConnectionPalette.add(getLabelDownHoleThree(), gbc_labelDownHoleThree);
			GridBagConstraints gbc_buttonTieFourColorChange = new GridBagConstraints();
			gbc_buttonTieFourColorChange.anchor = GridBagConstraints.EAST;
			gbc_buttonTieFourColorChange.insets = new Insets(2, 0, 2, 5);
			gbc_buttonTieFourColorChange.gridx = 1;
			gbc_buttonTieFourColorChange.gridy = 3;
			SurfaceConnectionPalette.add(getButtonTieFourColorChange(), gbc_buttonTieFourColorChange);
			GridBagConstraints gbc_textFieldTieFour = new GridBagConstraints();
			gbc_textFieldTieFour.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldTieFour.gridx = 2;
			gbc_textFieldTieFour.gridy = 3;
			SurfaceConnectionPalette.add(getTextFieldTieFour(), gbc_textFieldTieFour);
			GridBagConstraints gbc_labelTieFour = new GridBagConstraints();
			gbc_labelTieFour.anchor = GridBagConstraints.WEST;
			gbc_labelTieFour.insets = new Insets(2, 0, 2, 5);
			gbc_labelTieFour.gridx = 3;
			gbc_labelTieFour.gridy = 3;
			SurfaceConnectionPalette.add(getLabelTieFour(), gbc_labelTieFour);
			GridBagConstraints gbc_buttonTieFiveColorChange = new GridBagConstraints();
			gbc_buttonTieFiveColorChange.anchor = GridBagConstraints.EAST;
			gbc_buttonTieFiveColorChange.insets = new Insets(2, 0, 2, 5);
			gbc_buttonTieFiveColorChange.gridx = 1;
			gbc_buttonTieFiveColorChange.gridy = 4;
			SurfaceConnectionPalette.add(getButtonTieFiveColorChange(), gbc_buttonTieFiveColorChange);
			GridBagConstraints gbc_textFieldTieFive = new GridBagConstraints();
			gbc_textFieldTieFive.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldTieFive.gridx = 2;
			gbc_textFieldTieFive.gridy = 4;
			SurfaceConnectionPalette.add(getTextFieldTieFive(), gbc_textFieldTieFive);
			GridBagConstraints gbc_labelTieFive = new GridBagConstraints();
			gbc_labelTieFive.anchor = GridBagConstraints.WEST;
			gbc_labelTieFive.insets = new Insets(2, 0, 2, 5);
			gbc_labelTieFive.gridx = 3;
			gbc_labelTieFive.gridy = 4;
			SurfaceConnectionPalette.add(getLabelTieFive(), gbc_labelTieFive);
			GridBagConstraints gbc_buttonTieSixColorChange = new GridBagConstraints();
			gbc_buttonTieSixColorChange.anchor = GridBagConstraints.EAST;
			gbc_buttonTieSixColorChange.insets = new Insets(2, 0, 2, 5);
			gbc_buttonTieSixColorChange.gridx = 1;
			gbc_buttonTieSixColorChange.gridy = 5;
			SurfaceConnectionPalette.add(getButtonTieSixColorChange(), gbc_buttonTieSixColorChange);
			GridBagConstraints gbc_textFieldTieSix = new GridBagConstraints();
			gbc_textFieldTieSix.insets = new Insets(2, 0, 2, 5);
			gbc_textFieldTieSix.gridx = 2;
			gbc_textFieldTieSix.gridy = 5;
			SurfaceConnectionPalette.add(getTextFieldTieSix(), gbc_textFieldTieSix);
			GridBagConstraints gbc_labelTieSix = new GridBagConstraints();
			gbc_labelTieSix.anchor = GridBagConstraints.WEST;
			gbc_labelTieSix.insets = new Insets(2, 0, 2, 5);
			gbc_labelTieSix.gridx = 3;
			gbc_labelTieSix.gridy = 5;
			SurfaceConnectionPalette.add(getLabelTieSix(), gbc_labelTieSix);
		}
		return SurfaceConnectionPalette;
	}
	private JPanel getColorPanel2() {
		if (colorPanel2 == null) {
			colorPanel2 = new JPanel();
			colorPanel2.setBackground(new Color(225, 225, 225));
			colorPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(238, 238, 238), new Color(255, 255, 255), new Color(122, 138, 153), new Color(153, 153, 153)));
			GridBagLayout gbl_colorPanel2 = new GridBagLayout();
			gbl_colorPanel2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_colorPanel2.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_colorPanel2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
			gbl_colorPanel2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			colorPanel2.setLayout(gbl_colorPanel2);
			GridBagConstraints gbc_wellHoleIDColor = new GridBagConstraints();
			gbc_wellHoleIDColor.insets = new Insets(5, 10, 5, 5);
			gbc_wellHoleIDColor.gridx = 0;
			gbc_wellHoleIDColor.gridy = 0;
			colorPanel2.add(getPanelHoleIDColor(), gbc_wellHoleIDColor);
			GridBagConstraints gbc_labelHoleIdText = new GridBagConstraints();
			gbc_labelHoleIdText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelHoleIdText.insets = new Insets(5, 0, 5, 5);
			gbc_labelHoleIdText.gridx = 1;
			gbc_labelHoleIdText.gridy = 0;
			colorPanel2.add(getLabelHoleIdText(), gbc_labelHoleIdText);
			GridBagConstraints gbc_wellCanvasColor = new GridBagConstraints();
			gbc_wellCanvasColor.insets = new Insets(5, 10, 5, 5);
			gbc_wellCanvasColor.gridx = 4;
			gbc_wellCanvasColor.gridy = 0;
			colorPanel2.add(getPanelCanvasColor(), gbc_wellCanvasColor);
			GridBagConstraints gbc_labelBackgroundCanvas = new GridBagConstraints();
			gbc_labelBackgroundCanvas.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelBackgroundCanvas.insets = new Insets(5, 0, 5, 0);
			gbc_labelBackgroundCanvas.gridx = 5;
			gbc_labelBackgroundCanvas.gridy = 0;
			colorPanel2.add(getLabelBackgroundCanvas(), gbc_labelBackgroundCanvas);
			GridBagConstraints gbc_wellHoleLengthColor = new GridBagConstraints();
			gbc_wellHoleLengthColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellHoleLengthColor.gridx = 0;
			gbc_wellHoleLengthColor.gridy = 1;
			colorPanel2.add(getPanelHoleLengthColor(), gbc_wellHoleLengthColor);
			GridBagConstraints gbc_labelHoleLengthText = new GridBagConstraints();
			gbc_labelHoleLengthText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelHoleLengthText.insets = new Insets(0, 0, 5, 5);
			gbc_labelHoleLengthText.gridx = 1;
			gbc_labelHoleLengthText.gridy = 1;
			colorPanel2.add(getLabelHoleLengthText(), gbc_labelHoleLengthText);
			GridBagConstraints gbc_colorButton = new GridBagConstraints();
			gbc_colorButton.insets = new Insets(0, 10, 5, 5);
			gbc_colorButton.gridx = 4;
			gbc_colorButton.gridy = 1;
			colorPanel2.add(getSelectedObjectsColor(), gbc_colorButton);
			GridBagConstraints gbc_labelDisplayColour = new GridBagConstraints();
			gbc_labelDisplayColour.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelDisplayColour.insets = new Insets(0, 0, 5, 0);
			gbc_labelDisplayColour.gridx = 5;
			gbc_labelDisplayColour.gridy = 1;
			colorPanel2.add(getLabelDisplayColour(), gbc_labelDisplayColour);
			GridBagConstraints gbc_wellLabelOneColor = new GridBagConstraints();
			gbc_wellLabelOneColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellLabelOneColor.gridx = 0;
			gbc_wellLabelOneColor.gridy = 2;
			colorPanel2.add(getPanelLabelOneColor(), gbc_wellLabelOneColor);
			GridBagConstraints gbc_labelLabelOneText = new GridBagConstraints();
			gbc_labelLabelOneText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelLabelOneText.insets = new Insets(0, 0, 5, 5);
			gbc_labelLabelOneText.gridx = 1;
			gbc_labelLabelOneText.gridy = 2;
			colorPanel2.add(getLabelLabelOneText(), gbc_labelLabelOneText);
			GridBagConstraints gbc_wellGridLineColor = new GridBagConstraints();
			gbc_wellGridLineColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellGridLineColor.gridx = 4;
			gbc_wellGridLineColor.gridy = 2;
			colorPanel2.add(getPanelGridLineColor(), gbc_wellGridLineColor);
			GridBagConstraints gbc_labelGridLinesAnd = new GridBagConstraints();
			gbc_labelGridLinesAnd.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelGridLinesAnd.insets = new Insets(0, 0, 5, 0);
			gbc_labelGridLinesAnd.gridx = 5;
			gbc_labelGridLinesAnd.gridy = 2;
			colorPanel2.add(getLabelGridLinesAnd(), gbc_labelGridLinesAnd);
			GridBagConstraints gbc_wellLabelTwoColor = new GridBagConstraints();
			gbc_wellLabelTwoColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellLabelTwoColor.gridx = 0;
			gbc_wellLabelTwoColor.gridy = 3;
			colorPanel2.add(getPanelLabelTwoColor(), gbc_wellLabelTwoColor);
			GridBagConstraints gbc_labelLabelTwoText = new GridBagConstraints();
			gbc_labelLabelTwoText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelLabelTwoText.insets = new Insets(0, 0, 5, 5);
			gbc_labelLabelTwoText.gridx = 1;
			gbc_labelLabelTwoText.gridy = 3;
			colorPanel2.add(getLabelLabelTwoText(), gbc_labelLabelTwoText);
			GridBagConstraints gbc_wellFloorLocFillColor = new GridBagConstraints();
			gbc_wellFloorLocFillColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellFloorLocFillColor.gridx = 4;
			gbc_wellFloorLocFillColor.gridy = 3;
			colorPanel2.add(getPanelFloorLocFillColor(), gbc_wellFloorLocFillColor);
			GridBagConstraints gbc_labelHoleTrackLine = new GridBagConstraints();
			gbc_labelHoleTrackLine.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelHoleTrackLine.insets = new Insets(0, 0, 5, 0);
			gbc_labelHoleTrackLine.gridx = 5;
			gbc_labelHoleTrackLine.gridy = 3;
			colorPanel2.add(getLabelHoleTrackLine(), gbc_labelHoleTrackLine);
			GridBagConstraints gbc_wellLabelThreeColor = new GridBagConstraints();
			gbc_wellLabelThreeColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellLabelThreeColor.gridx = 0;
			gbc_wellLabelThreeColor.gridy = 4;
			colorPanel2.add(getPanelLabelThreeColor(), gbc_wellLabelThreeColor);
			GridBagConstraints gbc_labelLabelThreeText = new GridBagConstraints();
			gbc_labelLabelThreeText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelLabelThreeText.insets = new Insets(0, 0, 5, 5);
			gbc_labelLabelThreeText.gridx = 1;
			gbc_labelLabelThreeText.gridy = 4;
			colorPanel2.add(getLabelLabelThreeText(), gbc_labelLabelThreeText);
			GridBagConstraints gbc_wellFloorLineColor = new GridBagConstraints();
			gbc_wellFloorLineColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellFloorLineColor.gridx = 4;
			gbc_wellFloorLineColor.gridy = 4;
			colorPanel2.add(getPanelFloorLineColor(), gbc_wellFloorLineColor);
			GridBagConstraints gbc_labelFloorCircleLocation = new GridBagConstraints();
			gbc_labelFloorCircleLocation.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelFloorCircleLocation.insets = new Insets(0, 0, 5, 0);
			gbc_labelFloorCircleLocation.gridx = 5;
			gbc_labelFloorCircleLocation.gridy = 4;
			colorPanel2.add(getLabelFloorCircleLocation(), gbc_labelFloorCircleLocation);
			GridBagConstraints gbc_wellBenchHeightColor = new GridBagConstraints();
			gbc_wellBenchHeightColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellBenchHeightColor.gridx = 0;
			gbc_wellBenchHeightColor.gridy = 5;
			colorPanel2.add(getPanelBenchHeightColor(), gbc_wellBenchHeightColor);
			GridBagConstraints gbc_labelBenchHeightText = new GridBagConstraints();
			gbc_labelBenchHeightText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelBenchHeightText.insets = new Insets(0, 0, 5, 5);
			gbc_labelBenchHeightText.gridx = 1;
			gbc_labelBenchHeightText.gridy = 5;
			colorPanel2.add(getLabelBenchHeightText(), gbc_labelBenchHeightText);
			GridBagConstraints gbc_wellToeLocFillColor = new GridBagConstraints();
			gbc_wellToeLocFillColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellToeLocFillColor.gridx = 4;
			gbc_wellToeLocFillColor.gridy = 5;
			colorPanel2.add(getPanelToeLocFillColor(), gbc_wellToeLocFillColor);
			GridBagConstraints gbc_labelToeCircleLocation = new GridBagConstraints();
			gbc_labelToeCircleLocation.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelToeCircleLocation.insets = new Insets(0, 0, 5, 0);
			gbc_labelToeCircleLocation.gridx = 5;
			gbc_labelToeCircleLocation.gridy = 5;
			colorPanel2.add(getLabelToeCircleLocation(), gbc_labelToeCircleLocation);
			GridBagConstraints gbc_wellSubdrillColor = new GridBagConstraints();
			gbc_wellSubdrillColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellSubdrillColor.gridx = 0;
			gbc_wellSubdrillColor.gridy = 6;
			colorPanel2.add(getPanelSubdrillColor(), gbc_wellSubdrillColor);
			GridBagConstraints gbc_labelSubdrillAmountText = new GridBagConstraints();
			gbc_labelSubdrillAmountText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelSubdrillAmountText.insets = new Insets(0, 0, 5, 5);
			gbc_labelSubdrillAmountText.gridx = 1;
			gbc_labelSubdrillAmountText.gridy = 6;
			colorPanel2.add(getLabelSubdrillAmountText(), gbc_labelSubdrillAmountText);
			GridBagConstraints gbc_wellToeLocLineColor = new GridBagConstraints();
			gbc_wellToeLocLineColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellToeLocLineColor.gridx = 4;
			gbc_wellToeLocLineColor.gridy = 6;
			colorPanel2.add(getPanelToeLocLineColor(), gbc_wellToeLocLineColor);
			GridBagConstraints gbc_labelToeLocationLine = new GridBagConstraints();
			gbc_labelToeLocationLine.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelToeLocationLine.insets = new Insets(0, 0, 5, 0);
			gbc_labelToeLocationLine.gridx = 5;
			gbc_labelToeLocationLine.gridy = 6;
			colorPanel2.add(getLabelToeLocationLine(), gbc_labelToeLocationLine);
			GridBagConstraints gbc_wellHoleDiameterColor = new GridBagConstraints();
			gbc_wellHoleDiameterColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellHoleDiameterColor.gridx = 0;
			gbc_wellHoleDiameterColor.gridy = 7;
			colorPanel2.add(getPanelHoleDiameterColor(), gbc_wellHoleDiameterColor);
			GridBagConstraints gbc_labelHoleDiameterText = new GridBagConstraints();
			gbc_labelHoleDiameterText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelHoleDiameterText.insets = new Insets(0, 0, 5, 5);
			gbc_labelHoleDiameterText.gridx = 1;
			gbc_labelHoleDiameterText.gridy = 7;
			colorPanel2.add(getLabelHoleDiameterText(), gbc_labelHoleDiameterText);
			GridBagConstraints gbc_well = new GridBagConstraints();
			gbc_well.insets = new Insets(0, 10, 5, 5);
			gbc_well.gridx = 4;
			gbc_well.gridy = 7;
			colorPanel2.add(getPanelDetonatorDelayText(), gbc_well);
			GridBagConstraints gbc_labelDetonatorDelayText = new GridBagConstraints();
			gbc_labelDetonatorDelayText.anchor = GridBagConstraints.WEST;
			gbc_labelDetonatorDelayText.insets = new Insets(0, 0, 5, 0);
			gbc_labelDetonatorDelayText.gridx = 5;
			gbc_labelDetonatorDelayText.gridy = 7;
			colorPanel2.add(getLabelDetonatorDelayText(), gbc_labelDetonatorDelayText);
			GridBagConstraints gbc_wellHoleBearingColor = new GridBagConstraints();
			gbc_wellHoleBearingColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellHoleBearingColor.gridx = 0;
			gbc_wellHoleBearingColor.gridy = 8;
			colorPanel2.add(getPanelHoleBearingColor(), gbc_wellHoleBearingColor);
			GridBagConstraints gbc_labelHoleBearingText = new GridBagConstraints();
			gbc_labelHoleBearingText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelHoleBearingText.insets = new Insets(0, 0, 5, 5);
			gbc_labelHoleBearingText.gridx = 1;
			gbc_labelHoleBearingText.gridy = 8;
			colorPanel2.add(getLabelHoleBearingText(), gbc_labelHoleBearingText);
			GridBagConstraints gbc_well_1 = new GridBagConstraints();
			gbc_well_1.insets = new Insets(0, 10, 5, 5);
			gbc_well_1.gridx = 4;
			gbc_well_1.gridy = 8;
			colorPanel2.add(getPanelDownholeFiringTime(), gbc_well_1);
			GridBagConstraints gbc_labelDownHoleFiring = new GridBagConstraints();
			gbc_labelDownHoleFiring.insets = new Insets(0, 0, 5, 0);
			gbc_labelDownHoleFiring.anchor = GridBagConstraints.WEST;
			gbc_labelDownHoleFiring.gridx = 5;
			gbc_labelDownHoleFiring.gridy = 8;
			colorPanel2.add(getLabelDownHoleFiring(), gbc_labelDownHoleFiring);
			GridBagConstraints gbc_wellCollarLocColor = new GridBagConstraints();
			gbc_wellCollarLocColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellCollarLocColor.gridx = 0;
			gbc_wellCollarLocColor.gridy = 9;
			colorPanel2.add(getPanelCollarLocColor(), gbc_wellCollarLocColor);
			GridBagConstraints gbc_labelCollarLocationText = new GridBagConstraints();
			gbc_labelCollarLocationText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelCollarLocationText.insets = new Insets(0, 0, 5, 5);
			gbc_labelCollarLocationText.gridx = 1;
			gbc_labelCollarLocationText.gridy = 9;
			colorPanel2.add(getLabelCollarLocationText(), gbc_labelCollarLocationText);
			GridBagConstraints gbc_well_2 = new GridBagConstraints();
			gbc_well_2.insets = new Insets(0, 10, 5, 5);
			gbc_well_2.gridx = 4;
			gbc_well_2.gridy = 9;
			colorPanel2.add(getPanelSurfaceFiringTimes(), gbc_well_2);
			GridBagConstraints gbc_labelSurfaceConnectorFiring = new GridBagConstraints();
			gbc_labelSurfaceConnectorFiring.anchor = GridBagConstraints.WEST;
			gbc_labelSurfaceConnectorFiring.insets = new Insets(0, 0, 5, 0);
			gbc_labelSurfaceConnectorFiring.gridx = 5;
			gbc_labelSurfaceConnectorFiring.gridy = 9;
			colorPanel2.add(getLabelSurfaceConnectorFiring(), gbc_labelSurfaceConnectorFiring);
			GridBagConstraints gbc_wellFloorLocTextColor = new GridBagConstraints();
			gbc_wellFloorLocTextColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellFloorLocTextColor.gridx = 0;
			gbc_wellFloorLocTextColor.gridy = 10;
			colorPanel2.add(getPanelFloorLocTextColor(), gbc_wellFloorLocTextColor);
			GridBagConstraints gbc_labelFloorLocationText = new GridBagConstraints();
			gbc_labelFloorLocationText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelFloorLocationText.insets = new Insets(0, 0, 5, 5);
			gbc_labelFloorLocationText.gridx = 1;
			gbc_labelFloorLocationText.gridy = 10;
			colorPanel2.add(getLabelFloorLocationText(), gbc_labelFloorLocationText);
			GridBagConstraints gbc_wellToeLocTextColor = new GridBagConstraints();
			gbc_wellToeLocTextColor.insets = new Insets(0, 10, 5, 5);
			gbc_wellToeLocTextColor.gridx = 0;
			gbc_wellToeLocTextColor.gridy = 11;
			colorPanel2.add(getPanelToeLocTextColor(), gbc_wellToeLocTextColor);
			GridBagConstraints gbc_labelToeLocationText = new GridBagConstraints();
			gbc_labelToeLocationText.fill = GridBagConstraints.HORIZONTAL;
			gbc_labelToeLocationText.insets = new Insets(0, 0, 5, 5);
			gbc_labelToeLocationText.gridx = 1;
			gbc_labelToeLocationText.gridy = 11;
			colorPanel2.add(getLabelToeLocationText(), gbc_labelToeLocationText);
			GridBagConstraints gbc_panel = new GridBagConstraints();
			gbc_panel.anchor = GridBagConstraints.EAST;
			gbc_panel.insets = new Insets(0, 0, 5, 5);
			gbc_panel.gridx = 0;
			gbc_panel.gridy = 12;
			colorPanel2.add(getPanelHoleAngleTextColour(), gbc_panel);
			GridBagConstraints gbc_lblHoleAngledipText = new GridBagConstraints();
			gbc_lblHoleAngledipText.anchor = GridBagConstraints.WEST;
			gbc_lblHoleAngledipText.insets = new Insets(0, 0, 5, 5);
			gbc_lblHoleAngledipText.gridx = 1;
			gbc_lblHoleAngledipText.gridy = 12;
			colorPanel2.add(getLblHoleAngledipText(), gbc_lblHoleAngledipText);
		}
		return colorPanel2;
	}
	private JLabel getLabelTextAndObject() {
		if (labelTextAndObject == null) {
			labelTextAndObject = new JLabel("Text and Object Colours");
		}
		return labelTextAndObject;
	}
	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setBackground(new Color(225, 225, 225));
			panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			GridBagLayout gbl_panel_2 = new GridBagLayout();
			gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
			gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
			gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			panel_2.setLayout(gbl_panel_2);
			GridBagConstraints gbc_labelHoleSymbolStartup = new GridBagConstraints();
			gbc_labelHoleSymbolStartup.gridwidth = 3;
			gbc_labelHoleSymbolStartup.insets = new Insets(5, 40, 5, 5);
			gbc_labelHoleSymbolStartup.gridx = 0;
			gbc_labelHoleSymbolStartup.gridy = 0;
			panel_2.add(getLabelHoleSymbolStartup(), gbc_labelHoleSymbolStartup);
			GridBagConstraints gbc_spinnerHoleEnhancement = new GridBagConstraints();
			gbc_spinnerHoleEnhancement.gridwidth = 2;
			gbc_spinnerHoleEnhancement.insets = new Insets(5, 0, 5, 40);
			gbc_spinnerHoleEnhancement.gridx = 4;
			gbc_spinnerHoleEnhancement.gridy = 0;
			panel_2.add(getSpinnerHoleEnhancement(), gbc_spinnerHoleEnhancement);
			GridBagConstraints gbc_labelSurfaceConnectorStartup = new GridBagConstraints();
			gbc_labelSurfaceConnectorStartup.gridwidth = 3;
			gbc_labelSurfaceConnectorStartup.insets = new Insets(0, 40, 5, 5);
			gbc_labelSurfaceConnectorStartup.gridx = 0;
			gbc_labelSurfaceConnectorStartup.gridy = 1;
			panel_2.add(getLabelSurfaceConnectorStartup(), gbc_labelSurfaceConnectorStartup);
			GridBagConstraints gbc_spinnerSurfaceConnectorEnhancement = new GridBagConstraints();
			gbc_spinnerSurfaceConnectorEnhancement.gridwidth = 2;
			gbc_spinnerSurfaceConnectorEnhancement.insets = new Insets(0, 0, 5, 40);
			gbc_spinnerSurfaceConnectorEnhancement.gridx = 4;
			gbc_spinnerSurfaceConnectorEnhancement.gridy = 1;
			panel_2.add(getSpinnerSurfaceConnectorEnhancement(), gbc_spinnerSurfaceConnectorEnhancement);
			GridBagConstraints gbc_labelFloorAndToe = new GridBagConstraints();
			gbc_labelFloorAndToe.gridwidth = 3;
			gbc_labelFloorAndToe.insets = new Insets(0, 40, 5, 5);
			gbc_labelFloorAndToe.gridx = 0;
			gbc_labelFloorAndToe.gridy = 2;
			panel_2.add(getLabelFloorAndToe(), gbc_labelFloorAndToe);
			GridBagConstraints gbc_spinnerMarkerEnhancement = new GridBagConstraints();
			gbc_spinnerMarkerEnhancement.gridwidth = 2;
			gbc_spinnerMarkerEnhancement.insets = new Insets(0, 0, 5, 40);
			gbc_spinnerMarkerEnhancement.gridx = 4;
			gbc_spinnerMarkerEnhancement.gridy = 2;
			panel_2.add(getSpinnerMarkerEnhancement(), gbc_spinnerMarkerEnhancement);
			GridBagConstraints gbc_labelMultiTieIn = new GridBagConstraints();
			gbc_labelMultiTieIn.gridwidth = 2;
			gbc_labelMultiTieIn.insets = new Insets(0, 0, 5, 5);
			gbc_labelMultiTieIn.gridx = 1;
			gbc_labelMultiTieIn.gridy = 3;
			panel_2.add(getLabelMultiTieIn(), gbc_labelMultiTieIn);
			GridBagConstraints gbc_spinnerTieInLineTolerance = new GridBagConstraints();
			gbc_spinnerTieInLineTolerance.gridwidth = 2;
			gbc_spinnerTieInLineTolerance.insets = new Insets(0, 0, 5, 40);
			gbc_spinnerTieInLineTolerance.gridx = 4;
			gbc_spinnerTieInLineTolerance.gridy = 3;
			panel_2.add(getSpinnerTieInLineTolerance(), gbc_spinnerTieInLineTolerance);
			GridBagConstraints gbc_lblDefaultFont = new GridBagConstraints();
			gbc_lblDefaultFont.anchor = GridBagConstraints.EAST;
			gbc_lblDefaultFont.insets = new Insets(0, 0, 0, 5);
			gbc_lblDefaultFont.gridx = 1;
			gbc_lblDefaultFont.gridy = 4;
			panel_2.add(getLblDefaultFont(), gbc_lblDefaultFont);
			GridBagConstraints gbc_spinnerFontName = new GridBagConstraints();
			gbc_spinnerFontName.insets = new Insets(0, 0, 0, 5);
			gbc_spinnerFontName.gridx = 2;
			gbc_spinnerFontName.gridy = 4;
			panel_2.add(getSpinnerFontName(), gbc_spinnerFontName);
			GridBagConstraints gbc_lblPointSize = new GridBagConstraints();
			gbc_lblPointSize.insets = new Insets(0, 0, 0, 5);
			gbc_lblPointSize.gridx = 3;
			gbc_lblPointSize.gridy = 4;
			panel_2.add(getLblPointSize(), gbc_lblPointSize);
			GridBagConstraints gbc_spinnerFontPointSize = new GridBagConstraints();
			gbc_spinnerFontPointSize.insets = new Insets(0, 0, 0, 5);
			gbc_spinnerFontPointSize.anchor = GridBagConstraints.WEST;
			gbc_spinnerFontPointSize.gridx = 4;
			gbc_spinnerFontPointSize.gridy = 4;
			panel_2.add(getSpinnerFontPointSize(), gbc_spinnerFontPointSize);
			GridBagConstraints gbc_chckbxApplyScaling = new GridBagConstraints();
			gbc_chckbxApplyScaling.gridx = 5;
			gbc_chckbxApplyScaling.gridy = 4;
			panel_2.add(getCheckBoxApplyFontScaling(), gbc_chckbxApplyScaling);
		}
		return panel_2;
	}
	private JPanel getDisplayPanel() {
		if (displayPanel == null) {
			displayPanel = new JPanel();
			GridBagLayout gbl_displayPanel = new GridBagLayout();
			gbl_displayPanel.columnWidths = new int[]{245, 0};
			gbl_displayPanel.rowHeights = new int[]{0, 0, 0};
			gbl_displayPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_displayPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			displayPanel.setLayout(gbl_displayPanel);
			GridBagConstraints gbc_labelDisplayOnStartup = new GridBagConstraints();
			gbc_labelDisplayOnStartup.insets = new Insets(0, 0, 5, 0);
			gbc_labelDisplayOnStartup.gridx = 0;
			gbc_labelDisplayOnStartup.gridy = 0;
			displayPanel.add(getLabelDisplayOnStartup(), gbc_labelDisplayOnStartup);
			GridBagConstraints gbc_checkBoxDisplayPanel = new GridBagConstraints();
			gbc_checkBoxDisplayPanel.fill = GridBagConstraints.BOTH;
			gbc_checkBoxDisplayPanel.insets = new Insets(0, 10, 10, 10);
			gbc_checkBoxDisplayPanel.gridx = 0;
			gbc_checkBoxDisplayPanel.gridy = 1;
			displayPanel.add(getCheckBoxDisplayPanel(), gbc_checkBoxDisplayPanel);
		}
		return displayPanel;
	}
	private JLabel getLabelDisplayOnStartup() {
		if (labelDisplayOnStartup == null) {
			labelDisplayOnStartup = new JLabel("Display on Startup");
		}
		return labelDisplayOnStartup;
	}
	private JCheckBox getCheckBoxToeCircleMarker() {
		if (checkBoxToeCircleMarker == null) {
			checkBoxToeCircleMarker = new JCheckBox("Toe Circle Marker");
			checkBoxToeCircleMarker.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxToeCircleMarker.setBackground(new Color(225, 225, 225));
		}
		return checkBoxToeCircleMarker;
	}
	private JPanel getCheckBoxDisplayPanel() {
		if (checkBoxDisplayPanel == null) {
			checkBoxDisplayPanel = new JPanel();
			checkBoxDisplayPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			checkBoxDisplayPanel.setBackground(new Color(225, 225, 225));
			GridBagLayout gbl_checkBoxDisplayPanel = new GridBagLayout();
			gbl_checkBoxDisplayPanel.columnWidths = new int[]{0, 0, 0, 0};
			gbl_checkBoxDisplayPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			gbl_checkBoxDisplayPanel.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
			gbl_checkBoxDisplayPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			checkBoxDisplayPanel.setLayout(gbl_checkBoxDisplayPanel);
			GridBagConstraints gbc_checkBoxGridLines = new GridBagConstraints();
			gbc_checkBoxGridLines.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxGridLines.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxGridLines.gridx = 0;
			gbc_checkBoxGridLines.gridy = 0;
			checkBoxDisplayPanel.add(getCheckBoxGridLines(), gbc_checkBoxGridLines);
			GridBagConstraints gbc_checkBoxFloorCirclMarker = new GridBagConstraints();
			gbc_checkBoxFloorCirclMarker.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxFloorCirclMarker.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxFloorCirclMarker.gridx = 1;
			gbc_checkBoxFloorCirclMarker.gridy = 0;
			checkBoxDisplayPanel.add(getCheckBoxFloorCirclMarker(), gbc_checkBoxFloorCirclMarker);
			GridBagConstraints gbc_checkBoxHoleIdNumber = new GridBagConstraints();
			gbc_checkBoxHoleIdNumber.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleIdNumber.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxHoleIdNumber.gridx = 2;
			gbc_checkBoxHoleIdNumber.gridy = 0;
			checkBoxDisplayPanel.add(getCheckBoxHoleIdNumber(), gbc_checkBoxHoleIdNumber);
			GridBagConstraints gbc_checkBoxBoundaryLinesOn = new GridBagConstraints();
			gbc_checkBoxBoundaryLinesOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxBoundaryLinesOn.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxBoundaryLinesOn.gridx = 0;
			gbc_checkBoxBoundaryLinesOn.gridy = 1;
			checkBoxDisplayPanel.add(getCheckBoxBoundaryLinesOn(), gbc_checkBoxBoundaryLinesOn);
			GridBagConstraints gbc_checkBoxFloorLineMarker = new GridBagConstraints();
			gbc_checkBoxFloorLineMarker.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxFloorLineMarker.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxFloorLineMarker.gridx = 1;
			gbc_checkBoxFloorLineMarker.gridy = 1;
			checkBoxDisplayPanel.add(getCheckBoxFloorLineMarker(), gbc_checkBoxFloorLineMarker);
			GridBagConstraints gbc_checkBoxCollarLocationText = new GridBagConstraints();
			gbc_checkBoxCollarLocationText.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxCollarLocationText.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxCollarLocationText.gridx = 2;
			gbc_checkBoxCollarLocationText.gridy = 1;
			checkBoxDisplayPanel.add(getCheckBoxCollarLocationText(), gbc_checkBoxCollarLocationText);
			GridBagConstraints gbc_checkBoxTextOn = new GridBagConstraints();
			gbc_checkBoxTextOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxTextOn.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxTextOn.gridx = 0;
			gbc_checkBoxTextOn.gridy = 2;
			checkBoxDisplayPanel.add(getCheckBoxTextOn(), gbc_checkBoxTextOn);
			GridBagConstraints gbc_checkBoxGridLinesOn = new GridBagConstraints();
			gbc_checkBoxGridLinesOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxGridLinesOn.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxGridLinesOn.gridx = 1;
			gbc_checkBoxGridLinesOn.gridy = 2;
			checkBoxDisplayPanel.add(getCheckBoxToeCircleMarker(), gbc_checkBoxGridLinesOn);
			GridBagConstraints gbc_checkBoxFloorLocationText = new GridBagConstraints();
			gbc_checkBoxFloorLocationText.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxFloorLocationText.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxFloorLocationText.gridx = 2;
			gbc_checkBoxFloorLocationText.gridy = 2;
			checkBoxDisplayPanel.add(getCheckBoxFloorLocationText(), gbc_checkBoxFloorLocationText);
			GridBagConstraints gbc_checkBoxHoleSymbolsOn = new GridBagConstraints();
			gbc_checkBoxHoleSymbolsOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleSymbolsOn.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxHoleSymbolsOn.gridx = 0;
			gbc_checkBoxHoleSymbolsOn.gridy = 3;
			checkBoxDisplayPanel.add(getCheckBoxHoleSymbolsOn(), gbc_checkBoxHoleSymbolsOn);
			GridBagConstraints gbc_checkBoxToeLineMarker = new GridBagConstraints();
			gbc_checkBoxToeLineMarker.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxToeLineMarker.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxToeLineMarker.gridx = 1;
			gbc_checkBoxToeLineMarker.gridy = 3;
			checkBoxDisplayPanel.add(getCheckBoxToeLineMarker(), gbc_checkBoxToeLineMarker);
			GridBagConstraints gbc_checkBoxToeLocationText = new GridBagConstraints();
			gbc_checkBoxToeLocationText.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxToeLocationText.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxToeLocationText.gridx = 2;
			gbc_checkBoxToeLocationText.gridy = 3;
			checkBoxDisplayPanel.add(getCheckBoxToeLocationText(), gbc_checkBoxToeLocationText);
			GridBagConstraints gbc_checkBoxDummyHoleSymbols = new GridBagConstraints();
			gbc_checkBoxDummyHoleSymbols.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxDummyHoleSymbols.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxDummyHoleSymbols.gridx = 0;
			gbc_checkBoxDummyHoleSymbols.gridy = 4;
			checkBoxDisplayPanel.add(getCheckBoxDummyHoleSymbols(), gbc_checkBoxDummyHoleSymbols);
			GridBagConstraints gbc_checkBoxHoleDrillTracks = new GridBagConstraints();
			gbc_checkBoxHoleDrillTracks.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleDrillTracks.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxHoleDrillTracks.gridx = 1;
			gbc_checkBoxHoleDrillTracks.gridy = 4;
			checkBoxDisplayPanel.add(getCheckBoxHoleDrillTracks(), gbc_checkBoxHoleDrillTracks);
			GridBagConstraints gbc_checkBoxHoleDiameterOn = new GridBagConstraints();
			gbc_checkBoxHoleDiameterOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleDiameterOn.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxHoleDiameterOn.gridx = 2;
			gbc_checkBoxHoleDiameterOn.gridy = 4;
			checkBoxDisplayPanel.add(getCheckBoxHoleDiameterOn(), gbc_checkBoxHoleDiameterOn);
			GridBagConstraints gbc_checkBoxHoleBearingOn = new GridBagConstraints();
			gbc_checkBoxHoleBearingOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleBearingOn.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxHoleBearingOn.gridx = 0;
			gbc_checkBoxHoleBearingOn.gridy = 5;
			checkBoxDisplayPanel.add(getCheckBoxSurfaceConnections(), gbc_checkBoxHoleBearingOn);
			GridBagConstraints gbc_checkBoxHoleLengthOn = new GridBagConstraints();
			gbc_checkBoxHoleLengthOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleLengthOn.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxHoleLengthOn.gridx = 2;
			gbc_checkBoxHoleLengthOn.gridy = 5;
			checkBoxDisplayPanel.add(getCheckBoxHoleLengthOn(), gbc_checkBoxHoleLengthOn);
			GridBagConstraints gbc_checkBoxSurfaceConnectorLabels = new GridBagConstraints();
			gbc_checkBoxSurfaceConnectorLabels.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxSurfaceConnectorLabels.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxSurfaceConnectorLabels.gridx = 0;
			gbc_checkBoxSurfaceConnectorLabels.gridy = 6;
			checkBoxDisplayPanel.add(getCheckBoxSurfaceConnectorLabels(), gbc_checkBoxSurfaceConnectorLabels);
			GridBagConstraints gbc_checkBoxHoleAngleText = new GridBagConstraints();
			gbc_checkBoxHoleAngleText.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleAngleText.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxHoleAngleText.gridx = 2;
			gbc_checkBoxHoleAngleText.gridy = 6;
			checkBoxDisplayPanel.add(getCheckBoxHoleAngleText(), gbc_checkBoxHoleAngleText);
			GridBagConstraints gbc_checkBoxSurfaceConnectorFiring = new GridBagConstraints();
			gbc_checkBoxSurfaceConnectorFiring.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxSurfaceConnectorFiring.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxSurfaceConnectorFiring.gridx = 0;
			gbc_checkBoxSurfaceConnectorFiring.gridy = 7;
			checkBoxDisplayPanel.add(getCheckBoxSurfaceConnectorFiring(), gbc_checkBoxSurfaceConnectorFiring);
			GridBagConstraints gbc_checkBoxBenchHeightText = new GridBagConstraints();
			gbc_checkBoxBenchHeightText.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxBenchHeightText.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxBenchHeightText.gridx = 2;
			gbc_checkBoxBenchHeightText.gridy = 7;
			checkBoxDisplayPanel.add(getCheckBoxBenchHeightText(), gbc_checkBoxBenchHeightText);
			GridBagConstraints gbc_checkBoxDownHoleFiring = new GridBagConstraints();
			gbc_checkBoxDownHoleFiring.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxDownHoleFiring.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxDownHoleFiring.gridx = 0;
			gbc_checkBoxDownHoleFiring.gridy = 8;
			checkBoxDisplayPanel.add(getCheckBoxDownHoleFiring(), gbc_checkBoxDownHoleFiring);
			GridBagConstraints gbc_checkBoxSubdrillAmountText = new GridBagConstraints();
			gbc_checkBoxSubdrillAmountText.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxSubdrillAmountText.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxSubdrillAmountText.gridx = 2;
			gbc_checkBoxSubdrillAmountText.gridy = 8;
			checkBoxDisplayPanel.add(getCheckBoxSubdrillAmountText(), gbc_checkBoxSubdrillAmountText);
			GridBagConstraints gbc_checkBoxSurfaceTimeContours = new GridBagConstraints();
			gbc_checkBoxSurfaceTimeContours.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxSurfaceTimeContours.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxSurfaceTimeContours.gridx = 0;
			gbc_checkBoxSurfaceTimeContours.gridy = 9;
			checkBoxDisplayPanel.add(getCheckBoxSurfaceTimeContours(), gbc_checkBoxSurfaceTimeContours);
			GridBagConstraints gbc_checkBoxHoleLabelOne = new GridBagConstraints();
			gbc_checkBoxHoleLabelOne.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleLabelOne.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxHoleLabelOne.gridx = 2;
			gbc_checkBoxHoleLabelOne.gridy = 9;
			checkBoxDisplayPanel.add(getCheckBoxHoleLabelOne(), gbc_checkBoxHoleLabelOne);
			GridBagConstraints gbc_checkBoxFirstMovements = new GridBagConstraints();
			gbc_checkBoxFirstMovements.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxFirstMovements.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxFirstMovements.gridx = 0;
			gbc_checkBoxFirstMovements.gridy = 10;
			checkBoxDisplayPanel.add(getCheckBoxFirstMovements(), gbc_checkBoxFirstMovements);
			GridBagConstraints gbc_checkBoxDetonatorIndicationOn = new GridBagConstraints();
			gbc_checkBoxDetonatorIndicationOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxDetonatorIndicationOn.insets = new Insets(0, 0, 5, 5);
			gbc_checkBoxDetonatorIndicationOn.gridx = 1;
			gbc_checkBoxDetonatorIndicationOn.gridy = 10;
			checkBoxDisplayPanel.add(getCheckBoxDetonatorIndicationOn(), gbc_checkBoxDetonatorIndicationOn);
			GridBagConstraints gbc_checkBoxHoleLabelTwo = new GridBagConstraints();
			gbc_checkBoxHoleLabelTwo.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleLabelTwo.insets = new Insets(0, 0, 5, 0);
			gbc_checkBoxHoleLabelTwo.gridx = 2;
			gbc_checkBoxHoleLabelTwo.gridy = 10;
			checkBoxDisplayPanel.add(getCheckBoxHoleLabelTwo(), gbc_checkBoxHoleLabelTwo);
			GridBagConstraints gbc_checkBoxReliefShading = new GridBagConstraints();
			gbc_checkBoxReliefShading.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxReliefShading.insets = new Insets(0, 0, 0, 5);
			gbc_checkBoxReliefShading.gridx = 0;
			gbc_checkBoxReliefShading.gridy = 11;
			checkBoxDisplayPanel.add(getCheckBoxReliefShading(), gbc_checkBoxReliefShading);
			GridBagConstraints gbc_checkBoxDetonatorDelaysOn = new GridBagConstraints();
			gbc_checkBoxDetonatorDelaysOn.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxDetonatorDelaysOn.insets = new Insets(0, 0, 0, 5);
			gbc_checkBoxDetonatorDelaysOn.gridx = 1;
			gbc_checkBoxDetonatorDelaysOn.gridy = 11;
			checkBoxDisplayPanel.add(getCheckBoxDetonatorDelaysOn(), gbc_checkBoxDetonatorDelaysOn);
			GridBagConstraints gbc_checkBoxHoleLabelThree = new GridBagConstraints();
			gbc_checkBoxHoleLabelThree.fill = GridBagConstraints.HORIZONTAL;
			gbc_checkBoxHoleLabelThree.gridx = 2;
			gbc_checkBoxHoleLabelThree.gridy = 11;
			checkBoxDisplayPanel.add(getCheckBoxHoleLabelThree(), gbc_checkBoxHoleLabelThree);
		}
		return checkBoxDisplayPanel;
	}
	private JCheckBox getCheckBoxGridLines() {
		if (checkBoxGridLines == null) {
			checkBoxGridLines = new JCheckBox("Grid Lines");
			checkBoxGridLines.setSelected(true);
			checkBoxGridLines.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxGridLines.setBackground(new Color(225, 225, 225));
		}
		return checkBoxGridLines;
	}
	private JCheckBox getCheckBoxBoundaryLinesOn() {
		if (checkBoxBoundaryLinesOn == null) {
			checkBoxBoundaryLinesOn = new JCheckBox("Boundary Lines");
			checkBoxBoundaryLinesOn.setSelected(true);
			checkBoxBoundaryLinesOn.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxBoundaryLinesOn.setBackground(new Color(225, 225, 225));
		}
		return checkBoxBoundaryLinesOn;
	}
	private JCheckBox getCheckBoxTextOn() {
		if (checkBoxTextOn == null) {
			checkBoxTextOn = new JCheckBox("Text");
			checkBoxTextOn.setSelected(true);
			checkBoxTextOn.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxTextOn.setBackground(new Color(225, 225, 225));
		}
		return checkBoxTextOn;
	}
	private JCheckBox getCheckBoxHoleAngleText() {
		if (checkBoxHoleAngleText == null) {
			checkBoxHoleAngleText = new JCheckBox("Hole Angle Text");
			checkBoxHoleAngleText.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleAngleText.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleAngleText;
	}
	private JCheckBox getCheckBoxHoleSymbolsOn() {
		if (checkBoxHoleSymbolsOn == null) {
			checkBoxHoleSymbolsOn = new JCheckBox("Hole Symbols");
			checkBoxHoleSymbolsOn.setSelected(true);
			checkBoxHoleSymbolsOn.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleSymbolsOn.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleSymbolsOn;
	}
	private JCheckBox getCheckBoxDummyHoleSymbols() {
		if (checkBoxDummyHoleSymbols == null) {
			checkBoxDummyHoleSymbols = new JCheckBox("Dummy Hole Symbols");
			checkBoxDummyHoleSymbols.setSelected(true);
			checkBoxDummyHoleSymbols.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxDummyHoleSymbols.setBackground(new Color(225, 225, 225));
		}
		return checkBoxDummyHoleSymbols;
	}
	private JCheckBox getCheckBoxHoleDrillTracks() {
		if (checkBoxHoleDrillTracks == null) {
			checkBoxHoleDrillTracks = new JCheckBox("Hole Drill Tracks");
			checkBoxHoleDrillTracks.setSelected(true);
			checkBoxHoleDrillTracks.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleDrillTracks.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleDrillTracks;
	}
	private JCheckBox getCheckBoxFloorCirclMarker() {
		if (checkBoxFloorCirclMarker == null) {
			checkBoxFloorCirclMarker = new JCheckBox("Floor Circle Marker");
			checkBoxFloorCirclMarker.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxFloorCirclMarker.setBackground(new Color(225, 225, 225));
		}
		return checkBoxFloorCirclMarker;
	}
	private JCheckBox getCheckBoxFloorLineMarker() {
		if (checkBoxFloorLineMarker == null) {
			checkBoxFloorLineMarker = new JCheckBox("Floor Line Marker");
			checkBoxFloorLineMarker.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxFloorLineMarker.setBackground(new Color(225, 225, 225));
		}
		return checkBoxFloorLineMarker;
	}
	private JCheckBox getCheckBoxToeLineMarker() {
		if (checkBoxToeLineMarker == null) {
			checkBoxToeLineMarker = new JCheckBox("Toe Line Marker");
			checkBoxToeLineMarker.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxToeLineMarker.setBackground(new Color(225, 225, 225));
		}
		return checkBoxToeLineMarker;
	}
	private JCheckBox getCheckBoxHoleLabelTwo() {
		if (checkBoxHoleLabelTwo == null) {
			checkBoxHoleLabelTwo = new JCheckBox("Hole Label Two");
			checkBoxHoleLabelTwo.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleLabelTwo.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleLabelTwo;
	}
	private JCheckBox getCheckBoxHoleIdNumber() {
		if (checkBoxHoleIdNumber == null) {
			checkBoxHoleIdNumber = new JCheckBox("Hole ID Number");
			checkBoxHoleIdNumber.setSelected(true);
			checkBoxHoleIdNumber.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleIdNumber.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleIdNumber;
	}
	private JCheckBox getCheckBoxHoleLabelOne() {
		if (checkBoxHoleLabelOne == null) {
			checkBoxHoleLabelOne = new JCheckBox("Hole Label One");
			checkBoxHoleLabelOne.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleLabelOne.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleLabelOne;
	}
	private JCheckBox getCheckBoxHoleLabelThree() {
		if (checkBoxHoleLabelThree == null) {
			checkBoxHoleLabelThree = new JCheckBox("Hole Label Three");
			checkBoxHoleLabelThree.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleLabelThree.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleLabelThree;
	}
	private JCheckBox getCheckBoxBenchHeightText() {
		if (checkBoxBenchHeightText == null) {
			checkBoxBenchHeightText = new JCheckBox("Bench Height Text");
			checkBoxBenchHeightText.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxBenchHeightText.setBackground(new Color(225, 225, 225));
		}
		return checkBoxBenchHeightText;
	}
	private JCheckBox getCheckBoxSubdrillAmountText() {
		if (checkBoxSubdrillAmountText == null) {
			checkBoxSubdrillAmountText = new JCheckBox("Subdrill Amount Text");
			checkBoxSubdrillAmountText.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxSubdrillAmountText.setBackground(new Color(225, 225, 225));
		}
		return checkBoxSubdrillAmountText;
	}
	private JCheckBox getCheckBoxCollarLocationText() {
		if (checkBoxCollarLocationText == null) {
			checkBoxCollarLocationText = new JCheckBox("Collar Location Text");
			checkBoxCollarLocationText.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxCollarLocationText.setBackground(new Color(225, 225, 225));
		}
		return checkBoxCollarLocationText;
	}
	private JCheckBox getCheckBoxFloorLocationText() {
		if (checkBoxFloorLocationText == null) {
			checkBoxFloorLocationText = new JCheckBox("Floor Location Text");
			checkBoxFloorLocationText.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxFloorLocationText.setBackground(new Color(225, 225, 225));
		}
		return checkBoxFloorLocationText;
	}
	private JCheckBox getCheckBoxToeLocationText() {
		if (checkBoxToeLocationText == null) {
			checkBoxToeLocationText = new JCheckBox("Toe Location Text");
			checkBoxToeLocationText.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxToeLocationText.setBackground(new Color(225, 225, 225));
		}
		return checkBoxToeLocationText;
	}
	private JCheckBox getCheckBoxHoleLengthOn() {
		if (checkBoxHoleLengthOn == null) {
			checkBoxHoleLengthOn = new JCheckBox("Hole Length");
			checkBoxHoleLengthOn.setSelected(true);
			checkBoxHoleLengthOn.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleLengthOn.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleLengthOn;
	}
	private JCheckBox getCheckBoxHoleDiameterOn() {
		if (checkBoxHoleDiameterOn == null) {
			checkBoxHoleDiameterOn = new JCheckBox("Hole Diameter");
			checkBoxHoleDiameterOn.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxHoleDiameterOn.setBackground(new Color(225, 225, 225));
		}
		return checkBoxHoleDiameterOn;
	}
	private JCheckBox getCheckBoxSurfaceConnections() {
		if (checkBoxSurfaceConnections == null) {
			checkBoxSurfaceConnections = new JCheckBox("Surface Connections");
			checkBoxSurfaceConnections.setSelected(true);
			checkBoxSurfaceConnections.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxSurfaceConnections.setBackground(new Color(225, 225, 225));
		}
		return checkBoxSurfaceConnections;
	}
	private JCheckBox getCheckBoxSurfaceConnectorLabels() {
		if (checkBoxSurfaceConnectorLabels == null) {
			checkBoxSurfaceConnectorLabels = new JCheckBox("Surface Connector Labels");
			checkBoxSurfaceConnectorLabels.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxSurfaceConnectorLabels.setBackground(new Color(225, 225, 225));
		}
		return checkBoxSurfaceConnectorLabels;
	}
	private JCheckBox getCheckBoxDetonatorIndicationOn() {
		if (checkBoxDetonatorIndicationOn == null) {
			checkBoxDetonatorIndicationOn = new JCheckBox("Detonator Indication");
			checkBoxDetonatorIndicationOn.setSelected(true);
			checkBoxDetonatorIndicationOn.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxDetonatorIndicationOn.setBackground(new Color(225, 225, 225));
		}
		return checkBoxDetonatorIndicationOn;
	}
	private JCheckBox getCheckBoxDetonatorDelaysOn() {
		if (checkBoxDetonatorDelaysOn == null) {
			checkBoxDetonatorDelaysOn = new JCheckBox("Detonator Delays");
			checkBoxDetonatorDelaysOn.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxDetonatorDelaysOn.setBackground(new Color(225, 225, 225));
		}
		return checkBoxDetonatorDelaysOn;
	}
	private JCheckBox getCheckBoxDownHoleFiring() {
		if (checkBoxDownHoleFiring == null) {
			checkBoxDownHoleFiring = new JCheckBox("Down Hole Firing Times");
			checkBoxDownHoleFiring.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxDownHoleFiring.setBackground(new Color(225, 225, 225));
		}
		return checkBoxDownHoleFiring;
	}
	private JCheckBox getCheckBoxSurfaceConnectorFiring() {
		if (checkBoxSurfaceConnectorFiring == null) {
			checkBoxSurfaceConnectorFiring = new JCheckBox("Surface Connector Firing Times");
			checkBoxSurfaceConnectorFiring.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxSurfaceConnectorFiring.setBackground(new Color(225, 225, 225));
		}
		return checkBoxSurfaceConnectorFiring;
	}
	private JCheckBox getCheckBoxSurfaceTimeContours() {
		if (checkBoxSurfaceTimeContours == null) {
			checkBoxSurfaceTimeContours = new JCheckBox("Surface Time Contours");
			checkBoxSurfaceTimeContours.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxSurfaceTimeContours.setBackground(new Color(225, 225, 225));
		}
		return checkBoxSurfaceTimeContours;
	}
	private JCheckBox getCheckBoxFirstMovements() {
		if (checkBoxFirstMovements == null) {
			checkBoxFirstMovements = new JCheckBox("First Movements");
			checkBoxFirstMovements.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxFirstMovements.setBackground(new Color(225, 225, 225));
		}
		return checkBoxFirstMovements;
	}
	private JCheckBox getCheckBoxReliefShading() {
		if (checkBoxReliefShading == null) {
			checkBoxReliefShading = new JCheckBox("Relief Shading");
			checkBoxReliefShading.setFont(new Font("Dialog", Font.BOLD, 10));
			checkBoxReliefShading.setBackground(new Color(225, 225, 225));
		}
		return checkBoxReliefShading;
	}
	private JPanel getPanelDetonatorDelayText() {
		if (wellDetonatorDelayColor == null) {
			wellDetonatorDelayColor = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellDetonatorDelayColor.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellDetonatorDelayColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellDetonatorDelayColor.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellDetonatorDelayColor.setPreferredSize(new Dimension(22, 22));
			wellDetonatorDelayColor.setBackground(Color.WHITE);
			wellDetonatorDelayColor.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelDetonatorDelayText().getBackground());
					if (currentColor != null) {
						getPanelDetonatorDelayText().setBackground(currentColor);
						p_detonatorDelayTextColour = (wellDetonatorDelayColor.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellDetonatorDelayColor;
	}
	private JLabel getLabelDetonatorDelayText() {
		if (labelDetonatorDelayText == null) {
			labelDetonatorDelayText = new JLabel("Detonator Delay Text");
		}
		return labelDetonatorDelayText;
	}
	private JLabel getLabelDownHoleFiring() {
		if (labelDownHoleFiring == null) {
			labelDownHoleFiring = new JLabel("Down Hole Firing Times");
		}
		return labelDownHoleFiring;
	}
	private JLabel getLabelSurfaceConnectorFiring() {
		if (labelSurfaceConnectorFiring == null) {
			labelSurfaceConnectorFiring = new JLabel("Surface Connector Firing Times");
		}
		return labelSurfaceConnectorFiring;
	}
	private JPanel getPanelDownholeFiringTime() {
		if (wellDownholeFiringTime == null) {
			wellDownholeFiringTime = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellDownholeFiringTime.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellDownholeFiringTime.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellDownholeFiringTime.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellDownholeFiringTime.setPreferredSize(new Dimension(22, 22));
			wellDownholeFiringTime.setBackground(Color.WHITE);
			wellDownholeFiringTime.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelDownholeFiringTime().getBackground());
					if (currentColor != null) {
						getPanelDownholeFiringTime().setBackground(currentColor);
						p_downHoleFiringTimesColour = (wellDownholeFiringTime.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellDownholeFiringTime;
	}
	private JPanel getPanelSurfaceFiringTimes() {
		if (wellSurfaceFiringTime == null) {
			wellSurfaceFiringTime = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellSurfaceFiringTime.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellSurfaceFiringTime.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellSurfaceFiringTime.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			wellSurfaceFiringTime.setPreferredSize(new Dimension(22, 22));
			wellSurfaceFiringTime.setBackground(Color.WHITE);
			wellSurfaceFiringTime.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelSurfaceFiringTimes().getBackground());
					if (currentColor != null) {
						getPanelSurfaceFiringTimes().setBackground(currentColor);
						p_surfaceConnectorFiringTimesColour = (wellSurfaceFiringTime.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellSurfaceFiringTime;
	}
	private JButton getButtonDownHoleTwoColorChange() {
		if (buttonDownHoleTwoColorChange == null) {
			buttonDownHoleTwoColorChange = new JButton("");
			buttonDownHoleTwoColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			buttonDownHoleTwoColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonDownHoleTwoColorChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color color = JColorChooser.showDialog(buttonPanel, "Change Downhole Connector Colour", getTextFieldDownHoleTwo().getBackground());
					getTextFieldDownHoleTwo().setBackground(color);
					if((textFieldDownHoleTwo.getBackground().getRed()+textFieldDownHoleTwo.getBackground().getGreen()+textFieldDownHoleTwo.getBackground().getBlue())/3 < 130) {
						getTextFieldDownHoleTwo().setForeground(Color.white);
					}
					else
						getTextFieldDownHoleTwo().setForeground(Color.black);
				}
			});
		}
		return buttonDownHoleTwoColorChange;
	}
	private JButton getButtonDownHoleOneColorChange() {
		if (buttonDownHoleOneColorChange == null) {
			buttonDownHoleOneColorChange = new JButton("");
			buttonDownHoleOneColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonDownHoleOneColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			buttonDownHoleOneColorChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color color = JColorChooser.showDialog(buttonPanel, "Change Downhole Connector Colour", getTextFieldDownHoleOne().getBackground());
					getTextFieldDownHoleOne().setBackground(color);
					if((textFieldDownHoleOne.getBackground().getRed()+textFieldDownHoleOne.getBackground().getGreen()+textFieldDownHoleOne.getBackground().getBlue())/3 < 130) {
						getTextFieldDownHoleOne().setForeground(Color.white);
					}
					else
						getTextFieldDownHoleOne().setForeground(Color.black);
				}
			});
		}
		return buttonDownHoleOneColorChange;
	}
	private JButton getButtonDownHoleThreeColorChange() {
		if (buttonDownHoleThreeColorChange == null) {
			buttonDownHoleThreeColorChange = new JButton("");
			buttonDownHoleThreeColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonDownHoleThreeColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			buttonDownHoleThreeColorChange.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Color color = JColorChooser.showDialog(buttonPanel, "Change Downhole Connector Colour", getTextFieldDownHoleThree().getBackground());
					getTextFieldDownHoleThree().setBackground(color);
					if((textFieldDownHoleThree.getBackground().getRed()+textFieldDownHoleThree.getBackground().getGreen()+textFieldDownHoleThree.getBackground().getBlue())/3 < 130) {
						getTextFieldDownHoleThree().setForeground(Color.white);
					}
					else
						getTextFieldDownHoleThree().setForeground(Color.black);
				}
			});
		}
		return buttonDownHoleThreeColorChange;
	}
	private JButton getButtonTieOneColorChange() {
		if (buttonTieOneColorChange == null) {
			buttonTieOneColorChange = new JButton("");
			buttonTieOneColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonTieOneColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			if(getRdbtnCustom().isSelected()) {
				getButtonTieOneColorChange().setEnabled(true);
				buttonTieOneColorChange.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Color color = JColorChooser.showDialog(buttonPanel, "Change Surface Connector Colour", getTextFieldTieOne().getBackground());
						getTextFieldTieOne().setBackground(color);
						getRdbtnAustinPowder().setSelected(false);
						getRdbtnDynoNobelAp().setSelected(false);
						getRdbtnMaxamAus().setSelected(false);
						getRdbtnOricaAustralia().setSelected(false);
						getRdbtnCustom().setSelected(true);
						if((textFieldTieOne.getBackground().getRed()+textFieldTieOne.getBackground().getGreen()+textFieldTieOne.getBackground().getBlue())/3 < 130) {
							getTextFieldTieOne().setForeground(Color.white);
						}
						else
							getTextFieldTieOne().setForeground(Color.black);
					}
				});
			}
			else {
				getButtonTieOneColorChange().setEnabled(false);
			}
		}
		return buttonTieOneColorChange;
	}
	private JButton getButtonTieTwoColorChange() {
		if (buttonTieTwoColorChange == null) {
			buttonTieTwoColorChange = new JButton("");
			buttonTieTwoColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonTieTwoColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			if(getRdbtnCustom().isSelected()) {
				getButtonTieTwoColorChange().setEnabled(true);
				buttonTieTwoColorChange.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Color color = JColorChooser.showDialog(buttonPanel, "Change Surface Connector Colour", getTextFieldTieTwo().getBackground());
						getTextFieldTieTwo().setBackground(color);
						getRdbtnAustinPowder().setSelected(false);
						getRdbtnDynoNobelAp().setSelected(false);
						getRdbtnMaxamAus().setSelected(false);
						getRdbtnOricaAustralia().setSelected(false);
						getRdbtnCustom().setSelected(true);
						if((textFieldTieTwo.getBackground().getRed()+textFieldTieTwo.getBackground().getGreen()+textFieldTieTwo.getBackground().getBlue())/3 < 130) {
							getTextFieldTieTwo().setForeground(Color.white);
						}
						else
							getTextFieldTieTwo().setForeground(Color.black);
					}
				});
			}
			else {
				getButtonTieTwoColorChange().setEnabled(false);
			}
		}
		return buttonTieTwoColorChange;
	}
	private JButton getButtonTieThreeColorChange() {
		if (buttonTieThreeColorChange == null) {
			buttonTieThreeColorChange = new JButton("");
			buttonTieThreeColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonTieThreeColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			if(getRdbtnCustom().isSelected()) {
				getButtonTieThreeColorChange().setEnabled(true);
				buttonTieThreeColorChange.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Color color = JColorChooser.showDialog(buttonPanel, "Change Surface Connector Colour", getTextFieldTieThree().getBackground());
						getTextFieldTieThree().setBackground(color);
						getRdbtnAustinPowder().setSelected(false);
						getRdbtnDynoNobelAp().setSelected(false);
						getRdbtnMaxamAus().setSelected(false);
						getRdbtnOricaAustralia().setSelected(false);
						getRdbtnCustom().setSelected(true);
						if((textFieldTieThree.getBackground().getRed()+textFieldTieThree.getBackground().getGreen()+textFieldTieThree.getBackground().getBlue())/3 < 130) {
							getTextFieldTieThree().setForeground(Color.white);
						}
						else
							getTextFieldTieThree().setForeground(Color.black);
					}
				});
			}
			else {
				getButtonTieThreeColorChange().setEnabled(false);
			}
		}
		return buttonTieThreeColorChange;
	}
	private JButton getButtonTieFourColorChange() {
		if (buttonTieFourColorChange == null) {
			buttonTieFourColorChange = new JButton("");
			buttonTieFourColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonTieFourColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			if(getRdbtnCustom().isSelected()) {
				getButtonTieFourColorChange().setEnabled(true);
				buttonTieFourColorChange.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Color color = JColorChooser.showDialog(buttonPanel, "Change Surface Connector Colour", getTextFieldTieFour().getBackground());
						getTextFieldTieFour().setBackground(color);
						getRdbtnAustinPowder().setSelected(false);
						getRdbtnDynoNobelAp().setSelected(false);
						getRdbtnMaxamAus().setSelected(false);
						getRdbtnOricaAustralia().setSelected(false);
						getRdbtnCustom().setSelected(true);
						if((textFieldTieFour.getBackground().getRed()+textFieldTieFour.getBackground().getGreen()+textFieldTieFour.getBackground().getBlue())/3 < 130) {
							getTextFieldTieFour().setForeground(Color.white);
						}
						else
							getTextFieldTieFour().setForeground(Color.black);
					}
				});
			}
			else {
				getButtonTieFourColorChange().setEnabled(false);
			}
		}
		return buttonTieFourColorChange;
	}
	private JButton getButtonTieFiveColorChange() {
		if (buttonTieFiveColorChange == null) {
			buttonTieFiveColorChange = new JButton("");
			buttonTieFiveColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonTieFiveColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			if(getRdbtnCustom().isSelected()) {
				getButtonTieFiveColorChange().setEnabled(true);
				buttonTieFiveColorChange.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Color color = JColorChooser.showDialog(buttonPanel, "Change Surface Connector Colour", getTextFieldTieFive().getBackground());
						getTextFieldTieFive().setBackground(color);
						getRdbtnAustinPowder().setSelected(false);
						getRdbtnDynoNobelAp().setSelected(false);
						getRdbtnMaxamAus().setSelected(false);
						getRdbtnOricaAustralia().setSelected(false);
						getRdbtnCustom().setSelected(true);
						if((textFieldTieFive.getBackground().getRed()+textFieldTieFive.getBackground().getGreen()+textFieldTieFive.getBackground().getBlue())/3 < 130) {
							getTextFieldTieFive().setForeground(Color.white);
						}
						else
							getTextFieldTieFive().setForeground(Color.black);
					}
				});
			}
			else {
				getButtonTieFiveColorChange().setEnabled(false);
			}
		}
		return buttonTieFiveColorChange;
	}
	private JButton getButtonTieSixColorChange() {
		if (buttonTieSixColorChange == null) {
			buttonTieSixColorChange = new JButton("");
			buttonTieSixColorChange.setIcon(new ImageIcon(DialogPreferences.class.getResource("/icons_LiteTie_v2/colorPicker.png")));
			buttonTieSixColorChange.setBorder(new EmptyBorder(0, 0, 0, 0));
			if(getRdbtnCustom().isSelected()) {
				getButtonTieSixColorChange().setEnabled(true);
				buttonTieSixColorChange.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent e) {
						Color color = JColorChooser.showDialog(buttonPanel, "Change Surface Connector Colour", getTextFieldTieTwo().getBackground());
						getTextFieldTieSix().setBackground(color);
						getRdbtnAustinPowder().setSelected(false);
						getRdbtnDynoNobelAp().setSelected(false);
						getRdbtnMaxamAus().setSelected(false);
						getRdbtnOricaAustralia().setSelected(false);
						getRdbtnCustom().setSelected(true);
						if((textFieldTieSix.getBackground().getRed()+textFieldTieSix.getBackground().getGreen()+textFieldTieSix.getBackground().getBlue())/3 < 130) {
							getTextFieldTieSix().setForeground(Color.white);
						}
						else
							getTextFieldTieSix().setForeground(Color.black);
					}
				});
			}
			else {
				getButtonTieSixColorChange().setEnabled(false);
			}
		}
		return buttonTieSixColorChange;
	}
	private JPanel getPanelHoleAngleTextColour() {
		if (wellHoleAngleTextColour == null) {
			wellHoleAngleTextColour = new JPanel() {
				public void paintComponent(Graphics g) {
					if(wellHoleAngleTextColour.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);	g.fillRect(14, 2, 6, 6); g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);g.fillRect(14, 14, 6, 6);
						g.setColor(wellHoleAngleTextColour.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
					else {
						g.setColor(wellHoleAngleTextColour.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 22, 22);
					}
				}
			};
			FlowLayout flowLayout = (FlowLayout) wellHoleAngleTextColour.getLayout();
			wellHoleAngleTextColour.setPreferredSize(new Dimension(22, 22));
			wellHoleAngleTextColour.setBackground(Color.WHITE);
			wellHoleAngleTextColour.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {
					currentColor = JColorChooser.showDialog(DialogPreferences.this , "Change Current Colour", getPanelHoleAngleTextColour().getBackground());
					if (currentColor != null) {
						getPanelHoleAngleTextColour().setBackground(currentColor);
						p_holeAngleTextColour = (wellHoleAngleTextColour.getBackground());
					}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseReleased(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
			});
		}
		return wellHoleAngleTextColour;
	}
	private JLabel getLblHoleAngledipText() {
		if (lblHoleAngledipText == null) {
			lblHoleAngledipText = new JLabel("Hole Angle/Dip Text");
		}
		return lblHoleAngledipText;
	}
	private JSpinner getSpinnerFontName() {
		if (spinnerFontName == null) {
			spinnerFontName = new JSpinner();
			spinnerFontName.setPreferredSize(new Dimension(240, 20));
			spinnerFontName.setModel(spinnerModelFont);
			if(!(fontNames.contains(defaultFont))){
				defaultFont = "Dialog";
			}
			else {
				spinnerFontName.setValue(defaultFont);
			}
		}
		return spinnerFontName;
	}
	private JLabel getLblDefaultFont() {
		if (lblDefaultFont == null) {
			lblDefaultFont = new JLabel("Default Font");
		}
		return lblDefaultFont;
	}
	private JSpinner getSpinnerFontPointSize() {
		if (spinnerFontPointSize == null) {
			spinnerFontPointSize = new JSpinner();
			spinnerFontPointSize.setPreferredSize(new Dimension(80, 20));
			spinnerFontPointSize.setModel(spinnerModelFontSize);
		}
		return spinnerFontPointSize;
	}
	private JCheckBox getCheckBoxApplyFontScaling() {
		if (chckbxApplyScaling == null) {
			chckbxApplyScaling = new JCheckBox("Apply scaling");
			chckbxApplyScaling.setBackground(this.getPanel_2().getBackground());
		}
		return chckbxApplyScaling;
	}
	private JLabel getLblPointSize() {
		if (lblPointSize == null) {
			lblPointSize = new JLabel("Size");
		}
		return lblPointSize;
	}
	private JLabel getLabelMultiTieIn() {
		if (labelMultiTieIn == null) {
			labelMultiTieIn = new JLabel("Multi tie in Line Tolerance (meters)");
			labelMultiTieIn.setIcon(null);
		}
		return labelMultiTieIn;
	}
	private JSpinner getSpinnerTieInLineTolerance() {
		if (spinnerTieInLineTolerance == null) {
			spinnerTieInLineTolerance = new JSpinner(spinnerModelTieInLineTolerance);
			spinnerTieInLineTolerance.setPreferredSize(new Dimension(80, 22));
		}
		return spinnerTieInLineTolerance;
	}
}  //  @jve:decl-index=0:visual-constraint="122,10"
//Read up on NumberFormat.class about getIntegerInstance
//also JFormattedTextField 

//Fix error handling in the textfields i.e northing can't be a negative
