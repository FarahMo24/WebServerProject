package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;
import ServerConfig.Configuration;
import ServerUtils.Helpers;
import java.io.IOException;

public class Response200Status extends Response {

	public Response200Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) throws IOException {
		responseValues.put("STATUS_CODE", "200");
		responseValues.put("REASON_PHRASE", "OK");
		responseHeaders.put("Content-Type", Configuration.getMime(Helpers.getFileExtension(UriHandler.resolveURI(request.getURI()))));
		responseBody = ResourceOperation.readContents(UriHandler.resolveURI(request.getURI()));
		responseHeaders.put("Content-Length", Integer.toString(responseBody.size()));
	}
}
