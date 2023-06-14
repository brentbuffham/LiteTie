package litetie.model;

public class UnitConvert{
	
	// Class used to convert all values to a pixel amount.
	// Also used to convert from metric to imperial formats.
	
	//Metric Constants
	public static final double KILOMETER_PIXELS = 1000000;					//this is == 1000000 px
	public static final double METER_PIXELS = KILOMETER_PIXELS/1000;		//this is == 1000 px
	public static final double CENTIMETER_PIXELS = METER_PIXELS/100; 		//this is == 10 px
	public static final double MILLIMETER_PIXELS = CENTIMETER_PIXELS/10;  	//this is == 1 px
	
	
	//"If 1000000 pixel at (zero zoom) equals 1 kilometer then 1609334 pixels equals 1 mile"
	//all other constants are based on the above statement.  Metric is the Kilometer, Imperial is the Mile.
	
	//in terms of the computer Screen, PIXEL is the universal measurement. 
	
	
	//Imperial Constants
	public static final double MILE_PIXELS = 1609344;  						//this is == 1609344 px
	public static final double YARD_PIXELS = MILE_PIXELS/1760;				//this is == 914.4 px
	public static final double FOOT_PIXELS = YARD_PIXELS/3;					//this is == 304.8 px
	public static final double INCH_PIXELS = FOOT_PIXELS/12;				//this is == 25.4 px
	
	//pixel Constants metric
	public static final double PIXELS_KILOMETER = 0.000001;
	public static final double PIXELS_METER = PIXELS_KILOMETER/0.001;
	public static final double PIXELS_CENTIMETER = PIXELS_METER/0.01;
	public static final double PIXELS_MILLIMETER = PIXELS_CENTIMETER/0.1;
	//pixel constants imperial
	public static final double PIXELS_MILE = 0.00000062;
	public static final double PIXELS_YARD = PIXELS_MILE*1760;
	public static final double PIXELS_FOOT = PIXELS_YARD*3;
	public static final double PIXELS_INCH = PIXELS_FOOT*12;
	
	
	
	private static double millimeterToKilometers(double mm) {
		return 
			mm/1000000;
	}
	private static double millimeterToMeters(double mm) {
		return 
			mm/1000;
	}
	private static double millimeterToCentimeters(double mm) {
		return 
			mm/10;
	}
	private static double centimeterToKilometers(double cm) {
		return 
			cm/100000;
	}
	private static double centimeterToMeters(double cm) {
		return 
			cm/100;
	}
//Metric to Pixel Methods	
	public static double kilometersToPixels(double kms) {
		return 
			kms*KILOMETER_PIXELS;
	}
	public static double metersToPixels(double m) {
		return 
			m*METER_PIXELS;
	}
	public static double centimetersToPixels(double cms) {
		return 
			cms*CENTIMETER_PIXELS;
	}
	public static double millimetersToPixels(double mms) {
		return 
			mms*MILLIMETER_PIXELS;
	}
//Imperial to Pixel Methods	
	public static double milesToPixels(double ml) {
		return 
			ml*MILE_PIXELS;
	}
	public static double yardToPixels(double yrd) {
		return 
			yrd*YARD_PIXELS;
	}
	public static double feetToPixels(double ft) {
		return 
			ft*FOOT_PIXELS;
	}
	public static double inchesToPixels(double in) {
		return 
			in*INCH_PIXELS;
	}
//PIXEL To Metric Methods
	public static double pixelsToKilometers(double px) {
		return
			px*PIXELS_KILOMETER;
	}
	public static double pixelsToMeters(double px) {
		return
			px*PIXELS_METER;
	}
	public static double pixelsToCentimeters(double px) {
		return
			px*PIXELS_CENTIMETER;
	}
	public static double pixelsToMillimeters(double px) {
		return
			px*PIXELS_MILLIMETER;
	}
//PIXEL to Imperial Methods///////////////////////////////////fix the math dope.
	public static double pixelsToMile(double px) {
		return
			px/PIXELS_MILE;
	}
	public static double pixelsToYards(double px) {
		return
			px/PIXELS_YARD;
	}
	public static double pixelsToFeet(double px) {
		return
			px/PIXELS_FOOT;
	}
	public static double pixelsToInches(double px) {
		return
			px/PIXELS_INCH;
	}
	
	public static double milesToKilometers(double miles) {
		return
			pixelsToKilometers(milesToPixels(miles));
	}
	public static double yardsToMeters(double yards) {
		return
			pixelsToMeters(yardToPixels(yards));
	}
	public static double feetToCentimeters(double feet) {
		return
			pixelsToCentimeters(feetToPixels(feet));
	}
	public static double inchesToMillimeters(double inches) {
		return
			pixelsToMillimeters(inchesToPixels(inches)); 
		
	}
	
	public static void main(String [] args) {
		double a = 10, b = 100, c = 1000;
		double d = 1, e = 10;
		;
		
		double m1 = pixelsToMeters(a), m2 = pixelsToMeters(b),m3 = pixelsToMeters(c);
		double px1 = metersToPixels(m1),px2 = metersToPixels(m2),px3 = metersToPixels(m3);

		double mm1 = pixelsToMillimeters(d), mm2 = pixelsToMillimeters(e);
		
		
		System.out.println("a = 10 it also = " + m1 + "meters, b = 100 it also = " + m2 + "meters, c = 1000 it also = " + m3 + "meters");
		System.out.println("a = " + px1 + ", b = " + px2 + ", c = " + px3);
		System.out.println("d = " + mm1 + ", e = " + mm2);
		System.out.println("d = " + mm1 + "mm, also = " + millimeterToMeters(mm1) + "meters, e = " + mm2 + "mm, also " + millimeterToMeters(mm2) + "meters");
	}
	
}
