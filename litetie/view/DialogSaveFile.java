package litetie.view;


import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import litetie.FileHandler;
import litetie.file.FileTypeRegistry;


public class DialogSaveFile {
	
	public static FileTypeRegistry.FileMapping showBox() {

		boolean choosen = false;
		
		
		JFileChooser saveFile = new JFileChooser();
		for (FileHandler dialogue: FileTypeRegistry.HANDLERS) saveFile.addChoosableFileFilter(dialogue);
		
		saveFile.setAcceptAllFileFilterUsed(false);
		
		int fileTypeIndex;
		
		do {
			if (saveFile.showSaveDialog(saveFile.getTopLevelAncestor()) !=JFileChooser.APPROVE_OPTION) {
				return null;
				}
			//Gets the descriptor ie. "hole import from text (*.txt)" and returns .txt
			fileTypeIndex = (saveFile.getFileFilter().getDescription().length() -5);
			// Determines if the file ends with the specified file type filter eg. test.txt returns .txt 
			if (!(saveFile.getSelectedFile().getName().toLowerCase().endsWith(saveFile.getFileFilter().getDescription().substring(fileTypeIndex, (fileTypeIndex + 4))))){
				//Stores the appropriate suffix from the descriptor
				String fileNameAndExtension = saveFile.getSelectedFile().getName().concat(saveFile.getFileFilter().getDescription().substring(fileTypeIndex, (fileTypeIndex + 4)));
				//saves the selected file also adding on the appropriate suffix
				saveFile.setSelectedFile(new File(saveFile.getSelectedFile().getParent(), fileNameAndExtension));	
			} 
			
			
			if (saveFile.getSelectedFile().exists()) {
				if(JOptionPane.showConfirmDialog(saveFile.getTopLevelAncestor(), "\"" + saveFile.getSelectedFile().getName() + "\" already exists. Do you want to replace it?", 
					"Replace File", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
				continue;
				}
			}
		choosen = true;	
		}
		while (choosen == false);
		
		return 
		new FileTypeRegistry.FileMapping(saveFile.getSelectedFile(), (FileHandler) saveFile.getFileFilter());
	}
	
}
