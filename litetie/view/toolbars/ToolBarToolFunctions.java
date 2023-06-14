package litetie.view.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;

import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class ToolBarToolFunctions extends JToolBar implements ActionListener  {

	
	//Icons
	ImageIcon iconSelectionArrow = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/selectSingle.png"));
	ImageIcon iconSelectionRect = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/selectSquare.png"));
	ImageIcon iconSelectionPoly = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/selectPoly.png"));
	ImageIcon iconSelectionOval = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/selectCircle.png"));
	ImageIcon iconRuler = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/ruler.png"));
	ImageIcon iconProtractor = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/bearingMeasure.png"));
	ImageIcon iconMove = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/move.png"));
	ImageIcon iconRotate = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/rotate.png"));
	ImageIcon iconScale = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/scale.png"));
	ImageIcon iconPolygon = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/polygon.png"));
	ImageIcon iconSquare = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/square.png"));
	ImageIcon iconCircle = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/circle.png"));
	ImageIcon iconLine = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/line.png"));
	ImageIcon iconPolyLine = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/polyLine2.png"));
	ImageIcon iconDimension = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/dimensionArrow.png"));
	ImageIcon iconText = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/text.png"));
	ImageIcon iconPattern = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/pattern.png"));
	ImageIcon iconHole = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/insertHole.png"));
	ImageIcon iconDummy = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/insertDummy.png"));
	ImageIcon iconZAdjust = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/zMove.png"));
	ImageIcon iconLabel = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/label.png"));
	ImageIcon iconPatternPoly = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/polyPattern.png"));
	ImageIcon iconTimingSingle = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/tieSingle.png"));
	ImageIcon iconTimingMulti = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/tieMulti2.png"));
	ImageIcon iconDetonatorSingle = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/detonatorSingle.png"));
	ImageIcon iconDetonatorMulti = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/detonatorMulti.png"));
	ImageIcon iconChargeSingle = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/chargeSingle.png"));
	ImageIcon iconChargeMulti = new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie_v2/chargeMulti.png"));
	
	Dimension textFieldSize = new Dimension(60,20);
	Dimension buttonSize = new Dimension(27,27);
	Dimension panelSize = new Dimension(60,400);
	
	SpinnerModel spinnerIntegerModel = new SpinnerNumberModel(3, 3, 10, 1);
	
	//Toggle Selection Buttons
	JToggleButton buttonSelection = new JToggleButton(iconSelectionArrow);
	JToggleButton buttonSelectionMarquee = new JToggleButton(iconSelectionRect);
	JToggleButton buttonSelectionPolygon = new JToggleButton(iconSelectionPoly);
	JToggleButton buttonSelectionOval = new JToggleButton(iconSelectionOval);
	
	JToggleButton buttonRuler = new JToggleButton(iconRuler);
	JToggleButton buttonBearing = new JToggleButton(iconProtractor);

	JToggleButton buttonMove = new JToggleButton(iconMove);
	JToggleButton buttonRotate = new JToggleButton(iconRotate);
	JToggleButton buttonScale = new JToggleButton(iconScale);
	JToggleButton buttonPolygon = new JToggleButton(iconPolygon);
	JToggleButton buttonSquare = new JToggleButton(iconSquare);
	JToggleButton buttonCircle = new JToggleButton(iconCircle);
	JToggleButton buttonLine = new JToggleButton(iconLine);
	JToggleButton buttonPolyLine = new JToggleButton(iconPolyLine);
	
	JToggleButton buttonDimension = new JToggleButton(iconDimension);
	JToggleButton buttonText = new JToggleButton(iconText);
	
	JToggleButton buttonPattern = new JToggleButton(iconPattern);
	JToggleButton buttonPatternPoly  = new JToggleButton(iconPatternPoly);
	JToggleButton buttonHole = new JToggleButton(iconHole);
	JToggleButton buttonDummy = new JToggleButton(iconDummy);
	
	JToggleButton buttonZAdjust = new JToggleButton(iconZAdjust);
	JToggleButton buttonLabel = new JToggleButton(iconLabel);
	JToggleButton buttonTimingSingle = new JToggleButton(iconTimingSingle);
	JToggleButton buttonTimingMulti = new JToggleButton(iconTimingMulti);
	JToggleButton buttonDetonatorSingle = new JToggleButton(iconDetonatorSingle);
	JToggleButton buttonDetonatorMulti = new JToggleButton(iconDetonatorMulti);
	JToggleButton buttonChargeSingle = new JToggleButton(iconChargeSingle);
	JToggleButton buttonChargeMulti = new JToggleButton(iconChargeMulti);
	
	JButton showHideButton = new JButton(new ImageIcon(ToolBarToolFunctions.class.getResource("/icons_LiteTie/showeye.png")));
	
	ButtonGroup toggleButtons = new ButtonGroup();
	JPanel tools = new JPanel();
	//Constructor
	/**
	 * <code>ToolBarToolFunctions toolbar = new ToolBarToolFunctions();</code> to call the ToolBar.<br><br>
	 * This creates a <i>"Nimbus Styled toolbar"</i> containing view related JButtons, JToggleButtons</code>.
	 * 
	 * The <code>JToggleButton</code>'s <code>actionCommand</code>s are:<br>
	 * <code>zoomIn</code>, 
	 * <code>zoomOut</code>,<br>  
	 * <code>zoomFit</code>,
	 * <code>zoomUser</code>.<br> 
	 */
	public ToolBarToolFunctions() {
		setOpaque(false);
		setOrientation(SwingConstants.VERTICAL);
		setSize(new Dimension(80, 521));
		
		//Set the Look and Feel
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tools.setSize(70, 520);
		add(tools);
		//Set Layout
		tools.setLayout(new GridLayout(0,2));
		
		tools.add(buttonSelection);
		tools.add(buttonSelectionMarquee);
		tools.add(buttonSelectionPolygon);
		tools.add(buttonSelectionOval);
		tools.add(buttonRuler);
		tools.add(buttonBearing);
		tools.add(buttonMove);
		tools.add(buttonRotate);
		tools.add(buttonScale);
		tools.add(buttonSquare);
		tools.add(buttonPolygon);
		tools.add(buttonCircle);
		tools.add(buttonLine);
		tools.add(buttonPolyLine);
		tools.add(buttonDimension);
		tools.add(buttonText);
		
		tools.add(buttonPattern);
		tools.add(buttonPatternPoly);
		tools.add(buttonHole);
		tools.add(buttonDummy);
		
		tools.add(buttonZAdjust);
		tools.add(buttonLabel);
		tools.add(buttonTimingSingle);
		tools.add(buttonTimingMulti);
		tools.add(buttonDetonatorSingle);
		tools.add(buttonDetonatorMulti);
		tools.add(buttonChargeSingle);
		tools.add(buttonChargeMulti);
		
		//Add the buttons to the toolbar
		toggleButtons.add(buttonSelection);
		toggleButtons.add(buttonSelectionMarquee);
		toggleButtons.add(buttonSelectionPolygon);
		toggleButtons.add(buttonSelectionOval);
		toggleButtons.add(buttonRuler);
		toggleButtons.add(buttonBearing);
		
		toggleButtons.add(buttonMove);
		toggleButtons.add(buttonRotate);
		toggleButtons.add(buttonScale);
		toggleButtons.add(buttonSquare);
		toggleButtons.add(buttonPolygon);
		toggleButtons.add(buttonCircle);
		toggleButtons.add(buttonLine);
		toggleButtons.add(buttonPolyLine);
		
		toggleButtons.add(buttonDimension);
		toggleButtons.add(buttonText);
		
		toggleButtons.add(buttonPattern);
		toggleButtons.add(buttonPatternPoly);
		toggleButtons.add(buttonHole);
		toggleButtons.add(buttonDummy);
		
		toggleButtons.add(buttonZAdjust);
		toggleButtons.add(buttonLabel);
		toggleButtons.add(buttonTimingSingle);
		toggleButtons.add(buttonTimingMulti);
		toggleButtons.add(buttonDetonatorSingle);
		toggleButtons.add(buttonDetonatorMulti);
		toggleButtons.add(buttonChargeSingle);
		toggleButtons.add(buttonChargeMulti);
		
		buttonSelection.setActionCommand("select");
		buttonSelectionPolygon.setActionCommand("selectPoly");
		buttonSelectionOval.setActionCommand("selectOval");
		buttonSelectionMarquee.setActionCommand("selectMarquee");

	}
	//Returns the button Zoom In
//	public JButton getButtonZoomIn(){
//		return
//				buttonZoomIn;
//	}
//	//Returns the button Zoom Out
//	public JButton getButtonZoomOut(){
//		return
//				buttonZoomOut;
//	}
//	//Returns the button Zoom User
//	public JButton getButtonZoomUser(){
//		return
//				buttonZoomUser;
//	}
//	//Returns the toggle button Zoom to Fit
//	public JToggleButton getButtonZoomToFit(){
//		return
//				buttonSelection;
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
