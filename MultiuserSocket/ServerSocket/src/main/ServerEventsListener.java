package main;

import java.util.EventListener;

public interface ServerEventsListener extends EventListener{
    
    void onUserConnected(UserConnectedEvent evt);
    
    void onReceivedMessage(MessageReceivedEvent evt);
}
