package vn.snapbuck.sos.utils;


import vn.snapbuck.sos.security.Crypto;

/**
 * Created by Peter on 6/4/15.
 */
public class HeaderHelper {
    public static String createAuthorizationValue(String httpVerb, String date, String accessToken, String clientAppID, int contentLength){
        String result = "";
        String message = String.format("%s\n%s\n%d", date, httpVerb, contentLength);
        String hash = Crypto.getEncryptedHMAC(accessToken, message);
        result = String.format("sb %s:%s", clientAppID, hash);
        return result;
    }

}
