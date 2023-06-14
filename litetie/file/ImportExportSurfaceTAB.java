package litetie.file;
import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import litetie.FileHandler;
import litetie.model.FromToException;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.SurfaceConnector;
import litetie.model.ZeroArgumentException;



public class ImportExportSurfaceTAB extends FileHandler {

	public Object openFile(Frame liteTieWindow,String fileName) throws FileNotFoundException, ZeroArgumentException{
		//OPEN the file "fileName"
		Scanner fromFile = new Scanner( new FileInputStream (fileName));
		//SETs the delimeter to \n
		fromFile.useDelimiter("\n");

		
		
//		Declares integer for storage of pattern ID
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

			if (line.contains("SurfaceID")) {
				line = fromFile.next();
				parsing = true;

			}

			if (parsing == true){
				int toHID;
				int fromHID;
				int delay;
				int rValue;
				int gValue;
				int bValue;
				int surfaceID = 0;
				int length = 0;
				
				
				
				String [] store;
				
				SurfaceConnector surface;

				try {
					store = line.split("\t");
				}
				catch (PatternSyntaxException pse){
					pse.printStackTrace();
					//this is an error caused by "\t" make this visible
					throw pse; 
				}

				if ((store.length < 6 || store[5].length() == 0)){
					try {


						toHID = Integer.parseInt(store[0]);
						fromHID = Integer.parseInt(store[1]);
						delay =Integer.parseInt(store[2]);
						rValue = Integer.parseInt(store[3]);
						gValue = Integer.parseInt(store[4]);
						bValue = Integer.parseInt(store[5]);
						
						Color c = new Color(rValue, gValue, bValue, 255);
						
						pattern.addSurfaceConnector(surface = new SurfaceConnector(surfaceID, delay, length, fromHID, toHID, c ), true);

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
					} catch (NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FromToException e) {
						// TODO Auto-generated catch block
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
		toFile.printf("SurfaceID\tLength\tDelay\tFromHole\tToHole\trValue\tgValue\tbValue\n");


		for (SurfaceConnector sc : ((Pattern) pattern).getAllSurfaceConnectors()) {
			
			if(sc instanceof SurfaceConnector){
			
				toFile.printf((sc.getSurfaceID() + "\t" + sc.getLength()+ "\t" + sc.getDelay() + "\t" + sc.getFromHID()+ "\t" +sc.getToHID() + "\t" + sc.getColor().getRed()
						+ "\t" + sc.getColor().getGreen() + "\t"+ sc.getColor().getBlue()+"\n"));
			}
		}

		toFile.close();

		return true;

	}
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".sft"));
	}

	@Override
	public String getDescription() {
		return "Surface Data (*.sft)";
	}





}
