package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class Graph {

    private final Map<Integer, Node> nodes;

    /**
     * A matrix with distances between 2 neighbours.
     * A distnace of 0 means the 2 nodes are NOT neighbours.
     */
    private final int[][] distanceMatrix;

    private final Node loadingDock;

    public Graph(Map<Integer, Node> nodes, int[][] distanceMatrix) {
        this.nodes = Collections.unmodifiableMap(nodes);
        this.distanceMatrix = distanceMatrix;

        this.loadingDock = nodes.values().stream()
                                         .filter(x -> x.getType() == NodeType.LOADING_DOCK)
                                         .findFirst()
                                         .get();
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

        else throw new NullPointerException("This node does not exist");
    }

    /**
     * Get an immutable list of all the nodes in this graph
     * @return A List with all nodes
     */
    public List<Node> getNodes() {
        return Collections.unmodifiableList(new ArrayList(nodes.values()));
    }

    /**
     * Get the distance matrix of this graph.
     * The int at distanceMatrix[i][j] is the distance between Node i and Node j.
     * A distance of 0 means the two nodes are not connected.
     * @return A neighbour distance matrix
     */
    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    /**
     * Get the node where the loading dock is
     * @return The loading dock Node
     */
    public Node getLoadingDockNode() {
        return loadingDock;
    }


}
