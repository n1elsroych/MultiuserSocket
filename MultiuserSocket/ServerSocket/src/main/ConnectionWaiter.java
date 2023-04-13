package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionWaiter extends Thread{
    ServerSocket serverSocket;
    ClientHandler clientHandler;
    
    private ArrayList<ServerEventsListener> listeners;
    
    public ConnectionWaiter(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        listeners = new ArrayList<>();
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Esperando que un usuario se conecte...");
                Socket client = serverSocket.accept();
                System.out.println("Un usuario se ha conectado");
                triggerUserConnectedEvent(client);
                
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
    
    public void triggerUserConnectedEvent(Socket socket) {
        UserConnectedEvent evt = new UserConnectedEvent(this, socket);
        for (ServerEventsListener listener : listeners) {
            listener.onUserConnected(evt);
        }
    }
    
    
}
