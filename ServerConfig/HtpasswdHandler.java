package ServerConfig;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

public class HtpasswdHandler {
    // jrob:{SHA}cRDtpNCeBiql5KOQsKVyrA0sAiA=

    public static HashMap<String, String> authUser = new HashMap<>();

    static String username;
    static String aliasPassword;
    static String encryptedpassword;
    static String sha;



    public HtpasswdHandler(String htpassword) {

        String[] line = htpassword.split("\\r?\\n");

        for(int i = 0; i < line.length;i++){

            String[] lineArr = line[i].split(":",2);

            authUser.put(lineArr[0],lineArr[1]);

        }
    }

    // if username is in the authUser key
    public static boolean isAuthorized(String encryptedString) throws UnsupportedEncodingException {

        byte[] decode = Base64.getDecoder().decode(encryptedString);

        String decodedValue = new String(decode,"UTF-8");

        String[] lineArr = decodedValue.split(":", 2);

        username = lineArr[0];

        return authUser.containsKey(username);
    }



    public static Boolean isAuthenticated(String encryptedString) throws UnsupportedEncodingException {

        byte[] decode = Base64.getDecoder().decode(encryptedString);

        String decodedValue = new String(decode,"UTF-8");

        String[] lineArr = decodedValue.split(":", 2);

        username = lineArr[0];

        if (lineArr.length == 2) {

            aliasPassword = lineArr[1];

        }

        encryptedpassword = authUser.get(username);
        encryptedpassword = encryptedpassword.replace("{SHA}","");
        // cRDtpNCeBiql5KOQsKVyrA0sAiA=

        try {

            aliasPassword = new sun.misc.BASE64Encoder().encode(java.security.MessageDigest.getInstance("SHA").digest(aliasPassword.getBytes()));
        }
        // if BASE64Encoder fails
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryptedpassword.equals((sha));


    }
}
