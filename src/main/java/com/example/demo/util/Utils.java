package com.example.demo.util;

import java.util.Random;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }
    private static final Random random = new Random();

    public static String generatePermalink(String title) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        StringBuilder sb = new StringBuilder(title.length());

        for (int i = 0; i < title.length(); i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString().concat("-").concat(title);
    }
}
