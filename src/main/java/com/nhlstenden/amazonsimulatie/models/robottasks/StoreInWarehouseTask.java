package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.Node;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.RobotListener;
import com.nhlstenden.amazonsimulatie.models.World;

import java.util.LinkedList;
import java.util.Queue;

public class StoreInWarehouseTask extends RobotTask implements RobotListener {

    public StoreInWarehouseTask(World world, Robot robot) {
        super(world, robot);
    }

    @Override
    public Queue<Node> getPath() {
        LinkedList<Node> path = new LinkedList<>();

        // stellage.setStorageLocation() stuff
        // stellage.setParent() stuff
        // stellage.setStatus(BEING_PROCESSED) stuff

        return path;
    }

    @Override
    public void onFinishedPath() {
        robot.removeRobotListener(this);
        // stellage.setStatus(IN_WAREHOUSE) stuff
        // stellage.removeParent() stuff
        // robot.setTask(new IdleTask()) stuff
    }

}
