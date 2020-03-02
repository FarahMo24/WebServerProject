package ResponseStatusCode;

import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerResponse.ResourceOperation;
import ServerResponse.Response;
import java.io.IOException;

public class Response304Status extends Response {

	public Response304Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "304");
		responseValues.put("REASON_PHRASE", "Not Modified");
		responseHeaders.put("LAST_MODIFIED", ResourceOperation.lastModified(UriHandler.resolveURI(request.getURI())));
	}
}
