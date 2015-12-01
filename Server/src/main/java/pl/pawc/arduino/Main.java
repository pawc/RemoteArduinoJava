package pl.pawc.arduino;

import pl.pawc.arduino.server.ServerListener;
import pl.pawc.arduino.server.ClientConnection;

import java.io.IOException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        
        ServerListener serverListener;
        
        try{
            int port = Integer.parseInt(args[0]);
            serverListener = new ServerListener(port);
            new Thread(serverListener).start();
            Scanner sc = new Scanner(System.in);
            
            while(true){
                String command = sc.nextLine();
                if("clients".equals(command)){
                   System.out.println("Connected clients");
                   int i = 1;
                   for(ClientConnection clientConnection : serverListener.getList()){
                       System.out.println(i+". "+clientConnection.toString());
                       i++;
                   }
                }
            }
        }
        catch(NumberFormatException | IOException | ArrayIndexOutOfBoundsException e){
            System.out.println("Couldn't start server on port "+args[0]);
        }

    }
}
