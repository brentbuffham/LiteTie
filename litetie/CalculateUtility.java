package litetie;

import java.util.Set;


import litetie.model.BPoint;
import litetie.model.Coordinate;
import litetie.model.Hole;
import litetie.model.LTPoint3D;
import litetie.model.Text;

public class CalculateUtility {
	
	protected double volume;
	protected double area;
	protected double chargeLength;
	protected double chargeVolume;
	protected double burden;
	protected double spacing;

	double volumeCubic(double length, double width, double height) {
		volume = length * width * height;
		return volume;
	}
	
	double volumeSphere(double radius) {
		volume = Math.PI*(Math.pow(radius, 2));
		return volume;
	}
	double volumePolygon() {
		//fix for irregular polys
		return volume;
	}
	double volumeElipsoid(double length, double width, double height) {
		volume = 4*Math.PI*(length*width*height)/3;
		return volume;
	}
	double volumeCylinder(double radius, double height) {
		volume = Math.PI*(Math.pow(radius, 2))*height;
		return volume;
	}
	
	double volumeCylinderPortion(double radius, double height, double angle) {
		height = (height*(Math.sin(2*Math.PI*(angle)/360)) + height)/2;
		volume = Math.PI*(Math.pow(radius, 2))*height;
		return volume;
	}
	
	double areaSquare(double length, double width) {
		area = length*width;
		return area;
	}
	double areaCircle(double radius) {
		area = Math.PI*(2*radius);
		return area;
	}
	double areaPolygon(int points, double [] xpoints, double [] ypoints) {
		//fix for irregular
		return area;
	}
	double calculateBearing(Coordinate point1, Coordinate point2){
		double eastingDifference = (point2.getX() - point1.getX());
		double northingDifference = (point2.getY() - point1.getY());
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
		return 
				newBearing;
	}
	public double calculateBearing(LTPoint3D point3D_1, LTPoint3D point3D_2){
		double eastingDifference = (point3D_2.getX() - point3D_1.getX());
		double northingDifference = (point3D_2.getY() - point3D_1.getY());
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
		return 
				newBearing;
	}
	
	double calculateDistance(LTPoint3D p1, LTPoint3D p2){
		double xl = (p1.getX() - p2.getX());
		double yl = (p1.getY() - p2.getY());
		double d = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));
		return d;
	}

	public double calculateDistance(Coordinate p1, Coordinate p2) {
		double xl = (p1.getX() - p2.getX());
		double yl = (p1.getY() - p2.getY());
		double d = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));
		return d;
	}
	/**
	 * Gets the index of an entry in a Set.
	 * @param set
	 * @param value
	 * @return
	 */
	 public int getSetIndex(Set<? extends Object> set, Object value) {
		   int result = 0;
		   for (Object entry:set) {
		     if (entry.equals(value)) return result;
		     result++;
		   }
		   return -1;
		 }
	
}
