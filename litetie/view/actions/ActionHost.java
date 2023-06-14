package litetie.view.actions;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.Collection;

import javax.swing.undo.UndoableEdit;

import litetie.model.World;

/**
 * This interface provides the access to the world that
 * actions need from the main GUI (ie, LiteTie).
 * 
 * @author jking_000
 *
 */
public interface ActionHost {

	/**
	 * Add an undoable operation
	 * 
	 * @param newUndo The undoable operation that just occurred
	 */
	public void addUndo(UndoableEdit newUndo);
	
	/**
	 * Change the current mouse clicker
	 * 
	 * @param newCurrentMouseClicker The new current mouse clicker
	 */
	public void setCurrentMouseClicker(MouseListener newCurrentMouseClicker);
	
	/**
	 * Change the current mouse mover
	 * 
	 * @param newCurrentMouseMover The new current mouse mover
	 */
	public void setCurrentMouseMover(MouseMotionListener newCurrentMouseMover);
	
	/**
	 * Change the measurement status texts
	 * 
	 * @param new1 The first label
	 * @param new2 The second label
	 */
	public void setMeasurementStatusText(String new1, String new2);
	
	/**
	 * Change the text on the stroke colour label.
	 * 
	 * @param newText The new text
	 */
	public void setStrokeColourLabelText(String newText);
	
	/**
	 * Change the background colour of the stroke colour well
	 * 
	 * @param newBackground New colour
	 */
	public void setStrokeColourWellBackground(Color newBackground);
	
	/**
	 * Gets the current size of the canvas
	 * 
	 * @return Canvas dimensions in a rectangle
	 */
	public Rectangle2D getCanvasSize();
	
	/**
	 * Gets the current colour.
	 * 
	 * @return Current colour
	 */
	public Color getCurrentColour();
	
	/**
	 * Gets the first decimal format defined for metres.
	 * 
	 * @return Decimal format
	 */
	public DecimalFormat getDecimalFormatMetres1();
	
	/**
	 * Gets the default mouse mover.
	 * 
	 * @return Default mouse mover
	 */
	public MouseMotionListener getDefaultMouseMover();
	
	/**
	 * Gets the selection.
	 * 
	 * @return Selection
	 */
	public Object getSelection();
	
	/**
	 * Gets the world.
	 * 
	 * @return World
	 */
	public World getWorld();
	
/**
 * Selects the First Object near the mouse click 
 * @param nearX
 * @param nearY
 * @param nearZ 
 * @return object near mouse click
 */
	public Object selectFirstObjectNear(double nearX, double nearY, double nearZ);

/**
 * Sets the centre location
 * @param newX
 * @param newY
 * @param newZ
 */
	public void setCentreBounds(double newX, double newY, double newZ);
	
	/**
	 * Sets the delta (distance the mouse has moved since selection).
	 * 
	 * @param deltaY The change in northing
	 * @param deltaX The change in easting
	 * @param deltaZ The change in elevation
	 */
	public void setDelta(double deltaX, double deltaY, double deltaZ);
	
	/**
	 * Sets the selection to a collection of objects
	 * 
	 * @param collection New collection
	 */
	public void setSelection(Collection<?> collection);
	
	/**
	 * Updates the display of the canvas
	 */
	public void updateCanvas();
	
	/**
	 * Updates tree representations
	 */
	public void updateTrees();
}
