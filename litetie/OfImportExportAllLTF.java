package litetie;
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
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.PatternSyntaxException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import litetie.model.Detonator;
import litetie.model.Dummy;
import litetie.model.FromToException;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.SurfaceConnector;
import litetie.model.ZeroArgumentException;



public class OfImportExportAllLTF extends FileHandler {



	public Object openFile(Frame liteTieWindow,String fileName) throws NumberFormatException, FileNotFoundException, ZeroArgumentException{
		//OPEN the file "fileName"
		Scanner fromFile = new Scanner( new FileInputStream (fileName));
		//SETs the delimeter to \n
		fromFile.useDelimiter("\r\n");

		//Declares integer for storage of pattern ID
		
		String quotes = "\""; 
		
		String[] patternStore = fromFile.next().split("\t");
		String patternID = patternStore[0];
		
		if (patternID.startsWith(quotes) && patternID.endsWith(quotes)){
			patternID = patternID.substring(1, patternID.length() - 1);
			}
		
		Pattern filePattern = null;

		try {
			System.out.println(patternID);
			filePattern = new Pattern(Integer.parseInt(patternID),0,0);
			
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


		/////////////////////////////
		// HOLE AND DUMMY ADDING 	//
		/////////////////////////////			

		while (fromFile.hasNext()) { 

			String line = fromFile.next();


			if (line.contains("HoleID")) {System.out.println("Line contains HoleID"); 
			parsing = true;
			line = fromFile.next();
			}

			if (parsing == true) {
				//checking to see if the file is appropriate
				

				//Declaring variables to be used for Holes and Dummys
				String holeID;
				String northing;
				String easting;
				String collarRL;
				String labelOne;
				String labelTwo;
				String labelThree;
				String diameter;
				String length;
				String bench;
				String bearing;
				String floorRL;
				String toeRL;
				String angle;
				String subdrill;
				String shape;
				String colourR;
				String colourG;
				String colourB;
				//Declaring variables for Surface Connectors
				String toHID;
				String fromHID;
				String sDelay;
				double sLength;
				String rSValue;
				String gSValue;
				String bSValue;
				String surfaceID;
				//Declaring variables for Detonators
				String detID;
				String dDelay;
				Hole inHole;
				String inHoleID;
				double dLength;
				String rDValue;
				String gDValue;
				String bDValue;

				//Temporary storage objects - Each time the while repeats these values are over written
				String[] store;
				Dummy dummyStore;
				Dummy from = null;
				Dummy to = null;
				Hole holeStore;
				SurfaceConnector surfaceStore = null;
				Detonator detStore = null;


				try { 
					//Stating what pattern will split the line of text
					
					store = line.split("\t");
				} catch (PatternSyntaxException pse) {
					pse.printStackTrace();
					//this is an error caused by "\t" make this visible
					throw pse;
				}

				
				
				
				
				if (store.length == 19 && (!(store.length == 4)) && (!(store.length == 6))) {
					//Checks if it has 15 values - Holes have 15 values
					 
					try {
						//parsing the values into the variables
						
						
						holeID = (store[0]);
						northing = (store[1]);
						easting = (store[2]);
						collarRL = (store[3]);
						labelOne = store[4];
						labelTwo = store[5];
						labelThree = store[6];
						diameter = (store[7]);
						length = (store[8]);
						bench = (store[9]);
						bearing = (store[10]);
						floorRL = (store[11]);
						toeRL = (store[12]);
						angle = (store[13]);
						subdrill = (store[14]);
						shape = (store[15]);
						colourR = (store[16]);
						colourG = (store[17]);
						colourB = (store[18]);
						
						//ID check for " "
						if (holeID.startsWith(quotes) && holeID.startsWith(quotes)){
							holeID = holeID.substring(1, holeID.length() - 1);
							}
						//NORTHING check for " "
						if (northing.startsWith(quotes) && northing.startsWith(quotes))
							northing = northing.substring(1, northing.length() - 1);
						//EASTING check for " "
						if (easting.startsWith(quotes) && easting.startsWith(quotes))
							easting = easting.substring(1, easting.length() - 1);
						//COLLARRL check for " "
						if (collarRL.startsWith(quotes) && collarRL.startsWith(quotes))
							collarRL = collarRL.substring(1, collarRL.length() - 1);
						//LABEL ONE check for " "
						if (labelOne.startsWith(quotes)&& labelOne.startsWith(quotes))
							labelOne = labelOne.substring(1, labelOne.length() - 1);
						//LABEL2 check for " "
						if (labelTwo.startsWith(quotes) && labelTwo.startsWith(quotes))
							labelTwo = labelTwo.substring(1, labelTwo.length() - 1);
						//LABEL3 check for " "
						if (labelThree.startsWith(quotes) && labelThree.startsWith(quotes))
							labelThree = labelThree.substring(1, labelThree.length() - 1);
						//DIAMETER check for " "
						if (diameter.startsWith(quotes) && diameter.startsWith(quotes))
							diameter = diameter.substring(1, diameter.length() - 1);
						//LENGTH check for " "
						if (length.startsWith(quotes) && length.startsWith(quotes))
							length = length.substring(1, length.length() - 1);
						//BENCH check for " "
						if (bench.startsWith(quotes) && bench.startsWith(quotes))
							bench = bench.substring(1, bench.length() - 1);
						//BEARING check for " "
						if (bearing.startsWith(quotes) && bearing.startsWith(quotes))
							bearing = bearing.substring(1, bearing.length() - 1);
						//FLOORRL check for " "
						if (floorRL.startsWith(quotes) && floorRL.startsWith(quotes))
							floorRL = floorRL.substring(1, floorRL.length() - 1);
						//TOERL check for " "
						if (toeRL.startsWith(quotes) && toeRL.startsWith(quotes))
							toeRL = toeRL.substring(1, toeRL.length() - 1);
						//ANGLE check for " "
						if (angle.startsWith(quotes) && angle.startsWith(quotes))
							angle = angle.substring(1, angle.length() - 1);
						//SUBDRILL check for " "
						if (subdrill.startsWith(quotes) && subdrill.startsWith(quotes))
							subdrill = subdrill.substring(1, subdrill.length() - 1);
						//SHAPE check for " "
						if (shape.startsWith(quotes) && shape.startsWith(quotes))
							shape = shape.substring(1, shape.length() - 1);
						//R
						if (colourR.startsWith(quotes) && colourR.startsWith(quotes))
							colourR = colourR.substring(1, colourR.length() - 1);
						//G
						if (colourG.startsWith(quotes) && colourG.startsWith(quotes))
							colourG = colourG.substring(1, colourG.length() - 1);
						//B
						if (colourB.startsWith(quotes) && colourB.startsWith(quotes))
							colourB = colourB.substring(1, colourB.length() - 1);
						
						Color color = new Color(Integer.parseInt(colourR), Integer.parseInt(colourG), Integer.parseInt(colourB));
						
						
						//adding a new Hole to the pattern
						filePattern.addDummy(holeStore = new Hole(Integer.parseInt(holeID),
								Double.parseDouble(northing), Double.parseDouble(easting), Double.parseDouble(collarRL), 
								labelOne,labelTwo, labelThree, 
								Double.parseDouble(diameter), Double.parseDouble(length),Double.parseDouble(bench), 
								Double.parseDouble(bearing), Double.parseDouble(floorRL), Double.parseDouble(toeRL), 
								Double.parseDouble(angle),	Double.parseDouble(subdrill),Integer.parseInt(shape),color));


					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "
								+ e.getLocalizedMessage(),
								"NumberFormat Exception - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} catch (ZeroArgumentException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Zero values in file - "
								+ e.getLocalizedMessage(),
								"ZeroArgumentException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} catch (NegativeNumberException e) {
						JOptionPane
						.showMessageDialog(
								liteTieWindow,
								"Negative values in file - "
								+ e.getLocalizedMessage(),
								"NegativeNumberException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				} else if (store.length == 4) {
					//Checking that this has only enough values to make it a Dummy and not a Surface Connector or a Hole
					
					try {
						// Parsing the values to the variables
						holeID = (store[0]);
						northing = (store[1]);
						easting = (store[2]);
						collarRL = (store[3]);
						
						//ID check for " "
						if (holeID.startsWith(quotes) && holeID.startsWith(quotes))
							holeID = holeID.substring(1, holeID.length() - 1);
						//NORTHING check for " "
						if (northing.startsWith(quotes) && northing.startsWith(quotes))
							northing = northing.substring(1, northing.length() - 1);
						//EASTING check for " "
						if (easting.startsWith(quotes) && easting.startsWith(quotes))
							easting = easting.substring(1, easting.length() - 1);
						//COLLARRL check for " "
						if (collarRL.startsWith(quotes) && collarRL.startsWith(quotes))
							collarRL = collarRL.substring(1, collarRL.length() - 1);
						
						filePattern.addDummy(dummyStore = new Dummy(Integer.parseInt(holeID),Double.parseDouble(northing), Double.parseDouble(easting), Double.parseDouble(collarRL)));
						//Adding a new Dummy Hole
						
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "
								+ e.getLocalizedMessage(),
								"NumberFormat Exception - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();

					} catch (ZeroArgumentException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Zero values in file - "
								+ e.getLocalizedMessage(),
								"ZeroArgumentException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} catch (NegativeNumberException e) {
						JOptionPane
						.showMessageDialog(
								liteTieWindow,
								"Negative values in file - "
								+ e.getLocalizedMessage(),
								"NegativeNumberException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}

				}
				else if (store.length == 6) {
					//Checking that this has only enough values to make it a Detonator
					
					try {
						// Parsing the values to the variables
						detID = (store[0]);
						dDelay = (store[1]);
						inHoleID = (store[2]);
						rDValue = (store[3]);
						gDValue = (store[4]);
						bDValue = (store[5]);
						
						
						//ID check for " "
						if (detID.startsWith(quotes) && detID.startsWith(quotes))
							detID = detID.substring(1, detID.length() - 1);
						//NORTHING check for " "
						if (dDelay.startsWith(quotes) && dDelay.startsWith(quotes))
							dDelay = dDelay.substring(1, dDelay.length() - 1);
						//INHOLEID
						if (inHoleID.startsWith(quotes) && inHoleID.startsWith(quotes))
							inHoleID = inHoleID.substring(1, inHoleID.length() - 1);
						//RDVALUE
						if (rDValue.startsWith(quotes) && rDValue.startsWith(quotes))
							rDValue = rDValue.substring(1, rDValue.length() - 1);
						//GDVALUE
						if (gDValue.startsWith(quotes) && gDValue.startsWith(quotes))
							gDValue = gDValue.substring(1, gDValue.length() - 1);
						//BDVALUE
						if (bDValue.startsWith(quotes) && bDValue.startsWith(quotes))
							bDValue = bDValue.substring(1, bDValue.length() - 1);
						
						Color c = new Color(Integer.parseInt(rDValue), Integer.parseInt(gDValue), Integer.parseInt(bDValue));
						inHole = filePattern.getHoleList().get(Integer.parseInt(inHoleID));
						
						dLength = inHole.getHoleLength() - inHole.getSubdrill();
						
						filePattern.addDetonator(detStore = new Detonator(Integer.parseInt(detID),Integer.parseInt(dDelay),inHole, dLength , c));
						//Adding a new Detonator
						
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "
								+ e.getLocalizedMessage(),
								"NumberFormat Exception - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();

					} catch (ZeroArgumentException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Zero values in file - "
								+ e.getLocalizedMessage(),
								"ZeroArgumentException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} catch (NegativeNumberException e) {
						JOptionPane
						.showMessageDialog(
								liteTieWindow,
								"Negative values in file - "
								+ e.getLocalizedMessage(),
								"NegativeNumberException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}

				}
				else if (store.length == 7 ){
					//Checking that it has 8 values which makes it a Surface Connector
				

					try { 
						//parsing the values to the variables
						surfaceID = (store[0]); 
						sDelay = (store[1]);
						fromHID = (store[2]);
						toHID = (store[3]);
						rSValue =(store[4]); 
						gSValue = (store[5]);
						bSValue = (store[6]);
						
						//ID check for " "
						if (surfaceID.startsWith(quotes) && surfaceID.startsWith(quotes))
							surfaceID = surfaceID.substring(1, surfaceID.length() - 1);
						//NORTHING check for " "
						if (sDelay.startsWith(quotes) && sDelay.startsWith(quotes))
							sDelay = sDelay.substring(1, sDelay.length() - 1);
						//INHOLEID
						if (fromHID.startsWith(quotes) && fromHID.startsWith(quotes))
							fromHID = fromHID.substring(1, fromHID.length() - 1);
						//RDVALUE
						if (toHID.startsWith(quotes) && toHID.startsWith(quotes))
							toHID = toHID.substring(1, toHID.length() - 1);
						//GDVALUE
						if (rSValue.startsWith(quotes) && rSValue.startsWith(quotes))
							rSValue = rSValue.substring(1, rSValue.length() - 1);
						//BDVALUE
						if (gSValue.startsWith(quotes) && gSValue.startsWith(quotes))
							gSValue = gSValue.substring(1, gSValue.length() - 1);
						//BDVALUE
						if (bSValue.startsWith(quotes) && bSValue.charAt(bSValue.length() - 1) == '"')
							bSValue = bSValue.substring(1, bSValue.length() - 1);
						
						
						Color c = new Color(Integer.parseInt(rSValue), Integer.parseInt(gSValue), Integer.parseInt(bSValue));
						
						if( filePattern.getDummyList().get(Integer.parseInt(fromHID)) != null && filePattern.getDummyList().get(Integer.parseInt(toHID)) != null){
							from = filePattern.getDummyList().get(Integer.parseInt(fromHID)); 
							to = filePattern.getDummyList().get(Integer.parseInt(toHID));
						}
						else if( filePattern.getDummyList().get(Integer.parseInt(toHID)) != null && filePattern.getHoleList().get(Integer.parseInt(fromHID)) != null){
							from = filePattern.getHoleList().get(Integer.parseInt(fromHID));
							to = filePattern.getDummyList().get(Integer.parseInt(toHID));
						}
						else if( filePattern.getHoleList().get(Integer.parseInt(toHID)) != null && filePattern.getDummyList().get(Integer.parseInt(fromHID)) != null){
							from = filePattern.getDummyList().get(Integer.parseInt(fromHID));
							to = filePattern.getHoleList().get(Integer.parseInt(toHID)); 
						}
						else if( filePattern.getHoleList().get(Integer.parseInt(toHID)) != null && filePattern.getHoleList().get(Integer.parseInt(fromHID)) != null){
							from = filePattern.getHoleList().get(Integer.parseInt(fromHID)); 
							to = filePattern.getHoleList().get(Integer.parseInt(toHID)); 
						}
						
						double xl = to.getX() - from.getX();//adj length
						double yl = to.getY() - from.getY();//opp length
						sLength =  Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));//hyp length
						
						filePattern.addSurfaceConnector(surfaceStore = new SurfaceConnector(Integer.parseInt(surfaceID),Integer.parseInt(sDelay), sLength, from, to, c));
							//Add a surface connector
						
						
						

					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "
								+ e.getLocalizedMessage(),
								"NumberFormat Exception - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();

					} catch (ZeroArgumentException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Zero values in file - "
								+ e.getLocalizedMessage(),
								"ZeroArgumentException - Check File Data",
								JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} catch (NegativeNumberException e) {
						JOptionPane
						.showMessageDialog(
								liteTieWindow,
								"Negative values in file - "
								+ e.getLocalizedMessage(),
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
				
			
				
			}//end hole, dummy and surface parsing
		}



		return filePattern;
	}


	public boolean saveFile(Object pattern, String fileName) throws FileNotFoundException{

		PrintStream toFile = new PrintStream(fileName);

		toFile.printf(((Pattern) pattern).getPatternID()+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+"\r\n");
		toFile.printf("HoleID\tNorthing\tEasting\tCollar Level\tLabel One\tLabel Two\tLabel Three\tDiameter\tHole Length\tBench Height\t" +
		"Bearing\tFloor Level\tToe Level\tAngle\tSubdrill\tShape\tRed\tGreen\tBlue\r\n");


		for (Hole h : ((Pattern) pattern).getAllHoles()) {
			if(h instanceof Hole){
				toFile.printf((h.getHoleID()) + "\t" + h.getY()+ "\t" + h.getX() + "\t" + h.getZ()+ "\t" +h.getLabelOne() + "\t" + h.getLabelTwo()
						+ "\t" + h.getLabelThree() + "\t"+ h.getDiameter() + "\t" + h.getHoleLength()+ "\t" + h.getBench() + "\t" + h.getBearing()
						+ "\t" + h.getFloorRL() + "\t" + h.getToeRL()+ "\t" + h.getAngle() + "\t" + h.getSubdrill() +"\t" + h.getShape() + "\t" + h.getColor().getRed()+
						"\t" + h.getColor().getGreen()+"\t" + h.getColor().getBlue()+"\r\n");
			}
		}

		for(Dummy d: ((Pattern) pattern).getAllDummys()){
			if (d instanceof Dummy && (!(d instanceof Hole))) {
				toFile.printf((d.getHoleID()) + "\t" + d.getY()+ "\t" + d.getX() + "\t"+ d.getZ() + "\r\n");
			}

		}
		for (Detonator det : ((Pattern) pattern).getAllDetonators()) {

			if(det instanceof Detonator){

				toFile.printf((det.getDetID() + "\t" + det.getDelay()  + "\t" + det.getInHole().getHoleID()+ "\t" +  det.getColor().getRed()
						+ "\t" + det.getColor().getGreen() + "\t"+ det.getColor().getBlue()+"\r\n"));
			}
		}

		for (SurfaceConnector sc : ((Pattern) pattern).getAllSurfaceConnectors()) {

			if(sc instanceof SurfaceConnector){

				toFile.printf((sc.getSurfaceID() + "\t" + sc.getDelay()  + "\t" + sc.getFrom().getHoleID()+ "\t" +sc.getTo().getHoleID() + "\t" + sc.getColor().getRed()
						+ "\t" + sc.getColor().getGreen() + "\t"+ sc.getColor().getBlue()+"\r\n"));
			}
		}

		toFile.close();

		return true;

	}
	@Override
	public boolean accept(File f) {
		return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".txt"));
	}

	@Override
	public String getDescription() {
		return "Tab Delimited - MSEXCEL compatible (*.txt)";
	}





}
