package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Sender extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef broadcaster;

	// Empty Constructor
	public Sender() {}

	public Sender(ActorRef broadcaster) {
		this.broadcaster = broadcaster;
	}

	// Static function that creates an actor Props
	public static Props createActor(ActorRef broadcaster) {
		return Props.create(Sender.class, () -> {
			return new Sender(broadcaster);
		});
	}

	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("root")){
			broadcaster.tell("Message", getSelf());
			log.info("{}: Message '{}' sent to {}", getSelf().path().name(), "Message", broadcaster.path().name());
		}
		
	}
	
	
}
