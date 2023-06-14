package litetie.view.drawing;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

//import org.apache.commons.math3.analysis.MultivariateFunction;
//import org.apache.commons.math3.analysis.UnivariateFunction;
//import org.apache.commons.math3.analysis.interpolation.BicubicSplineInterpolator;
//import org.apache.commons.math3.analysis.interpolation.DividedDifferenceInterpolator;
//import org.apache.commons.math3.analysis.interpolation.MicrosphereInterpolator;
//import org.apache.commons.math3.analysis.interpolation.MultivariateInterpolator;
//import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
//import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberAxis;
//import org.jfree.chart.plot.Plot;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.renderer.GrayPaintScale;
//import org.jfree.chart.renderer.LookupPaintScale;
//import org.jfree.chart.renderer.PaintScale;
//import org.jfree.chart.renderer.xy.XYBlockRenderer;
//import org.jfree.data.DomainOrder;
//import org.jfree.data.general.DatasetChangeListener;
//import org.jfree.data.general.DatasetGroup;
//import org.jfree.data.time.TimeSeriesCollection;
//import org.jfree.data.xy.DefaultXYDataset;
//import org.jfree.data.xy.DefaultXYZDataset;
//import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import JSci.awt.ContourPlot;


import litetie.LiteTieTRIAL;
import litetie.controller.Transform;
import litetie.graph.Contour;
import litetie.graph.DataSet;
import litetie.graph.MarchingSquares;
import litetie.model.BPoint;
import litetie.model.Boundary;
import litetie.model.Coordinate;
import litetie.model.Dummy;
import litetie.model.Hole;
import litetie.model.InitiationPoint;
import litetie.model.LTPoint3D;
import litetie.model.Pattern;
import litetie.model.Polygon2D;
import litetie.model.Polyline2D;
import litetie.model.SurfaceConnector;
import litetie.model.Text;
import litetie.model.UnitConvert;
import litetie.model.World;
import litetie.view.DialogPreferences;
import litetie.view.Zoom;

public class Visualise2D {

	DecimalFormat dec2P = new DecimalFormat("#0.00");
	DecimalFormat dec1P = new DecimalFormat("#0.0");
	DecimalFormat dec0P = new DecimalFormat("#0");

	final static float DASH_FLOAT_008[] = {8.0f};
	final static float DASH_FLOAT_002[] = {2.0f};
	final static float DASH_FLOAT_016[] = {16.0f};
	final static float DASH_THICK[] = {4.0f};

	final int PX_SIZE_1 = 1;
	final int PX_SIZE_2 = 2;
	final int PX_SIZE_3 = 3;
	
	private static Font default_FONT = new Font("Dialog",Font.PLAIN,12);
	private static Font grid_FONT = new Font("Dialog",Font.PLAIN,12);
	private static Font ruler_FONT = new Font("Dialog",Font.PLAIN,10);
	
	final static BasicStroke DASHED_LINE2_THICK= new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_002, 0.0f);

	final static BasicStroke DASHED_LINE3 = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_016, 0.0f);
	final static BasicStroke DASHED_LINE_THICK_2 = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_THICK, 0.0f);	
	final static BasicStroke NOT_DASHED_LINE = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	
	final static BasicStroke LINE_025 = new BasicStroke(0.25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_050 = new BasicStroke(0.50f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_075 = new BasicStroke(0.75f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_100 = new BasicStroke(1.00f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_150 = new BasicStroke(1.50f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_200 = new BasicStroke(2.00f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_225 = new BasicStroke(2.25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_250 = new BasicStroke(2.50f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_275 = new BasicStroke(2.75f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_300 = new BasicStroke(3.00f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_325 = new BasicStroke(3.25f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_350 = new BasicStroke(3.50f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_375 = new BasicStroke(3.75f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	final static BasicStroke LINE_400 = new BasicStroke(4.00f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
	
	
	final static BasicStroke DASH_050_002 = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_002, 0.0f);
	final static BasicStroke DASH_100_002 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_002, 0.0f);
	final static BasicStroke DASH_050_008 = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_008, 0.0f);
	final static BasicStroke DASH_100_008 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_008, 0.0f);
	final static BasicStroke DASH_200_008 = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_008, 0.0f);
	final static BasicStroke DASH_400_008 = new BasicStroke(4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASH_FLOAT_008, 0.0f);
	
	public static Color p_holeTextColour,p_holeLengthTextColour,p_labelOneTextColour,p_labelTwoTextColour,p_labelThreeTextColour,
	p_benchHeightTextColour,p_subdrillTextColour,p_holeDiameterTextColour, p_holeBearingTextColour,
	p_collarLocationTextColour,p_floorLocationTextColour,p_toeLocationTextColour,p_holeAngleTextColour,
	p_backgroundCanvasColour,p_selectedObjectsColour,p_gridLinesAndTextColour,p_floorLocationFillColour,
	p_floorLocationLineColour,p_toeLocationFillColour,p_toeLocationLineColour,p_detonatorDelayTextColour,
	p_downHoleFiringTimesColour,p_surfaceConnectorFiringTimesColour;
	
	public static String p_defaultFontName;
	public static int  p_defaultFontSize;
	public static boolean p_applyFontScaling;
	
	public static void getPreferredColours() {
		DialogPreferences defaults = new DialogPreferences();
		Object[] preferences = defaults.readPreferences();
		int i = 56;

		p_holeTextColour = (Color) preferences[i++];//56
		p_holeLengthTextColour = (Color) preferences[i++];
		p_labelOneTextColour = (Color) preferences[i++];
		p_labelTwoTextColour = (Color) preferences[i++];
		p_labelThreeTextColour = (Color) preferences[i++];//60
		p_benchHeightTextColour = (Color) preferences[i++];
		p_subdrillTextColour = (Color) preferences[i++];
		p_holeDiameterTextColour = (Color) preferences[i++];
		p_holeBearingTextColour = (Color) preferences[i++];
		p_collarLocationTextColour = (Color) preferences[i++];//65
		p_floorLocationTextColour = (Color) preferences[i++];
		p_toeLocationTextColour = (Color) preferences[i++];
		p_holeAngleTextColour = (Color) preferences[i++];
		p_backgroundCanvasColour = (Color) preferences[i++];
		p_selectedObjectsColour = (Color) preferences[i++];//70
		p_gridLinesAndTextColour = (Color) preferences[i++];
		p_floorLocationFillColour = (Color) preferences[i++];
		p_floorLocationLineColour = (Color) preferences[i++];
		p_toeLocationFillColour = (Color) preferences[i++];
		p_toeLocationLineColour = (Color) preferences[i++];//75
		p_detonatorDelayTextColour = (Color) preferences[i++];
		p_downHoleFiringTimesColour = (Color) preferences[i++];
		p_surfaceConnectorFiringTimesColour = (Color) preferences[i++];//78
	}
	
	public static void getPreferredFont() {
		DialogPreferences defaults = new DialogPreferences();
		Object[] preferences = defaults.readPreferences();
		int i = 79;
		p_defaultFontName = (String) preferences[i++];//79
		p_defaultFontSize = Integer.parseInt(preferences[i++].toString());//80
		p_applyFontScaling = Boolean.parseBoolean(preferences[i++].toString());
		int style = Font.PLAIN;
		if(p_applyFontScaling == false){
				default_FONT = new Font(p_defaultFontName, style , p_defaultFontSize);
		}
		else{
			default_FONT = new Font(p_defaultFontName, style , (int) (p_defaultFontSize*(Zoom.getScalingFactor()*50)));
//			default_FONT.deriveFont((float) (p_defaultFontSize*Zoom.getScalingFactor()));
//			LiteTieTRIAL.setConsoleOutput("\n"+default_FONT.getName() +" size:"+default_FONT.getSize());
		}
	}
	
	
	Color defaultColour = Color.white;
	Color preferenceOppositeColor = getInverseColour(defaultColour);
	Color T_GRAY = new Color(150,150,150,100);
	Color LIGHT_PINK = new Color(255,230,240,80);
	Color MAGENTA_HALF_T = new Color(255,230,240,80);
	Color AMETHYST = new Color(153,102,204);
	Color MEDIUM_PURPLE = new Color(147,112,219);
	Color DARK_SLATE_GRAY = new Color(47,79,79);
	Color SLATE_BLUE = new Color(106,90,205);
	Color MEDIUM_SLATE_BLUE = new Color(123,104,238);
	Color LIGHT_SEA_GREEN = new Color(32,178,170);
	Color DARK_CYAN = new Color(0,139,139);
	Color DARK_ORANGE = new Color(241,71,0);
	Color CADET_BLUE = new Color(95,158,160);
	Color CADET_BLUE_T = new Color(95,158,160,50);
	
	public static Color getInverseColour(Color colour){
		int max = 255;
		int currentRed = colour.getRed();
		int currentGreen = colour.getGreen();
		int currentBlue = colour.getBlue();
		int currentAlpha = colour.getAlpha();
		colour = new Color(max-currentRed, max-currentGreen, max-currentBlue, currentAlpha);
		return colour;	
	}
	public void paintMouseInfluence(Graphics2D g2, Point2D mouseLoc, double radius, Rectangle2D bounds, double scale){
		double hx = (mouseLoc.getX() - bounds.getX() - radius) *scale;
		double hy = (bounds.getHeight() + bounds.getY() - mouseLoc.getY() - radius)*scale;
		
		double y = (bounds.getHeight() + bounds.getY() - mouseLoc.getY())*scale;
		double x = (mouseLoc.getX() - bounds.getX()) *scale;
//		double hx =  (((mouseLoc.getX() - UnitConvert.pixelsToMeters(5) - bounds.getX()))) * Zoom.getScalingFactor();
//		double hy =  (((bounds.getHeight()+ UnitConvert.pixelsToMeters(5) + bounds.getY() - mouseLoc.getY()) )) * Zoom.getScalingFactor() ;
		double hw =  (radius*2) * scale;
		double hh =  (radius*2) * scale;
		
		double p10 = hw * 0.2;
	
		g2.setStroke(LINE_050);
		g2.setColor(Color.BLACK);
		g2.draw(new Ellipse2D.Double(hx+p10,hy+p10,hw-(2*p10),hh-(2*p10)));
		g2.setColor( p_gridLinesAndTextColour);
		g2.draw(new Ellipse2D.Double(hx,hy,hw,hh));
	}
	public void paintTemporaryPoly(Graphics2D g2, double[] xs, double []ys, Rectangle2D bounds, double scale, boolean buttonIn){

		double x1 = (xs[0] - bounds.getX()) *scale;
		double x2 = (xs[1] - bounds.getX()) *scale;
		double x3 = (xs[2] - bounds.getX()) *scale;
		double x4 = (xs[3] - bounds.getX()) *scale;
		
		double y1 = (bounds.getHeight() + bounds.getY() - ys[0])*scale;
		double y2 = (bounds.getHeight() + bounds.getY() - ys[1])*scale;
		double y3 = (bounds.getHeight() + bounds.getY() - ys[2])*scale;
		double y4 = (bounds.getHeight() + bounds.getY() - ys[3])*scale;

		double [] xA = {x1,x2,x3,x4};
		double [] yA = {y1,y2,y3,y4};

		g2.setColor(p_selectedObjectsColour);
		g2.setStroke(DASH_050_002);
		g2.draw(new Polygon2D(xA,yA,4));
	}
	
	public void paintTemporaryLine(Graphics2D g2, LTPoint3D tempPoint1, LTPoint3D tempPoint2, Rectangle2D bounds, double scale, boolean buttonIn){

		double x = (tempPoint1.getX() - bounds.getX()) *scale;
		double y = (bounds.getHeight() + bounds.getY() - tempPoint1.getY())*scale;
		double x2 = (tempPoint2.getX() - bounds.getX()) *scale;
		double y2 = (bounds.getHeight() + bounds.getY() - tempPoint2.getY())*scale;

		g2.setColor(p_selectedObjectsColour);
		g2.setStroke(LINE_100);
		g2.draw(new Line2D.Double(x,y,x2,y2));
	}
	
	/*
	 * paintHole(Graphics hole2D) used to draw each hole provided by the holeList in Pattern.class
	 * 
	 * Draws a basic drill Hole (circle).
	 * The size is determined by the Hole Diameter provided by the holeList
	 * Further controls are to be provided by a percentage control that affects the Hole Diameter Variable
	 */	
	//DUMMY DRAWING	
	public void paintDummy(Graphics2D dummy2D, Dummy dummy, double lastEasting, double lastNorthing, Rectangle2D bounds, double averageHoleSize, double scale, boolean selected, boolean buttonIn, double enhance){
		// Draws a cross the size of the average holes in the pattern
		double [] xs1 = {(((dummy.getX() - bounds.getX() - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),(((dummy.getX() - bounds.getX() + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};
		double [] ys1 = {(((bounds.getHeight() + bounds.getY() - dummy.getY() + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),	(((bounds.getHeight() + bounds.getY() - dummy.getY() - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};	
		double [] xs2 = {(((dummy.getX() - bounds.getX() + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),(((dummy.getX() - bounds.getX() - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};
		double [] ys2 = {(((bounds.getHeight() + bounds.getY() - dummy.getY() + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),(((bounds.getHeight() + bounds.getY() - dummy.getY() - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};
		
		double [] xsl1 = {(((lastEasting - bounds.getX() - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),(((lastEasting - bounds.getX() + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};
		double [] ysl1 = {(((bounds.getHeight() + bounds.getY() - lastNorthing + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),	(((bounds.getHeight() + bounds.getY() - lastNorthing - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};	
		double [] xsl2 = {(((lastEasting - bounds.getX() + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),(((lastEasting - bounds.getX() - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};
		double [] ysl2 = {(((bounds.getHeight() + bounds.getY() - lastNorthing + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale),(((bounds.getHeight() + bounds.getY() - lastNorthing - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/2))*scale)};
		
		
		if (selected == true){
			enhance = enhance*2;
		}
//		dummy2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (!(selected)) {
			dummy2D.setColor(p_holeTextColour);
			dummy2D.setStroke(LINE_075);
			dummy2D.draw(new Polyline2D(xs1,ys1,2));		
			dummy2D.draw(new Polyline2D(xs2,ys2,2));
		}

		else if (!buttonIn && selected) {
			dummy2D.setColor(p_selectedObjectsColour);
			dummy2D.setStroke(LINE_300);
			dummy2D.draw(new Polyline2D(xs1,ys1,2));		
			dummy2D.draw(new Polyline2D(xs2,ys2,2));
		}

		else if (buttonIn && selected) {
			dummy2D.setColor(p_selectedObjectsColour);
			dummy2D.setStroke(LINE_300);

			double xl = (dummy.getX() - lastEasting);//adj length
			double yl = (dummy.getY() - lastNorthing);//opp length
			double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
			//Polyline Points
			
			
			if (dummy instanceof Dummy) {
				dummy2D.drawString("   \u25C0 " + dec1P.format(d)+"m",	(int) (((dummy.getX() - bounds.getX())) * scale),
						(int) (((bounds.getHeight() + bounds.getY() - dummy.getY()) - (UnitConvert.pixelsToMeters(averageHoleSize*enhance * 1.1)) / 2) * scale));
				dummy2D.setColor(p_selectedObjectsColour);
				dummy2D.setStroke(DASH_050_008);
				dummy2D.drawLine((int) (((dummy.getX() - bounds.getX())) * scale), 
						(int) ((bounds.getHeight() + bounds.getY() - dummy.getY())* scale),
						(int) (((lastEasting - bounds.getX())) * scale),
						(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
				dummy2D.setStroke(LINE_075);
				dummy2D.setColor(getInverseColour(p_selectedObjectsColour));
				dummy2D.draw(new Polyline2D(xsl1,ysl1,2));		
				dummy2D.draw(new Polyline2D(xsl2,ysl2,2));
			}
			dummy2D.setColor(p_selectedObjectsColour);
			dummy2D.setStroke(LINE_300);
			dummy2D.draw(new Polyline2D(xs1,ys1,2));		
			dummy2D.draw(new Polyline2D(xs2,ys2,2));
		}




	}
	//HOLE DRAWING
	public void paintHole(Graphics2D g2, Hole hole, double lastEasting, double lastNorthing, double mouseE, double mouseN, Rectangle2D bounds, double scale, double enhance,boolean selected, boolean moveToolSelected, int shape, double enhanceTie) {
		
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g2.setRenderingHint( RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		//int Shape means
		/* 0 = image
		 * 1 = circle
		 * 2 = cross
		 * 3 = Triangle
		 * 4 = square
		 * 5 = pentagon
		 * 6 = hexagon
		 * 7 = star
		 * 8 = filled circle
		 * 9 = filled triangle
		 * 10 = filled square
		 */
		
		double centX1 = ((hole.getX() - bounds.getX()) * scale);
		double centY1 =  ((bounds.getHeight() + bounds.getY() - hole.getY()) * scale);
		double hx =  (((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale);
		double hy =  (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale);
		double hw =  (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale);
		double hh =  (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale);
		double mLocX =  (((lastEasting - bounds.getX())) * scale);
		double mLocY =  (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale);
		double ovalX2 =  (((lastEasting - bounds.getX())- (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale);
		double ovalY2 = (((bounds.getHeight() + bounds.getY() - lastNorthing) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale);
		if (selected == true){
			enhance = enhance*1.25;
		}
		double xl = (hole.getX() - lastEasting);//adj length
		double yl = (hole.getY() - lastNorthing);//opp length
		double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
/**
 * 		NOT FILLED CIRCLE SHAPE
 */
		if(shape == 1){

				if (!(selected)) {
					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.draw(new Ellipse2D.Double(hx,hy,hw,hh));
				}
				else if (!moveToolSelected && selected){
					g2.setStroke(NOT_DASHED_LINE);
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Ellipse2D.Double(hx,hy,hw,hh));
				}
				else if (moveToolSelected && selected){
					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
//SET THE STROKE OF THE LINE TO A DASH						
						g2.setStroke(DASH_050_008);
//WRITE THE DISTANCE FROM THE ORIGINAL LOCATION NEAR THE HOLE						
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
//DRAWS A LINE 
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
						
					}

					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					
					g2.draw(new Ellipse2D.Double(hx,hy,hw,hh));		
				}
	
		}
		//1 = Circle Ends
		//CROSS STARTS
		if(shape == 2){
			
			double [] xs1 = {(((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
					(((hole.getX() - bounds.getX()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale)};
			double [] xs2 = {(((hole.getX() - bounds.getX()) * scale)),
					(((hole.getX() - bounds.getX()) * scale))};
			double [] ys1 = {(((bounds.getHeight() + bounds.getY() - hole.getY()) * scale)),
					(((bounds.getHeight() + bounds.getY() - hole.getY()) * scale))};
			double [] ys2 = {(((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
					(((bounds.getHeight() + bounds.getY() - hole.getY()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale)};
			
				if (!(selected)) {
					g2.setStroke(LINE_100);
					g2.setColor(hole.getColor());
					g2.draw(new Polyline2D(xs1,ys1,2));
					g2.draw(new Polyline2D(xs2,ys2,2));
				}
				else if (!moveToolSelected && selected){
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polyline2D(xs1,ys1,2));
					g2.draw(new Polyline2D(xs2,ys2,2));
				}
				else if (moveToolSelected && selected){
					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
					}
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polyline2D(xs1,ys1,2));
					g2.draw(new Polyline2D(xs2,ys2,2));
					}
		}
		//2 = Cross Ends

		//Triangle STARTS
		if(shape == 3){
	
				double [] xs = {(((hole.getX() - bounds.getX()) * scale)), 
						(((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
						(((hole.getX() - bounds.getX()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale)
				};

				double [] ys = {(((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), 
						(((bounds.getHeight() + bounds.getY() - hole.getY()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
						(((bounds.getHeight() + bounds.getY() - hole.getY()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale)
				};
				int n = 3;

				if (!(selected)) {
					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.draw(new Polygon2D(xs,ys,n));
				}
				else if (!moveToolSelected && selected){
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(xs,ys,n));
				}
				else if (moveToolSelected && selected){

					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
					}
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(xs,ys,n));
				}
			}

		//3 = Triangle Ends

		//SQUARE SHAPE
		if(shape == 4){
			
				if (!(selected)) {

					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.draw(new Rectangle2D.Double(
							(((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), //  X location - eastings is in mE
							(((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), //  Y location - northings is in mN - also is reversed
							(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale), //  size in mE - WIDTH of hole - convert these from px to millimeters to meters i.e. 10px = 10mm = 0.01m
							(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale))); // size in mN - HEIGHT of hole - as above for convert!!!

				}
				else if (!moveToolSelected && selected){
					g2.setStroke(LINE_075);
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Rectangle2D.Double(
							(((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), //  X location - eastings is in mE
							(((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), //  Y location - northings is in mN - also is reversed
							(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale), //  size in mE - WIDTH of hole - convert these from px to millimeters to meters i.e. 10px = 10mm = 0.01m
							(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale))); // size in mN - HEIGHT of hole - as above for convert!!!

				}


				else if (moveToolSelected && selected){

					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));

					}
					g2.setStroke(LINE_075);
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Rectangle2D.Double(
							(((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), //  X location - eastings is in mE
							(((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), //  Y location - northings is in mN - also is reversed
							(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale), //  size in mE - WIDTH of hole - convert these from px to millimeters to meters i.e. 10px = 10mm = 0.01m
							(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale))); // size in mN - HEIGHT of hole - as above for convert!!!

				}
			
		}
		//4 = Square Ends
		//PENTAGON STARTS
		if(shape == 5){

			int n = 5; 
			double r = 1; 		
			double[]x = new double[n];
			double[]y = new double[n];
			double angle = Math.toRadians(360/n);
			//	                double angle= 2*Math.PI/n;
			//	                double dv = 5*2*Math.PI/360;
			double turn = -18;
			double xCenter = hole.getX()-bounds.getX();
			double yCenter = (bounds.getHeight() + bounds.getY() - hole.getY());

				r = ((UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2);
					for(int i=0; i<n; i++) {
						double v = (i*angle) - Math.toRadians(turn);
						x[i] = ((xCenter + r*Math.cos(v))*scale);
						y[i] = ((yCenter - r*Math.sin(v))*scale);
					}
				if (!(selected)) {
					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.draw(new Polygon2D(x,y,n));
				}
				else if (!moveToolSelected && selected){
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(x,y,n));
				}
				else if (moveToolSelected && selected){
					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
					}
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(x,y,n));
				}
		}
		//5 = Pentagon Ends
		//HEXAGON STARTS
		if(shape == 6){

			int n = 6; 
			double r = 1; 		
			double[]x = new double[n];
			double[]y = new double[n];
			double angle = Math.toRadians(360/n);
			//	                double angle= 2*Math.PI/n;
			//	                double dv = 5*2*Math.PI/360;
			double turn = 30;
			double xCenter = hole.getX()-bounds.getX();
			double yCenter = (bounds.getHeight() + bounds.getY() - hole.getY());
				r = ((UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2);
					for(int i=0; i<n; i++) {
						double v = (i*angle) - Math.toRadians(turn);
						x[i] = ((xCenter + r*Math.cos(v))*scale);
						y[i] = ((yCenter - r*Math.sin(v))*scale);
					}
				if (!(selected)) {
					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.draw(new Polygon2D(x,y,n));
				}
				else if (!moveToolSelected && selected){
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(x,y,n));
				}
				else if (moveToolSelected && selected){
					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
					}
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(x,y,n));
				}
			
		}
		//6 -Hexagon Ends
		//STAR STARTS
		if(shape == 7){
			int n = 14; 
			double r = 1; 		
			double[]x = new double[n];
			double[]y = new double[n];
			double angle = Math.toRadians(360/n);
			//	                double angle= 2*Math.PI/n;
			//	                double dv = 5*2*Math.PI/360;
			double turn = 85;
			double xCenter = hole.getX()-bounds.getX();
			double yCenter = (bounds.getHeight() + bounds.getY() - hole.getY());
			
				r = ((UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2);
				for(int i=0; i<n; i++) {
					if(i%2 == 0){
						double v = (i*angle) - Math.toRadians(turn);
						x[i] =  ((xCenter + (r/4)*Math.cos(v))*scale);
						y[i] =  ((yCenter - (r/4)*Math.sin(v))*scale);
					}
					else{
						double v = (i*angle) - Math.toRadians(turn);
						x[i] =  ((xCenter + r*Math.cos(v))*scale);
						y[i] = ((yCenter - r*Math.sin(v))*scale);
					}	
				}

				if (!(selected)) {
					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.draw(new Polygon2D(x,y,n));
				}
				else if (!moveToolSelected && selected){
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(x,y,n));
				}
				else if (moveToolSelected && selected){
					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));

					}
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.draw(new Polygon2D(x,y,n));
				}
			
		}
		//7 -Star Ends
		//CIRCLE Fill SHAPE
		if(shape == 8){
				if (!(selected)) {
					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.fill(new Ellipse2D.Double(hx,hy,hw,hh));
				}
				else if (!moveToolSelected && selected){
					g2.setStroke(NOT_DASHED_LINE);
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.fill(new Ellipse2D.Double(hx,hy,hw,hh));
				}
				else if (moveToolSelected && selected){
					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
//SET THE STROKE OF THE LINE TO A DASH						
						g2.setStroke(DASH_050_008);
//WRITE THE DISTANCE FROM THE ORIGINAL LOCATION NEAR THE HOLE						
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
//DRAWS A LINE 
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
						
					}

					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.fill(new Ellipse2D.Double(hx,hy,hw,hh));		
				}
		}
		//8 = Circle FILLED ends

		//FILLED Triangle STARTS
		if(shape == 9){

				double [] xs = {(((hole.getX() - bounds.getX()) * scale)), 
						(((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
						(((hole.getX() - bounds.getX()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale)
				};

				double [] ys = {(((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), 
						(((bounds.getHeight() + bounds.getY() - hole.getY()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
						(((bounds.getHeight() + bounds.getY() - hole.getY()) + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale)
				};
				int n = 3;

				if (!(selected)) {
					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.fill(new Polygon2D(xs,ys,n));
				}
				else if (!moveToolSelected && selected){
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.fill(new Polygon2D(xs,ys,n));
				}
				else if (moveToolSelected && selected){

					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
					}
					g2.setColor(p_selectedObjectsColour);
					g2.setStroke(LINE_300);
					g2.fill(new Polygon2D(xs,ys,n));
				}
		}
		//9 = Triangle Ends

		//SQUARE SHAPE
		if(shape == 10){
				if (!(selected)) {

					g2.setStroke(LINE_075);
					g2.setColor(hole.getColor());
					g2.fillRect(
							(int) (((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), 
							(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
							(int) (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale), 
							(int) (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale));
				}
				else if (!moveToolSelected && selected){
					g2.setStroke(LINE_075);
					g2.setColor(p_selectedObjectsColour);
					g2.fillRect(
							(int) (((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), 
							(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
							(int) (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale), 
							(int) (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale));
				}

				else if (moveToolSelected && selected){
					//◀			BLACK LEFT-POINTING TRIANGLE  Unicode: U+25C0, UTF-8: E2 97 80
					if (hole instanceof Hole) {
						g2.setColor(p_selectedObjectsColour);
						g2.setStroke(DASH_050_008);
						g2.drawString("   \u25C0 "+dec2P.format(d)+"m",	(int) (((hole.getX() - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale));
						g2.drawLine((int) (((hole.getX() - bounds.getX())) * scale), 
								(int) ((bounds.getHeight() + bounds.getY() - hole.getY())* scale),
								(int) (((lastEasting - bounds.getX())) * scale),
								(int) (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale));
						g2.setColor(getInverseColour(p_selectedObjectsColour));
						g2.fill(new Ellipse2D.Double(ovalX2, ovalY2, hw, hh));
					}
					g2.setStroke(LINE_075);
					g2.setColor(p_selectedObjectsColour);
					g2.fillRect(
							(int) (((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale), 
							(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance)) / 2) * scale),
							(int) (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale), 
							(int) (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale));

				}
			}
			
		//10 = filled Square Ends
	}

	// HOLE TRACK DRAWING
	public void paintTrack(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale, boolean selected, boolean buttonIn) {
//		double tanA = Math.tan(Math.toRadians(90 - hole.getAngle()));
//		double toeLoc = (hole.getBench() + hole.getSubdrill()) * tanA;
//		double tx1 = hole.getX() - bounds.getX() + toeLoc	* Math.cos(Math.toRadians(90 - hole.getBearing()));
//		double ty1 = bounds.getHeight() + bounds.getY() - hole.getY() - ((toeLoc * Math.sin(Math.toRadians(90 - hole.getBearing()))));

		double x = (hole.getX() - bounds.getX()) *scale;
		double y = (bounds.getHeight() + bounds.getY() - hole.getY())*scale;
		double tx = (hole.getToeXYZ().getX() - bounds.getX()) *scale;
		double ty = (bounds.getHeight() + bounds.getY() - hole.getToeXYZ().getY())*scale;
		
		if (!(selected)) {
			g2.setColor(hole.getColor()); 
			g2.setStroke(LINE_050);
		} else {
			g2.setColor(p_selectedObjectsColour);		
			g2.setStroke(LINE_050);
		}
		if (!(selected)) {
			g2.setColor(hole.getColor());
			g2.setStroke(LINE_050);
		} else if (buttonIn) {
			g2.setColor(p_selectedObjectsColour);
			g2.setStroke(LINE_050);
			g2.drawString(dec1P.format(hole.getBearing()) + "\u00B0", (int) (tx), (int) (ty));
		}
		//Track
		g2.setStroke(LINE_050);

		g2.draw(new Line2D.Double(x,y,tx,ty));
		
		// Track
//		g2.setStroke(LINE_050);
//		double [] xs = {(((hole.getX() - bounds.getX())) * scale),((tx1) * scale)};
//		double [] ys = {(((bounds.getHeight() + bounds.getY() - hole.getY())) * scale), ((ty1)*scale)};
//		g2.draw(new Polyline2D(xs, ys,2));
	}

	//FLOOR RL LINE 
	public void paintFloorLine(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale, boolean selected, double enhance){

		double tanA = Math.tan(Math.toRadians(90-hole.getAngle()));
		double floorLoc = hole.getBench() * tanA;
		double fx1 = hole.getX() - bounds.getX() + floorLoc
				* Math.cos(Math.toRadians(90 - hole.getBearing()));
		double fy1 = bounds.getHeight()
				+ bounds.getY()
				- hole.getY()
				- ((floorLoc * Math.sin(Math.toRadians(90 - hole
						.getBearing()))));
		double fx2 = fx1
				+ (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2))
				* Math.cos(Math.toRadians(90 - hole.getBearing() + 90));
		double fy2 = fy1
				+ (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2))
				* Math.sin(Math.toRadians(90 - hole.getBearing() - 90));
		double fx3 = fx1
				- (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2))
				* Math.cos(Math.toRadians(90 - hole.getBearing() + 90));
		double fy3 = fy1
				- (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2))
				* Math.sin(Math.toRadians(90 - hole.getBearing() - 90));
		if (!(selected)) {
			g2.setColor(p_floorLocationLineColour);	
			g2.setStroke(DASH_050_008);
		} else {
			g2.setColor(p_selectedObjectsColour);
			g2.setStroke(LINE_075);
		}
		if (!(selected)) {
			g2.setColor(p_floorLocationLineColour);	
			g2.setStroke(DASH_050_002);
		} else {
			g2.setColor(p_selectedObjectsColour);
			g2.setStroke(LINE_075);
		}
		//		floorRL Line marker
		double [] xs = {(fx3 * scale),(fx2 * scale)};
		double [] ys = {(fy3 * scale), (fy2 * scale)};
		g2.draw(new Polyline2D(xs, ys,2));



	}

	//TOE RL LINE
	public void paintToeLine(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale, boolean selected, double enhance){
		
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		double tanA = Math.tan(Math.toRadians(90-hole.getAngle()));
		double toeLoc = (hole.getBench() + hole.getSubdrill()) * tanA;
		double tx1 = hole.getX() - bounds.getX() + toeLoc*Math.cos(Math.toRadians(90-hole.getBearing()));
		double ty1 = bounds.getHeight() + bounds.getY() - hole.getY()- ((toeLoc*Math.sin(Math.toRadians(90-hole.getBearing()))));
		double tx2 = tx1 + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance/2)) * Math.cos(Math.toRadians(90-hole.getBearing()+90));
		double ty2 = ty1 + (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance/2)) * Math.sin(Math.toRadians(90-hole.getBearing()-90));
		double tx3 = tx1 - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance/2)) * Math.cos(Math.toRadians(90-hole.getBearing()+90));
		double ty3 = ty1 - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance/2)) * Math.sin(Math.toRadians(90-hole.getBearing()-90));

		if (!(selected)) {g2.setColor(p_toeLocationLineColour);	g2.setStroke(DASH_050_008);	}
		else {	g2.setColor(p_selectedObjectsColour); g2.setStroke(LINE_075);		}

		if (!(selected)) {	g2.setColor(p_toeLocationLineColour);	g2.setStroke(DASH_050_002);	}
		else {	g2.setColor(p_selectedObjectsColour);	g2.setStroke(LINE_075);		}

		//ToeRL  Line marker
		

		double [] xs = {(tx3*scale),(tx2*scale)};
		double [] ys = {(ty3*scale), (ty2*scale)};
		g2.draw(new Polyline2D(xs, ys,2));
	}

	//TOE CIRCLE
	public void paintToeCircle(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale, boolean selected, double enhance){
		
		double tMX = (hole.getToeXYZ().getX()-bounds.getX()-(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2)))*scale;
		double tMY = (bounds.getHeight() + bounds.getY() - hole.getToeXYZ().getY() - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2)))*scale;
		double diam = (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale);
		
		if (!(selected)) {
			g2.setColor(p_toeLocationFillColour);
			g2.fill(new Ellipse2D.Double(tMX, tMY, diam, diam));
			//toeCircle
			g2.setStroke(DASH_050_002);
			g2.setColor(p_toeLocationLineColour);
			g2.draw(new Ellipse2D.Double(tMX, tMY, diam, diam));
		} else {
			g2.setColor(getInverseColour(p_toeLocationFillColour));
			g2.setStroke(LINE_075);
			g2.fill(new Ellipse2D.Double(tMX, tMY, diam, diam));
			//toeCircle
			g2.setColor(p_selectedObjectsColour);
			g2.draw(new Ellipse2D.Double(tMX, tMY, diam, diam));
			g2.setStroke(DASH_050_002);
		}
	}

	//FLOOR CIRCLE
	public void paintFloorCircle(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale, boolean selected, double enhance){
		
		double fMX = (hole.getFloorXYZ().getX()-bounds.getX()-(UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2)))*scale;
		double fMY = (bounds.getHeight() + bounds.getY() - hole.getFloorXYZ().getY() - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance / 2)))*scale;
		double diam = (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance) * scale);
		
		if (!(selected)) {
			g2.setColor(p_floorLocationFillColour);
			g2.fill(new Ellipse2D.Double(fMX, fMY, diam, diam));
			//toeCircle
			g2.setStroke(DASH_050_002);
			g2.setColor(p_floorLocationLineColour);
			g2.draw(new Ellipse2D.Double(fMX, fMY, diam, diam));
		} else {
			g2.setColor(getInverseColour(p_floorLocationFillColour));
			g2.setStroke(LINE_075);
			g2.fill(new Ellipse2D.Double(fMX, fMY, diam, diam));
			//toeCircle
			g2.setColor(p_selectedObjectsColour);
			g2.draw(new Ellipse2D.Double(fMX, fMY, diam, diam));
			g2.setStroke(DASH_050_002);
		}
	}	
	//TEXT	
	public void paintText(Graphics2D t2D, Color color,Text t, Font font, double bearing, Rectangle2D bounds, double scale, boolean selected, boolean isRotationTool, double enhance) {
		
		//Draws text where ever the user clicks
		FontMetrics fm = t2D.getFontMetrics();
//		t2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform at = new AffineTransform();

		float x = (float)(((t.getX())- bounds.getX())*scale);
		float y = (float)(((bounds.getHeight() + bounds.getY())-(t.getY()))*scale);

		at.setToRotation(Math.toRadians(bearing+270), x,y);
		FontRenderContext frc = t2D.getFontRenderContext();
		TextLayout layout = new TextLayout(t.getText(), fm.getFont(), frc);

		t2D.setTransform(at);
		if (!(selected)) {
			t2D.setColor(color);
		}
		else {
			float size = 20;//(float) (fm.stringWidth(t.getText())*0.5);
			double extra = 1.25;
			//			t2D.setColor(p_selectedObjectsColour);
			t2D.setStroke(LINE_100);
			//Highlight and origin indicator when selected - START
//			t2D.setColor(p_selectedObjectsColour);
//			t2D.fill(new Ellipse2D.Double(
//					(int) (((t.getEasting() - bounds.getX())*scale) - size), //  X location - eastings is in mE
//					(int) (((bounds.getHeight() + bounds.getY() - t.getNorthing())*scale) - size), //  Y location - northings is in mN - also is reversed
//					(int) (size*2), //  size in mE - WIDTH of hole - convert these from px to millimeters to meters i.e. 10px = 10mm = 0.01m
//					(int) (size*2))); // size in mN - HEIGHT of hole - as above for convert!!!
			t2D.setColor(p_selectedObjectsColour);
			t2D.draw(new Ellipse2D.Double(
					(((t.getX() - bounds.getX())*scale) - size), //  X location - eastings is in mE
					(((bounds.getHeight() + bounds.getY() - t.getY())*scale) - size), //  Y location - northings is in mN - also is reversed
					(size*2), //  size in mE - WIDTH of hole - convert these from px to millimeters to meters i.e. 10px = 10mm = 0.01m
					(size*2))); // size in mN - HEIGHT of hole - as above for convert!!!
			if(isRotationTool){
				t2D.drawString("  : \uu27f3 "+dec1P.format(bearing)+"\u00b0",(float) (x + (fm.stringWidth(t.getText())*1.05)),y);
			}
			t2D.setColor(p_selectedObjectsColour);
//			double textY = (((bounds.getHeight() + bounds.getY() - t.getY()))*scale)-fm.getStringBounds((dec2P.format(t)) + "m", t2d).getHeight();
			
			t2D.draw(new Rectangle2D.Double(
					(t.getX() - bounds.getX())* scale,
					((bounds.getHeight() + bounds.getY() - t.getY())*scale)-fm.getStringBounds(t.toString(), t2D).getHeight(),
					t.getBounds().getWidth(),
					t.getBounds().getHeight()
					));
			
			
			t2D.drawLine((int) (((t.getX() - bounds.getX())) * scale),
					(int)(((bounds.getHeight() + bounds.getY())-(t.getY()))*scale), 
					(int)(((t.getX())- bounds.getX())*scale)+fm.stringWidth(t.getText()),
					(int)(((bounds.getHeight() + bounds.getY())-(t.getY()))*scale));
			//			t2D.drawLine((int)(((t.getEasting())- bounds.getX())*scale),
			//					(int) (((bounds.getHeight() + bounds.getY() - t.getNorthing()) - (UnitConvert.pixelsToMeters(size*extra*enhance)) / 2) * scale),
			//					(int)(((t.getEasting())- bounds.getX())*scale),
			//					(int) (((bounds.getHeight() + bounds.getY() - t.getNorthing()) + (UnitConvert.pixelsToMeters(size*extra*enhance)) / 2) * scale));
			//			
			//Highlight and origin indicator when selected - FINISH
		}

		
		t2D.setColor(color);
		layout.draw(t2D, x, y);

		at.setToRotation(0, x,y);
		t2D.setTransform(at);

	}

	
	//BOUNDARY line DRAWING	
	public void paintPolyLine(Graphics2D b2, Color colorStroke, Color colorFill, Stroke stroke,
			Boundary b,  Coordinate c1 , Coordinate c2 ,Rectangle2D bounds,
			double scale, boolean selected, boolean isClosed,int arrowType,boolean isAnnotated){
		
//		b2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		ArrayList<Integer> xList = new ArrayList<Integer>();
		ArrayList<Integer> yList = new ArrayList<Integer>();
		ArrayList<Integer> zList = new ArrayList<Integer>();
		ArrayList<Double> dxList = new ArrayList<Double>();
		ArrayList<Double> dyList = new ArrayList<Double>();
		ArrayList<Double> dzList = new ArrayList<Double>();

		double[] xPoints ;
		double[] yPoints ;
		double[] zPoints ;
		
		for(BPoint temp: b.getBPList().values()){
			if (!(b.getBPoint(temp.getPointID()) == null)) {
				xList.add((int) ((b.getBPoint(temp.getPointID()).getX() - bounds.getX())*scale));
				yList.add((int) ((bounds.getHeight() + bounds.getY() - b.getBPoint(temp.getPointID()).getY())*scale));
				zList.add((int) b.getBPoint(temp.getPointID()).getZ());
				//Store the Double Values
				dxList.add((b.getBPoint(temp.getPointID()).getX()));
				dyList.add((b.getBPoint(temp.getPointID()).getY()));
				dzList.add((b.getBPoint(temp.getPointID()).getZ()));
			}
		}
		xPoints = new double [xList.size()];
		yPoints = new double [yList.size()];
		zPoints = new double [zList.size()];
		
		int nPoints = xList.size();
		
		for (int i = 0; i < b.getBPList().size(); i++){
			xPoints[i] = xList.get(i);
			yPoints[i] = yList.get(i);
			zPoints[i] = zList.get(i);
		}
		
		b2.setColor(colorStroke);
		b2.setStroke(stroke);
		
		if((isClosed)){
			b2.setColor(colorFill);
			b2.fill(new Polygon2D(xPoints, yPoints, nPoints));
			if(selected){
				b2.setColor(p_selectedObjectsColour);
			}
			else{
				b2.setColor(colorStroke);
			}
			b2.draw(new Polygon2D(xPoints, yPoints, nPoints));
		}
		if(!(isClosed)){
			if(selected){
				b2.setColor(p_selectedObjectsColour);
			}
			else{
				b2.setColor(colorStroke);
			}
			b2.draw(new Polyline2D(xPoints, yPoints, nPoints));
		}
		//ONE ARROW HEAD
		if((arrowType == 1 || arrowType == 2)  && dxList.size() >1 ){
			double e1 = dxList.get(nPoints-1);			double n1 = dyList.get(nPoints-1);
			double e2 = dxList.get(nPoints-2);			double n2 = dyList.get(nPoints-2);
			double xl = (e2 - e1);						double yl = (n2 - n1);//opp length
			double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
			double dd  = 30;
			if (d*30 < 90){dd = 90;} else {dd=d*30;}							
			double bearing = 0;
			//NORTH IS ZERO DEGREES FOR THESE EQUATIONS				
			//LOWER RIGHT QUADRANT ( CW 90 - 180)
			if(yl < 0 && xl >= 0){bearing = (Math.acos((yl/d)));}
			//LOWER LEFT QUADRANT (CW 180 - 270)
			else if(yl< 0 && xl <=0){bearing = (Math.asin(-(xl/d)))+ Math.toRadians(180);}
			//UPPER LEFT QUADRANT (CW 270 - 0)
			else if (xl <0 && yl >= 0){bearing = (Math.acos(-(yl/d)))+ Math.toRadians(180);}
			//UPPER RIGHT QUADRANT (CW 0 - 90)
			else if(yl >= 0 && xl >= 0){bearing = (Math.asin(xl/d));}
			else	bearing= 0;

			double x1,x2,x3,y1,y2,y3;
			x1 = (((e1 - bounds.getX() )))*scale;
			x2 = ((((e1 - bounds.getX())) - (UnitConvert.pixelsToMeters(dd))))*scale;
			x3 = ((((e1 - bounds.getX())) + (UnitConvert.pixelsToMeters(dd))))*scale;
			y1 = (((bounds.getHeight() + bounds.getY() - n1))) *scale;
			y2 = ((((bounds.getHeight() + bounds.getY() - n1))-(UnitConvert.pixelsToMeters((dd)*4))))*scale;
			y3 = ((((bounds.getHeight() + bounds.getY() - n1))-(UnitConvert.pixelsToMeters((dd)*4))))*scale;
			double tx1,tx2,tx3,ty1,ty2,ty3,anchorX,anchorY;
			anchorX = (e1 - bounds.getX())*scale;		anchorY = (bounds.getHeight() + bounds.getY() - n1)*scale;
			tx1 = x1-anchorX;		tx2 = x2-anchorX;		tx3 = x3-anchorX;
			ty1 = y1-anchorY;		ty2 = y2-anchorY;		ty3 = y3-anchorY;

			double rx1,rx2,rx3,ry1,ry2,ry3;//Points of the triangle after rotation
			rx1 = (tx1*Math.cos(bearing) - ty1*Math.sin(bearing))+anchorX;
			rx2 = (tx2*Math.cos(bearing) - ty2*Math.sin(bearing))+anchorX;
			rx3 = (tx3*Math.cos(bearing) - ty3*Math.sin(bearing))+anchorX;
			ry1 = (tx1*Math.sin(bearing) + ty1*Math.cos(bearing))+anchorY;
			ry2 = (tx2*Math.sin(bearing) + ty2*Math.cos(bearing))+anchorY;
			ry3 = (tx3*Math.sin(bearing) + ty3*Math.cos(bearing))+anchorY;
			double [] xs = { rx1,	rx2,rx3};
			double [] ys = { ry1,	ry2,ry3};
			if(dxList.size() >1 ){
				if(selected){
					b2.setColor(p_selectedObjectsColour);
				}
				if(!selected){
					b2.setColor(colorStroke);
				}
				b2.fill(new Polygon2D(xs,ys,3));
			}
		}
			if(isAnnotated){
				
				double e4 = dxList.get(nPoints-1);//dxList.get(dxList.lastIndexOf(dxList));
				double n4 = dyList.get(nPoints-1);
				double z4 = dzList.get(nPoints-1);
				double e5 = dxList.get(0);
				double n5 = dyList.get(0);
				double z5 = dzList.get(0);
				

				double xl2= (e5 - e4);//adj length
				double yl2= (n5 - n4);//opp length
				double zl2= (z5 - z4);
				double d2 =  Math.sqrt(Math.pow(xl2, 2) + Math.pow(yl2, 2) + Math.pow(zl2, 2));//hyp length
				double textX,textY,textX1,textY1,textFX,textFY;
				textX = (((dxList.get(0) - bounds.getX()))*scale);
				textY = (((bounds.getHeight() + bounds.getY() - dyList.get(0)))*scale);
				textX1 = (((dxList.get(nPoints-1) - bounds.getX()))*scale);
				textY1 = (((bounds.getHeight() + bounds.getY() - dyList.get(nPoints-1)))*scale);

				FontMetrics fm = b2.getFontMetrics();

				String distanceText = dec2P.format(d2) +"m ";

				textFX = textX - ((textX - textX1)/2);
				textFY = textY - ((textY - textY1)/2);


				b2.setColor(p_backgroundCanvasColour);
				b2.fillRect((int)textFX-2-(fm.stringWidth(distanceText)/2), (int)textFY-1-(fm.getHeight()/2), (int)fm.stringWidth(distanceText)+4, (int)fm.getHeight()+2);
				if(selected){
					b2.setColor(p_selectedObjectsColour);
				}
				if(!selected){
					b2.setColor(colorStroke);
				}
				b2.drawRect((int)textFX-2-(fm.stringWidth(distanceText)/2), (int)textFY-1-(fm.getHeight()/2), (int)fm.stringWidth(distanceText)+4, (int)fm.getHeight()+2);

				b2.drawString(dec2P.format(d2)+"m", 
						(int) (textFX-(fm.stringWidth(distanceText))/2.25),	
						(int) (textFY+(fm.getHeight())/3));
			}
			//TW ARROWS
		if(arrowType == 2  && dxList.size() > 1){

			//Start Arrow ********************************************************************************
			double e1 = dxList.get(0);
			double n1 = dyList.get(0);
			double e2 = dxList.get(1);
			double n2 = dyList.get(1);

			double xl = (e1 - e2);//adj length
			double yl = (n1 - n2);//opp length
			double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
			double dd  = 30;
			if (d*30 < 90){dd = 90;} else {dd=d*30;}
			//			System.out.println("n1 =" + n1 + "/ e1 =" +e1 + "/ n2 = " +n2 + "/ e2 = " +e2 +"\n distance = " +d);
			double bearing = 0;
			//NORTH IS ZERO DEGREES FOR THESE EQUATIONS				
			//LOWER RIGHT QUADRANT ( CW 90 - 180)
			if(yl < 0 && xl >= 0){
				bearing = (Math.acos((yl/d)));
			}
			//LOWER LEFT QUADRANT (CW 180 - 270)
			else if(yl< 0 && xl <=0){
				bearing = (Math.asin(-(xl/d)))+ Math.toRadians(180);
			}
			//UPPER LEFT QUADRANT (CW 270 - 0)
			else if (xl <0 && yl >= 0){
				bearing = (Math.acos(-(yl/d)))+ Math.toRadians(180);
			}
			//UPPER RIGHT QUADRANT (CW 0 - 90)
			else if(yl >= 0 && xl >= 0){
				bearing = (Math.asin(xl/d));

			}
			else
				bearing= 0;

			//			System.out.println("bearing " + bearing);

			double x1,x2,x3,y1,y2,y3;
			x1 = (((dxList.get(0) - bounds.getX() )))*scale;
			x2 = ((((dxList.get(0) - bounds.getX())) - (UnitConvert.pixelsToMeters(dd))))*scale;
			x3 = ((((dxList.get(0) - bounds.getX())) + (UnitConvert.pixelsToMeters(dd))))*scale;
			y1 = (((bounds.getHeight() + bounds.getY() - dyList.get(0)))) *scale;
			y2 = ((((bounds.getHeight() + bounds.getY() - dyList.get(0)))-(UnitConvert.pixelsToMeters((dd)*4))))*scale;
			y3 = ((((bounds.getHeight() + bounds.getY() - dyList.get(0)))-(UnitConvert.pixelsToMeters((dd)*4))))*scale;

			double tx1,tx2,tx3,ty1,ty2,ty3,anchorX,anchorY;
			anchorX = (e1 - bounds.getX())*scale;		anchorY = (bounds.getHeight() + bounds.getY() - n1)*scale;
			tx1 = x1-anchorX;		tx2 = x2-anchorX;		tx3 = x3-anchorX;
			ty1 = y1-anchorY;		ty2 = y2-anchorY;		ty3 = y3-anchorY;

			bearing = (bearing+ Math.toRadians(180));

			double rx1,rx2,rx3,ry1,ry2,ry3;//Points of the triangle after rotation
			rx1 = (tx1*Math.cos(bearing) - ty1*Math.sin(bearing))+anchorX;
			rx2 = (tx2*Math.cos(bearing) - ty2*Math.sin(bearing))+anchorX;
			rx3 = (tx3*Math.cos(bearing) - ty3*Math.sin(bearing))+anchorX;
			ry1 = (tx1*Math.sin(bearing) + ty1*Math.cos(bearing))+anchorY;
			ry2 = (tx2*Math.sin(bearing) + ty2*Math.cos(bearing))+anchorY;
			ry3 = (tx3*Math.sin(bearing) + ty3*Math.cos(bearing))+anchorY;

			double [] xs = { rx1,	rx2,rx3};
			double [] ys = { ry1,	ry2,ry3};
			//	
			if(dxList.size() >1 ){
				if(selected){
					b2.setColor(p_selectedObjectsColour);
				}
				if(!selected){
					b2.setColor(colorStroke);
				}
				b2.fill(new Polygon2D(xs,ys,3));
			}
		}
		//NO ARROWS
		if (!(selected) && arrowType == 0) {
			b2.setColor(colorStroke);
			b2.setStroke(stroke);
			if (c1 != null && c2!=null){
				double xPlanLength = (c2.getX() - c1.getX());//adj length
				double yPlanLength = (c2.getY() - c1.getY());//opp length
				double zPlanLength = (c2.getZ() - c1.getZ());
				double d =  Math.sqrt(Math.pow(xPlanLength, 2) + Math.pow(yPlanLength, 2) + Math.pow(zPlanLength, 2));//hyp length
				
				if (d != 0) {
					b2.setColor(p_selectedObjectsColour);
					b2.setStroke(DASHED_LINE3);
					b2.drawLine((int) (((c2.getX() - bounds.getX())) * scale), (int) ((bounds.getHeight() + bounds.getY() - c2.getY())  * scale),
							(int) (((c1.getX() - bounds.getX())) * scale), (int) ((bounds.getHeight() + bounds.getY() - c1.getY())  * scale));
					double bearing = 0;
					
					bearing = (Transform.orientationChange(c1.getX(), c1.getY(), c2.getX(), c2.getY()));
					
					//NORTH IS ZERO DEGREES FOR THESE EQUATIONS				
					//LOWER RIGHT QUADRANT ( CW 90 - 180)
					if(yPlanLength < 0 && xPlanLength >= 0){bearing = (Math.acos((yPlanLength/d)))+ Math.toRadians(180);}
					//LOWER LEFT QUADRANT (CW 180 - 270)
					else if(yPlanLength< 0 && xPlanLength <=0){bearing = (Math.asin(-(xPlanLength/d)));}
					//UPPER LEFT QUADRANT (CW 270 - 0)
					else if (xPlanLength <0 && yPlanLength >= 0){bearing = (Math.acos(-(yPlanLength/d)));}
					//UPPER RIGHT QUADRANT (CW 0 - 90)
					else if(yPlanLength >= 0 && xPlanLength >= 0){bearing = (Math.asin(xPlanLength/d))+ Math.toRadians(180);}
					else	bearing= 0;
					
					b2.drawString(dec2P.format(d)+"m - ["+ dec1P.format(Math.toDegrees(bearing))+"\u00b0]",	(int) (((c1.getX() - bounds.getX())) * scale),
							(int) ((bounds.getHeight() + bounds.getY() - c1.getY())  * scale));
				}
			}
		}
		else if (c1 != null && c2!=null){

			b2.setColor(p_selectedObjectsColour);
			b2.setStroke(DASH_050_002);
		}
	dxList.trimToSize();
	dyList.trimToSize();
	
	}
	
	//BOUNDARY POINT DRAWING	
	public void paintBPoint(Graphics2D bp2D, BPoint bp, Boundary b,double mE,double mN,double lastEasting, double lastNorthing, Rectangle2D bounds, double scale, boolean selected, boolean buttonIn, double enhance){
		lastEasting = (((lastEasting - bounds.getX())) * scale);
		lastNorthing = (((bounds.getHeight() + bounds.getY() - lastNorthing)) * scale);
		mE = (mE - bounds.getX()) *scale;
		mN = ((bounds.getHeight() + bounds.getY() - mN)) * scale;
		double offset = 4.5;
		double [] xs1 = { (((bp.getX() - bounds.getX())) * scale)-offset,(((bp.getX() - bounds.getX())) * scale)+offset};
		double [] ys1 = {  (((bounds.getHeight() + bounds.getY() - bp.getY())) * scale),(((bounds.getHeight() + bounds.getY() - bp.getY())) * scale)};
		double [] xs2 = { (((bp.getX() - bounds.getX())) * scale),(((bp.getX() - bounds.getX())) * scale)};
		double [] ys2 = { (((bounds.getHeight() + bounds.getY() - bp.getY())) * scale)-offset,(((bounds.getHeight() + bounds.getY() - bp.getY())) * scale)+offset};
		double [] xs = {(bp.getX() + bounds.getX())* scale, (bp.getX() + bounds.getX())* scale};
		double [] ys = {(bounds.getHeight() + bounds.getY() - bp.getY()) * scale, (bounds.getHeight() + bounds.getY() - bp.getY()) * scale};

		
//		bp2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (!(selected)) {
			bp2D.setColor(b.getColor());
			bp2D.draw(new Polyline2D(xs,ys,2));
		}
		else if (selected){
			bp2D.setColor(p_selectedObjectsColour);
			bp2D.drawString(" " + bp.getPointID() ,	(float) (((bp.getX() - bounds.getX())) * scale),(float) (((bounds.getHeight() + bounds.getY() - bp.getY()) * scale)));
			bp2D.setStroke(LINE_100);
			bp2D.draw(new Polyline2D(xs1,ys1,2));
			bp2D.draw(new Polyline2D(xs2,ys2,2));
		}
		if (b.getBPList().values().size() > 1) {
//			DRAWS ID ALL THE TIME COMMENT OUT IF NOT REQUIRED
//			bp2D.setColor(Color.RED);
//			bp2D.drawString("." + bp.getPointID() ,	(float) (((bp.getEasting() - bounds.getX())) * scale),(float) (((bounds.getHeight() + bounds.getY() - bp.getNorthing()) * scale)));
//			DRAWS POINT 
			bp2D.setColor(b.getColor());
			bp2D.draw(new Polyline2D(xs,ys,2));
		
		}
		else if(b.getBPList().values().size() < 2){

			bp2D.draw(new Polyline2D(xs1,ys1,2));
			bp2D.draw(new Polyline2D(xs2,ys2,2));
//			bp2D.setColor(p_selectedObjectsColour);
//			bp2D.draw(new Polyline2D(xsl,ysl,2));
			}
	}



	// INITIATION POINT
	public void paintIP(Graphics2D g2, Dummy dummy, Rectangle2D bounds, double averageHoleSize, double scale, boolean selected, double enhance){
		
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (!(selected)) {
			g2.setColor(p_holeTextColour);
			g2.setStroke(LINE_075);
		}
		else {
			g2.setColor(p_selectedObjectsColour);
			g2.setStroke(LINE_300);
		}

		double x1,x2,x3,x4,x5,x6,y1,y2,y3,y4,y5,y6;


		x1 = (((dummy.getX() - bounds.getX()) ) * scale);
		x2 = (((dummy.getX() - bounds.getX()) + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))) * scale);
		x3 = (((dummy.getX() - bounds.getX()) + (UnitConvert.pixelsToMeters(averageHoleSize*enhance))) * scale);
		x4 = (((dummy.getX() - bounds.getX()) ) * scale);
		x5 =  (((dummy.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))) * scale);
		x6 = (((dummy.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))) * scale);

		y1 =  (((bounds.getHeight() + bounds.getY() - dummy.getY()) - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))) * scale);
		y2 = (((bounds.getHeight() + bounds.getY() - dummy.getY()) - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/3) * scale);
		y3 = (((bounds.getHeight() + bounds.getY() - dummy.getY()) + 2*(UnitConvert.pixelsToMeters(averageHoleSize*enhance))/3) * scale);
		y4 = (((bounds.getHeight() + bounds.getY() - dummy.getY()) + 2*(UnitConvert.pixelsToMeters(averageHoleSize*enhance))) * scale);
		y5 = (((bounds.getHeight() + bounds.getY() - dummy.getY()) + 2*(UnitConvert.pixelsToMeters(averageHoleSize*enhance))/3) * scale);
		y6 = (((bounds.getHeight() + bounds.getY() - dummy.getY()) - (UnitConvert.pixelsToMeters(averageHoleSize*enhance))/3) * scale);

		double []lx = {x1,x1};
		double []ly = {y4, y1 + ((UnitConvert.pixelsToMeters(averageHoleSize*enhance))*scale)};


		double [] x = {x1,x2,x3,x4,x5,x6};
		double [] y = {y1,y2,y3,y4,y5,y6};
		
		Color transparent30 = new Color(p_holeTextColour.getRed(), p_holeTextColour.getGreen(), p_holeTextColour.getBlue(), 100);
		g2.setColor(transparent30);
		g2.fill(new Polygon2D(x,y,6));
		//g2.drawLine(lx1, ly1, lx2, ly2);
		if(!(selected)){
			g2.setColor(p_holeTextColour);
			g2.setStroke(LINE_075);
			g2.draw(new Polygon2D(x,y,6));
			g2.draw(new Polyline2D(lx, ly,2));
		}

	}
	public void paintCharge(Graphics2D d2D, Hole hole, Rectangle2D bounds, double diameter, Color color, double scale, boolean selected, double enhance){
		
		// Draws a diamond the in the center holes in the pattern
//		d2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (!(selected)) {
			d2D.setColor(color);
			d2D.setStroke(LINE_075);
		}
		else {
			d2D.setColor(p_selectedObjectsColour);
			d2D.setStroke(LINE_300);
		}

		int x1,x2,x3,x4,y1,y2,y3,y4;


		x1 = (int) (((hole.getX() - bounds.getX()) ) * scale);
		x2 = (int) (((hole.getX() - bounds.getX()) + (UnitConvert.pixelsToMeters((diameter/4)*enhance))) * scale);
		x3 = (int) (((hole.getX() - bounds.getX()) ) * scale);
		x4 = (int) (((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters((diameter/4)*enhance))) * scale);


		y1 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters((diameter/2)*enhance))) * scale);
		y2 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) ) * scale);
		y3 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) + (UnitConvert.pixelsToMeters((diameter/2)*enhance))) * scale);
		y4 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) ) * scale);


		int [] x = {x1,x2,x3,x4};
		int [] y = {y1,y2,y3,y4};
		d2D.fillPolygon(x, y, 4);
		//d2D.drawPolygon(x,y,4);


	}
	// DETONATOR 
	public void paintDetonator(Graphics2D d2D, Hole hole, Rectangle2D bounds, double diameter, Color color, double scale, boolean selected, double enhance){
		
		// Draws a diamond the in the center holes in the pattern
//		d2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (!(selected)) {
			d2D.setColor(color);
			d2D.setStroke(LINE_075);
		}
		else {
			d2D.setColor(p_selectedObjectsColour);
			d2D.setStroke(LINE_300);
		}

		int x1,x2,x3,x4,y1,y2,y3,y4;


		x1 = (int) (((hole.getX() - bounds.getX()) ) * scale);
		x2 = (int) (((hole.getX() - bounds.getX()) + (UnitConvert.pixelsToMeters((diameter/4)*enhance))) * scale);
		x3 = (int) (((hole.getX() - bounds.getX()) ) * scale);
		x4 = (int) (((hole.getX() - bounds.getX()) - (UnitConvert.pixelsToMeters((diameter/4)*enhance))) * scale);


		y1 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters((diameter/2)*enhance))) * scale);
		y2 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) ) * scale);
		y3 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) + (UnitConvert.pixelsToMeters((diameter/2)*enhance))) * scale);
		y4 = (int) (((bounds.getHeight() + bounds.getY() - hole.getY()) ) * scale);


		int [] x = {x1,x2,x3,x4};
		int [] y = {y1,y2,y3,y4};
		d2D.fillPolygon(x, y, 4);
		//d2D.drawPolygon(x,y,4);


	}
	//Draws a Surface connection DELAY in a simple format
	public void paintDelay(Graphics2D delay2D, SurfaceConnector sc, Rectangle2D bounds, double fromDiameter, double toDiameter, Color color, double scale, boolean selected, double enhanceTie, double enhance){
		
//		delay2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!(selected)){delay2D.setColor(color);delay2D.setStroke(LINE_075);}
		else {delay2D.setColor(p_selectedObjectsColour);delay2D.setStroke(DASH_050_002);}

		double textX,textY,textX1,textY1,textFX,textFY;
		textX = (((sc.getFrom().getX() - bounds.getX()))*scale);
		textY = (((bounds.getHeight() + bounds.getY() - sc.getFrom().getY()))*scale);
		textX1 = (((sc.getTo().getX() - bounds.getX()))*scale);
		textY1 = (((bounds.getHeight() + bounds.getY() - sc.getTo().getY()))*scale);

		FontMetrics fm = delay2D.getFontMetrics();

		String delayText = dec0P.format(sc.getDelay());

		textFX = textX - ((textX - textX1)/2);
		textFY = textY - ((textY - textY1)/2);

		
		delay2D.setColor(p_backgroundCanvasColour);
		delay2D.fillRect((int)textFX-2-(fm.stringWidth(delayText)/2), (int)textFY-1-(fm.getHeight()/2), (int)fm.stringWidth(delayText)+4, (int)fm.getHeight()+2);
		delay2D.setColor(color);
		delay2D.drawRect((int)textFX-2-(fm.stringWidth(delayText)/2), (int)textFY-1-(fm.getHeight()/2), (int)fm.stringWidth(delayText)+4, (int)fm.getHeight()+2);
		delay2D.setFont(new Font("Dialog",Font.PLAIN,8));
		delay2D.drawString(dec0P.format(sc.getDelay()), //DRAW THE DELAY OF THE TIE
				(int) (textFX-(fm.stringWidth(delayText))/2.25),	
				(int) (textFY+(fm.getHeight())/3));


	}

	//Draws a Surface connection in a simple format
	public void paintTie(Graphics2D tie2D, SurfaceConnector sc, Rectangle2D bounds, double fromDiameter, double toDiameter, Color color, double scale, boolean selected, double enhanceTie, double enhance){
		
		//		if (selected == true){
//			enhance = enhance*2;
//		}
//		tie2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!(selected)){tie2D.setColor(color);tie2D.setStroke(LINE_075);}
		else {tie2D.setColor(p_selectedObjectsColour);tie2D.setStroke(DASHED_LINE2_THICK);}
		//Draw Line from hole to hole

		double xl = (sc.getTo().getX() - sc.getFrom().getX());//adj length
		double yl = (sc.getTo().getY() - sc.getFrom().getY());//opp length
		double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
		double dd  = (toDiameter/2);

		double b = 0;
		//NORTH IS ZERO DEGREES FOR THESE EQUATIONS				
		//LOWER RIGHT QUADRANT ( CW 90 - 180)
		if(yl < 0 && xl >= 0){
			b = (Math.acos((yl/d)))+ Math.toRadians(180);
		}
		//LOWER LEFT QUADRANT (CW 180 - 270)
		else if(yl< 0 && xl <=0){
			b = (Math.asin(-(xl/d)));
		}
		//UPPER LEFT QUADRANT (CW 270 - 0)
		else if (xl <0 && yl >= 0){
			b = (Math.acos(-(yl/d)));
		}
		//UPPER RIGHT QUADRANT (CW 0 - 90)
		else if(yl >= 0 && xl >= 0){
			b = (Math.asin(xl/d))+ Math.toRadians(180);

		}
		else
			b= 0;

		double x1,x2,x3,y1,y2,y3;
		double lengthOnY = 2.5;
		double radius = toDiameter/2;
		x1 = (((sc.getTo().getX() - bounds.getX() )))*scale;
		x2 = ((((sc.getTo().getX() - bounds.getX())) - (UnitConvert.pixelsToMeters(radius*enhanceTie))))*scale;
		x3 = ((((sc.getTo().getX() -  bounds.getX())) + (UnitConvert.pixelsToMeters(radius*enhanceTie))))*scale;
		y1 = (((bounds.getHeight() + bounds.getY() - (sc.getTo().getY() + (UnitConvert.pixelsToMeters(dd*(enhance/2)))))))*scale;
		y2 = ((((bounds.getHeight() + bounds.getY() - sc.getTo().getY()))-(UnitConvert.pixelsToMeters((dd*enhanceTie*lengthOnY)))))*scale;
		y3 = ((((bounds.getHeight() + bounds.getY() - sc.getTo().getY()))-(UnitConvert.pixelsToMeters((dd*enhanceTie*lengthOnY)))))*scale;

		double tx1,tx2,tx3,ty1,ty2,ty3,anchorX,anchorY;
		anchorX = (sc.getTo().getX() - bounds.getX())*scale;		anchorY = (bounds.getHeight() + bounds.getY() - sc.getTo().getY())*scale;
		tx1 = x1-anchorX;		tx2 = x2-anchorX;		tx3 = x3-anchorX;
		ty1 = y1-anchorY;		ty2 = y2-anchorY;		ty3 = y3-anchorY;

		double rx1,rx2,rx3,ry1,ry2,ry3;//Points of the triangle after rotation
		rx1 = (tx1*Math.cos(b) - ty1*Math.sin(b))+anchorX;
		rx2 = (tx2*Math.cos(b) - ty2*Math.sin(b))+anchorX;
		rx3 = (tx3*Math.cos(b) - ty3*Math.sin(b))+anchorX;
		ry1 = (tx1*Math.sin(b) + ty1*Math.cos(b))+anchorY;
		ry2 = (tx2*Math.sin(b) + ty2*Math.cos(b))+anchorY;
		ry3 = (tx3*Math.sin(b) + ty3*Math.cos(b))+anchorY;

		//Double Precision Drawing
		double [] xs = { rx1,rx2,rx3};
		double [] ns = { ry1,ry2,ry3};

		double [] lineXs = {((sc.getFrom().getX() - bounds.getX()))*scale,  
				((sc.getTo().getX() - bounds.getX()))*scale};
		double [] lineYs = {((bounds.getHeight() + bounds.getY() - sc.getFrom().getY()))*scale,
				((bounds.getHeight() + bounds.getY() - sc.getTo().getY()))*scale};

		if( sc.getStyle() == SurfaceConnector.LIN_ST_STYLE){
			tie2D.draw(new Polyline2D(lineXs,lineYs,2));
		}
		if(sc.getStyle() == SurfaceConnector.ARC_SM_STYLE){
			//FIXME This doesn't work at all.
			tie2D.draw(new Arc2D.Double(20,20,50,50,0,90,Arc2D.OPEN));
		}
		if(sc.getStyle() == SurfaceConnector.ARC_LG_STYLE){
			//FIXME This doesn't quite work yet.
			double fromx = (sc.getFrom().getX() - bounds.getX()) *scale; 
			double fromy = (bounds.getHeight() + bounds.getY() - sc.getFrom().getY())*scale;
			double tox = (sc.getTo().getX() - bounds.getX())*scale;
			double toy = (bounds.getHeight()+bounds.getY() - sc.getTo().getY())*scale;
			double height = (toy - fromy);
			double width = (tox - fromx);
			double startAngle = 0;
			double arcAngle = 180;
			tie2D.draw(new Arc2D.Double(fromx,fromy,width,height,startAngle,arcAngle,Arc2D.OPEN));
		}
		if(sc.getStyle() == SurfaceConnector.BEZIER_STYLE){
			//FIXME This doesn't work at all.
			tie2D.draw(new Path2D.Double(Arc2D.OPEN));
		}
		

		if(!(selected)){//Drawing the triangle of the connector
			tie2D.fill(new Polygon2D(xs,ns,3));
		}
		else{
			this.paintPolyBounds(tie2D, sc, bounds, scale);
//			tie2D.setStroke(DASHED_LINE2_THICK);
//			tie2D.drawPolygon(xs, ns, 3);
		}

	}



	/**
	 * @return A polygon that displays the bounds of a SurfaceConnector
	 * @param g2 - graphics 
	 * @param sc - SurfaceConnetor unit
	 * @param bounds - bounds of the display canvas
	 * @param scale - factor to scale all graphics
	 */	
	//USE ONLY IF REQUIRED TO TROUBLE SHOOT
	public void paintPolyBounds(Graphics2D g2, SurfaceConnector sc, Rectangle2D bounds, double scale){
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(p_selectedObjectsColour);
		g2.setStroke(LINE_100);

		double x1,x2,x3,x4,   y1,y2,y3,y4;
		x1 = (((sc.getBounds().xpoints[0] - bounds.getX())))*scale;
		x2 = ((((sc.getBounds().xpoints[1] - bounds.getX()))))*scale;
		x3 = ((((sc.getBounds().xpoints[2] - bounds.getX()))))*scale;
		x4 = ((((sc.getBounds().xpoints[3] - bounds.getX()))))*scale;

		y1 = (((bounds.getHeight() + bounds.getY() - sc.getBounds().ypoints[0])))*scale;
		y2 = (((bounds.getHeight() + bounds.getY() - sc.getBounds().ypoints[1])))*scale;
		y3 = (((bounds.getHeight() + bounds.getY() - sc.getBounds().ypoints[2])))*scale;
		y4 = (((bounds.getHeight() + bounds.getY() - sc.getBounds().ypoints[3])))*scale;

		double [] xs = {x1,	x2, x3, x4};
		double [] ys = {y1, y2, y3, y4};

		g2.draw(new Polygon2D( xs,	ys, 4));
		
		
	}

//UNCOMMENT BLOCK WHEN DEVELOPED
/*
	//Draws a Surface connection for use in black and white printing
	public void paintBWTie(Graphics2D tie2D, SurfaceConnector sc, Rectangle2D bounds, double fromDiameter, double toDiameter, int lines, double scale, boolean selected){
		tie2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!(selected)){tie2D.setColor(Color.BLACK);tie2D.setStroke(LINE_075);}
		else {tie2D.setColor(p_selectedObjectsColour);tie2D.setStroke(DASH_050_002);}

		tie2D.drawLine((int) (((sc.getFrom().getEasting() - bounds.getX()))*scale),	(int) (((bounds.getHeight() + bounds.getY() - sc.getFrom().getNorthing()))*scale),
				(int) (((sc.getTo().getEasting() - bounds.getX()))*scale), (int) (((bounds.getHeight() + bounds.getY() - sc.getTo().getNorthing()))*scale));

		double rx1,rx2,rx3,ry1,ry2,ry3;
		double tx1,tx2,tx3,ty1,ty2,ty3,anchorX,anchorY;
		double x1,x2,x3,y1,y2,y3;
		double xl = (sc.getTo().getEasting() - sc.getFrom().getEasting());//adj length
		double yl = (sc.getTo().getNorthing() - sc.getFrom().getNorthing());//opp length
		double d =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
		double dd  = d - (toDiameter/2);

		double b = 0;

		if(yl < 0 && xl >= 0){//lower right 
			b = (Math.acos((yl/d)));

		}
		else if(yl< 0 && xl <=0){//lower left 
			b = (Math.asin(-(xl/d)))+ Math.toRadians(180);

		}
		else if (xl <0 && yl >= 0){//upper left 
			b = (Math.acos(-(yl/d)))+ Math.toRadians(180);

		}
		else if(yl >= 0 && xl >= 0){//upper right 
			b = (Math.asin(xl/d));

		}
		else
			b= 0;

		rx1 = 0; rx2 = 0; ry1 = 0; ry2 = 0;

		int [] xs={ (int)rx1,	(int)rx2,};
		int [] ns={ (int)ry1,	(int)ry2,};


		for(int i = 1; i>= lines; i++) {
			x1 = (((sc.getTo().getEasting()-(d/i) - bounds.getX() )))*scale;
			x2 = ((((sc.getTo().getEasting()-(d/i)  - bounds.getX())) - (UnitConvert.pixelsToMeters(toDiameter))))*scale;
			x3 = ((((sc.getTo().getEasting()-(d/i)  - bounds.getX())) + (UnitConvert.pixelsToMeters(toDiameter))))*scale;
			y1 = (((bounds.getHeight() + bounds.getY() - (sc.getTo().getNorthing()+ (d/i) + (UnitConvert.pixelsToMeters(dd))))))*scale;
			y2 = ((((bounds.getHeight() + bounds.getY() - sc.getTo().getNorthing()))- (d/i) -(UnitConvert.pixelsToMeters(dd*4))))*scale;
			y3 = ((((bounds.getHeight() + bounds.getY() - sc.getTo().getNorthing()))- (d/i) -(UnitConvert.pixelsToMeters(dd*4))))*scale;
			anchorX = (sc.getTo().getEasting() - bounds.getX())*scale;
			anchorY = (bounds.getHeight() + bounds.getY() - sc.getTo().getNorthing())*scale;
			tx1 = x1-anchorX;
			tx2 = x2-anchorX;
			tx3 = x3-anchorX;
			ty1 = y1-anchorY;
			ty2 = y2-anchorY;
			ty3 = y3-anchorY;
			rx1 = (tx1*Math.cos(b) - ty1*Math.sin(b))+anchorX;
			rx2 = (tx2*Math.cos(b) - ty2*Math.sin(b))+anchorX;
			//		rx3 = (tx3*Math.cos(b) - ty3*Math.sin(b))+anchorX;
			ry1 = (tx1*Math.sin(b) + ty1*Math.cos(b))+anchorY;
			ry2 = (tx2*Math.sin(b) + ty2*Math.cos(b))+anchorY;
			//		ry3 = (tx3*Math.sin(b) + ty3*Math.cos(b))+anchorY;
			if(!(selected)){
				tie2D.drawPolyline(xs,ns,2);
			}	
			else{
				tie2D.setStroke(LINE_300);
				tie2D.drawPolygon(xs, ns, 2);
			}	
		}

	}
	//Draws a surface connection in a complex fashion... This is the standard LiteTie Varidet
	public void paintLTtie (Graphics2D tieLT) {

	}
*/
	//FIXME  need to work out the how to get the format into the coordinate space
	public void paintPrintDimensions(Graphics2D g2, Rectangle2D bounds, Point2D start, double scale, PageFormat pageFormat){
		if(pageFormat !=null){
			double x = bounds.getX()*scale;
			double y = bounds.getY()*scale;
			double w = pageFormat.getImageableWidth()/scale;
			double h = pageFormat.getImageableHeight()/scale;
			g2.setStroke(LINE_250);
			g2.setColor(DARK_CYAN);
			g2.draw(new Rectangle2D.Double(x,y,w,h));
		}
	}
	
	public void paintMarquee(Graphics2D g2, Rectangle2D bounds, Point2D start, Point2D end, double scale, String selectionType, boolean selectRect, boolean selectElipse) {
		
		//		 Draws a rectangle around a a group of holes
		
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2.setColor(new Color(p_selectedObjectsColour.getRed(),p_selectedObjectsColour.getGreen(),p_selectedObjectsColour.getBlue(),20));//transparent Color
		g2.setFont(new Font("Dialog", Font.BOLD,18 ));// font Size for selection type
	
		if (selectRect == true) {
			g2.fillRect(
					(int) ((Math.min(start.getX(), end.getX()) - bounds.getX()) * scale),
					(int) ((bounds.getHeight() + bounds.getY()
							- Math.min(start.getY(), end.getY()) - Math
							.abs(start.getY() - end.getY())) * scale),
							(int) ((Math.abs(start.getX() - end.getX())) * scale),
							(int) ((Math.abs(start.getY() - end.getY())) * scale));
			g2.setStroke(DASH_050_008);
			g2.setColor(p_selectedObjectsColour);
			g2.drawString(selectionType, (int) (((start.getX()) - bounds
					.getX()) * scale), (int) (((bounds.getHeight() + bounds
							.getY()) - (start.getY())) * scale));
			g2.drawRect(
					(int) ((Math.min(start.getX(), end.getX()) - bounds.getX()) * scale),
					(int) ((bounds.getHeight() + bounds.getY()
							- Math.min(start.getY(), end.getY()) - Math
							.abs(start.getY() - end.getY())) * scale),
							(int) ((Math.abs(start.getX() - end.getX())) * scale),
							(int) ((Math.abs(start.getY() - end.getY())) * scale));
		}
		else if(selectElipse == true){
			g2.fillOval((int)((Math.min(start.getX(), end.getX())- bounds.getX())*scale), 
					(int)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale), 
					(int)((Math.abs(start.getX()-end.getX()))*scale), 
					(int)((Math.abs(start.getY()-end.getY()))*scale));

			g2.setStroke(DASH_050_008);
			g2.setColor(p_selectedObjectsColour);

			g2.drawString(selectionType,	
					(int)(((start.getX())- bounds.getX())*scale),
					(int)(((bounds.getHeight() + bounds.getY())-(start.getY()))*scale));

			g2.drawOval((int)((Math.min(start.getX(), end.getX())- bounds.getX())*scale), 
					(int)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale), 
					(int)((Math.abs(start.getX()-end.getX()))*scale), 
					(int)((Math.abs(start.getY()-end.getY()))*scale));
		}

	}
	public void paintZoomMarquee(Graphics2D g2, Rectangle2D bounds, Point2D start, Point2D end, double scale) {
		
		//		 Draws a rectangle around a a group of holes
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setStroke(LINE_050);
		g2.setColor(getInverseColour(p_selectedObjectsColour));
		g2.drawRect((int)((Math.min(start.getX(), end.getX())- bounds.getX())*scale), 
				(int)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale), 
				(int)((Math.abs(start.getX()-end.getX()))*scale), 
				(int)((Math.abs(start.getY()-end.getY()))*scale));

		g2.setStroke(DASH_050_008);
		g2.setColor(p_selectedObjectsColour);
		g2.setFont(new Font("Dialog", Font.BOLD,18 ));
		g2.drawString("ZOOM MARQUEE", 
				(int)((Math.min(start.getX(), end.getX())- bounds.getX())*scale),
				(int)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale));
		g2.drawRect((int)((Math.min(start.getX(), end.getX())- bounds.getX())*scale), 
				(int)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale), 
				(int)((Math.abs(start.getX()-end.getX()))*scale), 
				(int)((Math.abs(start.getY()-end.getY()))*scale));

	}
//	public void paintPrintMarquee(Graphics2D g2, Rectangle2D bounds, Point2D start, Point2D end, boolean isPortrait, double scale) {
//		
//		//		 Draws a rectangle around a a group of holes
////		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//		g2.setStroke(NOT_DASHED_LINE);
//		g2.setColor(Color.YELLOW);
//		g2.drawRect((int)((Math.min(start.getX(), end.getX())- bounds.getX())*scale), 
//				(int)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale), 
//				(int)((Math.abs(start.getX()-end.getX()))*scale), 
//				(int)((Math.abs(start.getY()-end.getY()))*scale));
//		g2.setColor(Color.GREEN);
//		g2.setFont(new Font("Dialog", Font.BOLD,18 ));
//		g2.drawString("PRINT MARQUEE", 
//				(float)((Math.min(start.getX(), end.getX())- bounds.getX())*scale),
//				(float)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale));
//		g2.setStroke(DASHED_LINE3);
//		g2.setColor(Color.RED);
//
//		g2.drawRect((int)((Math.min(start.getX(), end.getX())- bounds.getX())*scale), 
//				(int)((bounds.getHeight() + bounds.getY()-Math.min(start.getY(), end.getY()) - Math.abs(start.getY()-end.getY()))*scale), 
//				(int)((Math.abs(start.getX()-end.getX()))*scale), 
//				(int)((Math.abs(start.getY()-end.getY()))*scale));
//
//	}
	//Polygon Marquee for Selections
	public void paintPolyMarquee(Graphics2D g2, ArrayList<Double> x, ArrayList<Double> y,  Rectangle2D bounds, double scale, String selectionType){
		
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//m2D.setColor(LIGHT_PINK);//transparent Color
		g2.setFont(new Font("Dialog", Font.BOLD,18 ));// font Size for selection type
		g2.setColor(p_selectedObjectsColour);
		g2.setStroke(DASH_050_002);

		int xPoints[]  = new int[x.size()];
		int yPoints[]  = new int[y.size()];
		//xPoints = x.toArray(xPoints);
		//yPoints = y.toArray(yPoints);


		for (int i = 0; i < x.size(); i++){
			xPoints[i] = (int)((x.get(i).doubleValue() - bounds.getX())*scale);
			yPoints[i] = (int)((bounds.getHeight() +bounds.getY() - y.get(i).doubleValue())*scale);
			//			System.out.println("point #"+i+" added to array");
			//			double lastX = x.get(x.size()-1);
			//			double lastY = y.get(x.size()-1);
			g2.setFont(new Font("Dialog", Font.BOLD,18 ));
			g2.drawString(selectionType, (int)((x.get(xPoints.length - 1).doubleValue() - bounds.getX())*scale),(int)((bounds.getHeight() +bounds.getY() - y.get(yPoints.length - 1).doubleValue())*scale));
		}
		int nPoints = x.size();

		g2.setColor(new Color(p_selectedObjectsColour.getRed(),p_selectedObjectsColour.getGreen(),p_selectedObjectsColour.getBlue(),30));
		g2.fillPolygon(xPoints, yPoints, nPoints);
		g2.setColor(p_selectedObjectsColour);
		g2.drawPolygon(xPoints, yPoints, nPoints);			

	}

	public void paintRuler(Graphics2D g2, Rectangle2D bounds, Point2D start, Point2D end, double scale) {
		//		 Draws a rectangle around a a group of holes
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);



		double xl = (start.getX() - end.getX());
		double yl = (start.getY() - end.getY());
		double b = 0;
		double d = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));
		if(yl < 0 && xl >= 0){//lower right 
			b = (Math.acos((yl/d)));
		}
		else if(yl< 0 && xl <=0){//lower left 
			b = (Math.asin(-(xl/d)))+ Math.toRadians(180);
		}
		else if (xl <0 && yl >= 0){//upper left 
			b = (Math.acos(-(yl/d)))+ Math.toRadians(180);
		}
		else if(yl >= 0 && xl >= 0){//upper right 
			b = (Math.asin(xl/d));
		}
		else
			b= 0;
		double a = Math.toDegrees(b);
		//RULER LENGTH DISPLAY FIXED ON 20131202-1215
		//DRAWS A ROUNDED RECTANGLE DISPLAYING THE DISTANCE 20131202-1216
		g2.setColor(CADET_BLUE_T);
		FontMetrics fm = g2.getFontMetrics();
		double textX,textY;
		textX = (((end.getX() - bounds.getX()))*scale);
		textY = (((bounds.getHeight() + bounds.getY() - end.getY()))*scale)-fm.getStringBounds((dec2P.format(d)) + "m", g2).getHeight();
//		g2.fillRoundRect(
//				(int)(textX),
//				(int)(textY), 
//				(int)(fm.getStringBounds(" "+(dec2P.format(d)) + "m", g2).getWidth()+4), 
//				(int)(fm.getStringBounds(" "+(dec2P.format(d)) + "m", g2).getHeight()+4),6, 6);
		g2.setColor(DARK_CYAN);
		g2.setStroke(NOT_DASHED_LINE);
//		g2.drawRoundRect(
//				(int)(textX),
//				(int)(textY), 
//				(int)(fm.getStringBounds(" "+(dec2P.format(d)) + "m", g2).getWidth()+4), 
//				(int)(fm.getStringBounds(" "+(dec2P.format(d)) + "m", g2).getHeight()+4),6, 6);

		g2.setFont(ruler_FONT);
		g2.drawString("   "+(dec2P.format(d)) + "m", (int)((end.getX()- bounds.getX())*scale), 
				(int)((bounds.getHeight() + bounds.getY()-end.getY())*scale));
	//RULER MARKERS THAT PRESENT PREPENDICULAR TO THE LINE	
		g2.setColor(DARK_CYAN);
		g2.draw(new Line2D.Double(((start.getX()- bounds.getX())*scale), 
				((bounds.getHeight() + bounds.getY()-start.getY())*scale), 
				((end.getX()- bounds.getX())*scale), 
				((bounds.getHeight() + bounds.getY()-end.getY())*scale)));
		//METRE INCREMENTS
		for (int i = 0; i <= d ; i++) {
			double tx1 = start.getX() - bounds.getX() - i*Math.sin(Math.toRadians(a));
			double ty1 = bounds.getHeight() + bounds.getY() - start.getY()+ ((i*Math.cos(Math.toRadians(a))));
			double tx2 = tx1 + ((0.2)) * Math.cos(Math.toRadians(90-a-90));
			double ty2 = ty1 + ((0.2)) * Math.sin(Math.toRadians(90-a+90));
			double tx3 = tx1 - ((0.2)) * Math.cos(Math.toRadians(90-a-90));
			double ty3 = ty1 - ((0.2)) * Math.sin(Math.toRadians(90-a+90));

			g2.setStroke(NOT_DASHED_LINE);
			g2.setColor(DARK_CYAN);
			g2.draw(new Line2D.Double( (tx3 * scale), (ty3 * scale),
					 (tx2 * scale),  (ty2 * scale)));
		}
		//HALF METRE INCREMENTS
		for (double i = 0.5; i < d; i++){
			double tx4 = start.getX() - bounds.getX() - (i)*Math.sin(Math.toRadians(a));
			double ty4 = bounds.getHeight() + bounds.getY() - start.getY()+ (((i)*Math.cos(Math.toRadians(a))));
			double tx5 = tx4 + ((0.10)) * Math.cos(Math.toRadians(90-a-90));
			double ty5 = ty4 + ((0.10)) * Math.sin(Math.toRadians(90-a+90));
			double tx6 = tx4 - ((0.10)) * Math.cos(Math.toRadians(90-a-90));
			double ty6 = ty4 - ((0.10)) * Math.sin(Math.toRadians(90-a+90));
			g2.setStroke(NOT_DASHED_LINE);
			g2.setColor(DARK_CYAN);
			g2.draw(new Line2D.Double( (tx5 * scale),  (ty5 * scale),
					(tx6 * scale),  (ty6 * scale)));	
		}

	}
	public void paintAngleMeasure(Graphics2D g2, Rectangle2D bounds, Point2D p1, Point2D p2, Point2D p3, double scale) {
		//		 Draws a ruler and an arc to measure the bearings and angles
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//first line
		double xl1 = (p2.getX() - p1.getX());
		double yl1 = (p2.getY() - p1.getY());
		double b1 = 0;
		double d1 = Math.sqrt(Math.pow(xl1, 2) + Math.pow(yl1, 2));

		if(yl1 < 0 && xl1 >= 0){//lower right 
			b1 = (Math.acos((yl1/d1)));}
		else if(yl1< 0 && xl1 <=0){//lower left 
			b1 = (Math.asin(-(xl1/d1)))+ Math.toRadians(180);}
		else if (xl1 <0 && yl1 >= 0){//upper left 
			b1 = (Math.acos(-(yl1/d1)))+ Math.toRadians(180);}
		else if(yl1 >= 0 && xl1 >= 0){//upper right 
			b1 = (Math.asin(xl1/d1));}
		else
			b1= 0;

		double a1 = Math.toDegrees(b1);
		//Second Line
		double xl2 = (p3.getX() - p1.getX());
		double yl2 = (p3.getY() - p1.getY());
		double b2 = 0;
		double d2 = Math.sqrt(Math.pow(xl2, 2) + Math.pow(yl2, 2));

		if(yl2 < 0 && xl2 >= 0){//lower right ///
			b2 = (Math.acos((yl2/d2)));}
		else if(yl2< 0 && xl2 <=0){//lower left 
			b2 = (Math.asin(-(xl2/d2)))+ Math.toRadians(180);}
		else if (xl2 < 0 && yl2 >= 0){//upper left 
			b2 = (Math.acos(-(yl2/d2)))+ Math.toRadians(180);}
		else if(yl2 >= 0 && xl2 >= 0){//upper right ///
			b2 = (Math.asin(xl2/d2));}
		else
			b2= 0;

		double a2 = Math.toDegrees(b2);
		double angle = Math.toDegrees(b1)-Math.toDegrees(b2);
		//Angle fix
		if(a1-a2<0){angle = 0-(a1-a2);} else if(a1-a2 > 0 ){angle = (360 - (a1-a2));}

		//ANGLE LENGTH DISPLAY FIXED ON 20131202-1215
		//DRAWS A ROUNDED RECTANGLE DISPLAYING THE DISTANCE AND BEARING 20131202-1216
				
				FontMetrics fm = g2.getFontMetrics();
				double textPointX1,textPointY1,textPointX2,textPointY2,textPointX3,textPointY3;
				textPointX1 = (((p2.getX() - bounds.getX()))*scale);
				textPointY1 = (((bounds.getHeight() + bounds.getY() - p2.getY()))*scale)-fm.getStringBounds(" "+(dec2P.format(d1)) + "m "+ dec1P.format(Math.toDegrees(b1)) + "\u00B0", g2).getHeight();
				textPointX2 = (((p3.getX() - bounds.getX()))*scale);
				textPointY2 = (((bounds.getHeight() + bounds.getY() - p3.getY()))*scale)-fm.getStringBounds(" "+(dec2P.format(d2)) + "m "+ dec1P.format(Math.toDegrees(b2)) + "\u00B0", g2).getHeight();
				textPointX3 = (((p1.getX() - bounds.getX()))*scale);
				textPointY3 = (((bounds.getHeight() + bounds.getY() - p1.getY()))*scale)-fm.getStringBounds(" "+ dec1P.format(Math.toDegrees(angle)) + "\u00B0", g2).getHeight();
		//TEXTPOINT1 FILL
				g2.setColor(CADET_BLUE_T);
				g2.fillRoundRect(
						(int)(textPointX1),
						(int)(textPointY1), 
						(int)(fm.getStringBounds(" "+(dec2P.format(d1)) + "m "+ dec1P.format(Math.toDegrees(b1)) + "\u00B0", g2).getWidth()+4), 
						(int)(fm.getStringBounds(" "+(dec2P.format(d1)) + "m "+ dec1P.format(Math.toDegrees(b1)) + "\u00B0", g2).getHeight()+4),6, 6);
				g2.setColor(DARK_CYAN);
				g2.setStroke(NOT_DASHED_LINE);
				g2.drawRoundRect(
						(int)(textPointX1),
						(int)(textPointY1), 
						(int)(fm.getStringBounds(" "+(dec2P.format(d1)) + "m "+ dec1P.format(Math.toDegrees(b1)) + "\u00B0", g2).getWidth()+4), 
						(int)(fm.getStringBounds(" "+(dec2P.format(d1)) + "m "+ dec1P.format(Math.toDegrees(b1)) + "\u00B0", g2).getHeight()+4),6, 6);
				g2.setFont(ruler_FONT);
				g2.drawString(" "+(dec2P.format(d1)) + "m\n"+ dec1P.format(Math.toDegrees(b1)) + "\u00B0", (int)((p2.getX()- bounds.getX())*scale), 
						(int)((bounds.getHeight() + bounds.getY()-p2.getY())*scale));
				//SECOND AND THIRD POINTS
		if(p3!=p1){
			//TEXTPOINT2 FILL
			g2.setColor(CADET_BLUE_T);
			g2.fillRoundRect(
					(int)(textPointX2),
					(int)(textPointY2), 
					(int)(fm.getStringBounds(" "+(dec2P.format(d2)) + "m "+ dec1P.format(Math.toDegrees(b2)) + "\u00B0", g2).getWidth()+4), 
					(int)(fm.getStringBounds(" "+(dec2P.format(d2)) + "m "+ dec1P.format(Math.toDegrees(b2)) + "\u00B0", g2).getHeight()+4),6, 6);
			g2.setColor(DARK_CYAN);
			g2.setStroke(NOT_DASHED_LINE);
			g2.drawRoundRect(
					(int)(textPointX2),
					(int)(textPointY2), 
					(int)(fm.getStringBounds(" "+(dec2P.format(d2)) + "m "+ dec1P.format(Math.toDegrees(b2)) + "\u00B0", g2).getWidth()+4), 
					(int)(fm.getStringBounds(" "+(dec2P.format(d2)) + "m "+ dec1P.format(Math.toDegrees(b2)) + "\u00B0", g2).getHeight()+4),6, 6);
			g2.setFont(ruler_FONT);
			g2.drawString(" "+(dec2P.format(d2)) + "m "+ dec1P.format(Math.toDegrees(b2)) + "\u00B0", (int)((p3.getX()- bounds.getX())*scale), 
					(int)((bounds.getHeight() + bounds.getY()-p3.getY())*scale));
			
			//TEXTPOINT3 FILL
			g2.setColor(CADET_BLUE_T);
			g2.fillRoundRect(
					(int)(textPointX3),
					(int)(textPointY3), 
					(int)(fm.getStringBounds(" " + dec1P.format((angle)) + "\u00B0"+" / " + dec1P.format((360-angle)) + "\u00B0", g2).getWidth()+4), 
					(int)(fm.getStringBounds(" " + dec1P.format((angle)) + "\u00B0"+" / " + dec1P.format((360-angle)) + "\u00B0", g2).getHeight()+4),6, 6);
			g2.setColor(DARK_CYAN);
			g2.setStroke(NOT_DASHED_LINE);
			g2.drawRoundRect(
					(int)(textPointX3),
					(int)(textPointY3), 
					(int)(fm.getStringBounds(" " + dec1P.format((angle)) + "\u00B0"+" / " + dec1P.format((360-angle)) + "\u00B0", g2).getWidth()+4), 
					(int)(fm.getStringBounds(" " + dec1P.format((angle)) + "\u00B0"+" / " + dec1P.format((360-angle)) + "\u00B0", g2).getHeight()+4),6, 6);
			g2.setFont(ruler_FONT);
			g2.drawString(" " + dec1P.format((angle)) + "\u00B0"+" / " + dec1P.format((360-angle)) + "\u00B0", (int)((p1.getX()- bounds.getX())*scale), 
					(int)((bounds.getHeight() + bounds.getY()-p1.getY())*scale));
			
			
		}
		g2.setStroke(NOT_DASHED_LINE);
		g2.setColor(DARK_CYAN);

		//firstline
		g2.drawLine((int)((p1.getX()- bounds.getX())*scale), 
				(int)((bounds.getHeight() + bounds.getY()-p1.getY())*scale), 
				(int)((p2.getX()- bounds.getX())*scale), 
				(int)((bounds.getHeight() + bounds.getY()-p2.getY())*scale));
		//second line
		if(p3!=p1){
			g2.drawLine((int)((p1.getX()- bounds.getX())*scale), 
					(int)((bounds.getHeight() + bounds.getY()-p1.getY())*scale), 
					(int)((p3.getX()- bounds.getX())*scale), 
					(int)((bounds.getHeight() + bounds.getY()-p3.getY())*scale));
		}

		double xl3 = (p1.getX() - p2.getX());
		double yl3 = (p1.getY() - p2.getY());
		double b3 = 0;
		double d3 = Math.sqrt(Math.pow(xl3, 2) + Math.pow(yl3, 2));

		if(yl3 < 0 && xl3 >= 0){//lower right 
			b3 = (Math.acos((yl3/d3)));
		}
		else if(yl3< 0 && xl3 <=0){//lower left 
			b3 = (Math.asin(-(xl3/d3)))+ Math.toRadians(180);
		}
		else if (xl3 <0 && yl3 >= 0){//upper left 
			b3 = (Math.acos(-(yl3/d3)))+ Math.toRadians(180);
		}
		else if(yl3 >= 0 && xl3 >= 0){//upper right 
			b3 = (Math.asin(xl3/d3));
		}
		else
			b3= 0;
		double a3 = Math.toDegrees(b3);
//DRAWS AN ARC AT HALF THE DISTANCE OF THE LINES
		g2.setColor(Color.RED);
		double arcSpot = d1/2;
		if(d1<d2) {arcSpot = d1/2;}
		else arcSpot = d2/2;
		g2.drawArc((int)(((p1.getX()- bounds.getX())-arcSpot)*scale), //left corner
				(int)(((bounds.getHeight() + bounds.getY()-p1.getY())-arcSpot)*scale), // top corner
				(int)((arcSpot*2)*scale), //width
				(int)((arcSpot*2)*scale), //height
				(int)(90-a1), //Start angle
				(int)-angle); //Arc angle
		//first line marks
		for (int i = 0; i <= d1 ; i++) {
			double tx1 = p1.getX() - bounds.getX() - i*Math.sin(Math.toRadians(a3));
			double ty1 = bounds.getHeight() + bounds.getY() - p1.getY()+ ((i*Math.cos(Math.toRadians(a3))));
			double tx2 = tx1 + ((0.2)) * Math.cos(Math.toRadians(90-a1-90));
			double ty2 = ty1 + ((0.2)) * Math.sin(Math.toRadians(90-a1+90));
			double tx3 = tx1 - ((0.2)) * Math.cos(Math.toRadians(90-a1-90));
			double ty3 = ty1 - ((0.2)) * Math.sin(Math.toRadians(90-a1+90));

			g2.setStroke(NOT_DASHED_LINE);
			g2.setColor(DARK_CYAN);
			g2.drawLine((int) (tx3 * scale), (int) (ty3 * scale),
					(int) (tx2 * scale), (int) (ty2 * scale));
		}
		for (double i = 0.5; i < d1; i++){
			double tx4 = p1.getX() - bounds.getX() - (i)*Math.sin(Math.toRadians(a3));
			double ty4 = bounds.getHeight() + bounds.getY() - p1.getY()+ (((i)*Math.cos(Math.toRadians(a3))));
			double tx5 = tx4 + ((0.10)) * Math.cos(Math.toRadians(90-a1-90));
			double ty5 = ty4 + ((0.10)) * Math.sin(Math.toRadians(90-a1+90));
			double tx6 = tx4 - ((0.10)) * Math.cos(Math.toRadians(90-a1-90));
			double ty6 = ty4 - ((0.10)) * Math.sin(Math.toRadians(90-a1+90));
			g2.setStroke(NOT_DASHED_LINE);
			g2.setColor(DARK_CYAN);
			g2.drawLine((int) (tx5 * scale), (int) (ty5 * scale),
					(int) (tx6 * scale), (int) (ty6 * scale));	
		}
		//second line marks
		if(p3!=p1){
			for (int i = 0; i <= d2 ; i++) {
				double tx1 = p1.getX() - bounds.getX() - i*Math.sin(Math.toRadians(a2+180));
				double ty1 = bounds.getHeight() + bounds.getY() - p1.getY()+ ((i*Math.cos(Math.toRadians(a2+180))));
				double tx2 = tx1 + ((0.2)) * Math.cos(Math.toRadians(90-a2-90));
				double ty2 = ty1 + ((0.2)) * Math.sin(Math.toRadians(90-a2+90));
				double tx3 = tx1 - ((0.2)) * Math.cos(Math.toRadians(90-a2-90));
				double ty3 = ty1 - ((0.2)) * Math.sin(Math.toRadians(90-a2+90));

				g2.setStroke(NOT_DASHED_LINE);
				g2.setColor(DARK_CYAN);
				g2.drawLine((int) (tx3 * scale), (int) (ty3 * scale),
						(int) (tx2 * scale), (int) (ty2 * scale));
			}
			for (double i = 0.5; i < d2; i++){
				double tx4 = p1.getX() - bounds.getX() - (i)*Math.sin(Math.toRadians(a2+180));
				double ty4 = bounds.getHeight() + bounds.getY() - p1.getY()+ (((i)*Math.cos(Math.toRadians(a2+180))));
				double tx5 = tx4 + ((0.10)) * Math.cos(Math.toRadians(90-a2-90));
				double ty5 = ty4 + ((0.10)) * Math.sin(Math.toRadians(90-a2+90));
				double tx6 = tx4 - ((0.10)) * Math.cos(Math.toRadians(90-a2-90));
				double ty6 = ty4 - ((0.10)) * Math.sin(Math.toRadians(90-a2+90));
				g2.setStroke(NOT_DASHED_LINE);
				g2.setColor(DARK_CYAN);
				g2.drawLine((int) (tx5 * scale), (int) (ty5 * scale),
						(int) (tx6 * scale), (int) (ty6 * scale));	
			}
		}

	}

	//HOLE ID STRING	
	public void paintHoleID(Graphics2D g2, Dummy dummy, Hole hole, Rectangle2D bounds, double scale, double enhance){
		
		g2.setColor(p_holeTextColour);
		g2.setStroke(LINE_025);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics fm = g2.getFontMetrics();
		g2.setFont(default_FONT);
		int pixelOffset = 1;
		if (dummy instanceof Hole || hole instanceof Hole) {
			g2.drawString(Integer.toString(hole.getHoleID()),	(int) (((hole.getX() - bounds.getX())) * scale)- (fm.stringWidth(Integer.toString(hole.getHoleID()))/2),
					(int) (((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale)-pixelOffset);
		}
		if(dummy instanceof Dummy && (!(dummy instanceof Hole))){
			g2.drawString(Integer.toString(dummy.getHoleID()), (int) (((dummy.getX() - bounds.getX())) * scale),
					(int) (((bounds.getHeight() + bounds.getY() - dummy.getY()) - (UnitConvert.pixelsToMeters(LiteTieTRIAL.averageSize * enhance * 1.1)) / 2) * scale));
		}

	}
	//HOLE ANGLE STRING
	public void paintAngle(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale, double enhance){
		
		g2.setColor(p_holeAngleTextColour);
		g2.setStroke(LINE_075);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(default_FONT);
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString(
				Double.toString(hole.getAngle()), 
				(int)(((hole.getX() - bounds.getX()))*scale), 
				(int)(((bounds.getHeight() + bounds.getY() - hole.getY()) - (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance*1.1))/2)*scale));

	}

	//LABEL ONE STRING
	public void paintHoleLabel1(Graphics2D g2, Dummy dummy, Hole hole, Rectangle2D bounds, double scale, double enhance){
		
		g2.setColor(p_labelOneTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(default_FONT);
		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance*1.1));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString(hole.getLabelOne(), (int)((x1 + hEdge*Math.cos(90))*scale), (int)((y1 - hEdge*Math.sin(90))*scale));

	}
	//LABEL TWO STRING
	public void paintHoleLabel2(Graphics2D g2, Dummy dummy, Hole hole, Rectangle2D bounds, double scale, double enhance){
		
		g2.setColor(p_labelTwoTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(default_FONT);
		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance*1.1));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString(hole.getLabelTwo(), (int)((x1 + hEdge*Math.cos(90))*scale), (int)((y1 - hEdge*Math.sin(90))*scale));

	}
	//LABEL THREE STRING
	public void paintHoleLabel3(Graphics2D g2, Dummy dummy, Hole hole, Rectangle2D bounds, double scale, double enhance){
		
		g2.setColor(p_labelThreeTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(default_FONT);
		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance*1.1));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString(hole.getLabelThree(), (int)((x1 + hEdge*Math.cos(90))*scale), (int)((y1 - hEdge*Math.sin(90))*scale));

	}
	//SUBDRILL STRING
	public void paintSubdrill(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale, double enhance){
		
		g2.setColor(p_subdrillTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()*enhance*1.1));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString((dec1P.format(hole.getSubdrill())), (int)((x1 + hEdge*Math.cos(180))*scale), (int)((y1 - hEdge*Math.sin(180))*scale));

	}
	//COLLAR LEVEL STRING	
	public void paintZLevel(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale){
		
		g2.setColor(p_collarLocationTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString((dec1P.format(hole.getZ())), (int)((x1+hEdge*Math.cos(315))*scale), (int)((y1 - hEdge*Math.sin(315))*scale));

	}
	//FLOOR LEVEL STRING
	public void paintFloorRL(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale){
		
		g2.setColor(p_floorLocationTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString((dec1P.format(hole.getFloorRL())), (int)((x1+hEdge*Math.cos(0))*scale), (int)((y1 + hEdge*Math.sin(0))*scale));

	}
	//HOLE TOE LEVEL VALUE
	public void paintToeRL(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale){
		
		g2.setColor(p_toeLocationTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - (hole.getY()));
		g2.setFont(default_FONT);
		g2.drawString((dec1P.format(hole.getToeRL())), (int)((x1 + hEdge*Math.cos(90))*scale), (int)((y1 + hEdge*Math.sin(90))*scale));
		System.out.println(hole.getToeRL() + "Visualise2D.class");
	}

	//HOLE LENGTH VALUE
	public void paintHoleLength(Graphics2D g2, Hole hole, Rectangle2D bounds,double enhance, double scale){
		
		g2.setColor(p_holeLengthTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int pixelOffset = 2;
		//GET THE FONTMETRICS
		FontMetrics fm = g2.getFontMetrics();
		//GET THE DIAMETER OF THE HOLE
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()));
		// GET THE LOCATION OF THE HOLE
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		//SET THE COLOUR OF THE TEXT
		g2.setColor(p_holeLengthTextColour);
		//SET THE FONT TO USE
		g2.setFont(default_FONT);
		//h2.setFont(new Font("Monaco", Font.BOLD, 8));
		//DRAWGLYPHVECTOR() TO OVERCOME THE PRINTING ISSUE RELATED TO THE OSX PORT OF JAVA7u09 - MAY NOT NEED THIS WITH FUTURE UPDATES.
		/*h2.drawGlyphVector(
				h2.getFont().createGlyphVector(h2.getFontRenderContext(), 
				dec1P.format(hole.getHoleLength())),
				(int)((x1+hEdge*Math.cos(300))*scale)- (fm.stringWidth(dec1P.format(hole.getHoleLength()))/2), 	
				(int)((y1 + (hEdge *enhance*1.1)/2)*scale) +(fm.getHeight()/2) +pixelOffset
				);*/
		
		//USING - DRAWSTRING() DOESN'T WORK IN JAVA 7u09 ON MAC OSX 
		 //NOW... WORKS WITH AN UPDATE TO JRE7u45
		g2.drawString(
				(dec1P.format(hole.getHoleLength())), 	
				(int)((x1+hEdge*Math.cos(300))*scale)- (fm.stringWidth(dec1P.format(hole.getHoleLength()))/2), 	
				(int)((y1 + (hEdge *enhance*1.1)/2)*scale) +(fm.getHeight()/2) +pixelOffset
				);

//		g2.drawString(Integer.toString(hole.getHoleID()),		(int) (((hole.getEasting() - bounds.getX())) * scale)- (fm.stringWidth(Integer.toString(hole.getHoleID()))/2),
//				(int) (((bounds.getHeight() + bounds.getY() - hole.getNorthing()) - (UnitConvert.pixelsToMeters(hole.getDiameter() * enhance * 1.1)) / 2) * scale)-pixelOffset);
	}

	//DIAMETER STRING VALUE	
	public void paintDiameter(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale){
		
		g2.setColor(p_holeDiameterTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString((dec1P.format(hole.getDiameter())), (int)((x1+hEdge*Math.cos(300))*scale), (int)((y1 - hEdge*Math.sin(300))*scale));

	}
	//BEARING STRING	
	public void paintBearing(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale){
		
		g2.setColor(p_holeBearingTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());
		g2.setFont(default_FONT);
		g2.drawString((dec1P.format(hole.getBearing())+"\u00B0"), (int)((x1+hEdge*Math.cos(300))*scale), (int)((y1 - hEdge*Math.sin(300))*scale));

	}
	//BENCH HEIGHT VALUE	
	public void paintBench(Graphics2D g2, Hole hole, Rectangle2D bounds, double scale){
		
		g2.setColor(p_benchHeightTextColour);
		g2.setStroke(LINE_075);
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(hole.getDiameter()));
		double x1 =((hole.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - hole.getY());

		g2.setFont(default_FONT);
		g2.drawString((dec1P.format(hole.getBench())), (int)((x1+hEdge*Math.cos(300))*scale), (int)((y1 - hEdge*Math.sin(300))*scale));

	}
	public void paintSurfaceTimes(Graphics2D g2, SurfaceConnector s, InitiationPoint ip, Dummy d, Color c, Rectangle2D bounds,double scale, double enhanceTie, double enhance ){
		
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g2.getFontMetrics();
		double hEdge = (UnitConvert.pixelsToMeters(LiteTieTRIAL.averageSize));
		double x1 =((d.getX() - bounds.getX()));
		double y1 = (bounds.getHeight() + bounds.getY() - d.getY());
		int pixelOffset = 2;
		if (d instanceof Hole) {
			hEdge = (UnitConvert.pixelsToMeters(((Hole) d).getDiameter()* enhance)/2);
		}
		else
			hEdge = (UnitConvert.pixelsToMeters(LiteTieTRIAL.averageSize * enhance)/2);
		
		x1 =((d.getX() - bounds.getX()));
		y1 = (bounds.getHeight() + bounds.getY() - d.getY());
		
		g2.setColor(c);
		g2.setFont(default_FONT);
		fm = g2.getFontMetrics();
/*		gTimes.drawGlyphVector(
				gTimes.getFont().createGlyphVector(gTimes.getFontRenderContext(), 
				dec0P.format(s.getTime())),
				(int)((x1+hEdge*Math.cos(300))*scale)- (fm.stringWidth(dec0P.format(s.getTime()))/2), 	
				(int)((y1 + (hEdge *enhance*1.1)/2)*scale) +(fm.getHeight()/2) +pixelOffset
				);*/
	//JAVA 7 DRAWSTRING() ISSUE 
	  	g2.drawString((dec0P.format(s.getTime())), 
				(int)((x1+hEdge*Math.cos(180))*scale - fm.getStringBounds(dec0P.format(s.getTime()), g2).getWidth()),
				(int)((y1 - hEdge*Math.sin(0))*scale+ fm.getStringBounds(dec0P.format(s.getTime()), g2).getHeight()));
		if (d == ip.getIPDummy()) {
			g2.drawString(dec0P.format(ip.getIPTime()), 
					(int)((x1+hEdge*Math.cos(180))*scale - fm.getStringBounds(dec0P.format(s.getTime()), g2).getWidth()),
					(int)((y1 - hEdge*Math.sin(0))*scale+ fm.getStringBounds(dec0P.format(s.getTime()), g2).getHeight()));
	  	}	
				
	}
//DOESN'T WORK 20131222 - ARRRRRRRRGGGGGGGHHHHHHHH
	public void paintGraphContours(Graphics2D g2, World world) throws Exception {
		//Class to store the three attributes for mapping.
				class Triple {	
					int t;	double x;	double y;   
					public Triple(int time, double xLoc, double yLoc) { 
						t = time;	
						x = xLoc;	
						y = yLoc;  
						}	
					}
		for(Pattern p: world.getPatternList().values()) {
			Contour graph = new Contour();
			graph.square = true;
			graph.drawgrid = false;
			graph.datarect = new Rectangle((int)p.getBounds().getX(), (int)p.getBounds().getY(), (int) p.getBounds().getWidth(), (int)p.getBounds().getHeight());
			graph.noContours = false;
			graph.paintAll = true;
			
			if(!p.getiPList().isEmpty()) {
				//Create a new Triple
				Triple store;
				//Create a new TreeMap to store all Triples
				TreeMap<Integer, Triple> map = new TreeMap<Integer, Triple>();
				//Set a counting key
				int key = 0;
				//Set the range for the rendered result
				double xmin = p.getBounds().getMinX();
				double ymin = p.getBounds().getMinY();
				double xmax = p.getBounds().getMaxX();
				double ymax = p.getBounds().getMaxY();
				//Do for all surface connectors in the pattern
				for(SurfaceConnector sc: p.getSurfaceList().values()) {
					//Put values to map in the Triple
					store = new Triple(sc.getTime(),sc.getTo().getX(),sc.getTo().getY());
					//Put the Triple in the map
					map.put(key, store);
					//Increment the counting key
					key++;
				}		
				//Create a 1 dimensional array for storing time
				double [] time_1DArray = new double[map.size()];
				//Create a 2 dimentional array for storing locations
				double [] [] location_2DArray = new double[map.size()][2];
				//Create a 2 dimentional array for storing time, x, y so they can be sorted in ascending time order.
				double [] [] timeLocation_2DArray = new double[map.size()][3];
				//graph data X should go in the even numbers and Y in the odds so add the values in this fashion.
				double[] graph_1DArray = new double[map.size()*2];
				//Do if there are values in the Treemap
				if (!(map.values().isEmpty())) {
					//Loop through the rows
					for (int row = 0; row < time_1DArray.length; row++) {
						//Assign the values in the Triple TreeMap to the doubles 
						double tt = map.get(row).t;
						double xx = map.get(row).x;
						double yy = map.get(row).y;
						//Loop through the columns
						for(int column = 0; column<3; column++) {
							//Put the time in the first column
							if(column == 0) 		{timeLocation_2DArray[row][column] = tt;}
							//Put the x in the second column
							else if(column == 1)	{timeLocation_2DArray[row][column] = xx;}
							//Put the y in the third column
							else if(column == 2)	{timeLocation_2DArray[row][column] = yy;}
						}
					}
				}
				//Sort the array in order ascending from 0 to ∞ - based on time order
				Arrays.sort(timeLocation_2DArray, new Comparator<double[]>() {
						public int compare(double[] o1, double[] o2) {
							if (o1[0] > o2[0])		return 1;    // o1 comes after o2
							else if (o1[0] < o2[0])	return -1;   // o1 comes before o2
							else {					return 0;}
						}
					});
				for(int row = 0; row<timeLocation_2DArray.length; row++) {
					//place sorted values in to the appropriate arrays.
					for(int column = 0; column < 3; column++) {
						
						//Time goes in the time array
						if(column == 0) {
							time_1DArray[row] = (timeLocation_2DArray[row][column]);
						}
						//X values go in the first column of the Location Array
						else if(column == 1) {
//							location_2DArray[row][column-1] = UnitConvert.metersToPixels(((timeLocation_2DArray[row][column])+bounds.getX()));
							location_2DArray[row][column-1] = (timeLocation_2DArray[row][column]);
						}
						//Y values go in the second column of the Location Array
						else if (column == 2) {
//							location_2DArray[row][column-1] =  UnitConvert.metersToPixels(((bounds.getHeight() - bounds.getY()) + (timeLocation_2DArray[row][column])));	
							location_2DArray[row][column-1] =  (timeLocation_2DArray[row][column]);
						}

					}
					
					//Print the Arrays for confirmation and checking - Comment out when working
					LiteTieTRIAL.setConsoleOutput(
											"\nSorted Values T"+
											time_1DArray[row]+
											", XY"+
											Arrays.toString(location_2DArray[row]));
					}
				for(int row = 0; row<graph_1DArray.length; row++) {
					for(int column = 0; column < 2; column++) {
							if (column == 0) {
								if (row%2 == 0) {
									graph_1DArray[row] = location_2DArray[row / 2][column];
								}
							} else if (column == 1) {
								graph_1DArray[row] = location_2DArray[row / 2][column];
							}
						
					}
				}

				graph.setLevels(time_1DArray, time_1DArray.length/4);
				
				graph.setRange(p.getBounds().getX(), p.getBounds().getMaxX(), p.getBounds().getY(), p.getBounds().getMaxY());
				graph.setGrid(time_1DArray, time_1DArray.length, time_1DArray.length);
				
				DataSet data = new DataSet(graph_1DArray, graph_1DArray.length/2);

				graph.attachDataSet(data);

//				g2 = (Graphics2D) graph.getGraphics();
				}

			}
	
		
	}
	
	public void paintContourPlot(Graphics2D g2, World world, double scale){
		
	}
	public double [][]createContourData(){
		double data [][] = new double[50][50];
		double x,y;
        for(int i=0;i<data.length;i++) {
                for(int j=0;j<data[0].length;j++) {
                        x=(i-data.length/2.0)*3.0/data.length;
                        y=(j-data[0].length/2.0)*3.0/data[0].length;
                        data[i][j]=Math.exp(-x*x-y*y);
                }
        }
		
		return data;
	}


	public void paintContours(Graphics2D g2, World world, Rectangle2D bounds, Rectangle2D canvas, double scale) {

		//Class to store the three attributes for mapping.
		class Triple {	
			int t;	double x;	double y;   
			public Triple(int time, double xLoc, double yLoc) { 
				t = time;	
				x = xLoc;	
				y = yLoc;  
				}	
			}
		  
		//Set attributes of the Renderer
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//Do for the Pattern in the World 
		for(Pattern p: world.getPatternList().values()) {
			//Do if the pattern has at least 1 initiation point
			if(!p.getiPList().isEmpty()) {
				//Create a new Triple
				Triple store;
				//Create a new TreeMap to store all Triples
				TreeMap<Integer, Triple> map = new TreeMap<Integer, Triple>();
				//Set a counting key
				int key = 0;
				//Set the range for the rendered result
				double xmin = p.getBounds().getMinX();
				double ymin = p.getBounds().getMinY();
				double xmax = p.getBounds().getMaxX();
				double ymax = p.getBounds().getMaxY();
				//Do for all surface connectors in the pattern
				for(SurfaceConnector sc: p.getSurfaceList().values()) {
					//Put values to map in the Triple
					store = new Triple(sc.getTime(),sc.getTo().getX(),sc.getTo().getY());
					//Put the Triple in the map
					map.put(key, store);
					//Increment the counting key
					key++;
				}		
				//Create a 1 dimensional array for storing time
				double [] time_1DArray = new double[map.size()];
				//Create a 1 dimensional array for storing time
				double [] locX_1DArray = new double[map.size()];
				//Create a 1 dimensional array for storing time
				double [] locY_1DArray = new double[map.size()];
				//For Chart
				double [] [] chartSet = new double[3][map.size()];
				//Create a 2 dimentional array for storing locations
				double [] [] location_2DArray = new double[map.size()][2];
				//Create a 2 dimentional array for storing time, x, y so they can be sorted in ascending time order.
				double [] [] timeLocation_2DArray = new double[map.size()][3];
				//Do if there are values in the Treemap
				if (!(map.values().isEmpty())) {
					//Loop through the rows
					for (int row = 0; row < time_1DArray.length; row++) {
						//Assign the values in the Triple TreeMap to the doubles 
						double tt = map.get(row).t;
						double xx = map.get(row).x;
						double yy = map.get(row).y;
						//Loop through the columns
						for(int column = 0; column<3; column++) {
							//Put the time in the first column
							if(column == 0) 		{timeLocation_2DArray[row][column] = tt;}
							//Put the x in the second column
							else if(column == 1)	{timeLocation_2DArray[row][column] = xx;}
							//Put the y in the third column
							else if(column == 2)	{timeLocation_2DArray[row][column] = yy;}
						}
					}
				}
				//Sort the array in order ascending from 0 to ∞ - based on time order
				Arrays.sort(timeLocation_2DArray, new Comparator<double[]>() {
						public int compare(double[] o1, double[] o2) {
							if (o1[0] > o2[0])		return 1;    // o1 comes after o2
							else if (o1[0] < o2[0])	return -1;   // o1 comes before o2
							else {					return 0;}
						}
					});
				for(int row = 0; row<timeLocation_2DArray.length; row++) {
					//place sorted values in to the appropriate arrays.
					for(int column = 0; column < 3; column++) {
						
						//Time goes in the time array
						if(column == 0) {
							time_1DArray[row] = (timeLocation_2DArray[row][column]);
						}
						//X values go in the first column of the Location Array
						else if(column == 1) {
//							location_2DArray[row][column-1] = UnitConvert.metersToPixels(((timeLocation_2DArray[row][column])+bounds.getX()));
							location_2DArray[row][column-1] = (timeLocation_2DArray[row][column]);
							locX_1DArray[row] = timeLocation_2DArray[row][column];
						}
						//Y values go in the second column of the Location Array
						else if (column == 2) {
//							location_2DArray[row][column-1] =  UnitConvert.metersToPixels(((bounds.getHeight() - bounds.getY()) + (timeLocation_2DArray[row][column])));	
							location_2DArray[row][column-1] =  (timeLocation_2DArray[row][column]);
							locY_1DArray[row] = timeLocation_2DArray[row][column];
						}

					}
					
							
					//Print the Arrays for confirmation and checking - Comment out when working
					LiteTieTRIAL.setConsoleOutput(
											"\nSorted Values T"+
											time_1DArray[row]+
											", XY"+
											Arrays.toString(location_2DArray[row]));
				}
	
				//Create a new marching square algorithm
				MarchingSquares marchingSquares = new MarchingSquares();
				//Create the iso lines from the algorithm using the above sorted arrays.
				GeneralPath[] isolines = marchingSquares.mkIsos(location_2DArray, time_1DArray); 
				//PRNTLN For checking the ascii representation of the IsoLines
//				System.out.println(marchingSquares.asciiPrintContours(location_2DArray, time_1DArray));				
				// Convert isos from array coords to world UTM coords.
				//Create a Transformation
				AffineTransform xf = new AffineTransform();
				//Set the transforms translate location
				xf.translate(xmin, ymin);
				//PRNTLN the coords for checking
				System.out.println("Translate to = X"+xmin +" Y"+ymin);
				//Set the transforms scale
				xf.scale(scale,scale);
				//PRNTLN the Scale for checking
				System.out.println("Model Scale = "+scale);
		
			    xf.translate(-1, -1); // Because MxN data was padded to (M+2)x(N+2).
				for (int i = 0; i < isolines.length; i+=4) {
					Shape iso = (isolines[i]); // Remapped every pan & zoom.
					System.out.println(	
							"IsoLine Bounds #"+i+" X"+
							iso.getBounds2D().getX()+" Y"+
							iso.getBounds2D().getY()+" W"+
							iso.getBounds2D().getWidth()+" H"+
							iso.getBounds2D().getHeight());
					isolines[i].transform(xf); // Permanent mapping to world coords.
					System.out.println(	
							"IsoLine Transformed Bounds #"+i+" X"+
							iso.getBounds2D().getX()+" Y"+
							iso.getBounds2D().getY()+" W"+
							iso.getBounds2D().getWidth()+" H"+
							iso.getBounds2D().getHeight());
					g2.setColor(Color.BLACK);
					g2.setStroke(LINE_300);
					g2.draw(iso); // Color iso.
					g2.setStroke(DASH_200_008);
					g2.setColor(Color.YELLOW);
					g2.draw(iso); // Color iso.
				}
			}
		}
	}

	public void paintGridLines(Graphics2D g2, Rectangle2D bounds, Rectangle2D gridShape, double gridWidth, double gridHeight){
		
		//Sets the line width and colour of the grid lines
		g2.setStroke(LINE_025);
		g2.setColor(p_gridLinesAndTextColour);
		
		double canvasWidth = bounds.getWidth();
		double canvasHeight = bounds.getHeight();
		double canvasStartX = bounds.getMinX();
		double canvasStartY = bounds.getMinY();
		
		double rows = (canvasHeight/gridShape.getHeight());
		double cols = (canvasWidth/gridShape.getWidth());
		
		int startHeight = (int) (Math.floor(canvasStartY/gridHeight)*canvasStartY);
		int startWidth = (int) (Math.floor(canvasStartX/gridWidth)*canvasStartX);
		
		
		
		double counter;
		
		for (counter = 0; counter< rows; counter++){
			g2.draw(new Line2D.Double(canvasStartX, startHeight + (counter*gridHeight), canvasStartX + canvasWidth, startHeight+(counter*gridHeight)));
		}
		for (counter = 0; counter< cols; counter++){
			g2.draw(new Line2D.Double(canvasStartY + (counter*gridWidth), canvasStartY + canvasHeight, startWidth + (counter*gridWidth), canvasStartY));
		}
		
	}
	
	
	public void paintGrid(Graphics2D grid2D, Rectangle2D bounds, Rectangle2D gridDisplay, Rectangle2D labelDisplay) {

		//		grid2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		grid2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		grid2D.setStroke(LINE_025);
		grid2D.setColor(p_gridLinesAndTextColour);
		{

			//Easting Labels and lines
			//Easting amount to show on the screen.	
			double textMeters = labelDisplay.getX();

			//counts to draw lines within canvasSize() in LiteTie
			for (int i = 0; i < bounds.getWidth(); i = i+ (int) gridDisplay.getWidth()) { 
				//Easting text displayed horizonatally
				String horiText = dec2P.format(textMeters) + "mE"; 
				// Each iteration is the current value plus the rectangles with which is currently 10 so this works well
				textMeters = textMeters + labelDisplay.getWidth(); 
				grid2D.drawLine(i, 0, i, (int) (bounds.getHeight()));
				grid2D.setFont(grid_FONT);
				grid2D.drawString(horiText, i, (int) grid2D.getFontMetrics()
						.getHeight());
				grid2D.setFont(grid_FONT);
				grid2D.drawString(horiText, i, (int) bounds.getHeight());
			}

			//For Northings
			//Northing Amounts shown on the screen
			textMeters = labelDisplay.getY(); 

			for (double i = (bounds.getHeight()); i > 0; i = i- gridDisplay.getHeight()) {

				String vertText = dec2P.format(textMeters) + "mN";
				textMeters = textMeters + labelDisplay.getHeight();

				grid2D.draw(new Line2D.Double(0, i, bounds.getWidth(), i));
				grid2D.setFont(grid_FONT);
				grid2D.drawString(vertText, 0, (int) (i + grid2D
						.getFontMetrics().getHeight()));
				//System.out.println("gridDraw vertical text");
				grid2D.setFont(grid_FONT);
				grid2D.drawString(vertText, ((int) bounds.getWidth())
						- grid2D.getFontMetrics().stringWidth(vertText),
						(int) (i + grid2D.getFontMetrics().getHeight()));


			}
		}		

	}



}
// Fix to take notice of the bounds
// Put northings 
