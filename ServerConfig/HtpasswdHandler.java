package ServerConfig;

import ServerUtils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;

public class HtpasswdHandler {

    private static HashMap<String, String> authUser = new HashMap<>();

    private static String username;
    static String sha1;

    public static boolean isAuthorized(String encryptedString) throws IOException {
        setPropertiesFromHtPasswordContent(readHtPasswordContent());
        String[] decodedValues = decodeBase64(encryptedString);
        username = decodedValues[0];
        return authUser.containsKey(username);
    }

    private static String[] decodeBase64(String encryptedString) {
        String decodeValue = "";
        byte[] decodeBytes = Base64.getDecoder().decode(encryptedString.getBytes());
        for (byte decodeByte : decodeBytes) {
            decodeValue = decodeValue + ((char) decodeByte);
        }
        return decodeValue.split(":", 2);
    }

    public static String readHtPasswordContent() throws IOException {
        String dir = System.getProperty("user.dir");
        File htpasswdFile = new File(dir + "/htFiles/.htpasswd");
        FileInputStream htpswd = new FileInputStream(htpasswdFile);
        byte[] htpasswd_data = new byte[(int) htpasswdFile.length()];
        htpswd.read(htpasswd_data);
        htpswd.close();
        return new String(htpasswd_data, StandardCharsets.UTF_8);
    }

    public static Boolean isAuthenticated(String encryptedString) throws IOException, NoSuchAlgorithmException {
        if (isAuthorized(encryptedString)) {
            setPropertiesFromHtPasswordContent(readHtPasswordContent());
            String aliasPassword = decodeBase64(encryptedString)[1];
            MessageDigest mDigest = MessageDigest.getInstance("SHA-1");
            byte[] result = mDigest.digest(aliasPassword.getBytes());
            String encryptedpassword = Base64.getEncoder().encodeToString(result);
            String storedPassword = authUser.get(username);
            storedPassword = storedPassword.replace("{SHA}", "");
            return encryptedpassword.equals(storedPassword);
        } else {
            return false;
        }
    }

    private static void setPropertiesFromHtPasswordContent(String content) {
        String[] lines = content.split("\\r?\\n");
        for(String line : lines){
            String[] lineArr = line.split(":",2);
            authUser.put(lineArr[0],lineArr[1]);
            Logger.setUser(lineArr[0]);
        }
    }
}
