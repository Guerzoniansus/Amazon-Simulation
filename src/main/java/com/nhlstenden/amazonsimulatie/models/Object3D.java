package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;


public abstract class Object3D {

    protected World world;

    protected UUID uuid;

    protected Node node;
    protected double x;
    protected double y;
    protected double z;

    protected double rotationX;
    protected double rotationY;
    protected double rotationZ;

    public Object3D(Node node, World world) {
        this(node, world, 0, 0, 0);
    }

    public Object3D(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        this.world = world;
        this.node = node;
        this.uuid = UUID.randomUUID();
        this.x = node.getX();
        this.y = node.getY();
        this.z = node.getZ();
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
     * Get the current node this object is home to
     * @return The node
     */
    public Node getNode() {
        return this.node;
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

    /**
     * Delete this object from the world
     */
    public void destroy() {
        //TODO: Implement
    }
}