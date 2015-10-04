package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.UntypedActor;
import edu.qaware.cc.reactiveZwitscher.connectors.feedzilla.FeedzillaConnector;
import edu.qaware.cc.reactiveZwitscher.connectors.feedzilla.NewsCategory;
import java.util.List;

public class FeedzillaActor extends UntypedActor {
    
    public static class FeedzillaRequestMessage {
        private String term;
        private NewsCategory category;

        public FeedzillaRequestMessage(String term, NewsCategory category) {
            this.term = term;
            this.category = category;
        }

        public String getTerm() {
            return term;
        }

        public NewsCategory getCategory() {
            return category;
        }
        
    }

    private FeedzillaConnector connector = new FeedzillaConnector();
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof FeedzillaRequestMessage){
            //TODO
        } else {
            unhandled(message);
        }
    }
    
}
