package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;
import java.io.IOException;

public class Response500Status extends Response {

	public Response500Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "500");
		responseValues.put("REASON_PHRASE", "Internal Server Error");
	}
}
