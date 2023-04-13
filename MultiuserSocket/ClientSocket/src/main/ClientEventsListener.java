package main;

public interface ClientEventsListener {
    
    void onReceivedMessage(MessageReceivedEvent evt);
}
