/**
 * 
 */
package litetie.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author brentbuffham
 * @since 07-11-2014 @ 09:34AM
 */
public class BulkProduct {

	private int productID;
	private String productName;
	private ArrayList<BulkProductComponent> components;
	private boolean compressible;
	
	
	public BulkProduct(int productID, String productName, ArrayList<BulkProductComponent> components, boolean compressible){
		setProductID(productID);
		setProductName(productName);
		setComponents(components);
		this.compressible = compressible;
		
	}
	
	public BulkProduct(String productName){
		setProductID(productID);
		setProductName(productName);
		setComponents(components);
		
	}
	/**
	 * Sets the BulkProduct ID integer to assist with searching through lists.<BR>
	 * Generally the preference settings should have adequate products.
	 * @param productID
	 */
	public void setProductID(int productID){
		this.productID = productID;
	}
	/**
	 * Sets the BulkProduct name such as <B>TITAN2030</B> or <B>ANFO</B>.<BR>
	 * All <code>productName</code>(s) are converted immediately to upper case.
	 * @param productName
	 */
	public void setProductName(String productName){
		this.productName = productName.toUpperCase();
	}
	/**
	 * Sets the ArrayList of components that constitute the BulkProduct.<BR>
	 * Represented using a decimal value less than or equal to 1.0.
	 * @param componentPercentageOne
	 */
	public void setComponents(ArrayList<BulkProductComponent> components) {
		this.components = components;
	}
	/**
	 * Gets the <code>productID</code> which is the product identification integer.
	 * @return productID
	 */
	public int getProductID(){
		return
				productID;
	}
	/**
	 * Gets the <code>productName</code> which is the name of the bulk explosive product.<BR>
	 * Such as <B>HEAVYANFO30%</B> or <B>ANFO</B>.
	 * @return
	 */
	public String getProductName(){
		return
				productName;
	}
	/**
	 * Gets the <code>components</code> which constitute the recipe of the bulk product. 
	 * @return components
	 */
	public ArrayList<BulkProductComponent> getComponents() {
		return
				components;
		
	}
	public double getDensity(){
		//Start with an empty double value
		double density = 0;
		//Put all the components into an Array for searching through.
		BulkProductComponent [] bulkComponents = new BulkProductComponent[getComponents().size()];
		bulkComponents = getComponents().toArray(bulkComponents);
		//Create arrays for the percentage and the density of each component.
		double [] percentMix = new double[getComponents().size()];
		double [] densityMix = new double[getComponents().size()];
		//If the bulkComponents have something in them 
		if (bulkComponents != null){
			//Then cycle trough the components and fill the percentage and density Arrays.
			int i = 0;
			for(BulkProductComponent bc: bulkComponents){
				percentMix[i] = bc.getComponentPercentage();
				densityMix[i] = bc.getDensity();
				i++;
			}
			//Once the arrays are populated sum the multiplication of the densities and percentages of each component. 
			for(int k = 0; k<bulkComponents.length; k++){
				density = percentMix[k]*densityMix[k] + density;
			}
		}
		//return the overall product density.
		return density;
	}
	public boolean isCompressible(){
		return compressible;
	}
	
	
	
	
}
