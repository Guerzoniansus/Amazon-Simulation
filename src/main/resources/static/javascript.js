//import * as THREE from './three.min.js';
//import "GLTFLoader";

import {createObject} from "./objects.js";

function parseCommand(input = "") {
    return JSON.parse(input);
}

let socket;

window.onload = function () {
    let camera, scene, renderer;
    let cameraControls;

    let worldObjects = {};

    // Loading 3D models costs time, checking simple strings doesn't.
    // This list is used to check if an object "exists", even when it's not loaded onto the scene yet
    // to prevent multiple objects being made with the same UUID.
    let usedUUIDs = [];


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
        document.body.appendChild(renderer.domElement);

        window.addEventListener('resize', onWindowResize, false);

        let geometry = new THREE.PlaneGeometry(30, 30, 32);
        let material = new THREE.MeshBasicMaterial({ color: 0xffffff, side: THREE.DoubleSide });
        let plane = new THREE.Mesh(geometry, material);
        plane.rotation.x = Math.PI / 2.0;
        plane.position.x = 15;
        plane.position.z = 15;
        scene.add(plane);

        addLights();
    }

    function addLights() {
        let light = new THREE.AmbientLight(0x404040, 100); //ffffff
        light.intensity = 0.5;
        scene.add(light);

        let pointLight1 = new THREE.PointLight(0xffffff);
        pointLight1.intensity = 1;
        pointLight1.position.set(20, 20, 20);
        scene.add(pointLight1);
    }

    function onWindowResize() {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
    }

    function animate() {
        requestAnimationFrame(animate);
        cameraControls.update();
        renderer.render(scene, camera);
    }

    function addObject(object, uuid) {
        let group = new THREE.Group();
        group.add(object);

        scene.add(group);
        worldObjects[uuid] = group;
    }


    /*
     * Hier wordt de socketcommunicatie geregeld. Er wordt een nieuwe websocket aangemaakt voor het webadres dat we in
     * de server geconfigureerd hebben als connectiepunt (/connectToSimulation). Op de socket wordt een .onmessage
     * functie geregistreerd waarmee binnenkomende berichten worden afgehandeld.
     */
    socket = new WebSocket("ws://" + window.location.hostname + ":" + window.location.port + "/connectToSimulation");
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

    init();
    animate();
};