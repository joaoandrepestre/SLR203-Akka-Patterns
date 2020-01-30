package demo;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Node extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);


    // Empty Constructor
    public Node() {
    
    }

    // Static function that creates an actor Props
    public static Props createActor() {
        return Props.create(Node.class, () -> {
            return new Node();
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("{}: Message '{}' received from {}", getSelf().path().name(), (String) message,
                getSender().path().name());

        try{Thread.sleep(200);}catch(InterruptedException e) {e.printStackTrace();}

        getSender().tell("finished", getSelf());

    }

}
