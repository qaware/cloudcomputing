package edu.qaware.cc.reactive.actor;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import edu.qaware.cc.reactive.message.SearchMessage;
import edu.qaware.cc.reactive.message.SearchResultMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageCollectorActor extends UntypedAbstractActor {
    private ActorRef wikipedia;
    private ActorRef openlibrary;

    private ActorRef caller;
    private final List<String> result = new ArrayList<>();
    private boolean wikipediaFinished;
    private boolean openlibraryFinished;

    @Override
    public void preStart() {
        wikipedia = getContext().actorOf(Props.create(WikipediaActor.class), "Wikipedia");
        openlibrary = getContext().actorOf(Props.create(OpenLibraryActor.class), "OpenLibrary");
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof SearchMessage) {
            // This message is sent from MainWithActors
            handleSearchMessage((SearchMessage) message);
        } else if (message instanceof SearchResultMessage) {
            // This message is sent from OpenLibraryActor and WikipediaActor
            handleSearchResultMessage((SearchResultMessage) message);
        } else {
            unhandled(message);
        }
    }

    private void handleSearchResultMessage(SearchResultMessage message) {
        // Collect results
        result.addAll(message.getResults());

        if (getSender().equals(wikipedia)) {
            wikipediaFinished = true;
        } else if (getSender().equals(openlibrary)) {
            openlibraryFinished = true;
        }

        // Both have finished, respond to caller with the aggregated results
        if (wikipediaFinished && openlibraryFinished) {
            caller.tell(new SearchResultMessage(result), self());
        }
    }

    private void handleSearchMessage(SearchMessage message) {
        // Clean up old results
        result.clear();

        caller = getSender();

        // Call wikipedia & openLibrary
        wikipedia.tell(message, self());
        openlibrary.tell(message, self());
    }
}
