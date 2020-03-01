package ServerConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HtaccessHandler {

    public static String authUserFile;
    public static String authType;
    public static String authName;
    public static String require;

    public HtaccessHandler(String htacc) {

       String[] lineArr = htacc.split("//r?\\n");

        for (int i = 0; i < lineArr.length; i++) {
            String[] lineSplit = lineArr[i].split("\\s+", 2);
            try {
                String key = lineSplit[0];
                if (lineSplit[1].startsWith("\"")) {
                    lineSplit[1] = lineSplit[1].replaceAll("\"", "");
                }
                String value = lineSplit[1];
                switch (key) {
                    case "AuthUserFile":
                        authUserFile = value;
                        //System.out.println(key+"  " +value);
                        break;
                    case "AuthType":
                        authType = value;
                        //System.out.println(key+"  "+value);
                        break;
                    case "AuthName":
                        authName = value;
                        //System.out.println(key+"  "+value);
                        break;
                    case "Require":
                        require = value;
                        //System.out.println(key+"  "+value);
                        break;
                    default:
                        System.out.println("Invalid");
                }
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());

            }
        }

    }

    public Boolean isAuthenticated(String encryptedString) throws FileNotFoundException, IOException {
        if (HtpasswdHandler.isAuthorized(encryptedString)) {

            File htpasswdFile = new File("htFiles/.htpasswd");

            if (htpasswdFile.exists()) {
                FileInputStream htpswd = new FileInputStream(htpasswdFile);
                byte[] htpasswd_data = new byte[(int) htpasswdFile.length()];

                htpswd.read(htpasswd_data);
                htpswd.close();

                String content = new String(htpasswd_data, "UTF-8");
                HtpasswdHandler htpasswd = new HtpasswdHandler(content);

                return HtpasswdHandler.isAuthenticated(encryptedString);

            }
            else {

                System.out.print("Could not find file at " + authUserFile);

                return false;
            }
        }
        else {

            return false;
        }
    }

    public Boolean isAuthorized(String encryptedString) throws FileNotFoundException, IOException {

        File htpasswdFile = new File("htFiles/.htpasswd");

        FileInputStream htpswd = new FileInputStream(htpasswdFile);
        byte[] htpasswd_data = new byte[(int) htpasswdFile.length()];
        htpswd.read(htpasswd_data);
        htpswd.close();

        String content = new String(htpasswd_data, "UTF-8");
        HtpasswdHandler htpasswd = new HtpasswdHandler(content);

        return HtpasswdHandler.isAuthorized(encryptedString);
    }



}
