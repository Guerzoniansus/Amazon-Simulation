package com.nhlstenden.amazonsimulatie.models;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

abstract class MovingObject3D extends Object3D {

    protected double speed = 0.1;

    protected Queue<Node> path;
    protected Node currentDestination;

    public MovingObject3D(Node node, World world) {
        this(node, world, 0, 0, 0);
    }

    public MovingObject3D(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        path = new LinkedList<>();
        currentDestination = null;
    }

    /**
     * Attempt movement along this object's set path and check if any movement was made.
     * Updates current node when arrived at a new node.
     * @return True if moved, false if no movement (there is no path left).
     */
    protected boolean move() {
        if (currentDestination == null) {
            // No need to move
            return false;
        }

        /*
        ===== Movemement logic =====
         */


        if (currentDestination.getX() < x) {
            x = x - speed;

            if (x < currentDestination.getX()) {
                x = currentDestination.getX();
            }
        }

        else if (currentDestination.getX() > x) {
            x = x + speed;

            if (x > currentDestination.getX()) {
                x = currentDestination.getX();
            }
        }

        if (currentDestination.getZ() < z) {
            z = z - speed;

            if (z < currentDestination.getZ()) {
                z = currentDestination.getZ();
            }
        }

        else if (currentDestination.getZ() > z) {
            z = z + speed;

            if (z > currentDestination.getZ()) {
                z = currentDestination.getZ();
            }
        }

        //TODO: rotationY = determineRotation(node, currentDestination);

        /*
        ===== Check if reached destination =====
         */

        if (x == currentDestination.getX() && z == currentDestination.getZ()) {
            this.node = currentDestination;

            if (path.isEmpty()) {
                // Reached end of path
                currentDestination = null;
                onFinishedPath();
            }

            else {
                currentDestination = path.poll();
            }
        }

        return true;
    }

    /**
     * Event that gets fired when the moving object has reached its destination.
     * Needs to be overriden and implemented by child classes.
     */
    protected abstract void onFinishedPath();

    /**
     * Set the path to move through
     * @param path A list of nodes
     */
    protected void setPath(Queue<Node> path) {
        this.path = path;
        this.currentDestination = path.poll();

        if (currentDestination == null) {
            onFinishedPath();
        }
    }

    /**
     * Determine the direction rotation between two points
     * @param currentNode The starting node
     * @param targetNode The target node
     * @return The direction in degrees
     */
    private static double determineRotation(Node currentNode, Node targetNode) {
        return Math.toDegrees(Math.atan2(targetNode.getZ() - currentNode.getZ(),
                currentNode.getX() - targetNode.getX()));
    }
}
