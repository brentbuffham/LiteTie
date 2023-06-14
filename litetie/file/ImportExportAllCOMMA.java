package litetie.file;

//IMPORT CLASSES
import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

import litetie.FileHandler;
import litetie.LiteTieTRIAL;
import litetie.model.Detonator;
import litetie.model.Dummy;
import litetie.model.FromToException;
import litetie.model.Hole;
import litetie.model.InitiationPoint;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.SurfaceConnector;
import litetie.model.ZeroArgumentException;

public class ImportExportAllCOMMA extends FileHandler {

	final String END_OF_LINE = "\r";

	public Object openFile(Frame liteTieWindow, String fileName)
			throws NumberFormatException, FileNotFoundException, ZeroArgumentException {
		// OPEN the file "fileName"
		Scanner fromFile = new Scanner(new FileInputStream(fileName));
		// SETs the delimeter to \r
		fromFile.useDelimiter(END_OF_LINE);

		// Declares integer for storage of pattern ID

		String quotes = "\"";

		String[] patternStore = fromFile.next().split(",");
		String patternID = patternStore[0];

		if (patternID.startsWith(quotes) && patternID.endsWith(quotes)) {
			patternID = patternID.substring(1, patternID.length() - 1);
		}

		Pattern filePattern = null;

		try {

			filePattern = new Pattern(Integer.parseInt(patternID), 0, 0);

		}

		catch (ZeroArgumentException e) {
			JOptionPane.showMessageDialog(liteTieWindow,
					"Zero values in file - " + e.getLocalizedMessage(),
					"ZeroArgumentException - Check File Data",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (NegativeNumberException e) {
			JOptionPane.showMessageDialog(liteTieWindow,
					"Negative values in file - " + e.getLocalizedMessage(),
					"NegativeNumberException - Check File Data",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		boolean parsing = false;

		/////////////////////////////
		// HOLE AND DUMMY ADDING //
		/////////////////////////////

		while (fromFile.hasNext()) {

			String line = fromFile.next();

			if (line.contains("HoleID")) {
				parsing = true;
				line = fromFile.next();
			}

			if (parsing == true) {
				// checking to see if the file is appropriate

				// Declaring variables to be used for Holes and Dummys
				String holeID;
				String x;
				String y;
				String z;
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
				// Declaring variables for Surface Connectors
				String toHID;
				String fromHID;
				String sDelay;
				double sLength;
				String rSValue;
				String gSValue;
				String bSValue;
				String surfaceID;
				// Declaring variables for Detonators
				String detID;
				String dDelay;
				Hole inHole;
				String inHoleID;
				double dLength;
				String rDValue;
				String gDValue;
				String bDValue;
				// Declaring variables for InitiationPoint
				String ipID;
				String ipTime;
				String ipLocationID;

				// Temporary storage objects - Each time the while repeats these values are over
				// written
				String[] store;
				Dummy dummyStore;
				Dummy from = null;
				Dummy to = null;
				Hole holeStore;
				SurfaceConnector surfaceStore = null;
				Detonator detStore = null;
				InitiationPoint ipStore = null;

				try {
					// Stating what pattern will split the line of text

					store = line.split(",");
				} catch (PatternSyntaxException pse) {
					pse.printStackTrace();
					// this is an error caused by "," make this visible
					throw pse;
				}

				if (store.length == 19) {
					// Checks if it has 15 values - Holes have 15 values
					System.out.print("In store.length 19 - code\n");
					try {
						// parsing the values into the variables
						System.out.print("In Try - code\n");
						holeID = (store[0]).trim();
						x = (store[1]).trim();
						y = (store[2]).trim();
						z = (store[3]).trim();
						labelOne = store[4].trim();
						labelTwo = store[5].trim();
						labelThree = store[6].trim();
						diameter = (store[7]).trim();
						length = (store[8]).trim();
						bench = (store[9]).trim();
						bearing = (store[10]).trim();
						floorRL = (store[11]).trim();
						toeRL = (store[12]).trim();
						angle = (store[13]).trim();
						subdrill = (store[14]).trim();
						shape = (store[15]).trim();
						colourR = (store[16]).trim();
						colourG = (store[17]).trim();
						colourB = (store[18]).trim();

						// ID check for " "
						if (holeID.startsWith(quotes) && holeID.startsWith(quotes)) {
							System.out.print("In quotes check - code\n");
							holeID = holeID.substring(1, holeID.length() - 1);
						}
						// EASTING check for " "
						if (x.startsWith(quotes) && x.startsWith(quotes))
							x = x.substring(1, x.length() - 1);
						// NORTHING check for " "
						if (y.startsWith(quotes) && y.startsWith(quotes))
							y = y.substring(1, y.length() - 1);
						// COLLARRL check for " "
						if (z.startsWith(quotes) && z.startsWith(quotes))
							z = z.substring(1, z.length() - 1);
						// LABEL ONE check for " "
						if (labelOne.startsWith(quotes) && labelOne.startsWith(quotes))
							labelOne = labelOne.substring(1, labelOne.length() - 1);
						// LABEL2 check for " "
						if (labelTwo.startsWith(quotes) && labelTwo.startsWith(quotes))
							labelTwo = labelTwo.substring(1, labelTwo.length() - 1);
						// LABEL3 check for " "
						if (labelThree.startsWith(quotes) && labelThree.startsWith(quotes))
							labelThree = labelThree.substring(1, labelThree.length() - 1);
						// DIAMETER check for " "
						if (diameter.startsWith(quotes) && diameter.startsWith(quotes))
							diameter = diameter.substring(1, diameter.length() - 1);
						// LENGTH check for " "
						if (length.startsWith(quotes) && length.startsWith(quotes))
							length = length.substring(1, length.length() - 1);
						// BENCH check for " "
						if (bench.startsWith(quotes) && bench.startsWith(quotes))
							bench = bench.substring(1, bench.length() - 1);
						// BEARING check for " "
						if (bearing.startsWith(quotes) && bearing.startsWith(quotes))
							bearing = bearing.substring(1, bearing.length() - 1);
						// FLOORRL check for " "
						if (floorRL.startsWith(quotes) && floorRL.startsWith(quotes))
							floorRL = floorRL.substring(1, floorRL.length() - 1);
						// TOERL check for " "
						if (toeRL.startsWith(quotes) && toeRL.startsWith(quotes))
							toeRL = toeRL.substring(1, toeRL.length() - 1);
						// ANGLE check for " "
						if (angle.startsWith(quotes) && angle.startsWith(quotes))
							angle = angle.substring(1, angle.length() - 1);
						// SUBDRILL check for " "
						if (subdrill.startsWith(quotes) && subdrill.startsWith(quotes))
							subdrill = subdrill.substring(1, subdrill.length() - 1);
						// SHAPE check for " "
						if (shape.startsWith(quotes) && shape.startsWith(quotes))
							shape = shape.substring(1, shape.length() - 1);
						// R
						if (colourR.startsWith(quotes) && colourR.startsWith(quotes))
							colourR = colourR.substring(1, colourR.length() - 1);
						// G
						if (colourG.startsWith(quotes) && colourG.startsWith(quotes))
							colourG = colourG.substring(1, colourG.length() - 1);
						// B
						if (colourB.startsWith(quotes) && colourB.startsWith(quotes))
							colourB = colourB.substring(1, colourB.length() - 1);

						Color color = new Color(Integer.parseInt(colourR), Integer.parseInt(colourG),
								Integer.parseInt(colourB));

						System.out.print("About to add a hole - code\n");
						// adding a new Hole to the pattern
						filePattern.addDummy(
								holeStore = new Hole(
										Integer.parseInt(holeID),
										Double.parseDouble(x),
										Double.parseDouble(y),
										Double.parseDouble(z),
										labelOne,
										labelTwo,
										labelThree,
										Double.parseDouble(diameter),
										Double.parseDouble(length),
										Double.parseDouble(bench),
										Double.parseDouble(bearing),
										Double.parseDouble(floorRL),
										Double.parseDouble(toeRL),
										Double.parseDouble(angle),
										Double.parseDouble(subdrill),
										Integer.parseInt(shape),
										color));
						LiteTieTRIAL.setConsoleOutput("\n >Hole ID " + holeID + " read and Added.");

					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(liteTieWindow,
								"Format file problems - "
										+ e.getLocalizedMessage(),
								"NFE - ImportExportAllCOMMA.java",
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
					// Checking that this has only enough values to make it a Dummy and not a
					// Surface Connector or a Hole

					try {
						// Parsing the values to the variables
						holeID = (store[0]);
						x = (store[1]);
						y = (store[2]);
						z = (store[3]);

						// ID check for " "
						if (holeID.startsWith(quotes) && holeID.startsWith(quotes))
							holeID = holeID.substring(1, holeID.length() - 1);
						// NORTHING check for " "
						if (x.startsWith(quotes) && x.startsWith(quotes))
							x = x.substring(1, x.length() - 1);
						// EASTING check for " "
						if (y.startsWith(quotes) && y.startsWith(quotes))
							y = y.substring(1, y.length() - 1);
						// COLLARRL check for " "
						if (z.startsWith(quotes) && z.startsWith(quotes))
							z = z.substring(1, z.length() - 1);

						filePattern.addDummy(dummyStore = new Dummy(Integer.parseInt(holeID), Double.parseDouble(x),
								Double.parseDouble(y), Double.parseDouble(z)));
						// Adding a new Dummy Hole

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
				// INITIATION POINTS
				else if (store.length == 3) {
					// Checking that this has only enough values to make it a InitiationPoint and
					// not a Surface Connector/Hole/Detonator/Dummy

					try {
						// Parsing the values to the variables
						ipID = (store[0]);
						ipTime = (store[1]);
						ipLocationID = (store[2]);

						// ID check for " "
						if (ipID.startsWith(quotes) && ipID.startsWith(quotes))
							ipID = ipID.substring(1, ipID.length() - 1);
						// IPTIME check for " "
						if (ipID.startsWith(quotes) && ipTime.startsWith(quotes))
							ipTime = ipTime.substring(1, ipTime.length() - 1);
						// IPLOCATIONID check for " "
						if (ipLocationID.startsWith(quotes) && ipLocationID.startsWith(quotes))
							ipLocationID = ipLocationID.substring(1, ipLocationID.length() - 1);
						Dummy dummyOrHole = null;

						Dummy dummy = filePattern.getDummy(Integer.parseInt(ipLocationID));
						Hole hole = filePattern.getHole(Integer.parseInt(ipLocationID));

						if (dummy instanceof Dummy && (!(dummyOrHole instanceof Hole)) && dummy != null) {
							dummyOrHole = filePattern.getDummy(Integer.parseInt(ipLocationID));
						}
						if (hole instanceof Hole && hole != null) {
							dummyOrHole = filePattern.getHole(Integer.parseInt(ipLocationID));
						}

						filePattern.addInitiationPoint(ipStore = new InitiationPoint(Integer.parseInt(ipID),
								Integer.parseInt(ipTime), dummyOrHole));
						// Adding a new Dummy Hole

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

				} else if (store.length == 6) {
					// Checking that this has only enough values to make it a Detonator

					try {
						// Parsing the values to the variables
						detID = (store[0]);
						dDelay = (store[1]);
						inHoleID = (store[2]);
						rDValue = (store[3]);
						gDValue = (store[4]);
						bDValue = (store[5]);

						// ID check for " "
						if (detID.startsWith(quotes) && detID.startsWith(quotes))
							detID = detID.substring(1, detID.length() - 1);
						// NORTHING check for " "
						if (dDelay.startsWith(quotes) && dDelay.startsWith(quotes))
							dDelay = dDelay.substring(1, dDelay.length() - 1);
						// INHOLEID
						if (inHoleID.startsWith(quotes) && inHoleID.startsWith(quotes))
							inHoleID = inHoleID.substring(1, inHoleID.length() - 1);
						// RDVALUE
						if (rDValue.startsWith(quotes) && rDValue.startsWith(quotes))
							rDValue = rDValue.substring(1, rDValue.length() - 1);
						// GDVALUE
						if (gDValue.startsWith(quotes) && gDValue.startsWith(quotes))
							gDValue = gDValue.substring(1, gDValue.length() - 1);
						// BDVALUE
						if (bDValue.startsWith(quotes) && bDValue.startsWith(quotes))
							bDValue = bDValue.substring(1, bDValue.length() - 1);

						Color c = new Color(Integer.parseInt(rDValue), Integer.parseInt(gDValue),
								Integer.parseInt(bDValue));
						inHole = filePattern.getHoleList().get(Integer.parseInt(inHoleID));

						dLength = inHole.getHoleLength() - inHole.getSubdrill();

						filePattern.addDetonator(detStore = new Detonator(Integer.parseInt(detID),
								Integer.parseInt(dDelay), inHole, dLength, c));
						// Adding a new Detonator

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

				} else if (store.length == 7) {
					// Checking that it has 8 values which makes it a Surface Connector

					try {
						// parsing the values to the variables
						surfaceID = (store[0]);
						sDelay = (store[1]);
						fromHID = (store[2]);
						toHID = (store[3]);
						rSValue = (store[4]);
						gSValue = (store[5]);
						bSValue = (store[6]);

						// ID check for " "
						if (surfaceID.startsWith(quotes) && surfaceID.startsWith(quotes))
							surfaceID = surfaceID.substring(1, surfaceID.length() - 1);
						// Delay check for " "
						if (sDelay.startsWith(quotes) && sDelay.startsWith(quotes))
							sDelay = sDelay.substring(1, sDelay.length() - 1);
						// INHOLEID
						if (fromHID.startsWith(quotes) && fromHID.startsWith(quotes))
							fromHID = fromHID.substring(1, fromHID.length() - 1);
						// RDVALUE
						if (toHID.startsWith(quotes) && toHID.startsWith(quotes))
							toHID = toHID.substring(1, toHID.length() - 1);
						// GDVALUE
						if (rSValue.startsWith(quotes) && rSValue.startsWith(quotes))
							rSValue = rSValue.substring(1, rSValue.length() - 1);
						// BDVALUE
						if (gSValue.startsWith(quotes) && gSValue.startsWith(quotes))
							gSValue = gSValue.substring(1, gSValue.length() - 1);
						// BDVALUE
						if (bSValue.startsWith(quotes) && bSValue.charAt(bSValue.length() - 1) == '"')
							bSValue = bSValue.substring(1, bSValue.length() - 1);

						Color c = new Color(Integer.parseInt(rSValue), Integer.parseInt(gSValue),
								Integer.parseInt(bSValue));
						//

						if (filePattern.getDummyList().get(Integer.parseInt(fromHID)) != null
								&& filePattern.getDummyList().get(Integer.parseInt(toHID)) != null) {
							from = filePattern.getDummyList().get(Integer.parseInt(fromHID));
							to = filePattern.getDummyList().get(Integer.parseInt(toHID));
						} else if (filePattern.getDummyList().get(Integer.parseInt(toHID)) != null
								&& filePattern.getHoleList().get(Integer.parseInt(fromHID)) != null) {
							from = filePattern.getHoleList().get(Integer.parseInt(fromHID));
							to = filePattern.getDummyList().get(Integer.parseInt(toHID));
						} else if (filePattern.getHoleList().get(Integer.parseInt(toHID)) != null
								&& filePattern.getDummyList().get(Integer.parseInt(fromHID)) != null) {
							from = filePattern.getDummyList().get(Integer.parseInt(fromHID));
							to = filePattern.getHoleList().get(Integer.parseInt(toHID));
						} else if (filePattern.getHoleList().get(Integer.parseInt(toHID)) != null
								&& filePattern.getHoleList().get(Integer.parseInt(fromHID)) != null) {
							from = filePattern.getHoleList().get(Integer.parseInt(fromHID));
							to = filePattern.getHoleList().get(Integer.parseInt(toHID));
						}

						double xl = to.getX() - from.getX();// adj length
						double yl = to.getY() - from.getY();// opp length
						sLength = Math.sqrt(Math.pow(xl, 2) + Math.pow(yl, 2));// hyp length

						filePattern.addSurfaceConnector(surfaceStore = new SurfaceConnector(Integer.parseInt(surfaceID),
								Integer.parseInt(sDelay), sLength, from, to, c));
						// Add a surface connector

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

				} else
					System.out.println("Unexpected Store Length " + store.length);

			} // end hole, dummy and surface parsing

		}

		fromFile.close();
		return filePattern;
	}

	public boolean saveFile(Object pattern, String fileName) throws FileNotFoundException {

		PrintStream toFile = new PrintStream(fileName);

		toFile.printf(((Pattern) pattern).getPatternID() + ",,,,,,,,,,,,,,,,,," + END_OF_LINE);
		toFile.printf(
				"HoleID,Easting,Northing,Collar Level,Label One,Label Two,Label Three,Diameter,Hole Length,Bench Height,"
						+
						"Bearing,Floor Level,Toe Level,Angle,Subdrill,Shape,Red,Green,Blue" + END_OF_LINE);

		for (Hole h : ((Pattern) pattern).getAllHoles()) {
			if (h instanceof Hole) {
				toFile.printf((h.getHoleID()) + "," + h.getX() + "," + h.getY() + "," + h.getZ() + "," + h.getLabelOne()
						+ "," + h.getLabelTwo()
						+ "," + h.getLabelThree() + "," + h.getDiameter() + "," + h.getHoleLength() + "," + h.getBench()
						+ "," + h.getBearing()
						+ "," + h.getFloorRL() + "," + h.getToeRL() + "," + h.getAngle() + "," + h.getSubdrill() + ","
						+ h.getShape() + "," + h.getColor().getRed() +
						"," + h.getColor().getGreen() + "," + h.getColor().getBlue() + END_OF_LINE);
			}
		}

		for (Dummy d : ((Pattern) pattern).getAllDummys()) {
			if (d instanceof Dummy && (!(d instanceof Hole))) {
				toFile.printf((d.getHoleID()) + "," + d.getX() + "," + d.getY() + "," + d.getZ() + END_OF_LINE);
			}

		}
		for (Detonator det : ((Pattern) pattern).getAllDetonators()) {

			if (det instanceof Detonator) {

				toFile.printf((det.getDetID() + "," + det.getDelay() + "," + det.getInHole().getHoleID() + ","
						+ det.getColor().getRed()
						+ "," + det.getColor().getGreen() + "," + det.getColor().getBlue() + END_OF_LINE));
			}
		}

		for (SurfaceConnector sc : ((Pattern) pattern).getAllSurfaceConnectors()) {

			if (sc instanceof SurfaceConnector) {

				toFile.printf((sc.getSurfaceID() + "," + sc.getDelay() + "," + sc.getFrom().getHoleID() + ","
						+ sc.getTo().getHoleID() + "," + sc.getColor().getRed()
						+ "," + sc.getColor().getGreen() + "," + sc.getColor().getBlue() + END_OF_LINE));
			}
		}
		for (InitiationPoint ip : ((Pattern) pattern).getAllInitiationPoints()) {

			if (ip instanceof InitiationPoint) {

				toFile.printf((ip.getIPID() + "," + ip.getIPTime() + "," + ip.getIPDummy().getHoleID() + END_OF_LINE));
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
		return "Comma Separated Values (*.csv)";
	}

}
