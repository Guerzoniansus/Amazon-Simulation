package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {

    private final int ID;

    private final NodeType type;

    private final double x;
    private final double y;
    private final double z;

    /**
     * A Map that stores the distance from this node to a specific target node
     */
    private final Map<Integer, Double> neighboursAndDistances;

    public Node(int ID, NodeType type, Map<Integer, Double> neighboursAndDistancces,
                double x, double y, double z) {
        this.ID = ID;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.neighboursAndDistances = neighboursAndDistancces;
    }

    /**
     * Gets this node's ID
     * @return The ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets this node's NodeType
     * @return The NodeType
     */
    public NodeType getType() {
        return type;
    }

    /**
     * Returns the distance from this node to the argument node
     * @param node The Node of which to get the distance to
     * @return The distance to the node
     */
    public double getDistanceToNeighbour(Node node) {
        return neighboursAndDistances.get(node.getID());
    }

    /**
     * Get a list of all neighbour IDs of this node
     * @return A list with all the IDs of neighbours
     */
    public List<Integer> getNeighbours() {
        return new ArrayList(neighboursAndDistances.keySet());
    }


    /**
     * Gets the X position
     * @return X
     */
    public double getX() {
        return x;
    }

    /**
     * Retursn the Y position
     * @return Y
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the Z position
     * @return Z
     */
    public double getZ() {
        return z;
    }

}
