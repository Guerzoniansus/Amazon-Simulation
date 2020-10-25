package com.nhlstenden.amazonsimulatie.models;

import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {

    private World world;
    private World.WorldEditor worldEditor;

    private Truck truck;

    public Warehouse(World world, World.WorldEditor worldEditor) {
        this.world = world;
        this.worldEditor = worldEditor;

        this.truck = (Truck) worldEditor.getWorldObjects()
                                        .stream()
                                        .filter(object -> object instanceof Truck)
                                        .findFirst()
                                        .get();
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
     * Notify the warehouse that a new robot is becoming idle and is ready for a new task
     * @param robot
     */
    public void notifyNewRobotIsReady(Robot robot) {
        //TODO: Implement
    }

    public void replaceTruck() {
        //TODO: Implement

        // worldEditor.deleteObject(truck);
        // truck = new Truck(world);
        // worldEditor.createObject(truck);
        // truck.getStellages().forEach(x -> worldEditor.deleteObject(x))
        // potentially more stuff
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
            for (Stellage stellage : getStellages()) {
                if (stellage.hasStorageLocation()) {
                    if (stellage.getStorageLocation() == node) {
                        return false;
                    }
                }
            }

            if (node.getType() == NodeType.STELLAGE) {
                return true;
            }

            return false;
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
