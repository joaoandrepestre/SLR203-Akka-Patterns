package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Multicaster extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private Map<String, ArrayList<ActorRef>> groups;

	// Empty Constructor
	public Multicaster() {
		groups = new HashMap<String, ArrayList<ActorRef>>();
	}

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Multicaster.class, () -> {
			return new Multicaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof Group){
			Group g = (Group) message;
			groups.put(g.name, g.receivers);
			log.info("{}: Created group {}", getSelf().path().name(), g.name);
		} else if(message instanceof Message){
			Message msg = (Message) message;
			log.info("{}: Message '{}' to group {} received from {}", getSelf().path().name(), msg.data, msg.groupName, getSender().path().name());
			ArrayList<ActorRef> group = groups.get(msg.groupName);
			for(ActorRef actor : group){
				actor.tell(msg.data, getSender());
				log.info("{}: Message '{}' sent to {}", getSelf().path().name(), msg.data, actor.path().name());
			}
		}
	}
	
	
}
