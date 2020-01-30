package demo;

import akka.actor.ActorRef;
import java.util.ArrayList;

public class Group {
    public String name;
    public ArrayList<ActorRef> receivers;

    public Group(String n, ArrayList<ActorRef> r){
        name = n;
        receivers = r;
    }
}