package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SearchActorsWithNameOrPath {

	public static void main(final String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, system);

		final ActorRef a = system.actorOf(ActorCreator.createActor(), "a");

		a.tell("CREATE", ActorRef.noSender());
		a.tell("CREATE", ActorRef.noSender());

		system.actorSelection("user/a/actor1").tell("PATH", ActorRef.noSender());
		system.actorSelection("user/a/actor2").tell("PATH", ActorRef.noSender());

		system.actorSelection("/*").tell("PATH", ActorRef.noSender());


		// We wait 5 seconds before ending system (by default)
		// But this is not the best solution.
		try {
			waitBeforeTerminate();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}
}
