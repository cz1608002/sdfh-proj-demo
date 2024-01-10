package com.icbc.match.utils;

import java.util.UUID;

public class UuidUtil {

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
