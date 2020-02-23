package ServerConfig;

import ServerConfig.ConfigEntry;
import ServerConfig.Configuration;

class ServerConfigEntry extends ConfigEntry {

	ServerConfigEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getSettings();
	}
}
