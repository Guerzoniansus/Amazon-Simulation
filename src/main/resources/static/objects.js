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

/**
 * Create a new object
 * @param {string} objectName - The name of the object as received from the server
 * @param {function} addFunction - The function an object needs to call on its own to add itself to the scene
 * @param {string} UUID - The UUID of the object
 */
function createObject(objectName, addFunction, UUID) {
    switch (objectName) {
        case "robot":
            createRobot(addFunction, UUID);
            break;
        case "testcube":
            createTestCube(addFunction, UUID);
            break;
        case "stellage":
            createStellage(addFunction, UUID);
            break;
        case "truck":
            createTruck(addFunction, uuid);
            break;
    }
}

export {createObject};