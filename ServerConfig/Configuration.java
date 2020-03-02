package ServerConfig;

import ServerUtils.ServerHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class Configuration {

	private static final ServerConfiguration serverConfig = new ServerConfiguration();
		private static final ServerMimeMap mimeMap = new ServerMimeMap(new File(System.getProperty("user.dir") + "/conf/mime.types"));

	static {
		try {
			String dir = System.getProperty("user.dir");
			serverConfig.createSettingsFromFile(Paths.get(dir + "/conf/httpd.conf"));
		} catch(IOException e) {
			ServerHelper.isServerError = true;
			e.printStackTrace();
		}
	}

	public static String getMime(String ext) {
		return mimeMap.getMime(ext);
	}

	public static String getServerRoot() {
		return serverConfig.getServerRoot();
	}

	public static String getDocumentRoot() {
		return serverConfig.getDocumentRoot();
	}

	public static String getDirectoryIndex() {
		return serverConfig.getDirectoryIndex();
	}

	public static String getAccessFile() {
		return serverConfig.getAccessFile();
	}

	public static String getPort() {
		return serverConfig.getPort();
	}

	public static String getLogFilePath() {
		return serverConfig.getLogFilePath();
	}

	public static String getPathForAlias(String alias) {
		return serverConfig.getPathForAlias(alias);
	}

	public static String getPathForScriptAlias(String scriptAlias) {
		return serverConfig.getPathForScriptAlias(scriptAlias);
	}

	public static HashMap<String, String> getSettings() {
		return serverConfig.getSettings();
	}

	public static HashMap<String, String> getScriptAlias() {
		return serverConfig.getScriptAlias();
	}

	public static HashMap<String, String> getAlias() {
		return serverConfig.getAlias();
	}

	private static class ServerConfiguration {

		private final HashMap<String, String> settings;
		private final HashMap<String, String> alias;
		private final HashMap<String, String> scriptAlias;

		ServerConfiguration() {
			settings = new HashMap<>();
			settings.put("DirectoryIndex", "index.html");
			settings.put("Listen", "8080");
			settings.put("AccessFile", ".htaccess");
			alias = new HashMap<>();
			scriptAlias = new HashMap<>();
		}

		String getAccessFile() {
			return settings.get("AccessFile");
		}

		String getDirectoryIndex() {
			return settings.get("DirectoryIndex");
		}

		String getServerRoot() {
			return settings.get("ServerRoot");
		}

		String getDocumentRoot() {
			return settings.get("DocumentRoot");
		}

		String getPort() {
			return settings.get("Listen");
		}

		String getLogFilePath() {
			return settings.get("LogFile");
		}

		HashMap<String, String> getSettings() {
			return settings;
		}

		HashMap<String, String> getScriptAlias() {
			return scriptAlias;
		}

		HashMap<String, String> getAlias() {
			return alias;
		}

		String getPathForAlias(String alias) {
			return getAlias().get(alias);
		}

		String getPathForScriptAlias(String scriptAlias) {
			return getScriptAlias().get(scriptAlias);
		}

		void createSettingsFromFile(Path configPath) throws IOException {
			List<String> fileLines = Files.readAllLines(configPath, Charset.defaultCharset());
			for(String line : fileLines) {
				ConfigEntry setting = new ConfigParser(line).getSetting();
				setting.storeEntry();
			}
		}
	}

	private static class ServerMimeMap {

		private HashMap<String, String> mapExt;

		ServerMimeMap(File mimeFile) {
			try {
				createMapping(mimeFile);
			} catch(IOException e) {
				ServerHelper.isServerError = true;
				e.printStackTrace();
			}
		}

		String getMime(String ext){
			return mapExt.get(ext);
		}

		private void createMapping(File mimeFile) throws IOException {
			HashMap<String,String> extMap = new HashMap<>();
			Scanner fileReader = new Scanner(mimeFile);
			while(fileReader.hasNext()) {
				String str = fileReader.nextLine();
				if (str.startsWith("#") || str.startsWith(" ")) {
					continue;
				}
				String mimeType;
				String[] splitArr = str.split("\\s+");
				mimeType = splitArr[0];
				for(int i = 1; i < splitArr.length;i++){
					extMap.put(splitArr[i],mimeType);
				}
			}
			this.mapExt = extMap;
		}
	}
}
