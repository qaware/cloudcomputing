package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import java.util.ArrayList;
import java.util.List;

public class MessageCollectorActor extends UntypedActor {

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
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            caller = getSender();
            wikipedia.tell(message, self());
            openlibrary.tell(message, self());

        } else if (message instanceof List) {
            result.addAll((List) message);
            if (getSender().equals(wikipedia)) {
                wikipediaFinished = true;
            } else if (getSender().equals(openlibrary)) {
                openlibraryFinished = true;
            }
            if (wikipediaFinished && openlibraryFinished) {
                caller.tell(result, self());
            }
        } else {
            unhandled(message);
        }
    }

}
