package demo;

import akka.pattern.AskTimeoutException;
import java.util.concurrent.CompletionStage;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class SessionChildActor {

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        final ActorSystem system = ActorSystem.create("system");

        final ActorRef sessionManager = system.actorOf(SessionManager.createActor(), "sessionManager");
        final ActorRef client1 = system.actorOf(Client.createActor(sessionManager), "client1");
        

        client1.tell("createSession", ActorRef.noSender());

        try{Thread.sleep(1000);}catch(InterruptedException e) {e.printStackTrace();}

        client1.tell("root", ActorRef.noSender());
        client1.tell("root", ActorRef.noSender());

        client1.tell("endSession", ActorRef.noSender());

        

        // We wait 5 seconds before ending system (by default)
        // But this is not the best solution.
        try {
            waitBeforeTerminate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            system.terminate();
        }
    }

    public static void waitBeforeTerminate() throws InterruptedException {
        Thread.sleep(5000);
    }
}
