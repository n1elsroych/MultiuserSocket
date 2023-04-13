package main;

import java.util.EventObject;

public class MessageReceivedEvent extends EventObject{
    private String message;
    private int clientId;
    
    public MessageReceivedEvent(Object source, String message, int clientId) {
        super(source);
        this.message = message;
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getMessage() {
        return message;
    }
}
