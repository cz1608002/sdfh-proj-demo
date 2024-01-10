package com.icbc.match.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static Date dateParse(String src, String pattern) {
        if (StringUtils.isEmpty(src)) {
            return null;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(src);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }


}
