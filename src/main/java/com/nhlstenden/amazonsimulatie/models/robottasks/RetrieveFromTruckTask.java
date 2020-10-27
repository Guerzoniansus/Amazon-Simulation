package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.*;
import com.nhlstenden.amazonsimulatie.utilities.ShortestPathCalculator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class RetrieveFromTruckTask extends RobotTask implements RobotListener {

    public RetrieveFromTruckTask(World world, Robot robot) {
        super(world, robot);
        robot.addRobotListener(this);

    }

    @Override
    public Queue<Node> getPath() {
        Node truckNode = world.getWarehouse().getTruck().getNode();

        return calculatePath(robot.getNode(), truckNode);
    }

    @Override
    public void onFinishedPath() {
        robot.removeRobotListener(this);

        Truck truck = world.getWarehouse().getTruck();
        Stellage stellage = world.getWarehouse().getTruck().takeStellage();

        robot.setStellage(stellage);
        stellage.setParent(robot);
        robot.executeTask(new StoreInWarehouseTask(world, robot));
    }

}
