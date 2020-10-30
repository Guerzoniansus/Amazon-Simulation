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
    private final Node ARRIVING_NODE;

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

        this.speed = 0.2;
        this.rotationY = 180;

        gottenStellages = new ArrayList<>();
        status = Status.ARRIVING;
        ARRIVING_NODE = world.getGraph().getLoadingDockNode();

        setPath(getStartingPath());
    }

    @Override
    public boolean update() {

        if (status == Status.PARKED) {
            if (rotationY == 180) {
                rotationY = 0;
                return true;
            }
        }

        return move();
    }

    @Override
    protected void onFinishedPath() {
        if (status == Status.ARRIVING) {
            //rotationY += 180; // Turn around so the back faces the warehouse

            // Get all idle robots to go to truck
            world.getWarehouse().getIdleRobots().forEach(robot -> scheduleTruckToWarehouseOrder(robot));
            status = Status.PARKED;
        }

        else if (status == Status.LEAVING) {
            world.getWarehouse().replaceTruck();
        }
    }

    /**
     * Deliver a stellage to this truck
     * @param stellage The stellage to add
     */
    public void addStellage(Stellage stellage) {
        gottenStellages.add(stellage);
        stellage.die();

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
        if (status == Status.PARKED) {
            if (stellagesToDeliver > 0) {
                scheduleTruckToWarehouseOrder(robot);
            }

            else if (stellagesToGet > 0) {
                scheduleWarehouseToTruckOrder(robot);
            }
        }
    }

    /**
     * Appoint new robot(s) to retrieve remaining stellages from Truck
     */
    private void scheduleTruckToWarehouseOrder(Robot robot) {
        stellagesToDeliver--;
        robot.executeTask(new RetrieveFromTruckTask(world, robot));
    }

    /**
     * Appoint new robot(s) to retrieve stellage from the Warehouse and deliver them to the truck
     */
    private void scheduleWarehouseToTruckOrder(Robot robot) {
        stellagesToGet--;
        robot.executeTask(new RetrieveFromWarehouseTask(world, robot));
    }


    /**
     * Get the path the truck takes to arrive at the warehouse
     * @return The arrival path
     */
    private Queue<Node> getStartingPath() {
        Queue<Node> startingPath = new LinkedList<>();
        startingPath.add(ARRIVING_NODE);
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
