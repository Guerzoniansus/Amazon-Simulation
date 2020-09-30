package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

class TestCube implements Object3D, Updatable {

    private enum Direction {NORTH, SOUTH, EAST, WEST}

    private UUID uuid;

    private double x = 0;
    private double y = 0;
    private double z = 0;

    private double rotationX = 0;
    private double rotationY = 0;
    private double rotationZ = 0;

    private Direction direction;

    public TestCube() {
        this.uuid = UUID.randomUUID();
        this.direction = Direction.NORTH;
    }

    @Override
    public boolean update() {

        if (direction == Direction.NORTH) {
            if (x < 10)
                x++;

            else direction = direction.EAST;
        }

        if (direction == Direction.EAST) {
            if (z < 10)
                z++;

            else direction = direction.SOUTH;
        }

        if (direction == Direction.SOUTH) {
            if (x > 0)
                x--;

            else direction = direction.WEST;
        }

        if (direction == Direction.WEST) {
            if (z > 0)
                z--;

            else direction = direction.NORTH;
        }

        return true;
    }

    @Override
    public String getUUID() {
        return uuid.toString();
    }

    @Override
    public String getType() {
        return TestCube.class.getSimpleName().toLowerCase();
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public double getRotationX() {
        return rotationX;
    }

    @Override
    public double getRotationY() {
        return rotationY;
    }

    @Override
    public double getRotationZ() {
        return rotationZ;
    }
}
