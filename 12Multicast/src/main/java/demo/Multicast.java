package demo;
import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class Multicast {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		final ActorSystem system = ActorSystem.create("system");
		final ActorRef multicaster = system.actorOf(Multicaster.createActor(), "multicaster");
		final ActorRef sender = system.actorOf(Sender.createActor(multicaster), "sender");
	    final ActorRef receiver1 = system.actorOf(Receiver.createActor(), "receiver1");
		final ActorRef receiver2 = system.actorOf(Receiver.createActor(), "receiver2");
		final ActorRef receiver3 = system.actorOf(Receiver.createActor(), "receiver3");
		
		ArrayList<ActorRef> g1 = new ArrayList<ActorRef>();
		g1.add(receiver1);
		g1.add(receiver2);
		ArrayList<ActorRef> g2 = new ArrayList<ActorRef>();
		g2.add(receiver2);
		g2.add(receiver3);
		Group group1 = new Group("group1", g1);
		Group group2 = new Group("group2", g2);
		sender.tell(group1, ActorRef.noSender());
		sender.tell(group2, ActorRef.noSender());
		
		Message m1 = new Message("hello", "group1");
		Message m2 = new Message("world", "group2");
		sender.tell(m1, ActorRef.noSender());
		try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}
		sender.tell(m2, ActorRef.noSender());

	    // We wait 5 seconds before ending system (by default)
	    // But this is not the best solution.
	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}
}
