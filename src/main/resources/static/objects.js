import {createRobot} from './objects/Robot.js';
import {createTestCube} from "./objects/TestCube.js";

function createObject(objectName) {
    switch (objectName) {
        case "robot":
            return createRobot();
        case "testcube":
            return createTestCube();
    }
};

export {createObject};