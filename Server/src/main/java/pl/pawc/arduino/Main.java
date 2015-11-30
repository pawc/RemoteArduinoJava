package pl.pawc.arduino;

import pl.pawc.arduino.server.ServerListener;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        
        try{
            int port = Integer.parseInt(args[0]);
            ServerListener serverListener = new ServerListener(port);
            serverListener.run();
        }
        catch(NumberFormatException | IOException | ArrayIndexOutOfBoundsException e){
            System.out.println("Couldn't start server on port "+args[0]);
        }

    }
}
