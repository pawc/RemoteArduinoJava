package pl.pawc.arduino.shared;

import java.io.Serializable;

public class Message implements Serializable{

    private int i;

    public Message(int i){
        this.i=i;
    }

    public int getI(){
        return i;
    }

}
