package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;
import ServerConfig.Configuration;
import ServerUtils.Helpers;

public class Response200Status extends Response {

	public Response200Status(Request request) {
		super(request);
		//processRequest(request);
	}

	@Override
	public void processRequest(Request request) {
		responseValues.put("STATUS_CODE", "200");
		responseValues.put("REASON_PHRASE", "OK");
		responseHeaders.put("Content-Type", Configuration.getMime(Helpers.getFileExtension(UriHandler.resolveURI(request.getURI()))));
		System.out.println("File Extension: " + Helpers.getFileExtension(UriHandler.resolveURI(request.getURI())));
		responseBody = ResourceSearch.readContents(UriHandler.resolveURI(request.getURI()));
		responseHeaders.put("Content-Length", Integer.toString(responseBody.size()));
	}
}
