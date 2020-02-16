package ServerUtils;

public class ConfigParser {

	private ConfigEntry setting;

	public ConfigParser(String line) {
		String[] splitLine = line.split("\"");
		String value = "";
		if(splitLine.length > 1) {
			value = Helpers.removeOuterQuotes(splitLine[1]);
		}
		String[] keywords = splitLine[0].split("\\s+");
		setting = createConfigEntry(keywords, value);
	}

	private ConfigEntry createConfigEntry(String[] keywords, String value) {
		ConfigEntry entry;
		if(keywords[0].equals("ScriptAlias")) {
			entry = new ScriptAliasEntry(keywords[1], value);
		} else if(keywords[0].equals("Alias")) {
			entry = new AliasEntry(keywords[1], value);
		} else if(keywords[0].startsWith("#")) {
			entry = new ServerConfigEntry("", "");
		} else if(value.equals("")) {
			entry = new ServerConfigEntry(keywords[0], keywords[1]);
		} else {
			entry = new ServerConfigEntry(keywords[0], value);
		}
		return entry;
	}

	public ConfigEntry getSetting() {
		return setting;
	}
}
