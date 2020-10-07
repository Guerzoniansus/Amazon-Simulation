function createStellage(addFunction, uuid){

let loader = new THREE.GLTFLoader();

loader.load('textures/shelving unit/model.gltf', function(gltf){
    let stellage = gltf.scene;



      addFunction(stellage, uuid);
    });
}


export {createStellage};