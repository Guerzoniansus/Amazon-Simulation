package com.nhlstenden.amazonsimulatie.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AmazonSceneBuilderTest {

    @Test
    public void getGraphTest() {
        World world = new World();
        SceneBuilder sceneBuilder = new AmazonSceneBuilder(world);
        int nodesamount = 36, nodesamounttest = 0;
        Graph graph = sceneBuilder.getGraph();
        List<Node> nodes = graph.getNodes();

        for(Node node: nodes)
        {
            nodesamounttest++;
        }
        Assert.assertEquals(nodesamount, nodesamounttest);
    }

    @Test
    public void getObjectsTest() {
        int amountrobots = 2, amountstellage = 7, amounttruck = 1;
        int amountrobotstest = 0, amountstellagetest = 0, amounttrucktest = 0;

        World world = new World();
        SceneBuilder sceneBuilder = new AmazonSceneBuilder(world);

        List<Object3D> objects = sceneBuilder.getObjects();

        for(Object object: objects) {
            String string = object.toString();
            if(string.contains("Stellage"))
            {
                amountstellagetest++;
            }
            if (string.contains("Robot"))
            {
                amountrobotstest++;
            }
            if(string.contains("Truck"))
            {
                amounttrucktest++;
            }
        }
        Assert.assertEquals(amountstellage, amountstellagetest);
        Assert.assertEquals(amountrobots, amountrobotstest);
        Assert.assertEquals(amounttruck, amounttrucktest);
    }
}