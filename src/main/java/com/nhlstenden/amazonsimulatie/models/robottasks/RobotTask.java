package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.Node;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.World;

import java.util.Queue;

public abstract class RobotTask {

    protected World world;
    protected Robot robot;

    public RobotTask(World world, Robot robot) {
        this.world = world;
        this.robot = robot;
    }

    /**
     * Returns the path the robot needs to follow to get to their task's destination
     * @return The path
     */
    public abstract Queue<Node> getPath();

}
