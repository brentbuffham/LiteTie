package litetie.listeners;
import java.beans.PropertyChangeEvent;




public class SimplePatternChangeListener implements PatternChangeListener {

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getOldValue() == null)
			System.out.println(e.getPropertyName() + " has been added");
		else if (e.getNewValue() == null)
			System.out.println(e.getPropertyName() + " has been remove");
	}

}
