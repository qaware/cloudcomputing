package edu.qaware.cc.reactive.actors;

import akka.actor.UntypedAbstractActor;
import akka.actor.UntypedActor;
import edu.qaware.cc.reactive.connectors.openlibrary.OpenLibraryConnector;

import java.util.List;

public class OpenLibraryActor extends UntypedAbstractActor {
     
    private OpenLibraryConnector connector = new OpenLibraryConnector();
    
    @Override
    public void onReceive(Object message) throws Exception {
        //TODO
    }
    
}
