package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.List;

class AmazonSceneBuilder implements SceneBuilder {

    private List<Object3D> objects;
    private final World world;


    public AmazonSceneBuilder(World world) {
        objects = new ArrayList<>();
        this.world = world;
    }

    @Override
    public Graph getGraph() {
        GraphCreator graphCreator = new AmazonGraphCreator();
        Graph graph = graphCreator.getGraph();

        return graph;
    }

    /**
     * Helper shorthand method to add an object to the list of objects.
     * So you can type createObject(object) instead of objects.add(object) everytime.
     * @param object The new object to add to the list of objects
     */
    private void addObject(Object3D object) {
        objects.add(object);
    }

    @Override
    public List<Object3D> getObjects() {

        if (world.getGraph() == null) {
            throw new IllegalStateException("The given world must FIRST contain a graph before calling this method!");
        }

        buildTruck();
        buildStellages();
        buildRobots();

        return objects;
    }

    /**
     * Build the truck
     */
    private void buildTruck() {
        Truck truck = new Truck(world);
        objects.add(truck);
    }

    /**
     * Build the robots
     */
    private void buildRobots() {

    }

    /**
     * Build the stellages
     */
    private void buildStellages() {

    }
}
