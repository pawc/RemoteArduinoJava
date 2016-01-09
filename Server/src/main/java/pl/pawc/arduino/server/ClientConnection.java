package pl.pawc.arduino.server;

import java.net.Socket;
import java.net.SocketException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;

public class ClientConnection implements Runnable{
    
    private ServerListener serverListener;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    private String toString;

    public ClientConnection(Socket socket, ServerListener serverListener) throws IOException{
        this.serverListener = serverListener;
        System.out.println("creating streams for the new client");
        dataOut = new DataOutputStream(socket.getOutputStream());
        dataOut.flush();
        dataIn = new DataInputStream(socket.getInputStream());
        serverListener.getList().add(this);
        toString = socket.getInetAddress().toString();       
    }
 
    public void run(){
        while(true){
            try{
                int message = dataIn.readByte();
                System.out.println("Received a letter "+(char) message);
                String messageString = String.valueOf((char) message);
                serverListener.sendToAll(messageString, dataOut);
                System.out.println("The letter has been sent to all");      
            }
            catch(EOFException e){
                serverListener.getList().remove(this);
                break;
            }
            catch(SocketException e){
                serverListener.getList().remove(this);
                break;
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Error while sending message to all connected clients");
                continue;
            }
            catch(NullPointerException e){
                e.printStackTrace();
                System.out.println("Client disconnected");
                break;
            }
        }
    }    

    public DataInputStream getDataIn(){
        return dataIn;     
    }

    public DataOutputStream getDataOut(){
        return dataOut;
    }

    public String toString(){
        return toString;
    }

}
