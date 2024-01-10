package com.icbc.match.utils;

public class KillNullUtil {
    public static String killNull(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString();
    }
}
