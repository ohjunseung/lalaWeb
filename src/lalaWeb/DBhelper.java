package lalaWeb;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DBhelper {
    private static String hashSHA256(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digested = md.digest(msg.getBytes());
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digested.length; i++)
                hexString.append(Integer.toString((digested[i] & 0xff) + 0x100, 16).substring(1));
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
