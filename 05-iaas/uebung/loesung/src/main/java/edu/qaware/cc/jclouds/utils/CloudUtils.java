package edu.qaware.cc.jclouds.utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.IOException;
import java.util.Set;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.RunScriptOnNodesException;
import org.jclouds.compute.domain.ExecResponse;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.options.RunScriptOptions;
import org.jclouds.compute.options.TemplateOptions;
import static org.jclouds.compute.predicates.NodePredicates.inGroup;
import org.jclouds.domain.Location;
import org.jclouds.sshj.config.SshjSshClientModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Klasse bietet verschiedene statische Hilfsfunktionen zur Steuerung
 * einer Compute Cloud mit Hilfe der JClouds API.
 *
 * @author Josef Adersberger (josef.adersberger@qaware.de)
 */
public class CloudUtils {
    
    /**
     * Der Logger für alle Konsolenausgaben der Klasse
     */
    static Logger LOG = LoggerFactory.getLogger(CloudUtils.class);

    /**
     * Erzeugt eine Verbindung zu einer Compute Cloud.
     *
     * @param accountName Der Benutzername für den Zugriff auf die Cloud
     * @param accountKey Der private Schlüssel zur Authentifizierung an der Cloud
     * @param provider Der Name der zu konnektierenden Cloud (der genutzte Cloud
     * Provider)
     * @return ComputeServiceContext Der Kontext für den Zugriff auf die Compute
     * Cloud
     * @throws IOException
     */
    public static ComputeServiceContext connect(String accountName, String accountKey, String provider) throws IOException {
        //Kontext für den Zugriff auf die Compute Cloud aufbauen
        return ContextBuilder.newBuilder(provider)
                .credentials(accountName, accountKey)
                .modules(ImmutableSet.of(new SshjSshClientModule()))
                .buildApi(ComputeServiceContext.class);
    }

    /**
     * Gibt die Liste der verfügbaren Knotentypen aus.
     *
     * @param cs Die API für den Compute Service
     */
    public static void printHardwareProfiles(ComputeService cs) {
        Set<? extends Hardware> hw = cs.listHardwareProfiles();
        for (Hardware h : hw) {
            LOG.info(h.getId());
        }
    }

    /**
     * Gibt die Liste der verfügbaren Images aus.
     *
     * @param cs Die API für den Compute Service
     */
    public static void printImages(ComputeService cs) {
        Set<? extends Image> images = cs.listImages();
        for (Image i : images) {
            LOG.info(i.getId());
        }
    }
    
    /**
     * Gibt die Liste der verfügbaren Regionen aus.
     * @param cs Die API für den Compute Service
     */
    public static void printRegions(ComputeService cs) {
        Set<? extends Location> locations = cs.listAssignableLocations();
        for (Location l : locations) {
            LOG.info(l.getId());
        }
    }
    
    /**
     * Gibt die Liste der verfügbaren Images einer Betriebssystem-Familie aus
     * @param os Betriebssystem-Familie
     * @param cs Die API für den Compute Service
     * @return Die ID des Images
     */
    public static String getLastVersionImageForOs(OsFamily os, ComputeService cs) {
        Template template = cs.templateBuilder().osFamily(os).build();
        return template.getImage().getId();
    }

    /**
     * Führt ein SSH-Kommando auf einem Cloud-Knoten aus und gibt das Ergebnis
     * auf der Kommandozeile zurück.
     * Dieser Befehlt funktioniert nur dann, wenn im selben Programmablauf auch die Instanz
     * gestartet wurde, auf der das Kommando ausgeführt werden soll, da die SSH-Schlüssel
     * von JClouds nur im Speicher gehalten werden.
     * 
     * @param node Die Id des relevanten Cloud-Knoten, auf dem das Kommando ausgeführt werden soll
     * @param command Das SSH-Kommando
     * @param user Der Benutzer, mit dem sich die Shell verbindet. Falls <code>null</code>, dann "root"
     * @param fireAndForget Der Befehl wird abgesetzt ohne auf die Rückgabe zu warten
     * @param service Die API für den Compute Service
     * @throws RunScriptOnNodesException 
     */
    public static void exec(String node, String command, String user, boolean fireAndForget, 
            ComputeService service) throws RunScriptOnNodesException {
        RunScriptOptions ro = RunScriptOptions.NONE;
        if (user != null) ro = RunScriptOptions.Builder.overrideLoginUser(user);
        LOG.info(command + " on " + node + " => ");
        if (fireAndForget){
            ListenableFuture lf = service.submitScriptOnNode(node, command, ro);
            lf.cancel(false);
        } else {
            ExecResponse response = service.runScriptOnNode(node, command, ro);
            LOG.info(response.toString());
            LOG.info("<= Exit Status: " + response.getExitStatus());
        }
    }
    
    /**
     * Bringt ein Image in der Compute Cloud zur Ausführung.
     * 
     * @param location Die Zone/Region der Cloud, in der das Image instanziiert werden soll (z.B.: europe-west1-c)
     * @param hardwareProfile Der Knotentyp, auf dem das Image instanziiert werden soll (z.B.: europe-west1-c/g1-small)
     * @param image Das Image, das instanziiert werden soll (z.B.: debian-7-wheezy-v20141108)
     * @param group Die Knoten-Gruppe, in der sich die erzeugte Instanz befinden sollen (kann frei vergeben werden)
     * @param moreOptions Weitere Optionen zum Start eines Images
     * @param cs Die API für den Compute Service
     * @throws RunNodesException
     * @return Metadaten zum erzeugten Knoten mit der Image-Instanz oder null, falls kein Knoten erzeugt wurde
     */
    public static NodeMetadata launch(String location, String hardwareProfile, String image, 
                                                   String group, TemplateOptions moreOptions, 
                                                   ComputeService cs) throws RunNodesException{
        //Das Template zur Erzeugung einer Instanz erstellen
        Template template = cs.templateBuilder()
                .locationId(location)               
                .hardwareId(hardwareProfile)
                .imageId(image).build();
        if(moreOptions != null) {
            moreOptions.copyTo(template.getOptions());
        }

        //Die Instanzen erstellen und der Gruppe zuordnen
        Set<NodeMetadata> nodes = (Set<NodeMetadata>)cs.createNodesInGroup(group, 1, template);     
        for (NodeMetadata node : nodes){
            LOG.info("Started instance: " + node.toString());
        }
        if (!nodes.iterator().hasNext()) return null;
        return nodes.iterator().next();
    }
    
    /**
     * Terminiert alle Instanzen einer Gruppe.
     * 
     * @param group Der Name der Gruppe
     * @param cs Die API für den Compute Service
     */
    public static void destroy(String group, ComputeService cs){
        cs.destroyNodesMatching(inGroup(group));      
    }
}