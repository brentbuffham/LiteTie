/**
 * 
 */
package litetie.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author brentbuffham
 *
 */
public class ConstructedBlast implements Blast {
	
	Collection<Pattern> patterns = new ArrayList<Pattern>();
	
	public ConstructedBlast(Collection<Pattern> patterns){
		this.patterns = patterns;
	}

	@Override
	public void setBlastCode(int code) throws NegativeNumberException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastTitle(String title) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastOriginName(String originName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastType(String blastType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastDescription(String description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastDesignComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastDrillingComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastChargingComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastTimingComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastFiringComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastExcavationComment(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastOrigin(double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastArea(double area) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastVolume(double volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastMass(double mass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastAverageDensity(double avDensity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastWidth(double width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastLength(double height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastNumberOfHoles(int numberOfHoles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastExplosiveMass(double massExplosives) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastGeologicalMode(String mostCommonGeologicalType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastFiringDateTime(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastDesignDateTime(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastDrillingDateTime(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlastChargingDateTime(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNumberOfPatternsInBlast() {
		// TODO Auto-generated method stub
		
	}

}
