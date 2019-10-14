package edu.qaware.cc.jclouds;

import static edu.qaware.cc.jclouds.utils.CloudUtils.connect;
import static edu.qaware.cc.jclouds.utils.CloudUtils.exec;
import static edu.qaware.cc.jclouds.utils.CloudUtils.launch;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;


public class LaunchEC2Instance {

    public static void main(String[] args) throws Exception {

        //(1) Verbindung zur Compute Cloud herstellen
        ComputeService cs = connect(
                Credentials.USER.get(), Credentials.KEY.get(), Credentials.PROVIDER.get())
                .getComputeService();

        //(2) Eine Instanz in der Compute Cloud launchen
        TemplateOptions opts = AWSEC2TemplateOptions.Builder.subnetId("subnet-7c745915");
        NodeMetadata node = launch("eu-central-1",              // Frankfurt
                                   "t2.micro",                  //Maschinentyp
                                   "eu-central-1/ami-97e953f8", //"Ubuntu 16.04 LTS" Image
                                   "vl-cc",                     //Sicherheitsgruppe
                                   opts, cs);

        //(3) Daten zum erzeugten Knoten ausgeben
        System.out.println("Knoten erzeugt: " + node.getId()
               + " mit IP: " + node.getPublicAddresses().toString());

        //(4) SSH Kommandos ausführen
        // exec(node.getId(), "sudo whoami", null, true, cs);

        //(5) Verbindung zur Cloud schließen
        cs.getContext().close();
    }

}
