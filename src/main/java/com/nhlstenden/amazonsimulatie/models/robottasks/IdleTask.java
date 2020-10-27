package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.Node;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.World;

import java.util.LinkedList;
import java.util.Queue;

public class IdleTask extends RobotTask {

    public IdleTask(World world, Robot robot) {
        super(world, robot);

        world.getWarehouse().notifyNewRobotIsIdle(robot);
    }

    @Override
    public Queue<Node> getPath() {
        LinkedList<Node> path = new LinkedList<>();

        return path;
    }

}
