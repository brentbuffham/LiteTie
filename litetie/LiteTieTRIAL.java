package litetie;

/**
 *@Title LiteTie 
 *@author Brent Buffham 
 *@Date_Last_Modified 17 December 2009 
 * 
 */

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import litetie.controller.PatternTemplate;
import litetie.controller.Transform;
import litetie.file.FileTypeRegistry;
import litetie.model.BPoint;
import litetie.model.Boundary;
import litetie.model.Coordinate;
import litetie.model.Detonator;
import litetie.model.Dummy;
import litetie.model.FromToException;
import litetie.model.Hole;
import litetie.model.InitiationPoint;
import litetie.model.LTPoint3D;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.Polygon2D;
import litetie.model.SurfaceConnector;
import litetie.model.Text;
import litetie.model.UnitConvert;
import litetie.model.World;
import litetie.model.ZeroArgumentException;
import litetie.view.DialogBasicStroke;
import litetie.view.DialogCreateHole;
import litetie.view.DialogCreatePattern;
import litetie.view.DialogOpenFile;
import litetie.view.DialogPreferences;
import litetie.view.DialogPropertiesHole;
import litetie.view.DialogPropertiesSurfaceConnector;
import litetie.view.DialogSaveFile;
import litetie.view.DialogXYZValues;
import litetie.view.Zoom;
import litetie.view.actions.ActionHost;
import litetie.view.actions.MoveAction;
import litetie.view.actions.RotateAction;
import litetie.view.drawing.Printing2D;
import litetie.view.drawing.Visualise2D;
import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class LiteTieTRIAL extends JFrame implements ActionHost {

	private static final long serialVersionUID = 1L;

	public static Font default_Font = new Font("Dialog", Font.PLAIN, 10);

	// variable Declarations

	private World world = new World("World");

	// Create a undo manager
	private UndoManager undoManager = new UndoManager();

	double holeFactor = 3.1;
	double markerFactor = 1.1;
	double surfaceFactor = 5.1;
	double radius = 1;
	SpinnerNumberModel spinnerModelSurfaceFactor = new SpinnerNumberModel(surfaceFactor, 1, 40, 1.0);
	SpinnerNumberModel spinnerModelMarkerFactor = new SpinnerNumberModel(markerFactor, 1, 100.0, 1.0);
	SpinnerNumberModel spinnerModelHoleFactor = new SpinnerNumberModel(holeFactor, 1, 100.0, 1.0);
	SpinnerNumberModel lineSpinnerModel = new SpinnerNumberModel(1, 0, 15, 1);
	SpinnerNumberModel dashSpinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
	SpinnerNumberModel spaceSpinnerModel = new SpinnerNumberModel(0, 0, 50, 1);
	// String[] viewTypesList = { "Plan View", "Isometric", "Front", "Back",
	// "Top", "Bottom", "Perspective" };
	// SpinnerListModel viewSpinnerModel = new SpinnerListModel(viewTypesList);

	// Formats for various displays of details
	DecimalFormat decimalFormat4 = new DecimalFormat("#0.0000");
	DecimalFormat decimalFormat3 = new DecimalFormat("#0.000");
	DecimalFormat decimalFormat2 = new DecimalFormat("#0.00");
	DecimalFormat decimalFormat1 = new DecimalFormat("#0.0");
	DecimalFormat decimalFormat0 = new DecimalFormat("#0");

	DecimalFormat decimalFormatMetres3 = new DecimalFormat("#0.000" + "m");
	DecimalFormat decimalFormatMetres2 = new DecimalFormat("#0.00" + "m");
	DecimalFormat decimalFormatMetresN2 = new DecimalFormat("#0.00" + "mN");
	DecimalFormat decimalFormatMetresE2 = new DecimalFormat("#0.00" + "mE");
	DecimalFormat decimalFormatMetres1 = new DecimalFormat("#0.0" + "m");

	DecimalFormat decimalFormatDegrees2 = new DecimalFormat("#0.00" + "\u00B0");
	DecimalFormat decimalFormatDegrees1 = new DecimalFormat("#0.0" + "\u00B0");

	// Declaration of Pattern List to contain all Patterns created in the World
	// private LinkedList<Pattern> patList = new LinkedList<Pattern>();
	// currentPattern denotes the pattern that is currently active
	LinkedList<Object> selectedFIFO = new LinkedList<Object>();
	static Object selected = null; // selectedObject holds the active object
									// whether it is Holes, Dummys, Connectors
									// etc.

	// Boundary objects consider changing to Paths and Polygons as this Boundary
	// stuff may be confusing the application should as much as practicable be
	// generic

	// LITE_TIE OBJECTS
	private Text text;
	private BPoint bPoint;
	static Boundary currentBoundary = null;
	private Boundary boundary;
	static Pattern currentPattern = null;
	private Pattern pattern;
	private Dummy dummy;
	private Dummy dummy1;
	private Dummy dummy2;
	private Hole hole1;
	private InitiationPoint ip;
	private Detonator det;
	private CalculateUtility calcs = new CalculateUtility();

	// DIMENSIONS FOR GENERIC BUTTON SIZES
	private Dimension dimension30x30 = new Dimension(30, 30);
	private Dimension dimension30x60 = new Dimension(30, 60);
	private Dimension dimension30x120 = new Dimension(30, 120);
	private Dimension dimension60x60 = new Dimension(60, 60);
	private Dimension dimensionScreenSize = new Dimension(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
	// INSETS FOR BUTTON PADDING
	private Insets inset0 = new Insets(0, 0, 0, 0);

	private double lastRLHeight = 100;
	private double centerBoundsX, centerBoundsY, centerBoundsZ;
	private LTPoint3D center, currentPosition;

	private double deltaNorthing;
	private double deltaEasting;
	private double mouseClickX;
	private double mouseClickY;
	private double mouseX;
	private double mouseY;

	private static final int CLICK_ONE = 1;
	private static final int CLICK_TWO = 2;

	// SURFACE COLOURS
	// sColor1 thru to sColor6 are the color values used for the color of the
	// SurfaceConnectors
	private Color sColor1;
	private Color sColor2;
	private Color sColor3;
	private Color sColor4;
	private Color sColor5;
	private Color sColor6;
	private Color surfaceTieColor;

	// DOWNHOLE DETONATOR COLOURS
	// dColor1 thru to dColor3 are colours are for detonator color
	private Color dColor1;
	private Color dColor2;
	private Color dColor3;

	// user color
	public Color preferenceCanvasBackground;
	public Color currentColor = new Color(100, 100, 100, 255);
	public Color currentFillColor = new Color(0, 0, 0, 20);

	// BOUNDARY DISPLAY ATTRIBUTES
	private int strokeWidth = 1;
	private int dashLength = 1;
	private int spaceLength = 1;
	private float[] dashSpacing = { dashLength, spaceLength };
	private BasicStroke currentStroke = new BasicStroke(strokeWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1,
			dashSpacing, 1);

	private FileTypeRegistry.FileMapping file = new FileTypeRegistry.FileMapping(new File("temp"), null);

	// CANVAS RELATED POINT2Ds FOR MARQUEE DRAWING
	Point2D selectionPoint1 = null;
	Point2D selectionPoint2 = null;
	Point2D zoomPoint1 = null;
	Point2D zoomPoint2 = null;
	Point2D rulePoint1 = null;
	Point2D rulePoint2 = null;
	Point2D bearingPoint1 = null;
	Point2D bearingPoint2 = null;
	Point2D bearingPoint3 = null;
	Point2D arrowPoint1 = null;
	Point2D arrowPoint2 = null;
	Point2D textPoint = null;
	Point2D printPoint1 = null;
	Point2D printPoint2 = null;

	// temporary store for text values
	ArrayList<Double> polyPointsX = new ArrayList();
	ArrayList<Double> polyPointsY = new ArrayList();
	Point2D pPoint1 = null;
	Point2D pPoint2 = null;
	public static LTPoint3D tPoint1 = null; // Last poly point
	public static LTPoint3D tPoint2 = null; // PolyLine mouse location
	Coordinate coord1 = null;
	Coordinate coord2 = null;

	double[] xs = null;
	double[] ys = null;

	boolean isAnnotated = false;
	private JToggleButton toggleButton_1;
	private JToggleButton toolPolyPattern;
	private JSpinner spinnerStrokeWidth;
	private JSpinner spinnerStrokeDash;
	private JLabel lblType;
	private JLabel lblDesign;
	private JPanel colorWellFill;
	private JLabel lblFill;
	private JSpinner spinnerSpaceDash;
	private JLabel lblSpace;
	private JCheckBox checkBoxLockSpaceAndDash;
	private JSpinner spinnerViewTypes;
	private JToolBar toolBarMeasurementDetails;
	private JToolBar toolBar;
	private JLabel lblZValue;
	private JScrollPane scrollPane;
	private JScrollPane textScrollPane;
	private JTree textTree;
	private JScrollPane systemTreeScrollPane;
	private JTree systemTree;
	private JScrollPane scrollPane_3;
	private JTree tree_2;
	private static JTextArea textAreaConsoleOutput;
	private JScrollPane scrollPane_1;
	private JSpinner spinnerEnhanceAmount;
	private JSpinner spinnerEnhanceAmount2;
	private JSpinner spinnerEnhanceAmountTie;
	private JToggleButton toolMultiTieInLineButton;
	private JToggleButton toolAddChargeButton;

	// averageCRL used to determine altitude of dummys created
	public double averageCRL;
	// averageSize used to determine the size of the dummy drawn
	public static double averageSize;
	// surface delay value store
	int sDelay;
	// downhole delay value store
	int dDelay;

	String lastZoomClicked = "fit"; //
	String selectionType = "Selecting All";

	// UI Declarations
	private JPanel mainPane = null;
	private JMenuBar mainMenuBar = null;
	private JMenu liteTieMenu = null;
	private JMenu fileMenu = null;
	private JMenu viewMenu = null;
	private JMenu windowMenu = null;
	private JMenu helpMenu = null;
	private JMenu editMenu = null;
	private JMenu newMenu = null;
	private JMenuItem newPatternMenuItem = null;
	private JMenuItem newHoleMenuItem = null;
	private JMenuItem undoMenuItem = null;
	private JMenuItem redoMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem selectAllMenuItem = null;
	private JMenuItem initialiseWorldMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem preferencesMenuItem = null;
	private JMenuItem quitMenuItem = null;
	private JMenuItem openMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JMenuItem saveAsMenuItem = null;
	private JMenuItem pageSetupMenuItem = null;
	private JMenuItem printMenuItem = null;
	private JMenuItem zoomInMenuItem = null;
	private JMenuItem zoomOutMenuItem = null;
	private JMenuItem fitInWindowMenuItem = null;
	private JMenuItem hideToolbarMenuItem = null;
	private JMenuItem customiseToolbarMenuItem = null;
	private JMenuItem centerPatternMenuItem = null;
	private JMenu showMenu = null;
	private JScrollPane canvasScrollPane = null;
	private JPanel canvasPanel = null;

	private JLabel statusBarLabel = null;
	private JCheckBoxMenuItem gridCheckBoxMenuItem = null;
	private JButton undoButton = null;
	private JButton redoButton = null;
	private JButton zoomOutButton = null;
	private JButton zoomInButton = null;
	private JSplitPane layerHolderSplitPane = null;
	private JButton openButton = null;
	private JButton saveButton = null;
	private JButton printButton = null;
	private JToggleButton zoomToFitButton = null;
	private JToggleButton zoomUserSetButton = null;

	private JPanel colorWellStroke = null;
	private JButton saveAsButton = null;
	private JButton closeButton = null;
	private JToggleButton toolSelectionMarqueeButton = null;
	private JToggleButton toolPolygonSelectButton = null;
	private JPanel tieToolBarHolderPanel = null;
	private Color newColor = null;

	int pageOrientation = PageFormat.LANDSCAPE;

	private JPanel toolBarSideHolderPanel = null;
	private JLabel ToolBoxStatusLabel = null;
	private JProgressBar liteTieProgressBar = null;
	private JLabel labelColourStroke = null;
	private JLabel toolLabel2 = null;
	private JLabel toolKitLabel3 = null;
	private JLabel toolKitLabel4 = null;
	private JLabel toolKitLabel5 = null;
	static JLabel measurementStatusLabel1 = null;
	static JLabel measurementStatusLabel2 = null;
	static JLabel bearingStatusLabel = null;
	private JPanel layerHolderPanel = null;
	private JTabbedPane layerTabbedPane = null;
	private JScrollPane patternLayersScrollPane = null;
	private JScrollPane boundaryLayerScrollPane = null;
	private JScrollPane surfaceLayerScrollPane = null;
	private JButton trashCanButton = null;
	private JPanel layerOptionPanel = null;
	private JButton showLayerButton = null;
	private JButton newPatternLayerButton = null;
	private JButton mergePatternsButton = null;
	private JScrollPane timeEnvelopeScrollPane = null;

	private JPanel statusBarPanel = null;
	private JLabel yStatusLabel = null;
	private JLabel xStatusLabel = null;
	private JLabel zStatusLabel = null;

	// Surface Tie Buttons
	private JToggleButton tieButton1 = null;
	private JToggleButton tieButton2 = null;
	private JToggleButton tieButton3 = null;
	private JToggleButton tieButton4 = null;
	private JToggleButton tieButton5 = null;
	private JToggleButton tieButton6 = null;

	// Surface Tie Textfields
	private JTextField tieTextField1 = null;
	private JTextField tieTextField2 = null;
	private JTextField tieTextField3 = null;
	private JTextField tieTextField4 = null;
	private JTextField tieTextField5 = null;
	private JTextField tieTextField6 = null;

	// Detonator Buttons
	private JToggleButton detonatorTimeButton1 = null;
	private JToggleButton detonatorTimeButton2 = null;
	private JToggleButton detonatorTimeButton3 = null;

	// Detonator Textfields
	private JTextField detonatorTimeTextField1 = null;
	private JTextField detonatorTimeTextField2 = null;
	private JTextField detonatorTimeTextField3 = null;

	ButtonGroup buttonGroup1 = new ButtonGroup();// grouping of Side Tool Bar
													// buttons
	ButtonGroup buttonGroup2 = new ButtonGroup();// grouping of Tie Tool Bar
													// Buttons
	ButtonGroup buttonGroup3 = new ButtonGroup();// grouping for Detonator tool
													// bar Buttons - *INC*

	// Tool Buttons
	private JToggleButton toolMoveButton = null;
	private JToggleButton toolLabelButton = null;
	private JToggleButton toolRotateButton = null;
	private JToggleButton toolTieButton = null;
	private JToggleButton toolMultiTieButton = null;
	private JToggleButton toolSelectionButton = null;
	private JToggleButton toolHoleButton = null;
	private JToggleButton toolDummyButton = null;
	private JToggleButton toolPatternButton = null;
	private JToggleButton toolTextButton = null;
	private JToggleButton toolRulerButton = null;
	private JToggleButton holeTracksOnOffButton = null;
	private JToggleButton holeIDOnOffButton = null;
	private JToggleButton holeLabel1OnOffButton = null;
	private JToggleButton holeSubdrillOnOffButton = null;
	private JToggleButton holeLengthOnOffButton = null;
	private JToggleButton diameterOnOffButton = null;
	private JToggleButton bearingOnOffButton = null;
	private JToggleButton boundaryOnOffButton = null;
	private JToggleButton tieOnOffButton = null;
	private JToggleButton downholeTimesButton1 = null;
	private JToggleButton surfaceTimesOnOffButton = null;
	private JToggleButton collarRLOnOffButton = null;
	private JToggleButton toeRLOnOffButton = null;
	private JToggleButton gridOnOffButton = null;
	private JToggleButton angleOnOffButton = null;
	private JToggleButton holesOnOffButton = null;
	private JToggleButton detsOnOffButton = null;
	private JToggleButton detonatorDelayButton = null;
	private JToggleButton textOnOffButton = null;
	private JToggleButton toolNGonButton;
	private JSpinner toolNGonSpinner;
	private JToggleButton toolScaleButton;
	private JToggleButton toggleButton;
	private JToggleButton toggleButton_4;
	private JToggleButton toolPolyLineButton;
	private JToggleButton toolSelectionOval;
	private JToggleButton toolBearingMeasure;

	public static boolean notTheFirstTime;

	// ICONS slowly add these so there is one place to look for ICONS
	ImageIcon selection = new ImageIcon(getClass().getResource(("/icons_LiteTie/TOOLselection.png")));
	ImageIcon move = new ImageIcon(getClass().getResource(("/icons_LiteTie/TOOLmove.png")));
	ImageIcon rotate = new ImageIcon(getClass().getResource(("/icons_LiteTie/TOOLrotate.png")));
	ImageIcon liteTieIcon = new ImageIcon(getClass().getResource("/icons_LiteTie/iconLite64px.png"));

	// THIS MOTION ADAPTER FORCES THE canvasSize() TO BE CALLED
	// COMMENTED OUT AS IT IS REFRESHING THE SCREEN REGARDLESS
	private MouseMotionAdapter locationStatusMouseMover = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent e) {
			mouseClickY = getCanvasSize().getY() + getCanvasSize().getHeight()
					- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
			mouseClickX = getCanvasSize().getX() + UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
			setNorthingStatusLabel(decimalFormatMetresN2.format((mouseClickY)));
			setEastingStatusLabel(decimalFormatMetresE2.format((mouseClickX)));
		}
	};
	private MouseMotionAdapter defaultMouseMover = null;
	private MouseAdapter defaultMouseClicker = null;
	private MouseMotionListener currentMouseMover = defaultMouseMover;
	private MouseListener currentMouseClicker = defaultMouseClicker;

	private DefaultTreeModel textTreeModel;
	private DefaultTreeModel systemTreeModel;

	// Custom Cursors Section
	// PatternToolCursor Code
	Toolkit toolkit1 = Toolkit.getDefaultToolkit();
	Image patternToolCustom = toolkit1.getImage(getClass().getResource("/icons_LiteTie_v2/pattern.png")); //
	Point patternToolHotSpot = new Point(3, 3); //
	Cursor patternToolCursor = toolkit1.createCustomCursor(patternToolCustom, patternToolHotSpot, "PatternTool"); //
	// End PatternToolCursor Code

	// HoleToolCursor Code
	Toolkit toolkit2 = Toolkit.getDefaultToolkit(); //
	Image holeToolCustom = toolkit2.getImage(getClass().getResource("/icons_LiteTie/holeTOOL.png")); //
	Point holeToolHotSpot = new Point(0, 0); //
	Cursor holeToolCursor = toolkit2.createCustomCursor(holeToolCustom, holeToolHotSpot, "HoleTool"); //
	// End holeToolCursor Code

	// MarqueeToolCursor Code
	Toolkit toolkit3 = Toolkit.getDefaultToolkit(); //
	Image marqueeToolCustom = toolkit3.getImage(getClass().getResource("/icons_LiteTie/TOOLmarquee2.png")); //
	Point marqueeToolHotSpot = new Point(0, 0); //
	Cursor marqueeToolCursor = toolkit3.createCustomCursor(marqueeToolCustom, marqueeToolHotSpot, "MarqueeTool"); //
	// End MarqueeToolCursor Code

	// textToolCursor Code
	Toolkit toolkit4 = Toolkit.getDefaultToolkit(); //
	Image textToolCustom = toolkit4.getImage(getClass().getResource("/icons_LiteTie/TOOLText.png")); //
	Point textToolHotSpot = new Point(0, 0); //
	Cursor textToolCursor = toolkit4.createCustomCursor(textToolCustom, textToolHotSpot, "TextTool"); //
	// End MarqueeToolCursor Code

	// Selection Shift Down cursor
	Toolkit toolkit5 = Toolkit.getDefaultToolkit(); //
	Image selectionShiftCustom = toolkit5.getImage(getClass().getResource("/icons_LiteTie/selectionPlus.png")); //
	Point selectionShiftHotSpot = new Point(1, 1); //
	Cursor selectionShiftCursor = toolkit5.createCustomCursor(selectionShiftCustom, selectionShiftHotSpot,
			"Selection Tool Add"); //

	// Selection Alt Down cursor
	Toolkit toolkit6 = Toolkit.getDefaultToolkit(); //
	Image selectionAltCustom = toolkit6.getImage(getClass().getResource("/icons_LiteTie/selectionMinus.png"));
	Point selectionAltHotSpot = new Point(1, 1);
	Cursor selectionAltCursor = toolkit6.createCustomCursor(selectionAltCustom, selectionAltHotSpot,
			"Selection Tool Remove");

	private JToggleButton toolZMove = null;

	private JMenu selectAllInLayerMenu = null;
	private JMenuItem selectAllHolesMenuItem = null;
	private JMenuItem selectAllTiesMenuItem = null;
	private JToggleButton fRLMarkerButton = null;
	private JToggleButton tRLMarkerButton = null;
	private JToggleButton fRLCircleMarkerButton = null;
	private JToggleButton tRLCircleMarkerButton = null;
	private JToggleButton contourButton = null;
	private JToggleButton reliefButton = null;
	private JToggleButton firstMovementButton = null;
	private JToggleButton floorRLOnOffButton = null;
	private JToggleButton toolAddDetonatorButton = null;
	private JToggleButton toolAddMultiDetonatorButton = null;

	private JToggleButton holeLabel2OnOffButton = null;
	private JToggleButton holeLabel3OnOffButton = null;
	private JToggleButton benchHeightOnOffButton = null;

	protected DefaultTreeModel patternTreeModel; //
	protected JTree patternTree;
	protected TreePath pPath;

	protected DefaultTreeModel boundaryTreeModel; //
	protected JTree boundaryTree;
	protected TreePath bPath;

	protected DefaultTreeModel surfaceTreeModel; //
	protected JTree surfaceTree;
	protected TreePath sPath;

	private JPanel infoBarPanel = null;

	// Printer Setup
	private PrinterJob printJob = PrinterJob.getPrinterJob();
	private PageFormat pageFormat = null;

	public static String consoleOutput = "LiteTie v1.0 ©2013 \nConsole Output\n";

	boolean tieClicked = false;// this is for Timing tool
	int tieClick = 0;// this is for Timing tool

	private JToggleButton dummyOnOffButton = null;
	private JToggleButton tieDelayToggleButton = null;
	private JPanel toolPanelTop = null;
	private JButton showTabPaneButton = null;

	public static String p_directoryPath;
	public static float p_holeEFactor, p_markerEFactor, p_surfaceEFactor, p_tieInLineTolerance;
	public static int p_defaultFontSize;
	public static String p_defaultSupplier, p_tie1Amount, p_tie2Amount, p_tie3Amount, p_tie4Amount, p_tie5Amount,
			p_tie6Amount, p_downHole1Amount, p_downHole2Amount,
			p_downHole3Amount, p_registration, p_userName, p_defaultFont;

	public static Color p_holeTextColour, p_holeLengthTextColour, p_labelOneTextColour, p_labelTwoTextColour,
			p_labelThreeTextColour, p_benchHeightTextColour,
			p_subdrillTextColour, p_holeDiameterTextColour, p_holeBearingTextColour, p_collarLocationTextColour,
			p_floorLocationTextColour, p_toeLocationTextColour,
			p_holeAngleTextColour, p_backgroundCanvasColour, p_selectedObjectsColour, p_gridLinesAndTextColour,
			p_floorLocationFillColour, p_floorLocationLineColour,
			p_toeLocationFillColour, p_toeLocationLineColour, p_detonatorDelayTextColour, p_downHoleFiringTimesColour,
			p_surfaceConnectorFiringTimesColour, p_tie1Colour,
			p_tie2Colour, p_tie3Colour, p_tie4Colour, p_tie5Colour, p_tie6Colour, p_downHole1Colour, p_downHole2Colour,
			p_downHole3Colour, i_backgroundCanvasColour;

	public boolean p_showGrid, p_showBoundary, p_showText, p_showHoleAngle, p_showHoleSymbols, p_showDummySymbols,
			p_showDrillTracks, p_showFloorCircle, p_showFloorLine,
			p_showToeCircle, p_showToeLine, p_showHoleID, p_showLabelOne, p_showLabelTwo, p_showLabelThree, p_showBench,
			p_showSubdrill, p_showCollarLoc, p_showFloorLoc,
			p_showToeLoc, p_showLength, p_showDiameter, p_showSurfaceConnections, p_showSurfaceConnectionLabel,
			p_showDetonators, p_showDetonatorDelay, p_showDetonatorTimes,
			p_showSurfaceTimes, p_showContours, p_showmovements, p_showRelief, p_applyScaling;

	public LiteTieTRIAL(Object owner) {
		super();
		initialize();
	}

	public LiteTieTRIAL() {
		super();
		initialize();

	}

	// StatusBar set methods
	public void setStatusBarLabel(String status) {
		if (status == null) {
			status = "null";
		} else {
			this.statusBarLabel.setText(status);
		}
	}

	public void setNorthingStatusLabel(String status) {
		this.yStatusLabel.setText("Y = " + status);
		this.mouseY = Double.parseDouble(status.substring(0, status.length() - 2));
		updateCanvas();
	}

	public void setEastingStatusLabel(String status) {
		this.xStatusLabel.setText("X = " + status);
		this.mouseX = Double.parseDouble(status.substring(0, status.length() - 2));
		updateCanvas();
	}

	public void updateMouseLocation(double x, double y) {

	}

	public void setZStatusLabel(String status) {
		this.zStatusLabel.setText("Z = " + status);
	}

	/**
	 * @What_this_does Initializes the LiteTie program
	 * @Look_and_Feel Metal (Java cross-platform GUI)
	 */
	private void initialize() {
		try {
			int style = 2;
			if (style == 1) {

				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				// Nimbus kind of Stylish look
			} else if (style == 2) {
				UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			} else if (style == 3) {

				UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			}
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
		this.setPreferredSize(dimensionScreenSize);
		this.setSize(dimensionScreenSize);
		this.setMinimumSize(new Dimension(600, 600));
		this.setTitle("LiteTie");
		this.setBounds(
				new Rectangle(0, 22, (int) dimensionScreenSize.getWidth(), (int) dimensionScreenSize.getHeight()));
		this.setContentPane(getMainPane());
		this.setResizable(true);
		this.setJMenuBar(getMainMenuBar());
		this.setVisible(true);
		notTheFirstTime = false;
		Zoom.setZoomToFit(true);
		updateCanvas();
		Zoom.setZoomToFit(false);
		// START - STOPS THE USERS FROM CLOSING THE APP WITH UNSAVED INFORMATION
		// - 20131128_2209
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				if (!(world.getAllObjectsInWorld().isEmpty())) {
					int i = JOptionPane.showConfirmDialog(LiteTieTRIAL.this,
							"Quitting now may cause you to\nlose some of your work?\nWould you still like to quit?",
							"Confirm Quit", 0, 2);
					if (i == 0) {
						System.exit(0);
					}
				} else
					System.exit(0);
			}
		});
		this.setVisible(true);
		// END - STOPS THE USERS FROM CLOSING THE APP WITH UNSAVED INFORMATION -
		// 20131128_2209

		DialogPreferences defaults = new DialogPreferences();
		Object[] preferences = defaults.readPreferences();
		int i = 0;
		p_directoryPath = (String) preferences[i++];
		getSpinnerEnhanceAmount().setValue(p_holeEFactor = Float.parseFloat(preferences[i++].toString()));
		getSpinnerEnhanceAmount2().setValue(p_markerEFactor = Float.parseFloat(preferences[i++].toString()));
		getSpinnerEnhanceAmountTie().setValue(p_surfaceEFactor = Float.parseFloat(preferences[i++].toString()));
		p_defaultSupplier = (String) preferences[i++];
		getTieTextField1().setBackground(sColor1 = (Color) preferences[i++]);// c
		getTieTextField1().setText(p_tie1Amount = (String) preferences[i++]);// t
		getTieTextField2().setBackground(sColor2 = (Color) preferences[i++]);// c
		getTieTextField2().setText(p_tie2Amount = (String) preferences[i++]);// t
		getTieTextField3().setBackground(sColor3 = (Color) preferences[i++]);// c
		getTieTextField3().setText(p_tie3Amount = (String) preferences[i++]);// t
		getTieTextField4().setBackground(sColor4 = (Color) preferences[i++]);// c
		getTieTextField4().setText(p_tie4Amount = (String) preferences[i++]);// t
		getTieTextField5().setBackground(sColor5 = (Color) preferences[i++]);// c
		getTieTextField5().setText(p_tie5Amount = (String) preferences[i++]);// t
		getTieTextField6().setBackground(sColor6 = (Color) preferences[i++]);// c
		getTieTextField6().setText(p_tie6Amount = (String) preferences[i++]);// t

		getDetonatorTimeTextField1().setBackground(dColor1 = (Color) preferences[i++]);
		getDetonatorTimeTextField1().setText(p_downHole1Amount = (String) preferences[i++]);
		getDetonatorTimeTextField2().setBackground(dColor2 = (Color) preferences[i++]);
		getDetonatorTimeTextField2().setText(p_downHole2Amount = (String) preferences[i++]);
		getDetonatorTimeTextField3().setBackground(dColor3 = (Color) preferences[i++]);
		getDetonatorTimeTextField3().setText(p_downHole3Amount = (String) preferences[i++]);

		p_registration = (String) preferences[i++];
		p_userName = (String) preferences[i++];

		getGridOnOffButton().setSelected(p_showGrid = Boolean.parseBoolean(preferences[i++].toString()));
		getBoundaryOnOffButton().setSelected(p_showBoundary = Boolean.parseBoolean(preferences[i++].toString()));
		getTextOnOffButton().setSelected(p_showText = Boolean.parseBoolean(preferences[i++].toString()));
		getHolesOnOffButton().setSelected(p_showHoleSymbols = Boolean.parseBoolean(preferences[i++].toString()));
		getDummyOnOffButton().setSelected(p_showDummySymbols = Boolean.parseBoolean(preferences[i++].toString()));
		getTieOnOffButton().setSelected(p_showSurfaceConnections = Boolean.parseBoolean(preferences[i++].toString()));
		getTieDelayToggleButton()
				.setSelected(p_showSurfaceConnectionLabel = Boolean.parseBoolean(preferences[i++].toString()));
		getSurfaceTimesOnOffButton()
				.setSelected(p_showSurfaceTimes = Boolean.parseBoolean(preferences[i++].toString()));
		getDetonatorDelayButton().setSelected(p_showDetonatorTimes = Boolean.parseBoolean(preferences[i++].toString()));
		getContourButton().setSelected(p_showContours = Boolean.parseBoolean(preferences[i++].toString()));
		getFirstMovementButton().setSelected(p_showmovements = Boolean.parseBoolean(preferences[i++].toString()));
		getReliefButton().setSelected(p_showRelief = Boolean.parseBoolean(preferences[i++].toString()));
		getFRLCircleMarkerButton().setSelected(p_showFloorCircle = Boolean.parseBoolean(preferences[i++].toString()));
		getFRLMarkerButton().setSelected(p_showFloorLine = Boolean.parseBoolean(preferences[i++].toString()));
		getTRLCircleMarkerButton().setSelected(p_showToeCircle = Boolean.parseBoolean(preferences[i++].toString()));
		getTRLMarkerButton().setSelected(p_showToeLine = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleTracksOnOffButton().setSelected(p_showDrillTracks = Boolean.parseBoolean(preferences[i++].toString()));
		getDetsOnOffButton().setSelected(p_showDetonators = Boolean.parseBoolean(preferences[i++].toString()));
		getDetonatorDelayButton().setSelected(p_showDetonatorDelay = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleIDOnOffButton().setSelected(p_showHoleID = Boolean.parseBoolean(preferences[i++].toString()));
		getCollarRLOnOffButton().setSelected(p_showCollarLoc = Boolean.parseBoolean(preferences[i++].toString()));
		getFloorRLOnOffButton().setSelected(p_showFloorLoc = Boolean.parseBoolean(preferences[i++].toString()));
		getToeRLOnOffButton().setSelected(p_showToeLoc = Boolean.parseBoolean(preferences[i++].toString()));
		getDiameterOnOffButton().setSelected(p_showDiameter = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLengthOnOffButton().setSelected(p_showLength = Boolean.parseBoolean(preferences[i++].toString()));
		getAngleOnOffButton().setSelected(p_showHoleAngle = Boolean.parseBoolean(preferences[i++].toString()));
		getBenchHeightOnOffButton().setSelected(p_showBench = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleSubdrillOnOffButton().setSelected(p_showSubdrill = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLabel1OnOffButton().setSelected(p_showLabelOne = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLabel2OnOffButton().setSelected(p_showLabelTwo = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLabel3OnOffButton().setSelected(p_showLabelThree = Boolean.parseBoolean(preferences[i++].toString()));

		Visualise2D.getPreferredColours();

		p_holeTextColour = (Color) preferences[i++];// 56
		p_holeLengthTextColour = (Color) preferences[i++];
		p_labelOneTextColour = (Color) preferences[i++];
		p_labelTwoTextColour = (Color) preferences[i++];
		p_labelThreeTextColour = (Color) preferences[i++];// 60
		p_benchHeightTextColour = (Color) preferences[i++];
		p_subdrillTextColour = (Color) preferences[i++];
		p_holeDiameterTextColour = (Color) preferences[i++];
		p_holeBearingTextColour = (Color) preferences[i++];
		p_collarLocationTextColour = (Color) preferences[i++];// 65
		p_floorLocationTextColour = (Color) preferences[i++];
		p_toeLocationTextColour = (Color) preferences[i++];
		p_holeAngleTextColour = (Color) preferences[i++];
		canvasPanel.setBackground(p_backgroundCanvasColour = ((Color) preferences[i++]));
		p_selectedObjectsColour = (Color) preferences[i++];// 70
		p_gridLinesAndTextColour = (Color) preferences[i++];
		p_floorLocationFillColour = (Color) preferences[i++];
		p_floorLocationLineColour = (Color) preferences[i++];
		p_toeLocationFillColour = (Color) preferences[i++];
		p_toeLocationLineColour = (Color) preferences[i++];// 75
		p_detonatorDelayTextColour = (Color) preferences[i++];
		p_downHoleFiringTimesColour = (Color) preferences[i++];
		p_surfaceConnectorFiringTimesColour = (Color) preferences[i++];

		Visualise2D.getPreferredFont();
		p_defaultFont = (String) preferences[i++];
		p_defaultFontSize = Integer.parseInt((preferences[i++].toString()));

		default_Font = new Font(p_defaultFont, Font.PLAIN, p_defaultFontSize);
		p_applyScaling = Boolean.parseBoolean(preferences[i++].toString());
		p_tieInLineTolerance = Float.parseFloat((preferences[i++].toString()));

		i_backgroundCanvasColour = Visualise2D.getInverseColour(p_backgroundCanvasColour);

		updateCanvas();
	}

	public void refreshPreferences() {
		DialogPreferences defaults = new DialogPreferences();
		Object[] preferences = defaults.readPreferences();
		int i = 0;
		p_directoryPath = (String) preferences[i++];
		getSpinnerEnhanceAmount().setValue(p_holeEFactor = Float.parseFloat(preferences[i++].toString()));
		getSpinnerEnhanceAmount2().setValue(p_markerEFactor = Float.parseFloat(preferences[i++].toString()));
		getSpinnerEnhanceAmountTie().setValue(p_surfaceEFactor = Float.parseFloat(preferences[i++].toString()));
		p_defaultSupplier = (String) preferences[i++];
		getTieTextField1().setBackground(sColor1 = (Color) preferences[i++]);// c
		getTieTextField1().setText(p_tie1Amount = (String) preferences[i++]);// t
		getTieTextField2().setBackground(sColor2 = (Color) preferences[i++]);// c
		getTieTextField2().setText(p_tie2Amount = (String) preferences[i++]);// t
		getTieTextField3().setBackground(sColor3 = (Color) preferences[i++]);// c
		getTieTextField3().setText(p_tie3Amount = (String) preferences[i++]);// t
		getTieTextField4().setBackground(sColor4 = (Color) preferences[i++]);// c
		getTieTextField4().setText(p_tie4Amount = (String) preferences[i++]);// t
		getTieTextField5().setBackground(sColor5 = (Color) preferences[i++]);// c
		getTieTextField5().setText(p_tie5Amount = (String) preferences[i++]);// t
		getTieTextField6().setBackground(sColor6 = (Color) preferences[i++]);// c
		getTieTextField6().setText(p_tie6Amount = (String) preferences[i++]);// t

		getDetonatorTimeTextField1().setBackground(dColor1 = (Color) preferences[i++]);
		getDetonatorTimeTextField1().setText(p_downHole1Amount = (String) preferences[i++]);
		getDetonatorTimeTextField2().setBackground(dColor2 = (Color) preferences[i++]);
		getDetonatorTimeTextField2().setText(p_downHole2Amount = (String) preferences[i++]);
		getDetonatorTimeTextField3().setBackground(dColor3 = (Color) preferences[i++]);
		getDetonatorTimeTextField3().setText(p_downHole3Amount = (String) preferences[i++]);

		p_registration = (String) preferences[i++];
		p_userName = (String) preferences[i++];

		getGridOnOffButton().setSelected(p_showGrid = Boolean.parseBoolean(preferences[i++].toString()));
		getBoundaryOnOffButton().setSelected(p_showBoundary = Boolean.parseBoolean(preferences[i++].toString()));
		getTextOnOffButton().setSelected(p_showText = Boolean.parseBoolean(preferences[i++].toString()));
		getHolesOnOffButton().setSelected(p_showHoleSymbols = Boolean.parseBoolean(preferences[i++].toString()));
		getDummyOnOffButton().setSelected(p_showDummySymbols = Boolean.parseBoolean(preferences[i++].toString()));
		getTieOnOffButton().setSelected(p_showSurfaceConnections = Boolean.parseBoolean(preferences[i++].toString()));
		getTieDelayToggleButton()
				.setSelected(p_showSurfaceConnectionLabel = Boolean.parseBoolean(preferences[i++].toString()));
		getSurfaceTimesOnOffButton()
				.setSelected(p_showSurfaceTimes = Boolean.parseBoolean(preferences[i++].toString()));
		getDetonatorDelayButton().setSelected(p_showDetonatorTimes = Boolean.parseBoolean(preferences[i++].toString()));
		getContourButton().setSelected(p_showContours = Boolean.parseBoolean(preferences[i++].toString()));
		getFirstMovementButton().setSelected(p_showmovements = Boolean.parseBoolean(preferences[i++].toString()));
		getReliefButton().setSelected(p_showRelief = Boolean.parseBoolean(preferences[i++].toString()));
		getFRLCircleMarkerButton().setSelected(p_showFloorCircle = Boolean.parseBoolean(preferences[i++].toString()));
		getFRLMarkerButton().setSelected(p_showFloorLine = Boolean.parseBoolean(preferences[i++].toString()));
		getTRLCircleMarkerButton().setSelected(p_showToeCircle = Boolean.parseBoolean(preferences[i++].toString()));
		getTRLMarkerButton().setSelected(p_showToeLine = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleTracksOnOffButton().setSelected(p_showDrillTracks = Boolean.parseBoolean(preferences[i++].toString()));
		getDetsOnOffButton().setSelected(p_showDetonators = Boolean.parseBoolean(preferences[i++].toString()));
		getDetonatorDelayButton().setSelected(p_showDetonatorDelay = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleIDOnOffButton().setSelected(p_showHoleID = Boolean.parseBoolean(preferences[i++].toString()));
		getCollarRLOnOffButton().setSelected(p_showCollarLoc = Boolean.parseBoolean(preferences[i++].toString()));
		getFloorRLOnOffButton().setSelected(p_showFloorLoc = Boolean.parseBoolean(preferences[i++].toString()));
		getToeRLOnOffButton().setSelected(p_showToeLoc = Boolean.parseBoolean(preferences[i++].toString()));
		getDiameterOnOffButton().setSelected(p_showDiameter = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLengthOnOffButton().setSelected(p_showLength = Boolean.parseBoolean(preferences[i++].toString()));
		getAngleOnOffButton().setSelected(p_showHoleAngle = Boolean.parseBoolean(preferences[i++].toString()));
		getBenchHeightOnOffButton().setSelected(p_showBench = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleSubdrillOnOffButton().setSelected(p_showSubdrill = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLabel1OnOffButton().setSelected(p_showLabelOne = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLabel2OnOffButton().setSelected(p_showLabelTwo = Boolean.parseBoolean(preferences[i++].toString()));
		getHoleLabel3OnOffButton().setSelected(p_showLabelThree = Boolean.parseBoolean(preferences[i++].toString()));

		Visualise2D.getPreferredColours();

		p_holeTextColour = (Color) preferences[i++];// 56
		p_holeLengthTextColour = (Color) preferences[i++];
		p_labelOneTextColour = (Color) preferences[i++];
		p_labelTwoTextColour = (Color) preferences[i++];
		p_labelThreeTextColour = (Color) preferences[i++];// 60
		p_benchHeightTextColour = (Color) preferences[i++];
		p_subdrillTextColour = (Color) preferences[i++];
		p_holeDiameterTextColour = (Color) preferences[i++];
		p_holeBearingTextColour = (Color) preferences[i++];
		p_collarLocationTextColour = (Color) preferences[i++];// 65
		p_floorLocationTextColour = (Color) preferences[i++];
		p_toeLocationTextColour = (Color) preferences[i++];
		p_holeAngleTextColour = (Color) preferences[i++];
		canvasPanel.setBackground(p_backgroundCanvasColour = (Color) preferences[i++]);
		p_selectedObjectsColour = (Color) preferences[i++];// 70
		p_gridLinesAndTextColour = (Color) preferences[i++];
		p_floorLocationFillColour = (Color) preferences[i++];
		p_floorLocationLineColour = (Color) preferences[i++];
		p_toeLocationFillColour = (Color) preferences[i++];
		p_toeLocationLineColour = (Color) preferences[i++];// 75
		p_detonatorDelayTextColour = (Color) preferences[i++];
		p_downHoleFiringTimesColour = (Color) preferences[i++];
		p_surfaceConnectorFiringTimesColour = (Color) preferences[i++];

		Visualise2D.getPreferredFont();
		p_defaultFont = (String) preferences[i++];
		p_defaultFontSize = Integer.parseInt(preferences[i++].toString());

		default_Font = new Font(p_defaultFont, Font.PLAIN, p_defaultFontSize);
		p_applyScaling = Boolean.parseBoolean(preferences[i++].toString());
		p_tieInLineTolerance = Float.parseFloat(preferences[i++].toString());

		updateCanvas();
	}

	// HOLDER FOR ALL THE OTHER VIEWS
	private JPanel getMainPane() {
		if (mainPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(1);
			borderLayout.setVgap(1);
			statusBarLabel = new JLabel();
			String statusBarText = "LiteTie loaded...";
			statusBarLabel.setText(statusBarText);
			statusBarLabel.setPreferredSize(new Dimension(150, 15));
			statusBarLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
			statusBarLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			statusBarLabel.setComponentOrientation(ComponentOrientation.UNKNOWN);
			mainPane = new JPanel();
			mainPane.setLayout(borderLayout);
			mainPane.add(getLayerHolderSplitPane(), BorderLayout.CENTER);
			mainPane.add(getTieToolBarHolderPanel(), BorderLayout.NORTH);
			mainPane.add(getToolBarSideHolderPanel(), BorderLayout.WEST);
			mainPane.add(getStatusBarPanel(), BorderLayout.SOUTH);
		}
		return mainPane;
	}

	// TIE UP TOOLBAR
	/**
	 * This method initializes toolBarHolderPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getTieToolBarHolderPanel() {
		if (tieToolBarHolderPanel == null) {
			GridBagConstraints gridBagConstraints91 = new GridBagConstraints();
			gridBagConstraints91.insets = inset0;
			gridBagConstraints91.gridy = 1;
			gridBagConstraints91.gridx = 13;
			GridBagConstraints gridBagConstraints90 = new GridBagConstraints();
			gridBagConstraints90.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints90.gridy = 1;
			gridBagConstraints90.ipadx = 22;
			gridBagConstraints90.weightx = 1.0;
			gridBagConstraints90.gridx = 11;
			GridBagConstraints gridBagConstraints89 = new GridBagConstraints();
			gridBagConstraints89.gridx = 10;
			gridBagConstraints89.gridy = 1;
			GridBagConstraints gridBagConstraints88 = new GridBagConstraints();
			gridBagConstraints88.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints88.gridy = 1;
			gridBagConstraints88.ipadx = 22;
			gridBagConstraints88.weightx = 1.0;
			gridBagConstraints88.gridx = 9;
			GridBagConstraints gridBagConstraints87 = new GridBagConstraints();
			gridBagConstraints87.gridx = 8;
			gridBagConstraints87.gridy = 1;
			GridBagConstraints gridBagConstraints86 = new GridBagConstraints();
			gridBagConstraints86.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints86.gridy = 1;
			gridBagConstraints86.ipadx = 22;
			gridBagConstraints86.weightx = 1.0;
			gridBagConstraints86.gridx = 7;
			GridBagConstraints gridBagConstraints85 = new GridBagConstraints();
			gridBagConstraints85.gridx = 6;
			gridBagConstraints85.gridy = 1;
			GridBagConstraints gridBagConstraints84 = new GridBagConstraints();
			gridBagConstraints84.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints84.gridy = 1;
			gridBagConstraints84.ipadx = 22;
			gridBagConstraints84.weightx = 1.0;
			gridBagConstraints84.gridx = 5;
			GridBagConstraints gridBagConstraints83 = new GridBagConstraints();
			gridBagConstraints83.gridx = 4;
			gridBagConstraints83.gridy = 1;
			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints81.gridy = 1;
			gridBagConstraints81.ipadx = 22;
			gridBagConstraints81.weightx = 1.0;
			gridBagConstraints81.gridx = 3;
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridx = 2;
			gridBagConstraints71.gridy = 1;
			GridBagConstraints gridBagConstraints66 = new GridBagConstraints();
			gridBagConstraints66.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints66.gridy = 1;
			gridBagConstraints66.ipadx = 22;
			gridBagConstraints66.weightx = 1.0;
			gridBagConstraints66.gridx = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = inset0;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.insets = inset0;
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 120;
			gridBagConstraints.gridwidth = 14;
			tieToolBarHolderPanel = new JPanel();
			tieToolBarHolderPanel.setPreferredSize(new Dimension(32767, 60));
			tieToolBarHolderPanel.setLayout(new GridBagLayout());
			tieToolBarHolderPanel.add(getToolPanelTop(), gridBagConstraints);
			tieToolBarHolderPanel.add(getTieButton1(), gridBagConstraints1);
			tieToolBarHolderPanel.add(getTieTextField1(), gridBagConstraints66);
			tieToolBarHolderPanel.add(getTieButton2(), gridBagConstraints71);
			tieToolBarHolderPanel.add(getTieTextField2(), gridBagConstraints81);
			tieToolBarHolderPanel.add(getTieButton3(), gridBagConstraints83);
			tieToolBarHolderPanel.add(getTieTextField3(), gridBagConstraints84);
			tieToolBarHolderPanel.add(getTieButton4(), gridBagConstraints85);
			tieToolBarHolderPanel.add(getTieTextField4(), gridBagConstraints86);
			tieToolBarHolderPanel.add(getTieButton5(), gridBagConstraints87);
			tieToolBarHolderPanel.add(getTieTextField5(), gridBagConstraints88);
			tieToolBarHolderPanel.add(getTieButton6(), gridBagConstraints89);
			tieToolBarHolderPanel.add(getTieTextField6(), gridBagConstraints90);
			tieToolBarHolderPanel.add(getShowTabPaneButton(), gridBagConstraints91);

			buttonGroup2.add(getTieButton1());
			buttonGroup2.add(getTieButton2());
			buttonGroup2.add(getTieButton3());
			buttonGroup2.add(getTieButton4());
			buttonGroup2.add(getTieButton5());
			buttonGroup2.add(getTieButton6());

			//

		}
		return tieToolBarHolderPanel;
	}

	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();

			mainMenuBar.add(getLiteTieTRIALMenu());
			mainMenuBar.add(getFileMenu());
			mainMenuBar.add(getEditMenu());
			mainMenuBar.add(getViewMenu());
			mainMenuBar.add(getWindowMenu());
			mainMenuBar.add(getHelpMenu());
			// mainMenuBar.setVisible(true);

		}
		return mainMenuBar;
	}

	private JMenu getLiteTieTRIALMenu() {
		if (liteTieMenu == null) {
			liteTieMenu = new JMenu();
			liteTieMenu.setText("LiteTie");
			liteTieMenu.add(getAboutMenuItem());
			liteTieMenu.addSeparator();
			liteTieMenu.add(getPreferencesMenuItem());
			liteTieMenu.addSeparator();
			liteTieMenu.add(getQuitMenuItem());
		}
		return liteTieMenu;
	}

	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getNewMenu());
			fileMenu.add(getOpenMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getSaveAsMenuItem());
			fileMenu.addSeparator();
			fileMenu.add(getPageSetupMenuItem());
			fileMenu.add(getPrintMenuItem());
		}
		return fileMenu;
	}

	private JMenu getViewMenu() {
		if (viewMenu == null) {
			viewMenu = new JMenu();
			viewMenu.setText("View");
			viewMenu.add(getZoomInMenuItem());
			viewMenu.add(getZoomOutMenuItem());
			viewMenu.add(getFitInWindowMenuItem());
			viewMenu.add(getCenterPatternMenuItem());
			viewMenu.addSeparator();
			viewMenu.add(getShowMenu());
			viewMenu.addSeparator();
			viewMenu.add(getHideToolbarMenuItem());
			viewMenu.add(getCustomiseToolbarMenuItem());
		}
		return viewMenu;
	}

	private JMenu getWindowMenu() {
		if (windowMenu == null) {
			windowMenu = new JMenu();
			windowMenu.setText("Window");
		}
		return windowMenu;
	}

	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
		}
		return helpMenu;
	}

	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.setLocation(new Point(89, 0));
			editMenu.add(getUndoMenuItem());
			editMenu.add(getRedoMenuItem());
			editMenu.addSeparator();
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
			editMenu.addSeparator();
			editMenu.add(getSelectAllMenuItem());
			editMenu.add(getSelectAllInLayerMenu());
			editMenu.addSeparator();
			editMenu.add(initialiseWorld());
		}
		return editMenu;
	}

	private JMenu getNewMenu() {
		if (newMenu == null) {
			newMenu = new JMenu();
			newMenu.setText("New");
			newMenu.setForeground(Color.black);
			newMenu.add(getNewPatternMenuItem());
			newMenu.add(getNewHoleMenuItem());
			newMenu.setVisible(true);
		}
		return newMenu;
	}

	// MENU ITEM FOR CREATING A NEW PATTERN
	private JMenuItem getNewPatternMenuItem() {
		if (newPatternMenuItem == null) {
			newPatternMenuItem = new JMenuItem(new AbstractAction("Add Pattern...") {
				private static final long serialVersionUID = 1L;

				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						pattern = DialogCreatePattern.showBox(null);
					}

					catch (IllegalArgumentException e1) {
						System.out.println("getNewPatternMenuItem() method - IllegalArgumentException");
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"getNewPatternMenuItem() method - IllegalArgumentException");
						e1.printStackTrace();
					} catch (NegativeNumberException e1) {
						System.out.println("getNewPatternMenuItem() method - NegativeNumberException");
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"getNewPatternMenuItem() method - NegativeNumberException");
						e1.printStackTrace();
					} catch (ZeroArgumentException e1) {
						System.out.println("getNewPatternMenuItem() method - ZeroArgumentException");
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"getNewPatternMenuItem() method - ZeroArgumentException");
						e1.printStackTrace();
					}
					if (!(pattern == null)) {
						currentPattern = pattern;
						// world.setPatternList(patternList/*.add(shot)*/);
						try {
							world.addPattern(pattern, false);
						} catch (ZeroArgumentException e1) {
							e1.printStackTrace();
						} catch (NegativeNumberException e1) {
							e1.printStackTrace();
						}
						saveAsMenuItem.setEnabled(true);
						saveAsButton.setEnabled(true);
						updateCanvas();
					}
					statusBarLabel.setText("Pattern Created: " + world.getPatternList().size()
							+ " pattern(s). Current Pattern has " + currentPattern.getHoleList().size()
							+ " hole(s).");
				}
			});
		}
		return newPatternMenuItem;
	}

	private JMenuItem getNewHoleMenuItem() {
		if (newHoleMenuItem == null) {
			newHoleMenuItem = new JMenuItem();
			newHoleMenuItem.setText("Add Hole/Dummy Hole...");
			newHoleMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					try {
						dummy = DialogCreateHole.showBox(null, -1, -1, averageCRL, true);
					} catch (NumberFormatException e2) {
						System.out.println("getNewHoleMenuItem() method - NumberFormatException");
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"getNewHoleMenuItem() method - NumberFormatException");
						e2.printStackTrace();
					} catch (ZeroArgumentException e2) {
						System.out.println("getNewHoleMenuItem() method - ZeroArgumentException");
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"getNewHoleMenuItem() method - ZeroArgumentException");
						e2.printStackTrace();
					} catch (NegativeNumberException e2) {
						System.out.println("getNewHoleMenuItem() method - NegativeNumberException");
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"getNewHoleMenuItem() method - NegativeNumberException");
						e2.printStackTrace();
					}
					if (!(dummy == null)) {
						if (currentPattern == null) {
							Pattern firstPattern = new Pattern(0, 0);
							try {
								world.addPattern(firstPattern, false);
							} catch (ZeroArgumentException | NegativeNumberException e1) {
								e1.printStackTrace();
							}
							currentPattern = firstPattern;

						}
						try {
							currentPattern.addDummy(dummy, false);
						} catch (ZeroArgumentException e1) {
							System.out.println("getNewHoleMenuItem() method - ZeroArgumentException");
							JOptionPane.showMessageDialog(LiteTieTRIAL.this,
									"getNewHoleMenuItem() method - ZeroArgumentException");
							e1.printStackTrace();
						} catch (NegativeNumberException e1) {
							System.out.println("getNewHoleMenuItem() method - NegativeNumberException");
							JOptionPane.showMessageDialog(LiteTieTRIAL.this,
									"getNewHoleMenuItem() method - NegativeNumberException");
							e1.printStackTrace();
						}
						saveAsMenuItem.setEnabled(true);
						saveAsButton.setEnabled(true);
						updateCanvas();
					}
					statusBarLabel.setText("Pattern Created: " + world.getPatternList().size()
							+ " pattern(s). Current Pattern has " + currentPattern.getHoleList().size()
							+ " hole(s).");
				}
			});
		}
		return newHoleMenuItem;
	}

	private JMenuItem getUndoMenuItem() {
		if (undoMenuItem == null) {
			undoMenuItem = new JMenuItem();
			undoMenuItem.setText("Undo");
			undoMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (undoManager.canUndo()) {
						undoManager.undo();
					} else
						JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to complete request.",
								"Undo/Redo Error", JOptionPane.ERROR_MESSAGE);
					undoMenuItem.setEnabled(undoManager.canUndo());
					redoMenuItem.setEnabled(undoManager.canRedo());
				}
			});
		}
		return undoMenuItem;
	}

	private JMenuItem getRedoMenuItem() {
		if (redoMenuItem == null) {
			redoMenuItem = new JMenuItem();
			redoMenuItem.setText("Redo");
			redoMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (undoManager.canRedo()) {
						undoManager.redo();
					} else
						JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to complete request.",
								"Undo/Redo Error", JOptionPane.ERROR_MESSAGE);
					undoMenuItem.setEnabled(undoManager.canUndo());
					redoMenuItem.setEnabled(undoManager.canRedo());
				}
			});

		}
		return redoMenuItem;
	}

	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
		}
		return cutMenuItem;
	}

	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");

			updateCanvas();
		}
		return copyMenuItem;
	}

	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
		}
		return pasteMenuItem;
	}

	private JMenuItem initialiseWorld() {
		if (initialiseWorldMenuItem == null) {
			initialiseWorldMenuItem = new JMenuItem();
			initialiseWorldMenuItem.setText("Reset World");
			initialiseWorldMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!(world.getAllObjectsInWorld().isEmpty())) {
						// TODO Remove all from the world.
						JLabel message = new JLabel(
								"<html>This will clear all information<br>from the current world.<br><br>Are you sure?</html>",
								SwingConstants.CENTER);

						int reset = JOptionPane.showConfirmDialog(rootPane, message, "Reset World?",
								JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
						if (reset == 1) {
							System.out.println("User cancelled World reset");
						} else {
							world = new World();
							getTreeModelPattern().repaint();
							getTreeModelBoundary().repaint();
							getTreeModelSurface().repaint();
							updateCanvas();
							statusBarLabel.setText("The world has been reset");
							setConsoleOutput("World Reset");
						}

					}
				}
			});

			updateCanvas();
		}
		return initialiseWorldMenuItem;
	}

	private JMenuItem getSelectAllMenuItem() {
		if (selectAllMenuItem == null) {
			selectAllMenuItem = new JMenuItem();
			selectAllMenuItem.setText("Select All");
			selectAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (world.getPatternList() != null) {

						selected = world.getAllObjectsInWorld();
						updateCanvas();

						statusBarLabel.setText("All " + world.getNumberOfObjectsInWorld() + " object(s) are selected.");

					}
				}
			});

			updateCanvas();
		}
		return selectAllMenuItem;
	}

	public static JFrame showWindow() {

		LiteTieTRIAL mainWindow = new LiteTieTRIAL(null);
		mainWindow.setVisible(true);
		System.out.println("exited");
		return mainWindow;

	}

	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About LiteTie");
			aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JOptionPane.showMessageDialog(LiteTieTRIAL.this,
							"LiteTie was concieved by Brent Buffham " + "\n " + "\nCode by Chin-Apse Pty. Ltd.",
							"About LiteTie",
							JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return aboutMenuItem;
	}

	private JMenuItem getPreferencesMenuItem() {
		if (preferencesMenuItem == null) {
			preferencesMenuItem = new JMenuItem();
			preferencesMenuItem.setText("Preferences...");
			preferencesMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					File prefs = DialogPreferences.showBox(canvasPanel);
					refreshPreferences();
				}
			});
		}
		return preferencesMenuItem;
	}

	private JMenuItem getQuitMenuItem() {
		if (quitMenuItem == null) {
			quitMenuItem = new JMenuItem();
			quitMenuItem.setText("Quit");
			quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!(world.getAllObjectsInWorld().isEmpty())) {
						JOptionPane quitOption = new JOptionPane();
						int i = JOptionPane.showConfirmDialog(LiteTieTRIAL.this,
								"Quitting now may cause you to\nlose some of your work?\nWould you still like to quit?",
								"Confirm Quit", 0, 2);

						if (i == 0) {
							System.exit(0);
						}

					} else
						System.exit(0);
				}
			});

		}
		return quitMenuItem;
	}

	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Open...");
			openMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						FileTypeRegistry.FileMapping variable1 = DialogOpenFile.showBox();

						if (variable1.handler.getDescription().contains("Tab")
								|| variable1.handler.getDescription().contains("Comma")) {
							try {
								pattern = (Pattern) variable1.handler.openFile(LiteTieTRIAL.this,
										variable1.file.getAbsolutePath());
								System.out.println("OPENED FILE LOCATION = " + variable1.file.getAbsolutePath());

								if (!(pattern == null)) {
									currentPattern = pattern;
									world.addPattern(pattern, false);// patternList.add(shot);
									file = variable1;
									updateCanvas();
									saveMenuItem.setEnabled(true);
									saveAsMenuItem.setEnabled(true);
									saveButton.setEnabled(true);
									saveAsButton.setEnabled(true);
									getTreeModelPattern(); // This definitely
															// needs to be
															// updatePatTree()
									getTreeModelSurface();
									statusBarLabel.setText("File Opened: You have " + world.getPatternList().size()
											+ " pattern(s). Current Pattern has "
											+ currentPattern.getHoleList().size() + " hole(s).");
								}
								// if (variable1 == null) {
								// return;
								// }
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to open file.");
							}
						}

						if (variable1.handler.getDescription().contains(".ltf")) {
							try {
								world = (World) variable1.handler.openFile(LiteTieTRIAL.this,
										variable1.file.getAbsolutePath());
								// world = (World)
								// variable1.handler.openFile(LiteTieTRIAL.this,
								// variable1.file.getAbsolutePath());
								System.out.println("OPENED FILE LOCATION = " + variable1.file.getAbsolutePath());

								if (!(world == null)) {
									currentPattern = pattern;

									world.addPattern(pattern, true);
									world.addText(text, true);
									world.addBoundary(boundary, true);

									file = variable1;
									updateCanvas();
									saveMenuItem.setEnabled(true);
									saveAsMenuItem.setEnabled(true);
									saveButton.setEnabled(true);
									saveAsButton.setEnabled(true);
									getTreeModelPattern(); // This definitely
															// needs to be
															// updatePatTree()
									getTreeModelBoundary();
									getTreeModelSurface();
								}
								// if (variable1 == null) {
								// return;
								// }
							} catch (IOException ioe) {
								JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to open file.");
							}
						}

					} catch (ZeroArgumentException zae) {
						// TODO Auto-generated catch block
						zae.printStackTrace();
					} catch (NegativeNumberException nne) {
						// TODO Auto-generated catch block
						nne.printStackTrace();
					}

				}
			});

		}
		return openMenuItem;
	}

	private JMenuItem getPageSetupMenuItem() {
		if (pageSetupMenuItem == null) {
			pageSetupMenuItem = new JMenuItem();
			pageSetupMenuItem.setText("Page Setup...");
			pageSetupMenuItem.setEnabled(true);
			pageSetupMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					printJob = PrinterJob.getPrinterJob();
					// Displays a System Page Setup Dialog.
					if (pageFormat == null) {
						pageFormat = printJob.pageDialog(printJob.defaultPage());
					} else
						pageFormat = printJob.pageDialog(pageFormat);
				}
			});
		}
		return pageSetupMenuItem;
	}

	private JMenuItem getPrintMenuItem() {
		if (printMenuItem == null) {
			printMenuItem = new JMenuItem();
			printMenuItem.setText("Print...");
			printMenuItem.setEnabled(true);
			printMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getPrintButton().doClick();
				}
			});
		}
		return printMenuItem;
	}

	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setEnabled(false);
			saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getSaveButton().doClick();
				}
			});
		}
		return saveMenuItem;
	}

	private JMenuItem getSaveAsMenuItem() {
		if (saveAsMenuItem == null) {
			saveAsMenuItem = new JMenuItem();
			saveAsMenuItem.setText("Save As...");
			saveAsMenuItem.setEnabled(false);
			saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					FileTypeRegistry.FileMapping variable1 = DialogSaveFile.showBox();
					if (variable1 == null)
						return; // cancelled

					if (variable1.handler.getDescription().contains("Tab")) {
						try {
							System.out.println("Entered Tab Save");
							variable1.handler.saveFile(world.getFirstPattern(), variable1.file.getAbsolutePath());
							System.out.println("FIRST PATTERN SAVED = " + variable1.file.getAbsolutePath());
							statusBarLabel.setText("FIRST PATTERN SAVED = " + variable1.file.getAbsolutePath());

						} catch (IOException ioe) {
							JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to write file.");
						}
					} else if (variable1.handler.getDescription().contains("Comma")) {
						try {
							System.out.println("Entered Comma Save");
							variable1.handler.saveFile(world.getFirstPattern(), variable1.file.getAbsolutePath());
							System.out.println("FIRST PATTERN SAVED = " + variable1.file.getAbsolutePath());
							statusBarLabel.setText("FIRST PATTERN SAVED = " + variable1.file.getAbsolutePath());
						} catch (IOException ioe) {
							JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to write file.");
						}
					} else if (variable1.handler.getDescription().contains("LiteTie")) {

						try {
							System.out.println("Entered LiteTie Save");
							variable1.handler.saveFile(world, variable1.file.getAbsolutePath());
							System.out.println("WORLD SAVED = " + variable1.file.getAbsolutePath());
							statusBarLabel.setText("WORLD SAVED = " + variable1.file.getAbsolutePath());

						} catch (IOException ioe) {
							JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to write file.");
						}
					}

					else {
						if (world.getAllObjectsInWorld().isEmpty()) {
							JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to write file.");
						}
						if (pattern == null) {
							statusBarLabel.setText("Lite Tie was Unable to write File");
						}
						return;
					}
					saveButton.setEnabled(true);

				}
			});

		}
		return saveAsMenuItem;
	}

	int expand = 10; // Reference this in the Preferences dialog.

	protected boolean isVKSDown;
	protected boolean isVKPDown;
	protected boolean isVKBDown;

	protected boolean isVKTDown;

	protected boolean isVKDDown;

	protected boolean isVKHDown;

	protected boolean isVKXDown;
	protected boolean isVKShiftDown;
	protected boolean isVKCDown;
	protected boolean freezeCanvas;

	/*
	 * canvasSize determines how large the canvas is at startup
	 */
	public Rectangle2D getCanvasSize() {
		// Below makes a rectangle starting at 1000mE and 5000mN with a width of
		// 45m and a height of 40m for the starting canvasSize()
		Rectangle2D tempBounds = userZoom;

		// System.out.println("canvasSize() Called - REFRESH SCREEN");

		// if the coordinate list size is greater than 0 make the canvasSize()
		// equal to the boundary of all patterns in the coordList
		if (world.getCoordList().size() > 0 && Zoom.isZoomToFit() == true) {
			tempBounds = userZoom = new Rectangle2D.Double(
					world.getBounds().getX() - world.getBounds().getWidth() / 100 * /*
																					 * this
																					 * is
																					 * the
																					 * percentage
																					 * extra
																					 * --
																					 * --
																					 * ->
																					 */(expand),
					world.getBounds().getY()
							- world.getBounds().getWidth() / 100 * /*
																	 * this is the
																	 * percentage
																	 * extra----->
																	 */(expand),
					world.getBounds().getWidth() + world.getBounds().getWidth() / 50 * /*
																						 * this
																						 * is
																						 * the
																						 * percentage
																						 * extra
																						 * --
																						 * --
																						 * ->
																						 */(expand), world.getBounds()
							.getHeight() + world.getBounds().getWidth() / 50 * /*
																				 * this
																				 * is
																				 * the
																				 * percentage
																				 * extra
																				 * --
																				 * --->
																				 */(expand));

			userZoom = tempBounds;
			setConsoleOutput( // CONSOLE OUTPUT JTEXTAREA ADDED - 20131201-1055
					"\nZOOM BOUNDS\nX = " + decimalFormatMetres2.format(userZoom.getX()) + "\nY = "
							+ decimalFormatMetres2.format(tempBounds.getY())// userZoomMarquee.getY()
																			// -
																			// userZoomMarquee.getHeight())
							+ "\nW = " + decimalFormatMetres2.format(userZoom.getWidth()) + "\nH = "
							+ decimalFormatMetres2.format(userZoom.getHeight()));

		}

		if (Zoom.isZoomToFit() == false /* && userZoomMarquee != null */) {

			tempBounds = new Rectangle2D.Double((userZoom.getX()), (userZoom.getY() - userZoom.getHeight()),
					(userZoom.getWidth()), (userZoom.getHeight()));
			setCanvasSize((userZoom.getX()), (userZoom.getY()), (userZoom.getWidth()), (userZoom.getHeight()));
		}

		return tempBounds;

	}

	private Rectangle2D setCanvasSize(double x, double y, double w, double h) {

		Rectangle2D tempBounds = new Rectangle2D.Double(x, h - y, w, h);

		return tempBounds;
	}

	// private Rectangle2D zoomedSize(Rectangle2D tempBounds) {
	// return
	// Zoom.zoom(new
	// Rectangle2D.Double(UnitConvert.metersToPixels(tempBounds.getX()),
	// UnitConvert.metersToPixels(tempBounds.getY()),
	// UnitConvert.metersToPixels(tempBounds.getWidth()),
	// UnitConvert.metersToPixels(tempBounds.getHeight())));
	// }
	// Rectangle2D currentBounds = zoomedSize(canvasSize());

	public void updateCanvas() {
		world.addAllCoordinates();
		// System.out.println("isZoomToFit = "+Zoom.isZoomToFit());

		if (Zoom.isZoomToFit() == true) {
			Rectangle2D bounds = getCanvasSize();
			if (!(bounds.getWidth() == 0 && bounds.getHeight() == 0)) {
				Zoom.setScalingFactor(
						Math.min(canvasScrollPane.getWidth() / UnitConvert.metersToPixels(bounds.getWidth()),
								canvasScrollPane.getHeight() / UnitConvert.metersToPixels(bounds.getHeight())));
			}

			userZoom = getCanvasSize();
		} else if (Zoom.isZoomToFit() == false && zoomPoint1 == null) {

			Zoom.getScalingFactor();
		} else if (Zoom.isZoomToFit() == false) {
			if (userZoom != null) {
				Rectangle2D bounds = setCanvasSize((userZoom.getX()), (userZoom.getY()), (userZoom.getWidth()),
						(userZoom.getHeight()));

				// System.out.println(canvasSize().toString());
				//
				if (!(bounds.getWidth() == 0 && bounds.getHeight() == 0)) {

					Zoom.setScalingFactor(Math
							.abs(Math.min(canvasScrollPane.getWidth() / UnitConvert.metersToPixels(userZoom.getWidth()),
									canvasScrollPane.getHeight() / UnitConvert.metersToPixels(userZoom.getHeight()))));
					// Zoom.zoom(new Rectangle2D.Double(userZoom.getX(),
					// userZoom.getY(), userZoom.getWidth(),
					// userZoom.getHeight()));
				}
			}

		}
		Visualise2D.getPreferredFont();
		canvasPanel.revalidate();
		canvasPanel.repaint();
	}

	public double averageAllHoleSizes() {
		double diameter = 0;
		double totalDiameter = 0;
		int totalHoles = 0;
		int totalHolesAll = 0;
		double averageHoleSize;

		for (Pattern tempPat : world.getPatternList().values()) {
			totalHoles = tempPat.getNumberOfHoles();
			totalHolesAll = totalHolesAll + totalHoles;
			diameter = 0;
			for (Dummy tempDummy : tempPat.getAllHoles()) {
				if (tempDummy instanceof Hole) {
					diameter = ((Hole) tempDummy).getDiameter() + diameter;
				}
			}
			totalDiameter = totalDiameter + diameter;
		}
		if (totalHolesAll != 0) {
			averageHoleSize = totalDiameter / totalHolesAll;
		} else
			averageHoleSize = 100;
		// if (averageHoleSize < UnitConvert.pixelsToMeters(100))
		// averageHoleSize =
		// UnitConvert.pixelsToMeters(100)*Zoom.getScalingFactor();

		return averageHoleSize;

	}

	/**
	 * 
	 * @return Average Collar Level for the current pattern or if no pattern
	 *         returns 100 (double values)
	 */
	public double averageCurrentPatternCRL() {

		double cRL = 0;
		double totalCRL = 0;
		int totalHoles = 0;

		if (totalHoles != 0) {
			int tempPatNumber = world.getPatternList().get(currentPattern).getPatternID();
			Pattern tempPat = world.getPatternList().get(tempPatNumber);
			totalHoles = tempPat.getNumberOfHoles();

			for (Dummy tempDummy : tempPat.getAllHoles()) {
				if (tempDummy instanceof Hole) {

					cRL = ((Hole) tempDummy).getZ() + cRL;
				}

			}
			totalCRL = totalCRL + cRL;
			averageCRL = totalCRL / totalHoles;
		} else
			averageCRL = 500;

		return averageCRL;

	}

	/**
	 * This method initializes zoomInMenuItem
	 * 
	 * @sameAs getZoomInButton() method
	 * @return zoomInMenuItem
	 */
	private JMenuItem getZoomInMenuItem() {
		if (zoomInMenuItem == null) {

			zoomInMenuItem = new JMenuItem();
			zoomInMenuItem.setText("Zoom In");
			zoomInMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getZoomInButton().doClick();
				}
			});

		}
		return zoomInMenuItem;
	}

	/**
	 * This method initializes zoomOutMenuItem
	 * 
	 * @sameAs getZoomOutButton() method
	 * @return zoomOutMenuItem
	 */
	private JMenuItem getZoomOutMenuItem() {
		if (zoomOutMenuItem == null) {
			zoomOutMenuItem = new JMenuItem();
			zoomOutMenuItem.setText("Zoom Out");
			zoomOutMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getZoomOutButton().doClick();
				}
			});
		}
		return zoomOutMenuItem;
	}

	/**
	 * This method initializes fitInWindowMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getFitInWindowMenuItem() {
		if (fitInWindowMenuItem == null) {
			fitInWindowMenuItem = new JMenuItem();
			fitInWindowMenuItem.setText("Fit In Window");
			fitInWindowMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Turn off the zoom to fit function.
					// userZoomMarquee = null;
					getZoomToFitButton().doClick();
				}
			});
		}
		return fitInWindowMenuItem;
	}

	/**
	 * This method initializes hideToolbarMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getHideToolbarMenuItem() {
		if (hideToolbarMenuItem == null) {
			hideToolbarMenuItem = new JMenuItem();
			hideToolbarMenuItem.setText("Hide Toolbar");
		}
		return hideToolbarMenuItem;
	}

	/**
	 * This method initializes customiseToolbarMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCustomiseToolbarMenuItem() {
		if (customiseToolbarMenuItem == null) {
			customiseToolbarMenuItem = new JMenuItem();
			customiseToolbarMenuItem.setText("Customize Toolbar");
		}
		return customiseToolbarMenuItem;
	}

	/**
	 * This method initializes centerPatternMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getCenterPatternMenuItem() {
		if (centerPatternMenuItem == null) {
			centerPatternMenuItem = new JMenuItem();
			centerPatternMenuItem.setText("Center Current Pattern");
		}
		return centerPatternMenuItem;
	}

	/**
	 * This method initializes showMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getShowMenu() {
		if (showMenu == null) {
			showMenu = new JMenu();
			showMenu.setText("Display Options");
			showMenu.add(getGridCheckBoxMenuItem());
		}
		return showMenu;
	}

	/**
	 * This method initializes gridCheckBoxMenuItem
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private JCheckBoxMenuItem getGridCheckBoxMenuItem() {
		if (gridCheckBoxMenuItem == null) {
			gridCheckBoxMenuItem = new JCheckBoxMenuItem();
			gridCheckBoxMenuItem.setText("Grid Lines");
		}
		return gridCheckBoxMenuItem;
	}

	/**
	 * This method initializes undoButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getUndoButton() {
		if (undoButton == null) {
			undoButton = new JButton();
			undoButton.setMinimumSize(dimension30x30);
			undoButton.setMaximumSize(dimension30x30);
			undoButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/undo.png")));
			undoButton.setPreferredSize(dimension30x30);

			undoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (undoManager.canUndo()) {
						undoManager.undo();
						statusBarLabel.setText(undoManager.getPresentationName());
						updateCanvas();
					} else
						JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to complete request.",
								"Undo/Redo Error", JOptionPane.ERROR_MESSAGE);

					undoMenuItem.setEnabled(undoManager.canUndo());
					redoMenuItem.setEnabled(undoManager.canRedo());
				}
			});
		}
		return undoButton;
	}

	/**
	 * This method initializes redoButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRedoButton() {
		if (redoButton == null) {
			redoButton = new JButton();
			redoButton.setMinimumSize(dimension30x30);
			redoButton.setMaximumSize(dimension30x30);
			redoButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/redo.png")));
			redoButton.setPreferredSize(dimension30x30);
			redoButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if (undoManager.canRedo()) {
						undoManager.redo();
						updateCanvas();
					} else
						JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to complete request.",
								"Undo/Redo Error", JOptionPane.ERROR_MESSAGE);
					undoMenuItem.setEnabled(undoManager.canUndo());
					redoMenuItem.setEnabled(undoManager.canRedo());
				}
			});
		}
		return redoButton;
	}

	/**
	 * This method initializes LayerHolderSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getLayerHolderSplitPane() {
		if (layerHolderSplitPane == null) {
			layerHolderSplitPane = new JSplitPane();
			layerHolderSplitPane.setDividerSize(10);
			layerHolderSplitPane.setDividerLocation(((int) (dimensionScreenSize.getWidth() * 0.75)));
			layerHolderSplitPane.setContinuousLayout(false);
			layerHolderSplitPane.setResizeWeight(1.0D);
			layerHolderSplitPane.setRightComponent(getLayerHolderPanel());
			layerHolderSplitPane.setLeftComponent(getCanvas());
			layerHolderSplitPane.setOneTouchExpandable(true);
		}
		return layerHolderSplitPane;
	}

	/**
	 * This method initializes openButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setMinimumSize(dimension30x30);
			openButton.setMaximumSize(dimension30x30);
			openButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/open.png")));
			openButton.setPreferredSize(dimension30x30);
			openButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						FileTypeRegistry.FileMapping variable1 = DialogOpenFile.showBox();
						if (variable1 == null) {
							return;
						}
						pattern = (Pattern) variable1.handler.openFile(LiteTieTRIAL.this,
								variable1.file.getAbsolutePath());

						if (!(pattern == null)) {
							currentPattern = pattern;
							world.addPattern(pattern, true);// patternList.add(shot);

							file = variable1;

							updateCanvas();
							saveMenuItem.setEnabled(true);
							saveAsMenuItem.setEnabled(true);
							saveAsButton.setEnabled(true);
							saveButton.setEnabled(true);
							getTreeModelPattern();
							getTreeModelSurface();
							statusBarLabel.setText("File Opened: You have " + world.getPatternList().size()
									+ " pattern(s). Current Pattern has "
									+ currentPattern.getHoleList().size() + " hole(s).");
						}

					} catch (FileNotFoundException fnfe) {
						JOptionPane.showMessageDialog(LiteTieTRIAL.this, "File Not Found.");
						statusBarLabel.setText("The file you requested couldn't be found...");
					} catch (ZeroArgumentException zae) {
						// TODO Auto-generated catch block
						zae.printStackTrace();
					} catch (NegativeNumberException nne) {
						// TODO Auto-generated catch block
						nne.printStackTrace();
					}

				}
			});
		}
		return openButton;
	}

	/**
	 * This method initializes saveButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton();
			saveButton.setMinimumSize(dimension30x30);
			saveButton.setMaximumSize(dimension30x30);
			saveButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/save.png")));
			saveButton.setPreferredSize(dimension30x30);
			saveButton.setEnabled(false);
			saveButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						file.handler.saveFile(world.getFirstPattern()/*
																		 * Fix
																		 * .getFirst
																		 * when more
																		 * I have a
																		 * .ltf
																		 * (LiteTieTRIAL
																		 * File
																		 * Format)
																		 */, file.file.getAbsolutePath());

					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to write file.");
					}
				}
			});
		}
		return saveButton;
	}

	/**
	 * This method initializes printButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getPrintButton() {
		if (printButton == null) {
			printButton = new JButton();
			printButton.setMinimumSize(dimension30x30);
			printButton.setMaximumSize(dimension30x30);
			printButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/print.png")));
			printButton.setPreferredSize(dimension30x30);
			printButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					try {
						PageFormat pf = new PageFormat();
						pf = pageFormat;
						printJob.setPrintable(new LiteTiePrinter(), pf);
						if (printJob.printDialog())
							try {
								printJob.print();
							} catch (PrinterException pe) {
								System.out.println("Error printing: " + pe);
							}
					} catch (NullPointerException e1) {
						JOptionPane.showMessageDialog(null, "Printing was cancelled: \"Page Setup\" was null");
						System.out.println("Print Cancelled");
					}
				}
			});

		}
		return printButton;
	}

	// DRAWS ALL THE PRINTING STUFF
	public class LiteTiePrinter implements Printable {

		public int print(Graphics g, PageFormat pageFormat, int page) throws PrinterException {

			// We have only one page, and 'page'
			// is zero-based
			if (page > 0) {
				return NO_SUCH_PAGE;
			}

			// User (0,0) is typically outside the
			// imageable area, so we must translate
			// by the X and Y values in the PageFormat
			// to avoid clipping.
			Graphics2D g2d = (Graphics2D) g;

			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

			// Now we perform our rendering
			Printing2D printer = new Printing2D();
			// Visualise2D printer = new Visualise2D();

			// Graphics2D g2d = (Graphics2D) g;

			Rectangle2D bounds = getCanvasSize();
			// Must do this to get preference colours and fonts
			Printing2D.getPreferredColours();
			Printing2D.getPreferredFont();

			Rectangle2D rootView = new Rectangle2D.Double(canvasScrollPane.getX(), canvasScrollPane.getY(),
					canvasScrollPane.getWidth(), canvasScrollPane.getHeight());
			Point2D p2D = new Point2D.Double(rootView.getX(), rootView.getY());
			double scaleX = pageFormat.getImageableWidth() / rootView.getWidth();
			double scaleY = pageFormat.getImageableHeight() / rootView.getHeight();
			double scalePrint = 0;
			if (rootView.getWidth() >= rootView.getHeight()) {
				scaleX = pageFormat.getImageableWidth() / rootView.getWidth();
				scaleY = scaleX;
				scalePrint = scaleX;
			}
			if (rootView.getHeight() > rootView.getWidth()) {
				scaleY = pageFormat.getImageableHeight() / rootView.getHeight();
				scaleX = scaleY;
				scalePrint = scaleY;
			}
			System.out.println("CanvasW = " + rootView.getWidth());
			System.out.println("CanvasH = " + rootView.getHeight());
			System.out.println("pageW = " + pageFormat.getImageableWidth());
			System.out.println("pageH = " + pageFormat.getImageableHeight());
			System.out.println("ScaleX = " + scaleX);
			System.out.println("ScaleY = " + scaleY);

			Rectangle2D rootView2 = new Rectangle2D.Double(canvasScrollPane.getX(), canvasScrollPane.getY(),
					pageFormat.getImageableWidth(), pageFormat.getImageableHeight());

			printer.paintPrintDimensions(g2d, rootView2, p2D, scalePrint, pageFormat);

			g2d.scale(scaleX, scaleY);

			averageSize = averageAllHoleSizes();

			for (Pattern tempPat : world.getPatternList().values()) {
				for (InitiationPoint i : tempPat.getiPList().values()) {
					boolean isSelected = i == selected
							|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(i));
					if (i.getIPDummy() instanceof Dummy || i.getIPDummy() instanceof Hole) {
						printer.paintIP(g2d, i.getIPDummy(), bounds, averageSize,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected, holeFactor);
					}
				}
			}
			for (Text tempText : world.getTextList().values()) {

				boolean isSelected = tempText == selected
						|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(tempText));

				Text t = tempText;

				if (textOnOffButton.isSelected()) {
					if (t instanceof Text) {
						printer.paintText(g2d, t.getColor(), t, default_Font, t.getBearing(), bounds,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
								toolRotateButton.isSelected(), holeFactor);
					}
				}
			}
			for (Boundary tempBoundary : world.getBoundaryList().values()) {
				boolean isSelected = tempBoundary == selected
						|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(tempBoundary));
				Boundary b = tempBoundary;

				if (boundaryOnOffButton.isSelected()) {
					if (tempBoundary instanceof Boundary) {
						printer.paintPolyLine(g2d, b.getColor(), b.getFillColor(), b.getStroke(), b, coord1, coord2,
								bounds,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected, b.isClosed(),
								b.getArrow(), b.getAnnotated());
					}

				}
				for (BPoint i : tempBoundary.getBPList().values()) {
					boolean isSelected1 = i == selected
							|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(i));
					BPoint bp = i;
					if (boundaryOnOffButton.isSelected()) {
						if (bp instanceof BPoint) {
							printer.paintBPoint(g2d, bp, tempBoundary, mouseClickX, mouseClickY, centerBoundsX,
									centerBoundsY, bounds,
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected1,
									getToolMoveButton().isSelected(), holeFactor);
						}
					}
				}

			}
			// Draws Ties
			for (Pattern tempPat : world.getPatternList().values()) {

				for (SurfaceConnector s : tempPat.getSurfaceList().values()) {
					// UNCOMMENT PAINTPOLYBOUNDS IF YOU WISH TO VIEW THE BOUND
					// OF A SURFACE CONNECTOR
					// renderer.paintPolyBounds(g2, s, bounds,
					// Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
					boolean isSelected = s == selected
							|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(s));

					if (tieOnOffButton.isSelected()) {
						if (s.getFrom() instanceof Dummy && s.getTo() instanceof Dummy) {
							printer.paintTie(g2d, s, bounds, averageSize, averageSize, s.getColor(),
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
									surfaceFactor, holeFactor);
						} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Dummy) {
							printer.paintTie(g2d, s, bounds, ((Hole) s.getFrom()).getDiameter(), averageSize,
									s.getColor(),
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected, surfaceFactor,
									holeFactor);
						} else if (s.getFrom() instanceof Dummy && s.getTo() instanceof Hole) {
							printer.paintTie(g2d, s, bounds, averageSize, ((Hole) s.getTo()).getDiameter(),
									s.getColor(), Zoom.getScalingFactor() * UnitConvert.metersToPixels(1),
									isSelected, surfaceFactor, holeFactor);
						} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Hole) {
							printer.paintTie(g2d, s, bounds, ((Hole) s.getFrom()).getDiameter(),
									((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()
											* UnitConvert.metersToPixels(1),
									isSelected, surfaceFactor, holeFactor);
						}
					}
				}
			}
			// Draw holes
			for (Pattern tempPat : world.getPatternList().values()) { // For
																		// every
																		// pattern
																		// in
																		// the
																		// pattern
																		// list
																		// do
																		// the
																		// following
				for (Dummy tempDummy : tempPat.getAllDummysAndHoles()) { // and
																			// for
																			// every
																			// dummy
																			// /
																			// hole
																			// in
																			// tempPat
																			// do
																			// the
																			// following//
																			// the
																			// above
																			// two
																			// statements
																			// mean
																			// that
																			// every
																			// thing
																			// below
																			// will
																			// be
																			// done
																			// to
																			// every
																			// hole
																			// or
																			// dummy
																			// in
																			// all
																			// patterns
																			// on
																			// the
																			// canvas
					boolean isSelected = tempDummy == selected
							|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(tempDummy));// isSelected
																														// is
																														// saying
																														// that
																														// all
																														// holes
																														// and
																														// dummys
																														// in
																														// the
																														// selectedObject
																														// Object
																														// or
																														// in
																														// the
																														// collection
																														// of
																														// selected
																														// objects
																														// will
																														// be
																														// affected
					Dummy d = tempDummy;

					if (tempDummy instanceof Dummy && (!(tempDummy instanceof Hole)) && dummyOnOffButton.isSelected()) {
						printer.paintDummy(g2d, d, d.getX() - deltaEasting, d.getY() - deltaNorthing, bounds,
								averageSize, Zoom.getScalingFactor() * UnitConvert.metersToPixels(1),
								isSelected, toolMoveButton.isSelected(), holeFactor);
						if (holeIDOnOffButton.isSelected()) {
							if (tempDummy instanceof Dummy && (!(tempDummy instanceof Hole))) {
								printer.paintHoleID(g2d, tempDummy, null, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
							}
						}
					}
					if (tempDummy instanceof Hole) {
						Hole h = (Hole) tempDummy;

						if (fRLMarkerButton.isSelected()) {
							if (h.getAngle() != 90 || !(holesOnOffButton.isSelected()) || markerFactor > holeFactor) {
								printer.paintFloorLine(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
										markerFactor);
							}
						}
						if (tRLMarkerButton.isSelected()) {
							if (h.getAngle() != 90 || !(holesOnOffButton.isSelected()) || markerFactor > holeFactor) {
								printer.paintToeLine(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
										markerFactor);
							}
						}
						if (fRLCircleMarkerButton.isSelected()) {
							if (h.getAngle() != 90 || !(holesOnOffButton.isSelected()) || markerFactor > holeFactor) {
								printer.paintFloorCircle(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
										markerFactor);
							}
						}
						if (tRLCircleMarkerButton.isSelected()) {
							if (h.getAngle() != 90 || !(holesOnOffButton.isSelected()) || markerFactor > holeFactor) {
								printer.paintToeCircle(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
										markerFactor);
							}
						}
						if (holeTracksOnOffButton.isSelected()) {
							if (h.getAngle() != 90) {
								printer.paintTrack(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
										toolRotateButton.isSelected());
							}
						}
						if (tempDummy instanceof Hole && holesOnOffButton.isSelected()) {

							printer.paintHole(g2d, h, h.getX() - deltaEasting, h.getY() - deltaNorthing, mouseClickX,
									mouseClickY, bounds,
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor, isSelected,
									toolMoveButton.isSelected(), h.getShape(), surfaceFactor);

						}
						if (holeSubdrillOnOffButton.isSelected()) {
							printer.paintSubdrill(g2d, h, bounds,
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
						}
						if (collarRLOnOffButton.isSelected()) {
							printer.paintZLevel(g2d, h, bounds,
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
						}
						if (toeRLOnOffButton.isSelected()) {
							printer.paintToeRL(g2d, h, bounds, Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
						}
						if (floorRLOnOffButton.isSelected()) {
							printer.paintFloorRL(g2d, h, bounds,
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
						}
						if (holeIDOnOffButton.isSelected()) {
							if (tempDummy instanceof Hole) {
								printer.paintHoleID(g2d, tempDummy, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
							}
						}
						if (holeLabel1OnOffButton.isSelected()) {
							if (tempDummy instanceof Hole) {
								printer.paintHoleLabel1(g2d, tempDummy, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
							}
						}
						if (holeLabel2OnOffButton.isSelected()) {
							if (tempDummy instanceof Hole) {
								printer.paintHoleLabel2(g2d, tempDummy, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
							}
						}
						if (holeLabel3OnOffButton.isSelected()) {
							if (tempDummy instanceof Hole) {
								printer.paintHoleLabel3(g2d, tempDummy, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
							}
						}
						if (diameterOnOffButton.isSelected()) {
							if (tempDummy instanceof Hole) {
								printer.paintDiameter(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
							}
						}
						if (angleOnOffButton.isSelected()) {
							if (tempDummy instanceof Hole) {
								printer.paintAngle(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
							}
						}
						if (bearingOnOffButton.isSelected()) {
							if (tempDummy instanceof Dummy) {
								printer.paintBearing(g2d, h, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
							}
						}
						if (holeLengthOnOffButton.isSelected()) {
							printer.paintHoleLength(g2d, h, bounds, holeFactor,
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
						}
						if (benchHeightOnOffButton.isSelected()) {
							printer.paintBench(g2d, h, bounds, Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
						}
					}
				}
			}
			// Draws Detonators
			for (Pattern tempPat : world.getPatternList().values()) {
				for (Detonator d : tempPat.getDetonatorList().values()) {
					boolean isSelected = d == selected
							|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(d));

					if (detsOnOffButton.isSelected()) {
						if (d.getInHole() instanceof Hole) {
							printer.paintDetonator(g2d, d.getInHole(), bounds, d.getInHole().getDiameter(),
									d.getColor(), Zoom.getScalingFactor() * UnitConvert.metersToPixels(1),
									isSelected, holeFactor);
						}

					}
				}
			}

			// Draw Delay Times
			for (Pattern tempPat : world.getPatternList().values()) {
				for (SurfaceConnector s : tempPat.getSurfaceList().values()) {
					// UNCOMMENT PAINTPOLYBOUNDS IF YOU WISH TO VIEW THE BOUND
					// OF A SURFACE CONNECTOR
					// renderer.paintPolyBounds(g2, s, bounds,
					// ZoomScale.getZoomX()* UnitConvert.metersToPixels(1));
					boolean isSelected = s == selected
							|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(s));

					if (tieDelayToggleButton.isSelected()) {
						if (s.getFrom() instanceof Dummy && s.getTo() instanceof Dummy) {
							printer.paintDelay(g2d, s, bounds, averageSize, averageSize, s.getColor(),
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
									surfaceFactor, holeFactor);
						} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Dummy) {
							printer.paintDelay(g2d, s, bounds, ((Hole) s.getFrom()).getDiameter(), averageSize,
									s.getColor(),
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected, surfaceFactor,
									holeFactor);
						} else if (s.getFrom() instanceof Dummy && s.getTo() instanceof Hole) {
							printer.paintDelay(g2d, s, bounds, averageSize, ((Hole) s.getTo()).getDiameter(),
									s.getColor(),
									Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected, surfaceFactor,
									holeFactor);
						} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Hole) {
							printer.paintDelay(g2d, s, bounds, ((Hole) s.getFrom()).getDiameter(),
									((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()
											* UnitConvert.metersToPixels(1),
									isSelected, surfaceFactor, holeFactor);
						}
					}
				}
			}
			// Draws SurfaceTimes
			if (surfaceTimesOnOffButton.isSelected()) {
				try {
					getSurfaceTimes();
				} catch (NegativeNumberException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (Pattern tempPat : world.getPatternList().values()) {
					for (SurfaceConnector sc : tempPat.getSurfaceList().values()) {
						// boolean isSelected = sc == selected || (selected
						// instanceof Collection<?> &&
						// ((Collection<?>)selected).contains(sc));
						for (InitiationPoint ip : tempPat.getiPList().values()) {
							if (sc.getTo() instanceof Dummy) {
								printer.paintSurfaceTimes(g2d, sc, ip, sc.getTo(), p_surfaceConnectorFiringTimesColour,
										bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), surfaceFactor,
										holeFactor);
							}
						}
					}
				}
			}

			// GRID RESIZING WITH ZOOM
			if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() > 0.0030) {
				printer.paintGrid(
						// GRAPHICS OBJECT
						g2d,
						// GET THE BOUNDS OF THE AREA TO DRAW IN
						canvasPanel.getBounds(),
						// DRAW LINES PORTION
						new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
								(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10)), (Zoom
										.getScalingFactor() * UnitConvert.metersToPixels(10))),
						// DRAW THE TEXT LABELS
						new Rectangle2D.Double(bounds.getX(), bounds.getY(), 10, 10));
			} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.0030
					&& Zoom.getScalingFactor() > 0.002) {
				printer.paintGrid(
						// GRAPHICS OBJECT
						g2d,
						// GET THE BOUNDS OF THE AREA TO DRAW IN
						canvasPanel.getBounds(),
						// DRAW LINES PORTION
						new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
								(Zoom.getScalingFactor() * UnitConvert.metersToPixels(50)), (Zoom
										.getScalingFactor() * UnitConvert.metersToPixels(50))),
						// DRAW THE TEXT LABELS
						new Rectangle2D.Double(bounds.getX(), bounds.getY(), 50, 50));
			} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.002
					&& Zoom.getScalingFactor() > 0.0001) {
				printer.paintGrid(
						// GRAPHICS OBJECT
						g2d,
						// GET THE BOUNDS OF THE AREA TO DRAW IN
						canvasPanel.getBounds(),
						// DRAW LINES PORTION
						new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
								(Zoom.getScalingFactor() * UnitConvert.metersToPixels(200)), (Zoom
										.getScalingFactor() * UnitConvert.metersToPixels(200))),
						// DRAW THE TEXT LABELS
						new Rectangle2D.Double(bounds.getX(), bounds.getY(), 200, 200));
			} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.0001
					&& Zoom.getScalingFactor() > 0.00001) {
				printer.paintGrid(
						// GRAPHICS OBJECT
						g2d,
						// GET THE BOUNDS OF THE AREA TO DRAW IN
						canvasPanel.getBounds(),
						// DRAW LINES PORTION
						new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
								(Zoom.getScalingFactor() * UnitConvert.metersToPixels(1000)), (Zoom
										.getScalingFactor() * UnitConvert.metersToPixels(1000))),
						// DRAW THE TEXT LABELS
						new Rectangle2D.Double(bounds.getX(), bounds.getY(), 1000, 1000));
			} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.00001
					&& Zoom.getScalingFactor() > 0.000001) {
				printer.paintGrid(
						// GRAPHICS OBJECT
						g2d,
						// GET THE BOUNDS OF THE AREA TO DRAW IN
						canvasPanel.getBounds(),
						// DRAW LINES PORTION
						new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
								(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10000)), (Zoom
										.getScalingFactor() * UnitConvert.metersToPixels(10000))),
						// DRAW THE TEXT LABELS
						new Rectangle2D.Double(bounds.getX(), bounds.getY(), 10000, 10000));
			} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.000001) {
				gridOnOffButton.setSelected(false);
				// setConsoleOutput("Grid OFF - Default Memory Conservation");
			}

			// tell the caller that this page is part
			// of the printed document
			return PAGE_EXISTS;
		}
	}

	/**
	 * This method initializes zoomToFitButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getZoomToFitButton() {
		if (zoomToFitButton == null) {
			zoomToFitButton = new JToggleButton();
			zoomToFitButton.setMinimumSize(dimension30x30);
			zoomToFitButton.setMaximumSize(dimension30x30);
			zoomToFitButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/zoomFit.png")));
			zoomToFitButton.setPreferredSize(dimension30x30);
			// zoomToFitButton.setSelected(true);
			// zoomToFitButton.setBackground(Color.GREEN);
			// zoomToFitButton.setForeground(Color.GREEN);
			// zoomToFitButton.setSelectedIcon(new
			// ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/zoomFit2.png")));

			zoomToFitButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// userZoom = new Rectangle2D.Double(
					// world.getBounds().getX() -
					// world.getBounds().getWidth()/100* /*this is the
					// percentage extra----->*/(expand),
					// world.getBounds().getY() -
					// world.getBounds().getWidth()/100* /*this is the
					// percentage extra----->*/(expand),
					// world.getBounds().getWidth()+
					// world.getBounds().getWidth()/50* /*this is the
					// percentage extra----->*/(expand),
					// world.getBounds().getHeight()+
					// world.getBounds().getWidth()/50* /*this is the
					// percentage extra----->*/(expand));
					// setCanvasSize(
					// userZoom.getX(),
					// userZoom.getY()-userZoom.getHeight(),
					// userZoom.getWidth(),
					// userZoom.getHeight());
					Zoom.setZoomToFit(true);

					// freezeCanvas = false;
					lastZoomClicked = "fit";
					updateCanvas();

				}
			});
		}
		return zoomToFitButton;
	}

	/**
	 * This method initializes zoomInButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getZoomInButton() {
		if (zoomInButton == null) {
			zoomInButton = new JButton();
			zoomInButton.setMinimumSize(dimension30x30);
			zoomInButton.setMaximumSize(dimension30x30);
			zoomInButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/zoomIn.png")));
			zoomInButton.setPreferredSize(dimension30x30);
			zoomInButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Turn off the zoom to fit function.
					// userZoomMarquee = null;
					getZoomToFitButton().setSelected(false);

					double y1 = getCanvasSize().getY() + getCanvasSize().getHeight()
							- UnitConvert.pixelsToMeters(canvasScrollPane.getHeight() * 0.25) / Zoom.getScalingFactor();
					double x1 = getCanvasSize().getX()
							+ UnitConvert.pixelsToMeters(canvasScrollPane.getWidth() * 0.25) / Zoom.getScalingFactor();
					zoomPoint1 = new Point2D.Double(x1, y1);

					double y2 = getCanvasSize().getY() + getCanvasSize().getHeight()
							- UnitConvert.pixelsToMeters(canvasScrollPane.getHeight() * 0.75) / Zoom.getScalingFactor();
					double x2 = getCanvasSize().getX()
							+ UnitConvert.pixelsToMeters(canvasScrollPane.getWidth() * 0.75) / Zoom.getScalingFactor();
					zoomPoint2 = new Point2D.Double(x2, y2);

					// ORIGIN AND DOWN TO THE RIGHT - 20131201-1055
					if (zoomPoint1.getX() < zoomPoint2.getX() && zoomPoint1.getY() > zoomPoint2.getY()) {
						// SET THE CANVAS SIZE TO THE ZOOM MARQUEE SIZE -
						// 20131201-1055
						setCanvasSize(zoomPoint1.getX(), // X
								zoomPoint1.getY(), // Y
								Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
								Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
						);
						setConsoleOutput( // CONSOLE OUTPUT JTEXTAREA ADDED -
											// 20131201-1055
								"\nZOOM USER BOUNDS\nX = " + decimalFormatMetres2.format(zoomPoint1.getX()) + "\nY = "
										+ decimalFormatMetres2.format(zoomPoint1.getY()) + "\nW = "
										+ decimalFormatMetres2.format(Math.abs(zoomPoint2.getX() - zoomPoint1.getX()))
										+ "\nH = "
										+ decimalFormatMetres2.format(Math.abs(zoomPoint2.getY() - zoomPoint1.getY())));
						// SET THE USERZOOMMARQUEE TO THE ZOOMPOINT SETTINGS -
						// 20131201-1055
						userZoom = new Rectangle2D.Double(zoomPoint1.getX(), // X
								zoomPoint1.getY(), // Y
								Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
								Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
						);
					}

					// ONCE THE LAST MARQUEE CLICK SET THE ZOOM TO FIT TO OFF -
					// 20131131 - 1630
					Zoom.setZoomToFit(false);
					updateCanvas();

					// NULL THE ZOOM POINTS TO STOP DRAWING MARQUEE - 20131201 -
					// 1255
					zoomPoint1 = null;
					zoomPoint2 = null;

				}
			});

		}
		return zoomInButton;
	}

	/**
	 * This method initializes zoomOutButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getZoomOutButton() {
		if (zoomOutButton == null) {
			zoomOutButton = new JButton();
			zoomOutButton.setMinimumSize(dimension30x30);
			zoomOutButton.setMaximumSize(dimension30x30);
			zoomOutButton.setPreferredSize(dimension30x30);
			zoomOutButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/zoomOut.png")));

			zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Turn off the zoom to fit function.
					// userZoomMarquee = null;
					getZoomToFitButton().setSelected(false);

					double y1 = getCanvasSize().getY() + getCanvasSize().getHeight()
							+ UnitConvert.pixelsToMeters(canvasScrollPane.getHeight() * 0.25) / Zoom.getScalingFactor();
					double x1 = getCanvasSize().getX()
							- UnitConvert.pixelsToMeters(canvasScrollPane.getWidth() * 0.25) / Zoom.getScalingFactor();
					zoomPoint1 = new Point2D.Double(x1, y1);

					double y2 = getCanvasSize().getY() + getCanvasSize().getHeight()
							- UnitConvert.pixelsToMeters(canvasScrollPane.getHeight() * 1.25) / Zoom.getScalingFactor();
					double x2 = getCanvasSize().getX()
							+ UnitConvert.pixelsToMeters(canvasScrollPane.getWidth() * 1.25) / Zoom.getScalingFactor();
					zoomPoint2 = new Point2D.Double(x2, y2);

					// ORIGIN AND DOWN TO THE RIGHT - 20131201-1055
					if (zoomPoint1.getX() < zoomPoint2.getX() && zoomPoint1.getY() > zoomPoint2.getY()) {
						// SET THE CANVAS SIZE TO THE ZOOM MARQUEE SIZE
						// - 20131201-1055
						setCanvasSize(zoomPoint1.getX(), // X
								zoomPoint1.getY(), // Y
								Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
								Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
						);
						setConsoleOutput( // CONSOLE OUTPUT JTEXTAREA
											// ADDED - 20131201-1055
								"\nZOOM USER BOUNDS\nX = " + decimalFormatMetres2.format(zoomPoint1.getX()) + "\nY = "
										+ decimalFormatMetres2.format(zoomPoint1.getY()) + "\nW = "
										+ decimalFormatMetres2.format(Math.abs(zoomPoint2.getX() - zoomPoint1.getX()))
										+ "\nH = "
										+ decimalFormatMetres2.format(Math.abs(zoomPoint2.getY() - zoomPoint1.getY())));
						// SET THE USERZOOMMARQUEE TO THE ZOOMPOINT
						// SETTINGS - 20131201-1055
						userZoom = new Rectangle2D.Double(zoomPoint1.getX(), // X
								zoomPoint1.getY(), // Y
								Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
								Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
						);
					}

					// ONCE THE LAST MARQUEE CLICK SET THE ZOOM TO FIT
					// TO OFF - 20131131 - 1630
					Zoom.setZoomToFit(false);
					updateCanvas();

					// NULL THE ZOOM POINTS TO STOP DRAWING MARQUEE -
					// 20131201 - 1255
					zoomPoint1 = null;
					zoomPoint2 = null;

				}
			});
		}
		return zoomOutButton;
	}

	Rectangle2D userZoom = new Rectangle2D.Double(10000, 10000, 250, 250);

	/**
	 * This method initializes userSetZoomButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getZoomUserSetButton() {
		if (zoomUserSetButton == null) {
			zoomUserSetButton = new JToggleButton();
			zoomUserSetButton.setMinimumSize(dimension30x30);
			zoomUserSetButton.setMaximumSize(dimension30x30);
			zoomUserSetButton
					.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/zoomMarquee.png")));

			zoomUserSetButton.setToolTipText("Marquee Zoom Tool");
			zoomUserSetButton.setPreferredSize(dimension30x30);

			zoomUserSetButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// Turn off the zoom to fit function.
					// userZoomMarquee = null;
					getZoomToFitButton().setSelected(false);
					currentMouseMover = null;
					setStatusBarLabel("Click in Screen");// fix this
															// later to
															// something
															// for the
															// layman.
					currentMouseClicker = new MouseAdapter() {
						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {
							if (!(clicked)) { // First Click of the
												// mouse

								double y1 = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double x1 = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								zoomPoint1 = new Point2D.Double(x1, y1);
								currentMouseMover = new MouseMotionAdapter() {
									public void mouseMoved(MouseEvent e) {
										double y2 = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double x2 = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
										zoomPoint2 = new Point2D.Double(x2, y2);
										updateCanvas();
									}
								};
							} else { // Second Click
								double y2 = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double x2 = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								zoomPoint2 = new Point2D.Double(x2, y2);

								double extraX = 0;
								double extraY = 0;
								if (Math.abs(zoomPoint2.getX() - zoomPoint1.getX()) >= Math
										.abs(zoomPoint2.getY() - zoomPoint1.getY())) {
									extraY = (Math.abs(zoomPoint2.getX() - zoomPoint1.getX())
											- Math.abs(zoomPoint2.getY() - zoomPoint1.getY())) / 4;
								} else if (Math.abs(zoomPoint2.getX() - zoomPoint1.getX()) < Math
										.abs(zoomPoint2.getY() - zoomPoint1.getY())) {

									extraX = Math.abs((Math.abs(zoomPoint2.getX() - zoomPoint1.getX())
											- Math.abs(zoomPoint2.getY() - zoomPoint1.getY())) / 4);

								}

								// ORIGIN AND DOWN TO THE RIGHT -
								// 20131201-1055
								if (zoomPoint1.getX() < zoomPoint2.getX() && zoomPoint1.getY() > zoomPoint2.getY()) {
									// SET THE CANVAS SIZE TO THE ZOOM
									// MARQUEE SIZE - 20131201-1055
									setCanvasSize(zoomPoint1.getX() - extraX, // X
											zoomPoint1.getY() + extraY, // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
									setConsoleOutput( // CONSOLE OUTPUT
														// JTEXTAREA
														// ADDED -
														// 20131201-1055
											"\nZOOM USER BOUNDS\nX = " + decimalFormatMetres2.format(zoomPoint1.getX())
													+ "\nY = " + decimalFormatMetres2.format(zoomPoint1.getY())
													+ "\nW = "
													+ decimalFormatMetres2.format(
															Math.abs(zoomPoint2.getX() - zoomPoint1.getX()))
													+ "\nH = "
													+ decimalFormatMetres2
															.format(Math.abs(zoomPoint2.getY() - zoomPoint1.getY())));
									// SET THE USERZOOMMARQUEE TO THE
									// ZOOMPOINT SETTINGS -
									// 20131201-1055
									userZoom = new Rectangle2D.Double(zoomPoint1.getX() - extraX, // X
											zoomPoint1.getY() + extraY, // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
								}
								// ORIGIN AND UP TO THE RIGHT -
								// 20131201-1055
								else if (zoomPoint1.getX() < zoomPoint2.getX()
										&& zoomPoint1.getY() < zoomPoint2.getY()) {
									// SET THE CANVAS SIZE TO THE ZOOM
									// MARQUEE SIZE - 20131201-1055
									setCanvasSize(zoomPoint1.getX(), // X
											zoomPoint2.getY(), // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
									setConsoleOutput(// CONSOLE OUTPUT
														// JTEXTAREA
														// ADDED -
														// 20131201-1055
											"\nZOOM BOUNDS\nX = " + decimalFormatMetres2.format(zoomPoint1.getX())
													+ "\nY = " + decimalFormatMetres2.format(zoomPoint1.getY())
													+ "\nW = "
													+ decimalFormatMetres2.format(
															Math.abs(zoomPoint2.getX() - zoomPoint1.getX()))
													+ "\nH = "
													+ decimalFormatMetres2
															.format(Math.abs(zoomPoint2.getY() - zoomPoint1.getY())));
									// SET THE USERZOOMMARQUEE TO THE
									// ZOOMPOINT SETTINGS -
									// 20131201-1055
									userZoom = new Rectangle2D.Double(zoomPoint1.getX(), // X
											zoomPoint2.getY(), // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
								}
								// ORIGIN AND DOWN TO THE LEFT -
								// 20131201-1055
								else if (zoomPoint1.getX() > zoomPoint2.getX()
										&& zoomPoint1.getY() > zoomPoint2.getY()) {
									// SET THE CANVAS SIZE TO THE ZOOM
									// MARQUEE SIZE - 20131201-1055
									setCanvasSize(zoomPoint2.getX(), // X
											zoomPoint1.getY(), // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
									setConsoleOutput(// CONSOLE OUTPUT
														// JTEXTAREA
														// ADDED -
														// 20131201-1055
											"\nZOOM BOUNDS\nX = " + decimalFormatMetres2.format(zoomPoint1.getX())
													+ "\nY = " + decimalFormatMetres2.format(zoomPoint1.getY())
													+ "\nW = "
													+ decimalFormatMetres2.format(
															Math.abs(zoomPoint2.getX() - zoomPoint1.getX()))
													+ "\nH = "
													+ decimalFormatMetres2
															.format(Math.abs(zoomPoint2.getY() - zoomPoint1.getY())));
									// SET THE USERZOOMMARQUEE TO THE
									// ZOOMPOINT SETTINGS -
									// 20131201-1055
									userZoom = new Rectangle2D.Double(zoomPoint2.getX(), // X
											zoomPoint1.getY(), // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
								}
								// ORIGIN AND UP TO THE LEFT -
								// 20131201-1055
								else if (zoomPoint1.getX() > zoomPoint2.getX()
										&& zoomPoint1.getY() < zoomPoint2.getY()) {
									// SET THE CANVAS SIZE TO THE ZOOM
									// MARQUEE SIZE - 20131201-1055
									setCanvasSize(zoomPoint2.getX(), // X
											zoomPoint2.getY(), // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
									setConsoleOutput(// CONSOLE OUTPUT
														// JTEXTAREA
														// ADDED -
														// 20131201-1055
											"\nZOOM BOUNDS\nX = " + decimalFormatMetres2.format(zoomPoint1.getX())
													+ "\nY = " + decimalFormatMetres2.format(zoomPoint1.getY())
													+ "\nW = "
													+ decimalFormatMetres2.format(
															Math.abs(zoomPoint2.getX() - zoomPoint1.getX()))
													+ "\nH = "
													+ decimalFormatMetres2
															.format(Math.abs(zoomPoint2.getY() - zoomPoint1.getY())));
									// SET THE USERZOOMMARQUEE TO THE
									// ZOOMPOINT SETTINGS -
									// 20131201-1055
									userZoom = new Rectangle2D.Double(zoomPoint2.getX(), // X
											zoomPoint2.getY(), // Y
											Math.abs(zoomPoint2.getX() - zoomPoint1.getX()), // Width
											Math.abs(zoomPoint2.getY() - zoomPoint1.getY())// Height)
									);
								}
								// ONCE THE LAST MARQUEE CLICK SET THE
								// ZOOM TO FIT TO OFF - 20131131 - 1630
								Zoom.setZoomToFit(false);
								updateCanvas();
								// RESET TO THE DEFAULT MOUSE MOVER
								currentMouseMover = defaultMouseMover;
								// NULL THE ZOOM POINTS TO STOP DRAWING
								// MARQUEE - 20131201 - 1255
								zoomPoint1 = null;
								zoomPoint2 = null;
							}
							// RESET CLICKS TO NOT CLICKED
							clicked = !clicked;
						}
					};
					// NULL THE ZOOM POINTS TO STOP DRAWING MARQUEE -
					// 20131201 - 1255
					zoomPoint1 = null;
					zoomPoint2 = null;
				}
			});
		}
		return zoomUserSetButton;
	}

	/**
	 * This method initializes saveAsButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSaveAsButton() {
		if (saveAsButton == null) {
			saveAsButton = new JButton();
			saveAsButton.setMinimumSize(dimension30x30);
			saveAsButton.setMaximumSize(dimension30x30);
			saveAsButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/saveAs.png")));
			saveAsButton.setToolTipText("Save As...");
			saveAsButton.setPreferredSize(dimension30x30);
			saveAsButton.setEnabled(false);
			saveAsButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {

						FileTypeRegistry.FileMapping variable1 = DialogSaveFile.showBox();
						if (variable1 == null) {
							if (pattern == null) {
								System.out.println("Cacktus");
							}
							return;
						}
						variable1.handler.saveFile(pattern, variable1.file.getAbsolutePath());
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(LiteTieTRIAL.this, "Unable to write file.");

					}
				}
			});
		}
		return saveAsButton;
	}

	/**
	 * This method initializes closeButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setMinimumSize(dimension30x30);
			closeButton.setMaximumSize(dimension30x30);
			closeButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/closed.png")));
			closeButton.setPreferredSize(dimension30x30);
		}
		return closeButton;
	}

	/**
	 * This method initializes toolPolygonButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolPolygonSelectButton() {
		if (toolPolygonSelectButton == null) {
			toolPolygonSelectButton = new JToggleButton();
			toolPolygonSelectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolPolygonSelectButton.setMinimumSize(dimension30x30);
			toolPolygonSelectButton.setMaximumSize(dimension30x30);
			toolPolygonSelectButton
					.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/selectPoly.png"))));
			toolPolygonSelectButton.setPreferredSize(new Dimension(30, 30));
			toolPolygonSelectButton.addKeyListener(new java.awt.event.KeyAdapter() {
				// IF KEYS ARE PRESSED
				/*
				 * Keys are: ESCAPE = clear all the points from the polygon S =
				 * select ONLY surface connections C = select Charges - Not
				 * Complete H = select Holes D = select Detonators inholes T =
				 * select Text B = select Boundary Points X = select Dummy Holes
				 * SHIFT = not Assigned YET ALT = not Assisgned YET
				 */
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						polyPointsX.clear();
						polyPointsY.clear();
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyCode() == KeyEvent.VK_S) {
						statusBarLabel.setText("Surface Connectors selection only");
						isVKSDown = true;
						selectionType = "Selecting Surface Connections";
					} else if (e.getKeyCode() == KeyEvent.VK_H) {
						statusBarLabel.setText("Hole selection only");
						isVKHDown = true;
						selectionType = "Selecting Holes";
					} else if (e.getKeyCode() == KeyEvent.VK_D) {
						statusBarLabel.setText("Detonator selection only");
						isVKDDown = true;
						selectionType = "Selecting Detonators";
					} else if (e.getKeyCode() == KeyEvent.VK_T) {
						statusBarLabel.setText("Text Label selection only");
						isVKTDown = true;
						selectionType = "Selecting Text";
					} else if (e.getKeyCode() == KeyEvent.VK_B) {
						statusBarLabel.setText("Boundary Point selection only");
						isVKBDown = true;
						selectionType = "Selecting Boundry Points";
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						statusBarLabel.setText("Dummy selection only");
						isVKXDown = true;
						selectionType = "Selecting Dummy Holes";
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						// change mouse cursor to a marquee cursor with
						// a plus symbol
					} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
						// change mouse cursor to a marquee cursor with
						// a minus symbol
					} else {
						selectionType = "Selecting All";
					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						polyPointsX.clear();
						polyPointsY.clear();
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyCode() == KeyEvent.VK_S) {
						isVKSDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_H) {
						isVKHDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_D) {
						isVKDDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_T) {
						isVKTDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_B) {
						isVKBDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						isVKXDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						// change mouse cursor to a default cursor
					} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
						// change mouse cursor to a default cursor
					}
				}
			});

			toolPolygonSelectButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					labelColourStroke.setText("Stroke");
					getColorWellStroke().setBackground(currentColor);
					setStatusBarLabel(
							"Polygon Select Objects - /ESC=clear/S=surface/H=Holes/D=Detonators/T=Text/B=Boundary/X=Dummies \tDouble click to activate selection");

					currentMouseClicker = new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();

							polyPointsX.add(0, easting);
							polyPointsY.add(0, northing);
							polyPointsX.add(1, easting);
							polyPointsY.add(1, northing);

							currentMouseMover = new MouseMotionAdapter() {

								public void mouseMoved(MouseEvent mm) {

									double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
											- UnitConvert.pixelsToMeters(mm.getY()) / Zoom.getScalingFactor();
									double easting = getCanvasSize().getX()
											+ UnitConvert.pixelsToMeters(mm.getX()) / Zoom.getScalingFactor();

									double pointsX[] = new double[polyPointsX.size()];
									double pointsY[] = new double[polyPointsY.size()];
									polyPointsX.set(polyPointsX.size() - 1, easting);
									polyPointsY.set(polyPointsY.size() - 1, northing);

									for (int i = 0; i < polyPointsX.size(); i++) {
										pointsX[i] = (double) polyPointsX.get(i).doubleValue();
										pointsY[i] = (double) polyPointsY.get(i).doubleValue();
									}
									if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
											|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
													.isEmpty())
											&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
											&& !isVKXDown) {
										selected = null;
										selected = world
												.getAllObjectsIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
										updateCanvas();
									}
									if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
											|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
													.isEmpty())
											&& isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
											&& !isVKXDown) {
										selected = null;
										selected = world
												.getAllSurfaceIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
										updateCanvas();
									}
									if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
											|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
													.isEmpty())
											&& !isVKSDown && isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
											&& !isVKXDown) {
										selected = null;
										selected = world
												.getAllBPointsIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
										updateCanvas();
									}
									if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
											|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
													.isEmpty())
											&& !isVKSDown && !isVKBDown && isVKHDown && !isVKDDown && !isVKTDown
											&& !isVKXDown) {
										selected = null;
										selected = world
												.getAllHolesIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
										updateCanvas();
									}
									if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
											|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
													.isEmpty())
											&& !isVKSDown && !isVKBDown && !isVKHDown && isVKDDown && !isVKTDown
											&& !isVKXDown) {
										selected = null;
										selected = world.getAllDetonatorsIn(
												new Polygon2D(pointsX, pointsY, polyPointsX.size()));
										updateCanvas();
									}
									if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
											|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
													.isEmpty())
											&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && isVKTDown
											&& !isVKXDown) {
										selected = null;
										selected = world
												.getAllTextIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
										updateCanvas();
									}
									if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
											|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
													.isEmpty())
											&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
											&& isVKXDown) {
										selected = null;
										selected = world
												.getAllDummysIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
										updateCanvas();
									}
								}
							};
							// }
							// RIGHT CLICK - CONTROL BUTTON DOWN -
							// LISTENER FOR ENDING THE POLYGON
							if (e.getModifiers() == 4 || e.isControlDown()) {
								double pointsX[] = new double[polyPointsX.size()];
								double pointsY[] = new double[polyPointsY.size()];
								for (int i = 0; i < polyPointsX.size(); i++) {
									pointsX[i] = (double) polyPointsX.get(i).doubleValue();
									pointsY[i] = (double) polyPointsY.get(i).doubleValue();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {
									selected = world
											.getAllObjectsIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
									polyPointsX.clear();
									polyPointsY.clear();
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {
									selected = world
											.getAllSurfaceIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
									polyPointsX.clear();
									polyPointsY.clear();
									updateCanvas();

								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world
											.getAllBPointsIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
									polyPointsX.clear();
									polyPointsY.clear();
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world.getAllHolesIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
									polyPointsX.clear();
									polyPointsY.clear();
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world
											.getAllDetonatorsIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
									polyPointsX.clear();
									polyPointsY.clear();
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && isVKTDown && !isVKXDown) {

									selected = world.getAllTextIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
									polyPointsX.clear();
									polyPointsY.clear();
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && isVKXDown) {

									selected = world
											.getAllDummysIn(new Polygon2D(pointsX, pointsY, polyPointsX.size()));
									polyPointsX.clear();
									polyPointsY.clear();
									updateCanvas();
								}
								setStatusBarLabel("Polygon Selection Ended");
								currentMouseMover = defaultMouseMover;
								polyPointsX.clear();
								polyPointsY.clear();
							}
						}
					};
				}
			});// END of ACTIONLISTENER
		}
		return toolPolygonSelectButton;
	}

	/**
	 * This method initializes toolMoveButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolMoveButton() {
		if (toolMoveButton == null) {
			toolMoveButton = new JToggleButton(new MoveAction(this));
			toolMoveButton.setHideActionText(true);
			toolMoveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolMoveButton.setPreferredSize(dimension30x30);
			toolMoveButton.setToolTipText("Move Selected Items");
			// Key Event Listeners
			toolMoveButton.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent k) {
					// Deselect if the ESCAPE key has been pressed
					if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selectedFIFO = null;
						selected = null;
					}
				}

				public void keyReleased(java.awt.event.KeyEvent k) {
					// Deselct if the Key is released
					if (!(k.getKeyCode() == KeyEvent.VK_ESCAPE)) {
						selected = null;
						selectedFIFO = null;
					}
				}
			});
		}
		return toolMoveButton;
	}

	/**
	 * This method initializes toolLabelButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolLabelButton() {
		if (toolLabelButton == null) {
			toolLabelButton = new JToggleButton();
			toolLabelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolLabelButton.setMinimumSize(new Dimension(24, 22));
			toolLabelButton.setMaximumSize(new Dimension(24, 22));
			toolLabelButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/TOOLlabel.png"))));
			toolLabelButton.setToolTipText("Label Tool");
			toolLabelButton.setPreferredSize(new Dimension(24, 22));

			toolLabelButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(ActionEvent arg0) {

					// firstInfirstOutSelection = (LinkedHashSet)
					// selected;

					if (world.getTextList() != null && selected != null) {

						for (Text temp : world.getTextList().values()) {
							boolean isSelected = (Object) temp == selectedFIFO
									|| (selectedFIFO instanceof LinkedList && selectedFIFO.contains(temp));

							// boolean isSelected = temp == selected ||
							// (selected instanceof Collection<?> &&
							// ((Collection<?>)selected).contains(temp));
							if (isSelected && temp instanceof Text) {
								String newValue = JOptionPane.showInputDialog(null, "Please type the new text value",
										temp.getText());
								temp.setText(newValue);
								updateCanvas();
							}
						}
						updateCanvas();
						selected = null;
						selectedFIFO = null;
					}
					getTreeModelText();
					updateCanvas();

				}
			});
		}
		return toolLabelButton;
	}

	/**
	 * This method initializes toolRotateButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolRotateButton() {
		if (toolRotateButton == null) {
			RotateAction action = new RotateAction(this);

			toolRotateButton = new JToggleButton();
			toolRotateButton.setPreferredSize(dimension30x30);
			toolRotateButton.setHideActionText(true);
			toolRotateButton.setAction(action);
			toolRotateButton.setToolTipText("Rotate Bearings or Selected Items");
			toolRotateButton.addKeyListener(action.currentKeyEvent);
		}
		return toolRotateButton;
	}

	/**
	 * This method initializes toolTieButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolTieButton() {
		if (toolTieButton == null) {
			toolTieButton = new JToggleButton();
			toolTieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolTieButton.setMaximumSize(new Dimension(24, 22));
			toolTieButton.setMinimumSize(new Dimension(24, 22));
			toolTieButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/TOOLtie.png"))));
			toolTieButton.setToolTipText("Single Surface Connection Tool");
			toolTieButton.setPreferredSize(new Dimension(30, 30));

			toolTieButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					currentMouseMover = null;
					setStatusBarLabel("SINGLE SURFACE TIMING TOOL");
					selected = null;
					updateCanvas();

					labelColourStroke.setText("Tie");

					if (getTieButton1().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField1().getBackground());
					}
					if (getTieButton2().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField2().getBackground());
					}
					if (getTieButton3().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField3().getBackground());
					}
					if (getTieButton4().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField4().getBackground());
					}
					if (getTieButton5().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField5().getBackground());
					}
					if (getTieButton6().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField6().getBackground());
					}
					getColorWellFill().repaint();
					currentMouseClicker = new MouseAdapter() {

						@Override
						public void mouseReleased(MouseEvent e) {
							if (tieClicked == false || dummy1 == null && dummy2 == null && !(e.isShiftDown())) {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								// double radius =
								// UnitConvert.pixelsToMeters(8)/
								// Zoom.getScalingFactor();
								selected = world.getDummysAndHolesIn(new Ellipse2D.Double(easting - radius,
										northing - radius, radius * 2, radius * 2));
								if (selected != null
										|| (selected instanceof Collection<?>) && selected instanceof Dummy) {
									for (Pattern tempPat : world.getPatternList().values()) { // For
																								// every
																								// pattern
																								// in
																								// the
																								// pattern
																								// list
																								// do
																								// the
																								// following
										for (Dummy temp : tempPat.getAllDummysAndHoles()) { // and
																							// for
																							// every
																							// dummy
																							// /
																							// hole
																							// intempPatdothefollowing//the
																							// above
																							// two
																							// statements
																							// mean
																							// that
																							// everything
																							// below
																							// will
																							// be
																							// done
																							// to
																							// everyholeordummyinallpatternsonthecanvas
											boolean isSelected = temp == selected || (selected instanceof Collection<?>
													&& ((Collection<?>) selected).contains(temp));// isSelected
																									// is
																									// saying
																									// that
																									// all
																									// holes
																									// and
																									// dummys
																									// in
																									// the
																									// selectedObject
																									// Object
																									// or
																									// in
																									// the
																									// collection
																									// of
																									// selected
																									// objects
																									// will
																									// be
																									// affected
											if (isSelected) {
												dummy1 = temp;
												getTreeModelSurface();
												updateCanvas();
												tieClicked = true;
											}
										}
									}
								}
							} else if (tieClicked == true && dummy1 != null && !(e.isShiftDown())) {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								// double radius =
								// UnitConvert.pixelsToMeters(8)/
								// Zoom.getScalingFactor();
								selected = world.getDummysAndHolesIn(new Ellipse2D.Double(easting - radius,
										northing - radius, radius * 2, radius * 2));
								for (Pattern tempPat : world.getPatternList().values()) { // For
																							// every
																							// pattern
																							// in
																							// the
																							// pattern
																							// list
																							// do
																							// the
																							// following
									for (Dummy temp : tempPat.getAllDummysAndHoles()) { // and
																						// for
																						// every
																						// dummy
																						// /
																						// hole
																						// in
																						// tempPat
																						// do
																						// the
																						// following//
																						// the
																						// above
																						// two
																						// statements
																						// mean
																						// that
																						// every
																						// thing
																						// below
																						// will
																						// be
																						// done
																						// to
																						// every
																						// hole
																						// or
																						// dummy
																						// in
																						// all
																						// patterns
																						// on
																						// the
																						// canvas
										boolean isSelected = temp == selected || (selected instanceof Collection<?>
												&& ((Collection<?>) selected).contains(temp));// isSelected
																								// is
																								// saying
																								// that
																								// all
																								// holes
																								// and
																								// dummys
																								// in
																								// the
																								// selectedObject
																								// Object
																								// or
																								// in
																								// the
																								// collection
																								// of
																								// selected
																								// objects
																								// will
																								// be
																								// affected
										if (isSelected) {
											if (dummy1 != null || dummy1 != selected) {
												dummy2 = temp;
												currentPattern = tempPat;
											}
											getTreeModelSurface();
											updateCanvas();
										}
									}
								}
							}
							try {
								if (dummy1 instanceof Dummy && dummy2 instanceof Dummy && !(e.isShiftDown())) {
									double xl = Math.abs(dummy1.getX() - dummy2.getX());
									double yl = Math.abs(dummy1.getY() - dummy2.getY());
									double d = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));
									if (getTieButton1().isSelected()) {
										sDelay = Integer.parseInt(getTieTextField1().getText());
										sColor1 = getTieTextField1().getBackground();
										sColor2 = getTieTextField2().getBackground();
										sColor3 = getTieTextField3().getBackground();
										sColor4 = getTieTextField4().getBackground();
										sColor5 = getTieTextField5().getBackground();
										sColor6 = getTieTextField6().getBackground();
										currentPattern.addSurfaceConnector(
												new SurfaceConnector(sDelay, d, dummy1, dummy2, sColor1), false);
									} else if (getTieButton2().isSelected()) {
										sDelay = Integer.parseInt(getTieTextField2().getText());
										sColor1 = getTieTextField1().getBackground();
										sColor2 = getTieTextField2().getBackground();
										sColor3 = getTieTextField3().getBackground();
										sColor4 = getTieTextField4().getBackground();
										sColor5 = getTieTextField5().getBackground();
										sColor6 = getTieTextField6().getBackground();
										currentPattern.addSurfaceConnector(
												new SurfaceConnector(sDelay, d, dummy1, dummy2, sColor2), false);
									} else if (getTieButton3().isSelected()) {
										sDelay = Integer.parseInt(getTieTextField3().getText());
										sColor1 = getTieTextField1().getBackground();
										sColor2 = getTieTextField2().getBackground();
										sColor3 = getTieTextField3().getBackground();
										sColor4 = getTieTextField4().getBackground();
										sColor5 = getTieTextField5().getBackground();
										sColor6 = getTieTextField6().getBackground();
										currentPattern.addSurfaceConnector(
												new SurfaceConnector(sDelay, d, dummy1, dummy2, sColor3), false);
									} else if (getTieButton4().isSelected()) {
										sDelay = Integer.parseInt(getTieTextField4().getText());
										sColor1 = getTieTextField1().getBackground();
										sColor2 = getTieTextField2().getBackground();
										sColor3 = getTieTextField3().getBackground();
										sColor4 = getTieTextField4().getBackground();
										sColor5 = getTieTextField5().getBackground();
										sColor6 = getTieTextField6().getBackground();
										currentPattern.addSurfaceConnector(
												new SurfaceConnector(sDelay, d, dummy1, dummy2, sColor4), false);
									} else if (getTieButton5().isSelected()) {
										sDelay = Integer.parseInt(getTieTextField5().getText());
										sColor1 = getTieTextField1().getBackground();
										sColor2 = getTieTextField2().getBackground();
										sColor3 = getTieTextField3().getBackground();
										sColor4 = getTieTextField4().getBackground();
										sColor5 = getTieTextField5().getBackground();
										sColor6 = getTieTextField6().getBackground();
										currentPattern.addSurfaceConnector(
												new SurfaceConnector(sDelay, d, dummy1, dummy2, sColor5), false);
									} else if (getTieButton6().isSelected()) {
										sDelay = Integer.parseInt(getTieTextField6().getText());
										sColor1 = getTieTextField1().getBackground();
										sColor2 = getTieTextField2().getBackground();
										sColor3 = getTieTextField3().getBackground();
										sColor4 = getTieTextField4().getBackground();
										sColor5 = getTieTextField5().getBackground();
										sColor6 = getTieTextField6().getBackground();
										currentPattern.addSurfaceConnector(
												new SurfaceConnector(sDelay, d, dummy1, dummy2, sColor6), false);
									}
									getTreeModelSurface();
									statusBarLabel.setText("Surface Delay added = " + sDelay + "ms"
											+ "\tSurface delays in List =" + currentPattern.getSurfaceList().size());
									dummy1 = dummy2;
									dummy2 = null;
								}
							} catch (NumberFormatException e1) {
								System.out.println("NumberFormatException");
								e1.printStackTrace();
							} catch (NegativeNumberException e1) {
								System.out.println("NegativeNumberException");
								e1.printStackTrace();
							} catch (ZeroArgumentException e1) {
								System.out.println("ZeroArgumentException");
								e1.printStackTrace();
							} catch (FromToException e1) {
								System.out.println("FromToException");
								e1.printStackTrace();
								java.awt.Toolkit.getDefaultToolkit().beep();
							}
						}
					};
				}
			});

			updateCanvas();

			toolTieButton.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						getTreeModelSurface();
						updateCanvas();
					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						getTreeModelSurface();
						updateCanvas();
					}
				}
			});
			getTreeModelSurface();
			updateCanvas();
		}
		return toolTieButton;
	}

	/**
	 * This method initializes toolMultiTieButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolMultiTieButton() {
		if (toolMultiTieButton == null) {
			toolMultiTieButton = new JToggleButton();
			toolMultiTieButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolMultiTieButton.setMaximumSize(new Dimension(24, 22));
			toolMultiTieButton.setMinimumSize(new Dimension(24, 22));
			toolMultiTieButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/TOOLtiemulti.png"))));
			toolMultiTieButton.setToolTipText("Multi Surface Tablet Connection Tool");
			toolMultiTieButton.setPreferredSize(new Dimension(30, 30));

			toolMultiTieButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setConsoleOutput("\n multitooltieButton");
					currentMouseMover = defaultMouseMover;
					setStatusBarLabel("MULTITIE SURFACE TIMING TOOL"); // layman.
					selected = null;
					updateCanvas();

					labelColourStroke.setText("Tie");

					if (getTieButton1().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField1().getBackground());
					}
					if (getTieButton2().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField2().getBackground());
					}
					if (getTieButton3().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField3().getBackground());
					}
					if (getTieButton4().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField4().getBackground());
					}
					if (getTieButton5().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField5().getBackground());
					}
					if (getTieButton6().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField6().getBackground());
					}
					getColorWellFill().repaint();

					// CLICKER #1
					currentMouseClicker = new MouseAdapter() {

						@Override
						public void mouseReleased(MouseEvent e) {
							// If no tie button is clicked then warn the user
							if (!getTieButton1().isSelected() && !getTieButton2().isSelected()
									&& !getTieButton3().isSelected() && !getTieButton4().isSelected()
									&& !getTieButton5().isSelected() && !getTieButton6().isSelected()) {
								JOptionPane.showMessageDialog(canvasPanel,
										"Please select a Tie to use before proceeding", "ERROR - No Tie Selected",
										JOptionPane.ERROR_MESSAGE);
							} else if (tieClick == 0 || tieClicked == false || (dummy1 == null && dummy2 == null)) {

								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								selected = world.getDummysAndHolesIn(new Ellipse2D.Double(easting - radius,
										northing - radius, radius * 2, radius * 2));
								if (selected != null
										|| (selected instanceof Collection<?>) && selected instanceof Dummy) {
									for (Pattern tempPat : world.getPatternList().values()) { // For
																								// every
																								// pattern
																								// in
																								// the
																								// pattern
																								// list
																								// do
																								// the
																								// following
										for (Dummy temp : tempPat.getAllDummysAndHoles()) { // and
																							// for
																							// every
																							// dummy
																							// /
																							// hole
																							// in
																							// tempPat
																							// do
																							// the
																							// following//
																							// the
																							// above
																							// two
																							// statements
																							// mean
																							// that
																							// every
																							// thing
																							// below
																							// will
																							// be
																							// done
																							// to
																							// every
																							// hole
																							// or
																							// dummy
																							// in
																							// all
																							// patterns
																							// on
																							// the
																							// canvas
											boolean isSelected = temp == selected || (selected instanceof Collection<?>
													&& ((Collection<?>) selected).contains(temp));// isSelected
																									// is
																									// saying
																									// that
																									// all
																									// holes
																									// and
																									// dummys
																									// in
																									// the
																									// selectedObject
																									// Object
																									// or
																									// in
																									// the
																									// collection
																									// of
																									// selected
																									// objects
																									// will
																									// be
																									// affected
											if (isSelected) {
												dummy1 = temp;
												getTreeModelSurface();
												updateCanvas();
												tieClicked = true;
												tieClick = 1;
											}
										}
									}
								}
							}
							// MOUSEMOVER
							if (tieClick == 1 && tieClicked == true && dummy1 != null) {
								currentMouseMover = new MouseMotionAdapter() {// Mouse
																				// motion
																				// adapter
																				// to
																				// find
																				// the
																				// location
																				// of
																				// the
																				// mouse
																				// and
																				// update
																				// the
																				// objects
																				// origin
																				// to
																				// that
																				// location
									public void mouseMoved(MouseEvent e) {

										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

										selected = world.getDummysAndHolesIn(new Ellipse2D.Double(easting - radius,
												northing - radius, radius * 2, radius * 2));
										for (Pattern tempPat : world.getPatternList().values()) { // For
																									// every
																									// pattern
																									// in
																									// the
																									// pattern
																									// list
																									// do
																									// the
																									// following
											for (Dummy temp : tempPat.getAllDummysAndHoles()) { // and
																								// for
																								// every
																								// dummy
																								// hole
																								// in
																								// tempPat
																								// do
																								// the
																								// following
																								// the
																								// above
																								// two
																								// statements
																								// mean
																								// that
																								// every
																								// thing
																								// below
																								// will
																								// be
																								// done
																								// to
																								// every
																								// hole
																								// or
																								// dummy
																								// in
																								// all
																								// patterns
																								// on
																								// the
																								// canvas
												boolean isSelected = temp == selected
														|| (selected instanceof Collection<?>
																&& ((Collection<?>) selected).contains(temp));// isSelected
																												// is
																												// saying
																												// that
																												// all
																												// holes
																												// and
																												// dummys
																												// in
																												// the
																												// selectedObject
																												// Object
																												// or
																												// in
																												// the
																												// collection
																												// of
																												// selected
																												// objects
																												// will
																												// be
																												// affected
												if (isSelected) {
													if (dummy1 != null || dummy1 != selected) {
														dummy2 = temp;
														currentPattern = tempPat;
													}
													getTreeModelSurface();

													updateCanvas();
												}
											}
										}
										try {
											if (dummy1 instanceof Dummy && dummy2 instanceof Dummy) {
												double xl = Math.abs(dummy1.getX() - dummy2.getX());
												double yl = Math.abs(dummy1.getY() - dummy2.getY());
												double d = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));
												if (getTieButton1().isSelected()) {
													sDelay = Integer.parseInt(getTieTextField1().getText());
													sColor1 = getTieTextField1().getBackground();
													sColor2 = getTieTextField2().getBackground();
													sColor3 = getTieTextField3().getBackground();
													sColor4 = getTieTextField4().getBackground();
													sColor5 = getTieTextField5().getBackground();
													sColor6 = getTieTextField6().getBackground();
													if (d > 0) {
														currentPattern.addSurfaceConnector(new SurfaceConnector(sDelay,
																d, dummy1, dummy2, sColor1), false);
													}
												} else if (getTieButton2().isSelected()) {
													sDelay = Integer.parseInt(getTieTextField2().getText());
													sColor1 = getTieTextField1().getBackground();
													sColor2 = getTieTextField2().getBackground();
													sColor3 = getTieTextField3().getBackground();
													sColor4 = getTieTextField4().getBackground();
													sColor5 = getTieTextField5().getBackground();
													sColor6 = getTieTextField6().getBackground();
													if (d > 0) {
														currentPattern.addSurfaceConnector(new SurfaceConnector(sDelay,
																d, dummy1, dummy2, sColor2), false);
													}
												} else if (getTieButton3().isSelected()) {
													sDelay = Integer.parseInt(getTieTextField3().getText());
													sColor1 = getTieTextField1().getBackground();
													sColor2 = getTieTextField2().getBackground();
													sColor3 = getTieTextField3().getBackground();
													sColor4 = getTieTextField4().getBackground();
													sColor5 = getTieTextField5().getBackground();
													sColor6 = getTieTextField6().getBackground();
													if (d > 0) {
														currentPattern.addSurfaceConnector(new SurfaceConnector(sDelay,
																d, dummy1, dummy2, sColor3), false);
													}
												} else if (getTieButton4().isSelected()) {
													sDelay = Integer.parseInt(getTieTextField4().getText());
													sColor1 = getTieTextField1().getBackground();
													sColor2 = getTieTextField2().getBackground();
													sColor3 = getTieTextField3().getBackground();
													sColor4 = getTieTextField4().getBackground();
													sColor5 = getTieTextField5().getBackground();
													sColor6 = getTieTextField6().getBackground();
													if (d > 0) {
														currentPattern.addSurfaceConnector(new SurfaceConnector(sDelay,
																d, dummy1, dummy2, sColor4), false);
													}
												} else if (getTieButton5().isSelected()) {
													sDelay = Integer.parseInt(getTieTextField5().getText());
													sColor1 = getTieTextField1().getBackground();
													sColor2 = getTieTextField2().getBackground();
													sColor3 = getTieTextField3().getBackground();
													sColor4 = getTieTextField4().getBackground();
													sColor5 = getTieTextField5().getBackground();
													sColor6 = getTieTextField6().getBackground();
													if (d > 0) {
														currentPattern.addSurfaceConnector(new SurfaceConnector(sDelay,
																d, dummy1, dummy2, sColor5), false);
													}
												} else if (getTieButton6().isSelected()) {
													sDelay = Integer.parseInt(getTieTextField6().getText());
													sColor1 = getTieTextField1().getBackground();
													sColor2 = getTieTextField2().getBackground();
													sColor3 = getTieTextField3().getBackground();
													sColor4 = getTieTextField4().getBackground();
													sColor5 = getTieTextField5().getBackground();
													sColor6 = getTieTextField6().getBackground();
													if (d > 0) {
														currentPattern.addSurfaceConnector(new SurfaceConnector(sDelay,
																d, dummy1, dummy2, sColor6), false);
													}
												}
												getTreeModelSurface();
												statusBarLabel.setText("Surface Delay added = " + sDelay + "ms"
														+ "\tSurface delays in List"
														+ currentPattern.getSurfaceList().size());
												dummy1 = dummy2;
												dummy2 = null;
											}
										} catch (NumberFormatException e1) {
											System.out.println("NumberFormatException");
											e1.printStackTrace();
										} catch (NegativeNumberException e1) {
											System.out.println("NegativeNumberException");
											e1.printStackTrace();
										} catch (ZeroArgumentException e1) {
											System.out.println("ZeroArgumentException");
											// e1.printStackTrace();
										} catch (FromToException e1) {
											System.out.println("FromToException");
											getTextAreaConsoleOutput().setCaretColor(Color.RED);
											setConsoleOutput(e1.toString()
													+ "\nLiteTie thinks that you've\nselected the same hole twice.\n");
											getTextAreaConsoleOutput().setCaretColor(Color.BLACK);
											// e1.printStackTrace();
											java.awt.Toolkit.getDefaultToolkit().beep();
										}

										tieClick = 2;

									}
								};
							}

							// MOUSECLICKER
							if (tieClick == 2 && dummy2 == null) {
								dummy1 = null;
								dummy2 = null;
								selected = null;
								tieClicked = false;
								currentMouseMover = null;
								toolMultiTieButton.removeMouseMotionListener(currentMouseMover);
								toolMultiTieButton.removeMouseListener(currentMouseClicker);
								updateCanvas();
								tieClick = 0;
							}

						}
					};
					//
					toolMultiTieButton.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
								dummy1 = null;
								dummy2 = null;
								selected = null;
								selectedFIFO = null;
								tieClicked = false;
								currentMouseMover = null;
								toolMultiTieButton.removeMouseMotionListener(currentMouseMover);
								toolMultiTieButton.removeMouseListener(currentMouseClicker);
								updateCanvas();
							}
						}

						public void keyReleased(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
								dummy1 = null;
								dummy2 = null;
								selected = null;
								selectedFIFO = null;
								tieClicked = false;
								currentMouseMover = null;
								toolMultiTieButton.removeMouseMotionListener(currentMouseMover);
								toolMultiTieButton.removeMouseListener(currentMouseClicker);
								updateCanvas();
							}
						}
					});
				}
			});

			currentMouseMover = defaultMouseMover;
			updateCanvas();
		}
		return toolMultiTieButton;
	}

	private JToggleButton getToolNGonButton() {
		if (toolNGonButton == null) {
			toolNGonButton = new JToggleButton();
			toolNGonButton.setMinimumSize(dimension30x30);
			toolNGonButton.setMaximumSize(dimension30x30);
			toolNGonButton.setToolTipText("nGon Drawing Tool");
			toolNGonButton.setPreferredSize(dimension30x30);
			toolNGonButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolNGonButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie_v2/polygonN.png")));
			getBoundaryOnOffButton().setSelected(true);

			toolNGonButton.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selectionPoint1 = null;
						selectionPoint2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						statusBarLabel.setText("Rotation in increments of Five Degrees");
						isVKShiftDown = true;
					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selectionPoint1 = null;
						selectionPoint2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						isVKShiftDown = false;
					}
				}
			});

			toolNGonButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;

					labelColourStroke.setText("Stroke");
					getColorWellFill().repaint();

					currentMouseClicker = new MouseAdapter() {
						boolean clicked = false;
						boolean isClosed = true;
						boolean isAnnotated = false;

						@Override
						public void mouseReleased(MouseEvent e) {
							if (clicked == false) {
								double centerX = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								double centerY = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double centerZ = lastRLHeight;
								coord2 = new Coordinate(centerX, centerY, centerZ);
								boundary = new Boundary(0, 0, isClosed, 0, isAnnotated, currentColor, currentFillColor,
										getCurrentStroke());
								try {
									world.addBoundary(boundary, false);
								} catch (ZeroArgumentException e3) {
									// TODO Auto-generated catch block
									e3.printStackTrace();
								} catch (NegativeNumberException e3) {
									// TODO Auto-generated catch block
									e3.printStackTrace();
								}

							}
							currentMouseMover = new MouseMotionAdapter() {
								public void mouseMoved(MouseEvent mm) {
									try {
										clicked = true;
										double radiusY = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(mm.getY()) / Zoom.getScalingFactor();
										double radiusX = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(mm.getX()) / Zoom.getScalingFactor();
										coord1 = new Coordinate(radiusX, radiusY, lastRLHeight);
										int n = Integer.parseInt(getToolNGonSpinner().getValue().toString());
										ArrayList<Double> x = new ArrayList<Double>();
										ArrayList<Double> y = new ArrayList<Double>();
										double a = Math.toRadians(360.0000d / n); // Must
																					// format
																					// the
																					// integer
																					// to
																					// .00d
																					// else
																					// errors
																					// occur
																					// on
																					// the
																					// rotation
																					// equation

										double r = calcs.calculateDistance(coord2, coord1);
										double b = 0;
										if (!isVKShiftDown) {
											System.out.println("Shift Key is not pressed");
											b = // calcs.calculateBearing(coord1,
												// coord2);
													(Transform.orientationChange(coord1.getX(), coord1.getY(),
															coord2.getX(), coord2.getY()));
											//
											for (int i = 0; i < n; i++) {
												double v = (i * Math.toDegrees(a)) - Math.toDegrees(b);
												x.add((coord2.getX() - r * Math.cos(Math.toRadians(v))));
												y.add((coord2.getY() + r * Math.sin(Math.toRadians(v))));

												bPoint = new BPoint(boundary.getBoundaryID(), i + 1, x.get(i), y.get(i),
														lastRLHeight);

												boundary.addBPoint(bPoint);
												bearingStatusLabel
														.setText(decimalFormatDegrees1.format(Math.toDegrees(b)));
												updateCanvas();
											}

										}
										if (isVKShiftDown) {
											System.out.println("Shift Key is pressed");
											b = (Transform.orientationChangeByFive(coord1.getX(), coord1.getY(),
													coord2.getX(), coord2.getY()));

											for (int i = 0; i < n; i++) {
												double v = (i * Math.toDegrees(a)) - Math.toDegrees(b);
												x.add((coord2.getX() - r * Math.cos(Math.toRadians(v))));
												y.add((coord2.getY() + r * Math.sin(Math.toRadians(v))));

												bPoint = new BPoint(boundary.getBoundaryID(), i + 1, x.get(i), y.get(i),
														lastRLHeight);

												boundary.addBPoint(bPoint);
												bearingStatusLabel
														.setText(decimalFormatDegrees1.format(Math.toDegrees(b)));
												updateCanvas();
											}
										}
									} catch (ZeroArgumentException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									} catch (NegativeNumberException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									}
								}

							};// Mouse Mover Event finished

							if (clicked == true) {
								coord2 = null;
								coord1 = null;

								bPoint = null;
								currentBoundary = boundary;
								currentMouseMover = defaultMouseMover;

								saveAsMenuItem.setEnabled(true);
								updateCanvas();
								getTreeModelBoundary();
							}

						}

					};

					currentBoundary = boundary;
					currentMouseMover = defaultMouseMover;
				}
			});

		}
		return toolNGonButton;
	}

	// NGon Spinner works in conjunction with toolNGonButton
	private JSpinner getToolNGonSpinner() {
		if (toolNGonSpinner == null) {
			toolNGonSpinner = new JSpinner();
			toolNGonSpinner.setSize(40, 22);
			toolNGonSpinner.setModel(new SpinnerNumberModel(6, 3, 360 / 2, 1));
			toolNGonSpinner.setToolTipText("input the number of sides for a nGon");
			toolNGonSpinner.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getToolNGonSpinner().getValue().toString());
						getToolNGonSpinner().setValue(i);
						getToolNGonSpinner().setForeground(Color.black);
						return true;
					} catch (NumberFormatException nfe) {
						getToolNGonSpinner().setForeground(Color.red);
						JOptionPane
								.showMessageDialog(LiteTieTRIAL.this,
										"The value in for nGon sides in\nthe Spinner cannot be parsed.\nThe default value of 6 has been returned.");
						toolNGonSpinner.setValue(6);
						System.out.println("Caught - NumberFormatException");
						return false;
					}
				}
			});

		}
		return toolNGonSpinner;
	}

	// boolean isClosed = false;

	public void setCurrentPattern(Object o) {
		for (Pattern pattern : world.getPatternList().values()) {
			if (pattern.getAllDummysAndHoles().contains(o)) {
				currentPattern = pattern;
				System.out.println(
						"Current Pattern = ID" + pattern.getPatternID() + "Name = " + pattern.getPatternName());
			}
		}
	}

	/**
	 * This method initializes toolSelectionButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolSelectionButton() {
		if (toolSelectionButton == null) {
			toolSelectionButton = new JToggleButton();
			toolSelectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolSelectionButton.setMinimumSize(new Dimension(20, 20));
			toolSelectionButton.setMaximumSize(new Dimension(20, 20));

			// sets the icon of the button

			toolSelectionButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/selectSingle.png"))));
			// sets the tool tip
			toolSelectionButton.setPreferredSize(new Dimension(30, 30));
			toolSelectionButton.setToolTipText("Selection Tool");

			// toolSelectionButton.setRolloverIcon(new
			// ImageIcon(getClass().getResource("/icons_LiteTie/TOOLSelectionTrue.png")));
			toolSelectionButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					setStatusBarLabel("SELECTION TOOL");
					labelColourStroke.setText("Stroke");
					getColorWellStroke().setBackground(currentColor);
					getColorWellFill().repaint();
					currentMouseClicker = new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							// For multiple selection
							if (e.isShiftDown()) {
								updateCanvas();
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

								if (!(world.getPatternList() == null)) {
									selected = world.addNextObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								} else if (!(world.getBoundaryList() == null)) {
									selected = world.addNextObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								} else if (!(world.getTextList() == null)) {
									selected = world.addNextObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								}

								for (Object o : ((Set<Object>) selected)) {
									if (o instanceof Dummy) {
										statusBarLabel.setText("Hole Selected: Hole ID is " + ((Dummy) o).getHoleID());
										setCurrentPattern(o);
									}
									if (o instanceof BPoint) {
										statusBarLabel
												.setText("Point Selected: Point ID is " + ((BPoint) o).getPointID());

									}
									if (o instanceof Text) {
										statusBarLabel.setText("Text Selected: Text ID is " + ((Text) o).getTextID());
									}
								}
							} else if (e.isAltDown()) {
								updateCanvas();
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

								if (!(world.getPatternList() == null)) {
									selected = world.removeFirstObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								} else if (!(world.getBoundaryList() == null)) {
									selected = world.removeFirstObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								} else if (!(world.getTextList() == null)) {
									selected = world.removeFirstObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								}
							} else if (e.getModifiers() == 4 || e.isControlDown()) {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

								if (!(world.getPatternList() == null)) {
								} else if (!(world.getBoundaryList() == null)) {
								} else if (!(world.getTextList() == null)) {
								}

								JPopupMenu popUpMenu = new JPopupMenu();

								JMenuItem makeIP = new JMenuItem("Set as IP");
								JMenuItem timeIP = new JMenuItem("IP delay time...");
								JMenuItem removeIP = new JMenuItem("Remove IP");

								JMenuItem insertDet = new JMenuItem("Add detonator");
								JMenuItem detTime = new JMenuItem("Detonator delay ...");
								JMenuItem removeDet = new JMenuItem("Remove detonator");

								JMenuItem changeSurfaceConnector = new JMenuItem("Change surface connector delay...");
								JMenu subAdd = new JMenu("New");
								JMenuItem subPat = new JMenuItem("Pattern...");

								JMenuItem changeBoundary = new JMenuItem("Change Boundary Apperance...");

								JMenuItem subHole = new JMenuItem("Hole...");
								JMenuItem subDum = new JMenuItem("Dummy...");
								JMenuItem subBound = new JMenuItem("Boundary");

								JMenuItem del = new JMenuItem("Delete Selected");
								JMenuItem copy = new JMenuItem("Copy");
								JMenuItem cut = new JMenuItem("Cut");
								JMenuItem paste = new JMenuItem("Paste");

								JMenu sel = new JMenu("Select");
								JMenuItem selectAllInWorld = new JMenuItem("Select All");
								JMenuItem selectAll = new JMenuItem("Select All in Pattern");
								JMenuItem selectHoles = new JMenuItem("Select all holes");
								JMenuItem selectDummys = new JMenuItem("Select all dummys");
								JMenuItem selectTies = new JMenuItem("Select all ties");
								JMenuItem selectDets = new JMenuItem("Select all detonators");
								JMenuItem selectIPs = new JMenuItem("Select all IPs");

								JMenuItem move = new JMenuItem("Move to...");
								JMenuItem rotate = new JMenuItem("Rotate hole bearings...");
								JMenuItem dip = new JMenuItem("Change hole dip...");

								JMenuItem label = new JMenuItem("Re-label...");
								JMenuItem properties = new JMenuItem("Properties...");
								JMenuItem changeSymbol = new JMenuItem("Change hole Symbol...");

								// Action Listeners

								properties.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										for (Object o : ((Set<Object>) selected)) {
											if (o instanceof Hole && o != null) {

												Hole hole = ((Hole) o);
												if (o instanceof Dummy) {
													statusBarLabel.setText(
															"Hole Selected: Hole ID is " + ((Dummy) o).getHoleID());
													setCurrentPattern(o);
												}
												try {

													hole = DialogPropertiesHole.showBox(canvasPanel, hole);
													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (hole != null) {
														((Hole) o).setX((hole.getX()));
														((Hole) o).setY((hole.getY()));
														((Hole) o).setZ((hole.getZ()));
														((Hole) o).setLabelOne(hole.getLabelOne());
														((Hole) o).setLabelTwo(hole.getLabelTwo());
														((Hole) o).setLabelThree(hole.getLabelThree());
														((Hole) o).setDiameter(hole.getDiameter());
														((Hole) o).setHoleLength(hole.getHoleLength());
														((Hole) o).setBench(hole.getBench());
														((Hole) o).setBearing(hole.getBearing());
														((Hole) o).setFloorRL(hole.getFloorRL());
														((Hole) o).setToeRL(hole.getToeRL());
														((Hole) o).setAngle(hole.getAngle());
														((Hole) o).setSubdrill(hole.getSubdrill());
														((Hole) o).setShape(hole.getShape());
														((Hole) o).setColor(hole.getColor());

													} else
														JOptionPane.showMessageDialog(null, "Nothing was changed.");

													updateCanvas();
													statusBarLabel.setText(
															"Hole #" + ((Hole) o).getHoleID() + " has been moved");
												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												} catch (ZeroArgumentException e1) {
													// TODO
													// Auto-generated
													// catch
													// block
													e1.printStackTrace();
												} catch (NegativeNumberException e1) {
													// TODO
													// Auto-generated
													// catch
													// block
													e1.printStackTrace();
												}

											}

											JTextField[] listedTies = { getTieTextField1(), getTieTextField2(),
													getTieTextField3(), getTieTextField4(), getTieTextField5(),
													getTieTextField6() };

											if (o instanceof SurfaceConnector && (!(o instanceof Hole))
													&& (!(o instanceof BPoint)) && (!(o instanceof Boundary))) {

												SurfaceConnector sc = ((SurfaceConnector) o);
												Pattern tempPattern = currentPattern;
												for (Pattern p : world.getPatternList().values()) {
													if (p.getAllDummysAndHoles()
															.contains(((SurfaceConnector) o).getTo())) {
														tempPattern = p;
													}
												}

												try {

													sc = (SurfaceConnector) DialogPropertiesSurfaceConnector
															.showBox(canvasPanel, sc, listedTies);
													if (sc != null) {
														((SurfaceConnector) o).setSurfaceID(sc.getSurfaceID());
														((SurfaceConnector) o).setDelay(sc.getDelay());
														((SurfaceConnector) o).setFrom(
																((Pattern) tempPattern).getHole(sc.getFromHID()));
														((SurfaceConnector) o)
																.setTo(((Pattern) tempPattern).getHole(sc.getToHID()));
														((SurfaceConnector) o).setColor(sc.getColor());
													} else
														JOptionPane.showMessageDialog(null, "Nothing was changed.");

													updateCanvas();

												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Number Format Input",
															"Number Format Error", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (ZeroArgumentException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Zero Argument Input",
															"Negative Number Exception", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (NegativeNumberException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Negative Number Input",
															"Number Format Error", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (NullPointerException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Null Pointer Input",
															"Null Pointer Exception", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (FromToException e1) {
													JOptionPane.showMessageDialog(null, "Invalid From To Input",
															"From To Exception", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												}
											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();

										}
										updateCanvas();

									}
								});

								changeBoundary.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										for (Object o : ((Set<Object>) selected)) {
											for (Boundary temp : world.getBoundaryList().values()) {
												for (BPoint b : temp.getBPList().values()) {
													boolean isSelected = b == selected
															|| (selected instanceof Collection<?>
																	&& ((Collection<?>) selected).contains(b));
													if (isSelected) {
														try {
															float[] dashPattern = { 0, 0 };
															if (temp.getStroke().getDashArray() != null) {
																dashPattern = temp.getStroke().getDashArray();
															}
															int dash = (int) dashPattern[0];
															int space = (int) dashPattern[1];

															BasicStroke stroke = DialogBasicStroke.showBox(canvasPanel,
																	(int) temp.getStroke().getLineWidth(), (int) dash,
																	(int) space);

															temp.setStroke(stroke);
															if (temp.getStroke().getDashArray() != null) {
																dashPattern = temp.getStroke().getDashArray();
															}
															dash = (int) dashPattern[0];
															space = (int) dashPattern[1];
															setConsoleOutput(
																	"\nStroke Boundary Appearance\nLine width = "
																			+ temp.getStroke().getLineWidth()
																			+ "\nDash Length = "
																			+ dash + "\nSpace Length = " + space);
														} catch (NegativeNumberException e1) {
															// TODO
															// Auto-generated
															// catch
															// block
															e1.printStackTrace();
														}

													}
												}
												updateCanvas();
											}

											// ((Boundary)
											// o).setStroke(stroke);
											boundaryTree.validate(); // redraws
																		// the
																		// Jtree
																		// this
																		// means
																		// everything
																		// collapses.

										}
									}

								});
								// IP Listeners
								makeIP.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										try {

											for (Object o : ((Set<Object>) selected)) {
												if (o instanceof Dummy && (!(o instanceof Hole))) {
													if (o instanceof Dummy) {
														statusBarLabel.setText(
																"Hole Selected: Hole ID is " + ((Dummy) o).getHoleID());
														setCurrentPattern(o);
													}
													currentPattern.addInitiationPoint(
															new InitiationPoint(currentPattern.getiPList().size() + 1,
																	0,
																	currentPattern.getDummy(((Dummy) o).getHoleID())),
															true);
													//
													updateCanvas();
													statusBarLabel.setText(
															"Dummy #" + ((Dummy) o).getHoleID() + " is now an IP");

												} else if (o instanceof Hole) {
													currentPattern.addInitiationPoint(
															new InitiationPoint(currentPattern.getiPList().size() + 1,
																	0, ((Hole) o)),
															true);
													//
													//
													updateCanvas();
													statusBarLabel.setText(
															"Hole #" + ((Hole) o).getHoleID() + " is now an IP");
												}
											}

										}

										catch (NegativeNumberException e1) {
											System.out.println(
													"makeIP Actionlistener -NegativeNumberException - getToolSelectionButton()");
											e1.printStackTrace();
										} catch (ZeroArgumentException e1) {
											System.out.println(
													"makeIP Actionlistener -ZeroArgumentException - getToolSelectionButton()");
											e1.printStackTrace();
										}
									}
								});
								// IP time Listener
								timeIP.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										Dummy d = ((Set<Object>) selected).isEmpty() ? null
												: (Dummy) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one

										for (int i = currentPattern.getiPList().lastKey(); i >= 1; i--) {
											if (currentPattern.getInitiationPoint(i) != null) {
												if (currentPattern.getInitiationPoint(i).getIPDummy().equals(d)
														&& d instanceof Dummy && (!(d instanceof Hole))) {
													String newValue = Integer
															.toString(currentPattern.getInitiationPoint(i).getIPTime());

													try {
														newValue = JOptionPane.showInputDialog("Input new IP time",
																Integer.toString(currentPattern.getInitiationPoint(i)
																		.getIPTime()));
														ip = currentPattern.getInitiationPoint(i);
														if (newValue != null || newValue != "") {
															ip.setIPTime(Integer.parseInt(newValue));
														}
														updateCanvas();
														statusBarLabel.setText("Dummy #" + d.getHoleID()
																+ " IP changed to " + ip.getIPTime() + "ms");

													} catch (NumberFormatException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number format is incorrect",
																"Number Format Error",
																JOptionPane.ERROR_MESSAGE);
													} catch (NegativeNumberException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number is negative",
																"Negative Number Error",
																JOptionPane.ERROR_MESSAGE);
													}
												}

												else if (currentPattern.getInitiationPoint(i).getIPDummy().equals(d)
														&& d instanceof Hole) {
													String newValue = Integer
															.toString(currentPattern.getInitiationPoint(i).getIPTime());

													try {
														newValue = JOptionPane.showInputDialog("Input new IP time",
																Integer.toString(currentPattern.getInitiationPoint(i)
																		.getIPTime()));
														ip = currentPattern.getInitiationPoint(i);
														ip.setIPTime(Integer.parseInt(newValue));
														updateCanvas();
														statusBarLabel.setText("Hole #" + ((Hole) d).getHoleID()
																+ " IP changed to " + ip.getIPTime() + "ms");

													} catch (NumberFormatException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number format is incorrect",
																"Number Format Error",
																JOptionPane.ERROR_MESSAGE);
													} catch (NegativeNumberException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number is negative",
																"Negative Number Error",
																JOptionPane.ERROR_MESSAGE);
													}
												}

											}
										}

									}
								});
								// remove IP listener
								removeIP.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										Dummy d = ((Set<Object>) selected).isEmpty() ? null
												: (Dummy) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one

										for (int i = currentPattern.getiPList().lastKey(); i >= 1; i--) {
											if (currentPattern.getInitiationPoint(i) != null) {
												ip = currentPattern.getInitiationPoint(i);
												for (Object o : ((Set<Object>) selected)) {
													if (o instanceof Dummy && (!(o instanceof Hole))) {
														if (ip.getIPDummy().getHoleID() == ((Dummy) o).getHoleID()
																&& ip.getIPDummy() != null
																&& (currentPattern.getHoleList().containsValue(o)
																		|| currentPattern.getDummyList()
																				.containsValue(o))) {
															currentPattern.removeIP(ip);
															updateCanvas();
															statusBarLabel.setText("Dummy #" + ((Dummy) o).getHoleID()
																	+ "'s IP is removed");
														}
													} else if (o instanceof Hole) {
														if (ip.getIPDummy().getHoleID() == ((Hole) o).getHoleID()) {
															currentPattern.removeIP(ip);
															updateCanvas();
															statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
																	+ "'s IP is removed");
														}
													}
												}
											}
										}
									}
								});
								// remove Detonator listener
								removeDet.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										Hole h = ((Set<Object>) selected).isEmpty() ? null
												: (Hole) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one

										for (int i = currentPattern.getDetonatorList().lastKey(); i >= 1; i--) {
											if (currentPattern.getDetonator(i) != null) {
												det = currentPattern.getDetonator(i);
												for (Object o : ((Set<Object>) selected)) {
													if (o instanceof Hole) {
														if (det.getInHole().getHoleID() == ((Hole) o).getHoleID()) {
															currentPattern.removeDetonator(det);
															updateCanvas();
															statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
																	+ "'s detonator is removed");
														}
													}
												}
											}
										}
									}
								});
								insertDet.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										try {

											for (Object o : ((Set<Object>) selected)) {
												if (getDetonatorTimeButton1().isSelected()) {
													if (o instanceof Hole) {
														currentPattern.addDetonator(
																new Detonator(
																		currentPattern.getDetonatorList().size() + 1,
																		Integer
																				.parseInt(getDetonatorTimeTextField2()
																						.getText()),
																		currentPattern.getHole(((Hole) o).getHoleID()),
																		((Hole) o).getHoleLength()
																				- ((Hole) o).getSubdrill(),
																		dColor1),
																true);
														//
														updateCanvas();
														statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
																+ " now has a detonator");

													}
												}

											}

										}

										catch (NegativeNumberException e1) {
											System.out.println(
													"makeIP Actionlistener -NegativeNumberException - getToolSelectionButton()");
											e1.printStackTrace();
										} catch (ZeroArgumentException e1) {
											System.out.println(
													"makeIP Actionlistener -ZeroArgumentException - getToolSelectionButton()");
											e1.printStackTrace();
										}
									}
								});
								detTime.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										Hole h = ((Set<Object>) selected).isEmpty() ? null
												: (Hole) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one

										for (int i = currentPattern.getDetonatorList().lastKey(); i >= 1; i--) {
											if (currentPattern.getDetonator(i) != null) {
												if (currentPattern.getDetonator(i).getInHole().equals(h)
														&& h instanceof Hole) {
													String newValue = Integer
															.toString(currentPattern.getDetonator(i).getDelay());

													try {
														newValue = JOptionPane.showInputDialog("Input new det.setDelay",
																Integer.toString(
																		currentPattern.getDetonator(i).getDelay()));
														det = currentPattern.getDetonator(i);
														if (newValue != null || newValue != "") {
															det.setDelay(Integer.parseInt(newValue));
														}

														statusBarLabel.setText("Hole #" + h.getHoleID()
																+ " Detonator delay changed to " + det.getDelay()
																+ "ms");

													} catch (NumberFormatException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number format is incorrect",
																"Number Format Error",
																JOptionPane.ERROR_MESSAGE);
													} catch (NegativeNumberException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number is negative",
																"Negative Number Error",
																JOptionPane.ERROR_MESSAGE);
													}
												}
											}
										}

									}
								});

								// -----------------------------------------------------------------------------------------
								// delete listener in the JMenu
								// -----------------------------------------------------------------------------------------
								del.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										// TODO Add a Undo/Redo to all
										// the delete sections
										for (Object o : ((Set<Object>) selected)) {

											if (o instanceof Dummy && (!(o instanceof Hole))) {
												currentPattern.removeDummyOrHole(
														currentPattern.getDummy(((Dummy) o).getHoleID()));
												updateCanvas();
												getTreeModelPattern();
												getTreeModelSurface();

											} else if (o instanceof Hole) {
												currentPattern.removeDummyOrHole(
														currentPattern.getHole(((Hole) o).getHoleID()));
												updateCanvas();
												getTreeModelPattern();
												getTreeModelSurface();
											} else if (o instanceof SurfaceConnector) {
												currentPattern.removeSC(currentPattern
														.getSurfaceConnector(((SurfaceConnector) o).getSurfaceID()));
												updateCanvas();
												getTreeModelSurface();
											}
											selected = new Object();
										}
										if (world.getPatternList().isEmpty() || world.getAllObjectsInWorld() == null) {
											getSaveButton().setEnabled(false);
											getSaveMenuItem().setEnabled(false);
											getTreeModelPattern();
											getTreeModelSurface();
										}
									}
								});
								// select All listener
								selectAllInWorld.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										selected = world.getAllObjectsInWorld();
										updateCanvas();

									}
								});
								// select All listener
								selectAll.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										selected = currentPattern.getAllObjectsInPattern();
										updateCanvas();

									}
								});
								// selectHoles All listener
								selectHoles.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										selected = currentPattern.getAllHolesInPattern();

										updateCanvas();

									}
								});
								// selectDummys All listener
								selectDummys.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllDummysInPattern();
										updateCanvas();

									}
								});
								// selectTies All listener
								selectTies.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllSurfaceConnectors();
										updateCanvas();

									}
								});
								// selectDets All listener
								selectDets.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllDetonators();
										updateCanvas();

									}
								});
								// selectIPs All listener
								selectIPs.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllInitiationPoints();
										updateCanvas();

									}
								});
								// rotated by
								rotate.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										for (Object o : ((Set<Object>) selected)) {
											if (o instanceof Hole) {
												String newBearing = Double.toString(
														currentPattern.getHole(((Hole) o).getHoleID()).getBearing());
												try {
													newBearing = JOptionPane.showInputDialog(
															"Input new Bearing (North = 0" + "\u00B0" + ")",
															Double.toString(currentPattern
																	.getHole(((Hole) o).getHoleID()).getBearing()));
													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (newBearing != null || newBearing != "") {
														((Hole) o).setBearing(Double.parseDouble(newBearing));
													}
													statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
															+ " Bearing change to " + newBearing + "\u00B0");

												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												}
											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();
										}
										updateCanvas();

									}
								});
								// move to
								move.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										for (Object o : ((Set<Object>) selected)) {
											if (o instanceof Hole && o != null) {

												Coordinate newCoordinate = new Coordinate(
														currentPattern.getHole(((Hole) o).getHoleID()).getX(),
														currentPattern.getHole(
																((Hole) o).getHoleID()).getY(),
														currentPattern.getHole(((Hole) o).getHoleID()).getZ());

												try {

													newCoordinate = DialogXYZValues.showBox(canvasPanel, newCoordinate);

													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (newCoordinate != null) {
														((Hole) o).setX((newCoordinate.getX()));
														((Hole) o).setY((newCoordinate.getY()));
														((Hole) o).setZ((newCoordinate.getZ()));
														// undoManager.addEdit(undoableMoveObject())
													} else

														updateCanvas();
													statusBarLabel.setText(
															"Hole #" + ((Hole) o).getHoleID() + " has been moved");
												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												}

											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();

										}

									}
								});
								// changeSymbol
								changeSymbol.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										Object[] options = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
										String newValue = (String) JOptionPane.showInputDialog(canvasPanel,
												"Select Hole Shape.\n1 = Circle,2 = Cross,\n"
														+ "3 = Triangle, 4 = Square\n" + "5 = Pentagon, 6 = Hexagon\n"
														+ "7 = Star, 8 = Fill Circle\n"
														+ "9 = Fill Triangle, 10 = Fill Square",
												"Hole Shape", JOptionPane.QUESTION_MESSAGE, liteTieIcon, options, 1);

										for (Pattern tempPat : world.getPatternList().values()) {
											for (Hole temp : tempPat.getHoleList().values()) {
												boolean isSelected = temp == selected
														|| (selected instanceof Collection<?>
																&& ((Collection<?>) selected).contains(temp));
												if (isSelected) {
													temp.setShape(Integer.parseInt(newValue));
												}
												updateCanvas();
											}
											getTreeModelPattern();
											updateCanvas();
										}

										updateCanvas();

									}
								});
								/*
								 * °DEGREE SIGN Unicode: U+00B0, UTF-8: C2 B0
								 */
								dip.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										for (Object o : ((Set<Object>) selected)) {
											if (o instanceof Hole) {
												String newDip = Double.toString(
														currentPattern.getHole(((Hole) o).getHoleID()).getAngle());

												try {
													newDip = JOptionPane.showInputDialog(
															"Input new Dip (Vertical = 90" + "\u00B0" + ")",
															Double.toString(currentPattern
																	.getHole(((Hole) o).getHoleID()).getAngle()));

													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (newDip != null || newDip != "") {
														((Hole) o).setAngle(Double.parseDouble(newDip));
													}

													statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
															+ " Dip change to " + newDip + "\u00B0");

												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												}
											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();

										}

										updateCanvas();

									}
								});
								// Adding a new pattern through the
								// contextual menus
								subPat.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										currentMouseMover = null;
										setStatusBarLabel("Click in Screen");// fix
																				// this
																				// later
																				// to
																				// something
																				// for
																				// the
																				// layman.
										currentMouseClicker = new MouseAdapter() {
											@Override
											public void mouseReleased(MouseEvent e) {

												double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
														- UnitConvert.pixelsToMeters(e.getY())
																/ Zoom.getScalingFactor();
												System.out.println(northing + "= northing");
												double easting = getCanvasSize().getX()
														+ UnitConvert.pixelsToMeters(e.getX())
																/ Zoom.getScalingFactor();
												System.out.println(easting + "= easting");
												try {
													pattern = DialogCreatePattern.showBox(null,
															Double.parseDouble(decimalFormat4.format(northing)),
															Double.parseDouble(decimalFormat4.format(easting)),
															averageCurrentPatternCRL());
												} catch (NumberFormatException e1) {
													System.out.println(
															"getToolPatternButton() method - NumberFormatException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - NumberFormatException");
													e1.printStackTrace();
												} catch (IllegalArgumentException e1) {
													System.out.println(
															"getToolPatternButton() method - IllegalArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - IllegalArgumentException");
													e1.printStackTrace();
												} catch (NegativeNumberException e1) {
													System.out.println(
															"getToolPatternButton() method - NegativeNumberException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - NegativeNumberException");
													e1.printStackTrace();
												} catch (ZeroArgumentException e1) {
													System.out.println(
															"getToolPatternButton() method - ZeroArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - ZeroArgumentException");
													e1.printStackTrace();
												}
												if (!(pattern == null)) {
													currentPattern = pattern;
													try {
														world.addPattern(pattern, false);
													} catch (ZeroArgumentException e1) {
														e1.printStackTrace();
													} catch (NegativeNumberException e1) {
														e1.printStackTrace();
													}
													saveAsMenuItem.setEnabled(true);
													saveAsButton.setEnabled(true);
													updateCanvas();
													statusBarLabel.setText("Pattern Created: You have "
															+ world.getPatternList().size()
															+ " pattern(s). Current Pattern has "
															+ currentPattern.getHoleList().size() + " hole(s).");
													currentMouseClicker = defaultMouseClicker;
													currentMouseMover = defaultMouseMover;
												} else if (pattern == null) {
													statusBarLabel.setText("Pattern Not Created");
												}
												getTreeModelPattern();
												getTreeModelSurface();

												System.out.println("World node count =" + getTreeModelPattern()
														.getModel().getChildCount(patternTreeModel.getRoot()));
											}
										};

										updateCanvas();

									}
								});
								subHole.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										currentMouseMover = null;
										setStatusBarLabel("Click in Screen");// fix
																				// this
																				// later
																				// to
																				// something
																				// for
																				// the
																				// layman.
										currentMouseClicker = new MouseAdapter() {
											@Override
											public void mouseReleased(MouseEvent e) {
												double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
														- UnitConvert.pixelsToMeters(e.getY())
																/ Zoom.getScalingFactor();
												double easting = getCanvasSize().getX()
														+ UnitConvert.pixelsToMeters(e.getX())
																/ Zoom.getScalingFactor();
												System.out.println("*     " + Double.toString(northing) + " & "
														+ Double.toString(easting));// FOR
																					// DEBUGGING
												try {
													dummy = DialogCreateHole.showBox(null,
															Double.parseDouble(decimalFormat4.format(northing)),
															Double.parseDouble(decimalFormat4.format(easting)),
															averageCRL, true);
													System.out.println("**    " + Double.toString(northing) + " & "
															+ Double.toString(easting));// FOR
																						// DEBUGGING
												} catch (NumberFormatException e2) {
													System.out.println(
															"getToolHoleButton() method - NumberFormatException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolHoleButton() method - NumberFormatException");
													e2.printStackTrace();
												} catch (ZeroArgumentException e2) {
													System.out.println(
															"getToolHoleButton() method - ZeroArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolHoleButton() method - ZeroArgumentException");
													e2.printStackTrace();
												} catch (NegativeNumberException e2) {
													System.out.println(
															"getToolHoleButton() method - NegativeNumberException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolHoleButton() method - NegativeNumberException");
													e2.printStackTrace();
												}
												if (!(dummy == null)) {
													if (currentPattern == null) {
														Pattern firstPattern = new Pattern(0, 0);
														try {
															world.addPattern(firstPattern, false);
														} catch (ZeroArgumentException e1) {
															// TODO
															// Auto-generated
															// catch
															// block
															e1.printStackTrace();
														} catch (NegativeNumberException e1) {
															// TODO
															// Auto-generated
															// catch
															// block
															e1.printStackTrace();
														}
														currentPattern = firstPattern;
													}
													try {
														currentPattern.addDummy(dummy, false);
													} catch (NegativeNumberException e1) {
														System.out.println(
																"getToolHoleButton() method - NegativeNumberException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolHoleButton() method - NegativeNumberException");
														e1.printStackTrace();
													} catch (ZeroArgumentException e1) {
														System.out.println(
																"getToolHoleButton() method - ZeroArgumentException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolHoleButton() method - ZeroArgumentException");
														e1.printStackTrace();
													}
													saveAsMenuItem.setEnabled(true);
													updateCanvas();
												}
												getTreeModelSurface();
												getTreeModelPattern();
											}
										};

										updateCanvas();
									}
								});
								// Adding a dummy through the contextual
								// menus
								subDum.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										currentMouseMover = null;
										setStatusBarLabel("Click in Screen");// fix
																				// this
																				// later
																				// to
																				// something
																				// for
																				// the
																				// layman.
										currentMouseClicker = new MouseAdapter() {
											@Override
											public void mouseReleased(MouseEvent e) {
												double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
														- UnitConvert.pixelsToMeters(e.getY())
																/ Zoom.getScalingFactor();
												double easting = getCanvasSize().getX()
														+ UnitConvert.pixelsToMeters(e.getX())
																/ Zoom.getScalingFactor();
												try {
													System.out
															.println(
																	"Entered Try to dummy = NewHoleDialogue.showBox(null, Double.parseDouble(dec4p.format(northing)), Double.parseDouble(dec4p.format(easting)), averageCRL, false);");
													dummy = DialogCreateHole.showBox(null,
															Double.parseDouble(decimalFormat4.format(northing)),
															Double.parseDouble(decimalFormat4.format(easting)),
															averageCRL, false);
												} catch (NumberFormatException e2) {
													System.out.println(
															"getToolDummyButton() method - NumberFormatException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolDummyButton() method - NumberFormatException");
													e2.printStackTrace();
												} catch (ZeroArgumentException e2) {
													System.out.println(
															"getToolDummyButton() method - ZeroArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolDummyButton() method - ZeroArgumentException");
													e2.printStackTrace();
												} catch (NegativeNumberException e2) {
													System.out.println(
															"getToolDummyButton() method - NegativeNumberException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolDummyButton() method - NegativeNumberException");
													e2.printStackTrace();
												}
												if (!(dummy == null)) {
													if (currentPattern == null) {
														Pattern firstPattern = new Pattern(0, 0);
														try {
															world.addPattern(firstPattern, false);
														} catch (ZeroArgumentException e1) {
															e1.printStackTrace();
														} catch (NegativeNumberException e1) {
															e1.printStackTrace();
														}
														currentPattern = firstPattern;
													}
													try {
														currentPattern.addDummy(dummy, false);
													} catch (NegativeNumberException e1) {
														System.out.println(
																"getToolDummyButton() method - NegativeNumberException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolDummyButton() method - NegativeNumberException");
														e1.printStackTrace();
													} catch (ZeroArgumentException e1) {
														System.out.println(
																"getToolDummyButton() method - ZeroArgumentException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolDummyButton() method - ZeroArgumentException");
														e1.printStackTrace();
													}
													saveAsMenuItem.setEnabled(true);
													updateCanvas();
												}
												getTreeModelSurface();
												getTreeModelPattern();
											}
										};

									}
								});
								Object o;
								if (!(world.getCoordList().isEmpty())) {
									o = ((Set<Object>) selected).isEmpty() ? null
											: ((Set<Object>) selected).iterator().next();
									setCurrentPattern(o);
									// start Dummy type check and
									// contextual menu creation
									if (o instanceof Dummy) {
										popUpMenu.add(makeIP);
										popUpMenu.add(timeIP);
										popUpMenu.add(removeIP);
										popUpMenu.addSeparator();
										popUpMenu.add(insertDet);
										popUpMenu.add(detTime);
										popUpMenu.add(removeDet);
										popUpMenu.addSeparator();

										popUpMenu.add(del);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectDets);
										sel.add(selectTies);
										sel.add(selectIPs);
										popUpMenu.addSeparator();
										popUpMenu.add(cut);
										popUpMenu.add(copy);
										popUpMenu.add(paste);
										popUpMenu.addSeparator();
										popUpMenu.add(move);
										popUpMenu.add(dip);
										popUpMenu.add(rotate);
										popUpMenu.addSeparator();
										popUpMenu.add(changeSymbol);
										popUpMenu.add(properties);
									} // end Dummy type check
										// start SurfaceConnector type
										// check and contextual menu on
										// surface connectors creation
									else if (o instanceof SurfaceConnector) {
										setCurrentPattern(((SurfaceConnector) o).getTo());
										popUpMenu.add(changeSurfaceConnector);
										popUpMenu.addSeparator();
										popUpMenu.add(del);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectTies);
										sel.add(selectIPs);
										popUpMenu.addSeparator();
										popUpMenu.add(cut);
										popUpMenu.add(copy);
										popUpMenu.add(paste);
										popUpMenu.addSeparator();
										popUpMenu.add(properties);
									} // end Surface Connector type check
									else if (o instanceof BPoint) {
										popUpMenu.add(changeBoundary);
										popUpMenu.addSeparator();
										popUpMenu.add(del);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectTies);
										sel.add(selectIPs);
										popUpMenu.addSeparator();
										popUpMenu.add(cut);
										popUpMenu.add(copy);
										popUpMenu.add(paste);
										popUpMenu.addSeparator();
										popUpMenu.add(properties);
									}
									// start NULL check if nothing is
									// selected then this contextual
									// menu will occur
									else if (o == null) {
										popUpMenu.add(subAdd);
										subAdd.add(subPat);
										subAdd.add(subHole);
										subAdd.add(subDum);
										subAdd.addSeparator();
										subAdd.add(subBound);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectDets);
										sel.add(selectTies);
										sel.add(selectIPs);
									} // end NULL check
									else {
										popUpMenu.add(subAdd);
										subAdd.add(subPat);
										subAdd.add(subHole);
										subAdd.add(subDum);
										subAdd.addSeparator();
										subAdd.add(subBound);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectDets);
										sel.add(selectTies);
										sel.add(selectIPs);
									}
								}

								else {
									popUpMenu.add(subAdd);
									subAdd.add(subPat);
									subAdd.add(subHole);
									subAdd.add(subDum);
									subAdd.addSeparator();
									subAdd.add(subBound);
								}
								popUpMenu.show(canvasPanel, e.getX(), e.getY());
								updateCanvas();
							}

							else {
								// ///////////////////////////////////////
								// ///// NORMAL SELECTION
								// ///////////////////////////////////////

								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

								if (!(world.getPatternList() == null)) {
									selected = world.getFirstObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));

									updateCanvas();
								} else if (!(world.getBoundaryList() == null)) {
									selected = world.getFirstObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								} else if (!(world.getTextList() == null)) {
									selected = world.getFirstObjectIn(new Ellipse2D.Double(easting - radius,
											northing - radius, radius * 2, radius * 2));
									updateCanvas();
								}

								// start for loop

								try {
									for (Object o : ((Set<Object>) selected)) {
										if (o instanceof Dummy && (!(o instanceof Hole))) {
											statusBarLabel
													.setText("Dummy Selected: Dummy ID is " + ((Dummy) o).getHoleID());
											setCurrentPattern(o);
										} else if (o instanceof Hole) {
											statusBarLabel
													.setText("Hole Selected: Hole ID is " + ((Hole) o).getHoleID());
											setCurrentPattern(o);
										} else if (o instanceof SurfaceConnector) {
											statusBarLabel
													.setText("Surface Connector Selected: Surface Connector ID is "
															+ ((SurfaceConnector) o).getSurfaceID());
										} else if (o instanceof BPoint) {
											statusBarLabel.setText(
													"Point Selected: Point ID is " + ((BPoint) o).getPointID());
										} else if (o instanceof Text) {
											statusBarLabel
													.setText("Text Selected: Text ID is " + ((Text) o).getTextID());
										}

									} // end for loop
								} catch (Exception e3) {
									statusBarLabel.setText("Nothing Selected");
									// TODO: handle exception
								}

							}
						}

					};
				}
			});
			toolSelectionButton.setFocusable(true);
			toolSelectionButton.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.isShiftDown()) {
					} else if (e.isAltDown()) {
					}

				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (!(e.isShiftDown()) && !(e.isAltDown())) {
					}
				}

			});
		}
		return toolSelectionButton;
	}

	/**
	 * This method initializes toolMarqueeButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolSelectionMarqueeButton() {
		if (toolSelectionMarqueeButton == null) {
			toolSelectionMarqueeButton = new JToggleButton();
			toolSelectionMarqueeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolSelectionMarqueeButton.setMinimumSize(dimension30x30);
			toolSelectionMarqueeButton.setMaximumSize(dimension30x30);
			toolSelectionMarqueeButton
					.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/selectSquare.png"))));
			toolSelectionMarqueeButton.setToolTipText("Rectangular Selection Tool");
			toolSelectionMarqueeButton.setPreferredSize(new Dimension(30, 30));
			toolSelectionMarqueeButton.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selectionPoint1 = null;
						selectionPoint2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyChar() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_S) {
						statusBarLabel.setText("Surface Connectors selection only");
						isVKSDown = true;
						selected = null;
						selectionType = "Selecting Surface Connections";
					} else if (e.getKeyChar() == KeyEvent.VK_H || e.getKeyCode() == KeyEvent.VK_H) {
						statusBarLabel.setText("Hole selection only");
						isVKHDown = true;
						selectionType = "Selecting Holes";
					} else if (e.getKeyChar() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_D) {
						statusBarLabel.setText("Detonator selection only");
						isVKDDown = true;
						selectionType = "Selecting Detonators";
					} else if (e.getKeyChar() == KeyEvent.VK_T || e.getKeyCode() == KeyEvent.VK_T) {
						statusBarLabel.setText("Text Label selection only");
						isVKTDown = true;
						selectionType = "Selecting Text";
					} else if (e.getKeyChar() == KeyEvent.VK_B || e.getKeyCode() == KeyEvent.VK_B) {
						statusBarLabel.setText("Boundary Point selection only");
						isVKBDown = true;
						selectionType = "Selecting Boundry Points";
					} else if (e.getKeyChar() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_X) {
						statusBarLabel.setText("Dummy selection only");
						isVKXDown = true;

						selectionType = "Selecting Dummy Holes";
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						// change mouse cursor to a marquee cursor with
						// a plus symbol
					} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
						// change mouse cursor to a marquee cursor with
						// a minus symbol
					} else {
						selectionType = "Selecting All";
					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selectionPoint1 = null;
						selectionPoint2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyChar() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_S) {
						isVKSDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyChar() == KeyEvent.VK_H || e.getKeyCode() == KeyEvent.VK_H) {
						isVKHDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyChar() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_D) {
						isVKDDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyChar() == KeyEvent.VK_T || e.getKeyCode() == KeyEvent.VK_T) {
						isVKTDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyChar() == KeyEvent.VK_B || e.getKeyCode() == KeyEvent.VK_B) {
						isVKBDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyChar() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_X) {
						isVKXDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						// change mouse cursor to a default cursor
					} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
						// change mouse cursor to a default cursor
					}

				}

			});
			toolSelectionMarqueeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					labelColourStroke.setText("Stroke");
					getColorWellStroke().setBackground(currentColor);
					currentMouseMover = null;

					setStatusBarLabel("Click in Screen");// fix this
															// later to
															// something
															// for the
															// layman.

					currentMouseClicker = new MouseAdapter() {

						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {

							if (!clicked) {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								selectionPoint1 = new Point2D.Double(easting, northing);
								setStatusBarLabel("Last Northing = "
										+ Double.parseDouble(decimalFormat4.format(selectionPoint1.getY()))
										+ " & Last Easting = "
										+ Double.parseDouble(decimalFormat4.format(selectionPoint1.getX())));
								currentMouseMover = new MouseMotionAdapter() {
									public void mouseMoved(MouseEvent e) {
										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

										selectionPoint2 = new Point2D.Double(easting, northing);

										if (selectionPoint1 != null) {
											if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
													|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
															.isEmpty())
													&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown
													&& !isVKTDown && !isVKXDown) {

												selected = world.getAllObjectsIn(new Rectangle2D.Double(
														Math.min(selectionPoint1.getX(), selectionPoint2.getX()),
														Math.min(
																selectionPoint1.getY(), selectionPoint2.getY()),
														Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
																.abs(selectionPoint1.getY() - selectionPoint2.getY())));
												updateCanvas();
											}
											if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
													|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
															.isEmpty())
													&& isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
													&& !isVKXDown) {

												selected = (LinkedHashSet<Object>) world
														.getAllSurfaceIn(new Rectangle2D.Double(
																Math.min(selectionPoint1.getX(),
																		selectionPoint2.getX()),
																Math.min(selectionPoint1.getY(),
																		selectionPoint2.getY()),
																Math.abs(selectionPoint1.getX()
																		- selectionPoint2.getX()),
																Math.abs(selectionPoint1.getY()
																		- selectionPoint2.getY())));
												updateCanvas();
											}
											if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
													|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
															.isEmpty())
													&& !isVKSDown && isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
													&& !isVKXDown) {

												selected = (LinkedHashSet<Object>) world
														.getAllBPointsIn(new Rectangle2D.Double(
																Math.min(selectionPoint1.getX(),
																		selectionPoint2.getX()),
																Math.min(selectionPoint1.getY(),
																		selectionPoint2.getY()),
																Math.abs(selectionPoint1.getX()
																		- selectionPoint2.getX()),
																Math.abs(selectionPoint1.getY()
																		- selectionPoint2.getY())));
												updateCanvas();
											}
											if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
													|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
															.isEmpty())
													&& !isVKSDown && !isVKBDown && isVKHDown && !isVKDDown && !isVKTDown
													&& !isVKXDown) {

												selected = world.getAllHolesIn(new Rectangle2D.Double(
														Math.min(selectionPoint1.getX(), selectionPoint2.getX()),
														Math.min(
																selectionPoint1.getY(), selectionPoint2.getY()),
														Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
																.abs(selectionPoint1.getY() - selectionPoint2.getY())));
												updateCanvas();
											}
											if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
													|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
															.isEmpty())
													&& !isVKSDown && !isVKBDown && !isVKHDown && isVKDDown && !isVKTDown
													&& !isVKXDown) {

												selected = world.getAllDetonatorsIn(new Rectangle2D.Double(
														Math.min(selectionPoint1.getX(), selectionPoint2.getX()),
														Math.min(
																selectionPoint1.getY(), selectionPoint2.getY()),
														Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
																.abs(selectionPoint1.getY() - selectionPoint2.getY())));
												updateCanvas();
											}
											if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
													|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
															.isEmpty())
													&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && isVKTDown
													&& !isVKXDown) {

												selected = (LinkedHashSet<Object>) world
														.getAllTextIn(new Rectangle2D.Double(
																Math.min(selectionPoint1.getX(),
																		selectionPoint2.getX()),
																Math.min(selectionPoint1.getY(),
																		selectionPoint2.getY()),
																Math.abs(selectionPoint1.getX()
																		- selectionPoint2.getX()),
																Math.abs(selectionPoint1.getY()
																		- selectionPoint2.getY())));
												updateCanvas();
											}
											if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
													|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
															.isEmpty())
													&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown
													&& !isVKTDown && isVKXDown) {

												selected = world.getAllDummysIn(new Rectangle2D.Double(
														Math.min(selectionPoint1.getX(), selectionPoint2.getX()),
														Math.min(
																selectionPoint1.getY(), selectionPoint2.getY()),
														Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
																.abs(selectionPoint1.getY() - selectionPoint2.getY())));
												updateCanvas();
											}
											setStatusBarLabel("Last Northing = "
													+ Double.parseDouble(decimalFormat4.format(selectionPoint2.getY()))
													+ " & Last Easting = "
													+ Double.parseDouble(
															decimalFormat4.format(selectionPoint2.getX())));

										}

									}
								};

							} else {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								selectionPoint2 = new Point2D.Double(easting, northing);

								if (selectionPoint1 != null) {

									if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
											&& !isVKDDown && !isVKTDown && !isVKXDown) {

										selected = world.getAllObjectsIn(new Rectangle2D.Double(
												Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
														selectionPoint1.getY(), selectionPoint2.getY()),
												Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
														.abs(selectionPoint1.getY() - selectionPoint2.getY())));
										updateCanvas();
									}
									if (!world.getPatternList().isEmpty() && isVKSDown && !isVKBDown && !isVKHDown
											&& !isVKDDown && !isVKTDown && !isVKXDown) {

										selected = world.getAllSurfaceIn(new Rectangle2D.Double(
												Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
														selectionPoint1.getY(), selectionPoint2.getY()),
												Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
														.abs(selectionPoint1.getY() - selectionPoint2.getY())));
										updateCanvas();
									}
									if (!world.getPatternList().isEmpty() && !isVKSDown && isVKBDown && !isVKHDown
											&& !isVKDDown && !isVKTDown && !isVKXDown) {

										selected = world.getAllBPointsIn(new Rectangle2D.Double(
												Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
														selectionPoint1.getY(), selectionPoint2.getY()),
												Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
														.abs(selectionPoint1.getY() - selectionPoint2.getY())));
										updateCanvas();
									}
									if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && isVKHDown
											&& !isVKDDown && !isVKTDown && !isVKXDown) {

										selected = world.getAllHolesIn(new Rectangle2D.Double(
												Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
														selectionPoint1.getY(), selectionPoint2.getY()),
												Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
														.abs(selectionPoint1.getY() - selectionPoint2.getY())));
										updateCanvas();
									}
									if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
											&& isVKDDown && !isVKTDown && !isVKXDown) {

										selected = world.getAllDetonatorsIn(new Rectangle2D.Double(
												Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
														selectionPoint1.getY(), selectionPoint2.getY()),
												Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
														.abs(selectionPoint1.getY() - selectionPoint2.getY())));
										updateCanvas();
									}
									if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
											&& !isVKDDown && isVKTDown && !isVKXDown) {

										selected = world.getAllTextIn(new Rectangle2D.Double(
												Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
														selectionPoint1.getY(), selectionPoint2.getY()),
												Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
														.abs(selectionPoint1.getY() - selectionPoint2.getY())));
										updateCanvas();
									}
									if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
											&& !isVKDDown && !isVKTDown && isVKXDown) {

										selected = (world.getAllDummysIn(new Rectangle2D.Double(
												Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
														selectionPoint1.getY(), selectionPoint2.getY()),
												Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
														.abs(selectionPoint1.getY() - selectionPoint2.getY()))));
										updateCanvas();
									}

									setStatusBarLabel("Selected: " + ((Collection<?>) selected).size() + " object(s)");
									currentMouseMover = defaultMouseMover;
									selectionPoint1 = null;
									selectionPoint2 = null;

								}
							}

							clicked = !clicked;

							if (selected != null && e.getModifiers() == 4 || e.isControlDown()) {
								selectionPoint1 = null;
								selectionPoint2 = null;
								JPopupMenu popUpMenu = new JPopupMenu();

								JMenuItem makeIP = new JMenuItem("Set as IP");
								JMenuItem timeIP = new JMenuItem("IP delay time...");
								JMenuItem removeIP = new JMenuItem("Remove IP");

								JMenuItem insertDet = new JMenuItem("Add detonator");
								JMenuItem detTime = new JMenuItem("Detonator delay ...");
								JMenuItem removeDet = new JMenuItem("Remove detonator");

								JMenuItem changeSC = new JMenuItem("Change surface connector delay...");
								JMenu subAdd = new JMenu("New");
								JMenuItem subPat = new JMenuItem("Pattern...");

								JMenuItem subHole = new JMenuItem("Hole...");
								JMenuItem subDum = new JMenuItem("Dummy...");
								JMenuItem subBound = new JMenuItem("Boundary");

								JMenuItem del = new JMenuItem("Delete Selected");
								JMenuItem copy = new JMenuItem("Copy");
								JMenuItem cut = new JMenuItem("Cut");
								JMenuItem paste = new JMenuItem("Paste");

								JMenu sel = new JMenu("Select");
								JMenuItem selectAllInWorld = new JMenuItem("Select All");
								JMenuItem selectAll = new JMenuItem("Select All in Pattern");
								JMenuItem selectHoles = new JMenuItem("Select all holes");
								JMenuItem selectDummys = new JMenuItem("Select all dummys");
								JMenuItem selectTies = new JMenuItem("Select all ties");
								JMenuItem selectDets = new JMenuItem("Select all detonators");
								JMenuItem selectIPs = new JMenuItem("Select all IPs");

								JMenuItem move = new JMenuItem("Move to...");
								JMenuItem rotate = new JMenuItem("Rotate hole bearings...");
								JMenuItem dip = new JMenuItem("Change hole dip...");

								JMenuItem label = new JMenuItem("Re-label");
								JMenuItem properties = new JMenuItem("Properties...");
								JMenuItem changeSymbol = new JMenuItem("Change hole symbol...");
								// Action Listeners

								// Properties
								properties.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										for (Object o : ((Set<Object>) selected)) {
											if (o instanceof Hole && o != null) {

												Hole hole = ((Hole) o);
												if (o instanceof Dummy) {
													statusBarLabel.setText(
															"Hole Selected: Hole ID is " + ((Dummy) o).getHoleID());
													setCurrentPattern(o);

												}
												try {

													hole = DialogPropertiesHole.showBox(canvasPanel, hole);
													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (hole != null) {
														((Hole) o).setX((hole.getX()));
														((Hole) o).setY((hole.getY()));
														((Hole) o).setZ((hole.getZ()));
														((Hole) o).setLabelOne(hole.getLabelOne());
														((Hole) o).setLabelTwo(hole.getLabelTwo());
														((Hole) o).setLabelThree(hole.getLabelThree());
														((Hole) o).setDiameter(hole.getDiameter());
														((Hole) o).setHoleLength(hole.getHoleLength());
														((Hole) o).setBench(hole.getBench());
														((Hole) o).setBearing(hole.getBearing());
														((Hole) o).setFloorRL(hole.getFloorRL());
														((Hole) o).setToeRL(hole.getToeRL());
														((Hole) o).setAngle(hole.getAngle());
														((Hole) o).setSubdrill(hole.getSubdrill());
														((Hole) o).setShape(hole.getShape());
														((Hole) o).setColor(hole.getColor());

													} else
														JOptionPane.showMessageDialog(null, "Nothing was changed.");

													updateCanvas();
													statusBarLabel.setText(
															"Hole #" + ((Hole) o).getHoleID() + " has been moved");
												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												} catch (ZeroArgumentException e1) {
													// TODO
													// Auto-generated
													// catch
													// block
													e1.printStackTrace();
												} catch (NegativeNumberException e1) {
													// TODO
													// Auto-generated
													// catch
													// block
													e1.printStackTrace();
												}

											}

											JTextField[] listedTies = { getTieTextField1(), getTieTextField2(),
													getTieTextField3(), getTieTextField4(), getTieTextField5(),
													getTieTextField6() };

											if (o instanceof SurfaceConnector && (!(o instanceof Hole))
													&& (!(o instanceof BPoint)) && (!(o instanceof Boundary))) {

												SurfaceConnector sc = ((SurfaceConnector) o);
												Pattern tempPattern = currentPattern;

												for (Pattern p : world.getPatternList().values()) {
													if (p.getAllDummysAndHoles()
															.contains(((SurfaceConnector) o).getTo())) {
														tempPattern = p;
													}
												}

												try {

													sc = (SurfaceConnector) DialogPropertiesSurfaceConnector
															.showBox(canvasPanel, sc, listedTies);
													if (sc != null) {
														for (int i = 0; i < ((LinkedHashSet<Object>) selected)
																.size(); i++) {

															((SurfaceConnector) o).setSurfaceID(sc.getSurfaceID());
															((SurfaceConnector) o).setDelay(sc.getDelay());
															((SurfaceConnector) o).setFrom(
																	((Pattern) tempPattern).getHole(sc.getFromHID()));
															((SurfaceConnector) o).setTo(
																	((Pattern) tempPattern).getHole(sc.getToHID()));
															((SurfaceConnector) o).setColor(sc.getColor());
														}
													} else
														JOptionPane.showMessageDialog(null, "Nothing was changed.");

													updateCanvas();

												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Number Format Input",
															"Number Format Error", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (ZeroArgumentException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Zero Argument Input",
															"Negative Number Exception", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (NegativeNumberException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Negative Number Input",
															"Number Format Error", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (NullPointerException e1) {
													JOptionPane.showMessageDialog(null, "Invalid Null Pointer Input",
															"Null Pointer Exception", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												} catch (FromToException e1) {
													JOptionPane.showMessageDialog(null, "Invalid From To Input",
															"From To Exception", JOptionPane.ERROR_MESSAGE);
													e1.printStackTrace();
												}
											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();

										}
										updateCanvas();

									}
								});

								// changeSymbol
								changeSymbol.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										Object[] options = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
										String newValue = (String) JOptionPane.showInputDialog(canvasPanel,
												"Select Hole Shape.\n1 = Circle,2 = Cross,\n"
														+ "3 = Triangle, 4 = Square\n" + "5 = Pentagon, 6 = Hexagon\n"
														+ "7 = Star, 8 = Fill Circle\n"
														+ "9 = Fill Triangle, 10 = Fill Square",
												"Hole Shape", JOptionPane.QUESTION_MESSAGE, liteTieIcon, options, 1);

										for (Pattern tempPat : world.getPatternList().values()) {
											for (Hole temp : tempPat.getHoleList().values()) {
												boolean isSelected = temp == selected
														|| (selected instanceof Collection<?>
																&& ((Collection<?>) selected).contains(temp));
												if (isSelected) {
													temp.setShape(Integer.parseInt(newValue));
												}
												updateCanvas();
											}
											getTreeModelPattern();
											updateCanvas();
										}
									}

								});
								// IP Listeners
								makeIP.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										try {

											for (Object o : ((Set<Object>) selected)) {
												if (o instanceof Dummy && (!(o instanceof Hole))) {
													currentPattern.addInitiationPoint(
															new InitiationPoint(currentPattern.getiPList().size() + 1,
																	0,
																	currentPattern.getDummy(((Dummy) o).getHoleID())),
															true);
													//
													updateCanvas();
													statusBarLabel.setText(
															"Dummy #" + ((Dummy) o).getHoleID() + " is now an IP");

												} else if (o instanceof Hole) {
													currentPattern.addInitiationPoint(
															new InitiationPoint(currentPattern.getiPList().size() + 1,
																	0, ((Hole) o)),
															true);
													//
													//
													updateCanvas();
													statusBarLabel.setText(
															"Hole #" + ((Hole) o).getHoleID() + " is now an IP");
												}
											}

										}

										catch (NegativeNumberException e1) {
											System.out.println(
													"makeIP Actionlistener -NegativeNumberException - getToolSelectionButton()");
											e1.printStackTrace();
										} catch (ZeroArgumentException e1) {
											System.out.println(
													"makeIP Actionlistener -ZeroArgumentException - getToolSelectionButton()");
											e1.printStackTrace();
										}
									}
								});
								// IP time Listener
								timeIP.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										Dummy d = ((Set<Object>) selected).isEmpty() ? null
												: (Dummy) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one
										for (Pattern pattern : world.getPatternList().values()) {
											if (pattern.getAllDummysInPattern().contains(d)) {
												currentPattern = pattern;
											}
										}
										for (int i = currentPattern.getiPList().lastKey(); i >= 1; i--) {
											if (currentPattern.getInitiationPoint(i) != null) {
												if (currentPattern.getInitiationPoint(i).getIPDummy().equals(d)
														&& d instanceof Dummy && (!(d instanceof Hole))) {
													String newValue = Integer
															.toString(currentPattern.getInitiationPoint(i).getIPTime());

													try {
														newValue = JOptionPane.showInputDialog("Input new IP time",
																Integer.toString(currentPattern.getInitiationPoint(i)
																		.getIPTime()));
														ip = currentPattern.getInitiationPoint(i);
														if (newValue != null || newValue != "") {
															ip.setIPTime(Integer.parseInt(newValue));
														}

														statusBarLabel.setText("Dummy #" + d.getHoleID()
																+ " IP changed to " + ip.getIPTime() + "ms");

													} catch (NumberFormatException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number format is incorrect",
																"Number Format Error",
																JOptionPane.ERROR_MESSAGE);
													} catch (NegativeNumberException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number is negative",
																"Negative Number Error",
																JOptionPane.ERROR_MESSAGE);
													}
												}

												else if (currentPattern.getInitiationPoint(i).getIPDummy().equals(d)
														&& d instanceof Hole) {
													String newValue = Integer
															.toString(currentPattern.getInitiationPoint(i).getIPTime());

													try {
														newValue = JOptionPane.showInputDialog("Input new IP time",
																Integer.toString(currentPattern.getInitiationPoint(i)
																		.getIPTime()));
														ip = currentPattern.getInitiationPoint(i);
														ip.setIPTime(Integer.parseInt(newValue));
														updateCanvas();
														statusBarLabel.setText("Hole #" + ((Hole) d).getHoleID()
																+ " IP changed to " + ip.getIPTime() + "ms");

													} catch (NumberFormatException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number format is incorrect",
																"Number Format Error",
																JOptionPane.ERROR_MESSAGE);
													} catch (NegativeNumberException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number is negative",
																"Negative Number Error",
																JOptionPane.ERROR_MESSAGE);
													}
												}

											}
										}

									}
								});
								// remove IP listener
								removeIP.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										Dummy d = ((Set<Object>) selected).isEmpty() ? null
												: (Dummy) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one

										for (int i = currentPattern.getiPList().lastKey(); i >= 1; i--) {
											if (currentPattern.getInitiationPoint(i) != null) {
												ip = currentPattern.getInitiationPoint(i);
												for (Object o : ((Set<Object>) selected)) {
													if (o instanceof Dummy && (!(o instanceof Hole))) {
														if (ip.getIPDummy().getHoleID() == ((Dummy) o).getHoleID()
																&& ip.getIPDummy() != null) {
															currentPattern.removeIP(ip);
															updateCanvas();
															statusBarLabel.setText("Dummy #" + ((Dummy) o).getHoleID()
																	+ "'s IP is removed");
														}
													} else if (o instanceof Hole) {
														if (ip.getIPDummy().getHoleID() == ((Hole) o).getHoleID()) {
															currentPattern.removeIP(ip);
															updateCanvas();
															statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
																	+ "'s IP is removed");
														}
													}
												}
											}
										}
									}
								});
								// remove Detonator listener
								removeDet.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										Hole h = ((Set<Object>) selected).isEmpty() ? null
												: (Hole) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one

										for (int i = currentPattern.getDetonatorList().lastKey(); i >= 1; i--) {
											if (currentPattern.getDetonator(i) != null) {
												det = currentPattern.getDetonator(i);
												for (Object o : ((Set<Object>) selected)) {
													if (o instanceof Hole) {
														if (det.getInHole().getHoleID() == ((Hole) o).getHoleID()) {
															currentPattern.removeDetonator(det);
															updateCanvas();
															statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
																	+ "'s detonator is removed");
														}
													}
												}
											}
										}
									}
								});
								insertDet.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										try {

											for (Object o : ((Set<Object>) selected)) {
												if (getDetonatorTimeButton1().isSelected()) {
													if (o instanceof Hole) {
														currentPattern.addDetonator(
																new Detonator(
																		currentPattern.getDetonatorList().size() + 1,
																		Integer
																				.parseInt(getDetonatorTimeTextField2()
																						.getText()),
																		currentPattern.getHole(((Hole) o).getHoleID()),
																		((Hole) o).getHoleLength()
																				- ((Hole) o).getSubdrill(),
																		dColor1),
																true);
														//
														updateCanvas();
														statusBarLabel.setText("Hole #" + ((Hole) o).getHoleID()
																+ " now has a detonator");

													}
												}

											}

										}

										catch (NegativeNumberException e1) {
											System.out.println(
													"makeIP Actionlistener -NegativeNumberException - getToolSelectionButton()");
											e1.printStackTrace();
										} catch (ZeroArgumentException e1) {
											System.out.println(
													"makeIP Actionlistener -ZeroArgumentException - getToolSelectionButton()");
											e1.printStackTrace();
										}
									}
								});
								detTime.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										Hole h = ((Set<Object>) selected).isEmpty() ? null
												: (Hole) ((Set<Object>) selected).iterator().next(); // gets
																										// first
																										// element
																										// if
																										// there
																										// is
																										// one

										for (int i = currentPattern.getDetonatorList().lastKey(); i >= 1; i--) {
											if (currentPattern.getDetonator(i) != null) {
												if (currentPattern.getDetonator(i).getInHole().equals(h)
														&& h instanceof Hole) {
													String newValue = Integer
															.toString(currentPattern.getDetonator(i).getDelay());

													try {
														newValue = JOptionPane.showInputDialog("Input new det.setDelay",
																Integer.toString(
																		currentPattern.getDetonator(i).getDelay()));
														det = currentPattern.getDetonator(i);
														if (newValue != null || newValue != "") {
															det.setDelay(Integer.parseInt(newValue));
														}

														statusBarLabel.setText("Hole #" + h.getHoleID()
																+ " Detonator delay changed to " + det.getDelay()
																+ "ms");

													} catch (NumberFormatException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number format is incorrect",
																"Number Format Error",
																JOptionPane.ERROR_MESSAGE);
													} catch (NegativeNumberException e1) {
														JOptionPane.showMessageDialog(null,
																"Invalid Input - number is negative",
																"Negative Number Error",
																JOptionPane.ERROR_MESSAGE);
													}
												}
											}
										}

									}
								});
								// -----------------------------------------------------------------------------------------
								// delete listener second listener in the JMenus
								// -----------------------------------------------------------------------------------------
								del.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										for (Object o : ((Set<Object>) selected)) {

											if (o instanceof Dummy && (!(o instanceof Hole))) {
												currentPattern.removeDummyOrHole(
														currentPattern.getDummy(((Dummy) o).getHoleID()));
												updateCanvas();
												getTreeModelPattern();
												getTreeModelSurface();
											} else if (o instanceof Hole) {
												currentPattern.removeDummyOrHole(
														currentPattern.getHole(((Hole) o).getHoleID()));
												updateCanvas();
												getTreeModelPattern();
												getTreeModelSurface();
											} else if (o instanceof SurfaceConnector) {
												currentPattern.removeSC(currentPattern
														.getSurfaceConnector(((SurfaceConnector) o).getSurfaceID()));
												updateCanvas();
												getTreeModelSurface();
											}
											selected = new Object();
										}
										if (world.getPatternList().isEmpty() || world.getAllObjectsInWorld() == null) {
											getSaveButton().setEnabled(false);
											getSaveMenuItem().setEnabled(false);
											getTreeModelPattern();
											getTreeModelSurface();
										}
									}
								});
								// select All listener
								selectAllInWorld.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										selected = world.getAllObjectsInWorld();
										updateCanvas();

									}
								});
								// select All listener
								selectAll.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										selected = currentPattern.getAllObjectsInPattern();
										updateCanvas();

									}
								});
								// selectHoles All listener
								selectHoles.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										selected = currentPattern.getAllHolesInPattern();

										updateCanvas();

									}
								});
								// selectDummys All listener
								selectDummys.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllDummysInPattern();
										updateCanvas();

									}
								});
								// selectTies All listener
								selectTies.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllSurfaceConnectors();
										updateCanvas();

									}
								});
								// selectDets All listener
								selectDets.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllDetonators();
										updateCanvas();

									}
								});
								// selectIPs All listener
								selectIPs.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										selected = currentPattern.getAllInitiationPoints();
										updateCanvas();

									}
								});
								// rotated by
								rotate.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										// Create a set to store onlyholes
										Set<Hole> selectedHoles = new HashSet<Hole>();
										// Check the "selected" objects for
										// holes and put in the "selectedHoles"
										// set
										for (Object o : ((Set<Object>) selected)) {
											// check for holes
											if (o instanceof Hole) {
												selectedHoles.add((Hole) o);
											}
										}
										selected = selectedHoles;
										// Get the average angle to of the
										// selection to display in the dialog
										// box
										double sumAng = 0;
										for (Object o : ((Set<Object>) selected)) {
											// Sum the Angles of all the holes
											sumAng = sumAng + ((Hole) o).getBearing();

										}
										// Average the summed angles
										double avAng = sumAng / ((Set<Object>) selected).size();
										// Prompt user for Input
										String newBearing = JOptionPane
												.showInputDialog("Input new Bearing (North = 0\u00B0)", avAng);

										// For each hole in the selection
										for (Object o : ((Set<Object>) selected)) {
											// if each hole is a hole
											if (o instanceof Hole) {

												try { // set the holes angle to
														// a new angle
													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (newBearing != null || newBearing != "") {
														((Hole) o).setBearing(Double.parseDouble(newBearing));
													}

													statusBarLabel.setText("Hole bearings changed from " + avAng
															+ "\u00B0 to " + newBearing + "\u00B0");

												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												}
											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();

										}

										updateCanvas();

									}

								});
								// move to
								move.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										for (Object o : ((Set<Object>) selected)) {
											if (o instanceof Hole && o != null) {

												Coordinate newCoordinate = new Coordinate(
														currentPattern.getHole(((Hole) o).getHoleID()).getY(),
														currentPattern.getHole(
																((Hole) o).getHoleID()).getX(),
														currentPattern.getHole(((Hole) o).getHoleID()).getZ());

												try {

													newCoordinate = DialogXYZValues.showBox(canvasPanel, newCoordinate);

													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (newCoordinate != null) {
														((Hole) o).setX((newCoordinate.getX()));
														((Hole) o).setY((newCoordinate.getY()));
														((Hole) o).setZ((newCoordinate.getZ()));
													} else

														updateCanvas();
													statusBarLabel.setText(
															"Hole #" + ((Hole) o).getHoleID() + " has been moved");
												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												}

											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();

										}

										updateCanvas();

									}
								});
								/*
								 * Dip function in the Below code works on all
								 * multiples of the selection. Copy this to all
								 * other multiple functions.
								 */
								dip.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										// Create a set to store onlyholes
										Set<Hole> selectedHoles = new HashSet<Hole>();
										// Check the "selected" objects for
										// holes and put in the "selectedHoles"
										// set
										for (Object o : ((Set<Object>) selected)) {
											// check for holes
											if (o instanceof Hole) {
												selectedHoles.add((Hole) o);
											}
										}
										selected = selectedHoles;
										// Get the average angle to of the
										// selection to display in the dialog
										// box
										double sumAng = 0;
										for (Object o : ((Set<Object>) selected)) {
											// Sum the Angles of all the holes
											sumAng = sumAng + ((Hole) o).getAngle();

										}
										// Average the summed angles
										double avAng = sumAng / ((Set<Object>) selected).size();
										// Prompt user for Input
										String newDip = JOptionPane
												.showInputDialog("Input new Dip (Vertical = 90\u00B0)", avAng);

										// For each hole in the selection
										for (Object o : ((Set<Object>) selected)) {
											// if each hole is a hole
											if (o instanceof Hole) {

												try { // set the holes angle to
														// a new angle
													o = (currentPattern.getHole(((Hole) o).getHoleID()));
													if (newDip != null || newDip != "") {
														((Hole) o).setAngle(Double.parseDouble(newDip));
													}

													statusBarLabel.setText("Hole angles changed from " + avAng
															+ "\u00B0 to " + newDip + "\u00B0");

												} catch (NumberFormatException e1) {
													JOptionPane.showMessageDialog(null,
															"Invalid Input - number format is incorrect",
															"Number Format Error",
															JOptionPane.ERROR_MESSAGE);
												}
											}
											updateCanvas();
											getTreeModelPattern();
											getTreeModelSurface();

										}

										updateCanvas();

									}
								});
								// Adding a new pattern through the
								// contextual menus
								subPat.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										currentMouseMover = null;

										setStatusBarLabel("Click in Screen");// fix
																				// this
																				// later
																				// to
																				// something
																				// for
																				// the
																				// layman.
										currentMouseClicker = new MouseAdapter() {
											public void mouseClicked(MouseEvent e) {

												double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
														- UnitConvert.pixelsToMeters(e.getY())
																/ Zoom.getScalingFactor();
												System.out.println(northing + "= northing");
												double easting = getCanvasSize().getX()
														+ UnitConvert.pixelsToMeters(e.getX())
																/ Zoom.getScalingFactor();
												System.out.println(easting + "= easting");
												try {
													pattern = DialogCreatePattern.showBox(null,
															Double.parseDouble(decimalFormat4.format(northing)),
															Double.parseDouble(decimalFormat4.format(easting)),
															averageCurrentPatternCRL());
												} catch (NumberFormatException e1) {
													System.out.println(
															"getToolPatternButton() method - NumberFormatException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - NumberFormatException");
													e1.printStackTrace();
												} catch (IllegalArgumentException e1) {
													System.out.println(
															"getToolPatternButton() method - IllegalArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - IllegalArgumentException");
													e1.printStackTrace();
												} catch (NegativeNumberException e1) {
													System.out.println(
															"getToolPatternButton() method - NegativeNumberException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - NegativeNumberException");
													e1.printStackTrace();
												} catch (ZeroArgumentException e1) {
													System.out.println(
															"getToolPatternButton() method - ZeroArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolPatternButton() method - ZeroArgumentException");
													e1.printStackTrace();
												}
												if (!(pattern == null)) {
													currentPattern = pattern;
													try {
														world.addPattern(pattern, false);
													} catch (ZeroArgumentException e1) {
														e1.printStackTrace();
													} catch (NegativeNumberException e1) {
														e1.printStackTrace();
													}
													saveAsMenuItem.setEnabled(true);
													saveAsButton.setEnabled(true);
													updateCanvas();
													statusBarLabel.setText("Pattern Created: You have "
															+ world.getPatternList().size()
															+ " pattern(s). Current Pattern has "
															+ currentPattern.getHoleList().size() + " hole(s).");
													currentMouseClicker = defaultMouseClicker;
													currentMouseMover = defaultMouseMover;
												} else if (pattern == null) {
													statusBarLabel.setText("Pattern Not Created");
												}
												getTreeModelPattern();
												getTreeModelSurface();

												System.out.println("World node count =" + getTreeModelPattern()
														.getModel().getChildCount(patternTreeModel.getRoot()));
											}
										};

										updateCanvas();

									}
								});
								subHole.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {

										currentMouseMover = null;

										setStatusBarLabel("Click in Screen");// fix
																				// this
																				// later
																				// to
																				// something
																				// for
																				// the
																				// layman.
										currentMouseClicker = new MouseAdapter() {
											public void mouseClicked(MouseEvent e) {
												double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
														- UnitConvert.pixelsToMeters(e.getY())
																/ Zoom.getScalingFactor();
												double easting = getCanvasSize().getX()
														+ UnitConvert.pixelsToMeters(e.getX())
																/ Zoom.getScalingFactor();
												System.out.println("*     " + Double.toString(northing) + " & "
														+ Double.toString(easting));// FOR
																					// DEBUGGING
												try {
													dummy = DialogCreateHole.showBox(null,
															Double.parseDouble(decimalFormat4.format(northing)),
															Double.parseDouble(decimalFormat4.format(easting)),
															averageCRL, true);
													System.out.println("**    " + Double.toString(northing) + " & "
															+ Double.toString(easting));// FOR
																						// DEBUGGING
												} catch (NumberFormatException e2) {
													System.out.println(
															"getToolHoleButton() method - NumberFormatException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolHoleButton() method - NumberFormatException");
													e2.printStackTrace();
												} catch (ZeroArgumentException e2) {
													System.out.println(
															"getToolHoleButton() method - ZeroArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolHoleButton() method - ZeroArgumentException");
													e2.printStackTrace();
												} catch (NegativeNumberException e2) {
													System.out.println(
															"getToolHoleButton() method - NegativeNumberException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolHoleButton() method - NegativeNumberException");
													e2.printStackTrace();
												}
												if (!(dummy == null)) {
													if (currentPattern == null) {
														Pattern firstPattern = new Pattern(0, 0);
														try {
															world.addPattern(firstPattern, false);
														} catch (ZeroArgumentException e1) {
															// TODO
															// Auto-generated
															// catch
															// block
															e1.printStackTrace();
														} catch (NegativeNumberException e1) {
															// TODO
															// Auto-generated
															// catch
															// block
															e1.printStackTrace();
														}
														currentPattern = firstPattern;
													}
													try {
														currentPattern.addDummy(dummy, false);
													} catch (NegativeNumberException e1) {
														System.out.println(
																"getToolHoleButton() method - NegativeNumberException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolHoleButton() method - NegativeNumberException");
														e1.printStackTrace();
													} catch (ZeroArgumentException e1) {
														System.out.println(
																"getToolHoleButton() method - ZeroArgumentException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolHoleButton() method - ZeroArgumentException");
														e1.printStackTrace();
													}
													saveAsMenuItem.setEnabled(true);
													updateCanvas();
												}
												getTreeModelSurface();
												getTreeModelPattern();
											}
										};

										updateCanvas();
									}
								});
								// Adding a dummy through the contextual
								// menus
								subDum.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(java.awt.event.ActionEvent e) {
										currentMouseMover = null;
										setStatusBarLabel("Click in Screen");// fix
																				// this
																				// later
																				// to
																				// something
																				// for
																				// the
																				// layman.
										currentMouseClicker = new MouseAdapter() {
											public void mouseClicked(MouseEvent e) {
												double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
														- UnitConvert.pixelsToMeters(e.getY())
																/ Zoom.getScalingFactor();
												double easting = getCanvasSize().getX()
														+ UnitConvert.pixelsToMeters(e.getX())
																/ Zoom.getScalingFactor();
												try {
													System.out
															.println(
																	"Entered Try to dummy = NewHoleDialogue.showBox(null, Double.parseDouble(dec4p.format(northing)), Double.parseDouble(dec4p.format(easting)), averageCRL, false);");
													dummy = DialogCreateHole.showBox(null,
															Double.parseDouble(decimalFormat4.format(northing)),
															Double.parseDouble(decimalFormat4.format(easting)),
															averageCRL, false);
												} catch (NumberFormatException e2) {
													System.out.println(
															"getToolDummyButton() method - NumberFormatException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolDummyButton() method - NumberFormatException");
													e2.printStackTrace();
												} catch (ZeroArgumentException e2) {
													System.out.println(
															"getToolDummyButton() method - ZeroArgumentException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolDummyButton() method - ZeroArgumentException");
													e2.printStackTrace();
												} catch (NegativeNumberException e2) {
													System.out.println(
															"getToolDummyButton() method - NegativeNumberException");
													JOptionPane.showMessageDialog(LiteTieTRIAL.this,
															"getToolDummyButton() method - NegativeNumberException");
													e2.printStackTrace();
												}
												if (!(dummy == null)) {
													if (currentPattern == null) {
														Pattern firstPattern = new Pattern(0, 0);
														try {
															world.addPattern(firstPattern, false);
														} catch (ZeroArgumentException e1) {
															e1.printStackTrace();
														} catch (NegativeNumberException e1) {
															e1.printStackTrace();
														}
														currentPattern = firstPattern;
													}
													try {
														currentPattern.addDummy(dummy, false);
													} catch (NegativeNumberException e1) {
														System.out.println(
																"getToolDummyButton() method - NegativeNumberException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolDummyButton() method - NegativeNumberException");
														e1.printStackTrace();
													} catch (ZeroArgumentException e1) {
														System.out.println(
																"getToolDummyButton() method - ZeroArgumentException");
														JOptionPane.showMessageDialog(LiteTieTRIAL.this,
																"getToolDummyButton() method - ZeroArgumentException");
														e1.printStackTrace();
													}
													saveAsMenuItem.setEnabled(true);
													updateCanvas();
												}
												getTreeModelSurface();
												getTreeModelPattern();
											}
										};

									}
								});
								Object o;
								if (!(world.getPatternList().isEmpty())) {
									o = ((Set<Object>) selected).isEmpty() ? null
											: ((Set<Object>) selected).iterator().next();
									// start Dummy type check and
									// contextual menu creation
									if (o instanceof Dummy) {
										popUpMenu.add(makeIP);
										popUpMenu.add(timeIP);
										popUpMenu.add(removeIP);
										popUpMenu.addSeparator();
										popUpMenu.add(insertDet);
										popUpMenu.add(detTime);
										popUpMenu.add(removeDet);
										popUpMenu.addSeparator();

										popUpMenu.add(del);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectDets);
										sel.add(selectTies);
										sel.add(selectIPs);
										popUpMenu.addSeparator();
										popUpMenu.add(cut);
										popUpMenu.add(copy);
										popUpMenu.add(paste);
										popUpMenu.addSeparator();
										popUpMenu.add(move);
										popUpMenu.add(dip);
										popUpMenu.add(rotate);
										popUpMenu.addSeparator();
										popUpMenu.add(changeSymbol);
										popUpMenu.add(properties);
									} // end Dummy type check
										// start SurfaceConnector type
										// check and contextual menu on
										// surface connectors creation
									else if (o instanceof SurfaceConnector) {
										// popUpMenu.add(changeSC);
										// popUpMenu.addSeparator();
										popUpMenu.add(del);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectTies);
										sel.add(selectIPs);
										popUpMenu.addSeparator();
										popUpMenu.add(cut);
										popUpMenu.add(copy);
										popUpMenu.add(paste);
										popUpMenu.addSeparator();
										popUpMenu.add(properties);
									} // end Surface Connector type check
										// start NULL check if nothing
										// is selected then this
										// contextual menu will occur
									else if (o == null) {
										popUpMenu.add(subAdd);
										subAdd.add(subPat);
										subAdd.add(subHole);
										subAdd.add(subDum);
										subAdd.addSeparator();
										subAdd.add(subBound);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectDets);
										sel.add(selectTies);
										sel.add(selectIPs);
									} // end NULL check
									else {
										popUpMenu.add(subAdd);
										subAdd.add(subPat);
										subAdd.add(subHole);
										subAdd.add(subDum);
										subAdd.addSeparator();
										subAdd.add(subBound);
										popUpMenu.add(sel);
										sel.add(selectAllInWorld);
										sel.add(selectAll);
										sel.add(selectHoles);
										sel.add(selectDummys);
										sel.add(selectDets);
										sel.add(selectTies);
										sel.add(selectIPs);
									}
								} else {
									popUpMenu.add(subAdd);
									subAdd.add(subPat);
									subAdd.add(subHole);
									subAdd.add(subDum);
									subAdd.addSeparator();
									subAdd.add(subBound);
								}
								popUpMenu.show(canvasScrollPane, e.getX(), e.getY());
							}

							// currentMouseMover = defaultMouseMover;

						}

					};

				}
			});

		}
		return toolSelectionMarqueeButton;
	}

	/**
	 * @Description Used to construct a Basic Stroke from the GUI
	 * @return currentStroke
	 */
	private BasicStroke getCurrentStroke() {
		if (Integer.parseInt(getSpinnerStrokeDash().getValue().toString()) < 1) {
			currentStroke = new BasicStroke(Integer.parseInt(getSpinnerStrokeWidth().getValue().toString()));
		} else if (Integer.parseInt(getSpinnerStrokeDash().getValue().toString()) >= 1
				&& Integer.parseInt(getSpinnerSpaceDash().getValue().toString()) >= 1) {

			dashLength = Integer.parseInt(getSpinnerStrokeDash().getValue().toString());
			spaceLength = Integer.parseInt(getSpinnerSpaceDash().getValue().toString());
			dashSpacing[0] = dashLength;
			dashSpacing[1] = spaceLength;
			currentStroke = new BasicStroke(Integer.parseInt(getSpinnerStrokeWidth().getValue().toString()),
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, dashSpacing, 1);
		}
		return currentStroke;

	}

	/**
	 * This method initializes tieButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getTieButton1() {
		if (tieButton1 == null) {
			tieButton1 = new JToggleButton();
			tieButton1.setText("Tie 1");

			// tieButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			tieButton1.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/tiesoffon.png"))));
			tieButton1.setPreferredSize(new Dimension(85, 24));

			sDelay = Integer.parseInt(getTieTextField1().getText());
			surfaceTieColor = sColor1 = getTieTextField1().getBackground();

			tieButton1.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {

					if (tieButton1.isSelected()) {

						// tieButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

						if (getToolTieButton().isSelected() || getToolMultiTieInLineButton().isSelected()
								|| getToolMultiTieButton().isSelected()) {

							getColorWellStroke().setBackground(sColor1);
						}
						sDelay = Integer.parseInt(getTieTextField1().getText());
						surfaceTieColor = sColor1 = getTieTextField1().getBackground();

						tieButton2.setSelected(false);
						// tieButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton3.setSelected(false);
						// tieButton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton4.setSelected(false);
						// tieButton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton5.setSelected(false);
						// tieButton5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton6.setSelected(false);
						// tieButton6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					}
				}
			});
			tieButton1.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});

		}
		return tieButton1;
	}

	/**
	 * This method initializes tieButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getTieButton2() {
		if (tieButton2 == null) {
			tieButton2 = new JToggleButton();
			tieButton2.setText("Tie 2");
			// tieButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			tieButton2.setPreferredSize(new Dimension(85, 24));
			tieButton2.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/tiesoffon.png"))));
			tieButton2.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tieButton2.isSelected()) {
						// tieButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						surfaceTieColor = sColor2 = getTieTextField2().getBackground();

						if (getToolTieButton().isSelected() || getToolMultiTieInLineButton().isSelected()
								|| getToolMultiTieButton().isSelected()) {
							getColorWellStroke().setBackground(sColor2);
						}
						surfaceTieColor = sColor2 = getTieTextField2().getBackground();
						sDelay = Integer.parseInt(getTieTextField2().getText());
						tieButton1.setSelected(false);
						// tieButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton3.setSelected(false);
						// tieButton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton4.setSelected(false);
						// tieButton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton5.setSelected(false);
						// tieButton5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton6.setSelected(false);
						// tieButton6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

					}
				}
			});
			tieButton2.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});
		}
		return tieButton2;
	}

	/**
	 * This method initializes tieButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getTieButton3() {
		if (tieButton3 == null) {
			tieButton3 = new JToggleButton();
			tieButton3.setText("Tie 3");
			// tieButton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			tieButton3.setPreferredSize(new Dimension(85, 24));
			tieButton3.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/tiesoffon.png"))));
			tieButton3.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tieButton3.isSelected()) {
						// tieButton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						surfaceTieColor = sColor3 = getTieTextField3().getBackground();

						if (getToolTieButton().isSelected() || getToolMultiTieInLineButton().isSelected()
								|| getToolMultiTieButton().isSelected()) {
							getColorWellStroke().setBackground(sColor3);
						}
						surfaceTieColor = sColor3 = getTieTextField3().getBackground();
						sDelay = Integer.parseInt(getTieTextField3().getText());
						tieButton2.setSelected(false);
						// tieButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton1.setSelected(false);
						// tieButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton4.setSelected(false);
						// tieButton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton5.setSelected(false);
						// tieButton5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton6.setSelected(false);
						// tieButton6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					}
				}
			});
			tieButton3.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});
		}
		return tieButton3;
	}

	/**
	 * This method initializes tieButton4
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getTieButton4() {
		if (tieButton4 == null) {
			tieButton4 = new JToggleButton();
			tieButton4.setText("Tie 4");
			// tieButton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			tieButton4.setPreferredSize(new Dimension(85, 24));
			tieButton4.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/tiesoffon.png"))));
			tieButton4.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tieButton4.isSelected()) {
						// tieButton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						surfaceTieColor = sColor4 = getTieTextField4().getBackground();

						if (getToolTieButton().isSelected() || getToolMultiTieInLineButton().isSelected()
								|| getToolMultiTieButton().isSelected()) {
							getColorWellStroke().setBackground(sColor4);
						}

						surfaceTieColor = sColor4 = getTieTextField4().getBackground();
						sDelay = Integer.parseInt(getTieTextField4().getText());
						tieButton2.setSelected(false);
						// tieButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton3.setSelected(false);
						// tieButton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton1.setSelected(false);
						// tieButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton5.setSelected(false);
						// tieButton5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton6.setSelected(false);
						// tieButton6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					}
				}
			});
			tieButton4.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});
		}
		return tieButton4;
	}

	/**
	 * This method initializes tieButton5
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getTieButton5() {
		if (tieButton5 == null) {
			tieButton5 = new JToggleButton();
			tieButton5.setText("Tie 5");
			// tieButton5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			tieButton5.setPreferredSize(new Dimension(85, 24));
			tieButton5.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/tiesoffon.png"))));
			tieButton5.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tieButton5.isSelected()) {
						// tieButton5.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						surfaceTieColor = sColor5 = getTieTextField5().getBackground();

						if (getToolTieButton().isSelected() || getToolMultiTieInLineButton().isSelected()
								|| getToolMultiTieButton().isSelected()) {
							getColorWellStroke().setBackground(sColor5);
						}
						surfaceTieColor = sColor5 = getTieTextField5().getBackground();
						sDelay = Integer.parseInt(getTieTextField5().getText());

						tieButton2.setSelected(false);
						// tieButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton3.setSelected(false);
						// tieButton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton4.setSelected(false);
						// tieButton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton1.setSelected(false);
						// tieButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton6.setSelected(false);
						// tieButton6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					}
				}
			});
			tieButton5.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});
		}
		return tieButton5;
	}

	/**
	 * This method initializes tieButton6
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getTieButton6() {
		if (tieButton6 == null) {
			tieButton6 = new JToggleButton();
			tieButton6.setText("Tie 6");
			// tieButton6.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			tieButton6.setPreferredSize(new Dimension(85, 24));
			tieButton6.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/tiesoffon.png"))));
			tieButton6.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tieButton6.isSelected()) {
						// tieButton6.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						surfaceTieColor = sColor6 = getTieTextField6().getBackground();

						if (getToolTieButton().isSelected() || getToolMultiTieInLineButton().isSelected()
								|| getToolMultiTieButton().isSelected()) {
							getColorWellStroke().setBackground(sColor6);
						}
						sDelay = Integer.parseInt(getTieTextField6().getText());
						surfaceTieColor = sColor6 = getTieTextField6().getBackground();

						tieButton2.setSelected(false);
						// tieButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton3.setSelected(false);
						// tieButton3.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton4.setSelected(false);
						// tieButton4.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton5.setSelected(false);
						// tieButton5.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						tieButton1.setSelected(false);
						// tieButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
					}
				}
			});
			tieButton6.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});
		}
		return tieButton6;
	}

	/**
	 * This method initializes tieColourButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JPanel getColorWellStroke() {
		if (colorWellStroke == null) {
			colorWellStroke = new JPanel() {
				public void paintComponent(Graphics g) {
					if (colorWellStroke.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);
						g.fillRect(14, 2, 6, 6);
						g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);
						g.fillRect(14, 14, 6, 6);
						g.setColor(colorWellStroke.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 21, 21);
					} else if (colorWellStroke.getBackground().getAlpha() == 255) {
						g.setColor(colorWellStroke.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 21, 21);
					}

				}
			};
			colorWellStroke.setPreferredSize(new Dimension(22, 22));
			// colorWellStroke.setBackground(i_backgroundCanvasColour);
			colorWellStroke.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(MouseEvent e) {

					if (currentColor != null) {
						getColorWellStroke().setBackground(currentColor);
					}
					if (getToolAddDetonatorButton().isSelected() || getToolAddMultiDetonatorButton().isSelected()) {
						if (detonatorTimeButton1.isSelected()) {
							newColor = JColorChooser.showDialog(canvasPanel, "Change Detonator Colour",
									getDetonatorTimeTextField1().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getDetonatorTimeTextField1().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();
						}
						if (detonatorTimeButton2.isSelected()) {

							newColor = JColorChooser.showDialog(canvasPanel, "Change Detonator Colour",
									getDetonatorTimeTextField2().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getDetonatorTimeTextField2().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();

						}
						if (detonatorTimeButton3.isSelected()) {
							newColor = JColorChooser.showDialog(canvasPanel, "Change Detonator Colour",
									getDetonatorTimeTextField3().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getDetonatorTimeTextField3().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();

						}
					} else if (getToolTieButton().isSelected() || getToolMultiTieButton().isSelected()
							|| getToolMultiTieInLineButton().isSelected()) {
						if (tieButton1.isSelected()) {

							newColor = JColorChooser.showDialog(canvasPanel, "Change Surface Tie Colour",
									getTieTextField1().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getTieTextField1().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();

						}
						if (tieButton2.isSelected()) {

							newColor = JColorChooser.showDialog(canvasPanel, "Change Surface Tie Colour",
									getTieTextField2().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getTieTextField2().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();
						}
						if (tieButton3.isSelected()) {

							newColor = JColorChooser.showDialog(canvasPanel, "Change Surface Tie Colour",
									getTieTextField3().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getTieTextField3().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();
						}
						if (tieButton4.isSelected()) {

							newColor = JColorChooser.showDialog(canvasPanel, "Change Surface Tie Colour",
									getTieTextField4().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getTieTextField4().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();
						}
						if (tieButton5.isSelected()) {

							newColor = JColorChooser.showDialog(canvasPanel, "Change Surface Tie Colour",
									getTieTextField5().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getTieTextField5().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();
						}
						if (tieButton6.isSelected()) {

							newColor = JColorChooser.showDialog(canvasPanel, "Change Surface Tie Colour",
									getTieTextField6().getBackground());
							if (newColor != null) {
								colorWellStroke.setBackground(newColor);
								getTieTextField6().setBackground(newColor);
							} else
								newColor = colorWellStroke.getBackground();
						}
					}

					else {

						currentColor = JColorChooser.showDialog(canvasPanel, "Change Current Colour",
								getColorWellStroke().getBackground());
						if (currentColor != null) {
							getColorWellStroke().setBackground(currentColor);
						}
						for (Pattern tempPat : world.getPatternList().values()) {
							for (Hole temp : tempPat.getHoleList().values()) {
								boolean isSelected = temp == selected || (selected instanceof Collection<?>
										&& ((Collection<?>) selected).contains(temp));
								if (isSelected) {
									temp.setColor(currentColor);
								}
								updateCanvas();
							}
							getTreeModelPattern();
							updateCanvas();
						}

						for (Boundary temp : world.getBoundaryList().values()) {
							boolean isSelected = temp == selected
									|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(temp));
							if (isSelected) {
								temp.setColor(currentColor);
							}
							updateCanvas();
						}
						getTreeModelBoundary();
						updateCanvas();
					}

				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}
			});
			colorWellStroke.setBackground(currentColor);

			colorWellStroke.setToolTipText("Change a Colour");
			colorWellStroke.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});

		}
		return colorWellStroke;
	}

	/**
	 * This method initializes tieTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTieTextField1() {
		if (tieTextField1 == null) {
			tieTextField1 = new JTextField();
			tieTextField1.setText("9");
			tieTextField1.setMinimumSize(new Dimension(80, 22));
			tieTextField1.setBackground(new Color(0, 153, 0));
			tieTextField1.setPreferredSize(new Dimension(50, 24));
			tieTextField1.setHorizontalAlignment(JTextField.CENTER);
			Color b = getTieTextField1().getBackground();
			Color f = getTieTextField1().getForeground();
			getTieTextField1().setForeground(f);
			getTieTextField1().setBackground(b);
			tieTextField1.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTieTextField1().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Tie #1 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			tieTextField1.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((tieTextField1.getBackground().getRed() + tieTextField1.getBackground().getGreen()
							+ tieTextField1.getBackground().getBlue()) / 3 < 130) {
						getTieTextField1().setForeground(Color.white);
					} else
						getTieTextField1().setForeground(Color.black);
				}
			});
		}
		return tieTextField1;
	}

	/**
	 * This method initializes tieTextField3
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTieTextField2() {
		if (tieTextField2 == null) {
			tieTextField2 = new JTextField();
			tieTextField2.setText("17");
			tieTextField2.setMinimumSize(new Dimension(80, 22));
			tieTextField2.setBackground(new Color(204, 204, 0));
			tieTextField2.setPreferredSize(new Dimension(50, 24));
			tieTextField2.setHorizontalAlignment(JTextField.CENTER);
			tieTextField2.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTieTextField2().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Tie #2 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			tieTextField2.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((tieTextField2.getBackground().getRed() + tieTextField2.getBackground().getGreen()
							+ tieTextField2.getBackground().getBlue()) / 3 < 130) {
						getTieTextField2().setForeground(Color.white);
					} else
						getTieTextField2().setForeground(Color.black);
				}
			});
		}
		return tieTextField2;
	}

	/**
	 * This method initializes tieTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTieTextField3() {
		if (tieTextField3 == null) {
			tieTextField3 = new JTextField();
			tieTextField3.setMinimumSize(new Dimension(80, 22));
			tieTextField3.setText("25");
			tieTextField3.setBackground(new Color(255, 0, 51));
			tieTextField3.setPreferredSize(new Dimension(50, 24));
			tieTextField3.setHorizontalAlignment(JTextField.CENTER);
			tieTextField3.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTieTextField3().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Tie #3 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			tieTextField3.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((tieTextField3.getBackground().getRed() + tieTextField3.getBackground().getGreen()
							+ tieTextField3.getBackground().getBlue()) / 3 < 130) {
						getTieTextField3().setForeground(Color.white);
					} else
						getTieTextField3().setForeground(Color.black);
				}
			});
		}
		return tieTextField3;
	}

	/**
	 * This method initializes tieTextField4
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTieTextField4() {
		if (tieTextField4 == null) {
			tieTextField4 = new JTextField();
			tieTextField4.setText("42");
			tieTextField4.setMinimumSize(new Dimension(80, 22));
			tieTextField4.setBackground(new Color(170, 170, 170));
			tieTextField4.setPreferredSize(new Dimension(50, 24));
			tieTextField4.setHorizontalAlignment(JTextField.CENTER);
			tieTextField4.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTieTextField4().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Tie #4 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			tieTextField4.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((tieTextField4.getBackground().getRed() + tieTextField4.getBackground().getGreen()
							+ tieTextField4.getBackground().getBlue()) / 3 < 130) {
						getTieTextField4().setForeground(Color.white);
					} else
						getTieTextField4().setForeground(Color.black);
				}
			});
		}
		return tieTextField4;
	}

	/**
	 * This method initializes tieTextField5
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTieTextField5() {
		if (tieTextField5 == null) {
			tieTextField5 = new JTextField();
			tieTextField5.setText("67");
			tieTextField5.setMinimumSize(new Dimension(80, 22));
			tieTextField5.setBackground(new Color(0, 153, 255));
			tieTextField5.setPreferredSize(new Dimension(50, 24));
			tieTextField5.setHorizontalAlignment(JTextField.CENTER);
			tieTextField5.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTieTextField5().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Tie #5 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			tieTextField5.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((tieTextField5.getBackground().getRed() + tieTextField5.getBackground().getGreen()
							+ tieTextField5.getBackground().getBlue()) / 3 < 130) {
						getTieTextField5().setForeground(Color.white);
					} else
						getTieTextField5().setForeground(Color.black);
				}
			});
		}
		return tieTextField5;
	}

	/**
	 * This method initializes tieTextField6
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTieTextField6() {
		if (tieTextField6 == null) {
			tieTextField6 = new JTextField();
			tieTextField6.setText("109");
			tieTextField6.setMinimumSize(new Dimension(80, 22));
			tieTextField6.setBackground(new Color(153, 0, 204));
			tieTextField6.setPreferredSize(new Dimension(50, 24));
			tieTextField6.setHorizontalAlignment(JTextField.CENTER);
			tieTextField6.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getTieTextField6().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Tie #6 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			tieTextField6.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((tieTextField6.getBackground().getRed() + tieTextField6.getBackground().getGreen()
							+ tieTextField6.getBackground().getBlue()) / 3 < 130) {
						getTieTextField6().setForeground(Color.white);
					} else
						getTieTextField6().setForeground(Color.black);
				}
			});
		}
		return tieTextField6;
	}

	/**
	 * This method initializes toolBarSideHolderPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getToolBarSideHolderPanel() {
		if (toolBarSideHolderPanel == null) {

			toolBarSideHolderPanel = new JPanel();
			toolBarSideHolderPanel.setForeground(Color.WHITE);
			GridBagLayout gbl_toolBarSideHolderPanel = new GridBagLayout();
			gbl_toolBarSideHolderPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0 };
			gbl_toolBarSideHolderPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
			toolBarSideHolderPanel.setLayout(gbl_toolBarSideHolderPanel);
			toolBarSideHolderPanel.setPreferredSize(new Dimension(110, 2147483647));
			toolBarSideHolderPanel.setToolTipText("Side tools");

			// Boundary Tool
			GridBagConstraints gbc_TOOLBoundary = new GridBagConstraints();
			gbc_TOOLBoundary.insets = inset0;
			gbc_TOOLBoundary.gridx = 0;
			gbc_TOOLBoundary.weightx = 1;
			gbc_TOOLBoundary.fill = 1;
			gbc_TOOLBoundary.gridwidth = 1;
			gbc_TOOLBoundary.gridy = 10;

			// GridBagConstraints gbc_spinnerEnhanceAmount = new
			// GridBagConstraints();
			// gbc_spinnerEnhanceAmount.fill = GridBagConstraints.BOTH;
			// gbc_spinnerEnhanceAmount.gridwidth = 3;
			// gbc_spinnerEnhanceAmount.insets = new Insets(0, 0, 5, 0);
			// gbc_spinnerEnhanceAmount.gridx = 0;
			// gbc_spinnerEnhanceAmount.gridy = 0;

			// Selection 1 tool
			GridBagConstraints gbc_TOOLSelection = new GridBagConstraints();
			gbc_TOOLSelection.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLSelection.fill = GridBagConstraints.BOTH;
			gbc_TOOLSelection.gridx = 0;
			gbc_TOOLSelection.anchor = GridBagConstraints.CENTER;
			gbc_TOOLSelection.gridwidth = 1;
			gbc_TOOLSelection.gridy = 1;
			toolBarSideHolderPanel.add(getToolSelectionButton(), gbc_TOOLSelection);

			// Marquee Tool
			GridBagConstraints gbc_TOOLMarquee = new GridBagConstraints();
			gbc_TOOLMarquee.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLMarquee.fill = GridBagConstraints.BOTH;
			gbc_TOOLMarquee.gridx = 1;
			gbc_TOOLMarquee.weightx = 0.5;
			gbc_TOOLMarquee.anchor = GridBagConstraints.CENTER;
			gbc_TOOLMarquee.gridwidth = 1;
			gbc_TOOLMarquee.gridy = 1;
			toolBarSideHolderPanel.add(getToolSelectionMarqueeButton(), gbc_TOOLMarquee);

			// Polygon Tool
			GridBagConstraints gbc_TOOLPolygon = new GridBagConstraints();
			gbc_TOOLPolygon.insets = new Insets(0, 0, 5, 0);
			gbc_TOOLPolygon.fill = GridBagConstraints.BOTH;
			gbc_TOOLPolygon.gridx = 2;
			gbc_TOOLPolygon.weightx = 0.5;
			gbc_TOOLPolygon.anchor = GridBagConstraints.CENTER;
			gbc_TOOLPolygon.gridwidth = 1;
			gbc_TOOLPolygon.gridy = 1;
			toolBarSideHolderPanel.add(getToolPolygonSelectButton(), gbc_TOOLPolygon);
			GridBagConstraints gbc_toolSelectionOval = new GridBagConstraints();
			gbc_toolSelectionOval.fill = GridBagConstraints.BOTH;
			gbc_toolSelectionOval.insets = new Insets(0, 0, 5, 5);
			gbc_toolSelectionOval.gridx = 0;
			gbc_toolSelectionOval.gridy = 2;
			toolBarSideHolderPanel.add(getToolSelectionOval(), gbc_toolSelectionOval);

			GridBagConstraints gbc_TOOLRuler = new GridBagConstraints();
			gbc_TOOLRuler.fill = GridBagConstraints.BOTH;
			gbc_TOOLRuler.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLRuler.gridx = 1;
			gbc_TOOLRuler.anchor = GridBagConstraints.CENTER;
			gbc_TOOLRuler.gridwidth = 1;
			gbc_TOOLRuler.gridy = 2;
			gbc_TOOLRuler.weightx = 0.5;
			toolBarSideHolderPanel.add(getToolRulerButton(), gbc_TOOLRuler);
			GridBagConstraints gbc_ToolBearingMeasure = new GridBagConstraints();
			gbc_ToolBearingMeasure.insets = new Insets(0, 0, 5, 0);
			gbc_ToolBearingMeasure.fill = GridBagConstraints.BOTH;
			gbc_ToolBearingMeasure.gridx = 2;
			gbc_ToolBearingMeasure.gridy = 2;
			toolBarSideHolderPanel.add(getToolRulerBearing(), gbc_ToolBearingMeasure);

			// Rotate Tool
			GridBagConstraints gbc_TOOLRotate = new GridBagConstraints();
			gbc_TOOLRotate.fill = GridBagConstraints.BOTH;
			gbc_TOOLRotate.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLRotate.gridx = 0;
			gbc_TOOLRotate.weightx = 0.5;
			gbc_TOOLRotate.anchor = GridBagConstraints.CENTER;
			gbc_TOOLRotate.gridwidth = 1;
			gbc_TOOLRotate.gridy = 3;
			toolBarSideHolderPanel.add(getToolRotateButton(), gbc_TOOLRotate);
			GridBagConstraints gbc_toggleButton_4 = new GridBagConstraints();
			gbc_toggleButton_4.fill = GridBagConstraints.BOTH;
			gbc_toggleButton_4.insets = new Insets(0, 0, 5, 5);
			gbc_toggleButton_4.gridx = 1;
			gbc_toggleButton_4.gridy = 3;
			toolBarSideHolderPanel.add(getToolScaleButton(), gbc_toggleButton_4);

			// Move tool
			GridBagConstraints gbc_TOOLMove = new GridBagConstraints();
			gbc_TOOLMove.insets = new Insets(0, 0, 5, 0);
			gbc_TOOLMove.gridx = 2;
			gbc_TOOLMove.fill = GridBagConstraints.HORIZONTAL;
			gbc_TOOLMove.weightx = 0.5;
			gbc_TOOLMove.anchor = GridBagConstraints.CENTER;
			gbc_TOOLMove.fill = 1;
			gbc_TOOLMove.gridwidth = 1;
			gbc_TOOLMove.gridy = 3;

			toolBarSideHolderPanel.add(getToolMoveButton(), gbc_TOOLMove);

			GridBagConstraints gbc_TOOLNGon = new GridBagConstraints();
			gbc_TOOLNGon.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLNGon.gridx = 0;
			gbc_TOOLNGon.gridy = 4;
			gbc_TOOLNGon.fill = 1;

			// Add buttons to the gridbag layout
			toolBarSideHolderPanel.add(getToolNGonButton(), gbc_TOOLNGon);

			GridBagConstraints gbc_nGonSpinner = new GridBagConstraints();
			gbc_nGonSpinner.insets = new Insets(0, 0, 5, 0);
			gbc_nGonSpinner.gridx = 1;
			gbc_nGonSpinner.gridy = 4;
			gbc_nGonSpinner.weightx = 0.5;
			gbc_nGonSpinner.gridwidth = 2;
			gbc_nGonSpinner.fill = GridBagConstraints.BOTH;
			toolBarSideHolderPanel.add(getToolNGonSpinner(), gbc_nGonSpinner);
			GridBagConstraints gbc_ToolPolyLineButton = new GridBagConstraints();
			gbc_ToolPolyLineButton.fill = GridBagConstraints.BOTH;
			gbc_ToolPolyLineButton.insets = new Insets(0, 0, 5, 5);
			gbc_ToolPolyLineButton.gridx = 0;
			gbc_ToolPolyLineButton.gridy = 5;
			toolBarSideHolderPanel.add(getToolPolyLineButton(), gbc_ToolPolyLineButton);
			GridBagConstraints gbc_dimensionArrow = new GridBagConstraints();
			gbc_dimensionArrow.fill = GridBagConstraints.BOTH;
			gbc_dimensionArrow.insets = new Insets(0, 0, 5, 5);
			gbc_dimensionArrow.gridx = 1;
			gbc_dimensionArrow.gridy = 5;
			toolBarSideHolderPanel.add(getToolDimensionArrowButton(), gbc_dimensionArrow);

			GridBagConstraints gbc_TOOLText = new GridBagConstraints();
			gbc_TOOLText.insets = new Insets(0, 0, 5, 0);
			gbc_TOOLText.gridx = 2;
			gbc_TOOLText.anchor = GridBagConstraints.CENTER;
			gbc_TOOLText.fill = GridBagConstraints.BOTH;
			gbc_TOOLText.gridwidth = 1;
			gbc_TOOLText.gridy = 5;
			toolBarSideHolderPanel.add(getToolTextButton(), gbc_TOOLText);

			// Pattern Tool
			GridBagConstraints gbc_TOOLPattern = new GridBagConstraints();
			gbc_TOOLPattern.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLPattern.gridx = 0;
			gbc_TOOLPattern.weightx = 0.5;
			gbc_TOOLPattern.anchor = GridBagConstraints.CENTER;
			gbc_TOOLPattern.fill = 1;
			gbc_TOOLPattern.gridwidth = 1;
			gbc_TOOLPattern.gridy = 6;
			toolBarSideHolderPanel.add(getToolPatternButton(), gbc_TOOLPattern);

			// Hole Tool
			GridBagConstraints gbc_TOOLHole = new GridBagConstraints();
			gbc_TOOLHole.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLHole.gridx = 1;
			gbc_TOOLHole.anchor = GridBagConstraints.CENTER;
			gbc_TOOLHole.fill = GridBagConstraints.BOTH;
			gbc_TOOLHole.gridwidth = 1;
			gbc_TOOLHole.gridy = 6;
			toolBarSideHolderPanel.add(getToolHoleButton(), gbc_TOOLHole);

			// Dummy Tool
			GridBagConstraints gbc_TOOLDummy = new GridBagConstraints();
			gbc_TOOLDummy.insets = new Insets(0, 0, 5, 0);
			gbc_TOOLDummy.gridx = 2;
			gbc_TOOLDummy.weightx = 0.5;
			gbc_TOOLDummy.anchor = GridBagConstraints.CENTER;
			gbc_TOOLDummy.fill = GridBagConstraints.BOTH;
			gbc_TOOLDummy.gridwidth = 1;
			gbc_TOOLDummy.gridy = 6;
			toolBarSideHolderPanel.add(getToolDummyButton(), gbc_TOOLDummy);

			GridBagConstraints gbc_TOOLRamp = new GridBagConstraints();
			gbc_TOOLRamp.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLRamp.gridx = 0;
			gbc_TOOLRamp.weightx = 0.5;
			gbc_TOOLRamp.fill = GridBagConstraints.VERTICAL;
			gbc_TOOLRamp.anchor = GridBagConstraints.CENTER;
			gbc_TOOLRamp.fill = 1;
			gbc_TOOLRamp.gridwidth = 1;
			gbc_TOOLRamp.gridy = 7;
			toolBarSideHolderPanel.add(getToolZMove(), gbc_TOOLRamp);

			// Labeling Tool
			GridBagConstraints gbc_TOOLLabel = new GridBagConstraints();
			gbc_TOOLLabel.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLLabel.gridx = 1;
			gbc_TOOLLabel.weightx = 0.5;
			gbc_TOOLLabel.anchor = GridBagConstraints.CENTER;
			gbc_TOOLLabel.fill = GridBagConstraints.BOTH;
			gbc_TOOLLabel.gridwidth = 1;
			gbc_TOOLLabel.gridy = 7;
			toolBarSideHolderPanel.add(getToolLabelButton(), gbc_TOOLLabel);
			GridBagConstraints gbc_toolpolyPattern = new GridBagConstraints();
			gbc_toolpolyPattern.insets = new Insets(0, 0, 5, 0);
			gbc_toolpolyPattern.fill = GridBagConstraints.BOTH;
			gbc_toolpolyPattern.gridx = 2;
			gbc_toolpolyPattern.gridy = 7;
			toolBarSideHolderPanel.add(getToolPolyPattern(), gbc_toolpolyPattern);

			// Multi Tie Tool
			GridBagConstraints gbc_TOOLMultiTie = new GridBagConstraints();
			gbc_TOOLMultiTie.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLMultiTie.gridx = 0;
			gbc_TOOLMultiTie.weightx = 0.5;
			gbc_TOOLMultiTie.anchor = GridBagConstraints.CENTER;
			gbc_TOOLMultiTie.fill = GridBagConstraints.BOTH;
			gbc_TOOLMultiTie.gridwidth = 1;
			gbc_TOOLMultiTie.gridy = 8;
			toolBarSideHolderPanel.add(getToolMultiTieButton(), gbc_TOOLMultiTie);

			// Single Tie Tool
			GridBagConstraints gbc_TOOLSingleTie = new GridBagConstraints();
			gbc_TOOLSingleTie.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLSingleTie.gridx = 1;
			gbc_TOOLSingleTie.weightx = 0.5;
			gbc_TOOLSingleTie.anchor = GridBagConstraints.CENTER;
			gbc_TOOLSingleTie.fill = GridBagConstraints.BOTH;
			gbc_TOOLSingleTie.gridwidth = 1;
			gbc_TOOLSingleTie.gridy = 8;
			toolBarSideHolderPanel.add(getToolTieButton(), gbc_TOOLSingleTie);
			GridBagConstraints gbc_toolMultiTieInLineButton = new GridBagConstraints();
			gbc_toolMultiTieInLineButton.insets = new Insets(0, 0, 5, 0);
			gbc_toolMultiTieInLineButton.gridx = 2;
			gbc_toolMultiTieInLineButton.gridy = 8;
			toolBarSideHolderPanel.add(getToolMultiTieInLineButton(), gbc_toolMultiTieInLineButton);

			GridBagConstraints gbc_tglbtnC = new GridBagConstraints();
			gbc_tglbtnC.insets = new Insets(0, 0, 5, 5);
			gbc_tglbtnC.gridx = 0;
			gbc_tglbtnC.gridy = 9;
			toolBarSideHolderPanel.add(getToolAddChargeButton(), gbc_tglbtnC);

			GridBagConstraints gbc_TOOLDetonator = new GridBagConstraints();
			gbc_TOOLDetonator.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLDetonator.gridx = 0;
			gbc_TOOLDetonator.fill = GridBagConstraints.VERTICAL;
			gbc_TOOLDetonator.anchor = GridBagConstraints.CENTER;
			gbc_TOOLDetonator.fill = 1;
			gbc_TOOLDetonator.gridwidth = 1;
			gbc_TOOLDetonator.weightx = 0.5;
			gbc_TOOLDetonator.gridy = 10;
			toolBarSideHolderPanel.add(getToolAddDetonatorButton(), gbc_TOOLDetonator);
			GridBagConstraints gbc_TOOLMultiDetonator = new GridBagConstraints();
			gbc_TOOLMultiDetonator.insets = new Insets(0, 0, 5, 5);
			gbc_TOOLMultiDetonator.gridx = 1;
			gbc_TOOLMultiDetonator.anchor = GridBagConstraints.CENTER;
			gbc_TOOLMultiDetonator.fill = GridBagConstraints.BOTH;
			gbc_TOOLMultiDetonator.gridwidth = 1;
			gbc_TOOLMultiDetonator.weightx = 0.5;
			gbc_TOOLMultiDetonator.gridy = 10;
			toolBarSideHolderPanel.add(getToolAddMultiDetonatorButton(), gbc_TOOLMultiDetonator);
			GridBagConstraints gbc_toggleButton_1 = new GridBagConstraints();
			gbc_toggleButton_1.insets = new Insets(0, 0, 5, 0);
			gbc_toggleButton_1.fill = GridBagConstraints.BOTH;
			gbc_toggleButton_1.gridx = 2;
			gbc_toggleButton_1.gridy = 10;
			toolBarSideHolderPanel.add(getToggleButton_1(), gbc_toggleButton_1);
			GridBagConstraints gbc_lblDesign = new GridBagConstraints();
			gbc_lblDesign.insets = new Insets(0, 0, 5, 5);
			gbc_lblDesign.gridx = 0;
			gbc_lblDesign.gridy = 12;
			toolBarSideHolderPanel.add(getLblDesign(), gbc_lblDesign);
			GridBagConstraints gbc_chckbxLock = new GridBagConstraints();
			gbc_chckbxLock.gridwidth = 2;
			gbc_chckbxLock.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxLock.gridx = 1;
			gbc_chckbxLock.gridy = 12;
			toolBarSideHolderPanel.add(getCheckBoxLockSpaceAndDash(), gbc_chckbxLock);

			GridBagConstraints gbc_DesignLabel = new GridBagConstraints();
			gbc_DesignLabel.insets = new Insets(0, 0, 5, 5);
			gbc_DesignLabel.fill = 1;
			gbc_DesignLabel.gridx = 0;
			gbc_DesignLabel.gridy = 13;
			toolLabel2 = new JLabel();
			toolLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			toolLabel2.setText("Width");
			toolLabel2.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			toolBarSideHolderPanel.add(toolLabel2, gbc_DesignLabel);
			GridBagConstraints gbc_spinnerView2D3D = new GridBagConstraints();
			gbc_spinnerView2D3D.gridwidth = 2;
			gbc_spinnerView2D3D.insets = new Insets(0, 0, 5, 0);
			gbc_spinnerView2D3D.fill = GridBagConstraints.BOTH;
			gbc_spinnerView2D3D.gridx = 1;
			gbc_spinnerView2D3D.gridy = 13;
			toolBarSideHolderPanel.add(getSpinnerStrokeWidth(), gbc_spinnerView2D3D);
			GridBagConstraints gbc_lblSpace = new GridBagConstraints();
			gbc_lblSpace.insets = new Insets(0, 0, 5, 5);
			gbc_lblSpace.gridx = 0;
			gbc_lblSpace.gridy = 14;
			toolBarSideHolderPanel.add(getLblSpace(), gbc_lblSpace);
			GridBagConstraints gbc_SpinnerSpaceDash = new GridBagConstraints();
			gbc_SpinnerSpaceDash.gridwidth = 2;
			gbc_SpinnerSpaceDash.insets = new Insets(0, 0, 5, 0);
			gbc_SpinnerSpaceDash.fill = GridBagConstraints.HORIZONTAL;
			gbc_SpinnerSpaceDash.gridx = 1;
			gbc_SpinnerSpaceDash.gridy = 14;
			toolBarSideHolderPanel.add(getSpinnerSpaceDash(), gbc_SpinnerSpaceDash);
			GridBagConstraints gbc_lblType = new GridBagConstraints();
			gbc_lblType.insets = new Insets(0, 0, 5, 5);
			gbc_lblType.gridx = 0;
			gbc_lblType.gridy = 15;
			toolBarSideHolderPanel.add(getLblType(), gbc_lblType);
			GridBagConstraints gbc_SpinnerDashLength = new GridBagConstraints();
			gbc_SpinnerDashLength.gridwidth = 2;
			gbc_SpinnerDashLength.insets = new Insets(0, 0, 5, 0);
			gbc_SpinnerDashLength.fill = GridBagConstraints.BOTH;
			gbc_SpinnerDashLength.gridx = 1;
			gbc_SpinnerDashLength.gridy = 15;
			toolBarSideHolderPanel.add(getSpinnerStrokeDash(), gbc_SpinnerDashLength);
			GridBagConstraints gbc_lblFill = new GridBagConstraints();
			gbc_lblFill.insets = new Insets(0, 0, 5, 5);
			gbc_lblFill.gridx = 0;
			gbc_lblFill.gridy = 16;
			toolBarSideHolderPanel.add(getLblFill(), gbc_lblFill);
			GridBagConstraints gbc_colorWellFill = new GridBagConstraints();
			gbc_colorWellFill.gridwidth = 2;
			gbc_colorWellFill.insets = new Insets(0, 0, 5, 0);
			gbc_colorWellFill.fill = GridBagConstraints.VERTICAL;
			gbc_colorWellFill.gridx = 1;
			gbc_colorWellFill.gridy = 16;
			toolBarSideHolderPanel.add(getColorWellFill(), gbc_colorWellFill);

			GridBagConstraints gridBagConstraints42 = new GridBagConstraints();
			gridBagConstraints42.insets = new Insets(0, 0, 5, 5);
			gridBagConstraints42.fill = GridBagConstraints.BOTH;
			gridBagConstraints42.gridx = 0;
			gridBagConstraints42.gridy = 17;
			labelColourStroke = new JLabel();
			labelColourStroke.setHorizontalAlignment(SwingConstants.CENTER);
			labelColourStroke.setText("Stroke");
			labelColourStroke.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			toolBarSideHolderPanel.add(labelColourStroke, gridBagConstraints42);
			GridBagConstraints gbc_colorWellStroke = new GridBagConstraints();
			gbc_colorWellStroke.gridwidth = 2;
			gbc_colorWellStroke.insets = new Insets(0, 0, 5, 0);
			gbc_colorWellStroke.fill = GridBagConstraints.VERTICAL;
			gbc_colorWellStroke.gridx = 1;
			gbc_colorWellStroke.gridy = 17;
			toolBarSideHolderPanel.add(getColorWellStroke(), gbc_colorWellStroke);

			buttonGroup1.add(getToolSelectionButton());
			buttonGroup1.add(getToolMoveButton());
			buttonGroup1.add(getToolRotateButton());
			buttonGroup1.add(getToolSelectionMarqueeButton());
			buttonGroup1.add(getToolPolygonSelectButton());
			buttonGroup1.add(getToolSelectionOval());
			buttonGroup1.add(getToolDummyButton());
			buttonGroup1.add(getToolHoleButton());
			buttonGroup1.add(getToolLabelButton());
			buttonGroup1.add(getToolPatternButton());
			buttonGroup1.add(getToolPolyLineButton());
			buttonGroup1.add(getToolNGonButton());
			buttonGroup1.add(getToolTieButton());
			buttonGroup1.add(getToolMultiTieButton());
			buttonGroup1.add(getToolMultiTieInLineButton());
			buttonGroup1.add(getToolTextButton());
			buttonGroup1.add(getToolRulerButton());
			buttonGroup1.add(getToolAddDetonatorButton());
			buttonGroup1.add(getToolAddMultiDetonatorButton());
			buttonGroup1.add(getToolRulerBearing());
			buttonGroup1.add(getToolDimensionArrowButton());

			GridBagConstraints gridBagConstraints25 = new GridBagConstraints();
			gridBagConstraints25.gridheight = 2;
			gridBagConstraints25.fill = 1;
			gridBagConstraints25.gridx = 0;
			gridBagConstraints25.gridwidth = 3;
			gridBagConstraints25.weightx = 0.5;
			gridBagConstraints25.gridy = 19;

			ToolBoxStatusLabel = new JLabel();
			ToolBoxStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
			ToolBoxStatusLabel.setText("");
			ToolBoxStatusLabel.setToolTipText("");
			ToolBoxStatusLabel.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/iconLite64px.png")));
			toolBarSideHolderPanel.add(ToolBoxStatusLabel, gridBagConstraints25);

		}
		return toolBarSideHolderPanel;
	}

	/**
	 * This method initializes downHoleButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getDetonatorTimeButton2() {
		if (detonatorTimeButton2 == null) {
			detonatorTimeButton2 = new JToggleButton();
			detonatorTimeButton2.setText("Det 2");
			// downHoleButton1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			detonatorTimeButton2.setPreferredSize(new Dimension(140, 24));
			detonatorTimeButton2.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/downhole.png"))));
			dDelay = Integer.parseInt(getDetonatorTimeTextField2().getText());
			dColor2 = getDetonatorTimeTextField2().getBackground();
			// fix below with a check on null - MABEY????
			detonatorTimeButton2.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {

					if (detonatorTimeButton2.isSelected()) {

						dDelay = Integer.parseInt(getDetonatorTimeTextField2().getText());
						dColor2 = getDetonatorTimeTextField2().getBackground();

						detonatorTimeButton1.setSelected(false);
						detonatorTimeButton3.setSelected(false);

					}
				}
			});
		}
		return detonatorTimeButton2;
	}

	/**
	 * This method initializes downHoleTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDetonatorTimeTextField2() {
		if (detonatorTimeTextField2 == null) {
			detonatorTimeTextField2 = new JTextField();
			detonatorTimeTextField2.setText("450");
			detonatorTimeTextField2.setHorizontalAlignment(JTextField.CENTER);
			detonatorTimeTextField2.setPreferredSize(new Dimension(50, 24));
			detonatorTimeTextField2.setBackground(new Color(240, 110, 0));// orange
																			// colour
			detonatorTimeTextField2.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getDetonatorTimeTextField2().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Det #2 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			detonatorTimeTextField2.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((detonatorTimeTextField2.getBackground().getRed()
							+ detonatorTimeTextField2.getBackground().getGreen()
							+ detonatorTimeTextField2.getBackground().getBlue()) / 3 < 130) {
						getDetonatorTimeTextField2().setForeground(Color.white);
					} else
						getDetonatorTimeTextField2().setForeground(Color.black);
				}
			});
		}
		return detonatorTimeTextField2;
	}

	/**
	 * This method initializes downHoleButton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getDetonatorTimeButton3() {
		if (detonatorTimeButton3 == null) {
			detonatorTimeButton3 = new JToggleButton();
			detonatorTimeButton3.setText("Det 3");
			// downHoleButton2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			detonatorTimeButton3.setPreferredSize(new Dimension(140, 24));
			detonatorTimeButton3.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/downhole.png"))));
			dDelay = Integer.parseInt(getDetonatorTimeTextField3().getText());
			dColor3 = getDetonatorTimeTextField3().getBackground();
			// fix below with a check on null - MABEY????
			detonatorTimeButton3.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {

					if (detonatorTimeButton2.isSelected()) {

						dDelay = Integer.parseInt(getDetonatorTimeTextField3().getText());
						dColor2 = getDetonatorTimeTextField3().getBackground();

						detonatorTimeButton1.setSelected(false);
						detonatorTimeButton2.setSelected(false);

					}
				}
			});
		}
		return detonatorTimeButton3;
	}

	/**
	 * This method initializes downHoleTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDetonatorTimeTextField3() {
		if (detonatorTimeTextField3 == null) {
			detonatorTimeTextField3 = new JTextField();
			detonatorTimeTextField3.setText("500");
			detonatorTimeTextField3.setHorizontalAlignment(JTextField.CENTER);
			detonatorTimeTextField3.setPreferredSize(new Dimension(50, 24));
			detonatorTimeTextField3.setBackground(new Color(45, 100, 90));// teal
																			// colour
			detonatorTimeTextField3.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getDetonatorTimeTextField3().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Det #3 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			detonatorTimeTextField3.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((detonatorTimeTextField3.getBackground().getRed()
							+ detonatorTimeTextField3.getBackground().getGreen()
							+ detonatorTimeTextField3.getBackground().getBlue()) / 3 < 130) {
						getDetonatorTimeTextField3().setForeground(Color.white);
					} else
						getDetonatorTimeTextField3().setForeground(Color.black);
				}
			});
		}
		return detonatorTimeTextField3;
	}

	/**
	 * This method initializes gridOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getGridOnOffButton() {
		if (gridOnOffButton == null) {
			gridOnOffButton = new JToggleButton();
			gridOnOffButton.setSelected(true);
			gridOnOffButton.setPreferredSize(new Dimension(22, 22));
			gridOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/gridonoff.png"))));

			gridOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (gridOnOffButton.isSelected()) {
						statusBarLabel.setText("Grid: ON");
						setConsoleOutput(
								"\nGrid ON - Scaling Factor = " + decimalFormat4.format(Zoom.getScalingFactor()));
						updateCanvas();
					} else {
						statusBarLabel.setText("Grid: OFF");
						setConsoleOutput(
								"\nGrid OFF - Scaling Factor = " + decimalFormat4.format(Zoom.getScalingFactor()));
						updateCanvas();
					}
				}
			});

		}
		return gridOnOffButton;
	}

	/**
	 * This method initializes angleOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getAngleOnOffButton() {
		if (angleOnOffButton == null) {
			angleOnOffButton = new JToggleButton();
			angleOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holeangle.png"))));
			angleOnOffButton.setPreferredSize(new Dimension(22, 22));
			angleOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (angleOnOffButton.isSelected()) {
						statusBarLabel.setText("Hole Angles: ON");// this
																	// status
																	// Label
																	// ain't
																	// working
																	// for
																	// some
																	// reason
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole Angles: OFF");// this
																	// status
																	// Label
																	// ain't
																	// working
																	// for
																	// some
																	// reason
						updateCanvas();
					}
				}
			});
		}
		return angleOnOffButton;
	}

	/**
	 * This method initializes holeTracksOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getHoleTracksOnOffButton() {
		if (holeTracksOnOffButton == null) {
			holeTracksOnOffButton = new JToggleButton();
			holeTracksOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holetracks.png"))));
			holeTracksOnOffButton.setPreferredSize(new Dimension(22, 22));
			holeTracksOnOffButton.setSelected(true);
			holeTracksOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holeTracksOnOffButton.isSelected()) {
						statusBarLabel.setText("Hole Tracks: ON");// this
																	// status
																	// Label
																	// ain't
																	// working
																	// for
																	// some
																	// reason
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole Tracks: OFF");// this
																	// status
																	// Label
																	// ain't
																	// working
																	// for
																	// some
																	// reason
						updateCanvas();
					}
				}
			});
		}
		return holeTracksOnOffButton;
	}

	/**
	 * This method initializes StatusBarPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getStatusBarPanel() {
		if (statusBarPanel == null) {
			GridBagConstraints gridBagConstraints63 = new GridBagConstraints();
			gridBagConstraints63.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints63.gridx = 1;
			gridBagConstraints63.gridy = 0;
			gridBagConstraints63.anchor = GridBagConstraints.WEST;
			gridBagConstraints63.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints63.gridwidth = 38;

			statusBarPanel = new JPanel();
			statusBarPanel.setLayout(new GridBagLayout());
			statusBarPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 1;
			gridBagConstraints6.ipadx = 0;
			gridBagConstraints6.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints6.weighty = 1.0D;
			gridBagConstraints6.weightx = 0.5;
			gridBagConstraints6.gridy = 1;
			statusBarPanel.add(getGridOnOffButton(), gridBagConstraints6);
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints19.gridx = 2;
			gridBagConstraints19.weightx = 0.5;
			gridBagConstraints19.gridy = 1;
			statusBarPanel.add(getBoundaryOnOffButton(), gridBagConstraints19);
			GridBagConstraints gridBagConstraints98 = new GridBagConstraints();
			gridBagConstraints98.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints98.gridx = 3;
			gridBagConstraints98.weightx = 0.5;
			gridBagConstraints98.gridy = 1;
			statusBarPanel.add(getTextOnOffButton(), gridBagConstraints98);
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints7.gridx = 4;
			gridBagConstraints7.weightx = 0.5;
			gridBagConstraints7.gridy = 1;
			statusBarPanel.add(getAngleOnOffButton(), gridBagConstraints7);
			// HOLE COLLARS ON OFF
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints10.gridx = 5;
			gridBagConstraints10.weightx = 0.5;
			gridBagConstraints10.gridy = 1;
			statusBarPanel.add(getHolesOnOffButton(), gridBagConstraints10);
			// ENHANCE 1
			GridBagConstraints gbc_spinnerEnhanceAmount = new GridBagConstraints();
			gbc_spinnerEnhanceAmount.fill = GridBagConstraints.HORIZONTAL;
			gbc_spinnerEnhanceAmount.insets = new Insets(0, 0, 0, 5);
			gbc_spinnerEnhanceAmount.gridx = 7;
			gbc_spinnerEnhanceAmount.gridy = 1;
			statusBarPanel.add(getSpinnerEnhanceAmount(), gbc_spinnerEnhanceAmount);
			// DUMMY HOLES ON OFF
			GridBagConstraints gridBagConstraints64 = new GridBagConstraints();
			gridBagConstraints64.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints64.gridx = 6;
			gridBagConstraints64.weightx = 0.5;
			gridBagConstraints64.gridy = 1;
			statusBarPanel.add(getDummyOnOffButton(), gridBagConstraints64);
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints8.gridx = 8;
			gridBagConstraints8.weightx = 0.5;
			gridBagConstraints8.gridy = 1;
			statusBarPanel.add(getHoleTracksOnOffButton(), gridBagConstraints8);
			// FLOOR RL LINE
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints4.gridx = 11;
			gridBagConstraints4.weightx = 0.5;
			gridBagConstraints4.gridy = 1;
			statusBarPanel.add(getFRLMarkerButton(), gridBagConstraints4);
			// TOE AND FLOOR HOLE ENHANCER
			GridBagConstraints gbc_spinnerEnhanceAmount2 = new GridBagConstraints();
			gbc_spinnerEnhanceAmount2.insets = new Insets(0, 0, 0, 5);
			gbc_spinnerEnhanceAmount2.gridx = 13;
			gbc_spinnerEnhanceAmount2.gridy = 1;
			statusBarPanel.add(getSpinnerEnhanceAmount2(), gbc_spinnerEnhanceAmount2);
			// FLOOR RL CIRCLE
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints9.gridx = 9;
			gridBagConstraints9.weightx = 0.5;
			gridBagConstraints9.gridy = 1;
			statusBarPanel.add(getFRLCircleMarkerButton(), gridBagConstraints9);
			// TOE RL LINE
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints5.gridx = 12;
			gridBagConstraints5.weightx = 0.5;
			gridBagConstraints5.gridy = 1;
			statusBarPanel.add(getTRLMarkerButton(), gridBagConstraints5);
			// TOE RL CIRCLE
			GridBagConstraints gridBagConstraints55 = new GridBagConstraints();
			gridBagConstraints55.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints55.gridx = 10;
			gridBagConstraints55.weightx = 0.5;
			gridBagConstraints55.gridy = 1;
			statusBarPanel.add(getTRLCircleMarkerButton(), gridBagConstraints55);

			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints13.gridx = 14;
			gridBagConstraints13.weightx = 0.5;
			gridBagConstraints13.gridy = 1;
			statusBarPanel.add(getHoleIDOnOffButton(), gridBagConstraints13);
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints14.gridx = 15;
			gridBagConstraints14.weightx = 0.5;
			gridBagConstraints14.gridy = 1;
			statusBarPanel.add(getHoleLabel1OnOffButton(), gridBagConstraints14);
			GridBagConstraints gridBagConstraints37 = new GridBagConstraints();
			gridBagConstraints37.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints37.gridx = 16;
			gridBagConstraints37.weightx = 0.5;
			gridBagConstraints37.gridy = 1;
			statusBarPanel.add(getHoleLabel2OnOffButton(), gridBagConstraints37);
			GridBagConstraints gridBagConstraints38 = new GridBagConstraints();
			gridBagConstraints38.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints38.gridx = 17;
			gridBagConstraints38.weightx = 0.5;
			gridBagConstraints38.gridy = 1;
			statusBarPanel.add(getHoleLabel3OnOffButton(), gridBagConstraints38);
			GridBagConstraints gridBagConstraints62 = new GridBagConstraints();
			gridBagConstraints62.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints62.gridx = 18;
			gridBagConstraints62.weightx = 0.5;
			gridBagConstraints62.gridy = 1;
			statusBarPanel.add(getBenchHeightOnOffButton(), gridBagConstraints62);
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints15.gridx = 19;
			gridBagConstraints15.weightx = 0.5;
			gridBagConstraints15.gridy = 1;
			statusBarPanel.add(getHoleSubdrillOnOffButton(), gridBagConstraints15);
			GridBagConstraints gridBagConstraints23 = new GridBagConstraints();
			gridBagConstraints23.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints23.gridx = 20;
			gridBagConstraints23.weightx = 0.5;
			gridBagConstraints23.gridy = 1;

			statusBarPanel.add(getCollarRLOnOffButton(), gridBagConstraints23);
			GridBagConstraints gridBagConstraints59 = new GridBagConstraints();
			gridBagConstraints59.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints59.gridx = 21;
			gridBagConstraints59.weightx = 0.5;
			gridBagConstraints59.gridy = 1;
			statusBarPanel.add(getFloorRLOnOffButton(), gridBagConstraints59);
			GridBagConstraints gridBagConstraints24 = new GridBagConstraints();
			gridBagConstraints24.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints24.gridx = 22;
			gridBagConstraints24.weightx = 0.5;
			gridBagConstraints24.gridy = 1;
			statusBarPanel.add(getToeRLOnOffButton(), gridBagConstraints24);
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints16.gridx = 23;
			gridBagConstraints16.weightx = 0.5;
			gridBagConstraints16.gridy = 1;
			statusBarPanel.add(getHoleLengthOnOffButton(), gridBagConstraints16);
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints17.gridx = 24;
			gridBagConstraints17.weightx = 0.5;
			gridBagConstraints17.gridy = 1;
			statusBarPanel.add(getDiameterOnOffButton(), gridBagConstraints17);
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints18.gridx = 25;
			gridBagConstraints18.weightx = 0.5;
			gridBagConstraints18.gridy = 1;
			statusBarPanel.add(getBearingOnOffButton(), gridBagConstraints18);
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints20.gridx = 26;
			gridBagConstraints20.weightx = 0.5;
			gridBagConstraints20.gridy = 1;
			statusBarPanel.add(getTieOnOffButton(), gridBagConstraints20);
			// SURFACE TIE ENHANCE
			GridBagConstraints gbc_spinnerEnhanceAmountTie = new GridBagConstraints();
			gbc_spinnerEnhanceAmountTie.insets = new Insets(0, 0, 0, 5);
			gbc_spinnerEnhanceAmountTie.gridx = 28;
			gbc_spinnerEnhanceAmountTie.gridy = 1;
			statusBarPanel.add(getSpinnerEnhanceAmountTie(), gbc_spinnerEnhanceAmountTie);
			GridBagConstraints gridBagConstraints65 = new GridBagConstraints();
			gridBagConstraints65.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints65.gridx = 27;
			gridBagConstraints65.weightx = 0.5;
			gridBagConstraints65.gridy = 1;
			statusBarPanel.add(getTieDelayToggleButton(), gridBagConstraints65);
			GridBagConstraints gridBagConstraints96 = new GridBagConstraints();
			gridBagConstraints96.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints96.gridx = 29;
			gridBagConstraints96.weightx = 0.5;
			gridBagConstraints96.gridy = 1;
			statusBarPanel.add(getDetsOnOffButton(), gridBagConstraints96);
			GridBagConstraints gridBagConstraints97 = new GridBagConstraints();
			gridBagConstraints97.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints97.gridx = 30;
			gridBagConstraints97.weightx = 0.5;
			gridBagConstraints97.gridy = 1;
			statusBarPanel.add(getDetonatorDelayButton(), gridBagConstraints97);
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints21.gridx = 31;
			gridBagConstraints21.weightx = 0.5;
			gridBagConstraints21.gridy = 1;
			statusBarPanel.add(getDownholeTimesButton1(), gridBagConstraints21);
			GridBagConstraints gridBagConstraints22 = new GridBagConstraints();
			gridBagConstraints22.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints22.gridx = 32;
			gridBagConstraints22.weightx = 0.5;
			gridBagConstraints22.gridy = 1;
			statusBarPanel.add(getSurfaceTimesOnOffButton(), gridBagConstraints22);
			GridBagConstraints gridBagConstraints56 = new GridBagConstraints();
			gridBagConstraints56.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints56.gridx = 33;
			gridBagConstraints56.weightx = 0.5;
			gridBagConstraints56.gridy = 1;
			statusBarPanel.add(getContourButton(), gridBagConstraints56);
			GridBagConstraints gridBagConstraints58 = new GridBagConstraints();
			gridBagConstraints58.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints58.gridx = 34;
			gridBagConstraints58.weightx = 0.5;
			gridBagConstraints58.gridy = 1;
			statusBarPanel.add(getFirstMovementButton(), gridBagConstraints58);
			statusBarPanel.add(getInfoBarPanel(), gridBagConstraints63);
			GridBagConstraints gridBagConstraints57 = new GridBagConstraints();
			gridBagConstraints57.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints57.gridx = 35;
			gridBagConstraints57.weightx = 0.5;
			gridBagConstraints57.gridy = 1;
			statusBarPanel.add(getReliefButton(), gridBagConstraints57);
		}
		return statusBarPanel;
	}

	/**
	 * This method initializes holeIDOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getHoleIDOnOffButton() {
		if (holeIDOnOffButton == null) {
			holeIDOnOffButton = new JToggleButton();
			holeIDOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holeID.png"))));
			holeIDOnOffButton.setPreferredSize(new Dimension(22, 22));
			holeIDOnOffButton.setSelected(true);
			holeIDOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holeIDOnOffButton.isSelected()) {
						statusBarLabel.setText("Hole ID: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole ID: OFF");
						updateCanvas();
					}
				}
			});
		}
		return holeIDOnOffButton;
	}

	/**
	 * This method initializes holeLabelOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getHoleLabel1OnOffButton() {
		if (holeLabel1OnOffButton == null) {
			holeLabel1OnOffButton = new JToggleButton();
			holeLabel1OnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holelabel.png"))));
			holeLabel1OnOffButton.setPreferredSize(new Dimension(22, 22));
			holeLabel1OnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holeLabel1OnOffButton.isSelected()) {
						statusBarLabel.setText("Hole Label 1: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole Label 1: OFF");
						updateCanvas();
					}
				}
			});
		}
		return holeLabel1OnOffButton;
	}

	/**
	 * This method initializes holeSubdrillOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getHoleSubdrillOnOffButton() {
		if (holeSubdrillOnOffButton == null) {
			holeSubdrillOnOffButton = new JToggleButton();
			holeSubdrillOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holesubdrill.png"))));
			holeSubdrillOnOffButton.setPreferredSize(new Dimension(22, 22));
			holeSubdrillOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holeSubdrillOnOffButton.isSelected()) {
						statusBarLabel.setText("Hole Subdrill: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole Subdrill: OFF");
						updateCanvas();
					}
				}
			});
		}
		return holeSubdrillOnOffButton;
	}

	/**
	 * This method initializes holeLengthOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getHoleLengthOnOffButton() {
		if (holeLengthOnOffButton == null) {
			holeLengthOnOffButton = new JToggleButton();
			holeLengthOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holelength.png"))));
			holeLengthOnOffButton.setPreferredSize(new Dimension(22, 22));
			holeLengthOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holeLengthOnOffButton.isSelected()) {
						statusBarLabel.setText("Hole Depths: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole Depths: OFF");
						updateCanvas();
					}
				}
			});
		}
		return holeLengthOnOffButton;
	}

	/**
	 * This method initializes diameterOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getDiameterOnOffButton() {
		if (diameterOnOffButton == null) {
			diameterOnOffButton = new JToggleButton();
			diameterOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holediameter.png"))));
			diameterOnOffButton.setPreferredSize(new Dimension(22, 22));
			diameterOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (diameterOnOffButton.isSelected()) {
						statusBarLabel.setText("Diameters: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Diameters: OFF");
						updateCanvas();
					}
				}
			});
		}
		return diameterOnOffButton;
	}

	/**
	 * This method initializes bearingOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getBearingOnOffButton() {
		if (bearingOnOffButton == null) {
			bearingOnOffButton = new JToggleButton();
			bearingOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holebearing.png"))));
			bearingOnOffButton.setPreferredSize(new Dimension(22, 22));
			bearingOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (bearingOnOffButton.isSelected()) {
						statusBarLabel.setText("Hole Bearings: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole Bearings: OFF");
						updateCanvas();
					}
				}
			});
		}
		return bearingOnOffButton;
	}

	/**
	 * This method initializes boundaryOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getBoundaryOnOffButton() {
		if (boundaryOnOffButton == null) {
			boundaryOnOffButton = new JToggleButton();
			boundaryOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/boundaryonoff.png"))));
			boundaryOnOffButton.setPreferredSize(new Dimension(22, 22));
			boundaryOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (boundaryOnOffButton.isSelected()) {
						statusBarLabel.setText("Boundaries: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Boundaries: OFF");
						updateCanvas();
					}
				}
			});
		}
		return boundaryOnOffButton;
	}

	/**
	 * This method initializes tieOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getTieOnOffButton() {
		if (tieOnOffButton == null) {
			tieOnOffButton = new JToggleButton();
			tieOnOffButton.setSelected(true);
			tieOnOffButton.setPreferredSize(new Dimension(22, 22));
			tieOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/tiesoffon.png"))));
			tieOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tieOnOffButton.isSelected()) {
						statusBarLabel.setText("Surface Ties: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Surface Ties: OFF");
						updateCanvas();
					}
				}
			});
		}
		return tieOnOffButton;
	}

	/**
	 * This method initializes holeToolButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolHoleButton() {
		if (toolHoleButton == null) {
			toolHoleButton = new JToggleButton();
			toolHoleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolHoleButton.setMaximumSize(dimension30x30);
			toolHoleButton.setMinimumSize(dimension30x30);
			toolHoleButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/insertHole.png"))));
			toolHoleButton.setToolTipText("Hole Tool");
			toolHoleButton.setPreferredSize(dimension30x30);
			toolHoleButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					currentMouseMover = null;

					setStatusBarLabel("Click in Screen");// fix this
															// later to
															// something
															// for the
															// layman.
					currentMouseClicker = new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {

							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
							System.out.println("*     " + Double.toString(northing) + " & " + Double.toString(easting));// FOR
																														// DEBUGGING

							// FIXME
							try {
								dummy = DialogCreateHole.showBox(null,
										Double.parseDouble(decimalFormat4.format(northing)),
										Double.parseDouble(decimalFormat4.format(easting)),
										Double.parseDouble(decimalFormat4
												.format(currentPattern.getLastDummyOrHoleIDInPattern().getZ())),
										// averageCRL,
										true);
								System.out.println(
										"**    " + Double.toString(northing) + " & " + Double.toString(easting));// FOR
																													// DEBUGGING
							} catch (NumberFormatException e2) {
								System.out.println("getToolHoleButton() method - NumberFormatException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolHoleButton() method - NumberFormatException");
								e2.printStackTrace();
							} catch (ZeroArgumentException e2) {
								System.out.println("getToolHoleButton() method - ZeroArgumentException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolHoleButton() method - ZeroArgumentException");
								e2.printStackTrace();
							} catch (NegativeNumberException e2) {
								System.out.println("getToolHoleButton() method - NegativeNumberException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolHoleButton() method - NegativeNumberException");
								e2.printStackTrace();
							}
							if (!(dummy == null)) {
								if (currentPattern == null) {
									Pattern firstPattern = new Pattern(0, 0);
									try {
										world.addPattern(firstPattern, false);
									} catch (ZeroArgumentException e1) {
										// TODO Auto-generated catch
										// block
										e1.printStackTrace();
									} catch (NegativeNumberException e1) {
										// TODO Auto-generated catch
										// block
										e1.printStackTrace();
									} // .add(firstPattern);
									currentPattern = firstPattern;

								}
								try {
									currentPattern.addDummy(dummy, false);
								}

								catch (NegativeNumberException e1) {
									System.out.println("getToolHoleButton() method - NegativeNumberException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolHoleButton() method - NegativeNumberException");
									e1.printStackTrace();
								} catch (ZeroArgumentException e1) {
									System.out.println("getToolHoleButton() method - ZeroArgumentException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolHoleButton() method - ZeroArgumentException");
									e1.printStackTrace();
								}
								saveAsMenuItem.setEnabled(true);

								// toolHoleButton.setSelected(false);
								updateCanvas();

							}
							// currentMouseClicker =
							// defaultMouseClicker;
							// currentMouseMover = defaultMouseMover;
							// if (dummy.getHoleID() != 0){
							// setStatusBarLabel("Added hole: now "+dummy.getHoleID()+" in pattern.You have
							// "
							// + world.getPatternList().size() +
							// " patterns created."+
							// dummy.getNorthing()+
							// " & "+dummy.getEasting());//got to print
							// the useID from patternfix soon
							// }
							getTreeModelPattern();
						}
					};
				}
			});
		}
		return toolHoleButton;
	}

	/**
	 * This method initializes dummyToolButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolDummyButton() {
		if (toolDummyButton == null) {
			toolDummyButton = new JToggleButton();
			toolDummyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolDummyButton.setMaximumSize(dimension30x30);
			toolDummyButton.setMinimumSize(dimension30x30);
			toolDummyButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/insertDummy.png")));
			toolDummyButton.setToolTipText("Dummy Hole Tool");
			toolDummyButton.setPreferredSize(dimension30x30);

			toolDummyButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					// setCursor(dummyToolCursor);
					setStatusBarLabel("Click in Screen");// fix this
															// later to
															// something
															// for the
															// layman.
					currentMouseClicker = new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {

							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

							try {
								dummy = DialogCreateHole.showBox(null,
										Double.parseDouble(decimalFormat4.format(northing)),
										Double.parseDouble(decimalFormat4.format(easting)),
										averageCRL, false);
							} catch (NumberFormatException e2) {
								System.out.println("getToolDummyButton() method - NumberFormatException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolDummyButton() method - NumberFormatException");
								e2.printStackTrace();
							} catch (ZeroArgumentException e2) {
								System.out.println("getToolDummyButton() method - ZeroArgumentException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolDummyButton() method - ZeroArgumentException");
								e2.printStackTrace();
							} catch (NegativeNumberException e2) {
								System.out.println("getToolDummyButton() method - NegativeNumberException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolDummyButton() method - NegativeNumberException");
								e2.printStackTrace();
							}
							if (!(dummy == null)) {
								if (currentPattern == null) {
									Pattern firstPattern = new Pattern(0, 0);
									try {
										world.addPattern(firstPattern, false);
									} catch (ZeroArgumentException e1) {
										// TODO Auto-generated catch
										// block
										e1.printStackTrace();
									} catch (NegativeNumberException e1) {
										// TODO Auto-generated catch
										// block
										e1.printStackTrace();
									} // patternList.add(firstPattern);
									currentPattern = firstPattern;

								}
								try {
									currentPattern.addDummy(dummy, false);
								}

								catch (NegativeNumberException e1) {
									System.out.println("getToolDummyButton() method - NegativeNumberException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolDummyButton() method - NegativeNumberException");
									e1.printStackTrace();
								} catch (ZeroArgumentException e1) {
									System.out.println("getToolDummyButton() method - ZeroArgumentException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolDummyButton() method - ZeroArgumentException");
									e1.printStackTrace();
								}
								saveAsMenuItem.setEnabled(true);
								// setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));

								updateCanvas();

							}
							// currentMouseClicker =
							// defaultMouseClicker;
							// currentMouseMover = defaultMouseMover;
							setStatusBarLabel("Added dummy hole: now " + dummy.getHoleID() + " in pattern.  You have "
									+ world.getPatternList().size() + " patterns created. "
									+ dummy.getY() + " & " + dummy.getX());
							getTreeModelPattern();
						}
					};
				}
			});
		}
		return toolDummyButton;
	}

	/**
	 * This method initializes patternToolButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolPatternButton() {
		if (toolPatternButton == null) {
			toolPatternButton = new JToggleButton();
			toolPatternButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolPatternButton.setMaximumSize(dimension30x30);
			toolPatternButton.setMinimumSize(dimension30x30);
			toolPatternButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/pattern.png"))));
			toolPatternButton.setToolTipText("Pattern Tool");
			toolPatternButton.setPreferredSize(dimension30x30);
			toolPatternButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					// setCursor(patternToolCursor);
					setStatusBarLabel("Click in Screen");// fix this
															// later to
															// something
															// for the
															// layman.
					currentMouseClicker = new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {

							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							System.out.println(northing + "= northing");
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
							System.out.println(easting + "= easting");
							try {
								pattern = DialogCreatePattern.showBox(canvasPanel,
										Double.parseDouble(decimalFormat4.format(northing)),
										Double.parseDouble(decimalFormat4.format(easting)), averageCurrentPatternCRL());
							} catch (NumberFormatException e1) {
								System.out.println("getToolPatternButton() method - NumberFormatException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolPatternButton() method - NumberFormatException");
								e1.printStackTrace();
							} catch (IllegalArgumentException e1) {
								System.out.println("getToolPatternButton() method - IllegalArgumentException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolPatternButton() method - IllegalArgumentException");
								e1.printStackTrace();
							} catch (NegativeNumberException e1) {
								System.out.println("getToolPatternButton() method - NegativeNumberException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolPatternButton() method - NegativeNumberException");
								e1.printStackTrace();
							} catch (ZeroArgumentException e1) {
								System.out.println("getToolPatternButton() method - ZeroArgumentException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolPatternButton() method - ZeroArgumentException");
								e1.printStackTrace();
							}

							if (!(pattern == null)) {
								currentPattern = pattern;
								try {
									world.addPattern(pattern, false);
								} catch (ZeroArgumentException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (NegativeNumberException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} // patternList.add(shot);

								saveAsMenuItem.setEnabled(true);
								saveAsButton.setEnabled(true);

								// setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));//UNCOMMENT
								// IF RETURN TO DEFAULT -perhaps have an
								// option to return to the default
								// selection tool in Preferences.class
								updateCanvas();

								statusBarLabel.setText("Pattern Created: You have " + world.getPatternList().size()
										+ " pattern(s). Current Pattern has "
										+ currentPattern.getHoleList().size() + " hole(s).");
								currentMouseClicker = defaultMouseClicker;
								currentMouseMover = defaultMouseMover;

							} else if (pattern == null) {
								statusBarLabel.setText("Pattern Not Created");
							}
							getTreeModelPattern();
							// DefaultTreeModel model =
							// (DefaultTreeModel)
							// getPatTree().getModel();
							// DefaultMutableTreeNode nNode = new
							// DefaultMutableTreeNode();
							// TreePath path = patTree.getNextMatch("M",
							// 0, Position.Bias.Forward);
							// MutableTreeNode node =
							// (MutableTreeNode)path.getLastPathComponent();
							// model.insertNodeInto(nNode, node,
							// node.getChildCount());
							//

							System.out.println("World node count ="
									+ getTreeModelPattern().getModel().getChildCount(patternTreeModel.getRoot()));

							// currentMouseClicker =
							// defaultMouseClicker; //UNCOMMENT IF
							// RETURN TO DEFAULT -perhaps have an option
							// to return to the default selection tool
							// in Preferences.class
							// currentMouseMover =
							// defaultMouseMover;//UNCOMMENT IF RETURN
							// TO DEFAULT -perhaps have an option to
							// return to the default selection tool in
							// Preferences.class
							statusBarLabel
									.setText("PATTERN TOOL ACTIVE: Select another tool or the Selection Arrow to EXIT");
						}
					};
					currentMouseMover = defaultMouseMover;
				}
			});
		}
		return toolPatternButton;
	}

	/**
	 * This method initializes downholeTimesButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getDownholeTimesButton1() {
		if (downholeTimesButton1 == null) {
			downholeTimesButton1 = new JToggleButton();
			downholeTimesButton1.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/nominaltimeDH.png")));
			downholeTimesButton1.setPreferredSize(new Dimension(22, 22));
		}
		return downholeTimesButton1;
	}

	/**
	 * This method initializes surfaceTimesOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getSurfaceTimesOnOffButton() {
		if (surfaceTimesOnOffButton == null) {
			surfaceTimesOnOffButton = new JToggleButton();
			surfaceTimesOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/nominaltimeSUR.png")));

			surfaceTimesOnOffButton.setPreferredSize(new Dimension(22, 22));
			surfaceTimesOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (surfaceTimesOnOffButton.isSelected()) {
						statusBarLabel.setText("Surface Times: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Surface Times: OFF");
						updateCanvas();
					}
				}
			});

		}
		return surfaceTimesOnOffButton;
	}

	/**
	 * This method initializes collarRLOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getCollarRLOnOffButton() {
		if (collarRLOnOffButton == null) {
			collarRLOnOffButton = new JToggleButton();
			collarRLOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/collarRL.png")));
			collarRLOnOffButton.setPreferredSize(new Dimension(22, 22));
			collarRLOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (collarRLOnOffButton.isSelected()) {
						statusBarLabel.setText("Collar RL: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Collar RL: OFF");
						updateCanvas();
					}
				}
			});
		}
		return collarRLOnOffButton;
	}

	/**
	 * This method initializes toeRLOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToeRLOnOffButton() {
		if (toeRLOnOffButton == null) {
			toeRLOnOffButton = new JToggleButton();
			toeRLOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/toeRL.png")));
			toeRLOnOffButton.setPreferredSize(new Dimension(22, 22));
			toeRLOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (toeRLOnOffButton.isSelected()) {
						statusBarLabel.setText("Toe RL: ON");

						updateCanvas();
					} else {
						statusBarLabel.setText("Toe RL: OFF");
						updateCanvas();
					}
				}
			});
		}
		return toeRLOnOffButton;
	}

	/**
	 * This method initializes liteTieProgressBar
	 * 
	 * @return javax.swing.JProgressBar
	 */
	private JProgressBar getLiteTieTRIALProgressBar() {
		if (liteTieProgressBar == null) {
			liteTieProgressBar = new JProgressBar();
			liteTieProgressBar.setPreferredSize(new Dimension(50, 10));
			liteTieProgressBar.setBorder(null);
		}
		return liteTieProgressBar;
	}

	/**
	 * This method initializes toolTextButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolTextButton() {
		if (toolTextButton == null) {
			toolTextButton = new JToggleButton();
			toolTextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolTextButton.setMinimumSize(new Dimension(24, 22));
			toolTextButton.setMaximumSize(new Dimension(24, 22));
			toolTextButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/text.png")));
			toolTextButton.setToolTipText("Text Box Tool");
			toolTextButton.setPreferredSize(new Dimension(24, 22));
			toolTextButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					currentMouseMover = null;
					setStatusBarLabel("Click in Screen");
					textOnOffButton.setSelected(true);

					currentMouseClicker = new MouseAdapter() {

						@Override
						public void mouseReleased(MouseEvent e) {

							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX() / Zoom.getScalingFactor());
							int defaultBearing = 90;
							try {
								textPoint = new Point2D.Double(easting, northing);

							} catch (NumberFormatException e2) {
								System.out.println("getToolBoundaryButton() method - NumberFormatException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - NumberFormatException");
								e2.printStackTrace();
							}

							if ((!(textPoint == null))) {

								try {
									text = new Text(easting, northing, lastRLHeight, defaultBearing, "", default_Font,
											currentColor);

									String newValue = JOptionPane.showInputDialog(null, "Input text", "Type here...");
									if (newValue != null || newValue != "") {
										text.setTextAndAttributes(newValue, default_Font, defaultBearing, currentColor);

										// This code takes Text Values and
										// calculates them the text value
										// returned is the resultant equation.
										if (newValue.startsWith("=")) {
											text.setText(calculateValueFromString(newValue));
										}
									}
									// else
									// JOptionPane.showMessageDialog(null,"Text needs to be something other than
									// Null",
									// "Text Input Error", ERROR);

									setStatusBarLabel("Added text: ID#" + text.getTextID());
									world.addText(text, false);
									getTreeModelText();
								} catch (NegativeNumberException e1) {
									System.out.println("getToolBoundaryButton() method - NegativeNumberException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolBoundaryButton() method - NegativeNumberException");
									e1.printStackTrace();
								} catch (ZeroArgumentException e1) {
									System.out.println("getToolBoundaryButton() method - ZeroArgumentException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolBoundaryButton() method - ZeroArgumentException");
									e1.printStackTrace();
								} catch (NullPointerException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, "Calculation Error: " + e1.getMessage(),
											"Calculation Error", JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								} catch (IllegalArgumentException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, "Calculation Error: " + e1.getMessage(),
											"Calculation Error", JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								} catch (UnknownFunctionException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, "Calculation Error: " + e1.getMessage(),
											"Calculation Error", JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								} catch (UnparsableExpressionException e1) {
									// TODO Auto-generated catch block
									JOptionPane.showMessageDialog(null, "Calculation Error: " + e1.getMessage(),
											"Calculation Error", JOptionPane.ERROR_MESSAGE);
									e1.printStackTrace();
								}

								saveAsMenuItem.setEnabled(true);

								updateCanvas();

							}

						}
					};
				}

			});

		}
		return toolTextButton;
	}

	// Native Java Library that replaces the
	// calculateValueForomStingUsingJavaScriptEval()
	private String calculateValueFromString(String newValue)
			throws UnknownFunctionException, UnparsableExpressionException {
		newValue = newValue.substring(1);
		Calculable c = new ExpressionBuilder(newValue).build();
		double dVal = c.calculate();
		if (dVal % 1 == 0) {
			newValue = Integer.toString((int) dVal);
		} else {
			newValue = Double.toString(dVal);
		}

		return newValue;
	}

	// Java Script Engine that performs as an EVAL function
	private String calculateValueFromStringUsingJavaScriptEval(String newValue) throws ScriptException {
		newValue = newValue.substring(1);
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		Object result = engine.eval(newValue);

		newValue = (result).toString();
		return newValue;
	}

	/**
	 * This method initializes toolRulerButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolRulerButton() {
		if (toolRulerButton == null) {
			toolRulerButton = new JToggleButton();
			toolRulerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolRulerButton.setMinimumSize(new Dimension(24, 22));
			toolRulerButton.setMaximumSize(new Dimension(24, 22));
			toolRulerButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie_v2/ruler.png")));
			toolRulerButton.setToolTipText("Ruler Tool");
			toolRulerButton.setPreferredSize(new Dimension(24, 22));

			toolRulerButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					selected = null;

					setStatusBarLabel("Click in Screen");// fix this
															// later to
															// something
															// for the
															// layman.

					currentMouseClicker = new MouseAdapter() {
						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {

							if (!(clicked)) {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								rulePoint1 = new Point2D.Double(easting, northing);
								setStatusBarLabel("Last Northing = "
										+ Double.parseDouble(decimalFormat4.format(rulePoint1.getY()))
										+ " & Last Easting = "
										+ Double.parseDouble(decimalFormat4.format(rulePoint1.getX())));
								currentMouseMover = new MouseMotionAdapter() {
									public void mouseMoved(MouseEvent e) {

										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
										rulePoint2 = new Point2D.Double(easting, northing);

										updateCanvas();

										setStatusBarLabel("Last Northing = "
												+ Double.parseDouble(decimalFormat4.format(rulePoint2.getY()))
												+ " & Last Easting = "
												+ Double.parseDouble(decimalFormat4.format(rulePoint2.getX())));
										if (rulePoint1 != null && rulePoint2 != null) {
											double xl = (rulePoint1.getX() - rulePoint2.getX());
											double yl = (rulePoint1.getY() - rulePoint2.getY());
											double d = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));

											measurementStatusLabel2.setText(decimalFormatMetres2.format(d));
										}

									}
								};

							} else {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								rulePoint2 = new Point2D.Double(easting, northing);
								if (rulePoint1 != null && rulePoint2 != null) {
									double xl = (rulePoint1.getX() - rulePoint2.getX());
									double yl = (rulePoint1.getY() - rulePoint2.getY());
									double d = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));

									measurementStatusLabel1.setText(measurementStatusLabel2.getText());
									measurementStatusLabel2.setText(decimalFormatMetres2.format(d));
									updateCanvas();
								}
								setStatusBarLabel("Last Northing = "
										+ Double.parseDouble(decimalFormat4.format(rulePoint2.getY()))
										+ " & Last Easting = "
										+ Double.parseDouble(decimalFormat4.format(rulePoint2.getX())));
								currentMouseMover = defaultMouseMover;
							}

							clicked = !clicked;

						}

					};

				}
			});
			toolRulerButton.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						rulePoint2 = null;
						rulePoint1 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});
		}
		return toolRulerButton;
	}

	/**
	 * This method initializes holesOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getHolesOnOffButton() {
		if (holesOnOffButton == null) {
			holesOnOffButton = new JToggleButton();
			holesOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/Holesonoff.png")));
			holesOnOffButton.setPreferredSize(new Dimension(22, 22));
			holesOnOffButton.setSelected(true);
			statusBarLabel.setText("");
			holesOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holesOnOffButton.isSelected()) {
						statusBarLabel.setText("Hole Display: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Hole Display: OFF");
						updateCanvas();
					}
				}
			});
		}
		return holesOnOffButton;
	}

	/**
	 * This method initializes layerHolderPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLayerHolderPanel() {
		if (layerHolderPanel == null) {
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.gridx = 0;
			gridBagConstraints51.gridy = 5;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.weighty = 1.0;
			gridBagConstraints12.gridx = 0;
			layerHolderPanel = new JPanel();
			GridBagLayout gbl_layerHolderPanel = new GridBagLayout();
			gbl_layerHolderPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0 };
			gbl_layerHolderPanel.columnWeights = new double[] { 1.0 };
			layerHolderPanel.setLayout(gbl_layerHolderPanel);
			layerHolderPanel.add(getLayerTabbedPane(), gridBagConstraints12);
			GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
			gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_1.gridx = 0;
			gbc_scrollPane_1.gridy = 1;
			layerHolderPanel.add(getScrollPane_1(), gbc_scrollPane_1);
			GridBagConstraints gbc_toolBarMeasurementDetails = new GridBagConstraints();
			gbc_toolBarMeasurementDetails.fill = GridBagConstraints.BOTH;
			gbc_toolBarMeasurementDetails.insets = new Insets(0, 0, 5, 0);
			gbc_toolBarMeasurementDetails.gridx = 0;
			gbc_toolBarMeasurementDetails.gridy = 3;

			layerHolderPanel.add(getToolBarMeasurementDetails(), gbc_toolBarMeasurementDetails);
			GridBagConstraints gbc_toolBar = new GridBagConstraints();
			gbc_toolBar.fill = GridBagConstraints.BOTH;
			gbc_toolBar.insets = new Insets(0, 0, 5, 0);
			gbc_toolBar.gridx = 0;
			gbc_toolBar.gridy = 4;
			layerHolderPanel.add(getToolBar(), gbc_toolBar);
			layerHolderPanel.add(getLayerOptionPanel(), gridBagConstraints51);
		}
		return layerHolderPanel;
	}

	/**
	 * This method initializes layerTabbedPane
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getLayerTabbedPane() {
		if (layerTabbedPane == null) {
			layerTabbedPane = new JTabbedPane();
			layerTabbedPane.setSize(new Dimension(20, 0));
			layerTabbedPane.setTabPlacement(JTabbedPane.TOP);
			layerTabbedPane.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			layerTabbedPane.addTab("Boundaries", null, getBoundaryLayerScrollPane(),
					"Displays all Boundaries in the LiteTie World");

			layerTabbedPane.addTab("Patterns", null, getPatternLayersScrollPane(),
					"Displays all patterns in the LiteTie World");
			layerTabbedPane.addTab("Surface Connectors", null, getSurfaceLayerScrollPane(),
					"Displays all Surface Connectors in the LiteTie World");
			layerTabbedPane.addTab("Text", null, getTextScrollPane(), "Adjust Text Properties");
			layerTabbedPane.addTab("System", null, getSystemTreeScrollPane(), null);
			layerTabbedPane.addTab("Images", null, getScrollPane_3(), null);
			// layerTabbedPane.addTab("Time Envelope", null,
			// getTimeEnvelopeScrollPane(), null);
		}
		return layerTabbedPane;
	}

	/**
	 * This method initializes patternLayersScrollPane
	 * 
	 * @param textTree
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getPatternLayersScrollPane() {
		if (patternLayersScrollPane == null) {
			// patternLayersScrollPane.setFont(new Font("Lucida Grande",
			// Font.PLAIN, 10));

			patternLayersScrollPane = new JScrollPane();
			patternLayersScrollPane.setToolTipText("Select the pattern you want");
			patternLayersScrollPane.setViewportView(getTreeModelPattern());

		}
		return patternLayersScrollPane;
	}

	/**
	 * This method initializes propertiesScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getBoundaryLayerScrollPane() {
		if (boundaryLayerScrollPane == null) {

			boundaryLayerScrollPane = new JScrollPane();
			boundaryLayerScrollPane.setToolTipText("View individual properties");
			boundaryLayerScrollPane.setViewportView(getTreeModelBoundary());
		}
		return boundaryLayerScrollPane;
	}

	private JScrollPane getSurfaceLayerScrollPane() {
		if (surfaceLayerScrollPane == null) {
			surfaceLayerScrollPane = new JScrollPane();
			surfaceLayerScrollPane.setToolTipText("View individual properties");
			surfaceLayerScrollPane.setViewportView(getTreeModelSurface());
		}
		return surfaceLayerScrollPane;
	}

	/**
	 * This method initializes propertiesScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	// private JScrollPane getSurfaceLayerScrollPane() {
	// if (surfaceLayerScrollPane == null) {
	//
	// surfaceLayerScrollPane = new JScrollPane();
	// surfaceLayerScrollPane.setToolTipText("View individual properties");
	// surfaceLayerScrollPane.setViewportView(getTreeModelSurface());
	// }
	// return boundaryLayerScrollPane;
	// }
	/**
	 * This method initializes layerOptionPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getLayerOptionPanel() {
		if (layerOptionPanel == null) {
			GridBagConstraints gridBagConstraints52 = new GridBagConstraints();
			gridBagConstraints52.gridx = 6;
			gridBagConstraints52.weightx = 1.0D;
			gridBagConstraints52.anchor = GridBagConstraints.EAST;
			gridBagConstraints52.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints52.gridy = 0;
			trashCanButton = new JButton();
			trashCanButton.setToolTipText("Send the selected Layer to the Trash");
			trashCanButton.setPreferredSize(new Dimension(16, 16));
			trashCanButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/trashcanSM.jpg")));
			layerOptionPanel = new JPanel();
			layerOptionPanel.setLayout(new GridBagLayout());
			layerOptionPanel.setPreferredSize(new Dimension(250, 20));
			GridBagConstraints gridBagConstraints50 = new GridBagConstraints();
			gridBagConstraints50.gridx = 0;
			gridBagConstraints50.ipadx = 0;
			gridBagConstraints50.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints50.gridy = 0;
			showLayerButton = new JButton();
			showLayerButton.setToolTipText("Show or hide selected pattern");
			showLayerButton.setPreferredSize(new Dimension(16, 16));
			showLayerButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/showeyeSM.jpg")));
			layerOptionPanel.add(showLayerButton, gridBagConstraints50);
			GridBagConstraints gridBagConstraints53 = new GridBagConstraints();
			gridBagConstraints53.gridx = 1;
			gridBagConstraints53.ipadx = 0;
			gridBagConstraints53.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints53.gridy = 0;
			newPatternLayerButton = new JButton();
			newPatternLayerButton.setToolTipText("Add a new pattern");
			newPatternLayerButton.setPreferredSize(new Dimension(16, 16));
			newPatternLayerButton
					.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/addPatternLayerSM.jpg")));
			layerOptionPanel.add(newPatternLayerButton, gridBagConstraints53);

			GridBagConstraints gridBagConstraints54 = new GridBagConstraints();
			gridBagConstraints54.gridx = 2;
			gridBagConstraints54.ipadx = 0;
			gridBagConstraints54.insets = new Insets(0, 0, 0, 5);
			gridBagConstraints54.gridy = 0;
			mergePatternsButton = new JButton();
			mergePatternsButton.setToolTipText("Merge selected patterns into one");
			mergePatternsButton.setPreferredSize(new Dimension(16, 16));
			mergePatternsButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/mergeSM.jpg")));
			layerOptionPanel.add(mergePatternsButton, gridBagConstraints54);
			layerOptionPanel.add(trashCanButton, gridBagConstraints52);
		}
		return layerOptionPanel;
	}

	/**
	 * This method initializes timeEnvelopeScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getTimeEnvelopeScrollPane() {
		if (timeEnvelopeScrollPane == null) {
			timeEnvelopeScrollPane = new JScrollPane();
			timeEnvelopeScrollPane.setToolTipText("View overlap and crowding");
			timeEnvelopeScrollPane.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		}
		return timeEnvelopeScrollPane;
	}

	/**
	 * This method initializes SelectAllInLayerMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getSelectAllInLayerMenu() {
		if (selectAllInLayerMenu == null) {
			selectAllInLayerMenu = new JMenu();
			selectAllInLayerMenu.setText("Select in Layer");
			selectAllInLayerMenu.add(getSelectAllHolesMenuItem());
			selectAllInLayerMenu.add(getSelectAllTiesMenuItem());

		}
		return selectAllInLayerMenu;
	}

	/**
	 * This method initializes allHolesMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSelectAllHolesMenuItem() {
		if (selectAllHolesMenuItem == null) {
			selectAllHolesMenuItem = new JMenuItem();
			selectAllHolesMenuItem.setText("All Holes");
			selectAllHolesMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (currentPattern != null) {

						selected = currentPattern.getAllDummysAndHoles();
						updateCanvas();

						statusBarLabel.setText("Hole(s) Selected: " + currentPattern.getNumberOfHoles()
								+ ". You have selected all holes in this Pattern.");

					}
				}
			});
		}
		return selectAllHolesMenuItem;
	}

	/**
	 * This method initializes allTiesMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getSelectAllTiesMenuItem() {
		if (selectAllTiesMenuItem == null) {
			selectAllTiesMenuItem = new JMenuItem();
			selectAllTiesMenuItem.setText("All Surface Connections");
			selectAllTiesMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					if (currentPattern != null) {

						selected = currentPattern.getAllSurfaceConnectors();
						updateCanvas();

						statusBarLabel.setText("Ties(s) Selected: " + currentPattern.getNumberOfSurfaceConnections()
								+ ". You have selected all ties in this Pattern.");

					}
				}
			});
		}
		return selectAllTiesMenuItem;
	}

	/**
	 * This method initializes fRLMarkeButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getFRLMarkerButton() {
		if (fRLMarkerButton == null) {
			fRLMarkerButton = new JToggleButton();
			fRLMarkerButton.setToolTipText("Floor RL marker line");
			fRLMarkerButton.setPreferredSize(new Dimension(22, 22));
			fRLMarkerButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/FRLline.png")));
			fRLMarkerButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (fRLMarkerButton.isSelected()) {
						statusBarLabel.setText("Floor RL Marker Line: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Floor RL Marker Line: OFF");
						updateCanvas();
					}
				}
			});
		}
		return fRLMarkerButton;
	}

	/**
	 * This method initializes tRLMarkerButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getTRLMarkerButton() {
		if (tRLMarkerButton == null) {
			tRLMarkerButton = new JToggleButton();
			tRLMarkerButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/TRLline.png")));
			tRLMarkerButton.setPreferredSize(new Dimension(22, 22));
			tRLMarkerButton.setToolTipText("Toe RL marker line");
			tRLMarkerButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tRLMarkerButton.isSelected()) {
						statusBarLabel.setText("Toe RL Marker Line: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Toe RL Marker Line: OFF");
						updateCanvas();
					}
				}
			});
		}
		return tRLMarkerButton;
	}

	/**
	 * This method initializes fRLCircleMarkerButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getFRLCircleMarkerButton() {
		if (fRLCircleMarkerButton == null) {
			fRLCircleMarkerButton = new JToggleButton();
			fRLCircleMarkerButton.setToolTipText("Floor RL marker circle");
			fRLCircleMarkerButton.setPreferredSize(new Dimension(22, 22));
			fRLCircleMarkerButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/FRLcircle.png")));
			fRLCircleMarkerButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (fRLCircleMarkerButton.isSelected()) {
						statusBarLabel.setText("Floor RL Marker Circle: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Floor RL Marker Circle: OFF");
						updateCanvas();
					}
				}
			});
		}
		return fRLCircleMarkerButton;
	}

	/**
	 * This method initializes tRLCircleMarkerButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getTRLCircleMarkerButton() {
		if (tRLCircleMarkerButton == null) {
			tRLCircleMarkerButton = new JToggleButton();
			tRLCircleMarkerButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/TRLcircle.png")));
			tRLCircleMarkerButton.setPreferredSize(new Dimension(22, 22));
			tRLCircleMarkerButton.setToolTipText("Toe RL marker circle");
			tRLCircleMarkerButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tRLCircleMarkerButton.isSelected()) {
						statusBarLabel.setText("Toe RL Marker Circle: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Toe RL Marker Circle: OFF");
						updateCanvas();
					}
				}
			});
		}
		return tRLCircleMarkerButton;
	}

	/**
	 * This method initializes contourButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getContourButton() {
		if (contourButton == null) {
			contourButton = new JToggleButton();
			contourButton.setToolTipText("Display timing contours");
			contourButton.setPreferredSize(new Dimension(22, 22));
			contourButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/showcontours.png")));
			contourButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (contourButton.isSelected()) {
						try {
							getSurfaceTimes();
						} catch (NegativeNumberException e1) {
							e1.printStackTrace();
						}
						getSurfaceTimesOnOffButton().doClick();
						statusBarLabel.setText("Contours: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Contours: OFF");
						updateCanvas();
					}
				}
			});
		}
		return contourButton;
	}

	/**
	 * This method initializes reliefButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getReliefButton() {
		if (reliefButton == null) {
			reliefButton = new JToggleButton();
			reliefButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/showrelief.png")));
			reliefButton.setPreferredSize(new Dimension(22, 22));
			reliefButton.setToolTipText("Display timing relief");
			reliefButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (reliefButton.isSelected()) {
						statusBarLabel.setText("Relief: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Relief: OFF");
						updateCanvas();
					}
				}
			});
		}
		return reliefButton;
	}

	/**
	 * This method initializes firstMovementButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getFirstMovementButton() {
		if (firstMovementButton == null) {
			firstMovementButton = new JToggleButton();
			firstMovementButton.setToolTipText("Display first movments of shot");
			firstMovementButton.setPreferredSize(new Dimension(22, 22));
			firstMovementButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/show1stMove.png")));
			firstMovementButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (firstMovementButton.isSelected()) {
						statusBarLabel.setText("First Movements: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("First Movements: OFF");
						updateCanvas();
					}
				}
			});
		}
		return firstMovementButton;
	}

	/**
	 * This method initializes floorRLOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getFloorRLOnOffButton() {
		if (floorRLOnOffButton == null) {
			floorRLOnOffButton = new JToggleButton();
			floorRLOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/floorRL.png")));
			floorRLOnOffButton.setPreferredSize(new Dimension(22, 22));
			floorRLOnOffButton.setToolTipText("Display Floor RL amount");
			floorRLOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (floorRLOnOffButton.isSelected()) {
						statusBarLabel.setText("Floor RL: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Floor RL: OFF");
						updateCanvas();
					}
				}
			});
		}
		return floorRLOnOffButton;
	}

	/**
	 * This method initializes downholeTimeButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getToolAddDetonatorButton() {
		if (toolAddDetonatorButton == null) {
			toolAddDetonatorButton = new JToggleButton();
			toolAddDetonatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolAddDetonatorButton.setMinimumSize(dimension30x30);
			toolAddDetonatorButton.setMaximumSize(dimension30x30);
			toolAddDetonatorButton.setPreferredSize(dimension30x30);
			toolAddDetonatorButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/downholeSingle.png")));

			toolAddDetonatorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					// setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					setStatusBarLabel("DETONATOR TOOL");
					selected = null;
					updateCanvas();
					getDetsOnOffButton().setSelected(true);

					if (getDetonatorTimeButton1().isSelected()) {
						getColorWellStroke().setBackground(getDetonatorTimeTextField1().getBackground());
						labelColourStroke.setText(getDetonatorTimeButton1().getText());
					}
					if (getDetonatorTimeButton2().isSelected()) {
						getColorWellStroke().setBackground(getDetonatorTimeTextField2().getBackground());
						labelColourStroke.setText(getDetonatorTimeButton2().getText());
					}
					if (getDetonatorTimeButton3().isSelected()) {
						getColorWellStroke().setBackground(getDetonatorTimeTextField3().getBackground());
						labelColourStroke.setText(getDetonatorTimeButton3().getText());
					}
					getColorWellFill().repaint();

					currentMouseClicker = new MouseAdapter() {

						@Override
						public void mouseReleased(MouseEvent e) {

							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
							// double radius =
							// UnitConvert.pixelsToMeters(8)/
							// Zoom.getScalingFactor();

							if (!(currentPattern == null)) {
								selected = currentPattern.getHoleIn(new Ellipse2D.Double(easting - radius,
										northing - radius, radius * 2, radius * 2));
								if (selected instanceof Hole) {
									hole1 = (Hole) selected;
								}
								updateCanvas();
							}

							try {
								if (hole1 instanceof Hole) {
									if (getDetonatorTimeButton1().isSelected()) {
										currentPattern.addDetonator(new Detonator(dDelay, hole1,
												hole1.getHoleLength() - hole1.getSubdrill(), dColor1), false);
									} else if (getDetonatorTimeButton2().isSelected()) {
										currentPattern.addDetonator(new Detonator(dDelay, hole1,
												hole1.getHoleLength() - hole1.getSubdrill(), dColor2), false);
									} else if (getDetonatorTimeButton3().isSelected()) {
										currentPattern.addDetonator(new Detonator(dDelay, hole1,
												hole1.getHoleLength() - hole1.getSubdrill(), dColor3), false);
									}
									statusBarLabel.setText("Detonator added = " + dDelay + "ms"
											+ "\tDetonators in List =" + currentPattern.getDetonatorList().size());
								}
							} catch (NumberFormatException e1) {
								statusBarLabel.setText("The detonator delay or ID is not a number");
								e1.printStackTrace();
							} catch (NegativeNumberException e1) {
								statusBarLabel.setText("The detonator delay or ID is a negative number");
								e1.printStackTrace();
							} catch (ZeroArgumentException e1) {
								statusBarLabel.setText("The detonator ID is a zero");
								e1.printStackTrace();
							}

						}
					};
				}
			});

			updateCanvas();
		}

		return toolAddDetonatorButton;
	}

	/**
	 * This method initializes toolMultiDownHoleTime
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getToolAddMultiDetonatorButton() {

		if (toolAddMultiDetonatorButton == null) {
			toolAddMultiDetonatorButton = new JToggleButton();
			toolAddMultiDetonatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolAddMultiDetonatorButton.setMinimumSize(dimension30x30);
			toolAddMultiDetonatorButton.setMaximumSize(dimension30x30);
			toolAddMultiDetonatorButton.setPreferredSize(dimension30x30);
			toolAddMultiDetonatorButton
					.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/downholeMulti.png")));

			toolAddMultiDetonatorButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					// setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					setStatusBarLabel("MULTI DETONATOR TOOL");
					selected = null;
					updateCanvas();
					getDetsOnOffButton().setSelected(true);
					if (getDetonatorTimeButton1().isSelected()) {
						getColorWellStroke().setBackground(getDetonatorTimeTextField1().getBackground());
						labelColourStroke.setText(getDetonatorTimeButton1().getText());
					}
					if (getDetonatorTimeButton2().isSelected()) {
						getColorWellStroke().setBackground(getDetonatorTimeTextField2().getBackground());
						labelColourStroke.setText(getDetonatorTimeButton2().getText());
					}
					if (getDetonatorTimeButton3().isSelected()) {
						getColorWellStroke().setBackground(getDetonatorTimeTextField3().getBackground());
						labelColourStroke.setText(getDetonatorTimeButton3().getText());
					}
					getColorWellFill().repaint();

					currentMouseClicker = new MouseAdapter() {

						@Override
						public void mouseReleased(MouseEvent e) {

							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
							double radius = UnitConvert.pixelsToMeters(8) / Zoom.getScalingFactor();

							if (!(currentPattern == null)) {
								selected = currentPattern.getHoleIn(new Ellipse2D.Double(easting - radius,
										northing - radius, radius * 2, radius * 2));
								if (selected instanceof Hole) {
									selected = currentPattern.getAllHolesInPattern();
								}
								updateCanvas();
							}

							try {
								if (selected != null) {
									for (Hole o : ((Set<Hole>) selected)) {
										if (o instanceof Hole) {
											if (getDetonatorTimeButton1().isSelected()) {
												currentPattern.addDetonator(new Detonator(dDelay, o,
														o.getHoleLength() - o.getSubdrill(), dColor1), false);
											} else if (getDetonatorTimeButton2().isSelected()) {
												currentPattern.addDetonator(new Detonator(dDelay, o,
														o.getHoleLength() - o.getSubdrill(), dColor2), false);
											} else if (getDetonatorTimeButton3().isSelected()) {
												currentPattern.addDetonator(new Detonator(dDelay, o,
														o.getHoleLength() - o.getSubdrill(), dColor3), false);
											}

											statusBarLabel.setText(
													"Detonators added = " + dDelay + "ms" + "\tDetonators in List ="
															+ currentPattern.getDetonatorList().size());
										}
									}
								}
							} catch (NumberFormatException e1) {
								statusBarLabel.setText("The detonator delay or ID is not a number");
								e1.printStackTrace();
							} catch (NegativeNumberException e1) {
								statusBarLabel.setText("The detonator delay or ID is a negative number");
								e1.printStackTrace();
							} catch (ZeroArgumentException e1) {
								statusBarLabel.setText("The detonator ID is a zero");
								e1.printStackTrace();
							}

						}
					};
				}
			});

			updateCanvas();
		}
		return toolAddMultiDetonatorButton;
	}

	/**
	 * This method initializes downholeIncrementToggleButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getDetonatorTimeButton1() {
		if (detonatorTimeButton1 == null) {
			detonatorTimeButton1 = new JToggleButton();
			detonatorTimeButton1.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/downhole.png")));
			// downholeIncrementToggleButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			detonatorTimeButton1.setPreferredSize(new Dimension(140, 24));
			detonatorTimeButton1.setText("Det 1");
			dDelay = Integer.parseInt(getDetonatorTimeTextField1().getText());
			dColor1 = getDetonatorTimeTextField1().getBackground();
			// fix below with a check on null - MABEY????
			detonatorTimeButton1.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {

					if (detonatorTimeButton1.isSelected()) {

						dDelay = Integer.parseInt(getDetonatorTimeTextField1().getText());
						dColor1 = getDetonatorTimeTextField1().getBackground();

						detonatorTimeButton2.setSelected(false);
						detonatorTimeButton3.setSelected(false);

					}
				}
			});

		}
		return detonatorTimeButton1;
	}

	/**
	 * This method initializes downholeIncrementTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getDetonatorTimeTextField1() {
		if (detonatorTimeTextField1 == null) {
			detonatorTimeTextField1 = new JTextField();
			detonatorTimeTextField1.setBackground(new Color(160, 120, 180));// Pale
																			// Violet
																			// colour
			detonatorTimeTextField1.setHorizontalAlignment(JTextField.CENTER);
			detonatorTimeTextField1.setPreferredSize(new Dimension(50, 24));
			detonatorTimeTextField1.setText("400");
			detonatorTimeTextField1.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getDetonatorTimeTextField1().getText());
						if (i < 0) {
							return false;
						}
						return true;
					} catch (IllegalArgumentException e) {
						JOptionPane.showMessageDialog(canvasPanel, "Invalid Entry in the Det #1 field. Please Correct.",
								"Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			});
			detonatorTimeTextField1.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					// TODO Auto-generated method stub
					if ((detonatorTimeTextField1.getBackground().getRed()
							+ detonatorTimeTextField1.getBackground().getGreen()
							+ detonatorTimeTextField1.getBackground().getBlue()) / 3 < 130) {
						getDetonatorTimeTextField1().setForeground(Color.white);
					} else
						getDetonatorTimeTextField1().setForeground(Color.black);
				}
			});
		}
		return detonatorTimeTextField1;
	}

	/**
	 * This method initializes toolZMove
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getToolZMove() {
		if (toolZMove == null) {
			toolZMove = new JToggleButton();
			toolZMove.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolZMove.setMaximumSize(dimension30x30);
			toolZMove.setMinimumSize(dimension30x30);
			toolZMove.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie_v2/zMove.png")));
			toolZMove.setToolTipText("Adjust the altitude");
			toolZMove.setPreferredSize(dimension30x30);
			toolZMove.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent k) {
					if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selected = null;
						selectedFIFO = null;
					}
				}

				public void keyReleased(java.awt.event.KeyEvent k) {
					if (!(k.getKeyCode() == KeyEvent.VK_ESCAPE)) {
						selected = null;
						selectedFIFO = null;
					}
				}
			});
			toolZMove.addActionListener(new java.awt.event.ActionListener() { // Create
																				// a
																				// new
																				// listener
																				// that
																				// hears
																				// for
																				// any
																				// action
																				// in
																				// this
																				// case
																				// when
																				// the
																				// button
																				// is
																				// clicked/selected
				public void actionPerformed(java.awt.event.ActionEvent e) { // action
																			// performed
																			// listener
																			// for
																			// listening
																			// to
																			// clicks
																			// in
																			// the
																			// canvas
					currentMouseMover = null; // set as nothing so that
												// the new mouse
												// listener can be
												// applied later in the
												// code
					currentMouseClicker = new MouseAdapter() { // New
																// mouse
																// mover
						boolean clicked = false; // Set the clicked flag
													// to false. change
													// this to allow
													// access to other
													// ifs and else ifs
						Object allSelected = new TreeSet<Object>();

						@Override
						public void mouseReleased(MouseEvent e) {// if
																	// mouse
																	// gets
																	// clicked
																	// do
																	// this
																	// stuff
							if (!clicked) { // if nothing has been
											// selected do this code
								double mouseNorthing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY())
												/ Zoom.getScalingFactor(); // mouse's
																			// X
																			// location
																			// in
																			// the
																			// user
																			// co
																			// ords
								double mouseEasting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor(); // mouse's
																											// Y
																											// location
																											// in
																											// the
																											// user
																											// co
																											// ords
								// double radius =
								// UnitConvert.pixelsToMeters(8)/
								// Zoom.getScalingFactor(); //a radius
								// around the mouse
								Rectangle2D bounds = new Rectangle2D.Double();
								world.addAllCoordinates();
								if (selected == null) {
									selected = world.getFirstObjectIn(new Ellipse2D.Double(mouseEasting - radius,
											mouseNorthing - radius, radius * 2, radius * 2));
									String newValue = JOptionPane.showInputDialog(null, "Input new altitutde",
											"Numeric Values Please.");
									updateCanvas();
									for (Coordinate c : world.getCoordList().values()) {
										boolean isSelected = c == selected || (selected instanceof Collection<?>
												&& ((Collection<?>) selected).contains(c));

										if (isSelected) {
											if (newValue != null || newValue != "") {
												try {
													c.setZ(Double.parseDouble(newValue));
													// updateCanvas();
													selected = null;
													getTreeModelPattern();
												} catch (NumberFormatException e1) {
													// TODO
													// Auto-generated
													// catch block
													e1.printStackTrace();
													JOptionPane.showMessageDialog(null, "Numeric Values Only, Please",
															"Number Format Error", 2);
												}
												// newValue = null;
											}
										}
									}
									clicked = true;
								} else if (selected != null) {
									Set<Coordinate> cSelected = new HashSet<Coordinate>();
									for (Object c : (Collection<Object>) selected) {
										if (c instanceof Coordinate) {
											cSelected.add((Coordinate) c);
										}
									}
									allSelected = cSelected;
									selected = allSelected;
									if (selected instanceof Collection<?>) {
										allSelected = selected;
										String newValue = JOptionPane.showInputDialog(null, "Input new altitutde",
												"500.00");
										for (Coordinate c : (Collection<Coordinate>) allSelected) {
											if (newValue != null || newValue != "") {
												try {
													c.setZ(Double.parseDouble(newValue));
													selected = null;
													allSelected = null;
													getTreeModelPattern();
													updateCanvas();
												} catch (NumberFormatException e1) {
													// TODO
													// Auto-generated
													// catch block
													e1.printStackTrace();
													JOptionPane.showMessageDialog(null, "Numeric Values Only, Please",
															"Number Format Error", 3);
												}
												if (bounds == null || bounds.isEmpty()) {
													bounds = c.getBounds();
												} else {
													bounds = bounds.createUnion(c.getBounds());
												}
											}
										}
										clicked = true;
									}
								}
							}
						}

					};

				}
			});

		}
		return toolZMove;
	}

	/**
	 * This method initializes holeLabel2OnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getHoleLabel2OnOffButton() {
		if (holeLabel2OnOffButton == null) {
			holeLabel2OnOffButton = new JToggleButton();
			holeLabel2OnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holelabel2.png"))));
			holeLabel2OnOffButton.setPreferredSize(new Dimension(22, 22));
			holeLabel2OnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holeLabel3OnOffButton.isSelected()) {
						statusBarLabel.setText("#2 Labels: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("#2 Labels: OFF");
						updateCanvas();
					}
				}
			});
		}
		return holeLabel2OnOffButton;
	}

	/**
	 * This method initializes holeLabel3OnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getHoleLabel3OnOffButton() {
		if (holeLabel3OnOffButton == null) {
			holeLabel3OnOffButton = new JToggleButton();
			holeLabel3OnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/holelabel3.png"))));
			holeLabel3OnOffButton.setPreferredSize(new Dimension(22, 22));
			holeLabel3OnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (holeLabel3OnOffButton.isSelected()) {
						statusBarLabel.setText("#3 Labels: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("#3 Labels: OFF");
						updateCanvas();
					}
				}
			});
		}
		return holeLabel3OnOffButton;
	}

	/**
	 * This method initializes benchHeightOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getBenchHeightOnOffButton() {
		if (benchHeightOnOffButton == null) {
			benchHeightOnOffButton = new JToggleButton();
			benchHeightOnOffButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie/benchheight.png"))));
			benchHeightOnOffButton.setPreferredSize(new Dimension(22, 22));
			benchHeightOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (benchHeightOnOffButton.isSelected()) {
						statusBarLabel.setText("Bench Heights: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Bench Heights: OFF");
						updateCanvas();
					}
				}
			});
		}
		return benchHeightOnOffButton;
	}

	/**
	 * This method initializes patternTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getTreeModelPattern() {

		// These will be nodes that have children
		DefaultMutableTreeNode worldNode = new DefaultMutableTreeNode("World");// Initialized
																				// Tree
																				// so
																				// there
																				// is
																				// no
																				// null
																				// pointers
		DefaultMutableTreeNode patternNode; // declared nodes that I'd like in
											// it
		DefaultMutableTreeNode dummyNode;
		DefaultMutableTreeNode holeNode;// declared nodes that I'd like in it
		// These will be leafs with information
		DefaultMutableTreeNode pNode, burdenNode, spacingNode, orientationNode, rowsNode, holeInRowsNode;
		DefaultMutableTreeNode nNode, eNode, crlNode, l1Node, l2Node, l3Node, dNode, lNode, bhNode, bNode, frlNode,
				trlNode, aNode, sNode, shNode, colorNode;

		patternTree = new JTree(worldNode);// assigned root to Jtree

		patternTreeModel = (DefaultTreeModel) patternTree.getModel();// confirms
																		// TreeModel
																		// being
																		// used
																		// -
																		// probably
																		// superferlous

		// UNCOMMENT FOR CREATING CUSTOM TREE ICONS
		// patternTree.setCellRenderer(new DefaultTreeCellRenderer()
		// {
		//
		// @Override
		// public Component getTreeCellRendererComponent( JTree tree, Object
		// value, boolean sel, boolean expanded, boolean leaf, int row, boolean
		// hasFocus)
		// {
		// super.getTreeCellRendererComponent( tree, value, sel, expanded, leaf,
		// row, hasFocus);
		//
		// setClosedIcon(new
		// ImageIcon(getClass().getResource(("/icons_LiteTie/patternadd.png"))));
		// setOpenIcon(new
		// ImageIcon(getClass().getResource(("/icons_LiteTie/patternNode.png"))));
		// setLeafIcon(new
		// ImageIcon(getClass().getResource(("/icons_LiteTie/holeadd.png"))));
		//
		// return this;
		// }
		// });

		patternTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Pattern tempPattern;
				Hole tempHole;
				Dummy tempDummy;
				DefaultMutableTreeNode tempNode = ((DefaultMutableTreeNode) patternTree.getLastSelectedPathComponent());
				Object tempObject;
				// tempObject = ((LabelledUserObject)
				// tempNode.getUserObject()).getUserObject();
				tempObject = tempNode.getUserObject().toString();
				DefaultMutableTreeNode tempParent = (DefaultMutableTreeNode) tempNode.getParent();

				// SELECTS EVERYTHING IN THE CANVAS
				if (patternTree.getLastSelectedPathComponent().toString().contains("World")) {
					selected = world.getAllObjectsInWorld();
					updateCanvas();
				}

				// if (holeID.startsWith(quotes) && holeID.startsWith(quotes)){
				// holeID = holeID.substring(1, holeID.length() - 1);
				// }
				// SELECTS IN THE CANVAS THE ENTIRE PATTERN
				if (patternTree.getLastSelectedPathComponent().toString().contains("Pattern")) {
					selected = null;
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = ((Pattern) tempObject).getAllDummysAndHoles();// .getAllObjectsInPattern();
					setCurrentPattern(selected);
					updateCanvas();
				}
				// SELECTS IN THE CANVAS THE HOLES IN THAT PATTERN
				if (patternTree.getLastSelectedPathComponent().toString().contains("Hole")) {
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = tempObject;
					updateCanvas();
				}
				// SELECTS IN THE CANVAS THE HOLES IN THAT PATTERN
				if (patternTree.getLastSelectedPathComponent().toString().contains("Dummy")) {
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = tempObject;
					updateCanvas();
				}

				// }

				// TODO modification on any object in the pattern tree
				if (e.getClickCount() == 2 && patternTreeModel.isLeaf(patternTree.getLastSelectedPathComponent())) {

					if (patternTree.getLastSelectedPathComponent().toString().contains("Hole")) {
						// selected = tempObject;
						// patTree.validate();
						// updateCanvas();
					}

					try {

						if (((LabelledUserObject) tempParent.getUserObject()).getUserObject() instanceof Pattern
								&& (!(((LabelledUserObject) tempParent.getUserObject())
										.getUserObject() instanceof Dummy))) {
							tempPattern = ((Pattern) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());
							selected = tempPattern;
							updateCanvas();
							System.out.println("selected " + tempPattern.getPatternName());

							// PATTERN NAME
							if (patternTree.getLastSelectedPathComponent().toString().contains("Name")) {
								// FIXME
								// InputDialog input = new InputDialog(null,
								// "Pattern Name",
								// "Enter Pattern Name",((String)
								// tempPattern.getPatternName()) );
								// String newValue = input.getText();

								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new Pattern Name",
										((String) tempPattern.getPatternName()));
								// this below checks to find an = sign and then
								// performs a calculation.
								if (newValue.startsWith("=")) {
									try {
										newValue = calculateValueFromString(newValue);
										tempObject = (Object) newValue;
										tempNode.setUserObject(
												new LabelledUserObject("Name = " + tempObject, tempObject));
										tempPattern.setPatternName(newValue);
										((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
										patternTree.validate();
										updateCanvas();
									} catch (UnknownFunctionException | UnparsableExpressionException e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().getName(),
												JOptionPane.ERROR_MESSAGE);
										e1.printStackTrace();
									}
								}
								// Null Check and then a calculation
								if (newValue != null || newValue != "" || newValue != " ") {
									tempObject = (Object) newValue;
									tempNode.setUserObject(new LabelledUserObject("Name = " + tempObject, tempObject));
									newValue = (String) ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
									tempPattern.setPatternName(newValue);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									patternTree.validate();
									updateCanvas();
									// getPatTree();
								}
								if (newValue == null || newValue == "" || newValue == " ") {
									tempNode.setUserObject(
											new LabelledUserObject("Name = Pattern#" + tempPattern.getPatternID(),
													tempPattern.getPatternID()));
								}
							}

							// //BURDEN
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Burden")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new burden value",
										Double.toString((Double) tempPattern.getBurden()));
								if (newValue.startsWith("=")) {
									try {
										newValue = calculateValueFromString(newValue);
										tempObject = (Object) Double.parseDouble(newValue);
										tempNode.setUserObject(new LabelledUserObject(
												"Burden = " + decimalFormat2.format(tempObject) + "m", tempObject));
										newValue = Double
												.toString((Double) ((LabelledUserObject) tempNode.getUserObject())
														.getUserObject());
										double burden = Double.valueOf(newValue);
										tempPattern.setBurden(burden);
										((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
										patternTree.validate(); // redraws the
																// Jtree this
																// means
																// everything
																// collapses.
										updateCanvas(); // Redraws the canvas to
														// show the updated
														// location of the moved
														// object.
									} catch (UnknownFunctionException | UnparsableExpressionException e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().getName(),
												JOptionPane.ERROR_MESSAGE);
										e1.printStackTrace();
									}
								}
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {

									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Burden = " + decimalFormat2.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double burden = Double.valueOf(newValue);
									tempPattern.setBurden(burden);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									patternTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								} else if (tempObject instanceof Pattern && !(tempObject instanceof Hole)
										&& !(tempObject instanceof Dummy))
									tempNode.setUserObject(new LabelledUserObject(
											"Burden = " + decimalFormat1.format(tempPattern.getBurden()) + "m",
											tempPattern.getBurden()));

							}
							// //SPACING
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Spacing")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new spacing value",
										Double.toString((Double) tempPattern.getSpacing()));
								if (newValue.startsWith("=")) {
									try {
										newValue = calculateValueFromString(newValue);
										tempObject = (Object) Double.parseDouble(newValue);
										tempNode.setUserObject(new LabelledUserObject(
												"Spacing = " + decimalFormat2.format(tempObject) + "m", tempObject));
										newValue = Double
												.toString((Double) ((LabelledUserObject) tempNode.getUserObject())
														.getUserObject());
										double spacing = Double.valueOf(newValue);
										tempPattern.setSpacing(spacing);
										((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
										patternTree.validate(); // redraws the
																// Jtree this
																// means
																// everything
																// collapses.
										updateCanvas(); // Redraws the canvas to
														// show the updated
														// location of the moved
														// object.
									} catch (UnknownFunctionException | UnparsableExpressionException e1) {
										JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().getName(),
												JOptionPane.ERROR_MESSAGE);
										e1.printStackTrace();
									}
								}
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Spacing = " + decimalFormat1.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double spacing = Double.valueOf(newValue);
									tempPattern.setSpacing(spacing);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									patternTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								} else if (tempObject instanceof Pattern && !(tempObject instanceof Hole)
										&& !(tempObject instanceof Dummy))
									tempNode.setUserObject(new LabelledUserObject(
											"Spacing = " + decimalFormat1.format(tempPattern.getSpacing()) + "m",
											tempPattern.getSpacing()));
							}
						}

						// DUMMY OPTIONS
						if (((LabelledUserObject) tempParent.getUserObject()).getUserObject() instanceof Dummy
								&& (!(((LabelledUserObject) tempParent.getUserObject())
										.getUserObject() instanceof Hole))) {
							tempDummy = ((Dummy) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());

							selected = tempDummy;
							updateCanvas();
							System.out.println("selected" + tempDummy.getHoleID());

							// NORTHING
							if (patternTree.getLastSelectedPathComponent().toString().contains("Northing")
									&& !(tempObject instanceof Hole)) {

								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new northing value",
										Double.toString((Double) tempObject));

								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									System.out.println("in northing1");
									tempObject = (Object) Double.parseDouble(newValue);
									System.out.println("in northing2");
									tempNode.setUserObject(new LabelledUserObject(
											"Northing = " + decimalFormat4.format(tempObject) + "mN", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double northing = Double.valueOf(newValue);
									tempDummy.setX(northing);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									patternTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								} else if (tempObject instanceof Dummy && !(tempObject instanceof Hole))
									tempNode.setUserObject(new LabelledUserObject(
											"Northing = " + decimalFormat4.format(tempDummy.getY()) + "mN",
											tempDummy.getY()));

							}
							// EASTING
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Easting")
									&& !(tempObject instanceof Hole)) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new easting value",
										Double.toString((Double) tempObject));
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Easting = " + decimalFormat4.format(tempObject) + "mE", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double easting = Double.valueOf(newValue);
									tempDummy.setY(easting);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// patTree.validate();
								} else if (tempObject instanceof Dummy && !(tempObject instanceof Hole))
									tempNode.setUserObject(new LabelledUserObject(
											"Easting = " + decimalFormat4.format(tempDummy.getX()) + "mE",
											tempDummy.getX()));

							}
							// COLLAR RL
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Collar level")
									&& !(tempObject instanceof Hole)) {
								String newValue = JOptionPane.showInputDialog(canvasPanel,
										"Input new collar level value", Double.toString((Double) tempObject));
								if (newValue != null || newValue != "") {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Collar Level = " + decimalFormat1.format(tempObject) + "rL", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double collarRL = Double.valueOf(newValue);
									tempDummy.setZ(collarRL);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// patTree.validate();
								} else if (tempObject instanceof Dummy && !(tempObject instanceof Hole))
									tempNode.setUserObject(new LabelledUserObject(
											"Collar Level = " + decimalFormat1.format(tempObject) + "rL",
											tempDummy.getZ()));
							}

						}

						// HOLE OPTIONS
						if (((LabelledUserObject) tempParent.getUserObject()).getUserObject() instanceof Hole) {
							tempHole = ((Hole) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());

							selected = tempHole;
							updateCanvas();

							// NORTHING
							if (patternTree.getLastSelectedPathComponent().toString().contains("Northing")) {
								System.out.println("into the northing code");
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new northing value",
										Double.toString((Double) tempHole.getY()));
								System.out.println("showing the option pane");
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Northing = " + decimalFormat4.format(tempObject) + "mN", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double northing = Double.valueOf(newValue);
									tempHole.setX(northing);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else if (tempObject instanceof Hole)
									tempNode.setUserObject(new LabelledUserObject(
											"Northing = " + decimalFormat4.format(tempHole.getY()) + "mN",
											tempHole.getY()));

							}
							// EASTING
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Easting")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new easting value",
										Double.toString((Double) tempHole.getX()));
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Easting = " + decimalFormat4.format(tempObject) + "mE", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double easting = Double.valueOf(newValue);
									tempHole.setY(easting);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else if (tempObject instanceof Hole)
									tempNode.setUserObject(new LabelledUserObject(
											"Easting = " + decimalFormat4.format(tempHole.getX()) + "mE",
											tempHole.getX()));

							}

							// COLLAR RL
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Collar level")
									&& !(tempObject instanceof Dummy)) {
								String newValue = JOptionPane.showInputDialog(canvasPanel,
										"Input new collar level value", Double.toString((Double) tempHole.getZ()));
								if (newValue != null
										|| newValue != "" && Double.valueOf(newValue) > tempHole.getFloorRL()) {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Collar level = " + decimalFormat1.format(tempObject) + "rL", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());

									double collarRL = Double.valueOf(newValue);
									tempHole.setZ(collarRL);
									tempHole.setHoleLength((tempHole.getZ() - tempHole.getToeRL())
											/ Math.sin(Math.toRadians(tempHole.getAngle())));
									tempHole.setBench(collarRL - tempHole.getFloorRL());
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();

								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Collar level = " + decimalFormat1.format(tempHole.getZ()) + "rL",
											tempHole.getZ()));
							}
							// LABEL 1
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Label #1")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new label",
										((String) tempHole.getLabelOne()));
								if (newValue != null || newValue != "") {
									tempObject = (Object) newValue;
									tempNode.setUserObject(
											new LabelledUserObject("Label #1 = " + tempObject, tempObject));
									newValue = (String) ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
									tempHole.setLabelOne(newValue);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);

									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Label #1 = " + tempHole.getLabelOne(), tempHole.getLabelOne()));
							}
							// LABEL2
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Label #2")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new label",
										((String) tempHole.getLabelTwo()));
								if (newValue != null || newValue != "") {

									tempObject = (Object) newValue;
									tempNode.setUserObject(
											new LabelledUserObject("Label #2 = " + tempObject, tempObject));
									newValue = (String) ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
									tempHole.setLabelTwo(newValue);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Label #2 = " + tempHole.getLabelTwo(), tempHole.getLabelTwo()));
							}
							// LABEL3
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Label #3")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new label",
										((String) tempHole.getLabelThree()));
								if (newValue != null || newValue != "") {
									tempObject = (Object) newValue;
									tempNode.setUserObject(
											new LabelledUserObject("Label #3 = " + tempObject, tempObject));
									newValue = (String) ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
									tempHole.setLabelThree(newValue);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Label #3 = " + tempHole.getLabelThree(), tempHole.getLabelThree()));
							}
							// DIAMETER
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Diameter")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new diameter value",
										Double.toString((Double) tempHole.getDiameter()));
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Diameter = " + decimalFormat1.format(tempObject) + "mm", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double diameter = Double.valueOf(newValue);
									tempHole.setDiameter(diameter);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Diameter = " + decimalFormat1.format(tempHole.getDiameter()) + "mm",
											tempHole.getDiameter()));

							}
							// HOLE LENGTH
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Length")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel,
										"Input new hole length value",
										Double.toString((Double) tempHole.getHoleLength()));
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Length = " + decimalFormat1.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double length = Double.valueOf(newValue);

									tempHole.setHoleLength(length);
									tempHole.setSubdrill(
											(tempHole.getHoleLength() * Math.sin(Math.toRadians(tempHole.getAngle())))
													- tempHole.getBench());
									tempHole.setToeRL(tempHole.getFloorRL() - tempHole.getSubdrill());
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Length = " + decimalFormat1.format(tempHole.getHoleLength()) + "m",
											tempHole.getHoleLength()));
							}
							// BENCH HEIGHT
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Bench height")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new value",
										Double.toString((Double) tempHole.getBench()));
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Bench height = " + decimalFormat1.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double bench = Double.valueOf(newValue);
									tempHole.setBench(bench);
									tempHole.setFloorRL(tempHole.getZ() - tempHole.getBench());

									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Bench height = " + decimalFormat1.format(tempHole.getBench()) + "m",
											tempHole.getBench()));

							}
							// HOLE BEARING
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Bearing")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new value",
										Double.toString((Double) tempHole.getBearing()));
								if (newValue != null || newValue != "" && Double.valueOf(newValue) >= 360
										&& Double.valueOf(newValue) <= -360) {

									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Bearing = " + decimalFormat1.format(tempObject) + "\u00b0", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double bearing = Double.valueOf(newValue);
									tempHole.setBearing(bearing);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								}

								else
									tempNode.setUserObject(new LabelledUserObject(
											"Bearing = " + decimalFormat1.format(tempHole.getBearing()) + "\u00b0",
											tempHole.getBearing()));

							}
							// FLOOR RL
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Floor level")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new value",
										Double.toString((Double) tempHole.getFloorRL()));
								if (newValue != null
										|| newValue != "" && (Double.valueOf(newValue) < tempHole.getZ())) {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Floor level = " + decimalFormat1.format(tempObject) + "rL", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());

									double floorRL = Double.valueOf(newValue);
									tempHole.setFloorRL(floorRL);
									tempHole.setBench(tempHole.getZ() - tempHole.getFloorRL());
									tempHole.setSubdrill(tempHole.getFloorRL() - tempHole.getToeRL());

									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Floor level = " + decimalFormat1.format(tempHole.getFloorRL()) + "rL",
											tempHole.getFloorRL()));
							}
							// TOE
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Toe level")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new value",
										Double.toString((Double) tempHole.getToeRL()));

								if (newValue != null
										|| newValue != "" && (Double.valueOf(newValue) < tempHole.getZ())) {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Toe level = " + decimalFormat1.format(tempObject) + "rL", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double toeRL = Double.valueOf(newValue);
									tempHole.setToeRL(toeRL);
									tempHole.setHoleLength((tempHole.getBench() + tempHole.getSubdrill())
											/ Math.sin(Math.toRadians(tempHole.getAngle())));
									tempHole.setSubdrill(tempHole.getFloorRL() - tempHole.getToeRL());
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Toe level = " + decimalFormat1.format(tempHole.getToeRL()) + "rL",
											tempHole.getToeRL()));
							}
							// ANGLE
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Angle")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new hole angle value",
										Double.toString((Double) tempHole.getAngle()));

								if (newValue != null && Double.valueOf(newValue) < 20 && Double.valueOf(newValue) > 90
										|| newValue != "") {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Angle = " + decimalFormat1.format(tempObject) + "\u00b0", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double angle = Double.valueOf(newValue);
									tempHole.setAngle(angle);
									tempHole.setHoleLength((tempHole.getZ() - tempHole.getToeRL())
											/ Math.sin(Math.toRadians(tempHole.getAngle())));
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();
								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Angle = " + decimalFormat1.format(tempHole.getAngle()) + "\u00b0",
											tempHole.getAngle()));

							}
							// SUBDRILL
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Subdrill")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new subdrill value",
										Double.toString((Double) tempHole.getSubdrill()));

								if (newValue != null || newValue != "" && (Double
										.valueOf(newValue) <= (tempHole.getBench() - (2 * tempHole.getBench())))) {

									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Subdrill = " + decimalFormat1.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());

									double subdrill = Double.valueOf(newValue);
									tempHole.setSubdrill(subdrill);
									tempHole.setToeRL(tempHole.getFloorRL() - tempHole.getSubdrill());
									tempHole.setHoleLength((tempHole.getBench() + tempHole.getSubdrill())
											/ Math.sin(Math.toRadians(tempHole.getAngle())));

									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// getPatTree();

								} else
									tempNode.setUserObject(new LabelledUserObject(
											"Subdrill = " + decimalFormat1.format(tempHole.getSubdrill()) + "m",
											tempHole.getSubdrill()));

							}
							// SHAPE
							else if (patternTree.getLastSelectedPathComponent().toString().contains("Shape")) {
								// ADD MORE WHEN THE DRAWING METHODS ARE
								// COMPLETED
								Object[] options = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
								String newValue = (String) JOptionPane.showInputDialog(canvasPanel,
										"Select Hole Shape.\n1 = Circle,2 = Cross,\n" + "3 = Triangle, 4 = Square\n"
												+ "5 = Pentagon, 6 = Hexagon\n" + "7 = Star, 8 = Fill Circle\n"
												+ "9 = Fill Triangle, 10 = Fill Square",
										"Hole Shape",
										JOptionPane.QUESTION_MESSAGE, liteTieIcon, options,
										Integer.toString(tempHole.getShape()));

								// String newValue =
								// JOptionPane.showInputDialog(canvasPanel,
								// "Input new value.\n1=Circle,2=Cross,3=Triangle\n4=Square,5=... ",
								// (Integer.toString(tempHole.getShape())));

								if (newValue != null || newValue != "" && Integer.valueOf(newValue) < 10
										&& Integer.valueOf(newValue) > 0) {

									tempObject = (Object) Integer.parseInt(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Shape = " + decimalFormat0.format(tempObject) + " -  " + tempObject,
											tempObject));
									newValue = Integer.toString(
											(Integer) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									int shape = Integer.valueOf(newValue);
									tempHole.setShape(shape);
									tempHole.setShapeLabel(shape);
									tempNode.setUserObject(
											new LabelledUserObject("Shape = " + decimalFormat0.format(tempObject)
													+ " -  " + tempHole.getShapeType(), tempObject));
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);

									updateCanvas();
									// getPatTree();
								}

								else
									tempNode.setUserObject(new LabelledUserObject(
											"Shape = " + decimalFormat0.format(tempHole.getShape()) + " - "
													+ tempHole.getShapeType(),
											tempHole.getShape()));

							}
							if (patternTree.getLastSelectedPathComponent().toString().contains("Colour")
									&& !(tempObject instanceof Hole)) {
								// Component color = null;

								Color newValue = JColorChooser.showDialog(canvasPanel, "Change Current Colour",
										tempHole.getColor());

								if (newValue != null) {

									tempObject = (Color) newValue;

									tempNode.setUserObject(
											new LabelledUserObject("Colour = " + (tempObject), tempObject));
									newValue = (Color) (((LabelledUserObject) tempNode.getUserObject())
											.getUserObject());
									tempHole.setColor(newValue);
									((DefaultTreeModel) patternTree.getModel()).nodeChanged(tempNode);
									patternTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								}

								else if (tempObject instanceof Boundary)
									tempNode.setUserObject(new LabelledUserObject("Colour = " + tempHole.getColor(),
											tempHole.getColor()));

							} else
								System.out.println("There is an error in the JTree - PatTree - MouseListener");
						}
					}

					catch (NumberFormatException nf) {
						JOptionPane.showMessageDialog(patternTree, "Invalid... number format");
						getTreeModelPattern();

					} catch (NegativeNumberException nn) {
						JOptionPane.showMessageDialog(patternTree, "Invalid... negative value");
						getTreeModelPattern();
					} catch (ZeroArgumentException za) {
						JOptionPane.showMessageDialog(patternTree, "Invalid... zero value");
						getTreeModelPattern();
					} catch (IllegalArgumentException ia) {
						JOptionPane.showMessageDialog(patternTree, "Invalid... value not accepted");
						getTreeModelPattern();
					}
				}
			}
		});

		// Status Text to indicate which node is selected
		patternTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
				// DefaultMutableTreeNode node = new
				// DefaultMutableTreeNode();
				TreePath tp = e.getNewLeadSelectionPath();

				if (tp.getLastPathComponent() != null) {

					statusBarLabel.setText("Node in Environment Tree Selected: " + tp.getLastPathComponent());
					selected = tp.getLastPathComponent();
				}

			}// throws a Null Pointer if a selected node is then
				// collapsed will need to fix - eventually
		});

		if (world.getPatternList().size() > 0) {// if the World has a pattern in
												// it
			for (Pattern tempPat : world.getPatternList().values()) {// For
																		// every
																		// pattern
																		// in
																		// the
																		// world
																		// do
																		// the
																		// next
																		// bit

				// Below initialize the patternNode - current pattern index is
				// starts at 0 so had to add 1 to it

				patternNode = new DefaultMutableTreeNode(new LabelledUserObject(
						"Pattern " + (tempPat.getPatternID()) + "/ " + tempPat.getPatternName() + "/ "
								+ tempPat.getNumberOfHoles() + " holes.",
						tempPat));
				// you have to insert the node
				patternTreeModel.insertNodeInto((MutableTreeNode) patternNode,
						(MutableTreeNode) patternTreeModel.getRoot(), worldNode.getChildCount());
				pNode = new DefaultMutableTreeNode(
						new LabelledUserObject("Name = " + tempPat.getPatternName(), tempPat));
				burdenNode = new DefaultMutableTreeNode(new LabelledUserObject(
						"Burden = " + decimalFormat1.format((tempPat.getBurden())) + "m", tempPat.getBurden()));
				spacingNode = new DefaultMutableTreeNode(new LabelledUserObject(
						"Spacing = " + decimalFormat1.format((tempPat.getSpacing())) + "m", tempPat.getSpacing()));
				patternTreeModel.insertNodeInto((MutableTreeNode) pNode, (MutableTreeNode) patternNode,
						patternNode.getChildCount());
				patternTreeModel.insertNodeInto((MutableTreeNode) burdenNode, (MutableTreeNode) patternNode,
						patternNode.getChildCount());
				patternTreeModel.insertNodeInto((MutableTreeNode) spacingNode, (MutableTreeNode) patternNode,
						patternNode.getChildCount());

				for (Dummy tempDummy : tempPat.getDummyList().values()) {// for
																			// every
																			// dummy
																			// in
																			// the
																			// current
																			// pattern
					if (tempPat.getDummyList().size() > 0 && tempPat.getDummyList().size() < 5000) {// NEED
																									// TO
																									// FIX
																									// this
																									// so
																									// the
																									// tree
																									// listens
																									// to
																									// if
																									// the
																									// node
																									// is
																									// expanded
																									// or
																									// not

						if (tempDummy instanceof Dummy && (!(tempDummy instanceof Hole))) {// if
																							// it
																							// is
																							// a
																							// dummy
																							// do
																							// this
							dummyNode = new DefaultMutableTreeNode(
									new LabelledUserObject("Dummy ID " + (tempDummy.getHoleID()), tempDummy));
							nNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Northing = " + decimalFormat4.format((tempDummy.getY())) + "mN",
									tempDummy.getY()));
							eNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Easting = " + decimalFormat4.format((tempDummy.getX())) + "mE", tempDummy.getX()));
							crlNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Collar level = " + decimalFormat1.format((tempDummy.getZ())) + "rL",
									tempDummy.getZ()));

							patternTreeModel.insertNodeInto((MutableTreeNode) dummyNode, (MutableTreeNode) patternNode,
									patternNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) nNode, (MutableTreeNode) dummyNode,
									dummyNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) eNode, (MutableTreeNode) dummyNode,
									dummyNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) crlNode, (MutableTreeNode) dummyNode,
									dummyNode.getChildCount());
						}
					}
				}
				for (Hole tempHole : tempPat.getHoleList().values()) {// for
																		// every
																		// hole
																		// in
																		// the
																		// current
																		// pattern

					if (tempPat.getHoleList().size() > 0 && tempPat.getHoleList().size() < 5000) {// NEED
																									// TO
																									// FIX
																									// this
																									// so
																									// the
																									// tree
																									// listens
																									// to
																									// if
																									// the
																									// node
																									// is
																									// expanded
																									// or
																									// not
						if ((Hole) tempHole instanceof Hole) {// if it is a hole
																// do this bit

							holeNode = new DefaultMutableTreeNode(
									new LabelledUserObject("Hole ID " + (tempHole.getHoleID()), tempHole));// initialized
																											// holeNode
							nNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Northing = " + decimalFormat4.format((tempHole.getY())) + "mN", tempHole.getY()));
							eNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Easting = " + decimalFormat4.format((tempHole.getX())) + "mE", tempHole.getX()));
							crlNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Collar level = " + decimalFormat1.format((tempHole.getZ())) + "rL",
									tempHole.getZ()));
							l1Node = new DefaultMutableTreeNode(new LabelledUserObject(
									"Label #1 = " + (tempHole.getLabelOne()), tempHole.getLabelOne()));
							l2Node = new DefaultMutableTreeNode(new LabelledUserObject(
									"Label #2 = " + (tempHole.getLabelTwo()), tempHole.getLabelTwo()));
							l3Node = new DefaultMutableTreeNode(new LabelledUserObject(
									"Label #3 = " + (tempHole.getLabelThree()), tempHole.getLabelThree()));
							dNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Diameter = " + (tempHole.getDiameter()) + "mm", tempHole.getDiameter()));
							lNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Length = " + decimalFormat1.format((tempHole.getHoleLength())) + "m",
									tempHole.getHoleLength()));
							bhNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Bench height = " + decimalFormat1.format((tempHole.getBench())) + "m",
									tempHole.getBench()));
							bNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Bearing = " + decimalFormat1.format((tempHole.getBearing())) + "\u00b0",
									tempHole.getBearing()));
							frlNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Floor level = " + decimalFormat1.format((tempHole.getFloorRL())) + "rL",
									tempHole.getFloorRL()));
							trlNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Toe level = " + decimalFormat1.format((tempHole.getToeRL())) + "rL",
									tempHole.getToeRL()));
							aNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Angle = " + decimalFormat1.format((tempHole.getAngle())) + "\u00b0",
									tempHole.getAngle()));
							sNode = new DefaultMutableTreeNode(
									new LabelledUserObject(
											"Subdrill = " + decimalFormat1.format((tempHole.getSubdrill())) + "m",
											tempHole.getSubdrill()));
							shNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Shape = " + decimalFormat0.format((tempHole.getShape())) + " - "
											+ tempHole.getShapeType(),
									tempHole.getShape()));
							colorNode = new DefaultMutableTreeNode(
									new LabelledUserObject("Colour = " + tempHole.getColor(), tempHole.getColor()));
							// You have to insert the node
							patternTreeModel.insertNodeInto((MutableTreeNode) holeNode, (MutableTreeNode) patternNode,
									patternNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) nNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) eNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) crlNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) l1Node, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) l2Node, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) l3Node, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) dNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) lNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) bhNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) bNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) frlNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) trlNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) aNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) sNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) shNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
							patternTreeModel.insertNodeInto((MutableTreeNode) colorNode, (MutableTreeNode) holeNode,
									holeNode.getChildCount());
						}

					}

				}

			}
		} else {
			patternTreeModel.setRoot(worldNode);// if there is no pattern in the
												// pattern list do this

		}

		getPatternLayersScrollPane().setViewportView(patternTree);// displays
																	// the JTree

		return patternTree;

	}

	/**
	 * This method initializes boundaryTree
	 * 
	 * @return javax.swing.JTree
	 */
	private JTree getTreeModelBoundary() {

		// These will be nodes that have children
		DefaultMutableTreeNode worldNode = new DefaultMutableTreeNode("World");// Initialized
																				// Tree
																				// so
																				// there
																				// is
																				// no
																				// null
																				// pointers
		DefaultMutableTreeNode boundaryNode; // declared nodes that I'd like in
												// it
		DefaultMutableTreeNode pointNode;
		DefaultMutableTreeNode nNode, eNode, crlNode, arrowNode, annotateNode, closedNode, colorNode, colorFillNode,
				strokeNode;

		boundaryTree = new JTree(worldNode);// assigned root to Jtree

		boundaryTreeModel = (DefaultTreeModel) boundaryTree.getModel();// confirms
																		// TreeModel
																		// being
																		// used
																		// -
																		// probably
																		// superferlous

		boundaryTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				Coordinate c;
				Boundary b = null;
				BPoint bp;
				DefaultMutableTreeNode tempNode = ((DefaultMutableTreeNode) boundaryTree
						.getLastSelectedPathComponent());
				Object tempObject;
				// tempObject = ((LabelledUserObject)
				// tempNode.getUserObject()).getUserObject();
				tempObject = tempNode.getUserObject().toString();
				DefaultMutableTreeNode tempParent = (DefaultMutableTreeNode) tempNode.getParent();

				// SELECTS EVERYTHING IN THE CANVAS
				if (boundaryTree.getLastSelectedPathComponent().toString().contains("World")) {
					selected = world.getAllObjectsInWorld();
					updateCanvas();
				}

				// if (holeID.startsWith(quotes) && holeID.startsWith(quotes)){
				// holeID = holeID.substring(1, holeID.length() - 1);
				// }
				// SELECTS IN THE CANVAS THE ENTIRE PATTERN
				if (boundaryTree.getLastSelectedPathComponent().toString().contains("Boundary")) {
					selected = null;
					updateCanvas();
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = ((Boundary) tempObject).getAllBPointsInBoundary();// .getAllDummysAndHoles();//.getAllObjectsInPattern();
					updateCanvas();
				}
				// SELECTS IN THE CANVAS THE HOLES IN THAT PATTERN
				if (boundaryTree.getLastSelectedPathComponent().toString().contains("Point")) {
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = tempObject;
					updateCanvas();
				}

				// TODO modification on any object in the pattern tree
				if (e.getClickCount() == 2 && boundaryTreeModel.isLeaf(boundaryTree.getLastSelectedPathComponent())) {
					try {
						if (((LabelledUserObject) tempParent.getUserObject()).getUserObject() instanceof Boundary) {
							b = ((Boundary) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());
						}
						selected = b;
						updateCanvas();
						// System.out.println("selected - Boundary #" +
						// b.getBoundaryID());

						if (boundaryTree.getLastSelectedPathComponent().toString().contains("Arrow")
								&& !(tempObject instanceof Boundary)) {

							Object[] options = { "0", "1", "2" };
							String newValue = (String) JOptionPane.showInputDialog(canvasPanel,
									"Input new Arrow Type\n0 = No Arrows,\n1 = Single Arrow,\n2 = Double Arrows",
									"Arrow Type", JOptionPane.QUESTION_MESSAGE, liteTieIcon, options,
									String.valueOf(b.getArrow()));

							if (newValue != null && newValue != "" && Integer.parseInt(newValue) == 0) {

								tempObject = (Object) Integer.parseInt(newValue);

								tempNode.setUserObject(new LabelledUserObject(
										"Arrow Type = " + decimalFormat0.format(tempObject) + " (No Arrows)",
										tempObject));
								newValue = Integer.toString(
										(Integer) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								int arrowType = Integer.valueOf(newValue);
								b.setArrow(arrowType);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							} else if (newValue != null && newValue != "" && Integer.parseInt(newValue) == 1) {

								tempObject = (Object) Integer.parseInt(newValue);

								tempNode.setUserObject(new LabelledUserObject(
										"Arrow Type = " + decimalFormat0.format(tempObject) + " (Single Arrow)",
										tempObject));
								newValue = Integer.toString(
										(Integer) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								int arrowType = Integer.valueOf(newValue);
								b.setArrow(arrowType);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							}

							else if (newValue != null && newValue != "" && Integer.parseInt(newValue) == 2) {

								tempObject = (Object) Integer.parseInt(newValue);

								tempNode.setUserObject(new LabelledUserObject(
										"Arrow Type = " + decimalFormat0.format(tempObject) + " (Double Arrow)",
										tempObject));
								newValue = Integer.toString(
										(Integer) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								int arrowType = Integer.valueOf(newValue);
								b.setArrow(arrowType);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							} else if (tempObject instanceof Boundary) {
								if (b.getArrow() == 0)
									tempNode.setUserObject(new LabelledUserObject(
											"Arrow Type = " + decimalFormat0.format(b.getArrow()) + " (No Arrow)",
											b.getArrow()));
								if (b.getArrow() == 1)
									tempNode.setUserObject(new LabelledUserObject(
											"Arrow Type = " + decimalFormat0.format(b.getArrow()) + " (Single Arrow)",
											b.getArrow()));
								if (b.getArrow() == 2)
									tempNode.setUserObject(new LabelledUserObject(
											"Arrow Type = " + decimalFormat0.format(b.getArrow()) + " (Double Arrow)",
											b.getArrow()));
							}
						}
						if (boundaryTree.getLastSelectedPathComponent().toString().contains("Annotated")
								&& !(tempObject instanceof Boundary)) {

							// String newValue =
							// JOptionPane.showInputDialog(boundaryTree,
							// "Is Annotated?",
							// String.valueOf(b.getAnnotated()));
							Object[] options = { "True", "False" };
							String newValue = (String) JOptionPane.showInputDialog(canvasPanel,
									"Is Annotated?\nTrue = Dimension Label,\nFalse = No Dimension Label",
									"Distance Annotation", JOptionPane.QUESTION_MESSAGE, liteTieIcon, options,
									String.valueOf(b.getAnnotated()));

							if (newValue != null || newValue != "" && newValue == "true" || newValue == "True") {

								tempObject = (Object) String.valueOf(newValue).toLowerCase();

								tempNode.setUserObject(
										new LabelledUserObject("Is Annotated = " + (tempObject), tempObject));
								newValue = (String) (((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								boolean annotated = Boolean.parseBoolean(newValue);
								b.setAnnotated(annotated);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							}
							if (newValue != null || newValue != "" && newValue == "false" || newValue == "False") {

								tempObject = (Object) String.valueOf(newValue).toLowerCase();

								tempNode.setUserObject(
										new LabelledUserObject("Is Annotated = " + (tempObject), tempObject));
								newValue = (String) (((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								boolean annotated = Boolean.parseBoolean(newValue);
								b.setAnnotated(annotated);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							} else if (tempObject instanceof Boundary)
								tempNode.setUserObject(
										new LabelledUserObject("Is Annotated = " + b.getAnnotated(), b.getAnnotated()));

						}
						if (boundaryTree.getLastSelectedPathComponent().toString().contains("Closed")
								&& !(tempObject instanceof Boundary)) {
							Object[] options = { "True", "False" };
							// String newValue =
							// JOptionPane.showInputDialog(boundaryTree,
							// "Is Closed?", String.valueOf(b.getAnnotated()));
							String newValue = (String) JOptionPane.showInputDialog(canvasPanel,
									"Is Closed?\nTrue = Closed,\nFalse = Open", "Polyline or Polygon",
									JOptionPane.QUESTION_MESSAGE, liteTieIcon, options,
									String.valueOf(b.getAnnotated()));

							if (newValue != null || newValue != "" && newValue == "true" || newValue == "True") {

								tempObject = (Object) String.valueOf(newValue).toLowerCase();

								tempNode.setUserObject(
										new LabelledUserObject("Is Closed = " + (tempObject), tempObject));
								newValue = (String) (((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								boolean closed = Boolean.parseBoolean(newValue);
								b.setClosed(closed);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							}
							if (newValue != null || newValue != "" && newValue == "false" || newValue == "False") {

								tempObject = (Object) String.valueOf(newValue).toLowerCase();

								tempNode.setUserObject(
										new LabelledUserObject("Is Closed = " + (tempObject), tempObject));
								newValue = (String) (((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								boolean closed = Boolean.parseBoolean(newValue);
								b.setClosed(closed);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							} else if (tempObject instanceof Boundary)
								tempNode.setUserObject(
										new LabelledUserObject("Is Closed = " + b.getClosed(), b.getClosed()));

						}
						if (boundaryTree.getLastSelectedPathComponent().toString().contains("Stroke Colour")
								&& !(tempObject instanceof Boundary)) {
							Color newValue = JColorChooser.showDialog(canvasPanel, "Change Colour", b.getColor());

							if (newValue != null) {

								tempObject = (Color) newValue;

								tempNode.setUserObject(
										new LabelledUserObject("Stroke Colour = " + (tempObject), tempObject));
								newValue = (Color) (((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								b.setColor(newValue);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							}

							else if (tempObject instanceof Boundary)
								tempNode.setUserObject(
										new LabelledUserObject("Stroke Colour = " + b.getColor(), b.getColor()));

						}
						if (boundaryTree.getLastSelectedPathComponent().toString().contains("Fill Colour")
								&& !(tempObject instanceof Boundary)) {

							Color newValue = JColorChooser.showDialog(canvasPanel, "Change Colour", b.getFillColor());
							if (newValue != null) {

								tempObject = (Color) newValue;

								tempNode.setUserObject(
										new LabelledUserObject("Fill Colour = " + (tempObject), tempObject));
								newValue = (Color) (((LabelledUserObject) tempNode.getUserObject()).getUserObject());
								b.setFillColor(newValue);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							}

							else if (tempObject instanceof Boundary)
								tempNode.setUserObject(
										new LabelledUserObject("Fill Colour = " + b.getFillColor(), b.getFillColor()));

						}
						if (boundaryTree.getLastSelectedPathComponent().toString().contains("Stroke Style")
								&& !(tempObject instanceof Boundary)) {

							// String newValue =
							// JOptionPane.showInputDialog(boundaryTree,
							// "Input new Dash Length",
							// String.valueOf(b.getStroke().getDashArray()));
							// String newValue2 =
							// JOptionPane.showInputDialog(boundaryTree,
							// "Input new Space Length",
							// String.valueOf(b.getStroke().getDashArray()));
							// BasicStroke newValue =
							// StrokeChooserPanel.showBox(canvasPanel,
							// (int)b.getStroke().getLineWidth(),1,1);
							float[] dashPattern = { 0, 0 };
							if (b.getStroke().getDashArray() != null) {
								dashPattern = b.getStroke().getDashArray();
							}

							int dash = (int) dashPattern[0];
							int space = (int) dashPattern[1];
							BasicStroke newValue = DialogBasicStroke.showBox(canvasPanel,
									(int) b.getStroke().getLineWidth(), (int) dash, (int) space);

							//
							if (newValue != null) {

								tempObject = (BasicStroke) newValue;
								tempNode.setUserObject(
										new LabelledUserObject("Stroke Style = " + tempObject, tempObject));
								newValue = (BasicStroke) (((LabelledUserObject) tempNode.getUserObject())
										.getUserObject());
								b.setStroke(newValue);
								((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
								boundaryTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
								updateCanvas(); // Redraws the canvas to show
												// the updated location of the
												// moved object.
							}

							else if (tempObject instanceof Boundary)
								tempNode.setUserObject(
										new LabelledUserObject("Stroke Style = " + b.getStroke(), b.getStroke()));

						}

					} catch (NumberFormatException nf) {
						JOptionPane.showMessageDialog(boundaryTree, "Invalid... number format");
						getTreeModelBoundary();

					}

					catch (IllegalArgumentException ia) {
						JOptionPane.showMessageDialog(boundaryTree, "Invalid... value not accepted *************");
						getTreeModelBoundary();
						ia.printStackTrace();
					} catch (NegativeNumberException g) {
						// TODO Auto-generated catch block
						g.printStackTrace();
					}

					if (boundaryTree.getLastSelectedPathComponent().toString().contains("Point")) {
						bp = ((BPoint) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());
					}

					try {

						if (((LabelledUserObject) tempParent.getUserObject()).getUserObject() instanceof BPoint) {
							bp = ((BPoint) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());

							selected = bp;
							updateCanvas();
							// System.out.println("selected" + bp.getPointID());

							// NORTHING
							if (boundaryTree.getLastSelectedPathComponent().toString().contains("Y")
									&& !(tempObject instanceof Boundary)) {

								String newValue = JOptionPane.showInputDialog(boundaryTree, "Input new y value",
										decimalFormat4.format(bp.getY()));

								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Y = " + decimalFormat4.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double northing = Double.valueOf(newValue);
									bp.setY(northing);
									((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
									boundaryTree.validate(); // redraws the
																// Jtree this
																// means
																// everything
																// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								} else if (tempObject instanceof BPoint)
									tempNode.setUserObject(new LabelledUserObject(
											"Y = " + decimalFormat4.format(bp.getY()) + "m", bp.getY()));

							}
							// EASTING
							else if (boundaryTree.getLastSelectedPathComponent().toString().contains("X")
									&& !(tempObject instanceof Boundary)) {
								String newValue = JOptionPane.showInputDialog(boundaryTree, "Input new x value",
										decimalFormat4.format(bp.getX()));
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"X = " + decimalFormat4.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double easting = Double.valueOf(newValue);
									bp.setX(easting);
									((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// patTree.validate();
								} else if (tempObject instanceof BPoint)
									tempNode.setUserObject(new LabelledUserObject(
											"X = " + decimalFormat4.format(bp.getX()) + "m", bp.getX()));

							}
							// COLLAR RL
							else if (boundaryTree.getLastSelectedPathComponent().toString().contains("Z")
									&& !(tempObject instanceof Boundary)) {
								String newValue = JOptionPane.showInputDialog(boundaryTree, "Input new z value",
										decimalFormat4.format(bp.getZ()));
								if (newValue != null || newValue != "") {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Z = " + decimalFormat4.format(tempObject) + "m", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double collarRL = Double.valueOf(newValue);
									bp.setZ(collarRL);
									((DefaultTreeModel) boundaryTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// patTree.validate();
								} else if (tempObject instanceof BPoint)
									tempNode.setUserObject(new LabelledUserObject(
											"Z = " + decimalFormat4.format(tempObject) + "m", bp.getZ()));

							}
						}
					} catch (NumberFormatException nf) {
						JOptionPane.showMessageDialog(boundaryTree, "Invalid... number format");
						getTreeModelBoundary();

					} catch (IllegalArgumentException ia) {
						JOptionPane.showMessageDialog(boundaryTree, "Invalid... value not accepted");
						getTreeModelBoundary();
					}
				}
			}
		});

		// Status Text to indicate which node is selected
		boundaryTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
				// DefaultMutableTreeNode node = new
				// DefaultMutableTreeNode();
				TreePath tp = e.getNewLeadSelectionPath();

				if (tp.getLastPathComponent() != null) {

					statusBarLabel.setText("Node in Environment Tree Selected: " + tp.getLastPathComponent());
					selected = tp.getLastPathComponent();
				}

			}// throws a Null Pointer if a selected node is then
				// collapsed will need to fix - eventually
		});

		if (world.getBoundaryList().size() > 0) {// if the World has a pattern
													// in it
			for (Boundary temp : world.getBoundaryList().values()) {// For every
																	// pattern
																	// in the
																	// world do
																	// the next
																	// bit

				// Below initialize the patternNode - current pattern index is
				// starts at 0 so had to add 1 to it

				boundaryNode = new DefaultMutableTreeNode(new LabelledUserObject(
						"Boundary " + (temp.getBoundaryID()) + "/ contains " + temp.getNumberOfPointsInBoundary()
								+ " points.",
						temp));
				// you have to insert the node
				boundaryTreeModel.insertNodeInto((MutableTreeNode) boundaryNode,
						(MutableTreeNode) boundaryTreeModel.getRoot(), worldNode.getChildCount());
				if (temp instanceof Boundary) {
					arrowNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Arrow Type =" + temp.getArrow(), temp.getArrow()));
					annotateNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Is Annotated = " + temp.getAnnotated(), temp.getAnnotated()));
					closedNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Is Closed = " + temp.getClosed(), temp.getClosed()));
					colorNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Stroke Colour = " + temp.getColor(), temp.getColor()));
					colorFillNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Fill Colour = " + temp.getFillColor(), temp.getFillColor()));
					strokeNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Stroke Style= " + temp.getStroke(), temp.getStroke()));

					boundaryTreeModel.insertNodeInto((MutableTreeNode) arrowNode, (MutableTreeNode) boundaryNode,
							boundaryNode.getChildCount());
					boundaryTreeModel.insertNodeInto((MutableTreeNode) annotateNode, (MutableTreeNode) boundaryNode,
							boundaryNode.getChildCount());
					boundaryTreeModel.insertNodeInto((MutableTreeNode) closedNode, (MutableTreeNode) boundaryNode,
							boundaryNode.getChildCount());
					boundaryTreeModel.insertNodeInto((MutableTreeNode) colorNode, (MutableTreeNode) boundaryNode,
							boundaryNode.getChildCount());
					boundaryTreeModel.insertNodeInto((MutableTreeNode) colorFillNode, (MutableTreeNode) boundaryNode,
							boundaryNode.getChildCount());
					boundaryTreeModel.insertNodeInto((MutableTreeNode) strokeNode, (MutableTreeNode) boundaryNode,
							boundaryNode.getChildCount());
				}

				for (BPoint bp : temp.getBPList().values()) {// for every dummy
																// in the
																// current
																// pattern
					if (temp.getBPList().size() > 0 && temp.getBPList().size() < 5000) {// NEED
																						// TO
																						// FIX
																						// this
																						// so
																						// the
																						// tree
																						// listens
																						// to
																						// if
																						// the
																						// node
																						// is
																						// expanded
																						// or
																						// not
						if (bp instanceof BPoint) {// if it is a dummy do this
							// arrowNode = new DefaultMutableTreeNode(new
							// LabelledUserObject("Arrow Type ="+
							// temp.getArrow(), temp.getArrow()));
							// annotateNode= new DefaultMutableTreeNode(new
							// LabelledUserObject("Is Annotated = "+
							// temp.getAnnotated(), temp.getAnnotated()));
							pointNode = new DefaultMutableTreeNode(
									new LabelledUserObject("Point ID " + (bp.getPointID()), bp));
							nNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Y = " + decimalFormat4.format((bp.getY())) + "m", bp.getY()));
							eNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"X = " + decimalFormat4.format((bp.getX())) + "m", bp.getX()));
							crlNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Z = " + decimalFormat4.format((bp.getZ())) + "m", bp.getZ()));

							boundaryTreeModel.insertNodeInto((MutableTreeNode) pointNode,
									(MutableTreeNode) boundaryNode, boundaryNode.getChildCount());
							boundaryTreeModel.insertNodeInto((MutableTreeNode) nNode, (MutableTreeNode) pointNode,
									pointNode.getChildCount());
							boundaryTreeModel.insertNodeInto((MutableTreeNode) eNode, (MutableTreeNode) pointNode,
									pointNode.getChildCount());
							boundaryTreeModel.insertNodeInto((MutableTreeNode) crlNode, (MutableTreeNode) pointNode,
									pointNode.getChildCount());
						}
					}
				}
			}
		}
		getBoundaryLayerScrollPane().setViewportView(boundaryTree);// displays
																	// the JTree
		return boundaryTree;

	}

	private JTree getTreeModelSurface() {
		// These will be nodes that have children
		DefaultMutableTreeNode worldNode = new DefaultMutableTreeNode("World");// Initialized
																				// Tree
																				// so
																				// there
																				// is
																				// no
																				// null
																				// pointers
		DefaultMutableTreeNode surfaceNode; // declared nodes that I'd like in
											// it
		DefaultMutableTreeNode patternNode;

		DefaultMutableTreeNode toNode, fromNode, delayNode, colourNode;

		surfaceTree = new JTree(worldNode);// assigned root to Jtree

		surfaceTree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION); // Allows
																										// for
																										// multipleselections
																										// int
																										// the
																										// Tree

		surfaceTreeModel = (DefaultTreeModel) surfaceTree.getModel();// confirms
																		// TreeModel
																		// being
																		// used
																		// -
																		// probably
																		// superferlous

		surfaceTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Pattern tempPattern = currentPattern;
				Object temp;
				SurfaceConnector tempSC;
				DefaultMutableTreeNode tempNode = ((DefaultMutableTreeNode) surfaceTree.getLastSelectedPathComponent());
				Object tempObject;
				// tempObject = ((LabelledUserObject)
				// tempNode.getUserObject()).getUserObject();
				tempObject = tempNode.getUserObject().toString();
				DefaultMutableTreeNode tempParent = (DefaultMutableTreeNode) tempNode.getParent();

				// SELECTS EVERYTHING IN THE CANVAS
				if (surfaceTree.getLastSelectedPathComponent().toString().contains("World")) {
					selected = world.getAllObjectsInWorld();
					updateCanvas();
				}

				// SELECTS IN THE CANVAS THE ENTIRE PATTERN
				if (surfaceTree.getLastSelectedPathComponent().toString().contains("Pattern")) {
					selected = null;
					updateCanvas();
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = ((Pattern) tempObject).getAllSurfaceConnectors();// .getAllDummysAndHoles();//.getAllObjectsInPattern();
					updateCanvas();
				}
				// SELECTS IN THE CANVAS THE HOLES IN THAT PATTERN
				if (surfaceTree.getLastSelectedPathComponent().toString().contains("Surface ID")) {
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = tempObject;
					updateCanvas();
				}

				// TODO modification on any object in the pattern tree
				if (e.getClickCount() == 2 && surfaceTreeModel.isLeaf(surfaceTree.getLastSelectedPathComponent())) {

					try {

						// temp = ((DefaultMutableTreeNode)
						// tempParent.getParent()).getUserObject();
						// tempPattern = (Pattern) temp;
						if (((LabelledUserObject) tempParent.getUserObject())
								.getUserObject() instanceof SurfaceConnector) {
							tempSC = ((SurfaceConnector) ((LabelledUserObject) tempParent.getUserObject())
									.getUserObject());

							// Pattern tempPat = ((Pattern)
							// tempParent.getPreviousNode().getPreviousNode().getUserObject());

							selected = tempSC;
							updateCanvas();
							System.out.println("selected" + tempSC.getSurfaceID());

							// FROM
							if (surfaceTree.getLastSelectedPathComponent().toString().contains("From Point")) {

								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new from point",
										tempSC.getFrom().getHoleID());

								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Integer.parseInt(newValue);

									tempNode.setUserObject(
											new LabelledUserObject("From Point = " + (Integer) tempObject, tempObject));
									newValue = Integer.toString(
											(Integer) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());

									int i = Integer.valueOf(newValue);

									if (((Pattern) tempPattern).getHoleOrDummy(i) != null) {

										tempSC.setFrom(((Pattern) tempPattern).getHole(i));
										((DefaultTreeModel) surfaceTree.getModel()).nodeChanged(tempNode);
										surfaceTree.validate(); // redraws the
																// Jtree this
																// means
																// everything
																// collapses.
										updateCanvas(); // Redraws the canvas to
														// show the updated
														// location of the moved
														// object.
									}

								} else if (tempObject instanceof Dummy && !(tempObject instanceof Hole))
									tempNode.setUserObject(
											new LabelledUserObject("From Point = " + tempSC.getFrom().getHoleID(),
													tempSC.getFrom().getHoleID()));

							}
							// TO
							if (surfaceTree.getLastSelectedPathComponent().toString().contains("To Point")) {

								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new to point",
										tempSC.getFrom().getHoleID());

								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Integer.parseInt(newValue);

									tempNode.setUserObject(
											new LabelledUserObject("To Point = " + (Integer) tempObject, tempObject));
									newValue = Integer.toString(
											(Integer) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());

									int i = Integer.valueOf(newValue);

									if (((Pattern) tempPattern).getHoleOrDummy(i) != null) {

										tempSC.setTo(((Pattern) tempPattern).getHole(i));
										((DefaultTreeModel) surfaceTree.getModel()).nodeChanged(tempNode);
										surfaceTree.validate(); // redraws the
																// Jtree this
																// means
																// everything
																// collapses.
										updateCanvas(); // Redraws the canvas to
														// show the updated
														// location of the moved
														// object.
									}

								} else if (tempObject instanceof Dummy && !(tempObject instanceof Hole))
									tempNode.setUserObject(new LabelledUserObject(
											"To Point = " + tempSC.getTo().getHoleID(), tempSC.getTo().getHoleID()));

							}
							// DELAY
							if (surfaceTree.getLastSelectedPathComponent().toString().contains("Delay =")) {

								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new Delay",
										tempSC.getDelay());

								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Integer.parseInt(newValue);

									tempNode.setUserObject(
											new LabelledUserObject("Delay = " + (Integer) tempObject, tempObject));
									newValue = Integer.toString(
											(Integer) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());

									int i = Integer.valueOf(newValue);

									tempSC.setDelay(i);
									((DefaultTreeModel) surfaceTree.getModel()).nodeChanged(tempNode);
									surfaceTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.

								} else if (tempObject instanceof Dummy && !(tempObject instanceof Hole))
									tempNode.setUserObject(new LabelledUserObject(
											"To Point = " + tempSC.getFrom().getHoleID(), tempSC.getDelay()));

							}

							if (surfaceTree.getLastSelectedPathComponent().toString().contains("Colour")) {

								Color newValue = JColorChooser.showDialog(canvasPanel, "Change Current Colour",
										tempSC.getColor());

								if (newValue != null) {

									tempObject = (Color) newValue;

									tempNode.setUserObject(
											new LabelledUserObject("Colour = " + (tempObject), tempObject));
									newValue = (Color) (((LabelledUserObject) tempNode.getUserObject())
											.getUserObject());
									tempSC.setColor(newValue);
									((DefaultTreeModel) surfaceTree.getModel()).nodeChanged(tempNode);
									surfaceTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								}

								else if (tempObject instanceof Boundary)
									tempNode.setUserObject(
											new LabelledUserObject("Colour = " + tempSC.getColor(), tempSC.getColor()));

							} else
								System.out.println("There is an error in the JTree - surfaceTree - MouseListener");
						}
					}

					catch (NumberFormatException nf) {
						JOptionPane.showMessageDialog(surfaceTree, "Invalid... number format");
						getTreeModelSurface();

					} catch (NegativeNumberException nn) {
						JOptionPane.showMessageDialog(surfaceTree, "Invalid... negative value");
						getTreeModelSurface();
					} catch (IllegalArgumentException ia) {
						JOptionPane.showMessageDialog(surfaceTree, "Invalid... value not accepted");
						getTreeModelSurface();
					} catch (NullPointerException npe) {
						// TODO Auto-generated catch block
						npe.printStackTrace();
					} catch (FromToException fte) {
						// TODO Auto-generated catch block
						fte.printStackTrace();
					}
				}
			}
		});

		// Status Text to indicate which node is selected
		surfaceTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
				// DefaultMutableTreeNode node = new
				// DefaultMutableTreeNode();
				TreePath tp = e.getNewLeadSelectionPath();

				if (tp.getLastPathComponent() != null) {

					statusBarLabel.setText("Node in Environment Tree Selected: " + tp.getLastPathComponent());
					selected = tp.getLastPathComponent();
				}

			}// throws a Null Pointer if a selected node is then
				// collapsed will need to fix - eventually
		});

		if (world.getPatternList().size() > 0) {// if the World has a pattern in
												// it
			for (Pattern tempPat : world.getPatternList().values()) {// For
																		// every
																		// pattern
																		// in
																		// the
																		// world
																		// do
																		// the
																		// next
																		// bit

				// Below initialize the patternNode - current pattern index is
				// starts at 0 so had to add 1 to it

				patternNode = new DefaultMutableTreeNode(new LabelledUserObject(
						"Pattern " + (tempPat.getPatternID()) + "/ contains " + tempPat.getNumberOfSurfaceConnections()
								+ " Surface Connectors.",
						tempPat));
				// you have to insert the node
				surfaceTreeModel.insertNodeInto((MutableTreeNode) patternNode,
						(MutableTreeNode) surfaceTreeModel.getRoot(), worldNode.getChildCount());

				for (SurfaceConnector tempSC : tempPat.getSurfaceList().values()) {
					if (tempPat.getSurfaceList().size() > 0 && tempPat.getSurfaceList().size() < 5000) {// NEED
																										// TO
																										// FIX
																										// this
																										// so
																										// the
																										// tree
																										// listens
																										// to
																										// if
																										// the
																										// node
																										// is
																										// expanded
																										// or
																										// not

						if (tempSC instanceof SurfaceConnector) {
							surfaceNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"Surface ID " + (tempSC.getSurfaceID()) + "/ " + tempSC.getDelay() + "ms"
											+ "- from #"
											+ tempSC.getFrom().getHoleID() + "- to #" + tempSC.getTo().getHoleID(),
									tempSC));

							fromNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"From Point = " + (tempSC.getFrom().getHoleID()), tempSC.getFrom().getHoleID()));
							toNode = new DefaultMutableTreeNode(new LabelledUserObject(
									"To Point = " + (tempSC.getTo().getHoleID()), tempSC.getTo().getHoleID()));
							delayNode = new DefaultMutableTreeNode(
									new LabelledUserObject("Delay = " + (tempSC.getDelay()), tempSC.getDelay()));
							colourNode = new DefaultMutableTreeNode(
									new LabelledUserObject("Colour = " + tempSC.getColor(), tempSC.getColor()));

							surfaceTreeModel.insertNodeInto((MutableTreeNode) surfaceNode,
									(MutableTreeNode) patternNode, patternNode.getChildCount());
							surfaceTreeModel.insertNodeInto((MutableTreeNode) fromNode, (MutableTreeNode) surfaceNode,
									surfaceNode.getChildCount());
							surfaceTreeModel.insertNodeInto((MutableTreeNode) toNode, (MutableTreeNode) surfaceNode,
									surfaceNode.getChildCount());
							surfaceTreeModel.insertNodeInto((MutableTreeNode) delayNode, (MutableTreeNode) surfaceNode,
									surfaceNode.getChildCount());
							surfaceTreeModel.insertNodeInto((MutableTreeNode) colourNode, (MutableTreeNode) surfaceNode,
									surfaceNode.getChildCount());
						}
					}
				}
			}
		}

		else {
			surfaceTreeModel.setRoot(worldNode);

		}

		getSurfaceLayerScrollPane().setViewportView(surfaceTree);// displays the
																	// JTree

		return surfaceTree;

	}

	private JScrollPane getTextScrollPane() {
		if (textScrollPane == null) {
			textScrollPane = new JScrollPane();
			textScrollPane.setViewportView(getTreeModelText());
		}
		return textScrollPane;
	}

	private JTree getTreeModelText() {
		// These will be nodes that have children
		DefaultMutableTreeNode worldNode = new DefaultMutableTreeNode("World");// Initialized
																				// Tree
																				// so
																				// there
																				// is
																				// no
																				// null
																				// pointers
		DefaultMutableTreeNode textNode, pointNode; // declared nodes that I'd
													// like in it
		DefaultMutableTreeNode nNode, eNode, crlNode, oNode, valueNode, colorNode, fontNode;

		textTree = new JTree(worldNode);// assigned root to Jtree

		textTreeModel = (DefaultTreeModel) textTree.getModel();// confirms
																// TreeModel
																// being used -
																// probably
																// superferlous

		textTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {

				Text t = null;
				DefaultMutableTreeNode tempNode = ((DefaultMutableTreeNode) textTree.getLastSelectedPathComponent());
				Object tempObject;
				tempObject = tempNode.getUserObject().toString();
				DefaultMutableTreeNode tempParent = (DefaultMutableTreeNode) tempNode.getParent();

				// SELECTS EVERYTHING IN THE CANVAS
				if (textTree.getLastSelectedPathComponent().toString().contains("World")) {
					selected = world.getAllObjectsInWorld();
					updateCanvas();
				}
				// SELECTS IN THE CANVAS THE ENTIRE PATTERN
				if (textTree.getLastSelectedPathComponent().toString().contains("Text")) {
					selected = null;
					updateCanvas();
					tempObject = ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
					selected = tempObject;

					updateCanvas();
				}

				// TODO modification on any object in the pattern tree
				if (e.getClickCount() == 2 && textTreeModel.isLeaf(textTree.getLastSelectedPathComponent())) {
					try {
						if (((LabelledUserObject) tempParent.getUserObject()).getUserObject() instanceof Text) {
							t = ((Text) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());
						}
						selected = t;
						updateCanvas();

						if (textTree.getLastSelectedPathComponent().toString().contains("Text")) {
							t = ((Text) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());
						}

						if (((LabelledUserObject) tempParent.getUserObject()).getUserObject() instanceof Text) {
							t = ((Text) ((LabelledUserObject) tempParent.getUserObject()).getUserObject());

							selected = t;
							updateCanvas();

							// NORTHING
							if (textTree.getLastSelectedPathComponent().toString().contains("Northing")
									&& !(tempObject instanceof Text)) {

								String newValue = JOptionPane.showInputDialog(textTree, "Input new northing value",
										decimalFormat4.format(t.getY()));

								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Northing = " + decimalFormat4.format(tempObject) + "mN", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double northing = Double.valueOf(newValue);
									t.setX(northing);
									((DefaultTreeModel) textTree.getModel()).nodeChanged(tempNode);
									textTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								} else if (tempObject instanceof Text)
									tempNode.setUserObject(new LabelledUserObject(
											"Northing = " + decimalFormat4.format(t.getY()) + "mN", t.getY()));

							}
							// EASTING
							else if (textTree.getLastSelectedPathComponent().toString().contains("Easting")
									&& !(tempObject instanceof Text)) {
								String newValue = JOptionPane.showInputDialog(textTree, "Input new easting value",
										decimalFormat4.format(t.getX()));
								if (newValue != null || newValue != "" && Double.parseDouble(newValue) > 0) {
									tempObject = (Object) Double.parseDouble(newValue);

									tempNode.setUserObject(new LabelledUserObject(
											"Easting = " + decimalFormat4.format(tempObject) + "mE", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double easting = Double.valueOf(newValue);
									t.setY(easting);
									((DefaultTreeModel) textTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// patTree.validate();
								} else if (tempObject instanceof Text)
									tempNode.setUserObject(new LabelledUserObject(
											"Easting = " + decimalFormat4.format(t.getX()) + "mE", t.getX()));

							}
							// COLLAR RL
							else if (textTree.getLastSelectedPathComponent().toString().contains("Z Level")
									&& !(tempObject instanceof Text)) {
								String newValue = JOptionPane.showInputDialog(canvasPanel,
										"Input new collar level value", decimalFormat4.format(t.getZ()));
								if (newValue != null || newValue != "") {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Z Level = " + decimalFormat1.format(tempObject) + "rL", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double collarRL = Double.valueOf(newValue);
									t.setZ(collarRL);
									((DefaultTreeModel) textTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// patTree.validate();
								} else if (tempObject instanceof Text)
									tempNode.setUserObject(new LabelledUserObject(
											"Z Level = " + decimalFormat1.format(t.getZ()) + "rL", t.getZ()));

							}
							// LABEL2
							else if (textTree.getLastSelectedPathComponent().toString().contains("Value")) {
								String newValue = JOptionPane.showInputDialog(canvasPanel, "Input new Text Value",
										((String) t.getText()));
								if (newValue != null || newValue != "") {
									tempObject = (Object) newValue;
									tempNode.setUserObject(new LabelledUserObject("Value = " + tempObject, tempObject));
									newValue = (String) ((LabelledUserObject) tempNode.getUserObject()).getUserObject();
									t.setText(newValue);
									((DefaultTreeModel) textTree.getModel()).nodeChanged(tempNode);
									updateCanvas();

								} else
									tempNode.setUserObject(
											new LabelledUserObject("Value = " + t.getText(), t.getText()));
							}
							// ORIENTATION
							else if (textTree.getLastSelectedPathComponent().toString().contains("Bearing")
									&& !(tempObject instanceof Text)) {
								String newValue = JOptionPane.showInputDialog(textTree, "Input new bearing level value",
										decimalFormat4.format(t.getBearing()));
								if (newValue != null || newValue != "") {
									tempObject = (Object) Double.parseDouble(newValue);
									tempNode.setUserObject(new LabelledUserObject(
											"Bearing = " + decimalFormat1.format(tempObject) + "�", tempObject));
									newValue = Double.toString(
											(Double) ((LabelledUserObject) tempNode.getUserObject()).getUserObject());
									double b = Double.valueOf(newValue);
									t.setBearing(b);
									((DefaultTreeModel) textTree.getModel()).nodeChanged(tempNode);
									updateCanvas();
									// patTree.validate();
								} else if (tempObject instanceof Text)
									tempNode.setUserObject(new LabelledUserObject(
											"Bearing = " + decimalFormat1.format(t.getBearing()) + "�",
											t.getBearing()));

							}
							// COLOUR
							if (textTree.getLastSelectedPathComponent().toString().contains("Colour")
									&& !(tempObject instanceof Text)) {

								Color newValue = JColorChooser.showDialog(canvasPanel, "Change Colour", t.getColor());
								if (newValue != null) {

									tempObject = (Color) newValue;

									tempNode.setUserObject(
											new LabelledUserObject("Colour = " + (tempObject), tempObject));
									newValue = (Color) (((LabelledUserObject) tempNode.getUserObject())
											.getUserObject());
									t.setColor(newValue);
									((DefaultTreeModel) textTree.getModel()).nodeChanged(tempNode);
									textTree.validate(); // redraws the Jtree
															// this means
															// everything
															// collapses.
									updateCanvas(); // Redraws the canvas to
													// show the updated location
													// of the moved object.
								}

								else if (tempObject instanceof Boundary)
									tempNode.setUserObject(
											new LabelledUserObject("Colour = " + t.getColor(), t.getColor()));

							}
						}
					} catch (NumberFormatException nf) {
						JOptionPane.showMessageDialog(boundaryTree, "Invalid... number format");
						getTreeModelBoundary();

					} catch (IllegalArgumentException ia) {
						JOptionPane.showMessageDialog(boundaryTree, "Invalid... value not accepted");
						getTreeModelBoundary();
					}

				}
			}
		});

		// Status Text to indicate which node is selected
		textTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
				// DefaultMutableTreeNode node = new DefaultMutableTreeNode();
				TreePath tp = e.getNewLeadSelectionPath();

				if (tp.getLastPathComponent() != null) {

					statusBarLabel.setText("Node in Environment Tree Selected: " + tp.getLastPathComponent());
					selected = tp.getLastPathComponent();
				}

			}
		});
		if ((!world.getTextList().isEmpty())) {// if the World has a pattern in
												// it

			for (Text temp : world.getTextList().values()) {// For every text in
															// the world do the
															// next bit

				System.out.println(temp.getText());
				// Below initialize the textNode

				textNode = new DefaultMutableTreeNode(
						new LabelledUserObject("Text " + (temp.getTextID()) + "/ contains " + temp.getText(), temp));
				// you have to insert the node
				textTreeModel.insertNodeInto((MutableTreeNode) textNode, (MutableTreeNode) textTreeModel.getRoot(),
						worldNode.getChildCount());
				if (temp instanceof Text) {
					nNode = new DefaultMutableTreeNode(new LabelledUserObject("Northing =" + temp.getY(), temp.getY()));
					eNode = new DefaultMutableTreeNode(new LabelledUserObject("Easting = " + temp.getX(), temp.getX()));
					crlNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Z Level = " + temp.getZ(), temp.getZ()));
					oNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Bearing = " + temp.getBearing(), temp.getBearing()));
					valueNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Value = " + temp.getText(), temp.getText()));
					colorNode = new DefaultMutableTreeNode(
							new LabelledUserObject("Colour = " + temp.getColor(), temp.getColor()));
					// fontNode = new DefaultMutableTreeNode(new
					// LabelledUserObject("Font Style= "+ temp.,
					// temp.getFont()));

					textTreeModel.insertNodeInto((MutableTreeNode) nNode, (MutableTreeNode) textNode,
							textNode.getChildCount());
					textTreeModel.insertNodeInto((MutableTreeNode) eNode, (MutableTreeNode) textNode,
							textNode.getChildCount());
					textTreeModel.insertNodeInto((MutableTreeNode) crlNode, (MutableTreeNode) textNode,
							textNode.getChildCount());
					textTreeModel.insertNodeInto((MutableTreeNode) oNode, (MutableTreeNode) textNode,
							textNode.getChildCount());
					textTreeModel.insertNodeInto((MutableTreeNode) valueNode, (MutableTreeNode) textNode,
							textNode.getChildCount());
					textTreeModel.insertNodeInto((MutableTreeNode) colorNode, (MutableTreeNode) textNode,
							textNode.getChildCount());
					// textTreeModel.insertNodeInto((MutableTreeNode) fontNode,
					// (MutableTreeNode)textNode, textNode.getChildCount());

				}
			}
		}

		getTextScrollPane().setViewportView(textTree);// displays the JTree

		return textTree;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		String message;
		int notches = e.getWheelRotation();
		if (notches < 0) {
			message = "Mouse wheel moved UP " + -notches + " notch(es)\n";
		} else {
			message = "Mouse wheel moved DOWN " + notches + " notch(es)\n";
		}
		if (e.getScrollType() == MouseWheelEvent.WHEEL_UNIT_SCROLL) {
			message += "    Scroll type: WHEEL_UNIT_SCROLL\n";
			message += "    Scroll amount: " + e.getScrollAmount() + " unit increments per notch\n";
			message += "    Units to scroll: " + e.getUnitsToScroll() + " unit increments\n";
			message += "    Vertical unit increment: " + scrollPane.getVerticalScrollBar().getUnitIncrement(1)
					+ " pixels\n";
		} else { // scroll type == MouseWheelEvent.WHEEL_BLOCK_SCROLL
			message += "    Scroll type: WHEEL_BLOCK_SCROLL\n";
			message += "    Vertical block increment: " + scrollPane.getVerticalScrollBar().getBlockIncrement(1)
					+ " pixels\n";
		}
		System.out.print(message + e);
	}

	private JScrollPane getSystemTreeScrollPane() {
		if (systemTreeScrollPane == null) {
			systemTreeScrollPane = new JScrollPane();
			systemTreeScrollPane.setViewportView(getTreeModelSystem());
		}
		return systemTreeScrollPane;
	}

	private JTree getTreeModelSystem() {

		File[] listOfRoot = File.listRoots();
		for (int i = 0; i < listOfRoot.length; i++) {
			File root = listOfRoot[i];
		}

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Computer");
		DefaultMutableTreeNode dirNode, fileNode;

		// Make a tree list with all the nodes, and make it a JTree
		systemTree = new JTree(rootNode);
		systemTreeModel = (DefaultTreeModel) systemTree.getModel();// confirms
																	// TreeModel
																	// being
																	// used -
																	// probably
																	// superferlous
		// Add a listener
		systemTree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
			}
		});
		if (!(listOfRoot.length <= 0)) {// if the World has a pattern in it

			// For every dir in the fileSystem do the next bit
			for (File f : listOfRoot) {
				if (f.isDirectory()) {
					dirNode = new DefaultMutableTreeNode(new LabelledUserObject(f.getName(), f.getAbsoluteFile()));
					systemTreeModel.insertNodeInto((MutableTreeNode) dirNode,
							(MutableTreeNode) systemTreeModel.getRoot(), rootNode.getChildCount());

					if (f.isFile()) {
						fileNode = new DefaultMutableTreeNode(new LabelledUserObject(f.getName(), f.getAbsoluteFile()));
						systemTreeModel.insertNodeInto((MutableTreeNode) fileNode, (MutableTreeNode) dirNode,
								rootNode.getChildCount());
					}
				}
			}
			// System.out.println(temp.getText());
			// //Below initialize the textNode
			//
			// textNode = new DefaultMutableTreeNode(new
			// LabelledUserObject("Text "+ (temp.getTextID()) + "/ contains "+
			// temp.getText(), temp));
			// //you have to insert the node
			// textTreeModel.insertNodeInto((MutableTreeNode)textNode,
			// (MutableTreeNode)textTreeModel.getRoot(),
			// worldNode.getChildCount());
			// if (temp instanceof Text){
			// nNode = new DefaultMutableTreeNode(new
			// LabelledUserObject("Northing ="+ temp.getNorthing(),
			// temp.getNorthing()));
			// eNode= new DefaultMutableTreeNode(new
			// LabelledUserObject("Easting = "+ temp.getEasting(),
			// temp.getEasting()));
			// crlNode = new DefaultMutableTreeNode(new
			// LabelledUserObject("Z Level = "+ temp.getCollarRL(),
			// temp.getCollarRL()));
			// oNode = new DefaultMutableTreeNode(new
			// LabelledUserObject("Bearing = "+ temp.getBearing(),
			// temp.getBearing()));
			// valueNode = new DefaultMutableTreeNode(new
			// LabelledUserObject("Value = "+ temp.getText(), temp.getText()));
			// colorNode = new DefaultMutableTreeNode(new
			// LabelledUserObject("Colour = "+ temp.getColor(),
			// temp.getColor()));
			// // fontNode = new DefaultMutableTreeNode(new
			// LabelledUserObject("Font Style= "+ temp., temp.getFont()));
			//
			// textTreeModel.insertNodeInto((MutableTreeNode) nNode,
			// (MutableTreeNode)textNode, textNode.getChildCount());
			// textTreeModel.insertNodeInto((MutableTreeNode) eNode,
			// (MutableTreeNode)textNode, textNode.getChildCount());
			// textTreeModel.insertNodeInto((MutableTreeNode) crlNode,
			// (MutableTreeNode)textNode, textNode.getChildCount());
			// textTreeModel.insertNodeInto((MutableTreeNode) oNode,
			// (MutableTreeNode)textNode, textNode.getChildCount());
			// textTreeModel.insertNodeInto((MutableTreeNode) valueNode,
			// (MutableTreeNode)textNode, textNode.getChildCount());
			// textTreeModel.insertNodeInto((MutableTreeNode) colorNode,
			// (MutableTreeNode)textNode, textNode.getChildCount());
			// // textTreeModel.insertNodeInto((MutableTreeNode) fontNode,
			// (MutableTreeNode)textNode, textNode.getChildCount());

			// }
			// }
		}

		// TreeMap<Integer, File> fileList = dir.listFiles();
		//

		getSystemTreeScrollPane().setViewportView(systemTree);// displays the
																// JTree

		return systemTree;

	}

	private JScrollPane getScrollPane_3() {
		if (scrollPane_3 == null) {
			scrollPane_3 = new JScrollPane();
			scrollPane_3.setViewportView(getTree_2());
		}
		return scrollPane_3;
	}

	private JTree getTree_2() {
		if (tree_2 == null) {
			tree_2 = new JTree();
		}
		return tree_2;
	}

	/**
	 * This method initializes infoBarPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getInfoBarPanel() {
		if (infoBarPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.WEST;
			gridBagConstraints3.insets = new Insets(0, 10, 0, 0);
			gridBagConstraints3.gridwidth = 31;
			gridBagConstraints3.gridx = -1;
			gridBagConstraints3.gridy = -1;
			gridBagConstraints3.weightx = 0.1;
			gridBagConstraints3.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints39 = new GridBagConstraints();
			gridBagConstraints39.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints39.gridwidth = 4;
			gridBagConstraints39.gridx = 35;
			gridBagConstraints39.gridy = 0;
			gridBagConstraints39.insets = new Insets(0, 10, 0, 20);
			infoBarPanel = new JPanel();
			infoBarPanel.setLayout(new GridBagLayout());
			infoBarPanel.add(getLiteTieTRIALProgressBar(), gridBagConstraints39);
			infoBarPanel.add(statusBarLabel, gridBagConstraints3);
		}
		return infoBarPanel;
	}

	/**
	 * This method initializes dummyOnOffButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JToggleButton getDummyOnOffButton() {
		if (dummyOnOffButton == null) {
			dummyOnOffButton = new JToggleButton();
			dummyOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/dummyOnOff.png")));
			dummyOnOffButton.setPreferredSize(new Dimension(22, 22));
			dummyOnOffButton.setSelected(true);
			statusBarLabel.setText("");
			dummyOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (dummyOnOffButton.isSelected()) {
						statusBarLabel.setText("Dummy Display: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Dummy Display: OFF");
						updateCanvas();
					}
				}
			});
		}

		return dummyOnOffButton;
	}

	private void updateSurfaceTimes(SurfaceConnector startSC, Pattern pattern) throws NegativeNumberException {
		for (SurfaceConnector sc : pattern.getSurfaceList().values()) {
			if (sc.getFrom() == startSC.getTo()) {
				int fromTime = startSC.getTime();
				int toTime = fromTime + sc.getDelay();

				if (toTime < sc.getTime() || sc.getTime() == 0) {
					sc.setTime(toTime);
					updateSurfaceTimes(sc, pattern);
				}
			}
		}
	}

	public void getSurfaceTimes() throws NegativeNumberException {
		for (Pattern pattern : world.getPatternList().values()) {
			for (SurfaceConnector sc : pattern.getSurfaceList().values()) {
				sc.setTime(0);
			}
			for (InitiationPoint ip : pattern.getiPList().values()) {

				for (SurfaceConnector sc : pattern.getSurfaceList().values()) {
					// if(ip.getIPTime() < sc.getTime()-sc.getDelay()) {
					// ip.setIPTime(sc.getTime()-sc.getDelay());
					// }
					if (sc.getFrom() == ip.getIPDummy()) {
						sc.setTime(ip.getIPTime() + sc.getDelay());
						updateSurfaceTimes(sc, pattern);
					}

				}
			}
		}

	}

	/**
	 * This method initializes tieDelayToggleButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getTieDelayToggleButton() {
		if (tieDelayToggleButton == null) {
			tieDelayToggleButton = new JToggleButton();
			tieDelayToggleButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/tieDelayONOFF.png")));
			tieDelayToggleButton.setPreferredSize(new Dimension(22, 22));

			tieDelayToggleButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (tieDelayToggleButton.isSelected()) {
						statusBarLabel.setText("Surface Delays: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Surface Delays: OFF");
						updateCanvas();
					}
				}
			});
		}
		return tieDelayToggleButton;
	}

	/**
	 * This method initializes toolPanelTop
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getToolPanelTop() {
		if (toolPanelTop == null) {
			GridBagConstraints gridBagConstraints95 = new GridBagConstraints();
			gridBagConstraints95.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints95.gridy = 0;
			gridBagConstraints95.ipadx = 40;
			gridBagConstraints95.weightx = 1.0;
			gridBagConstraints95.gridx = 17;
			GridBagConstraints gridBagConstraints94 = new GridBagConstraints();
			gridBagConstraints94.gridx = 16;
			gridBagConstraints94.gridy = 0;
			GridBagConstraints gridBagConstraints93 = new GridBagConstraints();
			gridBagConstraints93.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints93.gridy = 0;
			gridBagConstraints93.ipadx = 40;
			gridBagConstraints93.weightx = 1.0;
			gridBagConstraints93.gridx = 15;
			GridBagConstraints gridBagConstraints82 = new GridBagConstraints();
			gridBagConstraints82.gridx = 14;
			gridBagConstraints82.gridy = 0;
			GridBagConstraints gridBagConstraints80 = new GridBagConstraints();
			gridBagConstraints80.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints80.gridy = 0;
			gridBagConstraints80.ipadx = 40;
			gridBagConstraints80.weightx = 1.0;
			gridBagConstraints80.gridx = 13;
			GridBagConstraints gridBagConstraints79 = new GridBagConstraints();
			gridBagConstraints79.gridx = 12;
			gridBagConstraints79.insets = new Insets(0, 4, 0, 0);
			gridBagConstraints79.gridy = 0;
			GridBagConstraints gridBagConstraints77 = new GridBagConstraints();
			gridBagConstraints77.gridx = 10;
			gridBagConstraints77.gridy = 0;
			GridBagConstraints gridBagConstraints76 = new GridBagConstraints();
			gridBagConstraints76.gridx = 9;
			gridBagConstraints76.gridy = 0;
			GridBagConstraints gridBagConstraints75 = new GridBagConstraints();
			gridBagConstraints75.gridx = 8;
			gridBagConstraints75.gridy = 0;
			GridBagConstraints gridBagConstraints74 = new GridBagConstraints();
			gridBagConstraints74.gridx = 7;
			gridBagConstraints74.ipadx = 0;
			gridBagConstraints74.insets = new Insets(0, 3, 0, 0);
			gridBagConstraints74.gridy = 0;
			GridBagConstraints gridBagConstraints73 = new GridBagConstraints();
			gridBagConstraints73.gridx = 6;
			gridBagConstraints73.gridy = 0;
			GridBagConstraints gridBagConstraints72 = new GridBagConstraints();
			gridBagConstraints72.gridx = 5;
			gridBagConstraints72.gridy = 0;
			GridBagConstraints gridBagConstraints70 = new GridBagConstraints();
			gridBagConstraints70.gridx = 4;
			gridBagConstraints70.gridy = 0;
			GridBagConstraints gridBagConstraints69 = new GridBagConstraints();
			gridBagConstraints69.gridx = 3;
			gridBagConstraints69.gridy = 0;
			GridBagConstraints gridBagConstraints68 = new GridBagConstraints();
			gridBagConstraints68.gridx = 2;
			gridBagConstraints68.gridy = 0;
			GridBagConstraints gridBagConstraints67 = new GridBagConstraints();
			gridBagConstraints67.gridx = 1;
			gridBagConstraints67.gridy = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 0;
			toolPanelTop = new JPanel();
			toolPanelTop.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			GridBagLayout gbl_toolPanelTop = new GridBagLayout();
			gbl_toolPanelTop.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
			gbl_toolPanelTop.columnWidths = new int[] { 30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			toolPanelTop.setLayout(gbl_toolPanelTop);
			toolPanelTop.add(getUndoButton(), gridBagConstraints11);
			toolPanelTop.add(getRedoButton(), gridBagConstraints67);
			toolPanelTop.add(getOpenButton(), gridBagConstraints68);
			toolPanelTop.add(getCloseButton(), gridBagConstraints69);
			toolPanelTop.add(getSaveButton(), gridBagConstraints70);
			toolPanelTop.add(getSaveAsButton(), gridBagConstraints72);
			toolPanelTop.add(getPrintButton(), gridBagConstraints73);
			toolPanelTop.add(getZoomOutButton(), gridBagConstraints74);
			toolPanelTop.add(getZoomInButton(), gridBagConstraints75);
			toolPanelTop.add(getZoomToFitButton(), gridBagConstraints76);
			toolPanelTop.add(getZoomUserSetButton(), gridBagConstraints77);
			toolPanelTop.add(getDetonatorTimeButton1(), gridBagConstraints79);
			toolPanelTop.add(getDetonatorTimeTextField1(), gridBagConstraints80);
			toolPanelTop.add(getDetonatorTimeButton2(), gridBagConstraints82);
			toolPanelTop.add(getDetonatorTimeTextField2(), gridBagConstraints93);
			toolPanelTop.add(getDetonatorTimeButton3(), gridBagConstraints94);
			toolPanelTop.add(getDetonatorTimeTextField3(), gridBagConstraints95);

			buttonGroup3.add(getDetonatorTimeButton1());
			buttonGroup3.add(getDetonatorTimeButton2());
			buttonGroup3.add(getDetonatorTimeButton3());
		}
		return toolPanelTop;
	}

	/**
	 * This method initializes showTabPaneButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getShowTabPaneButton() {
		if (showTabPaneButton == null) {
			showTabPaneButton = new JButton();
			showTabPaneButton.setPreferredSize(new Dimension(24, 24));

			final ImageIcon expanded = new ImageIcon(getClass().getResource("/icons_LiteTie/expandTree.png"));
			final ImageIcon collapsed = new ImageIcon(getClass().getResource("/icons_LiteTie/collapseTree.png"));
			showTabPaneButton.setIcon(collapsed);
			showTabPaneButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (showTabPaneButton.getIcon().equals(expanded)) {
						showTabPaneButton.setIcon(collapsed);
						layerHolderSplitPane.setDividerLocation(650);

					} else {
						showTabPaneButton.setIcon(expanded);
						layerHolderSplitPane.setDividerLocation(10000);
					}
				}
			});
		}
		return showTabPaneButton;
	}

	/**
	 * This method initializes detsOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getDetsOnOffButton() {
		if (detsOnOffButton == null) {
			detsOnOffButton = new JToggleButton();
			detsOnOffButton.setPreferredSize(new Dimension(22, 22));
			detsOnOffButton.setSelected(true);
			detsOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/detsonoff.png")));
			detsOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (detsOnOffButton.isSelected()) {
						statusBarLabel.setText("Detonator Display: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Detonator Display: OFF");
						updateCanvas();
					}
				}
			});
		}
		return detsOnOffButton;
	}

	/**
	 * This method initializes detonatorDelayButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getDetonatorDelayButton() {
		if (detonatorDelayButton == null) {
			detonatorDelayButton = new JToggleButton();
			detonatorDelayButton.setPreferredSize(new Dimension(22, 22));
			detonatorDelayButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/detdelayTimes.png")));
			detonatorDelayButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (detonatorDelayButton.isSelected()) {
						statusBarLabel.setText("Detonator Delays: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Detonator Delays: OFF");
						updateCanvas();
					}
				}
			});
		}
		return detonatorDelayButton;
	}

	/**
	 * This method initializes textOnOffButton
	 * 
	 * @return javax.swing.JToggleButton
	 */
	private JToggleButton getTextOnOffButton() {
		if (textOnOffButton == null) {
			textOnOffButton = new JToggleButton();
			textOnOffButton.setPreferredSize(new Dimension(22, 22));
			textOnOffButton.setIcon(new ImageIcon(getClass().getResource("/icons_LiteTie/textonoff.png")));
			textOnOffButton.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (textOnOffButton.isSelected()) {
						statusBarLabel.setText("Text Visibility: ON");
						updateCanvas();
					} else {
						statusBarLabel.setText("Text Visibility: OFF");
						updateCanvas();
					}
				}
			});
		}
		return textOnOffButton;
	}

	/**
	 * This method initializes eastingRuler1
	 * 
	 * @return javax.swing.JViewport
	 */
	// private JViewport getEastingRuler1() {
	// if (eastingRuler1 == null) {
	// eastingRuler1 = new JViewport();
	// eastingRuler1.setSize(new Dimension(644, 20));
	// eastingRuler1.setExtentSize(new Dimension(644, 20));
	// eastingRuler1.setMinimumSize(new Dimension(20, 20));
	// eastingRuler1.setPreferredSize(new Dimension(20, 800));
	// eastingRuler1.setView(canvasPanel);
	// eastingRuler1.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
	// }
	// return eastingRuler1;
	// }

	public static void main(String[] args) {
		showWindow();

	}

	private JToggleButton getToolScaleButton() {
		if (toolScaleButton == null) {
			toolScaleButton = new JToggleButton();
			toolScaleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolScaleButton.setMaximumSize(new Dimension(24, 22));
			toolScaleButton.setMinimumSize(new Dimension(24, 22));
			toolScaleButton.setToolTipText("Scale Selected Items");
			toolScaleButton.setPreferredSize(dimension30x30);
			toolScaleButton.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/scale.png")));
		}
		return toolScaleButton;
	}

	// VisualiseFX Drawing implementation

	double gridWidth = 10;
	double gridHeight = 10;

	// Visualise2D Drawing implementation
	@SuppressWarnings("serial")
	// Previously called getScrollBarHolder
	// DRAWING STUFF GOES HERE
	//
	private JScrollPane getCanvas() {

		// canvasScrollPane.setPreferredSize(new Dimension(400,300));
		if (canvasScrollPane == null) {
			// JViewport jViewport = new JViewport();
			// jViewport.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

			canvasPanel = new JPanel() {
				Visualise2D renderer = new Visualise2D();

				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g;
					double scale = Zoom.getScalingFactor() * UnitConvert.metersToPixels(1);
					Rectangle2D bounds = getCanvasSize(); // this returns the
															// canvas bounds
					if (pageFormat != null) {
						Rectangle2D rootView = new Rectangle2D.Double(canvasScrollPane.getX(), canvasScrollPane.getY(),
								canvasScrollPane.getWidth(), canvasScrollPane.getHeight());

						double scaleX = pageFormat.getImageableWidth() / rootView.getWidth();
						double scaleY = pageFormat.getImageableHeight() / rootView.getHeight();
						double scalePrint = 0;
						Point2D p2D = new Point2D.Double(rootView.getX(), rootView.getY());
						if (rootView.getWidth() >= rootView.getHeight()) {
							scaleX = pageFormat.getImageableWidth() / rootView.getWidth();
							scalePrint = scaleX;
						}
						if (rootView.getHeight() > rootView.getWidth()) {
							scaleY = pageFormat.getImageableHeight() / rootView.getHeight();
							scalePrint = scaleY;
						}

						renderer.paintPrintDimensions(g2, rootView, p2D, scalePrint, pageFormat);
					}

					renderer.paintMouseInfluence(g2, new Point2D.Double(mouseX, mouseY), radius, bounds, scale);

					// Below might change... Possibly changing this in the
					// future to listen on the scrollPane resize
					// if (!(bounds.getWidth() == 0 || bounds.getHeight() == 0))
					// {
					// if (Math.min(canvas.getWidth() /
					// UnitConvert.metersToPixels(bounds.getWidth()),
					// canvas.getHeight() /
					// UnitConvert.metersToPixels(bounds.getHeight())) !=
					// Zoom.getScalingFactor()) {
					// }
					// }
					// above might change see above comment
					averageSize = averageAllHoleSizes();

					for (Pattern tempPat : world.getPatternList().values()) {
						for (InitiationPoint i : tempPat.getiPList().values()) {
							boolean isSelected = i == selected
									|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(i));
							if (i.getIPDummy() instanceof Dummy || i.getIPDummy() instanceof Hole) {
								renderer.paintIP(g2, i.getIPDummy(), bounds, averageSize, scale, isSelected,
										holeFactor);
							}
						}
					}
					for (Text tempText : world.getTextList().values()) {

						boolean isSelected = tempText == selected
								|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(tempText));

						Text t = tempText;

						if (textOnOffButton.isSelected()) {
							if (t instanceof Text) {
								renderer.paintText(g2, t.getColor(), t, t.getFont(), t.getBearing(), bounds, scale,
										isSelected, toolRotateButton.isSelected(), holeFactor);
							}
						}
					}
					if (contourButton.isSelected()) {
						renderer.paintContours(g2, world, bounds, canvasScrollPane.getBounds(), scale);

					}

					for (Boundary tempBoundary : world.getBoundaryList().values()) {
						boolean isSelected = tempBoundary == selected || (selected instanceof Collection<?>
								&& ((Collection<?>) selected).contains(tempBoundary));
						Boundary b = tempBoundary;

						if (boundaryOnOffButton.isSelected()) {
							if (tempBoundary instanceof Boundary) {
								renderer.paintPolyLine(g2, b.getColor(), b.getFillColor(), b.getStroke(), b, coord1,
										coord2, bounds,
										Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
										b.isClosed(), b.getArrow(), b.getAnnotated());
							}

						}
						for (BPoint i : tempBoundary.getBPList().values()) {
							boolean isSelected1 = i == selected
									|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(i));
							BPoint bp = i;
							if (boundaryOnOffButton.isSelected()) {
								if (bp instanceof BPoint) {
									renderer.paintBPoint(g2, bp, tempBoundary, mouseClickX, mouseClickY, centerBoundsX,
											centerBoundsY, bounds, scale, isSelected1,
											getToolMoveButton().isSelected(), holeFactor);
								}
							}
						}

					}
					for (Pattern tempPat : world.getPatternList().values()) {
						for (Dummy tempDummy : tempPat.getAllDummysAndHoles()) { // and
																					// for
																					// every
																					// dummy
																					// /
																					// hole
																					// in
																					// tempPat
																					// do
																					// the
																					// following//
																					// the
																					// above
																					// two
																					// statements
																					// mean
																					// that
																					// every
																					// thing
																					// below
																					// will
																					// be
																					// done
																					// to
																					// every
																					// hole
																					// or
																					// dummy
																					// in
																					// all
																					// patterns
																					// on
																					// the
																					// canvas
							boolean isSelected = tempDummy == selected || (selected instanceof Collection<?>
									&& ((Collection<?>) selected).contains(tempDummy));
							Dummy d = tempDummy;

							if (tempDummy instanceof Dummy && (!(tempDummy instanceof Hole))
									&& dummyOnOffButton.isSelected()) {
								renderer.paintDummy(g2, d, d.getX() - deltaEasting, d.getY() - deltaNorthing, bounds,
										averageSize, scale, isSelected, toolMoveButton.isSelected(),
										holeFactor);
								if (holeIDOnOffButton.isSelected()) {
									if (tempDummy instanceof Dummy && (!(tempDummy instanceof Hole))) {
										renderer.paintHoleID(g2, tempDummy, null, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
									}
								}
							}
							if (tempDummy instanceof Hole) {
								Hole h = (Hole) tempDummy;

								if (holeTracksOnOffButton.isSelected()) {
									if (h.getAngle() != 90) {
										renderer.paintTrack(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
												toolRotateButton.isSelected());
									}
								}
								if (tempDummy instanceof Hole && holesOnOffButton.isSelected()) {
									renderer.paintHole(g2, h, h.getX() - deltaEasting, h.getY() - deltaNorthing,
											mouseClickX, mouseClickY, bounds, Zoom.getScalingFactor()
													* UnitConvert.metersToPixels(1),
											holeFactor, isSelected, toolMoveButton.isSelected(), h.getShape(),
											surfaceFactor);
								}
								if (fRLMarkerButton.isSelected()) {
									if (h.getAngle() != 90 || !(holesOnOffButton.isSelected())
											|| markerFactor > holeFactor) {
										renderer.paintFloorLine(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
												markerFactor);
									}
								}
								if (tRLMarkerButton.isSelected()) {
									if (h.getAngle() != 90 || !(holesOnOffButton.isSelected())
											|| markerFactor > holeFactor) {
										renderer.paintToeLine(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
												markerFactor);
									}
								}
								if (fRLCircleMarkerButton.isSelected()) {
									if (h.getAngle() != 90 || !(holesOnOffButton.isSelected())
											|| markerFactor > holeFactor) {
										renderer.paintFloorCircle(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
												markerFactor);
									}
								}
								if (tRLCircleMarkerButton.isSelected()) {
									if (h.getAngle() != 90 || !(holesOnOffButton.isSelected())
											|| markerFactor > holeFactor) {
										renderer.paintToeCircle(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
												markerFactor);
									}
								}
								if (holeSubdrillOnOffButton.isSelected()) {
									renderer.paintSubdrill(g2, h, bounds,
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
								}
								if (collarRLOnOffButton.isSelected()) {
									renderer.paintZLevel(g2, h, bounds,
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
								}
								if (toeRLOnOffButton.isSelected()) {
									renderer.paintToeRL(g2, h, bounds,
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
								}
								if (floorRLOnOffButton.isSelected()) {
									renderer.paintFloorRL(g2, h, bounds,
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
								}
								if (holeIDOnOffButton.isSelected()) {
									if (tempDummy instanceof Hole) {
										renderer.paintHoleID(g2, tempDummy, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
									}
								}
								if (holeLabel1OnOffButton.isSelected()) {
									if (tempDummy instanceof Hole) {
										renderer.paintHoleLabel1(g2, tempDummy, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
									}
								}
								if (holeLabel2OnOffButton.isSelected()) {
									if (tempDummy instanceof Hole) {
										renderer.paintHoleLabel2(g2, tempDummy, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
									}
								}
								if (holeLabel3OnOffButton.isSelected()) {
									if (tempDummy instanceof Hole) {
										renderer.paintHoleLabel3(g2, tempDummy, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
									}
								}
								if (diameterOnOffButton.isSelected()) {
									if (tempDummy instanceof Hole) {
										renderer.paintDiameter(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
									}
								}
								if (angleOnOffButton.isSelected()) {
									if (tempDummy instanceof Hole) {
										renderer.paintAngle(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), holeFactor);
									}
								}
								if (bearingOnOffButton.isSelected()) {
									if (tempDummy instanceof Dummy) {
										renderer.paintBearing(g2, h, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
									}
								}
								if (holeLengthOnOffButton.isSelected()) {
									renderer.paintHoleLength(g2, h, bounds, holeFactor,
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
								}
								if (benchHeightOnOffButton.isSelected()) {
									renderer.paintBench(g2, h, bounds,
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
								}
							}
						}
					}
					// Draws Detonators
					for (Pattern tempPat : world.getPatternList().values()) {
						for (Detonator d : tempPat.getDetonatorList().values()) {
							boolean isSelected = d == selected
									|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(d));

							if (detsOnOffButton.isSelected()) {
								if (d.getInHole() instanceof Hole) {
									renderer.paintDetonator(g2, d.getInHole(), bounds, d.getInHole().getDiameter(),
											d.getColor(),
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
											holeFactor);
								}

							}
						}
					}
					// Draws Ties
					for (Pattern tempPat : world.getPatternList().values()) {

						for (SurfaceConnector s : tempPat.getSurfaceList().values()) {
							// UNCOMMENT PAINTPOLYBOUNDS IF YOU WISH TO VIEW THE
							// BOUND OF A SURFACE CONNECTOR
							// renderer.paintPolyBounds(g2, s, bounds,
							// Zoom.getScalingFactor()*
							// UnitConvert.metersToPixels(1));
							boolean isSelected = s == selected
									|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(s));

							if (tieOnOffButton.isSelected()) {
								if (s.getFrom() instanceof Dummy && s.getTo() instanceof Dummy) {
									renderer.paintTie(g2, s, bounds, averageSize, averageSize, s.getColor(),
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
											surfaceFactor, holeFactor);
								} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Dummy) {
									renderer.paintTie(g2, s, bounds, ((Hole) s.getFrom()).getDiameter(), averageSize,
											s.getColor(),
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
											surfaceFactor, holeFactor);
								} else if (s.getFrom() instanceof Dummy && s.getTo() instanceof Hole) {
									renderer.paintTie(g2, s, bounds, averageSize, ((Hole) s.getTo()).getDiameter(),
											s.getColor(),
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
											surfaceFactor, holeFactor);
								} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Hole) {
									renderer.paintTie(g2, s, bounds, ((Hole) s.getFrom()).getDiameter(),
											((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()
													* UnitConvert.metersToPixels(1),
											isSelected, surfaceFactor, holeFactor);
								}
							}
						}
					}
					for (Pattern tempPat : world.getPatternList().values()) {
						for (SurfaceConnector s : tempPat.getSurfaceList().values()) {
							// UNCOMMENT PAINTPOLYBOUNDS IF YOU WISH TO VIEW THE
							// BOUND OF A SURFACE CONNECTOR
							// renderer.paintPolyBounds(g2, s, bounds,
							// ZoomScale.getZoomX()*
							// UnitConvert.metersToPixels(1));
							boolean isSelected = s == selected
									|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(s));

							if (tieDelayToggleButton.isSelected()) {
								if (s.getFrom() instanceof Dummy && s.getTo() instanceof Dummy) {
									renderer.paintDelay(g2, s, bounds, averageSize, averageSize, s.getColor(),
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
											surfaceFactor, holeFactor);
								} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Dummy) {
									renderer.paintDelay(g2, s, bounds, ((Hole) s.getFrom()).getDiameter(), averageSize,
											s.getColor(),
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
											surfaceFactor, holeFactor);
								} else if (s.getFrom() instanceof Dummy && s.getTo() instanceof Hole) {
									renderer.paintDelay(g2, s, bounds, averageSize, ((Hole) s.getTo()).getDiameter(),
											s.getColor(),
											Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), isSelected,
											surfaceFactor, holeFactor);
								} else if (s.getFrom() instanceof Hole && s.getTo() instanceof Hole) {
									renderer.paintDelay(g2, s, bounds, ((Hole) s.getFrom()).getDiameter(),
											((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()
													* UnitConvert.metersToPixels(1),
											isSelected, surfaceFactor, holeFactor);
								}
							}
						}
					}
					// Draws SurfaceTimes
					if (surfaceTimesOnOffButton.isSelected()) {
						try {
							getSurfaceTimes();
						} catch (NegativeNumberException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (Pattern tempPat : world.getPatternList().values()) {
							for (SurfaceConnector sc : tempPat.getSurfaceList().values()) {
								// boolean isSelected = sc == selected ||
								// (selected instanceof Collection<?> &&
								// ((Collection<?>)selected).contains(sc));
								for (InitiationPoint ip : tempPat.getiPList().values()) {
									if (sc.getTo() instanceof Dummy) {
										renderer.paintSurfaceTimes(g2, sc, ip, sc.getTo(),
												p_surfaceConnectorFiringTimesColour, bounds,
												Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), surfaceFactor,
												holeFactor);
									}
								}
							}
						}
					}

					// GRID RESIZING WITH ZOOM
					if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() > 0.0030) {
						renderer.paintGrid(

								// GRAPHICS OBJECT
								g2,
								// GET THE BOUNDS OF THE AREA TO DRAW IN
								canvasPanel.getBounds(),
								// DRAW LINES PORTION
								new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10)),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10))),
								// DRAW THE TEXT LABELS
								new Rectangle2D.Double(bounds.getX(), bounds.getY(), 10, 10));
					} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.0030
							&& Zoom.getScalingFactor() > 0.002) {
						renderer.paintGrid(
								// GRAPHICS OBJECT
								g2,
								// GET THE BOUNDS OF THE AREA TO DRAW IN
								canvasPanel.getBounds(),
								// DRAW LINES PORTION
								new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(50)),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(50))),
								// DRAW THE TEXT LABELS
								new Rectangle2D.Double(bounds.getX(), bounds.getY(), 50, 50));
					} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.002
							&& Zoom.getScalingFactor() > 0.0001) {
						renderer.paintGrid(
								// GRAPHICS OBJECT
								g2,
								// GET THE BOUNDS OF THE AREA TO DRAW IN
								canvasPanel.getBounds(),
								// DRAW LINES PORTION
								new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(200)),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(200))),
								// DRAW THE TEXT LABELS
								new Rectangle2D.Double(bounds.getX(), bounds.getY(), 200, 200));
					} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.0001
							&& Zoom.getScalingFactor() > 0.00001) {
						renderer.paintGrid(
								// GRAPHICS OBJECT
								g2,
								// GET THE BOUNDS OF THE AREA TO DRAW IN
								canvasPanel.getBounds(),
								// DRAW LINES PORTION
								new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(1000)),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(1000))),
								// DRAW THE TEXT LABELS
								new Rectangle2D.Double(bounds.getX(), bounds.getY(), 1000, 1000));
					} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.00001
							&& Zoom.getScalingFactor() > 0.000001) {
						renderer.paintGrid(
								// GRAPHICS OBJECT
								g2,
								// GET THE BOUNDS OF THE AREA TO DRAW IN
								canvasPanel.getBounds(),
								// DRAW LINES PORTION
								new Rectangle2D.Double(canvasPanel.getBounds().getX(), canvasPanel.getBounds().getY(),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10000)),
										(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10000))),
								// DRAW THE TEXT LABELS
								new Rectangle2D.Double(bounds.getX(), bounds.getY(), 10000, 10000));
					} else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor() < 0.000001) {
						gridOnOffButton.setSelected(false);
						setConsoleOutput("Grid OFF - Default Memory Conservation");
					}
					// PolyMarguee draw
					if (!(polyPointsX.isEmpty() && polyPointsY.isEmpty())) {
						renderer.paintPolyMarquee(g2, polyPointsX, polyPointsY, bounds,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), selectionType);
					}
					// Marguee draw
					boolean selectRect = getToolSelectionMarqueeButton().isSelected();
					boolean selectElipse = getToolSelectionOval().isSelected();
					if (selectionPoint1 != null && selectionPoint2 != null) {
						renderer.paintMarquee(g2, bounds, selectionPoint1, selectionPoint2,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1), selectionType, selectRect,
								selectElipse);
					}
					if (rulePoint1 != null && rulePoint2 != null) {
						renderer.paintRuler(g2, bounds, rulePoint1, rulePoint2,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
					}
					if (bearingPoint1 != null && bearingPoint2 != null) {
						renderer.paintAngleMeasure(g2, bounds, bearingPoint1, bearingPoint2, bearingPoint3,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
					}
					if (zoomPoint1 != null && zoomPoint2 != null) {
						renderer.paintZoomMarquee(g2, bounds, zoomPoint1, zoomPoint2,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1));
					}
					if (xs != null && ys != null) {
						renderer.paintTemporaryPoly(g2, xs, ys, bounds,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1),
								getToolPolyLineButton().isSelected());
					}
					if (tPoint1 != null && tPoint2 != null) {
						renderer.paintTemporaryLine(g2, tPoint1, tPoint2, bounds,
								Zoom.getScalingFactor() * UnitConvert.metersToPixels(1),
								getToolPolyLineButton().isSelected());
					}
				}
			};
			FlowLayout flowLayout = (FlowLayout) canvasPanel.getLayout();
			flowLayout.setHgap(2);
			flowLayout.setVgap(2);
			canvasPanel.setAutoscrolls(true);
			canvasPanel.setFocusable(true);

			/*
			 * DELETE KEY LISTENER FOR ANY BACKSPACE KEY PRESS
			 */
			canvasPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "delete");
			canvasPanel.getActionMap().put("delete", new AbstractAction("delete", null) {
				public void actionPerformed(ActionEvent e) {
					if (selected != null || (selected instanceof Collection<?>)) {
						for (Pattern tempPat : world.getPatternList().values()) { // For
																					// every
																					// pattern
																					// in
																					// the
																					// pattern
																					// list
																					// do
																					// the
																					// following
							for (Dummy temp : tempPat.getAllDummysAndHoles()) {
								// for every dummy hole in tempPat do the
								// followin the above two statements mean that
								// every thing below will be done to every hole
								// or dummy in all patterns on the canvas
								boolean isSelected = temp == selected || (selected instanceof Collection<?>
										&& ((Collection<?>) selected).contains(temp));// isSelected

								if (isSelected) {
									Dummy t = temp;
									tempPat.removeDummyOrHole(t);
									polyPointsX.clear();
									polyPointsY.clear();
									selectionPoint1 = null;
									selectionPoint2 = null;
									getTreeModelPattern();
									updateCanvas();
								}
							}
							for (SurfaceConnector temp : tempPat.getAllSurfaceConnectors()) {
								boolean isSelected = temp == selected || (selected instanceof Collection<?>
										&& ((Collection<?>) selected).contains(temp));

								if (isSelected) {
									SurfaceConnector t = temp;
									tempPat.removeSC(t);
									polyPointsX.clear();
									polyPointsY.clear();
									selectionPoint1 = null;
									selectionPoint2 = null;
									getTreeModelSurface();

									updateCanvas();
								}
							}
							for (Detonator temp : tempPat.getAllDetonators()) {
								boolean isSelected = temp == selected || (selected instanceof Collection<?>
										&& ((Collection<?>) selected).contains(temp));

								if (isSelected) {
									Detonator t = temp;
									tempPat.removeDetonator(t);
									// getTreeModelPattern();
									polyPointsX.clear();
									polyPointsY.clear();
									selectionPoint1 = null;
									selectionPoint2 = null;
									updateCanvas();
								}
							}

							if (tempPat.getHoleList().isEmpty() && tempPat.getDummyList().isEmpty()) {
								world.removePattern(tempPat);
								if (world.getPatternList().isEmpty()) {
									polyPointsX.clear();
									polyPointsY.clear();
									selectionPoint1 = null;
									selectionPoint2 = null;
									getCanvasSize();
								}
							}
						}
						// DELETE KEY PRESSED IF TEXT
						for (Text temp : world.getTextList().values()) {
							boolean isSelected = temp == selected
									|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(temp));
							if (isSelected) {
								Text t = temp;
								world.removeText(t);
								polyPointsX.clear();
								polyPointsY.clear();
								selectionPoint1 = null;
								selectionPoint2 = null;
								getTreeModelText();
								updateCanvas();
							}
						}
						// DELETE KEY PRESSED IF BPOINT
						for (Boundary tempBoundary : world.getBoundaryList().values()) {
							// boolean isSelected = tempBoundary ==
							// selected || (selected instanceof
							// Collection<?> &&
							// ((Collection<?>)selected).contains(tempBoundary));
							if (selected instanceof BPoint) {
								tempBoundary.removeBPoint((BPoint) selected);
							}
							if (tempBoundary.getBPList().size() < 2) {
								// tempBoundary.removeBPoint((BPoint)
								// selected);
								world.removeBoundary(tempBoundary);
								getTreeModelBoundary();
							} else if (selected instanceof Collection) {
								for (Object temp : (Collection) selected) {
									if (temp instanceof BPoint && tempBoundary.getBPList().containsValue(temp)) {
										tempBoundary.removeBPoint((BPoint) temp);
										polyPointsX.clear();
										polyPointsY.clear();
										selectionPoint1 = null;
										selectionPoint2 = null;
									}
								}
							}
							getTreeModelBoundary();
							updateCanvas();
						}
						selected = null;
						selectionPoint1 = selectionPoint2 = null;
						getTreeModelPattern();
						getTreeModelBoundary();
						getTreeModelSurface();
						getTreeModelText();
						updateCanvas();
						selected = new Object();
					} else
						getCanvasSize();
				}
			});
			// canvasPanel.setOpaque(true);
			// canvasPanel.setBackground(preferenceCanvasBackground); //add a
			// preference check to this so the background can change
			canvasPanel.addMouseMotionListener(locationStatusMouseMover);
			canvasPanel.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent e) {
					if (currentMouseMover != null) {
						currentMouseMover.mouseMoved(e);

					}
				}

				public void mouseDragged(MouseEvent e) {
					if (currentMouseMover != null) {
						currentMouseMover.mouseDragged(e);
					}
				}
			});
			canvasPanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (currentMouseClicker != null) {
						currentMouseClicker.mouseClicked(e);
					}
				}

				public void mouseEntered(MouseEvent e) {
					if (currentMouseClicker != null) {
						currentMouseClicker.mouseEntered(e);
					}
				}

				public void mouseExited(MouseEvent e) {
					if (currentMouseClicker != null) {
						currentMouseClicker.mouseExited(e);
					}
				}

				public void mousePressed(MouseEvent e) {
					if (currentMouseClicker != null) {
						currentMouseClicker.mousePressed(e);
					}
				}

				public void mouseReleased(MouseEvent e) {
					if (currentMouseClicker != null) {
						currentMouseClicker.mouseReleased(e);
					}
				}
			});
			// PUT THE DRAWING AREA INTO A SCROLL PANE
			canvasScrollPane = new JScrollPane(canvasPanel);
			// canvas.add(canvasPanel);
			if (world.getBounds() != null) {
				canvasPanel.setSize((int) world.getBounds().getWidth(), (int) world.getBounds().getHeight());
			} else {
				canvasPanel.setSize(canvasScrollPane.getSize());
			}

			// getContentPane().add(canvas); //This adds the canvas to the
			// content twice

		}
		return canvasScrollPane;
	}

	private JToggleButton getToolPolyLineButton() {
		if (toolPolyLineButton == null) {
			toolPolyLineButton = new JToggleButton();
			toolPolyLineButton.setToolTipText("Boundary Polyline Tool");
			toolPolyLineButton.setPreferredSize(dimension30x30);
			toolPolyLineButton.setMinimumSize(dimension30x30);
			toolPolyLineButton.setMaximumSize(new Dimension(30, 30));
			toolPolyLineButton.setFocusable(true);
			toolPolyLineButton.setAlignmentX(0.5f);
			toolPolyLineButton.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/polyLine.png"))));
			toolPolyLineButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					boolean isClosed = false;
					boolean isAnnotated = false;
					labelColourStroke.setText("Stroke");
					getColorWellStroke().setBackground(currentColor);
					currentMouseMover = null;
					boundaryOnOffButton.setSelected(true);

					boundary = new Boundary(0, 0, isClosed, 0, isAnnotated, currentColor, currentFillColor,
							getCurrentStroke());
					try {
						world.addBoundary(boundary, false);
					} catch (ZeroArgumentException e3) {
						e3.printStackTrace();
					} catch (NegativeNumberException e3) {
						e3.printStackTrace();
					}
					currentMouseClicker = new MouseAdapter() {

						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {

							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

							try {
								if (e.getModifiers() != 4) {
									int i = boundary.getNumberOfPointsInBoundary();
									bPoint = new BPoint(boundary.getBoundaryID(), i + 1, easting, northing,
											lastRLHeight);
									System.out.println("Added Point # " + bPoint.getPointID());
									clicked = true;
								}
								if (e.getModifiers() == 4) {
									setStatusBarLabel("Polyline Ended");
									currentMouseMover = defaultMouseMover;
									currentBoundary = null;// boundary;
									tPoint1 = null;
									tPoint2 = null;
									updateCanvas();
								}
							} catch (NumberFormatException e2) {
								System.out.println("getToolBoundaryButton() method - NumberFormatException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - NumberFormatException");
								e2.printStackTrace();
							} catch (ZeroArgumentException e2) {
								System.out.println("getToolBoundaryButton() method - ZeroArgumentException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - ZeroArgumentException");
								e2.printStackTrace();
							} catch (NegativeNumberException e2) {
								System.out.println("getToolBoundaryButton() method - NegativeNumberException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - NegativeNumberException");
								e2.printStackTrace();
							}

							// if (clicked == true) {
							try {
								if (e.getModifiers() != 4) {
									boundary.addBPoint(bPoint, false);
									tPoint1 = bPoint.toPoint3D();
									setStatusBarLabel("Added boundary point: now " + boundary.getBPList().size()
											+ " - ID #" + bPoint.getPointID() + "Boundary ID = "
											+ world.getBoundaryList().size());
								}
								if (e.getModifiers() == 4) {
									setStatusBarLabel("Polyline Ended");
									currentMouseMover = defaultMouseMover;
									currentBoundary = null;// boundary;
									tPoint1 = null;
									tPoint2 = null;
								}
							} catch (NegativeNumberException e1) {
								System.out.println("getToolBoundaryButton() method - NegativeNumberException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - NegativeNumberException");
								e1.printStackTrace();
							} catch (ZeroArgumentException e1) {
								System.out.println("getToolBoundaryButton() method - ZeroArgumentException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - ZeroArgumentException");
								e1.printStackTrace();
							}
							saveAsMenuItem.setEnabled(true);
							updateCanvas();
							getTreeModelBoundary();
							// }
							currentMouseMover = new MouseMotionAdapter() {
								public void mouseMoved(MouseEvent e) {
									double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
											- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
									double easting = getCanvasSize().getX()
											+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
									tPoint2 = new LTPoint3D.Double(easting, northing, lastRLHeight);
									updateCanvas();
								}
							};
							// clicked = false;
						}
					};
					// RIGHT CLICK - CONTROL BUTTON DOWN -
					// LISTENER FOR ENDING THE POLYGON
					if (e.getModifiers() == 4) {
						setStatusBarLabel("Polyline Ended");
						currentMouseMover = defaultMouseMover;
						currentBoundary = null;// boundary;
						tPoint1 = null;
						tPoint2 = null;
					}

				}

			});
		}

		return toolPolyLineButton;
	}

	private JToggleButton getToolSelectionOval() {
		if (toolSelectionOval == null) {
			toolSelectionOval = new JToggleButton();
			toolSelectionOval
					.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/selectCircle.png")));
			toolSelectionOval.setToolTipText("Oval Selection Tool");
			toolSelectionOval.setPreferredSize(dimension30x30);
			toolSelectionOval.setMinimumSize(dimension30x30);
			toolSelectionOval.setMaximumSize(dimension30x30);
			toolSelectionOval.setAlignmentX(0.5f);
			toolSelectionOval.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selectionPoint1 = null;
						selectionPoint2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyCode() == KeyEvent.VK_S) {
						statusBarLabel.setText("Surface Connectors selection only");
						isVKSDown = true;
						selectionType = "Selecting Surface Connections";
					} else if (e.getKeyCode() == KeyEvent.VK_H) {
						statusBarLabel.setText("Hole selection only");
						isVKHDown = true;
						selectionType = "Selecting Holes";
					} else if (e.getKeyCode() == KeyEvent.VK_D) {
						statusBarLabel.setText("Detonator selection only");
						isVKDDown = true;
						selectionType = "Selecting Detonators";
					} else if (e.getKeyCode() == KeyEvent.VK_T) {
						statusBarLabel.setText("Text Label selection only");
						isVKTDown = true;
						selectionType = "Selecting Text";
					} else if (e.getKeyCode() == KeyEvent.VK_B) {
						statusBarLabel.setText("Boundary Point selection only");
						isVKBDown = true;
						selectionType = "Selecting Boundry Points";
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						statusBarLabel.setText("Dummy selection only");
						isVKXDown = true;
						selectionType = "Selecting Dummy Holes";
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						// change mouse cursor to a marquee cursor with a plus
						// symbol
					} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
						// change mouse cursor to a marquee cursor with a minus
						// symbol
					} else {
						selectionType = "Selecting All";
					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						selectionPoint1 = null;
						selectionPoint2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();
					} else if (e.getKeyCode() == KeyEvent.VK_S) {
						isVKSDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_H) {

						isVKHDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_D) {
						isVKDDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_T) {
						isVKTDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_B) {
						isVKBDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_X) {
						isVKXDown = false;
						selectionType = "Selecting All";
					} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
						// change mouse cursor to a default cursor
					} else if (e.getKeyCode() == KeyEvent.VK_ALT) {
						// change mouse cursor to a default cursor
					}

				}

			});
			toolSelectionOval.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					// setCursor(marqueeToolCursor);
					setStatusBarLabel("Click in Screen");

					currentMouseClicker = new MouseAdapter() {

						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {

							if (!clicked) {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								selectionPoint1 = new Point2D.Double(easting, northing);
								setStatusBarLabel("Last Northing = "
										+ Double.parseDouble(decimalFormat4.format(selectionPoint1.getY()))
										+ " & Last Easting = "
										+ Double.parseDouble(decimalFormat4.format(selectionPoint1.getX())));
								currentMouseMover = new MouseMotionAdapter() {
									public void mouseMoved(MouseEvent e) {

										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
										selectionPoint2 = new Point2D.Double(easting, northing);

										if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
												|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
														.isEmpty())
												&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
												&& !isVKXDown) {

											selected = world.getAllObjectsIn(new Ellipse2D.Double(
													Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
															selectionPoint1.getY(), selectionPoint2.getY()),
													Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
															.abs(selectionPoint1.getY() - selectionPoint2.getY())));
											updateCanvas();
										}
										if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
												|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
														.isEmpty())
												&& isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
												&& !isVKXDown) {

											selected = world.getAllSurfaceIn(new Ellipse2D.Double(
													Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
															selectionPoint1.getY(), selectionPoint2.getY()),
													Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
															.abs(selectionPoint1.getY() - selectionPoint2.getY())));
											updateCanvas();
										}
										if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
												|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
														.isEmpty())
												&& !isVKSDown && isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
												&& !isVKXDown) {

											selected = world.getAllBPointsIn(new Ellipse2D.Double(
													Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
															selectionPoint1.getY(), selectionPoint2.getY()),
													Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
															.abs(selectionPoint1.getY() - selectionPoint2.getY())));
											updateCanvas();
										}
										if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
												|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
														.isEmpty())
												&& !isVKSDown && !isVKBDown && isVKHDown && !isVKDDown && !isVKTDown
												&& !isVKXDown) {

											selected = world.getAllHolesIn(new Ellipse2D.Double(
													Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
															selectionPoint1.getY(), selectionPoint2.getY()),
													Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
															.abs(selectionPoint1.getY() - selectionPoint2.getY())));
											updateCanvas();
										}
										if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
												|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
														.isEmpty())
												&& !isVKSDown && !isVKBDown && !isVKHDown && isVKDDown && !isVKTDown
												&& !isVKXDown) {

											selected = world.getAllDetonatorsIn(new Ellipse2D.Double(
													Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
															selectionPoint1.getY(), selectionPoint2.getY()),
													Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
															.abs(selectionPoint1.getY() - selectionPoint2.getY())));
											updateCanvas();
										}
										if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
												|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
														.isEmpty())
												&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && isVKTDown
												&& !isVKXDown) {

											selected = world.getAllTextIn(new Ellipse2D.Double(
													Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
															selectionPoint1.getY(), selectionPoint2.getY()),
													Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
															.abs(selectionPoint1.getY() - selectionPoint2.getY())));
											updateCanvas();
										}
										if ((!world.getPatternList().isEmpty() || !world.getTextList().isEmpty()
												|| !world.getBoundaryList().isEmpty() || !world.getCoordList()
														.isEmpty())
												&& !isVKSDown && !isVKBDown && !isVKHDown && !isVKDDown && !isVKTDown
												&& isVKXDown) {

											selected = world.getAllDummysIn(new Ellipse2D.Double(
													Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
															selectionPoint1.getY(), selectionPoint2.getY()),
													Math.abs(selectionPoint1.getX() - selectionPoint2.getX()), Math
															.abs(selectionPoint1.getY() - selectionPoint2.getY())));
											updateCanvas();
										}
										setStatusBarLabel("Last Northing = "
												+ Double.parseDouble(decimalFormat4.format(selectionPoint2.getY()))
												+ " & Last Easting = "
												+ Double.parseDouble(decimalFormat4.format(selectionPoint2.getX())));

									}
								};

							} else {
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								selectionPoint2 = new Point2D.Double(easting, northing);

								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world.getAllObjectsIn(new Ellipse2D.Double(
											Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
													selectionPoint1.getY(), selectionPoint2.getY()),
											Math.abs(selectionPoint1.getX() - selectionPoint2.getX()),
											Math.abs(selectionPoint1
													.getY() - selectionPoint2.getY())));
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world.getAllSurfaceIn(new Ellipse2D.Double(
											Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
													selectionPoint1.getY(), selectionPoint2.getY()),
											Math.abs(selectionPoint1.getX() - selectionPoint2.getX()),
											Math.abs(selectionPoint1
													.getY() - selectionPoint2.getY())));
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world.getAllBPointsIn(new Ellipse2D.Double(
											Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
													selectionPoint1.getY(), selectionPoint2.getY()),
											Math.abs(selectionPoint1.getX() - selectionPoint2.getX()),
											Math.abs(selectionPoint1
													.getY() - selectionPoint2.getY())));
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && isVKHDown
										&& !isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world.getAllHolesIn(new Ellipse2D.Double(
											Math.min(selectionPoint1.getX(), selectionPoint2.getX()),
											Math.min(selectionPoint1.getY(),
													selectionPoint2.getY()),
											Math.abs(selectionPoint1.getX() - selectionPoint2.getX()),
											Math.abs(selectionPoint1.getY()
													- selectionPoint2.getY())));
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& isVKDDown && !isVKTDown && !isVKXDown) {

									selected = world.getAllDetonatorsIn(new Ellipse2D.Double(
											Math.min(selectionPoint1.getX(), selectionPoint2.getX()), Math.min(
													selectionPoint1.getY(), selectionPoint2.getY()),
											Math.abs(selectionPoint1.getX() - selectionPoint2.getX()),
											Math.abs(selectionPoint1
													.getY() - selectionPoint2.getY())));
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && isVKTDown && !isVKXDown) {

									selected = world.getAllTextIn(new Ellipse2D.Double(
											Math.min(selectionPoint1.getX(), selectionPoint2.getX()),
											Math.min(selectionPoint1.getY(),
													selectionPoint2.getY()),
											Math.abs(selectionPoint1.getX() - selectionPoint2.getX()),
											Math.abs(selectionPoint1.getY()
													- selectionPoint2.getY())));
									updateCanvas();
								}
								if (!world.getPatternList().isEmpty() && !isVKSDown && !isVKBDown && !isVKHDown
										&& !isVKDDown && !isVKTDown && isVKXDown) {
									selected = null;
									selected = world.getAllDummysIn(new Ellipse2D.Double(
											Math.min(selectionPoint1.getX(), selectionPoint2.getX()),
											Math.min(selectionPoint1.getY(),
													selectionPoint2.getY()),
											Math.abs(selectionPoint1.getX() - selectionPoint2.getX()),
											Math.abs(selectionPoint1.getY()
													- selectionPoint2.getY())));
									updateCanvas();
								}
								setStatusBarLabel("Selected: " + ((Collection<?>) selected).size() + " objects(s)");
								currentMouseMover = defaultMouseMover;
								selectionPoint1 = null;
								selectionPoint2 = null;
							}
							clicked = !clicked;
						}
					};
				}
			});

		}
		return toolSelectionOval;
	}

	int clickCounter = 0;
	private JToggleButton toolDimensionArrow;

	private JToggleButton getToolRulerBearing() {
		if (toolBearingMeasure == null) {
			toolBearingMeasure = new JToggleButton();
			toolBearingMeasure.setToolTipText("Angle Measure Tool");
			toolBearingMeasure.setPreferredSize(dimension30x30);
			toolBearingMeasure.setMinimumSize(dimension30x30);
			toolBearingMeasure.setMaximumSize(dimension30x30);
			toolBearingMeasure.setAlignmentX(0.5f);
			toolBearingMeasure
					.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/bearingMeasure.png")));

			toolBearingMeasure.addActionListener(new java.awt.event.ActionListener() {
				double d1;
				double d2;
				double b1;

				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;

					// setCursor(new java.awt.Cursor(
					// java.awt.Cursor.CROSSHAIR_CURSOR));
					setStatusBarLabel("Click in Screen");
					currentMouseClicker = new MouseAdapter() {
						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {
							if (clickCounter == 0) {

								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

								bearingPoint1 = new Point2D.Double(easting, northing);
								// System.out.println("I was clicked at - "
								// + northing + " & " + easting);
								updateCanvas();
								setStatusBarLabel("Last Northing = "
										+ Double.parseDouble(decimalFormat4.format(bearingPoint1.getY()))
										+ " & Last Easting = "
										+ Double.parseDouble(decimalFormat4.format(bearingPoint1.getX())));

								currentMouseMover = new MouseMotionAdapter() {

									public void mouseMoved(MouseEvent e) {

										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
										bearingPoint2 = new Point2D.Double(easting, northing);
										bearingPoint3 = bearingPoint1;

										updateCanvas();

										setStatusBarLabel("Last Northing = "
												+ Double.parseDouble(decimalFormat4.format(bearingPoint2.getY()))
												+ " & Last Easting = "
												+ Double.parseDouble(decimalFormat4.format(bearingPoint2.getX())));
										if (bearingPoint1 != null && bearingPoint2 != null) {
											double xl = (bearingPoint1.getX() - bearingPoint2.getX());
											double yl = (bearingPoint1.getY() - bearingPoint2.getY());
											d1 = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));

											measurementStatusLabel1.setText(decimalFormatMetres2.format(d1));

										}

									}
								};

								clickCounter = 1;
							} else if (clickCounter == 1) {
								System.out.println(clickCounter);
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								bearingPoint2 = new Point2D.Double(easting, northing);

								currentMouseMover = null;
								currentMouseMover = new MouseMotionAdapter() {
									public void mouseMoved(MouseEvent e) {

										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
										bearingPoint3 = new Point2D.Double(easting, northing);

										updateCanvas();

										setStatusBarLabel("Last Northing = "
												+ Double.parseDouble(decimalFormat4.format(bearingPoint2.getY()))
												+ " & Last Easting = "
												+ Double.parseDouble(decimalFormat4.format(bearingPoint2.getX())));
										if (bearingPoint2 != null && bearingPoint3 != null) {
											double xl = (bearingPoint2.getX() - bearingPoint3.getX());
											double yl = (bearingPoint2.getY() - bearingPoint3.getY());
											d2 = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));

											measurementStatusLabel2.setText(decimalFormatMetres2.format(d2));
										}

									}
								};

								clickCounter = 2;
							} else if (clickCounter == 2) {
								System.out.println(clickCounter);
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								bearingPoint3 = new Point2D.Double(easting, northing);
								if (bearingPoint1 != null && bearingPoint2 != null && bearingPoint3 != null) {
									double xl = (bearingPoint2.getX() - bearingPoint3.getX());
									double yl = (bearingPoint2.getY() - bearingPoint3.getY());
									d2 = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));

									// measurementStatusLabel1.setText(measurementStatusLabel2.getText());
									// measurementStatusLabel2.setText(dec2pM.format(d));
									updateCanvas();
								}
								setStatusBarLabel("Last Northing = "
										+ Double.parseDouble(decimalFormat4.format(bearingPoint3.getY()))
										+ " & Last Easting = "
										+ Double.parseDouble(decimalFormat4.format(bearingPoint3.getX())));

								setConsoleOutput(
										consoleOutput + "\n" + "Distance 1 = " + (decimalFormatMetres2.format(d1))
												+ "\nDistance 2 = " + (decimalFormatMetres2.format(d2)));

								currentMouseMover = defaultMouseMover;
								clickCounter = 0;
							}

							clicked = !clicked;

						}

					};

				}
			});
			toolBearingMeasure.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						bearingPoint2 = null;
						bearingPoint1 = null;
						bearingPoint3 = null;
						selectedFIFO = null;
						selected = null;
						clickCounter = 0;
						System.out.println(clickCounter);
						// selected = null;
						updateCanvas();

					}
				}
			});
		}
		return toolBearingMeasure;
	}

	private JToggleButton getToolDimensionArrowButton() {
		if (toolDimensionArrow == null) {
			toolDimensionArrow = new JToggleButton();
			toolDimensionArrow.setToolTipText("Dimension Arrow Tool");
			toolDimensionArrow.setPreferredSize(dimension30x30);
			toolDimensionArrow.setMinimumSize(dimension30x30);
			toolDimensionArrow.setMaximumSize(dimension30x30);
			toolDimensionArrow.setAlignmentX(0.5f);
			toolDimensionArrow.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/dimensionArrow.png"))));

			toolDimensionArrow.addActionListener(new java.awt.event.ActionListener() {// Action

				public void actionPerformed(java.awt.event.ActionEvent e) {

					currentMouseMover = null;
					boundaryOnOffButton.setSelected(true);

					boundary = new Boundary(0, 0, false, 2, false, currentColor, currentFillColor, getCurrentStroke());

					// boundary.setArrow(3);
					boundary.setAnnotated(false);
					// System.out.println("Arrow Type "+boundary.getArrow());

					toolDimensionArrow.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent e) {
							if (e.getKeyChar() == '2') {
								boundary.setArrow(2);
								boundary.setAnnotated(true);
							} else if (e.getKeyChar() == '4') {
								boundary.setArrow(2);
								boundary.setAnnotated(false);
							} else if (e.getKeyChar() == '1') {
								boundary.setArrow(1);
								boundary.setAnnotated(false);
							} else if (e.getKeyChar() == '3') {
								boundary.setArrow(1);
								boundary.setAnnotated(true);
							} else {
								boundary.setArrow(2);
								boundary.setAnnotated(true);
							}

						}

					});

					try {
						world.addBoundary(boundary, false);

					} catch (ZeroArgumentException err1) {
						// TODO Auto-generated catch block
						err1.printStackTrace();
					} catch (NegativeNumberException err2) {
						// TODO Auto-generated catch block
						err2.printStackTrace();
					}
					currentMouseClicker = new MouseAdapter() {

						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {
							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
							try {
								if (boundary.getNumberOfPointsInBoundary() < 2) {
									int i = boundary.getNumberOfPointsInBoundary();
									bPoint = new BPoint(boundary.getBoundaryID(), i + 1, easting, northing,
											lastRLHeight);
									System.out.println("Added Point # " + bPoint.getPointID());
									clicked = true;
								}
							} catch (NumberFormatException e2) {
								System.out.println("getToolBoundaryButton() method - NumberFormatException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - NumberFormatException");
								e2.printStackTrace();
							} catch (ZeroArgumentException e2) {
								System.out.println("getToolBoundaryButton() method - ZeroArgumentException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - ZeroArgumentException");
								e2.printStackTrace();
							} catch (NegativeNumberException e2) {
								System.out.println("getToolBoundaryButton() method - NegativeNumberException");
								JOptionPane.showMessageDialog(LiteTieTRIAL.this,
										"getToolBoundaryButton() method - NegativeNumberException");
								e2.printStackTrace();
							}

							if (clicked == true && boundary.getNumberOfPointsInBoundary() < 2) {
								try {
									boundary.addBPoint(bPoint, false);

									currentBoundary = null;// boundary;
								} catch (NegativeNumberException e1) {
									System.out.println("getToolBoundaryButton() method - NegativeNumberException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolBoundaryButton() method - NegativeNumberException");
									e1.printStackTrace();
								} catch (ZeroArgumentException e1) {
									System.out.println("getToolBoundaryButton() method - ZeroArgumentException");
									JOptionPane.showMessageDialog(LiteTieTRIAL.this,
											"getToolBoundaryButton() method - ZeroArgumentException");
									e1.printStackTrace();
								}
								saveAsMenuItem.setEnabled(true);
								updateCanvas();
								getTreeModelBoundary();
							}
							clicked = false;

						}

					};

				}

			});

		}

		return toolDimensionArrow;
	}

	private JToggleButton getToggleButton_1() {
		if (toggleButton_1 == null) {
			toggleButton_1 = new JToggleButton();
			toggleButton_1.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/insertPoint.png")));
			toggleButton_1.setToolTipText("Boundary Polyline Tool");
			toggleButton_1.setPreferredSize(dimension30x30);
			toggleButton_1.setMinimumSize(dimension30x30);
			toggleButton_1.setMaximumSize(dimension30x30);
			toggleButton_1.setFocusable(true);
			toggleButton_1.setAlignmentX(0.5f);
		}
		return toggleButton_1;
	}

	private JToggleButton getToolPolyPattern() {
		if (toolPolyPattern == null) {
			toolPolyPattern = new JToggleButton();
			toolPolyPattern.setIcon(new ImageIcon(LiteTieTRIAL.class.getResource("/icons_LiteTie_v2/polypattern.png")));
			toolPolyPattern.setToolTipText("Poly Pattern Tool");
			toolPolyPattern.setPreferredSize(dimension30x30);
			toolPolyPattern.setMinimumSize(dimension30x30);
			toolPolyPattern.setMaximumSize(dimension30x30);
			toolPolyPattern.setAlignmentX(0.5f);

			toolPolyPattern.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					// setCursor(patternToolCursor);
					setStatusBarLabel("Select a Point of a Boundary");
					clickCounter = 0;

					currentMouseClicker = new MouseAdapter() {

						Point2D point1 = null;
						Point2D point2 = null;
						Point2D point3 = null;
						Point2D point4 = null;
						Coordinate referencePoint = null;
						Coordinate orientationPoint1 = null;
						Coordinate orientationPoint2 = null;
						Boundary extents = null;
						boolean clicked = false;

						@Override
						public void mouseReleased(MouseEvent e) {
							clickCounter++;
							if (clickCounter == 1) {
								statusBarLabel.setText("CLICK THE BOUNDARY TO PLACE THE PATTERN IN");
								point1 = new Point2D.Double(
										getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor(),
										getCanvasSize().getY()
												+ getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor());

								extents = world.getBoundaryInShape(new Rectangle2D.Double(point1.getX() - radius,
										point1.getY() - radius, radius * 2, radius * 2));
								selected = extents;
								updateCanvas();
								statusBarLabel.setText("BOUNDARY OBJECT SELECTED");

								System.out.print(clickCounter);
							}
							if (clickCounter == 2) {
								statusBarLabel.setText("CLICK THE FIRST ORIENTATION POINT");
								point2 = new Point2D.Double(
										getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor(),
										getCanvasSize().getY()
												+ getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor());

								orientationPoint1 = world
										.getCoordInShape(new Rectangle2D.Double(point2.getX() - (radius * 2),
												point2.getY() - (radius * 2), radius * 4, radius * 4));
								tPoint1 = new LTPoint3D.Double(orientationPoint1.getX(), orientationPoint1.getY(),
										lastRLHeight);
								tPoint2 = new LTPoint3D.Double(orientationPoint1.getX(), orientationPoint1.getY(),
										lastRLHeight);
								selected = orientationPoint1;
								updateCanvas();

								System.out.print("Clicked " + clickCounter + " times\n");
								currentMouseMover = new MouseMotionAdapter() {
									public void mouseMoved(MouseEvent e) {

										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
										tPoint2 = new LTPoint3D.Double(easting, northing, lastRLHeight);

										updateCanvas();
									}
								};
							}

							if (clickCounter == 3) {
								statusBarLabel.setText("CLICK THE SECOND ORIENTATION POINT");
								point3 = new Point2D.Double(
										getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor(),
										getCanvasSize().getY()
												+ getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor());

								orientationPoint2 = world
										.getCoordInShape(new Rectangle2D.Double(point3.getX() - (radius * 2),
												point3.getY() - (radius * 2), radius * 4, radius * 4));
								selected = orientationPoint2;

								updateCanvas();
								statusBarLabel.setText("ORIENTATION OBJECTS SELECTED");

								System.out.print("Clicked " + clickCounter + " times\n");
								currentMouseMover = null;
							}

							if (clickCounter == 4) {
								statusBarLabel.setText("SELECT A COORDINATE FOR REFERENCE");
								point4 = new Point2D.Double(
										getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor(),
										getCanvasSize().getY()
												+ getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor());

								referencePoint = world
										.getCoordInShape(new Rectangle2D.Double(point4.getX() - (radius * 2),
												point4.getY() - (radius * 2), radius * 4, radius * 4));
								selected = referencePoint;
								updateCanvas();
								statusBarLabel.setText("REFERENCE POINT SELECTED [X=" + referencePoint.getX() + ";Y="
										+ referencePoint.getY() + "]");

								System.out.print("Clicked " + clickCounter + " times\n");
								tPoint1 = tPoint2 = null;

								try {
									int rhs = 0;
									int state1 = 0;
									int state2 = 0;
									int state3 = 0;
									int state4 = 0;
									Scanner readPrefs = new Scanner(new File("patPrefs.ltpf"));

									readPrefs.nextLine(); // 1
									readPrefs.nextLine(); // 2
									double diameter = Double.parseDouble(readPrefs.nextLine()); // 3
									double length = Double.parseDouble(readPrefs.nextLine()); // 4
									double bench = Double.parseDouble(readPrefs.nextLine()); // 5
									double bearing = Double.parseDouble(readPrefs.nextLine()); // 6
									double z = Double.parseDouble(readPrefs.nextLine()); // 7
									double flr = Double.parseDouble(readPrefs.nextLine()); // 8
									double toe = Double.parseDouble(readPrefs.nextLine()); // 9
									double angle = Double.parseDouble(readPrefs.nextLine()); // 10
									double sub = Double.parseDouble(readPrefs.nextLine()); // 11
									int shape = Integer.parseInt(readPrefs.nextLine()); // 12
									Color color = Color.decode(readPrefs.nextLine()); // 13
									String patterntype = (readPrefs.nextLine()); // 14
									double burden = Double.parseDouble(readPrefs.nextLine()); // 15
									double spacing = Double.parseDouble(readPrefs.nextLine()); // 16
									readPrefs.nextLine(); // 17
									readPrefs.nextLine(); // 18

									state1 = (int) ((extents.getPolyBounds().getBounds2D().getHeight()) / burden);
									state2 = (int) ((extents.getPolyBounds().getBounds2D().getWidth()) / burden);
									state3 = (int) ((extents.getPolyBounds().getBounds2D().getHeight()) / spacing);
									state4 = (int) ((extents.getPolyBounds().getBounds2D().getWidth()) / spacing);

									if (state1 > state2 && state1 > state3 && state1 > state4) {
										rhs = state1;
									} else if (state2 > state1 && state2 > state3 && state2 > state4) {
										rhs = state2;
									} else if (state3 > state1 && state3 > state2 && state3 > state4) {
										rhs = state3;
									} else if (state4 > state1 && state4 > state2 && state4 > state3) {
										rhs = state4;
									}
									int rows = rhs; // 17
									int holeinrow = rhs; // 18
									readPrefs.nextLine(); // 19
									double orientation = calcs.calculateBearing(orientationPoint1, orientationPoint2); // 19
									System.out.println("Orientation = " + orientation);
									boolean bToO = Boolean.parseBoolean(readPrefs.nextLine());
									String type = (readPrefs.nextLine()); // 21

									try {
										pattern = PatternTemplate.createPatternInBoundary(extents,
												patterntype.toUpperCase(), referencePoint.getX(), referencePoint.getY(),
												z, "",
												"", "", diameter, length, bench, orientation + 90, flr, toe, angle, sub,
												shape, color, burden, spacing, rows, holeinrow,
												orientation);

										statusBarLabel.setText(
												"PATTERN CREATED [Total Holes=" + pattern.getNumberOfHoles() + "]");

										currentPattern = pattern;
										try {
											world.addPattern(pattern, false);
										} catch (ZeroArgumentException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										} catch (NegativeNumberException e1) {
											// TODO Auto-generated catch
											// block
											e1.printStackTrace();
										} // patternList.add(shot);

										saveAsMenuItem.setEnabled(true);
										saveAsButton.setEnabled(true);
										point1 = null;
										point2 = null;
										point3 = null;
										point4 = null;
										referencePoint = null;
										orientationPoint1 = null;
										orientationPoint2 = null;
										selected = null;
										extents = null;
										clickCounter = 1;
										getTreeModelPattern();
										System.out.println("World node count =" + getTreeModelPattern().getModel()
												.getChildCount(patternTreeModel.getRoot()));

									} catch (NumberFormatException e1) {
										System.out.println("getToolPatternButton() method - NumberFormatException");
										JOptionPane.showMessageDialog(LiteTieTRIAL.this,
												"getToolPatternButton() method - NumberFormatException");
										e1.printStackTrace();
									} catch (IllegalArgumentException e1) {
										System.out.println("getToolPatternButton() method - IllegalArgumentException");
										JOptionPane.showMessageDialog(LiteTieTRIAL.this,
												"getToolPatternButton() method - IllegalArgumentException");
										e1.printStackTrace();
									} catch (NegativeNumberException e1) {
										System.out.println("getToolPatternButton() method - NegativeNumberException");
										JOptionPane.showMessageDialog(LiteTieTRIAL.this,
												"getToolPatternButton() method - NegativeNumberException");
										e1.printStackTrace();
									} catch (ZeroArgumentException e1) {
										System.out.println("getToolPatternButton() method - ZeroArgumentException");
										JOptionPane.showMessageDialog(LiteTieTRIAL.this,
												"getToolPatternButton() method - ZeroArgumentException");
										e1.printStackTrace();
									}
								} catch (FileNotFoundException f) {
									JOptionPane.showMessageDialog(rootPane, f);
								}
							}

						}
					};
					currentMouseMover = defaultMouseMover;
					selected = null;
				}
			});

		}
		return toolPolyPattern;
	}

	private JSpinner getSpinnerStrokeWidth() {
		if (spinnerStrokeWidth == null) {
			spinnerStrokeWidth = new JSpinner();
			spinnerStrokeWidth.setMaximumSize(new Dimension(32767, 25));
			spinnerStrokeWidth.setPreferredSize(new Dimension(28, 25));
			spinnerStrokeWidth.setMinimumSize(new Dimension(28, 25));
			spinnerStrokeWidth.setFont(new Font("Dialog", Font.PLAIN, 12));
			spinnerStrokeWidth.setValue(1);
			spinnerStrokeWidth.setModel(lineSpinnerModel);

			spinnerStrokeWidth.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getSpinnerStrokeWidth().getValue().toString());
						getSpinnerStrokeWidth().setValue(i);
						strokeWidth = i;

						getSpinnerStrokeWidth().setForeground(Color.black);
						return true;
					} catch (NumberFormatException nfe) {
						getSpinnerStrokeWidth().setForeground(Color.red);
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"The value in for the line/border width\n in the Spinner cannot be parsed.\nThe default value of 1 has been returned.");
						spinnerStrokeWidth.setValue(1);
						System.out.println("Caught - NumberFormatException");
						return false;
					}
				}
			});
			ChangeListener listener = new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					for (Boundary temp : world.getBoundaryList().values()) {
						boolean isSelected = temp == selected
								|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(temp));
						if (isSelected) {
							temp.setStroke(getCurrentStroke());
						}
						updateCanvas();
					}
					getTreeModelBoundary();
					updateCanvas();
				}
			};
			spinnerStrokeWidth.addChangeListener(listener);
		}
		return spinnerStrokeWidth;
	}

	private JSpinner getSpinnerStrokeDash() {
		if (spinnerStrokeDash == null) {
			spinnerStrokeDash = new JSpinner();
			spinnerStrokeDash.setPreferredSize(new Dimension(28, 25));
			spinnerStrokeDash.setMinimumSize(new Dimension(28, 25));
			spinnerStrokeDash.setFont(new Font("Dialog", Font.PLAIN, 12));
			spinnerStrokeDash.setModel(dashSpinnerModel);
			spinnerStrokeDash.setValue(0);
			spinnerStrokeDash.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getSpinnerStrokeDash().getValue().toString());
						getSpinnerStrokeDash().setValue(i);
						dashLength = i;
						getSpinnerStrokeDash().setForeground(Color.black);
						return true;
					} catch (NumberFormatException nfe) {
						getSpinnerStrokeDash().setForeground(Color.red);
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"The value in for the dash spacing\n in the Spinner cannot be parsed.\nThe default value of 0 has been returned.");
						spinnerStrokeDash.setValue(1);
						System.out.println("Caught - NumberFormatException");
						return false;
					}
				}
			});
			ChangeListener listener = new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					for (Boundary temp : world.getBoundaryList().values()) {
						boolean isSelected = temp == selected
								|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(temp));
						if (isSelected) {
							temp.setStroke(getCurrentStroke());
						}
						updateCanvas();
					}
					getTreeModelBoundary();
					updateCanvas();

				}

			};

			spinnerStrokeDash.addChangeListener(listener);
		}
		return spinnerStrokeDash;
	}

	private JLabel getLblType() {
		if (lblType == null) {
			lblType = new JLabel();
			lblType.setText("Dash");
			lblType.setHorizontalAlignment(SwingConstants.CENTER);
			lblType.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		}
		return lblType;
	}

	private JLabel getLblDesign() {
		if (lblDesign == null) {
			lblDesign = new JLabel();
			lblDesign.setText("Design");
			lblDesign.setHorizontalAlignment(SwingConstants.CENTER);
			lblDesign.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		}
		return lblDesign;
	}

	private JPanel getColorWellFill() {
		if (colorWellFill == null) {
			colorWellFill = new JPanel() {
				public void paintComponent(Graphics g) {
					if (labelColourStroke.getText() != "Stroke") {
						g.setColor(Color.WHITE);
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.RED);
						g.drawLine(2, 2, 21, 21);
						g.drawLine(2, 21, 21, 2);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 21, 21);
					} else if (colorWellFill.getBackground().getAlpha() < 255) {
						g.setColor(Color.GRAY);
						g.fillRect(2, 2, 6, 6);
						g.fillRect(14, 2, 6, 6);
						g.fillRect(8, 8, 6, 6);
						g.fillRect(2, 14, 6, 6);
						g.fillRect(14, 14, 6, 6);
						g.setColor(colorWellFill.getBackground());
						g.fillRect(0, 0, 22, 22);
						g.setColor(Color.BLACK);
						g.drawRect(0, 0, 21, 21);
					} else {
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
				@Override
				public void mouseClicked(MouseEvent e) {
					currentFillColor = JColorChooser.showDialog(canvasPanel, "Change Current Colour",
							getColorWellFill().getBackground());
					if (currentFillColor != null) {
						getColorWellFill().setBackground(currentFillColor);
					}

					for (Boundary temp : world.getBoundaryList().values()) {
						boolean isSelected = temp == selected
								|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(temp));
						if (isSelected) {
							temp.setFillColor(currentFillColor);
						}
						updateCanvas();
					}
					getTreeModelBoundary();
					updateCanvas();

				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseReleased(MouseEvent e) {
				}

				public void mouseEntered(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
				}
			});
			colorWellFill.setToolTipText("Change a Fill Colour");

			colorWellFill.setBackground(currentFillColor);
			updateCanvas();

			colorWellFill.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}

				public void keyReleased(java.awt.event.KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {

						dummy1 = null;
						dummy2 = null;
						selected = null;
						selectedFIFO = null;
						updateCanvas();

					}
				}
			});
		}
		return colorWellFill;
	}

	private JLabel getLblFill() {
		if (lblFill == null) {
			lblFill = new JLabel();
			lblFill.setText("Fill");
			lblFill.setHorizontalAlignment(SwingConstants.CENTER);
			lblFill.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		}
		return lblFill;
	}

	private JSpinner getSpinnerSpaceDash() {
		if (spinnerSpaceDash == null) {
			spinnerSpaceDash = new JSpinner();
			spinnerSpaceDash.setPreferredSize(new Dimension(28, 25));
			spinnerSpaceDash.setMinimumSize(new Dimension(28, 25));
			spinnerSpaceDash.setFont(new Font("Dialog", Font.PLAIN, 12));
			spinnerSpaceDash.setModel(spaceSpinnerModel);
			spinnerSpaceDash.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						int i = Integer.parseInt(getSpinnerSpaceDash().getValue().toString());
						getSpinnerSpaceDash().setValue(i);
						spaceLength = i;
						getSpinnerSpaceDash().setForeground(Color.black);
						return true;
					} catch (NumberFormatException nfe) {
						getSpinnerSpaceDash().setForeground(Color.red);
						JOptionPane
								.showMessageDialog(LiteTieTRIAL.this,
										"The value in for the space\n in the Spinner cannot be parsed.\nThe default value of 0 has been returned.");
						spinnerSpaceDash.setValue(1);
						System.out.println("Caught - NumberFormatException");
						return false;
					}
				}
			});
			ChangeListener listener = new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					for (Boundary temp : world.getBoundaryList().values()) {
						boolean isSelected = temp == selected
								|| (selected instanceof Collection<?> && ((Collection<?>) selected).contains(temp));
						if (isSelected) {
							temp.setStroke(getCurrentStroke());
						}
						updateCanvas();
					}
					getTreeModelBoundary();
					updateCanvas();
				}
			};
			spinnerSpaceDash.addChangeListener(listener);
		}
		return spinnerSpaceDash;
	}

	private JLabel getLblSpace() {
		if (lblSpace == null) {
			lblSpace = new JLabel();
			lblSpace.setText("Space");
			lblSpace.setHorizontalAlignment(SwingConstants.CENTER);
			lblSpace.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		}
		return lblSpace;
	}

	private JCheckBox getCheckBoxLockSpaceAndDash() {
		if (checkBoxLockSpaceAndDash == null) {
			checkBoxLockSpaceAndDash = new JCheckBox("Locked");
			checkBoxLockSpaceAndDash.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
			checkBoxLockSpaceAndDash.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (checkBoxLockSpaceAndDash.isSelected()) {
						checkBoxLockSpaceAndDash.setForeground(Color.RED);
						checkBoxLockSpaceAndDash.setText("Locked");
						getSpinnerSpaceDash().setModel(dashSpinnerModel);
						// getSpinnerSpaceDash().setForeground(Color.RED);
						getSpinnerSpaceDash().setEnabled(false);
					} else if (!(checkBoxLockSpaceAndDash.isSelected())) {
						checkBoxLockSpaceAndDash.setForeground(Color.GRAY);
						checkBoxLockSpaceAndDash.setText("Locked");
						getSpinnerSpaceDash().setModel(spaceSpinnerModel);
						// getSpinnerSpaceDash().setForeground(Color.BLACK);
						getSpinnerSpaceDash().setEnabled(true);
					}
				}
			});
		}
		return checkBoxLockSpaceAndDash;
	}

	private JToolBar getToolBarMeasurementDetails() {
		if (toolBarMeasurementDetails == null) {
			toolBarMeasurementDetails = new JToolBar();

			xStatusLabel = new JLabel();
			toolBarMeasurementDetails.add(xStatusLabel);
			xStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			xStatusLabel.setText("X Value = ");
			xStatusLabel.setPreferredSize(new Dimension(80, 15));
			xStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
			xStatusLabel.setComponentOrientation(ComponentOrientation.UNKNOWN);
			xStatusLabel.setBackground(new Color(255, 214, 64));
			xStatusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			xStatusLabel.setFont(new Font("Lucida Grande", Font.BOLD, 10));

			yStatusLabel = new JLabel();
			toolBarMeasurementDetails.add(yStatusLabel);
			yStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			yStatusLabel.setText("Y Value = ");
			yStatusLabel.setPreferredSize(new Dimension(80, 15));
			yStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
			yStatusLabel.setComponentOrientation(ComponentOrientation.UNKNOWN);
			yStatusLabel.setBackground(new Color(255, 214, 64));
			yStatusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			yStatusLabel.setFont(new Font("Lucida Grande", Font.BOLD, 10));
			toolBarMeasurementDetails.add(getLblZValue());
		}
		return toolBarMeasurementDetails;
	}

	private JToolBar getToolBar() {
		if (toolBar == null) {
			toolBar = new JToolBar();

			bearingStatusLabel = new JLabel();
			toolBar.add(bearingStatusLabel);
			bearingStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			bearingStatusLabel.setText("Bearing");
			bearingStatusLabel.setBackground(new Color(255, 214, 64));
			bearingStatusLabel.setComponentOrientation(ComponentOrientation.UNKNOWN);
			bearingStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
			bearingStatusLabel.setHorizontalTextPosition(SwingConstants.CENTER);
			bearingStatusLabel.setFont(new Font("Lucida Grande", Font.BOLD, 10));

			measurementStatusLabel1 = new JLabel();
			toolBar.add(measurementStatusLabel1);
			measurementStatusLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
			measurementStatusLabel1.setText("Distance 1");
			measurementStatusLabel1.setBackground(new Color(255, 214, 64));
			measurementStatusLabel1.setComponentOrientation(ComponentOrientation.UNKNOWN);
			measurementStatusLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
			measurementStatusLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			measurementStatusLabel1.setFont(new Font("Lucida Grande", Font.BOLD, 10));

			measurementStatusLabel2 = new JLabel();
			toolBar.add(measurementStatusLabel2);
			measurementStatusLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
			measurementStatusLabel2.setText("Distance 2");
			measurementStatusLabel2.setBackground(new Color(255, 214, 64));
			measurementStatusLabel2.setComponentOrientation(ComponentOrientation.UNKNOWN);
			measurementStatusLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
			measurementStatusLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			measurementStatusLabel2.setFont(new Font("Lucida Grande", Font.BOLD, 10));
		}
		return toolBar;
	}

	private JLabel getLblZValue() {
		if (lblZValue == null) {
			lblZValue = new JLabel();
			lblZValue.setText("Z Value =");
			lblZValue.setPreferredSize(new Dimension(80, 15));
			lblZValue.setHorizontalTextPosition(SwingConstants.CENTER);
			lblZValue.setHorizontalAlignment(SwingConstants.CENTER);
			lblZValue.setFont(new Font("Lucida Grande", Font.BOLD, 10));
			lblZValue.setComponentOrientation(ComponentOrientation.UNKNOWN);
			lblZValue.setBackground(new Color(255, 214, 64));
			lblZValue.setAlignmentX(0.5f);
		}
		return lblZValue;
	}

	private JTextArea getTextAreaConsoleOutput() {
		if (textAreaConsoleOutput == null) {
			textAreaConsoleOutput = new JTextArea();
			textAreaConsoleOutput.setMinimumSize(new Dimension(0, 200));
			textAreaConsoleOutput.setMaximumSize(new Dimension(2147483647, 300));
			textAreaConsoleOutput.setFont(new Font("Monaco", Font.PLAIN, 11));
			textAreaConsoleOutput.setEditable(false);
		}
		return textAreaConsoleOutput;
	}

	public static void setConsoleOutput(String console) {
		textAreaConsoleOutput.append(console);
		int length = textAreaConsoleOutput.getText().length();
		textAreaConsoleOutput.setCaretPosition(length);
	}

	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTextAreaConsoleOutput());
			scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane_1.setMaximumSize(new Dimension(2147483647, 300));
		}
		return scrollPane_1;
	}

	private JSpinner getSpinnerEnhanceAmount() {
		if (spinnerEnhanceAmount == null) {
			spinnerEnhanceAmount = new JSpinner();
			spinnerEnhanceAmount.setPreferredSize(new Dimension(50, 25));
			spinnerEnhanceAmount.setMinimumSize(new Dimension(40, 25));
			spinnerEnhanceAmount.setMaximumSize(new Dimension(32767, 25));
			spinnerEnhanceAmount.setFont(new Font("Dialog", Font.PLAIN, 12));
			spinnerEnhanceAmount.setModel(spinnerModelHoleFactor);
			spinnerEnhanceAmount.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						double i = Double.parseDouble(getSpinnerEnhanceAmount().getValue().toString());
						getSpinnerEnhanceAmount().setValue(i);
						holeFactor = i;
						getSpinnerEnhanceAmount().setForeground(Color.black);
						return true;
					} catch (NumberFormatException nfe) {
						getSpinnerEnhanceAmount().setForeground(Color.red);
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"The value in for the enhance\n in the Spinner cannot be parsed.\nThe default value of 2.00 has been returned.");
						getSpinnerEnhanceAmount().setValue(1.1);
						System.out.println("Caught - NumberFormatException");
						return false;
					}
				}
			});
			ChangeListener listener = new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					holeFactor = Double.parseDouble(getSpinnerEnhanceAmount().getValue().toString());
					updateCanvas();
					setConsoleOutput("\nEnhance Changed " + decimalFormat2.format(holeFactor));
				}
			};
			spinnerEnhanceAmount.addChangeListener(listener);

		}
		return spinnerEnhanceAmount;
	}

	private JSpinner getSpinnerEnhanceAmount2() {
		if (spinnerEnhanceAmount2 == null) {
			spinnerEnhanceAmount2 = new JSpinner();
			spinnerEnhanceAmount2.setPreferredSize(new Dimension(50, 25));
			spinnerEnhanceAmount2.setMinimumSize(new Dimension(40, 25));
			spinnerEnhanceAmount2.setMaximumSize(new Dimension(32767, 25));
			spinnerEnhanceAmount2.setFont(new Font("Dialog", Font.PLAIN, 12));
			spinnerEnhanceAmount2.setModel(spinnerModelMarkerFactor);
			spinnerEnhanceAmount2.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						double i = Double.parseDouble(getSpinnerEnhanceAmount2().getValue().toString());
						getSpinnerEnhanceAmount2().setValue(i);
						markerFactor = i;
						getSpinnerEnhanceAmount2().setForeground(Color.black);
						return true;
					} catch (NumberFormatException nfe) {
						getSpinnerEnhanceAmount2().setForeground(Color.red);
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"The value in for the enhance\n in the Spinner cannot be parsed.\nThe default value of 1.0 has been returned.");
						getSpinnerEnhanceAmount2().setValue(1.1);
						System.out.println("Caught - NumberFormatException");
						return false;
					}
				}
			});
			ChangeListener listener = new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					markerFactor = Double.parseDouble(getSpinnerEnhanceAmount2().getValue().toString());
					updateCanvas();
					setConsoleOutput("\nEnhance2 Changed " + decimalFormat2.format(markerFactor));
				}

			};

			spinnerEnhanceAmount2.addChangeListener(listener);

		}
		return spinnerEnhanceAmount2;
	}

	private JSpinner getSpinnerEnhanceAmountTie() {
		if (spinnerEnhanceAmountTie == null) {
			spinnerEnhanceAmountTie = new JSpinner();
			spinnerEnhanceAmountTie.setPreferredSize(new Dimension(50, 25));
			spinnerEnhanceAmountTie.setMinimumSize(new Dimension(40, 25));
			spinnerEnhanceAmountTie.setMaximumSize(new Dimension(32767, 25));
			spinnerEnhanceAmountTie.setFont(new Font("Dialog", Font.PLAIN, 12));
			spinnerEnhanceAmountTie.setModel(spinnerModelSurfaceFactor);
			spinnerEnhanceAmountTie.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					try {
						double i = Double.parseDouble(getSpinnerEnhanceAmountTie().getValue().toString());
						getSpinnerEnhanceAmountTie().setValue(i);
						surfaceFactor = i;
						getSpinnerEnhanceAmountTie().setForeground(Color.black);
						return true;
					} catch (NumberFormatException nfe) {
						getSpinnerEnhanceAmount2().setForeground(Color.red);
						JOptionPane.showMessageDialog(LiteTieTRIAL.this,
								"The value in for the enhance\n in the Spinner cannot be parsed.\nThe default value of 1.0 has been returned.");
						getSpinnerEnhanceAmountTie().setValue(1.1);
						System.out.println("Caught - NumberFormatException");
						return false;
					}
				}
			});
			ChangeListener listener = new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					getTieOnOffButton().setSelected(true);
					surfaceFactor = Double.parseDouble(getSpinnerEnhanceAmountTie().getValue().toString());
					updateCanvas();
					setConsoleOutput("\nEnhanceTie Changed " + decimalFormat2.format(surfaceFactor));
				}

			};

			spinnerEnhanceAmountTie.addChangeListener(listener);

		}
		return spinnerEnhanceAmountTie;
	}

	private JToggleButton getToolMultiTieInLineButton() {
		if (toolMultiTieInLineButton == null) {
			toolMultiTieInLineButton = new JToggleButton();
			toolMultiTieInLineButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolMultiTieInLineButton
					.setIcon(new ImageIcon(getClass().getResource(("/icons_LiteTie_v2/tieMulti2.png"))));
			toolMultiTieInLineButton.setToolTipText("Multi Surface Tablet Connection Tool");
			toolMultiTieInLineButton.setPreferredSize(new Dimension(30, 30));

			toolMultiTieInLineButton.addActionListener(new java.awt.event.ActionListener() {

				public void actionPerformed(java.awt.event.ActionEvent e) {

					setConsoleOutput("\n multitooltieButton");
					currentMouseMover = defaultMouseMover;
					setStatusBarLabel("MULTITIE IN LINE SURFACE TIMING TOOL"); // layman.
					selected = null;
					selected = new Double(0.0);
					updateCanvas();

					labelColourStroke.setText("Tie");

					if (getTieButton1().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField1().getBackground());
					}
					if (getTieButton2().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField2().getBackground());
					}
					if (getTieButton3().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField3().getBackground());
					}
					if (getTieButton4().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField4().getBackground());
					}
					if (getTieButton5().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField5().getBackground());
					}
					if (getTieButton6().isSelected()) {
						getColorWellStroke().setBackground(getTieTextField6().getBackground());
					}
					getColorWellFill().repaint();

					// CLICKER #1
					currentMouseClicker = new MouseAdapter() {

						@Override
						public void mouseReleased(MouseEvent e) {
							if (!getTieButton1().isSelected() && !getTieButton2().isSelected()
									&& !getTieButton3().isSelected() && !getTieButton4().isSelected()
									&& !getTieButton5().isSelected() && !getTieButton6().isSelected()) {

								JOptionPane.showMessageDialog(canvasPanel,
										"Please select a Tie to use before proceeding", "ERROR - No Tie Selected",
										JOptionPane.ERROR_MESSAGE);
								selected = new Double(0.0);
							} else if (tieClick == 0 || tieClicked == false || dummy1 == null) {
								selected = 0.0;
								if (getTieButton1().isSelected()) {
									getColorWellStroke().setBackground(getTieTextField1().getBackground());
								}
								if (getTieButton2().isSelected()) {
									getColorWellStroke().setBackground(getTieTextField2().getBackground());
								}
								if (getTieButton3().isSelected()) {
									getColorWellStroke().setBackground(getTieTextField3().getBackground());
								}
								if (getTieButton4().isSelected()) {
									getColorWellStroke().setBackground(getTieTextField4().getBackground());
								}
								if (getTieButton5().isSelected()) {
									getColorWellStroke().setBackground(getTieTextField5().getBackground());
								}
								if (getTieButton6().isSelected()) {
									getColorWellStroke().setBackground(getTieTextField6().getBackground());
								}
								getColorWellFill().repaint();
								selected = new Double(0.0);
								double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
										- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
								double easting = getCanvasSize().getX()
										+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
								selected = world.getDummysAndHolesIn(new Ellipse2D.Double(easting - radius,
										northing - radius, radius * 2, radius * 2));
								if (selected != null || (selected instanceof Set<?>) && selected instanceof Dummy) {
									for (Pattern tempPat : world.getPatternList().values()) {
										for (Dummy temp : tempPat.getAllDummysAndHoles()) {
											boolean isSelected = temp == selected || (selected instanceof Collection<?>
													&& ((Collection<?>) selected).contains(temp));
											if (isSelected) {
												world.getAllObjectsInWorld();
												dummy1 = temp;
												tPoint1 = dummy1.toPoint3D();
												getTreeModelSurface();
												updateCanvas();
												tieClicked = true;
												tieClick = 1;

											}
										}
									}
								}

							}
							// MOUSEMOVER
							if (tieClick == 1 && tieClicked == true && dummy1 != null) {
								currentMouseMover = new MouseMotionAdapter() {
									public void mouseMoved(MouseEvent e) {

										double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
												- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
										double easting = getCanvasSize().getX()
												+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

										float tolerance = p_tieInLineTolerance; // in
																				// meters
										double extra = 1;
										setConsoleOutput("\nTie in Tolerance:" + p_tieInLineTolerance + " metres");
										//
										tPoint2 = new LTPoint3D.Double(easting, northing, lastRLHeight);

										double d = calcs.calculateDistance(tPoint1, tPoint2);
										double a = calcs.calculateBearing(tPoint1, tPoint2);

										double x1 = (((tPoint1.getX() + tolerance - tPoint1.getX())
												* Math.cos(Math.toRadians(90 - a + 90)))
												- ((tPoint1.getY() - tPoint1.getY() + (extra)) * Math
														.sin(Math.toRadians(90 - a + 90))))
												+ tPoint1.getX();
										double x2 = ((((tPoint1.getX()) + tolerance - tPoint1.getX())
												* Math.cos(Math.toRadians(90 - a + 90)))
												- ((tPoint1.getY() - tPoint1.getY() - (d + extra)) * Math
														.sin(Math.toRadians(90 - a + 90))))
												+ tPoint1.getX();
										double x3 = ((((tPoint1.getX()) - tolerance - tPoint1.getX())
												* Math.cos(Math.toRadians(90 - a + 90)))
												- ((tPoint1.getY() - tPoint1.getY() - (d + extra)) * Math
														.sin(Math.toRadians(90 - a + 90))))
												+ tPoint1.getX();
										double x4 = (((tPoint1.getX() - tolerance - tPoint1.getX())
												* Math.cos(Math.toRadians(90 - a + 90)))
												- ((tPoint1.getY() - tPoint1.getY() + (extra)) * Math
														.sin(Math.toRadians(90 - a + 90))))
												+ tPoint1.getX();

										double y1 = (((tPoint1.getX() + tolerance - tPoint1.getX())
												* Math.sin(Math.toRadians(90 - a + 90)))
												+ ((tPoint1.getY() - tPoint1.getY() + (extra)) * Math
														.cos(Math.toRadians(90 - a + 90))))
												+ tPoint1.getY();
										double y2 = ((((tPoint1.getX()) + tolerance - tPoint1.getX())
												* Math.sin(Math.toRadians(90 - a + 90)))
												+ ((tPoint1.getY() - tPoint1.getY() - (d + extra)) * Math
														.cos(Math.toRadians(90 - a + 90))))
												+ tPoint1.getY();
										double y3 = ((((tPoint1.getX()) - tolerance - tPoint1.getX())
												* Math.sin(Math.toRadians(90 - a + 90)))
												+ ((tPoint1.getY() - tPoint1.getY() - (d + extra)) * Math
														.cos(Math.toRadians(90 - a + 90))))
												+ tPoint1.getY();
										double y4 = (((tPoint1.getX() - tolerance - tPoint1.getX())
												* Math.sin(Math.toRadians(90 - a + 90)))
												+ ((tPoint1.getY() - tPoint1.getY() + (extra)) * Math
														.cos(Math.toRadians(90 - a + 90))))
												+ tPoint1.getY();

										xs = new double[] { x1, x2, x3, x4 };
										ys = new double[] { y1, y2, y3, y4 };

										selected = world.getDummysAndHolesIn(new Polygon2D(xs, ys, 4));
										updateCanvas();

										tieClick = 2;
									}
								};
							}

							// MOUSECLICKER
							if (tieClick == 2) {
								try {

									Dummy[] dummyArray = null;
									if (selected instanceof Dummy || selected instanceof LinkedHashSet<?>) {
										dummyArray = ((Set<Dummy>) selected)
												.toArray(new Dummy[((LinkedHashSet<Dummy>) selected).size()]);
									} else {
										JOptionPane.showMessageDialog(rootPane,
												"LiteTie thinks that nothing is Selected.");
									}
									for (int i = 0; i < (dummyArray.length - 1); i++) {
										Dummy dummy1A = dummyArray[i];
										Dummy dummy2A = dummyArray[i + 1];
										System.out.println("Connecting... [" + dummy1A.getHoleID() + "] to ["
												+ dummy2A.getHoleID() + "]");
										setConsoleOutput("\n> Connecting... [" + dummy1A.getHoleID() + "] to ["
												+ dummy2A.getHoleID() + "]");
										if (dummy1A instanceof Dummy && dummy2A instanceof Dummy) {

											double d = calcs.calculateDistance(dummy1A.toPoint3D(),
													dummy2A.toPoint3D());

											// *************************************************************************
											if (getTieButton1().isSelected()) {
												sDelay = Integer.parseInt(getTieTextField1().getText());
												sColor1 = getTieTextField1().getBackground();
												sColor2 = getTieTextField2().getBackground();
												sColor3 = getTieTextField3().getBackground();
												sColor4 = getTieTextField4().getBackground();
												sColor5 = getTieTextField5().getBackground();
												sColor6 = getTieTextField6().getBackground();
												if (d > 0) {
													currentPattern.addSurfaceConnector(
															new SurfaceConnector(sDelay, d, dummy1A, dummy2A, sColor1,
																	SurfaceConnector.LIN_ST_STYLE),
															false);
												}
											} else if (getTieButton2().isSelected()) {
												sDelay = Integer.parseInt(getTieTextField2().getText());
												sColor1 = getTieTextField1().getBackground();
												sColor2 = getTieTextField2().getBackground();
												sColor3 = getTieTextField3().getBackground();
												sColor4 = getTieTextField4().getBackground();
												sColor5 = getTieTextField5().getBackground();
												sColor6 = getTieTextField6().getBackground();
												if (d > 0) {
													currentPattern.addSurfaceConnector(
															new SurfaceConnector(sDelay, d, dummy1A, dummy2A, sColor2),
															false);
												}
											} else if (getTieButton3().isSelected()) {
												sDelay = Integer.parseInt(getTieTextField3().getText());
												sColor1 = getTieTextField1().getBackground();
												sColor2 = getTieTextField2().getBackground();
												sColor3 = getTieTextField3().getBackground();
												sColor4 = getTieTextField4().getBackground();
												sColor5 = getTieTextField5().getBackground();
												sColor6 = getTieTextField6().getBackground();
												if (d > 0) {
													currentPattern.addSurfaceConnector(
															new SurfaceConnector(sDelay, d, dummy1A, dummy2A, sColor3),
															false);
												}
											} else if (getTieButton4().isSelected()) {
												sDelay = Integer.parseInt(getTieTextField4().getText());
												sColor1 = getTieTextField1().getBackground();
												sColor2 = getTieTextField2().getBackground();
												sColor3 = getTieTextField3().getBackground();
												sColor4 = getTieTextField4().getBackground();
												sColor5 = getTieTextField5().getBackground();
												sColor6 = getTieTextField6().getBackground();
												if (d > 0) {
													currentPattern.addSurfaceConnector(
															new SurfaceConnector(sDelay, d, dummy1A, dummy2A, sColor4),
															false);
												}
											} else if (getTieButton5().isSelected()) {
												sDelay = Integer.parseInt(getTieTextField5().getText());
												sColor1 = getTieTextField1().getBackground();
												sColor2 = getTieTextField2().getBackground();
												sColor3 = getTieTextField3().getBackground();
												sColor4 = getTieTextField4().getBackground();
												sColor5 = getTieTextField5().getBackground();
												sColor6 = getTieTextField6().getBackground();
												if (d > 0) {
													currentPattern.addSurfaceConnector(
															new SurfaceConnector(sDelay, d, dummy1A, dummy2A, sColor5),
															false);
												}
											} else if (getTieButton6().isSelected()) {
												sDelay = Integer.parseInt(getTieTextField6().getText());
												sColor1 = getTieTextField1().getBackground();
												sColor2 = getTieTextField2().getBackground();
												sColor3 = getTieTextField3().getBackground();
												sColor4 = getTieTextField4().getBackground();
												sColor5 = getTieTextField5().getBackground();
												sColor6 = getTieTextField6().getBackground();
												if (d > 0) {
													currentPattern.addSurfaceConnector(
															new SurfaceConnector(sDelay, d, dummy1A, dummy2A, sColor6),
															false);
												}
											}

											// *************************************************************************

											getTreeModelSurface();
											statusBarLabel.setText("Surface Delay added = " + sDelay + "ms"
													+ "\tSurface delays in List"
													+ currentPattern.getSurfaceList().size());
										}
									}

								} catch (NumberFormatException e1) {
									System.out.println("NumberFormatException");
									e1.printStackTrace();
								} catch (NegativeNumberException e1) {
									System.out.println("NegativeNumberException");
									e1.printStackTrace();
								} catch (ZeroArgumentException e1) {
									System.out.println("ZeroArgumentException");
									// e1.printStackTrace();
								} catch (FromToException e1) {
									System.out.println("FromToException");
									getTextAreaConsoleOutput().setCaretColor(Color.RED);
									setConsoleOutput(e1.toString()
											+ "\nLiteTie thinks that you've\nselected the same hole twice.\n");
									getTextAreaConsoleOutput().setCaretColor(Color.BLACK);
									// e1.printStackTrace();
									// java.awt.Toolkit.getDefaultToolkit().beep();
								}
								xs = ys = null;
								dummy1 = null;
								dummy2 = null;
								tPoint1 = tPoint2 = null;
								selected = null;
								selectedFIFO = null;
								tieClicked = false;
								currentMouseMover = null;
								toolMultiTieInLineButton.removeMouseMotionListener(currentMouseMover);
								toolMultiTieInLineButton.removeMouseListener(currentMouseClicker);
								updateCanvas();
								tieClick = 0;
							}

						}
					};
					//
					toolMultiTieInLineButton.addKeyListener(new java.awt.event.KeyAdapter() {
						public void keyPressed(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
								dummy1 = null;
								dummy2 = null;
								selected = null;
								xs = ys = null;
								tPoint1 = tPoint2 = null;
								selectedFIFO = null;
								tieClicked = false;
								currentMouseMover = null;
								toolMultiTieInLineButton.removeMouseMotionListener(currentMouseMover);
								toolMultiTieInLineButton.removeMouseListener(currentMouseClicker);
								updateCanvas();
							}
						}

						public void keyReleased(java.awt.event.KeyEvent e) {
							if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
								dummy1 = null;
								dummy2 = null;
								xs = ys = null;
								tPoint1 = tPoint2 = null;
								selected = null;
								selectedFIFO = null;
								tieClicked = false;
								currentMouseMover = null;
								toolMultiTieInLineButton.removeMouseMotionListener(currentMouseMover);
								toolMultiTieInLineButton.removeMouseListener(currentMouseClicker);
								updateCanvas();
							}
						}
					});
				}
			});

			currentMouseMover = defaultMouseMover;
			updateCanvas();
		}
		return toolMultiTieInLineButton;
	}

	private JToggleButton getToolAddChargeButton() {
		if (toolAddChargeButton == null) {
			toolAddChargeButton = new JToggleButton();
			toolAddChargeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			toolAddChargeButton.setMinimumSize(dimension30x30);
			toolAddChargeButton.setMaximumSize(dimension30x30);
			toolAddChargeButton.setPreferredSize(dimension30x30);
			toolAddChargeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					currentMouseMover = null;
					setStatusBarLabel("CHARGE TOOL");
					selected = null;
					updateCanvas();
					// CHANGE TO CHARGES ON OFF BUTTON - When you create
					// the of on button.
					getDetsOnOffButton().setSelected(true);
					currentMouseClicker = new MouseAdapter() {
						@Override
						public void mouseReleased(MouseEvent e) {
							double northing = getCanvasSize().getY() + getCanvasSize().getHeight()
									- UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
							double easting = getCanvasSize().getX()
									+ UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
							if (!(currentPattern == null)) {
								selected = currentPattern.getHoleIn(new Ellipse2D.Double(easting - radius,
										northing - radius, radius * 2, radius * 2));
								if (selected instanceof Hole) {
									hole1 = (Hole) selected;
								}
								updateCanvas();
							}
							try {
								if (hole1 instanceof Hole) {
									// Charge charge = new Charge("AIR",
									// hole1, hole1.getBench()*0.5);
									// currentPattern.addCharge(DialogCreateCharge.showBox(canvasPanel,
									// charge, hole1));
									// new Charge(type, hole1, capDepth,
									// baseDepth, capDensity,
									// chargeDiameter,
									// isCompressible(type), color));
								}
								statusBarLabel.setText("Charge added = " + dDelay + "ms" + "\tCharges in List ="
										+ currentPattern.getChargeList().size());
							} catch (NumberFormatException ce1) {
								statusBarLabel.setText("The charge can not be created.");
								ce1.printStackTrace();
							}

						}
					};
				}
			});
			updateCanvas();
		}

		return toolAddChargeButton;
	}

	@Override
	public void addUndo(UndoableEdit newUndo) {
		undoManager.addEdit(newUndo);
	}

	@Override
	public void setCurrentMouseClicker(MouseListener newCurrentMouseClicker) {
		currentMouseClicker = newCurrentMouseClicker;
	}

	@Override
	public void setCurrentMouseMover(MouseMotionListener newCurrentMouseMover) {
		currentMouseMover = newCurrentMouseMover;
	}

	@Override
	public void setStrokeColourLabelText(String newText) {
		labelColourStroke.setText(newText);
	}

	@Override
	public void setStrokeColourWellBackground(Color newBackground) {
		colorWellStroke.setBackground(newBackground);
	}

	@Override
	public Color getCurrentColour() {
		return currentColor;
	}

	@Override
	public DecimalFormat getDecimalFormatMetres1() {
		return decimalFormatMetres1;
	}

	@Override
	public MouseMotionListener getDefaultMouseMover() {
		return defaultMouseMover;
	}

	@Override
	public Object getSelection() {
		return selected;
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public Object selectFirstObjectNear(double nearX, double nearY, double nearZ) {
		// This function should return a collection of one element
		selected = world
				.getFirstObjectIn(new Ellipse2D.Double(nearX - radius * 2, nearY - radius * 2, radius * 4, radius * 4));
		updateCanvas();
		return selected;
	}

	@Override
	public void setCentreBounds(double newX, double newY, double newZ) {
		centerBoundsY = newY;
		centerBoundsX = newX;
		centerBoundsZ = newZ;
	}

	@Override
	public void setDelta(double deltaX, double deltaY, double deltaZ) {
		deltaNorthing = deltaY;
		deltaEasting = deltaX;
	}

	@Override
	public void setSelection(Collection<?> collection) {
		selected = collection;
	}

	@Override
	public void setMeasurementStatusText(String new1, String new2) {
		measurementStatusLabel1.setText(new1);
		measurementStatusLabel2.setText(new2);
	}

	@Override
	public void updateTrees() {
		getTreeModelBoundary();
		getTreeModelPattern();
		getTreeModelText();
	}

	// END OF FRAME
}
// END OF CLASS
