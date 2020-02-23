import ServerUtils.*;


@SuppressWarnings("WeakerAccess")
public class WebServer {

	public WebServer(){
		System.out.println("Web Server instance created");
	}

	public static void main(String[] args){
		System.out.println("Starting point");
		Server server = new Server();
		server.start();
	}

}