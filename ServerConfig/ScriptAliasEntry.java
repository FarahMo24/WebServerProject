package ServerConfig;

import ServerConfig.ConfigEntry;
import ServerConfig.Configuration;

public class ScriptAliasEntry extends ConfigEntry {
	public ScriptAliasEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getScriptAlias();
	}
}
