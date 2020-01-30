package demo;

import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SimpleActor extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


	// Empty Constructor
	public SimpleActor() {}

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(SimpleActor.class, () -> {
			return new SimpleActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("PATH")){
			log.info("My path: {}", getSelf().path());
		}
	}
	
	
}
