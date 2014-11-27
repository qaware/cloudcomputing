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
                                         "centos-7-v20141108",
                                         "vl-cc", cs);

        //(3) SSH Kommandos ausführen
        exec(node.getId(), "sudo yum -y install docker", cs);
        exec(node.getId(), "sudo service docker start", cs);
        exec(node.getId(), "sudo docker build -t cloudcomputing/nginx-node http://goo.gl/Ye6l2s", cs);
        exec(node.getId(), "sudo docker build -t cloudcomputing/haproxy-node http://goo.gl/pGkdfl", cs);
        for (int i = 1; i <= 3; i++) {
            exec(node.getId(), "sudo docker run -d -p 8" + i + ":80 --name nginx" + i + " cloudcomputing/nginx-node", cs);
        }
        exec(node.getId(), "sudo docker run -d -p 80:80 --link nginx1:nginx1 --link nginx2:nginx2 --link nginx3:nginx3 cloudcomputing/haproxy-node", cs);
        
        //(4) Daten zum erzeugten Knoten ausgeben
        System.out.println("Knoten erzeugt: " + node.getId());        
        System.out.println(node.getPublicAddresses().toString());
        
        //(5) Verbindung zur Cloud schließen
        cs.getContext().close();
    }

}
