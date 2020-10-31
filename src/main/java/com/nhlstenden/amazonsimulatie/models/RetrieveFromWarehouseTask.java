package com.nhlstenden.amazonsimulatie.models;

import java.util.Queue;

class RetrieveFromWarehouseTask extends RobotTask {

    RetrieveFromWarehouseTask(World world, Robot robot) {
        super(world, robot);

        Stellage stellage = world.getWarehouse().getStellages()
                                                .stream()
                                                .filter(x -> x.getStatus() == StellageStatus.IN_WAREHOUSE)
                                                .findFirst()
                                                .get();

        stellage.setStatus(StellageStatus.BEING_PROCESSED);

        robot.setStellage(stellage);
    }

    @Override
    public Queue<Node> getPath() {
        Node stellageNode = robot.getStellage().getNode();

        return calculatePath(robot.getNode(), stellageNode);
    }

    @Override
    public void onFinishedPath() {
        robot.getStellage().removeStoragelocation();
        robot.getStellage().setParent(robot);
        robot.executeTask(new DeliverToTruckTask(world, robot));
    }
}
