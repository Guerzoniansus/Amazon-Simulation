function createTestCube() {

    let cube;

    let loader = new THREE.GLTFLoader();

    loader.load('textures/model.gltf', function(gltf) {
    cube = gltf.scene;
    });

    return cube;
}

export {createTestCube};