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
                "844448415254-61r7t0e6skchhm2hr8r33c2ni77v5gpk@developer.gserviceaccount.com",
                "vl-cc.pem", "google-compute-engine").getComputeService();        
        CloudUtils.printImages(cs);
        cs.getContext().close();
    }
 
}
