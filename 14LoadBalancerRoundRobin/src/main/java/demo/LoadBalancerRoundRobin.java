package demo;
import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;



public class LoadBalancerRoundRobin {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
        final ActorSystem system = ActorSystem.create("system");
        
        final ActorRef loadbalancer = system.actorOf(LoadBalancer.createActor(), "loadbalancer");
        final ActorRef a = system.actorOf(Sender.createActor(loadbalancer), "a");
		final ActorRef b = system.actorOf(Receiver.createActor(), "b");
		final ActorRef c = system.actorOf(Receiver.createActor(), "c");
		
        b.tell(loadbalancer, ActorRef.noSender());
        c.tell(loadbalancer, ActorRef.noSender());

        try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}

        a.tell("m1", ActorRef.noSender());
        a.tell("m2", ActorRef.noSender());
        a.tell("m3", ActorRef.noSender());
        try{Thread.sleep(500);}catch(InterruptedException e) {e.printStackTrace();}
        c.tell(loadbalancer, ActorRef.noSender());
        try{Thread.sleep(500);}catch(InterruptedException e) {e.printStackTrace();}
        a.tell("m4", ActorRef.noSender());


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
