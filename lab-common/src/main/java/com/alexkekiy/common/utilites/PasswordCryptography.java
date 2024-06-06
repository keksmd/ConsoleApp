package com.alexkekiy.common.utilites;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * утильный класс для шифрования паролей
 */
public class PasswordCryptography {
    private static final MessageDigest sha1;
    private PasswordCryptography() {}
    static {
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static String encodePassword(String password) {
        return  new String(sha1.digest(password.getBytes()));

    }

}