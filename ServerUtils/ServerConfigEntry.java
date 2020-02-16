package ServerUtils;

import java.util.HashMap;

public class ServerConfigEntry extends ConfigEntry {

	public ServerConfigEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getSettings();
	}
}
