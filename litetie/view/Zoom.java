package litetie.view;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import litetie.model.UnitConvert;

public class Zoom {
	
	//Class to increase the visable aspects of the displayed canvas
	
	//Constants
	public static final double IN = 2;
	public static final double OUT = 0.5;
	public static double scalingFactor;
	public static boolean zoomToFit;
	
	//Methods
	public static Rectangle2D zoom(Rectangle2D bounds){
		
		bounds = new Rectangle2D.Double(bounds.getX()*scalingFactor, bounds.getY()*scalingFactor, bounds.getWidth()*scalingFactor, bounds.getHeight()*scalingFactor);
		
		return
		 	bounds;
	}
	public static Rectangle2D zoomMarquee(double x, double y, double w, double h){		
		return
//				new Rectangle2D.Double(x, y, w, h);	
			new Rectangle2D.Double(x*scalingFactor, y*scalingFactor, w*scalingFactor, h*scalingFactor);
	}
	
	public static void setScalingFactor(double newScalingFactor){
		
		scalingFactor = newScalingFactor;
		
	}
	
	public static double getScalingFactor() {
		return
			scalingFactor;
	}
	
	public static void zoomIn() {
		scalingFactor = scalingFactor*IN;
	}
	
	public static void zoomOut() {
		scalingFactor = scalingFactor*OUT;
	}
	
	public static Point2D zoomIn(double x, double y){		
		scalingFactor = scalingFactor*IN;
		return	
			new Point2D.Double(x,y);
	}
	
	public static void setZoomToFit(boolean newZoomToFit) {
		
		zoomToFit = newZoomToFit;
	}
	public static boolean isZoomToFit() {
		return
			zoomToFit;
	}
	
//ACTIONS
	class ZoomOutAction extends AbstractAction {
	    public ZoomOutAction(String text, ImageIcon icon,
	                      String desc, Integer mnemonic) {
	        super(text, icon);
	        putValue(SHORT_DESCRIPTION, desc);
	        putValue(MNEMONIC_KEY, mnemonic);
	    }
	    public void actionPerformed(ActionEvent e) {
	        
	    }
	}
	
	
}
