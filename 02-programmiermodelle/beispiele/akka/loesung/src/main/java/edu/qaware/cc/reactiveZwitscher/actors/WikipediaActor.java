package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.UntypedActor;
import edu.qaware.cc.reactiveZwitscher.connectors.wikipedia.WikipediaConnector;

public class WikipediaActor extends UntypedActor {

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
