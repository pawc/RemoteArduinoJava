package pl.pawc.arduino.client;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Scanner;

import pl.pawc.arduino.shared.Message;

public class ServerConnection implements Runnable{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    
    public ServerConnection(int port, String host) throws IOException{
        Socket socket = new Socket(host, port);
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.flush();
        objectIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("Server connection successfully created");
    }

    public void run(){
        while(true){
            try{
                Message message = (Message) objectIn.readObject();
                System.out.println("Read: "+message.getI());
            }
            catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
                break;
            }
            catch(NumberFormatException e){
                e.printStackTrace();
                continue;
            }
        }
    }   

}
