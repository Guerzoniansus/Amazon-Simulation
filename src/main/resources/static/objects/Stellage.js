//import {GLTFLoader} from 'three/examples/jsm/loader/GLTFLoader.js';
//import 'GLTFLoader.js';


function createStellage(addFunction, uuid){

const loader = new THREE.GLTFLoader();

loader.load('textures/model.gltf', function(gltf){
    const stellage = gltf.scene;
    addFunction(stellage, uuid);
    });
}


export {createStellage};