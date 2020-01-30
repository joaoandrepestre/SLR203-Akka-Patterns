package demo;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class BroadcastRoundRobin {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		final ActorSystem system = ActorSystem.create("system");
		final ActorRef broadcaster = system.actorOf(Broadcaster.createActor(), "broadcaster");
	    final ActorRef a = system.actorOf(Sender.createActor(broadcaster), "a");
        final ActorRef b = system.actorOf(Receiver.createActor(), "b");
		final ActorRef c = system.actorOf(Receiver.createActor(), "c");
		
		b.tell(broadcaster, ActorRef.noSender());
		c.tell(broadcaster, ActorRef.noSender());
		try{Thread.sleep(2000);}catch(InterruptedException e) {e.printStackTrace();}
		a.tell("root", ActorRef.noSender());

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
