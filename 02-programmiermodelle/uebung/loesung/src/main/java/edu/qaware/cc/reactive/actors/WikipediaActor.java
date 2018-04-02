package edu.qaware.cc.reactive.actors;

import akka.actor.UntypedAbstractActor;
import akka.actor.UntypedActor;
import edu.qaware.cc.reactive.connectors.wikipedia.WikipediaConnector;

public class WikipediaActor extends UntypedAbstractActor {

    private WikipediaConnector connector = new WikipediaConnector();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            getSender().tell(connector.getArticleTitlesFor((String) message), self());
        } else {
            unhandled(message);
        }
    }
}
