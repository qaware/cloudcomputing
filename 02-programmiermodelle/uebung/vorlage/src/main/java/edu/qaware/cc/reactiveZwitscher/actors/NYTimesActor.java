package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.UntypedActor;
import edu.qaware.cc.reactiveZwitscher.connectors.nytimes.NYTimesConnector;

public class NYTimesActor extends UntypedActor {
     
    private NYTimesConnector connector = new NYTimesConnector();
    
    @Override
    public void onReceive(Object message) throws Exception {
      //TODO
    }
    
}
