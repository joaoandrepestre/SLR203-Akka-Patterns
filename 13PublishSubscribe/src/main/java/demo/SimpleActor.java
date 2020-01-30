package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

	// Empty Constructor
	public SimpleActor() {
    }

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(SimpleActor.class, () -> {
			return new SimpleActor();
		});
    }

	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof ActorRef){
            ActorRef topic = (ActorRef) message;
            topic.tell("subscribe", getSelf());
        }
		if(message instanceof String){
			log.info("{}: Message {} received from {}", getSelf().path().name(), (String) message, getSender().path().name());
		}
	}
	
	
}
