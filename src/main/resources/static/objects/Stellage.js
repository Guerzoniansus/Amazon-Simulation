function createStellage(addFunction, uuid, args){

    const geometry = new THREE.BoxGeometry(0.9, 3, 0.9);
    const material = new THREE.MeshBasicMaterial({color: 0x44aa88});
    const stellage = new THREE.Mesh(geometry, material);

    addFunction(stellage, uuid, args);

    /*
    let loader = new THREE.GLTFLoader();

    loader.load('textures/shelving unit/model.gltf', function(gltf){
        let stellage = gltf.scene;


          addFunction(stellage, uuid, args);
        });

    */

}


export {createStellage};