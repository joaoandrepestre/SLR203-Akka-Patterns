package demo;

import akka.actor.ActorRef;
import java.util.ArrayList;

public class Message{
    public String data;
    public ArrayList<ActorRef> senders;

    public Message(String m){
        data = m;
        senders = new ArrayList<ActorRef>();
    }

    public void addSender(ActorRef sender){
        senders.add(sender);
    }
}