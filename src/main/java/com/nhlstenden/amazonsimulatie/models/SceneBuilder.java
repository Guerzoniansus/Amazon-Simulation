package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.Object3D;

import java.util.List;

class SceneBuilder {

    static void buildScene(List<Object3D> worldObjects) {
        buildRobots(worldObjects);
        buildStellages(worldObjects);
        buildTruck(worldObjects);
    }

    private static void buildTruck(List<Object3D> worldObjects) {
       // Truck truck = new Truck();
       // worldObjects.add(truck);
    }

    private static void buildRobots(List<Object3D> worldObjects) {
        Robot robot1 = new Robot();
        worldObjects.add(robot1);
    }

    private static void buildStellages(List<Object3D> worldObjects) {
        Stellage shelvingUnit = new Stellage(0, -1, 0);
        worldObjects.add(shelvingUnit);
/*
        Stellage shelvingUnit2 = new Stellage(5, -1, 0);
        worldObjects.add(shelvingUnit2);

        Stellage shelvingUnit3 = new Stellage(10, -1, 0);
        worldObjects.add(shelvingUnit3);

        Stellage shelvingUnit4 = new Stellage(15, -1, 0);
        worldObjects.add(shelvingUnit4);

        Stellage shelvingUnit5 = new Stellage(20, -1, 0);
        worldObjects.add(shelvingUnit5);

        Stellage shelvingUnit6 = new Stellage(25, -1, 0);
        worldObjects.add(shelvingUnit6);

        Stellage shelvingUnit7 = new Stellage(30, -1, 0);
        worldObjects.add(shelvingUnit7);

        Stellage shelvingUnit8 = new Stellage(0, -1, 5);
        worldObjects.add(shelvingUnit8);

        Stellage shelvingUnit9 = new Stellage(5, -1, 5);
        worldObjects.add(shelvingUnit9);
        */
    }


}
