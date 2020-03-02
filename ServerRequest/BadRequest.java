package ServerRequest;

import ServerUtils.Helpers;

public class BadRequest {

	public static boolean isBadRequest = false;

	public static boolean isBadFirstLine(String line) {
		String[] lineSplit = line.split("\\s+");
		if(lineSplit.length != 3) {
			isBadRequest = true;
			return true;
		} else {
			if(!Helpers.httpVerbs.contains(lineSplit[0])) {
				isBadRequest = true;
				return true;
			}
			if(!Helpers.httpVersions.contains(lineSplit[2])) {
				isBadRequest = true;
				return true;
			}
			return false;
		}
	}
}
