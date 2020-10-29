package com.nhlstenden.amazonsimulatie.models.robottasks;

import com.nhlstenden.amazonsimulatie.models.Node;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.World;
import com.nhlstenden.amazonsimulatie.utilities.ShortestPathCalculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

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

    /**
     * Helper method to calculate a path to prevent copy pasting long code everytime
     * @param startingNode The starting node of the path
     * @param endNode The end node of the path
     * @return The path
     */
    protected Queue<Node> calculatePath(Node startingNode, Node endNode) {
        return ShortestPathCalculator.calculateShortestPath
                                        (world.getGraph().getDistanceMatrix(), startingNode, endNode)
                                        .stream()
                                        .map(ID -> (Node) world.getGraph().getNode(ID))
                                        .collect(Collectors.toCollection(LinkedList::new));
    }

}
