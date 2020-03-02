package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;
import CGI.*;
import java.io.IOException;

public class ResponseCGI200Status extends Response {

	public ResponseCGI200Status(Request request) throws IOException, InterruptedException {
		super(request);
	}

	@Override
	public void processRequest(Request request) throws IOException, InterruptedException {
		CGI programExecutor = new CGI(request);
		responseHeaders = programExecutor.getHeaders();
		responseValues.put("STATUS_CODE", "200");
		responseValues.put("REASON_PHRASE", "OK");
		responseBody = programExecutor.getBody();
	}
}
