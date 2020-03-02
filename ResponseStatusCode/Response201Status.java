package ResponseStatusCode;

import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerResponse.ResourceOperation;
import ServerResponse.Response;
import java.io.IOException;

public class Response201Status extends Response {

	public Response201Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) throws IOException {
		responseValues.put("STATUS_CODE", "201");
		responseValues.put("REASON_PHRASE", "Created");
		ResourceOperation.createFile(UriHandler.resolveURI(request.getURI()), request.getBody());
	}
}
