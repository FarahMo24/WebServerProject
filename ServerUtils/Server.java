package ServerUtils;

import ServerConfig.*;

import java.io.IOException;
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
		}catch (IOException e){
			System.out.println("Server Connection error: " + e.getMessage());
		}

	}
}