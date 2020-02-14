
//Entry point for Web Server
import ServerUtils.*;
import java.io.*;

public class WebServer {

	private ServerConfiguration config;
	private ServerMimeMap mimeType;

	public WebServer() {
		this.config = new ServerConfiguration(new File("./conf/httpd.conf"));
		this.mimeType = new ServerMimeMap(new File("./conf/mime.types"));

	}

	public static void main(String[] args) {
		WebServer server = new WebServer();
	}
}
