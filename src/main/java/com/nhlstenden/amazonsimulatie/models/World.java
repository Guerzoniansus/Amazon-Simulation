package com.nhlstenden.amazonsimulatie.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*
 * Deze class is een versie van het model van de simulatie. In dit geval is het
 * de 3D wereld die we willen modelleren (magazijn). De zogenaamde domain-logic,
 * de logica die van toepassing is op het domein dat de applicatie modelleerd, staat
 * in het model. Dit betekent dus de logica die het magazijn simuleert.
 */
public class World implements Model {
    /*
     * De wereld bestaat uit objecten, vandaar de naam worldObjects. Dit is een lijst
     * van alle objecten in de 3D wereld. Deze objecten zijn in deze voorbeeldcode alleen
     * nog robots. Er zijn ook nog meer andere objecten die ook in de wereld voor kunnen komen.
     * Deze objecten moeten uiteindelijk ook in de lijst passen (overerving). Daarom is dit
     * een lijst van Object3D onderdelen. Deze kunnen in principe alles zijn. (Robots, vrachrtwagens, etc)
     */
    private List<Object3D> worldObjects;
    private final Graph graph;
    private Warehouse warehouse;

    /**
     * We don't want every class to have direct access to worldObjects.
     * However, important classes (such as Warehouse) can access it through a WorldEditor if needed.
     */
    private final WorldEditor worldEditor;


    /*
     * Dit onderdeel is nodig om veranderingen in het model te kunnen doorgeven aan de controller.
     * Het systeem werkt al as-is, dus dit hoeft niet aangepast te worden.
     */
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /*
     * De wereld maakt een lege lijst voor worldObjects aan. Daarin wordt nu één robot gestopt.
     * Deze methode moet uitgebreid worden zodat alle objecten van de 3D wereld hier worden gemaakt.
     */
    public World() {
        worldEditor = new WorldEditor();

        SceneBuilder sceneBuilder = new AmazonSceneBuilder(this);

        graph = sceneBuilder.getGraph();
        worldObjects = sceneBuilder.getObjects();

        warehouse = new Warehouse(this, worldEditor);
    }

    /*
     * Deze methode wordt gebruikt om de wereld te updaten. Wanneer deze methode wordt aangeroepen,
     * wordt op elk object in de wereld de methode update aangeroepen. Wanneer deze true teruggeeft
     * betekent dit dat het onderdeel daadwerkelijk geupdate is (er is iets veranderd, zoals een positie).
     * Als dit zo is moet dit worden geupdate, en wordt er via het pcs systeem een notificatie gestuurd
     * naar de controller die luisterd. Wanneer de updatemethode van het onderdeel false teruggeeft,
     * is het onderdeel niet veranderd en hoeft er dus ook geen signaal naar de controller verstuurd
     * te worden.
     */
    @Override
    public void update() {
        // A new proxy list is made to prevent concurrent modification exception
        for (Object3D object : new ArrayList<Object3D>(this.worldObjects)) {

            if (object.isReadyToDie()) {
                deleteObject(object);
                continue;
            }

            if(object instanceof Updatable) {
                if (((Updatable)object).update()) {
                    pcs.firePropertyChange(Model.UPDATE_COMMAND, null, new ProxyObject3D(object));
                }
            }
        }
    }

    /*
     * Standaardfunctionaliteit. Hoeft niet gewijzigd te worden.
     */
    @Override
    public void addObserver(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /*
     * Deze methode geeft een lijst terug van alle objecten in de wereld. De lijst is echter wel
     * van ProxyObject3D objecten, voor de veiligheid. Zo kan de informatie wel worden gedeeld, maar
     * kan er niks aangepast worden.
     */
    @Override
    public List<Object3D> getWorldObjectsAsProxyList() {
        ArrayList<Object3D> returnList = new ArrayList<>();

        for(Object3D object : this.worldObjects) {
            returnList.add(new ProxyObject3D(object));
        }

        return returnList;
    }

    /**
     * Gets this world's graph
     * @return The Graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Get this world's Warehouse
     * @return The warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Delete an object from the world
     * @param object The object to delete
     */
    private void deleteObject(Object3D object) {
        if (worldObjects.contains(object)) {
            worldObjects.remove(object);
        }

        pcs.firePropertyChange(Model.DELETE_COMMAND, null, new ProxyObject3D(object));
    }

    /**
     * Create a new object in the world
     * @param object The object to create
     */
    private void createObject(Object3D object) {
        worldObjects.add(object);
        pcs.firePropertyChange(Model.CREATE_COMMAND, null, new ProxyObject3D(object));
    }

    /**
     * Having an instance of this class allows for encapsulated editing of the world.
     * Recommended to only give to important classes that need direct access to the world objects.
     */
    public class WorldEditor {

        /**
         * Returns the list of actual world objects. This list can be edited, so only important classes
         * should have access to WorldEditor.
         * @return A list of world objects
         */
        public List<Object3D> getWorldObjects() {
            return worldObjects;
        }

        /**
         * Delete an object from the world
         * @param object The object to delete
         */
        public void deleteObject(Object3D object) {
            World.this.deleteObject(object);
        }

        /**
         * Add an object to the world
         * @param object The Object3D instance to add
         */
        public void createObject(Object3D object) {
            World.this.createObject(object);
        }
    }
}