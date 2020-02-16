package ServerConfig;

public class AliasEntry extends ConfigEntry {
	public AliasEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getAlias();
	}
}
