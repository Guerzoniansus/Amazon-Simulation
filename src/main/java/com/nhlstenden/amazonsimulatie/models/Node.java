package com.nhlstenden.amazonsimulatie.models;

import java.util.HashMap;
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
    private final Map<Node, Double> distanceToValues;

    public Node(int ID, NodeType type, HashMap<Node, Double> distanceToValues,
                double x, double y, double z) {
        this.ID = ID;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.distanceToValues = distanceToValues;
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
    public double getDistanceTo(Node node) {
        return distanceToValues.get(node);
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
