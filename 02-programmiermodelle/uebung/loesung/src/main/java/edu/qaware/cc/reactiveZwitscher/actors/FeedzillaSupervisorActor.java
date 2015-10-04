package edu.qaware.cc.reactiveZwitscher.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import edu.qaware.cc.reactiveZwitscher.actors.FeedzillaActor.FeedzillaRequestMessage;
import edu.qaware.cc.reactiveZwitscher.connectors.feedzilla.NewsCategory;
import java.util.ArrayList;
import java.util.List;

public class FeedzillaSupervisorActor extends UntypedActor {

    private static final NewsCategory[] CATEGORIES = new NewsCategory[]{
        NewsCategory.BLOGS,
        NewsCategory.IT,
        NewsCategory.PROGRAMMING,
        NewsCategory.SCIENCE
    };

    private int resultCounter;
    private List<String> news = new ArrayList<String>();
    private ActorRef caller;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            caller = getSender();
            for (NewsCategory category : CATEGORIES) {
                getContext().actorOf(Props.create(FeedzillaActor.class),
                        "Feedzilla-" + category.getCategoryId())
                        .tell(new FeedzillaRequestMessage((String) message, category), self());
            }
        } else if (message instanceof List) {
            news.addAll((List)message);
            resultCounter++;
            if (resultCounter == CATEGORIES.length)
                caller.tell(news, self());
        } else {
            unhandled(message);
        }
    }
}