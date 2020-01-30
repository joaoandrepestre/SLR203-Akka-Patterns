package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;

public class Broadcaster extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> receivers;

	// Empty Constructor
	public Broadcaster() {
		receivers = new ArrayList<ActorRef>();
	}

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Broadcaster.class, () -> {
			return new Broadcaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("join")){
			receivers.add(getSender());
			log.info("{}: Added {} to receivers", getSelf().path().name(), getSender().path().name());
		}else{
			for(ActorRef receiver : receivers){
				receiver.tell(message, getSelf());
				log.info("{}: Sent '{}' to {}", getSelf().path().name(), (String) message, receiver.path().name());
			}
		}
	}
	
	
}
