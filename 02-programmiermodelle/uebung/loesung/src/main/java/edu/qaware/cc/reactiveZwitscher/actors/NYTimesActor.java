package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.UntypedActor;
import edu.qaware.cc.reactiveZwitscher.connectors.nytimes.NYTimesConnector;
import java.util.List;

public class NYTimesActor extends UntypedActor {
     
    private NYTimesConnector connector = new NYTimesConnector();
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String){
            List<String> result = connector.getArticleTitlesFor((String)message);
            getSender().tell(result, self());
        } else {
            unhandled(message);
        }
    }
    
}
