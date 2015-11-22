package edu.qaware.cc.jclouds;

import edu.qaware.cc.jclouds.utils.CloudUtils;
import static edu.qaware.cc.jclouds.utils.CloudUtils.connect;
import java.io.IOException;
import org.jclouds.compute.ComputeService;

/**
 * Gibt die Liste der verfügbaren Images aus.
 */
public class ListImages {
    public static void main(String[] args) throws IOException {        
        ComputeService cs = connect(
                Credentials.SERVICE_USER.get(),
                Credentials.CERT_FILE.get(), "google-compute-engine").getComputeService();     
        CloudUtils.printImages(cs);
        cs.getContext().close();
    }
 
}
