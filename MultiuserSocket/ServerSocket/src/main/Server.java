package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server implements ServerEventsListener{
    private ServerSocket serverSocket;
    private Map<Integer, Socket> clients;
    private int clientId;
    ConnectionWaiter connectionWaiter;
        
    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
        clients = new HashMap<>();
        clientId = 0;
        connectionWaiter = new ConnectionWaiter(serverSocket);
        connectionWaiter.addEventsListener(this);
        System.out.println("Servidor iniciado en el puerto " + port);
    }
    
    public void start() throws IOException{
        connectionWaiter.start();
    }

    private void sendBroadcast(int clientId, String message) throws IOException{
        DataOutputStream out;
        for (int i = 1; i <= this.clientId; i++) {
            //if (i != clientId) //Para que el que envio el mensaje no reciba el mismo, claro en caso de haberlo impreso desde el cliente y no se imprima 2 veces ese mismo mensaje
            out = new DataOutputStream(clients.get(i).getOutputStream());
            out.writeUTF("Usuario "+clientId+": "+message);
        }
    }
    
    @Override
    public void onUserConnected(UserConnectedEvent evt) {
        try {
            Socket socket = evt.getSocket();
            clientId++;
            clients.put(clientId, socket);
            ClientHandler clientHandler = new ClientHandler(clientId, socket.getInputStream());
            clientHandler.addEventsListener(this);
            clientHandler.start();
            System.out.println("El usuario ha sido registrado con el ID = "+clientId);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onReceivedMessage(MessageReceivedEvent evt) {
        int clientId = evt.getClientId();
        String message = evt.getMessage();
        System.out.println("El usuario con el ID = "+clientId+" ha enviado el siguiente mensaje: "+message);
        
        try {
            //DataOutputStream out = new DataOutputStream(clients.get(clientId).getOutputStream());
            //out.writeUTF("Usuario "+clientId+": "+message);
            //out.writeUTF("El servidor recibio correctamente su mensaje");
            sendBroadcast(clientId, message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
