package com.nhlstenden.amazonsimulatie.models;

import java.util.Queue;

class RetrieveFromTruckTask extends RobotTask {

    RetrieveFromTruckTask(World world, Robot robot) {
        super(world, robot);
    }

    @Override
    public Queue<Node> getPath() {
        Node loadingDockNode = world.getGraph().getLoadingDockNode();

        return calculatePath(robot.getNode(), loadingDockNode);
    }

    @Override
    public void onFinishedPath() {
        Truck truck = world.getWarehouse().getTruck();

        Stellage stellage = world.getWarehouse().getTruck().takeStellage();
        world.getWarehouse().addNewStellage(stellage);

        robot.setStellage(stellage);
        stellage.setParent(robot);
        robot.executeTask(new StoreInWarehouseTask(world, robot));
    }

}
