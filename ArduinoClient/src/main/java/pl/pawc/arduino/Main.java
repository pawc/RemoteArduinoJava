package pl.pawc.arduino;

import java.io.IOException;

import pl.pawc.arduino.client.ServerConnection;

public class Main {
    public static void main( String[] args ){
        try{
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[3]);
            ServerConnection serverConnection = new ServerConnection(i, args[0], j, args[3]);
            serverConnection.run();
        }
        catch(NumberFormatException | ArrayIndexOutOfBoundsException | IOException e){
            e.printStackTrace();
        }
    }
}
