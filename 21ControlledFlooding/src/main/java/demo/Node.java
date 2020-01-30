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
	private ArrayList<Integer> messagesReceived;

	// Empty Constructor
	public Node() {
		neighbours = new ArrayList<ActorRef>();
		messagesReceived = new ArrayList<Integer>();
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
		if(message instanceof String){
			if(message.equals("root")){
				Message myMessage = new Message("my message", 0);
				messagesReceived.add(0);
			
				for(ActorRef actor : neighbours){
					actor.tell(myMessage, getSelf());
					log.info("[{}]: Message '{}' with sequence number {} sent to {}", getSelf().path().name(), myMessage.data, myMessage.sequenceNumber, actor.path().name());
				}
			}
		}
		if(message instanceof Message){
			Message myMessage = (Message)message;
			if(!messagesReceived.contains(myMessage.sequenceNumber)){
				messagesReceived.add(myMessage.sequenceNumber);

				log.info("[{}]: Received message '{}' with sequence number {} from {}", getSelf().path().name(), myMessage.data, myMessage.sequenceNumber, getSender().path().name());
				for(ActorRef actor : neighbours){
					actor.tell(myMessage, getSelf());
					log.info("[{}]: Message '{}' forwarded to {}", getSelf().path().name(), myMessage.data ,actor.path().name());
				}
			}

		}
	}
}
