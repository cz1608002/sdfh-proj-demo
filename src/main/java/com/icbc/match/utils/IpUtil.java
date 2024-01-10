package com.icbc.match.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class IpUtil {

    /**
     * 获取本机IP
     */
    public static String getCurrentClientIp() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }
}
