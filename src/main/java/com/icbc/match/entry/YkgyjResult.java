package com.icbc.match.entry;



import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Map;

public class YkgyjResult{

    private String code;
    private String msg;
    private Map<String, Object> params;

    protected YkgyjResult() {
    }

    protected YkgyjResult(String code, String message, Map<String, Object> data) {
        this.code = code;
        this.msg = message;
        this.params = data;
    }


    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param errorMsg  提示信息
     */
    public static JSONObject  failed(String errorCode, String errorMsg) {
            JSONObject json = new JSONObject();
            json.put("code", String.valueOf(errorCode));
            json.put("msg", errorMsg);
            json.put("data","");

            return json;


    }


    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     * @param errorMsg  提示信息
     */
    public static JSONObject  success(String errorCode, String errorMsg) {
        JSONObject json = new JSONObject();
        json.put("code", String.valueOf(errorCode));
        json.put("msg", errorMsg);
        json.put("data","");

        return json;


    }






    public String getRetCode() {
        return code;
    }

    public void setRetCode(String retCode) {
        this.code = code;
    }

    public String getRetMsg() {
        return msg;
    }

    public void setRetMsg(String msg) {
        this.msg = code;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
