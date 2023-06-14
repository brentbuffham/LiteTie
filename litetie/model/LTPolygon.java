package litetie.model;

import java.awt.Polygon;

@SuppressWarnings("serial")
public class LTPolygon extends Polygon {

	private double[] xPoints;
	private double[] yPoints;
	private double[] zPoints;
	private int nPoints;
	
	/**
	 * Creates an Empty LiteTie Polygon
	 */
	public LTPolygon() {
		super();
	}
	
	/**
	 * Constructs a 3D polygon of Double Precision using three <code>double</code> arrays and an <code>int</code> that represents the number of points.
	 * @param xs
	 * @param ys
	 * @param zs
	 * @param ns
	 */
	public LTPolygon(double []xs, double []ys, double []zs, int ns){
		this.setxPoints(xs);
		this.setyPoints(ys);
		this.setzPoints(zs);
		this.setnPoints(ns);
	}

	/**
	 * Constructs a 3D polygon of Double Precision using a <code>Coordinate</code> Array
	 * @param coords
	 */
	public LTPolygon(Coordinate [] coords){
		this.setxPoints(new double[this.nPoints]);
		this.setyPoints(new double[this.nPoints]);
		this.setzPoints(new double[this.nPoints]);
		this.setnPoints(coords.length);

		for (int i = 0; i < this.nPoints; i++)
		{
			xPoints[i] = coords[i].getX();
			yPoints[i] = coords[i].getY();
			zPoints[i] = coords[i].getZ();
		}
	}

	public double[] getxPoints() {
		return xPoints;
	}


	public void setxPoints(double[] xPoints) {
		this.xPoints = xPoints;
	}


	public double[] getyPoints() {
		return yPoints;
	}


	public void setyPoints(double[] yPoints) {
		this.yPoints = yPoints;
	}


	public double[] getzPoints() {
		return zPoints;
	}


	public void setzPoints(double[] zPoints) {
		this.zPoints = zPoints;
	}


	public int getnPoints() {
		return nPoints;
	}


	public void setnPoints(int nPoints) {
		this.nPoints = nPoints;
	}

	public void reset()
	{
		nPoints = 0;
		bounds = null;
	}

	public void invalidate()
	{
		bounds = null;
	}

	public void translate(double deltaX, double deltaY, double deltaZ)
	{
		for (int i = 0; i < nPoints; i++)
		{
			xPoints[i] += deltaX;
			yPoints[i] += deltaY;
			zPoints[i] += deltaZ;
		}
		if (bounds != null)
		{
//			bounds.translate(deltaX, deltaY,deltaZ);
		}
	}

}
