package pl.pawc.arduino.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

import pl.pawc.arduino.shared.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.Socket;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

import pl.pawc.arduino.Listener;

public class Controller{

    private @FXML Button connectButton;
    private @FXML RadioButton LEDswitch;

    private boolean connected;
    private DataOutputStream dataOut;
    private BufferedReader bfr;
    private Socket socket;
	private Thread thread;


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
                BufferedReader bfr = new BufferedReader(new InputStreamReader((socket.getInputStream())));
				thread = new Thread(new Listener(bfr, this));
				thread.start();
                connected = true;
                connectButton.setText("Disconnect");
            }
            catch(IOException e){
                e.printStackTrace();
            }
          }
          else{
            try{
				thread.interrupt();
                dataOut.close();
                bfr.close();
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

	public void printTemperature(String temperature){

	}

}
