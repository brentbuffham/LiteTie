package litetie;

import java.util.Set;

import litetie.model.Detonator;
import litetie.model.Dummy;
import litetie.model.InitiationPoint;
import litetie.model.NegativeNumberException;
import litetie.model.Pattern;
import litetie.model.SurfaceConnector;
import litetie.model.World;
import litetie.model.ZeroArgumentException;

public class InitiationTime{
	
	protected int id;
	protected int time;
	protected Dummy dummy;
	protected SurfaceConnector surface;
	protected Detonator detonator;
	
	public InitiationTime (Dummy dummy, SurfaceConnector surface/*, Detonator detonator*/) throws NegativeNumberException {
		setDummy(dummy);
		setSurface(surface);
//		setDetonator(detonator);
	}
	public InitiationTime (int id, Dummy dummy, SurfaceConnector surface, Detonator detonator) throws NegativeNumberException, ZeroArgumentException {
		setID(id);
		setDummy(dummy);
		setSurface(surface);
		setDetonator(detonator);
	}
	
//	public boolean equals(Object duplicateTime) {
//		if (duplicateTime == null)
//			return false;
//		if (!(duplicateTime instanceof InitiationTime))
//			return false;
//		InitiationTime time = (InitiationTime)duplicateTime;
//		return
//			this.time.equals(dummy.);			
//	}
//	

	
	public String toString() {
		return
			("Initiation time is " + time +" ms\nPoint is " + dummy.getHoleID());
	}
	
	
	public int getTime(){
		return time;
	}
	public Dummy getDummy(){
		return dummy;
	}
	public SurfaceConnector getSurface() {
		return surface;
	}
	public Detonator getDetonator() {
		return detonator;
	}
	public int getID(){
		return id;
	}
	

	
	public void setID(int id) throws NegativeNumberException, ZeroArgumentException{
		if (id == 0)
			throw new ZeroArgumentException("An ID number can not be equal to 0. - InitiationTime  Class");
		else if (id < 0)
			throw new NegativeNumberException ("An ID number can not be negative - InitiationTime  Class");
		else
			this.id = id;
	
	}
	
	public void setTime(int iPTime) throws NegativeNumberException {
		if (iPTime < 0 )
			throw new NegativeNumberException ("time can not be negative");
		else
			this.time = time;
	}
	public void setDummy (Dummy dummy) throws IllegalArgumentException {
		if (dummy == null)
			throw new IllegalArgumentException ("Time(s) must be implemented on a dummy or a hole.");
		else
			this.dummy = dummy;
	}
	public void setSurface (SurfaceConnector surface) throws IllegalArgumentException {
		if (surface == null)
			throw new IllegalArgumentException ("Time(s) must have a surface connector to be instanctiated");
		else
			this.surface = surface;
	}
	public void setDetonator (Detonator detonator) throws IllegalArgumentException {
		if (detonator == null)
			throw new IllegalArgumentException ("Time(s) must have a detonator to be instanctiated");
		else
			this.detonator = detonator;
	}
	
	public void getSurfaceTimes(World world, SurfaceConnector surface){
		for (Pattern tempPattern: world.getPatternList().values()){
			Set<Dummy> all = tempPattern.getAllDummysAndHoles();
			for(InitiationPoint ip: tempPattern.getiPList().values()){
				try {
					setTime(ip.getIPTime());
				} catch (NegativeNumberException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(Dummy dummy: all){
					if (dummy == surface.getTo()){
						try {
							setTime(this.getTime() + surface.getDelay());
						} catch (NegativeNumberException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}

	}
	
}
