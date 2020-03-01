package ResponseStatusCode;

import ServerResponse.*;
import ServerRequest.*;
import CGI.*;

public class ResponseCGI200Status extends Response {

	//TODO: Set standard server header info

	public ResponseCGI200Status(Request request) {
		super(request);
	}

	@Override
	public void processRequest(Request request) {
		CGI programExecutor = new CGI(request);
		responseHeaders = programExecutor.getHeaders();
		System.out.println("Response header size: " + responseHeaders.size());
		responseValues.put("STATUS_CODE", "200");
		responseValues.put("REASON_PHRASE", "OK");
		responseBody = programExecutor.getBody();
	}
}
