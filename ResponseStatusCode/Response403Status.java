package ResponseStatusCode;

import ServerRequest.Request;
import ServerResponse.*;
import java.io.IOException;

public class Response403Status extends Response {

	public Response403Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "413");
		responseValues.put("REASON_PHRASE", "Forbidden");
	}


}
