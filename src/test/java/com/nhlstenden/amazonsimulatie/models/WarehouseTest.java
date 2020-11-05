package com.nhlstenden.amazonsimulatie.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.nhlstenden.amazonsimulatie.models.NodeType.PATH;
import static java.util.stream.StreamSupport.stream;
import static org.junit.Assert.*;

public class WarehouseTest {


    @Test
    public void getRobotsTest() {

        World world = new World();
        World.WorldEditor worldEditor = world.getWorldEditor();

        Warehouse warehouse = new Warehouse(world, worldEditor);

        List<Robot> robots = worldEditor.getWorldObjects()
                .stream()
                .filter(obj -> obj instanceof Robot)
                .map(obj -> (Robot) obj)
                .collect(Collectors.toList());

        List<Robot> robotstest = warehouse.getRobots();

        Assert.assertEquals(robots, robotstest);
    }

    @Test
    public void notifyNewRobotIsIdleTest() {
        World world = new World();
        World.WorldEditor worldEditor = world.getWorldEditor();

        Warehouse warehouse = new Warehouse(world, worldEditor);
        //warehouse.notifyNewRobotIsIdle();
    }

    @Test
    public void getIdleRobotsTest() {
        World world = new World();
        World.WorldEditor worldEditor = world.getWorldEditor();

        Warehouse warehouse = new Warehouse(world, worldEditor);

        List<Robot> robots = worldEditor.getWorldObjects()
                .stream()
                .filter(obj -> obj instanceof Robot)
                .map(obj -> (Robot) obj)
                .collect(Collectors.toList());
        robots.stream()
                .filter(robot -> robot.getTaskName().equals(IdleTask.class.getSimpleName()))
                .collect(Collectors.toList());

        List<Robot> robotstest = warehouse.getIdleRobots();
        Assert.assertEquals(robots, robotstest);
    }


    @Test
    public void getStellagesTest() {
        World world = new World();
        World.WorldEditor worldEditor = world.getWorldEditor();

        Warehouse warehouse = new Warehouse(world, worldEditor);

        List<Stellage> stellages = worldEditor.getWorldObjects().stream()
                .filter(object -> object instanceof Stellage)
                .map(object -> (Stellage) object)
                .collect(Collectors.toList());

        List<Stellage> stellagetest = warehouse.getStellages();

        Assert.assertEquals(stellages, stellagetest);
    }

    @Test
    public void getAvailableStorageLocations() {
        World world = new World();
        World.WorldEditor worldEditor = world.getWorldEditor();

        Warehouse warehouse = new Warehouse(world, worldEditor);

        List<Node> nodes = world.getGraph().getNodes().stream().filter(node -> {
            if (node.getType() != NodeType.STELLAGE) {
                return false;
            }

            // Check if there is already a stellage stored on this node
            for (Stellage stellage : warehouse.getStellages()) {
                if (stellage.hasStorageLocation()) {
                    if (stellage.getStorageLocation() == node) {
                        return false;
                    }
                }
            }

            return true;
        }).collect(Collectors.toList());

        List<Node> nodestest = warehouse.getAvailableStorageLocations();

        Assert.assertEquals(nodes, nodestest);
    }


    @Test
    public void getTruck() {
        World world = new World();
        World.WorldEditor worldEditor = world.getWorldEditor();

        Warehouse warehouse = new Warehouse(world, worldEditor);

        Truck truck = (Truck) worldEditor.getWorldObjects()
                .stream()
                .filter(object -> object instanceof Truck)
                .findFirst()
                .get();

        Truck trucktest = warehouse.getTruck();
        Assert.assertEquals(truck, trucktest);
    }

    @Test
    public void addNewStellageTest()
    {
        World world = new World();
        World.WorldEditor worldEditor = world.getWorldEditor();
        SceneBuilder sceneBuilder = new AmazonSceneBuilder(world);
        int worldObjectsAmount = 0, worldObjectsAmountTest = 0;
        Node node = new Node(0, PATH, 5.0, 0.0, 5.0);

        Stellage stellage = new Stellage(StellageStatus.BEING_PROCESSED, node, world);

        Warehouse warehouse = new Warehouse(world, worldEditor);
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
}
