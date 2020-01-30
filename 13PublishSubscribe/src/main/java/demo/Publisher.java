package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Publisher extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef topic;

	// Empty Constructor
	public Publisher() {
    }
    
    public Publisher(ActorRef topic){
        this.topic = topic;
    }

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Publisher.class, () -> {
			return new Publisher();
		});
    }
    
    public static Props createActor(ActorRef topic) {
		return Props.create(Publisher.class, () -> {
			return new Publisher(topic);
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
            topic.tell(message, getSelf());
			log.info("{}: Message '{}' published to {}", getSelf().path().name(), (String) message, topic.path().name());
		}
	}
	
	
}
