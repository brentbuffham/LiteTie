package litetie.model;
import java.awt.geom.Rectangle2D;

import litetie.LiteTieTRIAL;


public class InitiationPoint {

	protected int iPTime;
	protected Dummy pOI;
	protected int id;
	
	public InitiationPoint (int iPTime, Dummy pOI) throws NegativeNumberException {
		setIPTime(iPTime);
		setIP(pOI);
	}
	public InitiationPoint (int id, int iPTime, Dummy pOI) throws NegativeNumberException, ZeroArgumentException{
		setIPID(id);
		setIPTime(iPTime);
		setIP(pOI);
	}
	
	public boolean equals(Object duplicatePOI) {
		if (duplicatePOI == null)
			return false;
		if (!(duplicatePOI instanceof InitiationPoint))
			return false;
		InitiationPoint iP1 = (InitiationPoint)duplicatePOI;
		return
			this.pOI.equals(iP1.pOI);			
	}
	
// This class needs an boolean isIP so it can check when importing/loading a file whether something is an IP
//	public boolean isIP(boolean isIP){
//		if (getIPID() ==  )
//		return isIP;	
//	}
	
	public String toString() {
		return
			("P.O.I. time is " + iPTime +"\nP.O.I. is " + pOI.toString());
	}
	
	public int getIPID(){
		return id;
	}
	
	public int getIPTime() {
		return iPTime;
	}
	public Dummy getIPDummy() {
		return pOI;
	}
	

	
	public void setIPID(int id) throws NegativeNumberException, ZeroArgumentException{
		if (id == 0)
			throw new ZeroArgumentException("A IP ID number can not be equal to 0. - InitiationPoint  Class");
		else if (id < 0)
			throw new NegativeNumberException ("Id of an IP can not be negative - InitiationPoint  Class");
		else
			this.id = id;
	
	}
	
	public void setIPTime(int iPTime) throws NegativeNumberException {
		if (iPTime < 0 )
			throw new NegativeNumberException ("time of an IP can not be negative");
		else
			this.iPTime = iPTime;
	}
	public void setIP (Dummy pOI) throws IllegalArgumentException {
		if (pOI == null)
			throw new IllegalArgumentException ("IP's must be implemented on a dummy or a hole.");
		else
			this.pOI = pOI;
	}
	public Rectangle2D getBounds(){
		
		return new Rectangle2D.Double(pOI.x, pOI.y, LiteTieTRIAL.averageSize, LiteTieTRIAL.averageSize);
	}
	
}
