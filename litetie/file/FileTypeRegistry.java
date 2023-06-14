package litetie.file;
import java.io.File;

import java.io.IOException;

import litetie.FileHandler;
import litetie.model.NegativeNumberException;
import litetie.model.World;
import litetie.model.ZeroArgumentException;
import litetie.view.DialogOpenFile;
import litetie.view.DialogSaveFile;




public class FileTypeRegistry {
	
	public static final FileHandler[] HANDLERS = new FileHandler[]{ /*new ImportExportHolesCOMMA(), new ImportExportHolesTAB(),*/ 
//																	new ImportExportSurfaceTAB(), 
																	new ImportExportAllCOMMA(),
																	new ImportExportAllTAB(), 
																	new ImportExportAllLITETIE()
//																	new ImportExportCollarPointsCOMMA(),
//																	new ImportExportToePointsCOMMA()
																	};
	
	public static class FileMapping {
		

		public File file;
		public FileHandler handler;
		
		public FileMapping(File file, FileHandler handler) {
			this.file = file;
			this.handler = handler;
		}
		
	}

	public static void main (String[] args) throws IOException, ZeroArgumentException, NegativeNumberException{

		FileTypeRegistry.FileMapping variable1 = DialogOpenFile.showBox();
			
		//if (variable1 == null) {System.exit(0);	}
		if (variable1.handler.getDescription().contains("ltf")){
			World world = null;
			world = (World) variable1.handler.openFile(null, variable1.file.getAbsolutePath());
			variable1 = DialogSaveFile.showBox();
			if (variable1 == null) {System.exit(0);	}
			variable1.handler.saveFile(world,	variable1.file.getAbsolutePath());
		}
		
		else if (variable1.handler.getDescription().contains("txt") || variable1.handler.getDescription().contains("csv")) {
					
			Object o = variable1.handler.openFile(null, variable1.file.getAbsolutePath());
			variable1 = DialogSaveFile.showBox();
			if (variable1 == null) {System.exit(0);	}
			variable1.handler.saveFile(o,	variable1.file.getAbsolutePath());
		}
//		else if (variable1.handler.getDescription().contains("Collar") || variable1.handler.getDescription().contains("Toe")) {
//			
//			Object o = variable1.handler.openFile(null, variable1.file.getAbsolutePath());
//			variable1 = SaveDialogue.showBox();
//			if (variable1 == null) {System.exit(0);	}
//			variable1.handler.saveFile(o,	variable1.file.getAbsolutePath());
//		}
//		else if (variable1.handler.getDescription().contains("LiteTie")){
//			World world = null;
//			world = variable1.handler.openWorldFile(null, variable1.file.getAbsolutePath());
//			variable1 = SaveDialogue.showBox();
//			if (variable1 == null) {System.exit(0);	}
//			variable1.handler.saveWorldFile(world,	variable1.file.getAbsolutePath());
//		}
	}
}
