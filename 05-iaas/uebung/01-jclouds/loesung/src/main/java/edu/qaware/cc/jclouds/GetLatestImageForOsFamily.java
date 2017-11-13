package edu.qaware.cc.jclouds;

import edu.qaware.cc.jclouds.utils.CloudUtils;
import static edu.qaware.cc.jclouds.utils.CloudUtils.connect;
import java.io.IOException;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.OsFamily;

/**
 * Gibt die aktuellste Image-Version einer Betriebssystem-Familie zurück
 */
public class GetLatestImageForOsFamily {

    public static void main(String[] args) throws IOException {
        ComputeService cs = connect(
                Credentials.USER.get(), Credentials.KEY.get(), Credentials.PROVIDER.get())
                .getComputeService();
        String imageId = CloudUtils.getLastVersionImageForOs(OsFamily.UBUNTU, cs);
        System.out.println(imageId);
        cs.getContext().close();
    }

}