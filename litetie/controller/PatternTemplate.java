package litetie.controller;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import litetie.model.Boundary;
import litetie.model.Hole;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.Polygon2D;
import litetie.model.ZeroArgumentException;


public class PatternTemplate{
	//This is a Factory class
	//////////////////////////PROBLEMS/////////////////////////////

	final static String SQUARE = "SQUARE";
	final static String STAGGERED = "STAGGERED";
	
	public static Pattern createSquare(	double y, 
			double x, 
			double z, 
			String string1, 
			String string2, 
			String string3, 
			double startDiameter, 
			double holeLength ,
			double startBench,
			double startBearing, 
			double startFRL, 
			double startTRL, 
			double startAngle, 
			double startSubdrill, 
			int startShape, 
			Color startColor,
			double burden, 
			double spacing,
			int numberOfRows, 
			int holesInRow, 
			double orientation) 
					throws ZeroArgumentException, NegativeNumberException {

		orientation = -orientation+90;
		Pattern squarePat = new Pattern(burden, spacing);
		int holeID = 1;
		String label = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letter = "A";


		double xE,yN,yy,xx,a, eastingFinal, northingFinal;
		a = Math.toRadians(orientation);

		for (int i = 0; i < numberOfRows; i++){

			if (i <= 25) {
				letter = label.substring(i,i+1);
				//System.out.print(letter); DeBUGGER
			} else if (i <= 50 && i > 26) {
				letter = label.substring(i-27,i-26).concat(label.substring(i-27,i-26));
			} else if (i <= 70 && i > 51) {
				letter = label.substring(i - 51,i-50).concat(label.substring(i-51,i-50)).concat(label.substring(i-51,i-50));
			}
			else if(i>70){
				letter = "@";
			}

			for (int k = 0; k < holesInRow; k++){

				xE = (k*spacing);
				yN = (i*burden); 

				xx = xE*Math.cos(a) - yN*Math.sin(a);
				yy = xE*Math.sin(a) + yN*Math.cos(a);

				eastingFinal = xx + x;
				northingFinal = yy + y;

				squarePat.addDummy(new Hole((i*holesInRow+k+1), 
						eastingFinal, 
						northingFinal,
						z,
						"A"+ k, 
						" ", 
						" ",
						startDiameter,
						holeLength, 
						startBench, 
						startBearing, 
						startFRL/*floorRL*/,
						startTRL/*toeRL*/, 
						startAngle, 
						startSubdrill,
						startShape,startColor));

				squarePat.toString();

			}
		}
		return squarePat;
	}

	public static Pattern createStaggered(	double x,//1
			double y, //2
			double z, //3
			String string1, //4
			String string2, //5
			String string3, //6
			double startDiameter,//7 
			double holeLength ,//8
			double startBench,//9
			double startBearing, //10
			double startFRL, //11
			double startTRL, //12
			double startAngle, //13
			double startSubdrill, //14
			int startShape,
			Color startColor,
			double burden, //15
			double spacing,		//16	
			int numberOfRows, //17
			int holesInRow, //18
			double orientation) //19
					throws IllegalArgumentException, ZeroArgumentException, NegativeNumberException {

		orientation = -orientation+90;
		Pattern staggeredPat = new Pattern(burden, spacing);
		int holeID = 1;
		String label = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letter = "A";
		//char c;


		double xE,yN,yy,xx,a, eastingFinal, northingFinal;
		a = Math.toRadians(orientation);

		for (int i = 0; i < numberOfRows; i++){//firstrow to lastrow of grid
			//fix this

			if (i <= 25) {
				letter = label.substring(i,i+1);
				//System.out.print(letter); DeBUGGER
			} else if (i <= 50 && i > 26) {
				letter = label.substring(i-27,i-26).concat(label.substring(i-27,i-26));
			} else if (i <= 70 && i > 51) {
				letter = label.substring(i - 51,i-50).concat(label.substring(i-51,i-50)).concat(label.substring(i-51,i-50));
			}
			else if(i>70){
				letter = "@";
			}



			for (int k =0; k< holesInRow; k++){//firsthole in row to last hole in row
				if (i%2 == 0) {

					xE = (k*spacing);
					yN = (i*burden); 

					xx = xE*Math.cos(a) - yN*Math.sin(a);
					yy = xE*Math.sin(a) + yN*Math.cos(a);

					eastingFinal = xx + y;
					northingFinal = yy + x;


					staggeredPat.addDummy(new Hole(holeID++,(eastingFinal),( northingFinal),	z, letter + k, " ", " ", 
							startDiameter, holeLength,  startBench, startBearing, startFRL, startTRL, startAngle, startSubdrill,startShape,startColor ));
					staggeredPat.toString();

				}
				else{

					xE = ((k+0.5)*spacing);
					yN = (i*burden);

					xx = xE*Math.cos(a) - yN*Math.sin(a);
					yy = xE*Math.sin(a) + yN*Math.cos(a);

					eastingFinal = xx + y;
					northingFinal = yy + x;


					staggeredPat.addDummy(new Hole(holeID++,(eastingFinal),(northingFinal),	z, letter + k, "L2 ", "L3", 
							startDiameter, holeLength,  startBench, startBearing, startFRL, startTRL, startAngle, startSubdrill, startShape, startColor ));
					staggeredPat.toString();


				}
			}
		}
		return staggeredPat;

	}


	public static Pattern createPatternInBoundary(Boundary boundary,
			String patternType, double x, double y, double z, String string1,
			String string2, String string3, double startDiameter,
			double holeLength, double startBench, double startBearing,
			double startFRL, double startTRL, double startAngle,
			double startSubdrill, int startShape, Color startColor,
			double burden, double spacing, int numberOfRows, int holesInRow,
			double orientation) throws ZeroArgumentException,
			NegativeNumberException {

		System.out.println(boundary.toString());
		System.out.println(patternType);

		double[] xs = boundary.getAllXValuesInBoundary();
		double[] ys = boundary.getAllYValuesInBoundary();
		
		Polygon2D polygon = new Polygon2D(xs, ys, xs.length);
		orientation = -orientation + 90;
		Pattern patternInPoly = new Pattern(burden, spacing);
		int holeID = 1;
		String label = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letter = "A";

		double xE, yN, yy, xx, a, eastingFinal, northingFinal;
		double extraS = polygon.getBounds2D().getMinX();
		double extraB = polygon.getBounds2D().getMinY();
		a = Math.toRadians(orientation);

//		if(extraS < x){
//			extraS = spacing * ((int)(polygon.getBounds2D().getWidth()/spacing));
//			System.out.println("x is less than the minimum poygon x");
//			x = x + extraS;
//		}
//		if(polygon.getBounds2D().getMaxY() < y) {
//			extraB = burden * ((int)(polygon.getBounds2D().getHeight()/burden));
//			System.out.println("y is less than the minimum poygon y");
//			y = y - extraB;
//		}
		
		
		
		System.out.println("Just before patternType check");
		if (patternType.contains(SQUARE)) {
			System.out.println("inside patternType check");
			
			
			
			for (int i = 0; i < numberOfRows; i++) {

				if (i <= 25) {
					letter = label.substring(i, i + 1);
					// System.out.print(letter); DeBUGGER
				} else if (i <= 50 && i > 26) {
					letter = label.substring(i - 27, i - 26).concat(
							label.substring(i - 27, i - 26));
				} else if (i <= 70 && i > 51) {
					letter = label.substring(i - 51, i - 50)
							.concat(label.substring(i - 51, i - 50))
							.concat(label.substring(i - 51, i - 50));
				} else if (i > 70) {
					letter = "@";
				}

				for (int k = 0; k < holesInRow; k++) {

					xE = (k * spacing);
					yN = (i * burden);

					xx = xE * Math.cos(a) - yN * Math.sin(a);
					yy = xE * Math.sin(a) + yN * Math.cos(a);

					eastingFinal = xx + x;
					northingFinal = yy + y;

					if (polygon.contains(eastingFinal, northingFinal)) {
						patternInPoly.addDummy(new Hole(holeID, eastingFinal,
								northingFinal, z, "A" + k, " ", " ",
								startDiameter, holeLength, startBench,
								startBearing, startFRL/* floorRL */,
								startTRL/* toeRL */, startAngle, startSubdrill,
								startShape, startColor));
						holeID++;
					} else {
						System.out.println("Hole at position X:" + eastingFinal
								+ ", Y:" + northingFinal
								+ " is not within the selected polygon");
					}
					patternInPoly.toString();

				}
			}
		}// END IF SQUARE
		if (patternType.contains(STAGGERED)) {
			System.out.println("Inside Staggered Pattern Check");
			for (int i = 0; i < numberOfRows; i++){//firstrow to lastrow of grid
				//fix this

				if (i <= 25) {
					letter = label.substring(i,i+1);
					//System.out.print(letter); DeBUGGER
				} else if (i <= 50 && i > 26) {
					letter = label.substring(i-27,i-26).concat(label.substring(i-27,i-26));
				} else if (i <= 70 && i > 51) {
					letter = label.substring(i - 51,i-50).concat(label.substring(i-51,i-50)).concat(label.substring(i-51,i-50));
				}
				else if(i>70){
					letter = "@";
				}



				for (int k =0; k< holesInRow; k++){//firsthole in row to last hole in row
					if (i%2 == 0) {

						xE = (k*spacing);
						yN = (i*burden); 

						xx = xE*Math.cos(a) - yN*Math.sin(a);
						yy = xE*Math.sin(a) + yN*Math.cos(a);

						eastingFinal = xx + x;
						northingFinal = yy + y;

						if (polygon.contains(eastingFinal, northingFinal)) {
						patternInPoly.addDummy(new Hole(holeID,(eastingFinal),( northingFinal),	z, letter + k, " ", " ", 
								startDiameter, holeLength,  startBench, startBearing, startFRL, startTRL, startAngle, startSubdrill,startShape,startColor ));
						holeID++;
						}
						patternInPoly.toString();

					}
					else{

						xE = ((k+0.5)*spacing);
						yN = (i*burden);

						xx = xE*Math.cos(a) - yN*Math.sin(a);
						yy = xE*Math.sin(a) + yN*Math.cos(a);

						eastingFinal = xx + x;
						northingFinal = yy + y;

						if (polygon.contains(eastingFinal, northingFinal)) {
						patternInPoly.addDummy(new Hole(holeID,(eastingFinal),(northingFinal),	z, letter + k, "L2 ", "L3", 
								startDiameter, holeLength,  startBench, startBearing, startFRL, startTRL, startAngle, startSubdrill, startShape, startColor ));
						holeID++;
						}
						
						patternInPoly.toString();


					}
				}
			}
		}
		System.out.println("Returning Pattern");
		return patternInPoly;
	}

	public static Pattern createStaggeredInBoundary(Boundary boundary,	double x,//1
			double y, //2
			double z, //3
			String string1, //4
			String string2, //5
			String string3, //6
			double startDiameter,//7 
			double holeLength ,//8
			double startBench,//9
			double startBearing, //10
			double startFRL, //11
			double startTRL, //12
			double startAngle, //13
			double startSubdrill, //14
			int startShape,
			Color startColor,
			double burden, //15
			double spacing,		//16	
			int numberOfRows, //17
			int holesInRow, //18
			double orientation) //19
					throws IllegalArgumentException, ZeroArgumentException, NegativeNumberException {

		orientation = -orientation+90;
		Pattern staggeredPat = new Pattern(burden, spacing);
		int holeID = 1;
		String label = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String letter = "A";
		//char c;


		double xE,yN,yy,xx,a, eastingFinal, northingFinal;
		a = Math.toRadians(orientation);

		for (int i = 0; i < numberOfRows; i++){//firstrow to lastrow of grid
			//fix this

			if (i <= 25) {
				letter = label.substring(i,i+1);
				//System.out.print(letter); DeBUGGER
			} else if (i <= 50 && i > 26) {
				letter = label.substring(i-27,i-26).concat(label.substring(i-27,i-26));
			} else if (i <= 70 && i > 51) {
				letter = label.substring(i - 51,i-50).concat(label.substring(i-51,i-50)).concat(label.substring(i-51,i-50));
			}
			else if(i>70){
				letter = "@";
			}



			for (int k =0; k< holesInRow; k++){//firsthole in row to last hole in row
				if (i%2 == 0) {

					xE = (k*spacing);
					yN = (i*burden); 

					xx = xE*Math.cos(a) - yN*Math.sin(a);
					yy = xE*Math.sin(a) + yN*Math.cos(a);

					eastingFinal = xx + y;
					northingFinal = yy + x;


					staggeredPat.addDummy(new Hole(holeID++,(eastingFinal),( northingFinal),	z, letter + k, " ", " ", 
							startDiameter, holeLength,  startBench, startBearing, startFRL, startTRL, startAngle, startSubdrill,startShape,startColor ));
					staggeredPat.toString();

				}
				else{

					xE = ((k+0.5)*spacing);
					yN = (i*burden);

					xx = xE*Math.cos(a) - yN*Math.sin(a);
					yy = xE*Math.sin(a) + yN*Math.cos(a);

					eastingFinal = xx + y;
					northingFinal = yy + x;


					staggeredPat.addDummy(new Hole(holeID++,(eastingFinal),(northingFinal),	z, letter + k, "L2 ", "L3", 
							startDiameter, holeLength,  startBench, startBearing, startFRL, startTRL, startAngle, startSubdrill, startShape, startColor ));
					staggeredPat.toString();


				}
			}
		}
		return staggeredPat;

	}

}
