package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.Node;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.RobotListener;
import com.nhlstenden.amazonsimulatie.models.World;

import java.util.LinkedList;
import java.util.Queue;

public class RetrieveFromWarehouseTask extends RobotTask implements RobotListener {

    public RetrieveFromWarehouseTask(World world, Robot robot) {
        super(world, robot);

        // get available stellage stuff
        // stellage.setStatus(being_procssed) stuff
        // robot.setStellage() stuff

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
        // stellage.setParent() stuff
        // robot.setTask() stuff
    }
}
