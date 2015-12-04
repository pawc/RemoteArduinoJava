package pl.pawc.arduino.client;

import gnu.io.*;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;

import pl.pawc.arduino.shared.Message;

public class ServerConnection implements Runnable{

    private String arduinoAddress;
    private int arduinoPort;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private OutputStream outputStream = null;
    private DataOutputStream dataOutputStream;    

    public ServerConnection(int port, String host, int arduinoPort, String arduinoAddress) throws IOException{
        this.arduinoAddress = arduinoAddress;
        this.arduinoPort = arduinoPort;
        Socket socket = new Socket(host, port);
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.flush();
        objectIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("Server connection successfully created");
    }

    public void run(){
        setupWifiConnection();        
        System.out.println("Entering main loop");
        while(true){
            try{
                Message message = (Message) objectIn.readObject();
                System.out.println("Read: "+message.getI());
                sendToArduino(message);
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

    private void sendToArduino(Message message){
        try{
            dataOutputStream.writeByte(message.getI());
            dataOutputStream.flush();
            System.out.println(message.getI()+" has been sent to arduino");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setupWifiConnection(){
        try{
            Socket socket = new Socket(arduinoAddress, arduinoPort);
            System.out.println("Connected to ardunio "+arduinoAddress+", port "+arduinoPort);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("DataOutputStream to arduino is set");
        }
        catch(IOException e){
            System.out.println("Could not connect to arduino");
            e.printStackTrace();
        }
    }

}
