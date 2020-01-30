package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Session extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Empty Constructor
	public Session() {
    }

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Session.class, () -> {
			return new Session();
		});
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
            log.info("{}: Message {} received from {}", getSelf().path().name(), (String) message, getSender().path().name());
            String msg = (String) message + "_answer";
            getSender().tell(msg, getSelf());
        }
	}
	
	
}
