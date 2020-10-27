package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.*;

import java.util.LinkedList;
import java.util.Queue;

public class StoreInWarehouseTask extends RobotTask implements RobotListener {

    public StoreInWarehouseTask(World world, Robot robot) {
        super(world, robot);
        robot.addRobotListener(this);

        Node storagelocation = world.getWarehouse().getAvailableStorageLocations().get(0);
        robot.getStellage().setStorageLocation(storagelocation);
    }

    @Override
    public Queue<Node> getPath() {
        Node storageNode = robot.getStellage().getStorageLocation();

        return calculatePath(robot.getNode(), storageNode);
    }

    @Override
    public void onFinishedPath() {
        robot.removeRobotListener(this);

        robot.getStellage().setStatus(StellageStatus.IN_WAREHOUSE);
        robot.getStellage().removeParent();
        robot.removeStellage();

        robot.executeTask(new IdleTask(world, robot));

    }

}
