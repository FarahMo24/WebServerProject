package ServerUtils;

import ServerConfig.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	// TODO: Set default values in Configuration
	// Document Root
	public static String DOCUMENT_ROOT = null;

	// Server Root
	public static String  SERVER_ROOT = null;

	// Default port
	public static int DEFAULT_PORT = 8080;

	// Client connection via Socket Class
	public void start(){

		try {
			ServerSocket serverConnect = new ServerSocket(DEFAULT_PORT);
			Socket client = null;

			while(true){
				client = serverConnect.accept();
				System.out.println("New Request Received!");
				ServerHelper helper = new ServerHelper((client));
				helper.RequestStart();
				client.close();
			}

		}catch (IOException e){
			System.out.println("Server Connection error: " + e.getMessage());
		}

	}
}