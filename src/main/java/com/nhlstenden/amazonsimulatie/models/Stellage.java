package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;


class Stellage extends Object3D implements Updatable {


    Stellage() {
        super();
    }

    Stellage(double x, double y, double z) {
        super(x, y, z);
    }

    Stellage(double x, double y, double z, double rotationX, double rotationY, double rotationZ) {
        super(x, y, z, rotationX, rotationY, rotationZ);
    }

    @Override
    public boolean update() {


        return true;
    }

}
