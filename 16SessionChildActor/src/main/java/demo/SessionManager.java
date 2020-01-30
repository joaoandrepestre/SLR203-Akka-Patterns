package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class SessionManager extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private int sessionCounter;

	// Empty Constructor
	public SessionManager() {
        sessionCounter = 1;
    }

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(SessionManager.class, () -> {
			return new SessionManager();
		});
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("createSession")){
            ActorRef session = getContext().actorOf(Session.createActor(), "session"+sessionCounter);
            log.info("{}: Session '{}' created for {}", getSelf().path().name(), session.path().name(), getSender().path().name());
            getSender().tell(session, getSelf());
            sessionCounter++;
        }
        if(message instanceof ActorRef){
            ActorRef session = (ActorRef) message;
            getContext().stop(session);
            log.info("{}: Session '{}' stopped for {}", getSelf().path().name(), session.path().name(), getSender().path().name());
        }
	}
	
	
}
