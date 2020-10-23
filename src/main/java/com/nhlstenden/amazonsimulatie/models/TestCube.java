package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

class TestCube extends Object3D implements Updatable {

    private enum Direction {NORTH, SOUTH, EAST, WEST}

    private double speed = 0.1;

    private Direction direction;


    TestCube(Node node, World world) {
        super(node, world);
        this.direction = Direction.NORTH;
    }

    TestCube(Node node, World world, double rotationX, double rotationY, double rotationZ) {
        super(node, world, rotationX, rotationY, rotationZ);
        this.direction = Direction.NORTH;
    }

    @Override
    public boolean update() {

        if (direction == Direction.NORTH) {
            if (x < 10)
                x += speed;

            else direction = direction.EAST;
        }

        if (direction == Direction.EAST) {
            if (z < 10)
                z += speed;

            else direction = direction.SOUTH;
        }

        if (direction == Direction.SOUTH) {
            if (x > 0)
                x -= speed;

            else direction = direction.WEST;
        }

        if (direction == Direction.WEST) {
            if (z > 0)
                z -= speed;

            else direction = direction.NORTH;
        }

        return true;
    }

}
