package litetie.file;
import java.io.PrintStream;
import java.io.FileNotFoundException;

import litetie.FileHandler;
import litetie.model.Pattern;

public abstract class SimpleFileHandler extends FileHandler {

	public Pattern openFile(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean savePatternFile(Pattern shotName, String fileName) throws FileNotFoundException{
		
		
		PrintStream toFile = new PrintStream (fileName);
		toFile.println(shotName.toString());
		toFile.close();
		
		return true;
	}

}
