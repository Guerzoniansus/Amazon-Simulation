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
    camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 1000);
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

    addGround();
    addLights();
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
 */
function addObject(object, UUID) {
    object.castShadow = true;

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

/*
 * Hier wordt de socketcommunicatie geregeld. Er wordt een nieuwe websocket aangemaakt voor het webadres dat we in
 * de server geconfigureerd hebben als connectiepunt (/connectToSimulation). Op de socket wordt een .onmessage
 * functie geregistreerd waarmee binnenkomende berichten worden afgehandeld.
 */

socket.onmessage = function (event) {
    // Hier wordt het commando dat vanuit de server wordt gegeven uit elkaar gehaald
    let command = parseCommand(event.data);

    // Wanneer het commando is "object_update", dan wordt deze code uitgevoerd. Bekijk ook de servercode om dit goed te begrijpen.
    if (command.command == "object_update") {

        // Wanneer het object dat moet worden geupdate nog niet bestaat (komt niet voor in de lijst met worldObjects op de client),
        // dan wordt het 3D model eerst aangemaakt in de 3D wereld.
        if (Object.keys(worldObjects).indexOf(command.parameters.uuid) < 0
        && usedUUIDs.includes(command.parameters.uuid) == false) {

            usedUUIDs.push(command.parameters.uuid);
            createObject(command.parameters.type, addObject, command.parameters.uuid);

        }

        /*
         * Deze code wordt elke update uitgevoerd. Het update alle positiegegevens van het 3D object.
         */

        let object = worldObjects[command.parameters.uuid];

        object.position.x = command.parameters.x;
        object.position.y = command.parameters.y;
        object.position.z = command.parameters.z;

        object.rotation.x = command.parameters.rotationX;
        object.rotation.y = command.parameters.rotationY;
        object.rotation.z = command.parameters.rotationZ;
    }
};
