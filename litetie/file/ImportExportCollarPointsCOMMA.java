package litetie.file;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import litetie.FileHandler;
import litetie.model.Dummy;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.ZeroArgumentException;



public class ImportExportCollarPointsCOMMA extends FileHandler {

	public boolean saveFile(Object pattern, String fileName) throws FileNotFoundException{

		PrintStream toFile = new PrintStream(fileName);
	
		toFile.printf(((Pattern) pattern).getPatternID()+"\n");
		toFile.printf("PointID,X,Y,Z\n");


		for (Hole h : ((Pattern) pattern).getAllHoles()) {
			if(h instanceof Hole){
				toFile.printf(h.getHoleID() + "," + h.getX()+ "," + h.getY() + "," + h.getZ()+ "\n");
				System.out.println(h.getHoleID() + "," + h.getX()+ "," + h.getY() + "," + h.getZ()+ "\n");
			}
		}

		for(Dummy d: ((Pattern) pattern).getAllDummys()){
			if (d instanceof Dummy  && (!(d instanceof Hole))) {
				toFile.printf((d.getHoleID()) + "," + d.getY()+ "," + d.getX() + ","+ d.getZ() + "\n");
			}

		}

		toFile.close();

		return true;

	}
	
	public Object openFile(Frame liteTieWindow,String fileName) throws NumberFormatException, FileNotFoundException, ZeroArgumentException{
		//OPEN the file "fileName"
		Scanner fromFile = new Scanner( new FileInputStream (fileName));
		//SETs the delimeter to \n
		fromFile.useDelimiter("\n");

		//Declares integer for storage of pattern ID
		int patternID = fromFile.nextInt();
		Pattern pattern = null;

		try {
			pattern = new Pattern(patternID,0,0);

		} 

		catch (ZeroArgumentException e) {
			JOptionPane.showMessageDialog(liteTieWindow,
					"Zero values in file - "+ e.getLocalizedMessage(),
					"ZeroArgumentException - Check File Data",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} 
		catch (NegativeNumberException e) {
			JOptionPane.showMessageDialog(liteTieWindow,
					"Negative values in file - "+e.getLocalizedMessage(),
					"NegativeNumberException - Check File Data",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		boolean parsing = false;

		while (fromFile.hasNext()) { 

			String line = fromFile.next();

			if (line.contains("PointID")) {
				line = fromFile.next();
				parsing = true;
			}

			if (parsing == true){
				int holeID;
				double northing;
				double easting;
				double collarRL;

				String [] store;
				Dummy dummyStore;


				try {
					store = line.split(",");
				}
				catch (PatternSyntaxException pse){
					pse.printStackTrace();
					//this is an error caused by "," make this visible
					throw pse; 
				}

				if ((store.length < 7 || store[6].length() == 0) && (!(store.length < 5 || store[4].length() == 0))){
					try {


						holeID = Integer.parseInt(store[0]);
						northing = Double.parseDouble(store[1]);
						easting = Double.parseDouble(store[2]);
						collarRL = Double.parseDouble(store[3]);
						
						pattern.addDummy(dummyStore = new Dummy(holeID, northing, easting, collarRL));

					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "+e.getLocalizedMessage(),
								"NumberFormat Exception - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();

					} 
					catch (ZeroArgumentException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Zero values in file - "+ e.getLocalizedMessage(),
								"ZeroArgumentException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} 
					catch (NegativeNumberException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Negative values in file - "+e.getLocalizedMessage(),
								"NegativeNumberException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
				else if(store.length < 5 || store[4].length() == 0){
					try{
						holeID = Integer.parseInt(store[0]);
						northing = Double.parseDouble(store[1]);
						easting = Double.parseDouble(store[2]);
						collarRL = Double.parseDouble(store[3]);
						pattern.addDummy(dummyStore = new Dummy(holeID, northing, easting, collarRL));
					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "+e.getLocalizedMessage(),
								"NumberFormat Exception - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();

					} 
					catch (ZeroArgumentException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Zero values in file - "+ e.getLocalizedMessage(),
								"ZeroArgumentException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} 
					catch (NegativeNumberException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Negative values in file - "+e.getLocalizedMessage(),
								"NegativeNumberException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}

				}

			}


		}


		return pattern;
	}


	
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".csv"));
	}

	@Override
	public String getDescription() {
		return "Collar position Only (*.csv)";
	}

}
