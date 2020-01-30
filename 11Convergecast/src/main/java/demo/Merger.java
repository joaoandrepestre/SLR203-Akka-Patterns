package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;
import java.lang.Math;

public class Merger extends UntypedAbstractActor {

	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> senders;
	private ActorRef receiver;
	private int mask;
	private int received;
	private Message myMessage;

	// Empty Constructor
	public Merger() {
		senders = new ArrayList<ActorRef>();
		mask = 0;
		received = 0;
		myMessage= null;
	}

	public Merger(ActorRef receiver) {
		senders = new ArrayList<ActorRef>();
		this.receiver = receiver;
		mask = 0;
		received = 0;
		myMessage = null;
	}

	private void addSender(ActorRef sender){
		senders.add(sender);
		mask = (int)Math.pow(2, senders.size()) - 1;
	}

	private void removeSender(ActorRef sender){
		int i =  findSender(sender);
		senders.remove(i);
		mask = (int)Math.pow(2, senders.size()) - 1;
	}

	private int findSender(ActorRef sender){
		for(int i=0;i<senders.size();i++){
			if(senders.get(i) == sender){
				return i;
			}
		}
		return -1;
	}

	// Static function that creates an actor Props
	public static Props createActor() {
		return Props.create(Merger.class, () -> {
			return new Merger();
		});
	}

	public static Props createActor(ActorRef receiver) {
		return Props.create(Merger.class, () -> {
			return new Merger(receiver);
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message.equals("join")){
			addSender(getSender());
			log.info("{}: Added {} to senders", getSelf().path().name(), getSender().path().name());
		}else if(message.equals("unjoin")){
			removeSender(getSender());
			log.info("{}: Removed {} from senders", getSelf().path().name(), getSender().path().name());
		}else{
			if(myMessage == null){
				log.info("{}: New message '{}' received from {}", getSelf().path().name(), (String) message, getSender().path().name());
				myMessage = new Message((String) message);
				myMessage.addSender(getSender());

				received += (int)Math.pow(2, findSender(getSender()));
			}else if(message.equals(myMessage.data)){
				log.info("{}: New sender {} for message '{}''", getSelf().path().name(), getSender().path().name(), (String) message);
				myMessage.addSender(getSender());

				received += (int)Math.pow(2, findSender(getSender()));
			} else{
				throw new Exception();
			}

			if(received == mask){
				receiver.tell(myMessage, getSelf());
				log.info("{}: Sent '{}' to {}", getSelf().path().name(), myMessage.data, receiver.path().name());
				myMessage = null;
				received = 0;
			}
		}
	}
	
	
}
