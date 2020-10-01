//import {GLTFLoader} from 'three/examples/jsm/loader/GLTFLoader.js';
//import 'GLTFLoader.js';
const loader = new THREE.GLTFLoader();

function createStellage(){

loader.load('textures/model.gltf', function(gltf){
const stellage = gltf.scene;
return stellage;
 });
}


export {createStellage};