package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Sender extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef merger;
	private int counter;

	// Empty Constructor
	public Sender() {}

	public Sender(ActorRef merger) {
		this.merger = merger;
		counter = 1;
	}

	// Static function that creates an actor Props
	public static Props createActor(ActorRef merger) {
		return Props.create(Sender.class, () -> {
			return new Sender(merger);
		});
	}

	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("join")){
			merger.tell("join", getSelf());
			log.info("{}: Joined merger", getSelf().path().name());
		}else if(message.equals("unjoin")){
			merger.tell("unjoin", getSelf());
			log.info("{}: Unjoined merger", getSelf().path().name());
		}else if(message.equals("send")){
			String msg = "hi"+counter;
			merger.tell(msg, getSelf());
			counter++;
			log.info("{}: Sent '{}' to {}", getSelf().path().name(), msg, merger.path().name());
		}
		
	}
	
	
}
