package ResponseStatusCode;

import ServerRequest.UriHandler;
import ServerResponse.ResourceOperation;
import ServerResponse.Response;
import ServerRequest.Request;
import java.io.IOException;

public class Response204Status extends Response {

	public Response204Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "204");
		responseValues.put("REASON_PHRASE", "No Content");
		ResourceOperation.deleteFile(UriHandler.resolveURI(request.getURI()));
	}
}
