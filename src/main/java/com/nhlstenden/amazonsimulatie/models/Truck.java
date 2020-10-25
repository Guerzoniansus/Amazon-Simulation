package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.List;

/*
 * Deze class stelt een truck voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een truck geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Truck extends MovingObject3D implements Updatable {

    private static final int startNodeID = 1;

    private List<Stellage> deliveredStellages;

    int stellagesToDeliver = 3;
    int stellagesToGet = 3;

    Truck(World world) {
        this(world.getGraph().getNode(startNodeID), world, 0, 0, 0);
    }

    Truck(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);

        deliveredStellages = new ArrayList<>();
    }

    @Override
    public boolean update() {
        if(x < 30) {
            this.x += 0.1;
        } else {
            this.z += 0.1;
        }

        return true;
    }

    @Override
    protected void onFinishedPath() {
        // Deliver some deliveredStellages?
    }

    /**
     * Deliver a stellage to this truck
     * @param stellage The stellage to add
     */
    public void addStellage(Stellage stellage) {
        deliveredStellages.add(stellage);
    }



}
