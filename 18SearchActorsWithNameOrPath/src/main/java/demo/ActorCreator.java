package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ActorCreator extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private int actorCounter;

	public ActorCreator() {
		actorCounter = 1;
	}


	// Static function creating actor Props
	public static Props createActor() {
		return Props.create(ActorCreator.class, () -> {
			return new ActorCreator();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("CREATE")){
			ActorRef a = getContext().actorOf(SimpleActor.createActor(), "actor"+actorCounter);
			actorCounter++;
		}
	}



}
