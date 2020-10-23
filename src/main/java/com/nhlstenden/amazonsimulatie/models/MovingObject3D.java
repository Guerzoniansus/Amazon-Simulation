package com.nhlstenden.amazonsimulatie.models;

import java.util.LinkedList;
import java.util.Queue;

public abstract class MovingObject3D extends Object3D {

    protected double speed = 1;
    protected Queue<Node> path;
    protected Node currentDestination;

    public MovingObject3D(Node node, World world) {
        super(node, world);

        path = new LinkedList<>();
        currentDestination = null;
    }

    public MovingObject3D(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        path = new LinkedList<>();
        currentDestination = null;
    }

    /**
     * Attempt movement and check if any movement was made.
     * @return True if moved, false if no movement (there is no path).
     */
    protected boolean move() {
        if (currentDestination == null) {
            // No need to move
            return false;
        }

        /*
        ===== Movemement logic =====
         */

        // Code comes here

        /*
        ===== Check if reached destination =====
         */

        if (x == currentDestination.getX() && z == currentDestination.getZ()) {
            if (path.isEmpty()) {
                // Reached end of path
                currentDestination = null;
            }

            else {
                currentDestination = path.poll();
            }
        }

        return true;
    }

    /**
     * Set the path to move through
     * @param path A list of nodes
     */
    protected void setPath(Queue<Node> path) {
        this.path = path;
    }
}
