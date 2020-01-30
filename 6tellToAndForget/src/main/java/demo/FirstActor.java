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

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
			MyMessage msg = new MyMessage("Test message", actor);
			ActorRef transmitter = (ActorRef) message;
			transmitter.tell(msg, getSelf());
		}
	}
	
	
}
