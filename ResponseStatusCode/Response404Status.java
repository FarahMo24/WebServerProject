package ResponseStatusCode;

import ServerConfig.Configuration;
import ServerRequest.Request;
import ServerResponse.ResourceOperation;
import ServerResponse.Response;
import java.io.IOException;

public class Response404Status extends Response {

	public Response404Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) throws IOException {
		responseValues.put("STATUS_CODE", "404");
		responseValues.put("REASON_PHRASE", "Not Found");
		responseHeaders.put("CONTENT_TYPE", Configuration.getMime("html"));
		String dir = System.getProperty("user.dir");
		responseBody = ResourceOperation.readContents(dir + "/PrebuiltWebPages/404Error.html");
		responseHeaders.put("Content-Length", Integer.toString(responseBody.size()));
	}
}
