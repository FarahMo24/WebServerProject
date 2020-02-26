package ResponseStatusCode;

import ServerRequest.UriHandler;
import ServerResponse.ResourceSearch;
import ServerResponse.Response;
import ServerRequest.Request;

public class Response204Status extends Response {

	public Response204Status(Request request) {
		super(request);
		//processRequest(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("statusCode", "204");
		responseValues.put("reasonPhrase", "No Content");
		ResourceSearch.deleteFile(UriHandler.resolveURI(request.getURI()));
	}
}
