package litetie.model;

import java.awt.geom.Rectangle2D;


/**
 * 
 * @author Brent Buffham
 * 
 * @since 26 January 2009
 * 
 * @version 0.1 BETA
 * 
 * @category Co-ordinate System
 * 
 * Coordinate controls all initial point systems in the 3D environment that exists in LiteTie.
 * 
 * All visualization of Objects created are currently only displayed in two dimensions (2D) - 26-01-09.
 *
 */
public class Coordinate implements Cloneable{
	protected int cID;
	protected double y;//y value
	protected double x;//x value
	protected double z;//z value
	
	//Coordinate Constructor
	public Coordinate(double x, double y, double z) throws NumberFormatException{
		setX(x);
		setY(y);
		setZ(z);
		
	}
	public String toString(){
		return displayCoordinates();
	}
	
	
	public String displayCoordinates() {
		return
				"Coordinate" 
				+"\nX = " + (x)+" , \tY = " + (y)+ ", \tZ = " + (z);
	}
	
	public Rectangle2D getBounds(){
		return new Rectangle2D.Double((x - UnitConvert.pixelsToMeters(2)), (y + UnitConvert.pixelsToMeters(2)), UnitConvert.pixelsToMeters(4), UnitConvert.pixelsToMeters(4));
	}

	public double getX(){
		return x;
	}	
	public double getY(){
		return y;
		}
	public double getZ(){  
		return z; 
		}
	
	public void setX(double x) throws NumberFormatException {
			this.x = x;
	}
	
	public void setY(double y) throws NumberFormatException {

			this.y = y;
	} 
	
	public void setZ(double z)throws NumberFormatException, IllegalArgumentException {
			this.z = z;
		}
	
	public void setLocation(Coordinate coordinate) throws NumberFormatException, IllegalArgumentException{
		this.x = coordinate.getX();
		this.y = coordinate.getY();
		this.z = coordinate.getZ();
	}
	
	public Coordinate clone(){
		
		double x = getX();
		double y = getY();
		double z = getZ();
		
		Coordinate clone = null;
		try {
			clone = new Coordinate(x,y,z);
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return clone;
	}
}
