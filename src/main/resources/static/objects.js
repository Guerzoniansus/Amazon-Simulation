import {createRobot} from './objects/Robot.js';
import {createTestCube} from "./objects/TestCube.js";
import {createStellage} from "./objects/Stellage.js";
import {createTruck} from "./objects/Truck.js"

/*
To add an object:
1 - Create a .js file for it
2 - Create a function for it like: createSomeObject(addFunction, uuid)
3 - Make the object
4 - Run addFunction(yourObject, uuid) in the function
5 - Export the function
6 - Import it here
7 - Add it to the switch below
Example:

function createRobot(addFunction, uuid) {
    let robot = new THREE.Mesh();
    addFunction(robot, uuid);
}

export {createRobot}
*/

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
        case "truck":
            createTruck(addFunction, uuid);
            break;
    }
};

export {createObject};