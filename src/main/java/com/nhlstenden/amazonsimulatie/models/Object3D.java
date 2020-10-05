package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;


public abstract class Object3D {

    protected UUID uuid;

    protected double x;
    protected double y;
    protected double z;

    protected double rotationX;
    protected double rotationY;
    protected double rotationZ;

    public Object3D() {
        this(0, 0, 0, 0, 0, 0);
    }

    public Object3D(double x, double y, double z) {
        this(x, y, z, 0, 0, 0);
    }

    public Object3D(double x, double y, double z, double rotationX, double rotationY, double rotationZ) {
        this.uuid = UUID.randomUUID();
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotationX = rotationX;
        this.rotationY = rotationY;
        this.rotationZ = rotationZ;
    }

    /**
     * Get the UUID of this object as string
     * @return this object's UUID
     */
    public String getUUID() {
        return this.uuid.toString();
    }

    /**
     * Returns the class as lowercase name
     * @return the class as lowercase name
     */
    public String getType() {
        /*
         * Dit onderdeel wordt gebruikt om het type van dit object als stringwaarde terug
         * te kunnen geven. Het moet een stringwaarde zijn omdat deze informatie nodig
         * is op de client, en die verstuurd moet kunnen worden naar de browser. In de
         * javascript code wordt dit dan weer verder afgehandeld.
         */
        return this.getClass().getSimpleName().toLowerCase();
        //return Robot.class.getSimpleName().toLowerCase();
    }

    /**
     * Get the X location of this object
     * @return X
     */
    public double getX() {
        return this.x;
    }

    /**
     * Get the Y location of this object
     * @return Y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Get the Z location of this object
     * @return Z
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Get the X rotation
     * @return X rotation
     */
    public double getRotationX() {
        return this.rotationX;
    }

    /**
     * Get the Y rotation
     * @return Y rotation
     */
    public double getRotationY() {
        return this.rotationY;
    }

    /**
     * Get the Z rotation
     * @return Z rotation
     */
    public double getRotationZ() {
        return this.rotationZ;
    }
}