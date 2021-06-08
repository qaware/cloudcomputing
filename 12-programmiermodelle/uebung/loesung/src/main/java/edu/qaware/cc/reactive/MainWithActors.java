package edu.qaware.cc.reactive;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import edu.qaware.cc.reactive.actor.MessageCollectorActor;
import edu.qaware.cc.reactive.message.SearchMessage;
import edu.qaware.cc.reactive.message.SearchResultMessage;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static akka.japi.Util.classTag;
import static akka.pattern.Patterns.ask;

/**
 * Sammelt die Nachrichten über ein akka Aktorensystem zusammen
 */
public class MainWithActors {
    public static void main(String[] args) throws Exception {
        ActorSystem actorSystem = ActorSystem.create("akka-demo");
        ActorRef messageCollector = actorSystem.actorOf(Props.create(MessageCollectorActor.class), "Message-Collector");

        long start = System.nanoTime();
        Timeout timeout = new Timeout(30, TimeUnit.SECONDS);

        // Send a SearchMessage to MessageCollectorActor and wait for the SearchResultMessage
        Future<SearchResultMessage> resultFuture = ask(messageCollector, new SearchMessage("Reactive"), timeout).mapTo(classTag(SearchResultMessage.class));
        SearchResultMessage resultMessage = Await.result(resultFuture, timeout.duration());

        System.out.println(resultMessage.getResults().size() + " items found");
        for (String message : resultMessage.getResults()) {
            System.out.println(message);
        }

        System.out.println("Duration to collect results (reactive): " + Duration.ofNanos(System.nanoTime() - start).toMillis() + " ms");

        Await.ready(actorSystem.terminate(), scala.concurrent.duration.Duration.Inf());
    }
}