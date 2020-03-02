package ResponseStatusCode;

import ServerConfig.*;
import ServerResponse.Response;
import ServerRequest.Request;
import ServerRequest.UriHandler;
import java.io.*;
import java.util.HashMap;

public class ResponseStatusFactory {

	//TODO: Handle If-Modified-Since
	//TODO: Handle Required headers

	private static HashMap<String, String> headers;
	private static boolean isAuthenticated = false;
	private static String encryptedString;

	public static Response createResponse(Request request) throws IOException {
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
        } else if(isAuthorized(request)){
			if(isAuthenticated){
				return new Response200Status(request);
			}else{
				return new Response403Status(request);
			}
		}
		else if(!isAuthorized(request)){
			return new Response401Status(request);
		} else {
			return new Response200Status(request);
		}
	}
	
	private static boolean isAuthorized(Request request) throws FileNotFoundException, IOException {
		String dir = System.getProperty("user.dir");
		File htaccessFile = new File(dir + "/htFiles/.htaccess");
		FileInputStream htaccess = new FileInputStream(htaccessFile);
		headers = new HashMap<>();
        
		for(HashMap.Entry<String,String> e: request.getHeaders().entrySet()){
			//System.out.println(e.getKey());
			headers.put(e.getKey(),e.getValue());
		}

		byte[] data = new byte[(int) htaccessFile.length()];
		htaccess.read(data);        
		htaccess.close();
		String content = "";
		for(int i = 0; i < data.length; i++) {
			content = content + (char) data[i];
		}
		HtaccessHandler htaccessHandler = new HtaccessHandler(content);
		if(!isProtected(UriHandler.resolveURI(request.getURI()))){
			isAuthenticated = true;
			return true;
		} else {
			System.out.println("Looking for valid user!--------------------");
			if(htaccessHandler.require.equals("valid-user")){
				System.out.println("Valid user found!--------------------");
				if(!headers.containsKey("Authorization")){
					return false;
				} else {
					System.out.println("Headers contain authorization header");
					String[] encryptedStringArray = headers.get("Authorization").split("\\s+");
					System.out.println(encryptedStringArray[0]);
					System.out.println(encryptedStringArray[1]);
					encryptedString = encryptedStringArray[encryptedStringArray.length-1];
					if(htaccessHandler.isAuthorized(encryptedString)){
						System.out.println("HtaccessHandler Authorized");
						if(htaccessHandler.isAuthenticated(encryptedString)){
							isAuthenticated = true;
						}else{
							isAuthenticated = false;
						}
						return true;
					}
					else{
						return false;
					}
				}
			}
			else{
				isAuthenticated = true;
				return true;
			}
		}
	}

	private static boolean isProtected(String path){
		String htaccessPath = path.subSequence(0, path.lastIndexOf("/")).toString();
		File f = new File(htaccessPath + "/.htaccess");
		return f.exists();
	}
	
	private static boolean fileExists(String filePath) {
		System.out.println("File Path: " + filePath);
		File f = new File(filePath);
		return f.exists();
	}
}
