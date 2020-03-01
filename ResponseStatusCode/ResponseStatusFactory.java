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
    } else if(isAuthorized(request)){
			// if Authorized check if it authenticated
			if(isAuthenticated){
				// Successful
				return new Response200Status(request);
			}else{
				// Forbidden error
				return new Response403Status(request);
			}
		}        
		else if(!isAuthorized()){
			// if not Authorized
			return new Response401Status(request);
      
		} else {
			return new Response200Status(request);
		}
		return new Response200Status(request);
	}
	
	private static boolean isAuthorized(Request request) throws FileNotFoundException, IOException {
		File htaccessFile = new File("htFiles/.htaccess");
		FileInputStream htaccess = new FileInputStream(htaccessFile);
		headers = new HashMap<>();
        
		for(HashMap.Entry<String,String> e: request.getHeaders().entrySet()){           
			headers.put(e.getKey(),e.getValue());
		}      
        
		byte[] data = new byte[(int) htaccessFile.length()];
        
		htaccess.read(data);        
		htaccess.close();

		String content = new String(data,"UTF-8");
        
		HtaccessHandler htaccessHandler = new HtaccessHandler(content);
		if(!isProtected(UriHandler.resolveURI(request.getURI()))){
			isAuthenticated = true;
			return true;
		}     
		else{
			if(htaccessHandler.require.equals("valid-user")){
				if(!headers.containsKey("Authorization")){
					return false;
				}
				else{
					String[] encryptedStringArray = headers.get("Authorization").split("\\s+");
					encryptedString = encryptedStringArray[encryptedStringArray.length-1];
					if(htaccessHandler.isAuthorized(encryptedString)){
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
        	File f = new File(path + "/.htaccess");

        	return f.exists();
    	}
	
	private static boolean fileExists(String filePath) {
		System.out.println("File Path: " + filePath);
		File f = new File(filePath);
		return f.exists();
	}
}
