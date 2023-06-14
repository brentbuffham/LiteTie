package litetie;
import java.awt.geom.Rectangle2D;

import litetie.model.NegativeNumberException;
import litetie.model.ZeroArgumentException;

public interface TreeProperty{
	//////////PATTERN PROPERTIES////////
	void setOrientation(double angle);
	void setAnchor(double easting, double northing, double collar) throws NegativeNumberException, NumberFormatException;
	void setLocation(double easting, double northing, double collar) throws NegativeNumberException, NumberFormatException;

	double getOrientation();

	//////////HOLE or DUMMY PROPERTIES/////////
	void setHoleID(int holeID) throws ZeroArgumentException, NegativeNumberException;

	int getHoleID();

	//////////LEAF PROPERTIES/////////
	void setNorthing(double northing) throws NegativeNumberException, NumberFormatException;
	void setEasting(double easting) throws NegativeNumberException, NumberFormatException;
	void setCollarRL(double collarRL) throws NumberFormatException;
	
	void setLabelOne (String labelOne) throws NullPointerException, IllegalArgumentException;
	void setLabelTwo (String labelTwo) throws NullPointerException, IllegalArgumentException;
	void setLabelThree (String labelThree) throws NullPointerException, IllegalArgumentException;
	void setHoleLength(double length) throws NegativeNumberException, ZeroArgumentException;
	void setDiameter (double diameter) throws NegativeNumberException, ZeroArgumentException;
	void setBench(double bench) throws NegativeNumberException, ZeroArgumentException;
	void setBearing(double bearing) throws IllegalArgumentException;
	void setFloorRL(double floorRL)throws NumberFormatException;
	void setToeRL (double toeRL);
	void setAngle(double angle);
	void setSubdrill(double subdrill);
	
	double getNorthing();
	double getEasting();
	double getCollarRL();
	double getLabelOne();
	double getLabelTwo();
	double getLabelThree();
	double getDiameter();
	double getHoleLength();
	double getBench();
	double getBearing();
	double getFloorRL();
	double getToeRL();
	double getAngle();
	double getSubdrill();
	Rectangle2D getBounds();

}
