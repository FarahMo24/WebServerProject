package ServerResponse;

import java.util.HashMap;
import java.util.ArrayList;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerConfig.Configuration;

public abstract class Response {

	public HashMap<String, String> responseValues;
	public HashMap<String, String> responseHeaders;
	public ArrayList<Byte> responseBody;

	protected Response() {
	}

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

	public Response(Request request) {
		responseValues = new HashMap<>();
		responseHeaders = new HashMap<>();
		responseValues.put("httpVersion", request.getHttpVersion());
		processRequest(request);
	}

	public abstract void processRequest(Request request);

}
