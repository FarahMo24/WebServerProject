package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;
import java.io.IOException;

public class Response400Status extends Response {

	public Response400Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "400");
		responseValues.put("REASON_PHRASE", "Bad Request");
	}

}
