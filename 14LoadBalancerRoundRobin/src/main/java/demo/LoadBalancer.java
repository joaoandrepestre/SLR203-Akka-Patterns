package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoadBalancer extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ArrayList<ActorRef> subscribers;
    private int actorId;

    // Empty Constructor
    public LoadBalancer() {
        subscribers = new ArrayList<ActorRef>();
        actorId = -1;
    }

    // Static function that creates an actor Props
    public static Props createActor() {
        return Props.create(LoadBalancer.class, () -> {
            return new LoadBalancer();
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message.equals("join")) {
            subscribers.add(getSender());
            log.info("{}: {} joined", getSelf().path().name(), getSender().path().name());
        }else if(message.equals("unjoin")){
            subscribers.remove(getSender());
            log.info("{}: {} unjoined", getSelf().path().name(), getSender().path().name());
        } else {
            log.info("{}: Message '{}' received from {}", getSelf().path().name(), (String) message,
                    getSender().path().name());
            
            actorId = (actorId+1) % subscribers.size();
            ActorRef actor = subscribers.get(actorId);
            actor.tell(message, getSelf());
            log.info("{}: Message '{}' sent to {}", getSelf().path().name(), (String) message, actor.path().name());
        }
    }

}
