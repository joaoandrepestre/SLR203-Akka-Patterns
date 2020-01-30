package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Receiver extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private boolean joined;

	// Empty Constructor
	public Receiver() {
        joined = false;
    }

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Receiver.class, () -> {
			return new Receiver();
		});
    }

	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof ActorRef){
            if(!joined){
                ActorRef loadbalancer = (ActorRef) message;
                loadbalancer.tell("join", getSelf());
                joined = true;
            }else{
                ActorRef loadbalancer = (ActorRef) message;
                loadbalancer.tell("unjoin", getSelf());
                joined = false;
            }
        }
		if(message instanceof String){
			log.info("{}: Message {} received from {}", getSelf().path().name(), (String) message, getSender().path().name());
		}
	}
	
	
}
