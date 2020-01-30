package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Node extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> neighbours;

	// Empty Constructor
	public Node() {
		neighbours = new ArrayList<ActorRef>();
	}

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Node.class, () -> {
			return new Node();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
			neighbours.add((ActorRef) message);
		}
		if(message.equals("top")){
			log.info("[{}]: My neighbours: ", getSelf().path().name());
			for(ActorRef n : neighbours){
				log.info("{} ", n.path().name());
			}
		}
	}
	
	
}
