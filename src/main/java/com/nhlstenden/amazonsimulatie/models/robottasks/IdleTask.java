package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.Node;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.RobotListener;
import com.nhlstenden.amazonsimulatie.models.World;

import java.util.LinkedList;
import java.util.Queue;

public class IdleTask extends RobotTask implements RobotListener {

    public IdleTask(World world, Robot robot) {
        super(world, robot);
        robot.addRobotListener(this);
    }

    @Override
    public Queue<Node> getPath() {
        LinkedList<Node> path = new LinkedList<>();

        return path;
    }

    @Override
    public void onFinishedPath() {
        robot.removeRobotListener(this);

        if (world.getWarehouse() != null) {
            world.getWarehouse().notifyNewRobotIsIdle(robot);
        }
    }
}
