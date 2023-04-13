package main;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    private DataInputStream in;
    int clientId;
    
    private ArrayList<ServerEventsListener> listeners;
    
    public ClientHandler(int clientId, InputStream inputStream){
        this.clientId = clientId;
        in = new DataInputStream(inputStream);
        listeners = new ArrayList<>();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                String message = in.readUTF();
                triggerMessageReceivedEvent(message);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void addEventsListener(ServerEventsListener listener){
        listeners.add(listener);
    }
    
    public void removeMiEventoListener(ServerEventsListener listener) {
        listeners.remove(listener);
    }
    
    public void triggerMessageReceivedEvent(String message) {
        MessageReceivedEvent evt = new MessageReceivedEvent(this, message, clientId);
        for (ServerEventsListener listener : listeners) {
            listener.onReceivedMessage(evt);
        }
    }
}
