package ResponseStatusCode;

import ServerConfig.Configuration;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerResponse.*;
import ServerUtils.Helpers;

public class Response403Status extends Response {

	public Response403Status(Request request) {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "413");
		responseValues.put("REASON_PHRASE", "Forbidden");
	}


}
