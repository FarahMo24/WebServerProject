package ServerUtils;

import ServerConfig.*;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public void start(){

		try {
			ServerSocket serverConnect = new ServerSocket(Integer.parseInt(Configuration.getPort()));
			Socket client;

			while(true){
				client = serverConnect.accept();
				ServerHelper helper = new ServerHelper();
				helper.RequestStart(client);
				client.close();
			}
		}catch (Exception e){
			System.out.println("Server Connection error: " + e.getMessage());
		}

	}
}