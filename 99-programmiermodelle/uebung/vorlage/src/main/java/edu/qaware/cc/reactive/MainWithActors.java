package edu.qaware.cc.reactive;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.util.Timeout;
import edu.qaware.cc.reactive.actors.MessageCollectorActor;
import scala.concurrent.Await;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.japi.Util.classTag;
import static akka.pattern.Patterns.ask;

/**
 * Sammelt die Nachrichten über ein akka Aktorensystem zusammen
 */
public class MainWithActors {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        ActorSystem actorSystem = ActorSystem.create("Reactive");
        ActorRef messageCollector = actorSystem.actorOf(Props.create(MessageCollectorActor.class), "Message-Collector");

        long start = System.nanoTime();
        Timeout timeout = new Timeout(30, TimeUnit.SECONDS);
        var resultFuture = ask(messageCollector, "Reactive", timeout).mapTo(classTag(List.class));
        List<String> result = (List<String>) Await.result(resultFuture, timeout.duration());

        System.out.println(result.size() + " items found");
        for (String message : result) {
            System.out.println(message);
        }

        System.out.println("Duration to collect results (reactive): " + Duration.ofNanos(System.nanoTime() - start).toMillis() + " ms");

        Await.ready(actorSystem.terminate(), scala.concurrent.duration.Duration.Inf());
    }
}