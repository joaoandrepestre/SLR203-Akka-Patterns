package demo;
import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import demo.SimpleActor;


public class PublishSubscribe {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		final ActorSystem system = ActorSystem.create("system");
        final ActorRef topic1 = system.actorOf(Topic.createActor(), "topic1");
        final ActorRef topic2 = system.actorOf(Topic.createActor(), "topic2");
        final ActorRef publisher1 = system.actorOf(Publisher.createActor(topic1), "publisher1");
        final ActorRef publisher2 = system.actorOf(Publisher.createActor(topic2), "publisher2");
	    final ActorRef a = system.actorOf(SimpleActor.createActor(), "a");
		final ActorRef b = system.actorOf(SimpleActor.createActor(), "b");
		final ActorRef c = system.actorOf(SimpleActor.createActor(), "c");
		
        a.tell(topic1, ActorRef.noSender());
        b.tell(topic1, ActorRef.noSender());
        b.tell(topic2, ActorRef.noSender());
        c.tell(topic2, ActorRef.noSender());

        try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}

        publisher1.tell("hello", ActorRef.noSender());
        try{Thread.sleep(500);}catch(InterruptedException e) {e.printStackTrace();}
        publisher2.tell("world", ActorRef.noSender());

        try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}
        a.tell(topic1, ActorRef.noSender());
        try{Thread.sleep(500);}catch(InterruptedException e) {e.printStackTrace();}
        publisher1.tell("hello2", ActorRef.noSender());


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
