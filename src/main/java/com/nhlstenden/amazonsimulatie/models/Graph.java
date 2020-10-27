package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Graph {

    private final Map<Integer, Node> nodes;

    private final int[][] distanceMatrix;

    public Graph(Map<Integer, Node> nodes, int[][] distanceMatrix) {
        this.nodes = Collections.unmodifiableMap(nodes);
        this.distanceMatrix = distanceMatrix;
    }

    /**
     * Get a node through the number ID
     * @param ID The ID of the node
     * @return The Node, or null if no node with this ID was found
     */
    public Node getNode(int ID) {
        if (nodes.containsKey(ID)) {
            return nodes.get(ID);
        }

        else return null;
    }

    /**
     * Get an immutable list of all the nodes in this graph
     * @return A List with all nodes
     */
    public List<Node> getNodes() {
        return Collections.unmodifiableList(new ArrayList(nodes.values()));
    }

    /**
     * Get the distance matrix of this graph
     * @return A neighbour distance matrix
     */
    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }


}
