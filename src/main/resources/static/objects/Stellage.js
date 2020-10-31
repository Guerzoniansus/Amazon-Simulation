function createStellage(addFunction, args){

    let loader = new THREE.GLTFLoader();

    loader.load('textures/shelving unit/Stellage.glb', function(gltf){
        let stellage = gltf.scene;
        stellage.scale.set(0.1, 0.1, 0.1);

        stellage.traverse(function(node) {
            if (node.isMesh) {
                node.castShadow = true;
            }
        });


        addFunction(stellage, args);
    });




}


export {createStellage};