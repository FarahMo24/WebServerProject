package ResponseStatusCode;

import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerResponse.ResourceSearch;
import ServerResponse.Response;

public class Response304Status extends Response {

	public Response304Status(Request request) {
		super(request);
		//processRequest(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("statusCode", "304");
		responseValues.put("reasonPhrase", "Not Modified");
		responseHeaders.put("Last-Modified", ResourceSearch.lastModified(UriHandler.resolveURI(request.getURI())));
	}
}
