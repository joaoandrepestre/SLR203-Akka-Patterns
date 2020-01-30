package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Transmitter extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	public Transmitter() {}

	// Static function creating actor Props
	public static Props createActor() {
		return Props.create(Transmitter.class, () -> {
			return new Transmitter();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof MyMessage){
			MyMessage msg = (MyMessage) message;
			msg.getReceiver().tell(msg.getMessage(), getSender());
		}
	}



}
