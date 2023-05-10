package edu.qaware.cc.reactive.actors;

import akka.actor.UntypedAbstractActor;
import edu.qaware.cc.reactive.connectors.wikipedia.WikipediaConnector;

public class WikipediaActor extends UntypedAbstractActor {

    private final WikipediaConnector connector = new WikipediaConnector();

    @Override
    public void onReceive(Object message) throws Exception {
        //TODO
    }
}
