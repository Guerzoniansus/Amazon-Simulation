function createTruck(addFunction, args) {

    let loader = new THREE.GLTFLoader();

    loader.load('textures/truck/Truck_1359.gltf', function(gltf) {
        let truck = gltf.scene;

        truck.traverse(function(node) {
            if (node.isMesh) {
                node.castShadow = true;
            }
        });

        truck.scale.set(0.08, 0.08, 0.08);

        addFunction(truck, args);
    });

}

export {createTruck};