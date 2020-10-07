package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

/*
 * Deze class stelt een truck voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een truck geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Truck extends Object3D implements Updatable {

    Truck() {
        super();
    }

    Truck(double x, double y, double z) {
        super(x, y, z);
    }

    Truck(double x, double y, double z, double rotationX, double rotationY, double rotationZ) {
        super(x, y, z, rotationX, rotationY, rotationZ);
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


}