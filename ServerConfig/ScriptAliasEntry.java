package ServerConfig;

class ScriptAliasEntry extends ConfigEntry {
	ScriptAliasEntry(String key, String value) {
		this.keyword = key;
		this.value = value;
		this.configCategory = Configuration.getScriptAlias();
	}
}
