package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.models.Object3D;

import java.util.List;

class SceneBuilder {

    static void buildScene(List<Object3D> worldObjects) {
        buildRobots(worldObjects);
        buildStellages(worldObjects);
    }

    private static void buildRobots(List<Object3D> worldObjects) {
        Robot robot1 = new Robot();
        worldObjects.add(robot1);

        Robot robot2 = new Robot(5, 1, 5);
        worldObjects.add(robot2);
    }

    private static void buildStellages(List<Object3D> worldObjects) {
        TestCube cube = new TestCube(0, -1, 0);
        worldObjects.add(cube);

        TestCube cube2 = new TestCube(5, -1, 5);
        worldObjects.add(cube2);
    }


}
