package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;


class Stellage extends Object3D implements Updatable {

    private Object3D parent;
    private Node storageLocation;


    Stellage(Node node, World world) {
        super(node, world);

        this.parent = null;
        this.storageLocation = null;
    }

    Stellage(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        this.parent = null;
        this.storageLocation = null;
    }

    @Override
    public boolean update() {

        if (parent != null) {
            x = parent.getX();
            y = parent.getY();
            z = parent.getZ();
            rotationY = parent.getRotationX();
            rotationY = parent.getRotationY();
            rotationZ = parent.getRotationZ();
            return true;
        }

        return false;
    }

    /**
     * Set the parent object of this stellage.
     * This stellage will be "attached" to the 3D model of the parent and move along with it.
     * @param parent The parent Object3D object
     */
    public void setParent(Object3D parent) {
        this.parent = parent;
    }

    /**
     * Check if this stellage has a designated storage location yet
     * @return Whether or not it has been assigned a storage location already
     */
    public boolean hasStorageLocation() {
        return storageLocation != null;
    }

    /**
     * Get the Node where this stellage either is stored or will be stored
     * @return The node where it will be or is stored
     */
    public Node getStorageLocation() {
        return storageLocation;
    }

    /**
     * Set the Node where this stellage will be stored
     * @param storageLocation The node
     */
    public void setStorageLocation(Node storageLocation) {
        this.storageLocation = storageLocation;
    }

}
