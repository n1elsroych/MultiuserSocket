package main;

import java.io.IOException;
import java.net.Socket;

public class Client implements ClientEventsListener{
    private Socket socket;
    
    public Client(String serverAddress, int port) throws IOException{
        socket = new Socket(serverAddress, port);
    }
    
    public void connect() throws IOException{
        InputHandler inputHandler = new InputHandler(socket.getInputStream());
        inputHandler.addEventsListener(this);
        inputHandler.start();
        
        OutputHandler outputHandler = new OutputHandler(socket.getOutputStream());
        outputHandler.start();
    }

    @Override
    public void onReceivedMessage(MessageReceivedEvent evt) {
        String message = evt.getMessage();
        System.out.println("Servidor: "+message);
    }
}
