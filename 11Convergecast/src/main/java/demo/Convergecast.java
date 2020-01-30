package demo;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class Convergecast {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		final ActorSystem system = ActorSystem.create("system");
		final ActorRef d = system.actorOf(Receiver.createActor(), "d");
		final ActorRef merger = system.actorOf(Merger.createActor(d), "merger");
	    final ActorRef a = system.actorOf(Sender.createActor(merger), "a");
        final ActorRef b = system.actorOf(Sender.createActor(merger), "b");
		final ActorRef c = system.actorOf(Sender.createActor(merger), "c");
		
		a.tell("join", ActorRef.noSender());
		b.tell("join", ActorRef.noSender());
		c.tell("join", ActorRef.noSender());
		try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}
		a.tell("send", ActorRef.noSender());
		b.tell("send", ActorRef.noSender());
		c.tell("send", ActorRef.noSender());
		try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}
		c.tell("unjoin", ActorRef.noSender());
		try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}
		a.tell("send", ActorRef.noSender());
		b.tell("send", ActorRef.noSender());

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
		Thread.sleep(10000);
	}
}
