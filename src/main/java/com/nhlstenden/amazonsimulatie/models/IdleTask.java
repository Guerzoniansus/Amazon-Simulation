package com.nhlstenden.amazonsimulatie.models;

import java.util.LinkedList;
import java.util.Queue;

class IdleTask extends RobotTask {

    IdleTask(World world, Robot robot) {
        super(world, robot);
    }

    @Override
    public Queue<Node> getPath() {
        return new LinkedList<>();
    }

    @Override
    public void onFinishedPath() {
        if (world.getWarehouse() != null) {
            world.getWarehouse().notifyNewRobotIsIdle(robot);
        }
    }
}
