package edu.qaware.cc.reactive.actor;

import akka.actor.UntypedAbstractActor;
import edu.qaware.cc.reactive.connector.wikipedia.WikipediaConnector;
import edu.qaware.cc.reactive.message.SearchMessage;
import edu.qaware.cc.reactive.message.SearchResultMessage;

import java.util.List;

public class WikipediaActor extends UntypedAbstractActor {
    private final WikipediaConnector connector = new WikipediaConnector();

    @Override
    public void onReceive(Object message) {
        if (message instanceof SearchMessage) {
            // This message is sent from MessageCollectorActor
            handleSearchMessage((SearchMessage) message);
        } else {
            unhandled(message);
        }
    }

    private void handleSearchMessage(SearchMessage message) {
        List<String> results = connector.getArticleTitlesFor(message.getTerm());

        getSender().tell(new SearchResultMessage(results), self());
    }
}
