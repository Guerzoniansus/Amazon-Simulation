
let y = 1.95;
let ninetydegrees = Math.PI/2;
    const textureLoader = new THREE.TextureLoader();
    let wallshape1 = new THREE.BoxGeometry(7.599, 4, 0.302);
    let paddingshape1 = new THREE.BoxGeometry(7.7, 0.2, 0.4);
    let wallshape2 = new THREE.BoxGeometry(11.6, 4, 0.3);
    let paddingshape2 = new THREE.BoxGeometry(11, 0.2, 0.4);
    let wallSection = new THREE.BoxGeometry(4.201, 4, 0.3);
    let paddingSection = new THREE.BoxGeometry(4, 0.2, 0.4)
    let gateSection = new THREE.BoxGeometry(3.2, 1, 0.5);
    let invisibleroofshape = new THREE.BoxGeometry(7.5, 0.2, 11);

    const wall1frondback = textureLoader.load("textures/brick.jpg");
    wall1frondback.wrapS = THREE.RepeatWrapping;
    wall1frondback.wrapT = THREE.RepeatWrapping;
    wall1frondback.repeat.set(2, 1);

    let materialwall1 = [

        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide}), //LEFT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide}), //RIGHT
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide}), //TOP
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide}), //BOTTOM
        new THREE.MeshStandardMaterial({ map: wall1frondback, color: 0x9C2C2C, side: THREE.DoubleSide}), //FRONT
        new THREE.MeshStandardMaterial({ map: wall1frondback, color: 0x9C2C2C, side: THREE.DoubleSide}), //BACK
    ];
    const wall2frondback = textureLoader.load("textures/brick.jpg");
    wall2frondback.wrapS = THREE.RepeatWrapping;
    wall2frondback.wrapT = THREE.RepeatWrapping;
    wall2frondback.repeat.set(2.26, 1);
    let materialwall2 = [

        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //LEFT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //TOP
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshStandardMaterial({ map: wall2frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //FRONT
        new THREE.MeshStandardMaterial({ map: wall2frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //BACK
    ];
    let materialwallSection = [

        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //LEFT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //TOP
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //FRONT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //BACK
    ];

    let materialpadding = new THREE.MeshStandardMaterial(
        {
        side: THREE.DoubleSide,
        roughness: 0.8,
        metalness: 0.7,
        color: 0x2f4f4f
        }
    );
    let materialgate = new THREE.MeshStandardMaterial(
        {
        side: THREE.DoubleSide,
        roughness: 0.8,
        metalness: 0.7,
        color: 0x000080
        }
    );

    let invisiblematerial = new THREE.MeshStandardMaterial(
    {
    transparent: true,
     opacity: 0.0
    });

var wall = [];

    wall.push(createWall(4, y, 0.35, 0, wallshape1, materialwall1));
    wall.push(createWall(4, 0.05, 0.35, 0, paddingshape1, materialpadding));
    wall.push(createWall(7.65, y, 6, ninetydegrees, wallshape2, materialwall2));
    wall.push(createWall(7.65, 0.05, 6, ninetydegrees, paddingshape2, materialpadding));
    wall.push(createWall(4, y, 11.65, 0, wallshape1, materialwall1));
    wall.push(createWall(4, 0.05, 11.65, 0, paddingshape1, materialpadding));
    wall.push(createWall(0.35, y, 9.7, ninetydegrees, wallSection, materialwallSection));
    wall.push(createWall(0.35, 0.05, 9.7, ninetydegrees, paddingSection, materialpadding));
    wall.push(createWall(0.35, y, 2.3, ninetydegrees, wallSection, materialwallSection));
    wall.push(createWall(0.35, 0.05, 2.3, ninetydegrees, paddingSection, materialpadding));
    wall.push(createWall(0.15, 3.45, 6, ninetydegrees, gateSection, materialgate));

    function createWall(x, y, z, rotationY, geometry, material) {
    let wall =  new THREE.Mesh(geometry, material);
    wall.receiveShadow = true;
    wall.position.x = x;
    wall.position.y = y;
    wall.position.z = z;
    wall.rotation.y = rotationY;
    return wall;
    }

 export {wall};