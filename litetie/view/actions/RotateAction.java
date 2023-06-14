package litetie.view.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import litetie.model.LTPoint3D;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import litetie.LiteTieTRIAL;
import litetie.controller.Transform;
import litetie.listeners.UndoableRotateBearing;
import litetie.model.Coordinate;
import litetie.model.LTPoint3D;
import litetie.model.NegativeNumberException;
import litetie.model.UnitConvert;
import litetie.view.Zoom;

@SuppressWarnings("serial")
public class RotateAction extends AbstractAction {

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
	public RotateAction(ActionHost host) {
		super("Rotate Selected Items", new ImageIcon(RotateAction.class.getResource("/icons_LiteTie_v2/rotate.png")));
		
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
	
	private double lastX;
	private double lastY;
	
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
	
	/**
	 * Boolean flag that is set by the currentKeyEvent for when the "C" key is pressed down.
	 */
	private boolean isVKCDown;
	/**
	 * Boolean flag that is set by the currentKeyEvent for when the "P" key is pressed down.
	 */
	private boolean isVKPDown;
	/**
	 * Boolean flag that is set by the currentKeyEvent for when the "SHIFT" key is pressed down.
	 */
	private boolean isVKShiftDown;
	

	Collection<Coordinate> copy; 
	
	/**
	 * "currentKeyEvent" KeyListener to listen to when the various keys are pressed and then set the boolean flag
	 * allowing the code in the mouse motion listener to run.
	 */
	public KeyListener currentKeyEvent = new java.awt.event.KeyAdapter(){ 
		@Override
		public void keyPressed(KeyEvent e){
			if (e.getKeyCode() == KeyEvent.VK_C){
				LiteTieTRIAL.setConsoleOutput("\nAim bearings at mouse location");
				System.out.println("Listening to key..."+e.getKeyChar()); //Debug currently this code is not reached.
				System.out.println("VKCDown");//Debug currently this code is not reached.
				isVKCDown = true;
				host.updateCanvas();
			}
			if (e.getKeyCode() == KeyEvent.VK_P){
				LiteTieTRIAL.setConsoleOutput("\nRotate group around center");
				System.out.println("Listening to key..."+e.getKeyChar()); //Debug currently this code is not reached.
				System.out.println("VKPDown");//Debug currently this code is not reached.
				isVKPDown = true;
				host.updateCanvas();
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				LiteTieTRIAL.setConsoleOutput("\nRotation of bearings in increments of Five Degrees");
				System.out.println("Listening to key..."+e.getKeyChar()); //Debug currently this code is not reached.
				System.out.println("VK_ShiftDown");//Debug currently this code is not reached.
				isVKShiftDown = true;
				host.updateCanvas();
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				host.setSelection(null);
				host.updateCanvas();
			}
		}
		@Override
		public void keyReleased(KeyEvent e){
			if (e.getKeyCode() == KeyEvent.VK_C){
				isVKCDown = false;
				host.updateCanvas();
			}
			if (e.getKeyCode() == KeyEvent.VK_P){
				isVKPDown = false;
				host.updateCanvas();
			}
			if (e.getKeyCode() == KeyEvent.VK_SHIFT){
				isVKShiftDown = false;
				host.updateCanvas();
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				host.setSelection(null);
				host.updateCanvas();
			}
		}
		
	};
	
	
	//Deep Cloning of the Selection
	public Collection<Coordinate> deepClone(){
		int size = ((Collection<Coordinate>) host.getSelection()).size();
		copy = new HashSet<Coordinate>(size);
		
			Iterator<Coordinate> iterator = ((Collection<Coordinate>) host.getSelection()).iterator(); 
			while(iterator.hasNext()){ 
				copy.add(iterator.next().clone()); 
				System.out.println("Added to copy");
			}	
			System.out.println("Number of entries:"+ copy.size());
			
		return copy;
	}
	/**
	 * "firstClicker" MouseListener to listen to when the mouse is clicked for the first time
	 */
	private MouseListener firstClicker = new MouseAdapter() {
		//Mouse Event method
		public void mouseReleased(MouseEvent e) {
			//Clone the Selection
//			deepClone();
			
			//Get the Mouse Location and store it.
			Rectangle2D canvas = host.getCanvasSize();
			lastY = yClick = canvas.getY() + canvas.getHeight() - UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();  //mouse's X location in the user co ords
			lastX = xClick = canvas.getX() + UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor(); //mouse's Y location in the user co ords
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
			//Clone the Selection
			deepClone();
			
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
	
	/**
	 * "nextClicker" MouseListener to listen to when the mouse is clicked for the second time
	 */
	private MouseListener nextClicker = new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			Rectangle2D canvas = host.getCanvasSize();
			double yMouseClick = canvas.getY() + canvas.getHeight() - UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
			double xMouseClick = canvas.getX() + UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

			try {
//UNCOMMENT TO TEST THE KEYLISTENER
				if (!isVKShiftDown && !isVKPDown && !isVKCDown) {
					Transform.rotateBearingsOfCollection((Collection<?>) host.getSelection(), xMouseClick, yMouseClick);
					host.updateCanvas();
				} // ROTATE THE BEARING OF THE HOLE
				else if (isVKShiftDown && !isVKPDown && !isVKCDown) {
					Transform.rotateBearingsOfCollectionByFive((Collection<?>) host.getSelection(), xMouseClick, yMouseClick);	
					host.updateCanvas();
				} // ROTATE THE BEARING OF THE HOLE BY 5 DEGREE INCREMENTS	
				//remove the ! mark in front of the isVKPDown
				if (isVKPDown && !isVKShiftDown && !isVKCDown){
					Transform.rotateCoordinateCollectionIncrementally((Collection<?>) host.getSelection(),copy, xMouseClick, yMouseClick, lastX, lastY,0.01);
					host.updateCanvas();
				}
				else if (!isVKPDown && !isVKShiftDown && isVKCDown){
					Transform.rotateCoordinateCollection((Collection<?>) host.getSelection(), copy, xMouseClick, yMouseClick);
					host.updateCanvas();					
				}
			
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NegativeNumberException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			host.updateCanvas();
				
			//UNDO RECORD
			host.addUndo(new UndoableRotateBearing(host.getWorld(), host.getSelection(), new LTPoint3D.Double(xClick + xOffset, yClick + yOffset, 0), new LTPoint3D.Double(xMouseClick, yMouseClick, 0)));
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
	
	/**
	 * "mover" MouseMotionListener to listen to when the mouse is moved around the screen
	 */
	private MouseMotionListener mover = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent e){
			Rectangle2D canvas = host.getCanvasSize();
			double yMouseClick = canvas.getY() + canvas.getHeight() - UnitConvert.pixelsToMeters(e.getY()) / Zoom.getScalingFactor();
			double xMouseClick = canvas.getX() + UnitConvert.pixelsToMeters(e.getX()) / Zoom.getScalingFactor();

			try {
//UNCOMMENT TO TEST THE KEYLISTENER
				if (!isVKShiftDown && !isVKPDown && !isVKCDown) {
					Transform.rotateBearingsOfCollection((Collection<?>) host.getSelection(), xMouseClick, yMouseClick);
					host.updateCanvas();
				} // ROTATE THE BEARING OF THE HOLE
				if (isVKShiftDown && !isVKPDown && !isVKCDown) {
					Transform.rotateBearingsOfCollectionByFive((Collection<?>) host.getSelection(), xMouseClick, yMouseClick);	
					host.updateCanvas();
				} // ROTATE THE BEARING OF THE HOLE BY 5 DEGREE INCREMENTS	
				if (isVKPDown && !isVKShiftDown && !isVKCDown){
					Transform.rotateCoordinateCollectionIncrementally((Collection<?>) host.getSelection(),copy, xMouseClick, yMouseClick, lastX, lastY,0.01);
					host.updateCanvas();
				}
				if (isVKPDown && isVKShiftDown && !isVKCDown){
					Transform.rotateCoordinateCollectionByFive((Collection<?>) host.getSelection(), copy, xMouseClick, yMouseClick);
					host.updateCanvas();					
				}				
			} catch (NumberFormatException e1) {
				System.out.println("rotated - Error");
			} catch (NegativeNumberException e1) {
				System.out.println("rotated - Error");
			}
			host.updateCanvas();
			
			lastX = xMouseClick;
			lastY = yMouseClick;
		}
	};
	
}
