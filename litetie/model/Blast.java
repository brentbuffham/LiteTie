package litetie.model;

import java.util.Date;



public interface Blast {
	
	//Blast Identifiers
	public void setBlastCode(int code) throws NegativeNumberException;
	public void setBlastTitle(String title) throws IllegalArgumentException;
	public void setBlastOriginName(String originName);
	public void setBlastType(String blastType);
	
	//Blast Descriptors
	public void setBlastDescription(String description);
	public void setBlastDesignComment(String comment);
	public void setBlastDrillingComment(String comment);
	public void setBlastChargingComment(String comment);
	public void setBlastTimingComment(String comment);
	public void setBlastFiringComment(String comment);
	public void setBlastExcavationComment(String comment);
	
	//Blast Geometries
	public void setBlastOrigin(double x, double y, double z);
	public void setBlastArea(double area);
	public void setBlastVolume(double volume);
	public void setBlastMass(double mass);
	public void setBlastAverageDensity(double avDensity);
	public void setBlastWidth(double width);
	public void setBlastLength(double height);
	
	//Blast Attributes
	public void setBlastNumberOfHoles(int numberOfHoles);
	public void setBlastExplosiveMass(double massExplosives);
	public void setBlastGeologicalMode(String mostCommonGeologicalType);
	
	//Blast Date Times
	public void setBlastFiringDateTime(Date date);
	public void setBlastDesignDateTime(Date date);
	public void setBlastDrillingDateTime(Date date);
	public void setBlastChargingDateTime(Date date);
	
	//Blast Number of Patterns
	public void setNumberOfPatternsInBlast();
}
