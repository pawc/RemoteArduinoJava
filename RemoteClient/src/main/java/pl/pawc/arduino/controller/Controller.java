package pl.pawc.arduino.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import pl.pawc.arduino.client.ServerConnection;

import java.io.IOException;

public class Controller{

    @FXML Button connectButton;
    @FXML RadioButton LEDswitch;
    private ServerConnection serverConnection;
    private boolean connected;

    public Controller(){
    serverConnection = null;
    }

    public void initialize(){
    
        LEDswitch.setOnAction(event->{
            try{
                serverConnection.isConnected();
            }
            catch(NullPointerException e){
                System.out.println("You are not connected to the server");
                LEDswitch.setSelected(false);
                return;
            }
            if(!serverConnection.isConnected()) return;
            if(LEDswitch.isSelected()){
                serverConnection.sendMessage(1);
            }
            if(!LEDswitch.isSelected()){
                serverConnection.sendMessage(0);
            }
        });


        connectButton.setOnAction(event->{
            try{
                if(connectButton.getText().equals("Connect")){
                    serverConnection = new ServerConnection(443, "localhost");
                    connectButton.setText("Disconnect");
                }
                if(connectButton.getText().equals("Disconnect")){
                    serverConnection = null;
                    connectButton.setText("Connect");
                }
            }
            catch(IOException e){
                e.printStackTrace();   
            }
            catch(Exception e){
                e.printStackTrace();
            }            
        });

    }

}
