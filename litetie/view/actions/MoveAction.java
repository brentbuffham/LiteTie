package litetie.view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Collections;


import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import litetie.controller.Transform;
import litetie.listeners.UndoableMoveObject;
import litetie.model.Coordinate;
import litetie.model.LTPoint3D;
import litetie.model.NegativeNumberException;
import litetie.model.UnitConvert;
import litetie.view.Zoom;

@SuppressWarnings("serial")
public class MoveAction extends AbstractAction {

	/**
	 * The host provides all of the state of the interface
	 */
	private final ActionHost host;
	
	/**
	 * Constructs the move action, attaching it to the
	 * given host GUI.
	 * 
	 * @param host The hosting GUI
	 */
	public MoveAction(ActionHost host) {
		super("Move Selected Items", new ImageIcon(MoveAction.class.getResource("/icons_LiteTie_v2/move.png")));
		
		this.host = host;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		host.setCurrentMouseMover(null); //set as nothing so that the new mouse listener can be applied later in the code
		host.setStrokeColourLabelText("Stroke");
		host.setStrokeColourWellBackground(host.getCurrentColour());
		host.setCurrentMouseClicker(firstClicker);
	}
	/**
	 * The easting of the first click
	 */
	private double xClick;
	/**
	 * The northing of the first click
	 */
	private double yClick;
	/**
	 * The elevation of the first click
	 */
	private double zClick;
	
	/**
	 * The offset between the click and the centre of the selection in easting
	 */
	private double xOffset;
	/**
	 * The offset between the click and the centre of the selection in northing
	 */
	private double yOffset;
	
	/**
	 * The offset between the click and the centre of the selection in elevation
	 */
	private double zOffset;
	
	private MouseListener firstClicker = new MouseAdapter() {
		//Mouse Event method
		public void mouseReleased(MouseEvent e) {
			//Get the Mouse Location and store it.
			Rectangle2D canvas = host.getCanvasSize();
			yClick = canvas.getY() + canvas.getHeight() - UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();  //mouse's X location in the user co ords
			xClick = canvas.getX() + UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor(); //mouse's Y location in the user co ords
			zClick = 0;
			Rectangle2D bounds = new Rectangle2D.Double();
			host.getWorld().addAllCoordinates();

			Object selection = host.getSelection();
			if (selection == null) { // nothing selected yet
				selection = host.selectFirstObjectNear(xClick, yClick, zClick);
				if (selection == null) {
					// nothing near the click location, wait for the next click
					return;
				}
			}
			if (!(selection instanceof Collection<?>)) {
				// Put any single object into a collection so we only have one set of code
				host.setSelection(Collections.singleton(selection));
			}
			
			// Reaching this point means one or more items are selected
			// and are stored in a collection
			for (Coordinate c: host.getWorld().getCoordList().values()) {
				boolean isSelected = ((Collection<?>)selection).contains(c);
				//STEP THROUGH CHECK 
				if (isSelected) {
					if (bounds.isEmpty()) {
						bounds = c.getBounds();
					} else {
						bounds = bounds.createUnion(c.getBounds());
					}
				}
			}
			host.setCentreBounds(bounds.getCenterX(), bounds.getCenterY(), 0);
			host.setDelta(0, 0, 0);
			
			yOffset = bounds.getCenterY() - yClick;
			xOffset = bounds.getCenterX() - xClick;
			
			host.setCurrentMouseClicker(nextClicker);
			host.setCurrentMouseMover(mover);
		}
	};
	
	private MouseListener nextClicker = new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			Rectangle2D canvas = host.getCanvasSize();
			double yMouseClick = canvas.getY() + canvas.getHeight() - UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor() /*+ yOffset*/;
			double xMouseClick = canvas.getX() + UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor() /*+ xOffset*/;

			try {
				double [] differences = Transform.translateCoordinateCollection((Collection<?>) host.getSelection(), xMouseClick, yMouseClick);
					
				host.setMeasurementStatusText("X"+host.getDecimalFormatMetres1().format(differences[0]), "Y"+host.getDecimalFormatMetres1().format(differences[1]));
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NegativeNumberException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			host.updateCanvas();
				
			//UNDO RECORD
			host.addUndo(new UndoableMoveObject(host.getWorld(), host.getSelection(), new LTPoint3D.Double(xClick + xOffset, yClick + yOffset, 0), new LTPoint3D.Double(xMouseClick, yMouseClick, 0)));
			System.out.println("Add Coordinate Move - UndoableMoveObject()");
			host.setSelection(null);
			host.updateCanvas();
			host.updateTrees();
			host.setCurrentMouseClicker(firstClicker);
			host.setCurrentMouseMover(host.getDefaultMouseMover());
			host.setCentreBounds(0, 0, 0);
			host.setDelta(0, 0, 0);
		}
	};
	
	private MouseMotionListener mover = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent e){
			Rectangle2D canvas = host.getCanvasSize();
			double yMouseClick = canvas.getY() + canvas.getHeight() - UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
			double xMouseClick = canvas.getX() + UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();
			host.setDelta(xMouseClick - xClick - xOffset, yMouseClick - yClick - yOffset, 0);

			try {
				
				Transform.translateCoordinateCollection((Collection<?>) host.getSelection(), xMouseClick, yMouseClick);
				//System.out.println("translated - move");
				//selected = c;
						
			} catch (NumberFormatException e1) {
				System.out.println("translated - Error");
			} catch (NegativeNumberException e1) {
				System.out.println("translated - Error");
			}

			host.updateCanvas();
		}
	};
}
