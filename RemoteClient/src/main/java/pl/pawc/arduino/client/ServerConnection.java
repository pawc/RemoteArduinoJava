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
        objectIn = new ObjectInputStream(socket.getInputStream());
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Server connection successfully created");
    }

    public void run(){
        Scanner sc = new Scanner(System.in);
        while(true){
            try{
               String line = sc.nextLine();
               int i = Integer.parseInt(line);
               Message message = new Message(i);
               objectOut.writeObject(message);
               System.out.println("Message has been sent");
            }
            catch(IOException e){
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
