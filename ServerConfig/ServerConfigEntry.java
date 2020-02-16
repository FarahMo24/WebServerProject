package ServerConfig;

import ServerConfig.ConfigEntry;
import ServerConfig.Configuration;

public class ServerConfigEntry extends ConfigEntry {

	public ServerConfigEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getSettings();
	}
}
