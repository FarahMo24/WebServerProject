package ResponseStatusCode;

import ServerConfig.Configuration;
import ServerRequest.Request;
import ServerResponse.ResourceSearch;
import ServerResponse.Response;

public class Response404Status extends Response {

	public Response404Status(Request request) {
		super(request);
		//processRequest(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("statusCode", "404");
		responseValues.put("reasonPhrase", "Not Found");
		responseHeaders.put("Content-Type", Configuration.getMime("html"));
		String dir = System.getProperty("user.dir");
		responseBody = ResourceSearch.readContents(dir + "/PrebuiltWebPages/404Error.html");
		responseHeaders.put("Content-Length", Integer.toString(responseBody.size()));
	}
}
