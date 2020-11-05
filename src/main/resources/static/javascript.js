//import * as THREE from './three.min.js';
//import "GLTFLoader";
import {createObject} from "./objects.js";

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
    addWall();
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
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/background_2.jpg"), side: THREE.DoubleSide }), //LEFT
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/background_1.jpg"), side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/background_3.jpg"), side: THREE.DoubleSide }), //TOP
        new THREE.MeshBasicMaterial({ map: groundTexture, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/background_5.jpg"), side: THREE.DoubleSide }), //FRONT
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/background_6.jpg"), side: THREE.DoubleSide }), //BACK
    ];

    const skybox = new THREE.Mesh(boxGeometry, boxMaterials);
    skybox.receiveShadow = true;
    skybox.position.y = 24.90;

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

    let plane = new THREE.Mesh(geometry, material);
    plane.rotation.x = Math.PI / 2.0;
    plane.position.x = 4;
    plane.position.z = 6;
    plane.receiveShadow = true;
    scene.add(plane);
}

function addWall() {
let y = 2;
    const textureLoader = new THREE.TextureLoader();
    let ninetydegrees = Math.PI/2;
    let wallshape1 = new THREE.BoxGeometry(7.599, 4, 0.302);
    let paddingshape1 = new THREE.BoxGeometry(7.7, 0.2, 0.4);
    let wallshape2 = new THREE.BoxGeometry(11.6, 4, 0.3);
    let paddingshape2 = new THREE.BoxGeometry(11, 0.2, 0.4);
    let wallSection = new THREE.BoxGeometry(4.201, 4, 0.3);
    let paddingSection = new THREE.BoxGeometry(4, 0.2, 0.4)
    let gateSection = new THREE.BoxGeometry(3.2, 1, 0.5);

    const wall1frondback = textureLoader.load("textures/brick.jpg");
    wall1frondback.wrapS = THREE.RepeatWrapping;
    wall1frondback.wrapT = THREE.RepeatWrapping;
    wall1frondback.repeat.set(2, 1);

    let materialwall1 = [

        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //LEFT
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshBasicMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //TOP
        new THREE.MeshBasicMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshBasicMaterial({ map: wall1frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //FRONT
        new THREE.MeshBasicMaterial({ map: wall1frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //BACK
    ];
    const wall2frondback = textureLoader.load("textures/brick.jpg");
    wall2frondback.wrapS = THREE.RepeatWrapping;
    wall2frondback.wrapT = THREE.RepeatWrapping;
    wall2frondback.repeat.set(2.26, 1);
    let materialwall2 = [

        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //LEFT
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshBasicMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //TOP
        new THREE.MeshBasicMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshBasicMaterial({ map: wall2frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //FRONT
        new THREE.MeshBasicMaterial({ map: wall2frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //BACK
    ];
    let materialwallSection = [

        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //LEFT
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshBasicMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //TOP
        new THREE.MeshBasicMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //FRONT
        new THREE.MeshBasicMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //BACK
    ];

    let materialpadding = new THREE.MeshStandardMaterial(
        {
        side: THREE.DoubleSide,
        roughness: 0.8,
        metalness: 0.7,
        color: 0x2f4f4f
        }
    );
    let materialgate = new THREE.MeshStandardMaterial(
        {
        side: THREE.DoubleSide,
        roughness: 0.8,
        metalness: 0.7,
        color: 0x000080
        }
    );
    let wall1 = new THREE.Mesh(wallshape1, materialwall1);
    wall1.position.y = y;
    wall1.position.z = 0.35;
    wall1.position.x = 4;

    let padding = new THREE.Mesh(paddingshape1, materialpadding)
    padding.position.y = 0.1;
    padding.position.z = 0.35;
    padding.position.x = 4;

    let wall2 = new THREE.Mesh(wallshape2, materialwall2)
    wall2.position.y = y;
    wall2.position.x = 7.65;
    wall2.position.z = 6;
    wall2.rotation.y = ninetydegrees;

    let padding2 = new THREE.Mesh(paddingshape2, materialpadding)
    padding2.position.y = 0.1;
    padding2.position.x = 7.65;
    padding2.position.z = 6;
    padding2.rotation.y = ninetydegrees;

    let wall3 = new THREE.Mesh(wallshape1, materialwall1)
    wall3.position.y = y;
    wall3.position.z = 11.65
    wall3.position.x = 4;

    let padding3 = new THREE.Mesh(paddingshape1, materialpadding)
    padding3.position.y = 0.1;
    padding3.position.z = 11.65;
    padding3.position.x = 4;

    let tinywall1 = new THREE.Mesh(wallSection, materialwallSection)
    tinywall1.position.y = y;
    tinywall1.position.x = 0.35;
    tinywall1.position.z = 9.7;
    tinywall1.rotation.y = ninetydegrees;

    let paddingtinywall1 = new THREE.Mesh(paddingSection, materialpadding)
    paddingtinywall1.position.y = 0.1;
    paddingtinywall1.position.x = 0.35;
    paddingtinywall1.position.z = 9.8;
    paddingtinywall1.rotation.y = ninetydegrees;

    let tinywall2 = new THREE.Mesh(wallSection, materialwallSection)
    tinywall2.position.y = y;
    tinywall2.position.x = 0.35;
    tinywall2.position.z = 2.3;
    tinywall2.rotation.y = ninetydegrees;

    let paddingtinywall2 = new THREE.Mesh(paddingSection, materialpadding)
    paddingtinywall2.position.y = 0.1;
    paddingtinywall2.position.x = 0.35;
    paddingtinywall2.position.z = 2.4;
    paddingtinywall2.rotation.y = ninetydegrees;

    let gate = new THREE.Mesh(gateSection, materialgate)
    gate.position.y = 3.5;
    gate.position.z = 6;
    gate.position.x = 0.15;
    gate.rotation.y = ninetydegrees;

    scene.add(wall1)
    scene.add(padding)
    scene.add(wall2)
    scene.add(padding2)
    scene.add(wall3)
    scene.add(padding3)
    scene.add(tinywall1)
    scene.add(paddingtinywall1)
    scene.add(tinywall2)
    scene.add(paddingtinywall2)
    scene.add(gate)
}


/**
 * Add the lights to the scene
 */
function addLights() {
    let light = new THREE.AmbientLight(0xffffff);
    light.intensity = 0.4;
    //scene.add(light);

    let pointLight1 = new THREE.PointLight(0xffffff);
    pointLight1.intensity = 1;
    pointLight1.position.set(20, 20, 20);
    pointLight1.castShadow = true;
    scene.add(pointLight1);
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