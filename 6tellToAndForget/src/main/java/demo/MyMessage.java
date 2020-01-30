package demo;

import akka.actor.ActorRef;

public class MyMessage{
    
    private String message;
    private ActorRef receiver;

    public MyMessage(String m, ActorRef r){
        message = m;
        receiver = r;
    }

    public String getMessage(){
        return message;
    }

    public ActorRef getReceiver(){
        return receiver;
    }
}