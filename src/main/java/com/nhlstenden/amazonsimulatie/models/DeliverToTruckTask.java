package com.nhlstenden.amazonsimulatie.models;

import java.util.Queue;

class DeliverToTruckTask extends RobotTask {

    DeliverToTruckTask(World world, Robot robot) {
        super(world, robot);
    }

    @Override
    public Queue<Node> getPath() {
        Node loadingDockNode = world.getGraph().getLoadingDockNode();

        return calculatePath(robot.getNode(), loadingDockNode);
    }

    @Override
    public void onFinishedPath() {
        robot.getStellage().setStatus(StellageStatus.IN_TRUCK);
        world.getWarehouse().getTruck().addStellage(robot.getStellage());
        robot.removeStellage();
        robot.executeTask(new IdleTask(world, robot));
    }
}
