package pl.pawc.arduino.client;

import gnu.io.*;

import java.net.Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import pl.pawc.arduino.shared.Message;

public class ServerConnection implements Runnable{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private OutputStream outputStream = null;
    
    public ServerConnection(int port, String host) throws IOException{
        Socket socket = new Socket(host, port);
        objectOut = new ObjectOutputStream(socket.getOutputStream());
        objectOut.flush();
        objectIn = new ObjectInputStream(socket.getInputStream());
        System.out.println("Server connection successfully created");
    }

    public void run(){
        setupSerialConnection();
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
            outputStream.write(message.getI());
            outputStream.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void setupSerialConnection(){
        ArrayList<String> portsList = new ArrayList<String>();
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        HashMap portMap = new HashMap();
            while(ports.hasMoreElements()){
                CommPortIdentifier currentPort = (CommPortIdentifier) ports.nextElement();
                if(currentPort.getPortType()==CommPortIdentifier.PORT_SERIAL){
                    portMap.put(currentPort.getName(), currentPort);
                    portsList.add(currentPort.getName());
                    System.out.println("Found port "+currentPort.getName());
                }
            }
        Scanner sc = new Scanner(System.in);
        System.out.print("Choose a port: ");
        String selectedPort = sc.nextLine();

        CommPortIdentifier selectedPortIdentifier = (CommPortIdentifier) portMap.get(selectedPort);
        CommPort commPort = null;
        SerialPort serialPort = null;        

        try{
            commPort = selectedPortIdentifier.open(selectedPort, 2000); // 2000 - timeout   
            serialPort = (SerialPort) commPort;
            System.out.println(serialPort+" opened successfully");
        }
        catch(PortInUseException e){
            System.out.println("port "+selectedPort+" in use");
            return;
        }
        catch(Exception e){
            System.out.println("Failed to open port");
            e.printStackTrace();
            return;
        }

        try{
            outputStream = serialPort.getOutputStream();
            System.out.println("Stream opened successfully");
            outputStream.flush();
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }            

        try{
            Thread.sleep(4000);             
            return;         
        } 
        catch(InterruptedException e){             
            e.printStackTrace();             
            return;         
        }

    }

}
