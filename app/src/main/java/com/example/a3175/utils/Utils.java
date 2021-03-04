package com.example.a3175.utils;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class Utils {
    private static final String TAG = "test";
    private static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            Log.d(TAG, "static initializer: " + e.getMessage());
        }
    }

    /**
     * For encoding a password string
     */
    public static String encode(String plaintext) {
        String encoded = "";
        messageDigest.update(plaintext.getBytes());
        encoded = new String(messageDigest.digest());
        return encoded;
    }

}
