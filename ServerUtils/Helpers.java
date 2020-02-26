package ServerUtils;

import java.io.File;

public class Helpers {

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
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}
}
