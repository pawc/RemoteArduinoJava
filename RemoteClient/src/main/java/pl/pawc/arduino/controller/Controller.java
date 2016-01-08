package pl.pawc.arduino.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import pl.pawc.arduino.shared.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Controller{

    private @FXML Button connectButton;
    private @FXML RadioButton LEDswitch;

    private boolean connected;
    private DataOutputStream dataOut;
    private DataInputStream dataIn;
    private Socket socket;

    public void initialize(){
    
        LEDswitch.setOnAction(event->{
            if(LEDswitch.isSelected()){
                sendMessage("a");
            }
            if(!LEDswitch.isSelected()){
                sendMessage("b");
            }
        });


        connectButton.setOnAction(event->{
          if(!connected){
            try{
                socket = new Socket("kritsit.ddns.net", 6000);
                dataOut = new DataOutputStream(socket.getOutputStream());
                //dataOut.flush();
                dataIn = new DataInputStream(socket.getInputStream());
                connected = true;
                connectButton.setText("Disconnect");
            }
            catch(IOException e){
                e.printStackTrace();
            }
          }
          else{
            try{
                dataOut.close();
                dataIn.close();
                socket.close();
                connected=false;
                connectButton.setText("Connect");
                System.out.println("Disconnected from the server");
            }
            catch(IOException e){
                e.printStackTrace();
            }            
          }           

        });

    }

    public boolean getConnected(){
        return connected;
    }

    public void sendMessage(String message){
        if(connected){
            //Message message = new Message(i);
            try{        
                dataOut.writeBytes(message);
                dataOut.flush();
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
            System.out.println("Message has been sent: "+message);
        }
        else{
            System.out.println("You is not connected");
        }
    }

}
