package com.nhlstenden.amazonsimulatie.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.nhlstenden.amazonsimulatie.models.NodeType.PATH;
import static java.util.stream.StreamSupport.stream;
import static org.junit.Assert.*;

public class WarehouseTest {


    @Test
    public void getRobotsTest() {

        World world = new World();
        Warehouse warehouse = world.getWarehouse();
        int amountrobots = 2, amountrobotstest = 0;

        List<Robot> robotstest = warehouse.getIdleRobots();
        for(Robot robot: robotstest) {
            amountrobotstest++;
        }
        Assert.assertEquals(amountrobots, amountrobotstest);
    }

    // niet af het is alleen een placeholder
    @Test
    public void notifyNewRobotIsIdleTest() {
        World world = new World();
        Warehouse warehouse = world.getWarehouse();
        //warehouse.notifyNewRobotIsIdle();
    }

    @Test
    public void getIdleRobotsTest() {
        World world = new World();
        Warehouse warehouse = world.getWarehouse();
        int amountrobots = 2, amountrobotstest = 0;

        List<Robot> robotstest = warehouse.getIdleRobots();
        for(Robot robot: robotstest) {
            amountrobotstest++;
        }
        Assert.assertEquals(amountrobots, amountrobotstest);
    }


    @Test
    public void getStellagesTest() {
        World world = new World();
        Warehouse warehouse = world.getWarehouse();
        int amountstellages = 7, amountstellagestest = 0;

        List<Stellage> stellagetest = warehouse.getStellages();
        for(Stellage stellage: stellagetest) {
            amountstellagestest++;
        }

        Assert.assertEquals(amountstellages, amountstellagestest);
    }

    @Test
    public void getAvailableStorageLocations() {
        World world = new World();
        Warehouse warehouse = world.getWarehouse();
        int[] idnode = {18,21,28}, idnodetest = {0,0,0};
        int counter = 0;

        counter = 0;
        List<Node> nodestest = warehouse.getAvailableStorageLocations();
        for(Node node: nodestest) {
            idnodetest[counter] = node.getID();
            counter++;
        }

        Assert.assertEquals(idnode[0], idnodetest[0]);
        Assert.assertEquals(idnode[1], idnodetest[1]);
        Assert.assertEquals(idnode[2], idnodetest[2]);
    }


    @Test
    public void getTruck() {
        World world = new World();
        Warehouse warehouse = world.getWarehouse();
        Node node = new Node(35, PATH, 15.0, 0.0, -6.0);

        Truck truck = new Truck(node, world, node.getX(), node.getY(), node.getZ());

        Truck trucktest = warehouse.getTruck();
        Assert.assertEquals(truck.getNode().getID(), trucktest.getNode().getID());
    }

    @Test
    public void addNewStellageTest()
    {
        World world = new World();
        SceneBuilder sceneBuilder = new AmazonSceneBuilder(world);
        int worldObjectsAmount = 0, worldObjectsAmountTest = 0;
        Node node = new Node(0, PATH, 5.0, 0.0, 5.0);

        Stellage stellage = new Stellage(StellageStatus.BEING_PROCESSED, node, world);

        Warehouse warehouse = world.getWarehouse();
        List<Object3D>  worldObjects = sceneBuilder.getObjects();

        for(Object3D objects : worldObjects) {
            worldObjectsAmount++;
        }

        warehouse.addNewStellage(stellage);

        List<Object3D>  worldObjectstest = world.getWorldObjectsAsProxyList();

        for(Object3D objects: worldObjectstest) {
            worldObjectsAmountTest++;
        }
        Assert.assertEquals(worldObjectsAmount + 1,worldObjectsAmountTest);
    }

    @Test
    public void replaceTruckTest(){
        World world = new World();
        Warehouse warehouse = world.getWarehouse();

        Truck truck = warehouse.getTruck();
        warehouse.replaceTruck();
        Truck trucktest = warehouse.getTruck();

        Assert.assertNotEquals(truck.getUUID(), trucktest.getUUID());
    }
}
