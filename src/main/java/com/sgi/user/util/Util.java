package com.sgi.user.util;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Util {

    @SneakyThrows
    public static String hashPassword(String password) {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
