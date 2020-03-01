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
		responseValues.put("STATUS_CODE", "404");
		responseValues.put("REASON_PHRASE", "Not Found");
		responseHeaders.put("CONTENT_TYPE", Configuration.getMime("html"));
		String dir = System.getProperty("user.dir");
		responseBody = ResourceSearch.readContents(dir + "/PrebuiltWebPages/404Error.html");
		responseHeaders.put("Content-Length", Integer.toString(responseBody.size()));
	}
}
