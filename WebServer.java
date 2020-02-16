//Entry point for Web Server
import ServerConfig.Configuration;

public class WebServer {

	public WebServer() {
		System.out.println(Configuration.getPathForAlias("/ab/"));
		System.out.println(Configuration.getPathForScriptAlias("/cgi-bin/"));
		System.out.println(Configuration.getMime("html"));
	}

	public static void main(String[] args) {
		WebServer server = new WebServer();
	}
}
