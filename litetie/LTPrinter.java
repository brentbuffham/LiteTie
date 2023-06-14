package litetie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.Collection;

import litetie.model.Boundary;
import litetie.model.Dummy;
import litetie.model.InitiationPoint;
import litetie.model.Pattern;
import litetie.model.SurfaceConnector;
import litetie.model.Text;
import litetie.model.World;
import litetie.view.drawing.Visualise2D;

class LTPrinter implements Printable{
	public int print(Graphics g2,	PageFormat pageFormat, int pageIndex) throws PrinterException {
		return pageIndex;
//		Graphics2D g2D = (Graphics2D)g2;
//	    g2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
//
//	    // Now we perform our rendering
//		Rectangle2D bounds = new Rectangle2D.Double(pageFormat.getPaper().getImageableX(),
//				pageFormat.getPaper().getImageableX(),
//				pageFormat.getPaper().getImageableWidth(),
//				pageFormat.getPaper().getImageableHeight()); // this returns the canvas bounds
//
//		Visualise2D renderer = new Visualise2D();
//		//Draws Ties
//		for(Pattern tempPat: World.getPatternList().values()){
//
//			for(SurfaceConnector s: tempPat.getSurfaceList().values()){
//				//UNCOMMENT PAINTPOLYBOUNDS IF YOU WISH TO VIEW THE BOUND OF A SURFACE CONNECTOR
////				renderer.paintPolyBounds(g2, s, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//				boolean isSelected = s == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(s));
//
//				if(tieOnOffButton.isSelected())	{
//					if(s.getFrom() instanceof Dummy && s.getTo() instanceof Dummy){
//						renderer.paintTie((Graphics2D) g2, s, bounds, averageSize,averageSize, s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//					else if(s.getFrom() instanceof Hole && s.getTo() instanceof Dummy){
//						renderer.paintTie((Graphics2D) g2, s, bounds, ((Hole) s.getFrom()).getDiameter(), averageSize, s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//					else if(s.getFrom() instanceof Dummy && s.getTo() instanceof Hole){
//						renderer.paintTie((Graphics2D) g2, s, bounds, averageSize, ((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//					else if(s.getFrom() instanceof Hole && s.getTo() instanceof Hole){
//						renderer.paintTie((Graphics2D) g2, s, bounds, ((Hole) s.getFrom()).getDiameter(),((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//				}
//			}
//		}
//		for(Pattern tempPat: world.getPatternList().values()){
//			for(SurfaceConnector s: tempPat.getSurfaceList().values()){
//				//UNCOMMENT PAINTPOLYBOUNDS IF YOU WISH TO VIEW THE BOUND OF A SURFACE CONNECTOR
//				//							renderer.paintPolyBounds(g2, s, bounds, ZoomScale.getZoomX()* UnitConvert.metersToPixels(1));
//				boolean isSelected = s == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(s));
//
//				if(tieDelayToggleButton.isSelected())	{
//					if(s.getFrom() instanceof Dummy && s.getTo() instanceof Dummy){
//						renderer.paintDelay((Graphics2D) g2, s, bounds, averageSize,averageSize, s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//					else if(s.getFrom() instanceof Hole && s.getTo() instanceof Dummy){
//						renderer.paintDelay((Graphics2D) g2, s, bounds, ((Hole) s.getFrom()).getDiameter(), averageSize, s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//					else if(s.getFrom() instanceof Dummy && s.getTo() instanceof Hole){
//						renderer.paintDelay((Graphics2D) g2, s, bounds, averageSize, ((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//					else if(s.getFrom() instanceof Hole && s.getTo() instanceof Hole){
//						renderer.paintDelay((Graphics2D) g2, s, bounds, ((Hole) s.getFrom()).getDiameter(),((Hole) s.getTo()).getDiameter(), s.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, enhanceTie, enhance);
//					}
//				}
//			}
//		}
//		for(Pattern tempPat: world.getPatternList().values()){
//			for(InitiationPoint i: tempPat.getiPList().values()){
//				boolean isSelected = i == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(i));	
//				if(i.getIPDummy() instanceof Dummy || i.getIPDummy() instanceof Hole){
//					renderer.paintIP((Graphics2D) g2, i.getIPDummy(), bounds, averageSize, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected,  enhance);
//				}
//			}
//		}
//		for (Text tempText: world.getTextList().values()){
//
//			boolean isSelected = tempText == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(tempText));
//
//			Text t = tempText;
//
//			if(textOnOffButton.isSelected()){
//				if(t instanceof Text){
//					renderer.paintText((Graphics2D) g2, t.getColor(), t, t.getBearing(), bounds ,Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, toolRotateButton.isSelected() , enhance);
//				}
//			}
//		}
//		for (Boundary tempBoundary: world.getBoundaryList().values()){
//			boolean isSelected = tempBoundary == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(tempBoundary));	
//			Boundary b = tempBoundary;
//
//			if(boundaryOnOffButton.isSelected()){
//				if(tempBoundary instanceof Boundary){
//					renderer.paintPolyLine((Graphics2D) g2, b.getColor(), b.getFillColor(), b.getStroke(), b, coord1, coord2, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected, b.isClosed(),b.getArrow(),b.getAnnotated());
//				}
//
//			}
//			for(BPoint i: tempBoundary.getBPList().values()){
//				boolean isSelected1 = i == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(i));	
//				BPoint bp = i;
//				if(boundaryOnOffButton.isSelected()){
//					if(bp instanceof BPoint){
//						renderer.paintBPoint((Graphics2D) g2, bp, tempBoundary,mouseE,mouseN,centerBoundsEasting, centerBoundsNorthing, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected1, getToolMoveButton().isSelected(),  enhance);
//					}
//				}
//			}
//
//		}
//		for(Pattern tempPat: world.getPatternList().values()) { //For every pattern in the pattern list do the following
//			for(Dummy tempDummy: tempPat.getAllDummysAndHoles()) { // and for every dummy / hole in tempPat do the following// the above two statements mean that every thing below will be done to every hole or dummy in all patterns on the canvas
//				boolean isSelected = tempDummy == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(tempDummy));//isSelected is saying that all holes and dummys in the selectedObject Object or in the collection of selected objects will be affected
//				Dummy d = tempDummy;
//				if(tempDummy instanceof Dummy && (!(tempDummy instanceof Hole)) && dummyOnOffButton.isSelected()){
//					renderer.paintDummy((Graphics2D) g2, d, centerBoundsEasting, centerBoundsNorthing ,bounds,averageSize,Zoom.getScalingFactor()* UnitConvert.metersToPixels(1),isSelected, toolMoveButton.isSelected() , enhance);
//					if (holeIDOnOffButton.isSelected()){
//						if(tempDummy instanceof Dummy && (!(tempDummy instanceof Hole))){
//							renderer.paintHoleID((Graphics2D) g2, tempDummy, null, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance);
//						}
//					}	
//				}				
//				if (tempDummy instanceof Hole) {
//					Hole h = (Hole)tempDummy;
//					if(holeTracksOnOffButton.isSelected()) {
//						if (h.getAngle() != 90){ 
//							renderer.paintTrack((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1), isSelected, toolRotateButton.isSelected());  
//						}
//					}
//					if (tempDummy instanceof Hole && holesOnOffButton.isSelected()) {
//						renderer.paintHole((Graphics2D) g2, h,  centerBoundsEasting, centerBoundsNorthing, mouseE, mouseN, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance , isSelected, toolMoveButton.isSelected(), h.getShape());
//
//					}
//					if(fRLMarkerButton.isSelected()) {
//						if (h.getAngle() != 90){ 
//							renderer.paintFloorLine((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1), isSelected, enhance);  
//						}
//					}
//					if(tRLMarkerButton.isSelected()) {
//						if (h.getAngle() != 90){ 
//							renderer.paintToeLine((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1), isSelected, enhance);  
//						}
//					}
//					if(fRLCircleMarkerButton.isSelected()) {
//						if (h.getAngle() != 90){ 
//							renderer.paintFloorCircle((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1), isSelected, enhance);  
//						}
//					}
//					if(tRLCircleMarkerButton.isSelected()) {
//						if (h.getAngle() != 90){ 
//							renderer.paintToeCircle((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1), isSelected, enhance);  
//						}
//					}
//					if (holeSubdrillOnOffButton.isSelected()){
//						renderer.paintSubdrill((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance);
//					}
//					if (collarRLOnOffButton.isSelected()){
//						renderer.paintZLevel((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//					}
//					if (toeRLOnOffButton.isSelected()){
//						renderer.paintToeRL((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//					}
//					if (floorRLOnOffButton.isSelected()){
//						renderer.paintFloorRL((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//					}
//					if (holeIDOnOffButton.isSelected()){
//						if(tempDummy instanceof Hole){
//							renderer.paintHoleID((Graphics2D) g2, tempDummy, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance);
//						}
//					}
//					if (holeLabel1OnOffButton.isSelected()){
//						if(tempDummy instanceof Hole){
//							renderer.paintHoleLabel1((Graphics2D) g2, tempDummy,h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance);
//						}
//					}
//					if (holeLabel2OnOffButton.isSelected()){
//						if(tempDummy instanceof Hole){
//							renderer.paintHoleLabel2((Graphics2D) g2, tempDummy,h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance);
//						}
//					}
//					if (holeLabel3OnOffButton.isSelected()){
//						if(tempDummy instanceof Hole){
//							renderer.paintHoleLabel3((Graphics2D) g2, tempDummy,h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance);
//						}
//					}
//					if (diameterOnOffButton.isSelected()){
//						if(tempDummy instanceof Hole){
//							renderer.paintDiameter((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//						}
//					}
//					if (angleOnOffButton.isSelected()){
//						if(tempDummy instanceof Hole){
//							renderer.paintAngle((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhance);
//						}
//					}
//					if (bearingOnOffButton.isSelected()){
//						if(tempDummy instanceof Dummy){
//							renderer.paintBearing((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//						}
//					}
//					if (holeLengthOnOffButton.isSelected()){
//						renderer.paintHoleLength((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//					}
//					if (benchHeightOnOffButton.isSelected()){
//						renderer.paintBench((Graphics2D) g2, h, bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1));
//					}
//				}
//			}
//		}
//		//Draws Detonators
//		for(Pattern tempPat: world.getPatternList().values()){
//			for(Detonator d: tempPat.getDetonatorList().values()){
//				boolean isSelected = d == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(d));	
//
//
//				if(detsOnOffButton.isSelected())	{
//					if(d.getInHole() instanceof Hole ){
//						renderer.paintDetonator((Graphics2D) g2, d.getInHole(), bounds, d.getInHole().getDiameter(), d.getColor(), Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), isSelected,  enhance);
//					}
//
//				}
//			}
//		}
//		//Draws SurfaceTimes
//		if(surfaceTimesOnOffButton.isSelected())	{
//			try {
//				getSurfaceTimes();
//			} catch (NegativeNumberException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			for(Pattern tempPat: world.getPatternList().values()){
//				for(SurfaceConnector sc: tempPat.getSurfaceList().values()){
//					//							boolean isSelected = sc == selected || (selected instanceof Collection<?> && ((Collection<?>)selected).contains(sc));	
//
//					if(sc.getTo() instanceof Dummy ){
//						renderer.paintSurfaceTimes((Graphics2D) g2, sc, sc.getTo(), getColorButton().getBackground(), bounds, Zoom.getScalingFactor()* UnitConvert.metersToPixels(1), enhanceTie, enhance);
//					}
//
//				}
//			}
//		}
//
////GRID RESIZING WITH ZOOM 
//		if (gridOnOffButton.isSelected() && Zoom.getScalingFactor()>0.0030) {						
//			renderer.paintGrid(
//					//GRAPHICS OBJECT
//					(Graphics2D) g2, 
//					//GET THE BOUNDS OF THE AREA TO DRAW IN
//					canvasPanel.getBounds(),
//					//DRAW LINES PORTION
//					new Rectangle2D.Double(
//							canvasPanel.getBounds().getX(), 
//							canvasPanel.getBounds().getY(),
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10)), 
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10))),
//					//DRAW THE TEXT LABELS
//							new Rectangle2D.Double(
//									bounds.getX(), 
//									bounds.getY(),
//									10, 
//									10));
//		}
//		else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor()<0.0030 && Zoom.getScalingFactor()>0.002) {						
//			renderer.paintGrid(
//					//GRAPHICS OBJECT
//					(Graphics2D) g2, 
//					//GET THE BOUNDS OF THE AREA TO DRAW IN
//					canvasPanel.getBounds(),
//					//DRAW LINES PORTION
//					new Rectangle2D.Double(
//							canvasPanel.getBounds().getX(), 
//							canvasPanel.getBounds().getY(),
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(50)), 
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(50))),
//					//DRAW THE TEXT LABELS
//							new Rectangle2D.Double(
//									bounds.getX(), 
//									bounds.getY(),
//									50, 
//									50));
//		}
//		else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor()<0.002 && Zoom.getScalingFactor()>0.0001) {						
//			renderer.paintGrid(
//					//GRAPHICS OBJECT
//					(Graphics2D) g2, 
//					//GET THE BOUNDS OF THE AREA TO DRAW IN
//					canvasPanel.getBounds(),
//					//DRAW LINES PORTION
//					new Rectangle2D.Double(
//							canvasPanel.getBounds().getX(), 
//							canvasPanel.getBounds().getY(),
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(200)), 
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(200))),
//					//DRAW THE TEXT LABELS
//							new Rectangle2D.Double(
//									bounds.getX(), 
//									bounds.getY(),
//									200, 
//									200));
//		}
//		else if (gridOnOffButton.isSelected() && Zoom.getScalingFactor()<0.0001 && Zoom.getScalingFactor()>0.00001) {						
//			renderer.paintGrid(
//					//GRAPHICS OBJECT
//					(Graphics2D) g2, 
//					//GET THE BOUNDS OF THE AREA TO DRAW IN
//					canvasPanel.getBounds(),
//					//DRAW LINES PORTION
//					new Rectangle2D.Double(
//							canvasPanel.getBounds().getX(), 
//							canvasPanel.getBounds().getY(),
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(1000)), 
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(1000))),
//					//DRAW THE TEXT LABELS
//							new Rectangle2D.Double(
//									bounds.getX(), 
//									bounds.getY(),
//									1000, 
//									1000));
//		}
//		else if (gridOnOffButton.isSelected() &&  Zoom.getScalingFactor()<0.00001&&  Zoom.getScalingFactor()>0.000001) {						
//			renderer.paintGrid(
//					//GRAPHICS OBJECT
//					(Graphics2D) g2, 
//					//GET THE BOUNDS OF THE AREA TO DRAW IN
//					canvasPanel.getBounds(),
//					//DRAW LINES PORTION
//					new Rectangle2D.Double(
//							canvasPanel.getBounds().getX(), 
//							canvasPanel.getBounds().getY(),
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10000)), 
//							(Zoom.getScalingFactor() * UnitConvert.metersToPixels(10000))),
//					//DRAW THE TEXT LABELS
//							new Rectangle2D.Double(
//									bounds.getX(), 
//									bounds.getY(),
//									10000, 
//									10000));
//		}
//		else if (gridOnOffButton.isSelected() &&  Zoom.getScalingFactor()<0.000001) {						
//			gridOnOffButton.setSelected(false);
//			setConsoleOutput("Grid OFF - Default Memory Conservation");
//		}
//		//PolyMarguee draw
//		if (!(polyPointsX.isEmpty() && polyPointsY.isEmpty())){
//			renderer.paintPolyMarquee((Graphics2D) g2, polyPointsX, polyPointsY, bounds,Zoom.getScalingFactor()*UnitConvert.metersToPixels(1), selectionType);
//		}
//		//Marguee draw
//		boolean selectRect = getToolMarqueeButton().isSelected();
//		boolean selectElipse = getToolSelectionOval().isSelected();
//		if (selectionPoint1 != null && selectionPoint2 != null){
//			renderer.paintMarquee((Graphics2D) g2, bounds, selectionPoint1, selectionPoint2, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1), selectionType, selectRect, selectElipse);
//		}
//		if (rulePoint1 != null && rulePoint2 != null){
//			renderer.paintRuler((Graphics2D) g2, bounds, rulePoint1, rulePoint2, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1));
//		}
//		if (bearingPoint1 !=null && bearingPoint2 != null){
//			renderer.paintAngleMeasure((Graphics2D) g2, bounds, bearingPoint1, bearingPoint2, bearingPoint3, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1));
//		}
//		if (zoomPoint1 != null && zoomPoint2 != null){
//			renderer.paintZoomMarquee((Graphics2D) g2, bounds, zoomPoint1, zoomPoint2, Zoom.getScalingFactor()*UnitConvert.metersToPixels(1));
//		}
//	
////		canvasPanel.print(g2);
//		
//		return PAGE_EXISTS;
	}
}
	