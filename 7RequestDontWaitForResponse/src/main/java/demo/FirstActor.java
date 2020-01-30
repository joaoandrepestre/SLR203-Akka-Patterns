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
		if(message.equals("root")){
			actor.tell("Message 1", getSelf());
			log.info("Message 1 sent to {}", actor.path().name());
			actor.tell("Message 2", getSelf());
			log.info("Message 2 sent to {}", actor.path().name());
		}else{
			log.info("Received response '{}' from {}", (String)message, getSender().path().name());
		}
	}
	
	
}
