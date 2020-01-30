package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


public class Client extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ActorRef sessionManager;
    private ActorRef session;
    private int msgCounter;

	// Empty Constructor
	public Client() {
        msgCounter = 1;
    }

    public Client(ActorRef sessionManager) {
        this.sessionManager = sessionManager;
        msgCounter = 1;
    }

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Client.class, () -> {
			return new Client();
		});
    }

    public static Props createActor(ActorRef sessionManager) {
		return Props.create(Client.class, () -> {
			return new Client(sessionManager);
		});
    }

	@Override
	public void onReceive(Object message) throws Throwable {
        if(message.equals("createSession")){
            sessionManager.tell(message, getSelf());
            log.info("{}: Message '{}' sent to {}", getSelf().path().name(), (String) message, sessionManager.path().name());
        }
        else if(message.equals("endSession")){
            sessionManager.tell(session, getSelf());
            log.info("{}: Message '{}' sent to {}", getSelf().path().name(), (String) message, sessionManager.path().name());

        }
        else if(message instanceof ActorRef){
            session = (ActorRef) message;
            log.info("{}: Session '{}' received from {}", getSelf().path().name(), session.path().name(), getSender().path().name());
        }
        else if(message.equals("root")){
            String msg = "m"+msgCounter;
            msgCounter++;
            session.tell(msg, getSelf());
            log.info("{}: Message '{}' sent to {}", getSelf().path().name(), msg, session.path().name());
        }
        else{
            log.info("{}: Message '{}' received from {}", getSelf().path().name(), (String) message, getSender().path().name());
        }
	}
	
	
}
