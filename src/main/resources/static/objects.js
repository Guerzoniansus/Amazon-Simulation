import {createRobot} from './objects/Robot.js';
import {createTestCube} from "./objects/TestCube.js";
import {createStellage} from "./objects/Stellage.js";

function createObject(objectName) {
    switch (objectName) {
        case "robot":
            return createRobot();
        case "testcube":
            return createTestCube();
        case "stellage":
            return createStellage();
    }
};

export {createObject};