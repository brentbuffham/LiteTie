package litetie.model;
/**
 * @author brentbuffham
 * @description Dummies are insubstantial holes used to achieve correct timing when using Surface connectors
 */

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


import litetie.LiteTieTRIAL;

public class Dummy extends Coordinate implements Cloneable{
	
	protected int holeID;
	
	public Dummy( int holeID, double x, double y, double z) throws ZeroArgumentException, NegativeNumberException {
		super(x,y, z);
		setHoleID(holeID);
	}

	public Dummy( double x, double y, double z) throws ZeroArgumentException, NegativeNumberException {
			super(x,y, z);
	}
	
	//Old equals() method  Joshua helped me write way back in the day
	public boolean equals(Object dummy) {
		boolean isTrue = false;
		if(dummy == null || (dummy.getClass() != this.getClass())){
			isTrue = false;
			}
		
		else if (dummy instanceof Dummy){

			Dummy d = (Dummy)dummy;
			
			if (this.getHoleID() == d.getHoleID() && this.getY() == d.getY() && this.getX() == d.getX() && this.getZ() == d.getZ()){
				isTrue = true;
			}
			else if (this.getHoleID() == d.getHoleID() && this.getY() == d.getY() && this.getX() == d.getX() && this.getZ() != d.getX()){isTrue = false;}
			else if (this.getHoleID() == d.getHoleID() && this.getY() == d.getY() && this.getX() != d.getX() && this.getZ() != d.getX()){isTrue = false;}
			else if (this.getHoleID() == d.getHoleID() && this.getY() != d.getY() && this.getX() != d.getX() && this.getZ() != d.getX()){isTrue = false;}
			else if (this.getHoleID() != d.getHoleID() && this.getY() != d.getY() && this.getX() != d.getX() && this.getZ() != d.getX()){isTrue = false;}
			else if (this.getHoleID() != d.getHoleID() && this.getY() != d.getY() && this.getX() != d.getX() && this.getZ() == d.getX()){isTrue = false;}
			else if (this.getHoleID() != d.getHoleID() && this.getY() != d.getY() && this.getX() == d.getX() && this.getZ() == d.getX()){isTrue = false;}
			else if (this.getHoleID() != d.getHoleID() && this.getY() == d.getY() && this.getX() == d.getX() && this.getZ() == d.getX()){isTrue = false;}
			else if (this.getHoleID() != d.getHoleID() && this.getY() == d.getY() && this.getX() != d.getX() && this.getZ() == d.getX()){isTrue = false;}
			else if (this.getHoleID() == d.getHoleID() && this.getY() != d.getY() && this.getX() == d.getX() && this.getZ() != d.getX()){isTrue = false;}
			else if (this.getHoleID() == d.getHoleID() && this.getY() != d.getY() && this.getX() != d.getX() && this.getZ() == d.getX()){isTrue = false;}
			else if (this.getHoleID() != d.getHoleID() && this.getY() == d.getY() && this.getX() == d.getX() && this.getZ() != d.getX()){isTrue = false;}
		}
		 
		return isTrue;
	}
	

	public int hashCode() { 
		int hash = 7;
		int hVar = (int) holeID;
		
		hash = 31 * hash + hVar;
	
	    return hash;
	    
	  }
	
	public String toString(){
		System.out.println(super.toString());
		return displayDummyLocation();
	}
	
	
	public String displayDummyLocation() {
		return
				"Dummy Location" 
				+"\nHole ID number - \t" + (holeID)
				+"\nX - \t" + (x)
				+"\nX - \t" + (y)
				+"\nZ - \t" + (z)+ "\n";
	}
	/**
	 * @return The hole Identification Number
	 */
	public int getHoleID(){
		return holeID;
	}
	/**
	 * @return The bounds of a Dummy, that is the area that it occupies 
	 * @return (being that it is an infinite point, this uses average 
	 * @return hole size of the pattern the dummy is allocated to.)
	 */
	public Rectangle2D getBounds(){
		return new Rectangle2D.Double((x - ((LiteTieTRIAL.averageSize/1000)/2)), (y - ((LiteTieTRIAL.averageSize/1000)/2)), LiteTieTRIAL.averageSize/1000, LiteTieTRIAL.averageSize/1000);
	}
	
	public void setHoleID(int holeID) throws ZeroArgumentException, NegativeNumberException{
		if (holeID == 0)
			throw new ZeroArgumentException("A hole ID number can not be equal to 0. - Dummy Class");
		else if (holeID < 0)
			throw new NegativeNumberException("A hole ID number can not be a negative integer. - Dummy Class");
		else
			this.holeID = holeID;
		}
	

	public static void main(String[] args) throws ZeroArgumentException, NegativeNumberException {
		
		Dummy d1 = new Dummy(1,2,2);
		Dummy d2 = new Dummy(1,2,2);
		Dummy d3 = new Dummy(1,2,2);
		Dummy d4 = new Dummy(1,2,2);
		Dummy d5 = new Dummy(1,2,2);
		
		if(d1==d2)
			System.out.println("d1 same as d2");
		else if (d1==d3)
			System.out.println("d1 same as d2");
		
		
		

		
	}
	public LTPoint2D toPoint2D() {
		return new LTPoint2D.Double(this.getX(),this.getY());
	}
	public LTPoint3D toPoint3D() {
		return new LTPoint3D.Double(this.getX(),this.getY(),this.getZ());
	}
}
