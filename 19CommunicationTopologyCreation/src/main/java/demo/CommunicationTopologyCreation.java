package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class CommunicationTopologyCreation {

	public static void main(final String[] args) {

		final ActorSystem system = ActorSystem.create("system");
		final LoggingAdapter log = Logging.getLogger(system, system);

		int[][] matrix = { { 0, 1, 1, 0 }, 
						   { 0, 0, 0, 1}, 
						   { 1, 0, 0, 1}, 
						   { 1, 0, 0, 1} };

		ActorRef[] nodes = new ActorRef[matrix.length];
		for(int i=1;i<=matrix.length;i++){
			nodes[i-1] = system.actorOf(Node.createActor(), "node"+i);
		}

		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix.length;j++){
				if(matrix[i][j] == 1){
					nodes[i].tell(nodes[j], ActorRef.noSender());
				}
			}
		}

		for(int i=0;i<matrix.length;i++){
			nodes[i].tell("top", ActorRef.noSender());
		}

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
