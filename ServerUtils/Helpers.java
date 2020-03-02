package ServerUtils;

import java.io.File;
import java.util.HashSet;

public class Helpers {

	public static HashSet<String> httpVerbs = new HashSet<>();
	public static HashSet<String> httpVersions = new HashSet<>();

	static {
		httpVerbs.add("GET");
		httpVerbs.add("PUT");
		httpVerbs.add("POST");
		httpVerbs.add("HEAD");
		httpVerbs.add("DELETE");

		httpVersions.add("HTTP/1.1");
		httpVersions.add("HTTP/1.2");
	}

	public static String removeOuterQuotes(String value) {
		String croppedString = value;
		if(value.startsWith("\"") && value.endsWith("\"")) {
			croppedString = croppedString.substring(1, croppedString.length() - 1);
		}
		return croppedString;
	}

	public static String getFileExtension(String filePath) {
		File f = new File(filePath);
		String name = f.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";
		}
		return name.substring(lastIndexOf + 1);
	}
}
