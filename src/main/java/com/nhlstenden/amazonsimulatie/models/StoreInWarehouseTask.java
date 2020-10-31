package com.nhlstenden.amazonsimulatie.models;

import java.util.Queue;

class StoreInWarehouseTask extends RobotTask {

    StoreInWarehouseTask(World world, Robot robot) {
        super(world, robot);

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
        robot.getStellage().setStatus(StellageStatus.IN_WAREHOUSE);
        robot.getStellage().removeParent();
        robot.removeStellage();

        robot.executeTask(new IdleTask(world, robot));

    }

}
