package litetie;
import java.awt.Frame;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;

import javax.swing.filechooser.FileFilter;

import litetie.model.NegativeNumberException;
import litetie.model.ZeroArgumentException;


public abstract class FileHandler extends FileFilter {
	
//	abstract World openWorldFile(Frame liteTieWindow, String fileName) throws FileNotFoundException, ZeroArgumentException, NegativeNumberException;
//	abstract boolean saveWorldFile(World world, String fileName) throws FileNotFoundException, IOException;
	
	public abstract Object openFile(Frame liteTieWindow, String fileName) throws FileNotFoundException, ZeroArgumentException, NegativeNumberException;
	public abstract boolean saveFile(Object object, String fileName) throws FileNotFoundException, IOException;
	//abstract boolean savePrefs(String filename) throws FileNotFoundException, IOException;

}
