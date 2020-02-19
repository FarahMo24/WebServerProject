package ServerUtils;

import ServerConfig.*;
import ServerRequest.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;

public class ServerHelper {

	BufferedReader in;
	PrintWriter out;
	Socket connect = null;

	public ServerHelper(Socket accept) {

		this.connect = accept;
	}

	public void RequestStart(){

		try {
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));

			out = new PrintWriter(connect.getOutputStream(), true);

			// Parse Request
			RequestParser request = new RequestParser(in);
			System.out.println("Finished with Request");

			// Path Configuration
			UriHandler uriConfig = new UriHandler(request);

		} catch(Exception e) {
			System.out.println("InputStream errror");
		}
	}
}