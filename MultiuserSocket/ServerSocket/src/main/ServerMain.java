package main;

import java.io.IOException;

public class ServerMain {
    private static final int PORT = 8888;
    
    public static void main(String[] args) {
        try {
            Server server = new Server(PORT);
            server.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
