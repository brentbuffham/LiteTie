package litetie.file;
import java.awt.BasicStroke;
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

import litetie.FileHandler;
import litetie.model.BPoint;
import litetie.model.Boundary;
import litetie.model.Detonator;
import litetie.model.Dummy;
import litetie.model.FromToException;
import litetie.model.Hole;
import litetie.model.InitiationPoint;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.SurfaceConnector;
import litetie.model.Text;
import litetie.model.World;
import litetie.model.ZeroArgumentException;



public class ImportExportAllLITETIE extends FileHandler {



	public Object openFile(Frame liteTieWindow,String fileName) throws NumberFormatException, FileNotFoundException, ZeroArgumentException{
		//OPEN the file "fileName"
		Scanner fromFile = new Scanner( new FileInputStream (fileName));
		//SETs the delimeter to \r\n
		fromFile.useDelimiter("\n");

		//Declares integer for storage of pattern ID

		String quotes = "\""; 

		String[] worldStore = fromFile.next().split(",");
		String worldID = worldStore[0];

		if(worldID.startsWith(quotes) && worldID.endsWith(quotes)){
			worldID = worldID.substring(1, worldID.length()-1);
		}
		
		World fileWorld = null;
		Pattern filePattern = null;
		Boundary fileBoundary = null;

		try {
			fileWorld = new World();
		}
		catch (IllegalArgumentException e){
			JOptionPane.showMessageDialog(liteTieWindow, "Error in File being read");
		}

		boolean parsing = false;

		while (fromFile.hasNext()) { 

			String line = fromFile.next();
			

			if (line.contains("World")) { 

				System.out.println("OPENING FILE...");
				parsing = true;
				System.out.println("FILE CAN BE PARSE...");
				line = fromFile.next();
			}

			if (parsing == true) {
				//checking to see if the file is appropriate
				String patternID;
				String burden;
				String spacing;
				String holesInRows;
				String rows;
				String orientation;

				//Declaring variables to be used for Holes and Dummys - Store Length = 19
				String holeInPattern;				//0
				String holeID;					//1
				String northing;				//2
				String easting;					//3
				String collarRL;				//4
				String labelOne;				//5
				String labelTwo;				//6
				String labelThree;				//7
				String diameter;				//8
				String length;					//9
				String bench;					//10
				String bearing;					//11
				String floorRL;					//12
				String toeRL;					//13
				String angle;					//14
				String subdrill;				//15
				String shape;					//16
				String colourR;					//17
				String colourG;					//18
				String colourB;					//19

				//Declaring variables for Surface Connectors - Store Length = 8
				String surfaceInPattern;
				String toHID;					//0
				String fromHID;					//1
				String sDelay;					//2
				double sLength;					//3
				String rSValue;					//4
				String gSValue;					//5
				String bSValue;					//6
				String surfaceID;				//7

				//Declaring variables for Detonators - Store Length = 8
				String detonatorInPattern;
				String detID;					//0
				String dDelay;					//1
				Hole inHole;					//2
				String inHoleID;				//3
				double dLength;					//4
				String rDValue;					//5
				String gDValue;					//6
				String bDValue;					//7

				//Declaring variables for InitiationPoint - Store Length = 3
				String iPInPattern;
				String ipID;					//0
				String ipTime;					//1
				String ipLocationID;			//3

				//Declaring variables for Boundaries - Store Length = 17
				String bID; 					//0
				String bIsClosedPolygon;		//3
				String bArrowType;				//4
				String bIsAnnotated;			//5
				String bStrokeRed;				//6
				String bStrokeGreen;			//7
				String bStrokeBlue;				//8
				String bStrokeAlpha;			//9
				String bFillRed;				//10
				String bFillGreen;				//11
				String bFillBlue;				//12
				String bFillAlpha;				//13	
				String bStrokeWidth;			//14
				String bStrokeDashLength;		//15
				String bStrokeSpaceLength;		//16

				//Declaring variables for BoundaryPoints - Store Length = 5
				String bpBoundaryID;			//0
				String bpID;					//1
				String bNorthing;				//2
				String bEasting;				//3
				String bCollar;					//4

				//Temporary storage objects - Each time the while repeats these values are over written
				String[] store;
				Dummy dummyStore;
				Dummy from = null;
				Dummy to = null;
				Pattern patternStore;
				Hole holeStore;
				SurfaceConnector surfaceStore = null;
				Detonator detStore = null;
				InitiationPoint ipStore = null;
				Boundary boundaryStore = null;
				BPoint boundaryPointStore = null;
				Text textStore = null;

				try { 
					//Stating what pattern will split the line of text

					store = line.split(",");
				} catch (PatternSyntaxException pse) {
					pse.printStackTrace();
					//this is an error caused by "," make this visible
					throw pse;
				}

				if(store.length == 6) {
					try {
						patternID = (store[0]);
						burden = (store[1]);
						spacing = (store[2]);
						holesInRows = (store[3]);
						rows = (store[4]);
						orientation = (store[5]);

						//Pattern ID check for " "
						if (patternID.startsWith(quotes) && patternID.startsWith(quotes)){
							patternID = patternID.substring(1, patternID.length() - 1);
						}
						//ID check for " "
						if (burden.startsWith(quotes) && burden.startsWith(quotes)){
							burden = burden.substring(1, burden.length() - 1);
						}
						//NORTHING check for " "
						if (spacing.startsWith(quotes) && spacing.startsWith(quotes))
							spacing = spacing.substring(1, spacing.length() - 1);
						//EASTING check for " "
						if (holesInRows.startsWith(quotes) && holesInRows.startsWith(quotes))
							holesInRows = holesInRows.substring(1, holesInRows.length() - 1);
						//COLLARRL check for " "
						if (rows.startsWith(quotes) && rows.startsWith(quotes))
							rows = rows.substring(1, rows.length() - 1);
						//LABEL ONE check for " "
						if (orientation.startsWith(quotes)&& orientation.startsWith(quotes))
							orientation = orientation.substring(1, orientation.length() - 1);

						try {
							filePattern = new Pattern(Integer.parseInt(patternID), Double.parseDouble(burden), 
									Double.parseDouble(spacing), Integer.parseInt(holesInRows), Integer.parseInt(rows),
									Double.parseDouble(orientation));

							fileWorld.addPattern(filePattern, true);

							} catch (NegativeNumberException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "
										+ e.getLocalizedMessage(),
										"NumberFormat Exception - Check File Data",
										JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}

				else if (store.length == 20 ) {
					//Checks if it has 20 values 

					try {
						//parsing the values into the variables

						holeInPattern = (store[0]);
						holeID = (store[1]);
						northing = (store[2]);
						easting = (store[3]);
						collarRL = (store[4]);
						labelOne = store[5];
						labelTwo = store[6];
						labelThree = store[7];
						diameter = (store[8]);
						length = (store[9]);
						bench = (store[10]);
						bearing = (store[11]);
						floorRL = (store[12]);
						toeRL = (store[13]);
						angle = (store[14]);
						subdrill = (store[15]);
						shape = (store[16]);
						colourR = (store[17]);
						colourG = (store[18]);
						colourB = (store[19]);


						//Pattern ID check for " "
						if (holeInPattern.startsWith(quotes) && holeInPattern.startsWith(quotes)){
							holeInPattern = holeInPattern.substring(1, holeInPattern.length() - 1);
						}
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

						if (filePattern.getPatternID() == Integer.parseInt(holeInPattern)){

							filePattern.addDummy(holeStore = new Hole(Integer.parseInt(holeID),
									Double.parseDouble(northing), Double.parseDouble(easting), Double.parseDouble(collarRL), 
									labelOne,labelTwo, labelThree, 
									Double.parseDouble(diameter), Double.parseDouble(length),Double.parseDouble(bench), 
									Double.parseDouble(bearing), Double.parseDouble(floorRL), Double.parseDouble(toeRL), 
									Double.parseDouble(angle),	Double.parseDouble(subdrill), Integer.parseInt(shape), color));
						}
						else
							System.out.println("Hole does not belong to pattern "+filePattern.getPatternID());

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
				} else if (store.length == 5) {
					//Checking that this has only enough values to make it a Dummy and not a Surface Connector or a Hole

					try {
						// Parsing the values to the variables
						holeInPattern = (store[0]);
						holeID = (store[1]);
						northing = (store[2]);
						easting = (store[3]);
						collarRL = (store[4]);

						if (holeInPattern.startsWith(quotes) && holeInPattern.startsWith(quotes)){
							holeInPattern = holeInPattern.substring(1, holeInPattern.length() - 1);
						}
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
						if(filePattern.getPatternID() == Integer.parseInt(holeInPattern)){
							filePattern.addDummy(dummyStore = new Dummy(Integer.parseInt(holeID),Double.parseDouble(northing), Double.parseDouble(easting), Double.parseDouble(collarRL)));
						}//Adding a new Dummy Hole
						else
							System.out.println("Dummy does not belong to this pattern " + holeInPattern);

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
				else if (store.length == 7) {
					//Checking that this has only enough values to make it a Detonator

					try {
						// Parsing the values to the variables
						detonatorInPattern = (store[0]);
						detID = (store[1]);
						dDelay = (store[2]);
						inHoleID = (store[3]);
						rDValue = (store[4]);
						gDValue = (store[5]);
						bDValue = (store[6]);

						if (detonatorInPattern.startsWith(quotes) && detonatorInPattern.startsWith(quotes)){
							detonatorInPattern = detonatorInPattern.substring(1, detonatorInPattern.length() - 1);
						}
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

				else if (store.length == 8 ){
					//Checking that it has 9 values which makes it a Surface Connector


					try { 
						//parsing the values to the variables
						surfaceInPattern = (store[0]);
						surfaceID = (store[1]); 
						sDelay = (store[2]);
						fromHID = (store[3]);
						toHID = (store[4]);
						rSValue =(store[5]); 
						gSValue = (store[6]);
						bSValue = (store[7]);

						//ID check for " "
						if (surfaceInPattern.startsWith(quotes) && surfaceInPattern.startsWith(quotes))
							surfaceInPattern = surfaceInPattern.substring(1, surfaceInPattern.length() - 1);
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
				//INITIATION POINTS
				else if (store.length == 4) {
					//Checking that this has only enough values to make it a InitiationPoint and not a Surface Connector/Hole/Detonator/Dummy

					try {
						// Parsing the values to the variables
						iPInPattern = (store[0]);
						ipID = (store[1]);
						ipTime = (store[2]);
						ipLocationID = (store[3]);

						//ID check for " "
						if (ipID.startsWith(quotes) && ipID.startsWith(quotes))
							ipID = ipID.substring(1, ipID.length() - 1);
						//IPTIME check for " "
						if (ipID.startsWith(quotes) && ipTime.startsWith(quotes))
							ipTime = ipTime.substring(1, ipTime.length() - 1);
						//IPLOCATIONID check for " "
						if (ipLocationID.startsWith(quotes) && ipLocationID.startsWith(quotes))
							ipLocationID = ipLocationID.substring(1, ipLocationID.length() - 1);
						Dummy dummyOrHole = null;

						Dummy dummy = filePattern.getDummy(Integer.parseInt(ipLocationID));
						Hole hole = filePattern.getHole(Integer.parseInt(ipLocationID));

						if(dummy instanceof Dummy && (!(dummyOrHole instanceof Hole)) && dummy != null){
							dummyOrHole = filePattern.getDummy(Integer.parseInt(ipLocationID));
						}
						if(hole instanceof Hole && hole != null){
							dummyOrHole = filePattern.getHole(Integer.parseInt(ipLocationID));
						}


						filePattern.addInitiationPoint(ipStore = new InitiationPoint(Integer.parseInt(ipID), Integer.parseInt(ipTime), dummyOrHole));
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

				if (store.length == 15){
					bID = (store[0]); 					//0
					bIsClosedPolygon = (store[1]);		//3
					bArrowType = (store[2]);				//4
					bIsAnnotated = (store[3]);			//5
					bStrokeRed = (store[4]);				//6
					bStrokeGreen = (store[5]);			//7
					bStrokeBlue = (store[6]);				//8
					bStrokeAlpha = (store[7]);			//9
					bFillRed = (store[8]);				//10
					bFillGreen = (store[9]);				//11
					bFillBlue = (store[10]);				//12
					bFillAlpha = (store[11]);				//13	
					bStrokeWidth = (store[12]);			//14
					bStrokeDashLength = (store[13]);		//15
					bStrokeSpaceLength = (store[14]);		//16

					if (bID.startsWith(quotes) && bID.startsWith(quotes)){
						bID = bID.substring(1, bID.length() - 1);
					}
					//ID check for " "
					if (bIsClosedPolygon.startsWith(quotes) && bIsClosedPolygon.startsWith(quotes))
						bIsClosedPolygon = bIsClosedPolygon.substring(1, bIsClosedPolygon.length() - 1);
					//NORTHING check for " "
					if (bArrowType.startsWith(quotes) && bArrowType.startsWith(quotes))
						bArrowType = bArrowType.substring(1, bArrowType.length() - 1);
					//INHOLEID
					if (bIsAnnotated.startsWith(quotes) && bIsAnnotated.startsWith(quotes))
						bIsAnnotated = bIsAnnotated.substring(1, bIsAnnotated.length() - 1);
					//RDVALUE
					if (bStrokeRed.startsWith(quotes) && bStrokeRed.startsWith(quotes))
						bStrokeRed = bStrokeRed.substring(1, bStrokeRed.length() - 1);
					//GDVALUE
					if (bStrokeGreen.startsWith(quotes) && bStrokeGreen.startsWith(quotes))
						bStrokeGreen = bStrokeGreen.substring(1, bStrokeGreen.length() - 1);
					//BDVALUE
					if (bStrokeBlue.startsWith(quotes) && bStrokeBlue.startsWith(quotes))
						bStrokeBlue = bStrokeBlue.substring(1, bStrokeBlue.length() - 1);

					if (bStrokeAlpha.startsWith(quotes) && bStrokeAlpha.startsWith(quotes))
						bStrokeAlpha = bStrokeAlpha.substring(1, bStrokeAlpha.length() - 1);
					//GDVALUE
					if (bFillRed.startsWith(quotes) && bFillRed.startsWith(quotes))
						bFillRed = bFillRed.substring(1, bFillRed.length() - 1);
					//BDVALUE
					if (bFillGreen.startsWith(quotes) && bFillGreen.startsWith(quotes))
						bFillGreen = bFillGreen.substring(1, bFillGreen.length() - 1);

					if (bFillBlue.startsWith(quotes) && bFillBlue.startsWith(quotes))
						bFillBlue = bFillBlue.substring(1, bFillBlue.length() - 1);
					//GDVALUE
					if (bFillAlpha.startsWith(quotes) && bFillAlpha.startsWith(quotes))
						bFillAlpha = bFillAlpha.substring(1, bFillAlpha.length() - 1);
					//BDVALUE
					if (bStrokeWidth.startsWith(quotes) && bStrokeWidth.startsWith(quotes))
						bStrokeWidth = bStrokeWidth.substring(1, bStrokeWidth.length() - 1);

					if (bStrokeDashLength.startsWith(quotes) && bStrokeDashLength.startsWith(quotes))
						bStrokeDashLength = bStrokeDashLength.substring(1, bStrokeDashLength.length() - 1);

					if (bStrokeSpaceLength.startsWith(quotes) && bStrokeSpaceLength.startsWith(quotes))
						bStrokeSpaceLength = bStrokeSpaceLength.substring(1, bStrokeSpaceLength.length() - 1);

					Color strokeC = new Color(Integer.parseInt(bStrokeRed), Integer.parseInt(bStrokeGreen), Integer.parseInt(bStrokeBlue), Integer.parseInt(bStrokeAlpha));
					Color fillC = new Color(Integer.parseInt(bFillRed), Integer.parseInt(bFillGreen), Integer.parseInt(bFillBlue), Integer.parseInt(bFillAlpha));

					BasicStroke stroke = new BasicStroke(Integer.parseInt(bStrokeWidth));

					if(Float.parseFloat(bStrokeDashLength) == 0 || Float.parseFloat(bStrokeSpaceLength) == 0){
						stroke = new BasicStroke(Integer.parseInt(bStrokeWidth));
					}
					else if(Float.parseFloat(bStrokeDashLength) != 0 || Float.parseFloat(bStrokeSpaceLength) != 0){

						float[] dashPattern = {Float.parseFloat(bStrokeDashLength), Float.parseFloat(bStrokeSpaceLength)};
						stroke = new BasicStroke(Integer.parseInt(bStrokeWidth), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashPattern, 0.0f);
					}

					try {
						fileBoundary = new Boundary(Integer.parseInt(bID), Boolean.parseBoolean(bIsClosedPolygon), Integer.parseInt(bArrowType),
								Boolean.parseBoolean(bIsAnnotated), strokeC, fillC, stroke);

						fileWorld.addBoundary(fileBoundary, true);

					} catch (NegativeNumberException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ZeroArgumentException e){
						e.printStackTrace();
					}


				}
				if (store.length == 5) {
					//Checking that this has only enough values to make it a Dummy and not a Surface Connector or a Hole

					try {
						// Parsing the values to the variables

						bID = (store[0]);
						bpID = (store[1]);
						bNorthing = (store[2]);
						bEasting = (store[3]);
						bCollar = (store[4]);

						if (bID.startsWith(quotes) && bID.startsWith(quotes)){
							bID = bID.substring(1, bID.length() - 1);
						}
						//ID check for " "
						if (bpID.startsWith(quotes) && bpID.startsWith(quotes))
							bpID = bpID.substring(1, bpID.length() - 1);
						//NORTHING check for " "
						if (bNorthing.startsWith(quotes) && bNorthing.startsWith(quotes))
							bNorthing = bNorthing.substring(1, bNorthing.length() - 1);
						//EASTING check for " "
						if (bEasting.startsWith(quotes) && bEasting.startsWith(quotes))
							bEasting = bEasting.substring(1, bEasting.length() - 1);
						//COLLARRL check for " "
						if (bCollar.startsWith(quotes) && bCollar.startsWith(quotes))
							bCollar = bCollar.substring(1, bCollar.length() - 1);
						if(fileBoundary.getBoundaryID() == Integer.parseInt(bID)){
							fileBoundary.addBPoint(boundaryPointStore = new BPoint(Integer.parseInt(bID),Integer.parseInt(bpID),Double.parseDouble(bNorthing), Double.parseDouble(bEasting), Double.parseDouble(bCollar)));
						}//Adding a new Dummy Hole
						else
							System.out.println("boundary point does not belong to this boundary " + bID);

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



			}//end hole, dummy and surface parsing
		}

		fromFile.close();

		return fileWorld;
	}


	public boolean saveFile(Object o, String fileName) throws FileNotFoundException{

		System.out.println("SAVING CODE RUNNING");

		PrintStream toFile = new PrintStream(fileName);

		toFile.printf("World" + "\n");
		//		if(o instanceof World) {
		for (Pattern pattern: ((World) o).getPatternList().values()){
			System.out.println("SAVING PATTERN... " + pattern.getPatternID());
			toFile.printf(pattern.getPatternID() + "," + pattern.getBurden()+ "," + pattern.getSpacing() + "," + pattern.getNumberOfHoles() 
					+ "," + pattern.getRows() + "," + pattern.getHolesInRow() + "," + pattern.getOrientation() +"\n" );

			for (Hole h : pattern.getAllHoles()) {
				System.out.println("SAVING HOLES... " + h.getHoleID());
				if(h instanceof Hole){
					toFile.printf(pattern.getPatternID() + "," + h.getHoleID() + "," + h.getY()+ "," + h.getX() + "," + h.getZ()+ "," +h.getLabelOne() + "," + h.getLabelTwo()
							+ "," + h.getLabelThree() + ","+ h.getDiameter() + "," + h.getHoleLength()+ "," + h.getBench() + "," + h.getBearing()
							+ "," + h.getFloorRL() + "," + h.getToeRL()+ "," + h.getAngle() + "," + h.getSubdrill()+ "," + h.getShape()+ "," + h.getColor().getRed()+
							"," + h.getColor().getGreen()+"," + h.getColor().getBlue()+"\n");
				}
			}
			for(Dummy d: pattern.getAllDummys()){
				System.out.println("SAVING DUMMY... " + d.getHoleID());
				if (d instanceof Dummy && (!(d instanceof Hole))) {
					toFile.printf(pattern.getPatternID() + "," +d.getHoleID() + "," + d.getY()+ "," + d.getX() + ","+ d.getZ() + "\n");
				}

			}
			for (Detonator det : pattern.getAllDetonators()) {
				System.out.println("SAVING DETONATOR... " + det.getDetID());
				if(det instanceof Detonator){

					toFile.printf((pattern.getPatternID() + "," + det.getDetID() + "," + det.getDelay()  + "," + det.getInHole().getHoleID()+ "," +  det.getColor().getRed()
							+ "," + det.getColor().getGreen() + ","+ det.getColor().getBlue()+"\n"));
				}
			}
			for (SurfaceConnector sc : pattern.getAllSurfaceConnectors()) {
				System.out.println("SAVING SURFACE CONNECTOR... " + sc.getSurfaceID());
				if(sc instanceof SurfaceConnector){

					toFile.printf((pattern.getPatternID() + "," + sc.getSurfaceID() + "," + sc.getDelay()  + "," + sc.getFrom().getHoleID()+ "," +sc.getTo().getHoleID() + "," + sc.getColor().getRed()
							+ "," + sc.getColor().getGreen() + ","+ sc.getColor().getBlue()+"\n"));
				}
			}
			for (InitiationPoint ip: pattern.getAllInitiationPoints()){
				System.out.println("SAVING INITIATION POINT... " + ip.getIPID());
				if(ip instanceof InitiationPoint){

					toFile.printf(pattern.getPatternID() + "," + ip.getIPID() + "," + ip.getIPTime() + "," + ip.getIPDummy().getHoleID() + "\n");
				}
			}
		}

		for (Boundary boundary: ((World) o).getBoundaryList().values()){
			System.out.println("SAVING BOUNDARY... " + boundary.getBoundaryID());
			float[] dashSpace = boundary.getStroke().getDashArray();
			if(dashSpace != null){
				toFile.printf(boundary.getBoundaryID() + "," + boundary.getClosed()+ "," + boundary.getArrow() + "," + boundary.getAnnotated() 
						+ "," + boundary.getColor().getRed() + "," + boundary.getColor().getGreen() + "," + boundary.getColor().getBlue() + "," + boundary.getColor().getAlpha() 
						+ "," + boundary.getFillColor().getRed() + "," + boundary.getFillColor().getGreen() + "," + boundary.getFillColor().getBlue() + "," + boundary.getFillColor().getAlpha()
						+ "," + boundary.getStroke().getLineWidth() + "," + dashSpace[0] + "," + dashSpace[1]+ "\n");
			}
			else
				toFile.printf(boundary.getBoundaryID() + "," + boundary.getClosed()+ "," + boundary.getArrow() + "," + boundary.getAnnotated() 
						+ "," + boundary.getColor().getRed() + "," + boundary.getColor().getGreen() + "," + boundary.getColor().getBlue() + "," + boundary.getColor().getAlpha() 
						+ "," + boundary.getFillColor().getRed() + "," + boundary.getFillColor().getGreen() + "," + boundary.getFillColor().getBlue() + "," + boundary.getFillColor().getAlpha()
						+ "," + boundary.getStroke().getLineWidth() + "," + "0" + "," + "0"+ "\n");
			for(BPoint bp: boundary.getAllBPointsInBoundary()){
				if (bp instanceof BPoint) {
					toFile.printf(boundary.getBoundaryID() +"," + bp.getPointID() + "," + bp.getY()+ "," + bp.getX() + ","+ bp.getZ() + "\n");
				}

			}
		}

		for (Text text: ((World) o).getTextList().values()){
			System.out.println("SAVING TEXT... " + text.getTextID());
			toFile.printf(text.getTextID() +","+ text.getY()+","+ text.getX()+","
					+ text.getZ()+","+ text.getText()+","+ text.getBearing()+","+ text.getColor().getRed()
					+","+ text.getColor().getGreen()+","+ text.getColor().getBlue()+","+ text.getColor().getAlpha() + "\n");
		}


		//		}
		toFile.close();

		return true;
	}


	@Override
	public boolean accept(File f) {
		return f.isDirectory() || (f.isFile() && f.getName().toLowerCase().endsWith(".ltf"));
	}

	@Override
	public String getDescription() {
		return "LiteTie file (*.ltf)";
	}


}
