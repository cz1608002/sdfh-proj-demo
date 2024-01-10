package com.icbc.match.enums;

import org.json.JSONObject;

public enum AccReserveType {
    RESERVE("1", "保留"),
    UNRESERVE("2", "解保留"),
    FREZEE("3", "冻结"),
    UNFREZEE("4", "解冻"),
    LOST("5", "卡挂失");

    private String code;
    private String message;

    AccReserveType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return String.valueOf(code);
    }

    public void setCode(String code) {
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