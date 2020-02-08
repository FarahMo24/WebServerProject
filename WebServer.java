//Entry point for Web Server
import ServerUtils.*;
import java.io.*;

public class WebServer {

	private ServerConfiguration config;

	public WebServer() {
		this.config = new ServerConfiguration(new File("./conf/httpd.conf"));
	}

	public static void main(String[] args) {
		WebServer server = new WebServer();
	}
}