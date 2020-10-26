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

    public Node(int ID, NodeType type, double x, double y, double z) {
        this.ID = ID;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
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
