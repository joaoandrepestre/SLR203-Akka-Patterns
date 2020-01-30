package demo;

import java.util.ArrayList;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class LoadBalancer extends UntypedAbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private ArrayList<ActorRef> nodes;
    private int[] tasks;
    private int maxNodes;
    private int actorId;

    // Empty Constructor
    public LoadBalancer(int max) {
        maxNodes = max;
        nodes = new ArrayList<ActorRef>();
        tasks = new int[max];
        actorId = -1;
    }

    // Static function that creates an actor Props
    public static Props createActor(int max) {
        return Props.create(LoadBalancer.class, () -> {
            return new LoadBalancer(max);
        });
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        log.info("{}: Message '{}' received from {}", getSelf().path().name(), (String) message,
                getSender().path().name());

        if (message.equals("finished")) {
            int i = nodes.indexOf(getSender());
            tasks[i]--;
            if (tasks[i] == 0) {
                getContext().stop(getSender());
                nodes.remove(getSender());
                log.info("{}: All tasks done for {}. Terminating", getSelf().path().name(), getSender().path().name());
            }
        } else {

            if (nodes.size() < maxNodes) {
                nodes.add(getContext().actorOf(Node.createActor(), "node" + nodes.size()));
                log.info("{}: Creating {} ", getSelf().path().name(), "node"+(nodes.size()-1));
            }

            actorId = (actorId + 1) % nodes.size();
            ActorRef actor = nodes.get(actorId);
            actor.tell(message, getSelf());
            tasks[actorId]++;
            log.info("{}: Message '{}' sent to {}", getSelf().path().name(), (String) message, actor.path().name());
        }
    }

}
