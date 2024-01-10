package com.icbc.match.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    public static boolean isMatch(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
