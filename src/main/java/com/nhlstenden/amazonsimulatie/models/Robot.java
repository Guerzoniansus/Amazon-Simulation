package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Robot extends Object3D implements Updatable {

    Robot() {
        super();
    }

    Robot(double x, double y, double z) {
        super(x, y, z);
    }

    Robot(double x, double y, double z, double rotationX, double rotationY, double rotationZ) {
        super(x, y, z, rotationX, rotationY, rotationZ);
    }

    @Override
    public boolean update() {
        if(x < 15) {
            this.x += 0.1;
        } else {
            this.z += 0.1;
        }
        
        return true;
    }


}