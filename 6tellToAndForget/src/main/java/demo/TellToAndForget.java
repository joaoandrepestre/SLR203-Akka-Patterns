package demo;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class TellToAndForget {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		final ActorSystem system = ActorSystem.create("system");
		final ActorRef b = system.actorOf(SecondActor.createActor(), "b");
		final ActorRef transmitter = system.actorOf(Transmitter.createActor(), "transmitter");
	    final ActorRef a = system.actorOf(FirstActor.createActor(b), "a");

		a.tell(transmitter, ActorRef.noSender());

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
