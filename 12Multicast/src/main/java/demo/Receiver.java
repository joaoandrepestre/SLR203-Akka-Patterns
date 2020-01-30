package demo;

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
		if(message instanceof String){
			String msg = (String) message;
			log.info("{}: Received '{}' from {}", getSelf().path().name(), msg, getSender().path().name());
		}
	}
	
	
}
