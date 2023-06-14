/**

 */
package litetie.listeners;

/**
 * @author brentbuffham
 * <br>Interface to implement a Changable type of action - either undo or redo.<br><br>
 * Greg Cope originally wrote this and the link to the tutorial is at:<br>
 * <li> http://www.algosome.com/articles/implementing-undo-redo-java.html </li>
 */
public interface Changeable {

		/**
		 * Undoes an action
		 */
		public void undo();

		

		/**
		 * Redoes an action
		 */
		public void redo();

	
}
