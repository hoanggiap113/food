package com.food.utils;

public class UtilsCheckString {
    public static boolean checkString(String data) {
        if (data != null && !data.equals("")) return true;
        return false;
    }
}
