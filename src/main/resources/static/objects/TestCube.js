/*function createTestCube() {

    let cube;

    let loader = new THREE.GLTFLoader();

    loader.load('textures/model.gltf', function(gltf) {
    cube = gltf.scene;
    });

    return cube;
}*/


function createTestCube() {
    const width = 1;
    const height = 1;
    const depth = 1;

    const geometry = new THREE.BoxBufferGeometry(width, height, depth);

    const textureLoader = new THREE.TextureLoader();

    const material = new THREE.MeshPhongMaterial(
        {
            map: textureLoader.load("textures/TestCube.jpg")
        }
    );

    const cube = new THREE.Mesh(geometry, material);
    cube.position.x = 0;
    cube.position.y = 1;
    cube.position.z = 0;

    return cube;
}


export {createTestCube};