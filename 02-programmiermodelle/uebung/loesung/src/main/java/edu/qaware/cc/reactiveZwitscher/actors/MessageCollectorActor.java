package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import java.util.ArrayList;
import java.util.List;

public class MessageCollectorActor extends UntypedActor {

    private ActorRef wikipedia;
    private ActorRef feedzilla;
    private ActorRef nytimes;

    private ActorRef caller;
    private final List<String> result = new ArrayList<String>();
    private boolean wikipediaFinished;
    private boolean feedzillaFinished;
    private boolean nyTimesFinished;

    @Override
    public void preStart() throws Exception {
        wikipedia = getContext().actorOf(Props.create(WikipediaActor.class), "Wikipedia");
        feedzilla = getContext().actorOf(Props.create(FeedzillaSupervisorActor.class), "FeedzillaSupervisor");
        nytimes = getContext().actorOf(Props.create(NYTimesActor.class), "NY-Times");

    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            caller = getSender();
            wikipedia.tell(message, self());
            //TODO: Feedzilla is currently not available
            //feedzilla.tell(message, self());
            nytimes.tell(message, self());

        } else if (message instanceof List) {
            result.addAll((List) message);
            if (getSender().equals(wikipedia)) {
                wikipediaFinished = true;
            } else if (getSender().equals(feedzilla)) {
                feedzillaFinished = true;
            } else if (getSender().equals(nytimes)) {
                nyTimesFinished = true;
            }

            //TODO:  Use this statement when feedzilla is online again.
            /*
             if (wikipediaFinished && feedzillaFinished && nyTimesFinished) {
             caller.tell(result, self());
             }
             */
            
            if (wikipediaFinished && nyTimesFinished) {
                caller.tell(result, self());
            }
        } else {
            unhandled(message);
        }
    }

}
