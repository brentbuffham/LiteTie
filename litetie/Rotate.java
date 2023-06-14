package litetie;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.Text;

public class Rotate {
	
	DecimalFormat dec4p = new DecimalFormat("#0.0000");//  @jve:decl-index=0:
	DecimalFormat dec2p = new DecimalFormat("#0.00");
	DecimalFormat dec1p = new DecimalFormat("#0.0");
	DecimalFormat dec0p = new DecimalFormat("#0");
	DecimalFormat dec2pM = new DecimalFormat("#0.00"+"m");
	DecimalFormat dec1pM = new DecimalFormat("#0.0"+"m");
	static DecimalFormat dec1pD = new DecimalFormat("#0.0"+"\u00B0");

	/**
	 * @description Changes the bearing of an individual hole
	 * @author brentbuffham
	 * @param hole
	 * @param mouseLocation
	 * @param bearing
	 * @return bearing
	 */
	public static void rotateHoleBearing(Hole hole, double mouseEasting, double mouseNorthing, double bearing){
		double eastingDifference = (mouseEasting - hole.getX());
		double northingDifference = (mouseNorthing - hole.getY());
		double newBearing = bearing;
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
		else
			newBearing= bearing; 
		
		hole.setBearing(newBearing);
		LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(newBearing));
	}
	

	
	/**
	 * @description Changes the bearing of an individual Text
	 * @author brentbuffham
	 * @param Text
	 * @param mouseLocation
	 * @param orientation
	 */
	public static void rotateTextOrientation(Text t, double mouseEasting, double mouseNorthing, double bearing){
		double eastingDifference = (mouseEasting - t.getX());
		double northingDifference = (mouseNorthing - t.getY());
		double newBearing = bearing;
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
		else
			newBearing= bearing; 
		
		t.setBearing(newBearing);
		LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(newBearing));
	}
	/**
	 * @description Rotates the bearings of all the holes in a collection of holes to the same bearing.
	 * @author brentbuffham
	 * @param allHoles
	 * @param mouseEasting
	 * @param mouseNorthing
	 * @param bearing
	 */
	public static void rotateBearingsOfTextCollection(Collection<?> allText, double mouseEasting, double mouseNorthing, double bearing){

		Rectangle2D bounds = new Rectangle2D.Double();

		for(Object text: allText){

			if(bounds == null || bounds.isEmpty()){
				bounds = ((Text) text).getBounds();
			}
			else {
				bounds = bounds.createUnion(((Text) text).getBounds());
			}
		}
		double eastingDifference = (mouseEasting - bounds.getCenterX());
		double northingDifference = (mouseNorthing - bounds.getCenterY());
		double newBearing = bearing;
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
		else
			newBearing= bearing;

		for(Object text: allText){
			((Text) text).setBearing(newBearing);
			LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(newBearing));
		}



	}
	/**
	 * @description Rotates the bearings of all the holes in a collection of holes to the same bearing.
	 * @author brentbuffham
	 * @param allHoles
	 * @param mouseEasting
	 * @param mouseNorthing
	 * @param bearing
	 */
	public static void rotateBearingsOfCollection(Collection<?> allHoles, double mouseEasting, double mouseNorthing, double bearing){

		Rectangle2D bounds = new Rectangle2D.Double();

		for(Object hole: allHoles){

			if(bounds == null || bounds.isEmpty()){
				bounds = ((Hole) hole).getBounds();
			}
			else {
				bounds = bounds.createUnion(((Hole) hole).getBounds());
			}
		}
		double eastingDifference = (mouseEasting - bounds.getCenterX());
		double northingDifference = (mouseNorthing - bounds.getCenterY());
		double newBearing = bearing;
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
		else
			newBearing= bearing;

		for(Object hole: allHoles){
			((Hole) hole).setBearing(newBearing);
			LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(newBearing));
		}



	}
	public static void rotateBearingsOfCollectionForOrientation(Collection<?> allHoles, double mouseEasting, double mouseNorthing, double bearing){

		Rectangle2D bounds = new Rectangle2D.Double();

		for(Object hole: allHoles){

			if(bounds == null || bounds.isEmpty()){
				bounds = ((Hole) hole).getBounds();
			}
			else {
				bounds = bounds.createUnion(((Hole) hole).getBounds());
			}
		}
		double eastingDifference = (mouseEasting - bounds.getCenterX());
		double northingDifference = (mouseNorthing - bounds.getCenterY());
		double newBearing = bearing;
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
		else
			newBearing= bearing;

		for(Object hole: allHoles){
			((Hole) hole).setBearing(newBearing);
			LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(newBearing));
		}



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
		}
		//LOWER LEFT QUADRANT (CCW 180 - 270) 
		else if(yl< 0 && xl <=0){
			b =(Math.acos(-(xl/d)) + Math.toRadians(180)) ;
		}
		//UPPER LEFT QUADRANT (CCW 90 - 180)
		else if (xl <0 && yl >= 0){
			b = Math.asin(-(yl/d)) + Math.toRadians(180);
		}
		//UPPER RIGHT QUADRANT (CCW 0 - 90)
		else if(yl >= 0 && xl >= 0){
			b = Math.acos(xl/d);
		}
		
//		LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(Math.toDegrees(b)));
		
		return
		 b;
	}
	public static double bearingChange(double oldBearing, double mouseEasting, double mouseNorthing, double oldX, double oldY, double anchorX, double anchorY, double minX, double minY){
		//SEPARATION OF THE MOUSE LOCATION TO THE ANCHOR LOCATION
		double xl = (mouseEasting - anchorX);
		double yl = (mouseNorthing - anchorY);
		//LENGTH OF THE HYPOTENUSE
		double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
		//BEARING OF THE ORIENTATION 
		double b = 0;
		double oldB = Math.toRadians(oldBearing);
		
		double oldXl = (oldX-minX);
		double oldYl = (oldY-minY);
		double oldD = Math.sqrt(Math.pow(oldXl, 2)+ Math.pow(oldYl, 2));
		
		//FOR ORIENTATION ZERO DEGREES
				//LOWER RIGHT QUADRANT (CCW 270 - 360) - OK
				if(yl < 0 && xl <= 0){
					System.out.println("OK 180 to 270");
					b = Math.asin(yl/d) + Math.toRadians(360);
				}
				//UPPER LEFT QUADRANT (CCW 90 - 180) - OK
				else if (xl > 0 && yl <= 0){
					
					b =  Math.asin(-(yl/d)) + Math.toRadians(180);
				}
				//LOWER LEFT QUADRANT (CW 270 - 360) 
				else if(yl > 0 && xl <=0){
					b =   Math.acos(-(yl/d))+ Math.toRadians(-90);
				}
				
				//UPPER RIGHT QUADRANT (CCW 0 - 90)
				else if(yl >= 0 && xl >= 0){
					b =   Math.acos(-(xl/d));
				}
				//****************
				//LOWER RIGHT QUADRANT (CCW 270 - 360) - OK
//				if(oldYl < 0 && oldXl <= 0){
//					System.out.println("OK 180 to 270");
//					oldB = Math.asin(oldYl/oldD) + Math.toRadians(0);
//				}
//				//UPPER LEFT QUADRANT (CCW 90 - 180) - OK
//				else if (oldXl > 0 && oldYl <= 0){
//					
//					oldB =  Math.asin(-(oldYl/oldD)) + Math.toRadians(180);
//				}
//				//LOWER LEFT QUADRANT (CW 270 - 360) 
//				else if(oldYl > 0 && oldXl <=0){
//					oldB =   Math.acos(-(oldYl/oldD))+ Math.toRadians(180);
//				}
//				
//				//UPPER RIGHT QUADRANT (CCW 0 - 90)
//				else if(oldYl >= 0 && oldXl >= 0){
//					oldB =   Math.acos(-(oldXl/oldD));
//				}
		
		LiteTieTRIAL.bearingStatusLabel.setText(dec1pD.format(Math.toDegrees(b-oldB)));
		System.out.println("Old B " + Math.toDegrees(oldB));
		System.out.println("New B " + Math.toDegrees(b));
		return
		 b-oldB;
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
//	public static Point2D rotatePointByFive(double oldX, double oldY, double mouseEasting, double mouseNorthing, double anchorX, double anchorY) throws NumberFormatException, NegativeNumberException {
//		
//		
//		double transEasting;
//		double transNorthing;
//	
//		double rotatedEasting;
//		double rotatedNorthing;
//	
//		double newX;
//		double newY;
//		
//		
//		transEasting = oldX - anchorX;
//		transNorthing = oldY - anchorY;
//		
//		
//		rotatedEasting = transEasting*Math.cos(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY)) - transNorthing*Math.sin(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY));
//		rotatedNorthing = transEasting*Math.sin(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY)) + transNorthing*Math.cos(orientationChange(mouseEasting,mouseNorthing,anchorX,anchorY));
//		
//		//ATan2(dy , dx) where dy = y2 - y1 and dx = x2 - x1, or ATan(dy / dx) 
//		
//		newX = rotatedEasting + anchorX;
//		newY = rotatedNorthing + anchorY;
//		
//		double dy = newY-anchorY;
//		double dx = newX-anchorX;
//		double degs = Math.atan2(dy, dx);
//		
//		Point2D newPoint = new Point2D.Double(newX,newY);
//		Point2D returnPoint = new Point2D.Double();
//		if(degs == degs%5){
//			returnPoint = newPoint;
//			System.out.println(degs);
//		}
//		
//			
//		return
//				returnPoint;
//				
//}



}
