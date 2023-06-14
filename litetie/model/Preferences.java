package litetie.model;
import java.awt.Color;

// Preference class for all preferences
public class Preferences {
	
	//Booleans return true if selected (ON) 
	boolean gridVisible;
	boolean boundaryVisible;
	boolean angleVisible;
	boolean holesVisible;
	boolean tracksVisible;
	boolean floorLineVisible;
	boolean floorCircleVisible;
	boolean toeLineVisible;
	boolean toeCircleVisible;
	boolean idVisible;
	boolean labelVisible;
	boolean subdrillVisible;
	boolean collarRLVisible;
	boolean floorRLVisible;
	boolean toeRLVisible;
	boolean benchHeightVisible;
	boolean holeLengthVisible;
	boolean diameterVisible;
	boolean bearingVisible;
	boolean downholeTimeVisible;
	boolean surfaceTimeVisible;
	boolean surfaceDelaysVisible;
	boolean decksVisible;
	boolean contoursVisible;
	boolean firstMovementVisible;
	boolean reliefVisible;
	boolean firingOrderVisible;
	boolean rulersVisible;
	boolean compassVisible;
	boolean timingKeyVisible;
	boolean mapScaleVisible;
	
	// Used to adjust all scale factors by this value
	boolean universalAdjustmentFactor;
	
	//Visualising scaling factors
	int holeScale;
	int floorMarkerScale;
	int toeMarkerScale;
	int boundaryLineScale;
	int compassScale;
	
	//Visualising Text scaling Factors
	int idTextScale;
	int labelTextScale;
	int angleTextScale;
	int bearingTextScale;
	int diameterTextScale;
	int collarRLTextScale;
	int floorRLTextScale;
	int toeRLTextScale;
	int	benchHeightTextScale;
	int holeLengthTextScale;
	int downholeTimeTextScale;
	int surfaceTimeTextScale;
	int firingOrderTextScale;
	
	//Visualising colour changes
	Color holeColor;//1
	Color trackColor;//2
	Color floorMarkerColor;//3
	Color toeMarkerColor;//4
	Color idTextColor;//5
	Color labelTextColor;//6
	Color angleTextColor;//7
	Color bearingTextColor;//8
	Color collarRLTextColor;//9
	Color floorRLTextColor;//10
	Color toeRLTextColor;//11
	Color benchHeightTextColor;//12
	Color holeLengthTextColor;//13
	Color diameterTextColor;//14
	Color downholeTimeTextColor;//15
	Color surfaceTimeTextColor;//16
	Color firstMovementArrowColor;//17
	Color firingOrderTextColor;//18
	Color compassColor;//19
	Color selectedColor;//20
	Color gridColor;//20
	Color canvasColor;//20
	
	//Increment settings
	int rulerIncrements;
	
	
	
	
	

	

}
