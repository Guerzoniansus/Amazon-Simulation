package com.nhlstenden.amazonsimulatie.models;

public class Stellage extends Object3D implements Updatable {

    private Object3D parent;
    private Node storageLocation;
    private StellageStatus status;

    Stellage(StellageStatus status, Node node, World world) {
        this(status, null, node, world, 0, 0, 0);
    }

    Stellage(StellageStatus status, Node storageLocation, Node node, World world) {
        this(status, storageLocation, node, world, 0, 0, 0);
    }

    Stellage(StellageStatus status, Node storageLocation, Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        this.parent = null;
        this.storageLocation = storageLocation;
        this.status = status;
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
     * Remove this stellage's parent object (essentially sets it to null)
     */
    public void removeParent() {
        parent = null;
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

    /**
     * Removes this stellage's storage location (essentially sets it to null)
     */
    public void removeStoragelocation() {
        storageLocation = null;
    }

    /**
     * Returns the Stellage Status
     * @return The StellageStatus
     */
    public StellageStatus getStatus() {
        return status;
    }

    /**
     * Sets the Stellage Status
     * @param status The new Stellage Status
     */
    public void setStatus(StellageStatus status) {
        this.status = status;
    }

}
