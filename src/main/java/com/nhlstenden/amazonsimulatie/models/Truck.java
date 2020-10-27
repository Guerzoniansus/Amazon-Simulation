package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.robottasks.RetrieveFromTruckTask;
import com.nhlstenden.amazonsimulatie.models.robottasks.RetrieveFromWarehouseTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * Deze class stelt een truck voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een truck geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
public class Truck extends MovingObject3D implements Updatable {

    private static final int STARTING_NODE_ID = 35;
    private static final int ARRIVING_NODE_ID = 34;

    private enum Status {ARRIVING, PARKED, LEAVING}
    private Status status;

    private List<Stellage> gottenStellages;

    private int stellagesToDeliver = 3;
    private int stellagesToGet = 3;

    Truck(World world) {
        this(world.getGraph().getNode(STARTING_NODE_ID), world, 0, 0, 0);
    }

    Truck(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        gottenStellages = new ArrayList<>();
        status = Status.ARRIVING;

        setPath(getStartingPath());
    }

    @Override
    public boolean update() {
        return move();
    }

    @Override
    protected void onFinishedPath() {
        if (status == Status.ARRIVING) {

            scheduleTruckToWarehouseOrders();

            status = Status.PARKED;
        }

        else if (status == Status.LEAVING) {
            // world.replaceTruck()
            gottenStellages.forEach(obj -> obj.die());
        }
    }

    /**
     * Deliver a stellage to this truck
     * @param stellage The stellage to add
     */
    public void addStellage(Stellage stellage) {
        gottenStellages.add(stellage);

        if (stellagesToGet == 0) {
            status = Status.LEAVING;
            setPath(getLeavingPath());
        }
    }

    /**
     * Retrieve a stellage from this truck
     * @return The retrieved stellage
     */
    public Stellage takeStellage() {
        Stellage stellage = new Stellage(StellageStatus.BEING_PROCESSED, node, world);
        return stellage;
    }

    /**
     * Notify this truck that a new Robot has become available for tasks
     * @param robot The robot that is now idle and available
     */
    public void notifyNewRobotAvailable(Robot robot) {
        if (stellagesToDeliver > 0) {
            scheduleTruckToWarehouseOrders();
        }

        else if (stellagesToGet > 0) {
            scheduleWarehouseToTruckOrders();
        }
    }

    /**
     * Appoint new robot(s) to retrieve remaining stellages from Truck
     */
    private void scheduleTruckToWarehouseOrders() {
        world.getWarehouse().getIdleRobots().forEach(robot -> {
            if (stellagesToDeliver > 0) {
                stellagesToDeliver--;
                robot.executeTask(new RetrieveFromTruckTask(world, robot));
            }
        });
    }

    /**
     * Appoint new robot(s) to retrieve stellage from the Warehouse and deliver them to the truck
     */
    private void scheduleWarehouseToTruckOrders() {
        world.getWarehouse().getIdleRobots().forEach(robot -> {
            if (stellagesToGet > 0) {
                stellagesToGet--;
                robot.executeTask(new RetrieveFromWarehouseTask(world, robot));
            }
        });
    }


    /**
     * Get the path the truck takes to arrive at the warehouse
     * @return The arrival path
     */
    private Queue<Node> getStartingPath() {
        Queue<Node> startingPath = new LinkedList<>();
        startingPath.add(world.getGraph().getNode(ARRIVING_NODE_ID));
        return startingPath;
    }

    /**
     * Get the path the truck takes when it leaves the warehouse
     * @return The exit path
     */
    private Queue<Node> getLeavingPath() {
        Queue<Node> leavingPath = new LinkedList<>();
        leavingPath.add(world.getGraph().getNode(STARTING_NODE_ID));
        return leavingPath;
    }



}
