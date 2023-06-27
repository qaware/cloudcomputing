const SIZE_FACTOR = 50;
const KM = 1_000;
const EARH_RADIUS = 6_371 * KM;

const SCALE_FACTOR = SIZE_FACTOR / EARH_RADIUS;

const scene = new THREE.Scene();
const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);

const renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

$.get("/satellite/25544", result => {

    const material = new THREE.MeshNormalMaterial({color: 0x00ff00});

    const earth = new THREE.SphereGeometry(EARH_RADIUS * SCALE_FACTOR, 128, 128);
    const earthMesh = new THREE.Mesh(earth, material);
    // earthMesh.position.z = 0;

    const orbit = new THREE.TorusGeometry((EARH_RADIUS + 300 * KM) * SCALE_FACTOR, 0.1, 300, 300, 3.14 * 2);
    const orbitMesh = new THREE.Mesh(orbit, material);
    // orbitMesh.position.z = 0;

    const satellite = new THREE.SphereGeometry(50 * KM * SCALE_FACTOR, 128, 128);
    const satelliteMesh = new THREE.Mesh(satellite, material);
    // satelliteMesh.position.x = (EARH_RADIUS + 2000 * KM) * SCALE_FACTOR;
    // satelliteMesh.position.y = (EARH_RADIUS + 5000 * KM) * SCALE_FACTOR;
    satelliteMesh.position.z = (EARH_RADIUS + result.altitude * KM) * SCALE_FACTOR;

    console.log(result);
    console.log(satelliteMesh.position.z);

// earthMesh.material.side = THREE.DoubleSide;

    scene.add(earthMesh);
    scene.add(orbitMesh);
    scene.add(satelliteMesh);

    camera.position.z = 150;

// scene.rotation.y = 3.14 / 2;

    function animate() {
        requestAnimationFrame(animate);
        earthMesh.rotation.x += 0.01;
        earthMesh.rotation.y += 0.01;

        orbitMesh.rotation.x += 0.01;
        // orbitMesh.rotation.y += 0.01;

        renderer.render(scene, camera);
    }

    animate();

});
