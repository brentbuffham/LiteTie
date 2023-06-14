package litetie.view;
//Written by Brent Buffham with extensive aid from Joshua King
//Commenced and completed on the 29-june-2007

//This class opens a dialogue box that can access the FileTypeRegistry.class to open a file to be read. 

import javax.swing.JFileChooser; //Imports JFileChooser which has in it the ability to make dialogue boxes.
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import litetie.FileHandler;
import litetie.file.FileTypeRegistry;
import java.io.File;

public class DialogOpenFile { //In order to call this class and it's methods you must call as so - OpenDialogue.showBox()

	public static FileTypeRegistry.FileMapping showBox() {  //This method showBox() requires access and also to be of type FileTypeRegistry.FileMapping

		JFileChooser openFile = new JFileChooser();//JFileChooser is constructed a this point.  A variable is created called "openFile" and it is of type JFileChooser 
		for (FileHandler dialogue: FileTypeRegistry.HANDLERS) openFile.addChoosableFileFilter(dialogue);//This is a For:Each loop it says that "For variable dialogue add a filter that is located in FileTypeRegistry these  handlers are constants.
		
		openFile.setAcceptAllFileFilterUsed(false);
		
		if (openFile.showOpenDialog(null) !=JFileChooser.APPROVE_OPTION) {
			return null;
		}
		return 
		new FileTypeRegistry.FileMapping(openFile.getSelectedFile(), (FileHandler) openFile.getFileFilter());
	}
	
}
