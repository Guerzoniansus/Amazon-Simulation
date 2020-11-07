package com.nhlstenden.amazonsimulatie.models;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

abstract class RobotTask implements RobotListener {

    /**
     * ============== HALLO DOCENT :) ==============
     * De Robot Taken implementeerden eerst Robot Listener.
     * Robot had toen ook nog normale addListener() en removeListener() functies.
     * De robot vertelde dan aan ELKE listener wanneer het zijn pad had bereikt.
     * Dit leidde alleen jammer genoeg tot bugs die uiteindelij niet opgelost konden worden.
     * Robot heeft nu geen listener fields / functies meer,
     * maar de Robot Taken implementeren nog steeds RobotListener zodat er altijd
     * nog geprobeerd kan worden om die juiste manier wel weer te programmeren.
     */

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
