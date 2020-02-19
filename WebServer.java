import ServerConfig.*;
import ServerUtils.*;
import java.io.*;


public class WebServer {

	public WebServer(){
		System.out.println("Web Server instance created");
	}

	public static void main(String[] args){

		WebServer webServer = new WebServer();
		//System.out.println(webServer.mimeTypeConfig.getMime("ez"));
		Server server = new Server();
		server.start();
	}

}