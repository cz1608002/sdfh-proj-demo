package com.icbc.match.config;

public class Apollo {

    //以下为采用@Value方式获取参数
    public static final String SIGN_NOTIFY_URL = "${sign.bizcontent.notifyurl}";

    public static final String SIGN_RETURN_URL = "${sign.bizcontent.returnurl}";
    public static final String MESSAGE_APPID = "${message.appid}";
    public static final String MESSAGE_ID = "${message.id}";
    public static final String MESSAGE_URL = "${message.url}";

    public static final String APP_ID = "${sm.app.id}";
    public static final String CORP_NO = "${sm.corp.no}";
    public static final String BANK_APIGW_PUBLIC_KEY = "${bank.apigw.public.key}";
    public static final String MY_APIGW_PRIVATE_KEY = "${my.apigw.private.key}";
    public static final String BANK_SM2_PUBLIC_KEY = "${bank.sm2.public.key}";
    public static final String SM4_VI = "${sm4.vi}";
    public static final String CORP_SM2_PUBLIC_KEY = "${corp.sm2.public.key}";
    public static final String CORP_SM2_PRIVATE_KEY = "${corp.sm2.private.key}";

}
