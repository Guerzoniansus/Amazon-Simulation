package com.nhlstenden.amazonsimulatie.outdated;

import com.nhlstenden.amazonsimulatie.models.Node;
import com.nhlstenden.amazonsimulatie.models.NodeType;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class GraphCreatorOld {

    /*

    private static final double NODE_Y = 0;

    private final String textFileName;

    public GraphCreatorOld(String textFileName) {
        this.textFileName = textFileName;
    }

    *//**
     * Creates and returns a new Graph
     * @return A new Graph
     *//*
    public Graph getGraph() {
        return createGraph();
    }

    *//**
     * Create a graph filled with nodes
     * @return A graph
     *//*
    private Graph createGraph() {

        try {
            File file = new File(textFileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            final int[][] connectionMatrix;
            final Map<Integer, Node> nodes = new HashMap<>();

            String line = "";
            int lineCounter = 1;
            int nodeCounter = 0;

            while ((line = reader.readLine()) != null) {

                line = line.replaceAll(" ", "");

                // Check for comment
                if (line.startsWith("/")) {
                    continue;
                }

                if (isValidString(line) == false) {
                    throw new IllegalArgumentException("Invalid format at line " + lineCounter + " of " + textFileName);
                }

                Node newNode = parseNode(line);
                nodes.put(newNode.getID(), newNode);
                nodeCounter++;
                lineCounter++;
            }

            reader.close();

            connectionMatrix = createConnectionMatrix(nodes);
            Graph graph = new Graph(nodes, connectionMatrix);
            return graph;

        }

        catch (IOException e) {
            IntStream.rangeClosed(0, 25)
                    .forEach(x -> System.out.println("ERROR: FILE NOT FOUND!"));
            System.exit(1);
        }

        return null;
    }

    *//**
     * Create a new node by parsing its parameters from a string of text and updates the connectionMatrix
     * @param text The text to parse
     * @return The newly created Node
     *//*
    private Node parseNode(String text) {
        // Format:
        // ID: TYPE, X, Z - NeighbourID=Distance (NeighbourID2=Distance... etc)

        String[] idSplit = text.split(":");

        // ======== ID
        int ID = Integer.parseInt(idSplit[0]);

        // Spit "STELLAGE, 4, 2 - 5=1, 7=1" into half
        String[] restSplit = idSplit[1].split("-");
        String argSplit = restSplit[0];
        String neighbourArgsSplit = restSplit[1];

        // Regular args
        String[] args = argSplit.split(",");

        // ======== NodeType, X, Y
        NodeType nodeType = NodeType.valueOf(args[0].toUpperCase());
        double x = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);

        // ======== Neighbours
        Map<Integer, Double> neighboursAndDistances = new HashMap<>();
        String[] neighbourArgs = neighbourArgsSplit.split(",");

        List<Integer> neighbours = new ArrayList<Integer>();

        for (String neighbourArg : neighbourArgs) {
            int neighbourID = Integer.parseInt(neighbourArg.split("=")[0]);
            double neighbourDistance = Double.parseDouble(neighbourArg.split("=")[1]);
            neighboursAndDistances.put(neighbourID, neighbourDistance);

        }

        Node node = new Node(ID, nodeType, neighboursAndDistances, x, NODE_Y, z);

        return node;
    }

    *//**
     * Creates a connection matrix: for each Node, a 1 will be set for its neighbours, and a 0 for the rest.
     * @param nodes A collection of nodes
     * @return A connection matrix / table with 0s and 1s
     *//*
    private int[][] createConnectionMatrix(Map<Integer, Node> nodes) {

        int[][] connectionMatrix = new int[nodes.size()][nodes.size()];

        for (Node node : nodes.values()) {
            for (int neighbourID : node.getNeighbours()) {
                Node neighbour = nodes.get(neighbourID);
                connectionMatrix[node.getID()][neighbour.getID()] = 1;
            }
        }

        return connectionMatrix;
    }

    *//**
     * Check if the given string is valid according to the assumed format
     * @param text The string to check
     * @return Whether the format of this string is valid or not
     *//*
    private boolean isValidString(String text) {
        // Format:
        // ID: TYPE, X, Z - NeighbourID=Distance (NeighbourID2=Distance... etc)

        if (StringUtils.countOccurrencesOf(text, ":") != 1) {
            return false;
        }

        if (StringUtils.countOccurrencesOf(text, "-") != 1) {
            return false;
        }

        if (StringUtils.countOccurrencesOf(text.split("-")[0], ",") != 2) {
            return false;
        }

        if (StringUtils.countOccurrencesOf(text.split("-")[1], "=") < 1) {
            return false;
        }

        try {
            Integer.parseInt(text.split(":")[0]);
        }

        catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
*/

}
