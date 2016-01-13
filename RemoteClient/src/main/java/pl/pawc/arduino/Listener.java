package pl.pawc.arduino;

import java.io.BufferedReader;
import java.io.IOException;
import pl.pawc.arduino.controller.Controller;

public class Listener extends Thread{

	private BufferedReader bfr;
	private Controller controller;

	public Listener(BufferedReader bfr, Controller controller){
		this.bfr=bfr;
		this.controller=controller;
	}

	public void run(){
		try{
			while(true){
				String line = bfr.readLine();
				if(line==null) break;
				String[] split = line.split(":");
				controller.printTemperature(split[1]);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
