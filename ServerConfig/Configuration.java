package ServerConfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class Configuration {

	private static ServerConfiguration serverConfig = new ServerConfiguration();
	private static ServerMimeMap mimeMap = new ServerMimeMap(new File("./conf/mime.types"));

	static {
		serverConfig.createSettingsFromFile(Paths.get("./conf/httpd.conf"));
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

		private HashMap<String, String> settings;
		private HashMap<String, String> alias;
		private HashMap<String, String> scriptAlias;

		public ServerConfiguration() {
			settings = new HashMap();
			alias = new HashMap();
			scriptAlias = new HashMap();
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

		public HashMap<String, String> getSettings() {
			return settings;
		}

		public HashMap<String, String> getScriptAlias() {
			return scriptAlias;
		}

		public HashMap<String, String> getAlias() {
			return alias;
		}

		public String getPathForAlias(String alias) {
			return getAlias().get(alias);
		}

		public String getPathForScriptAlias(String scriptAlias) {
			return getScriptAlias().get(scriptAlias);
		}

		public void createSettingsFromFile(Path configPath) {
			try {
				List<String> fileLines = Files.readAllLines(configPath, Charset.defaultCharset());
				for(String line : fileLines) {
					ConfigEntry setting = new ConfigParser(line).getSetting();
					setting.storeEntry();
				}
			} catch (IOException e) {
				System.out.println("Error reading configuration file");
			}
		}
	}

	private static class ServerMimeMap {
		private HashMap<String, String> mapExt;

		public ServerMimeMap(File mimeFile){
			createMapping(mimeFile);
		}

		public String getMime(String ext){
			return mapExt.get(ext);
		}

		private void createMapping(File mimeFile){
			HashMap<String,String> extMap = new HashMap<>();
			try{

				Scanner fileReader = new Scanner(mimeFile);
				while(fileReader.hasNext()) {
					String str = fileReader.nextLine();
					if (str.startsWith("#") || str.startsWith(" ")) {
						continue;
					}
					String mimeType="";
					String[] splitArr = str.split("\\s+");
					mimeType = splitArr[0];
					for(int i = 1; i < splitArr.length;i++){
						extMap.put(splitArr[i],mimeType);
					}
				}
			}catch (IOException e){
				System.out.println("File not ");
			}
			this.mapExt = extMap;
		}
	}
}
