package pl.pawc.arduino.client;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import pl.pawc.arduino.shared.Message;

public class ServerConnection{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private boolean connected;    

    public ServerConnection(int port, String host) throws Exception{
        connected = false;
        Socket socket = new Socket(host, port);
        System.out.println("Socket created");
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("ObjectOutputStream created");
        objectOut.flush();
        System.out.println("and flushed");
        //objectIn = new ObjectInputStream(socket.getInputStream());
        connected = true;
        System.out.println("Server connection successfully created");
    }

    public boolean isConnected(){
        return connected;
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
            catch(NullPointerException e){
                e.printStackTrace();
                return;
            }  
    }   

}
