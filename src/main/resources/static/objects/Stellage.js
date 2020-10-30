function createStellage(addFunction, args){

    /*
    const geometry = new THREE.BoxGeometry(0.9, 3, 0.9);
    const material = new THREE.MeshBasicMaterial({color: 0x44aa88});
    const stellage = new THREE.Mesh(geometry, material);

    addFunction(stellage, uuid, args);
    */


    let loader = new THREE.GLTFLoader();

    loader.load('textures/shelving unit/Stellage.glb', function(gltf){
        let stellage = gltf.scene;
        stellage.scale.set(0.1, 0.1, 0.1);


        addFunction(stellage, args);
    });




}


export {createStellage};