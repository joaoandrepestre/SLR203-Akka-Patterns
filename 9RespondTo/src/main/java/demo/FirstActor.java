package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FirstActor extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef actor;

	// Empty Constructor
	public FirstActor() {}

	public FirstActor(ActorRef a) {
		actor = a;
	}

	// Static function that creates an actor Props
	public static Props createActor(ActorRef a) {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor(a);
		});
	}

	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
			ActorRef respondTo = (ActorRef) message;
			actor.tell(respondTo, getSelf());
			log.info("{}: Message '{}' sent to {}", getSelf().path().name(), respondTo.path().name(), actor.path().name());
		}else{
			log.info("{}: Response received '{}' from {}", getSelf().path().name(), (String) message, getSender().path().name());
		}
	}
	
	
}
