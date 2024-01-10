package com.icbc.match.utils;

import com.icbc.match.entity.KeyV;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tools {

    public static String getTime(){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }

    public static String join(List<KeyV> kvs) {
        String s = "";
        Collections.sort(kvs, new Comparator<KeyV>() {
  //          @Override
            public int compare(KeyV o1, KeyV o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
        for (KeyV a : kvs) {
            s += "&" + a.getKey() + "=" + a.getValue();
        }
        return s.substring(1);

    }


    public static final String getMd5(String s) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] strTemp = new byte[0];
            try {
                strTemp = s.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        try {
            MessageDigest mdTemp = null;

            mdTemp = MessageDigest.getInstance("MD5");

            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            return (new String(str)).toUpperCase(Locale.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }
    public static String getUrl() {
        String path = "/statictem";

        File f = new File("/statictem/pub.cer");
        InputStream in = Tools.class.getClassLoader().getResourceAsStream("static/pub.cer");

        try {
            FileUtils.copyInputStreamToFile(in,f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;

    }



}
