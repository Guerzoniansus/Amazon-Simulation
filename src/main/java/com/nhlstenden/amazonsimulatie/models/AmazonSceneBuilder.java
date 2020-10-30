package com.nhlstenden.amazonsimulatie.models;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class AmazonSceneBuilder implements SceneBuilder {

    private final World world;

    private final String TEXT_FILE = "src/main/resources/objects.txt";


    public AmazonSceneBuilder(World world) {
        this.world = world;
    }

    @Override
    public Graph getGraph() {
        GraphCreator graphCreator = new AmazonGraphCreator();
        Graph graph = graphCreator.getGraph();

        return graph;
    }

    @Override
    public List<Object3D> getObjects() {

        List<Object3D> objects = new ArrayList<>();

        if (world.getGraph() == null) {
            throw new IllegalStateException("The given world must FIRST contain a graph before calling this method!");
        }

        try {
            File file = new File(TEXT_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = "";
            int lineCounter = 0;

            while ((line = reader.readLine()) != null) {
                lineCounter++;

                line = line.replaceAll(" ", "");

                // Check for comment
                if (line.startsWith("/")) {
                    continue;
                }

                Object3D newObject = parseObject(line);

                if (newObject == null) {
                    throw new IllegalArgumentException("Could not parse line " + lineCounter + " of " + TEXT_FILE);
                }

                objects.add(newObject);

            }

            reader.close();
        }

        catch (IOException e) {
            IntStream.rangeClosed(0, 25)
                    .forEach(x -> System.out.println("ERROR: FILE NOT FOUND!"));
            System.exit(1);
        }

        return objects;
    }

    /**
     * Analyzes a line of text and returns a new Object3D created from the arguments
     * @param text The line of text to analyze
     * @return The newly created Object3D, or null if an object could not be determined
     */
    private Object3D parseObject(String text) {
        // Format:
        // Object: NodeID
        // Robot: 5
        // Truck:

        text = text.replaceAll(" ", "");

        String[] textSplit = text.split(":");
        String objectName = textSplit[0];

        if (objectName.equalsIgnoreCase("truck")) {
            return new Truck(world);
        }

        else {

            int nodeID = Integer.parseInt(textSplit[1]);
            Node node = world.getGraph().getNode(nodeID);


            switch (objectName.toLowerCase()) {
                case "robot":
                    return new Robot(node, world);
                case "stellage":
                    return new Stellage(StellageStatus.IN_WAREHOUSE, node, node, world);
            }

        }

        return null;
    }

}
