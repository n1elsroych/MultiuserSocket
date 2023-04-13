package main;

import java.net.Socket;
import java.util.EventObject;

public class UserConnectedEvent extends EventObject{
    private Socket socket;
    
    public UserConnectedEvent(Object source, Socket socket){
        super(source);
        this.socket = socket;
    }
    
    public Socket getSocket(){
        return socket;
    }
}
