package pl.pawc.arduino.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.util.Vector;
import java.io.DataOutputStream;

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
                new Thread(new ClientConnection(socket, this)).start();
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Error with incoming connection. Skipping.");
            }
        }
    }

    public void sendToAll(String message, DataOutputStream dataOut) throws IOException{
        for(ClientConnection clientConnection : list){
            if(dataOut!=clientConnection.getDataOut()){
            clientConnection.getDataOut().writeBytes(message);
            }
            //clientConnection.getObjectOut().flush();
        }   
    }    

}
