package litetie.model;
/**
 * @author Brent Buffham
 * @date Sometime in 2007
 * 
 * @description This class creates surface connection from hole to hole(s).
 */

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import litetie.LiteTieTRIAL;

public class SurfaceConnector{

	protected int time;
	protected int style = 0;
	protected int delay;
	protected double length;
	protected int surfaceID;
	protected int fromHID;
	protected int toHID;
	protected Dummy from;
	protected Dummy to;
	protected Color color;
	protected double xl;//adj length
	protected double yl;//opp length
	protected double d;//hyp length
	protected double w;
	protected double b;
	public final static int LIN_ST_STYLE = 0;
	public final static int ARC_SM_STYLE = 1;
	public final static int ARC_LG_STYLE = 2;
	public final static int BEZIER_STYLE = 3;
	
	public SurfaceConnector(int delay, double length, Dummy from, Dummy to, Color color) 
	throws NegativeNumberException, NumberFormatException, ZeroArgumentException, NullPointerException, FromToException {
		
		setDelay(delay);
		setLength(length);
		setFrom(from);
		setTo(to);
		setColor(color);
		setTime(0);
		setStyle(LIN_ST_STYLE);
	}
	public SurfaceConnector(int delay, double length, Dummy from, Dummy to, Color color, int style) 
	throws NegativeNumberException, NumberFormatException, ZeroArgumentException, NullPointerException, FromToException {
		
		setDelay(delay);
		setLength(length);
		setFrom(from);
		setTo(to);
		setColor(color);
		setTime(0);
		setStyle(style);
	}
	public SurfaceConnector(int id, int delay, double length, Dummy from, Dummy to, Color color)
	throws NegativeNumberException, NumberFormatException, ZeroArgumentException, NullPointerException, FromToException{
		setDelay(delay);
		setLength(length);
		setSurfaceID(id);
		setFrom(from);
		setTo(to);
		setColor(color);
		setTime(0);
		setStyle(LIN_ST_STYLE);
	}
	public SurfaceConnector(int id, int delay, double length, int fromHID, int toHID, Color color)
	throws NegativeNumberException, NumberFormatException, ZeroArgumentException, NullPointerException, FromToException{
		setDelay(delay);
		setLength(length);
		setSurfaceID(id);
		setFromHID(fromHID);
		setToHID(toHID);
		setColor(color);
		setTime(0);
		setStyle(LIN_ST_STYLE);
	}
	public SurfaceConnector(int id, int delay, double length, int fromHID, int toHID, Color color, int style)
			throws NegativeNumberException, NumberFormatException, ZeroArgumentException, NullPointerException, FromToException{
				setDelay(delay);
				setLength(length);
				setSurfaceID(id);
				setFromHID(fromHID);
				setToHID(toHID);
				setColor(color);
				setTime(0);
				setStyle(style);
			}
	

	public SurfaceConnector(int id, int delay, int fromHID, int toHID,Color color)
	throws NegativeNumberException, NumberFormatException, ZeroArgumentException, NullPointerException, FromToException{
				
		setDelay(delay);
		setSurfaceID(id);
		setFromHID(fromHID);
		setToHID(toHID);
		setColor(color);
		setTime(0);
		setStyle(LIN_ST_STYLE);
	}
	public SurfaceConnector(int delay, int fromHID, int toHID, Color color)
	throws NegativeNumberException, NumberFormatException, ZeroArgumentException, NullPointerException, FromToException{
				
		setDelay(delay);
		setFromHID(fromHID);
		setToHID(toHID);
		setColor(color);
		setTime(0);
		setStyle(LIN_ST_STYLE);
	}
	public SurfaceConnector(int delay, Dummy from, Dummy to, Color color) throws NullPointerException, FromToException, NegativeNumberException {
		setDelay(delay);
		setFrom(from);
		setTo(to);
		setColor(color);// TODO Auto-generated constructor stub
		setStyle(LIN_ST_STYLE);
	}
	public String displaySurfaceTime() {
		return
		"Surface Time Properties\n" +
		"Surface Connector #" + surfaceID +" from " + from.getHoleID()+" to " + to.getHoleID() + " is " + delay + " milliseconds.";// you must be finished
	}
	public String toString() {
		return
		displaySurfaceTime();
	}
	public boolean equals(Object duplicateDelay) {
		if (duplicateDelay == null)
			return false;
		if (!(duplicateDelay instanceof SurfaceConnector))
			return false;
		SurfaceConnector delay1 = (SurfaceConnector)duplicateDelay;
		if (this.getDelay() == delay1.getDelay())
			return false;
		if (this.getFrom() == delay1.getFrom())
			return false;
		if (this.getTo() == delay1.getTo())
			return true;
		return false;
	}
	
	public int getSurfaceID(){
		return surfaceID;
	}
	public int getDelay() {
		return delay;
	}

	public Dummy getFrom() {
		return from;
	}
	public int getFromHID(){
		return fromHID;
	}
	
	public Dummy getTo() {
		return to;
	}
	public int getToHID() {
		return toHID;
	}
	/**
	 * @return length
	 */
	public double getLength(){
		return length;
	}
	public int getTime(){
		return time;
	}
	public int getStyle(){
		return style;
	}
	
//	/**
//	 * 
//	 * @param f
//	 * @param t
//	 * @return length
//	 */
//	public double findLength(Dummy f, Dummy t){
//		
//		if ((f != null && t != null)){
//			//	&& ((t.getEasting() != f.getEasting() && t.getNorthing() == f.getNorthing()) || (t.getEasting() == f.getEasting() && t.getNorthing() != f.getNorthing()))
//			xl = t.getEasting() - f.getEasting();//adj length
//			yl = t.getNorthing() - f.getNorthing();//opp length
//			d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
//			try {
//				setLength(d);
//			} catch (NegativeNumberException e) {
//				
//				e.printStackTrace();
//			} catch (ZeroArgumentException e) {
//				
//				e.printStackTrace();
//			}
//		}
//		return d;
//	}
	
	/**
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * 
	 * @param id
	 * @throws ZeroArgumentException
	 * @throws NegativeNumberException
	 */
	public void setSurfaceID(int id) throws ZeroArgumentException, NegativeNumberException{
		if (id == 0)
			throw new ZeroArgumentException("A surface ID number can not be equal to 0. - SurfaceConnector  Class");
		else if (id < 0)
			throw new NegativeNumberException("A surface ID number can not be a negative integer. - SurfaceConnector  Class");
		else
			this.surfaceID = id;
		}
	
	/**
	 * @param delay
	 * @throws NegativeNumberException
	 */
	public void setDelay(int delay) throws NegativeNumberException {
		if (delay < 0)
			throw new NegativeNumberException ("Delay amount can not be negative");
		else
			this.delay = delay;
	}
	/**
	 * 
	 * @param from
	 * @throws NullPointerException
	 * @throws FromToException
	 */
	public void setFrom(Dummy from) throws NullPointerException, FromToException{
		if (from != null){
			this.from = from;
			}
		else 
			throw new NullPointerException ("Ties should start from a Dummy. - SurfaceTime().setFrom(Dummy from)");
		if(from != to){
			this.from = from;
		}
		else
			throw new FromToException ("Ties can not come and go to the same hole");
	}
	/**
	 * 
	 * @param fromHID
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 */
	public void setFromHID(int fromHID) throws NegativeNumberException, ZeroArgumentException{
		if (fromHID < 0){
			throw new NegativeNumberException("Hole IDs can not be negative. - SurfaceConnector().setFromHID"); 
		}
		else if (fromHID == 0){
			throw new ZeroArgumentException("Hole IDs can not be zero. - SurfaceConnector().setFromHID");
		}
		else
			this.fromHID = fromHID;
		
	}
	/**
	 * 
	 * @param toHID
	 * @throws NegativeNumberException
	 * @throws ZeroArgumentException
	 */
	public void setToHID(int toHID) throws NegativeNumberException, ZeroArgumentException{
		if (toHID < 0){
			throw new NegativeNumberException("Hole IDs can not be negative. - SurfaceConnector().setFromHID"); 
		}
		else if (toHID == 0){
			throw new ZeroArgumentException("Hole IDs can not be zero. - SurfaceConnector().setFromHID");
		}
		else
			this.toHID = toHID;
		
	}
	/**
	 * 
	 * @param to
	 * @throws IllegalArgumentException
	 * @throws NullPointerException
	 * @throws FromToException
	 */
	public void setTo(Dummy to) throws IllegalArgumentException, NullPointerException, FromToException{
		if (to != null)
			this.to = to;
		else if(to == getFrom()){
			throw new IllegalArgumentException("Ties cannot be connected to the same Dummy. - SurfaceTime().setTo(Dummy to)");
		}
		else{
			throw new NullPointerException ("Ties should end on a Dummy. - SurfaceTime().setTo(Dummy to)");
		}
		if(to != from){
			this.to = to;
		}
		else
			throw new FromToException ("Ties can not come and go to the same hole \n Hole from : " + getFrom() + " - Hole to : " + getTo());
		
		
	}
	/**
	 * @throws NegativeNumberException, ZeroArgumentException
	 * @param double
	 */
	public void setLength(double length)throws NegativeNumberException, ZeroArgumentException{
		if (length < 0){
			throw new NegativeNumberException("Surface Connectors must have a length. - SurfaceConnector().setLength"); 
		}
		else if (length == 0){
			throw new ZeroArgumentException("Surface Connectors must have a length. - SurfaceConnector().setLength");
			
		}
		else
			this.length = length;
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

	public void setTime(int time)throws NegativeNumberException{
		if (time < 0){
			throw new NegativeNumberException("Time cannot be negative. - SurfaceConnector().setTime(time)"); 
		}
		
		else
			this.time = time;
	}
	public void setStyle(int style)throws NegativeNumberException{
		if (style < 0 || style >= 4){
			throw new IllegalArgumentException("Style is required to be either 0,1,2,3. - SurfaceConnector().setStyle(style)"); 
		}
		else
			this.style = style;
	}
	
	
	
	
	public Polygon2D getBounds(){
		//DECLARE THE VARIABLES TO USE FOR LOCATION ASSIGNMENTS OF "FROM HOLE" AND "TO HOLE"
		double fromX,fromY,toX,toY;
		//DECLARE AND ASSIGN THE NUMBER OF POINTS OF THE POLYGON
		int i = 4;
		//FIND THE DISTANCE OF BETWEEN TWO HOLES
		xl = (getTo().getX() - getFrom().getX());//adj length
		yl = (getTo().getY() - getFrom().getY());//opp length
		d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
		
		//USE THE HOLE DIAMETER OF THE "TO" HOLE FOR WIDTH IF IT IS A HOLE 
		if (getTo() instanceof Hole){w = (double) ((Hole) getTo()).getDiameter()/1000;}
		//USE THE AVERAGE HOLE DIAMETER IF IT IS A DUMMY HOLE AS DUMMYS HAVE NO DIAMETER
		else if(getTo() instanceof Dummy){	w = LiteTieTRIAL.averageSize/2/1000;}
		
		//ASSIGN LOCATIONS OF THE FROM AND TO HOLES
		fromX	= getFrom().getX() ;
		toX		= getTo().getX();
		fromY 	= getFrom().getY();
		toY 	= getTo().getY();
		
		//AMOUNT TO BE SUBTRACTED OFF THE DISTANCE
		double adjustX = xl*0.2;
		double adjustY = yl*0.2;
		
		//RATIO TO INDREASE OR DECREASE THE BOUNDS OF THE SURFACE CONNECTOR
		double ratio = 1; 
		
		//DETERMINE BEARING IN RADIANS
		//ROTATION FOR LOWER RIGHT QUADRANT OF THE UNIT CIRCLE
		if(yl < 0 && xl >= 0){
			b = (Math.acos((yl/d)));
			fromX = fromX + adjustX;
			fromY = fromY + adjustY;
//			toX = toX - adjustX;
//			toY = toY - adjustY;		
		}
		//ROTATION FOR LOWER LEFT QUADRANT OF THE UNIT CIRCLE
		else if(yl< 0 && xl <=0){
			b = (Math.asin(-(xl/d)))+ Math.toRadians(180);
			fromX = fromX + adjustX;
			fromY = fromY + adjustY;
//			toX = toX - adjustX;
//			toY = toY - adjustY;
		}
		//ROTATION FOR UPPER LEFT QUADRANT OF THE UNIT CIRCLE
		else if (xl <0 && yl >= 0){
			b = (Math.acos(-(yl/d)))+ Math.toRadians(180);
			fromX = fromX + adjustX;
			fromY = fromY + adjustY;
//			toX = toX - adjustX;
//			toY = toY - adjustY;
		}//upper left GOOD
		//ROTATION FOR UPPER RIGHT QUADRANT OF THE UNIT CIRCLE
		else if(yl >= 0 && xl >= 0){
			b = (Math.asin(xl/d));
			fromX = fromX + adjustX;
			fromY = fromY + adjustY;
//			toX = toX - adjustX;
//			toY = toY - adjustY;
			}
		//IF EVERYTHING GOES WRONG MAKE IT ZREO
		else b= 0;
		//CONVERT FROM RADIENS TO DEGREES FOR EASE OF USE ONLY
		double a = Math.toDegrees(b);
				
		//DECLARE VARIABLES TO BE ROTATED
		double x1,x2,x3,x4,y1,y2,y3,y4;
		
		//ROTATE THE OFFSETS FROM THE LINE
		x1 = (   ((-w*ratio)*Math.cos(Math.toRadians(90-a+90)))); 
		y1 = (   w*ratio* Math.sin(Math.toRadians(90-a-90))) ;
		x2 = (   ((w*ratio)*Math.cos(Math.toRadians(90-a+90))) );
		y2 = (   w*ratio*Math.sin(Math.toRadians(90-a-90)))   ;
		x3 = (   ((w*ratio)*Math.cos(Math.toRadians(90-a+90)))  );
		y3 = (   -w*ratio*Math.sin(Math.toRadians(90-a-90))) ;
		x4 = (   ((-w*ratio)*Math.cos(Math.toRadians(90-a+90)))  );
		y4 = (   -w*ratio*Math.sin(Math.toRadians(90-a-90)))  ;
//TRIANGULAR BOUNDS
		//DECLARE AND ASSIGN THE DOUBLE ARRAY FOR BOUNDING AREA
//		double [] xs = { fromX,	 (x3*2.5)+toX-(adjustX/2), toX+(adjustX/2),(x4*2.5)+toX-(adjustX/2)};
//		double [] ys = { fromY,	 toY-(adjustY/2)+(y3*2.5), toY+(adjustY/2), toY-(adjustY/2)-(y4*2.5)};
//SQUARE BOUNDS		
		double [] xs = { x1+fromX,	x2+fromX, x3+toX, x4+toX};
		double [] ys = { y1+fromY,	fromY-y2, toY+y3, toY-y4};
		//RETURN THE BOUNDING AREA
		return new Polygon2D(xs,ys,i);
	}

	
	
}
