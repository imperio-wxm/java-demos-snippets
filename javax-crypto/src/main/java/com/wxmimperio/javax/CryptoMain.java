package com.wxmimperio.javax;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class CryptoMain {

    private static Cipher cipher;

    public static void main(String[] args) throws Exception {
        SecretKey secretKey = initKey("1234567890123456");
        String str = "wxmimperio";
        cipher = Cipher.getInstance("AES");

        if (secretKey != null) {
            //byte[] keyBytes = getBinaryKey(secretKey);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } else {
            System.out.println("key is null");
            return;
        }
        byte[] res = cipher.doFinal(str.getBytes(), 0, str.getBytes().length);
        System.out.println(new String(res));

        System.out.println(encipherMsg(res, secretKey));
    }

    private static SecretKey initKey(String key) {
        try {
            return new SecretKeySpec(key.getBytes(), 0, key.getBytes().length, "AES");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String encipherMsg(byte[] cipherText, Key k) {
        byte[] sourceText = null;

        try {
            cipher.init(Cipher.DECRYPT_MODE, k);
            sourceText = cipher.doFinal(cipherText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert sourceText != null;
        return new String(sourceText);
    }
}
