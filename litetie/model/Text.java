package litetie.model;
/**
 * @author brentbuffham
 * @description Text 
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.UIManager;

import litetie.LiteTieTRIAL;
import litetie.view.Zoom;


public class Text extends Coordinate {
	
	protected int textID;
	protected double bearing;
	protected String text;
	protected Color color;
	protected Font font;

	
	public Text( int pointID, double x, double y, double z, double bearing, String text,Font font, Color color) throws ZeroArgumentException, NegativeNumberException {
		super(x, y, z);
		setTextID(pointID);
		setText(text);
		setFont(font);
		setColor(color);
		setBearing(bearing);
	}

	public Text(double x, double y, double z, double bearing, String text,Font font, Color color) throws ZeroArgumentException, NegativeNumberException {
			super(x, y, z);
			setText(text);
			setFont(font);
			setColor(color);
			setBearing(bearing);
	}

	
	public String toString(){
		System.out.println(super.toString());
		return displayPointLocation();
	}
	
	
	public String displayPointLocation() {

		return
				"Point Location" 
				+"\nID number - \t" + (textID)
				+"\nX - \t" + (x)
				+"\nY - \t" + (y)
				+"\nZ - \t" + (z)
				+"\nBearing - \t" + (bearing)
				+"\nFont - \t" + (font)
				+"\nColour - \t" + (color)
				+"\nText - \t" + (text);
		
	}
	/**
	 * @return The hole Identification Number
	 */
	public int getTextID(){
		return textID;
	}
	public double getBearing(){
		return bearing;
	}
	public String getText(){ 
		return text;	
	}
	public Font getFont(){
		return font;
	}
	
	public void setColor(Color color) {
		this.color = color;
		
	}
	public Color getColor(){
		return color;
	}
	
	//FIXME bounds not accurate
	public Rectangle2D getBounds(){
		String s = this.getText();
		Canvas c = new Canvas();
		FontMetrics fm = c.getFontMetrics(font);
		double length = fm.stringWidth(s);
		double height = fm.getHeight();
//		return new Rectangle2D.Double(getX(), getY(), length, height);
		
		return new Rectangle2D.Double(getX(), getY(), length*Zoom.scalingFactor, height*Zoom.scalingFactor);
	}
	
	public void setTextID(int textID) throws ZeroArgumentException, NegativeNumberException{
		if (textID == 0)
			throw new ZeroArgumentException("A text ID number can not be equal to 0. - Text Class");
		else if (textID < 0)
			throw new NegativeNumberException("A text ID number can not be a negative integer. - Text Class");
		else
			this.textID = textID;
		}
	public void setText (String text) throws NullPointerException, IllegalArgumentException{
		if (text != null)
			this.text = text;				
		else
			throw new IllegalArgumentException("Text needs to be something else besides null.");
			}
	public void setBearing(double bearing) throws IllegalArgumentException {
		if (bearing <= -720 || bearing >= 720)
			throw new IllegalArgumentException ("Value outside range!");
		else
			this.bearing = bearing;
		}
	public void setFont(Font font){
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ArrayList<Font> fonts = new ArrayList<Font>(Arrays.asList(e.getAllFonts())); // Get the fonts
		if (fonts.contains(font.getFontName() )){
			this.font = font;
		}
		else
			this.font = font;
	}
	/**
	 * Sets all the Text and attributes.
	 * @param text
	 * @param fontMetrics
	 * @param bearing
	 * @param color
	 */
	public void setTextAndAttributes(String text, Font font, double bearing, Color color){
		setText(text);
		setFont(font);
		setBearing(bearing);
		setColor(color);
	}

}
