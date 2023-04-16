package com.csub.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class PasswordManager {
    private static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();

    public static String encryptPassword(String password) {
        return encryptor.encryptPassword(password);
    }

    public static boolean checkPassword(String inputPassword, String encryptedPassword) {
        return encryptor.checkPassword(inputPassword, encryptedPassword);
    }
}
