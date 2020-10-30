function createRobot(addFunction, args) {

    let loader = new THREE.GLTFLoader();

    loader.load('textures/robot/Robot.glb', function(gltf){
        let robot = gltf.scene;
        robot.scale.set(0.1, 0.1, 0.1);


        addFunction(robot, args);
    });
}

export {createRobot};

