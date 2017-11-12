package edu.qaware.cc.jclouds;

import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;

import static edu.qaware.cc.jclouds.utils.CloudUtils.*;


public class LaunchNginxCluster {
    private static final String NGINX_DOCKERFILE = "https://raw.githubusercontent.com/adersberger/cloudcomputing/master/04-provisionierung/uebung/loesung/nginx/Dockerfile";
    private static final String HAPROXY_DOCKERFILE = "https://raw.githubusercontent.com/adersberger/cloudcomputing/master/04-provisionierung/uebung/loesung/haproxy/Dockerfile";
   
    public static void main(String[] args) throws Exception {

        //(1) Verbindung zur Compute Cloud herstellen
        ComputeService cs = connect(
                Credentials.USER.get(), Credentials.KEY.get(), Credentials.PROVIDER.get())
                .getComputeService();

        //(2) Eine Instanz in der Compute Cloud launchen
        TemplateOptions opts = AWSEC2TemplateOptions.Builder.subnetId("subnet-7c745915"); // default subnet  eu-central-1a
        NodeMetadata node = launch("eu-central-1",             // Frankfurt
                                   "t2.small",               //Maschinentyp
                                   "eu-central-1/ami-f970ae96", //"centos-docker" Image
                                   "vl-cc",                  //Gruppe
                                    opts, cs);

        //(3) SSH Kommandos ausführen      
        final String shellUser = "centos";
        //Einen Docker-Daemon auf dem Ziel-Knoten starten
        exec(node.getId(), "sudo docker daemon -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock", shellUser, true, cs);
        
        //Die Docker-Container starten
        exec(node.getId(), "sudo docker build -t cloudcomputing/nginx " + NGINX_DOCKERFILE, shellUser, false, cs);
        exec(node.getId(), "sudo docker build -t cloudcomputing/haproxy " + HAPROXY_DOCKERFILE, shellUser, false, cs);
        for (int i = 1; i <= 3; i++) {
            exec(node.getId(), "sudo docker run -d -p 8" + i + ":80 --name web" + i + " cloudcomputing/nginx", shellUser, false, cs);
        }
        exec(node.getId(), "sudo docker run -d -p 80:80 --link web1:nginx1 --link web2:nginx2 --link web3:nginx3 cloudcomputing/haproxy", shellUser, false, cs);
        
        //(4) Daten zum erzeugten Knoten ausgeben
        System.out.println("Knoten erzeugt: " + node.getId() 
                + " mit IP: " + node.getPublicAddresses().toString());        
        
        //(5) Verbindung zur Cloud schließen
        cs.getContext().close();
    }

}