package edu.qaware.cc.reactiveZwitscher;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import static akka.japi.Util.classTag;
import static akka.pattern.Patterns.ask;
import akka.util.Timeout;
import edu.qaware.cc.reactiveZwitscher.actors.MessageCollectorActor;
import java.util.List;
import java.util.concurrent.TimeUnit;
import scala.concurrent.Await;
import scala.concurrent.Future;

/**
 * Sammelt die Nachrichten über ein akka Aktorensystem zusammen
 * 
 * @author Josef Adersberger
 */
public class MainWithActors {
    
    public static void main(String[] args) throws Exception {
        
        ActorSystem actorSystem = ActorSystem.create("Zwitscher");
        ActorRef messageCollector = 
                actorSystem.actorOf(Props.create(MessageCollectorActor.class), "Message-Collector");
        
        long start = System.currentTimeMillis();
        Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
        Future resultFuture = ask(messageCollector, "Java", timeout)
                              .mapTo(classTag(List.class));
        List<String> result = (List<String>)Await.result(resultFuture, timeout.duration());
        
        System.out.println(result.size() + " Items found");
        for (String message : result){
            System.out.println(message);
        }

        System.out.println( "Duration to collect Zwitschers (akka): " + (System.currentTimeMillis() - start) + " ms");   
        
        actorSystem.shutdown();
        actorSystem.awaitTermination();
        
    }
    
}