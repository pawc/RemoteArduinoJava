import java.net.Socket;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class TestClient{

	public static void main(String args[]){
		
		try{
			Socket socket = new Socket(args[0], 6000);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.flush();
			InputStreamReader isr = new InputStreamReader(socket.getInputStream());
			Listener listener = new Listener(isr);
			new Thread(listener).start();
			Scanner sc = new Scanner(System.in);
			while(true){
				String line = sc.nextLine();
				dos.writeBytes(line+"\n");
				dos.flush();
			}

		}
		catch(IOException e){
			e.printStackTrace();
		}

	}

}
