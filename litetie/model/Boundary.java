package litetie.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import litetie.listeners.BoundaryChangeListener;

public class Boundary extends Polygon2D implements Shape{
	
	DecimalFormat dec0P = new DecimalFormat("#0");
	
//	protected int bID;
//	protected int pID;
//	protected BPoint bp;
//	protected boolean isClosed;
//	protected int arrowType;
//	protected boolean isAnnotated;
	
	
	private HashSet<BoundaryChangeListener> listeners;
	Set<Object> allObjects = new HashSet<Object>();	
	
	private TreeMap<Integer, BPoint>bpList;
	private double length;
	private double width;
	private boolean isClosed;
	private int arrowType;
	private boolean isAnnotated;
	private Color color;
	private Color fillColor;
	private BasicStroke stroke;
	private int bID;
	private int pID;
	private BPoint bp;
	
	public Boundary(double width, double length, boolean isClosedPolygon, int arrowType, boolean isAnnotated, Color color, Color fillColor, BasicStroke stroke) {
		setWidth(width);
		setLength(length);
		setClosed(isClosedPolygon);
		setArrow(arrowType);
		setColor(color);
		setFillColor(fillColor);
		setStroke(stroke);
//		SortedMap bpList = Collections.synchronizedSortedMap(new TreeMap<Integer, BPoint>());
		bpList = new TreeMap<Integer, BPoint>();
		listeners = new HashSet<BoundaryChangeListener>();

	}
	public Boundary(int boundaryID, boolean isClosedPolygon, int arrowType, boolean isAnnotated, Color color, Color fillColor, BasicStroke stroke) throws ZeroArgumentException, NegativeNumberException {
		
		setBoundaryID(boundaryID);
		setClosed(isClosedPolygon);
		setArrow(arrowType);
		setColor(color);
		setFillColor(fillColor);
		setStroke(stroke);
//		SortedMap bpList = Collections.synchronizedSortedMap(new TreeMap<Integer, BPoint>());
		bpList = new TreeMap<Integer, BPoint>();
		listeners = new HashSet<BoundaryChangeListener>();

	}
	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
		
	}
	public BasicStroke getStroke(){
		return stroke;
	}
	public void setColor(Color color) {
		this.color = color;
		
	}
	public Color getColor(){
		return color;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		
	}
	public Color getFillColor(){
		return fillColor;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
		
	}
	public boolean getClosed(){
		return isClosed;
	}

	public void setAnnotated(boolean isAnnotated) {
		this.isAnnotated = isAnnotated;
		
	}
	public boolean getAnnotated(){
		return isAnnotated;
	}
	
	public void setArrow(int arrowType) {
		this.arrowType = arrowType;
		
	}
	public int getArrow(){
		return arrowType;
	}
	
	public Boundary(int bID, TreeMap<Integer, BPoint> bpList) throws ZeroArgumentException, NegativeNumberException{
		setBoundaryID(bID);
		
		bpList = new TreeMap<Integer, BPoint>();
		listeners = new HashSet<BoundaryChangeListener>();
	}
	
	public Boundary() throws ZeroArgumentException, NegativeNumberException{	
		setBoundaryID(bID);
		bpList = new TreeMap<Integer, BPoint>();
		listeners = new HashSet<BoundaryChangeListener>();
	}
	

	public String toString() {
		
		String boundryInfo = "Boundary ID = "+ getBoundaryID() 
				+"\nNumber of Points = "+getNumberOfPointsInBoundary()
				+"\nClosed = "+getClosed()
				+"\nArrow Type = "+ getArrow()
				+"\nAnnotated = "+getAnnotated();
		
		return 
			boundryInfo;
	}
	public void addBPoint(BPoint bp) {
		if (!bpList.containsValue(bp)){
			if (bp instanceof BPoint) {
				bpList.put(bp.getPointID(), (BPoint) bp);
				fireBoundaryChange("Hole", null, bp);
			}
		}
	}
	
	public void insertBPoint(BPoint newPoint, BPoint nextPoint) {
		if (!bpList.containsValue(newPoint)) {
			if(newPoint instanceof BPoint) {
				// ADD CODE HERE WHEN TREEMAP ISSUE
			}
		}
	}
	
	
	public BPoint getBPoint(int id) {
		return bpList.get(id);
	}

	
	
	public void addBPoint(BPoint bp, boolean useID) throws ZeroArgumentException, NegativeNumberException {
		
		int k = bpList.size()+1;
		
		if (bp instanceof BPoint) {
			if (!useID) {
				
				while(bpList.containsKey(k)) {
					k++;
				}
				
				bp.setPointID(bpList.size()+1);
				}
			if (bp != null){
			bpList.put(bp.getPointID(), (BPoint) bp);
			}
			fireBoundaryChange("Boundary Point", null, bp);
		}
		
	}
	public void addListener(BoundaryChangeListener listen) {
		listeners.add(listen);
	}
	 public Set<BPoint> getAllBPointsIn(Shape polygon) {
			Set<BPoint> allBPoints = new HashSet<BPoint>();
			for (BPoint tempBPoints : bpList.values()) {
				
				if (polygon.intersects(tempBPoints.getBounds())){
					allBPoints.add(tempBPoints);
				}
			}
			return allBPoints;
		}
	 public BPoint getBPointIn(Shape ellipse){
	        
	    	for (BPoint tempBPoint: bpList.values()){
	    		if(tempBPoint instanceof BPoint){
	    			if (tempBPoint.getBounds().getWidth() == 0 || tempBPoint.getBounds().getHeight() == 0 ?
	    					ellipse.contains(tempBPoint.getBounds().getX(), tempBPoint.getBounds().getY()): 
	    						ellipse.intersects(tempBPoint.getBounds())){
	    				return
	    				tempBPoint;
	    			}
	    		}
	    	}
	    	return null;
	    
	    }
	
	public Set<BPoint> getAllBPointsInBoundary(){
		Set<BPoint> allBPoints = new HashSet<BPoint>();
		
		for (BPoint tempBPoint : bpList.values()){
			
			if (tempBPoint instanceof BPoint ) {
				allBPoints.add(tempBPoint);
			}
		}
		return allBPoints;
	}
	
	public double[] getAllXValuesInBoundary(){
		double[] all = new double[bpList.values().size()];
		int i = 0;
		for (BPoint tempBPoint : bpList.values()){
			
			if (tempBPoint instanceof BPoint ) {
				all[i] = tempBPoint.getX();
				i++;
			}
		}
		return all;
	}
	public double[] getAllYValuesInBoundary(){
		double[] all = new double[bpList.values().size()];
		int i = 0;
		for (BPoint tempBPoint : bpList.values()){
			
			if (tempBPoint instanceof BPoint ) {
				all[i] = tempBPoint.getY();
				i++;
			}
		}
		return all;
	}
	
	
	public int getPointID(){
		return pID;
	}
	
	public int getBoundaryID() {
		return bID;
	}
	public BPoint getBPoint() {
		return 	bp;
	}
	public int getNumberOfPointsInBoundary() {
		
		return bpList.size();
	}
	
	private void setLength(double length) {
		if (length > 0 || length < 10000)
			this.length = length;
		else
			throw new IllegalArgumentException ("length outside program range");
	}
	
	private void setWidth(double width) {
		if (width > 0 || width < 10000)
			this.width = width;
		else
			throw new IllegalArgumentException ("width outside program range");
	}
	
	
	public void setBoundaryID(int bID) throws ZeroArgumentException, NegativeNumberException{
		if (bID == 0)
			throw new ZeroArgumentException("A boundary ID number can not be equal to 0. - Boundary Class");
		else if (bID < 0)
			throw new NegativeNumberException("A boundary ID number can not be a negative integer. - Boundary Class");
		else
			this.bID = bID;
		}

	public void removeBPoint(BPoint bp) {
		if (bp instanceof BPoint ) {
			bpList.remove(bp.getPointID());
			fireBoundaryChange("Boundary", bp, null);
		}
	}
	
	public void removeAllBPoints(BPoint bp) {
		for (int k = bpList.size(); bpList.size() > 0; k--){
			if (bpList.containsValue(bp)) {
				bpList.remove(bp.getPointID());
				
				fireBoundaryChange("Boundary", bp, null);
			}
		}
	}
	public void removeListener(BoundaryChangeListener listen) {
		listeners.remove(listen);
	}
	
	
	 /**
	    *@param accepts an ellipse of Type Shape
	    *@return the first object in the Set<Object>  
	    *
	    **/
		public Set<Object> getFirstObjectIn(Shape ellipse) {
			
			allObjects.clear(); //Clears the allObject variable so that only one object can be in the Set<Object> at any time.
			
			for (BPoint temPoint : bpList.values()) { //Searches the TreeMap dummyList 
				if (temPoint.getBounds().getWidth() == 0 || temPoint.getBounds().getHeight() == 0 ?
						ellipse.contains(temPoint.getBounds().getX(), temPoint.getBounds().getY()): 
							ellipse.intersects(temPoint.getBounds())){
					allObjects.add(temPoint);
					
					return allObjects;
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
		
			for (BPoint tempPoint : bpList.values()) {
				if (tempPoint.getBounds().getWidth() == 0 || tempPoint.getBounds().getHeight() == 0 ?
						ellipse.contains(tempPoint.getBounds().getX(), tempPoint.getBounds().getY()): 
							ellipse.intersects(tempPoint.getBounds())){
					allObjects.add(tempPoint);
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
			for (BPoint tempPoint : bpList.values()) {
				if (allObjects.contains(tempPoint) && tempPoint.getBounds().getWidth() == 0 || tempPoint.getBounds().getHeight() == 0 ?
						ellipse.contains(tempPoint.getBounds().getX(), tempPoint.getBounds().getY()): 
							ellipse.intersects(tempPoint.getBounds())){
					allObjects.remove(tempPoint);
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
		public Set<Object> getAllObjectsInBoundary(){
			
			for (BPoint tempPoint : bpList.values()){
				allObjects.add(tempPoint);
			}
			
			return allObjects;
		}
	
	
	
	
	
	
	
	
	
	
	private void fireBoundaryChange(String propertyName, Object oldValue, Object newValue) {
		
		PropertyChangeEvent boundaryChange = new PropertyChangeEvent(this ,propertyName, oldValue, newValue);
		
		for (Iterator<BoundaryChangeListener> i = listeners.iterator(); i.hasNext();) {
			i.next().propertyChange(boundaryChange);
		}
	}

	public Polygon2D getPolyBounds(){
		
		double [] xs = new double[bpList.values().size()];
		double [] ys = new double[bpList.values().size()];
		int n = 0;
		
		for(BPoint temp: bpList.values()){
			
			xs[n] = temp.getX();
			ys[n] = temp.getY();
			n++;
		}
		
		return new Polygon2D(xs,ys,n);
	}
	/**
	 * @return the bpList
	 */
	public TreeMap<Integer, BPoint> getBPList() {
		return bpList;
	}
	/**
	 * @param bpList the bpList to set
	 */
	public void setBPList(TreeMap<Integer, BPoint> bpList) {
		this.bpList = bpList;
	}
	/**
	 * @return the isClosed
	 */
	public boolean isClosed() {
		return isClosed;
	}

}
