package edu.qaware.cc.akka;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {

    public static enum Msg {
        GREETME,
        THANKS
    }
    
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Msg.GREETME){
            getSender().tell("Hallo Welt!", getSelf());
        } else if (message == Msg.THANKS){
            System.out.println(getSender().toString() + " - gern geschehen");
        } 
        else {
            unhandled(message);
        }
    }
    
}
