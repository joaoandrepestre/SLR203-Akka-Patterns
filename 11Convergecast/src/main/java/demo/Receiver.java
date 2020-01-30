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
		if(message instanceof Message){
			Message msg = (Message) message;
			for(ActorRef sender : msg.senders){
				log.info("{}: Received '{}' from {}", getSelf().path().name(), (String) msg.data, sender.path().name());
			}
		}
	}
	
	
}
