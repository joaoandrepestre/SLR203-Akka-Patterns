package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Topic extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> subscribers;

	// Empty Constructor
	public Topic() {
		subscribers = new ArrayList<ActorRef>();
	}

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Topic.class, () -> {
			return new Topic();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("subscribe")){
			if(subscribers.contains(getSender())){
				subscribers.remove(getSender());
				log.info("{}: {} unsubscribed", getSelf().path().name(), getSender().path().name());
			}else{
				subscribers.add(getSender());
				log.info("{}: {} subscribed", getSelf().path().name(), getSender().path().name());
			}
		} else {
			log.info("{}: Message '{}' received from {}", getSelf().path().name(), (String) message, getSender().path().name());
			for(ActorRef actor : subscribers){
				actor.tell(message, getSender());
				log.info("{}: Message '{}' sent to {}", getSelf().path().name(), (String) message, actor.path().name());
			}
		}
	}
	
	
}
