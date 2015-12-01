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

    public void initialize(){

        connectButton.setOnAction(event->{
            try{
                if(connectButton.getText().equals("Connect")){
                    serverConnection = new ServerConnection(6000, "kritsit.ddns.net");
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
        });

    }

}
