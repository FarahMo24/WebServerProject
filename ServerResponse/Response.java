package ServerResponse;

import java.util.HashMap;
import java.util.ArrayList;
import ServerRequest.Request;

public abstract class Response {

	public HashMap<String, String> responseValues;
	public HashMap<String, String> responseHeaders;
	public ArrayList<Byte> responseBody;

	protected Response() {
	}

	public String getHttpVersion() {
		return responseValues.get("HTTP_PROTOCOL");
	}

	public String getStatusCode() {
		return responseValues.get("STATUS_CODE");
	}

	public String getReasonPhrase() {
		return responseValues.get("REASON_PHRASE");
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
		responseValues.put("HTTP_PROTOCOL", request.getHttpVersion());
		processRequest(request);
	}

	public abstract void processRequest(Request request);

}
