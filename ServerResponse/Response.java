package ServerResponse;

import java.util.HashMap;
import java.util.ArrayList;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerConfig.Configuration;

public class Response {

	private HashMap<String, String> responseValues;
	private HashMap<String, String> responseHeaders;
	private ArrayList<Byte> responseBody;

	public String getHttpVersion() {
		return responseValues.get("httpVersion");
	}

	public String getStatusCode() {
		return responseValues.get("statusCode");
	}

	public String getReasonPhrase() {
		return responseValues.get("reasonPhrase");
	}

	public HashMap<String, String> getResponseHeaders() {
		return responseHeaders;
	}

	public ArrayList getResponseBody() {
		return responseBody;
	}

	public Response(Request httpRequest) {
		responseValues = new HashMap<>();
		responseHeaders = new HashMap<>();
		if(httpRequest.getHttpMethod().equals("GET")) {
			responseBody = ResourceSearch.readContents(UriHandler.resolveURI(httpRequest.getURI()));
		}

		responseValues.put("httpVersion", httpRequest.getHttpVersion());
		responseValues.put("statusCode", "200");
		responseValues.put("reasonPhrase", "OK");
		if(responseBody != null) {
			responseHeaders.put("Content-Length", Integer.toString(responseBody.size()));
		}
		responseHeaders.put("Content-Type", Configuration.getMime("html"));
	}

}
