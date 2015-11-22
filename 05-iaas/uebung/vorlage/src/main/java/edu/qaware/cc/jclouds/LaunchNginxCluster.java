package edu.qaware.cc.jclouds;

import static edu.qaware.cc.jclouds.utils.CloudUtils.connect;
import static edu.qaware.cc.jclouds.utils.CloudUtils.exec;
import static edu.qaware.cc.jclouds.utils.CloudUtils.launch;
import java.io.IOException;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.RunScriptOnNodesException;
import org.jclouds.compute.domain.NodeMetadata;


public class LaunchNginxCluster {

    public static void main(String[] args) throws IOException, RunNodesException, RunScriptOnNodesException {

        //(1) Verbindung zur Compute Cloud herstellen
        ComputeService cs = connect(
                "844448415254-61r7t0e6skchhm2hr8r33c2ni77v5gpk@developer.gserviceaccount.com",
                "vl-cc.pem", "google-compute-engine").getComputeService();

        //(2) Eine Instanz in der Compute Cloud launchen
        NodeMetadata node = launch("europe-west1-c", 
                                         "europe-west1-c/g1-small", 
        //********************************************************************
        //TODO: Folgend ein aktuelles CentOS Image wählen
        //********************************************************************
                                         "<<TODO>>", 
                                         "vl-cc", cs);        

        //(3) SSH Kommandos ausführen
        exec(node.getId(), "sudo yum -y install docker", cs);
        exec(node.getId(), "sudo service docker start", cs);   
        
        //********************************************************************
        //TODO: Kommandos entsprechend der Übung zur Provisionierung absetzen
        //Hinweis: Allen Kommandos muss "sudo" vorgestellt werden, 
        //damit diese privilegiert ausgeführt werden können
        //********************************************************************
        
        //(4) Daten zum erzeugten Knoten ausgeben
        System.out.println("Knoten erzeugt: " + node.getId());        
        System.out.println(node.getPublicAddresses().toString());
        
        //(5) Verbindung zur Cloud schließen
        cs.getContext().close();
    }

}
