package ResponseStatusCode;

import ServerRequest.BadRequest;
import ServerResponse.Response;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import ServerUtils.AuthorizationUtils;
import ServerUtils.ServerHelper;

import java.io.*;
import java.security.NoSuchAlgorithmException;

public class ResponseStatusFactory {

	public static Response createResponse(Request request) throws IOException,InterruptedException, NoSuchAlgorithmException {

		if(ServerHelper.isServerError) {
			ServerHelper.isServerError = false;
			return new Response500Status(request);
		} else if(BadRequest.isBadRequest) {
			BadRequest.isBadRequest = false;
			return new Response400Status(request);
		} else if (!fileExists(UriHandler.resolveURI(request.getURI())) && !request.getHttpMethod().equals("PUT")) {
			return new Response404Status(request);
		} else if(AuthorizationUtils.isRequestAuthenticated(request)) {
			if (request.getHttpMethod().equals("PUT")) {
				return new Response201Status(request);
			} else if (request.getHttpMethod().equals("HEAD")) {
				return new Response304Status(request);
			} else if (request.getHttpMethod().equals("DELETE")) {
				return new Response204Status(request);
			} else if (UriHandler.isResourceScript(request.getURI())) {
				return new ResponseCGI200Status(request);
			} else {
				return new Response200Status(request);
			}
		} else if (request.getHeaders().containsKey("AUTHORIZATION")) {
			return new Response403Status(request);
		} else {
			return new Response401Status(request);
		}
	}
	
	private static boolean fileExists(String filePath) {
		File f = new File(filePath);
		return f.exists();
	}
}
