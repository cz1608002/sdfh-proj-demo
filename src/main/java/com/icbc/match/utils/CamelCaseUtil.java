package com.icbc.match.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCaseUtil {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    //处理map中的key转为下划线的情况
    public static Map<String, Object> toLine(Map map) {
        Map resMap = new HashMap();
        for (Object key : map.keySet()) {
            resMap.put(toLine(key.toString()), killNull(map.get(key)));
        }
        return resMap;
    }


    //处理map中的key不转为下划线的情况
    public static Map<String, Object> notToLine(Map map) {
        Map resMap = new HashMap();
        for (Object key : map.keySet()) {
            resMap.put(key.toString(), killNull(map.get(key)));
        }
        return resMap;
    }


    /**
     * 下划线转驼峰法
     *
     * @param line
     * @param smallCamel 大小驼峰,是否为小驼峰
     * @return
     */
    public static String toCamlCase(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0)) : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }


    /**
     * 驼峰转下划线
     *
     * @param str
     * @return
     */
    public static String toLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String[] toLine(String[] strs) {
        String[] res = new String[strs.length];
        for (int i = 0; i < strs.length; i++) {
            res[i] = toLine(strs[i]);
        }
        return res;
    }

    //测试
/*    public static void main(String[] args) {
        Gson gson = new Gson();
        String str = "corp_no ;" +
                "    corp_serno ;" +
                "    out_service_code;" +
                "    ori_corp_serno";
        System.out.println(toCamlCase(str, true));
        Map map = new HashMap();
        map.put("userName", "hello");
        map.put("nickName", "nickname");
        map.put("id", "123");
        Map res = toCamlCase(map);
        System.out.println("去掉" + gson.toJson(res));

    }*/

    public static Object killNull(Object object) {
        if (object == null) {
            return "";
        }
        return object;
    }


}
