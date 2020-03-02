package ServerRequest;

import ServerConfig.Configuration;
import java.io.*;

public class UriHandler {

	public UriHandler(String uri) {

	}

	public static String resolveURI(String uri){
		String lookup = parseAliasLookupKeyword(uri);
		String pathSuffix = "";
		String[] uriComponents = uri.split("/");
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
		if(isResourceScript(lookup)) {
			return Configuration.getPathForScriptAlias(lookup);
		} else if (Configuration.getPathForAlias(lookup) != null) {
			return Configuration.getPathForAlias(lookup);
		} else {
			if(Configuration.getDocumentRoot().endsWith("/") && lookup.startsWith("/")) {
				return Configuration.getDocumentRoot() + lookup.subSequence(1, lookup.length());
			} else {
				return Configuration.getDocumentRoot() + lookup;
			}
		}
	}

	private static String parseAliasLookupKeyword(String uri) {
		String lookup = "";
		String[] uriComponents = uri.split("/");
		if(uriComponents.length > 1) {
			lookup = "/" + uriComponents[1] + "/";
		}
		return lookup;
	}

	public static boolean isResourceScript(String uri) {
		String lookup = parseAliasLookupKeyword(uri);
		return Configuration.getPathForScriptAlias(lookup) != null;
	}
}