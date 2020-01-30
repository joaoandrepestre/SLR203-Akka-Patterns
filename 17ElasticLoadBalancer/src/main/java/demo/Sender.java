package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sender extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef loadbalancer;

	// Empty Constructor
	public Sender() {
    }
    
    public Sender(ActorRef loadbalancer){
        this.loadbalancer = loadbalancer;
    }

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
    }
    
    public static Props createActor(ActorRef loadbalancer) {
		return Props.create(Sender.class, () -> {
			return new Sender(loadbalancer);
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof String){
            loadbalancer.tell(message, getSelf());
			log.info("{}: Message '{}' sent to {}", getSelf().path().name(), (String) message, loadbalancer.path().name());
		}
	}
	
	
}
