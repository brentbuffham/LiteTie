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
import litetie.model.NegativeNumberException;
import litetie.model.UnitConvert;
import litetie.model.World;
import litetie.view.Zoom;

public class UndoableMoveObject extends AbstractUndoableEdit {

	private static final long serialVersionUID = 1L;

	protected World world;
	protected Object selected;
	protected LTPoint3D from;
//	protected double fromX;
	protected LTPoint3D to;
//	protected double toX;

	public UndoableMoveObject(World world, Object selected, LTPoint3D from, LTPoint3D to) {
		// TODO Need to check whether any of these need to be cloned
		this.world = world;
		this.selected = selected;
		this.to = to;
		this.from = from;

	}
	
	public String getPresentationName() {
		return "Move Selected Object(s)"; // use language of the user not code
	}

	public void undo() {
		super.undo();			

		if (selected instanceof Collection<?>) {
			try {
				// deliberately in reverse for undo
				Transform.translateCoordinateCollection((Collection<?>) selected, from.getX(), from.getY());

			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NegativeNumberException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		}
		else{
			for(Coordinate c: world.getCoordList().values()){
				boolean isSelected = c == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(c));	

				if (isSelected) {

					try {
						double [] differences =	Transform.translateCoordinate(c, from.getX(), from.getY(), to.getX(), to.getY());

					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NegativeNumberException e1) {
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
				Transform.translateCoordinateCollection((Collection<?>) selected, to.getX(), to.getY());

			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NegativeNumberException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
		}
		else{
			for(Coordinate c: world.getCoordList().values()){
				boolean isSelected = c == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(c));	

				if (isSelected) {

					try {
						double [] differences =	Transform.translateCoordinate(c, to.getX(), to.getY(), from.getX(), from.getY());
						

						// measurementStatusLabel1.setText("E"+decimalFormatMetres1.format(differences[0]));
						// measurementStatusLabel2.setText("N"+decimalFormatMetres1.format(differences[1]));

					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NegativeNumberException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		}
	}
	
	public String getMoveInformation() {
		return "Move objects by N" + (to.getY() - from.getY()) + " E" + (to.getX() - from.getX());
	}
}
