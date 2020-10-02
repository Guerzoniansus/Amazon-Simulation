import {createRobot} from './objects/Robot.js';
import {createTestCube} from "./objects/TestCube.js";
import {createStellage} from "./objects/Stellage.js";

function createObject(objectName) {

    let newObject;

    switch (objectName) {
        case "robot":
            newObject = createRobot();
            break;
        case "testcube":
            newObject = createTestCube();
            break;
        case "stellage":
            newObject = createStellage();
            break;
    }

    newObject.castShadow = true;

    return newObject;
};

export {createObject};