package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Sender extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef multicaster;

	// Empty Constructor
	public Sender() {}

	public Sender(ActorRef multicaster) {
		this.multicaster = multicaster;
	}

	// Static function that creates an actor Props
	public static Props createActor(ActorRef multicaster) {
		return Props.create(Sender.class, () -> {
			return new Sender(multicaster);
		});
	}

	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof Group){
			Group g = (Group) message;
			multicaster.tell(g, getSelf());
			log.info("{}: Sent group {} to multicaster", getSelf().path().name(), g.name);
		}else if(message instanceof Message){
			Message msg = (Message) message;
			multicaster.tell(msg, getSelf());
			log.info("{}: Sent '{}' to {}", getSelf().path().name(), msg.data, msg.groupName);
		}
		
	}
	
	
}
