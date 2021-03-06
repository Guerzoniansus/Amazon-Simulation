//import * as THREE from './three.min.js';
//import "GLTFLoader";
import {createObject} from "./objects.js";
import {walls} from "./walls.js";

window.onload = () => {
    init();
    animate();
};

const socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");

let camera, scene, renderer;
let cameraControls;

let worldObjects = {};


// Loading 3D models costs time, checking simple strings doesn't.
// This list is used to check if an object "exists", even when it's not loaded onto the scene yet
// to prevent multiple objects being made with the same UUID.
let usedUUIDs = [];

/**
 * Initialize the scene
 */
function init() {
    const fov = 70;
    const near = 1;
    const far = 1500;
    camera = new THREE.PerspectiveCamera(fov, window.innerWidth / window.innerHeight, near, far);
    cameraControls = new THREE.OrbitControls(camera);
    camera.position.z = 15;
    camera.position.y = 5;
    camera.position.x = 15;
    cameraControls.update();

    scene = new THREE.Scene();

    renderer = new THREE.WebGLRenderer({ antialias: true });
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(window.innerWidth, window.innerHeight + 5);
    renderer.shadowMap.enabled = true;
    document.body.appendChild(renderer.domElement);

    window.addEventListener('resize', onWindowResize, false);

    addSkybox();
    addGround();
    addWalls();
    addLights();
    addPath();

    /*
     * Hier wordt de socketcommunicatie geregeld. Er wordt een nieuwe websocket aangemaakt voor het webadres dat we in
     * de server geconfigureerd hebben als connectiepunt (/connectToSimulation). Op de socket wordt een .onmessage
     * functie geregistreerd waarmee binnenkomende berichten worden afgehandeld.
     */

    socket.onmessage = function (event) {
        // Hier wordt het commando dat vanuit de server wordt gegeven uit elkaar gehaald
        let command = parseCommand(event.data);

        switch (command.command) {
            case "object_update":
                executeUpdateCommand(command);
                break;
            case "object_create":
                executeCreateCommand(command);
                break;
            case "object_delete":
                executeDeleteCommand(command);
                break;
        }
    };

}

function addSkybox() {
    const boxGeometry = new THREE.BoxGeometry(500, 50, 500);

    const textureLoader = new THREE.TextureLoader();
    const groundTexture = textureLoader.load("textures/background_4.jpg");
    groundTexture.wrapS = THREE.RepeatWrapping;
    groundTexture.wrapT = THREE.RepeatWrapping;
    groundTexture.repeat.set(30, 30);

    const boxMaterials = [
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/background_2.jpg"), side: THREE.DoubleSide }), //LEFT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/background_1.jpg"), side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/background_3.jpg"), side: THREE.DoubleSide }), //TOP
        new THREE.MeshStandardMaterial({ map: groundTexture, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/background_5.jpg"), side: THREE.DoubleSide }), //FRONT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/background_6.jpg"), side: THREE.DoubleSide }), //BACK
    ];

    const skybox = new THREE.Mesh(boxGeometry, boxMaterials);
    skybox.receiveShadow = true;
    skybox.roughness = 0.0;
    skybox.position.y = 24.94;

    scene.add(skybox);
}

/**
 * Add the floor pane to the scene
 */
function addGround() {
    let geometry = new THREE.PlaneGeometry(7.5, 11, 32);
    let material = new THREE.MeshStandardMaterial(
        {
        side: THREE.DoubleSide,
        roughness: 0.8,
        metalness: 0.7,
        map: new THREE.TextureLoader().load("textures/metal_ground.jpg")
        }
    );

    const ground = [];
    let plane = new THREE.Mesh(geometry, material);
    plane.rotation.x = Math.PI / 2.0;
    plane.position.x = 4;
    plane.position.y = -0.05
    plane.position.z = 6;
    plane.receiveShadow = true;
    scene.add(plane);
}

function addWalls() {

    walls.forEach(wall => {
        scene.add(wall);
    });

}


/**
 * Add the lights to the scene
 */
function addLights() {
    let light = new THREE.AmbientLight(0xffffff);
    light.intensity = 0.05;
    scene.add(light);

    const intensity = 0.3;
    let pointLight1 = new THREE.PointLight(0xffffff, intensity);
    pointLight1.position.set(4, 3, 1);
    pointLight1.castShadow = true;
    scene.add(pointLight1);

    let pointLight2 = new THREE.PointLight(0xffffff, intensity);
        pointLight2.position.set(4, 3, 11);
        pointLight2.castShadow = true;
        scene.add(pointLight2);

    let sun = new THREE.PointLight(0xf48037)
    sun.intensity = 0.9
    sun.position.set(15, 5, 90)
    scene.add(sun);
}

/**
 * Draws a path line on the ground
 * @returns {Promise<void>} Idk what this does
 */
async function addPath() {

    const textFile = "graph.txt";
    const graph = await fetch(textFile).then(r => r.text());

    const CONSTANT_Y = 0;
    const vectors = [...graph.replace(/\/\/.+/g, '').matchAll(/(\d+): (\w+), (-?\d+), (-?\d+)/g)]
        .map(([, id, type, x, z]) => new THREE.Vector3(+x, CONSTANT_Y, +z));

    vectors.forEach(vector => {
        const geometry = new THREE.BoxBufferGeometry(1, 0.1, 1);
        const material = new THREE.MeshBasicMaterial({color: 0x000000, opacity: 0.3, transparent: true});
        const square = new THREE.Mesh(geometry, material);
        square.position.x = vector.x;
        square.position.y = vector.y;
        square.position.z = vector.z;
        scene.add(square);
    })
}

function onWindowResize() {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
}

/**
 * Animate the scene
 */
function animate() {
    requestAnimationFrame(animate);
    cameraControls.update();
    renderer.render(scene, camera);
}

/**
 * Add an object to the scene and to the worldObjects array
 * @param object - The 3D object (a Three JS Mesh)
 * @param {string} UUID - The UUID of the object as it's stored on the server
 * @param {string} args - The object arguments such as UUID, coordinates and rotation
 */
function addObject(object, args) {
    object.castShadow = true;

    object.position.x = parseFloat(args.x);
    object.position.y = parseFloat(args.y);
    object.position.z = parseFloat(args.z);
    object.rotation.x = THREE.Math.degToRad(parseFloat(args.rotationX));
    object.rotation.y = THREE.Math.degToRad(parseFloat(args.rotationY));
    object.rotation.z = THREE.Math.degToRad(parseFloat(args.rotationZ));

    scene.add(object);
    worldObjects[args.uuid] = object;
}

/**
 * Parse incoming command input
 * @param {string} input - The input to parse
 * @returns {any} - The parsed input
 */
function parseCommand(input = "") {
    return JSON.parse(input);
}


/**
 * Execute the object update command
 * @param {string} command - The command sent by the server
 */
function executeUpdateCommand(command) {

    // First check if object exists
    if (Object.keys(worldObjects).indexOf(command.parameters.uuid) >= 0) {
        let object = worldObjects[command.parameters.uuid];
        object.position.x = command.parameters.x;
        object.position.y = command.parameters.y;
        object.position.z = command.parameters.z;

        object.rotation.x = THREE.Math.degToRad(parseFloat(command.parameters.rotationX));
        object.rotation.y = THREE.Math.degToRad(parseFloat(command.parameters.rotationY));
        object.rotation.z = THREE.Math.degToRad(parseFloat(command.parameters.rotationZ));
    }

}

/**
 * Execute the object create command by creating a new 3D object
 * @param {string} command - The command sent by the server
 */
function executeCreateCommand(command) {
    if (Object.keys(worldObjects).indexOf(command.parameters.uuid) < 0
        && usedUUIDs.includes(command.parameters.uuid) == false) {

        usedUUIDs.push(command.parameters.uuid);
        createObject(command.parameters.type, addObject, command.parameters);
    }
}

/**
 * Execute the delete update command by deleting an object
 * @param {string} command - The command sent by the server
 */
function executeDeleteCommand(command) {
    let object = worldObjects[command.parameters.uuid];
    scene.remove(object);
    object.geometry.dispose();
    object.material.dispose();
    object = undefined;
}