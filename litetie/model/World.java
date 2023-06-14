package litetie.model;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.tree.DefaultMutableTreeNode;

import litetie.CalculateUtility;



public class World {

	private Object world;
	private TreeMap<Integer, Pattern> patternList;
	private TreeMap<Integer, Coordinate> coordList;
	private Pattern pattern;
	private Set<WorldChangeListener> listeners;
	private LinkedHashSet<Object> allInWorld = new LinkedHashSet<Object>();
	private int patternID;
	private TreeMap<Integer, Boundary> boundaryList;
	private TreeMap<Integer, Text> textList;
//	private TreeMap<Integer, InitiationPoint> ipList;
	private int boundaryID;
	private Boundary boundary;
	LinkedHashSet<Object> allObjects = new LinkedHashSet<Object>();
	ArrayDeque<Object> allFIFOObjects = new ArrayDeque<Object>();	


	public World(){
		setWorld(world);
		coordList = new TreeMap<Integer, Coordinate>();
		boundaryList = new TreeMap<Integer, Boundary>();
		patternList = new TreeMap<Integer, Pattern>();
		textList = new TreeMap<Integer, Text>();
		listeners = new HashSet<WorldChangeListener>();
	}

	public World(String worldName){
		setWorld(world);
		setWorldName(worldName);
		coordList = new TreeMap<Integer, Coordinate>();
		boundaryList = new TreeMap<Integer, Boundary>();
		patternList = new TreeMap<Integer, Pattern>();
		textList = new TreeMap<Integer, Text>();
		listeners = new HashSet<WorldChangeListener>();

	}
	//	public DefaultMutableTreeNode child = new DefaultMutableTreeNode();
	//	public DefaultMutableTreeNode parent = new DefaultMutableTreeNode();
	//	public int index = 0;

	public World getWorld(){
		return (World) world;
	}
	public void setWorld(Object liteTieWorld){
		this.world = liteTieWorld;
	}
	public void setWorldName(String worldName){
		this.world = worldName;
	}
	
	public TreeMap<Integer,Coordinate> getCoordList(){
		return coordList;
	}
	public void setCoordList(TreeMap<Integer, Coordinate> coordList){
		this.coordList = coordList;
	}
	/**
	 * Fills a Map of all the coordindates.
	 * @return coordList
	 */
	public TreeMap<Integer,Coordinate> addAllCoordinates(){
		int i = 0;
		if (!(patternList.isEmpty())) {
			for (Pattern p : patternList.values()) {
				for (Hole temp : p.getHoleList().values()){
					coordList.put(i++, temp);
				}
				for (Dummy temp : p.getDummyList().values()){
					coordList.put(i++, temp);
				}
			}
		}
		if (!(boundaryList.isEmpty())) {
			for (Boundary b : boundaryList.values()) {
				for (BPoint temp : b.getBPList().values()){
					coordList.put(i++, temp);
				}
			}
		}
		if (!(textList.isEmpty())) {
			for (Text temp : textList.values()) {
				coordList.put(i++, temp);
			}
		}

		return coordList;
		
	}
	
	

	//////////////////////////////////////////////
	//////////          PATTERN 
	/////////////////////////////////////////////


	public TreeMap<Integer,Pattern> getPatternList(){
		return patternList;
	}
	//Change these if needed to suit TreeMap
	public Pattern getLastPattern(){
		return patternList.get(patternList.lastKey());
	}

	public Pattern getFirstPattern(){
		return patternList.get(patternList.firstKey());
	}
	public Pattern getPattern(int patternID){
		return patternList.get(patternID);
	}

	public void setPatternList(TreeMap<Integer, Pattern> patList){
		this.patternList = patList;
	}

	public void insertPattern(Pattern p) {
		if (!(patternList.containsValue(p))){ //if thepatternList does not contain the value pattern "p"
			patternList.put(p.getPatternID(), p);//then put the pattern "p" int the list with the integer key as "patternID"
			fireWorldChange("Pattern", null, p);//let the listeners know that a pattern has been added.
		}
	}

	public void addPattern(Pattern newPattern, boolean useID) throws ZeroArgumentException, NegativeNumberException {
		if (!useID) {
			int i = 1;
			while(patternList.containsKey(i)) {
				i++;
			} 
			newPattern.setPatternID(i);
		}
		patternList.put(newPattern.getPatternID(), newPattern);
		fireWorldChange("Pattern", null, newPattern);

	}

	public void removePatternIfEmpty(Pattern p){
		if(p.getDummyList().isEmpty() && p.getHoleList().isEmpty()){
			patternList.remove(p.getPatternID());
			fireWorldChange("Pattern ID", p, null);
		}
	}
	public void removePattern(Pattern p) {

		patternList.remove(p.getPatternID());
		fireWorldChange("Pattern ID", p, null);

	}


	//////////////////////////////////////////////
	//////////          BOUNDARY 
	/////////////////////////////////////////////


	public TreeMap<Integer,Boundary> getBoundaryList(){
		return boundaryList;
	}
	//Change these if needed to suit TreeMap
	public Boundary getLastBoundary(){
		return boundaryList.get(boundaryList.lastKey());
	}

	public Boundary getFirstBoundary(){
		return boundaryList.get(boundaryList.firstKey());
	}
	public Boundary getBoundary(int boundaryID){
		return boundaryList.get(boundaryID);
	}

	public void setBoundaryList(TreeMap<Integer, Boundary> boundaryList){
		this.boundaryList = boundaryList;
	}


	public void insertBoundary(Boundary p) {
		if (!(boundaryList.containsValue(p))){ //if thepatternList does not contain the value pattern "p"
			boundaryList.put(p.getBoundaryID(), p);//then put the pattern "p" int the list with the integer key as "patternID"
			fireWorldChange("Boundary", null, p);//let the listeners know that a pattern has been added.
		}
	}

	public void addBoundary(Boundary newBoundary, boolean useID) throws ZeroArgumentException, NegativeNumberException {
		if (!useID) {
			int i = 1;
			while(boundaryList.containsKey(i)) {
				i++;
			} 
			newBoundary.setBoundaryID(i);
		}
		boundaryList.put(newBoundary.getBoundaryID(), newBoundary);
		fireWorldChange("Boundary", null, newBoundary);

	}

	public void removeBoundaryIfEmpty(Boundary b){
		if(b.getBPList().isEmpty()){
			boundaryList.remove(b.getBoundaryID());
			fireWorldChange("Boundary ID", b, null);
		}
	}
	public void removeBoundary(Boundary b) {

		boundaryList.remove(b.getBoundaryID());
		fireWorldChange("Boundary ID", b, null);

	}

	//////////////////////////////////////////////
	//////////          TEXT 
	/////////////////////////////////////////////

	public TreeMap<Integer,Text> getTextList(){
		return textList;
	}
	//Change these if needed to suit TreeMap
	public Text getLastText(){
		return textList.get(textList.lastKey());
	}

	public Text getFirstText(){
		return textList.get(textList.firstKey());
	}
	public Text getTextID(int textID){
		return textList.get(textID);
	}
	public String getText(int textID){
		return textList.get(textID).getText();
	}
	
	public void setTextList(TreeMap<Integer, Text> textList){
		this.textList = textList;
	}


	public void insertText(Text p) {
		if (!(textList.containsValue(p))){ //if thepatternList does not contain the value pattern "p"
			textList.put(p.getTextID(), p);//then put the pattern "p" int the list with the integer key as "patternID"
			fireWorldChange("text", null, p);//let the listeners know that a pattern has been added.
		}
	}

	public void addText(Text text, boolean useID) throws ZeroArgumentException, NegativeNumberException {
		if (!useID) {
			int i = 1;
			while(textList.containsKey(i)) {
				i++;
			} 
			text.setTextID(i);
		}
		textList.put(text.getTextID(), text);
		fireWorldChange("text", null, text);

	}

	public void removeTextIfEmpty(Text p){
		if(textList.isEmpty()){
			textList.remove(p.getTextID());
			fireWorldChange("Text ID", p, null);
		}
	}
	public void removeText(Text b) {

		textList.remove(b.getTextID());
		fireWorldChange("Text ID", b, null);

	}

	//////////////////////////////////////////////
	///////////   SELECTION METHODS
	////////////////////////////////////////////

	public void removeParentObject(Object o) {
		if (o instanceof Pattern ) {
			removePattern(((Pattern)o));
			fireWorldChange("Pattern", o, null);
		}
		if (o instanceof Boundary ) {
			removeBoundary(((Boundary)o));
			fireWorldChange("Boundary", o, null);
		}
		if (o instanceof Text ) {
			removeText(((Text)o));
			fireWorldChange("Text", o, null);
		}

	}
	public void removeChildObject(Object o){

		for(Boundary temp: boundaryList.values()){
			for( BPoint t : temp.getBPList().values()){
				if(o instanceof BPoint && t.equals(o)){

					temp.removeBPoint((BPoint) o);
				}
			}
		}

		for(Pattern temp: patternList.values()){
			if(o instanceof Dummy){
				temp.removeDummyOrHole((Dummy) o);
			}
			if(o instanceof SurfaceConnector){
				temp.removeSC((SurfaceConnector) o);
			}
			if(o instanceof Detonator){
				temp.removeDetonator((Detonator) o);
			}
			if(o instanceof InitiationPoint){
				temp.removeIP((InitiationPoint) o);
			}

		}

	}

	/**
	 * @author brentbuffham
	 * @explaination Removes the selected object in the Set as long as it is in the Set
	 * @param ellipse
	 * @return 
	 */
	public Set<Object> removeFirstObjectIn(Shape ellipse) {

		for (Text temp: textList.values()){
			if (allObjects.contains(temp) && temp.getBounds().getWidth() == 0 || temp.getBounds().getHeight() == 0 ?
					ellipse.contains(temp.getBounds().getX(), temp.getBounds().getY()): 
						ellipse.intersects(temp.getBounds())){
				allObjects.remove(temp);
				return
				allObjects;
			}
		}
		for (Boundary temp: boundaryList.values()){
			if (temp.getPolyBounds().intersects(ellipse.getBounds2D().getX(), ellipse.getBounds2D().getY(),ellipse.getBounds2D().getWidth(), ellipse.getBounds2D().getHeight())){
				allObjects.remove(temp);
			}
		}
		for (Boundary temp: boundaryList.values()){
			for (BPoint tempPoint : temp.getBPList().values()) {
				if (allObjects.contains(tempPoint) && tempPoint.getBounds().getWidth() == 0 || tempPoint.getBounds().getHeight() == 0 ?
						ellipse.contains(tempPoint.getBounds().getX(), tempPoint.getBounds().getY()): 
							ellipse.intersects(tempPoint.getBounds())){
					allObjects.remove(tempPoint);
					return
					allObjects;
				}
			}
		}
		for(Pattern temp: patternList.values()){
			for (Dummy tempDummy : temp.getDummyList().values()) {
				if (allObjects.contains(tempDummy) && tempDummy.getBounds().getWidth() == 0 || tempDummy.getBounds().getHeight() == 0 ?
						ellipse.contains(tempDummy.getBounds().getX(), tempDummy.getBounds().getY()): 
							ellipse.intersects(tempDummy.getBounds())){
					allObjects.remove(tempDummy);
					return
					allObjects;
				}
			}

			for (Hole tempHole: temp.getHoleList().values()){
				if (allObjects.contains(tempHole) && tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
						ellipse.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
							ellipse.intersects(tempHole.getBounds())){
					allObjects.remove(tempHole);
					return
					allObjects;
				}
			}

			for (SurfaceConnector tempSC : temp.getSurfaceList().values()) {
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
		return null;
	}
	public LinkedHashSet<Object> getAllObjectsIn(Shape polygon) {

		LinkedHashSet<Object> allInShape = new LinkedHashSet<Object>();
//		allObjects.clear();
		for (Text t : textList.values()) { //Searches the TreeMap dummyList 
			if (polygon.intersects(t.getBounds())){
				allObjects.add(t);
			}
			else if (!(polygon.intersects(t.getBounds()))) {
				allObjects.remove(t);
			}
		}
		for (Boundary temp : boundaryList.values()){
			for(BPoint t: temp.getBPList().values()){
				if (polygon.intersects(t.getBounds())){
					allObjects.add(t);	
				}
				else if (!(polygon.intersects(t.getBounds()))) {
					allObjects.remove(t);
				}
			}
		}
		for(Pattern p: patternList.values()){
			for (Dummy t : p.getDummyList().values()) {
				if (polygon.intersects(t.getBounds())){
					allObjects.add(t);
				}
				else if (!(polygon.intersects(t.getBounds()))) {
					allObjects.remove(t);
				}
			}
			for (Hole t: p.getHoleList().values()){
				if (polygon.intersects(t.getBounds())){
					allObjects.add(t);
				}
				else if (!(polygon.intersects(t.getBounds()))) {
					allObjects.remove(t);
				}
			}
			for (SurfaceConnector t : p.getSurfaceList().values()) {
				if (/*polygon.contains(new Point2D.Double(tempSC.getFrom().getEasting(), tempSC.getFrom().getNorthing())) ||*/
						polygon.contains(new Point2D.Double(t.getTo().getX(), t.getTo().getY())) ||
						polygon.contains(new Point2D.Double(t.getBounds().getBounds2D().getCenterX(), t.getBounds().getBounds2D().getCenterY()))){
					allObjects.add(t);
				}
				else if (!(/*polygon.contains(new Point2D.Double(tempSC.getFrom().getEasting(), tempSC.getFrom().getNorthing())) ||*/
						polygon.contains(new Point2D.Double(t.getTo().getX(), t.getTo().getY())) ||
						polygon.contains(new Point2D.Double(t.getBounds().getBounds2D().getCenterX(), t.getBounds().getBounds2D().getCenterY())))){
					allObjects.remove(t);
				}
//				Point2D xy = new Point2D.Double(polygon.getBounds2D().getCenterX(), polygon.getBounds2D().getCenterY());
//				Area scA = new Area(tempSC.getBounds());
//				if (scA.equals(0) ?
//						tempSC.getBounds().contains(xy): tempSC.getBounds().intersects(polygon.getBounds2D())){
//					allInShape.add(tempSC);
//				}
			}
			for (Detonator t: p.getDetonatorList().values()){
				if (polygon.intersects(t.getBounds())){
					allObjects.add(t);
				}
				else if (!(polygon.intersects(t.getBounds()))) {
					allObjects.remove(t);
				}
			}
		}
		return allObjects;
	}
	public LinkedHashSet<Object> getDummysAndHolesIn(Shape polygon) {
//		CalculateUtility calcs = new CalculateUtility();
//		LinkedHashSet<Dummy> allInShape = new LinkedHashSet<Dummy>();	
		for(Pattern p: patternList.values()){
			for (Dummy t : p.getDummyList().values()) {
				if (polygon.contains(t.getBounds())){
					allObjects.add(t);	
//					System.out.println("Added dummy ["+t.getHoleID()+"] at Index(" + calcs.getSetIndex(allObjects, t)+")");
				}
				else if (!(polygon.contains(t.getBounds()))) {
//					System.out.println("Removing dummy ["+t.getHoleID()+"] from Index(" + calcs.getSetIndex(allObjects, t)+")");
					allObjects.remove(t);
				}
			}
			for (Hole t: p.getHoleList().values()){
				if (polygon.contains(t.getBounds())){
					allObjects.add(t);
//					System.out.println("Added dummy ["+t.getHoleID()+"] at Index(" + calcs.getSetIndex(allObjects, t)+")");
				}
				else if (!(polygon.contains(t.getBounds()))) {
//					System.out.println("Removing dummy ["+t.getHoleID()+"] from Index(" + calcs.getSetIndex(allObjects, t)+")");
					allObjects.remove(t);
					}
			}
		}
		return allObjects;
	}
	public ArrayDeque<Object> getDummysAndHolesFIFO(Shape polygon) {
	
		for(Pattern p: patternList.values()){
			for (Dummy t : p.getDummyList().values()) {
				if (polygon.intersects(t.getBounds())){
					allFIFOObjects.add(t);	
				}
				else if (!(polygon.intersects(t.getBounds()))) {
					allFIFOObjects.remove(t);
				}
			}
			for (Hole t: p.getHoleList().values()){
				if (polygon.intersects(t.getBounds())){
					allFIFOObjects.add(t);
				}
				else if (!(polygon.intersects(t.getBounds()))) {
					allFIFOObjects.remove(t);
				}
			}
		}
		return allFIFOObjects;
	}
	public Set<Object> getAllSurfaceIn(Shape polygon) {

		LinkedHashSet<Object> allInShape = new LinkedHashSet<Object>();		
		for(Pattern p: patternList.values()){

			for (SurfaceConnector tempSC : p.getSurfaceList().values()) {
//				Point2D xy = new Point2D.Double(polygon.getBounds2D().getCenterX(), polygon.getBounds2D().getCenterY());
//				Area scA = new Area(tempSC.getBounds());
//				if (scA.equals(0) ?
//						tempSC.getBounds().contains(xy): tempSC.getBounds().intersects(polygon.getBounds2D())){
//					allInShape.add(tempSC);
//				}
				if (/*polygon.contains(new Point2D.Double(tempSC.getFrom().getEasting(), tempSC.getFrom().getNorthing())) ||*/
						polygon.contains(new Point2D.Double(tempSC.getTo().getX(), tempSC.getTo().getY())) ||
						polygon.contains(new Point2D.Double(tempSC.getBounds().getBounds2D().getCenterX(), tempSC.getBounds().getBounds2D().getCenterY()))){
					allObjects.add(tempSC);
			}
				else if (!(/*polygon.contains(new Point2D.Double(tempSC.getFrom().getEasting(), tempSC.getFrom().getNorthing())) ||*/
						polygon.contains(new Point2D.Double(tempSC.getTo().getX(), tempSC.getTo().getY())) ||
						polygon.contains(new Point2D.Double(tempSC.getBounds().getBounds2D().getCenterX(), tempSC.getBounds().getBounds2D().getCenterY())))){
					allObjects.remove(tempSC);
			}
			}
		}
		return allObjects;
	}
	public LinkedHashSet<Object> getAllDummysIn(Shape polygon) {

		LinkedHashSet<Object> allInShape = new LinkedHashSet<Object>();		
		for(Pattern p: patternList.values()){
			for (Dummy t : p.getDummyList().values()) {
				if (t instanceof Dummy && !(t instanceof Hole)) {
					if (polygon.intersects(t.getBounds())) {
						allObjects.add(t);
					}
					else if (!(polygon.intersects(t.getBounds())) ){
						allObjects.remove(t);
					}
				}
			}
		}
		return allObjects;
	}
	public LinkedHashSet<Object> getAllHolesIn(Shape polygon) {

		LinkedHashSet<Object> allInShape = new LinkedHashSet<Object>();		
		for(Pattern p: patternList.values()){
			for (Hole t : p.getHoleList().values()) {
				if (t instanceof Hole) {
					if (polygon.intersects(t.getBounds())) {
						allObjects.add(t);	
					}
					else if (!(polygon.intersects(t.getBounds()))) {
						allObjects.remove(t);	
					}
				}
			}
		}
		return allObjects;
	}
	public LinkedHashSet<Object> getAllDetonatorsIn(Shape polygon) {

		LinkedHashSet<Object> allInShape = new LinkedHashSet<Object>();		
		for(Pattern p: patternList.values()){
			for (Detonator t : p.getDetonatorList().values()) {
				if (t instanceof Detonator) {
					if (polygon.intersects(t.getBounds())) {
						allObjects.add(t);
					}
					else if (!(polygon.intersects(t.getBounds()))) {
						allObjects.remove(t);	
					}
				}
			}
		}
		return allObjects;
	}
	public LinkedHashSet<Object> getAllTextIn(Shape polygon) {

		LinkedHashSet<Object> allInShape = new LinkedHashSet<Object>();		
		for (Text t : textList.values()) { //Searches the TreeMap dummyList 
			if (polygon.intersects(t.getBounds())){
				allObjects.add(t);	
			}
			else if (!(polygon.intersects(t.getBounds()))) {
				allObjects.remove(t);	
			}
		}
		return allObjects;
	}
	public LinkedHashSet<Object> getAllBPointsIn(Shape polygon) {

		LinkedHashSet<Object> allInShape = new LinkedHashSet<Object>();		
		for (Boundary temp : boundaryList.values()){
			for(BPoint t: temp.getBPList().values()){
				if (polygon.intersects(t.getBounds())){
					allObjects.add(t);
				}
				else if (!(polygon.intersects(t.getBounds()))) {
					allObjects.remove(t);	
				}
			}
		}
		return allObjects;
	}
	public Boundary getBoundaryInShape(Rectangle2D polygon) {
		Boundary temp = null;
		for(Boundary b: boundaryList.values())	{
			if (b.getPolyBounds().intersects(polygon)){
					temp = b;
			}
		}
		
		
		
		return temp;
	}

	/**
	 *@param accepts an ellipse of Type Shape
	 *@return the first object in the Set<Object>  
	 *
	 **/
	public LinkedHashSet<Object> getFirstObjectIn(Shape ellipse) {

		allObjects.clear(); //Clears the allObject variable so that only one object can be in the Set<Object> at any time.

		for (Text temp : textList.values()) { 
			if (temp.getBounds().getWidth() == 0 || temp.getBounds().getHeight() == 0 ?
					ellipse.contains(temp.getBounds().getX(), temp.getBounds().getY()): 
						ellipse.intersects(temp.getBounds())){
				allObjects.add(temp);

				return allObjects;
			}
		}
		for (Boundary temp : boundaryList.values()){
			if (temp.getPolyBounds().intersects(ellipse.getBounds2D().getX(), ellipse.getBounds2D().getY(),ellipse.getBounds2D().getWidth(), ellipse.getBounds2D().getHeight())){
				allObjects.add(temp);
		}
			for(BPoint t: temp.getBPList().values()){
				if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? ellipse.contains(t.getBounds().getX(), t.getBounds().getY()): ellipse.intersects(t.getBounds())){
					allObjects.add(t);
				}
			}
		}
		for (Pattern temp : patternList.values()){
			
			for(Dummy t: temp.getDummyList().values()){
				if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? ellipse.contains(t.getBounds().getX(), t.getBounds().getY()): ellipse.intersects(t.getBounds())){
					allObjects.add(t);
				}
			}
			for(Hole t: temp.getHoleList().values()){
				if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? ellipse.contains(t.getBounds().getX(), t.getBounds().getY()): ellipse.intersects(t.getBounds())){
					allObjects.add(t);
				}
			}
			for (SurfaceConnector t : temp.getSurfaceList().values()) {
				Point2D p = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());
				Area scA = new Area(t.getBounds());
				if (scA.equals(0) ?
						t.getBounds().contains(p): t.getBounds().intersects(ellipse.getBounds2D())){
//					allObjects.clear();
					allObjects.add(t);

					return 
					allObjects;
				}
			}
		}
		return allObjects;
	}
	public LinkedHashSet<Object> getFirstCoordIn(Shape ellipse) {

		allObjects.clear(); //Clears the allObject variable so that only one object can be in the Set<Object> at any time.

		for (Text temp : textList.values()) { 
			if (temp.getBounds().getWidth() == 0 || temp.getBounds().getHeight() == 0 ?
					ellipse.contains(temp.getBounds().getX(), temp.getBounds().getY()): 
						ellipse.intersects(temp.getBounds())){
				allObjects.add(temp);

				return allObjects;
			}
		}
		for (Boundary temp : boundaryList.values()){
			for(BPoint t: temp.getBPList().values()){
				if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? ellipse.contains(t.getBounds().getX(), t.getBounds().getY()): ellipse.intersects(t.getBounds())){
					allObjects.add(t);
				}
			}
		}
		for (Pattern temp : patternList.values()){
			for(Dummy t: temp.getDummyList().values()){
				if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? ellipse.contains(t.getBounds().getX(), t.getBounds().getY()): ellipse.intersects(t.getBounds())){
					allObjects.add(t);
				}
			}

			for(Hole t: temp.getHoleList().values()){
				if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? ellipse.contains(t.getBounds().getX(), t.getBounds().getY()): ellipse.intersects(t.getBounds())){
					allObjects.add(t);
				}
			}

		}

		return allObjects;
	}



	
	/**
	 * Returns a single coordinate
	 * @param shape
	 * @return coordinate
	 */
	public Coordinate getCoordInShape(Shape shape){
		Coordinate coordinate = null;
		for(Coordinate t: coordList.values()){
			
			if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? shape.contains(t.getBounds().getX(), t.getBounds().getY()): shape.intersects(t.getBounds())){
				coordinate = t;
			}
		}
		return coordinate;
	}
	
	/**
	 *@param accepts an ellipse of Type Shape
	 *@return the accumulation of next added objects in the Set<Object>  
	 *
	 **/	
	public LinkedHashSet<Object> addNextObjectIn(Shape ellipse) {


		for (Text temp : textList.values()) { //Searches the TreeMap dummyList 
			if (temp.getBounds().getWidth() == 0 || temp.getBounds().getHeight() == 0 ?
					ellipse.contains(temp.getBounds().getX(), temp.getBounds().getY()): 
						ellipse.intersects(temp.getBounds())){
				allObjects.add(temp);

				return allObjects;
			}
		}
		for (Boundary temp : boundaryList.values()){
			for(BPoint t: temp.getBPList().values()){
				if (t.getBounds().getWidth() == 0 || t.getBounds().getHeight() == 0 ? ellipse.contains(t.getBounds().getX(), t.getBounds().getY()): ellipse.intersects(t.getBounds())){
					allObjects.add(t);
				}
			}
		}

		for(Pattern p: patternList.values()){
			for (Dummy tempDummy : p.getDummyList().values()) {
				if (tempDummy.getBounds().getWidth() == 0 || tempDummy.getBounds().getHeight() == 0 ?
						ellipse.contains(tempDummy.getBounds().getX(), tempDummy.getBounds().getY()): 
							ellipse.intersects(tempDummy.getBounds())){
					allObjects.add(tempDummy);
					return
					allObjects;
				}
			}
			for (Hole tempHole: p.getHoleList().values()){
				if (tempHole.getBounds().getWidth() == 0 || tempHole.getBounds().getHeight() == 0 ?
						ellipse.contains(tempHole.getBounds().getX(), tempHole.getBounds().getY()): 
							ellipse.intersects(tempHole.getBounds())){
					allObjects.add(tempHole);
					return
					allObjects;
				}
			}
			for (SurfaceConnector tempSC : p.getSurfaceList().values()) {
				Point2D xy = new Point2D.Double(ellipse.getBounds2D().getCenterX(), ellipse.getBounds2D().getCenterY());
				Area scA = new Area(tempSC.getBounds());
				if (scA.equals(0) ?
						tempSC.getBounds().contains(xy): tempSC.getBounds().intersects(ellipse.getBounds2D())){
					allObjects.add(tempSC);
					return
					allObjects;
				}
			}
		}

		return allObjects;
	}



	/////////////////////////////////////////////
	//////////////  OTHER METHODS
	////////////////////////////////////////////



	private void fireWorldChange(String propertyName, Object oldValue, Object newValue) {

		PropertyChangeEvent worldChange = new PropertyChangeEvent(this ,propertyName, oldValue, newValue);
		for (Iterator<WorldChangeListener> i = listeners.iterator(); i.hasNext();) {i.next().propertyChange(worldChange);}
	}
	/**
	 * 
	 * @return All objects in the pattern that it is used on
	 */

	public LinkedHashSet<Object> getAllObjectsInWorld(){

		if (!(patternList.isEmpty())) {
			for (Pattern p : patternList.values()) {

				for (SurfaceConnector temp : p.getSurfaceList().values()){
					allInWorld.add(temp);
				}
				for (Hole temp : p.getHoleList().values()){
					allInWorld.add(temp);
				}
				for (Dummy temp : p.getDummyList().values()){
					allInWorld.add(temp);
				}

				for (InitiationPoint temp : p.getiPList().values()){
					allInWorld.add(temp);
				}
			}
		}
		if (!(boundaryList.isEmpty())) {
			for (Boundary b : boundaryList.values()) {
				for (BPoint temp : b.getBPList().values()){
					allInWorld.add(temp);
				}
			}
		}
		if (!(textList.isEmpty())) {
			for (Text temp : textList.values()) {
				allInWorld.add(temp);
			}
		}

		return allInWorld;

	}

	/**
	 * 
	 * @return All BPoints in the World
	 */

	public Set<Object> getAllBPointsInWorld(){

		if (!(boundaryList.isEmpty())) {
			for (Boundary b : boundaryList.values()) {
				for (BPoint temp : b.getBPList().values()){
					allInWorld.add(temp);
				}
			}
		}
		return allInWorld;

	}
	
	/**
	 * 
	 * @return All Text in the World
	 */

	public Set<Object> getAllTextInWorld(){
		
		if (!(textList.isEmpty())) {
			for (Text temp : textList.values()) {
				allInWorld.add(temp);
			}
		}
		return allInWorld;

	}
	
	public void removeAllInWorld(World w){
		allInWorld.removeAll(allInWorld);
		fireWorldChange("World", w, null);
	}



	public int getNumberOfObjectsInWorld() {
		return getAllObjectsInWorld().size();
	}

	public Rectangle2D getBounds(){
		Rectangle2D tempBounds = null;
		if (getAllObjectsInWorld().size() > 0) {
					
			for (Coordinate temp : getCoordList().values()) {
				if (tempBounds != null) {
					tempBounds = tempBounds.createUnion(temp.getBounds());
				}
				else{
		
					tempBounds = temp.getBounds();
				}
				
			}
		}	
		else {
				tempBounds = null;
		}
			
		return tempBounds;
	}


}
