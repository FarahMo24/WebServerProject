package ResponseStatusCode;

import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerResponse.ResourceSearch;
import ServerResponse.Response;

public class Response201Status extends Response {

	public Response201Status(Request request) {
		super(request);
		//processRequest(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("statusCode", "201");
		responseValues.put("reasonPhrase", "Created");
		ResourceSearch.createFile(UriHandler.resolveURI(request.getURI()), request.getBody());
	}
}
