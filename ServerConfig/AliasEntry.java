package ServerConfig;

class AliasEntry extends ConfigEntry {
	AliasEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getAlias();
	}
}
