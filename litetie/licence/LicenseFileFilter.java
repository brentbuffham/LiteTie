package litetie.licence;

//import Utility.FileExtension;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class LicenseFileFilter extends FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(".ZIP");
    }
    
    public String getDescription() {
       return "License files";
    }
}