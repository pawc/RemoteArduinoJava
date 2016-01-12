import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Listener extends Thread{

	private InputStreamReader isr;

	public Listener(InputStreamReader isr){
		this.isr=isr;
	}

	public void run(){
		try{
			BufferedReader bfr = new BufferedReader(isr);
			while(true){
				String line = bfr.readLine();
				System.out.println(line);
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
