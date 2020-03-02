package ResponseStatusCode;

import ServerRequest.Request;
import ServerResponse.*;

public class Response401Status extends Response {

	public Response401Status(Request request) {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "401");
		responseValues.put("REASON_PHRASE", "Unauthorized");
		responseHeaders.put("WWW-Authenticate", "Basic");
	}
}
