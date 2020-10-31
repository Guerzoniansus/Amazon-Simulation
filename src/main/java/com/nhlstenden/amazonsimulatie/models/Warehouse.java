package com.nhlstenden.amazonsimulatie.models;

import java.util.List;
import java.util.stream.Collectors;

class Warehouse {

    private World world;
    private World.WorldEditor worldEditor;

    private Truck truck;

    private List<Robot> robots;

    public Warehouse(World world, World.WorldEditor worldEditor) {
        this.world = world;
        this.worldEditor = worldEditor;

        this.truck = (Truck) worldEditor.getWorldObjects()
                                        .stream()
                                        .filter(object -> object instanceof Truck)
                                        .findFirst()
                                        .get();

        this.robots = worldEditor.getWorldObjects()
                                        .stream()
                                        .filter(obj -> obj instanceof Robot)
                                        .map(obj -> (Robot) obj)
                                        .collect(Collectors.toList());
    }

    /**
     * Gets all the robots in the warehouse
     * @return A list of robots
     */
    public List<Robot> getRobots() {
        return worldEditor.getWorldObjects().stream()
                                            .filter(object -> object instanceof Robot)
                                            .map(object -> (Robot) object)
                                            .collect(Collectors.toList());
    }

    /**
     * Notify the warehouse that a new robot is now idle and ready for a new tank
     * @param robot The Robot that has now become idle
     */
    public void notifyNewRobotIsIdle(Robot robot) {
        truck.notifyNewRobotAvailable(robot);
    }

    /**
     * Gets a list of all idle robots
     * @return A List with idle robots
     */
    public List<Robot> getIdleRobots() {
        return robots.stream()
                     .filter(robot -> robot.getTaskName().equals(IdleTask.class.getSimpleName()))
                     .collect(Collectors.toList());
    }

    /**
     * Add a new stellage to the world so it gets displayed in 3D
     * @param stellage The new stellage to add
     */
    public void addNewStellage(Stellage stellage) {
        worldEditor.createObject(stellage);
    }

    /**
     * Replaces the truck with a new one
     */
    public void replaceTruck() {
        truck.die();
        truck = new Truck(world);
        worldEditor.createObject(truck);
    }

    /**
     * Get a list of all the Stellages currently in the world
     * @return A list of all stellages
     */
    public List<Stellage> getStellages() {
        return worldEditor.getWorldObjects().stream()
                                            .filter(object -> object instanceof Stellage)
                                            .map(object -> (Stellage) object)
                                            .collect(Collectors.toList());
    }

    /**
     * Get a list of available storage locations that have not been assigned to a stellage yet
     * @return A list of available nodes of NodeType STELLAGE
     */
    public List<Node> getAvailableStorageLocations() {
        return world.getGraph().getNodes().stream().filter(node -> {
            if (node.getType() != NodeType.STELLAGE) {
                return false;
            }

            // Check if there is already a stellage stored on this node
            for (Stellage stellage : getStellages()) {
                if (stellage.hasStorageLocation()) {
                    if (stellage.getStorageLocation() == node) {
                        return false;
                    }
                }
            }

            return true;
        }).collect(Collectors.toList());
    }

    /**
     * Get this warehouse's current truck
     * @return The Truck
     */
    public Truck getTruck() {
        return truck;
    }

}
