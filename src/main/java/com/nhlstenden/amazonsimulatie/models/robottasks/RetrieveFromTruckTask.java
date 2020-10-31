package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.*;

import java.util.Queue;

public class RetrieveFromTruckTask extends RobotTask {

    public RetrieveFromTruckTask(World world, Robot robot) {
        super(world, robot);
        //robot.setRobotListener(this);

    }

    @Override
    public Queue<Node> getPath() {
        Node loadingDockNode = world.getWarehouse().getTruck().getNode();

        return calculatePath(robot.getNode(), loadingDockNode);
    }

    @Override
    public void onFinishedPath() {
        //robot.removeRobotListener(this);

        Truck truck = world.getWarehouse().getTruck();

        Stellage stellage = world.getWarehouse().getTruck().takeStellage();
        world.getWarehouse().addNewStellage(stellage);

        robot.setStellage(stellage);
        stellage.setParent(robot);
        robot.executeTask(new StoreInWarehouseTask(world, robot));
    }

}
