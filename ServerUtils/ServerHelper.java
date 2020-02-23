package ServerUtils;

import ServerRequest.*;
import ServerResponse.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

class ServerHelper {

	private final Socket connect;

	ServerHelper(Socket accept) {
		this.connect = accept;
	}

	public void RequestStart(){

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			PrintWriter out = new PrintWriter(connect.getOutputStream(), true);

			Request request = new Request(in);
			Response response = new Response(request);
			ResponseGenerator outputValues = new ResponseGenerator(response);
			sendResponse(connect, outputValues.responseOutput, outputValues.bodyOutput);

		} catch(Exception e) {
			System.out.println("InputStream error");
		}
	}

	public void sendResponse(Socket client, String output, byte[] bodyOutput) throws IOException {
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		out.println(output);
		client.getOutputStream().write(bodyOutput);
		System.out.println("I sent:\n" + output);
		System.out.println("Body:");
		for(byte element : bodyOutput) {
			System.out.print(element);
		}
		System.out.println("\n");
	}
}