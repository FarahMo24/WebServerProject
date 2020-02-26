package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;

public class Response400Status extends Response {

	public Response400Status(Request request) {
		super(request);
		//processRequest(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("statusCode", "400");
		responseValues.put("reasonPhrase", "Bad Request");
	}

}
