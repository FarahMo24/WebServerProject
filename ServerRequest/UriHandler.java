package ServerRequest;

import ServerConfig.Configuration;
import java.io.*;

public class UriHandler {

	static private Boolean isResourceScript = false;

	public UriHandler(String uri) {

	}

	public static String resolveURI(String uri){
		String lookup = "";
		String pathSuffix = "";
		String[] uriComponents = uri.split("/");
		if(uriComponents.length > 1) {
			lookup = "/" + uriComponents[1] + "/";
		}
		if(uriComponents.length > 2) {
			pathSuffix = uriComponents[2];
		}
		String pathPrefix = resolveAlias(lookup);
		String fullPath = pathPrefix + pathSuffix;
		return appendDirectoryIndex(fullPath);
	}

	private static String appendDirectoryIndex(String fullPath) {
		File f = new File(fullPath);
		if(f.exists() && f.isDirectory()) {
			if(!fullPath.endsWith("/")) {
				fullPath = fullPath + "/";
			}
			fullPath = fullPath + Configuration.getDirectoryIndex();
		}
		return fullPath;
	}

	private static String resolveAlias(String lookup) {
		if(Configuration.getPathForScriptAlias(lookup) != null) {
			isResourceScript = true;
			return Configuration.getPathForScriptAlias(lookup);
		} else if (Configuration.getPathForAlias(lookup) != null) {
			return Configuration.getPathForAlias(lookup);
		} else {
			return Configuration.getDocumentRoot();
		}
	}
}