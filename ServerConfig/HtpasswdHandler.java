package ServerConfig;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;
//import static org.apache.commons.codec.digest.MessageDigestAlgorithms.SHA_224;

public class HtpasswdHandler {
    // jrob:{SHA}cRDtpNCeBiql5KOQsKVyrA0sAiA=

    public static HashMap<String, String> authUser = new HashMap<>();

    static String username;
    static String aliasPassword;
    static String encryptedpassword;
    static String sha1;



    public HtpasswdHandler(String htpassword) {
        String[] line = htpassword.split("\\r?\\n");
        for(int i = 0; i < line.length;i++){
            String[] lineArr = line[i].split(":",2);
            authUser.put(lineArr[0],lineArr[1]);
        }
    }

    public static boolean isAuthorized(String encryptedString) {
        String[] decodedValues = decodeBase64(encryptedString);
        username = decodedValues[0];
        return authUser.containsKey(username);
    }

    public static String[] decodeBase64(String encryptedString) {
        String decodeValue = "";
        byte[] decodeBytes = Base64.getDecoder().decode(encryptedString.getBytes());
        for(int i = 0; i < decodeBytes.length; i++) {
            decodeValue = decodeValue + ((char) decodeBytes[i]);
        }
        String[] splitDecodeValue = decodeValue.split(":", 2);
        return splitDecodeValue;
    }

    public static Boolean isAuthenticated(String encryptedString) {
        try {
            aliasPassword = decodeBase64(encryptedString)[1];
            MessageDigest mDigest = MessageDigest.getInstance( "SHA-1" );
            byte[] result = mDigest.digest( aliasPassword.getBytes() );
            encryptedpassword = Base64.getEncoder().encodeToString(result);
            String storedPassword = authUser.get(username);
            storedPassword = storedPassword.replace("{SHA}","");
            return encryptedpassword.equals(storedPassword);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
