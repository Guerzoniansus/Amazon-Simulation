package com.nhlstenden.amazonsimulatie.models;

;import com.nhlstenden.amazonsimulatie.models.robottasks.IdleTask;
import com.nhlstenden.amazonsimulatie.models.robottasks.RobotTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
public class Robot extends MovingObject3D implements Updatable {

    private RobotListener task;

    private Stellage stellage;

    public Robot(Node node, World world) {
        this(node, world, 0, 0, 0);
    }

    public Robot(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        this.speed = 0.1;

        this.stellage = null;
        this.task = new IdleTask(world, this);
    }


    @Override
    protected void onFinishedPath() {
        task.onFinishedPath();
    }


    @Override
    public boolean update() {
        return move();
    }

    /**
     * Register a RobotListener that listens to this robot instance
     * @param listener The listener to add
     */
    public void addRobotListener(RobotListener listener) {
        if (!(this.task instanceof IdleTask) && this.task != null)
            throw new IllegalStateException(String.format("bro im already doing %s", this.task));
        this.task = listener;
    }

    /**
     * Remove a specific RobotListener that lsitens to this robot instance
     * @param listener
     */
    public void removeRobotListener(RobotListener listener) {
        this.task = null;
    }

    /**
     * Makes the robot execute their task by determining their path
     */
    public void executeTask(RobotTask newTask) {
        setPath(newTask.getPath());
    }

    /**
     * Returns the name of the current RobotTask task assigned to this robot
     * @return This robot's RobotTask instance's simple Class name
     */
    public String getTaskName() {
        return task.getClass().getSimpleName();
    }

    /**
     * Get the stellage that this robot is currently assigned to transport
     * @return The Stellage
     */
    public Stellage getStellage() {
        return stellage;
    }

    /**
     * Set the stellage currently assigned to this robot
     * @param stellage The Stellage
     */
    public void setStellage(Stellage stellage) {
        this.stellage = stellage;
    }

    /**
     * Un-assigns this robot's stellage (essentially sets it to null)
     */
    public void removeStellage() {
        stellage = null;
    }

    /**
     * Returns whether or not this robot has a stellage assigned to them
     * @return Whether or not this robot has a stellage assigned to them
     */
    public boolean hasStellage() {
        return stellage != null;
    }

}