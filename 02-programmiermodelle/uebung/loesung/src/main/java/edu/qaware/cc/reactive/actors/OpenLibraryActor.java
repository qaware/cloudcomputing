package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.UntypedActor;
import edu.qaware.cc.reactiveZwitscher.connectors.openlibrary.OpenLibraryConnector;

import java.util.List;

public class OpenLibraryActor extends UntypedActor {
     
    private OpenLibraryConnector connector = new OpenLibraryConnector();
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String){
            List<String> result = connector.getBooksWithTitleContaining((String)message);
            getSender().tell(result, self());
        } else {
            unhandled(message);
        }
    }
    
}
