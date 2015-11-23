package edu.qaware.cc.jclouds;

import static edu.qaware.cc.jclouds.utils.CloudUtils.*;
import java.io.IOException;
import org.jclouds.compute.ComputeService;

/**
 * Gibt die Liste der verfügbaren Hardware-Profile aus.
 */
public class ListHardware {
    public static void main(String[] args) throws IOException {
        ComputeService cs = connect(
                Credentials.USER.get(), Credentials.KEY.get(), Credentials.PROVIDER.get())
                .getComputeService();
        
        //********************************************************************
        //TODO
        //********************************************************************
        
        cs.getContext().close();
    }
}