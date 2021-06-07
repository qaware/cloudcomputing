package edu.qaware.cc.reactive.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

import java.util.ArrayList;
import java.util.List;

public class MessageCollectorActor extends UntypedAbstractActor {
    private ActorRef wikipedia;
    private ActorRef openlibrary;

    private ActorRef caller;
    private final List<String> result = new ArrayList<String>();
    private boolean wikipediaFinished;
    private boolean openlibraryFinished;

    @Override
    public void preStart() throws Exception {
        wikipedia = getContext().actorOf(Props.create(WikipediaActor.class), "Wikipedia");
        openlibrary = getContext().actorOf(Props.create(OpenLibraryActor.class), "OpenLibrary");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            result.clear();
            caller = getSender();

            // Start wikipedia & openLibrary
            wikipedia.tell(message, self());
            openlibrary.tell(message, self());
        } else if (message instanceof List) {
            // Collect results
            result.addAll((List<String>) message);

            if (getSender().equals(wikipedia)) {
                wikipediaFinished = true;
            } else if (getSender().equals(openlibrary)) {
                openlibraryFinished = true;
            }
            // Both have finished, respond to caller
            if (wikipediaFinished && openlibraryFinished) {
                caller.tell(result, self());
            }
        } else {
            unhandled(message);
        }
    }
}
