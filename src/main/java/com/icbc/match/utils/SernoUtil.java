package com.icbc.match.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class SernoUtil {

    public static String getSerno() {
        Date now = Calendar.getInstance().getTime();

        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");

        String serno1 = sdf3.format(now);

        String random = random(5);

        return serno1 + random;
    }

    public static String random(int width) {
        if (width <= 0) {
            return null;
        }

        Random random = new Random();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < width; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getSerno());
        }

        System.out.println("2018081416124363299".length());
    }
}
