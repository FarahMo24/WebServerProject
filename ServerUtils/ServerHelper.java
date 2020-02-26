package ServerUtils;

import ServerRequest.*;
import ServerResponse.*;
import ResponseStatusCode.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class ServerHelper {

	private static Socket connect;

	public void RequestStart(Socket accept){

		try {
			connect = accept;
			BufferedReader in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			PrintWriter out = new PrintWriter(connect.getOutputStream(), true);

			Request request = new Request(in);
			Response response = ResponseStatusFactory.createResponse(request); //Call Factory Method to determine response
			sendResponse(response);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendResponse(Response response) throws IOException {
		ResponseGenerator outputValues = new ResponseGenerator(response);

		PrintWriter out = new PrintWriter(connect.getOutputStream(), true);
		out.println(outputValues.responseOutput);
		if(outputValues.bodyOutput != null) {
			connect.getOutputStream().write(outputValues.bodyOutput);
			System.out.println("I sent:\n" + outputValues.responseOutput);
			System.out.println("Body:");
			for (byte element : outputValues.bodyOutput) {
				System.out.print(element);
			}
		}
		System.out.println("\n");
	}
}