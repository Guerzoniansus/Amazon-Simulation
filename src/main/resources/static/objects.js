import {createRobot} from './objects/Robot.js';
import {createTestCube} from "./objects/TestCube.js";
import {createStellage} from "./objects/Stellage.js";

function createObject(objectName, addFunction, uuid) {
    switch (objectName) {
        case "robot":
            createRobot(addFunction, uuid);
            break;
        case "testcube":
            createTestCube(addFunction, uuid);
            break;
        case "stellage":
            createStellage(addFunction, uuid);
            break;
    }
};

export {createObject};