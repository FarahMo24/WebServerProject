package ServerUtils;

import CGI.*;
import ServerRequest.*;
import ServerResponse.*;
import ResponseStatusCode.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class ServerHelper extends Thread {

	private static Socket connect;
	public static boolean isServerError = false;

	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			PrintWriter out = new PrintWriter(connect.getOutputStream(), true);
			Request request = new Request(in);
			if(UriHandler.isResourceScript(request.getURI())) {
				CGI executor = new CGI(request);
			}
			Response response = ResponseStatusFactory.createResponse(request);
			Logger.outputLog(request, response);
			sendResponse(response);
		} catch(Exception e) {
			isServerError = true;
			e.printStackTrace();
		}
	}

	public void RequestStart(Socket accept){
		connect = accept;
		run();
	}

	public static void sendResponse(Response response) throws IOException {
		ResponseGenerator outputValues = new ResponseGenerator(response);
		PrintWriter out = new PrintWriter(connect.getOutputStream(), true);
		out.println(outputValues.responseOutput);
		if(outputValues.bodyOutput != null) {
			connect.getOutputStream().write(outputValues.bodyOutput);
		}
	}
}