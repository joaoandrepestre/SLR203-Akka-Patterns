package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Receiver extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Empty Constructor
	public Receiver() {}

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Receiver.class, () -> {
			return new Receiver();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
			ActorRef broadcaster = (ActorRef) message;
			broadcaster.tell("join", getSelf());
			log.info("{}: Joined broadcaster", getSelf().path().name());
		}else{
			log.info("{}: Received '{}' from {}", getSelf().path().name(), (String) message, getSender().path().name());
		}
	}
	
	
}
