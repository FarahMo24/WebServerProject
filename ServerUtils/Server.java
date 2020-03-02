package ServerUtils;

import ServerConfig.*;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public void start() {
		try {
			ServerSocket serverConnect = new ServerSocket(Integer.parseInt(Configuration.getPort()));
			Socket client;

			int counter = 0;

			while(true){
				counter++;
				client = serverConnect.accept();
				System.out.println(" >> " + "Client No:" + counter + " started!");
				ServerHelper helper = new ServerHelper();
				helper.RequestStart(client);
				client.close();
			}
		}catch (Exception e){
			ServerHelper.isServerError = true;
			e.printStackTrace();
		}

	}
}