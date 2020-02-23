package ServerResponse;

import java.util.Map;

public class ResponseGenerator {

	public String responseOutput;
	public byte[] bodyOutput;

	public ResponseGenerator(Response response) {
		String firstLine = response.getHttpVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase();
		String headers = "";
		for (Map.Entry<String, String> header : response.getResponseHeaders().entrySet()) {
			headers = headers + header.getKey() + ": " + header.getValue() + "\n";
		}
		responseOutput = firstLine + "\n" + headers;
		bodyOutput = new byte[response.getResponseBody().size()];
		if(response.getResponseBody() != null) {
			for (int i = 0; i < response.getResponseBody().size(); i++) {
				bodyOutput[i] = (byte)response.getResponseBody().get(i);
			}
		}
	}

	public static String generateResponse(Response response) {
		String firstLine = response.getHttpVersion() + " " + response.getStatusCode() + " " + response.getReasonPhrase();
		String headers = "";
		for (Map.Entry<String, String> header : response.getResponseHeaders().entrySet()) {
			headers = headers + header.getKey() + ": " + header.getValue() + "\n";
		}
		String body = "";
		if(response.getResponseBody() != null) {
			for (Object element : response.getResponseBody()) {
				body = body + element;
			}
		}
		return firstLine + "\n" + headers + "\n" + body;
	}
}