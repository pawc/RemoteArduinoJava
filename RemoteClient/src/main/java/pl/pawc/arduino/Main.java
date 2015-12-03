package pl.pawc.arduino;

import pl.pawc.arduino.controller.Controller;

import javafx.application.Application; 
import javafx.application.Platform; 
import javafx.event.EventHandler; 
import javafx.fxml.FXMLLoader; 
import javafx.scene.Scene; 
import javafx.scene.layout.AnchorPane; 
import javafx.scene.layout.BorderPane; 
import javafx.stage.Stage; 
import javafx.stage.WindowEvent;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Platform.runLater(new Runnable() {
            public void run(){

                Controller controller = new Controller();
                FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("Frame.fxml"));
                loader.setController(controller);
                AnchorPane anchorPane = null;
                    try {
                        anchorPane = (AnchorPane) loader.load();
                    }
                    catch(IOException e){
                         e.printStackTrace();
                    }

                primaryStage.setTitle("Controlls");
                Scene scene = new Scene(anchorPane);

                scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                    if(controller.getConnected()){ 
                        switch(event.getCode().toString()){
                        case "LEFT" : controller.sendMessage(2); break;
                        case "RIGHT" : controller.sendMessage(3); break;
                        case "ENTER" : controller.sendMessage(4); break;
                        }
                    }
                    else{
                        System.out.println("You are not connected to the server");
                    }
                    System.out.println("Pressed: "+event.getCode());
                });

                primaryStage.setResizable(false);
                primaryStage.setScene(scene);
                primaryStage.show();
            }

        });
    
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }            
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
