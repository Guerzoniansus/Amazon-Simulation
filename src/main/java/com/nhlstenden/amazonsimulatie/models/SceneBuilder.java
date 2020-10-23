package com.nhlstenden.amazonsimulatie.models;

import java.util.List;

interface SceneBuilder {

    /**
     * Builds the scene. This method needs to be called FIRST, before retrieving the scene.
     */
    void buildScene();

    /**
     * Get a list of 3D objects that belong to the newly created scene
     * @return
     */
    List<Object3D> getObjects();

    /**
     * Returns a graph with nodes that belong to the newly created scene
     * @return
     */
    Graph getGraph();
}
