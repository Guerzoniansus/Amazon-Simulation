package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.List;

class AmazonSceneBuilder implements SceneBuilder {

    boolean doneBuilding;

    List<Object3D> objects;
    Graph graph;

    public AmazonSceneBuilder() {
        doneBuilding = false;
        objects = new ArrayList<>();
    }

    @Override
    public void buildScene() {

        buildTruck();
        buildStellages();
        buildRobots();

        doneBuilding = true;
    }

    /**
     * Helper shorthand method to add an object to the list of objects.
     * So you can type addObject(object) instead of objects.add(object) everytime.
     * @param object The new object to add to the list of objects
     */
    private void addObject(Object3D object) {
        objects.add(object);
    }

    @Override
    public List<Object3D> getObjects() {

        if (doneBuilding == false) {
            throw new IllegalStateException("You FIRST need to call buildScene() before using this method");
        }

        return objects;
    }

    @Override
    public Graph getGraph() {
        if (doneBuilding == false) {
            throw new IllegalStateException("You FIRST need to call buildScene() before using this method");
        }



        // graph = new Graph();
        return graph;
    }

    /**
     * Build the truck
     */
    private void buildTruck() {

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
