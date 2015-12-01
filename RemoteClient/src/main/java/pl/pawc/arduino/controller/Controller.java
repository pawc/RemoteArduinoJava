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
    private boolean connected = false;

    public void initialize(){
    
        LEDswitch.setOnAction(event->{
            if(!connected){
            System.out.println("You are not connected to the server");
            LEDswitch.setSelected(false);
            return;
            }
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
                    serverConnection = new ServerConnection(6000, "kritsit.ddns.net");
                    connectButton.setText("Disconnect");
                    connected = true;
                }
                if(connectButton.getText().equals("Disconnect")){
                    serverConnection = null;
                    connectButton.setText("Connect");
                    connected = false;
                }
            }
            catch(IOException e){
                e.printStackTrace();   
            }
        });

    }

}
