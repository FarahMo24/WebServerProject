package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;

public class Response500Status extends Response {

	public Response500Status(Request request) {
		super(request);
		//processRequest(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("statusCode", "500");
		responseValues.put("reasonPhrase", "Internal Server Error");
	}
}
