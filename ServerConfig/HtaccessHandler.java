package ServerConfig;

public class HtaccessHandler {

    public String authUserFile;
    public String authType;
    public String authName;
    public String require;

    public HtaccessHandler(String htacc) {
       readPropertiesFromHtAccessFile(htacc);
    }

    private void readPropertiesFromHtAccessFile(String htacc) {
        String[] lineArr = htacc.split("\\r?\\n");
        for (String line : lineArr) {
            String[] lineSplit = line.split("\\s+", 2);
            String key = lineSplit[0];
            if (lineSplit[1].startsWith("\"")) {
                lineSplit[1] = lineSplit[1].replaceAll("\"", "");
            }
            String value = lineSplit[1];
            switch (key) {
                case "AuthUserFile":
                    authUserFile = value;
                    break;
                case "AuthType":
                    authType = value;
                    break;
                case "AuthName":
                    authName = value;
                    break;
                case "Require":
                    require = value;
                    break;
            }
        }
    }
}
