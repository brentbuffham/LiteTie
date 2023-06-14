package litetie;

//Assists in set object to a Jtree
public class LabelledUserObject {

	public LabelledUserObject(String label, Object userObject) {
		setLabel(label);
		setUserObject(userObject);
	}

	public String toString() {
		return label;
	}

	public Object getUserObject() {
		return userObject;
	}
	public void setUserObject(Object newObject){
		this.userObject = newObject;
	}
	
	public void setUserObject(String label, Object newObject){
		this.label = label;
		this.userObject = newObject;
		}
	public void setLabel(String newLabel) { 
		this.label = newLabel; 
		}

	private String label;
	private Object userObject;
	
	


}
