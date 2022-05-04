package edu.qaware.cc.reactive.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;

public class MessageCollectorActor extends UntypedAbstractActor {
    private ActorRef wikipedia;
    private ActorRef openlibrary;

    @Override
    public void preStart() throws Exception {
        wikipedia = getContext().actorOf(Props.create(WikipediaActor.class), "Wikipedia");
        openlibrary = getContext().actorOf(Props.create(OpenLibraryActor.class), "OpenLibrary");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        //TODO
    }
}
