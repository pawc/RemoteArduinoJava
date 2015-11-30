package pl.pawc.arduino.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.Vector;

public class ServerListener implements Runnable{

    private ServerSocket serverSocket;
    private Vector<ClientConnection> list;    

    public ServerListener(int port) throws IOException{
        System.out.println("Creating ServerListener object");
        list = new Vector<ClientConnection>();
        System.out.println("Setting port to "+port);
        serverSocket = new ServerSocket(port);
        System.out.println("All set");
    }

    public Vector<ClientConnection> getList(){
        return list;
    }

    public void run(){
        while(true){
            try{
                System.out.println("Awaiting connections");
                Socket socket = serverSocket.accept();
                System.out.println("New incoming connection from "+socket.toString());
                ClientConnection clientConnection = new ClientConnection(socket, this);
                System.out.println("New thread has been created");
                clientConnection.run();
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Error with incoming connection. Skipping.");
            }
        }
    }

    public void sendToAll(Object obj) throws IOException{
        for(ClientConnection clientConnection : list){
            clientConnection.getObjectOut().writeObject(obj);
        }   
    }    

}
