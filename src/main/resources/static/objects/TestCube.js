function createTestCube(addFunction, uuid) {

    let loader = new THREE.GLTFLoader();

    loader.load('textures/model.gltf', function(gltf) {
        let cube = gltf.scene;

        cube.traverse(function(node) {
            if (node.isMesh) {
                node.castShadow = true;
            }
        });

        addFunction(cube, uuid);
    });

}

export {createTestCube};

/* Oude code
function createTestCube(addFunction, uuid) {
    const width = 1;
    const height = 1;
    const depth = 1;

    const geometry = new THREE.BoxBufferGeometry(width, height, depth);

    const textureLoader = new THREE.TextureLoader();

    const material = new THREE.MeshPhongMaterial(
        {
            map: textureLoader.load("textures/test_cube.jpg")
        }
    );

    const cube = new THREE.Mesh(geometry, material);
    cube.position.x = 0;
    cube.position.y = 1;
    cube.position.z = 0;

    addFunction(cube, uuid);
}
*/

