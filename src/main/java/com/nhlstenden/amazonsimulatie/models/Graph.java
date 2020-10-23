package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Graph {

    private final Map<Integer, Node> nodes;

    private final int[][] connectionMatrix;

    public Graph(Map<Integer, Node> nodes, int[][] connectionMatrix) {
        this.nodes = nodes;
        this.connectionMatrix = connectionMatrix;
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
     * Get a list of all the nodes in this graph
     * @return A List with all nodes
     */
    public List<Node> getNodes() {
        return new ArrayList(nodes.values());
    }


}
