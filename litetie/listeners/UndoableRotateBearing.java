package litetie.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import litetie.model.LTPoint3D;

import javax.swing.undo.AbstractUndoableEdit;

import litetie.controller.Transform;
import litetie.model.Coordinate;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.UnitConvert;
import litetie.model.World;
import litetie.view.Zoom;

public class UndoableRotateBearing extends AbstractUndoableEdit {

	private static final long serialVersionUID = 1L;

	protected World world;
	protected Object selected;
	protected LTPoint3D firstClick;
	protected LTPoint3D lastClick;


	public UndoableRotateBearing(World world, Object selected, LTPoint3D firstClick, LTPoint3D lastClick) {
		// TODO Need to check whether any of these need to be cloned
		this.world = world;
		this.selected = selected;
		this.firstClick = firstClick;
		this.lastClick = lastClick;
	}
	
	public String getPresentationName() {
		return "Rotated Bearing(s)"; // use language of the user not code
	}

	public void undo() {
		super.undo();			

		if (selected instanceof Collection<?>) {
			try {
				// deliberately in reverse for undo
				Transform.rotateBearingsOfCollection((Collection<?>) selected, lastClick.getX(), lastClick.getY());

			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else{
			for(Coordinate c: world.getCoordList().values()){
				boolean isSelected = c == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(c));	

				if (isSelected) {

					try {
						Transform.rotateBearingsOfCollection((Collection<?>) c, lastClick.getX(), lastClick.getY());


					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}

			}
		}
	}

	public void redo() {
		super.redo();
		if (selected instanceof Collection<?>) {
			try {
				// deliberately in reverse for undo
				Transform.rotateBearingsOfCollection((Collection<?>) selected, firstClick.getX(), firstClick.getY());


			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		else{
			for(Coordinate c: world.getCoordList().values()){
				boolean isSelected = c == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(c));	
				if (isSelected) {
					try {
						Transform.rotateBearingsOfCollection((Collection<?>) c, firstClick.getX(), firstClick.getY());	
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}

			}
		}
	}
	
	public String getMoveInformation() {
		return "Bearings changed" + ((Hole) selected).getBearing();
	}
}
