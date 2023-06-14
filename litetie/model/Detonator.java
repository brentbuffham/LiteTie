package litetie.model;

import java.awt.Color;
import java.awt.geom.Rectangle2D;


public class Detonator{
	
	protected int detID;
	protected int delay;
	protected Hole hole;
	protected double length;
	protected Color color;
	
	public Detonator(int detID, int delay, Hole hole,double length, Color color) throws NumberFormatException, ZeroArgumentException, NegativeNumberException {
		setDetID(detID);
		setDelay(delay);
		setLength(length);
		setInHole(hole);
		setColor(color);
		
	}
	
	public Detonator(int delay) throws NumberFormatException, NegativeNumberException {
		
		setDelay(delay);
		
	}
	public Detonator(int delay, Hole hole, double length, Color color) throws NumberFormatException, NegativeNumberException, ZeroArgumentException {
	
		setDelay(delay);
		setInHole(hole);
		setLength(length);
		setColor(color);
		
	}
	// this uses Hole's equals and toString methods.
	


	public String displayDelayProperties() {
		
		return 
				"DetID - \t" + detID +"\n"+
				"Delay - \t" + delay +"\n"+
				"In Hole - \t" + hole+ "\n";
		
	}
	
//GET METHODS	
	public int getDetID(){
		return detID;
	}
	
	public int getDelay() {
		return delay;
	}

	public Hole getInHole() {
		return hole;
	}
	public double getLength(){
		return length;
	}
	public Color getColor() {
		return color;
	}
	
//SET METHODS
	void setDetID(int id) throws NegativeNumberException, ZeroArgumentException {
		if (id < 0){
			throw new NegativeNumberException("Detonator IDs can't be negative."); 
		}
		else if (id == 0){
			throw new ZeroArgumentException("Detonator IDs can't be zero.");
		}
		else
			this.detID = id;
	}
	public void setDelay(int delay) throws NegativeNumberException{
		if (delay < 0)
			throw new NegativeNumberException("Delay needs to be positive");
		else
			this.delay = delay;
	}
	void setLength(double length) throws NegativeNumberException, ZeroArgumentException {
		if (length < 0){
			throw new NegativeNumberException("Detonator lengths can't be negative."); 
		}
		else if (length == 0){
			throw new ZeroArgumentException("Detonator lengths can't be zero.");
		}
		else
			this.length = length;
	}
	public void setInHole(Hole hole)throws NullPointerException{
		if (hole != null){
			this.hole = hole;
			}
		else 
			throw new NullPointerException ("Detonators must go in a hole.");
	}
	
	/**
	 * @author brentbuffham
	 * @throws Stuff
	 * @param color
	 */
	public void setColor(Color color){
		if(color == null){
			throw new NullPointerException("color is null");
		}
		else if(!(color instanceof Color)){
			throw new ClassCastException("color is not a of class Color");
		}
		else
			this.color =color;
	}
	
	
	public Rectangle2D getBounds(){
		return new Rectangle2D.Double((hole.getX() - ((hole.getDiameter()/1000)/2)), (hole.getY() - ((hole.getDiameter()/1000)/2)), hole.getDiameter()/1000, hole.getDiameter()/1000);
	}
	
	
//	
}
