function createTruck(addFunction, uuid) {

    let loader = new THREE.GLTFLoader();

    loader.load('textures/truck/Truck_1359.gltf', function(gltf) {
        let truck = gltf.scene;

        //truck.traverse(function(node) {
        //    if (node.isMesh) {
        //        node.castShadow = true;
        //    }
        //});

        addFunction(truck, uuid);
    });

}

export {createTruck};