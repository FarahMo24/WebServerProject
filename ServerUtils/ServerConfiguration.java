package ServerUtils;
import java.io.*;
import java.util.*;

public class ServerConfiguration {

	HashMap<String, String> settings;
	HashMap<String, HashMap<String, String>> alias;

	public ServerConfiguration(File configFile) {
		createSettingsFromFile(configFile);
	}

	public String getServerRoot() {
		return settings.get("ServerRoot");
	}

	public String getDocumentRoot() {
		return settings.get("DocumentRoot");
	}

	public String getPort() {
		return settings.get("Listen");
	}

	public String getLogFilePath() {
		return settings.get("LogFile");
	}

	public HashMap<String, String> getScriptAlias() {
		return alias.get("ScriptAlias");
	}

	public HashMap<String, String> getAlias() {
		return alias.get("Alias");
	}

	private void createSettingsFromFile(File configFile) {
		//TODO: Get rid of quotes around strings in config file
		HashMap<String, String> configSettings = new HashMap<>();
		configSettings.put("ServerRoot", "");
		configSettings.put("DocumentRoot", "");
		configSettings.put("Listen", "");
		configSettings.put("LogFile", "");

		HashMap<String, HashMap<String, String>> aliasSettings = new HashMap<>();
		aliasSettings.put("ScriptAlias", new HashMap<>());
		aliasSettings.put("Alias", new HashMap<>());

		try {
			Scanner fileReader = new Scanner(configFile);
			while(fileReader.hasNext()) {
				String keyword = fileReader.next();
				if(configSettings.containsKey(keyword)) {
					configSettings.put(keyword, Helpers.removeOuterQuotes(fileReader.next()));
				}
				if(aliasSettings.containsKey(keyword)) {
					String pathAlias = fileReader.next();
					String absolutePath = fileReader.next();
					HashMap<String, String> pathLookup = aliasSettings.get(keyword);
					pathLookup.put(pathAlias, Helpers.removeOuterQuotes(absolutePath));
					aliasSettings.put(keyword, pathLookup);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading configuration file");
		}

		this.settings = configSettings;
		this.alias = aliasSettings;
	}
}
