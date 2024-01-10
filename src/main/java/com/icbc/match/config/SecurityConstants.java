package com.icbc.match.config;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 30 * 60 * 1000;

    public static final String SECRET = "jwT@icbcMatch@seCret*95588~#!t";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String API_QRCODE_GENERATE_URL = "/api/qrcode/generate";
    public static final String API_QRCODE_QUERY_URL = "/api/qrcode/query";

    public static final String CORP_LOGIN_URL = "/custlogin/V1"; //合作方登录地址

    public static final String CLAIM_KEY_UUID = "uuid";
}