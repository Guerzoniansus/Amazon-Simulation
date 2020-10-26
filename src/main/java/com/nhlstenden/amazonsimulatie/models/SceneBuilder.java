package com.nhlstenden.amazonsimulatie.models;

import java.util.List;

interface SceneBuilder {


    /**
     * Get a list of 3D objects that belong to the newly created scene
     * @return
     */
    List<Object3D> getObjects();

    /**
     * Returns a graph with nodes that belong to the newly created scene.
     * This need to be run FIRST before getting the objects, as some objects might rely on the world
     * already having a graph on instantiation.
     * @return
     */
    Graph getGraph();
}
