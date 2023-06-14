package litetie.model;
/**
 * @author brentbuffham
 * @description BPoints are insubstantial points on a boundary
 */

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;



public class BPoint extends Coordinate{
	
	protected int pointID;
	protected int boundaryID;
	
//	public BPoint( int pointID, double easting, double northing, double collarRL) throws ZeroArgumentException, NegativeNumberException {
//		super(easting, northing, collarRL);
//		setPointID(pointID);
//	}

	public BPoint(int boundaryID, int pointID, double x, double y, double z) throws ZeroArgumentException, NegativeNumberException {
		super(x, y, z);
		setBoundaryID(boundaryID);
		setPointID(pointID);
	}
	public String toString(){
		System.out.println(super.toString());
		return displayPointLocation();
	}
	
	
	public String displayPointLocation() {
		return 
				"Point Location" 
				+"\nID number - \t" + (pointID)
				+"\nX - \t" + (x)
				+"\nY - \t" + (y)
				+"\nZ - \t" + (z);
	}
	/**
	 * @return The hole Identification Number
	 */
	public int getPointID(){
		return pointID;
	}
	public int getBoundaryID(){
		return boundaryID;
	}
	/**
	 * @return The bounds of a Point, that is the area that it occupies 
	 * @return (being that it is an infinite point, this uses 2 pixels to determine point size 
	 */
	public Rectangle2D getBounds(){
		return new Rectangle2D.Double((x - UnitConvert.pixelsToMeters(2)), (y + UnitConvert.pixelsToMeters(2)), UnitConvert.pixelsToMeters(4), UnitConvert.pixelsToMeters(4));
	}
	
	public void setPointID(int pointID) throws ZeroArgumentException, NegativeNumberException{
		if (pointID == 0)
			throw new ZeroArgumentException("A point ID number can not be equal to 0. - Dummy Class");
		else if (pointID < 0)
			throw new NegativeNumberException("A point ID number can not be a negative integer. - Dummy Class");
		else
			this.pointID = pointID;
		}
	public void setBoundaryID(int boundaryID) throws ZeroArgumentException, NegativeNumberException{
		if (boundaryID == 0)
			throw new ZeroArgumentException("A boundary ID number can not be equal to 0. - Dummy Class");
		else if (pointID < 0)
			throw new NegativeNumberException("A boundary ID number can not be a negative integer. - Dummy Class");
		else
			this.boundaryID = boundaryID;
		}
	public LTPoint3D toPoint3D() {
		LTPoint3D point = new LTPoint3D.Double(this.getX(),this.getY(),this.getZ());
		return point;
	}
	public Point2D toPoint2D() {
		Point2D point = new Point2D.Double(this.getX(),this.getY());
		return point;
	}	

}
