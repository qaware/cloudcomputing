package edu.qaware.cc.reactive.actors;

import akka.actor.UntypedAbstractActor;
import edu.qaware.cc.reactive.connectors.openlibrary.OpenLibraryConnector;

import java.util.Set;

public class OpenLibraryActor extends UntypedAbstractActor {
    private final OpenLibraryConnector connector = new OpenLibraryConnector();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            Set<String> result = connector.getBooksWithTitleContaining((String) message);
            getSender().tell(result, self());
        } else {
            unhandled(message);
        }
    }

}
