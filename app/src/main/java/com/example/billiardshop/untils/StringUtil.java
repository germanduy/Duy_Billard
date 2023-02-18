package com.example.billiardshop.untils;

public class StringUtil {
    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty() || ("").equals(input.trim());
    }
}
