package ServerConfig;

class ServerConfigEntry extends ConfigEntry {

	ServerConfigEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getSettings();
	}
}
