package litetie.view.toolbars;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class ToolBarVisibilityOptions extends JToolBar implements ActionListener  {

	//Icons
	ImageIcon iconGrid = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/gridONOFF.png"));
	ImageIcon iconPoly = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/boundaryonoff.png"));
	ImageIcon iconText = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/textonoff.png"));
	ImageIcon iconHole = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holesonoff.png"));
	ImageIcon iconDummy = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/dummyOnOff.png"));
	ImageIcon iconTrack = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holeTracks.png"));
	ImageIcon iconFloorM = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/frlcircle.png"));
	ImageIcon iconFloorL = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/frlline.png"));
	ImageIcon iconToeM = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/trlcircle.png"));
	ImageIcon iconToeL = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/trlline.png"));
	ImageIcon iconAngle = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holeangle.png"));
	ImageIcon iconBearing = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holebearing.png"));
	ImageIcon iconId = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holeID.png"));
	ImageIcon iconBenchHeight = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/benchheight.png"));
	ImageIcon iconStemming = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/stemming.png"));
	ImageIcon iconLength = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holebearing.png"));
	ImageIcon iconSubdrill = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holesubdrill.png"));
	ImageIcon iconLabelOne = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holelabel.png"));
	ImageIcon iconLabelTwo = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holelabel2.png"));
	ImageIcon iconLabelThree = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holelabel3.png"));
	ImageIcon iconChargeLength = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/chargeLength.png"));
	ImageIcon iconChargeAmount = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/chargeAmount.png"));
	ImageIcon iconDiameter = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/holediameter.png"));
	ImageIcon iconDownHoleTimes = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/nominalTimeDH.png"));
	ImageIcon iconDetonatorDelay = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/detdelayTimes.png"));
	ImageIcon iconSurfaceTimes = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/nominaltimeSUR.png"));
	ImageIcon iconDetonator = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/detsonoff.png"));
	ImageIcon iconCharge = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/charge.png"));
	ImageIcon iconSurfaceDelays = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/tieDelayONOFF.png"));
	ImageIcon iconSurfaceConnector = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/tiesoffon.png"));
	ImageIcon iconContours = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/showContours.png"));
	ImageIcon iconFirstMovements = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/show1stMove.png"));
	ImageIcon iconRelief = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/showRelief.png"));
	Icon iconEye = new ImageIcon(ToolBarVisibilityOptions.class.getResource("/icons_LiteTie/showeye.png"));
	
	
	
	//Toggle Selection Buttons
	JToggleButton buttonGrid = new JToggleButton(iconGrid);
	JToggleButton buttonPoly = new JToggleButton(iconPoly);
	JToggleButton buttonText = new JToggleButton(iconText);
	JToggleButton buttonHole = new JToggleButton(iconHole);
	JToggleButton buttonDummy = new JToggleButton(iconDummy);
	JToggleButton buttonTrack = new JToggleButton(iconTrack);
	JToggleButton buttonFloorM = new JToggleButton(iconFloorM);
	JToggleButton buttonFloorL = new JToggleButton(iconFloorL);
	JToggleButton buttonToeM = new JToggleButton(iconToeM);
	JToggleButton buttonToeL = new JToggleButton(iconToeL);
	JToggleButton buttonAngle = new JToggleButton(iconAngle);
	JToggleButton buttonBearing = new JToggleButton(iconBearing);
	JToggleButton buttonId = new JToggleButton(iconId);
	JToggleButton buttonBenchHeight = new JToggleButton(iconBenchHeight);
	JToggleButton buttonStemming = new JToggleButton(iconStemming);
	JToggleButton buttonLength = new JToggleButton(iconLength);
	JToggleButton buttonSubdrill = new JToggleButton(iconSubdrill);
	JToggleButton buttonLabelOne = new JToggleButton(iconLabelOne);
	JToggleButton buttonLabelTwo = new JToggleButton(iconLabelTwo);
	JToggleButton buttonLabelThree = new JToggleButton(iconLabelThree);
	JToggleButton buttonChargeLength = new JToggleButton(iconChargeLength);
	JToggleButton buttonChargeAmount = new JToggleButton(iconChargeAmount);
	JToggleButton buttonDiameter = new JToggleButton(iconDiameter);
	JToggleButton buttonDownHoleTimes = new JToggleButton(iconDownHoleTimes);
	JToggleButton buttonDetonatorDelay = new JToggleButton(iconDetonatorDelay);
	JToggleButton buttonSurfaceTimes = new JToggleButton(iconSurfaceTimes);
	JToggleButton buttonDetonator = new JToggleButton(iconDetonator);
	JToggleButton buttonCharge = new JToggleButton(iconCharge);
	JToggleButton buttonSurfaceDelays = new JToggleButton(iconSurfaceDelays);
	JToggleButton buttonSurfaceConnector = new JToggleButton(iconSurfaceConnector);
	JToggleButton buttonContours = new JToggleButton(iconContours);
	JToggleButton buttonFirstMovements = new JToggleButton(iconFirstMovements);
	JToggleButton buttonRelief = new JToggleButton(iconRelief);

	
//	JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
	
	//Spinner Models
	SpinnerModel modelHoleSize = new SpinnerNumberModel(2.5, 0.1, 50.0, 0.1);
	SpinnerModel modelMarkSize = new SpinnerNumberModel(2.5, 0.1, 50.0, 0.1);
	SpinnerModel modelTieSize = new SpinnerNumberModel(2.5, 0.1, 50.0, 0.1);
	SpinnerModel modelTextSize = new SpinnerNumberModel(10, 4, 72, 2);
	
	//Spinners
	JSpinner spinnerHoleSize = new JSpinner(modelHoleSize);
	JSpinner spinnerMarkSize = new JSpinner(modelMarkSize);
	JSpinner spinnerTieSize = new JSpinner(modelTieSize);
	JSpinner spinnerTextSize = new JSpinner(modelTextSize);
	
	Dimension textFieldSize = new Dimension(55,27);
	Dimension buttonSize = new Dimension(27,27);
	Dimension panelSize = new Dimension(1060,27);

	
	
	//Constructor
	/**
	 * <code>ToolBarVisibilityOptions toolbar = new ToolBarVisibilityOptions();</code> to call the ToolBar.<br><br>
	 * This creates a <i>"Nimbus Styled toolbar"</i> containing view related JButtons, JToggleButtons</code>.
	 */
	public ToolBarVisibilityOptions() {
		setPreferredSize(panelSize);
		
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
		
//		setLayout(new GridLayout(1,20));
		setLayout(new FlowLayout(FlowLayout.LEFT,-2,0));
		
		spinnerHoleSize.setPreferredSize(textFieldSize);
		spinnerMarkSize.setPreferredSize(textFieldSize);
		spinnerTieSize.setPreferredSize(textFieldSize);
		spinnerTextSize.setPreferredSize(textFieldSize);
		
//		eye.setPreferredSize(new Dimension(25,25));
		
		buttonGrid.setPreferredSize(buttonSize);
		buttonPoly.setPreferredSize(buttonSize);
		buttonText.setPreferredSize(buttonSize);
		buttonHole.setPreferredSize(buttonSize);
		buttonDummy.setPreferredSize(buttonSize);
		buttonTrack.setPreferredSize(buttonSize);
		buttonFloorM.setPreferredSize(buttonSize);
		buttonFloorL.setPreferredSize(buttonSize);
		buttonToeM.setPreferredSize(buttonSize);
		buttonToeL.setPreferredSize(buttonSize);
		buttonId.setPreferredSize(buttonSize);
		buttonAngle.setPreferredSize(buttonSize);
		buttonStemming.setPreferredSize(buttonSize);
		buttonBearing.setPreferredSize(buttonSize);
		buttonBenchHeight.setPreferredSize(buttonSize);
		buttonLength.setPreferredSize(buttonSize);
		buttonSubdrill.setPreferredSize(buttonSize);
		buttonLabelOne.setPreferredSize(buttonSize);
		buttonLabelTwo.setPreferredSize(buttonSize);
		buttonLabelThree.setPreferredSize(buttonSize);
		buttonChargeLength.setPreferredSize(buttonSize);
		buttonChargeAmount.setPreferredSize(buttonSize);
		buttonDiameter.setPreferredSize(buttonSize);
		buttonDownHoleTimes.setPreferredSize(buttonSize);
		buttonDetonatorDelay.setPreferredSize(buttonSize);
		buttonSurfaceTimes.setPreferredSize(buttonSize);
		buttonDetonator.setPreferredSize(buttonSize);
		buttonCharge.setPreferredSize(buttonSize);
		buttonSurfaceDelays.setPreferredSize(buttonSize);
		buttonSurfaceConnector.setPreferredSize(buttonSize);
		buttonContours.setPreferredSize(buttonSize);
		buttonFirstMovements.setPreferredSize(buttonSize);
		buttonRelief.setPreferredSize(buttonSize);
		
		//Set the Tool Text TipText
		buttonGrid.setToolTipText("Grid On/Off");
		buttonPoly.setToolTipText("Lines On/Off");
		buttonText.setToolTipText("Text On/Off");
		buttonHole.setToolTipText("Holes On/Off");
		buttonDummy.setToolTipText("Dummy On/Off");
		buttonTrack.setToolTipText("Track On/Off");
		buttonFloorM.setToolTipText("Floor Circle On/Off");
		buttonFloorL.setToolTipText("Floor Line  On/Off");
		buttonToeM.setToolTipText("Toe Circle On/Off");
		buttonToeL.setToolTipText("Toe Line On/Off");
		buttonId.setToolTipText("Id Text On/Off");
		buttonAngle.setToolTipText("Angle On/Off");
		buttonStemming.setToolTipText("Stem Length On/Off");
		buttonBearing.setToolTipText("Bearing On/Off");
		buttonBenchHeight.setToolTipText("Bench Height On/Off");
		buttonLength.setToolTipText("Hole Length On/Off");
		buttonSubdrill.setToolTipText("Subdrill On/Off");
		buttonLabelOne.setToolTipText("Label One On/Off");
		buttonLabelTwo.setToolTipText("Label Two On/Off");
		buttonLabelThree.setToolTipText("Label Three On/Off");
		buttonChargeLength.setToolTipText("Charge Length On/Off");
		buttonChargeAmount.setToolTipText("Charge Amount On/Off");
		buttonDiameter.setToolTipText("Diameter On/Off");
		buttonDownHoleTimes.setToolTipText("Downhole Time On/Off");
		buttonDetonatorDelay.setToolTipText("Detonator Delay On/Off");
		buttonSurfaceTimes.setToolTipText("Surface Times On/Off");
		buttonDetonator.setToolTipText("Detonators On/Off");
		buttonCharge.setToolTipText("Charges On/Off");
		buttonSurfaceDelays.setToolTipText("Surface Delays On/Off");
		buttonSurfaceConnector.setToolTipText("Surface Connections On/Off");
		buttonContours.setToolTipText("Timing Contours On/Off");
		buttonFirstMovements.setToolTipText("First Movements On/Off");
		buttonRelief.setToolTipText("Timing Relief On/Off");
		spinnerHoleSize.setToolTipText("Hole and Dummy Size");
		spinnerMarkSize.setToolTipText("Floor and Toe Mark Size");
		spinnerTieSize.setToolTipText("Surface Arrow Size");
		spinnerTextSize.setToolTipText("Text Size");
		
		//Add buttons to the panel
		add(buttonGrid);
		add(buttonPoly);
		add(buttonHole);
		add(buttonDummy);
		add(spinnerHoleSize);
		add(buttonTrack);
		add(buttonFloorM);
		add(buttonFloorL);
		add(buttonToeM);
		add(buttonToeL);
		add(spinnerMarkSize);
		add(buttonId);
		add(buttonAngle);
		add(buttonBearing);
		add(buttonBenchHeight);
		add(buttonLength);
		add(buttonSubdrill);
		add(buttonLabelOne);
		add(buttonLabelTwo);
		add(buttonLabelThree);
		add(buttonStemming);
		add(buttonChargeLength);
		add(buttonChargeAmount);
		add(buttonDiameter);
		add(buttonDownHoleTimes);
		add(buttonDetonatorDelay);
		add(buttonSurfaceTimes);
		add(buttonText);
		add(spinnerTextSize);
		add(buttonDetonator);
		add(buttonCharge);
		add(buttonSurfaceDelays);
		add(buttonSurfaceConnector);
		add(spinnerTieSize);
		add(buttonContours);
		add(buttonFirstMovements);
		add(buttonRelief);
		
		buttonGrid.setActionCommand("grid");

	}
	//Returns the button grid on off
	public JToggleButton getButtonGrid(){
		return
				buttonGrid;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
