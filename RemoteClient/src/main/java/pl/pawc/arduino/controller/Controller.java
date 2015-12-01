package pl.pawc.arduino.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import pl.pawc.arduino.shared.Message;

import java.io.IOException;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class Controller{

    private @FXML Button connectButton;
    private @FXML RadioButton LEDswitch;

    private boolean connected;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Socket socket;

    public void initialize(){
    
        LEDswitch.setOnAction(event->{
            if(LEDswitch.isSelected()){
                sendMessage(1);
            }
            if(!LEDswitch.isSelected()){
                sendMessage(0);
            }
        });


        connectButton.setOnAction(event->{
          if(!connected){
            try{
                socket = new Socket("localhost", 443);
                objOut = new ObjectOutputStream(socket.getOutputStream());
                objOut.flush();
                objIn = new ObjectInputStream(socket.getInputStream());
                connected = true;
                connectButton.setText("Disconnect");
            }
            catch(IOException e){
                e.printStackTrace();
            }
          }
          else{
            try{
                objOut.close();
                objIn.close();
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

    public void sendMessage(int i){
        if(connected){
            Message message = new Message(i);
            try{        
                objOut.writeObject(message);
                objOut.flush();
            }
            catch(IOException e){
                e.printStackTrace();
                return;
            }
            System.out.println("Message has been sent: "+i);
        }
        else{
            System.out.println("You is not connected");
        }
    }

}
