package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.*;

import java.util.Queue;

public class DeliverToTruckTask extends RobotTask {

    public DeliverToTruckTask(World world, Robot robot) {
        super(world, robot);
    }

    @Override
    public Queue<Node> getPath() {
        Node loadingDockNode = world.getGraph().getLoadingDockNode();

        return calculatePath(robot.getNode(), loadingDockNode);
    }

    @Override
    public void onFinishedPath() {
        //robot.removeRobotListener(this);
        robot.getStellage().setStatus(StellageStatus.IN_TRUCK);
        world.getWarehouse().getTruck().addStellage(robot.getStellage());
        robot.removeStellage();
        robot.executeTask(new IdleTask(world, robot));
    }
}
