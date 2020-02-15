package ServerUtils;

import java.util.HashMap;

public abstract class ConfigEntry {

	String keyword;
	String value;
	HashMap<String, String> configCategory;

	public String getKeyword() {
		return keyword;
	}

	public String getValue() {
		return value;
	}

	public HashMap<String, String> getConfigCategory() {
		return configCategory;
	}

	public void storeEntry() {
		if(keyword != "") {
			configCategory.put(keyword, value);
		}
	}
}
