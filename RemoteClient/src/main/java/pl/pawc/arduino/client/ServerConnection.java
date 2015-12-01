package pl.pawc.arduino.client;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.pawc.arduino.shared.Message;

public class ServerConnection{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    
    public ServerConnection(int port, String host) throws IOException{
        Socket socket = new Socket(host, port);
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.flush();
        objectIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("Server connection successfully created");
    }

    public void sendMessage(int i){
            try{
               Message message = new Message(i);
               objectOut.writeObject(message);
               objectOut.flush();
               System.out.println("Message has been sent");
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
            catch(NumberFormatException e){
                e.printStackTrace();
                return;
            }  
    }   

}
