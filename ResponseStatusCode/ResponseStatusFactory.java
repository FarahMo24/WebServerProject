package ResponseStatusCode;

import ServerResponse.Response;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import java.io.File;

public class ResponseStatusFactory {

	//TODO: Handle If-Modified-Since
	//TODO: Handle Required headers

	public static Response createResponse(Request request) {
		if(request.isBadRequest) {
			return new Response400Status(request);
		} else if (!fileExists(UriHandler.resolveURI(request.getURI())) && request.getHttpMethod().equals("PUT")) {
			return new Response201Status(request);
		} else if (!fileExists(UriHandler.resolveURI(request.getURI()))) {
			return new Response404Status(request);
		} else if (request.getHttpMethod().equals("DELETE")) {
			return new Response204Status(request);
		} else if (request.getHttpMethod().equals("HEAD")) {
			return new Response304Status(request);
		} else if(UriHandler.isResourceScript(request.getURI())) {
			return new ResponseCGI200Status(request);
		} else {
			return new Response200Status(request);
		}
	}

	private static boolean fileExists(String filePath) {
		System.out.println("File Path: " + filePath);
		File f = new File(filePath);
		return f.exists();
	}
}
