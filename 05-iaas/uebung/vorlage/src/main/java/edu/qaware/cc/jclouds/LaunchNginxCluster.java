package edu.qaware.cc.jclouds;

import static edu.qaware.cc.jclouds.utils.CloudUtils.connect;
import static edu.qaware.cc.jclouds.utils.CloudUtils.exec;
import static edu.qaware.cc.jclouds.utils.CloudUtils.launch;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;


public class LaunchNginxCluster {
    private static final String NGINX_DOCKERFILE = "https://raw.githubusercontent.com/adersberger/cloudcomputing/master/04-provisionierung/uebung/loesung/nginx/Dockerfile";
    private static final String HAPROXY_DOCKERFILE = "https://raw.githubusercontent.com/adersberger/cloudcomputing/master/04-provisionierung/uebung/loesung/haproxy/Dockerfile";
   
    public static void main(String[] args) throws Exception {

        //(1) Verbindung zur Compute Cloud herstellen
        ComputeService cs = connect(
                Credentials.USER.get(), Credentials.KEY.get(), Credentials.PROVIDER.get())
                .getComputeService();

        //(2) Eine Instanz in der Compute Cloud launchen
        TemplateOptions opts = AWSEC2TemplateOptions.Builder.subnetId("subnet-73da6b4e");
        NodeMetadata node = launch("us-east-1",
                                   "t2.small", 
                                   "us-east-1/ami-5400463e",
                                   "vl-cc", opts, cs);

        //(3) SSH Kommandos ausführen      
        final String shellUser = "centos";
        
        //Einen Docker-Daemon auf dem Ziel-Knoten starten
        exec(node.getId(), "sudo docker daemon -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock", shellUser, true, cs);
        
        //********************************************************************
        //TODO: Kommandos entsprechend der Übung zur Provisionierung absetzen
        //Hinweise: 
        // * Allen Kommandos muss "sudo" vorgestellt werden, 
        //   damit diese privilegiert ausgeführt werden.
        // * Die Container-Namen "nginx1", "nginx2" und "nginx3" sind bereits
        //   vergeben. Wählen sie andere Container-Namen, behalten sie die Aliase
        //   beim Linking aber bei.
        //********************************************************************
        
        //(4) Daten zum erzeugten Knoten ausgeben
        System.out.println("Knoten erzeugt: " + node.getId() 
                + " mit IP: " + node.getPublicAddresses().toString());        
        
        //(5) Verbindung zur Cloud schließen
        cs.getContext().close();
    }

}