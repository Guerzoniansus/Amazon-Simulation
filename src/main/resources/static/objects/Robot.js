function createRobot(addFunction, uuid) {
    const geometry = new THREE.BoxGeometry(0.9, 0.3, 0.9);

    const textureLoader = new THREE.TextureLoader();

    const cubeMaterials = [
        new THREE.MeshPhongMaterial({ map: textureLoader.load("textures/robot_side.png"), side: THREE.DoubleSide }), //LEFT
        new THREE.MeshPhongMaterial({ map: textureLoader.load("textures/robot_side.png"), side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshPhongMaterial({ map: textureLoader.load("textures/robot_top.png"), side: THREE.DoubleSide }), //TOP
        new THREE.MeshPhongMaterial({ map: textureLoader.load("textures/robot_bottom.png"), side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshPhongMaterial({ map: textureLoader.load("textures/robot_front.png"), side: THREE.DoubleSide }), //FRONT
        new THREE.MeshPhongMaterial({ map: textureLoader.load("textures/robot_front.png"), side: THREE.DoubleSide }), //BACK
    ];


    let robot = new THREE.Mesh(geometry, cubeMaterials);
    robot.position.y = 0.15;

    addFunction(robot, uuid);
}

export {createRobot};

