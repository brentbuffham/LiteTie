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



public class ImportExportToePointsCOMMA extends FileHandler {

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

			if (line.contains("HoleID")) {
				line = fromFile.next();
				parsing = true;

			}

			if (parsing == true){
				int holeID;
				double northing;
				double easting;
				double collarRL;
				String labelOne;
				String labelTwo;
				String labelThree;
				double diameter;
				double length;
				double bench;
				double bearing;
				double floorRL;
				double toeRL;
				double angle;
				double subdrill;
				int	shape;

				String [] store;
				Dummy dummyStore;
				Hole holeStore;

				try {
					store = line.split(",");
				}
				catch (PatternSyntaxException pse){
					pse.printStackTrace();
					//this is an error caused by "," make this visible
					throw pse; 
				}

				if ((store.length < 16 || store[15].length() == 0) && (!(store.length < 5 || store[4].length() == 0))){
					try {


						holeID = Integer.parseInt(store[0]);
						northing = Double.parseDouble(store[1]);
						easting = Double.parseDouble(store[2]);
						collarRL = Double.parseDouble(store[3]);
						labelOne = store[4];
						labelTwo = store[5];
						labelThree = store[6];
						diameter = Double.parseDouble(store[7]);
						length = Double.parseDouble(store[8]);
						bench = Double.parseDouble(store[9]);
						bearing = Double.parseDouble(store[10]);
						floorRL = Double.parseDouble(store[11]);
						toeRL = Double.parseDouble(store[12]);
						angle = Double.parseDouble(store[13]);
						subdrill = Double.parseDouble(store[14]);
						shape = Integer.parseInt(store[15]);
						pattern.addDummy(holeStore = new Hole(holeID, northing, easting, collarRL, labelOne, labelTwo, labelThree, diameter, length, bench , bearing,  floorRL, toeRL, angle, subdrill,shape,null));

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


	public boolean saveFile(Object pattern, String fileName) throws FileNotFoundException{

		PrintStream toFile = new PrintStream(fileName);
	
		toFile.printf(((Pattern) pattern).getPatternID()+"\n");
		toFile.printf("HoleID,Northing,Easting,Collar Level,Label One,Label Two,Label Three,Diameter,Hole Length,Bench Height," +
		"Bearing,Floor Level,Toe Level,Angle,Subdrill,Shape,,,\n");


		for (Hole h : ((Pattern) pattern).getAllHoles()) {
			if(h instanceof Hole){
				toFile.printf((h.getHoleID()) + "," + h.getY()+ "," + h.getX() + "," + h.getZ()+ "," +h.getLabelOne() + "," + h.getLabelTwo()
						+ "," + h.getLabelThree() + ","+ h.getDiameter() + "," + h.getHoleLength()+ "," + h.getBench() + "," + h.getBearing()
						+ "," + h.getFloorRL() + "," + h.getToeRL()+ "," + h.getAngle() + "," + h.getSubdrill()+"," + h.getShape()+",,,"+"\n");
			}
		}

		for(Dummy d: ((Pattern) pattern).getAllDummys()){
			if (d instanceof Dummy && (!(d instanceof Hole))) {
				toFile.printf((d.getHoleID()) + "," + d.getY()+ "," + d.getX() + ","+ d.getZ() + ",,,,,,,,,,,,,,,\n");
			}

		}

		toFile.close();

		return true;

	}
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".csv"));
	}

	@Override
	public String getDescription() {
		return "CSV Import/Export hole data (*.csv)";
	}

}
