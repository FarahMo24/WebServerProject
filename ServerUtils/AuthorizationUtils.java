package ServerUtils;

import ServerConfig.HtaccessHandler;
import ServerConfig.HtpasswdHandler;
import ServerRequest.Request;
import ServerRequest.UriHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class AuthorizationUtils {

	public static boolean isRequestAuthenticated(Request request) throws IOException, NoSuchAlgorithmException {
		String content = readHtAccessFileContent();
		HtaccessHandler htaccessHandler = new HtaccessHandler(content);
		if (!isFileAtPathProtected(UriHandler.resolveURI(request.getURI()))) {
			return true;
		} else {
			if (htaccessHandler.require.equals("valid-user")) {
				if (!request.getHeaders().containsKey("AUTHORIZATION")) {
					return false;
				} else {
					return isAuthorizationHeaderMatching(request);
				}
			} else {
				return true;
			}
		}
	}

	private static boolean isAuthorizationHeaderMatching(Request request) throws IOException, NoSuchAlgorithmException {
		String[] encryptedStringArray = request.getHeaders().get("AUTHORIZATION").split("\\s+");
		String encryptedString = encryptedStringArray[encryptedStringArray.length-1];
		if (HtpasswdHandler.isAuthorized(encryptedString)) {
			if (HtpasswdHandler.isAuthenticated(encryptedString)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private static String readHtAccessFileContent() throws IOException {
		String dir = System.getProperty("user.dir");
		File htaccessFile = new File(dir + "/htFiles/.htaccess");
		FileInputStream htaccess = new FileInputStream(htaccessFile);
		byte[] data = new byte[(int) htaccessFile.length()];
		htaccess.read(data);
		htaccess.close();
		String content = "";
		for(byte dataElement : data) {
			content = content + (char) dataElement;
		}
		return content;
	}

	private static boolean isFileAtPathProtected(String path){
		String htaccessPath = path.subSequence(0, path.lastIndexOf("/")).toString();
		File f = new File(htaccessPath + "/.htaccess");
		return f.exists();
	}
}
