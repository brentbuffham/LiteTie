package litetie.controller;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import litetie.model.LTPoint3D;
import litetie.CalculateUtility;
import litetie.LiteTieTRIAL;
import litetie.model.Coordinate;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.Text;

@SuppressWarnings("serial")
public class Transform extends AffineTransform{
	
	DecimalFormat dec4p = new DecimalFormat("#0.0000");//  @jve:decl-index=0:
	DecimalFormat dec2p = new DecimalFormat("#0.00");
	DecimalFormat dec1p = new DecimalFormat("#0.0");
	DecimalFormat dec0p = new DecimalFormat("#0");
	DecimalFormat dec2pM = new DecimalFormat("#0.00"+"m");
	static DecimalFormat dec1pM = new DecimalFormat("#0.0"+"m");
	static DecimalFormat dec1pD = new DecimalFormat("#0.0"+"\u00B0");

	/**
	 * @description Changes the bearing of an individual hole
	 * @author brentbuffham
	 * @param hole
	 * @param mouseLocation
	 * @param bearing
	 * @throws NegativeNumberException 
	 * @throws NumberFormatException 
	 * @return An Array of two doubles  comprising of the easting and northing differences
	 */
	public static double [] translateCoordinate(Coordinate c, double mouseEasting, double mouseNorthing, double centerBoundsEasting, double centerBoundsNorthing) throws NumberFormatException, NegativeNumberException{


		double eastingDifference = (mouseEasting - centerBoundsEasting);
		double northingDifference = (mouseNorthing - centerBoundsNorthing);


		try {
			c.setX(c.getX() + (mouseEasting - c.getX()));
		} catch (NumberFormatException e) {
			//			} catch (NegativeNumberException e) {
		}
		try {
			c.setY(c.getY() + (mouseNorthing - c.getY()));
		} catch (NumberFormatException e) {	
			//			} catch (NegativeNumberException e) {
		}

	
		return
				new double[] {eastingDifference, northingDifference};
	}
	
	/**
	 * @description Used to move any object that extends Coordinate.
	 * @param all
	 * @param mouseEasting
	 * @param mouseNorthing
	 * @throws NegativeNumberException 
	 * @throws NumberFormatException 
	 * @throws NumberFormatException
	 * @throws NegativeNumberException
	 */
	public static double[] translateCoordinateCollection(Collection<?> all, double mouseEasting, double mouseNorthing) throws NumberFormatException, NegativeNumberException {
		
		Rectangle2D bounds = new Rectangle2D.Double();
		LinkedHashSet allCoords = new LinkedHashSet();
		for(Object c: all){

			if (c instanceof Coordinate) {
				allCoords.add(c);
				if (bounds == null || bounds.isEmpty()) {
					bounds = ((Coordinate) c).getBounds();
				} else {
					bounds = bounds.createUnion(((Coordinate) c).getBounds());
				}
			}
		}

		double eastingDifference = 0;
		double northingDifference = 0;
			
			for(Object c: allCoords){
				
				if(c instanceof Coordinate){
					
					double originX = ((Coordinate) c).getX();
					double originY = ((Coordinate) c).getY();					
					
					Coordinate o = (Coordinate)c;
					eastingDifference = (mouseEasting - bounds.getCenterX());
					northingDifference = (mouseNorthing - bounds.getCenterY());
					o.setX(originX+eastingDifference);
					o.setY(originY+northingDifference);
					
				}
	
			}
			return
					new double[] {eastingDifference, northingDifference};
			
	}

/**
 * 
 * @param all
 */
	public static void getCoordinateCollectionBounds(Collection<?> all){
		Rectangle2D bounds = new Rectangle2D.Double();

		for(Object c: all){

			if(bounds == null || bounds.isEmpty()){
				bounds = ((Coordinate) c).getBounds();
			}
			else {
				bounds = bounds.createUnion(((Coordinate) c).getBounds());
			}
		}
	}
		
	/**
	 * @description Changes the bearing of an individual hole
	 * @author brentbuffham
	 * @param c The coordinate either a hole or a text
	 * @param mouseLocation
	 * @param bearing
	 * @return bearing
	 */
	public static double rotateBearing(Collection<?> c, double mouseEasting, double mouseNorthing){
		double eastingDifference = (mouseEasting - ((Coordinate) c).getX());
		double northingDifference = (mouseNorthing - ((Coordinate) c).getY());
		double newBearing = 0;
		double distance =  Math.sqrt(Math.pow(eastingDifference, 2) + Math.pow(northingDifference, 2));//hyp length

		if(northingDifference < 0 && eastingDifference >= 0){//lower right GOOD
			newBearing = Math.toDegrees(Math.acos((northingDifference/distance)));
		}
		else if(northingDifference< 0 && eastingDifference <=0){//lower left GOOD
			newBearing = Math.toDegrees((Math.asin(-(eastingDifference/distance)))+ Math.toRadians(180));
		}
		else if (eastingDifference <0 && northingDifference >= 0){//upper left GOOD
			newBearing = Math.toDegrees((Math.acos(-(northingDifference/distance)))+ Math.toRadians(180));
		}
		else if(northingDifference >= 0 && eastingDifference >= 0){//upper right GOOD
			newBearing = Math.toDegrees(Math.asin(eastingDifference/distance));
		} 
		if (c instanceof Hole) {
			((Hole) c).setBearing(newBearing);
		}
		else if (c instanceof Text) {
			((Text) c).setBearing(newBearing);
		}
		return 
				newBearing;
				
	}
	public static double rotateBearingByFive(Collection<?> c, double mouseEasting, double mouseNorthing){
		double eastingDifference = (mouseEasting - ((Coordinate) c).getX());
		double northingDifference = (mouseNorthing - ((Coordinate) c).getY());
		double newBearing = 0;
		double distance =  Math.sqrt(Math.pow(eastingDifference, 2) + Math.pow(northingDifference, 2));//hyp length

		if(northingDifference < 0 && eastingDifference >= 0){//lower right GOOD
			newBearing = Math.round(Math.toDegrees(Math.acos((northingDifference/distance)))/5)*5;
		}
		else if(northingDifference< 0 && eastingDifference <=0){//lower left GOOD
			newBearing = Math.round(Math.toDegrees((Math.asin(-(eastingDifference/distance)))+ Math.toRadians(180))/5)*5;
		}
		else if (eastingDifference <0 && northingDifference >= 0){//upper left GOOD
			newBearing = Math.round(Math.toDegrees((Math.acos(-(northingDifference/distance)))+ Math.toRadians(180))/5)*5;
		}
		else if(northingDifference >= 0 && eastingDifference >= 0){//upper right GOOD
			newBearing = Math.round(Math.toDegrees(Math.asin(eastingDifference/distance))/5)*5;
		}
 
		if (c instanceof Hole) {
			((Hole) c).setBearing(newBearing);
		}
		else if (c instanceof Text) {
			((Text) c).setBearing(newBearing);
		}
		
		
		return 
				newBearing;
				
	}


	/**
	 * @description Rotates the bearings of all the holes in a collection of holes to the same bearing.
	 * @author brentbuffham
	 * @param all
	 * @param mouseEasting
	 * @param mouseNorthing
	 * @param bearing
	 */
	public static double rotateBearingsOfCollection(Collection<?> all, double mouseEasting, double mouseNorthing){

		Rectangle2D bounds = new Rectangle2D.Double();

		for(Object o: all){

			if(bounds == null || bounds.isEmpty()){
				if( o instanceof Coordinate) {
					bounds = ((Coordinate) o).getBounds();
				}
			}
			else {
				if( o instanceof Coordinate) {
					bounds = bounds.createUnion(((Coordinate) o).getBounds());
				}
			}
		}
		double eastingDifference = (mouseEasting - bounds.getCenterX());
		double northingDifference = (mouseNorthing - bounds.getCenterY());
		double newBearing = 0;
		double distance =  Math.sqrt(Math.pow(eastingDifference, 2) + Math.pow(northingDifference, 2));//hyp length

		if(northingDifference < 0 && eastingDifference >= 0){//lower right GOOD
			newBearing = Math.toDegrees(Math.acos((northingDifference/distance)));
		}
		else if(northingDifference< 0 && eastingDifference <=0){//lower left GOOD
			newBearing = Math.toDegrees((Math.asin(-(eastingDifference/distance)))+ Math.toRadians(180));
		}
		else if (eastingDifference <0 && northingDifference >= 0){//upper left GOOD
			newBearing = Math.toDegrees((Math.acos(-(northingDifference/distance)))+ Math.toRadians(180));
		}
		else if(northingDifference >= 0 && eastingDifference >= 0){//upper right GOOD
			newBearing = Math.toDegrees(Math.asin(eastingDifference/distance));
		}
		

		for(Object o: all){
			if( o instanceof Hole) {
			((Hole) o).setBearing(newBearing);
			}
			if( o instanceof Text) {
				((Text) o).setBearing(newBearing);
			}
			
//			LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(newBearing));
		}

		return newBearing;

	}
	public static double rotateBearingsOfCollectionByFive(Collection<?> all, double mouseEasting, double mouseNorthing){
		Rectangle2D bounds = new Rectangle2D.Double();
		for(Object o: all){
			if(bounds == null || bounds.isEmpty()){
				if( o instanceof Coordinate) {bounds = ((Coordinate) o).getBounds();}
				}
			else {if( o instanceof Coordinate) {bounds = bounds.createUnion(((Coordinate) o).getBounds());}
			}
		}
		double eastingDifference = (mouseEasting - bounds.getCenterX());
		double northingDifference = (mouseNorthing - bounds.getCenterY());
		double newBearing = 0;
		double distance =  Math.sqrt(Math.pow(eastingDifference, 2) + Math.pow(northingDifference, 2));//hyp length

		if(northingDifference < 0 && eastingDifference >= 0){//lower right GOOD
			newBearing = Math.round(Math.toDegrees(Math.acos((northingDifference/distance)))/5)*5;
		}
		else if(northingDifference< 0 && eastingDifference <=0){//lower left GOOD
			newBearing = Math.round(Math.toDegrees((Math.asin(-(eastingDifference/distance)))+ Math.toRadians(180))/5)*5;
		}
		else if (eastingDifference <0 && northingDifference >= 0){//upper left GOOD
			newBearing = Math.round(Math.toDegrees((Math.acos(-(northingDifference/distance)))+ Math.toRadians(180))/5)*5;
		}
		else if(northingDifference >= 0 && eastingDifference >= 0){//upper right GOOD
			newBearing = Math.round(Math.toDegrees(Math.asin(eastingDifference/distance))/5)*5;
		}
		for(Object o: all){
			if( o instanceof Hole) {
				((Hole) o).setBearing(newBearing);
			}
			if( o instanceof Text) {
				((Text) o).setBearing(newBearing);
			}
		}
		return newBearing;
	}


	public static double orientationChange(double mouseEasting, double mouseNorthing, double anchorX, double anchorY){
		//SEPARATION OF THE MOUSE LOCATION TO THE ANCHOR LOCATION
		double xl = (mouseEasting - anchorX);
		double yl = (mouseNorthing - anchorY);
		//LENGTH OF THE HYPOTENUSE
		double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
		//BEARING OF THE ORIENTATION 
		double b =0;
		
		//FOR ORIENTATION ZERO DEGREES = HORIZONTAL LEFT TO RIGHT
		//LOWER RIGHT QUADRANT (CCW 270 - 360)
		if(yl < 0 && xl >= 0){
			b = Math.asin(yl/d) + Math.toRadians(360);
//			System.out.println("FIRST");
		}
		//LOWER LEFT QUADRANT (CCW 180 - 270) 
		else if(yl< 0 && xl <=0){
			b =(Math.acos(-(xl/d)) + Math.toRadians(180)) ;
//			System.out.println("SECOND");
		}
		//UPPER LEFT QUADRANT (CCW 90 - 180)
		else if (xl <0 && yl >= 0){
			b = Math.asin(-(yl/d)) + Math.toRadians(180);
//			System.out.println("THIRD");
		}
		//UPPER RIGHT QUADRANT (CCW 0 - 90)
		else if(yl >= 0 && xl >= 0){
			b = Math.acos(xl/d);
//			System.out.println("FOURTH");
		}
		return
		 b;
	}
//*********************
	public static double orientationChangeByFive(double mouseX, double mouseY, double anchorX, double anchorY){
		//SEPARATION OF THE MOUSE LOCATION TO THE ANCHOR LOCATION
		double xl = (mouseX - anchorX);
		double yl = (mouseY - anchorY);
		//LENGTH OF THE HYPOTENUSE
		double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
		//BEARING OF THE ORIENTATION 
		double b =0;
		
		//FOR ORIENTATION ZERO DEGREES = HORIZONTAL LEFT TO RIGHT
		//LOWER RIGHT QUADRANT (CCW 270 - 360)
		if(yl < 0 && xl >= 0){
			b = Math.toRadians(Math.round(Math.toDegrees(Math.asin(yl/d) + Math.toRadians(360))/5)*5);
//			System.out.println(b);
		}
		//LOWER LEFT QUADRANT (CCW 180 - 270) 
		else if(yl< 0 && xl <=0){
			b = Math.toRadians(Math.round(Math.toDegrees(Math.acos(-(xl/d)) + Math.toRadians(180))/5)*5);
//			System.out.println(b);
		}
		//UPPER LEFT QUADRANT (CCW 90 - 180)
		else if (xl <0 && yl >= 0){
			b = Math.toRadians(Math.round(Math.toDegrees(Math.asin(-(yl/d)) + Math.toRadians(180))/5)*5);
//			System.out.println(b);
		}
		//UPPER RIGHT QUADRANT (CCW 0 - 90)
		else if(yl >= 0 && xl >= 0){
			b = Math.toRadians(Math.round(Math.toDegrees(Math.acos(xl/d))/5)*5);
//			System.out.println(b);
		}
		return
		 b;
		
		
	}
public static void rotateCoordinateCollection(Collection<?> all,Collection<Coordinate> copy, double mouseX, double mouseY) throws NumberFormatException, NegativeNumberException {
		CalculateUtility calcs = new CalculateUtility();
		Rectangle2D bounds = new Rectangle2D.Double();
		
		for(Object o: copy){
			if(bounds == null || bounds.isEmpty()){
				bounds = ((Coordinate) o).getBounds();
				}
			else {
				bounds = bounds.createUnion(((Coordinate) o).getBounds());
				}
		}

		double anchorX = bounds.getCenterX();
		double anchorY = bounds.getCenterY();
		double xRotated = 0;
		double yRotated = 0;
		
		LTPoint3D p1 = new LTPoint3D.Double(anchorX,anchorY,0);
		LTPoint3D p2 = new LTPoint3D.Double(mouseX,mouseY,0);

		//Using this method is better as it returns the world coord bearings that is top of page is North or 0 degrees.
		double orientation1 = orientationChange(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		double orientation = Math.toRadians(calcs.calculateBearing(p1, p2));//reverse to get the pattern orientation
		
		Iterator<?> it1 = all.iterator();
	    Iterator<Coordinate> it2 = copy.iterator();
	    while (it1.hasNext()) {
	        Object o1 = it1.next();
	        Coordinate o2 = it2.next();
	        
	        double originX = o2.getX();
			double originY = o2.getY();
			
			if(o1 instanceof Coordinate){

				double transX = originX - anchorX;
				double transY = originY - anchorY;
				
				Coordinate c = (Coordinate)o1;
				
				xRotated = (transX*Math.cos(orientation1) - transY*Math.sin(orientation1)) + anchorX;
				yRotated = (transX*Math.sin(orientation1) + transY*Math.cos(orientation1)) + anchorY;

				c.setX(xRotated);
				c.setY(yRotated);
				LiteTieTRIAL.setConsoleOutput("\nDegrees:"+Math.toDegrees(orientation));
			}
	    }			
	}

public static void rotateCoordinateCollectionByFive(Collection<?> all,Collection<Coordinate> copy, double mouseX, double mouseY) throws NumberFormatException, NegativeNumberException {
	CalculateUtility calcs = new CalculateUtility();
	Rectangle2D bounds = new Rectangle2D.Double();
	
	for(Object o: copy){
		if(bounds == null || bounds.isEmpty()){
			bounds = ((Coordinate) o).getBounds();
			}
		else {
			bounds = bounds.createUnion(((Coordinate) o).getBounds());
			}
	}

	double anchorX = bounds.getCenterX();
	double anchorY = bounds.getCenterY();
	double xRotated = 0;
	double yRotated = 0;
	
	LTPoint3D p1 = new LTPoint3D.Double(anchorX,anchorY,0);
	LTPoint3D p2 = new LTPoint3D.Double(mouseX,mouseY,0);

	//Using this method is better as it returns the world coord bearings that is top of page is North or 0 degrees.
	double orientation1 = orientationChangeByFive(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	double orientation = calcs.calculateBearing(p1, p2);//reverse to get the pattern orientation
	
	Iterator<?> it1 = all.iterator();
    Iterator<Coordinate> it2 = copy.iterator();
    while (it1.hasNext()) {
        Object o1 = it1.next();
        Coordinate o2 = it2.next();
        
        double originX = o2.getX();
		double originY = o2.getY();
		
		if(o1 instanceof Coordinate){

			double transX = originX - anchorX;
			double transY = originY - anchorY;
			
			Coordinate c = (Coordinate)o1;
			
			xRotated = (transX*Math.cos(orientation1) - transY*Math.sin(orientation1)) + anchorX;
			yRotated = (transX*Math.sin(orientation1) + transY*Math.cos(orientation1)) + anchorY;

			c.setX(xRotated);
			c.setY(yRotated);
			LiteTieTRIAL.setConsoleOutput("\nDegrees:"+Math.round(orientation/5)*5 );
		}
    }

}


public static void rotateCoordinateCollectionIncrementally(Collection<?> all, Collection<Coordinate> copy, double mouseX, double mouseY, double lastX, double lastY, double increment) throws NumberFormatException, NegativeNumberException {
	CalculateUtility calcs = new CalculateUtility();
	Rectangle2D bounds = new Rectangle2D.Double();
	
	for(Object o: copy){
		if(bounds == null || bounds.isEmpty()){
			bounds = ((Coordinate) o).getBounds();
			}
		else {
			bounds = bounds.createUnion(((Coordinate) o).getBounds());
			}
	}

	double anchorX = bounds.getCenterX();
	double anchorY = bounds.getCenterY();
	double xRotated = 0;
	double yRotated = 0;
	
	LTPoint3D p1 = new LTPoint3D.Double(anchorX,anchorY,0);
	LTPoint3D p2 = new LTPoint3D.Double(lastX,lastY,0);
	LTPoint3D p3 = new LTPoint3D.Double(mouseX,mouseY,0);

	//Using this method is better as it returns the world coord bearings that is top of page is North or 0 degrees.
	//this takes the angle of the last point and compares it with the current point
	//then applies only the difference rather than getting the absolute angle in the method above.
	double orientation = -Math.toRadians(calcs.calculateBearing(p1, p3) - calcs.calculateBearing(p1,  p2));
	
		for(Object o: all){
			
			if(o instanceof Coordinate){

				double originX = ((Coordinate) o).getX();
				double originY = ((Coordinate) o).getY();
				
				double transX = originX - anchorX;
				double transY = originY - anchorY;
				
				Coordinate c = (Coordinate)o;
				
				xRotated = (transX*Math.cos(orientation) - transY*Math.sin(orientation)) + anchorX;
				yRotated = (transX*Math.sin(orientation) + transY*Math.cos(orientation)) + anchorY;

				c.setX(xRotated);
				c.setY(yRotated);
				System.out.println(/*"Radians:"+(orientation)+*/" Degrees:"+Math.toDegrees(orientation));
			}
			
		}			
}
	public static Point2D rotatePoint(double oldX, double oldY, double mouseEasting, double mouseNorthing, double anchorX, double anchorY) throws NumberFormatException, NegativeNumberException {
	
		
			double transEasting;
			double transNorthing;
		
			double rotatedEasting;
			double rotatedNorthing;
		
			double newX;
			double newY;
			
			
			transEasting = oldX - anchorX;
			transNorthing = oldY - anchorY;
			
			
			rotatedEasting = transEasting*Math.cos(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY)) - transNorthing*Math.sin(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY));
			rotatedNorthing = transEasting*Math.sin(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY)) + transNorthing*Math.cos(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY));

			newX = rotatedEasting + anchorX;
			newY = rotatedNorthing + anchorY;
			
			return
				new Point2D.Double(newX, newY);
	}
	public static Point2D rotatePointByFive(double oldX, double oldY, double mouseEasting, double mouseNorthing, double anchorX, double anchorY) throws NumberFormatException, NegativeNumberException {
 
		double transEasting;
		double transNorthing;
	
		double rotatedEasting;
		double rotatedNorthing;
	
		double newX;
		double newY;

		transEasting = oldX - anchorX;
		transNorthing = oldY - anchorY;

		double oChange = orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY); // bearing in radians	
		double oDegrees = Math.toDegrees(oChange);
		double bearingInFive = Math.round(oDegrees/5)*5.0;
		oChange = Math.toRadians(bearingInFive);
		
		rotatedEasting = transEasting*Math.cos(oChange) - transNorthing*Math.sin(oChange);
		rotatedNorthing = transEasting*Math.sin(oChange) + transNorthing*Math.cos(oChange);

		newX = rotatedEasting + anchorX;
		newY = rotatedNorthing + anchorY;
		
		Point2D newPoint = new Point2D.Double(newX,newY);
	

		return
				newPoint;
				
}


}
