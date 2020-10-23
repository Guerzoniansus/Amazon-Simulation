package com.nhlstenden.amazonsimulatie.models;

;import com.nhlstenden.amazonsimulatie.models.robottasks.IdleTask;
import com.nhlstenden.amazonsimulatie.models.robottasks.RobotTask;

import java.util.Queue;
/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
public class Robot extends MovingObject3D implements Updatable {

    private RobotTask task;

    public Robot(Node node, World world) {
        super(node, world);

        this.speed = 0.2;
        this.task = new IdleTask(world, this);

    }

    public Robot(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        this.speed = 0.2;
        this.task = new IdleTask(world, this);
        currentDestination = null;
    }

    @Override
    public boolean update() {

        if (move()) {
            return true;
        }

        else {
            if (this.task.getClass().getSimpleName() != IdleTask.class.getSimpleName()) {
                this.task = new IdleTask(world, this);
            }

            return false;
        }
    }

    /**
     * Makes the robot execute their task by determining their path
     */
    public void executeTask() {
        setPath(task.getPath());
    }

    /**
     * Returns the name of the current RobotTask task assigned to this robot
     * @return This robot's RobotTask instance's Class name
     */
    public String getTaskName() {
        return this.task.getClass().getSimpleName();
    }

}