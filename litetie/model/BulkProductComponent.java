/**
 * 
 */
package litetie.model;

/**
 * @author brentbuffham
 * @since 07-11-2014 @ 10:04AM
 */
public class BulkProductComponent {

	private String componentName;
	private double componentPercentage;
	private double density;
	
	public static final double AIR_DENSITY = 0.00120479; //This Value is at sea level and can change dependent on pressure
	public static final double AN_POROUS_PRILL_DENSITY = 0.82;
	public static final double AN_DENSE_PRILL_DENSITY = 0.95;
	public static final double EM_DYNO_DENSITY = 1.33;
	public static final double WG_MAXAM_DENSITY = 1.53;
	public static final double EM_ORICA_DENSITY = 1.34;
	public static final double FO_DIESEL_DENSITY = 0.835; //@ 15°C
	
	
	public BulkProductComponent(String componentName, double componentPercentage , double density){
		setComponentName(componentName);
		setComponentPercentage(componentPercentage);
		setDensity(density);
	}
	
	
	/**
	 * The name of the first component of the BulkProduct.<BR>
	 * All <code>componentName</code>(s) are converted immediately to upper case.
	 * @param componentNameOne
	 */
	public void setComponentName(String componentName){
		this.componentName = componentName.toUpperCase();
	}
	/**
	 * The component percentage of the BulkProduct.<BR>
	 * Represented using a decimal value less than or equal to 1.0.
	 * @param componentPercentageOne
	 */
	public void setComponentPercentage(double componentPercentage){
		this.componentPercentage = componentPercentage;
	}
	/**
	 * The density of the component of the BulkProduct.
	 * Available in this class are some final values.
	 * @param density
	 */
	public void setDensity(double density){
		this.density = density;
	}
	
	/**
	 * Gets the <code>componentName</code> in <B><I>UPPERCASE</B></I>.<BR>
	 * @return componentName
	 */
	public String getComponentName(){
		return
				componentName.toUpperCase();
	}
	/**
	 * Gets the <code>componentPercentage</code> in a decimal format equal to or less than <I>1.0</I> but greater than or equal to <I>0.0</I>.<BR>
	 * @return componentName
	 */
	public double getComponentPercentage(){
		return
				componentPercentage;
	}
	/**
	 * Gets the <code>density</code> of the BulkProductComponent this can also be a final value from the class.
	 * @return
	 */
	public double getDensity(){
		return
				density;
	}
}
