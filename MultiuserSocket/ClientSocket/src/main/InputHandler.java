package main;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class InputHandler extends Thread{
    DataInputStream in;
    
    private ArrayList<ClientEventsListener> listeners;
    
    public InputHandler(InputStream inputStream){
        in = new DataInputStream(inputStream);
        listeners = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Esperando mensaje...");
                String message = in.readUTF();
                System.out.println("Se ha recibido un mensaje");
                triggerMessageReceivedEvent(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void addEventsListener(ClientEventsListener listener){
        listeners.add(listener);
    }
    
    public void removeMiEventoListener(ClientEventsListener listener) {
        listeners.remove(listener);
    }
    
    public void triggerMessageReceivedEvent(String message) {
        MessageReceivedEvent evt = new MessageReceivedEvent(this, message);
        for (ClientEventsListener listener : listeners) {
            listener.onReceivedMessage(evt);
        }
    }
}
