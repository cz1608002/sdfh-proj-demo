package com.icbc.match.entry;

import org.json.JSONObject;

public enum ResultCode {
    SUCCESS(0, "success"),
    FAILURE(-1, "出了点小问题"),

    VALIDATE_FAILURE(4001, "请求参数不合法"),

    API_INVOKE_ERROR(6001, "API接口调用异常"),

    USER_LOGIN_ERROR(7001, "用户名或密码不正确"),
    USER_REGIST_ERROR(7002, "用户名已存在"),
    USER_DECRYPT_ERROR(7007, "密码解密失败"),
    USER_NOTIFY_SIGN_ERROR(7004, "返回通知验签失败"),

    AUTH_FAIL(8001, "身份验证未授权"),
    AUTH_EXPIRE(8002, "身份认证过期");
    //兖矿工银聚

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return String.valueOf(code);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        try {
            JSONObject json = new JSONObject();
            json.put("retCode", String.valueOf(code));
            json.put("retMsg", message);

            return json.toString();
        } catch (Exception e) {
            return "json format error!";
        }
    }
}