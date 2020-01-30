package demo;
import java.util.ArrayList;
import java.util.Arrays;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;



public class ElasticLoadBalancer {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
        final ActorSystem system = ActorSystem.create("system");
        
        final ActorRef loadbalancer = system.actorOf(LoadBalancer.createActor(2), "loadbalancer");
        final ActorRef a = system.actorOf(Sender.createActor(loadbalancer), "a");

        a.tell("t1", ActorRef.noSender());
        a.tell("t2", ActorRef.noSender());
        a.tell("t3", ActorRef.noSender());

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
