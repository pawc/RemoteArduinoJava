package pl.pawc.arduino.server;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ClientConnection implements Runnable{
    
    private ServerListener serverListener;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public ClientConnection(Socket socket, ServerListener serverListener) throws IOException{
        System.out.println("creating streams for the new client");
        objectIn = new ObjectInputStream(socket.getInputStream());
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        serverListener.getList().add(this);       
    }
 
    public void run(){
        while(true){
            try{
                if(objectIn.available()>0){
                    System.out.println("Received an object");
                    serverListener.sendToAll(objectIn.readObject());
                    System.out.println("The new object has been sent to all");
                }
            }
            catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
                System.out.println("Error while sending object to all connected clients");
                continue;
            }
            catch(NullPointerException e){
                e.printStackTrace();
                System.out.println("Client disconnected");
                break;
            }
        }
    }    

    public ObjectInputStream getObjectIn(){
        return objectIn;     
    }

    public ObjectOutputStream getObjectOut(){
        return objectOut;
    }

}
