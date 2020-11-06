
let y = 2;
    const textureLoader = new THREE.TextureLoader();
    let ninetydegrees = Math.PI/2;
    let wallshape1 = new THREE.BoxGeometry(7.599, 4, 0.302);
    let paddingshape1 = new THREE.BoxGeometry(7.7, 0.2, 0.4);
    let wallshape2 = new THREE.BoxGeometry(11.6, 4, 0.3);
    let paddingshape2 = new THREE.BoxGeometry(11, 0.2, 0.4);
    let wallSection = new THREE.BoxGeometry(4.201, 4, 0.3);
    let paddingSection = new THREE.BoxGeometry(4, 0.2, 0.4)
    let gateSection = new THREE.BoxGeometry(3.2, 1, 0.5);

    const wall1frondback = textureLoader.load("textures/brick.jpg");
    wall1frondback.wrapS = THREE.RepeatWrapping;
    wall1frondback.wrapT = THREE.RepeatWrapping;
    wall1frondback.repeat.set(2, 1);

    let materialwall1 = [

        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //LEFT
        new THREE.MeshStandardMaterial({ map: textureLoader.load("textures/brick.jpg"), color: 0x9C2C2C, side: THREE.DoubleSide }), //RIGHT
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //TOP
        new THREE.MeshStandardMaterial({ color: 0x9C2C2C, side: THREE.DoubleSide }), //BOTTOM
        new THREE.MeshStandardMaterial({ map: wall1frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //FRONT
        new THREE.MeshStandardMaterial({ map: wall1frondback, color: 0x9C2C2C, side: THREE.DoubleSide }), //BACK
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
    let wall1 = new THREE.Mesh(wallshape1, materialwall1);
    wall1.position.y = y;
    wall1.position.z = 0.35;
    wall1.position.x = 4;
    wall1.receiveShadow = true;

    let padding = new THREE.Mesh(paddingshape1, materialpadding)
    padding.position.y = 0.1;
    padding.position.z = 0.35;
    padding.position.x = 4;
    padding.receiveShadow = true;

    let wall2 = new THREE.Mesh(wallshape2, materialwall2)
    wall2.position.y = y;
    wall2.position.x = 7.65;
    wall2.position.z = 6;
    wall2.rotation.y = ninetydegrees;
    wall2.receiveShadow = true;

    let padding2 = new THREE.Mesh(paddingshape2, materialpadding)
    padding2.position.y = 0.1;
    padding2.position.x = 7.65;
    padding2.position.z = 6;
    padding2.rotation.y = ninetydegrees;
    padding2.receiveShadow = true;

    let wall3 = new THREE.Mesh(wallshape1, materialwall1)
    wall3.position.y = y;
    wall3.position.z = 11.65
    wall3.position.x = 4;
    wall3.receiveShadow = true;

    let padding3 = new THREE.Mesh(paddingshape1, materialpadding)
    padding3.position.y = 0.1;
    padding3.position.z = 11.65;
    padding3.position.x = 4;
    padding3.receiveShadow = true;

    let tinywall1 = new THREE.Mesh(wallSection, materialwallSection)
    tinywall1.position.y = y;
    tinywall1.position.x = 0.35;
    tinywall1.position.z = 9.7;
    tinywall1.rotation.y = ninetydegrees;
    tinywall1.receiveShadow = true;

    let paddingtinywall1 = new THREE.Mesh(paddingSection, materialpadding)
    paddingtinywall1.position.y = 0.1;
    paddingtinywall1.position.x = 0.35;
    paddingtinywall1.position.z = 9.8;
    paddingtinywall1.rotation.y = ninetydegrees;
    paddingtinywall1.receiveShadow = true;

    let tinywall2 = new THREE.Mesh(wallSection, materialwallSection)
    tinywall2.position.y = y;
    tinywall2.position.x = 0.35;
    tinywall2.position.z = 2.3;
    tinywall2.rotation.y = ninetydegrees;
    tinywall2.receiveShadow = true;

    let paddingtinywall2 = new THREE.Mesh(paddingSection, materialpadding)
    paddingtinywall2.position.y = 0.1;
    paddingtinywall2.position.x = 0.35;
    paddingtinywall2.position.z = 2.4;
    paddingtinywall2.rotation.y = ninetydegrees;
    paddingtinywall2.receiveShadow = true;

    let gate = new THREE.Mesh(gateSection, materialgate)
    gate.position.y = 3.5;
    gate.position.z = 6;
    gate.position.x = 0.15;
    gate.rotation.y = ninetydegrees;
    gate.receiveShadow = true;

 export {wall1, padding ,wall2, padding2, wall3, padding3, tinywall1, paddingtinywall1, tinywall2, paddingtinywall2, gate};