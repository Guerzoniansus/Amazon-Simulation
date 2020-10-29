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
    addLights();

    /*
     * Hier wordt de socketcommunicatie geregeld. Er wordt een nieuwe websocket aangemaakt voor het webadres dat we in
     * de server geconfigureerd hebben als connectiepunt (/connectToSimulation). Op de socket wordt een .onmessage
     * functie geregistreerd waarmee binnenkomende berichten worden afgehandeld.
     */

    socket.onmessage = function (event) {
        // Hier wordt het commando dat vanuit de server wordt gegeven uit elkaar gehaald
        let command = parseCommand(event.data);
        console.log(command);

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
    skybox.position.y = 24;

    scene.add(skybox);
}

/**
 * Add the floor pane to the scene
 */
function addGround() {
    let geometry = new THREE.PlaneGeometry(30, 30, 32);
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
    plane.position.x = 15;
    plane.position.z = 15;
    plane.receiveShadow = true;
    scene.add(plane);
}

/**
 * Add the lights to the scene
 */
function addLights() {
    let light = new THREE.AmbientLight(0xffffff);
    light.intensity = 0.5;
    scene.add(light);

    let pointLight1 = new THREE.PointLight(0xffffff);
    pointLight1.intensity = 1;
    pointLight1.position.set(20, 20, 20);
    pointLight1.castShadow = true;
    scene.add(pointLight1);
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
 * @param {string} args - The object arguments such as coordinates and rotation
 */
function addObject(object, UUID, args) {
    //object.castShadow = true;

    object.position.x = parseInt(args.x);
    object.position.y = parseInt(args.y);
    object.position.z = parseInt(args.z);
    object.rotation.x = parseInt(args.rotationX);
    object.rotation.y = parseInt(args.rotationY);
    object.rotation.z = parseInt(args.rotationZ);

    let group = new THREE.Group();
    group.add(object);

    scene.add(group);
    worldObjects[UUID] = group;
}

/**
 * Parse incoming command input
 * @param {string} input - The input to parse
 * @returns {any} - The parsed input
 */
function parseCommand(input = "") {
    return JSON.parse(input);
}



function executeUpdateCommand(command) {

    // First check if object exists
    if (Object.keys(worldObjects).indexOf(command.parameters.uuid) >= 0) {
        let object = worldObjects[command.parameters.uuid];
        object.position.x = command.parameters.x;
        object.position.y = command.parameters.y;
        object.position.z = command.parameters.z;

        object.rotation.x = command.parameters.rotationX;
        object.rotation.y = command.parameters.rotationY;
        object.rotation.z = command.parameters.rotationZ;
    }

}

function executeCreateCommand(command) {
    if (Object.keys(worldObjects).indexOf(command.parameters.uuid) < 0
        && usedUUIDs.includes(command.parameters.uuid) == false) {

        usedUUIDs.push(command.parameters.uuid);
        createObject(command.parameters.type, addObject, command.parameters.uuid, command.parameters);
    }
}

function executeDeleteCommand(command) {
    let object = worldObjects[command.parameters.uuid];
    scene.remove(object);
    object.geometry.dispose();
    object.material.dispose();
    object = undefined;
}