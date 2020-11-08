package com.nhlstenden.amazonsimulatie.models;

import org.junit.Assert;
import org.junit.Test;

import com.nhlstenden.amazonsimulatie.controllers.Controller;
import com.nhlstenden.amazonsimulatie.controllers.SimulationController;
import com.nhlstenden.amazonsimulatie.models.World;
import com.nhlstenden.amazonsimulatie.views.DefaultWebSocketView;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.beans.PropertyChangeListener;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class WorldTest {


    // this test does nothing, but i decided to leave it in
    @Test
    public void updateTest() throws Exception {
        World world = new World();
        world.update();
    }
    // couldn't make this work
    /*@Test
      public void addObserver() {
        World world = new World();
        PropertyChangeListener pcl;

        world.addObserver(pcl);
    }*/

    @Test
    public void getWorldObjectsAsProxyListTest() {
        World world = new World();
        SceneBuilder sceneBuilder = new AmazonSceneBuilder(world);
        int worldObjectsamount = 0, testWorldObjectsamount = 0;

        List<Object3D>  worldObjects = sceneBuilder.getObjects();
        List<Object3D> testWorldObjects = world.getWorldObjectsAsProxyList();

        for(Object3D object : worldObjects) {
            worldObjectsamount++;
        }

        for(Object3D object : testWorldObjects) {
            testWorldObjectsamount++;
        }

        Assert.assertEquals(worldObjectsamount, testWorldObjectsamount);
    }

    @Test
    public void getGraphTest() {
        World world = new World();
        GraphCreator graphCreator = new AmazonGraphCreator();

        Graph graph = graphCreator.getGraph();
        Graph graphtest = world.getGraph();

        Assert.assertEquals(graphtest, graph);

    }

    // Test is nog niet klaar en heeft alleen een placeholder
    @Test
    public void getWarehouseTest() {
        World world = new World();

        Warehouse warehousetest = world.getWarehouse();
        //String string = "com.nhlstenden.amazonsimulatie.models.Warehouse@61dc03ce";
        String string = warehousetest.toString();
        Assert.assertEquals(warehousetest, warehousetest);
    }
}