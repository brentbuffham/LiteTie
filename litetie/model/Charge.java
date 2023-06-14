package litetie.model;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


public class Charge{
	
	protected int id;
	protected BulkProduct bulkProduct;
	protected String type;
	protected Hole hole;
	protected double capDepth;
	protected double baseDepth;
	protected double capDensity;
	protected boolean compressible;
	protected double mass;
	protected double chargeDiameter;
	protected Color color;
	protected double length;
	
	public Charge(int id, BulkProduct bulkProduct, Hole hole, double capDepth, double baseDepth, double capDensity, double chargeDiameter, boolean compressible, Color color) throws NumberFormatException, ZeroArgumentException, NegativeNumberException {
		setID(id);
		setBulkProduct(bulkProduct);
		setInHole(hole);
		setStartDepth(capDepth); 
		setFinishDepth(baseDepth); 
		setDensity(capDensity);
		setChargeDiameter(chargeDiameter);
		isCompressible(compressible);
		setColor(color);
		
	}
	public Charge(BulkProduct type, Hole hole, double capDepth, double baseDepth, double capDensity, double chargeDiameter, boolean compressible, Color color) throws NumberFormatException, ZeroArgumentException, NegativeNumberException {
		setBulkProduct(bulkProduct);
		setInHole(hole);
		setStartDepth(capDepth); 
		setFinishDepth(baseDepth); 
		setDensity(capDensity);
		setChargeDiameter(chargeDiameter);
		isCompressible(compressible);
		setColor(color);
		
	}
	
	public Charge(BulkProduct bulkProduct, Hole hole, double capDepth) throws NumberFormatException, NegativeNumberException, ZeroArgumentException {
		
		setBulkProduct(bulkProduct);
		setInHole(hole);
		setStartDepth(capDepth); 
		setMass();
		setColor(color);
		
	}
	public Charge(BulkProduct bulkProduct, Hole hole, double capDepth, double capDensity) throws NumberFormatException, NegativeNumberException, ZeroArgumentException {
		
		setBulkProduct(bulkProduct);
		setInHole(hole);
		setStartDepth(capDepth); 
		setDensity(capDensity);
		setColor(color);
		
	}
	public Charge(BulkProduct bulkProduct, Hole hole, double length, Color color) throws NumberFormatException, NegativeNumberException, ZeroArgumentException {
	
		setBulkProduct(bulkProduct);
		setInHole(hole);
		setLength(length);
		setColor(color);
		
	}
	public Charge(String type, Hole hole, double capDepth, double baseDepth, double capDensity, double chargeDiameter, boolean compressible, Color color) throws NumberFormatException, ZeroArgumentException, NegativeNumberException {
		setTypeName(type);
		setInHole(hole);
		setStartDepth(capDepth); 
		setFinishDepth(baseDepth); 
		setDensity(capDensity);
		setChargeDiameter(chargeDiameter);
		isCompressible(compressible);
		setColor(color);
		
	}
	public Charge(int id, String type, Hole hole, double capDepth, double baseDepth, double capDensity, double chargeDiameter, boolean compressible, Color color) throws NumberFormatException, ZeroArgumentException, NegativeNumberException {
		setID(id);
		setTypeName(type);
		setInHole(hole);
		setStartDepth(capDepth); 
		setFinishDepth(baseDepth); 
		setDensity(capDensity);
		setChargeDiameter(chargeDiameter);
		isCompressible(compressible);
		setColor(color);
		
	}
	
	public Charge(){
		super();
	}

// this uses Hole's equals and toString methods.
	public String displayChargeProperties() {
		
		return 
				"Charge ID - \t" + id +"\n"+
				"Type - \t" + bulkProduct.getProductName() +"\n"+
				"Mass - \t" + mass +"\n"+
				"In Hole - \t" + hole+ "\n";
		
	}
	
//GET METHODS	
	public int getID(){
		return id;
	}	
	public BulkProduct getBulkProduct() {
		return bulkProduct;
	}
	public String getTypeName(){
		return type;
	}
	public Hole getInHole() {
		return hole;
	}
	public double getLength(){
		return length;
	}
	public double getStartDepth() {
		return capDepth; 
	}
	public double getFinishDepth() {
		return baseDepth; 
	}
	public double getDensity() {
		return capDensity;
	}
	public double getChargeDiameter() {
		return chargeDiameter;
	}
	public boolean getCompressible() {
		return compressible;
	}
	public Color getColor() {
		return color;
	}
	
//SET METHODS
	/**
	 * @param id
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 * @since 01-03-2014
	 */
	void setID(int id) throws NegativeNumberException, ZeroArgumentException {
		if (id < 0){
			throw new NegativeNumberException("Detonator IDs can't be negative."); 
		}
		else if (id == 0){
			throw new ZeroArgumentException("Detonator IDs can't be zero.");
		}
		else
			this.id = id;
	}

	/**
	 * @param hole
	 * @throws NullPointerException
	 * @since 01-03-2014
	 */
	public void setInHole(Hole hole)throws NullPointerException{
		if (hole != null){
			this.hole = hole;
			}
		else 
			throw new NullPointerException ("Charges must go in a hole.");
	}
	/**
	 * @param length
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 * @since 01-03-2014
	 */
	public void setLength(double length) throws NegativeNumberException, ZeroArgumentException {
		
		if(Math.abs(length - (Math.abs(getStartDepth() - getFinishDepth())/Math.sin(Math.toRadians(hole.getAngle())))) <= 0.009){
			this.length = length;
		}
		else if(Math.abs(length - (Math.abs(getStartDepth() - getFinishDepth())/Math.sin(Math.toRadians(hole.getAngle())))) > 0.009){
			length = Math.abs(getStartDepth() - getFinishDepth())/Math.sin(Math.toRadians(hole.getAngle()));
		}	
		if(length < 0)
			throw new NegativeNumberException ("Charge Length  can not be negative.");
		else if(length == 0)
			throw new ZeroArgumentException("Charge Length can not be zero.");
	}
	/**
	 * @param startDepth
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 * @since 01-03-2014
	 */
	public void setStartDepth(double startDepth) throws NegativeNumberException, ZeroArgumentException{
		if (startDepth < 0){
			throw new NegativeNumberException("Start Depth can not be negative."); 
		}
		else
			this.capDepth = startDepth;
	}
	/**
	 * @param finishDepth
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 * @since 01-03-2014
	 */
	public void setFinishDepth(double finishDepth) throws NegativeNumberException, ZeroArgumentException{
		if (finishDepth < 0){
			throw new NegativeNumberException("Finish Depth can not be negative."); 
		}
		else
			this.baseDepth = finishDepth;
	}
	/**
	 * @since 01-03-2014
	 * @param density
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 */
	public void setDensity(double density) throws NegativeNumberException, ZeroArgumentException{
		if (density < 0){
			throw new NegativeNumberException("Density can not be negative."); 
		}
		else if (density == 0){
			throw new ZeroArgumentException("Density can not be zero.");
		}
		else
			this.capDensity = density;
	}
	/**
	 * @since 01-03-2014
	 * @param chargeDiameter
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 */
	public void setChargeDiameter(double chargeDiameter) throws NegativeNumberException, ZeroArgumentException{
		if (chargeDiameter < 0){
			throw new NegativeNumberException("Charge Diameter can not be negative."); 
		}
		else if (chargeDiameter == 0){
			throw new ZeroArgumentException("Charge Diameter can not be zero.");
		}
		else
			this.chargeDiameter = chargeDiameter;
	}

	public void isCompressible(boolean compressible) {
		this.compressible = compressible;
	}
	/**
	 * @param bulkProduct 
	 * 
	 */
	public void setBulkProduct(BulkProduct bulkProduct){
		this.bulkProduct = bulkProduct;
	}
	public void setTypeName(String type){
		this.type = type;
	}
	
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
	public void setMass() {
		if (getLength() > 0) {
			double radiusSQ = (Math.pow((getChargeDiameter()/2),2)/1000);
			this.mass = ((Math.PI * radiusSQ) * getLength() * getDensity());
		}
	}
	
	public Rectangle2D getBounds(){
		return new Rectangle2D.Double((hole.getX() - ((hole.getDiameter()/1000)/2)), (hole.getY() - ((hole.getDiameter()/1000)/2)), hole.getDiameter()/1000, hole.getDiameter()/1000);
	}
	
	
//	
}
