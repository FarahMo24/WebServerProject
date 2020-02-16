package ServerUtils;

public class Helpers {

	public static String removeOuterQuotes(String value) {
		String croppedString = value;
		if(value.startsWith("\"") && value.endsWith("\"")) {
			croppedString = croppedString.substring(1, croppedString.length() - 1);
		}
		return croppedString;
	}
}
