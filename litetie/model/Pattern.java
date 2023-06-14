package litetie.model;

//import java.util.ListIterator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;

import litetie.listeners.BoundaryChangeListener;
import litetie.listeners.PatternChangeListener;

public class Pattern {

	protected int patternID;
	protected String patternName;
	protected double burden;
	protected double spacing;
	private int rows;
	private int holesInRow;
	private double orientation;
	

	private TreeMap<Integer, Dummy>dummyList;
	private TreeMap<Integer, Hole> holeList;
	private TreeMap<Integer,SurfaceConnector> surfaceList;
	private TreeMap<Integer, Detonator> detonatorList;
	private TreeMap<Integer, Charge> chargeList;
	private TreeMap<Integer,InitiationPoint> iPList;

	private Set<PatternChangeListener> listeners;
	Set<Object> allObjects = new HashSet<Object>();	
	//Object oneObject = null;

	public Pattern(int patternID, double burden, double spacing, int rows, int holesInRow, double orientation) throws ZeroArgumentException, NegativeNumberException{
		setPatternID(patternID);
		setBurden(burden);
		setSpacing(spacing);
		setRows(rows);
		setHolesinRow(holesInRow);
		setOrientation(orientation);
		
		dummyList = new TreeMap<Integer, Dummy>();
		holeList = new TreeMap<Integer, Hole>();
		surfaceList = new TreeMap<Integer,SurfaceConnector>();
		detonatorList = new TreeMap<Integer, Detonator>();
		chargeList = new TreeMap<Integer, Charge>();
		iPList = new TreeMap<Integer,InitiationPoint>();
		listeners = new HashSet<PatternChangeListener>();
	}
	public Pattern(int patternID, String patternName, double burden, double spacing, int rows, int holesInRow, double orientation) throws ZeroArgumentException, NegativeNumberException{
		setPatternID(patternID);
		setPatternName(patternName);
		setBurden(burden);
		setSpacing(spacing);
		setRows(rows);
		setHolesinRow(holesInRow);
		setOrientation(orientation);
		
		dummyList = new TreeMap<Integer, Dummy>();
		holeList = new TreeMap<Integer, Hole>();
		surfaceList = new TreeMap<Integer,SurfaceConnector>();
		detonatorList = new TreeMap<Integer, Detonator>();
		chargeList = new TreeMap<Integer, Charge>();
		iPList = new TreeMap<Integer,InitiationPoint>();
		listeners = new HashSet<PatternChangeListener>();
	}
	public Pattern( double burden, double spacing, int rows, int holesInRow, double orientation){
		setBurden(burden);
		setSpacing(spacing);
		setRows(rows);
		setHolesinRow(holesInRow);
		setOrientation(orientation);
		
		dummyList = new TreeMap<Integer, Dummy>();
		holeList = new TreeMap<Integer, Hole>();
		surfaceList = new TreeMap<Integer,SurfaceConnector>();
		detonatorList = new TreeMap<Integer, Detonator>();
		iPList = new TreeMap<Integer,InitiationPoint>();
		chargeList = new TreeMap<Integer, Charge>();
		listeners = new HashSet<PatternChangeListener>();
	}
	
	public Pattern(double burden, double spacing) {
		setBurden(burden);
		setSpacing(spacing);

		dummyList = new TreeMap<Integer, Dummy>();
		holeList = new TreeMap<Integer, Hole>();
		surfaceList = new TreeMap<Integer,SurfaceConnector>();		
		detonatorList = new TreeMap<Integer, Detonator>();
		chargeList = new TreeMap<Integer, Charge>();
		iPList = new TreeMap<Integer,InitiationPoint>();
		listeners = new HashSet<PatternChangeListener>();
	}
	public Pattern(int patternID, double burden, double spacing) throws ZeroArgumentException, NegativeNumberException {
		setPatternID(patternID);
		setBurden(burden);
		setSpacing(spacing);

		dummyList = new TreeMap<Integer, Dummy>();
		holeList = new TreeMap<Integer, Hole>();
		surfaceList = new TreeMap<Integer,SurfaceConnector>();		
		detonatorList = new TreeMap<Integer, Detonator>();
		iPList = new TreeMap<Integer,InitiationPoint>();
		chargeList = new TreeMap<Integer, Charge>();
		listeners = new HashSet<PatternChangeListener>();
		
	}
	public String toString() {

		String patternInfo = "Pattern ID = "+patternID +" Pattern Name = "+patternName;

		for (Hole hole: holeList.values()) {//Dummy dummy: dummyList.values(), 

			patternInfo = (patternInfo + "Hole ID " + hole.getHoleID());
		}
		for (Dummy dummy: dummyList.values()) {//Dummy dummy: dummyList.values(), 

			patternInfo = (patternInfo + "Dummy ID" + dummy.getHoleID());
		}

		return

		patternInfo;


	}
	public boolean equals(Object duplicatePattern) {
		if (duplicatePattern == null)
			return false;
		if (!(duplicatePattern instanceof Pattern))
			return false;
		Pattern pattern1 = (Pattern)duplicatePattern;
		if (!this.holeList.equals(pattern1.holeList))
			return false;
		if (this.holeList.equals(pattern1.holeList))
			return true;
		return false;
	}
	public int hashCode() {
		  assert false : "hashCode not designed";
		  return 42; // any arbitrary constant will do 
		  }
	
	
//	ADD METHODS	
	public void addDummy(Dummy dummyHole) {
		if (!holeList.containsValue(dummyHole)){
			if (dummyHole instanceof Hole) {
				holeList.put(dummyHole.getHoleID(), (Hole) dummyHole);
				firePatternChange("Hole", null, dummyHole);
			}
			else if (dummyHole instanceof Dummy && (!(dummyHole instanceof Hole))) {
				dummyList.put(dummyHole.getHoleID(), (Dummy) dummyHole);
				firePatternChange("Dummy", null, dummyHole);
			}
			
		}
	}
	public void addDummy(Dummy newDummy, boolean useID) throws ZeroArgumentException, NegativeNumberException {
		int i = 1+holeList.size();
		int k = 1+dummyList.size();
		if (newDummy instanceof Hole) {
			if (!useID) {
				
//				while(holeList.containsKey(i)) {
//					i++;
//				}
				
				newDummy.setHoleID(i+dummyList.size());
				
				}
			holeList.put(newDummy.getHoleID(), (Hole) newDummy);
			firePatternChange("Hole", null, newDummy);
		}
		if (newDummy instanceof Dummy &&(!(newDummy instanceof Hole))) {
			if (!useID) {
				
				while(dummyList.containsKey(k)) {
					k++;
				}
				
				newDummy.setHoleID(dummyList.size()+1+holeList.size());
				}
			if (newDummy != null){
			dummyList.put(newDummy.getHoleID(), (Dummy) newDummy);
			}
			firePatternChange("Dummy", null, newDummy);
		}
		
	}
	public void addSurfaceConnector(SurfaceConnector sc){
		if (!surfaceList.containsValue(sc)){
			if (sc instanceof SurfaceConnector) {
				surfaceList.put(sc.getSurfaceID(), sc);
				firePatternChange("Surface Connector", null, sc);
			}
		}
	}
	public void addSurfaceConnector(SurfaceConnector sc, boolean useID) throws ZeroArgumentException, NegativeNumberException {

		if (sc instanceof SurfaceConnector) {
			
			if (!useID) {
				int i = 1;
				while(surfaceList.containsKey(i)) {
					i++;
				} 
				sc.setSurfaceID(i);
			}
			if (sc != null){
			surfaceList.put(sc.getSurfaceID(), (SurfaceConnector) sc);
			}
			firePatternChange("SurfaceConnector", null, sc);
		}
	}
	public void addInitiationPoint(InitiationPoint ip, boolean useID) throws ZeroArgumentException, NegativeNumberException {

		if (ip instanceof InitiationPoint) {
			if (!useID) {
				int i = 1;
				while(iPList.containsKey(i)) {
				i++;
				} 
				ip.setIPID(i);
			}
			iPList.put(ip.getIPID(), (InitiationPoint) ip);
			firePatternChange("Initiation Point", null, ip);
		}
	}
	public void addInitiationPoint(InitiationPoint ip){
		if (!iPList.containsValue(ip)){
			if (ip instanceof InitiationPoint) {
				iPList.put(ip.getIPID(), ip);
				firePatternChange("Intitiation Point", null, ip);
			}
		}
	}
	public void addListener(PatternChangeListener listen) {
		listeners.add(listen);
	}
	public void addChargesToAllHoles(Charge charge){	
		if (!chargeList.containsValue(charge)) {
			for (Hole hole : getAllHolesInPattern()) {
				addCharge(charge);
			}
		}
		
	}
	
	public void addCharge(Charge charge){
		if (!chargeList.containsValue(charge)){
			if (charge instanceof Charge) {
				chargeList.put(charge.getID(), charge);
				firePatternChange("Charge", null, charge);
			}
		}
	}

	
	public void addCharge(Charge charge, boolean useID) throws ZeroArgumentException, NegativeNumberException {
		if (charge instanceof Charge) {

			if (!useID) {
				int i = 1;
				while(detonatorList.containsKey(i)) {
					i++;
				} 
				charge.setID(i);
			}
			if (charge != null){
				chargeList.put(charge.getID(), (Charge) charge);
			}
			firePatternChange("Charge", null, charge);
		}
	}
	public void addDetonatorsToAllHoles(Detonator det){	
		if (!detonatorList.containsValue(det)) {
			for (Hole hole : getAllHolesInPattern()) {
				addDetonator(det);
			}
		}
		
	}
	
	public void addDetonator(Detonator det){
		if (!detonatorList.containsValue(det)){
			if (det instanceof Detonator) {
				detonatorList.put(det.getDetID(), det);
				firePatternChange("Detonator", null, det);
			}
		}
	}
	public void addDetonator(Detonator det, boolean useID) throws ZeroArgumentException, NegativeNumberException {
		if (det instanceof Detonator) {

			if (!useID) {
				int i = 1;
				while(detonatorList.containsKey(i)) {
					i++;
				} 
				det.setDetID(i);
			}
			if (det != null){
				detonatorList.put(det.getDetID(), (Detonator) det);
			}
			firePatternChange("Detonator", null, det);
		}
	}
//**********************************************************************************************//
//*************************	REMOVE METHODS  ****************************************************//
	
	public void removeDummyOrHole(Dummy dummy) {
		if (dummy instanceof Dummy && !(dummy instanceof Hole)) {
			if (dummyList.containsValue(dummy)) {
				
				if(!(iPList.values().isEmpty())){
					for (int j = iPList.lastKey(); j >0; j--) {
						if (getInitiationPoint(j) != null ){
							if (dummy != null && dummy.getHoleID() == getInitiationPoint(j).getIPDummy().getHoleID()){
								if(iPList.size() > 0)	{
									iPList.remove(j);
									firePatternChange("InitiationPoint", getInitiationPoint(j), null);
								}
							}
						}
					}
				}
//				for (int i = 0; i < iPList.size(); i++){				
//					if (getInitiationPoint(i).getIPDummy().equals(dummy)){
//						iPList.remove(i);
//						firePatternChange("InitiationPoint", getInitiationPoint(i), null);
//					}
//				}
				if(!(surfaceList.values().isEmpty())){
					for (int j = surfaceList.lastKey(); j >0; j--) {
						if (getSurfaceConnector(j) != null ){
							if (dummy != null && dummy.getHoleID() == getSurfaceConnector(j).getTo().getHoleID() || dummy.getHoleID() == getSurfaceConnector(j).getFrom().getHoleID()){
								if(surfaceList.size() > 0)	{
									surfaceList.remove(j);
									firePatternChange("Surface Connector", getSurfaceConnector(j), null);
								}
							}
						}
					}
				}
				
				dummyList.remove(dummy.getHoleID());
				firePatternChange("Dummy", dummy, null);
			}
		}
		if (dummy instanceof Hole) {
			if (holeList.containsValue(dummy)) {
				
				if(!(iPList.values().isEmpty())){
					for (int j = iPList.lastKey(); j >0; j--) {
						if (getInitiationPoint(j) != null ){
							if (dummy != null && dummy.getHoleID() == getInitiationPoint(j).getIPDummy().getHoleID()){
								if(iPList.size() > 0)	{
									iPList.remove(j);
									firePatternChange("InitiationPoint", getInitiationPoint(j), null);
								}
							}
						}
					}
				}
//				for (int i = 0; i < iPList.size(); i++){
//					if (getInitiationPoint(i).getIPDummy().equals(dummy)){
//						iPList.remove(i);
//						firePatternChange("InitiationPoint", getInitiationPoint(i), null);
//					}
//				}
				if(!(surfaceList.values().isEmpty())){
					for (int j = surfaceList.lastKey(); j >0; j--) {//Using .lastKey() maintains the iteration through the SurfaceConnectors ID
						if (getSurfaceConnector(j) != null){
							if (dummy != null && dummy.getHoleID() == getSurfaceConnector(j).getTo().getHoleID() || dummy.getHoleID() == getSurfaceConnector(j).getFrom().getHoleID()){
								if(surfaceList.size() > 0 )	{
									surfaceList.remove(j);
									firePatternChange("Surface Connector", getSurfaceConnector(j), null);
								}
							}
						}
					}
				}
				if(!(detonatorList.values().isEmpty())){
					for (int j = detonatorList.lastKey(); j >0; j--) {
						if (getDetonator(j) != null ){
							if ((Hole)dummy != null && ((Hole)(dummy)).getHoleID() == getDetonator(j).getInHole().getHoleID()){
								if(detonatorList.size() > 0)	{
									detonatorList.remove(j);
									firePatternChange("Detonator", getDetonator(j), null);
								}
							}
						}
					}
				}
				if(!(chargeList.values().isEmpty())){
					for (int j = chargeList.lastKey(); j >0; j--) {
						if (getCharge(j) != null ){
							if ((Hole)dummy != null && ((Hole)(dummy)).getHoleID() == getCharge(j).getInHole().getHoleID()){
								if(chargeList.size() > 0)	{
									chargeList.remove(j);
									firePatternChange("Charge", getCharge(j), null);
								}
							}
						}
					}
				}
				holeList.remove(dummy.getHoleID());

				firePatternChange("Hole", dummy, null);


			}
		}
	}
	
	public void removeAllDummys(Dummy dummy) {
		for (int k = 0; holeList.size()>k; k++){
			if (dummyList.containsValue(dummy)) {
				dummyList.remove(dummy.getHoleID());
				for (int i = 0; i < iPList.size(); i++)
					if (getInitiationPoint(i).getIPDummy().equals(dummy)){
						iPList.remove(i);
						firePatternChange("InitiationPoint", getInitiationPoint(i), null);
					}
				for (int i = (surfaceList.size()-1); i >= 0; i--){
					if (dummy.getHoleID() == getSurfaceConnector(i).getFrom().getHoleID() ||dummy.getHoleID() == getSurfaceConnector(i).getTo().getHoleID()){
						if(surfaceList.size() > 0)	{
							surfaceList.remove(i);
							firePatternChange("Surface Connector", surfaceList.get(i), null);
						}
						else{
							surfaceList.clear();
							firePatternChange("Surface Connector", surfaceList.get(i), null);
						}
					}
					
				}
				firePatternChange("Dummy", dummy, null);
			}
		}
	}
	public void removeAllHoles(Hole hole) {
		for (int k = 0; holeList.size()>k; k++){
			if (holeList.containsValue(hole)) {
				holeList.remove(hole.getHoleID());
				for (int i = 0; i < iPList.size(); i++)
					if (getInitiationPoint(i).getIPDummy().equals(hole)){
						iPList.remove(i);
						firePatternChange("InitiationPoint", getInitiationPoint(i), null);
					}
				for (int i = (surfaceList.size()-1); i >= 0; i--){
					if (hole.getHoleID() == getSurfaceConnector(i).getFrom().getHoleID() || hole.getHoleID() == getSurfaceConnector(i).getTo().getHoleID()){
						if(surfaceList.size() > 0)	{
							surfaceList.remove(i);
							firePatternChange("Surface Connector", getSurfaceConnector(i), null);
						}
						else{
							surfaceList.clear();
							firePatternChange("Surface Connector", getSurfaceConnector(i), null);
						}
					}
					
				}
				
				for (int j = detonatorList.lastKey(); j >0; j--) {
					if (getDetonator(j) != null ){
							if (hole != null && hole.getHoleID() == getDetonator(j).getInHole().getHoleID()){
								if(detonatorList.size() > 0)	{
									detonatorList.remove(j);
									firePatternChange("Detonator", getDetonator(j), null);
								}
								else{
									detonatorList.clear();
									firePatternChange("Detonator", getDetonator(j), null);
								}
							}
						}
					}
				for (int j = chargeList.lastKey(); j >0; j--) {
					if (getCharge(j) != null ){
							if (hole != null && hole.getHoleID() == getCharge(j).getInHole().getHoleID()){
								if(chargeList.size() > 0)	{
									chargeList.remove(j);
									firePatternChange("Charge", getCharge(j), null);
								}
								else{
									chargeList.clear();
									firePatternChange("Charge", getCharge(j), null);
								}
							}
						}
					}
				firePatternChange("Hole", hole, null);
			}
		}
	}
	
	public void removeSC(SurfaceConnector sc) {
		if (sc instanceof SurfaceConnector ) {
			surfaceList.remove(sc.getSurfaceID());
			firePatternChange("Surface Connector", sc, null);
		}
	}public void removeDetonator(Detonator det){
		if (det instanceof Detonator ) {
			if (detonatorList.containsValue(det)) {
				detonatorList.remove(det.getDetID());
			}
			firePatternChange("Detonator", det, null);
		}
	}
	public void removeIP(InitiationPoint ip) {
		if (ip instanceof InitiationPoint ) {
			if (iPList.containsValue(ip)) {
				iPList.remove(ip.getIPID());
			}
			firePatternChange("Initiation Point", ip, null);
		}
	}
	public void removeListener(PatternChangeListener listen) {
		listeners.remove(listen);
	}
//	GET METHODS	
	public int getPatternID(){
		return patternID;
	}
	public String getPatternName() {
		return patternName;
	}
	public int getNumberOfHoles() {
		return
		holeList.size();
	}
	public int getNumberOfDummys() {
		return 
		dummyList.size();
	}
	public int getNumberOfSurfaceConnections() {
		return 
			surfaceList.size();
	}
	public int getNumberOfIPs() {
		return
			iPList.size();
	}
	public Dummy getHoleOrDummy(int id){
		if (dummyList.get(id) != null){
			return
					dummyList.get(id);
		}
		else if (holeList.get(id) != null){
			return
					holeList.get(id);
		}
		
		else
			return null;
				
	}
	public Dummy getDummy(int id) {
		return dummyList.get(id);
	}
	public Hole getHole(int id) {
		return holeList.get(id);
	}
	
	public Collection<Hole> getAllHoles(){
		return holeList.values();
	}
	public Collection<Dummy> getAllDummys(){
		return
		dummyList.values();
	}
	public SurfaceConnector getSurfaceConnector(int scID) {
		return
		surfaceList.get(scID);
		
	}
	public Detonator getDetonator(int detID) {
		return
		detonatorList.get(detID);
		
	}
	public Charge getCharge(int id) {
		return
		chargeList.get(id);
		
	}
	public InitiationPoint getInitiationPoint(int ipID) {
		return
		iPList.get(ipID);
	}	
	public double getBurden() {
		return burden;
	}
	public double getSpacing() {
		return spacing;
	}
	public int getRows() {
		return rows;
	}
	public int getHolesInRow() {
		return holesInRow;
	}
	public double getOrientation() {
		return orientation;
	}
	public Dummy getLastDummyOrHoleIDInPattern() {

		if (dummyList != null || !dummyList.isEmpty()){
			return
					dummyList.lastEntry().getValue();
		}
		else if (holeList != null || !holeList.isEmpty()){
			return
					holeList.lastEntry().getValue();
		}
		else
		return null;

	}
	public Rectangle2D getBounds(){
		Rectangle2D tempBounds = new Rectangle2D.Double(0,0,0,0);
		if (getNumberOfHoles() > 0 ) {
			tempBounds = null;
			for (Dummy tempDummy : holeList.values()) {
				if (tempBounds != null) {
					tempBounds = tempBounds.createUnion(tempDummy.getBounds());
				}
				else
					tempBounds = tempDummy.getBounds();
			}
		}		
		return tempBounds;
	}
	/**
	 * 
	 * @param pattern
	 * @return All Dummys in dummyList in a Pattern
	 */
	public Set<Dummy> getAllDummysInPattern(){
		Set<Dummy> allDummys = new HashSet<Dummy>();
		
		for (Dummy tempDummy : dummyList.values()){
			
			if (tempDummy instanceof Dummy && (!(tempDummy instanceof Hole))) {
				allDummys.add(tempDummy);
			}
		}
		return allDummys;
	}
	public Set<Dummy> getAllDummysAndHoles(){
		Set<Dummy> allDummys = new HashSet<Dummy>();
		for (Dummy tempDummy: dummyList.values()){
			allDummys.add(tempDummy);
		}
		for (Hole tempHole: holeList.values()){
			allDummys.add(tempHole);
		}
		
		return allDummys;
		
	}
	/**
	 * 
	 * @param pattern
	 * @return All Holes in holeList in a Pattern
	 */
	public Set<Hole> getAllHolesInPattern(){
		Set<Hole> allHoles = new HashSet<Hole>();
		for (Hole tempHole: holeList.values()){
			if (tempHole instanceof Hole) {
				
				allHoles.add(tempHole);
			}
		}
		return allHoles;
		
	}
	
	
	/**
	 * 
	 * @param polygon
	 * @return All dummys and holes in a polygon shape
	 */
    public Set<Dummy> getAllDummysIn(Shape polygon) {
		Set<Dummy> allDummys = new HashSet<Dummy>();
		for (Dummy tempDummy : dummyList.values()) {
			
			if (polygon.intersects(tempDummy.getBounds())){
				allDummys.add(tempDummy);
			}
			
		}
		for (Hole tempHole: holeList.values()){
			if (polygon.intersects(tempHole.getBounds())){
				allDummys.add(tempHole);
			}
		}
		return allDummys;
	}
    public SurfaceConnector getSurfaceConnectorIn(Shape ellipse) {
		
    	for (SurfaceConnector tempSC : surfaceList.values()) {
			if (tempSC.getBounds().contains(ellipse.getBounds2D()) ?
					tempSC.getBounds().contains(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY()): ellipse.intersects(tempSC.getBounds().getBounds2D())){
				return
				tempSC;
			}
		}
		return null;
	}
    public Dummy getDummyOrHoleIn(Shape ellipse){
    
    	for (Dummy tempDummy: dummyList.values()){
    		if(tempDummy instanceof Dummy  && (!(tempDummy instanceof Hole))){
    			if (tempDummy.getBounds().getWidth() == 0 || tempDummy.getBounds().getHeight() == 0 ?
    					ellipse.contains(tempDummy.getBounds().getX(), tempDummy.getBounds().getY()): 
    						ellipse.intersects(tempDummy.getBounds())){
    				return
    				tempDummy;
    			}
    		}
    	}
    	for (Dummy tempHole: holeList.values()){
    		if(tempHole instanceof Hole){
    			if (tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
    					ellipse.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
    						ellipse.intersects(tempHole.getBounds())){
    				return
    				tempHole;
    			}
    		}
    	}
    	return null;
    
    }
    public Hole getHoleIn(Shape ellipse){
        
    	for (Hole tempHole: holeList.values()){
    		if(tempHole instanceof Hole){
    			if (tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
    					ellipse.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
    						ellipse.intersects(tempHole.getBounds())){
    				return
    				tempHole;
    			}
    		}
    	}
    	return null;
    
    }
    public Set<Detonator> getAllDetonators(){
		Set<Detonator> allDetonators = new HashSet<Detonator>();
		
		for (Detonator tempDet : detonatorList.values()){
			
			if (tempDet instanceof Detonator) {
				allDetonators.add(tempDet);
			}
		}
		return allDetonators;
	}
    public Set<Charge> getAllCharges(){
 		Set<Charge> allCharges = new HashSet<Charge>();
 		
 		for (Charge temp : chargeList.values()){
 			
 			if (temp instanceof Charge) {
 				allCharges.add(temp);
 			}
 		}
 		return allCharges;
 	}
    
    public Set<SurfaceConnector> getAllSurfaceConnectors(){
		Set<SurfaceConnector> allSurface = new HashSet<SurfaceConnector>();
		
		for (SurfaceConnector tempSC : surfaceList.values()){
			
			if (tempSC instanceof SurfaceConnector) {
				allSurface.add(tempSC);
				System.out.println("Surface Connector "+tempSC.getSurfaceID()+" added from " + tempSC.getFrom().getHoleID());
			}
		}
		return allSurface;
	}
    public Set<InitiationPoint> getAllInitiationPoints(){
		Set<InitiationPoint> allIPs = new HashSet<InitiationPoint>();
		
		for (InitiationPoint tempIP : iPList.values()){
			
			if (tempIP instanceof InitiationPoint) {
				allIPs.add(tempIP);
			}
		}
		return allIPs;
	}
    /**
    *@param accepts an ellipse of Type Shape
    *@return the first object in the Set<Object>  
    *
    **/
	public Set<Object> getFirstObjectIn(Shape ellipse) {
		
		allObjects.clear(); //Clears the allObject variable so that only one object can be in the Set<Object> at any time.
		
		for (Dummy tempDummy : dummyList.values()) { //Searches the TreeMap dummyList 
			if (tempDummy.getBounds().getWidth() == 0 || tempDummy.getBounds().getHeight() == 0 ?
					ellipse.contains(tempDummy.getBounds().getX(), tempDummy.getBounds().getY()): 
						ellipse.intersects(tempDummy.getBounds())){
				allObjects.add(tempDummy);
				
				return allObjects;
			}
		}
		
		for (Hole tempHole: holeList.values()){//Searches the TreeMap holeList
			if (tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
					ellipse.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
						ellipse.intersects(tempHole.getBounds())){
				
				allObjects.add(tempHole);
				
				return allObjects;
			}
		}
		
		for (SurfaceConnector tempSC : surfaceList.values()) {
			Point2D p = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());
    		Area scA = new Area(tempSC.getBounds());
			if (scA.equals(0) ?
					tempSC.getBounds().contains(p): tempSC.getBounds().intersects(ellipse.getBounds2D())){
				allObjects.clear();
				allObjects.add(tempSC);
				
				return 
				allObjects;
			}
		}
	
		
		return allObjects;
	}
	
	 /**
	    *@param accepts an ellipse of Type Shape
	    *@return the accumulation of next added objects in the Set<Object>  
	    *
	    **/	
	public Set<Object> addNextObjectIn(Shape ellipse) {
	
		for (Dummy tempDummy : dummyList.values()) {
			if (tempDummy.getBounds().getWidth() == 0 || tempDummy.getBounds().getHeight() == 0 ?
					ellipse.contains(tempDummy.getBounds().getX(), tempDummy.getBounds().getY()): 
						ellipse.intersects(tempDummy.getBounds())){
				allObjects.add(tempDummy);
				return
				allObjects;
			}
		}
		for (Hole tempHole: holeList.values()){
			if (tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
					ellipse.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
						ellipse.intersects(tempHole.getBounds())){
				allObjects.add(tempHole);
				return
				allObjects;
			}
		}
		for (SurfaceConnector tempSC : surfaceList.values()) {
			Point2D p = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());
    		Area scA = new Area(tempSC.getBounds());
			if (scA.equals(0) ?
					tempSC.getBounds().contains(p): tempSC.getBounds().intersects(ellipse.getBounds2D())){
				allObjects.add(tempSC);
				return
				allObjects;
			}
		}
	

		return allObjects;
	}
	/**
	 * @author brentbuffham
	 * @explaination Removes the selected object in the Set as long as it is in the Set
	 * @param ellipse
	 * @return 
	 */
	public Set<Object> removeFirstObjectIn(Shape ellipse) {
		for (Dummy tempDummy : dummyList.values()) {
			if (allObjects.contains(tempDummy) && tempDummy.getBounds().getWidth() == 0 || tempDummy.getBounds().getHeight() == 0 ?
					ellipse.contains(tempDummy.getBounds().getX(), tempDummy.getBounds().getY()): 
						ellipse.intersects(tempDummy.getBounds())){
				allObjects.remove(tempDummy);
				return
				allObjects;
			}
		}
		
		for (Hole tempHole: holeList.values()){
			if (allObjects.contains(tempHole) && tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
					ellipse.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
						ellipse.intersects(tempHole.getBounds())){
				allObjects.remove(tempHole);
				return
				allObjects;
			}
		}
		
		for (SurfaceConnector tempSC : surfaceList.values()) {
			Point2D p = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());
    		Area scA = new Area(tempSC.getBounds());
			if (allObjects.contains(tempSC) && scA.equals(0) ? tempSC.getBounds().contains(p): tempSC.getBounds().intersects(ellipse.getBounds2D())){
				allObjects.remove(tempSC);
				return
				allObjects;
			}
		}
	

		return null;
	}
	/**
	 * 
	 * @return All objects in the pattern that it is used on
	 */
	public Set<Object> getAllObjectsInPattern(){
		for (SurfaceConnector temp : surfaceList.values()){
			allObjects.add(temp);
		}
		for (Detonator temp : detonatorList.values()){
			allObjects.add(temp);
		}
		for (Charge temp : chargeList.values()){
			allObjects.add(temp);
		}
		for (Hole temp : holeList.values()){
			allObjects.add(temp);
		}
		for (Dummy temp : dummyList.values()){
			allObjects.add(temp);
		}
		for (Dummy temp : dummyList.values()){
			allObjects.add(temp);
		}
		for (InitiationPoint temp : iPList.values()){
			allObjects.add(temp);
		}
		
		return allObjects;
	}
	
	
//THIS NEEDS WORK FOR THE MULTI TIE FUNCTION	
public Dummy getDummyInLine(Line2D line) {
		for (Dummy tempDummy : dummyList.values()) {
			if (tempDummy.getBounds().getWidth() == 0 || tempDummy.getBounds().getHeight() == 0 ?
					line.contains(tempDummy.getBounds().getX(), tempDummy.getBounds().getY()): 
						line.intersects(tempDummy.getBounds())){
				return
					tempDummy;
			}
		}
		for (Hole tempHole: holeList.values()){
			if (tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
					line.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
						line.intersects(tempHole.getBounds())){
				return
					tempHole;
			}
		}
		return null;
	}
	
	public Dummy getDummyAt(Point2D point) {
		
		for (Dummy tempDummy : dummyList.values()) {
			if (tempDummy.getBounds().contains(point)){	return tempDummy; }
		}
		for (Hole tempHole : holeList.values()) {
			
			if (tempHole.getBounds().contains(point)){
				return
				tempHole;
			}
		
		}
		return null;
	}
	
	
//	SET METHODS		
	public void setBurden(double burden) throws IllegalArgumentException {
		if (burden > 0 || burden < 1000)
			this.burden = burden;
		else
			throw new IllegalArgumentException ("Burdens outside program range");
	}
	public void setSpacing(double spacing) throws IllegalArgumentException {
		if (spacing > 0 || spacing < 2000)
			this.spacing = spacing;
		else
			throw new IllegalArgumentException ("Spacing outside program range");
	}
	public void setRows(int rows) throws IllegalArgumentException {
		if (rows >0 || rows< 1000)
			this.rows = rows;
		else
			throw new IllegalArgumentException ("Row range exceeded");
	}
	public void setHolesinRow(int holesInRow) throws IllegalArgumentException{
		if (holesInRow >0 || holesInRow < 5000)
			this.holesInRow = holesInRow;
		else
			throw new IllegalArgumentException ("Holes in row outside range");
	}
	public void setOrientation(double orientation) throws IllegalArgumentException  {
		if (orientation > -361 || orientation < 361)
			this.orientation = orientation;
		else
			throw new IllegalArgumentException ("Orientation has to be between -360 and 360 degrees");
	}
	public void setPatternID(int pID) throws ZeroArgumentException, NegativeNumberException{
		if (pID == 0)
			throw new ZeroArgumentException("A pattern ID number can not be equal to 0. - Pattern Class");
		else if (pID < 0)
			throw new NegativeNumberException("A pattern ID number can not be a negative integer. - Pattern Class");
		else
			this.patternID = pID;
		}
	public void setPatternName(String name) {
		if (name == null) {
			name = "Default Name #"+getPatternID();
		}
		else
			this.patternName = name;
	}

//LISTENER METHODS

	private void firePatternChange(String propertyName, Object oldValue, Object newValue) {
		
		PropertyChangeEvent patternChange = new PropertyChangeEvent(this ,propertyName, oldValue, newValue);
		
		for (Iterator<PatternChangeListener> i = listeners.iterator(); i.hasNext();) {
			((PropertyChangeListener) i.next()).propertyChange(patternChange);
		}
	}
	/**
	 * @return the holeList
	 */
	public TreeMap<Integer, Hole> getHoleList() {
		return holeList;
	}
	/**
	 * @param holeList the holeList to set
	 */
	public void setHoleList(TreeMap<Integer, Hole> holeList) {
		this.holeList = holeList;
	}
	/**
	 * @return the surfaceList
	 */
	public TreeMap<Integer,SurfaceConnector> getSurfaceList() {
		return surfaceList;
	}
	/**
	 * @param surfaceList the surfaceList to set
	 */
	public void setSurfaceList(TreeMap<Integer,SurfaceConnector> surfaceList) {
		this.surfaceList = surfaceList;
	}
	/**
	 * @return the iPList
	 */
	public TreeMap<Integer,InitiationPoint> getiPList() {
		return iPList;
	}
	/**
	 * @param iPList the iPList to set
	 */
	public void setiPList(TreeMap<Integer,InitiationPoint> iPList) {
		this.iPList = iPList;
	}
	/**
	 * @return the detonatorList
	 */
	public TreeMap<Integer, Detonator> getDetonatorList() {
		return detonatorList;
	}
	/**
	 * @param detonatorList the detonatorList to set
	 */
	public void setDetonatorList(TreeMap<Integer, Detonator> detonatorList) {
		this.detonatorList = detonatorList;
	}
	/**
	 * @return the dummyList
	 */
	public TreeMap<Integer, Dummy> getDummyList() {
		return dummyList;
	}
	/**
	 * @param dummyList the dummyList to set
	 */
	public void setDummyList(TreeMap<Integer, Dummy> dummyList) {
		this.dummyList = dummyList;
	}
	public TreeMap<Integer, Charge> getChargeList() {
		return chargeList;
	}
	public void setChargeList(TreeMap<Integer, Charge> chargeList) {
		this.chargeList = chargeList;
	}
	

	
	
}
