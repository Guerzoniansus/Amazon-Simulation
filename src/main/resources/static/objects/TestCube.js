let loader = new THREE.GLTFLoader();
function createTestCube() {

// dit is een comment
    loader.load('textures/model.gltf', function(gltf){
    let cube = gltf.scene;
    return cube;
     });
}

export {createTestCube};