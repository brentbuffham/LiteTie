package litetie.model;


import java.io.Serial;
import java.io.Serializable;

/**


 */
public abstract class LTPoint3D implements Cloneable {

    /**
     * The {@code Float} class defines a point specified in float
     * precision.
     * @since 1.2
     */
    public static class Float extends LTPoint3D implements Serializable {
        /**
         * The X coordinate of this {@code LTPoint3D}.
         * @since 1.2
         * @serial
         */
        public float x;

        /**
         * The Y coordinate of this {@code LTPoint3D}.
         * @since 1.2
         * @serial
         */
        public float y;

        /**
         * Constructs and initializes a {@code LTPoint3D} with
         * coordinates (0,&nbsp;0).
         * @since 1.2
         */
        public float z;

        /**
         * Constructs and initializes a {@code Point2D} with
         * coordinates (0,&nbsp;0).
         * @since 1.2
         */
        public Float() {
        }

        /**
         * Constructs and initializes a {@code LTPoint3D} with
         * the specified coordinates.
         *
         * @param x the X coordinate of the newly
         *          constructed {@code LTPoint3D}
         * @param y the Y coordinate of the newly
         *          constructed {@code LTPoint3D}
         * @param z the Z coordinate of the newly
         *          constructed {@code LTPoint3D}
         * @since 1.2
         */
        public Float(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public double getX() {
            return (double) x;
        }

        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public double getY() {
            return (double) y;
        }
        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public double getZ() {
            return (double) y;
        }

        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public void setLocation(double x, double y , double z) {
            this.x = (float) x;
            this.y = (float) y;
            this.z = (float) z;
        }

        /**
         * Sets the location of this {@code LTPoint3D} to the
         * specified {@code float} coordinates.
         *
         * @param x the new X coordinate of this {@code LTPoint3D}
         * @param y the new Y coordinate of this {@code LTPoint3D}
         * @since 1.2
         */
        public void setLocation(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Returns a {@code String} that represents the value
         * of this {@code LTPoint3D}.
         * @return a string representation of this {@code LTPoint3D}.
         * @since 1.2
         */
        public String toString() {
            return "LTPoint3D.Float["+x+", "+y+", "+z+"]";
        }

        /**
         * Use serialVersionUID from JDK 1.6 for interoperability.
         */
        @Serial
        private static final long serialVersionUID = -2870572449815403710L;
    }

    /**
     * The {@code Double} class defines a point specified in
     * {@code double} precision.
     * @since 1.2
     */
    public static class Double extends LTPoint3D implements Serializable {
        /**
         * The X coordinate of this {@code LTPoint3D}.
         * @since 1.2
         * @serial
         */
        public double x;

        /**
         * The Y coordinate of this {@code LTPoint3D}.
         * @since 1.2
         * @serial
         */
        public double y;
        /**
         * The Z coordinate of this {@code LTPoint3D}.
         * @since 1.2
         * @serial
         */
        public double z;

        /**
         * Constructs and initializes a {@code LTPoint3D} with
         * coordinates (0,&nbsp;0).
         * @since 1.2
         */
        public Double() {
        }

        /**
         * Constructs and initializes a {@code LTPoint3D} with the
         * specified coordinates.
         *
         * @param x the X coordinate of the newly
         *          constructed {@code LTPoint2D}
         * @param y the Y coordinate of the newly
         *          constructed {@code LTPoint2D}
         * @param z the Z coordinate of the newly
         *          constructed {@code LTPoint3D}
         * @since 1.2
         */
        public Double(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public double getX() {
            return x;
        }

        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public double getY() {
            return y;
        }
        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public double getZ() {
            return z;
        }
        /**
         * {@inheritDoc}
         * @since 1.2
         */
        public void setLocation(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Returns a {@code String} that represents the value
         * of this {@code Point2D}.
         * @return a string representation of this {@code LTPoint3D}.
         * @since 1.2
         */
        public String toString() {
            return "LTPoint3D.Double["+x+", "+y+", "+z+"]";
        }

        /**
         * Use serialVersionUID from JDK 1.6 for interoperability.
         */
        @Serial
        private static final long serialVersionUID = 6150783262733311327L;
    }

    /**
     * This is an abstract class that cannot be instantiated directly.
     * Type-specific implementation subclasses are available for
     * instantiation and provide a number of formats for storing
     * the information necessary to satisfy the various accessor
     * methods below.
     *
     * @see java.awt.geom.Point2D.Float
     * @see java.awt.geom.Point2D.Double
     * @see java.awt.Point
     * @since 1.2
     */
    protected LTPoint3D() {
    }

    /**
     * Returns the X coordinate of this {@code LTPoint3D} in
     * {@code double} precision.
     * @return the X coordinate of this {@code LTPoint3D}.
     * @since 1.2
     */
    public abstract double getX();

    /**
     * Returns the Y coordinate of this {@code LTPoint3D} in
     * {@code double} precision.
     * @return the Y coordinate of this {@code LTPoint3D}.
     * @since 1.2
     */
    public abstract double getY();
    /**
     * Returns the Z coordinate of this {@code LTPoint3D} in
     * {@code double} precision.
     * @return the Z coordinate of this {@code LTPoint3D}.
     * @since 1.2
     */
    public abstract double getZ();

    /**
     * Sets the location of this {@code LTPoint3D} to the
     * specified {@code double} coordinates.
     *
     * @param x the new X coordinate of this {@code LTPoint3D}
     * @param y the new Y coordinate of this {@code LTPoint3D}
     * @since 1.2
     */
    public abstract void setLocation(double x, double y, double z);

    /**
     * Sets the location of this {@code LTPoint3D} to the same
     * coordinates as the specified {@code LTPoint3D} object.
     * @param p the specified {@code LTPoint3D} to which to set
     * this {@code LTPoint3D}
     * @since 1.2
     */
    public void setLocation(LTPoint3D p) {
        setLocation(p.getX(), p.getY(), p.getZ());
    }

    
    // Distance PLAN
    
    
    /**
     * Returns the square of the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the square of the distance between the two
     * sets of specified coordinates.
     * @since 1.2
     */
    public static double distanceSq(double x1, double y1,
                                    double x2, double y2)
    {
        x1 -= x2;
        y1 -= y2;
        return (x1 * x1 + y1 * y1);
    }

    /**
     * Returns the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the distance between the two sets of specified
     * coordinates.
     * @since 1.2
     */
    public static double distance(double x1, double y1,
                                  double x2, double y2)
    {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    /**
     * Returns the square of the distance from this
     * {@code LTPoint3D} to a specified point.
     *
     * @param px the X coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @param py the Y coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the square of the distance between this
     * {@code LTPoint3D} and the specified point.
     * @since 1.2
     */
    public double distanceSq(double px, double py) {
        px -= getX();
        py -= getY();
        return (px * px + py * py);
    }

    /**
     * Returns the square of the distance from this
     * {@code LTPoint3D} to a specified {@code LTPoint3D}.
     *
     * @param pt the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the square of the distance between this
     * {@code LTPoint3D} to a specified {@code LTPoint3D}.
     * @since 1.2
     */
    public double distanceSq(LTPoint3D pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        return (px * px + py * py);
    }

    /**
     * Returns the distance from this {@code LTPoint3D} to
     * a specified point.
     *
     * @param px the X coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @param py the Y coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the distance between this {@code LTPoint3D}
     * and a specified point.
     * @since 1.2
     */
    public double distance(double px, double py) {
        px -= getX();
        py -= getY();
        return Math.sqrt(px * px + py * py);
    }

    /**
     * Returns the distance from this {@code LTPoint3D} to a
     * specified {@code LTPoint3D}.
     *
     * @param pt the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the distance between this {@code LTPoint3D} and
     * the specified {@code LTPoint3D}.
     * @since 1.2
     */
    public double distance(LTPoint3D pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        return Math.sqrt(px * px + py * py);
    }

    /**
     * Returns the square of the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the square of the distance between the two
     * sets of specified coordinates.
     * @since 1.2
     */
    
    //Distance 3D
    
    public static double distance3DSq(double x1, double y1,
                                    double x2, double y2,
                                    double z1, double z2)
    {
        x1 -= x2;
        y1 -= y2;
        z1 -= z2;
        
        return (x1 * x1 + y1 * y1 + z1 * z2);
    }

    /**
     * Returns the distance between two points.
     *
     * @param x1 the X coordinate of the first specified point
     * @param y1 the Y coordinate of the first specified point
     * @param x2 the X coordinate of the second specified point
     * @param y2 the Y coordinate of the second specified point
     * @return the distance between the two sets of specified
     * coordinates.
     * @since 1.2
     */
    public static double distance3D(double x1, double y1,
                                  double x2, double y2,
                                  double z1, double z2)
    {
        x1 -= x2;
        y1 -= y2;
        z1 -= z2;
        return Math.sqrt(x1 * x1 + y1 * y1 + z1 * z2);
    }

    /**
     * Returns the square of the distance from this
     * {@code LTPoint3D} to a specified point.
     *
     * @param px the X coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @param py the Y coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the square of the distance between this
     * {@code LTPoint3D} and the specified point.
     * @since 1.2
     */
    public double distance3DSq(double px, double py, double pz) {
        px -= getX();
        py -= getY();
        pz -= getZ();
        return (px * px + py * py + pz * pz);
    }

    /**
     * Returns the square of the distance from this
     * {@code LTPoint3D} to a specified {@code LTPoint3D}.
     *
     * @param pt the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the square of the distance between this
     * {@code LTPoint3D} to a specified {@code LTPoint3D}.
     * @since 1.2
     */
    public double distance3DSq(LTPoint3D pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        double pz = pt.getZ() - this.getZ();
        return (px * px + py * py + pz * pz);
    }

    /**
     * Returns the distance from this {@code LTPoint3D} to
     * a specified point.
     *
     * @param px the X coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @param py the Y coordinate of the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the distance between this {@code LTPoint3D}
     * and a specified point.
     * @since 1.2
     */
    public double distance3D(double px, double py, double pz) {
        px -= getX();
        py -= getY();
        pz -= getZ();
        return Math.sqrt(px * px + py * py + pz * pz);
    }

    /**
     * Returns the distance from this {@code LTPoint3D} to a
     * specified {@code LTPoint3D}.
     *
     * @param pt the specified point to be measured
     *           against this {@code LTPoint3D}
     * @return the distance between this {@code LTPoint3D} and
     * the specified {@code LTPoint3D}.
     * @since 1.2
     */
    public double distance3D(LTPoint3D pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        double pz = pt.getZ() - this.getZ();
        return Math.sqrt(px * px + py * py + pz * pz);
    }
    
    
    /**
     * Creates a new object of the same class and with the
     * same contents as this object.
     * @return     a clone of this instance.
     * @exception  OutOfMemoryError            if there is not enough memory.
     * @see        java.lang.Cloneable
     * @since      1.2
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    /**
     * Returns the hashcode for this {@code LTPoint3D}.
     * @return      a hash code for this {@code LTPoint3D}.
     */
    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    /**
     * Determines whether or not two points are equal. Two instances of
     * {@code LTPoint3D} are equal if the values of their
     * {@code x} and {@code y} member fields, representing
     * their position in the coordinate space, are the same.
     * @param obj an object to be compared with this {@code LTPoint3D}
     * @return {@code true} if the object to be compared is
     *         an instance of {@code LTPoint3D} and has
     *         the same values; {@code false} otherwise.
     * @since 1.2
     */
    public boolean equals(Object obj) {
        if (obj instanceof LTPoint3D) {
        	LTPoint3D p3d = (LTPoint3D) obj;
            return (getX() == p3d.getX()) && (getY() == p3d.getY());
        }
        return super.equals(obj);
    }
}

