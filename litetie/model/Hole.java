package litetie.model;
/**
 * @author Brent Buffham
 * 
 * @since June 2007
 * 
 * @version 0.1 BETA
 * 
 * @Description Hole is the model for all holes created in LiteTie.
 * @Description Hole extends the Dummy.class with extends the Coordinate.class
 * 
 * @Methods 
 * @LastEditored 26-Jan-09
 * 
 */

import java.awt.Color;
import java.awt.geom.Rectangle2D;

//import javafx.scene.paint.Paint;


public class Hole extends Dummy implements Cloneable{

	protected String labelOne;
	protected String labelTwo;
	protected String labelThree;
	protected double diameter; //mm
	protected double holeLength;//meters
	protected double benchHeight;//meters
	protected double bearing;//degrees
	protected double floorRL;//meters
	protected double toeRL;//meters
	protected double angle;//degrees
	protected double subdrill;//meters
	protected int shape;
	protected String shapeLabel;
	protected Color color;
	//private Paint colorFX;
	private LTPoint3D tXYZ;
	private LTPoint3D fXYZ;
	
	public Hole(int holeID, double x, double y, double z)throws ZeroArgumentException, NegativeNumberException{
		
		super(holeID, x, y, z);
		
	}
	
	public Hole( int holeID, double x, double y, double z, String labelOne, String labelTwo, String labelThree, double diameter, double length ,double bench,
			double bearing,  double floorRL, double toeRL, double angle, double subdrill, int shape, Color color) throws ZeroArgumentException, NegativeNumberException, NumberFormatException{
		
		super (holeID, x, y, z);
		
		setLabelOne(labelOne);
		setLabelTwo(labelTwo);
		setLabelThree(labelThree);
		setDiameter(diameter);
		setHoleLength(length);
		setBench(bench);		
		setBearing(bearing);
		setFloorRL(floorRL);
		setToeRL(toeRL);
		setAngle(angle);
		setSubdrill(subdrill);
		setShape(shape);
		setColor(color);
		
	}
	
	public Hole( double x, double y, double z, String labelOne, String labelTwo, String labelThree,double diameter, double length,double bench,
			double bearing, double floorRL, double toeRL, double angle, double subdrill,int shape, Color color) throws ZeroArgumentException, NegativeNumberException, NumberFormatException{
		
		super (x, y, z);
		
		setLabelOne(labelOne);
		setLabelTwo(labelTwo);
		setLabelThree(labelThree);
		setDiameter(diameter);
		setHoleLength(length);		
		setBench(bench);
		setBearing(bearing);
		setFloorRL(floorRL);
		setToeRL(toeRL);
		setAngle(angle);
		setSubdrill(subdrill);
		setShape(shape);
		setColor(color);
		
	}
	

	
	public String toString(){
		System.out.println(super.toString());
		return displayHoleProperties();
		
	}
	public Hole clone(){
		
		double n = getY();
		double e = getX();
		double c = getZ();
		String l1 = getLabelOne();
		String l2 = getLabelTwo();
		String l3 = getLabelThree();
		double d = getDiameter();
		double l = getHoleLength();
		double bh = getBench();
		double b = getBearing();
		double f = getFloorRL();
		double t = getToeRL();
		double a = getAngle();
		double s = getSubdrill();
		int sh = getShape();
		Color cl = getColor();
		
		Hole clone = null;
		try {
			clone = new Hole(e,n,c,l1,l2,l3,d,l,bh,b,f,t,a,s,sh,cl);
		} catch (NumberFormatException e1) {
			System.out.println("Number is not formatted correctly or value is not a number.");
			e1.printStackTrace();
		} catch (NegativeNumberException e1) {
			System.out.println("Value is required to be a positive value.  Number is not a positive number.");
			e1.printStackTrace();
		} catch (ZeroArgumentException e1) {
			System.out.println("Value is required to be more than zero.  Number has a zero value.");
			e1.printStackTrace();
		}
		
		return clone;
	}
	
	public String displayHoleProperties() {
		
		return
				"\n\tHole Properties"
				+"\nHole ID Number \t-\t" + (getHoleID())
				+"\nNorthing \t-\t" + (getY())+ " mN"
				+"\nEasting \t-\t" + (getX())+ " mE"
				+"\nCollar RL \t-\t" + (getZ()) + " m"
				+"\nDiameter \t-\t" + (getDiameter()) + " mm"
				+"\nLength \t\t-\t" + (getHoleLength()) + " m"
				+"\nBench \t\t-\t" + (getBench()) + " m"
				+"\nBearing \t-\t" + (getBearing()) + " d"
				+"\nFloor RL\t-\t" + (getFloorRL()) + " m"
				+"\nToe RL \t\t-\t" + (getToeRL()) + " m"
				+"\nAngle \t\t-\t" + (getAngle()) + " d"
				+"\nSubrill \t-\t" + (getSubdrill()) + " m"
				+"\nLabel One \t-\t" + (getLabelOne())
				+"\nLabel Two \t-\t" + (getLabelTwo())
				+"\nLabel Three \t-\t" + (getLabelThree())
				+"\nDisplay Shape\t-\t" + (getShape())
				+"\nDisplay Colour\t-\t" + (getColor());
	}
	public Color getColor(){
		return color;
	}
//	public Paint getColorFX(){
//		return colorFX;
//	}
	
	
	/**
	 * @return String "labelOne" of a blast hole
	 * 
	 */
	public String getLabelOne(){
		
		return labelOne;	
		}
	/**
	 * @return String "labelTwo" of a blast hole
	 * 
	 */
	public String getLabelTwo(){ 
		
		return labelTwo;	
		}
	/**
	 * @return String "labelThree" of a blast hole
	 * 
	 */
	public String getLabelThree(){ 
		
		return labelThree;	
		}
	/**
	 * @return The diameter of a blast hole
	 * 
	 */
	public double getDiameter(){ 
	
		return diameter;
		}
	
	/**
	 * @return The length of a blast hole including angle 
	 * 
	 */
	public double getHoleLength()  {
		
		return holeLength;
	}
	/**
	 * @return The bench height of a blast hole
	 * 
	 */
	public double getBench(){
		
		return benchHeight;
		}
	/**
	 * @return The bearing of a blast hole
	 * 
	 */
	public double getBearing(){  
		
		return bearing;
		}
	/**
	 * @return The floor level of a blast hole
	 * 
	 */
	public double getFloorRL(){	
		
		return floorRL;
	}
	/**
	 * @return The toe level of a blast hole
	 * 
	 */
	public double getToeRL(){ 
		
		return toeRL;
		}
	/**
	 * @return The angle of a blast hole
	 * 
	 */
	public double getAngle(){  
		
		return angle;
		}
	/**
	 * @return The sub drill of a blast hole
	 * 
	 */
	public double getSubdrill(){
		
		return subdrill;
	}
	/**
	 * @return The display shape integer of a blast hole
	 * 
	 */
	public int getShape(){
		
		return shape;
	}
	/**
	 * @return The display shape string of a blast hole
	 * 
	 */	
	
	public String getShapeType(){
		
		return shapeLabel;
	}
	
	/**
	 * @return The bounds of a blast hole
	 * 
	 */
	public Rectangle2D getBounds(){
		return new Rectangle2D.Double((x - ((diameter/1000)/2)), (y - ((diameter/1000)/2)), diameter/1000, diameter/1000);
	}
	
	public LTPoint3D getToeXYZ(){
		LTPoint3D cXYZ = new LTPoint3D.Double(getX(),getY(),getZ());
		double a = getAngle();
		double b = getBearing();
		double l = getHoleLength()*Math.sin(Math.toRadians(a));
		double tanA = Math.tan(Math.toRadians(90 - a));
		double toeLoc = l * tanA;
		double translatedX = cXYZ.getX() + toeLoc	* Math.cos(Math.toRadians(b - 90));
		double translatedY = cXYZ.getY() - ((toeLoc * Math.sin(Math.toRadians(b - 90))));
		double translatedZ = getToeRL();
		tXYZ = new LTPoint3D.Double(translatedX, translatedY,translatedZ);
		return tXYZ;
	}
	public LTPoint3D getFloorXYZ(){
		LTPoint3D cXYZ = new LTPoint3D.Double(getX(),getY(),getZ());
		double a = getAngle();
		double b = getBearing();
		double l = Math.abs(cXYZ.getZ()-getFloorRL());
		double tanA = Math.tan(Math.toRadians(90 - a));
		double floorLoc = l * tanA;
		double translatedX = cXYZ.getX() + floorLoc	* Math.cos(Math.toRadians(b-90));
		double translatedY = cXYZ.getY() - ((floorLoc * Math.sin(Math.toRadians(b-90))));
		double translatedZ = getFloorRL();
		fXYZ = new LTPoint3D.Double(translatedX, translatedY,translatedZ);
		
		return fXYZ;
	}
	
	
	//set methods
	
	public void setColor(Color color) {
		this.color = color;
	}
//	public void setColorFX(Paint colorFX) {
//		this.colorFX = colorFX;
//	}
	
	/**
	 * @param String value for 1st Label
	 */
	@ Override
	public void setZ(double z)throws IllegalArgumentException{
		if(z <= getFloorRL())
			throw new IllegalArgumentException("Collar RL on a Hole cannot be <= floor RL");
		else {
			this.holeLength = (z-(this.toeRL))/Math.sin(Math.toRadians(this.angle));
			this.benchHeight = z-(this.floorRL);
			this.z = z;
		}
	}
		
	public void setLabelOne(String labelOne) throws NullPointerException, IllegalArgumentException{
		if (labelOne != null)
			this.labelOne = labelOne;				
		else
			throw new IllegalArgumentException("Label One needs to be something else besides null.");
			}
	/**
	 * @param String value for 2nd Label
	 */
	public void setLabelTwo(String labelTwo) throws NullPointerException, IllegalArgumentException{
		if (labelTwo != null)
			this.labelTwo = labelTwo;				
		else
			throw new IllegalArgumentException("Label Two needs to be something else besides null.");
			}
	/**
	 * @param String value for 3rd Label
	 */
	public void setLabelThree(String labelThree) throws NullPointerException, IllegalArgumentException{
		if (labelThree != null)
			this.labelThree = labelThree;				
		else
			throw new IllegalArgumentException("Label Three needs to be something else besides null.");
			}
	/**
	 * @param Double value for Hole Length
	 */
	public void setHoleLength(double length) throws NegativeNumberException, ZeroArgumentException {
		
		if(Math.abs(length - (getZ()- getToeRL())/Math.sin(Math.toRadians(getAngle()))) < 0.009){
			this.holeLength = length;
		}
		else if(!(Math.abs(length - (getZ()- getToeRL())/Math.sin(Math.toRadians(getAngle()))) < 0.009)){
			this.holeLength = length;

			setSubdrill(Math.abs(length - (getZ()- getToeRL())*Math.sin(Math.toRadians(getAngle()))));
		}
			
		if(length < 0)
			throw new NegativeNumberException ("The hole length can not be negative");
		else if(length == 0)
			throw new ZeroArgumentException("A hole of 0m does not constitute a hole but a point");
		else
			this.holeLength = length;
			this.subdrill = length-(z-floorRL);
		}
	/**
	 * @param Double value for Hole Diameter
	 */
	public void setDiameter (double diameter) throws NegativeNumberException, ZeroArgumentException {
		if (diameter < 0)
			throw new NegativeNumberException ("The diameter can not be negative.");
		else if (diameter == 0)
			throw new ZeroArgumentException ("The diameter can not be equal to zero");
		else 
			this.diameter = diameter;
		}
	/**
	 * @param Double value for Vertical Bench Height
	 */
	// bench will be affected by collarRL, toeRL, angle
	public void setBench(double bench) throws NegativeNumberException, ZeroArgumentException{
		if (bench < 0)
			throw new NegativeNumberException ("The bench height can not be negative");
		else if (bench == 0)
			throw new ZeroArgumentException ("The bench height can not be equal to zero");
		else {
			this.benchHeight = bench;
			this.floorRL = this.z-bench;
			this.toeRL = this.floorRL-this.subdrill;
			this.holeLength = (this.z-(this.toeRL))/Math.sin(Math.toRadians(this.angle));
		}
	}
	/**
	 * @param Double value for Hole Bearing
	 */
	public void setBearing(double bearing) throws IllegalArgumentException {
		if (bearing < -720 || bearing > 720)
			throw new IllegalArgumentException ("Value is either less than -720 or more than 720!");
		else {
			this.bearing = bearing;
			
		}
	}

	/**
	 * @param Double value for Floor Reducing Level
	 */
	public void setFloorRL(double floorRL)throws NumberFormatException {
		if (floorRL >= z)
			throw new IllegalArgumentException ("floorRL can not be equal to or greater than CollarRL");
		else {
			this.floorRL = floorRL;
			this.toeRL = floorRL-this.subdrill;
			this.holeLength = (this.z-(this.toeRL))/Math.sin(Math.toRadians(this.angle));
			this.benchHeight = this.z-floorRL;
		}
	}
	/**
	 * @param Double value for Toe Reducing Level
	 */
	public void setToeRL (double toeRL) {
		if (toeRL >= z)
			throw new IllegalArgumentException ("ToeRL can not be equal to or greater than CollarRL");
		else
			this.toeRL = toeRL;
		}
	/**
	 * @param Double value for Hole Angle (Vertical Holes are 90 degrees)
	 */
	public void setAngle(double angle) {
		if (angle <= 20 || angle > 90){
			angle = 90;
			throw new IllegalArgumentException ("Value outside range! Angle of hole force set to 90 degrees");
			}
		else {
			this.angle = angle;
			this.holeLength = (this.z-(this.toeRL))/Math.sin(Math.toRadians(angle));
		}
		}
	/**
	 * @param Double value for Hole Subdrill 
	 */
	public void setSubdrill(double subdrill) {
		if (subdrill <= (0 - benchHeight ))
			throw new IllegalArgumentException ("Subdrill makes hole negative depth");
		else {
			this.subdrill = subdrill;
			this.toeRL = this.floorRL-subdrill;
			this.holeLength = (this.z-(this.toeRL))/Math.sin(Math.toRadians(angle));
		}
	}
	/**
	 * @param int value for Hole shape 
	 */
	public void setShape(int shape) {
		if (shape <  0 && shape >10)
			throw new IllegalArgumentException ("No shapes are available above 10");
		else{
			this.shape = shape;
			this.setShapeLabel(shape);
		}
	}
	public void setShapeLabel(int shape){
		
		if (this.getShape() == 0){
			shapeLabel = "Image";
		}
		else if (this.getShape() == 1){
			this.shapeLabel ="Circle";
		}
		else if (this.getShape() == 2){
			this.shapeLabel ="Cross";
		}
		else if (this.getShape() == 3){
			this.shapeLabel ="Triangle";
		}
		else if (this.getShape() == 4){
			this.shapeLabel ="Square";
		}
		else if (this.getShape() == 5){
			this.shapeLabel ="Pentagon";
		}
		else if (this.getShape() == 6){
			this.shapeLabel ="Hexagon";
		}
		else if (this.getShape() == 7){
			this.shapeLabel ="Star";
		}
		else if (this.getShape() == 8){
			this.shapeLabel ="Filled Circle";
		}
		else if (this.getShape() == 9){
			this.shapeLabel ="Filled Triangle";
		}
		else if (this.getShape() == 10){
			this.shapeLabel ="Filled Square";
		}
	}
//	public boolean equals(Object duplicateHole) {
//	
//		if (duplicateHole == null || (duplicateHole.getClass() != this.getClass()))
//			return false;
//		Hole hole1 = (Hole)duplicateHole;
//		if (this.getHoleID() != hole1.getHoleID())
//			return false;
//		if (this.getNorthing() != hole1.getNorthing())
//			return false;
//		if (this.getEasting() != hole1.getEasting())
//			return false;
//		if (this.getCollarRL() !=hole1.getCollarRL())
//			return false;
//		return true;
//	}
//	public int hashCode() { 
//		int hash = 9;  //In Dummy.class this hash code is 7 is this an issue???? Why it thinks a Hole is the same as a dummy? ToFromException
//		int hVar = (int) holeID;
//		
//		long nBits = Double.doubleToLongBits(northing);
//		int nVar = (int) (nBits ^ (nBits >>>32));
//		
//		long eBits = Double.doubleToLongBits(easting);
//		int eVar = (int) (eBits ^ (eBits >>> 32));
//			
//		hash = 31 * hash + hVar;
//		hash = 31 * hash + nVar;
//		hash = 31 * hash + eVar;
//	    return hash;
//	    
//	  }
	
//	public static void main(String [] args) throws ZeroArgumentException,  NegativeNumberException{
//		
//		//Variable assignment for Testing
//		Hole testH = new Hole(1, 500, 100,100, "Label1","Label2", "Label3",80, 10, 9, 0, 91, 90, 80, 1,1);
//		
//		
//		testH.setNorthing(20000.555); 
//		testH.setEasting(1000.555);
//		testH.setCollarRL(555);
//		testH.setLabelOne("1a");
//		testH.setLabelTwo("2a");
//		testH.setLabelThree("3a");
//		testH.setHoleLength(12.5);
//		testH.setDiameter(89);
//		testH.setBench(12.5);
//		testH.setBearing(10);
//		testH.setToeRL(10);
//		testH.setAngle(80);
//		testH.setSubdrill(2);
//		testH.setShape(1);
//		
//		testH.getLabelOne();
//		testH.getLabelTwo();
//		testH.getLabelThree();
//		testH.getNorthing(); 
//		testH.getEasting();
//		testH.getHoleLength();
//		testH.getDiameter();
//		testH.getBench();
//		testH.getBearing();
//		testH.getCollarRL();
//		testH.getToeRL();
//		testH.getAngle();
//		testH.getSubdrill();
//		testH.getShape();
//		
//		//Console display of methods
//		//System.out.print(testH.displayHoleProperties());
//		
//		//System.out.print(testH.toString());
//		
//		
//		
//		
//		
//	}
	
	}

